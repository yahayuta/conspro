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

/**
 * 日報編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditDailyReportSvlt extends HttpServlet {

	/** 種別：0 更新画面起動 */
	private static final String TYPE_EDIT = "0";
	/** 種別：1  更新確定 */
	private static final String TYPE_EDIT_COMMIT = "1";
	/** 種別：2  削除 */
	private static final String TYPE_DELETE = "2";
	/** 種別：3 整備履歴 */
	private static final String TYPE_HISTORY = "3";
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
		String id = arg0.getParameter("EDITID");
		
		// ACCOUNTが取れない場合エラー
		if (id == null || id.length() == 0) {
			// エラーページへ
			arg1.sendRedirect("Error.jsp");
			return;
		}
		
	    try {
			// マスターチェック実行
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("DailyReport", id);
		    Entity dailyReportRecord = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (dailyReportRecord == null) {
				// エラーページへ
		    	throw new ServletException("処理異常です。データ不正です。");
	    	}

			String type = arg0.getParameter("type");
			
			// 更新
			if (TYPE_EDIT.equals(type)) {
				// 更新ページへ遷移
			    arg0.setAttribute("DailyReportRecord", dailyReportRecord);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateDailyReport.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				// 更新実行
				dailyReportRecord.setProperty("WORKER",arg0.getParameter("WORKER"));
				dailyReportRecord.setProperty("WORK_DATE",arg0.getParameter("WORK_DATE"));
				dailyReportRecord.setProperty("SERIALNO",arg0.getParameter("SERIALNO"));
				dailyReportRecord.setProperty("NAME",arg0.getParameter("NAME"));
				dailyReportRecord.setProperty("MEMO",arg0.getParameter("MEMO"));
				dailyReportRecord.setProperty("HOURS",arg0.getParameter("HOURS"));
				dailyReportRecord.setProperty("CLIENT_CODE",arg0.getParameter("CLIENT_CODE"));
				dailyReportRecord.setProperty("CLIENT_NAME",arg0.getParameter("CLIENT_NAME"));
				dailyReportRecord.setProperty("DATA_FLG",arg0.getParameter("DATA_FLG"));
				arg0.getSession().setAttribute("MSG", "選択した日報を更新しました");
	        	datastore.put(dailyReportRecord);
	        	// 再検索してもとに戻る
				arg1.sendRedirect("FTCEditDailyReport?ACCOUNT=" + arg0.getParameter("ACCOUNT") + "&EDITID=" + id + "&type=" + TYPE_EDIT);
			} else if (TYPE_DELETE.equals(type)) {			
				// 日報削除実行
		    	datastore.delete(keyObj);
				arg0.getSession().setAttribute("MSG", "選択した日報を削除しました");
				// 一覧画面へ
				arg1.sendRedirect("DailyReportList.jsp?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_HISTORY.equals(type)) {
				// 整備履歴
				// 詳細
				
				// 日報検索
				if (dailyReportRecord.getProperty("SERIALNO") != null && dailyReportRecord.getProperty("SERIALNO").toString().length() > 0 && dailyReportRecord.getProperty("NAME") != null && dailyReportRecord.getProperty("NAME").toString().length() > 0) {
			    	Query q = new Query("DailyReport");
			    	List<Filter> filters = new ArrayList<Filter>();
			    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, dailyReportRecord.getProperty("NAME")));
			    	filters.add(new FilterPredicate("SERIALNO", FilterOperator.EQUAL, dailyReportRecord.getProperty("SERIALNO")));
			        CompositeFilter filter = CompositeFilterOperator.and(filters);
			        q.setFilter(filter);
			    	q.addSort("WORK_DATE", SortDirection.DESCENDING);
			    	PreparedQuery pq = datastore.prepare(q);
			    	arg0.setAttribute("DailyReportList", pq.asList(FetchOptions.Builder.withDefaults()));
				}
				
				// 詳細ページへ遷移
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/InventoryDetail.jsp");
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
