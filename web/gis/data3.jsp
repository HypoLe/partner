
<%@page import="com.boco.eoms.partner.geo.dao.ResourceInfoDao"%><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.geo.client.*" %>
<%@page import="com.boco.eoms.partner.geo.model.*" %>
<%@page import="com.boco.eoms.partner.geo.parse.*" %>

<%@page import="com.google.gson.*"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.boco.eoms.partner.geo.util.DateTypeAdapter"%>
<%@page import="com.boco.eoms.partner.geo.util.TimestampTypeAdapter"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
	ResourceInfoDao dao = (ResourceInfoDao)context.getBean("resourceInfoDao");
	List list =dao.getAllResourceInfo();
	
	Gson gson = new Gson();
	String jsonString = gson.toJson(list,List.class);
	out.print(jsonString);
%>
