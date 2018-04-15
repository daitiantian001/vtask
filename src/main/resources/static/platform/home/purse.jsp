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
			<div class="page-title">我的钱包</div>
			<div class="purse-account">
				<div class="purse-yue">余额：<span id="user-account"></span>元</div>
				<div class="purse-btns">
				    <span class="layui-btn layui-btn-primary" onclick="javascript:window.location.href='platform/takeout.do';">提现</span>
				    <span class="layui-btn layui-btn-normal" onclick="javascript:window.location.href='platform/charge.do';">充值</span>
				</div>
			</div>
			<div class="page-title">账单明细</div>
			<div class="page-tabs">
				<div class="page-tab-item active" url="1">支出</div>
				<div class="page-tab-item" url="0">收入</div>
			</div>
			<div class="purse-data-list">
				<table class="purse-data-table">
					<thead>
						<tr>
							<td>时间</td>
							<td>交易名称</td>
							<td>交易对象</td>
							<td>交易状态</td>
							<td>金额（元）</td>
						</tr>
					</thead>
					<tbody id="data-list"></tbody>
				</table>
			</div>
			<div class="page-block">
				<div class="page-num-btn"><span>上一页</span></div>
				<div class="page-num-btn active"><span>1</span></div>
				<div class="page-num-btn"><span>2</span></div>
				<div class="page-num-btn"><span>3</span></div>
				<div class="page-num-btn"><span>4</span></div>
				<div class="page-num-btn"><span>25</span></div>
				<div class="page-num-point">…</div>
				<div class="page-num-btn"><span>下一页</span></div>
				<div class="page-num-btn"><input type="text"/></div>
				<div class="page-num-btn"><span>跳转</span></div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
function initInfo(){
	$.ajax({
		type: "POST",
		url: "pdata/user/queryById.do",
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$("#user-account").html(parseFloat(jsonObject.data.account*0.01).toFixed(2));
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

var type = 1, currentPage = 1;
function loadRecord(){
	$.ajax({
		type: "POST",
		url: "pdata/deal/finance.do",
		data: {
			type: type, 
			currentPage: currentPage
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				if(jsonObject.pageInfo.pageInfo.totalRows==0){
					$("#data-list").html("<tr><td colspan=\"5\" style=\"text-align:center;color:#999999;\">暂无账单明细~</td></tr>");
				}else{
					$("#data-list").html("");
				}
				$.each(jsonObject.data, function(i, n){
					switch(n.type){
					case 1:
						$("#data-list").append("<tr>"+
							"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
							"<td>充值</td>"+
							"<td>飞腾赚客</td>"+
							"<td>交易成功</td>"+
							"<td>￥"+parseFloat(n.money*0.01).toFixed(2)+"</td>"+
						"</tr>");
						break;
					case 2:
						var status = "";
						if(n.payStatus==-1){
							status = "提现失败";
						}else if(n.payStatus==2){
							status = "待审核";
						}else if(n.payStatus==1){
							status = "提现成功";
						}
						$("#data-list").append("<tr>"+
							"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
							"<td>提现</td>"+
							"<td>飞腾赚客</td>"+
							"<td>"+status+"</td>"+
							"<td>￥"+parseFloat(n.money*0.01).toFixed(2)+"</td>"+
						"</tr>");
						break;
					case 3:
						$("#data-list").append("<tr>"+
							"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
							"<td>微任务预支付-"+n.task.name+"</td>"+
							"<td>飞腾赚客</td>"+
							"<td>交易成功</td>"+
							"<td>￥"+parseFloat(n.money*0.01).toFixed(2)+"</td>"+
						"</tr>");
						break;
					case 4:
						$("#data-list").append("<tr>"+
							"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
							"<td>微任务佣金-"+n.task.name+"</td>"+
							"<td>"+n.user.name+"</td>"+
							"<td>交易成功</td>"+
							"<td>￥"+parseFloat(n.money*0.01).toFixed(2)+"</td>"+
						"</tr>");
						break;
					case 5:
						$("#data-list").append("<tr>"+
							"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
							"<td>微任务邀请佣金</td>"+
							"<td>"+n.user.name+"</td>"+
							"<td>交易成功</td>"+
							"<td>￥"+parseFloat(n.money*0.01).toFixed(2)+"</td>"+
						"</tr>");
						break;
					case 6:
						$("#data-list").append("<tr>"+
							"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
							"<td>误判充值补偿</td>"+
							"<td>飞腾赚客</td>"+
							"<td>交易成功</td>"+
							"<td>￥"+parseFloat(n.money*0.01).toFixed(2)+"</td>"+
						"</tr>");
						break;
					case 7:
						$("#data-list").append("<tr>"+
							"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
							"<td>微任务预支付退款-"+n.task.name+"</td>"+
							"<td>飞腾赚客</td>"+
							"<td>交易成功</td>"+
							"<td>￥"+parseFloat(n.money*0.01).toFixed(2)+"</td>"+
						"</tr>");
						break;
					}
				});
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

$(document).ready(function(){
	$("#purse-menu").addClass("active");
	
	$(".page-tab-item").click(function(){
		$(".page-tab-item.active").removeClass("active");
		$(this).addClass("active");
		type = parseInt($(this).attr("url"));
		currentPage = 1;
		loadRecord();
	});
	
	initInfo();
	loadRecord();
});
</script>
</html>