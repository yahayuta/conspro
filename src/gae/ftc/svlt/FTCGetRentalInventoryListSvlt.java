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
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * レンタル在庫一覧
 * DEBUG:FTCGetInventoryList?page=3
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetRentalInventoryListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    
    	// データ検索
	    Query q = new Query("RentalInventoryRecord");
	    
	    String account = request.getParameter("ACCOUNT");
	    // ログイン有無によって処理を変える
	    if (account == null) {
		    List<Filter> filters = new ArrayList<Filter>();
		    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    	filters.add(new FilterPredicate("WEB_DISP", FilterOperator.EQUAL, "1"));
			String TYPE = request.getParameter("TYPE");
			if (TYPE != null && TYPE.length()> 0) {
			    if ("maker".equals(TYPE)) {
			    	filters.add(new FilterPredicate("MANUFACTURER", FilterOperator.EQUAL, request.getParameter("MANUFACTURER")));
			    } else {
			    	filters.add(new FilterPredicate("TYPE", FilterOperator.EQUAL, TYPE));
			    }			
			}

	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
	    } else {
		    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
		    
		    String id = request.getParameter("ID");
		    if (id != null && id.length() > 0) {
		    	q.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, id));
		    }
		    
	        String sortType = request.getParameter("sort");
		    if ("1".equals(sortType)) {
		    	// 分類
		    	q.addSort("TYPE", SortDirection.ASCENDING);
		    } else if ("2".equals(sortType)) {
				// 型式
		    	q.addSort("NAME", SortDirection.ASCENDING);
		    } else if ("3".equals(sortType)) {
		    	// メーカー
		    	q.addSort("MANUFACTURER", SortDirection.ASCENDING);
		    } else if ("0".equals(sortType)) {
		    	// ステータス
		    	q.addSort("STATUS", SortDirection.ASCENDING);
		    }
	    }
        
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listInventoryRecord = pq.asList(FetchOptions.Builder.withDefaults());
	    request.setAttribute("RentalInventoryRecordList", listInventoryRecord);
	    
	    // ログイン有無によって遷移先を変える
	    String url = "/RentalInventoryList.jsp";
	    if (account == null) {
	    	url = "/FTCRentalInventoryList.jsp";
	    }
	    
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
}
