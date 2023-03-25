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
	String type = request.getParameter("type");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script type="text/javascript">
<!--
	// 注文詳細
	function detailOrder(obj) {
		obj.target='detail';
		obj.action='FTCEditRentalOrder?type=6';
		obj.submit();
	}

	// 注文編集
	function editeOrder(obj) {
		obj.target='edit';
		obj.action='FTCEditRentalOrder?type=0';
		obj.submit();
	}

	// 売上確定
	function commitOrderSales(obj) {
		if (!ckYM(obj.SALES_MONTH.value)) {
			alert('売上月の入力が不正です。入力を見直してください');
			return;
		}
		obj.target='_self';
		obj.action='FTCEditRentalOrder?type=2';
		obj.submit();
	}

	// 注文削除
	function deleteOrder(obj) {
		if (!confirm('削除してよろしいですか？')) {
			return;
		}
		obj.target='_self';
		obj.action='FTCEditRentalOrder?type=3';
		obj.submit();
	}

	// 注文締め
	function closeOrder(obj) {
		if (!confirm('注文締めしてよろしいですか？')) {
			return;
		}
		obj.target='_self';
		obj.action='FTCEditRentalOrder?type=4';
		obj.submit();
	}

	// 注文締め戻し
	function backClosedOrder(obj) {
		if (!confirm('注文締め戻しをしてよろしいですか？')) {
			return;
		}
		obj.target='_self';
		obj.action='FTCEditRentalOrder?type=5';
		obj.submit();
	}

	// 注文締め一覧
	function searchClosedOrder(obj) {
		obj.target='_self';
		obj.action='FTCGetRentalOrderList?type=closed';
		obj.submit();
	}

	// 出庫・返却伝票出力
	function createOrderSheet(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelRentalOrderSheet?type=all';
		obj.submit();
	}

	// ソート
	function sortList(obj,sortType) {
    	obj.sort.value=sortType;
		obj.target='_self';
		obj.action='FTCGetRentalOrderList';
		obj.submit();
	}
	
	// 年月チェック
	function ckYM(datestr) {
		if (datestr == "") {
			return false;
		}
		if(!datestr.match(/^\d{4}\/\d{2}$/)){
			return false;
		}
		return true;
	}
-->
</script>
<title>レンタル注文一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<form action="FTCEditInventory"  method="POST">
<div>
<div><h1>レンタル注文一覧</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<%
List rentalOrderList = (List)request.getAttribute("RentalOrderRecordList");
if (rentalOrderList != null) {
out.println("<div id=\"count\">件数:" + rentalOrderList.size() + "</div>");
}
%>
<div align="right">
<input type="button" name="sort2" value="型式で並び替え" onclick="sortList(this.form,2);">
</div>
<div>
<% if (!"closed".equals(type)) { %>
<input type="button" name="detail" value="注文詳細" onclick="detailOrder(this.form);">
<input type="button" name="update" value="注文更新" onclick="editeOrder(this.form);">
<input type="button" name="all" value="出庫・返却伝票出力" onclick="createOrderSheet(this.form);">
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<input type="button" name="delete" value="注文削除" onclick="deleteOrder(this.form);">
<%
}
%>
<input type="button" name="searchclosed" value="注文締め一覧" onclick="searchClosedOrder(this.form);">
<% } else { %>
<input type="button" name="reverse" value="注文締め戻し" onclick="backClosedOrder(this.form);">
<% } %>
</div>
<input type="hidden" name="type">
<input type="hidden" name="sort">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>No</th>
<th nowrap>最終請求日</th>
<th nowrap>締め日</th>
<!-- <th nowrap>在籍</th> -->
<th nowrap>在庫ID</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
<th nowrap>注文区分</th>
<th nowrap>出庫日</th>
<th nowrap>返却日</th>
<th nowrap>レンタル開始日</th>
<th nowrap>レンタル終了日</th>
<th nowrap>金額（税別）</th>
<th nowrap>顧客コード</th>
<th nowrap>顧客名</th>
</tr>
<%
if (rentalOrderList != null) {
int cnt = 1;
	for (Iterator iterator = rentalOrderList.iterator(); iterator.hasNext();) {
		Entity rentalOrder = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"EDITID\" value=\""+ rentalOrder.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + cnt + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(rentalOrder.getProperty("REQUEST_DATE")) + "</td>";
		record = record + "<td nowrap"+ FTCCommonUtil.checkBillingCondition(rentalOrder) +">" + FTCCommonUtil.nullConv(rentalOrder.getProperty("CLOSE_DATE")) + "</td>";
		//record = record + "<td nowrap>" + FTCCommonUtil.nullConv(rentalOrder.getProperty("ENROLLMENT")) + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("NAME") + rentalOrder.getProperty("SERIALNO") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("NAME") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("SERIALNO") + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getOrderType(rentalOrder.getProperty("ORDER_TYPE")) + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("OUT_DATE") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("IN_DATE") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("START_DATE") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("END_DATE") + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(rentalOrder.getProperty("AMOUNT")) + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("CLIENT_CODE") + "</td>";
		record = record + "<td nowrap>" + rentalOrder.getProperty("CLIENT_NAME") + "</td>";
		record = record + "</tr>";
		out.println(record);
		cnt++;
	}
}
 %>
</table>
</div>
<div>
<% if (!"closed".equals(type)) { %>
<input type="button" name="detail" value="注文詳細" onclick="detailOrder(this.form);">
<input type="button" name="update" value="注文更新" onclick="editeOrder(this.form);">
<input type="button" name="all" value="出庫・返却伝票出力" onclick="createOrderSheet(this.form);">
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<input type="button" name="delete" value="注文削除" onclick="deleteOrder(this.form);">
<%
}
%>
<input type="button" name="searchclosed" value="注文締め一覧" onclick="searchClosedOrder(this.form);">
<% } else { %>
<input type="button" name="reverse" value="注文締め戻し" onclick="backClosedOrder(this.form);">
<% } %>
</div>
</div>
</form>
</body>
</html>