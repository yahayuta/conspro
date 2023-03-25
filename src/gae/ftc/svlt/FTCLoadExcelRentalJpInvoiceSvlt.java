package gae.ftc.svlt;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
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
 * レンタル注文請求書発行
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoadExcelRentalJpInvoiceSvlt extends HttpServlet {
	
	private static final int MAX_INV = 9;
	
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
			URL url = context.getResource("/doc/RentalJpInvoice.xlsx");
			FileInputStream is = new FileInputStream(url.getPath());
	        Workbook workbook = new XSSFWorkbook(is);
	        Sheet sheet = workbook.getSheet("請求書");
				
		    String clientrCode = null;
		    
	        // カンパニーデータ取得
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("Company", "0001");
		    Entity company = datastore.get(keyObj);
		    
			for (int i = 0; i < ids.length; i++) {
				
				if (MAX_INV == i) {
					break;
				}
				
				String id = ids[i];
				
		        // 注文データ取得
			    keyObj = KeyFactory.createKey("RentalOrder", id);
			    Entity rentalOrder = datastore.get(keyObj);
			    
			    // レンタル注文を更新する
			    FTCCommonUtil.updateRentalOrder(datastore, rentalOrder, arg0);
			    // 請求データを更新する
			    FTCCommonUtil.updateRentalOrderInvoiceHistory(rentalOrder);

			    // 顧客コード
			    if (clientrCode == null) {
				    // 顧客コード
				     clientrCode = FTCCommonUtil.nullConv(rentalOrder.getProperty("CLIENT_CODE"));    	
				    // 顧客コードが設定されている場合のみ
				    if (clientrCode != null && clientrCode.length() > 0) {
					    keyObj = KeyFactory.createKey("Client", clientrCode);
					    Entity client = datastore.get(keyObj);
					    
					    // 会社名
					    Row row = sheet.getRow(3);
					    Cell cell = row.getCell(2);
				        cell.setCellValue(client.getProperty("COMPANY") + "　御中");
				        
				        // 営業所
				        row = sheet.getRow(4);
				        cell = row.getCell(2);
				        cell.setCellValue(client.getProperty("OFFICE") + "様");
				        
				        // 郵便番号
				        row = sheet.getRow(1);
				        cell = row.getCell(2);
				        cell.setCellValue("〒" + client.getProperty("ZIP"));
				        
				        // 住所
				        row = sheet.getRow(2);
				        cell = row.getCell(2);
				        cell.setCellValue(FTCCommonUtil.nullConv(client.getProperty("ADDRESS")));
				        
				        // 電話番号
				        row = sheet.getRow(8);
				        cell = row.getCell(2);
				        cell.setCellValue("電話：" + client.getProperty("TEL") + "　FAX：" + client.getProperty("FAX"));
				    }
			    } else if (clientrCode != null && !clientrCode.equals(rentalOrder.getProperty("CLIENT_CODE"))) {
			    	// 販売先コード一致しないエラー
					arg0.getSession().setAttribute("MSG", "顧客の一致しない注文が含まれているため出力できません");
					arg1.sendRedirect("FTCGetRentalOrderList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
					workbook.close();
					return;
			    }
			    
			    int rowPos = 17;
			    if ("1".equals(arg0.getParameter("AMOUNT_CHK"))) {
				    // 型式 ／号機
				    Row row = sheet.getRow(rowPos + (i*2));
				    Cell cell = row.getCell(1);
			        cell.setCellValue(rentalOrder.getProperty("NAME") + " " + rentalOrder.getProperty("SERIALNO"));
			        
			        // 数量
			        double count = FTCCommonUtil.parseDouble(rentalOrder.getProperty("COUNT"));
			        cell = row.getCell(6);
		        	cell.setCellValue(count);
		        	
			        // 単価（税別）
			        double price = FTCCommonUtil.parseDouble(rentalOrder.getProperty("PRICE"));
			        if (price != 0) {
				        cell = row.getCell(8);
			        	cell.setCellValue(price);
			        }
			        cell = row.getCell(10);
			        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("AMOUNT_MEMO")));
			        rowPos = rowPos + 2;
			    }

			    if ("1".equals(arg0.getParameter("SUPPORT_CHK"))) {
				    // サポート料金
				    Row row = sheet.getRow(rowPos + (i*2));
				    Cell cell = row.getCell(1);
			        cell.setCellValue("サポート料金");
			        
			        cell = row.getCell(6);
		        	cell.setCellValue(1);
		        	
			        // サポート料金
			        double transFee = FTCCommonUtil.parseDouble(rentalOrder.getProperty("SUPPORT_FEE"));
			        if (transFee != 0) {
				        cell = row.getCell(8);
				        cell.setCellValue(transFee);
			        }
			        cell = row.getCell(10);
			        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("SUPPORT_MEMO")));
			        rowPos = rowPos + 2;
			    }
	
			    if ("1".equals(arg0.getParameter("TRANS_FEE_CHK"))) {
				    // 搬入運賃
				    Row row = sheet.getRow(rowPos + (i*2));
				    Cell cell = row.getCell(1);
			        cell.setCellValue("搬入運賃");
			        
			        cell = row.getCell(6);
		        	cell.setCellValue(1);
		        	
			        // 搬入運賃
			        double transFee = FTCCommonUtil.parseDouble(rentalOrder.getProperty("TRANS_FEE"));
			        if (transFee != 0) {
				        cell = row.getCell(8);
				        cell.setCellValue(transFee);
			        }
			        cell = row.getCell(10);
			        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("TRANS_FEE_MEMO")));
			        rowPos = rowPos + 2;
			    }
			    
			    if ("1".equals(arg0.getParameter("RETURN_FEE_CHK"))) {
				    // 返却運賃
				    Row row = sheet.getRow(rowPos + (i*2));
				    Cell cell = row.getCell(1);
			        cell.setCellValue("返却運賃");
			        
			        cell = row.getCell(6);
		        	cell.setCellValue(1);
		        	
			        // 返却運賃
			        double returnFee = FTCCommonUtil.parseDouble(rentalOrder.getProperty("RETURN_FEE"));
			        if (returnFee != 0) {
				        cell = row.getCell(8);
				        cell.setCellValue(returnFee);
			        }
			        cell = row.getCell(10);
			        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("RETURN_FEE_MEMO")));
			        rowPos = rowPos + 2;
			    }
			    
			    if ("1".equals(arg0.getParameter("FEE1_CHK"))) {
			        // その他費用1
			        double fee1 = FTCCommonUtil.parseDouble(rentalOrder.getProperty("FEE1"));
				    if (fee1 != 0) {
				    	Row row = sheet.getRow(rowPos + (i*2));
				    	Cell cell = row.getCell(1);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE1_NAME")));
				        
				        cell = row.getCell(6);
			        	cell.setCellValue(1);
			        	
				        cell = row.getCell(8);
				        cell.setCellValue(fee1);
				        
				        cell = row.getCell(10);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE1_MEMO")));
				        rowPos = rowPos + 2;
			        }
			    }
		       
			    if ("1".equals(arg0.getParameter("FEE2_CHK"))) {
			        // その他費用2
			        double fee2 = FTCCommonUtil.parseDouble(rentalOrder.getProperty("FEE2"));
				    if (fee2 != 0) {
				    	Row row = sheet.getRow(rowPos + (i*2));
				    	Cell cell = row.getCell(1);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE2_NAME")));
				        
				        cell = row.getCell(6);
			        	cell.setCellValue(1);
			        	
				        cell = row.getCell(8);
				        cell.setCellValue(fee2);
				        
				        cell = row.getCell(10);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE2_MEMO")));
				        rowPos = rowPos + 2;
				    }
			    }
		        
			    if ("1".equals(arg0.getParameter("FEE3_CHK"))) {
			        // その他費用3
				    double fee3 = FTCCommonUtil.parseDouble(rentalOrder.getProperty("FEE3"));
				    if (fee3 != 0) {
				    	Row row = sheet.getRow(rowPos + (i*2));
				    	Cell cell = row.getCell(1);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE3_NAME")));
				        
				        cell = row.getCell(6);
			        	cell.setCellValue(1);
			        	
				        cell = row.getCell(8);
				        cell.setCellValue(fee3);
				        
				        cell = row.getCell(10);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE3_MEMO")));
				        rowPos = rowPos + 2;
				    }
			    }
			    
			    if ("1".equals(arg0.getParameter("FEE4_CHK"))) {
			        // その他費用4
				    double fee4 = FTCCommonUtil.parseDouble(rentalOrder.getProperty("FEE4"));
				    if (fee4 != 0) {
				    	Row row = sheet.getRow(rowPos + (i*2));
				    	Cell cell = row.getCell(1);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE4_NAME")));
				        
				        cell = row.getCell(6);
			        	cell.setCellValue(1);
			        	
				        cell = row.getCell(8);
				        cell.setCellValue(fee4);
				        
				        cell = row.getCell(10);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE4_MEMO")));
				        rowPos = rowPos + 2;
				    }
			    }
			    
			    if ("1".equals(arg0.getParameter("FEE5_CHK"))) {
			        // その他費用5
				    double fee5 = FTCCommonUtil.parseDouble(rentalOrder.getProperty("FEE5"));
				    if (fee5 != 0) {
				    	Row row = sheet.getRow(rowPos + (i*2));
				    	Cell cell = row.getCell(1);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE5_NAME")));
				        
				        cell = row.getCell(6);
			        	cell.setCellValue(1);
			        	
				        cell = row.getCell(8);
				        cell.setCellValue(fee5);
				        
				        cell = row.getCell(10);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE5_MEMO")));
				        rowPos = rowPos + 2;
				    }
			    }
			    
			    if ("1".equals(arg0.getParameter("FEE6_CHK"))) {
			        // その他費用6
				    double fee6 = FTCCommonUtil.parseDouble(rentalOrder.getProperty("FEE6"));
				    if (fee6 != 0) {
				    	Row row = sheet.getRow(rowPos + (i*2));
				    	Cell cell = row.getCell(1);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE6_NAME")));
				        
				        cell = row.getCell(6);
			        	cell.setCellValue(1);
			        	
				        cell = row.getCell(8);
				        cell.setCellValue(fee6);
				        
				        cell = row.getCell(10);
				        cell.setCellValue(FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE6_MEMO")));
				        rowPos = rowPos + 2;
				    }
			    }
		        
		        // 入力日
			    Row row = sheet.getRow(1);
			    Cell cell = row.getCell(10);
		        cell.setCellValue(new Date(System.currentTimeMillis()));
		        
		        // 会社情報の出力
		        row = sheet.getRow(4);
		        cell = row.getCell(11);
		        cell.setCellValue(FTCCommonUtil.nullConv(company.getProperty("COMPANY")));
		        
		        row = sheet.getRow(5);
		        cell = row.getCell(11);
		        cell.setCellValue("登録番号 " + FTCCommonUtil.nullConv(company.getProperty("REGISTRATION_NUMBER")));
		        
		        row = sheet.getRow(6);
		        cell = row.getCell(11);
		        cell.setCellValue("〒 " + FTCCommonUtil.nullConv(company.getProperty("ZIP")));
		        
		        row = sheet.getRow(7);
		        cell = row.getCell(11);
		        cell.setCellValue(FTCCommonUtil.nullConv(company.getProperty("ADDRESS")));
		        
		        row = sheet.getRow(8);
		        cell = row.getCell(11);
		        cell.setCellValue("電話：" + FTCCommonUtil.nullConv(company.getProperty("TEL")));
		        
		        row = sheet.getRow(9);
		        cell = row.getCell(11);
		        cell.setCellValue("FAX：" + FTCCommonUtil.nullConv(company.getProperty("FAX")));
			}
			
	        // 再計算
	        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
	        formulaEvaluator.evaluateAll();
	        
		    // ヘッダー情報構築
		    arg1.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		    arg1.setHeader("Pragma", "no-cache");  
		    arg1.setHeader("Cache-Control", "no-cache"); 
		    arg1.setHeader("Content-Type","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		    arg1.addHeader("Content-Disposition", "inline; filename=\""+ System.currentTimeMillis() + "RentalInvoice.xlsx\""); 
		    
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
