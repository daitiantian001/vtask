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
	<style type="text/css">
	.layer-pay-win {
		width: 100%;
		padding: 30px;
		text-align: center;
	}
	.pay-win-money {
		color: #666666;
	}
	.pay-win-money span {
		color: #333333;
		font-weight: bold;
		font-size: 20px;
	}
	.pay-win-qrcode {
		height: 220px;
		padding: 20px;
	}
	.pay-win-qrcode img {
		width: 180px;
		height: 180px;
	}
	.pay-text {
		font-size: 14px;
		line-height: 20px;
		color: #666666;
		width: 180px;
		margin: 0 auto;
		padding-left: 45px;
		background-image: url("platform/css/images/icon_win_wxpay.png");
		background-size: 40px;
		background-position: left center;
		background-repeat: no-repeat;
		text-align: left;
	}
	</style>
</head>
<body>
	<input id="taskId" type="hidden" value="${taskId}"/>
	<input id="orderId" type="hidden" value="${orderId}"/>
	<jsp:include page="top.jsp"></jsp:include>
	<div class="container-block">
		<div class="container-bg" style="padding:20px;">
			<div class="page-color-title">支付微任务</div>
			<div class="page-title">请核对支付信息</div>
			<div style="padding:0 20px;">
				<div class="border-box">
					<div class="value-item">微任务标题：<span id="task-name"></span></div>
					<div class="value-item">支付数量：<span id="task-num"></span>个</div>
					<div class="value-item">支付单价：<span id="task-price"></span></div>
					<div class="value-item pay-total">实付款：<span><span id="task-total"></span></span>元</div>
				</div>
			</div>
			<div class="page-title">请选择支付方式</div>
			<div style="padding:0 20px;">
				<div class="border-box">
					<div class="pay-type-item">
						<input name="pay-type" type="radio" checked="checked" value="1"/>
						<img src="platform/css/images/icon_pay_zbf.png"/>
					</div>
					<div class="pay-type-item">
						<input name="pay-type" type="radio" value="2"/>
						<img src="platform/css/images/icon_pay_wx.png"/>
					</div>
				</div>
			</div>
			<div style="width:240px;padding:20px 0;">
				<div class="submit-btn" onclick="payOrder();">立即支付</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
var task = null;
function loadTask(){
	$.ajax({
		type: "POST",
		url: "pdata/task/queryById.do",
		data: {
			id: $("#taskId").val()
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				task = jsonObject.data;
				$("#task-name").html(task.name);
				$("#task-num").html(task.num);
				$("#task-price").html("￥"+parseFloat(task.price*0.01).toFixed(2));
				$("#task-total").html((parseFloat(task.price)*0.01*parseInt(task.num)).toFixed(2));
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function payOrder(){
	var payType = 1, order = null;
	$.each($("input[name='pay-type']"), function(i, n){
		if($(n).prop("checked")){
			payType = $(n).val();
		}
	});
	$.ajax({
		type: "POST",
		url: "pdata/deal/create.do",
		data: {
			taskId: $("#taskId").val(),
			payType: payType
		},
		dataType: "text", 
		async: false,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				order = jsonObject.data;
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
	var orderId = order.orderNum;
	var totalFee = parseFloat(order.money*0.01).toFixed(2);
	var body = "微任务预支付-"+task.name;
	$.ajax({
		type: "POST",
		url: "pdata/pay/wxpay.do",
		data: {
			orderId: orderId,
			totalFee: totalFee,
			body: body
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				layerIndex = layer.open({
					type: 1,
					title: "微信支付",
					area: ['570px', '420px'],
					offset: (document.body.clientHeight/2-420/2)+'px',
					shadeClose: false, //点击遮罩关闭
					content: "<div class=\"layer-pay-win\">"+
						"<div class=\"pay-win-money\">待支付 <span>￥"+parseFloat(totalFee).toFixed(2)+"</span></div>"+
						"<div class=\"pay-win-qrcode\"><img src=\"pdata/image/qrcode.do?content="+encodeURI(jsonObject.data)+"\"/></div>"+
						"<div class=\"pay-text\">请使用微信扫一扫<br/>扫描二维码完成支付</div>"+
					"</div>"
				});
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

$(document).ready(function(){
	$(".pay-type-item").click(function(){
		$(this).children("input[type='radio']").prop("checked", "checked");
	});

	loadTask();	
});
</script>
</html>