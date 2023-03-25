package gae.ftc.svlt;

import java.io.IOException;

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
 * レンタル注文登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistRentalOrderSvlt extends HttpServlet {

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
		
		try {
			// マスターチェック実行
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("RentalInventoryRecord", rentalInvId);
		    Entity inventoryRecord = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (inventoryRecord == null) {
				// エラーページへ
				resp.sendRedirect("Error.jsp");
	            return;
	    	}
	    	
	    	// 区分値不正の場合はエラー
	    	if (!"0".equals(inventoryRecord.getProperty("STATUS"))) {
				// エラーページへ
	            req.getSession().setAttribute("MSG", "レンタル在庫は空車ではないため注文登録できません");
	            resp.sendRedirect("RegistRentalOrder.jsp?ACCOUNT=" + req.getParameter("ACCOUNT"));
	            return;
            }
	    	
	    	//レンタル在庫を出庫中に更新する
	    	inventoryRecord.setProperty("STATUS", "1"); 
        	datastore.put(inventoryRecord);
        
			// レンタル注文データを転記
	        Entity rentalOrder = new Entity("RentalOrder", String.valueOf(System.currentTimeMillis()));
			rentalOrder.setProperty("RENTAL_INVENTORY_ID", rentalInvId);
			rentalOrder.setProperty("NAME", req.getParameter("NAME"));
			rentalOrder.setProperty("SERIALNO", req.getParameter("SERIALNO"));
			rentalOrder.setProperty("PRICE_DAY", req.getParameter("PRICE_DAY"));
			rentalOrder.setProperty("PRICE_MONTH", req.getParameter("PRICE_MONTH"));
			rentalOrder.setProperty("PRICE_SUPPORT", req.getParameter("PRICE_SUPPORT"));
			rentalOrder.setProperty("HOURS_START", req.getParameter("HOURS_START"));
			rentalOrder.setProperty("ORDER_TYPE", req.getParameter("ORDER_TYPE"));
			rentalOrder.setProperty("OUT_DATE", req.getParameter("OUT_DATE"));
			rentalOrder.setProperty("IN_DATE", req.getParameter("IN_DATE"));
			rentalOrder.setProperty("START_DATE", req.getParameter("START_DATE"));
			rentalOrder.setProperty("END_DATE", req.getParameter("END_DATE"));
			rentalOrder.setProperty("HOURS_END", req.getParameter("HOURS_END"));
			rentalOrder.setProperty("AMOUNT", req.getParameter("AMOUNT"));
			rentalOrder.setProperty("SUPPORT_FEE", req.getParameter("SUPPORT_FEE"));
			rentalOrder.setProperty("TRANS_FEE", req.getParameter("TRANS_FEE"));
			rentalOrder.setProperty("RETURN_FEE", req.getParameter("RETURN_FEE"));
			rentalOrder.setProperty("ORDER_NAME", req.getParameter("ORDER_NAME"));
			rentalOrder.setProperty("ORDER_PLACE", req.getParameter("ORDER_PLACE"));
			rentalOrder.setProperty("CLIENT_CODE", req.getParameter("CLIENT_CODE"));
			rentalOrder.setProperty("CLIENT_NAME", req.getParameter("CLIENT_NAME"));
			rentalOrder.setProperty("MEMO", req.getParameter("MEMO"));
			rentalOrder.setProperty("DATA_FLG", "0");
	
			rentalOrder.setProperty("COUNT", req.getParameter("COUNT"));
			rentalOrder.setProperty("PRICE", req.getParameter("PRICE"));
			rentalOrder.setProperty("FEE1", req.getParameter("FEE1"));
			rentalOrder.setProperty("FEE2", req.getParameter("FEE2"));
			rentalOrder.setProperty("FEE3", req.getParameter("FEE3"));
			rentalOrder.setProperty("FEE4", req.getParameter("FEE4"));
			rentalOrder.setProperty("FEE5", req.getParameter("FEE5"));
			rentalOrder.setProperty("FEE6", req.getParameter("FEE6"));
			rentalOrder.setProperty("TOTAL", req.getParameter("TOTAL"));
			rentalOrder.setProperty("FEE1_NAME", req.getParameter("FEE1_NAME"));
			rentalOrder.setProperty("FEE2_NAME", req.getParameter("FEE2_NAME"));
			rentalOrder.setProperty("FEE3_NAME", req.getParameter("FEE3_NAME"));
			rentalOrder.setProperty("FEE4_NAME", req.getParameter("FEE4_NAME"));
			rentalOrder.setProperty("FEE5_NAME", req.getParameter("FEE5_NAME"));
			rentalOrder.setProperty("FEE6_NAME", req.getParameter("FEE6_NAME"));
		
			rentalOrder.setProperty("ENROLLMENT", req.getParameter("ENROLLMENT"));

			rentalOrder.setProperty("CLOSE_DATE", req.getParameter("CLOSE_DATE"));
			//rentalOrder.setProperty("REQUEST_DATE", req.getParameter("REQUEST_DATE"));
			rentalOrder.setProperty("CLIENT_REP", req.getParameter("CLIENT_REP"));
			rentalOrder.setProperty("CLIENT_TEL", req.getParameter("CLIENT_TEL"));
			
			rentalOrder.setProperty("FEE1_MEMO", req.getParameter("FEE1_MEMO"));
			rentalOrder.setProperty("FEE2_MEMO", req.getParameter("FEE2_MEMO"));
			rentalOrder.setProperty("FEE3_MEMO", req.getParameter("FEE3_MEMO"));
			rentalOrder.setProperty("FEE4_MEMO", req.getParameter("FEE4_MEMO"));
			rentalOrder.setProperty("FEE5_MEMO", req.getParameter("FEE5_MEMO"));
			rentalOrder.setProperty("FEE6_MEMO", req.getParameter("FEE6_MEMO"));

			rentalOrder.setProperty("AMOUNT_MEMO", req.getParameter("AMOUNT_MEMO"));
			rentalOrder.setProperty("SUPPORT_MEMO", req.getParameter("SUPPORT_MEMO"));
			rentalOrder.setProperty("TRANS_FEE_MEMO", req.getParameter("TRANS_FEE_MEMO"));
			rentalOrder.setProperty("RETURN_FEE_MEMO", req.getParameter("RETURN_FEE_MEMO"));

			// 初回請求待ちで登録
	    	rentalOrder.setProperty("CLOSE_REQ", "3");

        	datastore.put(rentalOrder);
        } catch (Throwable th) {
	    	throw new ServletException(th);
        }

        req.getSession().setAttribute("MSG", "登録しました");
        resp.sendRedirect("RegistRentalOrder.jsp?ACCOUNT=" + req.getParameter("ACCOUNT"));
   }
}
