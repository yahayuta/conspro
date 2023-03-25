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

/**
 * 顧客一覧検索
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCSeachClientListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
		// データ検索  	
	    Query q = new Query("Client");
	    List<Filter> filters = new ArrayList<Filter>();
	    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    
    	// 通常検索
	    if (arg0.getParameter("CLIENT_CODE") != null && arg0.getParameter("CLIENT_CODE").length() > 0) {	    	
	    	filters.add(new FilterPredicate("CLIENT_CODE", FilterOperator.EQUAL, arg0.getParameter("CLIENT_CODE")));
	    }
	    if (arg0.getParameter("CLIENT_TYPE") != null && arg0.getParameter("CLIENT_TYPE").length() > 0) {
	    	filters.add(new FilterPredicate("CLIENT_TYPE", FilterOperator.EQUAL, arg0.getParameter("CLIENT_TYPE")));
	    }
	    if (arg0.getParameter("CREDIT") != null && arg0.getParameter("CREDIT").length() > 0) {
	    	filters.add(new FilterPredicate("CREDIT", FilterOperator.EQUAL, arg0.getParameter("CREDIT")));
	    }
	    if (arg0.getParameter("COUNTRY") != null && arg0.getParameter("COUNTRY").length() > 0) {
	    	filters.add(new FilterPredicate("COUNTRY", FilterOperator.EQUAL, arg0.getParameter("COUNTRY")));
	    }
	    if (arg0.getParameter("SEQ") != null && arg0.getParameter("SEQ").length() > 0) {
	    	filters.add(new FilterPredicate("SEQ", FilterOperator.EQUAL, arg0.getParameter("SEQ")));
	    }
	    if (arg0.getParameter("COMPANY") != null && arg0.getParameter("COMPANY").length() > 0) {
	    	filters.add(new FilterPredicate("COMPANY", FilterOperator.EQUAL, arg0.getParameter("COMPANY")));
	    }
	    if (arg0.getParameter("NAME") != null && arg0.getParameter("NAME").length() > 0) {
	    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, arg0.getParameter("NAME")));
	    }
	    if (arg0.getParameter("NAME2") != null && arg0.getParameter("NAME2").length() > 0) {
	    	filters.add(new FilterPredicate("NAME2", FilterOperator.EQUAL, arg0.getParameter("NAME2")));
	    }
	    if (arg0.getParameter("NAME3") != null && arg0.getParameter("NAME3").length() > 0) {
	    	filters.add(new FilterPredicate("NAME3", FilterOperator.EQUAL, arg0.getParameter("NAME3")));
	    }
	    if (arg0.getParameter("NAME4") != null && arg0.getParameter("NAME4").length() > 0) {
	    	filters.add(new FilterPredicate("NAME4", FilterOperator.EQUAL, arg0.getParameter("NAME4")));
	    }
	    if (arg0.getParameter("NAME5") != null && arg0.getParameter("NAME5").length() > 0) {
	    	filters.add(new FilterPredicate("NAME5", FilterOperator.EQUAL, arg0.getParameter("NAME5")));
	    }
	    if (arg0.getParameter("TEL") != null && arg0.getParameter("TEL").length() > 0) {
	    	filters.add(new FilterPredicate("TEL", FilterOperator.EQUAL, arg0.getParameter("TEL")));
	    }
	    if (arg0.getParameter("ZIP") != null && arg0.getParameter("ZIP").length() > 0) {
	    	filters.add(new FilterPredicate("ZIP", FilterOperator.EQUAL, arg0.getParameter("ZIP")));
	    }
	    if (arg0.getParameter("OFFICE") != null && arg0.getParameter("OFFICE").length() > 0) {
	    	filters.add(new FilterPredicate("OFFICE", FilterOperator.EQUAL, arg0.getParameter("OFFICE")));
	    }
	    if (arg0.getParameter("PREFIX") != null && arg0.getParameter("PREFIX").length() > 0) {
	    	filters.add(new FilterPredicate("PREFIX", FilterOperator.EQUAL, arg0.getParameter("PREFIX")));
	    }
	    
	    if (filters.size() == 1) {
	        q.setFilter(filters.get(0));
	    } else {
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
	    }

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    arg0.setAttribute("ClientRecordList", pq.asList(FetchOptions.Builder.withDefaults()));
	    
	    // 遷移先判定
	    String jsp = "/ClientList.jsp";
	    if ("0".equals(arg0.getParameter("type"))) {
	    	jsp = "/ClientListPopUp.jsp";
	    }
	    
	    // JSPへフォワードする
	    getServletContext().getRequestDispatcher(jsp).forward(arg0, arg1);
	}
}
