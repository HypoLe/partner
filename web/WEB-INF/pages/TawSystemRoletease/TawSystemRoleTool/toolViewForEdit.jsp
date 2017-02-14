<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<fmt:bundle basename="config/applicationResource-tawsystemroletease">
<html:form action="/tawSystemRoleteases.do?method=roletoolupdate"
	styleId="tawSystemRoleteaseForm" method="post">


<table class="formTable">
	<caption>
		<div class="header center">修改角色</div>
	</caption>

	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.roleName" />
		</td>
		<td class="content">
			<html:text property="roleName" styleId="roleName" styleClass="text medium"
						readonly="true" value="${tawSystemRoleteaseForm.roleName}" />
		</td>
	</tr>
	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.roleId" />
		</td>
		<td class="content">
			<html:text property="roleId" styleId="roleId" styleClass="text medium"
						readonly="true" value="${tawSystemRoleteaseForm.roleId}" />
		</td>
	</tr>
	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.workflowName" />
		</td>
	
		<td class="content">
			<html:text property="workflowName" styleId="workflowName" styleClass="text medium"
						readonly="true" value="${tawSystemRoleteaseForm.workflowName}" />
		</td>
		</tr>
	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.workflowId" />
		</td>
		
		<td class="content">
			<html:text property="workflowId" styleId="workflowId" styleClass="text medium"
						readonly="true" value="${tawSystemRoleteaseForm.workflowId}" />
		</td>
		
		</tr>
	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.workTemple" />
		</td>
		
		<td class="content">
			<html:text property="workTemple" styleId="workTemple" styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.workTemple}" />
		</td>
		</tr>
	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.timeLimite" />
		</td>
		<td class="content">
			<html:text property="timeLimite" styleId="timeLimite" styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.timeLimite}" />
		</td>
		</tr>
	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.skillRequire" />
		</td>
		<td class="content">
			<html:textarea property="skillRequire" styleId="skillRequire" styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.skillRequire}" />
		</td>
		</tr>
	<tr>
		<td  class="label">
			<fmt:message key="tawSystemRoletease.checkLine" />
		</td>
		<td class="content">
			<html:text property="checkLine" styleId="checkLine" styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.checkLine}" />
		</td>
	</tr>
	
	</table>
	<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="保存" />
		</td>
	</tr>
</table>
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>