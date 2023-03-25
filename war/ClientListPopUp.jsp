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
<script type="text/javascript">
<!--
	function selectData(obj) {
		window.opener.document.getElementById(obj.code.value).value = window.document.getElementById('CLIENT_CODE' + obj.CNT.value).value;
		window.opener.document.getElementById(obj.name.value).value = window.document.getElementById('COMPANY_OFFICE' + obj.CNT.value).value;
		if (obj.rep.value != 'null' && obj.tel.value != 'null') {
			window.opener.document.getElementById(obj.rep.value).value = window.document.getElementById('NAME' + obj.CNT.value).value;
			window.opener.document.getElementById(obj.tel.value).value = window.document.getElementById('TEL' + obj.CNT.value).value;			
		}
		window.close();
	}

	function search(obj) {
		obj.target='_self';
		obj.action='FTCSeachClientList';
		obj.submit();
	}
-->
</script>
<title>顧客選択</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form method="POST">
<div><h1>顧客選択</h1></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div id="cond">検索条件</div>
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
	<td>接頭辞</td>
	<td>
		<input type="text" name="PREFIX" value="">
	</td>
</tr>
<tr>
	<td>業種</td>
	<td>
		<select name="CLIENT_TYPE">
			<option value="">選択</option>
			<%= FTCConst.COMBO_CLIENT_TYPE %>
		</select>
	</td>
</tr>
<tr>
	<td>与信管理</td>
	<td>
		<select name="CREDIT">
			<option value="">選択</option>
			<%= FTCConst.COMBO_CREDIT %>
		</select>
	</td>
</tr>
<tr>
	<td>国番号</td>
	<td>
		<input type="text" name="COUNTRY" value="">
	</td>
</tr>
<tr>
	<td>会社名</td>
	<td>
		<input type="text" name="COMPANY" value="">
	</td>
</tr>
<tr>
	<td>支店営業所</td>
	<td>
		<input type="text" name="OFFICE" value="">
	</td>
</tr>
<tr>
	<td>担当者</td>
	<td>
		<input type="text" name="NAME" value="">
		<input type="text" name="NAME2" value="">
		<input type="text" name="NAME3" value="">
		<input type="text" name="NAME4" value="">
		<input type="text" name="NAME5" value="">
	</td>
</tr>
<tr>
	<td>電話番号</td>
	<td>
		<input type="text" name="TEL" value="">
	</td>
</tr>
<tr>
	<td>郵便番号</td>
	<td>
		<input type="text" name="ZIP" value="">
	</td>
</tr>
<tr>
	<td><input type="button" name="srch" value="検索" onclick="search(this.form);"></td>
</tr>
</table>
</div>
<div>&nbsp;</div>
<div>
<input type="hidden" name="type" value="0">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="hidden" name="code" value="<%= request.getParameter("code") %>">
<input type="hidden" name="name" value="<%= request.getParameter("name") %>">
<input type="hidden" name="rep" value="<%= request.getParameter("rep") %>">
<input type="hidden" name="tel" value="<%= request.getParameter("tel") %>">
<input type="button" name="select" value="選択" onclick="selectData(this.form);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>接頭辞</th>
<th nowrap>顧客コード</th>
<th nowrap>業種</th>
<th nowrap>与信管理</th>
<th nowrap>国番号</th>
<th nowrap>顧客番号</th>
<th nowrap>会社名</th>
<th nowrap>支店営業所</th>
<th nowrap>担当者1</th>
<th nowrap>担当者2</th>
<th nowrap>担当者3</th>
<th nowrap>担当者4</th>
<th nowrap>担当者5</th>
</tr>
<%
List listClientRecord = (List)request.getAttribute("ClientRecordList");
if (listClientRecord != null) {
	int index = 0;
	for (Iterator iterator = listClientRecord.iterator(); iterator.hasNext();) {
		Entity clientRecord = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"CNT\" value=\"" + index +"\"></td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("PREFIX") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getKey().getName() + "<input type=\"hidden\" id=\"CLIENT_CODE" + index + "\" value=\""+ clientRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getClientType(clientRecord.getProperty("CLIENT_TYPE")) + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getCredit(clientRecord.getProperty("CREDIT")) + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("COUNTRY") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("SEQ") + "</td>";
		String comanyOffice = clientRecord.getProperty("COMPANY") +" " + clientRecord.getProperty("OFFICE");
		record = record + "<td nowrap>" + clientRecord.getProperty("COMPANY") + "<input type=\"hidden\" id=\"COMPANY_OFFICE" + index + "\" value=\""+ comanyOffice.trim() +"\"></td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("OFFICE") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("NAME") + "<input type=\"hidden\" id=\"NAME" + index + "\" value=\""+ clientRecord.getProperty("NAME") +"\"></td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME2")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME3")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME4")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME5")) + "</td>";
		record = record + "<input type=\"hidden\" id=\"TEL" + index + "\" value=\""+ clientRecord.getProperty("TEL") +"\">";
		record = record + "</tr>";
		out.println(record);
		index++;
	}
}
%>
</table>
</div>
</form>
</div>
</body>
</html>