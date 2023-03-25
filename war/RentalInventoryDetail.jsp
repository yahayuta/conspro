<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	// 在庫データ取得
	Entity inventoryRecord = (Entity)request.getAttribute("RentalInventoryRecord");

	// 認証チェック
	FTCAuthUtil.isLogin(request, response);
	
	String account = request.getParameter("ACCOUNT");
	String msg = (String)request.getSession().getAttribute("MSG");
	request.getSession().setAttribute("MSG", "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>レンタル在庫詳細</title>
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
<form name="detailForm" method="POST">
<div>
<div><h1>レンタル在庫詳細</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div>
<input type="button" value="戻る" onclick="location.href='FTCGetRentalInventoryList?ACCOUNT=<%= account %>'">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>サムネイル</td>
<td><img src="FTCGetPictureRental?EDITID=<%= inventoryRecord.getKey().getName() %>"width="160" height="120"></td>
</tr>
<tr>
<td>ステータス</td>
<td><%= FTCCodeUtil.getRental(inventoryRecord.getProperty("STATUS")) %></td>
</tr>
<tr>
<td>分類</td>
<td><%= FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) %></td>
</tr>
<tr>
<td>分類サブ</td>
<td><%= FTCCodeUtil.getTypeSubJa(inventoryRecord.getProperty("TYPE_SUB")) %></td>
</tr>
<tr>
<td>型式</td>
<td><%= inventoryRecord.getProperty("NAME") %></td>
</tr>
<tr>
<td>メーカー</td>
<td><%= inventoryRecord.getProperty("MANUFACTURER") %></td>
</tr>
<tr>
<td>年式</td>
<td><%= inventoryRecord.getProperty("YEAR") %></td>
</tr>
<tr>
<td>号機</td>
<td><%= inventoryRecord.getProperty("SERIALNO") %></td>
</tr>
<tr>
<td>稼働時間</td>
<td align="right"><%= inventoryRecord.getProperty("HOURS") %></td>
</tr>
<tr>
<td>詳細／仕様</td>
<td><%= inventoryRecord.getProperty("OTHER_JA") %></td>
</tr>
<tr>
<td>写真</td>
<td><%= inventoryRecord.getProperty("PIC_URL") %></td>
</tr>
<tr>
<td>仕入先コード</td>
<td><%= inventoryRecord.getProperty("SELLER_CODE") %></td>
</tr>
<tr>
<td>仕入先</td>
<td><%= inventoryRecord.getProperty("SELLER") %></td>
</tr>
<tr>
<td>日単価</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE_DAY")) %></td>
</tr>
<tr>
<td>月単価</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE_MONTH")) %></td>
</tr>
<tr>
<td>サポート料金</td>
<td align="right"><%= FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE_SUPPORT")) %></td>
</tr>
<tr>
<td>在籍</td>
<td><%= inventoryRecord.getProperty("ENROLLMENT") %></td>
</tr>
<tr>
<td>サイズ（㎥）</td>
<td align="right"><%= inventoryRecord.getProperty("SIZE") %></td>
</tr>
<tr>
<td>重量（㎏）</td>
<td align="right"><%= inventoryRecord.getProperty("WEIGHT") %></td>
</tr>
<tr>
<td>WEB表示</td>
<td><%= FTCCodeUtil.getIsDisp(inventoryRecord.getProperty("WEB_DISP")) %></td>
</tr>
<tr>
<td>在庫メモ</td>
<td><%= inventoryRecord.getProperty("MEMO") %></td>
</tr>
</table>
</div>
<div>&nbsp;</div>
<div><h1>レンタル注文引合情報</h1></div>
<div>&nbsp;</div>
<div>
<input type="button" name="edit" value="編集" onclick="editPreOrder(this.form,0)">
<input type="button" name="delete" value="削除" onclick="editPreOrder(this.form,2);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
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
		record = record + "<td nowrap><input type=\"radio\" name=\"PREID\" value=\""+ preRentalOrder.getKey().getName() +"\"></td>";
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
<div><h1>レンタル注文履歴</h1></div>
<div>&nbsp;</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>レンタル開始日</th>
<th nowrap>レンタル終了日</th>
<th nowrap>稼動時間開始時</th>
<th nowrap>稼動時間終了時</th>
<th nowrap>ユーザー名</th>
<th nowrap>顧客名</th>
</tr>
<%
List listRentalOrder = (List)request.getAttribute("RentalOrderList");
if (listRentalOrder != null) {
	for (Iterator iterator = listRentalOrder.iterator(); iterator.hasNext();) {
		Entity rentalOrder = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("START_DATE") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("END_DATE") + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("HOURS_START")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("HOURS_END")) + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("ORDER_NAME") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("CLIENT_NAME") + "</td>";
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
</form>
</body>
</html>