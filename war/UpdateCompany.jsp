<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	// 顧客データ取得
	Entity company = (Entity)request.getAttribute("Company");

	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");

	String msg = (String)request.getSession().getAttribute("MSG");
	request.getSession().setAttribute("MSG", "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>会社情報更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
		obj.submit();
	}
-->
</script>
</head>
<body>
<div>
<form action="FTCEditCompany?ACCOUNT=<%= account %>" name="companyForm" method="POST">
<div><h1>会社情報更新</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>システム名</td>
<td><input type="text" name="SYSTEM_NAME" value="<%= company.getProperty("SYSTEM_NAME") %>" size="100"></td>
</tr>
<tr>
<td>会社名</td>
<td><input type="text" name="COMPANY" value="<%= company.getProperty("COMPANY") %>" size="100"></td>
</tr>
<tr>
<td>郵便番号</td>
<td><input type="text" name="ZIP" value="<%= company.getProperty("ZIP") %>"></td>
</tr>
<tr>
<td>住所</td>
<td><input type="text" name="ADDRESS" value="<%= company.getProperty("ADDRESS") %>" size="100"></td>
</tr>
<tr>
<td>電話番号</td>
<td><input type="text" name="TEL" value="<%= company.getProperty("TEL") %>"></td>
</tr>
<tr>
<td>FAX番号</td>
<td><input type="text" name="FAX" value="<%= company.getProperty("FAX") %>"></td>
</tr>
<tr>
<td>登録番号</td>
<td><input type="text" name="REGISTRATION_NUMBER" value="<%= company.getProperty("REGISTRATION_NUMBER") %>"></td>
</tr>
<tr>
<td>会社名（英語）</td>
<td><input type="text" name="COMPANY_EN" value="<%= company.getProperty("COMPANY_EN") %>" size="100"></td>
</tr>
<tr>
<td>住所（英語）</td>
<td><input type="text" name="ADDRESS_EN" value="<%= company.getProperty("ADDRESS_EN") %>" size="100"></td>
</tr>
<tr>
<td>電話番号（英語）</td>
<td><input type="text" name="TEL_EN" value="<%= company.getProperty("TEL_EN") %>"></td>
</tr>
<tr>
<td>FAX番号（英語）</td>
<td><input type="text" name="FAX_EN" value="<%= company.getProperty("FAX_EN") %>"></td>
</tr>
</table>
<div>
<input type="hidden" name="type" value="1">
<input type="hidden" name="EDITID" value="<%= company.getKey().getName() %>">
<input type="button" value="更新" onclick="callServer(this.form)">
</div>
</div>
</form>
</div>
</body>
</html>