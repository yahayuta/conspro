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
 * 顧客編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditClientSvlt extends HttpServlet {

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
		    Key keyObj = KeyFactory.createKey("Client", id);
		    Entity clientRecord = datastore.get(keyObj);
		    
	    	// マスタにない場合エラー
	    	if (clientRecord == null) {
				// エラーページへ
		    	throw new ServletException("処理異常です。データ不正です。");
	    	}
		
			String type = arg0.getParameter("type");
			
			// 更新
			if (TYPE_EDIT.equals(type)) {
				// 更新ページへ遷移
			    arg0.setAttribute("ClientRecord", clientRecord);
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateClient.jsp");
			    rd.forward(arg0, arg1);
			} else if (TYPE_EDIT_COMMIT.equals(type)) {
				// 更新実行
				clientRecord.setProperty("ADDRESS", arg0.getParameter("ADDRESS"));
				clientRecord.setProperty("ZIP", arg0.getParameter("ZIP"));
				clientRecord.setProperty("TEL", arg0.getParameter("TEL"));
				clientRecord.setProperty("FAX", arg0.getParameter("FAX"));
				clientRecord.setProperty("MAIL", arg0.getParameter("MAIL"));
				clientRecord.setProperty("COMPANY", arg0.getParameter("COMPANY"));
				clientRecord.setProperty("OFFICE", arg0.getParameter("OFFICE"));
				clientRecord.setProperty("NAME", arg0.getParameter("NAME"));
				clientRecord.setProperty("NAME2", arg0.getParameter("NAME2"));
				clientRecord.setProperty("NAME3", arg0.getParameter("NAME3"));
				clientRecord.setProperty("NAME4", arg0.getParameter("NAME4"));
				clientRecord.setProperty("NAME5", arg0.getParameter("NAME5"));
				clientRecord.setProperty("COMMENT", arg0.getParameter("COMMENT"));
				clientRecord.setProperty("PREFIX", arg0.getParameter("PREFIX"));
				arg0.getSession().setAttribute("MSG", "選択した顧客を更新しました");
				datastore.put(clientRecord);
	        	// 再検索してもとに戻る
				arg1.sendRedirect("FTCEditClient?ACCOUNT=" + arg0.getParameter("ACCOUNT") + "&EDITID=" + id + "&type=" + TYPE_EDIT);
			} else if (TYPE_DELETE.equals(type)) {			
				// 顧客削除実行
				datastore.delete(keyObj);
				arg0.getSession().setAttribute("MSG", "選択した顧客を削除しました");
				// 一覧画面へ
				arg1.sendRedirect("ClientList.jsp?ACCOUNT=" + arg0.getParameter("ACCOUNT"));
			} else {
				// エラーページへ
				arg1.sendRedirect("Error.jsp");
			}
	    } catch ( Throwable th ) {
	    	throw new ServletException(th);
		}
	}
}
