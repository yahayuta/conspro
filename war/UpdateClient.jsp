<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	// 顧客データ取得
	Entity clientRecord = (Entity)request.getAttribute("ClientRecord");

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
<title>顧客情報更新</title>
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
<form action="FTCEditClient?ACCOUNT=<%= account %>" name="clientForm" method="POST">
<div><h1>顧客情報更新</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div>
<input type="button" value="戻る" onclick="location.href='ClientList.jsp?ACCOUNT=<%=account %>'">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>接頭辞</td>
<td><input type="text" name="PREFIX" value="<%= clientRecord.getProperty("PREFIX") %>"></td>
</tr>
<tr>
<td>顧客コード</td>
<td><%= clientRecord.getKey().getName() %></td>
</tr>
<tr>
<td>業種</td>
<td><%= FTCCodeUtil.getClientType(clientRecord.getProperty("CLIENT_TYPE")) %></td>
</tr>
<tr>
<td>与信管理</td>
<td><%= FTCCodeUtil.getCredit(clientRecord.getProperty("CREDIT")) %></td>
</tr>
<tr>
<td>国番号</td>
<td><%= clientRecord.getProperty("COUNTRY") %></td>
</tr>
<tr>
<td>顧客番号</td>
<td><%= clientRecord.getProperty("SEQ") %></td>
</tr>
<tr>
<td>住所</td>
<td><input type="text" name="ADDRESS" value="<%= clientRecord.getProperty("ADDRESS") %>" size="100"></td>
</tr>
<tr>
<td>郵便番号</td>
<td><input type="text" name="ZIP" value="<%= clientRecord.getProperty("ZIP") %>"></td>
</tr>
<tr>
<td>電話番号</td>
<td><input type="text" name="TEL" value="<%= clientRecord.getProperty("TEL") %>"></td>
</tr>
<tr>
<td>FAX番号</td>
<td><input type="text" name="FAX" value="<%= clientRecord.getProperty("FAX") %>"></td>
</tr>
<tr>
<td>メール</td>
<td><input type="text" name="MAIL" value="<%= clientRecord.getProperty("MAIL") %>"></td>
</tr>
<tr>
<td>会社名</td>
<td><input type="text" name="COMPANY" value="<%= clientRecord.getProperty("COMPANY") %>" size="100"></td>
</tr>
<tr>
<td>支店営業所名</td>
<td><input type="text" name="OFFICE" value="<%= clientRecord.getProperty("OFFICE") %>" size="100"></td>
</tr>
<tr>
<td>担当者1</td>
<td><input type="text" name="NAME" value="<%= clientRecord.getProperty("NAME") %>" size="100"></td>
</tr>
<tr>
<td>担当者2</td>
<td><input type="text" name="NAME2" value="<%= FTCCommonUtil.nullConv(clientRecord.getProperty("NAME2")) %>" size="100"></td>
</tr>
<tr>
<td>担当者3</td>
<td><input type="text" name="NAME3" value="<%= FTCCommonUtil.nullConv(clientRecord.getProperty("NAME3")) %>" size="100"></td>
</tr>
<tr>
<td>担当者4</td>
<td><input type="text" name="NAME4" value="<%= FTCCommonUtil.nullConv(clientRecord.getProperty("NAME4")) %>" size="100"></td>
</tr>
<tr>
<td>担当者5</td>
<td><input type="text" name="NAME5" value="<%= FTCCommonUtil.nullConv(clientRecord.getProperty("NAME5")) %>" size="100"></td>
</tr>
<tr>
<td>備考</td>
<td><textarea name="COMMENT" cols="50" rows="5" maxlength="256"><%= clientRecord.getProperty("COMMENT") %></textarea></td>
</tr>
</table>
<div>
<input type="hidden" name="EDITID" value="<%= clientRecord.getKey().getName() %>">
<input type="hidden" name="type" value="1">
<input type="button" value="更新" onclick="callServer(this.form)">
</div>
</div>
</form>
</div>
</body>
</html>