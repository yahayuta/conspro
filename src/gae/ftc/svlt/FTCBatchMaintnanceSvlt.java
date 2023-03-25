package gae.ftc.svlt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
 * メンテナンスバッチ
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCBatchMaintnanceSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// レンタル在庫ステータスチェック
	    Query q = new Query("RentalInventoryRecord");
	    // 条件：有効在庫
        q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listInventoryRecord = pq.asList(FetchOptions.Builder.withDefaults());
	    
	    for (Iterator<Entity> iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
			Entity inventoryRecord = (Entity) iterator.next();
		    
	    	// レンタル注文検索
		    Query qRentalOrder = new Query("RentalOrder");
		    
		    List<Filter> filters = new ArrayList<Filter>();
		    filters.add(new FilterPredicate("RENTAL_INVENTORY_ID", FilterOperator.EQUAL, inventoryRecord.getKey().getName()));
		    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	        CompositeFilter filter = CompositeFilterOperator.and(filters);
	        qRentalOrder.setFilter(filter);
	        
		    PreparedQuery pqRentalOrder = datastore.prepare(qRentalOrder);
		    List<Entity> rentalOrderList = pqRentalOrder.asList(FetchOptions.Builder.withDefaults());
		    
		    boolean noOrder = true;
		    for (Iterator<Entity> iterator2 = rentalOrderList.iterator(); iterator2.hasNext();) {
				Entity rentalOrder = (Entity) iterator2.next();
				// 返却日が未設定の場合は出庫中とする
				if (rentalOrder.getProperty("IN_DATE") == null || rentalOrder.getProperty("IN_DATE").toString().length() == 0) {
			    	//レンタル在庫を出庫中に更新する
			    	inventoryRecord.setProperty("STATUS", "1"); 
		        	datastore.put(inventoryRecord);
		        	noOrder = false;
		        	break;
	        	}
			}
		    
		    // すべての注文が返却日設定ありの場合
		    if (noOrder) {
		    	//レンタル在庫を空車に更新する
		    	inventoryRecord.setProperty("STATUS", "0");
	        	datastore.put(inventoryRecord);
		    }
			
		}
	    
	    // 表示期間を過ぎたレンタル注文引合に削除フラグを立てる
	    q = new Query("PreRentalOrder");
	    // 条件：有効データ（データはそんなに多くないはずなので全部取る）
        q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    datastore = DatastoreServiceFactory.getDatastoreService();
	    pq = datastore.prepare(q);
	    List<Entity> preRentalOrderList = pq.asList(FetchOptions.Builder.withDefaults());
        Date currentDate = new Date();
	    for (Iterator<Entity> iterator = preRentalOrderList.iterator(); iterator.hasNext();) {
			Entity preRentalOrder = (Entity) iterator.next();
            Date date = new Date(Long.parseLong(preRentalOrder.getKey().getName()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // 引合作成から45日後
            calendar.add(Calendar.DAY_OF_MONTH, 45);
            date = calendar.getTime();
            System.out.println(date);
            System.out.println(currentDate);
            // 引合作成の45日以内だったら何もしない
            if (currentDate.before(date)) {
            	continue;
            }
            // 45日経過してるものは削除
            preRentalOrder.setProperty("DATA_FLG", "　1");
        	datastore.put(preRentalOrder);
	    }
	}
}
