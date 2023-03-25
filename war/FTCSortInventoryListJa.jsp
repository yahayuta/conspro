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
<title>最新在庫情報</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script src="js/ftc.js" type="text/javascript"></script>
</head>
<body>
<div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive" width="640">
<tr>
<%
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
	int count = 1;
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		if (count > 8) {
			break;
		}
		if (count == 5) {
				  out.println("</tr><tr>");
		}
		Entity inventoryRecord = (Entity) iterator.next();
		String record = "<td width=\"160\"><a href=\"javascript:void(0)\" onclick=\"loadDetailJa(" + inventoryRecord.getKey().getName() + ")\"><img src=\"FTCGetPicture?EDITID=" + inventoryRecord.getKey().getName() + "\" width=\"160\" height=\"120\" title=\"" + inventoryRecord.getProperty("OTHER_JA") + "\"><br>" + inventoryRecord.getProperty("MANUFACTURER") + "<br>" + inventoryRecord.getProperty("NAME") + "</a></td>";
	  	out.println(record);
		count++;
	}
}
%>
</tr>
</table>
</div>
</div>
</body>
</html>