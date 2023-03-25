package gae.ftc.svlt;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import gae.ftc.util.FTCCodeUtil;
import gae.ftc.util.FTCCommonUtil;

/**
 * 在庫データEXCEL作成基底
 * @author yahayuta
 */
@SuppressWarnings("serial")
public abstract class FTCLoadExcelInvCommonAllSvlt extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// 在庫データを検索する
	    Query q = new Query("InventoryRecord");
	    getQuery(q, arg0);
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
            HSSFCell cell30 = row1.createCell(29);
            HSSFCell cell31 = row1.createCell(30);
            HSSFCell cell32 = row1.createCell(31);
            HSSFCell cell33 = row1.createCell(32);
            HSSFCell cell34 = row1.createCell(33);
            HSSFCell cell35 = row1.createCell(34);
            HSSFCell cell36 = row1.createCell(35);
            HSSFCell cell37 = row1.createCell(36);
            HSSFCell cell38 = row1.createCell(37);
            HSSFCell cell39 = row1.createCell(38);
            HSSFCell cell40 = row1.createCell(39);
            
            cell01.setCellValue("分類");
            cell02.setCellValue("メーカー");     
            cell03.setCellValue("型式"); 
            cell04.setCellValue("号機"); 
            cell05.setCellValue("年式"); 
            cell06.setCellValue("稼働時間");
            cell07.setCellValue("DETAIL");
            cell08.setCellValue("詳細");
            cell09.setCellValue("程度");
            cell10.setCellValue("表示価格");
            cell11.setCellValue("業販価格");
            cell12.setCellValue("写真"); 
            cell13.setCellValue("登録日");
            cell14.setCellValue("仕入担当");
            cell15.setCellValue("発注日");
            cell16.setCellValue("WEB表示");
            cell17.setCellValue("仕入先コード");
            cell18.setCellValue("仕入先");
            cell19.setCellValue("仕入価格");
            cell20.setCellValue("仕入運賃");
            cell21.setCellValue("部品代");
            cell22.setCellValue("整備費");
            cell23.setCellValue("仕入外注費");
            cell24.setCellValue("仕入原価");
            cell25.setCellValue("引渡場所");
            cell26.setCellValue("販売運賃");
            cell27.setCellValue("船積費用");
            cell28.setCellValue("販売外注費");
            cell29.setCellValue("保険料");
            cell30.setCellValue("フレイト");
            cell31.setCellValue("販売原価");
            cell32.setCellValue("販売価格");
            cell33.setCellValue("利益");
            cell34.setCellValue("販売先コード");
            cell35.setCellValue("販売先");
            cell36.setCellValue("INVOICE NO");
            cell37.setCellValue("仕入代金支払日");
            cell38.setCellValue("売上入金日");
            cell39.setCellValue("売上月");
            cell40.setCellValue("整備状況");
            
            int count = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            
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
	            HSSFCell cellData30 = row.createCell(29);
	            HSSFCell cellData31 = row.createCell(30);
	            HSSFCell cellData32 = row.createCell(31);
	            HSSFCell cellData33 = row.createCell(32);
	            HSSFCell cellData34 = row.createCell(33);	
	            HSSFCell cellData35 = row.createCell(34);	
	            HSSFCell cellData36 = row.createCell(35);	
	            HSSFCell cellData37 = row.createCell(36);	
	            HSSFCell cellData38 = row.createCell(37);	
	            HSSFCell cellData39 = row.createCell(38);	
	            HSSFCell cellData40 = row.createCell(39);
	            
	            // セルデータ設定
	            cellData01.setCellValue(FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE"))); //分類
	            cellData02.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("MANUFACTURER"))); //メーカー 
	            cellData03.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("NAME"))); //型式  
	            cellData04.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SERIALNO"))); //号機
	            cellData05.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("YEAR"))); //年式
	            cellData06.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("HOURS"))); //稼働時間
	            cellData07.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("OTHER"))); //DETAIL
	            cellData08.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("OTHER_JA"))); //詳細
	            cellData09.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("CONDITION"))); //程度
	            cellData10.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("PRICE"))); //表示価格
	            cellData11.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("WHOL_PRICE"))); //業販価格
	            cellData12.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("PIC_URL"))); //写真
	            Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
	            cellData13.setCellValue(sdf.format(date)); //登録日
	            
	            cellData14.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ACCOUNT"))); //仕入担当
	            cellData15.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_DATE"))); //発注日
	            cellData16.setCellValue(FTCCodeUtil.getIsDisp(inventoryRecord.getProperty("WEB_DISP"))); //WEB表示
	            cellData17.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER_CODE"))); //仕入先コード
	            cellData18.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER"))); //仕入先
	            cellData19.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_PRICE"))); //仕入価格
	            cellData20.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_TRANS_COST"))); //仕入運賃
	            cellData21.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("PARTS_COST"))); //部品代
	            cellData22.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("MAINTENANCE_COST"))); //整備費
	            cellData23.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_OUT_ORDER_COST"))); //仕入外注費
	            cellData24.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_COST_PRICE"))); //仕入原価
	            cellData25.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("TRAN_PLACE"))); //引渡場所
	            
	            cellData26.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_TRANCE_COST"))); //販売運賃
	            cellData27.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SHIP_COST"))); //船積費用
	            cellData28.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_OUT_ORDER_COST"))); //販売外注費
	            cellData29.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("INS_COST"))); //保険料
	            cellData30.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("FREIGHT_COST"))); //フレイト
	            cellData31.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_COST_PRICE"))); //販売原価
	            cellData32.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_PRICE"))); //販売価格
	            cellData33.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("PROFIT"))); //利益
	            cellData34.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("BUYER_CODE"))); //販売先コード
	            cellData35.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("BUYER"))); //販売先
	            cellData36.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("INVOICE_NO"))); //INVOICE NO
	            
	            cellData37.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("ORDER_PAY_DATE"))); //仕入代金支払日
	            cellData38.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_PAY_DATE"))); //売上入金日
	            cellData39.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELL_MONTH"))); //売上月
	            cellData40.setCellValue(FTCCodeUtil.getConditionMaintenance(inventoryRecord.getProperty("CONDITION_MAINTENANCE"))); //整備状況
	            
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
	 * 条件設定可能
	 * @param request
	 * @param q
	 */
	public abstract void getQuery(Query q, HttpServletRequest request);
	
	/**
	 * シート名を取得する
	 * @return シート名
	 */
	public abstract String getSheetName();
}
