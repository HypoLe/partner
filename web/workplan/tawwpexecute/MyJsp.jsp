<%@ page language="java" import="com.boco.eoms.workplan.scheduler.*" pageEncoding="ISO-8859-1"%>
<%@ page import= "org.quartz.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  
  <body>sdfsdf
<%

    TawwpInspectionScheduler tawwpInspectionScheduler=new TawwpInspectionScheduler(); 
   tawwpInspectionScheduler.execute();
 %>
  </body>
</html>
