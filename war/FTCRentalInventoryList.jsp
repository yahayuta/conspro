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
<title>レンタル機一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callSearchByMaker() {
		location.href='FTCGetRentalInventoryList?TYPE=maker&MANUFACTURER=' + document.main.MANUFACTURER.value;
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
<input type="button" value="油圧ショベル 8t未満" onClick="location.href='FTCGetRentalInventoryList?TYPE=1'">&nbsp;
<input type="button" value="油圧ショベル 10t以上" onClick="location.href='FTCGetRentalInventoryList?TYPE=2'">&nbsp;
<input type="button" value="ブルドーザー" onClick="location.href='FTCGetRentalInventoryList?TYPE=3'">&nbsp;
<input type="button" value="ショベルローダー" onClick="location.href='FTCGetRentalInventoryList?TYPE=4'">&nbsp;
<input type="button" value="タイヤショベル" onClick="location.href='FTCGetRentalInventoryList?TYPE=5'">&nbsp;
<input type="button" value="舗装機械" onClick="location.href='FTCGetRentalInventoryList?TYPE=6'">&nbsp;
<input type="button" value="クレーン" onClick="location.href='FTCGetRentalInventoryList?TYPE=7'">&nbsp;
<input type="button" value="キャリアダンプ" onClick="location.href='FTCGetRentalInventoryList?TYPE=8'">&nbsp;
<input type="button" value="アタッチメント" onClick="location.href='FTCGetRentalInventoryList?TYPE=9'">&nbsp;
<input type="button" value="部品" onClick="location.href='FTCGetRentalInventoryList?TYPE=10'">&nbsp;
<input type="button" value="発電機" onClick="location.href='FTCGetRentalInventoryList?TYPE=11'">&nbsp;
<input type="button" value="エアーコンプレッサー" onClick="location.href='FTCGetRentalInventoryList?TYPE=12'">&nbsp;
<input type="button" value="溶接機" onClick="location.href='FTCGetRentalInventoryList?TYPE=13'">&nbsp;
<input type="button" value="トラック" onClick="location.href='FTCGetRentalInventoryList?TYPE=14'">&nbsp;
<input type="button" value="高所作業車" onClick="location.href='FTCGetRentalInventoryList?TYPE=15'">&nbsp;
<input type="button" value="その他" onClick="location.href='FTCGetRentalInventoryList?TYPE=99'">&nbsp;
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
<table class="table table-bordered table-striped table-hover table-responsive" width="680" align="left">
<tbody>
<tr>
<td nowrap>サムネイル</td>
<td nowrap>分類</td>
<td nowrap>メーカー</td>
<td nowrap>型式</td>
<td nowrap>重量（㎏）</td>
<td nowrap>サイズ（㎥）</td>
<!-- <td nowrap>年式</td> -->
<td nowrap>詳細</td>
<!-- <td nowrap>出庫状況</td> -->
<!-- <td nowrap>写真</td> -->
</tr>
<%
List listInventoryRecord = (List)request.getAttribute("RentalInventoryRecordList");
if (listInventoryRecord != null) {
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td width=\"112\"><img src=\"FTCGetPictureRental?EDITID=" + inventoryRecord.getKey().getName() + "\" width=\"112\" height=\"84\"></td>";
		record = record + "<td>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME")  + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("WEIGHT")  + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("SIZE")  + "</td>";
		//record = record + "<td align=\"right\">" + inventoryRecord.getProperty("YEAR")  + "</td>";
		record = record + "<td><input type=\"button\" value=\"詳細\" onClick=\"location.href='FTCDetailRentalInventory?page=3&EDITID=" + inventoryRecord.getKey().getName() + "'\"></td>";
		//record = record + "<td>" + FTCCodeUtil.getRental(inventoryRecord.getProperty("STATUS")) + "</td>";
	
		//if (inventoryRecord.getProperty("PIC_URL") != null && FTCCommonUtil.nullConv(inventoryRecord.getProperty("PIC_URL")).length() > 0) {
		//	record = record + "<td><a href=\"" + inventoryRecord.getProperty("PIC_URL") + "\" target=\"_blank\"><img src=\"img/piclink.gif\"></a></td>";
		//} else {
		//	record = record + "<td>&nbsp;</td>";
		//}
		
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
