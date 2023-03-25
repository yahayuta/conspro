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
	String msg = (String)request.getSession().getAttribute("MSG");
	request.getSession().setAttribute("MSG", "");
	
	Long total = (Long)request.getAttribute("total");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script type="text/javascript">
<!--

	// 注文売上一覧取得
	function getOrderSalesList(obj) {
		obj.target='_self';
		obj.action='FTCGetRentalOrderSalesList';
		obj.submit();
	}


	// 注文売上削除
	function deleteOrderSales(obj) {
		if (!confirm('削除してよろしいですか？')) {
			return;
		}
		obj.target='_self';
		obj.action='FTCEditRentalOrderSales?type=3';
		obj.submit();
	}

	// 注文売上一覧出力
	function createRentalOrderSalesList(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelRentalOrderSalesList';
		obj.submit();
	}
	
	// 初期化
	function initData() {
		document.listForm.SALES_MONTH.value = "<%= request.getParameter("SALES_MONTH") %>";
	}
-->
</script>
<title>レンタル注文売上一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body onload="initData()">
<form action="FTCEditInventory" name="listForm" method="POST">
<div>
<div><h1>レンタル注文売上一覧</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<%
List rentalOrderSalesList = (List)request.getAttribute("RentalOrderSalesList");
if (rentalOrderSalesList != null) {
out.println("<div id=\"count\">件数:" + rentalOrderSalesList.size() + "</div>");
}
%>
<div>
<select name="SALES_MONTH" onchange="getOrderSalesList(this.form)">
		<option value="">売上月</option>
		<%= FTCCommonUtil.buildOptions(FTCConst.SELL_MONTH_SET) %>
</select>
</div>
<div>
<input type="hidden" name="type">
<input type="hidden" name="sort">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="delete" value="売上削除" onclick="deleteOrderSales(this.form);">
<input type="button" name="list" value="売上出力" onclick="createRentalOrderSalesList(this.form);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>No</th>
<th nowrap>売上月</th>
<th nowrap>売上先</th>
<th nowrap>売上金額</th>
<th nowrap>在庫ID</th>
</tr>
<%
if (rentalOrderSalesList != null) {
int cnt = 1;
	for (Iterator iterator = rentalOrderSalesList.iterator(); iterator.hasNext();) {
		Entity rentalOrderSales = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"EDITID\" value=\""+ rentalOrderSales.getKey().getName() + "\"></td>";
		record = record + "<td nowrap><a href=\"FTCEditRentalOrder?type=6&EDITID=" + rentalOrderSales.getProperty("RENTAL_ORDER_ID") + "&ACCOUNT=" + account + "\" target=\"detail\">" + cnt + "</td>";
		record = record + "<td nowrap>" + rentalOrderSales.getProperty("SALES_MONTH") + "</td>";
		record = record + "<td nowrap>" + rentalOrderSales.getProperty("CLIENT_NAME") + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(rentalOrderSales.getProperty("AMOUNT")) + "</td>";
		record = record + "<td nowrap>" + rentalOrderSales.getProperty("RENTAL_INVENTORY_CODE") + "</td>";
		record = record + "</tr>";
		out.println(record);
		cnt++;
	}
}
String record = "<tr>";
record = record + "<td nowrap>売上合計</td>";
record = record + "<td nowrap>&nbsp;</td>";
record = record + "<td nowrap>&nbsp;</td>";
record = record + "<td nowrap>&nbsp;</td>";
record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(total) + "</td>";
record = record + "<td nowrap>&nbsp;</td>";
record = record + "</tr>";
out.println(record);
 %>
</table>
</div>
</div>
</form>
</body>
</html>