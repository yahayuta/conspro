<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="gae.ftc.util.*" %>
<%
	Entity dailyReportRecord = (Entity)request.getAttribute("DailyReportRecord");

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
<title>日報情報更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
		if (!ckDate(obj.WORK_DATE.value)) {
			alert('日付の入力が不正です。入力を見直してください');
			return;
		}
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

	// 初期化
	function initData() {
		document.dailyReportForm.WORKER.value = document.dailyReportForm.LOADWORKER.value;
	}

	// 販売在庫ポップ
	function invPop(name,serialno,hours){
		window.open("/InventoryListPopUp.jsp?name="+name+"&serialno="+serialno+"&hours="+hours+"&ACCOUNT=<%=account %>", 'invPop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}

	// レンタル在庫ポップ
	function invRentPop(name,serialno,hours){
		window.open("/FTCSeachRentalInventoryList?SEARCH_TYPE=0&id=dummy&name="+name+"&serialno="+serialno+"&pricemonth=dummy&priceday=dummy&hours="+hours+"&enrollment=dummy&ACCOUNT=<%=account %>&TYPE=0", 'invpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}
	
	// その他機械ポップ
	function invOtherPop(name,serialno,hours,clientcode,clientname){
		window.open("/FTCSeachOtherInventoryList?SEARCH_TYPE=0&name="+name+"&serialno="+serialno+"&hours="+hours+"&clientcode="+clientcode+"&clientname="+clientname+"&ACCOUNT=<%=account %>", 'invPop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
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
<form action="FTCEditDailyReport?ACCOUNT=<%= account %>" name="dailyReportForm" method="POST">
<div><h1>日報情報更新</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div>
<input type="button" value="戻る" onclick="location.href='DailyReportList.jsp?ACCOUNT=<%=account %>'">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>担当者</td>
<td>
<input type="hidden" name="LOADWORKER" value="<%= dailyReportRecord.getProperty("WORKER") %>">
<select name="WORKER">
<%= FTCCommonUtil.getEmployeeListRankNormalOptionTag() %>
</select>
</td>
</tr>
<tr>
<td>日付</td>
<td><input type="text" name="WORK_DATE" value="<%= dailyReportRecord.getProperty("WORK_DATE") %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>型式</td>
<td>
	<input type="text" name="NAME" id="NAME" value="<%= dailyReportRecord.getProperty("NAME") %>">
	<input type="button" value="販売在庫選択" onclick="invPop('NAME','SERIALNO','HOURS')">
	<input type="button" value="レンタル在庫選択" onclick="invRentPop('NAME','SERIALNO','HOURS')">
	<input type="button" value="その他機械選択" onclick="invOtherPop('NAME','SERIALNO','HOURS','CLIENT_CODE','CLIENT_NAME')">
</td>
</tr>
<tr>
<td>号機</td>
<td><input type="text" name="SERIALNO" id="SERIALNO" value="<%= dailyReportRecord.getProperty("SERIALNO") %>"></td>
</tr>
<tr>
<td>稼働時間</td>
<td><input type="text" name="HOURS" id="HOURS" value="<%= FTCCommonUtil.nullConv(dailyReportRecord.getProperty("HOURS")) %>"></td>
</tr>
<tr>
<td>顧客コード</td>
<td><input type="text" name="CLIENT_CODE" id="CLIENT_CODE" value="<%= FTCCommonUtil.nullConv(dailyReportRecord.getProperty("CLIENT_CODE")) %>" size="50"><input type="button" value="顧客選択" onclick="clientPop('CLIENT_CODE','CLIENT_NAME')"></td>
</tr>
<tr>
<td>顧客名</td>
<td><input type="text" name="CLIENT_NAME" id="CLIENT_NAME" value="<%= FTCCommonUtil.nullConv(dailyReportRecord.getProperty("CLIENT_NAME")) %>" size="100"></td>
</tr>
<tr>
<td>作業内容</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"><%= dailyReportRecord.getProperty("MEMO") %></textarea></td>
</tr>
</table>
<div>
<input type="hidden" name="EDITID" value="<%= dailyReportRecord.getKey().getName() %>">
<input type="hidden" name="type" value="1">
<input type="hidden" id="dummy">
<input type="hidden" name="DATA_FLG" id="DATA_FLG" value="<%= dailyReportRecord.getProperty("DATA_FLG") %>">
<input type="button" value="更新" onclick="callServer(this.form)">
</div>
</div>
</form>
</div>
</body>
</html>