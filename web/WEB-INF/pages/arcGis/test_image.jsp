<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test_image.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
  <table class="listTable" id="sheet">
	<tr>
		<td class="label">
			人员姓名
		</td>
		<td class="content">
			<input type="text" name="nameSearch" id="name"	class="text medium"	alt="allowBlank:false,vtext:''"  value="111111"/>
		</td>
		<td class="label">
			所属公司
		</td>
		<td class="content">	
			<input type="text" name="nameSearch" id="name"	class="text medium"	alt="allowBlank:false,vtext:''"  value="222222"/>
		</td>
	</tr>
	<tr>
		<td >
			<img src="D:\apache-tomcat-6.0.39_shandong\webapps\partner\arcGis\tempImage\tmp59712.png" src='D:\apache-tomcat-6.0.39_shandong\webapps\partner\arcGis\tempImage\tmp59712.png' /> 
		</td>
	</tr>

</table>
</html>
