<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="gae.ftc.util.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// 注文引合データ取得
	Entity preRentalOrder = (Entity)request.getAttribute("PreRentalOrder");

	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");
	String msg = (String)request.getSession().getAttribute("MSG");
	request.getSession().setAttribute("MSG", "");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>レンタル注文引合更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
		if (!ckDate(obj.START_DATE.value)) {
			alert('日付の入力が不正です。入力を見直してください');
			return;
		}
		obj.target='_self';
		obj.action='FTCEditPreRentalOrder?ACCOUNT=<%= account %>';
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

	// 顧客ポップ
	function clientPop(code,name,rep,tel){
		window.open("/ClientListPopUp.jsp?type=0&code="+code+"&name="+name+"&rep="+rep+"&tel="+tel+"&ACCOUNT=<%=account %>", 'clientpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}
-->
</script>
</head>
<body onload="initData()">
<div>
<form name="updateForm" method="POST">
<div><h1>レンタル注文引合更新</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>レンタル在庫ID</td>
<td><%= preRentalOrder.getProperty("RENTAL_INVENTORY_ID") %></td>
</tr>
<tr>
<td>レンタル開始希望日</td>
<td><input type="text" name="START_DATE" value="<%= preRentalOrder.getProperty("START_DATE") %>">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>顧客コード</td>
<td><input type="text" name="CLIENT_CODE" id="CLIENT_CODE" value="<%= preRentalOrder.getProperty("CLIENT_CODE") %>"><input type="button" value="顧客選択" onclick="clientPop('CLIENT_CODE','CLIENT_NAME','CLIENT_REP','CLIENT_TEL')"></td>
</tr>
<tr>
<td>顧客名</td>
<td><input type="text" name="CLIENT_NAME" id="CLIENT_NAME" value="<%= preRentalOrder.getProperty("CLIENT_NAME") %>"></td>
</tr>
<tr>
<td>顧客担当者</td>
<td><input type="text" name="CLIENT_REP" id="CLIENT_REP" value="<%= preRentalOrder.getProperty("CLIENT_REP") %>"></td>
</tr>
<tr>
<td>顧客TEL</td>
<td><input type="text" name="CLIENT_TEL" id="CLIENT_TEL" value="<%= preRentalOrder.getProperty("CLIENT_TEL") %>"></td>
</tr>
<tr>
<td>備考欄</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"><%= preRentalOrder.getProperty("MEMO") %></textarea></td>
</tr>
</table>
</div>
<div>
<input type="hidden" name="PREID" value="<%= preRentalOrder.getKey().getName() %>">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="hidden" name="type" value="1">
<input type="button" name="update" value="更新" onclick="callServer(this.form)">
</div>
</form>
</div>
</body>
</html>