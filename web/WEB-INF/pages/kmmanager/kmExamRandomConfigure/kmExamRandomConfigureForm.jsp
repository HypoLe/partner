<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExamRandomConfigureForm'});
});
</script>

<html:form action="/kmExamRandomConfigures.do?method=save" styleId="kmExamRandomConfigureForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmExamRandomConfigure.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="kmExamRandomConfigure.randomTestId" />
		</td>
		<td class="content">
			<html:text property="randomTestId" styleId="randomTestId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExamRandomConfigureForm.randomTestId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExamRandomConfigure.testType" />
		</td>
		<td class="content">
			<html:text property="testType" styleId="testType"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExamRandomConfigureForm.testType}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExamRandomConfigure.number" />
		</td>
		<td class="content">
			<html:text property="number" styleId="number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExamRandomConfigureForm.number}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty kmExamRandomConfigureForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/kmExamRandomConfigure/kmExamRandomConfigures.do?method=remove&id=${kmExamRandomConfigureForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${kmExamRandomConfigureForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>