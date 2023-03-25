<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="gae.ftc.util.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// 注文データ取得
	Entity rentalOrder = (Entity)request.getAttribute("RentalOrder");

	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");
	String msg = (String)request.getSession().getAttribute("MSG");
	request.getSession().setAttribute("MSG", "");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>レンタル注文詳細</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body onload="initData()">
<div>
<div><h1>レンタル注文詳細</h1></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>レンタル在庫ID</td>
<td><%= rentalOrder.getProperty("RENTAL_INVENTORY_ID") %></td>
<td></td>
</tr>
<tr>
<td>型式</td>
<td><%= rentalOrder.getProperty("NAME") %></td>
<td></td>
</tr>
<tr>
<td>号機</td>
<td><%= rentalOrder.getProperty("SERIALNO") %></td>
<td></td>
</tr>
<tr>
<td>月単価</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE_MONTH")) %></td>
<td></td>
</tr>
<tr>
<td>日単価</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE_DAY")) %></td>
<td></td>
</tr>
<tr>
<td>サポート料金</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE_SUPPORT")) %></td>
<td></td>
</tr>
<tr>
<td>稼動時間開始時</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("HOURS_START")) %></td>
<td></td>
</tr>
<tr>
<td>在籍</td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("ENROLLMENT")) %></td>
<td></td>
</tr>
<tr>
<td>注文区分</td>
<td><%= FTCCodeUtil.getOrderType(rentalOrder.getProperty("ORDER_TYPE")) %></td>
<td></td>
</tr>
<tr>
<td>締め日</td>
<td><%= rentalOrder.getProperty("CLOSE_DATE") %></td>
<td></td>
</tr>
<tr>
<td>出庫日</td>
<td><%= rentalOrder.getProperty("OUT_DATE") %></td>
<td></td>
</tr>
<tr>
<td>返却日</td>
<td><%= rentalOrder.getProperty("IN_DATE") %></td>
<td></td>
</tr>
<tr>
<td>レンタル開始日</td>
<td><%= rentalOrder.getProperty("START_DATE") %></td>
<td></td>
</tr>
<tr>
<td>レンタル終了日</td>
<td><%= rentalOrder.getProperty("END_DATE") %></td>
<td></td>
</tr>
<tr>
<td>稼動時間終了時</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("HOURS_END")) %></td>
<td></td>
</tr>
<tr>
<td colspan="3" id="editmenu"></td>
</tr>
<tr>
<td>月極／日極</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE")) %></td>
<td></td>
</tr>
<tr>
<td>数量</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("COUNT")) %></td>
<td></td>
</tr>
<tr>
<td>レンタル代</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("AMOUNT")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("AMOUNT_MEMO")) %></td>
</tr>
<tr>
<td>サポート料金</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("SUPPORT_FEE")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("SUPPORT_MEMO")) %></td>
</tr>
<tr>
<td>搬入運賃</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("TRANS_FEE")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("TRANS_FEE_MEMO")) %></td>
</tr>
<tr>
<td>返却運賃</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("RETURN_FEE")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("RETURN_FEE_MEMO")) %></td>
</tr>
<tr>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE1_NAME")) %></td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE1")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE1_MEMO")) %></td>
</tr>
<tr>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE2_NAME")) %></td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE2")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE2_MEMO")) %></td>
</tr>
<tr>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE3_NAME")) %></td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE3")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE3_MEMO")) %></td>
</tr>
<tr>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE4_NAME")) %></td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE4")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE4_MEMO")) %></td>
</tr>
<tr>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE5_NAME")) %></td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE5")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE5_MEMO")) %></td>
</tr>
<tr>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE6_NAME")) %></td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE6")) %></td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE6_MEMO")) %></td>
</tr>
<tr>
<td>合計金額</td>
<td align="right"><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("TOTAL")) %></td>
<td></td>
</tr>
<tr>
<td>最終請求年月</td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_YM")) %></td>
<td></td>
</tr>
<tr>
<td>最終請求日</td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_DATE")) %></td>
<td></td>
</tr>
<tr>
<td>請求メモ</td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_NOTE")) %></td>
<td></td>
</tr>
<tr>
<td>全請求完了日</td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_END_DATE")) %></td>
<td></td>
</tr>
<tr>
<tr>
<td colspan="3" id="editmenu"></td>
</tr>
<tr>
<td>ユーザー名</td>
<td><%= rentalOrder.getProperty("ORDER_NAME") %></td>
<td></td>
</tr>
<tr>
<td>搬入先</td>
<td><%= rentalOrder.getProperty("ORDER_PLACE") %></td>
<td></td>
</tr>
<tr>
<td>顧客コード</td>
<td><%= rentalOrder.getProperty("CLIENT_CODE") %></td>
<td></td>
</tr>
<tr>
<td>顧客名</td>
<td><%= rentalOrder.getProperty("CLIENT_NAME") %></td>
<td></td>
</tr>
<tr>
<td>顧客担当者</td>
<td><%= rentalOrder.getProperty("CLIENT_REP") %></td>
<td></td>
</tr>
<tr>
<td>顧客TEL</td>
<td><%= rentalOrder.getProperty("CLIENT_TEL") %></td>
<td></td>
</tr>
<tr>
<td>備考欄</td>
<td><%= rentalOrder.getProperty("MEMO") %></td>
<td></td>
</tr>
</table>
</div>
<div>&nbsp;</div>
<div><h1>請求履歴</h1></div>
<div>&nbsp;</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>請求月</th>
<th nowrap>請求日</th>
<th nowrap>請求金額</th>
<th nowrap>請求メモ</th>
</tr>
<%
List listRentalOrderInvoiceHistory = (List)request.getAttribute("RentalOrderInvoiceHistoryList");
if (listRentalOrderInvoiceHistory != null) {
	for (Iterator iterator = listRentalOrderInvoiceHistory.iterator(); iterator.hasNext();) {
		Entity rentalOrderData = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap>" + rentalOrderData.getProperty("REQUEST_YM") + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(rentalOrderData.getProperty("REQUEST_DATE")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.moneyFormat(rentalOrderData.getProperty("AMOUNT")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(rentalOrderData.getProperty("REQUEST_NOTE")) + "</td>";
		record = record + "</tr>";
		out.println(record);
	}
}
%>
</table>
</div>
<div>&nbsp;</div>
<div><h1>レンタル注文引合情報</h1></div>
<div>&nbsp;</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>レンタル開始希望日</th>
<th nowrap>顧客名</th>
<th nowrap>顧客担当者</th>
<th nowrap>顧客TEL</th>
<th nowrap>備考欄</th>

</tr>
<%
List listPreRentalOrder = (List)request.getAttribute("PreRentalOrderList");
if (listPreRentalOrder != null) {
	for (Iterator iterator = listPreRentalOrder.iterator(); iterator.hasNext();) {
		Entity preRentalOrder = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap>" + preRentalOrder.getProperty("START_DATE") + "</td>";
		record = record + "<td nowrap>" + preRentalOrder.getProperty("CLIENT_NAME") + "</td>";
		record = record + "<td nowrap>" + preRentalOrder.getProperty("CLIENT_REP") + "</td>";
		record = record + "<td nowrap>" + preRentalOrder.getProperty("CLIENT_TEL") + "</td>";
		record = record + "<td nowrap>" + preRentalOrder.getProperty("MEMO") + "</td>";
	  record = record + "</tr>";
	  out.println(record);
	}
}
%>
</table>
</div>
<div>&nbsp;</div>
<div><h1>整備履歴</h1></div>
<div>&nbsp;</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>担当者</th>
<th nowrap>日付</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
<th nowrap>稼働時間</th>
<th nowrap>作業内容</th>
</tr>
<%
List listDailyReport = (List)request.getAttribute("DailyReportList");
if (listDailyReport != null) {
	for (Iterator iterator = listDailyReport.iterator(); iterator.hasNext();) {
		Entity dailyReport = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap>" + dailyReport.getProperty("WORKER") + "</td>";
		record = record + "<td nowrap>" + dailyReport.getProperty("WORK_DATE") + "</td>";
		record = record + "<td nowrap>" + dailyReport.getProperty("NAME") + "</td>";
		record = record + "<td nowrap>" + dailyReport.getProperty("SERIALNO") + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(dailyReport.getProperty("HOURS")) + "</td>";
		record = record + "<td nowrap>" + dailyReport.getProperty("MEMO") + "</td>";
		record = record + "</tr>";
		out.println(record);
	}
}
%>
</table>
</div>
</div>
</body>
</html>