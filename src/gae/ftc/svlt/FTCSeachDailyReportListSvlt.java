package gae.ftc.svlt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * 日報一覧検索
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCSeachDailyReportListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
		// データ検索 	
	    Query q = new Query("DailyReport");
	    List<Filter> filters = new ArrayList<Filter>();
	    List<String> dataFlgList = new ArrayList<String>();
	    dataFlgList.add("0");
	    dataFlgList.add("1");
	    dataFlgList.add("2");
	    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.IN, dataFlgList));
	    
    	// 通常検索
	    boolean hasFilter = false;
	    if (arg0.getParameter("WORKER") != null && arg0.getParameter("WORKER").length() > 0) {	    	
	    	filters.add(new FilterPredicate("WORKER", FilterOperator.EQUAL, arg0.getParameter("WORKER")));
	    	q.addSort("WORK_DATE", SortDirection.DESCENDING);
	    	hasFilter = true;
	    }
	    if (arg0.getParameter("NAME") != null && arg0.getParameter("NAME").length() > 0) {
	    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, arg0.getParameter("NAME")));
	    	hasFilter = true;
	    }
	    if (arg0.getParameter("WORK_DATE") != null && arg0.getParameter("WORK_DATE").length() > 0) {
	    	filters.add(new FilterPredicate("WORK_DATE", FilterOperator.EQUAL, arg0.getParameter("WORK_DATE")));
	    	hasFilter = true;
	    }
	    if (arg0.getParameter("CLIENT_CODE") != null && arg0.getParameter("CLIENT_CODE").length() > 0) {
	    	filters.add(new FilterPredicate("CLIENT_CODE", FilterOperator.EQUAL, arg0.getParameter("CLIENT_CODE")));
	    	hasFilter = true;
	    }
	    if (arg0.getParameter("CLIENT_NAME") != null && arg0.getParameter("CLIENT_NAME").length() > 0) {
	    	filters.add(new FilterPredicate("CLIENT_NAME", FilterOperator.EQUAL, arg0.getParameter("CLIENT_NAME")));
	    	hasFilter = true;
	    }
	    if (!hasFilter) {
	    	q.addSort("WORK_DATE", SortDirection.DESCENDING);
	    	q.addSort("WORKER", SortDirection.ASCENDING);
	    }
	    
	    if (filters.size() == 1) {
	        q.setFilter(filters.get(0));
	    } else {
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
	    }

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    arg0.setAttribute("DailyReportList", pq.asList(FetchOptions.Builder.withLimit(1000)));
	    
	    // JSPへフォワードする
	    getServletContext().getRequestDispatcher("/DailyReportList.jsp").forward(arg0, arg1);
	}
}
