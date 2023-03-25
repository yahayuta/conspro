package gae.ftc.svlt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
 * レンタル在庫一覧検索
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCSeachRentalInventoryListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
	    Query q = new Query("RentalInventoryRecord");
	    List<Filter> filters = new ArrayList<Filter>();
	    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    
    	// 条件検索
	    if (arg0.getParameter("TYPE") != null && arg0.getParameter("TYPE").length() > 0) {
	    	filters.add(new FilterPredicate("TYPE", FilterOperator.EQUAL, arg0.getParameter("TYPE")));
	    }
	    if (arg0.getParameter("TYPE_SUB") != null && arg0.getParameter("TYPE_SUB").length() > 0) {
	    	filters.add(new FilterPredicate("TYPE_SUB", FilterOperator.EQUAL, arg0.getParameter("TYPE_SUB")));
	    }
	    if (arg0.getParameter("MANUFACTURER") != null && arg0.getParameter("MANUFACTURER").length() > 0) {	    	
	    	filters.add(new FilterPredicate("MANUFACTURER", FilterOperator.EQUAL, arg0.getParameter("MANUFACTURER")));
	    }
	    if (arg0.getParameter("NAME") != null && arg0.getParameter("NAME").length() > 0) {
	    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, arg0.getParameter("NAME")));
	    }
	    if (arg0.getParameter("SERIALNO") != null && arg0.getParameter("SERIALNO").length() > 0) {
	    	filters.add(new FilterPredicate("SERIALNO", FilterOperator.EQUAL, arg0.getParameter("SERIALNO")));
	    }
	    
	    if (filters.size() == 1) {
	        q.setFilter(filters.get(0));
	    } else {
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
	    }
	    
        //q.addSort("NAME", SortDirection.ASCENDING);

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listInventoryRecord = pq.asList(FetchOptions.Builder.withDefaults());
	    
	    // 画面返却
	    arg0.setAttribute("RentalInventoryRecordList", listInventoryRecord);
	    RequestDispatcher rd = getServletContext().getRequestDispatcher("/RentalInventoryListPopUp.jsp");
		rd.forward(arg0, arg1);
	}
}
