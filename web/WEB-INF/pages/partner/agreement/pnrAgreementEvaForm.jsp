<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrAgreementEvaForm'});
});
</script>

<html:form action="/pnrAgreementEvas.do?method=save" styleId="pnrAgreementEvaForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrAgreementEva.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="pnrAgreementEva.agreeId" />
		</td>
		<td class="content">
			<html:text property="agreeId" styleId="agreeId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementEvaForm.agreeId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="pnrAgreementEva.evaContent" />
		</td>
		<td class="content">
			<html:text property="evaContent" styleId="evaContent"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementEvaForm.evaContent}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<!-- 
			<c:if test="${not empty pnrAgreementEvaForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/pnrAgreementEva/pnrAgreementEvas.do?method=remove&id=${pnrAgreementEvaForm.id}';
						location.href=url}"	/>
			</c:if>
			 -->
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pnrAgreementEvaForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>