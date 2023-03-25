package gae.ftc.svlt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import gae.ftc.util.FTCCommonUtil;

/**
 * 締め在庫一覧
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetOldInventoryListSvlt extends HttpServlet {

	protected static SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM");
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = null;
	    
	    // 当月で絞り込む
	    Date now = new Date(System.currentTimeMillis());
	    
    	// データ検索	    
	    Query q = new Query("InventoryRecord");
	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "1"));
	    q.setFilter(new FilterPredicate("SELL_MONTH", FilterOperator.EQUAL, SDF.format(now)));
	    q.addSort("SELL_MONTH", SortDirection.DESCENDING);
	    q.addSort("SELL_PAY_DATE", SortDirection.ASCENDING);
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
	    	if("1".equals(request.getParameter("MENU"))) {
		    	nameSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("NAME")));
		    	sellerSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER")));
		    	buyerSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("BUYER")));
		    	tantoSet.add(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ACCOUNT")));
	    	}
	    	orderCostPrice = orderCostPrice + FTCCommonUtil.getLong(inventoryRecord.getProperty("ORDER_COST_PRICE"));
	    	sellCostPrice = sellCostPrice + FTCCommonUtil.getLong(inventoryRecord.getProperty("SELL_COST_PRICE"));
	    	sellPrice = sellPrice + FTCCommonUtil.getLong(inventoryRecord.getProperty("SELL_PRICE"));
	    	profit = profit + FTCCommonUtil.getLong(inventoryRecord.getProperty("PROFIT"));
		}
	    
        if("1".equals(request.getParameter("MENU"))) {
            request.getSession().setAttribute("NameSet", nameSet);
            request.getSession().setAttribute("SellerSet", sellerSet);
            request.getSession().setAttribute("BuyerSet", buyerSet);
            request.getSession().setAttribute("TantoSet", tantoSet);
        }
        
	    // 画面返却
	    request.setAttribute("InventoryRecordList", listInventoryRecord);
	    request.setAttribute("orderCostPrice", orderCostPrice);
	    request.setAttribute("sellCostPrice", sellCostPrice);
	    request.setAttribute("sellPrice", sellPrice);
	    request.setAttribute("profit", profit);
    	rd = sc.getRequestDispatcher("/InventoryOldList.jsp");
    	
	    rd.forward(request, response);
	}
}
