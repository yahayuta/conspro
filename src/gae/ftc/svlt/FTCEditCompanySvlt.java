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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * アカウント編集
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCEditCompanySvlt extends HttpServlet {

	/** 種別：1  更新確定 */
	private static final String TYPE_EDIT_COMMIT = "1";

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
		String id = arg0.getParameter("EDITID");
		
		// IDが取れない場合エラー
		if (id == null || id.length() == 0) {
			// エラーページへ
			arg1.sendRedirect("Error.jsp");
			return;
		}
		
		Entity company = null;
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    try {
			// マスターチェック実行
		    Key keyObj = KeyFactory.createKey("Company", id);
		    company = datastore.get(keyObj);
	    } catch ( EntityNotFoundException th ) {
    		company = new Entity("Company", id);
			datastore.put(company);
		}
	    
    	// マスタにない場合は新しく作成
    	if (company == null) {
    		company = new Entity("Company", id);
			datastore.put(company);
    	}

		String type = arg0.getParameter("type");
		
		if (TYPE_EDIT_COMMIT.equals(type)) {
			
			// 更新実行
			company.setProperty("SYSTEM_NAME",arg0.getParameter("SYSTEM_NAME"));
			company.setProperty("COMPANY",arg0.getParameter("COMPANY"));
			company.setProperty("ZIP",arg0.getParameter("ZIP"));
			company.setProperty("ADDRESS",arg0.getParameter("ADDRESS"));
			company.setProperty("TEL",arg0.getParameter("TEL"));
			company.setProperty("FAX",arg0.getParameter("FAX"));
			company.setProperty("REGISTRATION_NUMBER",arg0.getParameter("REGISTRATION_NUMBER"));
			company.setProperty("COMPANY_EN",arg0.getParameter("COMPANY_EN"));
			company.setProperty("ADDRESS_EN",arg0.getParameter("ADDRESS_EN"));
			company.setProperty("TEL_EN",arg0.getParameter("TEL_EN"));
			company.setProperty("FAX_EN",arg0.getParameter("FAX_EN"));

			datastore.put(company);
			arg0.getSession().setAttribute("MSG", "会社情報を更新しました");
		}
		
		// 更新ページへ遷移
	    arg0.setAttribute("Company", company);
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher("/UpdateCompany.jsp");
	    rd.forward(arg0, arg1);
	}
}
