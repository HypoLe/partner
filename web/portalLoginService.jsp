<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  import="com.ultrapower.casp.client.LoginUtil" %>
<%@ page  import="com.ultrapower.casp.client.config.CaspClientConfig" %>
<%@ page  import="com.ultrapower.casp.common.code.ResultCode" %>
<%@ page  import="com.ultrapower.casp.common.datatran.data.ticket.TransferTicket" %>
<%@ page  import="com.ultrapower.casp.common.datatran.data.user.UserInfo" %>
<%@ page  import="com.ultrapower.casp.common.util.CodeUtil" %>
<%@ page  import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm" %>
<%@ page  import="com.boco.eoms.commons.system.priv.bo.TawSystemPrivAssignOut" %>
<%@ page  import="com.boco.eoms.workplan.mgr.ITawwpStubUserMgr" %>
<%@ page  import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page  import="org.springframework.context.ApplicationContext" %>
<%@ page  import="com.boco.eoms.commons.ui.bo.TawSystemTreeBo" %>

<%@ page  import="com.boco.eoms.commons.system.session.action.TawSystemSessionAction" %>

<%@ page  import="org.apache.struts.action.ActionMapping" %>
<%@ page  import="java.net.URLEncoder" %>
<%@ page  import="java.io.File" %>


    
<%
String redirectUrl = request.getParameter("redirectUrl");
System.out.println("redirectUrl=" + redirectUrl + "--- \n" + LoginUtil.getInstance().checkTicket(
		(HttpServletRequest) request) + LoginUtil
		.getInstance()
		.analysTicket(
				request
						.getParameter(CaspClientConfig.TICKET_PARAM_NAME)));

if (request.getSession().getServletContext().getAttribute("4AInit") == null) {
	String configPath = request.getSession().getServletContext()
			.getRealPath("/")
			+ File.separator
			+ "WEB-INF"
			+ File.separator
			+ "casp_client_config.properties";
	boolean initTrue = LoginUtil.getInstance().init(configPath);
	request.getSession().getServletContext().setAttribute("4AInit",
			initTrue);
	System.out.println("init4A:" + initTrue);
}

if (LoginUtil.getInstance().isEnable()) {
	// 用户是否已经登录，需要应用系统自己填写代码
	if (LoginUtil.getInstance().hasAliveServer()) {
		if (LoginUtil.getInstance().checkTicket(
				(HttpServletRequest) request)) {
			TransferTicket ticket = LoginUtil
					.getInstance()
					.analysTicket(
							request
									.getParameter(CaspClientConfig.TICKET_PARAM_NAME));

			if (ticket != null && ticket.getRetCode() != null
					&& ticket.getRetCode().equals(ResultCode.RESULT_OK)) {
				UserInfo userInfo = LoginUtil.getInstance()
						.qryUserByTicket(ticket);
				System.out.println("userId:" + userInfo.getAccountID()
						+ "--" + userInfo.toString());
				if (!userInfo.getRetCode().equals(ResultCode.RESULT_OK)) {
					// 根据错误码生成提示信息；
				} else {
					((HttpServletRequest) request).getSession()
							.setAttribute(CodeUtil.ROOT_TICKET_KEY,
									ticket.getRootTicket());
					// 应用资源根据帐号信息做登录后业务处理；
					System.out.println("获得登录用户："
							+ userInfo.getAccountID());
				 
					TawSystemSessionForm sessionform = new TawSystemSessionForm();
					sessionform.setUserid(userInfo.getAccountID());
					TawSystemSessionAction sessionaction = new TawSystemSessionAction();
					ApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getSession().getServletContext());
					ITawwpStubUserMgr tawwpStubUserMgr = (ITawwpStubUserMgr) ctx.getBean("tawwpStubUserMgr"); 
					
					request.setAttribute("tawwpStubUserMgr", tawwpStubUserMgr);
					
					ActionMapping mapping = new ActionMapping();
					try {
					sessionaction.performInit4A(mapping, sessionform, request,
							response);
					
					}catch (Exception e) {
						e.printStackTrace();
					}
					redirectUrl = (redirectUrl==null) ?  "/partner/index.jsp" : redirectUrl;
				 	System.out.println("准备跳转：" +  redirectUrl + "----" + request.getContextPath() );
					response.sendRedirect(redirectUrl);
					
					 
				}
			} else {
				// 根据错误码生成提示信息；
				out.println("错误的ticket" + ticket);
			}
			 
		}
	} else {
		// 使用应用资源本地认证
		out.println("无活跃的4A服务器" );
	}
} else {
	out.println("使用本地认证" );
}



%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
中文
</body>
</html>
