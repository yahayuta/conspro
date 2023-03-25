<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// 認証チェック
	FTCAuthUtil.isLogin(request, response);
	String account = request.getParameter("ACCOUNT");
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
<title>日報登録</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCRegistDailyReport" method="POST" >
<div><h1>日報登録</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>担当者</td>
<td>
<select name="WORKER">
<%= FTCCommonUtil.getEmployeeListRankNormalOptionTag() %>
</select>
</td>
</tr>
<tr>
<td>日付</td>
<td><input type="text" name="WORK_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>型式</td>
<td>
	<input type="text" name="NAME" id="NAME" value="">
	<input type="button" value="販売在庫選択" onclick="invPop('NAME','SERIALNO','HOURS')">
	<input type="button" value="レンタル在庫選択" onclick="invRentPop('NAME','SERIALNO','HOURS')">
	<input type="button" value="その他機械選択" onclick="invOtherPop('NAME','SERIALNO','HOURS','CLIENT_CODE','CLIENT_NAME')">
</td>
</tr>
<tr>
<td>号機</td>
<td><input type="text" name="SERIALNO" id="SERIALNO" value=""></td>
</tr>
<tr>
<td>稼働時間</td>
<td><input type="text" name="HOURS" id="HOURS" value=""></td>
</tr>
<tr>
<td>顧客コード</td>
<td><input type="text" name="CLIENT_CODE" id="CLIENT_CODE" value="" size="50"><input type="button" value="顧客選択" onclick="clientPop('CLIENT_CODE','CLIENT_NAME')"></td>
</tr>
<tr>
<td>顧客名</td>
<td><input type="text" name="CLIENT_NAME" id="CLIENT_NAME" value="" size="100"></td>
</tr>
<tr>
<td>作業内容</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"></textarea></td>
</tr>
</table>
</div>
<div>
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="hidden" id="dummy">
<input type="hidden" name="DATA_FLG" id="DATA_FLG">
<input type="button" value="登録" onclick="callServer(this.form)">
</div>
</form>
</div>
</body>
</html>