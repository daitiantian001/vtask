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
</head>
<body>
	<div class="normal-relative">
		<div class="normal-absolute page-title">
			<span class="page-tip-item">任务管理</span><span class="page-tip-item">任务审核</span>
		</div>
		<div class="normal-absolute toolbar-block">
			<div class="normal-relative">
				<div class="absolute-left" style="padding:5px;">
					<span class="layui-btn layui-btn-primary layui-btn-sm">批量审核</span>
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
						<td>商家</td>
						<td>任务名称</td>
						<td>任务类型</td>
						<td>数量</td>
						<td>单价</td>
						<td>截止时间</td>
						<td>审核时间</td>
						<td>设备</td>
						<td>联系方式</td>
						<td>电话</td>
						<td>邮箱</td>
						<td>操作</td>
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
		url: "system/task/list.do",
		data: {
			currentPage: currentPage
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$(".page-block .absolute-left").html("<span class=\"page-text\">总<b>"+jsonObject.pageInfo.pageInfo.totalPages+"</b>页，共<b>"+jsonObject.pageInfo.pageInfo.totalRows+"</b>条记录</span>");
				if(jsonObject.pageInfo.pageInfo.totalPages>1){
					$(".page-block .absolute-right").html("<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"firstPage();\">首页</span>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"prevPage();\">上一页</span>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"nextPage();\">下一页</span>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"lastPage();\">尾页</span>"+
						"<input class=\"normal-text\" type=\"text\" style=\"margin:0 5px;vertical-align:-1px;text-align:center;width:50px;\" value=\""+jsonObject.pageInfo.pageInfo.currentPage+"\"/>"+
						"<span class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"turnPage();\">跳转</span>");
				}
				$.each(jsonObject.data, function(i, n){
					var device = "不限";
					if(n.deviceType==1){
						device = "Android";
					}else if(n.deviceType==2){
						device = "iOS";
					}
					$("#data-list").append("<tr>"+
						"<td>"+n.user.name+"</td>"+
						"<td>"+n.name+"</td>"+
						"<td>"+n.VSystemCategory.name+"</td>"+
						"<td>"+n.num+"</td>"+
						"<td>￥"+parseFloat(n.price).toFixed(2)+"</td>"+
						"<td>"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm:ss"))+"</td>"+
						"<td>"+n.checkTime+"小时制</td>"+
						"<td>"+device+"</td>"+
						"<td>"+n.contactorName+"</td>"+
						"<td>"+n.contactorMobile+"</td>"+
						"<td>"+n.contactorEmail+"</td>"+
						"<td>"+
							"<span class=\"layui-btn layui-btn-normal layui-btn-sm\">查看</span>"+
							"<span class=\"layui-btn layui-btn-primary layui-btn-sm\">通过</span>"+
							"<span class=\"layui-btn layui-btn-danger layui-btn-sm\">打回</span>"+
						"</td>"+
					"</tr>");
					$.each(n.modules.sort(function(a,b){return a.id-b.id}), function(x, y){
						if(y.pId=="0"){
							$("#role-"+n.id).append("<div class=\"modules-item module-"+y.id+"\"><span class=\"module-parent\"><i>"+y.icon+"</i>"+y.name+"</span></div>");
						}else{
							$("#role-"+n.id+" .module-"+y.pId).append("<span class=\"module-child\"><i>&#xe6d6;</i>"+y.name+"</span>");
						}
					});
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