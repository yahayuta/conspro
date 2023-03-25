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
 * レンタル注文書発行
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelRentalOrderSheetSvlt extends HttpServlet {
	
	@SuppressWarnings("resource")
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String editId = arg0.getParameter("EDITID");

		if (editId == null || editId.length() == 0) {
			// 未選択エラー
			arg0.getSession().setAttribute("MSG", "注文がが選択されていないため出力できません");
			arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			return;
		}
		
		String type = arg0.getParameter("type");
		if (type == null || type.length() == 0) {
			// エラーページへ
	    	throw new ServletException("処理異常です。データ不正です。");
		}
		
		String templatePath = "/doc/RentalOrderSheet.xlsx";
		String sheetName = "出庫・返却伝票";
		
	    try {
		    // テンプレートのロード
			ServletContext context = super.getServletContext();
			URL url = context.getResource(templatePath);
			FileInputStream is = new FileInputStream(url.getPath());
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheet(sheetName);
	        
	        // 注文データ取得			   
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("RentalOrder", editId);
		    Entity rentalOrder = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (rentalOrder == null) {
				// エラーページへ
		    	throw new ServletException("処理異常です。データ不正です。");
	    	}
	    	
		    // 販売先
		    String clientrCode = FTCCommonUtil.nullConv(rentalOrder.getProperty("CLIENT_CODE"));   

	        // カンパニーデータ取得
		    datastore = DatastoreServiceFactory.getDatastoreService();
		    keyObj = KeyFactory.createKey("Company", "0001");
		    Entity company = datastore.get(keyObj);
		    
		    // 販売先コードが設定されている場合のみ
		    if (clientrCode != null && clientrCode.length() > 0) {
			    keyObj = KeyFactory.createKey("Client", clientrCode);
			    Entity client = datastore.get(keyObj);
			    
			    // 会社名
			    Row row = sheet.getRow(4);
			    Cell cell = row.getCell(0);
		        cell.setCellValue(client.getProperty("COMPANY") + "　御中");
		        
		        // 営業所
		        row = sheet.getRow(5);
		        cell = row.getCell(0);
		        cell.setCellValue(client.getProperty("OFFICE") + "　様");
		        
		        // TEL
		        row = sheet.getRow(7);
		        cell = row.createCell(1);
		        cell.setCellValue(FTCCommonUtil.nullConv(client.getProperty("TEL")));
		        
		        // FAX
		        row = sheet.getRow(8);
		        cell = row.createCell(1);
		        cell.setCellValue(FTCCommonUtil.nullConv(client.getProperty("FAX")));
		    }
		    
	        // 型式・号機
		    Row row = sheet.getRow(10);
		    Cell cell = row.getCell(4);
	        cell.setCellValue(rentalOrder.getProperty("NAME") + "・" + rentalOrder.getProperty("SERIALNO"));
	  
	        // 搬入先
	        row = sheet.getRow(11);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("ORDER_PLACE")));
	        
	        // ユーザー名
	        row = sheet.getRow(12);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("ORDER_NAME")));
	
	        // 月極／日極
	        row = sheet.getRow(13);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("PRICE")));
	        
	        // 出庫日
	        row = sheet.getRow(14);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("OUT_DATE")));
	        
	        // 搬入運賃
	        row = sheet.getRow(15);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("TRANS_FEE")));
	  
	        // 稼動時間開始時
	        row = sheet.getRow(16);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("HOURS_START")));
	     
	        // レンタル開始日
	        row = sheet.getRow(17);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("START_DATE")));
	        
	        // サポート料金
	        row = sheet.getRow(18);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("SUPPORT_FEE")));
	        
	        // 返却日
	        row = sheet.getRow(28);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("IN_DATE")));
	        
	        // 返却運賃
	        row = sheet.getRow(29);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("RETURN_FEE")));
	        
	        // 稼動時間終了時
	        row = sheet.getRow(30);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("HOURS_END")));
	        
	        // レンタル終了日
	        row = sheet.getRow(32);
	        cell = row.getCell(4);
	        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("END_DATE")));

	        // 会社情報の出力
	        row = sheet.getRow(5);
	        cell = row.getCell(7);
	        cell.setCellValue(FTCCommonUtil.nullConv(company.getProperty("COMPANY")));
	        
	        row = sheet.getRow(6);
	        cell = row.getCell(7);
	        cell.setCellValue(FTCCommonUtil.nullConv(company.getProperty("ADDRESS")));
	        
	        row = sheet.getRow(7);
	        cell = row.getCell(7);
	        cell.setCellValue("電話：" + FTCCommonUtil.nullConv(company.getProperty("TEL")));
	        
	        row = sheet.getRow(8);
	        cell = row.getCell(7);
	        cell.setCellValue("FAX：" + FTCCommonUtil.nullConv(company.getProperty("FAX")));
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        
		    // ヘッダー情報構築
		    arg1.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		    arg1.setHeader("Pragma", "no-cache");  
		    arg1.setHeader("Cache-Control", "no-cache"); 
		    arg1.setHeader("Content-Type","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		    arg1.addHeader("Content-Disposition", "inline; filename=\"" + sdf.format(new Date(System.currentTimeMillis())) + "_" + rentalOrder.getProperty("NAME") + rentalOrder.getProperty("SERIALNO")+ ".xlsx\""); 
		    
	        // ストリームに書き込みsdf
			ServletOutputStream objSos = arg1.getOutputStream();
	        workbook.write(objSos);
			objSos.close();
			workbook.close();
			
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
 
}
