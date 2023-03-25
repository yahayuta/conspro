<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><%=FTCCommonUtil.getSystemName() %>ログイン</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<form action="FTCLogin"  method="POST">
<div>
<div align="center"><%= FTCCommonUtil.getSystemName() %>ログイン</div>
<div>&nbsp;</div>
<div align="center">
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>アカウント</td>
<td><input type="text" name="ACCOUNT"></td>
</tr>
<tr>
<td>パスワード</td>
<td><input type="password" name="PASSWORD"></td>
</tr>
</table>
</div>
<div align="center"><input type="submit" name="LOGIN" value="ログイン"></div>
</div>
</form>
</body>
</html>