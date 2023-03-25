<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="gae.ftc.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// 認証チェック
	FTCAuthUtil.isLogin(request, response);

	String account = request.getParameter("ACCOUNT");
	String authCode = (String)(request.getSession()).getAttribute("AUTH_CODE");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title>在庫登録</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
	  if (isNaN(obj.PRICE.value)||
				isNaN(obj.ORDER_PRICE.value)||
				isNaN(obj.ORDER_TRANS_COST.value)||
				isNaN(obj.PARTS_COST.value)||
				isNaN(obj.MAINTENANCE_COST.value)||
				isNaN(obj.ORDER_OUT_ORDER_COST.value)||
				isNaN(obj.ORDER_COST_PRICE.value)||
				isNaN(obj.SELL_TRANCE_COST.value)||
				isNaN(obj.SHIP_COST.value)||
				isNaN(obj.SELL_OUT_ORDER_COST.value)||
				isNaN(obj.INS_COST.value)||
				isNaN(obj.FREIGHT_COST.value)||
				isNaN(obj.SELL_COST_PRICE.value)||
				isNaN(obj.SELL_PRICE.value)||
				isNaN(obj.WHOL_PRICE.value)||
				isNaN(obj.PROFIT.value)) {
			alert('金額項目に数字以外の文字が入力されています。入力を見直してください');
			return;
		}
		if (!ckDate(obj.ORDER_DATE.value) || (!ckDate(obj.ORDER_PAY_DATE.value) && obj.ORDER_PAY_DATE.value != '買掛') || (!ckDate(obj.SELL_PAY_DATE.value) && obj.SELL_PAY_DATE.value != '売掛')) {
			alert('日付の入力が不正です。入力を見直してください');
			return;
		}
		if (!ckYM(obj.SELL_MONTH.value)) {
			alert('年月の入力が不正です。入力を見直してください');
			return;
		}
		obj.submit();
	}

	// 計算ロジック
	function autoSum(obj) {
		obj.ORDER_COST_PRICE.value = Number(obj.ORDER_PRICE.value) + Number(obj.ORDER_TRANS_COST.value) + Number(obj.PARTS_COST.value) + Number(obj.MAINTENANCE_COST.value) + Number(obj.ORDER_OUT_ORDER_COST.value);
		obj.SELL_COST_PRICE.value = Number(obj.SELL_TRANCE_COST.value) + Number(obj.SHIP_COST.value) + Number(obj.SELL_OUT_ORDER_COST.value) + Number(obj.INS_COST.value) + Number(obj.FREIGHT_COST.value) + Number(obj.ORDER_COST_PRICE.value);
		obj.PROFIT.value = Number(obj.SELL_PRICE.value) - Number(obj.SELL_COST_PRICE.value);
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

	// 年月チェック
	function ckYM(datestr) {
		if (datestr == "") {
			return true;
		}
		if(!datestr.match(/^\d{4}\/\d{2}$/)){
			return false;
		}
		return true;
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
<form action="FTCRegistInventory"  method="POST">
<div><h1>在庫登録</h1></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td colspan="2" id="editmenu">WEB表示項目：ホームページに表示される項目です。外部に公開される情報です。</td>
</tr>
<tr>
<td>分類</td>
<td>
<select name="TYPE">
<%= FTCConst.COMBO_TYPE %>
</select>
</td>
</tr>
<tr>
<td>メーカー/MANUFACTURER</td>
<td>
<select name="MANUFACTURER">
<%= FTCConst.COMBO_MANUFACTURER %>
</select>
</td>
</tr>
<tr>
<td>型式/MODEL</td>
<td><input type="text" name="NAME" value="" size="50"></td>
</tr>
<tr>
<td>年式/YEAR</td>
<td><input type="text" name="YEAR" value=""></td>
</tr>
<tr>
<td>号機/SERIAL</td>
<td><input type="text" name="SERIALNO" value=""></td>
</tr>
<tr>
<td>稼働時間/HOUR METER</td>
<td><input type="text" name="HOURS" value=""></td>
</tr>
<tr>
<td>DESCRIPTION</td>
<td><input type="text" name="OTHER" value="" size="100"></td>
</tr>
<tr>
<td>詳細／仕様</td>
<td><input type="text" name="OTHER_JA" value="" size="100"></td>
</tr>
<tr>
<td>程度/CONDITION RANK</td>
<td>
<select name="CONDITION">
<%= FTCConst.COMBO_CONDITION %>
</select>
</td>
</tr>
<tr>
<td>整備状況/CONDITION MAINTENANCE</td>
<td>
<select name="CONDITION_MAINTENANCE">
<%= FTCConst.COMBO_CONDITION_MAINTENANCE %>
</select>
</td>
</tr>
<tr>
<td>表示価格/LIST PRICE</td>
<td><input type="text" name="PRICE" value="0" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>写真/PHOTO</td>
<td><input type="text" name="PIC_URL" value="" size="100">（入力形式:アドレス）</td>
</tr>
<tr>
<td colspan="2" id="editmenu">内部業務項目：内部業務で利用する項目です。非公開情報です。</td>
</tr>
<tr>
<td>仕入担当</td>
<%
// 事務以外
if (!FTCConst.AUTH_OFFICE.equals(authCode)) {
%>
<td><input type="text" name="INACCOUNT" value="<%= account %>"></td>
<%
} else {
%>
<td>
<select name="INACCOUNT">
<%= FTCConst.COMBO_ACCOUNT %>
</select>
</td>
<%
}
%>
</tr>
<tr>
<td>発注日</td>
<td><input type="text" name="ORDER_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>WEB表示</td>
<td>
<select name="WEB_DISP">
<%= FTCConst.COMBO_WEB_DISP %>
</select>
</td>
</tr>
<tr>
<td>仕入先コード</td>
<td><input type="text" name="SELLER_CODE" id="SELLER_CODE" value="" size="50"><input type="button" value="仕入先選択" onclick="clientPop('SELLER_CODE','SELLER')"></td>
</tr>
<tr>
<td>仕入先</td>
<td><input type="text" name="SELLER" id="SELLER" value="" size="100"></td>
</tr>
<tr>
<td>仕入価格</td>
<td><input type="text" name="ORDER_PRICE" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<%
// 事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode)) {
%>
<tr>
<td>仕入運賃</td>
<td><input type="text" name="ORDER_TRANS_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>部品代</td>
<td><input type="text" name="PARTS_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>整備費</td>
<td><input type="text" name="MAINTENANCE_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>仕入外注費</td>
<td><input type="text" name="ORDER_OUT_ORDER_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>仕入原価</td>
<td><input type="text" name="ORDER_COST_PRICE" value="0" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>引渡場所</td>
<td><input type="text" name="TRAN_PLACE" value="" size="100"></td>
</tr>
<%
} else {
%>
<input type="hidden" name="RENTAL" value="">
<!--<input type="hidden" name="SELLER_CODE" value="">-->
<!--<input type="hidden" name="SELLER" value="">-->
<!--<input type="hidden" name="ORDER_PRICE" value="0">-->
<input type="hidden" name="ORDER_TRANS_COST" value="0">
<input type="hidden" name="PARTS_COST" value="0">
<input type="hidden" name="MAINTENANCE_COST" value="0">
<input type="hidden" name="ORDER_OUT_ORDER_COST" value="0">
<input type="hidden" name="ORDER_COST_PRICE" value="0">
<input type="hidden" name="TRAN_PLACE" value="">
<%
}
%>
<tr>
<td>販売運賃</td>
<td><input type="text" name="SELL_TRANCE_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>船積費用</td>
<td><input type="text" name="SHIP_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>販売外注費</td>
<td><input type="text" name="SELL_OUT_ORDER_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>保険料</td>
<td><input type="text" name="INS_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>フレイト</td>
<td><input type="text" name="FREIGHT_COST" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<%
// 事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode)) {
%>
<td>販売原価</td>
<td><input type="text" name="SELL_COST_PRICE" value="0" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>販売価格</td>
<td><input type="text" name="SELL_PRICE" value="0" style="text-align:right;" onblur="autoSum(this.form)">（数字のみ入力可能）</td>
</tr>
<tr>
<td>利益</td>
<td><input type="text" name="PROFIT" value="0" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>販売先コード</td>
<td><input type="text" name="BUYER_CODE" id="BUYER_CODE" value="" size="50"><input type="button" value="販売先選択" onclick="clientPop('BUYER_CODE','BUYER')"></td>
</tr>
<tr>
<td>販売先</td>
<td><input type="text" name="BUYER" id="BUYER" value="" size="100"></td>
</tr>
<tr>
<td>業販価格</td>
<td><input type="text" name="WHOL_PRICE" value="0" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<%
} else {
%>
<input type="hidden" name="SELL_COST_PRICE" value="0">
<input type="hidden" name="SELL_PRICE" value="0">
<input type="hidden" name="PROFIT" value="0">
<input type="hidden" name="BUYER_CODE" value="">
<input type="hidden" name="BUYER" value="">
<input type="hidden" name="WHOL_PRICE" value="0">
<%
}
%>
<%
// 経理・管理者権限のみ表示
if (FTCConst.AUTH_ADMIN.equals(authCode) || FTCConst.AUTH_ACCT.equals(authCode)) {
%>
<tr>
<td>仕入代金支払日</td>
<td><input type="text" name="ORDER_PAY_DATE" value="">（入力形式:YYYY/MM/DD　もしくは　「買掛」）</td>
</tr>
<tr>
<td>売上入金日</td>
<td><input type="text" name="SELL_PAY_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>売上月</td>
<td><input type="text" name="SELL_MONTH" value="">（入力形式:YYYY/MM）</td>
</tr>
<%
} else {
%>
<input type="hidden" name="ORDER_PAY_DATE" value="">
<input type="hidden" name="SELL_PAY_DATE" value="">
<input type="hidden" name="SELL_MONTH" value="">
<%
}
%>
<%
// 事務以外は表示
if (!FTCConst.AUTH_OFFICE.equals(authCode)) {
%>
<tr>
<td>在庫メモ</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"></textarea></td>
</tr>
<%
} else {
%>
<input type="hidden" name="MEMO" value="">
<%
}
%>
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