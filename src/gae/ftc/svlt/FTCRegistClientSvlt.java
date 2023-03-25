package gae.ftc.svlt;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import gae.ftc.util.FTCConst;

/**
 * 顧客登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistClientSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    
			// 採番するためシーケンスデータを取得する
		    Key keyObj = KeyFactory.createKey("Seq", FTCConst.SEQ_ID_CLIENT);
		    Entity seq = null;
		    try {
		    	seq = datastore.get(keyObj);
		    } catch(EntityNotFoundException enfex) {
		    	seq = new Entity("Seq", FTCConst.SEQ_ID_CLIENT);
		    	seq.setProperty("SEQ", 0);
		    }
		    
		    // カウントアップ
		    seq.setProperty("SEQ", Long.parseLong(seq.getProperty("SEQ").toString()) + 1);
		    String seqStr = String.format("%04d", seq.getProperty("SEQ"));
		    
			String clientCode = req.getParameter("CLIENT_TYPE") + req.getParameter("CREDIT") + req.getParameter("COUNTRY") + seqStr;
			
			// チェック処理
			Query q = new Query("Client");
			q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
			q.setFilter(new FilterPredicate("CLIENT_CODE", FilterOperator.EQUAL, clientCode));
			PreparedQuery pq = datastore.prepare(q);
		    List<Entity> clientRecordList = (List<Entity>)pq.asList(FetchOptions.Builder.withDefaults());
		    if (clientRecordList != null && !clientRecordList.isEmpty()) {
		    	req.getSession().setAttribute("MSG", clientCode + " 顧客コードが重複しています。入力をやり直してください");
		        resp.sendRedirect("RegistClient.jsp?ACCOUNT=" + req.getParameter("ACCOUNT"));
		    	return;
		    }
			
			// パラメータ取得
		    Entity client = new Entity("Client", clientCode);
			client.setProperty("CLIENT_TYPE",req.getParameter("CLIENT_TYPE"));
			client.setProperty("CREDIT",req.getParameter("CREDIT"));
			client.setProperty("COUNTRY",req.getParameter("COUNTRY"));
			client.setProperty("SEQ", seqStr);
			client.setProperty("ADDRESS",req.getParameter("ADDRESS"));
			client.setProperty("ZIP",req.getParameter("ZIP"));
			client.setProperty("TEL",req.getParameter("TEL"));
			client.setProperty("FAX",req.getParameter("FAX"));
			client.setProperty("MAIL",req.getParameter("MAIL"));
			client.setProperty("COMPANY",req.getParameter("COMPANY"));
			client.setProperty("OFFICE",req.getParameter("OFFICE"));
			client.setProperty("NAME",req.getParameter("NAME"));
			client.setProperty("NAME2",req.getParameter("NAME2"));
			client.setProperty("NAME3",req.getParameter("NAME3"));
			client.setProperty("NAME4",req.getParameter("NAME4"));
			client.setProperty("NAME5",req.getParameter("NAME5"));
			client.setProperty("COMMENT",req.getParameter("COMMENT"));
			client.setProperty("DATA_FLG","0");
			client.setProperty("PREFIX",req.getParameter("PREFIX"));

        	datastore.put(client);
        	datastore.put(seq);
        } catch (Throwable th) {
	    	throw new ServletException(th);
        }

        resp.sendRedirect("RegResultClient.jsp");
   }
}
