<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");

	Set<String> nameSet = (Set)request.getSession().getAttribute("NameSet");
	Set<String> sellerSet = (Set)request.getSession().getAttribute("SellerSet");
	Set<String> buyerSet = (Set)request.getSession().getAttribute("BuyerSet");
	Set<String> tantoSet = (Set)request.getSession().getAttribute("TantoSet");
	Long orderCostPrice = (Long)request.getAttribute("orderCostPrice");
	Long sellCostPrice = (Long)request.getAttribute("sellCostPrice");
	Long sellPrice = (Long)request.getAttribute("sellPrice");
	Long profit = (Long)request.getAttribute("profit");
	
	String msg = (String)request.getSession().getAttribute("MSG");
	request.getSession().setAttribute("MSG", "");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script type="text/javascript">
<!--
	function callServer(obj,arg) {
		obj.target='_self';
		obj.type.value=arg;
		if (arg==6) {
			if (!confirm('締め戻しを行います\nよろしいですか？')) {
				return;
			}
		}
		obj.action='FTCEditInventory';
		obj.submit();
	}

	function createExcelForClosedInv(obj) {

		// 負荷軽減のため検索条件設定必須
		if (obj.SELL_MONTH.value == '') {
			alert('売上月を必ず指定してください。');
			return;
		}

		obj.target='_self';
		obj.action='FTCLoadExcelForClosedInv';
		obj.submit();
	}

//	function sortList(obj,sortType) {
//    obj.sort.value=sortType;
//		obj.target='_self';
//		obj.action='FTCGetOldInventoryList';
//		obj.submit();
//	}

	function search(obj) {

		// 負荷軽減のため検索条件設定必須
		if (obj.SELL_MONTH.value == '' && obj.NAME.value == '' && obj.SELLER.value == '' && obj.BUYER.value == '' && obj.TANTO.value == '' && obj.SELL_MONTH_TXT.value == '' && obj.NAME_TXT.value == '') {
			alert('検索条件を必ず指定してください。');
			return;
		}

		obj.target='_self';
		obj.action='/FTCSeachOldInventoryList';
		obj.submit();
	}

-->
</script>
<title>締め在庫一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditInventory" name="cloesedListForm" method="POST">
<div><h1>締め在庫一覧</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div id="cond">検索条件</div>
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
	<td>売上月（入力形式:YYYY/MM）</td>
	<td><input type="text" name="SELL_MONTH_TXT" value=""></td>
</tr>
<tr>
	<td>型式（自由入力）</td>
	<td><input type="text" name="NAME_TXT" value=""></td>
</tr>
</table>
</div>
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
	<td>
		<select name="SELL_MONTH">
				<option value="">売上月</option>
				<%= FTCCommonUtil.buildOptions(FTCConst.SELL_MONTH_SET) %>
		</select>
	</td>
	<td>
		<select name="NAME">
				<option value="">型式</option>
				<%= FTCCommonUtil.buildOptions(nameSet) %>
		</select>
	</td>
	<td>
		<select name="SELLER">
				<option value="">仕入先</option>
				<%= FTCCommonUtil.buildOptions(sellerSet) %>
		</select>
	</td>
	<td>
		<select name="BUYER">
				<option value="">販売先</option>
				<%= FTCCommonUtil.buildOptions(buyerSet) %>
		</select>
	</td>
	<td>
		<select name="TANTO">
				<option value="">担当者</option>
				<%= FTCCommonUtil.buildOptions(tantoSet) %>
		</select>
	</td>
	<td><input type="button" name="srch" value="検索" onclick="search(this.form);"></td>
</tr>
</table>
</div>
<div align="right">
<!--
<input type="button" name="sort1" value="分類で並び替え" onclick="sortList(this.form,1);">
<input type="button" name="sort0" value="メーカーで並び替え" onclick="sortList(this.form,6);">
<input type="button" name="sort2" value="登録日で並び替え" onclick="sortList(this.form,2);">
-->
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<%
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
out.println("<div id=\"count\">件数:" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<input type="hidden" name="type">
<input type="hidden" name="sort">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="detail" value="在庫詳細" onclick="callServer(this.form,4);">
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<input type="button" name="back" value="締め戻し" onclick="callServer(this.form,6);">
<%
}
%>
<%
// 一般以外表示
if (!FTCConst.AUTH_NORMAL.equals(authCode)) {
%>
<input type="button" name="createex" value="締め在庫EXCEL出力" onclick="createExcelForClosedInv(this.form);">
<%
}
%>
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>No</th>
<th nowrap>分類</th>
<th nowrap>メーカー</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
<th nowrap>年式</th>
<th nowrap>表示価格</th>
<th nowrap>登録日</th>
<th nowrap>仕入担当</th>
<th nowrap>発注日</th>
<th nowrap>仕入先</th>
<th nowrap>仕入原価</th>
<th nowrap>仕入支払日</th>
<th nowrap>販売先</th>
<th nowrap>販売原価</th>
<th nowrap>販売価格</th>
<th nowrap>利益</th>
<th nowrap>売上入金日</th>
<th nowrap>売上月</th>
</tr>
<%
if (listInventoryRecord != null) {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
int cnt = 1;
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(Long.parseLong(inventoryRecord.getKey().getName()));
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"EDITID\" value=\""+ inventoryRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + cnt + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SERIALNO") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("YEAR") + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE")) + "</td>";
		record = record + "<td nowrap>" + sdf.format(date) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("ACCOUNT") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("ORDER_DATE") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELLER") + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("ORDER_COST_PRICE")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("ORDER_PAY_DATE") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("BUYER") + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_COST_PRICE")) + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_PRICE")) + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PROFIT")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELL_PAY_DATE") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELL_MONTH") + "</td>";
		record = record + "</tr>";
		out.println(record);
		cnt++;
	}
}

String record = "<tr>";
record = record + "<td>Total</td>";
record = record + "<td>&nbsp;</td>";
//record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(orderCostPrice) + "</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(sellCostPrice) + "</td>";
record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(sellPrice) + "</td>";
record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(profit) + "</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "</tr>";
out.println(record);

 %>
</table>
</div>
<div>
<input type="button" name="detail2" value="在庫詳細" onclick="callServer(this.form,4);">
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<input type="button" name="back2" value="締め戻し" onclick="callServer(this.form,6);">
<%
}
%>
<%
// 一般以外表示
if (!FTCConst.AUTH_NORMAL.equals(authCode)) {
%>
<input type="button" name="createex" value="締め在庫EXCEL出力" onclick="createExcelForClosedInv(this.form);">
<%
}
%>
</div>
</form>
</div>
</body>
</html>