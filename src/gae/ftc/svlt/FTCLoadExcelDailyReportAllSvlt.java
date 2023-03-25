package gae.ftc.svlt;

import java.io.IOException;
import java.util.ArrayList;
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
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import gae.ftc.util.FTCCodeUtil;
import gae.ftc.util.FTCCommonUtil;

/**
 * 日報データEXCEL作成
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelDailyReportAllSvlt extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		// ヘッダー情報構築
	    arg1.setContentType("application/vnd.ms-excel"); 
	    arg1.setHeader("Pragma", "no-cache");  
	    arg1.setHeader("Cache-Control", "no-cache"); 
	    arg1.setHeader("Content-Type","application/vnd.ms-excel"); 
	    arg1.addHeader("Content-Disposition", "inline; filename=\""+ System.currentTimeMillis() + "DailyReport.xls\""); 
	    
	    try {
	    	HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0,"日報一覧");  
            
            // ヘッダーデータ構築
	        HSSFRow row1 = sheet.createRow(0);
	        
            HSSFCell cell01 = row1.createCell(0);
            HSSFCell cell02 = row1.createCell(1);
            HSSFCell cell03 = row1.createCell(2);
            HSSFCell cell04 = row1.createCell(3);
            HSSFCell cell05 = row1.createCell(4);
            HSSFCell cell06 = row1.createCell(5);
            HSSFCell cell07 = row1.createCell(6);
            HSSFCell cell08 = row1.createCell(7);
            HSSFCell cell09 = row1.createCell(8);

            cell01.setCellValue("担当者");
            cell02.setCellValue("日付");     
            cell03.setCellValue("型式"); 
            cell04.setCellValue("号機"); 
            cell05.setCellValue("作業内容"); 
            cell06.setCellValue("稼働時間"); 
            cell07.setCellValue("顧客コード"); 
            cell08.setCellValue("顧客名"); 
            cell09.setCellValue("区分"); 

            int count = 1;
            
            // 日報データを検索する
    	    Query q = new Query("DailyReport");
    	    List<String> dataFlgList = new ArrayList<String>();
    	    dataFlgList.add("0");
    	    dataFlgList.add("1");
    	    dataFlgList.add("2");
    	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.IN, dataFlgList));
	    	q.addSort("WORK_DATE", SortDirection.ASCENDING);
    	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	    PreparedQuery pq = datastore.prepare(q);
    	    List<Entity> listDailyReportRecord = pq.asList(FetchOptions.Builder.withDefaults());
    	    
            for (Iterator<Entity> iterator = listDailyReportRecord.iterator(); iterator.hasNext();) {
            	Entity dailyReportRecord = (Entity) iterator.next();
		        HSSFRow row = sheet.createRow(count);
		        
		        // セルデータ構築
	            HSSFCell cellData01 = row.createCell(0);
	            HSSFCell cellData02 = row.createCell(1);
	            HSSFCell cellData03 = row.createCell(2);
	            HSSFCell cellData04 = row.createCell(3);
	            HSSFCell cellData05 = row.createCell(4);
	            HSSFCell cellData06 = row.createCell(5);
	            HSSFCell cellData07 = row.createCell(6);
	            HSSFCell cellData08 = row.createCell(7);
	            HSSFCell cellData09 = row.createCell(8);

	            // セルデータ設定
	            cellData01.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("WORKER")));
	            cellData02.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("WORK_DATE")));
	            cellData03.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("NAME")));
	            cellData04.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("SERIALNO")));
	            cellData05.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("MEMO")));
	            cellData06.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("HOURS")));
	            cellData07.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("CLIENT_CODE")));
	            cellData08.setCellValue(FTCCommonUtil.nullConv(dailyReportRecord.getProperty("CLIENT_NAME")));
	            cellData09.setCellValue(FTCCommonUtil.nullConv(FTCCodeUtil.getDailyReportType(dailyReportRecord.getProperty("DATA_FLG"))));

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
