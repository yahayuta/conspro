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
<title>顧客情報登録</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {

		if(obj.COUNTRY.value==''){
			alert("「国番号」を入力してください");
			return;
		}

		obj.submit();
	}
-->
</script>
</head>
<body>
<div>
<form action="FTCRegistClient" method="POST">
<div><h1>顧客情報登録</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>接頭辞</td>
<td><input type="text" name="PREFIX" value=""></td>
</tr>
<tr>
<td>業種</td>
<td>
<select name="CLIENT_TYPE">
<%= FTCConst.COMBO_CLIENT_TYPE %>
</select>
</td>
</tr>
<tr>
<td>与信管理</td>
<td>
<select name="CREDIT">
<%= FTCConst.COMBO_CREDIT %>
</select>
</td>
</tr>
<tr>
<td>国番号</td>
<td><input type="text" name="COUNTRY" value=""></td>
</tr>
<tr>
<td>住所</td>
<td><input type="text" name="ADDRESS" value="" size="100"></td>
</tr>
<tr>
<td>郵便番号</td>
<td><input type="text" name="ZIP" value=""></td>
</tr>
<tr>
<td>電話番号</td>
<td><input type="text" name="TEL" value=""></td>
</tr>
<tr>
<td>FAX番号</td>
<td><input type="text" name="FAX" value=""></td>
</tr>
<tr>
<td>メール</td>
<td><input type="text" name="MAIL" value=""></td>
</tr>
<tr>
<td>会社名</td>
<td><input type="text" name="COMPANY" value="" size="100"></td>
</tr>
<tr>
<td>支店営業所名</td>
<td><input type="text" name="OFFICE" value="" size="100"></td>
</tr>
<tr>
<td>担当者1</td>
<td><input type="text" name="NAME" value="" size="100"></td>
</tr>
<tr>
<td>担当者2</td>
<td><input type="text" name="NAME2" value="" size="100"></td>
</tr>
<tr>
<td>担当者3</td>
<td><input type="text" name="NAME3" value="" size="100"></td>
</tr>
<tr>
<td>担当者4</td>
<td><input type="text" name="NAME4" value="" size="100"></td>
</tr>
<tr>
<td>担当者5</td>
<td><input type="text" name="NAME5" value="" size="100"></td>
</tr>
<tr>
<td>備考</td>
<td><textarea name="COMMENT" cols="50" rows="5" maxlength="256"></textarea></td>
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