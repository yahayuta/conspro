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
<script type="text/javascript">
<!--
	// 削除
	function callServer(obj,arg) {
		obj.target='_self';
		obj.type.value=arg;
		if (arg==2) {
			if (!confirm('削除してよろしいですか？')) {
				return;
			}
		}
		obj.action='FTCEditRentalInventory';
		obj.submit();
	}

	// 検索
	function search(obj) {
		obj.target='_self';
		obj.action='FTCGetRentalInventoryList';
		obj.submit();
	}
	
	// ソート
	function sortList(obj,sortType) {
    	obj.sort.value=sortType;
		obj.target='_self';
		obj.action='FTCGetRentalInventoryList';
		obj.submit();
	}
	
	// 引合登録
	function preOrder(obj) {
		obj.target='_blank';
		obj.action='RegistPreRentalOrder.jsp';
		obj.submit();
	}
-->
</script>
<title>レンタル在庫一覧</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<form action="FTCEditInventory"  method="POST">
<div><h1>レンタル在庫一覧</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<!--
<div align="left">
<table class="table table-bordered table-striped table-hover table-responsive" border="0">
<tr>
	<td>在庫ID<input type="text" name="ID" value=""></td>
	<td><input type="button" name="srch" value="検索" onclick="search(this.form);"></td>
</table>
</div>
-->
<div align="right">
<input type="button" name="sort0" value="ステータスで並び替え" onclick="sortList(this.form,0);">
<input type="button" name="sort1" value="分類で並び替え" onclick="sortList(this.form,1);">
<input type="button" name="sort2" value="型式で並び替え" onclick="sortList(this.form,2);">
<input type="button" name="sort3" value="メーカーで並び替え" onclick="sortList(this.form,3);">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<%
List listInventoryRecord = (List)request.getAttribute("RentalInventoryRecordList");
if (listInventoryRecord != null) {
out.println("<div id=\"count\">件数:" + listInventoryRecord.size() + "</div>");
}
%>
<div>
<input type="hidden" name="type">
<input type="hidden" name="sort">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" name="detail" value="在庫詳細" onclick="callServer(this.form,4);">
<input type="button" name="update" value="レンタル在庫更新" onclick="callServer(this.form,0);">
<input type="button" name="updatepic" value="写真更新" onclick="callServer(this.form,3);">
<input type="button" name="delete" value="レンタル在庫削除" onclick="callServer(this.form,2);">
<input type="button" name="preorder" value="引合登録" onclick="preOrder(this.form);">
<input type="button" name="preorderlist" value="引合一覧" onclick="window.open('FTCGetPreRentalOrderList?ACCOUNT=<%=account %>', '_blank');">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<th nowrap>選択</th>
<th nowrap>引合</th>
<th nowrap>No</th>
<th nowrap>ID</th>
<th nowrap>ステータス</th>
<!-- <th nowrap>在籍</th> -->
<th nowrap>分類</th>
<th nowrap>分類サブ</th>
<!-- <th nowrap>ID</th> -->
<th nowrap>型式</th>
<th nowrap>号機</th>
<th nowrap>メーカー</th>
<th nowrap>年式</th>
<!-- <th nowrap>登録日</th> -->
<!-- <th nowrap>仕入先コード</th> -->
<th nowrap>仕入先</th>
<th nowrap>日単価</th>
<th nowrap>月単価</th>
<th nowrap>サポート料金</th>
<th nowrap>在庫メモ</th>
</tr>
<%
if (listInventoryRecord != null) {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
int cnt = 1;
	for (Iterator iterator = listInventoryRecord.iterator(); iterator.hasNext();) {
		Entity inventoryRecord = (Entity) iterator.next();
	  	Date date = new Date(inventoryRecord.getKey().getId());
		String record = "<tr>";
		record = record + "<td nowrap><input type=\"radio\" name=\"EDITID\" value=\""+ inventoryRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap><input type=\"checkbox\" name=\"PREID\" value=\""+ inventoryRecord.getKey().getName() +"\"></td>";
		record = record + "<td nowrap>" + cnt + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getKey().getName() + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getRental(inventoryRecord.getProperty("STATUS")) + "</td>";
		//record = record + "<td nowrap>" + inventoryRecord.getProperty("ENROLLMENT") + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeJa(inventoryRecord.getProperty("TYPE")) + "</td>";
		record = record + "<td nowrap>" + FTCCodeUtil.getTypeSubJa(inventoryRecord.getProperty("TYPE_SUB")) + "</td>";
		//record = record + "<td nowrap>" + inventoryRecord.getProperty("NAME") + inventoryRecord.getProperty("SERIALNO") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("NAME") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SERIALNO") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("MANUFACTURER") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("YEAR") + "</td>";
		//record = record + "<td nowrap>" + sdf.format(date) + "</td>";
		//record = record + "<td nowrap>" + inventoryRecord.getProperty("SELLER_CODE") + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("SELLER") + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE_DAY")) + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE_MONTH")) + "</td>";
		record = record + "<td nowrap style=\"text-align:right;\">" + FTCCommonUtil.moneyFormat(inventoryRecord.getProperty("PRICE_SUPPORT")) + "</td>";
		record = record + "<td nowrap>" + inventoryRecord.getProperty("MEMO") + "</td>";
	  	record = record + "</tr>";
	  	out.println(record);
		cnt++;
	}
}
 %>
</table>
</div>
</form>
</div>
</body>
</html>