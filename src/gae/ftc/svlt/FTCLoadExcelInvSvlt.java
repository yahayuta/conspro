package gae.ftc.svlt;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * 在庫データEXCEL作成
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelInvSvlt extends FTCLoadExcelInvCommonAllSvlt {

	@Override
	public String getFileName() {
		return "InvList";
	}

	@Override
	public void getQuery(Query q, HttpServletRequest request) {
		// 締められていない在庫をすべて取得
		q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
    	q.addSort("TYPE", SortDirection.ASCENDING);
    	q.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.DESCENDING);
	}

	@Override
	public String getSheetName() {
		return "在庫一覧";
	}
}
