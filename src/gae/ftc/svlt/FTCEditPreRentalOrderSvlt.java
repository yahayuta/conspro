package gae.ftc.svlt;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * レンタル注文引合編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditPreRentalOrderSvlt extends HttpServlet {

	/** 種別：0 更新画面起動 */
	private static final String TYPE_EDIT = "0";
	/** 種別：1  更新確定 */
	private static final String TYPE_EDIT_COMMIT = "1";
	/** 種別：2  削除 */
	private static final String TYPE_DELETE = "2";

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {

		String id = arg0.getParameter("PREID");
		
		// idが取れない場合エラー
		if (id == null || id.length() == 0) {
			// エラーページへ
			arg1.sendRedirect("Error.jsp");
			return;
		}
		
	    try {
			// マスターチェック実行
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("PreRentalOrder", id);
		    Entity preRentalOrder = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (preRentalOrder == null) {
		    	throw new ServletException("処理異常です。レンタル引合注文データ不正です。");
	    	}
	    	
			String type = arg0.getParameter("type");
			
			// 更新
			if (TYPE_EDIT.equals(type)) {								
				// 更新ページへ遷移			
			    arg0.setAttribute("PreRentalOrder", preRentalOrder);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdatePreRentalOrder.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				
				// データ転記
				preRentalOrder.setProperty("START_DATE", arg0.getParameter("START_DATE"));
				preRentalOrder.setProperty("CLIENT_CODE", arg0.getParameter("CLIENT_CODE"));
				preRentalOrder.setProperty("CLIENT_NAME", arg0.getParameter("CLIENT_NAME"));
				preRentalOrder.setProperty("CLIENT_REP", arg0.getParameter("CLIENT_REP"));
				preRentalOrder.setProperty("CLIENT_TEL", arg0.getParameter("CLIENT_TEL"));
				preRentalOrder.setProperty("MEMO", arg0.getParameter("MEMO"));
				datastore.put(preRentalOrder);

				// 更新ページへ遷移			
			    arg0.setAttribute("PreRentalOrder", preRentalOrder);
			    
	            arg0.getSession().setAttribute("MSG", "更新しました");
				arg1.sendRedirect("FTCEditPreRentalOrder?type=0&ACCOUNT=" + arg0.getParameter("ACCOUNT") + "&PREID=" + id);		
			} else if (TYPE_DELETE.equals(type)) {
				// 削除
				
				// 削除フラグを立てるだけ
				preRentalOrder.setProperty("DATA_FLG","1");
	        	datastore.put(preRentalOrder);
	        	// 画面出力
	        	arg1.setContentType("text/html");
	        	arg1.setCharacterEncoding("Shift_JIS");
	        	arg1.getWriter().println("選択したレンタル注文引合を削除しました");
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
