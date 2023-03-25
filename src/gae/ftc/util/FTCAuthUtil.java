package gae.ftc.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FTC権限Util
 * @author yahayuta
 */
public class FTCAuthUtil {

	/**
	 * ログインチェック
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void isLogin(HttpServletRequest request, HttpServletResponse response)throws IOException {
		String account = request.getParameter("ACCOUNT");
		if (account == null || account.length() == 0) {
			response.sendRedirect("LoginErr.jsp");
		}

		if (!account.equals(request.getSession().getAttribute("ACCOUNT"))) {
			response.sendRedirect("LoginErr.jsp");
		}
	}
}
