<%@page import="cn.net.vtask.util.CookieTools"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>后台管理系统</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<jsp:include page="../import.jsp"></jsp:include>
	<style type="text/css">
	.task-detail {
		padding: 20px;
	}
	.page-task-title {
		font-size: 20px;
    	font-weight: 700;
    	padding-bottom: 10px;
    	border-bottom: 1px solid #e5e5e5;
    	margin-bottom: 10px;
    	color: #333333;
	}
	.page-VSystemCategory-title {
		font-size: 16px;
    	font-weight: 500;
    	color: #666666;
    	padding: 10px 0;
	}
	.page-info-block {
		max-width: 800px;
	}
	.page-info--item {
		display: inline-block;
		width: 250px;
	}
	</style>
</head>
<body>
	<input id="taskId" type="hidden" value="${taskId}"/>
	<div class="normal-relative">
		<div class="normal-absolute page-title">
			<span class="page-tip-item">任务管理</span><span class="page-tip-item">任务审核</span><span class="page-tip-item">任务详情</span>
		</div>
		<div class="normal-absolute toolbar-block">
			<div class="normal-relative">
				<div class="absolute-left" style="padding:5px;">
					<span class="layui-btn layui-btn-primary layui-btn-sm">通过</span>
					<span class="layui-btn layui-btn-danger layui-btn-sm">打回</span>
				</div>
			</div>
		</div>
		<div class="normal-absolute data-block" style="bottom:20px;">
			<div class="task-detail">
				<div class="page-task-title">-</div>
				<div class="page-VSystemCategory-title">基本信息</div>
				<div class="page-info-block">
					<div class="page-info-item"></div>
				</div>
				<div class="page-VSystemCategory-title">操作步骤</div>
				<div class="page-VSystemCategory-title">联系方式</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function loadData(){
	$.ajax({
		type: "POST",
		url: "system/task/queryById.do",
		data: {
			id: $("#taskId").val()
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				var task = jsonObject.data;
				$(".page-task-title").html(task.name);
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

$(document).ready(function(){
	loadData();
});
</script>
</html>