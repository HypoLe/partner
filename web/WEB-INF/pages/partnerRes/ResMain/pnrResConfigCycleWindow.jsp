<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'pnrResConfigWindow.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

  </head>
  <script type="text/javascript">
  var jq=jQuery.noConflict();
  jq(function(){
  	jq("#tijiao").click(function(){
  	    var sel = jq("#seldelcar").val();
  	    var pro = jq("#cycle").val();
		if(pro==""){
			alert("请选择巡检周期");
			return ;
		}
		window.returnValue=pro;
		window.close();
  		/*Ext.Ajax.request({
		method:"post",
		url: "${app}/partner/res/PnrResConfig.do?method=updateCycle",
		params:{
			seldelcar:sel,
			prov:pro
		},
		success: function(x){
			//刷新父页面
			window.opener.location.href=window.opener.location.href;
			alert("周期分配成功");	
			window.close();
		}
	 });*/
  	});
  })
  </script>
  <body>
   <html:form action="PnrResConfig" method="post" >
   <input type="hidden" value="${seldelcar}" id="seldelcar" />
   	<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				分配巡检周期
			</div>
		</caption>
			<tr>
				<td class="label">巡检周期</td>
				<td class="content">
					<select name="inspectCycle" class="select" id="cycle">
					<option value="">请选择</option>
					<c:forEach items="${cycleMap}" var="cycleMap" > 
						<option value="${cycleMap.key}">${cycleMap.value}</option>
					</c:forEach> 
				<select>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="button" value="提交" id="tijiao">
				</td>
			</tr>
	</table>
   </html:form>
  </body>
</html>
