package gae.ftc.svlt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * 従業員登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistEmployeeSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// パラメータ取得
		Entity employee = new Entity("Employee", req.getParameter("ACCOUNT"));
		employee.setProperty("PASSWORD",req.getParameter("PASSWORD"));
		employee.setProperty("NAME",req.getParameter("NAME"));
		employee.setProperty("AUTH_CODE",req.getParameter("AUTH_CODE"));

        try {
        	// 従業員データを登録する
        	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        	datastore.put(employee);
        } catch (Throwable th) {
	    	throw new ServletException(th);
        }

        resp.sendRedirect("RegResultEmp.jsp");
   }
}
