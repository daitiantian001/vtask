<%@ page language="java" import="java.util.*,com.lmnml.group.util.*" pageEncoding="UTF-8"%>
<%if(CookieTools.getCookie(request, "puserId")!=null && CookieTools.getCookie(request, "puserId").getValue().trim().length()>0){%>
<jsp:forward page="home/task.jsp"></jsp:forward>
<%}else{%>
<jsp:forward page="home/login.jsp"></jsp:forward>
<%}%>