<%@page contentType="text/html;charset=ISO8859_1"%>
<%@page import="java.util.*"%>
<%@page import="com.boco.eoms.resmanage.operator.*"%>
<%
operator op = new operator();
String strName = null;
String strPwd = null;
if(request.getParameter("username") == null)
	strName = "0";
else
	strName = request.getParameter("username");

if(request.getParameter("password") == null)
	strPwd = "0";
else
	strPwd = request.getParameter("password");

String strRIP = request.getRemoteHost();
if(!op.operatorLogin(strName,strPwd,strRIP))
	response.sendRedirect("./index.jsp"); 
else
{
	session.setAttribute("UserInfo",op);
}
%>
<html>
<head>
<title>E-DIS 四川基础信息数据库系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO8859_1">
</head>
<frameset cols="250,*" frameborder="NO" border="0" framespacing="0" rows="*">
  <frameset cols="249,1" frameborder="NO" border="0" framespacing="0" rows="*">
    <frame name="leftFrame" scrolling="auto" noresize src="explorer.jsp">
    <frame name="rightFrame" scrolling="NO" noresize src="border.jsp">
  </frameset>
  <frameset rows="50,*" frameborder="NO" border="0" framespacing="0">
    <frame name="topFrame" scrolling="NO" noresize src="top.jsp">
    <frameset rows="*,25" frameborder="NO" border="0" framespacing="0">
      <frame name="mainFrame" scrolling="auto" noresize src="content.jsp">
      <frame name="bottomFrame" scrolling="NO" noresize src="bottom.jsp">
    </frameset>
  </frameset>
</frameset>
<noframes>
<body bgcolor="#FFFFFF" text="#000000">
</body>
</noframes>
</html>