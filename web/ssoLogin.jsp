<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  import="com.ultrapower.casp.client.LoginUtil" %>
<%@ page  import="com.ultrapower.casp.client.config.CaspClientConfig" %>
<%@ page  import="com.ultrapower.casp.common.code.ResultCode" %>
<%@ page  import="com.ultrapower.casp.common.datatran.data.ticket.TransferTicket" %>
<%@ page  import="com.ultrapower.casp.common.datatran.data.user.UserInfo" %>
<%@ page  import="com.ultrapower.casp.common.util.CodeUtil" %>
<%@ page  import="java.net.URLEncoder" %>

 
    
<%
 
 	String loginServiceUrl = URLEncoder.encode("http://10.168.87.187:8080/partner/portalLoginService.jsp");
	String loginServiceUrl1 = URLEncoder.encode("http://10.168.68.15:8070/CCIP2/logon.do");
	
	String loginServiceUrl2 = URLEncoder.encode("http://10.168.182.47:58045/pasm/jsp/portalLoginService.jsp");
	String loginServiceUrl3 = URLEncoder.encode("http://10.168.87.154:8089/eoms/sso");
	
	// 知识管理
	String loginServiceUrl4 = URLEncoder.encode("http://10.168.182.193:8080/portalDemo/km.sso?action=getShare");
	
	String redirectUrl1 = URLEncoder.encode("http://10.168.68.15:8070/CCIP2/");
	String redirectUrl = URLEncoder.encode("http://10.168.87.187:8080/login/login.jsp?user=login");	
	// http://10.168.182.47:58045/pasm/index.htm 
	String redirectUrl2 = URLEncoder.encode("http://10.168.182.47:55100/nop3_eoms/sign.action");	
	//String redirectUrl2 = URLEncoder.encode("http://10.168.182.47:58045/pasm/index.htm");
	// http://10.168.87.154:8089/eoms/main.jsp?id=33
	String redirectUrl3 = URLEncoder.encode("http://10.168.87.154:8089/eoms/main.jsp?id=10");	
	String redirectUrl4 = URLEncoder.encode("http://10.168.182.193:8080/portalDemo/");	
%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 <a href="ssoLoginService.jsp?loginServiceUrl=<%= loginServiceUrl %>&requestUrl=<%= redirectUrl %>&appCode=DATA.SEC.PARTNERAM" target="_blank">合作伙伴</a>
 <a href="ssoLoginService.jsp?loginServiceUrl=<%= loginServiceUrl1 %>&requestUrl=<%= redirectUrl1 %>&appCode=DATA.SEC.CCIPAM" target="_blank">投诉平台</a>
 
 <a href="ssoLoginService.jsp?loginServiceUrl=<%= loginServiceUrl2 %>&requestUrl=<%= redirectUrl2 %>&appCode=DATA.SEC.COSAM" target="_blank">集中操作</a> 
 <a href="ssoLoginService.jsp?loginServiceUrl=<%= loginServiceUrl3 %>&requestUrl=<%= redirectUrl3 %>&appCode=DATA.SEC.EOMSAM" target="_blank">EOMS</a>
 <a href="ssoLoginService.jsp?loginServiceUrl=<%= loginServiceUrl4 %>&requestUrl=<%= redirectUrl4 %>&appCode=DATA.SEC.KMAM" target="_blank">知识管理</a>
 </body>
</html>