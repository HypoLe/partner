<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.forceCheck.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
 v = new eoms.form.Validation({form:'pnrForceCheckPlanForm'});
});
function addTask(){
	var url=eoms.appPath+'/forceCheck/forceCheckPlan.do?method=addTask';
	location.href=url}
</script>
<eoms:xbox id="pollingManTree" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText="巡检负责人" 
	  	valueField="pollingMan" handler="pollingManName"
		textField="pollingManName"
		checktype="user" single="true"
		data='${pollingManData}'></eoms:xbox>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:form action="/forceCheckPlan.do?method=save" styleId="pnrForceCheckPlanForm" method="post">
<fmt:bundle basename="com/boco/eoms/forceCheck/config/applicationResources-forceCheck"> 
	<html:hidden name="pnrForceCheckPlanForm" property="id" />
   <table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="forceCheckPlan.form.title"/></div>
	</caption>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.planName" />&nbsp;*	
		</td>
		<td class="content" colspan="3">
		   ${pnrForceCheckPlanForm.planName}
		</td>
	  </tr>	 
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckRoute" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.forcecheckRoute}
		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckRouteStage" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.forcecheckRouteStage}
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckPlace" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.forcecheckPlace}
		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckGpsFacility" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.forcecheckGpsFacility}
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.startDate" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.startDate}

		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.endDate" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.endDate}
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.planTime" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.planTime}
		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.planContent" />&nbsp;*
		</td>
		<td class="content">
		 ${pnrForceCheckPlanForm.planContent}
		</td>
	  </tr> 
      </table>
<display:table  name="pnrForceCheckTaskList" cellspacing="0" cellpadding="0"
      id="pnrForceCheckTaskList"  pagesize="${pageSize}" class="table pnrForceCheckTaskList" 
      export="false" 
      requestURI="${app}/forceCheck/forceCheckTask.do?method=search"
      sort="list" partialList="true" size="resultSize">
      <display:column property="taskName" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.taskName" />
	  <display:column property="siteName" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.siteName" />
	  <display:column property="pollingMan" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.pollingMan" />
	  <display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.startTime"  format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
	  <display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.endTime"  format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
      <display:column sortable="true" headerClass="sortable" title="任务状态">
			<c:if test="${pnrForceCheckTaskList.status eq '0'}">
				新建状态
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '1'}">
				任务执行中
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '2'}">
				任务归档
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '-1'}">
				计划取消或终止
			</c:if>
		</display:column>		
 
</display:table>


</fmt:bundle>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>