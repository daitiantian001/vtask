<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html style="background-color:#475062;">
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
	.login-form-block {
		position: absolute;
		width: 700px;
		height: 500px;
		background-color: #ffffff;
		top: 50%;
		left: 50%;
		margin-top: -250px;
		margin-left: -350px;
		box-shadow: 0 0 5px #333333
	}
	.company-bg {
		position: absolute;
		width: 257px;
		height: 500px;
		background-color: #4c38a7;
		height: 100%;
    	background: -webkit-linear-gradient(top, #a089f5, #4c38a7);
    	background: -moz-linear-gradient(top, #a089f5, #4c38a7);
    	background: linear-gradient(top, #a089f5, #4c38a7);
	}
	.company-bg-img {
		width: 257px;
		height: 500px;
		background-image: url("platform/css/images/bg_login_alpha.png");
		background-size: 100% 100%;
		background-repeat: no-repeat;
		position: relative;
	}
	.login-logo {
		position: absolute;
		top: 30px;
		left: 50%;
		width: 160px;
		height: 160px;
		margin-left: -80px;
	}
	.login-logo-text {
		position: absolute;
		top: 180px;
		left: 0;
		right: 0;
		text-align: center;
		color: #ffffff;
		font-size: 16px;
	}
	.login-qrcode {
		position: absolute;
		bottom: 100px;
		width: 160px;
		height: 160px;
		left: 50%;
		margin-left: -80px;
	}
	.login-qrcode-text {
		position: absolute;
		left: 50px;
		bottom: 40px;
		color: #ffffff;
		line-height: 24px;
	}
	.form-layout {
		position: absolute;
		left: 257px;
		top: 0;
		right: 0;
		bottom: 0;
	}
	.login-tabs {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		height: 80px;
		text-align: center;
		padding: 10px;
	}
	.login-tab-item {
		display: inline-block;
	    height: 60px;
	    line-height: 40px;
	    padding: 10px;
	    width: 100px;
	    text-align: center;
	    font-size: 16px;
	    color: #666666;
	    position: relative;
	    cursor: pointer;
	}
	.login-tab-item.active {
		color: #4c39a8;
	}
	.login-tab-item.active:after {
	    content: '';
	    position: absolute;
	    bottom: 5px;
	    width: 30px;
	    height: 4px;
	    left: 50%;
	    margin-left: -15px;
	    border-radius: 2px;
	    background: -moz-linear-gradient(top left, #a089f5, #4c38a7);
	    background: -webkit-linear-gradient(top left, #a089f5, #4c38a7);
	    background: -o-linear-gradient(top left, #a089f5, #4c38a7);
	    background: -ms-linear-gradient(top left, #a089f5, #4c38a7);
	    background: linear-gradient(to bottom right, #a089f5, #4c38a7);
	}
	.login-form {
		position: absolute;
		top: 100px;
		left: 90px;
		right: 90px;
	}
	.login-form .login-form-input {
		width: 100%;
		height: 40px;
		margin-top: 20px;
		position: relative;
	}
	.login-form .login-form-input i {
		font-family: 'iconfont';
		position: absolute;
		top: 10px;
		left: 10px;
		font-size: 20px;
		line-height: 20px;
		text-align: center;
		font-style: normal;
		color: #999999;
	}
	.login-form .login-form-input input, #auth .ivu-input {
		width: 100%;
		background: transparent;
    	border: 0;
		border: 1px solid #e5e5e5;
		box-shadow: inset 0 0 0 1000px #fff;
    	outline: 0;
    	height: 40px;
    	border-radius: 4px;
    	padding-left: 40px;
    	padding-right: 10px;
	}
	.login-form .login-form-input input:focus, #auth .ivu-input:focus {
		border: 1px solid #4c38a7;
		box-shadow: 0 0 5px #4c38a7, inset 0 0 0 1000px #fff;
	}
	.login-btn {
		margin-top: 30px;
		width: 100%;
		height: 50px;
		border-radius: 25px;
		background-color: #4c38a7;
	    background: -moz-linear-gradient(top left, #a089f5, #4c38a7);
	    background: -webkit-linear-gradient(top left, #a089f5, #4c38a7);
	    background: -o-linear-gradient(top left, #a089f5, #4c38a7);
	    background: -ms-linear-gradient(top left, #a089f5, #4c38a7);
	    background: linear-gradient(to bottom right, #a089f5, #4c38a7);
	    text-align: center;
	    line-height: 50px;
	    font-size: 16px;
	    color: #ffffff;
	    cursor: pointer;
	}
	.login-btn:hover {
		opacity: 0.8;
	}
	.code-btn {
		position: absolute;
		top: 0;
		right: 0;
		bottom: 0;
		line-height: 40px;
		width: 140px;
		color: #4c38a7;
		cursor: pointer;
		text-align: center;
	}
	.code-btn:hover {
		opacity: 0.8;
	}
	</style>
</head>
<body style="background-color:#475062;position:fixed;top:0;left:0;right:0;bottom:0;">
	<div class="normal-relative">
		<div class="login-form-block">
			<div class="normal-relative">
				<div class="company-bg">
					<div class="company-bg-img">
						<img class="login-logo" src="platform/css/images/bg_login_logo.png"/>
						<div class="login-logo-text">微任务发布平台</div>
						<img class="login-qrcode" src="https://cdn1-oss-site-static.kdjz.com/prod/company-web/assets/images/download.59adfc6e65d7eef6d5a636826b6f8e86.png"/>
						<div class="login-qrcode-text">
							扫一扫二维码<br/>下载飞腾赚客App
						</div>
					</div>
				</div>
				<div class="form-layout">
					<div class="normal-relative">
						<div class="login-tabs">
							<div class="login-tab-item active" url="login-form">登录</div>
							<div class="login-tab-item" url="reg-form">注册</div>
						</div>
						<div id="login-form" class="login-form">
							<div class="login-form-input">
								<input id="login-mobile" type="text" placeholder="手机号"/><i>&#xe704;</i>
							</div>
							<div class="login-form-input">
								<input id="login-pwd" type="password" placeholder="登录密码"/><i>&#xe6c0;</i>
							</div>
							<div id="login-form-submit" class="login-btn">立即登录</div>
						</div>
						<div id="reg-form" class="login-form" style="display:none;">
							<div class="login-form-input">
								<input id="reg-mobile" type="text" placeholder="手机号"/><i>&#xe704;</i>
							</div>
							<div class="login-form-input">
								<input id="reg-code" type="text" placeholder="验证码" style="padding-right:140px;"/><i>&#xe7bd;</i>
								<div id="code-btn" class="code-btn">获取验证码</div>
							</div>
							<div class="login-form-input">
								<input id="reg-pwd" type="password" placeholder="登录密码"/><i>&#xe6c0;</i>
							</div>
							<div class="login-form-input">
								<input id="reg-invent" type="text" placeholder="邀请码（选填）"/><i>&#xe6cc;</i>
							</div>
							<div id="reg-form-submit" class="login-btn">立即注册</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
function login(){
	$.ajax({
		type: "POST",
		url: "pdata/index/login.do",
		data:{
			mobile: $("#login-mobile").val(),
			password: $("#login-pwd").val()
		},
		dataType: "text", 
		async: false,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
				window.location.reload();
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function register(){
	$.ajax({
		type: "POST",
		url: "pdata/index/register.do",
		data:{
			mobile: $("#reg-mobile").val(),
			password: $("#reg-pwd").val(),
			msgCode: $("#reg-code").val(),
			inventCode: $("#reg-invent").val()
		},
		dataType: "text", 
		async: false,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
			
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

function msgCode(){
	$.ajax({
		type: "POST",
		url: "pdata/index/msgCode.do",
		data:{
			mobile: $("#reg-mobile").val()
		},
		dataType: "text", 
		async: false,
		traditional: true,
		success: function(data){
			var jsonObject = eval('(' + data + ')');
			if(jsonObject.result==1){
			
			}else{
				layer.msg(jsonObject.exception);
			}
		}
	});
}

var msgtime = 99;
function checkTime(){
	$("#code-btn").html("重新获取（"+msgtime+"s）");
	if(msgtime>0){
		msgtime = msgtime - 1;
		setTimeout("checkTime()", 1000);
	}else{
		$("#code-btn").css("color", "#4c39a8");
		$("#code-btn").html("获取验证码");
		$("#code-btn").removeAttr("disabled")
	}
}

$(document).ready(function(){
	$(".login-tab-item").click(function(){
		$(".login-tab-item.active").removeClass("active");
		$(this).addClass("active");
		$(".login-form").hide();
		$("#"+$(this).attr("url")).show();
	});
	
	$(".login-form .login-form-input input").focus(function(){
		$(this).next().css("color", "#4c38a7");
	});
	$(".login-form .login-form-input input").blur(function(){
		$(this).next().css("color", "#999999");
	});
	
	$("#login-form-submit").click(function(){
		login();
	});
	$("#reg-form-submit").click(function(){
		register();
	});
	
	$("#code-btn").click(function(){
		if($("#reg-mobile").val().length!=11){
			layer.msg("请填写正确的手机号！");
			return;
		}
		if($("#code-btn").attr("disabled")=="disabled"){
			return;
		}
		msgCode();
		msgtime = 99;
		$("#code-btn").css("color", "#999999");
		$("#code-btn").html("重新获取（"+msgtime+"s）");
		msgtime--;
		$("#code-btn").attr("disabled", "disabled");
		setTimeout("checkTime()", 1000);
	});
});
</script>
</html>