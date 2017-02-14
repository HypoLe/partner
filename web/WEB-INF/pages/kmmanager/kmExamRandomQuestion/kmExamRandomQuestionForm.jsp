<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExamRandomQuestionForm'});
});
</script>

<html:form action="/kmExamRandomQuestions.do?method=save" styleId="kmExamRandomQuestionForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmExamRandomQuestion.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="kmExamRandomQuestion.randomSpecailityId" />
		</td>
		<td class="content">
			<html:text property="randomSpecailityId" styleId="randomSpecailityId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExamRandomQuestionForm.randomSpecailityId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExamRandomQuestion.questionId" />
		</td>
		<td class="content">
			<html:text property="questionId" styleId="questionId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExamRandomQuestionForm.questionId}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty kmExamRandomQuestionForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/kmExamRandomQuestion/kmExamRandomQuestions.do?method=remove&id=${kmExamRandomQuestionForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${kmExamRandomQuestionForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>