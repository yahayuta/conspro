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

import gae.ftc.util.FTCCodeUtil;
import gae.ftc.util.FTCCommonUtil;

/**
 * 業務用データEXCEL作成基底
 * @author yahayuta
 */
@SuppressWarnings("serial")
public abstract class FTCLoadExcelForBusinessCommonSvlt extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// 在庫データを検索する
	    Query q = new Query("InventoryRecord");
	    getQuery(q);
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    PreparedQuery pq = datastore.prepare(q);
	    List<Entity> listInventoryRecord = pq.asList(FetchOptions.Builder.withDefaults());
	    
	    // ヘッダー情報構築
	    arg1.setContentType("application/vnd.ms-excel"); 
	    arg1.setHeader("Pragma", "no-cache");  
	    arg1.setHeader("Cache-Control", "no-cache"); 
	    arg1.setHeader("Content-Type","application/vnd.ms-excel"); 
	    arg1.addHeader("Content-Disposition", "inline; filename=\""+ System.currentTimeMillis() + getFileName() + ".xls\""); 
	    
	    try {
	    	HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0,getSheetName());  
            
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
            HSSFCell cell19 = row1.createCell(18);
            HSSFCell cell20 = row1.createCell(19);
            HSSFCell cell21 = row1.createCell(20);
            HSSFCell cell22 = row1.createCell(21);
            HSSFCell cell23 = row1.createCell(22);
            HSSFCell cell24 = row1.createCell(23);
            HSSFCell cell25 = row1.createCell(24);
            HSSFCell cell26 = row1.createCell(25);
            HSSFCell cell27 = row1.createCell(26);
            HSSFCell cell28 = row1.createCell(27);
            HSSFCell cell29 = row1.createCell(28);
            
            cell01.setCellValue("仕入担当");
            cell02.setCellValue("発注日");
            cell03.setCellValue("型式"); 
            cell04.setCellValue("号機"); 
            cell05.setCellValue("WEB表示");
            cell06.setCellValue("仕入先コード");
            cell07.setCellValue("仕入先");
            cell08.setCellValue("仕入価格");
            cell09.setCellValue("仕入運賃");
            cell10.setCellValue("部品代");
            cell11.setCellValue("整備費");
            cell12.setCellValue("仕入外注費");
            cell13.setCellValue("仕入原価");
            cell14.setCellValue("引渡場所");
            cell15.setCellValue("販売運賃");
            cell16.setCellValue("船積費用");
            cell17.setCellValue("販売外注費");
            cell18.setCellValue("保険料");
            cell19.setCellValue("フレイト");
            cell20.setCellValue("販売原価");
            cell21.setCellValue("販売価格");
            cell22.setCellValue("利益");
            cell23.setCellValue("販売先コード");
            cell24.setCellValue("販売先");
            cell25.setCellValue("INVOICE NO");
            cell26.setCellValue("業販価格");
            cell27.setCellValue("仕入代金支払日");
            cell28.setCellValue("売上入金日");
            cell29.setCellValue("売上月");
            
            int count = 1;

            for (Iterator<Entity> iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
            	Entity inventoryRecord = (Entity) iterator.next();
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
	            HSSFCell cellData19 = row.createCell(18);
	            HSSFCell cellData20 = row.createCell(19);
	            HSSFCell cellData21 = row.createCell(20);
	            HSSFCell cellData22 = row.createCell(21);
	            HSSFCell cellData23 = row.createCell(22);
	            HSSFCell cellData24 = row.createCell(23);
	            HSSFCell cellData25 = row.createCell(24);
	            HSSFCell cellData26 = row.createCell(25);
	            HSSFCell cellData27 = row.createCell(26);
	            HSSFCell cellData28 = row.createCell(27);
	            HSSFCell cellData29 = row.createCell(28);
	            
	            // セルデータ設定            
	            cellData01.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ACCOUNT"))); //仕入担当
	            cellData02.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_DATE"))); //発注日
	            cellData03.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("NAME"))); //型式  
	            cellData04.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SERIALNO"))); //号機
	            cellData05.setCellValue(FTCCodeUtil.getIsDisp(inventoryRecord.getProperty("WEB_DISP"))); //WEB表示
	            cellData06.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER_CODE"))); //仕入先コード
	            cellData07.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER"))); //仕入先
	            cellData08.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_PRICE"))); //仕入価格
	            cellData09.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_TRANS_COST"))); //仕入運賃
	            cellData10.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("PARTS_COST"))); //部品代
	            cellData11.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("MAINTENANCE_COST"))); //整備費
	            cellData12.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_OUT_ORDER_COST"))); //仕入外注費
	            cellData13.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_COST_PRICE"))); //仕入原価
	            cellData14.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("TRAN_PLACE"))); //引渡場所
	            
	            cellData15.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_TRANCE_COST"))); //販売運賃
	            cellData16.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SHIP_COST"))); //船積費用
	            cellData17.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_OUT_ORDER_COST"))); //販売外注費
	            cellData18.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("INS_COST"))); //保険料
	            cellData19.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("FREIGHT_COST"))); //フレイト
	            cellData20.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_COST_PRICE"))); //販売原価
	            cellData21.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_PRICE"))); //販売価格
	            cellData22.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("PROFIT"))); //利益
	            cellData23.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("BUYER_CODE"))); //販売先コード
	            cellData24.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("BUYER"))); //販売先
	            cellData25.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("INVOICE_NO"))); //INVOICE NO
	            cellData26.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("WHOL_PRICE"))); //業販価格
	            
	            cellData27.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_PAY_DATE"))); //仕入代金支払日
	            cellData28.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_PAY_DATE"))); //売上入金日
	            cellData29.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_MONTH"))); //売上月
	            
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
	
	/**
	 * ファイル名を取得する
	 * @return ファイル名
	 */
	public abstract String getFileName();
	
	/**
	 * クエリーを取得する
	 * @param q
	 */
	public abstract void getQuery(Query q);
	
	/**
	 * シート名を取得する
	 * @return シート名
	 */
	public abstract String getSheetName();
}
