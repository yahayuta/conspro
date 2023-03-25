package gae.ftc.svlt;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * 棚卸在庫データEXCEL作成
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelForInvCountSvlt extends FTCLoadExcelForBusinessCommonSvlt {

	@Override
	public String getFileName() {
		return "StockCount";
	}

	@Override
	public void getQuery(Query q) {
		// 条件：仕入支払日が入力ありかつ売上入金日・売上月が空欄
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
    	filters.add(new FilterPredicate("ORDER_PAY_DATE", FilterOperator.NOT_EQUAL, ""));
    	filters.add(new FilterPredicate("SELL_MONTH", FilterOperator.EQUAL, ""));
    	filters.add(new FilterPredicate("SELL_PAY_DATE", FilterOperator.EQUAL, ""));
        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);
		q.addSort("ORDER_PAY_DATE", SortDirection.ASCENDING);
		q.addSort("TYPE", SortDirection.ASCENDING);
	}

	@Override
	public String getSheetName() {
		return "棚卸在庫一覧";
	}
}
