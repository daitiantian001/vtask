<%@page import="com.lmnml.group.util.CookieTools"%>
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
</head>
<body>
	<div class="normal-relative">
		<div class="normal-absolute page-title">
			<span class="page-tip-item">任务管理</span><span class="page-tip-item">任务类型</span>
		</div>
		<div class="normal-absolute toolbar-block">
			<div class="normal-relative">
				<div class="absolute-left" style="padding:5px;">
					<span class="layui-btn layui-btn-normal layui-btn-sm">新建类型</span>
				</div>
				<div class="absolute-right" style="padding:5px;">
					<input class="normal-text" type="text" placeholder="请输入角色名称" style="margin-right:5px;"/>
					<span class="layui-btn layui-btn-normal layui-btn-sm">搜索</span>
				</div>
			</div>
		</div>
		<div class="normal-absolute data-block">
			<table class="data-table">
				<thead class="data-thead">
					<tr>
						<td>任务类型</td>
						<td style="width:160px;">操作</td>
					</tr>
				</thead>
				<tbody id="data-list" class="data-tbody"></tbody>
			</table>
		</div>
		<div class="normal-absolute page-block">
			<div class="normal-relative">
				<div class="absolute-left" style="padding:15px;line-height:20px;"></div>
				<div class="absolute-right" style="padding:10px;line-height:30px;"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var currentPage = 1;
function loadData(){
	$.ajax({
		type: "POST",
		url: "system/VSystemCategoryMapper/queryAll.do",
		data: {
			currentPage: currentPage
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$(".page-block .absolute-left").html("<span class=\"page-text\">共<b>"+jsonObject.data.length+"</b>条记录</span>");
				$.each(jsonObject.data, function(i, n){
					$("#data-list").append("<tr>"+
						"<td><img src=\""+n.icon+"\" style=\"width:20px;height:20px;margin-right:10px;\"/>"+n.name+"</td>"+
						"<td>"+
							"<span class=\"layui-btn layui-btn-warm layui-btn-sm\">编辑</span>"+
							"<span class=\"layui-btn layui-btn-danger layui-btn-sm\">删除</span>"+
						"</td>"+
					"</tr>");
				});
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