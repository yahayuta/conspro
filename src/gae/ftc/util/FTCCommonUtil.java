package gae.ftc.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * FTC共通Util
 * @author yahayuta
 */
public class FTCCommonUtil {
	
	/** 金額編集 */
	private static DecimalFormat formatter = new DecimalFormat("#,###");
	
	/** 請求必要であることのアラート文字列 */
	private static String REQ_ALERT_STR = "style=\"background-color:red !important;\"";
	
	/**
	 * NULL置換
	 * @param str
	 * @return
	 */
	public static String nullConv(String str) {
		if (str==null) return "";
		return str;
	}
	
	/**
	 * NULL置換
	 * @param obj
	 * @return
	 */
	public static String nullConv(Object obj) {
		if (obj==null) return "";
		return obj.toString();
	}

	/**
	 * 金額編集
	 * @param amount
	 * @return
	 */
	public static String moneyFormat(String amount) {
		if (amount == null || amount.length() == 0) {
			return "";
		}
		
		Long value = null;
		try {
			value = Long.parseLong(amount);
		} catch (Throwable th) {
			try {
				value = Math.round(parseDouble(amount));
			} catch (Throwable t21) {
				return amount;
			}
		}
		return moneyFormat(value);
	}
	
	/**
	 * 金額編集
	 * @param obj
	 * @return
	 */
	public static String moneyFormat(Object obj) {
		if (obj == null) {
			return "";
		}
		return moneyFormat(obj.toString());
	}
	
	/**
	 * 金額編集
	 * @param value
	 * @return
	 */
	public static String moneyFormat(Long value) {
		return formatter.format(value);
	}
	
	/**
	 * 金額がNULLか0かチェックし代替文字を返却する
	 * @param num
	 * @return
	 */
	public static String checkEmptyOrZero(String num) {
		
		if (num == null || num.length() == 0 || "0".equals(num)) {
			return "ASK";
		} else if ("9,999".equals(num)) {
			return "SOLD";
		}
		
		return num;
	}
	
	/**
	 * コンボの中身を構築します
	 * @param optionSet
	 * @return
	 */
	public static String buildOptions(Set<String> optionSet) {
		if (optionSet == null || optionSet.size() == 0) {
			return "";
		}
		
		StringBuffer retBuf = new StringBuffer();
		
		for (String value : optionSet) {
			retBuf.append("<option value=\"" + value + "\">" + value + "</option>\n");
		}
		
		return retBuf.toString();
	}
	
	/**
	 * 数値変換する
	 * @param num
	 * @return
	 */
	public static long getLong(String num) {
		if (num == null || num.length() == 0) {
			return 0;
		}
		
		try {
			return Long.parseLong(num);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}
	
	/**
	 * 数値変換する
	 * @param obj
	 * @return
	 */
	public static long getLong(Object obj) {
		if (obj == null) {
			return 0;
		}
		return getLong(obj.toString());
	}
	
	/**
	 * double変換
	 * @param str
	 * @return
	 */
	public static double parseDouble(String str) {
		if (str == null || str.length() == 0) return 0;
		return Double.parseDouble(str);
	}
	
	/**
	 * 文字列double変換
	 * @param obj
	 * @return
	 */
	public static double parseDouble(Object obj) {
		if (obj == null) {
			return 0;
		}
			
		return parseDouble(obj.toString());
	}
	
	/**
	 * 文字列double変換
	 * @param str
	 * @return
	 */
	public static String parseDoubleString(String str) {
		if (str == null || str.length() == 0) return "0";
		return str;
	}
	
	/**
	 * 文字列double変換
	 * @param obj
	 * @return
	 */
	public static String parseDoubleString(Object obj) {
		if (obj == null) {
			return "0";
		}
			
		return parseDoubleString(obj.toString());
	}
	
	/**
	 * 一般権限保有の従業員データリストを取得する
	 * @return
	 */
	public static String getEmployeeListRankNormalOptionTag() {
	    Query q = new Query("Employee");
	    List<String> authCodes = new ArrayList<String>();
	    authCodes.add(FTCConst.AUTH_NORMAL);
	    authCodes.add(FTCConst.AUTH_SERVICE);
	    authCodes.add(FTCConst.AUTH_OFFICE);
        q.setFilter(new FilterPredicate("AUTH_CODE", FilterOperator.IN, authCodes));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    StringBuffer htmlTag = new StringBuffer();
		for (Iterator<Entity> iterator = pq.asList(FetchOptions.Builder.withDefaults()).iterator(); iterator.hasNext();) {
			Entity employeeRecord = (Entity) iterator.next();
			htmlTag.append("<option value=\""+employeeRecord.getProperty("NAME")+"\">"+employeeRecord.getProperty("NAME")+"</option>\n");
		}
	    return htmlTag.toString();
	}
	
	/**
	 * 日報検索
	 * @param inventoryRecord
	 * @param request
	 */
	public static void searchDailyReport(Entity inventoryRecord, HttpServletRequest request) {
		// 日報検索
		if (inventoryRecord.getProperty("SERIALNO") != null && 
				FTCCommonUtil.nullConv(inventoryRecord.getProperty("SERIALNO")).length() > 0 && 
				inventoryRecord.getProperty("NAME") != null && 
						FTCCommonUtil.nullConv(inventoryRecord.getProperty("NAME")).length() > 0) {
			
		    Query q = new Query("DailyReport");
	    	List<Filter> filters = new ArrayList<Filter>();
	    	filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "1"));
	    	filters.add(new FilterPredicate("NAME", FilterOperator.EQUAL, inventoryRecord.getProperty("NAME")));
	    	filters.add(new FilterPredicate("SERIALNO", FilterOperator.EQUAL, inventoryRecord.getProperty("SERIALNO")));
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        q.setFilter(filter);
		    q.addSort("WORK_DATE", SortDirection.DESCENDING);
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    PreparedQuery pq = datastore.prepare(q);
	    	request.setAttribute("DailyReportList", pq.asList(FetchOptions.Builder.withDefaults()));
		}
	}

	/**
	 * レンタル注文検索
	 * @param inventoryRecord
	 * @param request
	 */
	public static void searchRentalOrderHistory(Entity inventoryRecord, HttpServletRequest request) {
	    Query q = new Query("RentalOrder");
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(new FilterPredicate("RENTAL_INVENTORY_ID", FilterOperator.EQUAL, inventoryRecord.getKey().getName()));
    	filters.add(new FilterPredicate("DATA_FLG", FilterOperator.NOT_EQUAL, "9"));
        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
    	request.setAttribute("RentalOrderList", pq.asList(FetchOptions.Builder.withDefaults()));
	}
	
	/**
	 * レンタル注文を更新する
	 * @param datastore
	 * @param rentalOrder
	 * @param req
	 * @throws EntityNotFoundException
	 */
	public static void updateRentalOrder (DatastoreService datastore, Entity rentalOrder, HttpServletRequest req) throws EntityNotFoundException {
		rentalOrder.setProperty("OUT_DATE", req.getParameter("OUT_DATE"));
		rentalOrder.setProperty("IN_DATE", req.getParameter("IN_DATE"));
		rentalOrder.setProperty("START_DATE", req.getParameter("START_DATE"));
		rentalOrder.setProperty("END_DATE", req.getParameter("END_DATE"));
		rentalOrder.setProperty("HOURS_END", req.getParameter("HOURS_END"));
		rentalOrder.setProperty("AMOUNT", req.getParameter("AMOUNT"));
		rentalOrder.setProperty("SUPPORT_FEE", req.getParameter("SUPPORT_FEE"));
		rentalOrder.setProperty("RETURN_FEE", req.getParameter("RETURN_FEE"));
		rentalOrder.setProperty("TRANS_FEE", req.getParameter("TRANS_FEE"));
		rentalOrder.setProperty("ORDER_NAME", req.getParameter("ORDER_NAME"));
		rentalOrder.setProperty("ORDER_NAME", req.getParameter("ORDER_NAME"));
		rentalOrder.setProperty("ORDER_PLACE", req.getParameter("ORDER_PLACE"));
		rentalOrder.setProperty("CLIENT_CODE", req.getParameter("CLIENT_CODE"));
		rentalOrder.setProperty("CLIENT_NAME", req.getParameter("CLIENT_NAME"));
		rentalOrder.setProperty("MEMO", req.getParameter("MEMO"));
		
		rentalOrder.setProperty("COUNT", req.getParameter("COUNT"));
		rentalOrder.setProperty("PRICE", req.getParameter("PRICE"));
		rentalOrder.setProperty("FEE1", req.getParameter("FEE1"));
		rentalOrder.setProperty("FEE2", req.getParameter("FEE2"));
		rentalOrder.setProperty("FEE3", req.getParameter("FEE3"));
		rentalOrder.setProperty("FEE4", req.getParameter("FEE4"));
		rentalOrder.setProperty("FEE5", req.getParameter("FEE5"));
		rentalOrder.setProperty("FEE6", req.getParameter("FEE6"));
		rentalOrder.setProperty("TOTAL", req.getParameter("TOTAL"));
		rentalOrder.setProperty("FEE1_NAME", req.getParameter("FEE1_NAME"));
		rentalOrder.setProperty("FEE2_NAME", req.getParameter("FEE2_NAME"));
		rentalOrder.setProperty("FEE3_NAME", req.getParameter("FEE3_NAME"));
		rentalOrder.setProperty("FEE4_NAME", req.getParameter("FEE4_NAME"));
		rentalOrder.setProperty("FEE5_NAME", req.getParameter("FEE5_NAME"));
		rentalOrder.setProperty("FEE6_NAME", req.getParameter("FEE6_NAME"));
		
		//rentalOrder.setProperty("ENROLLMENT", req.getParameter("ENROLLMENT"));

		rentalOrder.setProperty("CLOSE_DATE", req.getParameter("CLOSE_DATE"));
		rentalOrder.setProperty("REQUEST_YM", req.getParameter("REQUEST_YM"));
		rentalOrder.setProperty("REQUEST_DATE", req.getParameter("REQUEST_DATE"));
		rentalOrder.setProperty("REQUEST_NOTE", req.getParameter("REQUEST_NOTE"));
		rentalOrder.setProperty("REQUEST_END_DATE", req.getParameter("REQUEST_END_DATE"));
		rentalOrder.setProperty("CLIENT_REP", req.getParameter("CLIENT_REP"));
		rentalOrder.setProperty("CLIENT_TEL", req.getParameter("CLIENT_TEL"));
		
		rentalOrder.setProperty("FEE1_MEMO", req.getParameter("FEE1_MEMO"));
		rentalOrder.setProperty("FEE2_MEMO", req.getParameter("FEE2_MEMO"));
		rentalOrder.setProperty("FEE3_MEMO", req.getParameter("FEE3_MEMO"));
		rentalOrder.setProperty("FEE4_MEMO", req.getParameter("FEE4_MEMO"));
		rentalOrder.setProperty("FEE5_MEMO", req.getParameter("FEE5_MEMO"));
		rentalOrder.setProperty("FEE6_MEMO", req.getParameter("FEE6_MEMO"));

		rentalOrder.setProperty("AMOUNT_MEMO", req.getParameter("AMOUNT_MEMO"));
		rentalOrder.setProperty("SUPPORT_MEMO", req.getParameter("SUPPORT_MEMO"));
		rentalOrder.setProperty("TRANS_FEE_MEMO", req.getParameter("TRANS_FEE_MEMO"));
		rentalOrder.setProperty("RETURN_FEE_MEMO", req.getParameter("RETURN_FEE_MEMO"));
		
		// 返却日が設定されている場合空車にする
		if (rentalOrder.getProperty("IN_DATE") != null && rentalOrder.getProperty("IN_DATE").toString().length() > 0) {
		    Key keyObjRentalInv = KeyFactory.createKey("RentalInventoryRecord", rentalOrder.getProperty("RENTAL_INVENTORY_ID").toString());
		    Entity inventoryRecord = datastore.get(keyObjRentalInv);
		    
	    	//レンタル在庫を空車に更新する
	    	inventoryRecord.setProperty("STATUS", "0");
	    	
	    	if (rentalOrder.getProperty("HOURS_END") != null && rentalOrder.getProperty("HOURS_END").toString().length() > 0) {
		    	// 稼働時間を上書きする
		    	inventoryRecord.setProperty("HOURS", rentalOrder.getProperty("HOURS_END"));
	    	}
	    	
	        datastore.put(inventoryRecord);
		}
	    
		datastore.put(rentalOrder);
	}

	/**
	 * 請求履歴更新
	 * @param rentalOrder
	 */
	public static void updateRentalOrderInvoiceHistory(Entity rentalOrder) {
		
		// データ検索
		// 対象年月の請求データがあるかチェック
	    Query q = new Query("RentalOrderInvoiceHistory");
	    List<Filter> filters = new ArrayList<Filter>();
	    filters.add(new FilterPredicate("RENTAL_ORDER_ID", FilterOperator.EQUAL, rentalOrder.getKey().getName()));
	    filters.add(new FilterPredicate("REQUEST_YM", FilterOperator.EQUAL, rentalOrder.getProperty("REQUEST_YM")));

        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listRentalOrderInvoiceHistory = pq.asList(FetchOptions.Builder.withDefaults());
	    
		Entity rentalOrderInvoiceHistory = new Entity("RentalOrderInvoiceHistory", String.valueOf(System.currentTimeMillis()));

		// すでに請求データがある場合は更新とする
	    if (listRentalOrderInvoiceHistory.size() > 0) {
	    	// ひと月に1個の請求データしかない想定
	    	rentalOrderInvoiceHistory = listRentalOrderInvoiceHistory.get(0);
	    }
	    
	    // レンタル注文のデータを転記
		rentalOrderInvoiceHistory.setProperty("RENTAL_ORDER_ID", rentalOrder.getKey().getName());
		rentalOrderInvoiceHistory.setProperty("REQUEST_YM", rentalOrder.getProperty("REQUEST_YM"));
		rentalOrderInvoiceHistory.setProperty("REQUEST_DATE", rentalOrder.getProperty("REQUEST_DATE"));
		rentalOrderInvoiceHistory.setProperty("REQUEST_NOTE", rentalOrder.getProperty("REQUEST_NOTE"));
		rentalOrderInvoiceHistory.setProperty("AMOUNT", String.valueOf(FTCCommonUtil.parseDouble(rentalOrder.getProperty("TOTAL").toString())));
		rentalOrderInvoiceHistory.setProperty("CLOSE_REQ", "1");
		datastore.put(rentalOrderInvoiceHistory);
		
		// 入力請求月以外で未請求の履歴があるかチェックする
		Query q2 = new Query("RentalOrderInvoiceHistory");
		List<Filter> filters2 = new ArrayList<Filter>();
		filters2.add(new FilterPredicate("RENTAL_ORDER_ID", FilterOperator.EQUAL, rentalOrder.getKey().getName()));
		filters2.add(new FilterPredicate("CLOSE_REQ", FilterOperator.EQUAL, "0"));
		filters2.add(new FilterPredicate("REQUEST_YM", FilterOperator.NOT_EQUAL, rentalOrder.getProperty("REQUEST_YM")));

		CompositeFilter filter2 = CompositeFilterOperator.and(filters2);
        q2.setFilter(filter2);

        PreparedQuery pq2 = datastore.prepare(q2);
        List<Entity> listRentalOrderInvoiceHistory2 = pq2.asList(FetchOptions.Builder.withDefaults());
	    
		// 未請求データがある場合
	    if (listRentalOrderInvoiceHistory2.size() > 0) {
	    	// 請求待ちにする
	    	rentalOrder.setProperty("CLOSE_REQ", "0");
	    } else {
	    	// 請求済にする
	    	rentalOrder.setProperty("CLOSE_REQ", "1");
	    }
	    
		datastore.put(rentalOrder);
	}
	
	/**
	 * 請求対象判定
	 * @param rentalOrder
	 * @return
	 */
	public static String checkBillingCondition(Entity rentalOrder) {
		// 請求待ちの場合は赤くする
		if ("0".equals(rentalOrder.getProperty("CLOSE_REQ"))) return REQ_ALERT_STR;				
		return "";
	}
	
	/**
	 * 請求履歴検索
	 * @param inventoryRecord
	 * @param request
	 */
	public static void searchRentalOrderInvoiceHistory(Entity rentalOrder, HttpServletRequest request) {
	    Query q = new Query("RentalOrderInvoiceHistory");
        q.setFilter(new FilterPredicate("RENTAL_ORDER_ID", FilterOperator.EQUAL, rentalOrder.getKey().getName()));
	    q.addSort("REQUEST_YM", SortDirection.DESCENDING);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    request.setAttribute("RentalOrderInvoiceHistoryList", pq.asList(FetchOptions.Builder.withDefaults()));
	}
	
	/**
	 * 引合情報検索
	 * @param inventoryRecord
	 * @param request
	 */
	public static void searchPreRentalOrder(Entity inventoryRecord, HttpServletRequest request) {			
	    Query q = new Query("PreRentalOrder");
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
    	filters.add(new FilterPredicate("RENTAL_INVENTORY_ID", FilterOperator.EQUAL, inventoryRecord.getKey().getName()));
        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);
	    q.addSort("START_DATE", SortDirection.DESCENDING);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
    	request.setAttribute("PreRentalOrderList", pq.asList(FetchOptions.Builder.withDefaults()));
	}
	
	/**
	 * システム名を取得する
	 * @return
	 */
	public static String getSystemName() {
		// キャッシュよりシステム名を取得
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		
		String systemName = (String)syncCache.get("0001");
		
		if (systemName != null && systemName.length() > 0 ) {
			return systemName;
		}
		
	    try {
	    	// キャッシュにない場合はデータストアからカンパニーデータ取得
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("Company", "0001");
		    Entity company = datastore.get(keyObj);
		    
		    systemName = FTCCommonUtil.nullConv(company.getProperty("SYSTEM_NAME"));
		    
		    syncCache.put("0001", systemName);
	    } catch ( Throwable th ) {
	    	th.printStackTrace();
    	}
		return systemName;
	}
}


