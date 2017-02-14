<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrAgreementWorkForm'});
});
</script>

<html:form action="/pnrAgreementWorks.do?method=save" styleId="pnrAgreementWorkForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrAgreementWork.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="pnrAgreementWork.agreeId" />
		</td>
		<td class="content">
			<html:text property="agreeId" styleId="agreeId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementWorkForm.agreeId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrAgreementWork.startTime" />
		</td>
		<td class="content">
			<html:text property="startTime" styleId="startTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementWorkForm.startTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrAgreementWork.endTime" />
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementWorkForm.endTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrAgreementWork.workContent" />
		</td>
		<td class="content">
			<html:text property="workContent" styleId="workContent"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementWorkForm.workContent}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty pnrAgreementWorkForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/pnrAgreementWork/pnrAgreementWorks.do?method=remove&id=${pnrAgreementWorkForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pnrAgreementWorkForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>