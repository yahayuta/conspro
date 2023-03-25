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
<title>レンタル注文引合一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	// 引合編集
	function editPreOrder(obj,type) {
		obj.target='_blank';
		obj.action='FTCEditPreRentalOrder?ACCOUNT=<%= account %>&type=' + type;
		obj.submit();
	}
-->
</script>
</head>
<body>
<form action="FTCEditInventory"  method="POST">
<div><h1>レンタル注文引合一覧</h1></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<input type="button" name="edit" value="編集" onclick="editPreOrder(this.form,0)">
<input type="button" name="delete" value="削除" onclick="editPreOrder(this.form,2);">
</div>
<%
List rentalOrderList = (List)request.getAttribute("PreRentalOrderRecordList");
if (rentalOrderList != null) {
out.println("<div id=\"count\">件数:" + rentalOrderList.size() + "</div>");
}
%>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap class="th">選択</th>
<th nowrap>No</th>
<th nowrap>レンタル在庫ID</th>
<th nowrap>レンタル開始希望日</th>
<th nowrap>顧客コード</th>
<th nowrap>顧客名</th>
<th nowrap>顧客担当者</th>
<th nowrap>顧客TEL</th>
<th nowrap>備考欄</th>
</tr>
<%
if (rentalOrderList != null) {
int cnt = 1;
	for (Iterator iterator = rentalOrderList.iterator(); iterator.hasNext();) {
		Entity rentalOrder = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap class=\"td\"><input type=\"radio\" name=\"PREID\" value=\""+ rentalOrder.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + cnt + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("RENTAL_INVENTORY_ID") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("START_DATE") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("CLIENT_CODE") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("CLIENT_NAME") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("CLIENT_REP") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("CLIENT_TEL") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("MEMO") + "</td>";
		record = record + "</tr>";
		out.println(record);
		cnt++;
	}
}
 %>
</table>
</div>
</form>
</body>
</html>