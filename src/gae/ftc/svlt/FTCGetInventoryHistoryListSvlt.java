package gae.ftc.svlt;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * 在庫削除履歴一覧
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetInventoryHistoryListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// 履歴データを検索する
	    ServletContext sc = getServletContext();
	    Query q = new Query("InventoryRecord");
	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "9"));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listInventoryRecord = pq.asList(FetchOptions.Builder.withDefaults());
	    // 画面返却
	    arg0.setAttribute("InventoryRecordList", listInventoryRecord);
	    RequestDispatcher rd = sc.getRequestDispatcher("/InventoryHistoryList.jsp");
	    	
	    rd.forward(arg0, arg1);
	}
}
