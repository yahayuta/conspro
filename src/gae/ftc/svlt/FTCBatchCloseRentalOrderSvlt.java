package gae.ftc.svlt;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * レンタル注文締めバッチ
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCBatchCloseRentalOrderSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
    	// データ検索
	    Query q = new Query("RentalOrder");
	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> rentalOrderList = pq.asList(FetchOptions.Builder.withDefaults());

	    for (Iterator<Entity> iterator = rentalOrderList.iterator(); iterator.hasNext();) {
	    	Entity rentalOrder = (Entity) iterator.next();						
		    try {
		    	// 併せてレンタル在庫情報も取得
			    Key keyObjRentalInv = KeyFactory.createKey("RentalInventoryRecord", rentalOrder.getProperty("RENTAL_INVENTORY_ID").toString());
			    Entity inventoryRecord = datastore.get(keyObjRentalInv);
		   
				// 注文締め
				
				// 終了日と返却日と全請求完了日が取れない場合注文締めは不可とする
				if (rentalOrder.getProperty("REQUEST_END_DATE") == null || rentalOrder.getProperty("REQUEST_END_DATE").toString().length() == 0) {
					continue;
				} else if (rentalOrder.getProperty("IN_DATE") == null || rentalOrder.getProperty("IN_DATE").toString().length() == 0) {
					continue;
				} else if (rentalOrder.getProperty("END_DATE") == null || rentalOrder.getProperty("END_DATE").toString().length() == 0) {
					continue;
				} else {				    
			    	//念のためレンタル在庫を強制的に空車に更新する
				    inventoryRecord.setProperty("STATUS", "0");
			        datastore.put(inventoryRecord);
			        
					// 注文締めフラグを立てるだけ
					rentalOrder.setProperty("DATA_FLG", "1");
		        	datastore.put(rentalOrder);
				}
		    } catch ( EntityNotFoundException e) {
		    	e.printStackTrace();
		    	continue;
			}
		}
	}
}
