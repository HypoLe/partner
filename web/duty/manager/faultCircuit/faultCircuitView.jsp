<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultCircuitForm'});
});
</script>

<html:form action="/faultCircuits.do?method=save" styleId="faultCircuitForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="faultCircuit.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.sequenceNo" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCircuitForm" property="sequenceNo" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.title" />
		</td>
		<td class="content">
			<bean:write name="faultCircuitForm" property="title" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.greffier" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${faultCircuitForm.greffier}" beanId="tawSystemUserDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.beginTime" />
		</td>
		<td class="content">
			<bean:write name="faultCircuitForm" property="beginTime" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.endTime" />
		</td>
		<td class="content">
			<bean:write name="faultCircuitForm" property="endTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.timeSlot" />
		</td>
		<td class="content">
			<bean:write name="faultCircuitForm" property="timeSlot" />&nbsp;
			<fmt:message key="faultCommont.tims.unit" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.resumeTimeSlot" />
		</td>
		<td class="content">
			<bean:write name="faultCircuitForm" property="resumeTimeSlot" />&nbsp;
			<fmt:message key="faultCommont.tims.unit" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.agentId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${faultCircuitForm.agentId}" beanId="tawSystemDictTypeDao" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.fromTo" />
		</td>
		<td class="content">
			<bean:write name="faultCircuitForm" property="fromTo" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.typeId" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCircuitForm" property="faultTypeName" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.filialeId" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultfilialeId" itemId="${faultCircuitForm.filialeId}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.propertyRight" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultPropertyRight" itemId="${faultCircuitForm.propertyRight}" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.isAppEffect" />
		</td>
		<td class="content">
		<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${faultCircuitForm.isAppEffect}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.serialNos" />
		</td>
		<td class="content">
			<bean:write name="faultCircuitForm" property="serialNos" />
		</td>		
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.resumeTime" />
		</td>
		<td class="content" >
			<bean:write name="faultCircuitForm" property="resumeTime" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.resumeMethod" />
		</td>
		<td class="content" >
			<bean:write name="faultCircuitForm" property="resumeMethod" />
		</td>
	</tr>
	<c:choose>
		<c:when	test="${faultCircuitForm.isAppEffect=='1'}">
			<tr id="ifEffectDiv" style="display:block">
			</c:when>
		<c:otherwise><tr id="ifEffectDiv" style="display:none"></c:otherwise>
	</c:choose>
		<td class="label" >
			<fmt:message key="faultCircuit.appEffect" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCircuitForm" property="appEffect" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.dealUser" />
		</td>
		<td class="content" >
			<bean:write name="faultCircuitForm" property="dealUser" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.dealUser" />
		</td>
		<td class="content" >
			<bean:write name="faultCircuitForm" property="dealUser" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.faultDot" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCircuitForm" property="faultDot" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.faultCause" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCircuitForm" property="faultCause" />
		</td>
	</tr>
	<!-- tr>
		<td class="label">
			<fmt:message key="faultCircuit.faultComment" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCircuitForm" property="faultComment" />
		</td>
	</tr-->
</table>
<c:choose>
	<c:when	test="${faultCircuitForm.roomId==sessionform.roomId}">
		<table>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<c:if test="${not empty faultCircuitForm.id}">
						<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
							onclick="javascript:{
								var url='${app}/duty/faultCircuits.do?method=edit&id=${faultCircuitForm.id}';
								location.href=url};"	/>
						<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
							onclick="javascript:if(confirm('<fmt:message key="faultCommont.is.delete"/>')){
								var url='${app}/duty/faultCircuits.do?method=remove&id=${faultCircuitForm.id}';
								location.href=url};"	/>
					</c:if>
				</td>
			</tr>
		</table>
	</c:when>
</c:choose>
<html:hidden property="id" value="${faultCircuitForm.id}" />
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>