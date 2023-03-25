package gae.ftc.svlt;

import java.io.IOException;
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
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * レンタル注文一覧
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetRentalOrderListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = null;
		
		String type = request.getParameter("type");
		String dataFlg = "0";
		// 注文締め一覧判定
		if ("closed".equals(type)) {
			dataFlg = "1";
			request.getSession().setAttribute("MSG", "締め注文一覧を表示しています");
		}
		
		
    	// データ検索
	    Query q = new Query("RentalOrder");
	    
        String sortType = request.getParameter("sort");
		if ("2".equals(sortType)) {
			// 型式
	    	q.addSort("NAME", SortDirection.ASCENDING);
	    }
		
	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, dataFlg));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> rentalOrderList = pq.asList(FetchOptions.Builder.withDefaults());
	    request.setAttribute("RentalOrderRecordList", rentalOrderList);
	    rd = sc.getRequestDispatcher("/RentalOrderList.jsp");
	    rd.forward(request, response);
	}
}
