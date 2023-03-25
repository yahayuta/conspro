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
		if (arg==2) {
			if (!confirm('削除してよろしいですか？')) {
				return;
			}
		}
		if (arg==5) {
			if (!confirm('在庫締めを行います\nよろしいですか？')) {
				return;
			}
		}
		obj.action='FTCEditInventory';
		obj.submit();
	}

	function createOrderSheet(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelOrderSheet';
		obj.submit();
	}

	function createJpInvoice(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelJpInvoice';
		obj.submit();
	}

	function createProformaInvoice(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelProformaInvoice';
		obj.submit();
	}

	function createExcel(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelInv';
		obj.submit();
	}

	function seachForSellInv(obj) {
		obj.target='_self';
		obj.action='FTCSeachInventoryList?SEARCH_TYPE=1';
		obj.submit();
	}

	function seachForInvCount(obj) {
		obj.target='_self';
		obj.action='FTCSeachInventoryList?SEARCH_TYPE=2';
		obj.submit();
	}

	function seachForCloseInv(obj) {
		obj.target='_self';
		obj.action='FTCSeachInventoryList?SEARCH_TYPE=3';
		obj.submit();
	}

	function sortList(obj,sortType) {
    	obj.sort.value=sortType;
		obj.target='_self';
		obj.action='FTCGetInventoryList';
		obj.submit();
	}

	function search(obj) {
		obj.target='_self';
		obj.action='FTCSeachInventoryList';
		obj.submit();
	}

	function regInv(obj) {
		obj.target='reginv';
		obj.action='RegistInventory.jsp';
		obj.submit();
	}

-->
</script>
<title>在庫一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditInventory"  method="POST">
<div><h1>在庫一覧</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div id="cond">検索条件</div>
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
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
<%
// 管理者権限・一般権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode) || FTCConst.AUTH_NORMAL.equals(authCode)) {
%>
	<td><input type="button" name="sellInv" value="販売用在庫検索" onclick="seachForSellInv(this.form);"></td>
<%
}
%>
<%
// 管理者権限・経理権限のみ表示のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode) || FTCConst.AUTH_ACCT.equals(authCode)) {
%>
	<td><input type="button" name="invCount" value="棚卸在庫検索" onclick="seachForInvCount(this.form);"></td>
<%
}
%>
	<td>
		<select name="WEB_DISP" onchange="search(this.form);">
			<option value="">WEB</option>
			<option value="0">無</option>
			<option value="1">F</option>
			<option value="2">濱</option>
			<option value="3">F濱</option>
		</select>
	<td>
</tr>
</table>
</div>
<div align="right">
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" name="reginv" value="在庫登録" onclick="regInv(this.form);">
<%
}
%>
<input type="button" name="sort1" value="分類で並び替え" onclick="sortList(this.form,1);">
<input type="button" name="sort0" value="メーカーで並び替え" onclick="sortList(this.form,6);">
<input type="button" name="sort2" value="登録日で並び替え" onclick="sortList(this.form,2);">
<input type="button" name="sort1" value="WEB表示日時で並び替え" onclick="sortList(this.form,7);">
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
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" name="update" value="在庫更新" onclick="callServer(this.form,0);">
<input type="button" name="updatepic" value="写真更新" onclick="callServer(this.form,3);">
<%
}
%>
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<input type="button" name="delete" value="在庫削除" onclick="callServer(this.form,2);">
<input type="button" name="close" value="在庫締め" onclick="callServer(this.form,5);">
<%
}
%>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" name="createos" value="注文書出力" onclick="createOrderSheet(this.form);">
<%
}
%>
<%
// サービス・事務以外表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" name="createji" value="見積書・請求書出力" onclick="createJpInvoice(this.form);">
<input type="button" name="createji" value="Proforma Invoice出力" onclick="createProformaInvoice(this.form);">
<%
}
%>
<%
// 一般と事務以外表示
if (!FTCConst.AUTH_NORMAL.equals(authCode) && !FTCConst.AUTH_OFFICE.equals(authCode)) {
%>
<input type="button" name="createex" value="EXCEL出力" onclick="createExcel(this.form);">
<%
}
%>
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>出力</th>
<th nowrap>No</th>
<%
// 管理は表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<th nowrap>売上月</th>
<%
}
%>
<!--<th nowrap>WEB</td>-->
<!--<th nowrap>WEB表示日時</td>-->
<th nowrap>分類</th>
<th nowrap>メーカー</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
<!--<th nowrap>年式</th>-->
<!--<th nowrap>表示価格</th>-->
<th nowrap>登録日</th>
<th nowrap>仕入担当</th>
<%
// サービス・事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<th nowrap>発注日</th>
<th nowrap>仕入先</th>
<th nowrap>仕入原価</th>
<th nowrap>仕入支払日</th>
<th nowrap>販売先</th>
<!--<th nowrap>販売原価</th>-->
<!--<th nowrap>販売価格</th>-->
<!--<th nowrap>利益</th>-->
<th nowrap>売上入金日</th>
<%
	// 管理以外は表示
	if (!FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<th nowrap>売上月</th>
<%
	}
}
%>
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
		record = record + "<td nowrap><input type=\"checkbox\" name=\"REPID\" value=\""+ inventoryRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + cnt + "</td>";
		// 管理は表示
		if (FTCConst.AUTH_ADMIN.equals(authCode)) {
			record = record + "<td nowrap>" + inventoryRecord.getProperty("SELL_MONTH") + "</td>";
		}
//		record = record + "<td nowrap>" + FTCCodeUtil.getIsDisp(inventoryRecord.getProperty("WEB_DISP()) + "</td>";
//		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(inventoryRecord.getProperty("WEB_DISP_DATE()) + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td>" + inventoryRecord.getProperty("NAME") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SERIALNO") + "</td>";
//		record = record + "<td nowrap>" + inventoryRecord.getProperty("YEAR") + "</td>";
//		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE()) + "</td>";
		record = record + "<td nowrap>" + sdf.format(date) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("ACCOUNT") + "</td>";
// サービス・事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
		record = record + "<td nowrap>" + inventoryRecord.getProperty("ORDER_DATE") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELLER") + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("ORDER_COST_PRICE")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("ORDER_PAY_DATE") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("BUYER") + "</td>";
//		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_COST_PRICE()) + "</td>";
//		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("SELL_PRICE()) + "</td>";
//		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PROFIT()) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELL_PAY_DATE") + "</td>";
		// 管理以外は表示
		if (!FTCConst.AUTH_ADMIN.equals(authCode)) {
			record = record + "<td nowrap>" + inventoryRecord.getProperty("SELL_MONTH") + "</td>";
		}
}
	  record = record + "</tr>";
	  out.println(record);
		cnt++;
	}
}

String record = "<tr>";
record = record + "<td>Total</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
// 管理は表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
	record = record + "<td>&nbsp;</td>";
}
//record = record + "<td>&nbsp;</td>";
//record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
//record = record + "<td>&nbsp;</td>";
//record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
record = record + "<td>&nbsp;</td>";
// サービス・事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
	record = record + "<td>&nbsp;</td>";
	record = record + "<td>&nbsp;</td>";
	record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(orderCostPrice) + "</td>";
	record = record + "<td>&nbsp;</td>";
	record = record + "<td>&nbsp;</td>";
	//record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(sellCostPrice) + "</td>";
	//record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(sellPrice) + "</td>";
	//record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(profit) + "</td>";
	record = record + "<td>&nbsp;</td>";
			// 管理以外は表示
			if (!FTCConst.AUTH_ADMIN.equals(authCode)) {
				record = record + "<td>&nbsp;</td>";
			}
	record = record + "</tr>";
}
out.println(record);

 %>
</table>
</div>
<div>
<input type="button" name="detail2" value="在庫詳細" onclick="callServer(this.form,4);">
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" name="update2" value="在庫更新" onclick="callServer(this.form,0);">
<input type="button" name="updatepic2" value="写真更新" onclick="callServer(this.form,3);">
<%
}
%>
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<input type="button" name="delete2" value="在庫削除" onclick="callServer(this.form,2);">
<input type="button" name="close2" value="在庫締め" onclick="callServer(this.form,5);">
<%
}
%>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" name="createos" value="注文書出力" onclick="createOrderSheet(this.form);">
<%
}
%>
<%
// サービス・事務以外表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
<input type="button" name="createji" value="見積書・請求書出力" onclick="createJpInvoice(this.form);">
<input type="button" name="createji" value="Proforma Invoice出力" onclick="createProformaInvoice(this.form);">
<%
}
%>
<%
// 一般と事務以外表示
if (!FTCConst.AUTH_NORMAL.equals(authCode) && !FTCConst.AUTH_OFFICE.equals(authCode)) {
%>
<input type="button" name="createex" value="EXCEL出力" onclick="createExcel(this.form);">
<%
}
%>
</div>
</form>
</div>
</body>
</html>