package gae.ftc.svlt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import gae.ftc.util.FTCCommonUtil;

/**
 * その他機械編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditOtherInventorySvlt extends HttpServlet {

	/** 種別：0 更新画面起動 */
	private static final String TYPE_EDIT = "0";
	/** 種別：1  更新確定 */
	private static final String TYPE_EDIT_COMMIT = "1";
	/** 種別：2  削除 */
	private static final String TYPE_DELETE = "2";
	
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
				searchDailyReport(inventoryRecord, arg0);
				
			    arg0.setAttribute("InventoryRecord", inventoryRecord);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateOtherInventory.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				// 更新確定
				
				// 更新実行
				inventoryRecord.setProperty("NAME", arg0.getParameter("NAME"));
				inventoryRecord.setProperty("TYPE", arg0.getParameter("TYPE"));
				inventoryRecord.setProperty("MANUFACTURER", arg0.getParameter("MANUFACTURER"));
				inventoryRecord.setProperty("YEAR", arg0.getParameter("YEAR"));
				inventoryRecord.setProperty("SERIALNO", arg0.getParameter("SERIALNO"));
				inventoryRecord.setProperty("HOURS", arg0.getParameter("HOURS"));
				inventoryRecord.setProperty("OTHER_JA", arg0.getParameter("OTHER_JA"));
				inventoryRecord.setProperty("ACCOUNT", arg0.getParameter("INACCOUNT"));
				inventoryRecord.setProperty("SELLER_CODE", arg0.getParameter("SELLER_CODE"));
				inventoryRecord.setProperty("SELLER", arg0.getParameter("SELLER"));
				inventoryRecord.setProperty("MEMO", arg0.getParameter("MEMO"));
				
	        	datastore.put(inventoryRecord);
	        	
				// 一覧画面へ
				arg1.sendRedirect("FTCSeachOtherInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_DELETE.equals(type)) {
				// 削除
				
				// 削除フラグを立てるだけ
				inventoryRecord.setProperty("DATA_FLG", "6");
	        	datastore.put(inventoryRecord);
				arg0.getSession().setAttribute("MSG", "選択した在庫を削除ました");
				
				// 一覧画面へ
				arg1.sendRedirect("FTCSeachOtherInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
	
	/**
	 * 日報検索
	 * @param inventoryRecord
	 * @param request
	 */
	protected void searchDailyReport(Entity inventoryRecord, HttpServletRequest request) {
		// 日報検索
		if (inventoryRecord.getProperty("SERIALNO") != null && 
				FTCCommonUtil.nullConv(inventoryRecord.getProperty("SERIALNO")).length() > 0 && 
				inventoryRecord.getProperty("NAME") != null && 
						FTCCommonUtil.nullConv(inventoryRecord.getProperty("NAME")).length() > 0) {
			
		    Query q = new Query("DailyReport");
	    	List<Filter> filters = new ArrayList<Filter>();
	    	filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "2"));
	    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, inventoryRecord.getProperty("NAME")));
	    	filters.add(new FilterPredicate("SERIALNO", FilterOperator.EQUAL, inventoryRecord.getProperty("SERIALNO")));
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
		    q.addSort("WORK_DATE", SortDirection.DESCENDING);
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    PreparedQuery pq = datastore.prepare(q);
	    	request.setAttribute("DailyReportList", pq.asList(FetchOptions.Builder.withDefaults()));
		}
	}
}
