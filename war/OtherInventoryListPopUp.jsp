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
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script type="text/javascript">
<!--
function selectData(obj) {
	window.opener.document.getElementById(obj.name.value).value = window.document.getElementById('NAME' + obj.CNT.value).value;
	window.opener.document.getElementById(obj.serialno.value).value = window.document.getElementById('SERIALNO' + obj.CNT.value).value;
	window.opener.document.getElementById(obj.hours.value).value = window.document.getElementById('HOURS' + obj.CNT.value).value;
	window.opener.document.getElementById(obj.clientcode.value).value = window.document.getElementById('SELLER_CODE' + obj.CNT.value).value;
	window.opener.document.getElementById(obj.clientname.value).value = window.document.getElementById('SELLER' + obj.CNT.value).value;
	window.opener.document.getElementById('DATA_FLG').value = 2;
	window.close();
}
-->
</script>
<title>その他機械一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCSeachOtherInventoryList" name="cloesedListForm" method="POST">
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
	<td>
		<input type="submit" name="srch" value="検索">
		<input type="hidden" name="SEARCH_TYPE" value="0">
	</td>
</tr>
</table>
</div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<%
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
out.println("<div id=\"count\">件数:" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<input type="hidden" name="SEARCH_TYPE" value="0">
<input type="hidden" name="name" value="<%= request.getParameter("name") %>">
<input type="hidden" name="serialno" value="<%= request.getParameter("serialno") %>">
<input type="hidden" name="hours" value="<%= request.getParameter("hours") %>">
<input type="hidden" name="clientcode" value="<%= request.getParameter("clientcode") %>">
<input type="hidden" name="clientname" value="<%= request.getParameter("clientname") %>">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="detail" value="選択" onclick="selectData(this.form,4);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>分類</th>
<th nowrap>メーカー</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
<th nowrap>年式</th>
<th nowrap>稼働時間</th>
<th nowrap>顧客コード</th>
<th nowrap>顧客名</th>
</tr>
<%
if (listInventoryRecord != null) {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
int cnt = 1;
int index = 0;
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"CNT\" value=\""+ index +"\"></td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME") + "<input type=\"hidden\" id=\"NAME" + index + "\" value=\""+ inventoryRecord.getProperty("NAME") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SERIALNO") + "<input type=\"hidden\" id=\"SERIALNO" + index + "\" value=\""+ inventoryRecord.getProperty("SERIALNO") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("YEAR") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("HOURS") + "<input type=\"hidden\" id=\"HOURS" + index + "\" value=\""+ inventoryRecord.getProperty("HOURS") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELLER_CODE") + "<input type=\"hidden\" id=\"SELLER_CODE" + index + "\" value=\""+ inventoryRecord.getProperty("SELLER_CODE") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELLER") + "<input type=\"hidden\" id=\"SELLER" + index + "\" value=\""+ inventoryRecord.getProperty("SELLER") +"\"></td>";
		record = record + "</tr>";
		out.println(record);
		cnt++;
		index++;
	}
}
%>
</table>
</div>
<div>
<input type="button" name="detail2" value="選択" onclick="selectData(this.form,4);">
</div>
</form>
</div>
</body>
</html>