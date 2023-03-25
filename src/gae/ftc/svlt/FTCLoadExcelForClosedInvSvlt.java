package gae.ftc.svlt;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * 締め在庫データEXCEL作成
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelForClosedInvSvlt extends FTCLoadExcelInvCommonAllSvlt {

	@Override
	public String getFileName() {
		return "ClosedInvList";
	}

	@Override
	public void getQuery(Query q, HttpServletRequest request) {
		String sellMonth = request.getParameter("SELL_MONTH");
		// 締め在庫のみを検索
    	List<Filter> filters = new ArrayList<Filter>();
    	filters.add(new FilterPredicate("SELL_MONTH", FilterOperator.EQUAL, sellMonth));
    	filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "1"));
        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);
    	q.addSort("SELL_MONTH", SortDirection.DESCENDING);
    	q.addSort("SELL_PAY_DATE", SortDirection.ASCENDING);
	}

	@Override
	public String getSheetName() {
		return "締め在庫一覧";
	}
}
