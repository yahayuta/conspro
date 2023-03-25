package gae.ftc.svlt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import gae.ftc.util.FTCCommonUtil;

/**
 * レンタル注文売上計上バッチ
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCBatchCreateRentalSalesSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
    	// データ検索
	    Query q = new Query("RentalOrder");
	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> rentalOrderList = pq.asList(FetchOptions.Builder.withDefaults());

	    for (Iterator<Entity> iterator = rentalOrderList.iterator(); iterator.hasNext();) {
	    	Entity rentalOrder = (Entity) iterator.next();		
				
			// 請求済履歴があるかチェックする
		    q = new Query("RentalOrderInvoiceHistory");
		    List<Filter> filters = new ArrayList<Filter>();
		    filters.add(new FilterPredicate("RENTAL_ORDER_ID", FilterOperator.EQUAL, rentalOrder.getKey().getName()));
		    filters.add(new FilterPredicate("CLOSE_REQ", FilterOperator.EQUAL, "1"));

	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);

		    pq = datastore.prepare(q);
		    List<Entity> listRentalOrderInvRiceHistory = pq.asList(FetchOptions.Builder.withDefaults());
		    
		    for (Iterator<Entity> iterator2 = listRentalOrderInvRiceHistory.iterator(); iterator2.hasNext();) {
				Entity rentalOrderInvRiceHistory = (Entity) iterator2.next();
				
				// 該当月の売上があるかチェックする
			    q = new Query("RentalOrderSales");
			    filters = new ArrayList<Filter>();
			    filters.add(new FilterPredicate("RENTAL_ORDER_ID", FilterOperator.EQUAL, rentalOrderInvRiceHistory.getProperty("RENTAL_ORDER_ID")));
			    filters.add(new FilterPredicate("SALES_MONTH", FilterOperator.EQUAL, rentalOrderInvRiceHistory.getProperty("REQUEST_YM")));

		        CompositeFilter filter2 = CompositeFilterOperator.and(filters);
		        q.setFilter(filter2);

			    pq = datastore.prepare(q);
			    List<Entity> listRentalOrderSales = pq.asList(FetchOptions.Builder.withDefaults());
			    
				// 売上確定
				Entity rentalOrderSales = new Entity("RentalOrderSales", String.valueOf(System.currentTimeMillis()));
				
				// すでに該当月の売上がある場合は更新
				if (listRentalOrderSales.size() > 0) {
					rentalOrderSales = listRentalOrderSales.get(0);
				}
				
				rentalOrderSales.setProperty("RENTAL_INVENTORY_ID", rentalOrder.getProperty("RENTAL_INVENTORY_ID"));
				rentalOrderSales.setProperty("RENTAL_ORDER_ID", rentalOrderInvRiceHistory.getProperty("RENTAL_ORDER_ID"));
				rentalOrderSales.setProperty("SALES_MONTH", rentalOrderInvRiceHistory.getProperty("REQUEST_YM"));
				rentalOrderSales.setProperty("AMOUNT", String.valueOf(FTCCommonUtil.parseDouble(rentalOrderInvRiceHistory.getProperty("AMOUNT").toString())));
				rentalOrderSales.setProperty("CLIENT_NAME", rentalOrder.getProperty("CLIENT_NAME"));
				rentalOrderSales.setProperty("RENTAL_INVENTORY_CODE", rentalOrder.getProperty("NAME").toString() + rentalOrder.getProperty("SERIALNO").toString());
				rentalOrderSales.setProperty("DATA_FLG", "0");
				
	        	// DB登録
	        	datastore.put(rentalOrderSales);
			}	
		}
	}
}
