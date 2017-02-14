<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>


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
			${partnerEvaluationForm.partnerId}
		</td>
		<td class="label">
			<fmt:message key="partnerEvaluation.contractId" />
		</td>
		<td class="content">
			${partnerEvaluationForm.contractId}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.feePlanId" />
		</td>
		<td class="content">
			${partnerEvaluationForm.feePlanId}
		</td>
		<td class="label">
			<fmt:message key="partnerEvaluation.evaResult" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-baseinfo" dictId="evaResult" itemId="${partnerEvaluationForm.evaResult}" beanId="id2nameXML" />		
		</td>
	</tr>

	<tr>

		<td class="label">
			<fmt:message key="partnerEvaluation.evaUser" />
		</td>
		<td class="content">
			${partnerEvaluationForm.evaUser}	
		</td>
		<td class="label">
			<fmt:message key="partnerEvaluation.evaDept" />
		</td>
		<td class="content">
			${partnerEvaluationForm.evaDept}		
		</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.unContent" />
		</td>
		<td class="content" colspan="3">
			${partnerEvaluationForm.unContent}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.propose" />
		</td>
			<td class="content" colspan="3">
				${partnerEvaluationForm.propose}
			</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerEvaluation.evaTime" />
		</td>
		<td class="content" colspan="3">
			${partnerEvaluationForm.evaTime}
		</td>

	</tr>


</table>

</fmt:bundle>

<html:hidden property="partnerId" value="${partnerEvaluationForm.partnerId}" />
<html:hidden property="contractId" value="${partnerEvaluationForm.contractId}" />
<html:hidden property="feePlanId" value="${partnerEvaluationForm.feePlanId}" />
<html:hidden property="id" value="${partnerEvaluationForm.id}" />

<%@ include file="/common/footer_eoms.jsp"%>