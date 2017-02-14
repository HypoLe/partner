<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>   
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@page import="com.boco.supplychain.util.StaticVariable"%>
<%
String webapp = request.getContextPath();
long contractId=StaticMethod.nullObject2Long(request.getAttribute("contractId"));
int specialtyId=StaticMethod.nullObject2int(request.getAttribute("specialtyId"));
System.out.println(specialtyId);
String contractName=(String)request.getAttribute("contractName");
String contractNo=(String)request.getAttribute("contractNo");
String hasSubContract=(String)request.getAttribute("hasSubContract");  //维护设备  //没其他作用，显示区别.
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <title>合同功能菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
<script type="text/javascript">
<!--
function confirmDel(){
  if( confirm("物业合同删除后不可恢复,您确认要删除吗？") ){
  	window.location='<%=webapp%>/contract/delete.do?contractId=<bean:write name="contractId" />';
  	document.forms[0].submit();
    return true;
  }
  else{
    return false;
  }
}
//-->
</script>
  </head>

  <body>
	<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
		<b><bean:write name="specialtyName"/>-><bean:write name="contractName"/>->(合同控制界面)</b>
	</P>
	<logic:equal name="view_type" value="viewView">
		<table align="center">
	  		<logic:equal value="true" name="hasSubContract">
	  				<tr><td><a href="<%=webapp%>/contract/propertyContract_SubContractView.do?contractId=<%=contractId%>">(查看)子合同|合同项信息</a>&nbsp;&nbsp;</td></tr>
	  		</logic:equal>
	  		<logic:equal value="false" name="hasSubContract">
	  				<tr><td><span>未配置(子合同|合同项)</span></td></tr>
	  		</logic:equal>
  		</table>
	</logic:equal>
	<logic:equal name="view_type" value="deleteView">
		<table align="center">
  		<logic:equal value="true" name="hasSubContract">
  				<tr><td><a href="<%=webapp%>/contract/propertyContract_SubContractView.do?contractId=<%=contractId%>">(查看)子合同|合同项信息</a>&nbsp;&nbsp;</td></tr>
  		</logic:equal>
  		<logic:equal value="false" name="hasSubContract">
  				<tr><td><span>未配置(子合同|合同项)</span></td></tr>
  		</logic:equal>
  		</table>
	</logic:equal>
	<logic:equal name="view_type" value="modifyView">
		<table align="center">
  		<tr><td><img alt="完成" src="<%=webapp%>/images/082.gif"><a href="<%=webapp%>/contract/propertiesContractPageUpdate_BaseInfo.do?contractId=<%=contractId%>">(修改)合同基本信息</a>&nbsp;<a href="<%=webapp%>/contract/propertyContract_view.do?contractId=<%=contractId%>">[查看]</a></td></tr>
  		<logic:equal value="true" name="hasSubContract">
  				<tr><td><img alt="完成" src="<%=webapp%>/images/082.gif"><a href="<%=webapp%>/contract/propertiesContract_SubContract_PageSaveUpdate.do?contractId=<%=contractId%>">(修改)子合同|合同项配置</a>&nbsp;&nbsp;<a href="<%=webapp%>/contract/propertyContract_SubContractView.do?contractId=<%=contractId%>">[查看]</a></td></tr>
  		</logic:equal>
  		<logic:equal value="false" name="hasSubContract">
  				<tr><td><img alt="未完成" src="<%=webapp%>/images/delete.gif"><a href="<%=webapp%>/contract/propertiesContract_SubContract_PageSaveUpdate.do?contractId=<%=contractId%>">(创建)子合同|合同项配置</a></td></tr>
  		</logic:equal>
  		</table>
	</logic:equal>
  		<logic:equal name="view_type" value="deleteView">
  			<iframe width="100%"  name="contractBaseinfoViewFrm" src="<%=webapp%>/contract/propertyContract_view.do?contractId=<bean:write name="contractId" />&view_type=deleteView"  style="position:absolute;HEIGHT: 100%;WIDTH: 100%; Z-INDEX: 0" frameborder="0" scrolling="auto"></iframe>
  		</logic:equal>
  		<logic:notEqual name="view_type" value="deleteView">
  			<iframe width="100%"  name="contractBaseinfoViewFrm" src="<%=webapp%>/contract/propertyContract_view.do?contractId=<bean:write name="contractId" />&view_type=viewView"  style="position:absolute;HEIGHT: 100%;WIDTH: 100%; Z-INDEX: 0" frameborder="0" scrolling="auto" ></iframe>
  		</logic:notEqual>
 <%@ include file="/common/footer_eoms_innerpage.jsp"%>	 

