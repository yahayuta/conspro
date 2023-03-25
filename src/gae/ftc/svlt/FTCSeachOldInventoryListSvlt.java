package gae.ftc.svlt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import gae.ftc.util.FTCCommonUtil;

/**
 * 締め在庫一覧検索
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCSeachOldInventoryListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// データ検索
	    Query q = new Query("InventoryRecord");
	    // 締め在庫のみ
	    List<Filter> filters = new ArrayList<Filter>();
	    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "1"));
	    
    	// 通常検索
	    if (arg0.getParameter("NAME") != null && arg0.getParameter("NAME").length() > 0) {
	    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, arg0.getParameter("NAME")));
	    }
	    if (arg0.getParameter("SELLER") != null && arg0.getParameter("SELLER").length() > 0) {
	    	filters.add(new FilterPredicate("SELLER", FilterOperator.EQUAL, arg0.getParameter("SELLER")));
	    }
	    if (arg0.getParameter("BUYER") != null && arg0.getParameter("BUYER").length() > 0) {
	    	filters.add(new FilterPredicate("BUYER", FilterOperator.EQUAL, arg0.getParameter("BUYER")));
	    }
	    if (arg0.getParameter("TANTO") != null && arg0.getParameter("TANTO").length() > 0) {
	    	filters.add(new FilterPredicate("TANTO", FilterOperator.EQUAL, arg0.getParameter("TANTO")));
	    }
	    if (arg0.getParameter("WEB_DISP") != null && arg0.getParameter("WEB_DISP").length() > 0) {
	    	filters.add(new FilterPredicate("WEB_DISP", FilterOperator.EQUAL, arg0.getParameter("WEB_DISP")));
	    }
	    if (arg0.getParameter("SELL_MONTH") != null && arg0.getParameter("SELL_MONTH").length() > 0) {
	    	filters.add(new FilterPredicate("SELL_MONTH", FilterOperator.EQUAL, arg0.getParameter("SELL_MONTH")));
	    }

	    if (arg0.getParameter("NAME_TXT") != null && arg0.getParameter("NAME_TXT").length() > 0) {
	    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, arg0.getParameter("NAME_TXT")));
	    }
	    if (arg0.getParameter("SELL_MONTH_TXT") != null && arg0.getParameter("SELL_MONTH_TXT").length() > 0) {
	    	filters.add(new FilterPredicate("SELL_MONTH", FilterOperator.EQUAL, arg0.getParameter("SELL_MONTH_TXT")));
	    }
	    
	    if (filters.size() == 1) {
	        q.setFilter(filters.get(0));
	    } else {
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
	    }

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listInventoryRecord = pq.asList(FetchOptions.Builder.withDefaults());
	    
	    Set<String> nameSet = new TreeSet<String>();
	    Set<String> sellerSet = new TreeSet<String>();
	    Set<String> buyerSet = new TreeSet<String>();
	    Set<String> tantoSet = new TreeSet<String>();
	    Long orderCostPrice = 0L;
	    Long sellCostPrice = 0L;
	    Long sellPrice = 0L;
	    Long profit = 0L;
	    for (Entity inventoryRecord : listInventoryRecord) {
	    	nameSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("NAME")));
	    	sellerSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER")));
	    	buyerSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("BUYER")));
	    	tantoSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ACCOUNT")));
	    	orderCostPrice = orderCostPrice + FTCCommonUtil.getLong(inventoryRecord.getProperty("ORDER_COST_PRICE"));
	    	sellCostPrice = sellCostPrice + FTCCommonUtil.getLong(inventoryRecord.getProperty("SELL_COST_PRICE"));
	    	sellPrice = sellPrice + FTCCommonUtil.getLong(inventoryRecord.getProperty("SELL_PRICE"));
	    	profit = profit + FTCCommonUtil.getLong(inventoryRecord.getProperty("PROFIT"));
		}
	    
	    // 画面返却
	    arg0.setAttribute("InventoryRecordList", listInventoryRecord);
	    arg0.setAttribute("orderCostPrice", orderCostPrice);
	    arg0.setAttribute("sellCostPrice", sellCostPrice);
	    arg0.setAttribute("sellPrice", sellPrice);
	    arg0.setAttribute("profit", profit);
	    
	    arg0.getSession().setAttribute("NameSet", nameSet);
	    arg0.getSession().setAttribute("SellerSet", sellerSet);
	    arg0.getSession().setAttribute("BuyerSet", buyerSet);
	    arg0.getSession().setAttribute("TantoSet", tantoSet);
	    
	    RequestDispatcher rd = getServletContext().getRequestDispatcher("/InventoryOldList.jsp");
		rd.forward(arg0, arg1);
	}
}
