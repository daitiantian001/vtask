<%@ page language="java" import="java.util.*, cn.net.vtask.util.*" pageEncoding="UTF-8"%>
<%if(CookieTools.getCookie(request, "puserId")!=null && CookieTools.getCookie(request, "puserId").getValue().trim().length()>0){%>
<jsp:forward page="task.do"></jsp:forward>
<%}else{%>
<jsp:forward page="login.do"></jsp:forward>
<%}%>