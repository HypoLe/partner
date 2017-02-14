<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultEquipmentForm'});
});
</script>

<html:form action="/faultEquipments.do?method=save" styleId="faultEquipmentForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="faultEquipment.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.title" />
		</td>
		<td class="content">
			<html:text property="title" styleId="title"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.title}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.sequenceNo" />
		</td>
		<td class="content">
			<html:text property="sequenceNo" styleId="sequenceNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.sequenceNo}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.beginTime" />
		</td>
		<td class="content">
			<html:text property="beginTime" styleId="beginTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.beginTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.endTime" />
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.endTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.recordTime" />
		</td>
		<td class="content">
			<html:text property="recordTime" styleId="recordTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.recordTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.resumeTime" />
		</td>
		<td class="content">
			<html:text property="resumeTime" styleId="resumeTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.resumeTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.filialeId" />
		</td>
		<td class="content">
			<html:text property="filialeId" styleId="filialeId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.filialeId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.station" />
		</td>
		<td class="content">
			<html:text property="station" styleId="station"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.station}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.isAppEffect" />
		</td>
		<td class="content">
			<html:text property="isAppEffect" styleId="isAppEffect"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.isAppEffect}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.equipmentCorpId" />
		</td>
		<td class="content">
			<html:text property="equipmentCorpId" styleId="equipmentCorpId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.equipmentCorpId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.serialNos" />
		</td>
		<td class="content">
			<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.serialNos}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.greffier" />
		</td>
		<td class="content">
			<html:text property="greffier" styleId="greffier"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.greffier}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.inputTime" />
		</td>
		<td class="content">
			<html:text property="inputTime" styleId="inputTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.inputTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.faultComment" />
		</td>
		<td class="content">
			<html:text property="faultComment" styleId="faultComment"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.faultComment}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.roomId" />
		</td>
		<td class="content">
			<html:text property="roomId" styleId="roomId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.roomId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.recordPerId" />
		</td>
		<td class="content">
			<html:text property="recordPerId" styleId="recordPerId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.recordPerId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.inputUserId" />
		</td>
		<td class="content">
			<html:text property="inputUserId" styleId="inputUserId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.inputUserId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.timeSlot" />
		</td>
		<td class="content">
			<html:text property="timeSlot" styleId="timeSlot"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.timeSlot}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.resumeTimeSlot" />
		</td>
		<td class="content">
			<html:text property="resumeTimeSlot" styleId="resumeTimeSlot"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.resumeTimeSlot}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.fromBeginTime" />
		</td>
		<td class="content">
			<html:text property="fromBeginTime" styleId="fromBeginTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.fromBeginTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.toBeginTime" />
		</td>
		<td class="content">
			<html:text property="toBeginTime" styleId="toBeginTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultEquipmentForm.toBeginTime}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty faultEquipmentForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/faultEquipment/faultEquipments.do?method=remove&id=${faultEquipmentForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${faultEquipmentForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>