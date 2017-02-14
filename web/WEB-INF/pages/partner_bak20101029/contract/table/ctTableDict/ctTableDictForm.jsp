<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctTableDictForm'});
});
</script>

<html:form action="/ctTableDicts.do?method=save" styleId="ctTableDictForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">


<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctTableDict.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="ctTableDict.id" />
		</td>
		<td class="content">
			<html:text property="id" styleId="id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableDictForm.id}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctTableDict.dictName" />
		</td>
		<td class="content">
			<html:text property="dictName" styleId="dictName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableDictForm.dictName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctTableDict.parentId" />
		</td>
		<td class="content">
			<html:text property="parentId" styleId="parentId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableDictForm.parentId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctTableDict.isDeleted" />
		</td>
		<td class="content">
			<html:text property="isDeleted" styleId="isDeleted"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableDictForm.isDeleted}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty ctTableDictForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/partner/contract/ctTableDicts.do?method=remove&id=${ctTableDictForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${ctTableDictForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>