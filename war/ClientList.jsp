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
	function callServer(obj,arg) {
		obj.type.value=arg;
		if (arg==2) {
			if (!confirm('削除してよろしいですか？')) {
				return;
			}
		}
		obj.submit();
	}

	function search(obj) {
		obj.target='_self';
		obj.action='FTCSeachClientList';
		obj.submit();
	}

	function createExcel(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelClientAll';
		obj.submit();
	}
-->
</script>
<title>顧客一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditClient"  method="POST">
<div><h1>顧客一覧</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
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
<input type="hidden" name="type">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="update" value="顧客情報更新" onclick="callServer(this.form,0);">
<input type="button" name="delete" value="顧客削除" onclick="callServer(this.form,2);">
<input type="button" name="createex" value="EXCEL出力" onclick="createExcel(this.form);">
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
<th nowrap>住所</th>
<th nowrap>郵便番号</th>
<th nowrap>電話番号</th>
<th nowrap>FAX番号</th>
<th nowrap>メール</th>
<th nowrap>備考</th>
</tr>
<%
List listClientRecord = (List)request.getAttribute("ClientRecordList");
if (listClientRecord != null) {
	for (Iterator iterator = listClientRecord.iterator(); iterator.hasNext();) {
		Entity clientRecord = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"EDITID\" value=\""+ clientRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("PREFIX") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getKey().getName() + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getClientType(clientRecord.getProperty("CLIENT_TYPE")) + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getCredit(clientRecord.getProperty("CREDIT")) + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("COUNTRY") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("SEQ") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("COMPANY") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("OFFICE") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("NAME") + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME2")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME3")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME4")) + "</td>";
		record = record + "<td nowrap>" + FTCCommonUtil.nullConv(clientRecord.getProperty("NAME5")) + "</td>";
		record = record + "<td>" + clientRecord.getProperty("ADDRESS") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("ZIP") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("TEL") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("FAX") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("MAIL") + "</td>";
		record = record + "<td nowrap>" + clientRecord.getProperty("COMMENT") + "</td>";
	  record = record + "</tr>";
	  out.println(record);
	}
}
%>
</table>
</div>
</form>
</div>
</body>
</html>