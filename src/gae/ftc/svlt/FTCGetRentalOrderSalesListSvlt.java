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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import gae.ftc.util.FTCCommonUtil;

/**
 * レンタル注文売上一覧
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetRentalOrderSalesListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = null;
		    
		String salesMonth = request.getParameter("SALES_MONTH");
	    double total = 0;

		// 売上月の設定がある場合のみ検索
		if (salesMonth != null && salesMonth.length() > 0) {
			
	    	// データ検索
		    Query q = new Query("RentalOrderSales");		    
		    List<Filter> filters = new ArrayList<Filter>();
		    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
		    filters.add(new FilterPredicate("SALES_MONTH", FilterOperator.EQUAL, salesMonth));
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
	        
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    PreparedQuery pq = datastore.prepare(q);
		    List<Entity> rentalOrderSalesList = pq.asList(FetchOptions.Builder.withDefaults());
		    request.setAttribute("RentalOrderSalesList", rentalOrderSalesList);
		    // 合計金額カウント
		    for (Entity rentalOrderSales : rentalOrderSalesList) {
		    	total = total + FTCCommonUtil.parseDouble(rentalOrderSales.getProperty("AMOUNT"));
			}
		}
		
	    request.setAttribute("total", Math.round(total));
	    rd = sc.getRequestDispatcher("/RentalOrderSalesList.jsp");
	    rd.forward(request, response);
	}
}
