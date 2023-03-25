package gae.ftc.svlt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * レンタル在庫詳細
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCDetailRentalInventorySvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

	    try {
			String id = arg0.getParameter("EDITID");
			
			// idが取れない場合エラー
			if (id == null || id.length() == 0) {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
				return;
			}

			// マスターチェック実行
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("RentalInventoryRecord", id);
		    Entity inventoryRecord = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (inventoryRecord == null) {
				// エラーページへ
		    	throw new ServletException("処理異常です。データ不正です。");
	    	}
	
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/FTCRentalInventoryDetail.jsp");
		    arg0.setAttribute("InventoryRecord", inventoryRecord);
		    arg0.setAttribute("TYPE", arg0.getParameter("TYPE"));
		    rd.forward(arg0, arg1);
		    
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	    
	}
}
