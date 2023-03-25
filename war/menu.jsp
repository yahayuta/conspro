<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// 認証チェック
	FTCAuthUtil.isLogin(request, response);
	String account = request.getParameter("ACCOUNT");
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");
	String accName = (String)(request.getSession()).getAttribute("NAME");
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><%= FTCCommonUtil.getSystemName() %>メニュー</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
</head>
<body>
<div>
<div align="center" id="menuhead"><%=FTCCommonUtil.getSystemName() %>メニュー</div>
<div>&nbsp;</div>
<div align="center" id="menuhead"><%= accName %>さん（権限レベル：<%= FTCCodeUtil.getAuthName(authCode) %>）</div>
<div>&nbsp;</div>
<div align="center">
<table class="table table-bordered table-striped table-hover table-responsive">
	<tr>
		<td colspan="2" id="menucat"><center>販売管理</center></td>
	</tr>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="RegistInventory.jsp?ACCOUNT=<%=account %>" target="reginv">在庫登録</a></td>
		<td>在庫を新規登録します</td>
	</tr>
<%
}
%>
	<tr>
		<td id="menu"><a href="FTCGetInventoryList?ACCOUNT=<%=account %>&MENU=1" target="invlist1">在庫一覧</a></td>
		<td>在庫の一覧を参照します</td>
	</tr>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="FTCLoadExcelForSellInv">販売用在庫一覧出力</a></td>
		<td>販売用在庫一覧を出力します</td>
	</tr>
<%
}
%>
<%
// 管理者権限・経理権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode) || FTCConst.AUTH_ACCT.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="FTCLoadExcelForInvCount">棚卸在庫一覧出力</a></td>
		<td>棚卸在庫一覧を出力します</td>
	</tr>
<%
}
%>
<%
// 事務・サービス以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode) && !FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="FTCGetOldInventoryList?ACCOUNT=<%=account %>&MENU=1" target="invlist2">締め在庫一覧</a></td>
		<td>締め在庫の一覧を参照します</td>
	</tr>
	<tr>
		<td id="menu"><a href="FTCGetInventoryHistoryList?ACCOUNT=<%=account %>" target="invlist3">在庫削除履歴一覧</a></td>
		<td>在庫の削除履歴一覧を参照します</td>
	</tr>
<%
}
%>
	<tr>
		<td colspan="2" id="menucat"><center>レンタル管理</center></td>
	</tr>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="RegistRentalInventory.jsp?ACCOUNT=<%=account %>" target="regrinv">レンタル在庫登録</a></td>
		<td>レンタル在庫登録を新規登録します</td>
	</tr>
<%
}
%>
	<tr>
		<td id="menu"><a href="FTCGetRentalInventoryList?ACCOUNT=<%=account %>" target="rentinvlist">レンタル在庫一覧</a></td>
		<td>レンタル在庫の一覧を参照します</td>
	</tr>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="RegistRentalOrder.jsp?ACCOUNT=<%=account %>" target="regodr">レンタル注文登録</a></td>
		<td>レンタル注文登録を新規登録します</td>
	</tr>
<%
}
%>
	<tr>
		<td id="menu"><a href="FTCGetRentalOrderList?ACCOUNT=<%=account %>" target="rentodrlist">レンタル注文一覧</a></td>
		<td>レンタル注文の一覧を参照します</td>
	</tr>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="FTCGetRentalOrderSalesList?ACCOUNT=<%=account %>" target="rentodrslslist">レンタル注文売上一覧</a></td>
		<td>レンタル注文売上の一覧を参照します</td>
	</tr>
<%
}
%>
	<tr>
		<td colspan="2" id="menucat"><center>整備管理</center></td>
	</tr>
	<tr>
		<td id="menu"><a href="RegistDailyReport.jsp?ACCOUNT=<%=account %>" target="drreg">日報登録</a></td>
		<td>日報を登録します</td>
	</tr>
	<tr>
		<td id="menu"><a href="DailyReportList.jsp?ACCOUNT=<%=account %>" target="dlist">日報一覧</a></td>
		<td>日報一覧を参照します</td>
	</tr>
	<tr>
		<td id="menu"><a href="RegistOtherInventory.jsp?ACCOUNT=<%=account %>" target="regotherinv">その他機械登録</a></td>
		<td>その他機械を新規登録します</td>
	</tr>
	<tr>
		<td id="menu"><a href="FTCSeachOtherInventoryList?ACCOUNT=<%=account %>" target="otherlist">その他機械一覧</a></td>
		<td>その他機械一覧を参照します</td>
	</tr>
<%
// サービス以外は表示
if (!FTCConst.AUTH_SERVICE.equals(authCode)) {
%>
	<tr>
		<td colspan="2" id="menucat"><center>マスタ管理</center></td>
	</tr>
	<tr>
		<td id="menu"><a href="RegistClient.jsp?ACCOUNT=<%=account %>" target="clientreg">顧客情報登録</a></td>
		<td>顧客を登録します</td>
	</tr>
	<tr>
		<td id="menu"><a href="ClientList.jsp?ACCOUNT=<%=account %>" target="clientlist">顧客一覧</a></td>
		<td>顧客一覧を参照します</td>
	</tr>
<%
}
%>
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
	<tr>
		<td id="menu"><a href="RegistEmployee.jsp?ACCOUNT=<%=account %>" target="userreg">ユーザー情報登録</a></td>
		<td>システム利用ユーザーを登録します</td>
	</tr>
	<tr>
		<td id="menu"><a href="FTCGetEmployeeAllList?ACCOUNT=<%=account %>" target="userlist">ユーザー一覧</a></td>
		<td>システム利用ユーザー一覧を参照します</td>
	</tr>
	<tr>
		<td id="menu"><a href="FTCEditCompany?ACCOUNT=<%=account %>&EDITID=0001&type=0" target="clientreg">会社情報更新</a></td>
		<td>自社の会社情報管理します</td>
	</tr>
<%
}
%>
<!-- 	<tr> -->
<!-- 		<td colspan="2" id="menucat"><center>その他</center></td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td id="menu"><a href="http://www.usedmachinery.co.jp/" target="ftcja">http://www.usedmachinery.co.jp/</a></td> -->
<!-- 		<td>藤沢貿易（日本語）</td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td id="menu"><a href="http://www.usedmachinery.co.jp/en/" target="ftcen">http://www.usedmachinery.co.jp/en/</a></td> -->
<!-- 		<td>藤沢貿易（英語）</td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td id="menu"><a href="http://www.hzj.co.jp/" target="hzj">http://www.hzj.co.jp/</a></td> -->
<!-- 		<td>濱善重機</td> -->
<!-- 	</tr> -->
<%
// 管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode)) {
%>
<!-- 	<tr> -->
<!-- 		<td id="menu"><a href="http://gaeftcdev.appspot.com/" target="dev">http://gaeftcdev.appspot.com/</a></td> -->
<!-- 		<td>FTCSYSテスト環境</td> -->
<!-- 	</tr> -->
<%
}
%>
</table>
</div>
</div>
</body>
</html>
