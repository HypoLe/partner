<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultEquipmentForm'});
});
</script>

<html:form action="/faultEquipments.do?method=edit" styleId="faultEquipmentForm" method="post"> 
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="faultEquipment.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.sequenceNo" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultEquipmentForm" property="sequenceNo" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.title" />
		</td>
		<td class="content">
			<bean:write name="faultEquipmentForm" property="title" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.greffier" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${faultEquipmentForm.greffier}" beanId="tawSystemUserDao" />
		</td>
	</tr>
		<tr>
		<td class="label">
			<fmt:message key="faultEquipment.beginTime" />
		</td>
		<td class="content">
			<bean:write name="faultEquipmentForm" property="beginTime" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.endTime" />
		</td>
		<td class="content">
			<bean:write name="faultEquipmentForm" property="endTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.timeSlot" />
		</td>
		<td class="content">
			<bean:write name="faultEquipmentForm" property="timeSlot" />&nbsp;
			<fmt:message key="faultCommont.tims.unit" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.isAppEffect" />
		</td>
		<td class="content">
		<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${faultEquipmentForm.isAppEffect}" beanId="id2nameXML" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.filialeId" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultfilialeId" itemId="${faultEquipmentForm.filialeId}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.station" />
		</td>
		<td class="content">
			<bean:write name="faultEquipmentForm" property="station" />&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.equipmentCorpId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${faultEquipmentForm.equipmentCorpId}" beanId="tawSystemDictTypeDao" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.serialNos" />
		</td>
		<td class="content">
			<a href="#" onclick='return faultsheet("<bean:write name="faultEquipmentForm" property="serialNos" />")'>
				<bean:write name="faultEquipmentForm" property="serialNos" />
			</a>
		</td>
	</tr>
	<c:choose>
		<c:when	test="${faultEquipmentForm.isAppEffect=='1'}"><tr id="ifEffectDiv" style="display:block"></c:when>
		<c:otherwise><tr id="ifEffectDiv" style="display:none"></c:otherwise>
	</c:choose>
		<td class="label">
			<fmt:message key="faultEquipment.resumeTime" />
		</td>
		<td class="content">
			<bean:write name="faultEquipmentForm" property="resumeTime" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.appEffect" />
		</td>
		<td class="content">
			<bean:write name="faultEquipmentForm" property="appEffect" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.resumeTimeSlot" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultEquipmentForm" property="resumeTimeSlot" />&nbsp;
			<fmt:message key="faultCommont.tims.unit" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.faultComment" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultEquipmentForm" property="faultComment" />
		</td>
	</tr>
</table>

<c:choose>
	<c:when	test="${faultEquipmentForm.roomId==sessionform.roomId}">
		<table>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<c:if test="${not empty faultEquipmentForm.id}">
						<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
							onclick="javascript:{
								var url='${app}/duty/faultEquipments.do?method=edit&id=${faultEquipmentForm.id}';
								location.href=url};"	/>
						<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
							onclick="javascript:if(confirm('<fmt:message key="faultCommont.is.delete"/>')){
								var url='${app}/duty/faultEquipments.do?method=remove&id=${faultEquipmentForm.id}';
								location.href=url};"	/>
					</c:if>
				</td>
			</tr>
		</table>
	</c:when>
</c:choose>
<html:hidden property="id" value="${faultEquipmentForm.id}" />
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>