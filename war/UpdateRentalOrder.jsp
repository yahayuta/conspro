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
<title>レンタル注文更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
	  if (isNaN(obj.AMOUNT.value)||
				isNaN(obj.TRANS_FEE.value)||
				isNaN(obj.RETURN_FEE.value)) {
			alert('数字項目に数字以外の文字が入力されています。入力を見直してください');
			return;
		}
		if (!ckDate(obj.OUT_DATE.value) || !ckDate(obj.IN_DATE.value) || !ckDate(obj.START_DATE.value) || !ckDate(obj.END_DATE.value)　|| !ckDate(obj.REQUEST_DATE.value)　|| !ckDate(obj.REQUEST_END_DATE.value)) {
			alert('日付の入力が不正です。入力を見直してください');
			return;
		}
		if (!ckYM(obj.REQUEST_YM.value)) {
			alert('年月の入力が不正です。入力を見直してください');
			return;
		}
		obj.target='_self';
		obj.action='FTCEditRentalOrder?ACCOUNT=<%= account %>';
		obj.submit();
	}

	// 日付チェック
	function ckDate(datestr) {
		if (datestr == "") {
			return true;
		}
		if(!datestr.match(/^\d{4}\/\d{2}\/\d{2}$/)){
			return false;
		}
		return true;
	}

	// 年月チェック
	function ckYM(datestr) {
		if (datestr == "") {
			return true;
		}
		if(!datestr.match(/^\d{4}\/\d{2}$/)){
			return false;
		}
		return true;
	}
	
	// 注文区分選択
	function changeOrderType(obj) {
		if (obj.ORDER_TYPE.value == "0") {
			obj.PRICE.value = obj.PRICE_MONTH.value;
		}
		if (obj.ORDER_TYPE.value == "1") {
			obj.PRICE.value = obj.PRICE_DAY.value;
		}
	}

	// 初期化
	function initData() {
		document.updateForm.ORDER_TYPE.value = document.updateForm.LOADORDER_TYPE.value;
		document.updateForm.CLOSE_DATE.value = document.updateForm.LOADCLOSE_DATE.value;
	}

	// 金額計算
	function calcAmount(obj) {
		obj.TOTAL.value = 0;
		obj.AMOUNT.value = parseFloat(obj.PRICE.value) * parseFloat(obj.COUNT.value);
		
		if (obj.AMOUNT_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.AMOUNT.value);
		}
		if (obj.TRANS_FEE_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.TRANS_FEE.value);
		}
		if (obj.RETURN_FEE_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.RETURN_FEE.value);
		}
		if (obj.FEE1_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.FEE1.value);
		}
		if (obj.FEE2_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.FEE2.value);
		}
		if (obj.FEE3_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.FEE3.value);
		}
		if (obj.FEE4_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.FEE4.value);
		}
		if (obj.FEE5_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.FEE5.value);
		}
		if (obj.FEE6_CHK.checked) {
			obj.TOTAL.value = parseFloat(obj.TOTAL.value) + parseFloat(obj.FEE6.value);
		}

//		obj.TOTAL.value = parseFloat(obj.AMOUNT.value) + parseFloat(obj.TRANS_FEE.value) + parseFloat(obj.RETURN_FEE.value) + parseFloat(obj.FEE1.value) + parseFloat(obj.FEE2.value) + parseFloat(obj.FEE3.value) + parseFloat(obj.FEE4.value) + parseFloat(obj.FEE5.value) + parseFloat(obj.FEE6.value);
	}

	// 顧客ポップ
	function clientPop(code,name,rep,tel){
		window.open("/ClientListPopUp.jsp?type=0&code="+code+"&name="+name+"&rep="+rep+"&tel="+tel+"&ACCOUNT=<%=account %>", 'clientpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}
	
	// 請求書出力
	function createInvoice(obj) {
	  if (obj.REQUEST_YM.value == '') {
			alert('最終請求年月を入力してください');
			return;
		}
	  	if (obj.REQUEST_DATE.value == '') {
			alert('最終請求日を入力してください');
			return;
		}
		obj.target='_self';
		obj.action='FTCLoadExcelRentalJpInvoice';
		obj.submit();
	}
-->
</script>
</head>
<body onload="initData()">
<div>
<form name="updateForm" method="POST">
<div><h1>レンタル注文更新</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>レンタル在庫ID</td>
<td><%= rentalOrder.getProperty("RENTAL_INVENTORY_ID") %></td>
</tr>
<tr>
<td>型式</td>
<td><%= rentalOrder.getProperty("NAME") %></td>
</tr>
<tr>
<td>号機</td>
<td><%= rentalOrder.getProperty("SERIALNO") %></td>
</tr>
<tr>
<td>月単価</td>
<td><input type="text" name="PRICE_MONTH" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE_MONTH")) %>" disabled></td>
</tr>
<tr>
<td>日単価</td>
<td><input type="text" name="PRICE_DAY" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE_DAY")) %>" disabled></td>
</tr>
<tr>
<td>サポート料金</td>
<td><input type="text" name="PRICE_DAY" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE_SUPPORT")) %>" disabled></td>
</tr>
<tr>
<tr>
<td>稼動時間開始時</td>
<td><%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("HOURS_START")) %></td>
</tr>
<tr>
<td>在籍</td>
<td><%= FTCCommonUtil.nullConv(rentalOrder.getProperty("ENROLLMENT")) %></td>
</tr>
<tr>
<td>注文区分</td>
<td>
<input type="hidden" name="LOADORDER_TYPE" value="<%= rentalOrder.getProperty("ORDER_TYPE") %>" size="1">
<select name="ORDER_TYPE" onchange="changeOrderType(this.form);">
<%= FTCConst.ORDER_TYPE %>
</select>
</td>
</tr>
<tr>
<td>締め日</td>
<td>
<input type="hidden" name="LOADCLOSE_DATE" value="<%= rentalOrder.getProperty("CLOSE_DATE") %>" size="1">
<select name="CLOSE_DATE">
<%= FTCConst.CLOSE_DATE %>
</select>
</td>
</tr>
<tr>
<td>出庫日</td>
<td><input type="text" name="OUT_DATE" value="<%= rentalOrder.getProperty("OUT_DATE") %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>返却日</td>
<td><input type="text" name="IN_DATE" value="<%= rentalOrder.getProperty("IN_DATE") %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>レンタル開始日</td>
<td><input type="text" name="START_DATE" value="<%= rentalOrder.getProperty("START_DATE") %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>レンタル終了日</td>
<td><input type="text" name="END_DATE" value="<%= rentalOrder.getProperty("END_DATE") %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>稼動時間終了時</td>
<td><input type="text" name="HOURS_END" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("HOURS_END")) %>" style="text-align:right;"></td>
</tr>
<tr>
<td colspan="2" id="editmenu"></td>
</tr>
<tr>
<td>月極／日極</td>
<td><input type="text" name="PRICE" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("PRICE")) %>" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>数量</td>
<td><input type="text" name="COUNT" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("COUNT")) %>" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td><input type="checkbox" name="AMOUNT_CHK" value="1" checked="checked">レンタル代</td>
<td><input type="text" name="AMOUNT" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("AMOUNT")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="AMOUNT_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("AMOUNT_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="SUPPORT_CHK" value="1" checked="checked">サポート料金</td>
<td><input type="text" name="SUPPORT_FEE" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("SUPPORT_FEE")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="SUPPORT_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("SUPPORT_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="TRANS_FEE_CHK" value="1" checked="checked">搬入運賃</td>
<td><input type="text" name="TRANS_FEE" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("TRANS_FEE")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="TRANS_FEE_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("TRANS_FEE_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="RETURN_FEE_CHK" value="1" checked="checked">返却運賃</td>
<td><input type="text" name="RETURN_FEE" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("RETURN_FEE")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="RETURN_FEE_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("RETURN_FEE_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="FEE1_CHK" value="1" checked="checked"><input type="text" name="FEE1_NAME" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE1_NAME")) %>"></td>
<td><input type="text" name="FEE1" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE1")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE1_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE1_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="FEE2_CHK" value="1" checked="checked"><input type="text" name="FEE2_NAME" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE2_NAME")) %>"></td>
<td><input type="text" name="FEE2" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE2")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE2_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE2_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="FEE3_CHK" value="1" checked="checked"><input type="text" name="FEE3_NAME" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE3_NAME")) %>"></td>
<td><input type="text" name="FEE3" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE3")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE3_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE3_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="FEE4_CHK" value="1" checked="checked"><input type="text" name="FEE4_NAME" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE4_NAME")) %>"></td>
<td><input type="text" name="FEE4" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE4")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE4_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE4_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="FEE5_CHK" value="1" checked="checked"><input type="text" name="FEE5_NAME" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE5_NAME")) %>"></td>
<td><input type="text" name="FEE5" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE5")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE5_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE5_MEMO")) %>"></td>
</tr>
<tr>
<td><input type="checkbox" name="FEE6_CHK" value="1" checked="checked"><input type="text" name="FEE6_NAME" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE6_NAME")) %>"></td>
<td><input type="text" name="FEE6" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("FEE6")) %>" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE6_MEMO" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("FEE6_MEMO")) %>"></td>
</tr>
<tr>
<td>合計金額</td>
<td><input type="text" name="TOTAL" value="<%= FTCCommonUtil.parseDoubleString(rentalOrder.getProperty("TOTAL")) %>" style="text-align:right;">（数字のみ入力可能）<input type="button" value="金額計算" onclick="calcAmount(this.form)"></td>
</tr>
<tr>
<td>最終請求年月</td>
<td><input type="text" name="REQUEST_YM" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_YM")) %>">（入力形式:YYYY/MM）</td>
</tr>
<tr>
<td>最終請求日</td>
<td><input type="text" name="REQUEST_DATE" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_DATE")) %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>請求メモ</td>
<td><input type="text" name="REQUEST_NOTE" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_NOTE")) %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>全請求完了日</td>
<td><input type="text" name="REQUEST_END_DATE" value="<%= FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_END_DATE")) %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<tr>
<td colspan="2" id="editmenu"></td>
</tr>
<tr>
<td>ユーザー名</td>
<td><input type="text" name="ORDER_NAME" value="<%= rentalOrder.getProperty("ORDER_NAME") %>"></td>
</tr>
<tr>
<td>搬入先</td>
<td><input type="text" name="ORDER_PLACE" value="<%= rentalOrder.getProperty("ORDER_PLACE") %>"></td>
</tr>
<tr>
<td>顧客コード</td>
<td><input type="text" name="CLIENT_CODE" id="CLIENT_CODE" value="<%= rentalOrder.getProperty("CLIENT_CODE") %>"><input type="button" value="顧客選択" onclick="clientPop('CLIENT_CODE','CLIENT_NAME','CLIENT_REP','CLIENT_TEL')"></td>
</tr>
<tr>
<td>顧客名</td>
<td><input type="text" name="CLIENT_NAME" id="CLIENT_NAME" value="<%= rentalOrder.getProperty("CLIENT_NAME") %>"></td>
</tr>
<tr>
<td>顧客担当者</td>
<td><input type="text" name="CLIENT_REP" id="CLIENT_REP" value="<%= rentalOrder.getProperty("CLIENT_REP") %>"></td>
</tr>
<tr>
<td>顧客TEL</td>
<td><input type="text" name="CLIENT_TEL" id="CLIENT_TEL" value="<%= rentalOrder.getProperty("CLIENT_TEL") %>"></td>
</tr>
<tr>
<td>備考欄</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"><%= rentalOrder.getProperty("MEMO") %></textarea></td>
</tr>
</table>
</div>
<div>
<input type="hidden" name="EDITID" value="<%= rentalOrder.getKey().getName() %>">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="hidden" name="type" value="1">
<input type="button" name="update" value="更新" onclick="callServer(this.form)">
<input type="button" name="invoice" value="請求書出力" onclick="createInvoice(this.form);">
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
</form>
</div>
</body>
</html>