<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String ipport = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<script type="text/javascript">
basePath = '<%=basePath%>';
</script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>system/css/basic.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>system/css/loading.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>system/scripts/bootstrap/css/bootstrap-datetimepicker.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>system/scripts/select/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>system/scripts/select/bootstrap-select.css"/>

<script type="text/javascript" src="<%=basePath%>system/scripts/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>system/scripts/basic.js"></script>
<script type="text/javascript" src="<%=basePath%>system/scripts/bootstrap/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>system/scripts/select/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>system/scripts/select/bootstrap-select.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>system/scripts/layer/css/layui.css"/>
<script type="text/javascript" src="<%=basePath%>system/scripts/layer/layui.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>system/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=basePath%>system/scripts/ztree/js/jquery.ztree.core.js"></script>

<script type="text/javascript">
layui.use('layer', function(){
	var layer = layui.layer;
});
$(document).ready(function(){

});
</script>
