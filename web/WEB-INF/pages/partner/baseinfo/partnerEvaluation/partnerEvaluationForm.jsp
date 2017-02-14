<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerEvaluationForm'});
});
</script>

<html:form action="/partnerEvaluations.do?method=save" styleId="partnerEvaluationForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="partnerEvaluation.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.partnerId" />
		</td>
		<td class="content">
			<html:text property="partnerId" styleId="partnerId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${partnerEvaluationForm.partnerId}" />
		</td>
		<td class="label">
			<fmt:message key="partnerEvaluation.contractId" />
		</td>
		<td class="content">
			<html:text property="contractId" styleId="contractId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${partnerEvaluationForm.contractId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.feePlanId" />
		</td>
		<td class="content">
			<html:text property="feePlanId" styleId="feePlanId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${partnerEvaluationForm.feePlanId}" />
		</td>
		<td class="label">
			<fmt:message key="partnerEvaluation.evaResult" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-baseinfo" dictId="evaResult" isQuery="false" alt="allowBlank:false"
			defaultId="${partnerEvaluationForm.evaResult}" selectId="evaResult" beanId="selectXML" />		
		</td>
	</tr>

	<tr>

		<td class="label">
			<fmt:message key="partnerEvaluation.evaUser" />
		</td>
		<td class="content">
			<html:hidden property="evaUser" value="${partnerEvaluationForm.evaUser}" />
			${partnerEvaluationForm.evaUser}	
		</td>
		<td class="label">
			<fmt:message key="partnerEvaluation.evaDept" />
		</td>
		<td class="content">
			<html:hidden property="evaDept" value="${partnerEvaluationForm.evaDept}" />
			${partnerEvaluationForm.evaDept}		
		</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.unContent" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="unContent" styleId="unContent"
						styleClass="text medium"  rows="3" style="width:80%"
						alt="allowBlank:false,vtext:'',maxLength:500" value="${partnerEvaluationForm.unContent}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.propose" />
		</td>
			<td class="content" colspan="3">
				<html:textarea property="propose" styleId="propose" 
				styleClass="text medium" style="width:80%" rows="3"
				alt="allowBlank:true,vtext:'',maxLength:500" value="${partnerEvaluationForm.propose}" />
			</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.evaTime" />
		</td>
		<td class="content" colspan="3">
			<html:hidden property="evaTime" value="${partnerEvaluationForm.evaTime}" />
			${partnerEvaluationForm.evaTime}
		</td>

	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty partnerEvaluationForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定删除吗?')){
						var url=eoms.appPath+'/partner/baseinfo/partnerEvaluations.do?method=remove&id=${partnerEvaluationForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="partnerId" value="${partnerEvaluationForm.partnerId}" />
<html:hidden property="contractId" value="${partnerEvaluationForm.contractId}" />
<html:hidden property="feePlanId" value="${partnerEvaluationForm.feePlanId}" />
<html:hidden property="id" value="${partnerEvaluationForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>