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
 * レンタル注文売上編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditRentalOrderSalesSvlt extends HttpServlet {
	/** 種別：3  削除 */
	private static final String TYPE_DELETE = "3";
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		String id = arg0.getParameter("EDITID");
		
		// idが取れない場合エラー
		if (id == null || id.length() == 0) {
			// エラーページへ
			arg1.sendRedirect("Error.jsp");
			return;
		}
		
	    try {
			// マスターチェック実行
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("RentalOrderSales", id);
		    Entity rentalOrderSales = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (rentalOrderSales == null) {
				// エラーページへ
		    	throw new ServletException("処理異常です。データ不正です。");
	    	}

			String type = arg0.getParameter("type");
			if (TYPE_DELETE.equals(type)) {
				// 削除
				
				// 削除フラグを立てるだけ
				rentalOrderSales.setProperty("DATA_FLG", "9");
	        	datastore.put(rentalOrderSales);
				arg0.getSession().setAttribute("MSG", "選択したレンタル注文売上を削除ました");
				
				// 一覧画面へ
				arg1.sendRedirect("FTCGetRentalOrderSalesList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
