<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	Entity inventoryRecord = (Entity)request.getAttribute("InventoryRecord");
	String type = (String)request.getAttribute("TYPE");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><%= inventoryRecord.getProperty("MANUFACTURER") %> <%= inventoryRecord.getProperty("NAME") %></title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script src="js/ftc.js" type="text/javascript"></script>
</head>
<body>
<div>
<div><input type="button" name="close" value="戻る" onclick="history.back();"></div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive" width="665" align="left">
<tr>
<td width="664" colspan="2"><img src="FTCGetPictureRental?EDITID=<%= inventoryRecord.getKey().getName() %>"width="664" height="498"></td>
</tr>
<tr>
<td nowrap>分類</td>
<td><%= FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) %></td>
</tr>
<tr>
<td nowrap>メーカー</td>
<td><%= inventoryRecord.getProperty("MANUFACTURER") %></td>
</tr>
<tr>
<td nowrap>型式</td>
<td><%= inventoryRecord.getProperty("NAME") %></td>
</tr>
<tr>
<td nowrap>重量（㎏）</td>
<td><%= inventoryRecord.getProperty("WEIGHT") %></td>
</tr>
<tr>
<td nowrap>サイズ（㎥）</td>
<td><%= inventoryRecord.getProperty("SIZE") %></td>
</tr>
<tr>
<td nowrap>年式</td>
<td><%= inventoryRecord.getProperty("YEAR") %></td>
</tr>
<tr>
<td nowrap>詳細</td>
<td><%= inventoryRecord.getProperty("OTHER_JA") %></td>
</tr>
<tr>
<td nowrap>出庫状況</td>
<td><%= FTCCodeUtil.getRental(inventoryRecord.getProperty("STATUS")) %></td>
</tr>
<tr>
<td nowrap>写真</td>
<td>
<%
if (inventoryRecord.getProperty("PIC_URL") != null && FTCCommonUtil.nullConv(inventoryRecord.getProperty("PIC_URL")).length() > 0) {
%>
<a href="<%= inventoryRecord.getProperty("PIC_URL") %>" target="_blank"><img src="img/piclink.gif"></a>
<%
} else {
%>
&nbsp;
<%
}
%>
</td>
</tr>
</table>
</div>
</div>
</body>
</html>