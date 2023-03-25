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
<title>INVENTORY ALL LIST</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCGetInventoryAllList"  method="POST">
<div><input type="button" name="close" value="BACK" onclick="history.back();"></div>
<div>
<input type="hidden" name="page" value="1">
<input type="hidden" name="sort">
</div>
<%
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
out.println("<div id=\"count\">Number of Inventory:" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<table class="table table-bordered table-striped table-hover table-responsive" width="700" align="left">
<tr>
<td nowrap>TYPE</td>
<td nowrap>MAKER</td>
<td nowrap>MODEL</td>
<td nowrap>YEAR</td>
<!--<td nowrap>SERIAL</td>-->
<!--<td nowrap>METER</td>-->
<td nowrap>RANK</td>
<td nowrap>PRICE</td>
<td nowrap>DETAIL</td>
<td nowrap>PHOTO</td>
</tr>
<%
if (listInventoryRecord != null) {
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
		String record = "<tr>";
		record = record + "<td>" + FTCCodeUtil.getTypeEng(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("YEAR") + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("SERIALNO") + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("HOURS") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("CONDITION")  + "</td>";
		record = record + "<td style=\"text-align:right;\">" + FTCCommonUtil.checkEmptyOrZero(FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE")))  + "</td>";
		record = record + "<td><input type=\"button\" value=\"DETAIL\" onClick=\"location.href='FTCDetailInventory?page=1&EDITID=" + inventoryRecord.getKey().getName() + "'\"></td>";

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
</form>
</div>
</body>
</html>