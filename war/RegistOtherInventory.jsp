<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
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
<title>その他機械登録</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
		obj.submit();
	}

	// 顧客ポップ
	function clientPop(code,name){
		window.open("/ClientListPopUp.jsp?type=0&code="+code+"&name="+name+"&ACCOUNT=<%=account %>", 'clientpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}

-->
</script>
</head>
<body>
<div>
<form action="FTCRegistOtherInventory"  method="POST">
<div><h1>その他機械登録</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>分類</td>
<td>
<select name="TYPE">
<%= FTCConst.COMBO_TYPE %>
</select>
</td>
</tr>
<tr>
<td>メーカー</td>
<td>
<select name="MANUFACTURER">
<%= FTCConst.COMBO_MANUFACTURER %>
</select>
</td>
</tr>
<tr>
<td>型式</td>
<td><input type="text" name="NAME" value="" size="50"></td>
</tr>
<tr>
<td>年式</td>
<td><input type="text" name="YEAR" value=""></td>
</tr>
<tr>
<td>号機</td>
<td><input type="text" name="SERIALNO" value=""></td>
</tr>
<tr>
<td>稼働時間</td>
<td><input type="text" name="HOURS" value=""></td>
</tr>
<tr>
<td>詳細／仕様</td>
<td><input type="text" name="OTHER_JA" value="" size="100"></td>
</tr>
<tr>
<td>担当</td>
<td>
<select name="INACCOUNT">
<%= FTCConst.COMBO_ACCOUNT %>
</select>
</td>
</tr>
<tr>
<td>顧客コード</td>
<td><input type="text" name="SELLER_CODE" id="SELLER_CODE" value="" size="50"><input type="button" value="顧客選択" onclick="clientPop('SELLER_CODE','SELLER')"></td>
</tr>
<tr>
<td>顧客名</td>
<td><input type="text" name="SELLER" id="SELLER" value="" size="100"></td>
</tr>
<tr>
<td>メモ</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"></textarea></td>
</tr>
</table>
</div>
<div>
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" value="登録" onclick="callServer(this.form)">
</div>
</form>
</div>
</body>
</html>