<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script type="text/javascript">
<!--
	function callServer(obj,arg) {
		obj.type.value=arg;
		if (arg==2) {
			if (!confirm('削除してよろしいですか？')) {
				return;
			}
		}
		obj.submit();
	}
-->
</script>
<title>ユーザー一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditEmployee"  method="POST">
<div><h1>ユーザー一覧</h1></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<input type="hidden" name="type">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="update" value="ユーザー情報更新" onclick="callServer(this.form,0);">
<input type="button" name="delete" value="ユーザー削除" onclick="callServer(this.form,2);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>アカウント</th>
<th nowrap>ユーザー名</tthd>
<th nowrap>権限</th>
</tr>
<%
List listEmployeeRecord = (List)request.getAttribute("EmployeeRecordList");
if (listEmployeeRecord != null) {
	for (Iterator iterator = listEmployeeRecord.iterator(); iterator.hasNext();) {
		Entity employeeRecord = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td><input type=\"radio\" name=\"EDITID\" value=\""+ employeeRecord.getKey().getName() +"\"></td>";
		record = record + "<td>" + employeeRecord.getKey().getName()  + "</td>";
		record = record + "<td>" + employeeRecord.getProperty("NAME")  + "</td>";
		record = record + "<td>" + FTCCodeUtil.getAuthName(employeeRecord.getProperty("AUTH_CODE").toString())  + "</td>";
	  record = record + "</tr>";
	  out.println(record);
	}
}
%>
</table>
</div>
</form>
</div>
</body>
</html>