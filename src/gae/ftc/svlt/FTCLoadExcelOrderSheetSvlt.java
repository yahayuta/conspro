package gae.ftc.svlt;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import gae.ftc.util.FTCCommonUtil;


/**
 * 注文書発行
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelOrderSheetSvlt extends HttpServlet {
	
	private static final int MAX_INV = 9;
	private static final SimpleDateFormat SDF_ORDER_NUM = new SimpleDateFormat("yyMMdd");
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String editId = arg0.getParameter("EDITID");
		String ids[ ] = arg0.getParameterValues("REPID");
		
		if (ids == null || ids.length == 0) {
			if (editId == null || editId.length() == 0) {
				// 未選択エラー
				arg0.getSession().setAttribute("MSG", "在庫が選択されていないため出力できません");
				arg1.sendRedirect("FTCGetInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
				return;
			}
			
			ids = new String[]{editId};
		}
		
	    try {
		    // テンプレートのロード
			ServletContext context = super.getServletContext();
			URL url = context.getResource("/doc/OrderSheet.xlsx");
			FileInputStream is = new FileInputStream(url.getPath());
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheet("注文書");
	    
			String sellerCode = null;
			
	        Date now = new Date(System.currentTimeMillis());
	        StringBuffer fileNameBuf = new StringBuffer(SDF_ORDER_NUM.format(now));
	        
	        // カンパニーデータ取得
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("Company", "0001");
		    Entity company = datastore.get(keyObj);
		    
			for (int i = 0; i < ids.length; i++) {
				
				if (MAX_INV == i) {
					break;
				}
				
				String id = ids[i];
				
		        // 在庫データ取得			    
			    keyObj = KeyFactory.createKey("InventoryRecord", id);
			    Entity inventoryRecord = datastore.get(keyObj);
			    
			    // ファイル名作成
			    fileNameBuf.append(inventoryRecord.getProperty("NAME"));
		        
			    // 仕入先
			    if (sellerCode == null) {
			    	sellerCode = FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER_CODE"));
			    	
				    // 仕入先コードが設定されている場合のみ
				    if (sellerCode != null && sellerCode.length() > 0) {
					    keyObj = KeyFactory.createKey("Client", sellerCode);
					    Entity client = datastore.get(keyObj);
					    
					    // 会社名
					    Row row = sheet.getRow(4);
					    Cell cell = row.getCell(1);
				        cell.setCellValue(client.getProperty("COMPANY") + "　御中");
				        
				        // 営業所
				        row = sheet.getRow(5);
				        cell = row.getCell(2);
				        cell.setCellValue(client.getProperty("OFFICE") + "様");
				        
				        // 郵便番号
				        row = sheet.getRow(6);
				        cell = row.getCell(1);
				        cell.setCellValue("〒" + client.getProperty("ZIP"));
				        
				        // 住所
				        row = sheet.getRow(7);
				        cell = row.getCell(1);
				        cell.setCellValue(FTCCommonUtil.nullConv(client.getProperty("ADDRESS")));
				        
				        // 電話番号
				        row = sheet.getRow(9);
				        cell = row.getCell(1);
				        cell.setCellValue("電話：" + client.getProperty("TEL") + "　FAX：" + client.getProperty("FAX"));
				    }
				    
			    } else if (sellerCode != null && !sellerCode.equals(inventoryRecord.getProperty("SELLER_CODE"))) {
			    	// 仕入先コード一致しないエラー
					arg0.getSession().setAttribute("MSG", "仕入先の一致しない在庫が含まれているため出力できません");
					arg1.sendRedirect("FTCGetInventoryList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
					workbook.close();
					return;
			    }

			    // 商品名
			    Row row = sheet.getRow(22 + (i*2));
			    Cell cell = row.getCell(1);
		        cell.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("NAME")));
		        
		        // 号機
		        row = sheet.getRow(23 + (i*2));
		        cell = row.getCell(1);
		        cell.setCellValue(FTCCommonUtil.nullConv(inventoryRecord.getProperty("SERIALNO")));
		        
		        // 数量
		        row = sheet.getRow(22 + (i*2));
		        cell = row.getCell(5);
		        cell.setCellValue(1);
		        
		        // 単位
		        row = sheet.getRow(22 + (i*2));
		        cell = row.getCell(6);
		        cell.setCellValue("台");
		        
		        // 仕入価格
		        row = sheet.getRow(22+ (i*2));
		        cell = row.getCell(7);
		        cell.setCellValue(FTCCommonUtil.parseDouble(inventoryRecord.getProperty("ORDER_PRICE")));
		        
		        // 引渡場所
		        if (inventoryRecord.getProperty("TRAN_PLACE") != null && FTCCommonUtil.nullConv(inventoryRecord.getProperty("TRAN_PLACE")).length() > 0) {
			        row = sheet.getRow(22+ (i*2));
			        cell = row.getCell(9);
			        cell.setCellValue("引渡場所：" + inventoryRecord.getProperty("TRAN_PLACE"));
		        }
		        
		        // 入力日
		        row = sheet.getRow(1);
		        cell = row.getCell(9);
		        cell.setCellValue(now);
		        
		        // 会社情報の出力
		        row = sheet.getRow(4);
		        cell = row.getCell(10);
		        cell.setCellValue(FTCCommonUtil.nullConv(company.getProperty("COMPANY")));
		        
		        row = sheet.getRow(5);
		        cell = row.getCell(10);
		        cell.setCellValue("〒 " + FTCCommonUtil.nullConv(company.getProperty("ZIP")));
		        
		        row = sheet.getRow(6);
		        cell = row.getCell(10);
		        cell.setCellValue(FTCCommonUtil.nullConv(company.getProperty("ADDRESS")));
		        
		        row = sheet.getRow(7);
		        cell = row.getCell(10);
		        cell.setCellValue("電話：" + FTCCommonUtil.nullConv(company.getProperty("TEL")) + "　FAX：" + FTCCommonUtil.nullConv(company.getProperty("FAX")));
			}

	        // 再計算
	        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
	        formulaEvaluator.evaluateAll();
	        
		    // ヘッダー情報構築
		    arg1.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		    arg1.setHeader("Pragma", "no-cache");  
		    arg1.setHeader("Cache-Control", "no-cache"); 
		    arg1.setHeader("Content-Type","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		    arg1.addHeader("Content-Disposition", "inline; filename=\"" + fileNameBuf.toString() + ".xlsx\""); 
		    
	        // ストリームに書き込み
			ServletOutputStream objSos = arg1.getOutputStream();
	        workbook.write(objSos);
			objSos.close();
			workbook.close();
			
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
 
}
