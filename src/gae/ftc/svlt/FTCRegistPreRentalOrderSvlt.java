package gae.ftc.svlt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * レンタル注文引合登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistPreRentalOrderSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// レンタル在庫情報チェック
		String rentalInvId = req.getParameter("RENTAL_INVENTORY_ID");
		
		// idが取れない場合エラー
		if (rentalInvId == null || rentalInvId.length() == 0) {
			// エラーページへ
			resp.sendRedirect("Error.jsp");
			return;
		}
		
		// 在庫単位に分割
		String[] rentalInvIdｓ = rentalInvId.split(",");
	   
		for (int i = 0; i < rentalInvIdｓ.length; i++) {
			try {			
				// レンタル注文データを転記
				Entity preRentalOrder = new Entity("PreRentalOrder", String.valueOf(System.currentTimeMillis()));
				preRentalOrder.setProperty("RENTAL_INVENTORY_ID", rentalInvIdｓ[i]);
				preRentalOrder.setProperty("START_DATE", req.getParameter("START_DATE"));
				preRentalOrder.setProperty("CLIENT_CODE", req.getParameter("CLIENT_CODE"));
				preRentalOrder.setProperty("CLIENT_NAME", req.getParameter("CLIENT_NAME"));
				preRentalOrder.setProperty("CLIENT_REP", req.getParameter("CLIENT_REP"));
				preRentalOrder.setProperty("CLIENT_TEL", req.getParameter("CLIENT_TEL"));
				preRentalOrder.setProperty("MEMO", req.getParameter("MEMO"));
				preRentalOrder.setProperty("DATA_FLG", "0");
				
			    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    	datastore.put(preRentalOrder);
		    } catch (Throwable th) {
		    	throw new ServletException(th);
		    }
	   }
	   resp.sendRedirect("RegResultPreOrder.jsp");
   }
}
