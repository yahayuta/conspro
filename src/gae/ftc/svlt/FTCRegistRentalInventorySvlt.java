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
 * レンタル在庫登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistRentalInventorySvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// レンタル在庫データを転記
		Entity lcInventoryRecord = new Entity("RentalInventoryRecord", String.valueOf(System.currentTimeMillis()));
		lcInventoryRecord.setProperty("NAME",req.getParameter("NAME"));
		lcInventoryRecord.setProperty("TYPE",req.getParameter("TYPE"));
		lcInventoryRecord.setProperty("TYPE_SUB",req.getParameter("TYPE_SUB"));
		lcInventoryRecord.setProperty("MANUFACTURER",req.getParameter("MANUFACTURER"));
		lcInventoryRecord.setProperty("YEAR",req.getParameter("YEAR"));
		lcInventoryRecord.setProperty("SERIALNO",req.getParameter("SERIALNO"));
		lcInventoryRecord.setProperty("HOURS",req.getParameter("HOURS"));
		lcInventoryRecord.setProperty("OTHER_JA",req.getParameter("OTHER_JA"));
		lcInventoryRecord.setProperty("PIC_URL",req.getParameter("PIC_URL"));
		lcInventoryRecord.setProperty("SELLER_CODE",req.getParameter("SELLER_CODE"));
		lcInventoryRecord.setProperty("SELLER",req.getParameter("SELLER"));
		lcInventoryRecord.setProperty("PRICE_DAY",req.getParameter("PRICE_DAY"));
		lcInventoryRecord.setProperty("PRICE_MONTH",req.getParameter("PRICE_MONTH"));
		lcInventoryRecord.setProperty("PRICE_SUPPORT",req.getParameter("PRICE_SUPPORT"));
		lcInventoryRecord.setProperty("ENROLLMENT",req.getParameter("ENROLLMENT"));
		lcInventoryRecord.setProperty("DATA_FLG","0");
		lcInventoryRecord.setProperty("STATUS","0");

		lcInventoryRecord.setProperty("SIZE",req.getParameter("SIZE"));
		lcInventoryRecord.setProperty("WEIGHT",req.getParameter("WEIGHT"));
		lcInventoryRecord.setProperty("WEB_DISP",req.getParameter("WEB_DISP"));
		
		lcInventoryRecord.setProperty("MEMO", req.getParameter("MEMO"));

        try {
        	// DB登録
        	// 従業員データを登録する
        	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        	datastore.put(lcInventoryRecord);
        } catch (Throwable th) {
	    	throw new ServletException(th);
        }

        req.getSession().setAttribute("MSG", "登録しました");
        resp.sendRedirect("RegistRentalInventory.jsp?ACCOUNT=" + req.getParameter("ACCOUNT"));
   }
}
