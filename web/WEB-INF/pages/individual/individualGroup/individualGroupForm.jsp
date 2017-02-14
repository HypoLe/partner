<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'individualGroupForm'});
});
</script>

<html:form action="/individualGroups.do?method=save" styleId="individualGroupForm" method="post"> 

<fmt:bundle basename="config/applicationResource-individualgroup">

<table class="formTable">
	<caption>
		<div class="header center">
		<bean:message key='individualGroup.detail.heading'/>
		</div>
	</caption>


	<tr>
		<td class="label" align="right">
			<bean:message key='individualGroup.groupName'/>
		</td>
		<td class="content">
			<html:text property="groupName" styleId="groupName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualGroupForm.groupName}" />
		</td>
	</tr>

		<tr>
		<td class="label" align="right">
		描&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp述
		</td>
		<td class="content">
			<html:text property="remark" styleId="remark"
						styleClass="text medium"
						 value="${individualGroupForm.remark}" />
		</td>
	</tr>
	<!--  
	<tr>
		<td class="label">
			<fmt:message key="individualGroup.specialId" />
		</td>
		<td class="content">
			<html:text property="specialId" styleId="specialId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualGroupForm.specialId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="individualGroup.isDeleted" />
		</td>
		<td class="content">
			<html:text property="isDeleted" styleId="isDeleted"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualGroupForm.isDeleted}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="individualGroup.flag2" />
		</td>
		<td class="content">
			<html:text property="isleaf" styleId="isleaf"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualGroupForm.isleaf}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="individualGroup.createDate" />
		</td>
		<td class="content">
			<html:text property="createDate" styleId="createDate"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualGroupForm.createDate}" />
		</td>
	</tr>
-->
<html:hidden property="id" value="${individualGroupForm.id}" />
<html:hidden property="createDate" value="${individualGroupForm.createDate}" />
<html:hidden property="isDeleted" value="${individualGroupForm.isDeleted}" />
<html:hidden property="isleaf" value="${individualGroupForm.isleaf}" />
<html:hidden property="specialId" value="${individualGroupForm.specialId}" />
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty individualGroupForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/individual/individualGroups.do?method=remove&id=${individualGroupForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${individualGroupForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>