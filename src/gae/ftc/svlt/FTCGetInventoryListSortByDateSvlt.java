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

import gae.ftc.util.FTCConst;

/**
 * 在庫一覧をTYPEで絞り込む
 * DEBUG:FTCGetInventoryListSortByDate?page=1
 * DEBUG:FTCGetInventoryListSortByDate?page=2
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetInventoryListSortByDateSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		String page = arg0.getParameter("page");
		
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = null;
	    
	    // 絞り込み条件作成
	    Query q = new Query("InventoryRecord");
	    List<Filter> filters = new ArrayList<Filter>();
	    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    List<String> webDisp = new ArrayList<String>();
	    webDisp.add("1");
	    webDisp.add("3");
	    filters.add(new FilterPredicate("WEB_DISP", FilterOperator.IN, webDisp));
        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);
        q.addSort("WEB_DISP_DATE", SortDirection.DESCENDING);
        
	    // クエリー発行
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listInventoryRecord = pq.asList(FetchOptions.Builder.withDefaults());

	    arg0.setAttribute("InventoryRecordList", listInventoryRecord);
	    
	    // ページ制御
	    if (FTCConst.PAGE_TYPE_EN.equals(page)) {
	    	rd = sc.getRequestDispatcher("/FTCSortInventoryList.jsp");
	    } else if (FTCConst.PAGE_TYPE_JA.equals(page)) {
	    	rd = sc.getRequestDispatcher("/FTCSortInventoryListJa.jsp");
	    }
	    rd.forward(arg0, arg1);
	}
}
