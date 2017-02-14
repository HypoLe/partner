<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'closCostForm'});
});
</script>

<html:form action="/closCosts.do?method=save" styleId="closCostForm" method="post"> 
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
			<html:text property="agreementNo" styleId="agreementNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${closCostForm.agreementNo}" />
		</td>
		<td class="label">
			<fmt:message key="closCost.workPlanNo" />
		</td>
		<td class="content">
			<html:text property="workPlanNo" styleId="workPlanNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${closCostForm.workPlanNo}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.assessmentNo" />
		</td>
		<td class="content">
			<html:text property="assessmentNo" styleId="assessmentNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${closCostForm.assessmentNo}" />
		</td>
		<td class="label">
			<fmt:message key="closCost.money" />
		</td>
		<td class="content">
			<html:text property="money" styleId="money"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${closCostForm.money}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.payPlan" />
		</td>
		<td class="content" colspan="3">
			<html:text property="payPlan" styleId="payPlan"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${closCostForm.payPlan}" />
		</td>

	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.payMessage" />
		</td>
		<td class="content">
			<html:text property="payMessage" styleId="payMessage"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${closCostForm.payMessage}" />
		</td>	
		<td class="label">
			<fmt:message key="closCost.payfin" />
		</td>
		<td class="content">
			<html:text property="payfin" styleId="payfin"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${closCostForm.payfin}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="closCost.remark" />
		</td>
		<td class="content"  colspan="3">
				<html:textarea property="remark" styleId="remark" 
				styleClass="text medium" style="width:95%" rows="4"
				alt="allowBlank:true,vtext:'',maxLength:255" value="${closCostForm.remark}" />		
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty closCostForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除?')){
						var url=eoms.appPath+'/partner/closCost/closCosts.do?method=remove&id=${closCostForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${closCostForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>