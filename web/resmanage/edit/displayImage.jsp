<%@page contentType="text/html;charset=ISO8859_1"%>
<%@page import="java.util.*"%>
<%@page import="com.boco.eoms.resmanage.entity.*"%>
<%@page import="mcs.common.db.*"%>
<%
/**
*@ E-DIS (四川省)
*@ Copyright : (c) 2003
*@ Company : BOCO.
*@ 显示图片信息
*@ version 1.0
**/
%>
<%

String path = null;
if(request.getParameter("path") != null)
	path = request.getParameter("path");

%>
<html>
<head>
<title>显示图片</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO8859_1">

</head>
<body bgcolor="#eeeeee" text="#000000" class="listStyle">
<img src=<%=path%>>
</body>
</html>

