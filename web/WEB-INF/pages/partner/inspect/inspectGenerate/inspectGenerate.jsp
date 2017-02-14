<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'PnrResConfigAdd.jsp' starting page</title>
    
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
  	<font color="red" size="4">本页面为测试、开发人员专用</font>
  		
  
	<html:form action="inspectGenerateAction.do?method=generateInspectPlanResAll" method="post" styleId="resForm">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				产生巡检资源
			</div>
		</caption>
		
		<tr><td class="label">月份</td>
			<td> 
			<select name="resMonth">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>
			</td>
			<td align="center" >
				<input type="submit" value="提交" id="tijiao">
			</td>
		</tr>
		</table>
	</html:form>  
    <br/>
    <br/>
  
  	<!-- 
	<html:form action="inspectGenerateAction.do?method=generateInspectPlanRes" method="post" styleId="resForm">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				按周期产生资源
			</div>
		</caption>
			<tr>
				<td class="label">巡检周期</td>
				<td class="content">
					<select name="cycle" class="select" id="cycle">
					<option value="">请选择</option>
					<c:forEach items="${cycleMap}" var="cycleMap" > 
						<option value="${cycleMap.key}">${cycleMap.value}</option>
					</c:forEach> 
				<select>
				</td>
			
				<td class="label">开始时间</td>
				<td>
					<html:text property="cycleStartTime"  styleId="assetUseTime" 
	                        styleClass="text medium"
	                        value="" 
	                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" readonly="true" />
				</td>
			
			</tr>
			
			<tr>
				<td class="label">结束时间</td>
				<td>
					<html:text property="cycleEndTime"  styleId="assetUseTime" 
	                        styleClass="text medium"
	                        value="" 
	                        onclick="popUpCalendar(this, this,'yyyy-mm-dd 23:59:59',-1,-1,false,-1);" readonly="true" />
				</td>
				<td align="center" colspan="2">
					<input type="submit" value="提交" id="tijiao">
				</td>
			</tr>
	</table>
    </html:form>
    
     -->
    
    <br/>
    <br/>
	<html:form action="inspectGenerateAction.do?method=generateInspectPlanItem" method="post" styleId="resForm">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				产生巡检项
			</div>
		</caption>
		<tr>
			<td class="label"> 
			
			</td>
			<td align="center" >
				<input type="submit" value="提交" id="tijiao">
			</td>
		</tr>
		</table>
	</html:form>  
	
	<br/>
    <br/>
    <c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
	<html:form action="inspectGenerateAction.do?method=generateTLPlanRes" method="post">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				<font color="red">产生线路巡检资源</font>
			</div>
		</caption>
		
		<tr><td class="label">月份</td>
			<td> 
			<select name="resMonth">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>
			</td>
			<td align="center" >
				<input type="submit" value="提交" id="tijiao">
			</td>
		</tr>
		</table>
	</html:form>  
	<br/>
    <br/>
	<html:form action="inspectGenerateAction.do?method=generateTLPlanItem" method="post">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				<font color="red">产生线路巡检项</font>
			</div>
		</caption>
		<tr>
			<td class="label"> 
			
			</td>
			<td align="center" >
				<input type="submit" value="提交" id="tijiao">
			</td>
		</tr>
		</table>
	</html:form>  
	<br/>
    <br/>
    </c:if>
	<html:form action="inspectGenerateAction.do?method=generateInspectPlan" method="post" styleId="resForm">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				根据上月计划生成本月计划（上月计划制定时选择了下月复制的才能复制）
			</div>
		</caption>
		<tr>
			<td class="label"> 
			
			</td>
			<td align="center" >
				<input type="submit" value="提交" id="tijiao">
			</td>
		</tr>
		</table>
	</html:form>  
	
	
	<br/>
    <br/>
	<html:form action="inspectGenerateAction.do?method=saveStatisticAreaData" method="post" styleId="resForm">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				采集区域巡检数据（巡检统计）
			</div>
		</caption>
		<tr>
			<td class="label"> 
			
			</td>
			<td align="center" >
				<input type="submit" value="提交" id="tijiao">
			</td>
		</tr>
		</table>
	</html:form>  
	
	<br/>
    <br/>
	<html:form action="inspectGenerateAction.do?method=saveStatisticPartnerData" method="post" styleId="resForm">
		<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				采集巡检单位巡检数据（巡检统计）
			</div>
		</caption>
		<tr>
			<td class="label"> 
			
			</td>
			<td align="center" >
				<input type="submit" value="提交" id="tijiao">
			</td>
		</tr>
		</table>
	</html:form>  
  </body>
</html>
