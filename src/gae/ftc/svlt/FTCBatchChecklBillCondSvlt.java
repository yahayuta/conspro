package gae.ftc.svlt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * レンタル注文締め日チェックバッチ
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCBatchChecklBillCondSvlt extends HttpServlet {

	private static int DIFF_HOURS = 15;
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
	private static final Logger log = Logger.getLogger(FTCBatchChecklBillCondSvlt.class.getName());

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
    	// データ検索
	    Query q = new Query("RentalOrder");
	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> rentalOrderList = pq.asList(FetchOptions.Builder.withDefaults());
	    
		// 現在時刻
		Calendar calendarNowDate = Calendar.getInstance();
		// 当月締め日
		Calendar calendarCloseDate = Calendar.getInstance();
		
		// GAEは米国中部標準時刻なので日本時間にあわせる
		calendarNowDate.add(Calendar.HOUR, DIFF_HOURS);
		calendarCloseDate.add(Calendar.HOUR, DIFF_HOURS);

	    for (Iterator<Entity> iterator = rentalOrderList.iterator(); iterator.hasNext();) {
	    	Entity rentalOrder = (Entity) iterator.next();
	    	Object closeDateObj = rentalOrder.getProperty("CLOSE_DATE");
	    	if (closeDateObj == null) {
	    		continue;
	    	}
	    	String closeDateStr = closeDateObj.toString();
			log.info("closeDateStr = " + closeDateStr);
	    	if (closeDateStr == "") {
	    		continue;
	    	}
			try {
				// ２月の場合は締め日が３０だったら強制的に２８にする
			    if (2 == calendarCloseDate.get(Calendar.MONTH) && "30".equals(closeDateStr)) {
			    	calendarCloseDate.set(Calendar.DATE, 28);
					System.out.println("Feb to 28");
			    } else {
			    	// ２月以外はそのまま設定
			    	calendarCloseDate.set(Calendar.DATE, Integer.parseInt(closeDateStr));
			    }
			   
			    Date closeDate = calendarCloseDate.getTime();
				Date nowDate = calendarNowDate.getTime();        
 
				System.out.println(rentalOrder.getKey().getName());
				System.out.println(nowDate.toString());
				System.out.println(closeDate.toString());
				
				log.info("rentalOrderID = " + rentalOrder.getKey().getName());
				log.info("nowDate = " + nowDate.toString());
				log.info("closeDate = " + closeDate.toString());

				// 締め日を過ぎていない場合はチェックOK
				if (nowDate.before(closeDate)) {
					System.out.println("not over close date");
					continue;
				}
				
				// 当月締め日を過ぎている場合
				
				// レンタル終了日未設定の場合は請求対象となる
				Object endDateObj = rentalOrder.getProperty("END_DATE");
				String endDate = "";
				if (endDateObj != null) {
					endDate = endDateObj.toString();
				}
				
				// 終了日が設定されている場合は請求対象から外す
				if (endDate != null && endDate.length() > 0) {
					System.out.println("reltal order closed");
					continue;
				}
				
				// 当月請求対象の履歴があるかチェックする
			    q = new Query("RentalOrderInvoiceHistory");
			    List<Filter> filters = new ArrayList<Filter>();
			    filters.add(new FilterPredicate("RENTAL_ORDER_ID", FilterOperator.EQUAL, rentalOrder.getKey().getName()));
			    String reqYM = formatter.format(nowDate);
			    filters.add(new FilterPredicate("REQUEST_YM", FilterOperator.EQUAL, reqYM));

		        CompositeFilter filter = CompositeFilterOperator.and(filters);
		        q.setFilter(filter);

			    pq = datastore.prepare(q);
			    List<Entity> listRentalOrderInvRiceHistory = pq.asList(FetchOptions.Builder.withDefaults());
			    
		    	// 当月の請求履歴がある場合は何もしない
			    if (!listRentalOrderInvRiceHistory.isEmpty() && listRentalOrderInvRiceHistory.size() > 0) {
					System.out.println("already billing existed");
		    		continue;
			    }
			    
			    // 請求履歴を作成するためレンタル注文のデータを転記
				Entity rentalOrderInvoiceHistory = new Entity("RentalOrderInvoiceHistory", String.valueOf(System.currentTimeMillis()));
				rentalOrderInvoiceHistory.setProperty("RENTAL_ORDER_ID", rentalOrder.getKey().getName());
				rentalOrderInvoiceHistory.setProperty("REQUEST_YM", reqYM);
				rentalOrderInvoiceHistory.setProperty("CLOSE_REQ", "0");
				datastore.put(rentalOrderInvoiceHistory);
			    
				// レンタル注文は請求対象とする
	    		rentalOrder.setProperty("CLOSE_REQ", "0");
				datastore.put(rentalOrder);
				
			} catch (Throwable th) {
				th.printStackTrace();
				// 例外発生時はチェックOKとみなす
	    		continue;
			}
		}
	}
}
