<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>     
<%@page import="com.boco.supplychain.util.StaticMethod"%>
<%@page import="com.boco.supplychain.util.StaticVariable"%>
<%
String webapp = request.getContextPath();
long contractId=StaticMethod.nullObject2Long(request.getAttribute("contractId"));
long accessModelId=StaticMethod.nullObject2Long(request.getAttribute("accessModelId"));
int planId=StaticMethod.nullObject2int(request.getAttribute("payPlanId"));
int serviceTypeId=StaticMethod.nullObject2int(request.getAttribute("serviceTypeId"));
int specialtyId=StaticMethod.nullObject2int(request.getAttribute("specialtyId"));
System.out.println(specialtyId);
String contractName=(String)request.getAttribute("contractName");
String contractNo=(String)request.getAttribute("contractNo");
String hasAccessModel=(String)request.getAttribute("hasAccessModel");  //是否已经配置了考核模型
String hasPayPlan=(String)request.getAttribute("hasPayPlan");   //付款计划
String hasServiceDevices=(String)request.getAttribute("hasServiceDevices");  //维护设备  //没其他作用，显示区别.
//由于网管专业维护合同没有合同费用这里面做处理
String allowPayPlan="true";
if(specialtyId==StaticVariable.AVAILABLE_SPECIALTY[1]){ //网管专业
	allowPayPlan="false";
}
request.setAttribute("allowPayPlan",allowPayPlan);

String view_type=StaticMethod.nullObject2String(request.getAttribute("view_type"));
if(view_type.equals("")){
	view_type="modifyView";  //默认是可编辑视图.
}
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
			<!-- 引用考核模型树 -->
	
<script type="text/javascript">
<!--
function confirmDel(){
  if( confirm("您确认要合同删除吗,下页继续确认!") ){
  	window.location='<%=webapp%>/contract/view.do?contractId=<bean:write name="contractId" />&view_type=delete';
    return true;
  }
  else{
    return false;
  }
}
function createPayPlan(){
	var hasServiceDevices='<%=hasServiceDevices%>';
	if(hasServiceDevices=="true"){
		return true;
	}else{
		alert("先(创建)合同服务对象,再创建付款计划!");
		return false;
	}
}

function getIE(e)
{ 
	var t=e.offsetTop; 
	var l=e.offsetLeft; 
	var w=e.offsetWidth;
	var h=e.offsetHeight;
	while(e=e.offsetParent)
	{ 
		t+=e.offsetTop; 
		l+=e.offsetLeft; 
	} 
	alert("top="+t+"\nleft="+l+"\nWidth="+w+"\nHeight="+h); 
}


function showAccessModleView(){
 // dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:300px;dialogHeight:400px";
 // dWin = window.showModalDialog("/supplychain/contract/selectAccessModalTree.jsp?contractId=<=contractId%>", window, dWinOrnaments);
  var pars="width=400px,height=500px,left=100,top=100,toolbar=no,menubar=no,resizable=yes,location=no,status=no,scroll:auto";
  var dWin= window.open("/supplychain/access/accessmanager/quote/accessModelTreeView.jsp?contractId=<%=contractId%>","mywin",pars);
}
function showAccessModleSelectView(){
 // dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:300px;dialogHeight:400px";
 // dWin = window.showModalDialog("/supplychain/contract/selectAccessModalTree.jsp?contractId=<%=contractId%>", window, dWinOrnaments);
  var pars="width=400px,height=600px,toolbar=no,menubar=no,resizable=yes,location=no,status=no,scroll:auto";
  var dWin= window.open("/supplychain/contract/selectAccessModalTree.jsp?contractId=<%=contractId%>","mywin",pars);

}
function showUpdateAccessModleSelectView(){
 // dWinOrnaments = "status:no;scroll:auto;resizable:no;dialogWidth:300px;dialogHeight:400px";
 // dWin = window.showModalDialog("/supplychain/contract/selectAccessModalTree.jsp?contractId=<%=contractId%>", window, dWinOrnaments);
 var pars="width=400px,height=600px,left=100,top=100,toolbar=no,menubar=no,resizable=yes,location=no,status=no,scroll:auto";
  dWin= window.open("/supplychain/contract/selectAccessModalTree.jsp?contractId=<%=contractId%>&accessModelId=<%=accessModelId%>&serviceTypeId=<%=serviceTypeId%>","mywin",pars);
}

//-->
</script>
  </head>

  <body>
 
  			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b><bean:write name="specialtyName"/>-><bean:write name="contractName"/>->(合同控制界面)</b>
			</P>
  	<table align="center" id="main">
  		
  		<logic:equal name="view_type" value="viewView">
	  		<logic:equal value="true" name="hasServiceDevices">
	  				<tr><td align="left">&nbsp;<a href='<%=webapp%>/contract/serviceFee_view.do?contractId=<bean:write name="contractId" />'>(查看)合同服务对象</a></td></tr>
	  		</logic:equal>
	  		<logic:equal value="false" name="hasServiceDevices">
	  				<tr><td align="left">未创建合同服务对象</td></tr>
	  		</logic:equal>
	  		 <logic:equal value="true" name="hasAccessModel">
	  				<tr><td align="left">&nbsp;<a href="javascript:showAccessModleView();">查看)考核模型</a></td></tr>
	  		</logic:equal>
	  		<logic:equal value="false" name="hasAccessModel">
	  			<tr><td align="left">未引用合同考核模型</td></tr>
	  		</logic:equal>
		  	<logic:equal name="allowPayPlan" value="true" >
		  		<logic:equal value="true" name="hasPayPlan">
		  			<tr><td align="left"><a href="#">(查看)合同付款计划</a></td></tr>
		  		</logic:equal>
		  		<logic:equal value="false" name="hasPayPlan">
		  			<tr><td align="left">未创建合同付款计划</td></tr>
		  		</logic:equal>
		  	</logic:equal>
  		</logic:equal>
  		<logic:equal name="view_type" value="deleteView">
  			<logic:equal value="true" name="hasServiceDevices">
	  				<tr><td align="left">&nbsp;<a href='<%=webapp%>/contract/serviceFee_view.do?contractId=<bean:write name="contractId" />'>(查看)合同服务对象</a></td></tr>
	  		</logic:equal>
	  		<logic:equal value="false" name="hasServiceDevices">
	  				<tr><td align="left">未创建合同服务对象</td></tr>
	  		</logic:equal>
	  		 <logic:equal value="true" name="hasAccessModel">
	  				<tr><td align="left">&nbsp;<a href="javascript:showAccessModleView();">(查看)考核模型</a></td></tr>
	  		</logic:equal>
	  		<logic:equal value="false" name="hasAccessModel">
	  			<tr><td align="left">未引用合同考核模型</td></tr>
	  		</logic:equal>
		  	<logic:equal name="allowPayPlan" value="true" >
		  		<logic:equal value="true" name="hasPayPlan">
		  			<tr><td align="left"><a href="#">(查看)合同付款计划</a></td></tr>
		  		</logic:equal>
		  		<logic:equal value="false" name="hasPayPlan">
		  			<tr><td align="left">未创建合同付款计划</td></tr>
		  		</logic:equal>
		  	</logic:equal>
  		</logic:equal>
  		<logic:equal name="view_type" value="modifyView">
	  		<tr><td><img alt="完成" src="<%=webapp%>/images/082.gif"><a href="<%=webapp%>/contract/pageUpdateMain.do?contractId=<%=contractId%>">(修改)合同基本信息</a>&nbsp;<a href='<%=webapp%>/contract/view.do?contractId=<bean:write name="contractId" />&view_type=view'>[查看]</a></td></tr>
	  		<logic:equal value="true" name="hasServiceDevices">
	  				<tr><td><img alt="完成" src="<%=webapp%>/images/082.gif"><a href="<%=webapp%>/contract/serviceContractMoneyUpdate_Page.do?contractId=<%=contractId%>">(修改)合同服务对象</a>&nbsp;<a href='<%=webapp%>/contract/serviceFee_view.do?contractId=<bean:write name="contractId" />'>[查看]</a></td></tr>
	  		</logic:equal>
	  		<logic:equal value="false" name="hasServiceDevices">
	  				<tr><td><img alt="未完成" src="<%=webapp%>/images/delete.gif"><a href="<%=webapp%>/contract/serviceContractMoneySave_Page.do?contractId=<%=contractId%>">(创建)合同服务对象</a></td></tr>
	  		</logic:equal>
	  		 <logic:equal value="true" name="hasAccessModel">
	  				<tr><td><img alt="完成" src="<%=webapp%>/images/082.gif"><a href="javascript:showUpdateAccessModleSelectView();">(修改)考核模型定制</a>&nbsp;<a href="javascript:showAccessModleView();">[查看]</a></td></tr>
	  		</logic:equal>
	  		<logic:equal value="false" name="hasAccessModel">
	  			<tr><td><img alt="未完成" src="<%=webapp%>/images/delete.gif"><a href="javascript:showAccessModleSelectView();">(引用)考核模型定制</a></td></tr>
	  		</logic:equal>
		  	<logic:equal name="allowPayPlan" value="true" >
		  		<logic:equal value="true" name="hasPayPlan">
		  				<tr><td><img alt="完成" src="<%=webapp%>/images/082.gif"><a href="<%=webapp%>/pay/payPlan.do?method=updatePayPlanList&planId=<%=planId%>">(修改)付款计划定制</a></td></tr>
		  		</logic:equal>
		  		<logic:equal value="false" name="hasPayPlan">
		  			<tr><td><img alt="未完成" src="<%=webapp%>/images/delete.gif"><a href="<%=webapp%>/pay/payPlan.do?method=addPayPlan&contractNo=<%=contractNo%>" onclick="return createPayPlan();">(创建)付款计划定制</a></td></tr>
		  		</logic:equal>
		  	</logic:equal>
  		</logic:equal>
  	

  	<%--	
  	  	<tr><td>
			<a href="#" onclick="return confirmDel();"><bean:message key="label.remove"/>合同</a>
		</td></tr>
  	--%></table>
  		<logic:equal name="view_type" value="deleteView">
  		<iframe width="100%"  name="contractBaseinfoViewFrm" src="<%=webapp%>/contract/view.do?contractId=<bean:write name="contractId" />&view_type=deleteView"  style="position:absolute;HEIGHT: 100%;WIDTH: 100%; Z-INDEX: 0" frameborder="0" scrolling="auto"></iframe>
  		</logic:equal>
  		<logic:notEqual name="view_type" value="deleteView">
  		<iframe width="100%"  name="contractBaseinfoViewFrm" src="<%=webapp%>/contract/view.do?contractId=<bean:write name="contractId" />&view_type=viewView"  style="position:absolute;HEIGHT: 100%;WIDTH: 100%; Z-INDEX: 0" frameborder="0" scrolling="auto"></iframe>
  		</logic:notEqual>

 <%@ include file="/common/footer_eoms_innerpage.jsp"%>
