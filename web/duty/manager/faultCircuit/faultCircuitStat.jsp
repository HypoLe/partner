<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultCircuitForm'});
});
</script>

<html:form action="/faultCircuits.do?method=save" styleId="faultCircuitForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="faultCircuit.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.title" />
		</td>
		<td class="content">
			<html:text property="title" styleId="title"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.title}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.sequenceNo" />
		</td>
		<td class="content">
			<html:text property="sequenceNo" styleId="sequenceNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.sequenceNo}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.beginTime" />
		</td>
		<td class="content">
			<html:text property="beginTime" styleId="beginTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.beginTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.endTime" />
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.endTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.resumeTime" />
		</td>
		<td class="content">
			<html:text property="resumeTime" styleId="resumeTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.resumeTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.agentId" />
		</td>
		<td class="content">
			<html:text property="agentId" styleId="agentId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.agentId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.typeId" />
		</td>
		<td class="content">
			<html:text property="typeId" styleId="typeId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.typeId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.fromTo" />
		</td>
		<td class="content">
			<html:text property="fromTo" styleId="fromTo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.fromTo}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.filialeId" />
		</td>
		<td class="content">
			<html:text property="filialeId" styleId="filialeId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.filialeId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.propertyRight" />
		</td>
		<td class="content">
			<html:text property="propertyRight" styleId="propertyRight"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.propertyRight}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.isAppEffect" />
		</td>
		<td class="content">
			<html:text property="isAppEffect" styleId="isAppEffect"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.isAppEffect}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.serialNos" />
		</td>
		<td class="content">
			<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.serialNos}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.greffier" />
		</td>
		<td class="content">
			<html:text property="greffier" styleId="greffier"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.greffier}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.inputUserId" />
		</td>
		<td class="content">
			<html:text property="inputUserId" styleId="inputUserId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.inputUserId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.inputTime" />
		</td>
		<td class="content">
			<html:text property="inputTime" styleId="inputTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.inputTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.faultComment" />
		</td>
		<td class="content">
			<html:text property="faultComment" styleId="faultComment"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.faultComment}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.roomId" />
		</td>
		<td class="content">
			<html:text property="roomId" styleId="roomId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.roomId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.recordPerId" />
		</td>
		<td class="content">
			<html:text property="recordPerId" styleId="recordPerId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.recordPerId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.typeIdList" />
		</td>
		<td class="content">
			<html:text property="typeIdList" styleId="typeIdList"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.typeIdList}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.timeSlot" />
		</td>
		<td class="content">
			<html:text property="timeSlot" styleId="timeSlot"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.timeSlot}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.resumeTimeSlot" />
		</td>
		<td class="content">
			<html:text property="resumeTimeSlot" styleId="resumeTimeSlot"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.resumeTimeSlot}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.fromBeginTime" />
		</td>
		<td class="content">
			<html:text property="fromBeginTime" styleId="fromBeginTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.fromBeginTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.toBeginTime" />
		</td>
		<td class="content">
			<html:text property="toBeginTime" styleId="toBeginTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCircuitForm.toBeginTime}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty faultCircuitForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/faultCircuit/faultCircuits.do?method=remove&id=${faultCircuitForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${faultCircuitForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>