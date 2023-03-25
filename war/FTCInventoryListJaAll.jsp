<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>全在庫一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<form action="FTCGetInventoryAllList"  method="POST">
<div>
<div><input type="button" name="close" value="戻る" onclick="history.back();"></div>
<div>
<input type="hidden" name="page" value="2">
<input type="hidden" name="sort">
</div>
<%
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
out.println("<div id=\"count\">在庫登録数：" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<table class="table table-bordered table-striped table-hover table-responsive" width="700" align="left">
<tr>
<td nowrap>分類</td>
<td nowrap>メーカー</td>
<td nowrap>型式</td>
<td nowrap>年式</td>
<!--<td nowrap>号機</td>-->
<!--<td nowrap>メーター</td>-->
<td nowrap>程度</td>
<td nowrap>価格</td>
<td nowrap>詳細</td>
<td nowrap>写真</td>
</tr>
<%
if (listInventoryRecord != null) {
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
		String record = "<tr>";
		record = record + "<td>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("YEAR") + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("SERIALNO") + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("HOURS") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("CONDITION") + "</td>";
		record = record + "<td style=\"text-align:right;\">" + FTCCommonUtil.checkEmptyOrZero(FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE")))  + "</td>";
		record = record + "<td><input type=\"button\" value=\"詳細\" onClick=\"location.href='FTCDetailInventory?page=2&EDITID=" + inventoryRecord.getKey().getName() + "'\"></td>";

		if (inventoryRecord.getProperty("PIC_URL") != null && FTCCommonUtil.nullConv(inventoryRecord.getProperty("PIC_URL")).length() > 0) {
			record = record + "<td><a href=\"" + inventoryRecord.getProperty("PIC_URL") + "\" target=\"_blank\"><img src=\"img/piclink.gif\"></a></td>";
		} else {
			record = record + "<td>&nbsp;</td>";
		}

	 	record = record + "</tr>";
	  	out.println(record);
	}
}
%>
</table>
</div>
</div>
</form>
</body>
</html>