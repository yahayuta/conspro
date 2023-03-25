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

import gae.ftc.util.FTCCommonUtil;

/**
 * レンタル在庫編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditRentalInventorySvlt extends HttpServlet {

	/** 種別：0 更新画面起動 */
	private static final String TYPE_EDIT = "0";
	/** 種別：1  更新確定 */
	private static final String TYPE_EDIT_COMMIT = "1";
	/** 種別：2  削除 */
	private static final String TYPE_DELETE = "2";
	/** 種別：3  写真更新 */
	private static final String TYPE_PICUPDATE = "3";
	/** 種別：4 詳細画面起動 */
	private static final String TYPE_DETAIL = "4";
	
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
		    Key keyObj = KeyFactory.createKey("RentalInventoryRecord", id);
		    Entity inventoryRecord = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (inventoryRecord == null) {
				// エラーページへ
		    	throw new ServletException("処理異常です。データ不正です。");
	    	}

			String type = arg0.getParameter("type");
			// 更新
			if (TYPE_EDIT.equals(type)) {
				// 更新ページへ遷移	
				
				// 日報検索
				FTCCommonUtil.searchDailyReport(inventoryRecord, arg0);
				// レンタル注文検索
				FTCCommonUtil.searchRentalOrderHistory(inventoryRecord, arg0);
				// 引合情報検索
				FTCCommonUtil.searchPreRentalOrder(inventoryRecord, arg0);
				
			    arg0.setAttribute("RentalInventoryRecord", inventoryRecord);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateRentalInventory.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				// 更新確定
				
				// 更新実行
				inventoryRecord.setProperty("NAME",arg0.getParameter("NAME"));
				inventoryRecord.setProperty("TYPE",arg0.getParameter("TYPE"));
				inventoryRecord.setProperty("TYPE_SUB",arg0.getParameter("TYPE_SUB"));
				inventoryRecord.setProperty("MANUFACTURER",arg0.getParameter("MANUFACTURER"));
				inventoryRecord.setProperty("YEAR",arg0.getParameter("YEAR"));
				inventoryRecord.setProperty("SERIALNO",arg0.getParameter("SERIALNO"));
				inventoryRecord.setProperty("HOURS",arg0.getParameter("HOURS"));
				inventoryRecord.setProperty("OTHER_JA",arg0.getParameter("OTHER_JA"));
				inventoryRecord.setProperty("PIC_URL",arg0.getParameter("PIC_URL"));
				inventoryRecord.setProperty("SELLER_CODE",arg0.getParameter("SELLER_CODE"));
				inventoryRecord.setProperty("SELLER",arg0.getParameter("SELLER"));
				inventoryRecord.setProperty("PRICE_DAY",arg0.getParameter("PRICE_DAY"));
				inventoryRecord.setProperty("PRICE_MONTH",arg0.getParameter("PRICE_MONTH"));
				inventoryRecord.setProperty("PRICE_SUPPORT",arg0.getParameter("PRICE_SUPPORT"));
				inventoryRecord.setProperty("ENROLLMENT",arg0.getParameter("ENROLLMENT"));

				inventoryRecord.setProperty("SIZE",arg0.getParameter("SIZE"));
				inventoryRecord.setProperty("WEIGHT",arg0.getParameter("WEIGHT"));
				inventoryRecord.setProperty("WEB_DISP",arg0.getParameter("WEB_DISP"));

				inventoryRecord.setProperty("MEMO", arg0.getParameter("MEMO"));

	        	datastore.put(inventoryRecord);
	        	
				// 一覧画面へ
	            arg0.getSession().setAttribute("MSG", "更新しました");
				arg1.sendRedirect("FTCGetRentalInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_DELETE.equals(type)) {
				// 削除
				
				// 出庫中のものは削除できない
				if ("1".equals(inventoryRecord.getProperty("STATUS"))) {
					arg0.getSession().setAttribute("MSG", "出庫中のため削除できません");
				} else {
					// 削除フラグを立てるだけ
					inventoryRecord.setProperty("DATA_FLG","9");
		        	datastore.put(inventoryRecord);
		        	arg0.getSession().setAttribute("MSG", "選択したレンタル在庫を削除ました");
				}
				
				// 一覧画面へ
				arg1.sendRedirect("FTCGetRentalInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			}  else if (TYPE_PICUPDATE.equals(type)) {
				// 写真更新 
			    // セッションにつっこむ
			    arg0.getSession().setAttribute("RentalInventoryRecord", inventoryRecord.getKey().getName());
			    arg0.getSession().setAttribute("AuthACCOUNT", arg0.getParameter("ACCOUNT"));
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdatePictureRental.jsp?NAME=" + inventoryRecord.getProperty("NAME") + "&MANUFACTURER=" + inventoryRecord.getProperty("MANUFACTURER"));
			    rd.forward(arg0, arg1);
			} else if (TYPE_DETAIL.equals(type)) {
				// 詳細
				
				// 日報検索
				FTCCommonUtil.searchDailyReport(inventoryRecord, arg0);
				// レンタル注文検索
				FTCCommonUtil.searchRentalOrderHistory(inventoryRecord, arg0);
				// 引合情報検索
				FTCCommonUtil.searchPreRentalOrder(inventoryRecord, arg0);
				
				// 詳細ページへ遷移
			    arg0.setAttribute("RentalInventoryRecord", inventoryRecord);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/RentalInventoryDetail.jsp");
			    rd.forward(arg0, arg1);
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
