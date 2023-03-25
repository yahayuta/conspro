package gae.ftc.svlt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
import com.google.appengine.api.datastore.Query.SortDirection;

import gae.ftc.util.FTCCommonUtil;

/**
 * レンタル売上一覧EXCEL作成基底
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelRentalOrderSalesListSvlt extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String salesMonth = arg0.getParameter("SALES_MONTH");
		if (salesMonth == null || salesMonth.length() == 0) {
			// エラーページへ
			arg1.sendRedirect("Error.jsp");
			return;
		}
        
    	// データ検索
		Query q = new Query("RentalOrderSales");
	    List<Filter> filters = new ArrayList<Filter>();
	    filters.add(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
	    filters.add(new FilterPredicate("SALES_MONTH", FilterOperator.EQUAL, salesMonth));
        CompositeFilter filter = CompositeFilterOperator.and(filters);
        q.setFilter(filter);
    	q.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> rentalOrderSalesList = pq.asList(FetchOptions.Builder.withDefaults());
	    
	    // ヘッダー情報構築
	    arg1.setContentType("application/vnd.ms-excel"); 
	    arg1.setHeader("Pragma", "no-cache");  
	    arg1.setHeader("Cache-Control", "no-cache"); 
	    arg1.setHeader("Content-Type","application/vnd.ms-excel"); 
	    arg1.addHeader("Content-Disposition", "inline; filename=\""+ System.currentTimeMillis() + "SalesOrderList.xls\""); 
	    
	    try {
	    	HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0,salesMonth.replaceAll("/", ""));  
            
            // ヘッダーデータ構築
	        HSSFRow row1 = sheet.createRow(0);
	        
            HSSFCell cell01 = row1.createCell(0);
            HSSFCell cell02 = row1.createCell(1);
            HSSFCell cell03 = row1.createCell(2);
            HSSFCell cell04 = row1.createCell(3);
            
            cell01.setCellValue("売上先");
            cell02.setCellValue("売上金額");     
            cell03.setCellValue("在庫ID"); 
            cell04.setCellValue("計上日"); 
            
            int count = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            
            for (Iterator<Entity> iterator = rentalOrderSalesList.iterator(); iterator.hasNext();) {
            	Entity rentalOrderSales = (Entity) iterator.next();
		        HSSFRow row = sheet.createRow(count);
		        
		        // セルデータ構築
	            HSSFCell cellData01 = row.createCell(0);
	            HSSFCell cellData02 = row.createCell(1);
	            HSSFCell cellData03 = row.createCell(2);
	            HSSFCell cellData04 = row.createCell(3);
	            
	            // セルデータ設定
	            cellData01.setCellValue(FTCCommonUtil.nullConv(rentalOrderSales.getProperty("CLIENT_NAME"))); //売上先 
	            cellData02.setCellValue(FTCCommonUtil.nullConv(rentalOrderSales.getProperty("AMOUNT"))); //売上金額
	            cellData03.setCellValue(FTCCommonUtil.nullConv(rentalOrderSales.getProperty("RENTAL_INVENTORY_CODE"))); //在庫ID
	            Date date = new Date(Long.parseLong(rentalOrderSales.getKey().getName()));
	            cellData04.setCellValue(sdf.format(date)); //計上日
	            
		        count++;
			}
            
			ServletOutputStream objSos = arg1.getOutputStream();
	        workbook.write(objSos);
			objSos.close();
			workbook.close();
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
