<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page
	import="com.boco.eoms.partner.interfaces.services.partnerservice.PartnerService"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
 
 
 		<%
 		String result = "";
			if ("addAll".equals(request.getParameter("action"))) {
				PartnerService service  = new PartnerService();
				 result= service.addAllPartner();
			}
		 
		%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>批量添加或修改合作伙伴信息</title>




	</head>

	<body>
	<form action="">
	
	<button value="addAll" name="action">批量添加</button>	
	<br>
	<%=result %>
	</form>
	</body>
</html>
