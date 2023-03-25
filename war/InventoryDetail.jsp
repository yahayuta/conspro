<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	// 在庫データ取得
	Entity inventoryRecord = (Entity)request.getAttribute("InventoryRecord");

	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");
	String history = request.getParameter("history");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<%
if (!"1".equals(history)) {
%>
<title>在庫詳細</title>
<%
} else {
%>
<title>整備履歴</title>
<%
}
%>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form>
<%
if (!"1".equals(history)) {
%>
<div><h1>在庫詳細</h1></div>
<div>&nbsp;</div>
<div>
<%
//サービス・事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" value="注文書出力" onclick="location.href='FTCLoadExcelOrderSheet?EDITID=<%= inventoryRecord.getKey().getName() %>'">
<input type="button" value="請求書出力" onclick="location.href='FTCLoadExcelJpInvoice?EDITID=<%= inventoryRecord.getKey().getName() %>'">
<input type="button" value="Proforma Invoice出力" onclick="location.href='FTCLoadExcelProformaInvoice?EDITID=<%= inventoryRecord.getKey().getName() %>'">
<%
}
%>
<input type="button" value="戻る" onclick="history.back();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>サムネイル</td>
<td><img src="FTCGetPicture?EDITID=<%= inventoryRecord.getKey().getName() %>"width="160" height="120"></td>
</tr>
<tr>
<td>分類</td>
<td><%= FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) %></td>
</tr>
<tr>
<td>型式/MODEL</td>
<td><%= inventoryRecord.getProperty("NAME") %></td>
</tr>
<tr>
<td>メーカー/MANUFACTURER</td>
<td><%= inventoryRecord.getProperty("MANUFACTURER") %></td>
</tr>
<tr>
<td>年式/YEAR</td>
<td><%= inventoryRecord.getProperty("YEAR") %></td>
</tr>
<tr>
<td>号機/SERIAL</td>
<td><%= inventoryRecord.getProperty("SERIALNO") %></td>
</tr>
<tr>
<td>稼働時間/HOUR METER</td>
<td align="right"><%= inventoryRecord.getProperty("HOURS") %></td>
</tr>
<tr>
<td>DESCRIPTION</td>
<td><%= inventoryRecord.getProperty("OTHER") %></td>
</tr>
<tr>
<td>詳細</td>
<td><%= inventoryRecord.getProperty("OTHER_JA") %></td>
</tr>
<tr>
<td>程度/CONDITION RANK</td>
<td><%= inventoryRecord.getProperty("CONDITION") %></td>
</tr>
<tr>
<td>整備状況/CONDITION MAINTENANCEK</td>
<td><%= FTCCodeUtil.getConditionMaintenance(inventoryRecord.getProperty("CONDITION_MAINTENANCE")) %></td>
</tr>
<tr>
<td>表示価格/LIST PRICE</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE")) %></td>
</tr>
<tr>
<td>業販価格</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("WHOL_PRICE")) %></td>
</tr>
<tr>
<td>写真/PHOTO</td>
<td><a href="<%= inventoryRecord.getProperty("PIC_URL") %>" target="_blank"><%= inventoryRecord.getProperty("PIC_URL") %></a></td>
</tr>
<tr>
<td>仕入担当</td>
<td><%= inventoryRecord.getProperty("ACCOUNT") %></td>
</tr>
<tr>
<td>発注日</td>
<td><%= inventoryRecord.getProperty("ORDER_DATE") %></td>
</tr>
<tr>
<td>WEB表示</td>
<td><%= FTCCodeUtil.getIsDisp(inventoryRecord.getProperty("WEB_DISP")) %></td>
</tr>
<tr>
<td>WEB表示日時</td>
<td><%= FTCCommonUtil.nullConv(inventoryRecord.getProperty("WEB_DISP_DATE")) %></td>
</tr>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<tr>
<td>仕入先コード</td>
<td><%= FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER_CODE")) %></td>
</tr>
<tr>
<td>仕入先</td>
<td><%= inventoryRecord.getProperty("SELLER") %></td>
</tr>
<tr>
<td>仕入価格</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("ORDER_PRICE")) %></td>
</tr>
<%
}
%>
<%
// 事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode)) {
%>
<tr>
<td>仕入運賃</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("ORDER_TRANS_COST")) %></td>
</tr>
<tr>
<td>部品代</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PARTS_COST")) %></td>
</tr>
<tr>
<td>整備費</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("MAINTENANCE_COST")) %></td>
</tr>
<tr>
<td>仕入外注費</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("ORDER_OUT_ORDER_COST")) %></td>
</tr>
<tr>
<td>仕入原価</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("ORDER_COST_PRICE")) %></td>
</tr>
<tr>
<td>引渡場所</td>
<td align="right"><%= FTCCommonUtil.nullConv(inventoryRecord.getProperty("TRAN_PLACE")) %></td>
</tr>
<%
}
%>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<tr>
<td>販売運賃</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_TRANCE_COST")) %></td>
</tr>
<tr>
<td>船積費用</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SHIP_COST")) %></td>
</tr>
<tr>
<td>販売外注費</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_OUT_ORDER_COST")) %></td>
</tr>
<tr>
<td>保険料</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("INS_COST")) %></td>
</tr>
<tr>
<td>フレイト</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("FREIGHT_COST")) %></td>
</tr>
<%
}
%>
<%
// サービス・事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<tr>
<td>販売原価</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_COST_PRICE")) %></td>
</tr>
<tr>
<td>販売価格</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_PRICE")) %></td>
</tr>
<tr>
<td>利益</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PROFIT")) %></td>
</tr>
<tr>
<td>販売先コード</td>
<td><%= FTCCommonUtil.nullConv(inventoryRecord.getProperty("BUYER_CODE")) %></td>
</tr>
<tr>
<td>販売先</td>
<td><%= inventoryRecord.getProperty("BUYER") %></td>
</tr>
<tr>
<td>INVOICE NO</td>
<td><%= FTCCommonUtil.nullConv(inventoryRecord.getProperty("INVOICE_NO")) %></td>
</tr>
<tr>
<td>仕入代金支払日</td>
<td><%= inventoryRecord.getProperty("ORDER_PAY_DATE") %></td>
</tr>
<tr>
<td>売上入金日</td>
<td><%= inventoryRecord.getProperty("SELL_PAY_DATE") %></td>
</tr>
<tr>
<td>売上月</td>
<td><%= inventoryRecord.getProperty("SELL_MONTH") %></td>
</tr>
<tr>
<td>在庫メモ</td>
<td><%= inventoryRecord.getProperty("MEMO") %></td>
</tr>
<%
}
%>
</table>
</div>
<div>&nbsp;</div>
<div><h1>整備履歴</h1></div>
<div>&nbsp;</div>
<%
}
%>
<div>
<table class="table table-bordered table-striped table-hover table-responsive"　style="table-layout:fixed;width:100%;">
<tr>
<th width="50">担当者</th>
<th width="100">日付</th>
<th width="100">型式</th>
<th width="120">号機</th>
<th width="60">稼働時間</th>
<th>作業内容</th>
</tr>
<%
List listDailyReport = (List)request.getAttribute("DailyReportList");
if (listDailyReport != null) {
	for (Iterator iterator = listDailyReport.iterator(); iterator.hasNext();) {
		Entity dailyReport = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td>" + dailyReport.getProperty("WORKER") + "</td>";
		record = record + "<td>" + dailyReport.getProperty("WORK_DATE") + "</td>";
		record = record + "<td>" + dailyReport.getProperty("NAME") + "</td>";
		record = record + "<td>" + dailyReport.getProperty("SERIALNO") + "</td>";
		record = record + "<td>" + FTCCommonUtil.nullConv(dailyReport.getProperty("HOURS")) + "</td>";
		record = record + "<td>" + dailyReport.getProperty("MEMO") + "</td>";
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