package gae.ftc.svlt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * 在庫登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistInventorySvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 在庫データを転記
		Entity lcInventoryRecord = new Entity("InventoryRecord", String.valueOf(System.currentTimeMillis()));
		
		// WEB表示項目
		lcInventoryRecord.setProperty("NAME", req.getParameter("NAME"));
		lcInventoryRecord.setProperty("PRICE", req.getParameter("PRICE"));
		lcInventoryRecord.setProperty("CONDITION", req.getParameter("CONDITION"));
		lcInventoryRecord.setProperty("CONDITION_MAINTENANCE", req.getParameter("CONDITION_MAINTENANCE"));
		lcInventoryRecord.setProperty("TYPE", req.getParameter("TYPE"));
		lcInventoryRecord.setProperty("MANUFACTURER", req.getParameter("MANUFACTURER"));
		lcInventoryRecord.setProperty("YEAR", req.getParameter("YEAR"));
		lcInventoryRecord.setProperty("SERIALNO", req.getParameter("SERIALNO"));
		lcInventoryRecord.setProperty("HOURS", req.getParameter("HOURS"));
		lcInventoryRecord.setProperty("OTHER", req.getParameter("OTHER"));
		lcInventoryRecord.setProperty("OTHER_JA", req.getParameter("OTHER_JA"));
		lcInventoryRecord.setProperty("PIC_URL", req.getParameter("PIC_URL"));
		
		// 内部業務項目
		lcInventoryRecord.setProperty("ACCOUNT", req.getParameter("INACCOUNT"));
		lcInventoryRecord.setProperty("ORDER_DATE", req.getParameter("ORDER_DATE"));
		lcInventoryRecord.setProperty("WEB_DISP", req.getParameter("WEB_DISP"));
		lcInventoryRecord.setProperty("SELLER_CODE", req.getParameter("SELLER_CODE"));
		lcInventoryRecord.setProperty("SELLER", req.getParameter("SELLER"));
		lcInventoryRecord.setProperty("ORDER_PRICE", req.getParameter("ORDER_PRICE"));
		lcInventoryRecord.setProperty("ORDER_TRANS_COST", req.getParameter("ORDER_TRANS_COST"));
		lcInventoryRecord.setProperty("PARTS_COST", req.getParameter("PARTS_COST"));
		lcInventoryRecord.setProperty("MAINTENANCE_COST", req.getParameter("MAINTENANCE_COST"));
		lcInventoryRecord.setProperty("ORDER_OUT_ORDER_COST", req.getParameter("ORDER_OUT_ORDER_COST"));
		lcInventoryRecord.setProperty("ORDER_COST_PRICE", req.getParameter("ORDER_COST_PRICE"));
		lcInventoryRecord.setProperty("TRAN_PLACE", req.getParameter("TRAN_PLACE"));
		lcInventoryRecord.setProperty("SELL_TRANCE_COST", req.getParameter("SELL_TRANCE_COST"));
		lcInventoryRecord.setProperty("SHIP_COST", req.getParameter("SHIP_COST"));
		lcInventoryRecord.setProperty("SELL_OUT_ORDER_COST", req.getParameter("SELL_OUT_ORDER_COST"));
		lcInventoryRecord.setProperty("INS_COST", req.getParameter("INS_COST"));
		lcInventoryRecord.setProperty("FREIGHT_COST", req.getParameter("FREIGHT_COST"));
		lcInventoryRecord.setProperty("SELL_COST_PRICE", req.getParameter("SELL_COST_PRICE"));
		lcInventoryRecord.setProperty("SELL_PRICE", req.getParameter("SELL_PRICE"));
		lcInventoryRecord.setProperty("WHOL_PRICE", req.getParameter("WHOL_PRICE"));
		lcInventoryRecord.setProperty("PROFIT", req.getParameter("PROFIT"));
		lcInventoryRecord.setProperty("BUYER_CODE", req.getParameter("BUYER_CODE"));
		lcInventoryRecord.setProperty("BUYER", req.getParameter("BUYER"));
		lcInventoryRecord.setProperty("ORDER_PAY_DATE", req.getParameter("ORDER_PAY_DATE"));
		lcInventoryRecord.setProperty("SELL_PAY_DATE", req.getParameter("SELL_PAY_DATE"));
		lcInventoryRecord.setProperty("SELL_MONTH", req.getParameter("SELL_MONTH"));
		lcInventoryRecord.setProperty("MEMO", req.getParameter("MEMO"));
		
		lcInventoryRecord.setProperty("DATA_FLG","0");
		
		if ("0".equals(lcInventoryRecord.getProperty("WEB_DISP"))) {
			lcInventoryRecord.setProperty("WEB_DISP_DATE", "");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			lcInventoryRecord.setProperty("WEB_DISP_DATE", sdf.format(date));
		}

        try {
        	// DB登録
        	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        	datastore.put(lcInventoryRecord);
        } catch (Throwable th) {
	    	throw new ServletException(th);
        }

        resp.sendRedirect("RegResult.jsp?ACCOUNT=" + req.getParameter("ACCOUNT"));
   }
}
