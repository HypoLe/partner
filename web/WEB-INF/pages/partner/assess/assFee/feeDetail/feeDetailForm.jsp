<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'feeDetailForm'});
});
</script>

<html:form action="/feeDetails.do?method=save" styleId="feeDetailForm" method="post"> 

<fmt:bundle basename="config/applicationResource-feedetail">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="feeDetail.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.number" />
		</td>
		<td class="content">
			<html:text property="number" styleId="number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.number}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.price" />
		</td>
		<td class="content">
			<html:text property="price" styleId="price"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.price}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.total" />
		</td>
		<td class="content">
			<html:text property="total" styleId="total"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.total}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.resultId" />
		</td>
		<td class="content">
			<html:text property="resultId" styleId="resultId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.resultId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.nodeId" />
		</td>
		<td class="content">
			<html:text property="nodeId" styleId="nodeId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.nodeId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.areaId" />
		</td>
		<td class="content">
			<html:text property="areaId" styleId="areaId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.areaId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.year" />
		</td>
		<td class="content">
			<html:text property="year" styleId="year"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.year}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.type" />
		</td>
		<td class="content">
			<html:text property="type" styleId="type"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.type}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="feeDetail.deleted" />
		</td>
		<td class="content">
			<html:text property="deleted" styleId="deleted"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${feeDetailForm.deleted}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty feeDetailForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/feeDetail/feeDetails.do?method=remove&id=${feeDetailForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${feeDetailForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>