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
<title>INVENTORY LIST</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<!--<script src="js/tinybox.js" type="text/javascript"></script>-->
<!--<script src="js/ftc.js" type="text/javascript"></script>-->
<script type="text/javascript">
<!--
	function callSearchByMaker() {
		location.href='FTCGetInventoryListByType?TYPE=maker&page=1&MANUFACTURER=' + document.main.MANUFACTURER.value;
	}
-->
</script>
</head>
<body>
<form name="main">
<div>
<div>
<select name="MANUFACTURER" size="1" onChange="callSearchByMaker()">
<option value="" selected>MAKER</option>
<%= FTCConst.COMBO_MANUFACTURER %>
</select>
&nbsp;
<input type="button" value="Mini Excavator" onClick="location.href='FTCGetInventoryListByType?TYPE=1&page=1'">&nbsp;
<input type="button" value="Excavator" onClick="location.href='FTCGetInventoryListByType?TYPE=2&page=1'">&nbsp;
<input type="button" value="Crawler Dozer" onClick="location.href='FTCGetInventoryListByType?TYPE=3&page=1'">&nbsp;
<input type="button" value="Crawler Loader" onClick="location.href='FTCGetInventoryListByType?TYPE=4&page=1'">&nbsp;
<input type="button" value="Wheel Loader" onClick="location.href='FTCGetInventoryListByType?TYPE=5&page=1'">&nbsp;
<input type="button" value="Road Equipment" onClick="location.href='FTCGetInventoryListByType?TYPE=6&page=1'">&nbsp;
<input type="button" value="Crane" onClick="location.href='FTCGetInventoryListByType?TYPE=7&page=1'">&nbsp;
<input type="button" value="Crawler Carrier" onClick="location.href='FTCGetInventoryListByType?TYPE=8&page=1'">&nbsp;
<input type="button" value="Attachment" onClick="location.href='FTCGetInventoryListByType?TYPE=9&page=1'">&nbsp;
<input type="button" value="Parts" onClick="location.href='FTCGetInventoryListByType?TYPE=10&page=1'">&nbsp;
<input type="button" value="Generator" onClick="location.href='FTCGetInventoryListByType?TYPE=11&page=1'">&nbsp;
<input type="button" value="Air Compressor" onClick="location.href='FTCGetInventoryListByType?TYPE=12&page=1'">&nbsp;
<input type="button" value="Welder" onClick="location.href='FTCGetInventoryListByType?TYPE=13&page=1'">&nbsp;
<input type="button" value="Truck" onClick="location.href='FTCGetInventoryListByType?TYPE=14&page=1'">&nbsp;
<input type="button" value="Aerial Platform" onClick="location.href='FTCGetInventoryListByType?TYPE=15&page=1'">&nbsp;
<input type="button" value="Other" onClick="location.href='FTCGetInventoryListByType?TYPE=99&page=1'">&nbsp;
<input type="button" value="ALL LIST" onClick="location.href='FTCGetInventoryAllList?page=1'">
</div>
<div>&nbsp;</div>
<div id="category">
<%
if (request.getParameter("MANUFACTURER") != null && request.getParameter("MANUFACTURER").length() > 0) {
	out.println("Now Showing:" + request.getParameter("MANUFACTURER"));
} else if (request.getParameter("TYPE") != null && request.getParameter("TYPE").length() > 0 && !"all".equals(request.getParameter("TYPE"))) {
	out.println("Now Showing:" + FTCCodeUtil.getTypeEng(request.getParameter("TYPE")));
}
%>
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive" width="700" align="left">
<tr>
<td nowrap>THUMBNAIL</td>
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
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
		String record = "<tr>";
		record = record + "<td width=\"112\"><img src=\"FTCGetPicture?EDITID=" + inventoryRecord.getKey().getName() + "\" width=\"112\" height=\"84\"></td>";
		record = record + "<td>" + inventoryRecord.getProperty("MANUFACTURER")  + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("YEAR") + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("SERIALNO") + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("HOURS") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("CONDITION") + "</td>";
		record = record + "<td style=\"text-align:right;\">" + FTCCommonUtil.checkEmptyOrZero(FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE"))) + "</td>";
//		record = record + "<td><a href=\"#\" onclick=\"showDetailInfoEn('"+ inventoryRecord.getProperty("MANUFACTURER()  + "','"+ inventoryRecord.getProperty("NAME") + "','"+ inventoryRecord.getProperty("OTHER") + "')\">SHOW</a></td>";
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
</div>
</form>
</body>
</html>