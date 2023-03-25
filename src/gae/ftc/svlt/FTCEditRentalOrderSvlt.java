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
 * レンタル注文編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditRentalOrderSvlt extends HttpServlet {

	/** 種別：0 更新画面起動 */
	private static final String TYPE_EDIT = "0";
	/** 種別：1  更新確定 */
	private static final String TYPE_EDIT_COMMIT = "1";
	/** 種別：2  売上確定 */
	//private static final String TYPE_ORDER_SALES = "2";
	/** 種別：3  削除 */
	private static final String TYPE_DELETE = "3";
	/** 種別：4  注文締め */
	//private static final String TYPE_CLOSE = "4";
	/** 種別：5 締め戻し */
	private static final String TYPE_CLOSE_BACK = "5";
	/** 種別：4 詳細画面起動 */
	private static final String TYPE_DETAIL = "6";
	
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
		    Key keyObj = KeyFactory.createKey("RentalOrder", id);
		    Entity rentalOrder = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (rentalOrder == null) {
		    	throw new ServletException("処理異常です。レンタル注文データ不正です。");
	    	}

	    	// 併せてレンタル在庫情報も取得
		    Key keyObjRentalInv = KeyFactory.createKey("RentalInventoryRecord", rentalOrder.getProperty("RENTAL_INVENTORY_ID").toString());
		    Entity inventoryRecord = datastore.get(keyObjRentalInv);
		    
	    	// マスタにない場合エラー
	    	if (inventoryRecord == null) {
		    	throw new ServletException("処理異常です。レンタル在庫データ不正です。");
	    	}
	    	
			String type = arg0.getParameter("type");
			// 更新
			if (TYPE_EDIT.equals(type)) {
				// 日報検索
				FTCCommonUtil.searchDailyReport(inventoryRecord, arg0);
				// 引合情報検索
				FTCCommonUtil.searchPreRentalOrder(inventoryRecord, arg0);
				// 請求履歴検索
				FTCCommonUtil.searchRentalOrderInvoiceHistory(rentalOrder, arg0);
				
				// 更新ページへ遷移			
			    arg0.setAttribute("RentalOrder", rentalOrder);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateRentalOrder.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_DETAIL.equals(type)) {
				// 日報検索
				FTCCommonUtil.searchDailyReport(inventoryRecord, arg0);
				// 引合情報検索
				FTCCommonUtil.searchPreRentalOrder(inventoryRecord, arg0);
				// 請求履歴検索
				FTCCommonUtil.searchRentalOrderInvoiceHistory(rentalOrder, arg0);
				
				// 詳細ページへ遷移			
			    arg0.setAttribute("RentalOrder", rentalOrder);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/RentalOrderDetail.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				// 更新確定
				FTCCommonUtil.updateRentalOrder(datastore, rentalOrder, arg0);
				
				// 更新実行
				rentalOrder.setProperty("ORDER_TYPE", arg0.getParameter("ORDER_TYPE"));
				
				// 一覧画面へ
	            arg0.getSession().setAttribute("MSG", "更新しました");
				arg1.sendRedirect("FTCEditRentalOrder?type=0&ACCOUNT=" + arg0.getParameter("ACCOUNT") + "&EDITID=" + id);
//			} else if (TYPE_ORDER_SALES.equals(type)) {
//				// 売上月が取れない場合エラー
//				String salesMonth = arg0.getParameter("SALES_MONTH");
//				if (salesMonth == null || salesMonth.length() == 0) {
//					arg0.getSession().setAttribute("MSG", "売上月を指定してください");
//					arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
//					return;
//				}
//				
//				// 請求日が取れない場合売上確定は不可とする
//				if (rentalOrder.getProperty("REQUEST_DATE") == null || rentalOrder.getProperty("REQUEST_DATE").toString().length() == 0) {
//					arg0.getSession().setAttribute("MSG", "請求されていないため売上確定を中止しました");
//					arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
//					return;
//				}
//				
//				// 売上確定
//				Entity rentalOrderSales = new Entity("RentalOrderSales", String.valueOf(System.currentTimeMillis()));
//				rentalOrderSales.setProperty("RENTAL_INVENTORY_ID", rentalOrder.getProperty("RENTAL_INVENTORY_ID"));
//				rentalOrderSales.setProperty("RENTAL_ORDER_ID", rentalOrder.getKey().getName());
//				rentalOrderSales.setProperty("SALES_MONTH", salesMonth);
//				rentalOrderSales.setProperty("AMOUNT", String.valueOf(FTCCommonUtil.parseDouble(rentalOrder.getProperty("TOTAL").toString())));
//				rentalOrderSales.setProperty("CLIENT_NAME", rentalOrder.getProperty("CLIENT_NAME"));
//				rentalOrderSales.setProperty("RENTAL_INVENTORY_CODE", rentalOrder.getProperty("NAME").toString() + rentalOrder.getProperty("SERIALNO").toString());
//				rentalOrderSales.setProperty("DATA_FLG", "0");
//				
//	        	// DB登録
//	        	datastore.put(rentalOrderSales);
//		        
//				arg0.getSession().setAttribute("MSG", "選択したレンタル注文の売上を確定しました");
//				
//				// 一覧画面へ
//				arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
//				
			} else if (TYPE_DELETE.equals(type)) {
				// 削除
				
				// 強制的に空車状態にする
		    	inventoryRecord.setProperty("STATUS", "0");
		        datastore.put(inventoryRecord);
		        
				// 削除フラグを立てるだけ
				rentalOrder.setProperty("DATA_FLG", "9");
				datastore.put(rentalOrder);
				arg0.getSession().setAttribute("MSG", "選択したレンタル注文を削除ました");
				
				// 一覧画面へ
				arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
//			} else if (TYPE_CLOSE.equals(type)) {
//				// 注文締め
//				
//				// 終了日と返却日と全請求完了日が取れない場合注文締めは不可とする
//				if (rentalOrder.getProperty("REQUEST_END_DATE") == null || rentalOrder.getProperty("REQUEST_END_DATE").toString().length() == 0) {
//					arg0.getSession().setAttribute("MSG", "請求完了されていないため締めを中止しました");
//				} else if (rentalOrder.getProperty("IN_DATE") == null || rentalOrder.getProperty("IN_DATE").toString().length() == 0) {
//					arg0.getSession().setAttribute("MSG", "返却されていないため締めを中止しました");
//				} else if (rentalOrder.getProperty("END_DATE") == null || rentalOrder.getProperty("END_DATE").toString().length() == 0) {
//					arg0.getSession().setAttribute("MSG", "レンタル終了されていないため締めを中止しました");
//				} else {				    
//			    	//念のためレンタル在庫を強制的に空車に更新する
//				    inventoryRecord.setProperty("STATUS", "0");
//			        datastore.put(inventoryRecord);
//			        
//					// 注文締めフラグを立てるだけ
//					rentalOrder.setProperty("DATA_FLG", "1");
//		        	datastore.put(rentalOrder);
//					arg0.getSession().setAttribute("MSG", "選択したレンタル注文を締めました");
//				}
//				
//				// 一覧画面へ
//				arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_CLOSE_BACK.equals(type)) {
				// 締め戻し
	        	
				// 出庫状態にするため
			    if ("1".equals(inventoryRecord.getProperty("STATUS"))) {
					arg0.getSession().setAttribute("MSG", "出庫中のため注文を締め戻しできません");
				} else {
			    	//レンタル在庫を出庫中に更新する
					inventoryRecord.setProperty("STATUS", "1");
			        datastore.put(inventoryRecord);
			        
					// データフラグを通常に戻す
					rentalOrder.setProperty("DATA_FLG", "0");
					datastore.put(rentalOrder);
					arg0.getSession().setAttribute("MSG", "選択した注文を締め戻しました");
			    }
				// 一覧画面へ
				arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
