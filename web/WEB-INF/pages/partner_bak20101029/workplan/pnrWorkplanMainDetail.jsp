<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.Map"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.partner.workplan.model.PnrWorkplanAudit"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";
Map map = (Map)request.getAttribute("exeCont");
%>
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('plan-info', '工作计划信息 ');
        	tabs.addTab('finish-info', '已完成工作任务信息 ');
    		tabs.activate(0);
	});
</script>
<fmt:bundle basename="com/boco/eoms/partner/workplan/config/applicationResources-partner-workplan">
<div id="info-page">
  <div id="plan-info" class="tabContent">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrWorkplanMain.form.heading"/></div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanNO" />
		</td>
		<td class="content">
			${pnrWorkplanMainForm.workplanNO}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.workplanName" />
		</td>
		<td class="content">
			${pnrWorkplanMainForm.workplanName}
		</td>
	</tr>	
	<tr>
		<td class="label" style="vertical-align:middle">
			服务协议名称：
		</td>
		<td class="content" >
			${pnrWorkplanMainForm.agreementName}
		</td>	
		<td class="label" style="vertical-align:middle">
			服务协议编号：
		</td>
		<td class="content" >
			<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${pnrWorkplanMainForm.agreementId }" target="_blank">		
				${pnrWorkplanMainForm.agreementNO}
			</a>
			<html:hidden property="agreementId" value="${pnrWorkplanMainForm.agreementId}" />
		</td>
	</tr>
	
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.startTime" />
		</td>
		<td class="content">
			${pnrWorkplanMainForm.startTime}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.endTime" />
		</td>
		<td class="content">
			${pnrWorkplanMainForm.endTime}
		</td>
	</tr>

	<tr>
	<td colspan="4">
	<div id="workDiv">
	<span style="font-weight:bold;" >工作任务：</span>
	
	<table class="formTable">
	<c:forEach var="work" items="${workList}" varStatus="stauts">
	
	<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.workContent" />
		</td>
		<td class="content" colspan="3">
			${work.workContent}
		</td>
	</tr>
	<tr>	
	<td class="label" style="vertical-align:middle">
		工作完成标准
	</td>
	<td class="content" colspan="3">
		${work.workStandard}
	</td>
	</tr>	
	<tr>		
		<td class="label" style="vertical-align:middle">
			工作任务类型
		</td>
		<td class="content">
				<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />					
		</td>	
		<td class="label" style="vertical-align:middle">
			工作任务执行周期
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-agreement" dictId="workCycle" itemId="${work.cycle}" beanId="id2nameXML" />			
		</td>					
	</tr>	
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.startTime" />
		</td>
		<td class="content">
			${work.startTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanWork.endTime" />
		</td>
		<td class="content">
			${work.endTimeStr}
		</td>	
	</tr>
<!-- 
		<tr>	
		<td class="label" style="vertical-align:middle">
			工作考核标准
		</td>
		<td class="content" colspan="3">
			${work.evaStandard}
		</td>
		</tr>
-->		
	</c:forEach>
		</table>
	</div>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrWorkplanMain.accessories" />
		</td>
		<td class="content" colspan="3">
 			<eoms:attachment name="pnrWorkplanMainForm" property="accessoriesId" scope="request" idField="${pnrWorkplanMain.accessoriesId}" appCode="workplan"  viewFlag="Y"/> 
		</td>
	</tr>
</table>

  </div>

<div id="finish-info" class="tabContent">
	<div id="workDiv">
	<c:if test="${workExeListsize==0}">
		暂无完成任务
	</c:if>
	<table class="formTable">
	<c:forEach var="work" items="${workExeList}" varStatus="stauts">
			<tr>
					<th colspan="4"><b>第${stauts.count} 项：</b></th>
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrWorkplanWork.startTime" />
				</td>
				<td class="content">
					${work.startTimeStr}
				</td>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrWorkplanWork.endTime" />
				</td>
				<td class="content">
					${work.endTimeStr}
				</td>	
				<tr>
				<td class="label" style="vertical-align:middle">
					<fmt:message key="pnrWorkplanWork.workContent" />
				</td>
				<td class="content" colspan="3">
					${work.workContent}
				</td>
				</tr>
				<tr>	
				<td class="label" style="vertical-align:middle">
					工作完成标准
				</td>
				<td class="content" colspan="3">
					${work.workStandard}
				</td>
				</tr>
				<tr>
				<td class="label" style="vertical-align:middle">
					完成情况说明
				</td>
				<td class="content" colspan="3">
					<c:forEach items="${exeCont}" var="item"> 
						<c:if test="${item.key==work.id}">
							${item.value} <br/> 
						</c:if>
					</c:forEach> 
				</td>
				</tr>				
	</c:forEach>
		</table>
	</div>
</div>	
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>