<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>写真更新</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCPictureUploadRental"  method="POST" enctype="multipart/form-data">
<div><h1>写真更新</h1></div>
<div><input type="button" value="戻る" onclick="history.back();"></div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>メーカー</td>
<td><%= request.getParameter("MANUFACTURER") %></td>
</tr>
<tr>
<td>型式</td>
<td><%= request.getParameter("NAME") %></td>
</tr>
<tr>
<td>画像</td>
<td><input type="file" name="FILE1"></td>
</tr>
</table>
</div>
<div><input type="submit" value="更新"></div>
</form>
</div>
</body>
</html>