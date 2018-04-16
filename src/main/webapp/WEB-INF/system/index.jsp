<%@ page language="java" import="java.util.*, cn.net.vtask.util.*" pageEncoding="UTF-8"%>
<%if(CookieTools.getCookie(request, "userId")!=null && CookieTools.getCookie(request, "userId").getValue().trim().length()>0){%>
<jsp:forward page="home/index.jsp"></jsp:forward>
<%}else{%>
<jsp:forward page="home/login.jsp"></jsp:forward>
<%}%>