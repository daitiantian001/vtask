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
	.normal-absolute.top-bar {
		top: 0;
		left: 0;
		right: 0;
		height: 60px;
		background-color: #2f3b45;
		z-index: 100;
		box-shadow: 0 0 5px #333333;
	}
	.normal-absolute.logout-block {
		left: 0;
		bottom: 0;
		width: 170px;
		height: 40px;
		background-color: #2f3b45;
		color: #baccd1;
		font-size: 14px;
		line-height: 40px;
		box-shadow: 0 0 5px #29333e;
		cursor: pointer;
		text-align: center;
	}
	.normal-absolute.logout-block:hover {
		background-color: #4c38a7;
		color: #ffffff;
	}
	.normal-absolute.left-menu {
		top: 60px;
		left: 0;
		bottom: 40px;
		width: 170px;
		background-color: #2f3b45;
	}
	.normal-absolute.main-frame {
		top: 60px;
		left: 170px;
		right: 0;
		bottom: 0;
	}
	.main-logo {
		position: absolute;
		top: 15px;
		left: 15px;
		width: 144px;
		height: 30px;
	}
	.login-photo {
		position: absolute;
		top: 10px;
		right: 210px;
		width: 40px;
		height: 40px;
		border-radius: 50%;
	}
	.login-info-block {
		position: absolute;
		top: 10px;
		right: 100px;
		width: 100px;
		height: 40px;
		border-radius: 50%;
	}
	.login-info-block .login-name {
		height: 20px;
		line-height: 20px;
		color: #ffffff;
		font-size: 14px;
	}
	.login-info-block .login-role {
		height: 20px;
		line-height: 20px;
		color: #e5e5e5;
		font-size: 12px;
	}
	.menu-item .menu-item-parent {
		width: 100%;
		height: 50px;
		background-color: #29333e;
		line-height: 50px;
		color: #baccd1;
		padding-left: 10px;
		cursor: pointer;
		position: relative;
	}
	.menu-item .menu-item-parent:hover, .menu-item .menu-item-parent.acitve {
		color: #ffffff;
	}
	.menu-item .menu-item-parent:hover:after, .menu-item .menu-item-parent.active:after {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		bottom: 0;
		width: 3px;
		background-color: #4c38a7;
	}
	.menu-item .menu-item-parent i {
		font-family: 'iconfont';
		font-style: normal;
		font-size: 18px;
	}
	.menu-item .menu-item-parent b {
		font-family: 'iconfont';
		font-style: normal;
		font-size: 18px;
		font-weight: normal;
		position: absolute;
		right: 10px;
	}
	.menu-item .menu-item-parent span {
		font-size: 14px;
		vertical-align: 2px;
		margin-left: 10px;
	}
	.menu-item-children {
		display: none;
	}
	.menu-item-children .menu-child-item {
		width: 100%;
		height: 50px;
		background-color: 2f3b45;
		line-height: 50px;
		color: #ffffff;
		padding-left: 40px;
		border-bottom: 1px solid #28323d;
		color: #baccd1;
		cursor: pointer;
	}
	.menu-item-children .menu-child-item:hover {
		color: #ffffff;
		background-color: rgba(76, 56, 167, 0.5);
	}
	.menu-item-children .menu-child-item.active {
		color: #ffffff;
		background-color: #4c38a7;
	}
	.custom-button {
		position: fixed;
		bottom: 0;
		right: 0;
		width: 110px;
		height: 40px;
		background-color: #29333e;
		color: #ffffff;
		font-size: 14px;
		line-height: 40px;
		padding-left: 15px;
		border-radius: 5px 0 0 0;
		box-shadow: 0 0 5px #29333e;
		cursor: pointer;
		display: none;
	}
	.custom-button:hover {
		background-color: #4c38a7;
	}
	.custom-button i {
		font-family: 'iconfont';
		font-size: 18px;
		font-style: normal;
		margin-right: 10px;
		vertical-align: -1px;
	}
	</style>
</head>
<body>
	<input id="userId" type="hidden" value="<%=CookieTools.getCookie(request, "userId").getValue().toString()%>"/>
	<div class="normal-relative">
		<div class="normal-absolute top-bar">
			<div class="normal-relative">
				<img class="main-logo" src="system/css/images/icon_system_logo.png"/>
				<img class="login-photo" src="system/css/images/icon_user_photo.png"/>
				<div class="login-info-block">
					<div class="login-name"></div>
					<div class="login-role"></div>
				</div>
			</div>
		</div>
		<div class="normal-absolute logout-block" onclick="logout();">退出</div>
		<div class="normal-absolute left-menu"></div>
		<div class="normal-absolute main-frame">
			<iframe id="main-frame" name="main-frame" src="" frameborder="0" width="100%" height="100%" scrolling="no" style="border:0px none;width:100%;height:100%;"></iframe>
		</div>
		<div class="custom-button"><i>&#xe6ff;</i>在线客服</div>
		<div class="win-mask" style="position:fixed;top:0;left:0;right:0;height:60px;background-color:rgba(0, 0, 0, 0.3);z-index:10000;display:none;"></div>
		<div class="win-mask" style="position:fixed;top:60px;left:0;bottom:0;width:170px;background-color:rgba(0, 0, 0, 0.3);z-index:10000;display:none;"></div>
	</div>
</body>
<script type="text/javascript">
function showMask(){
	$(".win-mask").show();
}

function hideMask(){
	$(".win-mask").hide();
}

function logout(){
	ESTAB.showLoading();
	$.ajax({
		type: "POST",
		url: "system/index/logout.do",
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				window.location.reload();
			}else{
				ESTAB.hideLoading();
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function initMenu(){
	$.ajax({
		type: "POST",
		url: "system/index/home.do",
		data: {
			id: $("#userId").val()
		},
		dataType: "text", 
		async:true,
		traditional:true,
		success: function(data) {
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				$(".login-name").html(jsonObject.data.name);
				$(".login-role").html(jsonObject.data.role.name);
				$.each(jsonObject.data.role.modules.sort(function(a,b){return a.id-b.id}), function(i, n){
					if(n.id=="05"){
						//$(".custom-button").show();
					}else{
						if(n.pId=="0"){
							$(".left-menu").append("<div class=\"menu-item\">"+
								"<div class=\"menu-item-parent\"><i>"+n.icon+"</i><span>"+n.name+"</span><b>&#xe6a3;</b></div>"+
								"<div id=\""+n.id+"\" class=\"menu-item-children\"></div>"+
							"</div>");
						}else{
							$("#"+n.pId).append("<div class=\"menu-child-item\" url=\""+n.url+"\">"+n.name+"</div>");
						}
					}
				});
				$(".menu-item-parent").click(function(){
					$(this).next().slideToggle(500);
				});
				$(".menu-child-item").click(function(){
					$(".menu-child-item.active").removeClass("active");
					$(".menu-item-parent.active").removeClass("active");
					$(this).addClass("active");
					$(this).parent().prev().addClass("active");
					$(this).parent().parent().addClass("active");
					$("#main-frame").attr("src", $(this).attr("url"));
				});
				$(".left-menu .menu-item:first-child .menu-item-children").show();
				$(".left-menu .menu-item:first-child .menu-item-children .menu-child-item:first-child").click();
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

$(document).ready(function(){
	initMenu();
});
</script>
</html>