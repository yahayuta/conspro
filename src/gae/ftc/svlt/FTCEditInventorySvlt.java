package gae.ftc.svlt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * 在庫編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditInventorySvlt extends HttpServlet {

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
	/** 種別：5 在庫締め */
	private static final String TYPE_CLOSE = "5";
	/** 種別：6 締め戻し */
	private static final String TYPE_CLOSE_BACK = "6";
	
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
		    Key keyObj = KeyFactory.createKey("InventoryRecord", id);
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
				
			    arg0.setAttribute("InventoryRecord", inventoryRecord);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateInventory.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				// 更新確定
				
				// 更新実行
				inventoryRecord.setProperty("NAME", arg0.getParameter("NAME"));
				inventoryRecord.setProperty("PRICE", arg0.getParameter("PRICE"));
				inventoryRecord.setProperty("CONDITION", arg0.getParameter("CONDITION"));
				inventoryRecord.setProperty("CONDITION_MAINTENANCE", arg0.getParameter("CONDITION_MAINTENANCE"));
				inventoryRecord.setProperty("TYPE", arg0.getParameter("TYPE"));
				inventoryRecord.setProperty("MANUFACTURER", arg0.getParameter("MANUFACTURER"));
				inventoryRecord.setProperty("YEAR", arg0.getParameter("YEAR"));
				inventoryRecord.setProperty("SERIALNO", arg0.getParameter("SERIALNO"));
				inventoryRecord.setProperty("HOURS", arg0.getParameter("HOURS"));
				inventoryRecord.setProperty("OTHER", arg0.getParameter("OTHER"));
				inventoryRecord.setProperty("OTHER_JA", arg0.getParameter("OTHER_JA"));
				inventoryRecord.setProperty("PIC_URL", arg0.getParameter("PIC_URL"));
				
				// 内部業務項目
				inventoryRecord.setProperty("ACCOUNT", arg0.getParameter("INACCOUNT"));
				inventoryRecord.setProperty("ORDER_DATE", arg0.getParameter("ORDER_DATE"));
				inventoryRecord.setProperty("WEB_DISP", arg0.getParameter("WEB_DISP"));
				inventoryRecord.setProperty("SELLER_CODE", arg0.getParameter("SELLER_CODE"));
				inventoryRecord.setProperty("SELLER", arg0.getParameter("SELLER"));
				inventoryRecord.setProperty("ORDER_PRICE", arg0.getParameter("ORDER_PRICE"));
				inventoryRecord.setProperty("ORDER_TRANS_COST", arg0.getParameter("ORDER_TRANS_COST"));
				inventoryRecord.setProperty("PARTS_COST", arg0.getParameter("PARTS_COST"));
				inventoryRecord.setProperty("MAINTENANCE_COST", arg0.getParameter("MAINTENANCE_COST"));
				inventoryRecord.setProperty("ORDER_OUT_ORDER_COST", arg0.getParameter("ORDER_OUT_ORDER_COST"));
				inventoryRecord.setProperty("ORDER_COST_PRICE", arg0.getParameter("ORDER_COST_PRICE"));
				inventoryRecord.setProperty("TRAN_PLACE", arg0.getParameter("TRAN_PLACE"));
				inventoryRecord.setProperty("SELL_TRANCE_COST", arg0.getParameter("SELL_TRANCE_COST"));
				inventoryRecord.setProperty("SHIP_COST", arg0.getParameter("SHIP_COST"));
				inventoryRecord.setProperty("SELL_OUT_ORDER_COST", arg0.getParameter("SELL_OUT_ORDER_COST"));
				inventoryRecord.setProperty("INS_COST", arg0.getParameter("INS_COST"));
				inventoryRecord.setProperty("FREIGHT_COST", arg0.getParameter("FREIGHT_COST"));
				inventoryRecord.setProperty("SELL_COST_PRICE", arg0.getParameter("SELL_COST_PRICE"));
				inventoryRecord.setProperty("SELL_PRICE", arg0.getParameter("SELL_PRICE"));
				inventoryRecord.setProperty("WHOL_PRICE", arg0.getParameter("WHOL_PRICE"));
				inventoryRecord.setProperty("PROFIT", arg0.getParameter("PROFIT"));
				inventoryRecord.setProperty("BUYER_CODE", arg0.getParameter("BUYER_CODE"));
				inventoryRecord.setProperty("BUYER", arg0.getParameter("BUYER"));
				inventoryRecord.setProperty("ORDER_PAY_DATE", arg0.getParameter("ORDER_PAY_DATE"));
				inventoryRecord.setProperty("SELL_PAY_DATE", arg0.getParameter("SELL_PAY_DATE"));
				inventoryRecord.setProperty("SELL_MONTH", arg0.getParameter("SELL_MONTH"));
				inventoryRecord.setProperty("MEMO", arg0.getParameter("MEMO"));
				
				if ("0".equals(inventoryRecord.getProperty("WEB_DISP"))) {
					inventoryRecord.setProperty("WEB_DISP_DATE", "");
				} else if ("0".equals(arg0.getParameter("LOADWEB_DISP")) && !"0".equals(inventoryRecord.getProperty("WEB_DISP"))) {
					// 非表示から変更された場合のみ書き換える
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					inventoryRecord.setProperty("WEB_DISP_DATE", sdf.format(date));
				}
				
	        	datastore.put(inventoryRecord);
	        	
				// 一覧画面へ
				arg1.sendRedirect("FTCGetInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_DELETE.equals(type)) {
				// 削除
				
				// 削除フラグを立てるだけ
				inventoryRecord.setProperty("DATA_FLG", "9");
	        	datastore.put(inventoryRecord);
				arg0.getSession().setAttribute("MSG", "選択した在庫を削除ました");
				
				// 一覧画面へ
				arg1.sendRedirect("FTCGetInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_PICUPDATE.equals(type)) {
				// 写真更新 
			    // セッションにつっこむ
			    arg0.getSession().setAttribute("InventoryRecord", inventoryRecord.getKey().getName());
			    arg0.getSession().setAttribute("AuthACCOUNT", arg0.getParameter("ACCOUNT"));
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdatePicture.jsp?NAME=" + inventoryRecord.getProperty("NAME") + "&MANUFACTURER=" + inventoryRecord.getProperty("MANUFACTURER"));
			    rd.forward(arg0, arg1);
			} else if (TYPE_DETAIL.equals(type)) {
				// 詳細
				
				// 日報検索
				FTCCommonUtil.searchDailyReport(inventoryRecord, arg0);
				
				// 詳細ページへ遷移
			    arg0.setAttribute("InventoryRecord", inventoryRecord);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/InventoryDetail.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_CLOSE.equals(type)) {
				// 在庫締め
				
				String sellMonth = FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_MONTH"));
				if (sellMonth == null || sellMonth.length() == 0) {
					arg0.getSession().setAttribute("MSG", "エラー：売上月が入力されていないため締めることができません");
				} else {
					// データフラグを締めに更新
					inventoryRecord.setProperty("DATA_FLG", "1");
		        	datastore.put(inventoryRecord);
					arg0.getSession().setAttribute("MSG", "選択した在庫を締めました");
				}
	        	
				// 一覧画面へ
				arg1.sendRedirect("FTCGetInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_CLOSE_BACK.equals(type)) {
				// 締め戻し
	        	
				// データフラグを通常に戻す
				inventoryRecord.setProperty("DATA_FLG", "0");
	        	datastore.put(inventoryRecord);
				arg0.getSession().setAttribute("MSG", "選択した在庫を締め戻しました");
				
				// 一覧画面へ
				arg1.sendRedirect("FTCGetOldInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
