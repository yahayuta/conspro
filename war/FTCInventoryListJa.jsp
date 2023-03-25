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
<title>在庫一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<!--<script src="js/tinybox.js" type="text/javascript"></script>-->
<!--<script src="js/ftc.js" type="text/javascript"></script>-->
<script type="text/javascript">
<!--
	function callSearchByMaker() {
		location.href='FTCGetInventoryListByType?TYPE=maker&page=2&MANUFACTURER=' + document.main.MANUFACTURER.value;
	}
-->
</script>
</head>
<body>
<form name="main">
<div>
<div>
<select name="MANUFACTURER" size="1" onChange="callSearchByMaker()">
<option value="" selected>メーカー選択</option>
<%= FTCConst.COMBO_MANUFACTURER %>
</select>
&nbsp;
<input type="button" value="油圧ショベル 8t未満" onClick="location.href='FTCGetInventoryListByType?TYPE=1&page=2'">&nbsp;
<input type="button" value="油圧ショベル 10t以上" onClick="location.href='FTCGetInventoryListByType?TYPE=2&page=2'">&nbsp;
<input type="button" value="ブルドーザー" onClick="location.href='FTCGetInventoryListByType?TYPE=3&page=2'">&nbsp;
<input type="button" value="ショベルローダー" onClick="location.href='FTCGetInventoryListByType?TYPE=4&page=2'">&nbsp;
<input type="button" value="タイヤショベル" onClick="location.href='FTCGetInventoryListByType?TYPE=5&page=2'">&nbsp;
<input type="button" value="舗装機械" onClick="location.href='FTCGetInventoryListByType?TYPE=6&page=2'">&nbsp;
<input type="button" value="クレーン" onClick="location.href='FTCGetInventoryListByType?TYPE=7&page=2'">&nbsp;
<input type="button" value="キャリアダンプ" onClick="location.href='FTCGetInventoryListByType?TYPE=8&page=2'">&nbsp;
<input type="button" value="アタッチメント" onClick="location.href='FTCGetInventoryListByType?TYPE=9&page=2'">&nbsp;
<input type="button" value="部品" onClick="location.href='FTCGetInventoryListByType?TYPE=10&page=2'">&nbsp;
<input type="button" value="発電機" onClick="location.href='FTCGetInventoryListByType?TYPE=11&page=2'">&nbsp;
<input type="button" value="エアーコンプレッサー" onClick="location.href='FTCGetInventoryListByType?TYPE=12&page=2'">&nbsp;
<input type="button" value="溶接機" onClick="location.href='FTCGetInventoryListByType?TYPE=13&page=2'">&nbsp;
<input type="button" value="トラック" onClick="location.href='FTCGetInventoryListByType?TYPE=14&page=2'">&nbsp;
<input type="button" value="高所作業車" onClick="location.href='FTCGetInventoryListByType?TYPE=15&page=2'">&nbsp;
<input type="button" value="その他" onClick="location.href='FTCGetInventoryListByType?TYPE=99&page=2'">&nbsp;
<input type="button" value="全在庫一覧" onClick="location.href='FTCGetInventoryAllList?page=2'">
</div>
<div>&nbsp;</div>
<div id="category">
<%
if (request.getParameter("MANUFACTURER") != null && request.getParameter("MANUFACTURER").length() > 0) {
	out.println("現在のカテゴリー：" + request.getParameter("MANUFACTURER"));
} else if (request.getParameter("TYPE") != null && request.getParameter("TYPE").length() > 0 && !"all".equals(request.getParameter("TYPE"))) {
	out.println("現在のカテゴリー：" + FTCCodeUtil.getTypeJa(request.getParameter("TYPE")));
}
%>
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive" width="700" align="left">
<tbody>
<tr>
<td nowrap>サムネイル</td>
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
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
		String record = "<tr>";
		record = record + "<td width=\"112\"><img src=\"FTCGetPicture?EDITID=" + inventoryRecord.getKey().getName() + "\" width=\"112\" height=\"84\"></td>";
		record = record + "<td>" + inventoryRecord.getProperty("MANUFACTURER")  + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME")  + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("YEAR")  + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("SERIALNO")  + "</td>";
//		record = record + "<td>" + inventoryRecord.getProperty("HOURS")  + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("CONDITION")  + "</td>";
		record = record + "<td style=\"text-align:right;\">" + FTCCommonUtil.checkEmptyOrZero(FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE"))) + "</td>";
//		record = record + "<td><a href=\"#\" onclick=\"showDetailInfo('"+ inventoryRecord.getProperty("MANUFACTURER")  + "','"+ inventoryRecord.getProperty("NAME") + "','"+ inventoryRecord.getProperty("OTHER_JA") + "')\">表示</a></td>";
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
</tbody>
</table>
</div>
</div>
</form>
</body>
</html>