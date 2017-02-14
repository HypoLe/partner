<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List,java.util.Map,java.util.HashMap,com.boco.eoms.commons.system.role.model.TawSystemRole;"%>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawSystemRoleteaseForm'});
});
</script>

<html:form action="/tawSystemRoleteases.do?method=save" styleId="tawSystemRoleteaseForm" method="post"> 

<fmt:bundle basename="config/applicationResource-tawsystemroletease">
<table class="formTable">
<tr>
<% List workFlowList =(List) request.getAttribute("workflowMaplist"); 
						for(int i=0;i<workFlowList.size();i++){
						HashMap map = (HashMap) workFlowList.get(i);
						String workflowName =(String) map.get("workflow");
						Integer count = (Integer) map.get("count");
						%>
						<td colspan="<%=count%>" align="center">
						<%=workflowName %>
						</td>
						<%} %>
</tr>
<tr>
<% List roleList = (List) request.getAttribute("roleList");  
   for(int j=0;j<roleList.size();j++){
   List list = (List)roleList.get(j);
   for(int k=0;k<list.size();k++){
   TawSystemRole role = (TawSystemRole) list.get(k);
   long roleId = role.getRoleId();
   String roleName = role.getRoleName();
 %>
 <td>
 <%=roleName %>
 </td>
 <%}} %>
</tr>
</table>
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawSystemRoletease.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.workflowId" />
		</td>
		<td class="content">
			<html:text property="workflowId" styleId="workflowId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.workflowId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.workflowName" />
		</td>
		<td class="content">
			<html:text property="workflowName" styleId="workflowName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.workflowName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.roleId" />
		</td>
		<td class="content">
			<html:text property="roleId" styleId="roleId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.roleId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.roleName" />
		</td>
		<td class="content">
			<html:text property="roleName" styleId="roleName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.roleName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.subRole_id" />
		</td>
		<td class="content">
			<html:text property="subRole_id" styleId="subRole_id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.subRole_id}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.subRoleName" />
		</td>
		<td class="content">
			<html:text property="subRoleName" styleId="subRoleName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.subRoleName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.workTemple" />
		</td>
		<td class="content">
			<html:text property="workTemple" styleId="workTemple"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.workTemple}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.timeLimite" />
		</td>
		<td class="content">
			<html:text property="timeLimite" styleId="timeLimite"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.timeLimite}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.skillRequire" />
		</td>
		<td class="content">
			<html:text property="skillRequire" styleId="skillRequire"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.skillRequire}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.checkLine" />
		</td>
		<td class="content">
			<html:text property="checkLine" styleId="checkLine"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.checkLine}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.deptId" />
		</td>
		<td class="content">
			<html:text property="deptId" styleId="deptId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.deptId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.deptName" />
		</td>
		<td class="content">
			<html:text property="deptName" styleId="deptName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.deptName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.userId" />
		</td>
		<td class="content">
			<html:text property="userId" styleId="userId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.userId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoletease.userName" />
		</td>
		<td class="content">
			<html:text property="userName" styleId="userName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleteaseForm.userName}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty tawSystemRoleteaseForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/tawSystemRoletease/tawSystemRoleteases.do?method=remove&id=${tawSystemRoleteaseForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawSystemRoleteaseForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>