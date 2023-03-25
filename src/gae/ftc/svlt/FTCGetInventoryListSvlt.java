package gae.ftc.svlt;

import java.io.IOException;
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
import gae.ftc.util.FTCConst;

/**
 * 在庫一覧
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCGetInventoryListSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = null;
	    // 遷移先制御
	    if (FTCConst.PAGE_TYPE_EN.equals(page)) {
	    	rd = sc.getRequestDispatcher("/FTCGetInventoryListByType?TYPE=all&page=" + FTCConst.PAGE_TYPE_EN);
	    } else if (FTCConst.PAGE_TYPE_JA.equals(page)) {
	    	rd = sc.getRequestDispatcher("/FTCGetInventoryListByType?TYPE=all&page=" + FTCConst.PAGE_TYPE_JA);
	    } else if (FTCConst.PAGE_TYPE_HZJ.equals(page)) {
	    	rd = sc.getRequestDispatcher("/FTCGetRentalInventoryList");
	    } else {
			String sortType = request.getParameter("sort");
		    Query q = new Query("InventoryRecord");
		    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
		    
		    if ("1".equals(sortType)) {
		    	// 種類
		    	q.addSort("TYPE", SortDirection.ASCENDING);
		    	q.addSort("MANUFACTURER", SortDirection.ASCENDING);
		    	q.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.DESCENDING);
		    } else if ("2".equals(sortType)) {
				// 日付
		    	q.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.DESCENDING);
		    } else if ("3".equals(sortType)) {
		    	// 値段
		    	q.addSort("PRICE", SortDirection.ASCENDING);
		    } else if ("4".equals(sortType)) {
		    	// 状態
		    	q.addSort("CONDITION", SortDirection.DESCENDING);
		    } else if ("5".equals(sortType)) {
		    	// 稼動時間
		    	q.addSort("HOURS", SortDirection.DESCENDING);
		    } else if ("6".equals(sortType)) {
		    	// メーカー
		    	q.addSort("MANUFACTURER", SortDirection.ASCENDING);
		    	q.addSort("TYPE", SortDirection.ASCENDING);
		    	q.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.DESCENDING);
		    } else if ("7".equals(sortType)) {
		    	// WEB表示
		    	q.addSort("WEB_DISP", SortDirection.ASCENDING);
		    	q.addSort("WEB_DISP_DATE", SortDirection.DESCENDING);
		    	q.addSort("TYPE", SortDirection.ASCENDING);
		    } else {
		    	q.addSort("TYPE", SortDirection.ASCENDING);
		    	q.addSort("MANUFACTURER", SortDirection.ASCENDING);
		    	q.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.DESCENDING);
		    }
		    
	    	// データ検索		    
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
		    
		    // 画面返却
		    request.setAttribute("InventoryRecordList", listInventoryRecord);
	        if("1".equals(request.getParameter("MENU"))) {
	            request.getSession().setAttribute("NameSet", nameSet);
	            request.getSession().setAttribute("SellerSet", sellerSet);
	            request.getSession().setAttribute("BuyerSet", buyerSet);
	            request.getSession().setAttribute("TantoSet", tantoSet);
	        }
		    request.setAttribute("orderCostPrice", orderCostPrice);
		    request.setAttribute("sellCostPrice", sellCostPrice);
		    request.setAttribute("sellPrice", sellPrice);
		    request.setAttribute("profit", profit);
	    	rd = sc.getRequestDispatcher("/InventoryList.jsp");
	    }
	    rd.forward(request, response);
	}
}
