<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>飞腾赚客</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../import.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="top.jsp"></jsp:include>
	<div class="container-block">
		<div class="container-bg" style="padding:20px;">
			<div class="page-color-title">钱包充值</div>
			<div class="tip-info-item">1、钱包功能仅支持支付工资之用，不得用于洗钱、套现、虚假交易等禁止行为。</div>
			<div class="tip-info-item">2、对于用户利用钱包进行恶意充值提现、洗钱、套现等用途，口袋兼职将扣留充值金额一个月，情节严重者，将会报警处理。</div>
			<div class="tip-info-item">3、使用钱包充值即默认遵守以上使用条款。</div>
			<div class="charge-form">
				<div class="charge-form-title">充值金额</div>
				<div class="charge-form-value"><input type="text" value="1"/></div>
			</div>
			<div class="pay-type-title">请选择支付方式：</div>
			<div class="pay-type-item">
				<input name="pay-type" type="radio" checked="checked"/>
				<img src="platform/css/images/icon_pay_zbf.png"/>
			</div>
			<div class="pay-type-item">
				<input name="pay-type" type="radio"/>
				<img src="platform/css/images/icon_pay_wx.png"/>
			</div>
			<div style="width:240px;padding:20px 0;">
				<div class="submit-btn">立即支付</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</html>