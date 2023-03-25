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
		obj.action='FTCSeachDailyReportList';
		obj.submit();
	}

	function createExcel(obj) {
		obj.target='_self';
		obj.action='FTCLoadExcelDailyReportAll';
		obj.submit();
	}

	// 顧客ポップ
	function clientPop(code,name){
		window.open("/ClientListPopUp.jsp?type=0&code="+code+"&name="+name+"&ACCOUNT=<%=account %>", 'clientpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}

	// 修理履歴ポップ
	function historyPop(obj){
		window.open("/FTCEditDailyReport?type=3&EDITID="+obj.EDITID.value+"&history=1&ACCOUNT=<%=account %>", 'historypop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}
-->
</script>
<title>日報一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditDailyReport"  method="POST">
<div><h1>日報一覧</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div id="cond">検索条件</div>
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
	<td>担当者</td>
	<td>
		<select name="WORKER">
			<option value="">選択</option>
			<%= FTCCommonUtil.getEmployeeListRankNormalOptionTag() %>
		</select>
	</td>
</tr>
<tr>
	<td>型式</td>
	<td><input type="text" name="NAME" value=""></td>
</tr>
<tr>
	<td>日付</td>
	<td><input type="text" name="WORK_DATE" value=""></td>
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
	<td><input type="button" name="srch" value="検索" onclick="search(this.form);"></td>
</tr>
</table>
</div>
<div>&nbsp;</div>
<div>
<input type="hidden" name="type">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="update" value="日報更新" onclick="callServer(this.form,0);">
<input type="button" name="delete" value="日報削除" onclick="callServer(this.form,2);">
<input type="button" name="createex" value="EXCEL出力" onclick="createExcel(this.form);">
<input type="button" name="history" value="整備履歴" onclick="historyPop(this.form);">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive" style="table-layout:fixed;width:100%;">
<tr>
<th width="30">選択</th>
<th width="50">担当者</th>
<th width="100">日付</th>
<th width="80">区分</th>
<th width="100">型式</th>
<th width="120">号機</th>
<th width="60">稼働時間</th>
<th width="100">顧客コード</th>
<th width="150">顧客名</th>
<th>作業内容</th>
</tr>
<%
List listDailyReport = (List)request.getAttribute("DailyReportList");
if (listDailyReport != null) {
	for (Iterator iterator = listDailyReport.iterator(); iterator.hasNext();) {
		Entity dailyReport = (Entity) iterator.next();
		String record = "<tr>";
		record = record + "<td><input type=\"radio\" name=\"EDITID\" value=\""+ dailyReport.getKey().getName() +"\"></td>";
		record = record + "<td>" + dailyReport.getProperty("WORKER") + "</td>";
		record = record + "<td>" + dailyReport.getProperty("WORK_DATE") + "</td>";
		record = record + "<td>" + FTCCodeUtil.getDailyReportType(dailyReport.getProperty("DATA_FLG")) + "</td>";
		record = record + "<td>" + dailyReport.getProperty("NAME") + "</td>";
		record = record + "<td>" + dailyReport.getProperty("SERIALNO") + "</td>";
		record = record + "<td>" + FTCCommonUtil.nullConv(dailyReport.getProperty("HOURS")) + "</td>";
		record = record + "<td>" + FTCCommonUtil.nullConv(dailyReport.getProperty("CLIENT_CODE")) + "</td>";
		record = record + "<td>" + FTCCommonUtil.nullConv(dailyReport.getProperty("CLIENT_NAME")) + "</td>";
		record = record + "<td>" + dailyReport.getProperty("MEMO") + "</td>";
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