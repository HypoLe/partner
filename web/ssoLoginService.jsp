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


out.println(request.getParameter("loginServiceUrl") + "---" + request.getParameter("requestUrl"));
String loginServiceUrl = request.getParameter("loginServiceUrl");

String appCode = request.getParameter("appCode");

String requestUrl = request.getParameter("requestUrl");
String rootTicket = (String) request.getSession().getAttribute(
		CodeUtil.ROOT_TICKET_KEY);

String url = "";

// 判断根票据是否为空
if (rootTicket != null) {
	// 获取从票据，参数”appId”和”slaveAcctId”需要应用系统自行替换
 
	TransferTicket ssoTicket = LoginUtil.getInstance().getSsoTicket(
			rootTicket, appCode, null);
	
	 
	// 票据为空，跳到错误页面
	if (ssoTicket == null) {
		// 票据为空，跳到错误页面
	}
	String strSsoTicket = ssoTicket.getSsoTicket();
	// 单点登录票据为空，跳到错误页面
	if (strSsoTicket == null) {
		// 单点登录票据为空，跳到错误页面
	}
	if (false) { // 应用系统为代填方式，判断逻辑需要自己补充
		// 跳转到认证网关的代填服务
		response.sendRedirect(CaspClientConfig.getInstance()
				.getCurServer().getAgentSsoUrl()
				+ "?"
				+ CodeUtil.APP_CODE
				+ "="
				+ "appId"
				+ "&"
				+ CaspClientConfig.TICKET_PARAM_NAME
				+ "="
				+ URLEncoder.encode(strSsoTicket, "UTF8"));
	 
	} else { // 应用系统为票据认证方式
		// 跳转到应用系统
		String enote = URLEncoder.encode(strSsoTicket);
		// 获取应用跳转url
		url =  loginServiceUrl +   (loginServiceUrl.indexOf("?") > -1 ? "&" : "?") +"requestUrl=" +  URLEncoder.encode(requestUrl) + "&" + CaspClientConfig.TICKET_PARAM_NAME + "=" + enote;
 
		// 跳转到应用系统认证模块
		  
		  System.out.println("ssoUrl=" + url);
		
	      response.sendRedirect(url);
	
	}
} else {
	// 根票据为null，跳转到错误页面
}

 


%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>
 <a href="<%= url %>"> link</a>
</body>
</html>