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
 * ログイン認証
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCLoginSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String ACCOUNT = arg0.getParameter("ACCOUNT");
		String PASSWORD = arg0.getParameter("PASSWORD");
		if (ACCOUNT == null || ACCOUNT.length() == 0 || PASSWORD == null || PASSWORD.length() == 0) {
			arg1.sendRedirect("LoginErr.jsp");
			return;
		}

	    try {
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key keyObj = KeyFactory.createKey("Employee", ACCOUNT);
		    Entity employee = datastore.get(keyObj);
	
			if (employee == null) {
				arg1.sendRedirect("LoginErr.jsp");
				return;
			}
	
			if (!PASSWORD.equals(employee.getProperty("PASSWORD"))) {
				arg1.sendRedirect("LoginErr.jsp");
				return;
			}
	
			arg0.getSession().setAttribute("ACCOUNT", ACCOUNT);
			arg0.getSession().setAttribute("AUTH_CODE", employee.getProperty("AUTH_CODE"));
			arg0.getSession().setAttribute("NAME", employee.getProperty("NAME"));
			
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher("/menu.jsp");
		    rd.forward(arg0, arg1);
	    } catch (Throwable th) {
			arg1.sendRedirect("LoginErr.jsp");
			return;
	    }
	}
}
