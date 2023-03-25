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
 * その他機械登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistOtherInventorySvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 在庫データを転記
		Entity lcInventoryRecord = new Entity("InventoryRecord", String.valueOf(System.currentTimeMillis()));
		
		lcInventoryRecord.setProperty("NAME", req.getParameter("NAME"));
		lcInventoryRecord.setProperty("CONDITION", req.getParameter("CONDITION"));
		lcInventoryRecord.setProperty("TYPE", req.getParameter("TYPE"));
		lcInventoryRecord.setProperty("MANUFACTURER", req.getParameter("MANUFACTURER"));
		lcInventoryRecord.setProperty("YEAR", req.getParameter("YEAR"));
		lcInventoryRecord.setProperty("SERIALNO", req.getParameter("SERIALNO"));
		lcInventoryRecord.setProperty("HOURS", req.getParameter("HOURS"));
		lcInventoryRecord.setProperty("OTHER_JA", req.getParameter("OTHER_JA"));
		lcInventoryRecord.setProperty("ACCOUNT", req.getParameter("INACCOUNT"));
		lcInventoryRecord.setProperty("SELLER_CODE", req.getParameter("SELLER_CODE"));
		lcInventoryRecord.setProperty("SELLER", req.getParameter("SELLER"));
		lcInventoryRecord.setProperty("MEMO", req.getParameter("MEMO"));
		
		lcInventoryRecord.setProperty("DATA_FLG","5");

        try {
        	// DB登録
        	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        	datastore.put(lcInventoryRecord);
        } catch (Throwable th) {
	    	throw new ServletException(th);
        }

        req.getSession().setAttribute("MSG", "登録しました");
        resp.sendRedirect("RegistOtherInventory.jsp?ACCOUNT=" + req.getParameter("ACCOUNT"));
   }
}
