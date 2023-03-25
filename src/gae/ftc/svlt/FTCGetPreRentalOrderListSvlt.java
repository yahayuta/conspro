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

/**
 * レンタル注文引合一覧
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetPreRentalOrderListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// データ検索
	    Query q = new Query("PreRentalOrder");	
	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> rentalOrderList = pq.asList(FetchOptions.Builder.withDefaults());
	    ServletContext sc = getServletContext();		
	    request.setAttribute("PreRentalOrderRecordList", rentalOrderList);
	    RequestDispatcher rd = sc.getRequestDispatcher("/PreRentalOrderList.jsp");
	    rd.forward(request, response);
	}
}
