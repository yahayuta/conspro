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
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");

	String msg = (String)request.getSession().getAttribute("MSG");
	request.getSession().setAttribute("MSG", "");
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
		obj.target='_self';
		obj.type.value=arg;
		if (arg==2) {
			if (!confirm('削除してよろしいですか？')) {
				return;
			}
		}
		obj.action='FTCEditOtherInventory';
		obj.submit();
	}
	
	function search(obj) {
		obj.target='_self';
		obj.action='FTCSeachOtherInventoryList';
		obj.submit();
	}
-->
</script>
<title>その他機械一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditOtherInventory"  method="POST">
<div><h1>その他機械一覧</h1></div>
<div id="cond">検索条件</div>
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
	<td>型式</td>
	<td><input type="text" name="NAME" value=""></td>
</tr>
<tr>
	<td>号機</td>
	<td><input type="text" name="SERIALNO" value=""></td>
</tr>
<tr>
	<td><input type="button" name="srch" value="検索" onclick="search(this.form);"></td>
</tr>
</table>
</div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<%
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
out.println("<div id=\"count\">件数:" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<input type="hidden" name="type">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="update" value="その他機械更新" onclick="callServer(this.form,0);">
<input type="button" name="delete" value="その他機械削除" onclick="callServer(this.form,2);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>No</th>
<th nowrap>分類</th>
<th nowrap>メーカー</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
<th nowrap>担当</th>
<th nowrap>顧客名</th>
</tr>
<%
if (listInventoryRecord != null) {
int cnt = 1;
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"EDITID\" value=\""+ inventoryRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + cnt + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SERIALNO") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("ACCOUNT") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELLER") + "</td>";
		record = record + "</tr>";
		out.println(record);
		cnt++;
	}
}
 %>
</table>
</div>
</form>
</div>
</body>
</html>