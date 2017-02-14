<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawSystemRoleDescForm'});
});
</script>

<html:form action="/tawSystemRoleDescs.do?method=save" styleId="tawSystemRoleDescForm" method="post"> 

<fmt:bundle basename="config/applicationResource-tawsystemroledesc">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawSystemRoleDesc.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.roleId" />
		</td>
		<td class="content">
			<html:text property="roleId" styleId="roleId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.roleId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.roleName" />
		</td>
		<td class="content">
			<html:text property="roleName" styleId="roleName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.roleName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.dictCode" />
		</td>
		<td class="content">
			<html:text property="dictCode" styleId="dictCode"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.dictCode}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.workflowFlag" />
		</td>
		<td class="content">
			<html:text property="workflowFlag" styleId="workflowFlag"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.workflowFlag}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.workflowName" />
		</td>
		<td class="content">
			<html:text property="workflowName" styleId="workflowName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.workflowName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.templateCode" />
		</td>
		<td class="content">
			<html:text property="templateCode" styleId="templateCode"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.templateCode}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.templateName" />
		</td>
		<td class="content">
			<html:text property="templateName" styleId="templateName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.templateName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.timeLimit" />
		</td>
		<td class="content">
			<html:text property="timeLimit" styleId="timeLimit"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.timeLimit}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.skill" />
		</td>
		<td class="content">
			<html:text property="skill" styleId="skill"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.skill}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.kpi" />
		</td>
		<td class="content">
			<html:text property="kpi" styleId="kpi"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.kpi}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.orderFlag" />
		</td>
		<td class="content">
			<html:text property="orderFlag" styleId="orderFlag"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.orderFlag}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawSystemRoleDesc.notes" />
		</td>
		<td class="content">
			<html:text property="notes" styleId="notes"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawSystemRoleDescForm.notes}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty tawSystemRoleDescForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=remove&id=${tawSystemRoleDescForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawSystemRoleDescForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>