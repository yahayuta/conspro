package gae.ftc.svlt;

import java.io.IOException;
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

import gae.ftc.util.FTCCodeUtil;
import gae.ftc.util.FTCCommonUtil;

/**
 * 顧客データEXCEL作成
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelClientAllSvlt extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
	    
	    // ヘッダー情報構築
	    arg1.setContentType("application/vnd.ms-excel"); 
	    arg1.setHeader("Pragma", "no-cache");  
	    arg1.setHeader("Cache-Control", "no-cache"); 
	    arg1.setHeader("Content-Type","application/vnd.ms-excel"); 
	    arg1.addHeader("Content-Disposition", "inline; filename=\""+ System.currentTimeMillis() + "Client.xls\""); 
	    
	    try {
	    	HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0,"顧客一覧");  
            
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
            HSSFCell cell10 = row1.createCell(9);
            HSSFCell cell11 = row1.createCell(10);
            HSSFCell cell12 = row1.createCell(11);
            HSSFCell cell13 = row1.createCell(12);
            HSSFCell cell14 = row1.createCell(13);
            HSSFCell cell15 = row1.createCell(14);
            HSSFCell cell16 = row1.createCell(15);
            HSSFCell cell17 = row1.createCell(16);
            HSSFCell cell18 = row1.createCell(17);
            
            cell01.setCellValue("顧客コード");
            cell02.setCellValue("業種");     
            cell03.setCellValue("与信管理"); 
            cell04.setCellValue("国番号"); 
            cell05.setCellValue("顧客番号"); 
            cell06.setCellValue("会社名");
            cell07.setCellValue("支店営業所");
            cell08.setCellValue("担当者1"); 
            cell09.setCellValue("住所");
            cell10.setCellValue("郵便番号");
            cell11.setCellValue("電話番号");
            cell12.setCellValue("FAX番号");
            cell13.setCellValue("メール");
            cell14.setCellValue("備考");
            cell15.setCellValue("担当者2");
            cell16.setCellValue("担当者3");
            cell17.setCellValue("担当者4");
            cell18.setCellValue("担当者5");
            
            int count = 1;
            
            // 顧客データを検索する
    	    Query q = new Query("Client");
    	    q.setFilter(new FilterPredicate("DATA_FLG", FilterOperator.EQUAL, "0"));
    	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	    PreparedQuery pq = datastore.prepare(q);
    	    List<Entity> listClientRecord = pq.asList(FetchOptions.Builder.withDefaults());
    	    
            for (Iterator<Entity> iterator = listClientRecord.iterator(); iterator.hasNext();) {
            	Entity clientRecord = (Entity) iterator.next();
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
	            HSSFCell cellData10 = row.createCell(9);
	            HSSFCell cellData11 = row.createCell(10);
	            HSSFCell cellData12 = row.createCell(11);
	            HSSFCell cellData13 = row.createCell(12);
	            HSSFCell cellData14 = row.createCell(13);
	            HSSFCell cellData15 = row.createCell(14);
	            HSSFCell cellData16 = row.createCell(15);
	            HSSFCell cellData17 = row.createCell(16);
	            HSSFCell cellData18 = row.createCell(17);
	            
	            // セルデータ設定
	            cellData01.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("CLIENT_CODE")));
	            cellData02.setCellValue(FTCCodeUtil.getClientType(clientRecord.getProperty("CLIENT_TYPE")));
	            cellData03.setCellValue(FTCCodeUtil.getCredit(clientRecord.getProperty("CREDIT")));
	            cellData04.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("COUNTRY")));
	            cellData05.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("SEQ")));
	            cellData06.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("COMPANY")));
	            cellData07.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("OFFICE")));
	            cellData08.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("NAME")));
	            cellData09.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("ADDRESS")));
	            cellData10.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("ZIP")));
	            cellData11.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("TEL")));
	            cellData12.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("FAX")));
	            cellData13.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("MAIL")));
	            cellData14.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("COMMENT")));
	            cellData15.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("NAME2")));
	            cellData16.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("NAME3")));
	            cellData17.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("NAME4")));
	            cellData18.setCellValue(FTCCommonUtil.nullConv(clientRecord.getProperty("NAME5")));
	            
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
