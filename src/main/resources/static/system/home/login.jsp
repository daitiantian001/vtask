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
	.login-form {
		width: 400px;
		height: 300px;
		top: 40%;
		margin-top: -150px;
		left: 50%;
		margin-left: -200px;
	}
	.login-form .login-text {
		width: 100%;
		color: #a089f5;
		height: 80px;
		font-size: 36px;
		text-align: center;
	}
	.login-form .login-input-block {
		width: 400px;
		height: 220px;
		background-color: #ffffff;
		border-radius: 4px;
		box-shadow: 0 0 5px #a089f5;
		padding: 20px 0;
	}
	.login-input-item {
		width: 100%;
		padding: 10px 20px;
		position: relative;
	}
	.login-input-item input {
		width: 100%;
		height: 40px;
		border-radius: 4px;
		border: 1px solid #e5e5e5;
		padding: 10px 10px 10px 40px;
	}
	.login-input-item input:focus {
		border: 1px solid #4c38a7;
		box-shadow: 0 0 5px #4c38a7;
	}
	.login-input-item .item-icon {
		position: absolute;
		left: 31px;
		top: 19px;
		z-index: 10;
		font-family: 'iconfont';
		font-size: 20px;
		color: #999999;
	}
	.login-input-item .item-icon.active {
		color: #4c38a7;
	}
	.login-submit {
		margin: 10px 20px 0;
		height: 40px;
		border-radius: 4px;
		background-color: #4c38a7;
		color: #ffffff;
		text-align: center;
		line-height: 40px;
		cursor: pointer;
	}
	.login-submit:hover {
		opacity: 0.8;
	}
	</style>
</head>
<body style="background:#29333e;">
	<div class="normal-relative">
		<div class="normal-absolute login-form">
			<div class="login-text">飞腾赚客后台管理系统</div>
			<div class="login-input-block">
				<div class="login-input-item">
					<span class="item-icon">&#xe736;</span>
					<input id="mobile" class="login-input-text" type="text" placeholder="请输入手机号" autocomplete="off"/>
				</div>
				<div class="login-input-item">
					<span class="item-icon">&#xe6c0;</span>
					<input id="password" class="login-input-text" type="password" placeholder="请输入密码" autocomplete="off"/>
				</div>
				<div class="login-submit" onclick="login();">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</div>
			</div>
		</div>
		<div class="icp-text">沪ICP备17053958号</div>
		<div class="company-text">版权所有© 2017~2018 RIPPED letsgetripped.cn</div>
	</div>
</body>
<script type="text/javascript">
function login(){
	ESTAB.showLoading();
	$.ajax({
		type: "POST",
		url: "system/index/login.do",
		data: {
			mobile: $("#mobile").val(),
			password: $("#password").val()
		},
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

$(document).ready(function(){
	$(".login-input-text").focus(function(){
		$(this).prev().addClass("active");
	});
	$(".login-input-text").blur(function(){
		$(this).prev().removeClass("active");
	});
});
</script>
</html>