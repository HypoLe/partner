<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<fmt:bundle basename="com/boco/eoms/partner/costmanage/config/applicationResource-partner-costmanage">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="closCost.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="closCost.agreementNo" />
		</td>
		<td class="content">
			${closCostForm.agreementNo}
		</td>
		<td class="label">
			<fmt:message key="closCost.workPlanNo" />
		</td>
		<td class="content">
			${closCostForm.workPlanNo}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.assessmentNo" />
		</td>
		<td class="content">
			${closCostForm.assessmentNo}
		</td>
		<td class="label">
			<fmt:message key="closCost.money" />
		</td>
		<td class="content">
			${closCostForm.money}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.payPlan" />
		</td>
		<td class="content" colspan="3">
			${closCostForm.payPlan}
		</td>

	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.payMessage" />
		</td>
		<td class="content">
			${closCostForm.payMessage}
		</td>	
		<td class="label">
			<fmt:message key="closCost.payfin" />
		</td>
		<td class="content">
			${closCostForm.payfin}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.remark" />
		</td>
		<td class="content"  colspan="3">
				${closCostForm.remark}		
		</td>
	</tr>


</table>

</fmt:bundle>

<html:hidden property="id" value="${closCostForm.id}" />

<%@ include file="/common/footer_eoms.jsp"%>