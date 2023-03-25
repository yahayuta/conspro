<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	// ユーザーデータ取得
	Entity employeeRecord = (Entity)request.getAttribute("EmployeeRecord");

	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>ユーザー情報更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
	  if (obj.PASSWORD.value != obj.PASSWORD2.value) {
			alert('パスワードが一致しません。再度入力してください');
			return;
		}
		obj.submit();
	}

	// 初期化
	function initData() {
		document.empForm.AUTH_CODE.value = document.empForm.LOADAUTH_CODE.value;
	}
-->
</script>
</head>
<body onload="initData()">
<div>
<form action="FTCEditEmployee?ACCOUNT=<%= account %>" name="empForm" method="POST">
<div><h1>ユーザー情報更新</h1></div>
<div>
<input type="button" value="戻る" onclick="history.back();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>アカウント</td>
<td><%= employeeRecord.getKey().getName() %></td>
</tr>
<tr>
<td>パスワード</td>
<td><input type="PASSWORD" name="PASSWORD" value="<%= employeeRecord.getProperty("PASSWORD") %>"></td>
</tr>
<tr>
<td>パスワード（確認用）</td>
<td><input type="PASSWORD" name="PASSWORD2" value="<%= employeeRecord.getProperty("PASSWORD") %>"></td>
</tr>
<tr>
<td>ユーザー名</td>
<td><input type="text" name="NAME" value="<%= employeeRecord.getProperty("NAME") %>"></td>
</tr>
<tr>
<td>権限</td>
<td>
<input type="hidden" name="LOADAUTH_CODE" value="<%= employeeRecord.getProperty("AUTH_CODE") %>">
<select name="AUTH_CODE">
<%= FTCConst.COMBO_AUTH_CODE %>
</select>
</td>
</tr>
</table>
<div>
<input type="hidden" name="EDITID" value="<%= employeeRecord.getKey().getName() %>">
<input type="hidden" name="type" value="1">
<input type="button" value="更新" onclick="callServer(this.form)">
</div>
</div>
</form>
</div>
</body>
</html>