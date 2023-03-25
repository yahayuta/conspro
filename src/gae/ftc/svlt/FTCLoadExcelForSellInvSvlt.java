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
 * 販売用在庫データEXCEL作成
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelForSellInvSvlt extends FTCLoadExcelForBusinessCommonSvlt {

	@Override
	public String getFileName() {
		return "InvListForSell";
	}

	@Override
	public void getQuery(Query q) {
		// 条件：販売先が空欄
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
    	filters.add(new FilterPredicate("BUYER", FilterOperator.EQUAL, ""));
        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);
    	q.addSort("TYPE", SortDirection.ASCENDING);
	}

	@Override
	public String getSheetName() {
		return "販売用在庫一覧";
	}
}
