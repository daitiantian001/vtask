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
		<div class="menu-tabs">
			<div class="menu-tab-item" url="0">待付款</div>
			<div class="menu-tab-item" url="1">待审核</div>
			<div class="menu-tab-item active" url="2">正在招</div>
			<div class="menu-tab-item" url="3">已下架</div>
			<div class="menu-tab-item" url="-1">不通过</div>
			<div class="menu-tab-item" url="4">二次审核</div>
		</div>
		<div class="container-bg" style="padding:20px;">
			<div id="data-list"></div>
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
var status = 2, currentPage = 1;

function loadTask(){
	$.ajax({
		type: "POST",
		url: "pdata/task/list.do",
		data: {
			status: status,
			currentPage: currentPage
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				if(jsonObject.pageInfo.pageInfo.totalRows==0){
					$("#data-list").html("<div class=\"null-list\"><img src=\"platform/css/images/icon_list_null.png\"/><div class=\"null-tips\">暂无正在招的微任务~</div></div>");
				}else{
					$("#data-list").html("");
				}
				$.each(jsonObject.data, function(i, n){
					var btns = "";
					if(status==0){
						btns = "<span class=\"layui-btn layui-btn-primary\">删除</span>"+
						    "<span class=\"layui-btn layui-btn-normal\" onclick=\"payTask('"+n.id+"');\">立即付款</span>";
					}
					$("#data-list").append("<div class=\"task-item\">"+
						"<img class=\"task-item-icon\" src=\"platform/css/images/icon_type_task.png\"/>"+
						"<div class=\"task-item-title\">"+n.name+"</div>"+
						"<div class=\"task-item-time\">"+(new Date(n.createTime).format("yyyy-MM-dd HH:mm"))+"</div>"+
						"<div class=\"task-item-desc\">该微任务待付款<span>"+(parseFloat(n.price)*0.01*parseInt(n.num)).toFixed(2)+"</span>元</div>"+
						"<div class=\"task-item-btns\">"+btns+"</div>"+
					"</div>");
				});
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function payTask(id){
	window.location.href = "platform/pay.do?taskId="+id;
}

$(document).ready(function(){
	$("#task-menu").addClass("active");
	
	$(".menu-tab-item").click(function(){
		$(".menu-tab-item.active").removeClass("active");
		$(this).addClass("active");
		status = parseInt($(this).attr("url"));
		currentPage = 1;
		loadTask();
	});
	
	loadTask();
});
</script>
</html>