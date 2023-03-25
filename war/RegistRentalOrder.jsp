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
<title>レンタル注文登録</title>
<link rel="stylesheet" href="css/ftc.css" type="text/css">
<script type="text/javascript">
<!--
	function callServer(obj) {
	  if (isNaN(obj.AMOUNT.value)||
				isNaN(obj.TRANS_FEE.value)||
				isNaN(obj.RETURN_FEE.value)) {
			alert('数字項目に数字以外の文字が入力されています。入力を見直してください');
			return;
		}
		if (!ckDate(obj.OUT_DATE.value) || !ckDate(obj.IN_DATE.value) || !ckDate(obj.START_DATE.value) || !ckDate(obj.END_DATE.value)) {
			alert('日付の入力が不正です。入力を見直してください');
			return;
		}
		if (obj.ORDER_TYPE.value === '') {
			alert('注文区分を選択してください');
			return;
		}
		obj.submit();
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

	// 注文区分選択
	function changeOrderType(obj) {
		if (obj.ORDER_TYPE.value == "0") {
			obj.PRICE.value = obj.PRICE_MONTH.value;
		}
		if (obj.ORDER_TYPE.value == "1") {
			obj.PRICE.value = obj.PRICE_DAY.value;
		}
		obj.SUPPORT_FEE.value = obj.PRICE_SUPPORT.value;
	}

	// 金額計算
	function calcAmount(obj) {
		obj.TOTAL.value = 0;
		obj.AMOUNT.value = parseFloat(obj.PRICE.value) * parseFloat(obj.COUNT.value);
		obj.TOTAL.value = parseFloat(obj.AMOUNT.value) + parseFloat(obj.SUPPORT_FEE.value) + parseFloat(obj.TRANS_FEE.value) + parseFloat(obj.RETURN_FEE.value) + parseFloat(obj.FEE1.value) + parseFloat(obj.FEE2.value) + parseFloat(obj.FEE3.value) + parseFloat(obj.FEE4.value) + parseFloat(obj.FEE5.value) + parseFloat(obj.FEE6.value);
	}

	// レンタル在庫ポップ
	function invPop(id,name,serialno,pricemonth,priceday,hours,enrollment,pricesupport){
		window.open("/FTCSeachRentalInventoryList?id="+id+"&name="+name+"&serialno="+serialno+"&pricemonth="+pricemonth+"&priceday="+priceday+"&hours="+hours+"&enrollment="+enrollment+"&pricesupport="+pricesupport+"&ACCOUNT=<%=account %>", 'invpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}

	// 顧客ポップ
	function clientPop(code,name,rep,tel){
		window.open("/ClientListPopUp.jsp?type=0&code="+code+"&name="+name+"&rep="+rep+"&tel="+tel+"&ACCOUNT=<%=account %>", 'clientpop', 'width=800 height=600,menubar=no,toolbar=no,scrollbars=yes');
	}
-->
</script>
</head>
<body>
<div>
<form action="FTCRegistRentalOrder" method="POST">
<div><h1>レンタル注文登録</h1></div>
<div id="msg"><%= FTCCommonUtil.nullConv(msg) %></div>
<div align="right">
<input type="button" name="close" value="閉じる" onclick="window.close();">
</div>
<div>
<table class="table table-bordered table-striped table-hover table-responsive">
<tr>
<td>レンタル在庫ID</td>
<td><input type="text" name="RENTAL_INVENTORY_ID" id="RENTAL_INVENTORY_ID" value="" readonly><input type="button" value="レンタル在庫選択" onclick="invPop('RENTAL_INVENTORY_ID','NAME','SERIALNO','PRICE_MONTH','PRICE_DAY','HOURS_START','ENROLLMENT','PRICE_SUPPORT')"></td>
</tr>
<tr>
<td>型式</td>
<td><input type="text" name="NAME" id="NAME" value="" readonly></td>
</tr>
<tr>
<td>号機</td>
<td><input type="text" name="SERIALNO" id="SERIALNO" value="" readonly></td>
</tr>
<tr>
<td>月単価</td>
<td><input type="text" name="PRICE_MONTH" id="PRICE_MONTH" value="" readonly></td>
</tr>
<tr>
<td>日単価</td>
<td><input type="text" name="PRICE_DAY" id="PRICE_DAY" value="" readonly></td>
</tr>
<tr>
<td>サポート料金</td>
<td><input type="text" name="PRICE_SUPPORT" id="PRICE_SUPPORT" value="" readonly></td>
</tr>
<tr>
<td>稼動時間開始時</td>
<td><input type="text" name="HOURS_START" id="HOURS_START" value="" readonly></td>
</tr>
<tr>
<td>在籍</td>
<td><input type="text" name="ENROLLMENT" id="ENROLLMENT" value="" readonly></td>
</tr>
<tr>
<td>注文区分</td>
<td>
<select name="ORDER_TYPE" onchange="changeOrderType(this.form);">
<option value="">選択してください</option>
<%= FTCConst.ORDER_TYPE %>
</select>
</td>
</tr>
<tr>
<td>締め日</td>
<td>
<select name="CLOSE_DATE">
<option value="">選択してください</option>
<%= FTCConst.CLOSE_DATE %>
</select>
</td>
</tr>
<tr>
<td>出庫日</td>
<td><input type="text" name="OUT_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>返却日</td>
<td><input type="text" name="IN_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>レンタル開始日</td>
<td><input type="text" name="START_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>レンタル終了日</td>
<td><input type="text" name="END_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
<tr>
<td>稼動時間終了時</td>
<td><input type="text" name="HOURS_END" id="HOURS_END" value="" readonly style="text-align:right;"></td>
</tr>
<tr>
<td colspan="2" id="editmenu"></td>
</tr>
<tr>
<td>月極／日極</td>
<td><input type="text" name="PRICE" value="0" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>数量</td>
<td><input type="text" name="COUNT" value="1" style="text-align:right;">（数字のみ入力可能）</td>
</tr>
<tr>
<td>レンタル代</td>
<td><input type="text" name="AMOUNT" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="AMOUNT_MEMO"></td>
</tr>
<tr>
<td>サポート料金</td>
<td><input type="text" name="SUPPORT_FEE" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="SUPPORT_MEMO"></td>
</tr>
<tr>
<td>搬入運賃</td>
<td><input type="text" name="TRANS_FEE" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="TRANS_FEE_MEMO"></td>
</tr>
<tr>
<td>返却運賃</td>
<td><input type="text" name="RETURN_FEE" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="RETURN_FEE_MEMO"></td>
</tr>
<tr>
<td><input type="text" name="FEE1_NAME"></td>
<td><input type="text" name="FEE1" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE1_MEMO"></td>
</tr>
<tr>
<td><input type="text" name="FEE2_NAME"></td>
<td><input type="text" name="FEE2" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE2_MEMO"></td>
</tr>
<tr>
<td><input type="text" name="FEE3_NAME"></td>
<td><input type="text" name="FEE3" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE3_MEMO"></td>
</tr>
<tr>
<td><input type="text" name="FEE4_NAME"></td>
<td><input type="text" name="FEE4" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE4_MEMO"></td>
</tr>
<tr>
<td><input type="text" name="FEE5_NAME"></td>
<td><input type="text" name="FEE5" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE5_MEMO"></td>
</tr>
<tr>
<td><input type="text" name="FEE6_NAME"></td>
<td><input type="text" name="FEE6" value="0" style="text-align:right;">（数字のみ入力可能）<input type="text" name="FEE5_MEMO"></td>
</tr>
<tr>
<td>合計金額</td>
<td><input type="text" name="TOTAL" value="0" style="text-align:right;">（数字のみ入力可能）<input type="button" value="金額計算" onclick="calcAmount(this.form)"></td>
</tr>
<!-- 
<tr>
<td>請求日</td>
<td><input type="text" name="REQUEST_DATE" value="">（入力形式:YYYY/MM/DD）</td>
</tr>
 -->
<tr>
<td colspan="2" id="editmenu"></td>
</tr>
<tr>
<td>ユーザー名</td>
<td><input type="text" name="ORDER_NAME" value=""></td>
</tr>
<tr>
<td>搬入先</td>
<td><input type="text" name="ORDER_PLACE" value=""></td>
</tr>
<tr>
<td>顧客コード</td>
<td><input type="text" name="CLIENT_CODE" id="CLIENT_CODE" value=""><input type="button" value="顧客選択" onclick="clientPop('CLIENT_CODE','CLIENT_NAME','CLIENT_REP','CLIENT_TEL')"></td>
</tr>
<tr>
<td>顧客名</td>
<td><input type="text" name="CLIENT_NAME" id="CLIENT_NAME" value=""></td>
</tr>
<tr>
<td>顧客担当者</td>
<td><input type="text" name="CLIENT_REP" id="CLIENT_REP" value=""></td>
</tr>
<tr>
<td>顧客TEL</td>
<td><input type="text" name="CLIENT_TEL" id="CLIENT_TEL" value=""></td>
</tr>
<tr>
<td>備考欄</td>
<td><textarea name="MEMO" cols="50" rows="5" maxlength="256"></textarea></td>
</tr>
</table>
</div>
<div>
<input type="hidden" name="DATA_FLG" id="DATA_FLG">
<input type="hidden" name="ACCOUNT" value="<%= account %>">
<input type="button" value="登録" onclick="callServer(this.form)">
</div>
</form>
</div>
</body>
</html>