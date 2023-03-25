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
 * アカウント編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditEmployeeSvlt extends HttpServlet {

	/** 種別：0 更新画面起動 */
	private static final String TYPE_EDIT = "0";
	/** 種別：1  更新確定 */
	private static final String TYPE_EDIT_COMMIT = "1";
	/** 種別：2  削除 */
	private static final String TYPE_DELETE = "2";

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
		String id = arg0.getParameter("EDITID");
		
		// ACCOUNTが取れない場合エラー
		if (id == null || id.length() == 0) {
			// エラーページへ
			arg1.sendRedirect("Error.jsp");
			return;
		}
		
	    try {
			// マスターチェック実行
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("Employee", id);
		    Entity employee = datastore.get(keyObj);
	
	    	// マスタにない場合エラー
	    	if (employee == null) {
				// エラーページへ
		    	throw new ServletException("処理異常です。データ不正です。");
	    	}

			String type = arg0.getParameter("type");
			
			if (TYPE_EDIT.equals(type)) {
				// 更新ページへ遷移
			    arg0.setAttribute("EmployeeRecord", employee);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateEmployee.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				// 更新実行
				employee.setProperty("PASSWORD",arg0.getParameter("PASSWORD"));
				employee.setProperty("NAME",arg0.getParameter("NAME"));
				employee.setProperty("AUTH_CODE",arg0.getParameter("AUTH_CODE"));
				
				datastore.put(employee);
	        	
				// 一覧画面へ
				arg1.sendRedirect("FTCGetEmployeeAllList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else if (TYPE_DELETE.equals(type)) {
				// アカウント削除実行
				datastore.delete(keyObj);
				// 一覧画面へ
				arg1.sendRedirect("FTCGetEmployeeAllList?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
