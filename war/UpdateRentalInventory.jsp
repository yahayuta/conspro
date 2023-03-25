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
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");
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
<title>レンタル在庫更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
	  if (isNaN(obj.PRICE_DAY.value)||
				isNaN(obj.PRICE_MONTH.value)) {
			alert('数字項目に数字以外の文字が入力されています。入力を見直してください');
			return;
		}
		obj.submit();
	}


	// 初期化
	function initData() {
		document.updateForm.MANUFACTURER.value = document.updateForm.LOADMANUFACTURER.value;
		document.updateForm.TYPE.value = document.updateForm.LOADTYPE.value;
		document.updateForm.TYPE_SUB.value = document.updateForm.LOADTYPE_SUB.value;
		document.updateForm.WEB_DISP.value = document.updateForm.LOADWEB_DISP.value;
	}


	// 顧客ポップ
	function clientPop(code,name){
		window.open("/ClientListPopUp.jsp?type=0&code="+code+"&name="+name+"&ACCOUNT=<%=account %>", 'clientpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}
-->
</script>
</head>
<body onload="initData()">
<div>
<form action="FTCEditRentalInventory?ACCOUNT=<%= account %>" name="updateForm" method="POST">
<div><h1>レンタル在庫更新</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div>
<input type="button" value="戻る" onclick="history.back();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>サムネイル</td>
<td><img src="FTCGetPictureRental?EDITID=<%= inventoryRecord.getKey().getName() %>"width="160" height="120"></td>
</tr>
<tr>
<td>分類</td>
<td>
<input type="hidden" name="LOADTYPE" value="<%= inventoryRecord.getProperty("TYPE") %>" size="1">
<select name="TYPE">
<%= FTCConst.COMBO_TYPE %>
</select>
</td>
</tr>
<tr>
<td>分類サブ</td>
<td>
<input type="hidden" name="LOADTYPE_SUB" value="<%= inventoryRecord.getProperty("TYPE_SUB") %>" size="1">
<select name="TYPE_SUB">
<option value="">選択</option>
<%= FTCConst.COMBO_TYPE_SUB %>
</select>
</td>
</tr>
<tr>
<td>型式</td>
<td><input type="text" name="NAME" value="<%= inventoryRecord.getProperty("NAME") %>" size="50"></td>
</tr>
<tr>
<td>メーカー</td>
<td>
<input type="hidden" name="LOADMANUFACTURER" value="<%= inventoryRecord.getProperty("MANUFACTURER") %>">
<select name="MANUFACTURER">
<%= FTCConst.COMBO_MANUFACTURER %>
</select>
</td>
</tr>
<tr>
<td>年式</td>
<td><input type="text" name="YEAR" value="<%= inventoryRecord.getProperty("YEAR") %>"></td>
</tr>
<tr>
<td>号機</td>
<td><input type="text" name="SERIALNO" value="<%= inventoryRecord.getProperty("SERIALNO") %>"></td>
</tr>
<tr>
<td>稼働時間</td>
<td><input type="text" name="HOURS" value="<%= inventoryRecord.getProperty("HOURS") %>"></td>
</tr>
<tr>
<td>詳細／仕様</td>
<td><input type="text" name="OTHER_JA" value="<%= inventoryRecord.getProperty("OTHER_JA") %>" size="100"></td>
</tr>
<tr>
<td>写真</td>
<td><input type="text" name="PIC_URL" value="<%= inventoryRecord.getProperty("PIC_URL") %>" size="100">（入力形式:アドレス）</td>
</tr>
<tr>
<td>仕入先コード</td>
<td><input type="text" name="SELLER_CODE" id="SELLER_CODE" value="<%= inventoryRecord.getProperty("SELLER_CODE") %>" size="50"><input type="button" value="仕入先選択" onclick="clientPop('SELLER_CODE','SELLER')"></td>
</tr>
<tr>
<td>仕入先</td>
<td><input type="text" name="SELLER" id="SELLER" value="<%= inventoryRecord.getProperty("SELLER") %>" size="100"></td>
</tr>
<tr>
<td>日単価</td>
<td><input type="text" name="PRICE_DAY" value="<%= inventoryRecord.getProperty("PRICE_DAY") %>" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>月単価</td>
<td><input type="text" name="PRICE_MONTH" value="<%= inventoryRecord.getProperty("PRICE_MONTH") %>" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>サポート料金</td>
<td><input type="text" name="PRICE_SUPPORT" value="<%= inventoryRecord.getProperty("PRICE_SUPPORT") %>" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>在籍</td>
<td><input type="text" name="ENROLLMENT" value="<%= inventoryRecord.getProperty("ENROLLMENT") %>" size="100"></td>
</tr>
<tr>
<td>サイズ（㎥）</td>
<td><input type="text" name="SIZE" value="<%= inventoryRecord.getProperty("SIZE") %>" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>重量（㎏）</td>
<td><input type="text" name="WEIGHT" value="<%= inventoryRecord.getProperty("WEIGHT") %>" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>WEB表示</td>
<td>
<input type="hidden" name="LOADWEB_DISP" value="<%= inventoryRecord.getProperty("WEB_DISP") %>" size="1">
<select name="WEB_DISP">
<%= FTCConst.COMBO_WEB_DISP %>
</select>
</td>
</tr>
<tr>
<td>在庫メモ</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"><%= inventoryRecord.getProperty("MEMO") %></textarea></td>
</tr>
</table>
<div>
<input type="hidden" name="EDITID" value="<%= inventoryRecord.getKey().getName() %>">
<input type="hidden" name="type" value="1">
<input type="button" value="更新" onclick="callServer(this.form)">
</div>
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
</form>
</div>
</body>
</html>