<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/css/table_style.css"
		type="text/css">

	<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultEquipmentForm'});
});

function validateFaultForm() {
	var frm = document.forms["faultEquipmentForm"];
	var date1 = frm.endTime.value;
	var date2 = frm.beginTime.value;
	
	//  
	if (document.getElementById("title").value == "") {
 		//Ext.MessageBox.alert('<fmt:message key="faultEquipment.detail.title" />','<fmt:message key="faultCommont.validate.date10" />');
		alert('<fmt:message key="faultCommont.validate.date10" />');
		document.getElementById("title").focus();
 		return false;
 	}
	
	// 
	if(dateCompare(date2,getDateStr(0))) { 
		//Ext.MessageBox.alert('<fmt:message key="faultEquipment.detail.title" />','<fmt:message key="faultCommont.validate.date2" />');
		alert('<fmt:message key="faultCommont.validate.date2" />');
		document.getElementById("beginTime").focus();
		return false;
	} 
	
	// 
	if(document.getElementById("endTime").value=="") { 
		// Ext.MessageBox.alert('<fmt:message key="faultEquipment.detail.title" />','<fmt:message key="faultCommont.validate.date6" />');
		// document.getElementById("endTime").focus();
		// return false;
	}
	
	// 
	if(date1!="" && dateCompare(date2,date1)) { 
		//Ext.MessageBox.alert('<fmt:message key="faultEquipment.detail.title" />','<fmt:message key="faultCommont.validate.date11" />');
		alert('<fmt:message key="faultCommont.validate.date11" />');
		document.getElementById("endTime").focus();
		return false;
	} 
	
	//
	if(document.getElementById("isAppEffectId1").checked&&document.getElementById("resumeTime").value=="") { 
		//Ext.MessageBox.alert('<fmt:message key="faultEquipment.detail.title" />','<fmt:message key="faultCommont.validate.date7" />');
		alert('<fmt:message key="faultCommont.validate.date7" />');
		document.getElementById("resumeTime").focus();
		return false;
	} 
	
 	frm.submit();
 }

function ifEffects(ifEffectValue) {
	var obj = document.getElementById("ifEffectDiv");
	if(ifEffectValue == 1) { 
		obj.style.display = "block";
	 } else {
		obj.style.display = "none";
	}
}
</script>

	<html:form action="/faultEquipments.do?method=save"
		styleId="faultEquipmentForm" method="post">
		<input type="hidden" id="recordPerId" name="recordPerId"
			value="${faultEquipmentForm.recordPerId}" />
		<input type="hidden" id="inputTime" name="inputTime"
			value="${faultEquipmentForm.inputTime}" />
		<input type="hidden" id="inputUser" name="inputUser"
			value="${faultEquipmentForm.inputUserId}" />
		<input type="hidden" id="roomId" name="roomId"
			value="${faultEquipmentForm.roomId}" />
		<input type="hidden" id="id" name="id"
			value="${faultEquipmentForm.id}" />
		<input type="hidden" id="sequenceNo" name="sequenceNo"
			value="${faultEquipmentForm.sequenceNo}" />

		<table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="faultEquipment.form.heading" />
				</div>
			</caption>

			<tr>
				<td class="label">
					<fmt:message key="faultEquipment.title" />
				</td>
				<td class="content">
					<html:text property="title" styleId="title"
						styleClass="text medium" alt="allowBlank:true,vtext:''"
						value="${faultEquipmentForm.title}" />
				</td>
				<td class="label">
					<fmt:message key="faultEquipment.greffier" />
				</td>
				<td class="content">
					<select name="greffier" style="width: 4.0cm">
						<option value='${faultEquipmentForm.greffier}'>
							<eoms:id2nameDB id="${faultEquipmentForm.greffier}"
								beanId="tawSystemUserDao" />
						</option>
						<logic:iterate id="user" name="USERLIST"
							type="com.boco.eoms.commons.system.user.model.TawSystemUser">
							<option
								value='<bean:write name="user" property="userid" scope="page"/>'>
								<bean:write name="user" property="username" scope="page" />
							</option>
						</logic:iterate>
					</select>
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="faultEquipment.beginTime" />
				</td>
				<td class="content">
					<input type="text" name="beginTime" size="20"
						value="${faultEquipmentForm.beginTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						readonly="true" class="text">
				</td>
				<td class="label">
					<fmt:message key="faultEquipment.endTime" />
				</td>
				<td class="content">
					<input type="text" name="endTime" size="20"
						value="${faultEquipmentForm.endTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						readonly="true" class="text">
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="faultEquipment.filialeId" />
				</td>
				<td class="content">
					<eoms:dict key="dict-duty" dictId="faultfilialeId"
						defaultId="${faultEquipmentForm.filialeId}" beanId="selectXML"
						selectId="filialeId" />
				</td>
				<td class="label">
					<fmt:message key="faultEquipment.station" />
				</td>
				<td class="content">
					<html:text property="station" styleId="station"
						styleClass="text medium" alt="allowBlank:false,vtext:''"
						value="${faultEquipmentForm.station}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="faultEquipment.equipmentCorpId" />
				</td>
				<td class="content">
					<eoms:comboBox name="equipmentCorpId" id="equipmentCorpId"
						defaultValue="${faultEquipmentForm.equipmentCorpId}"
						initDicId="1040401" alt="allowBlank:false,vtext:''" />
				</td>
				<td class="label">
					<fmt:message key="faultEquipment.serialNos" />
				</td>
				<td class="content">
					<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium" alt="allowBlank:true,vtext:''"
						value="${faultEquipmentForm.serialNos}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="faultEquipment.isAppEffect" />
				</td>
				<td class="content" colspan="3">
					<c:choose>
						<c:when test="${faultEquipmentForm.isAppEffect=='1'}">
							<input type="radio" id="isAppEffectId1" name="isAppEffect"
								value="1" checked="checked" onClick="ifEffects(this.value)">
							<fmt:message key="faultCommont.yes" />
							<input type="radio" id="isAppEffectId2" name="isAppEffect"
								value="0" onClick="ifEffects(this.value)">
							<fmt:message key="faultCommont.no" />
						</c:when>
						<c:otherwise>
							<input type="radio" id="isAppEffectId1" name="isAppEffect"
								value="1" onClick="ifEffects(this.value)">
							<fmt:message key="faultCommont.yes" />
							<input type="radio" id="isAppEffectId2" name="isAppEffect"
								value="0" checked="checked" onClick="ifEffects(this.value)">
							<fmt:message key="faultCommont.no" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<c:choose>
				<c:when test="${faultEquipmentForm.isAppEffect=='1'}">
					<tr id="ifEffectDiv" style="display: block">
				</c:when>
				<c:otherwise>
					<tr id="ifEffectDiv" style="display: none">
				</c:otherwise>
			</c:choose>
				<td class="label">
					<fmt:message key="faultEquipment.resumeTime" />
				</td>
				<td class="content" >
					<input type="text" name="resumeTime" size="20"
						value="${faultEquipmentForm.resumeTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						readonly="true" class="text">
				</td>
				<td class="label">
					<fmt:message key="faultEquipment.appEffect" />
				</td>
				<td class="content">
					<html:text property="appEffect" styleId="appEffect"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultEquipmentForm.appEffect}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="faultEquipment.faultComment" />
				</td>
				<td class="content" colspan="3">
					<html:text property="faultComment" styleId="faultComment"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:false,vtext:''"
						value="${faultEquipmentForm.faultComment}" />
				</td>
			</tr>
		</table>

		<table>
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" class="btn"
						value="<fmt:message key="button.save"/>"
						onclick="validateFaultForm();" />
				</td>
			</tr>
		</table>
		<html:hidden property="id" value="${faultEquipmentForm.id}" />
	</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>