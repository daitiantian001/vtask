<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="top-bar">
	<div class="top-bar-block">
		<img class="top-logo" src="platform/css/images/icon_platform_logo.png"/>
		<div class="top-menus">
			<div id="task-menu" class="top-menu-bar" url="task">微任务</div>
			<div id="purse-menu" class="top-menu-bar" url="purse">我的钱包</div>
		</div>
		<img class="top-photo" src="//iconfont.alicdn.com/t/1493123647101.png@200h_200w.jpg"/>
		<div class="publish-btn">立即发布</div>
		<div class="top-win-block" style="display:none;">
			<div class="nick-name">小米商城</div>
			<div class="win-menu-item" url="merchant">商家信息</div>
			<div class="win-menu-item" url="logout">退出</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".top-menu-bar").click(function(){
		window.location.href = "platform/"+$(this).attr("url")+".do";
	});
	
	$(".win-menu-item").click(function(){
		window.location.href = "platform/"+$(this).attr("url")+".do";
	});
	
	$(".publish-btn").click(function(){
		window.location.href = "platform/publish.do";
	});
	
	$(".top-photo").click(function(){
		$(".top-win-block").fadeIn(200);
		$("body").bind("click", function(event){
			var e = e || window.event; 
			var elem = e.target || e.srcElement; 
			while (elem) {
				if (elem.className == "top-win-block" || elem.className == "top-photo") {
					return; 
				}
				elem = elem.parentNode; 
			}
			$("body").unbind("click");
			$(".top-win-block").fadeOut(200);
		});
	});
});
</script>