<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	// 在庫データ取得
	Entity inventoryRecord = (Entity)request.getAttribute("InventoryRecord");

	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>その他機械更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
		obj.submit();
	}

	// 初期化
	function initData() {
		document.updateForm.MANUFACTURER.value = document.updateForm.LOADMANUFACTURER.value;
		document.updateForm.TYPE.value = document.updateForm.LOADTYPE.value;
		document.updateForm.INACCOUNT.value = document.updateForm.LOADINACCOUNT.value;
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
<form action="FTCEditOtherInventory?ACCOUNT=<%= account %>" name="updateForm" method="POST">
<div><h1>その他機械更新</h1></div>
<div>
<input type="button" value="戻る" onclick="history.back();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
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
<td>型式/MODEL</td>
<td><input type="text" name="NAME" value="<%= inventoryRecord.getProperty("NAME") %>" size="50"></td>
</tr>
<tr>
<td>メーカー/MANUFACTURER</td>
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
<td>担当</td>
<td>
<input type="hidden" name="LOADINACCOUNT" value="<%= inventoryRecord.getProperty("ACCOUNT") %>">
<select name="INACCOUNT">
<%= FTCConst.COMBO_ACCOUNT %>
</select>
</td>
</tr>
<tr>
<td>顧客コード</td>
<td><input type="text" name="SELLER_CODE" id="SELLER_CODE" value="<%= FTCCommonUtil.nullConv(inventoryRecord.getProperty("SELLER_CODE")) %>" size="50"><input type="button" value="顧客選択" onclick="clientPop('SELLER_CODE','SELLER')"></td>
</tr>
<tr>
<td>顧客名</td>
<td><input type="text" name="SELLER" id="SELLER" value="<%= inventoryRecord.getProperty("SELLER") %>" size="100"></td>
</tr>
<tr>
<td>メモ</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"><%= inventoryRecord.getProperty("MEMO") %></textarea></td>
</tr>
</table>
<div>
<input type="hidden" name="EDITID" value="<%= inventoryRecord.getKey().getName() %>">
<input type="hidden" name="type" value="1">
<input type="button" value="更新" onclick="callServer(this.form)">
</div>
<div>&nbsp;</div>
<div><h1>作業日報</h1></div>
<div>&nbsp;</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>担当者</th>
<th nowrap>日付</th>
<th nowrap>型式</th>
<th nowrap>号機</th>
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
</div>
</body>
</html>