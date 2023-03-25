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
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>在庫削除履歴一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditInventoryHistory"  method="POST">
<div><h1>在庫削除履歴一覧</h1></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<%
List listInventoryRecord = (List)request.getAttribute("InventoryRecordList");
if (listInventoryRecord != null) {
out.println("<div id=\"count\">在庫削除履歴数:" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
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
 %>
</table>
</div>
</form>
</div>
</body>
</html>