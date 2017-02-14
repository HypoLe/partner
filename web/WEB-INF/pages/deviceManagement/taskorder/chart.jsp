<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'chart.jsp' starting page</title>
    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   代维任务工单统计：
   <hr>
   <select id="scType" onchange="loadChartBySelectType(this)">  
	<option value="1">请选择一种图表</option>  
	<option value="status">工单状态统计</option> 
	<option value="type">工单类型统计</option>  
	<option value="netGroup">网络类型统计</option>  
	<option value="efficiency">工单效率统计</option>  
</select>  
 <hr>  
 <div id="displayChart"></div>
 <script type="text/javascript">  
 	function loadChartBySelectType(sua){
 		var cType=sua.value; 
 		if(cType=="1"){   
     	  return ; 
     	 }
   		var disabledImageZone=document.getElementById("displayChart");   
    	disabledImageZone.innerHTML="";   
    	var imageZone = document.createElement('img');
    	imageZone.setAttribute('id','imageZone');
    	imageZone.setAttribute('src',"taskorder/taskOrderAction.do?method=createChart&chartType="+cType);
    	disabledImageZone.appendChild(imageZone);
 	}
 </script>  
 
   
  </body>
</html>
