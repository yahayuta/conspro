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
 * 日報登録
 * @author yahayuta
 */
@SuppressWarnings("serial")
public class FTCRegistDailyReportSvlt extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 日報データを転記
		Entity dailyReport = new Entity("DailyReport", String.valueOf(System.currentTimeMillis()));
		
		dailyReport.setProperty("NAME",req.getParameter("NAME"));
		dailyReport.setProperty("SERIALNO",req.getParameter("SERIALNO"));
		dailyReport.setProperty("MEMO",req.getParameter("MEMO"));
		dailyReport.setProperty("WORK_DATE",req.getParameter("WORK_DATE"));
		dailyReport.setProperty("WORKER",req.getParameter("WORKER"));
		
		dailyReport.setProperty("HOURS",req.getParameter("HOURS"));
		dailyReport.setProperty("CLIENT_CODE",req.getParameter("CLIENT_CODE"));
		dailyReport.setProperty("CLIENT_NAME",req.getParameter("CLIENT_NAME"));

		dailyReport.setProperty("DATA_FLG",req.getParameter("DATA_FLG"));

        try {
        	// DB登録
        	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        	datastore.put(dailyReport);
        } catch (Throwable th) {
	    	throw new ServletException(th);
        }

        req.getSession().setAttribute("MSG", "日報を登録しました");
        resp.sendRedirect("RegistDailyReport.jsp?ACCOUNT=" + req.getParameter("ACCOUNT"));
   }
}
