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
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<script type="text/javascript">
<!--
	function selectData(obj) {
		window.opener.document.getElementById(obj.id.value).value = window.document.getElementById('ID' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.name.value).value = window.document.getElementById('NAME' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.serialno.value).value = window.document.getElementById('SERIALNO' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.priceday.value).value = window.document.getElementById('PRICE_DAY' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.pricemonth.value).value = window.document.getElementById('PRICE_MONTH' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.hours.value).value = window.document.getElementById('HOURS' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.enrollment.value).value = window.document.getElementById('ENROLLMENT' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.pricesupport.value).value = window.document.getElementById('PRICE_SUPPORT' + obj.CNT.value).value;
		window.opener.document.getElementById('DATA_FLG').value = 1;
		window.close();
	}
	
	function search(obj) {
		obj.target='_self';
		obj.action='FTCSeachRentalInventoryList';
		obj.submit();
	}
-->
</script>
<title>レンタル在庫一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form method="POST">
<div><h1>レンタル在庫一覧</h1></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div id="cond">検索条件</div>
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
<td>分類</td>
<td>
<select name="TYPE">
<option value="">選択</option>
<%= FTCConst.COMBO_TYPE %>
</select>
</td>
</tr>
<tr>
<td>分類サブ</td>
<td>
<select name="TYPE_SUB">
<option value="">選択</option>
<%= FTCConst.COMBO_TYPE_SUB %>
</select>
</td>
</tr>
<tr>
<td>メーカー</td>
<td>
<select name="MANUFACTURER">
<option value="">選択</option>
<%= FTCConst.COMBO_MANUFACTURER %>
</select>
</td>
</tr>
<tr>
<td>型式</td>
<td><input type="text" name="NAME" value="" size="50"></td>
</tr>
<tr>
<td>号機</td>
<td><input type="text" name="SERIALNO" value=""></td>
</tr>
</table>
<input type="button" name="srch" value="検索" onclick="search(this.form);">
</div>
<%
List listInventoryRecord = (List)request.getAttribute("RentalInventoryRecordList");
if (listInventoryRecord != null) {
	out.println("<div id=\"count\">件数:" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<input type="hidden" name="id" value="<%= request.getParameter("id") %>">
<input type="hidden" name="name" value="<%= request.getParameter("name") %>">
<input type="hidden" name="serialno" value="<%= request.getParameter("serialno") %>">
<input type="hidden" name="priceday" value="<%= request.getParameter("priceday") %>">
<input type="hidden" name="pricemonth" value="<%= request.getParameter("pricemonth") %>">
<input type="hidden" name="hours" value="<%= request.getParameter("hours") %>">
<input type="hidden" name="enrollment" value="<%= request.getParameter("enrollment") %>">
<input type="hidden" name="pricesupport" value="<%= request.getParameter("pricesupport") %>">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="detail" value="選択" onclick="selectData(this.form,4);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>ステータス</th>
<th nowrap>ID</th>
<th nowrap>分類</th>
<th nowrap>分類サブ</th>
<th nowrap>メーカー</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
<th nowrap>日単価</th>
<th nowrap>月単価</th>
<th nowrap>サポート料金</th>
<th nowrap>稼動時間</th>
<th nowrap>在籍</th>
</tr>
<%
if (listInventoryRecord != null) {
int cnt = 1;
int index = 0;
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"CNT\" value=\""+ index +"\"></td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getRental(inventoryRecord.getProperty("STATUS")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getKey().getName() + "<input type=\"hidden\" id=\"ID" + index + "\" value=\""+ inventoryRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeSubJa(inventoryRecord.getProperty("TYPE_SUB")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("NAME") + "<input type=\"hidden\" id=\"NAME" + index + "\" value=\""+ inventoryRecord.getProperty("NAME") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SERIALNO") + "<input type=\"hidden\" id=\"SERIALNO" + index + "\" value=\""+ inventoryRecord.getProperty("SERIALNO") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("PRICE_DAY") + "<input type=\"hidden\" id=\"PRICE_DAY" + index + "\" value=\""+ inventoryRecord.getProperty("PRICE_DAY") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("PRICE_MONTH") + "<input type=\"hidden\" id=\"PRICE_MONTH" + index + "\" value=\""+ inventoryRecord.getProperty("PRICE_MONTH") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("PRICE_SUPPORT") + "<input type=\"hidden\" id=\"PRICE_SUPPORT" + index + "\" value=\""+ inventoryRecord.getProperty("PRICE_SUPPORT") +"\"></td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("HOURS") + "<input type=\"hidden\" id=\"HOURS" + index + "\" value=\""+ inventoryRecord.getProperty("HOURS") +"\"></td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(inventoryRecord.getProperty("ENROLLMENT")) + "<input type=\"hidden\" id=\"ENROLLMENT" + index + "\" value=\""+ FTCCommonUtil.nullConv(inventoryRecord.getProperty("ENROLLMENT")) +"\"></td>";
		record = record + "</tr>";
		out.println(record);
		cnt++;
		index++;
	}
}
%>
</table>
</div>
<div>
<input type="button" name="detail2" value="選択" onclick="selectData(this.form,4);">
</div>
</form>
</div>
</body>
</html>