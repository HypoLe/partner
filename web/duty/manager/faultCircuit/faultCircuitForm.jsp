<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultCircuitForm'});
	fun_initMyBox();
});

function validateFaultForm() {
	var frm = document.forms["faultCircuitForm"];
	var date1 = frm.endTime.value;
	var date2 = frm.beginTime.value;
	
	if (document.getElementById("title").value == "") {
 		// Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date10" />');
 		alert('<fmt:message key="faultCommont.validate.date10" />');
		document.getElementById("title").focus();
 		return false;
 	}
 	
 	if (document.getElementById("fromTo").value == "") {
 		//Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date12" />');
 		alert('<fmt:message key="faultCommont.validate.date12" />');
		document.getElementById("fromTo").focus();
 		return false;
 	}
 	
 	if (document.getElementById("serialNos").value == "") {
 		//Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date13" />');
 		alert('<fmt:message key="faultCommont.validate.date13" />');
		document.getElementById("serialNos").focus();
 		return false;
 	}
 	
 	if (document.getElementById("typeIdList").value == "") {
 		//Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date8" />');
 		alert('<fmt:message key="faultCommont.validate.date8" />');
		document.getElementById("typeIdList").focus();
 		return false;
 	}
	
	// 
	if(dateCompare(date2,getDateStr(0))) { 
		//Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date2" />');
		alert('<fmt:message key="faultCommont.validate.date2" />');
		document.getElementById("beginTime").focus();
		return false;
	} 
	
	// 
	if(document.getElementById("endTime").value=="") { 
		// Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date6" />');
		// document.getElementById("endTime").focus();
		// return false;
	}
	
	// 
	if(date1!="" && dateCompare(date2,date1)) { 
		//Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date11" />');
		alert('<fmt:message key="faultCommont.validate.date11" />');
		document.getElementById("endTime").focus();
		return false;
	} 
	
	//
	if(document.getElementById("isAppEffectId1").checked&&document.getElementById("resumeTime").value=="") { 
		//Ext.MessageBox.alert('<fmt:message key="faultCircuit.detail.title" />','<fmt:message key="faultCommont.validate.date14" />');
		alert('<fmt:message key="faultCommont.validate.date14" />');
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

<html:form action="/faultCircuits.do?method=save" styleId="faultCircuitForm" method="post"> 
<input type="hidden" id="recordPerId" name="recordPerId" value="${faultCircuitForm.recordPerId}" />	
<input type="hidden" id="inputTime" name="inputTime" value="${faultCircuitForm.inputTime}" />
<input type="hidden" id="inputUser" name="inputUser" value="${faultCircuitForm.inputUserId}" />
<input type="hidden" id="roomId" name="roomId" value="${faultCircuitForm.roomId}" />
<input type="hidden" id="id" name="id" value="${faultCircuitForm.id}" />
<input type="hidden" id="sequenceNo" name="sequenceNo" value="${faultCircuitForm.sequenceNo}" />

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
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.title}" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.greffier" />
		</td>
		<td class="content">
			<select name="greffier" style="width: 4.0cm">
				<option value='${faultCircuitForm.greffier}'><eoms:id2nameDB id="${faultCircuitForm.greffier}" beanId="tawSystemUserDao" /></option>
				<logic:iterate id="user" name="USERLIST" type="com.boco.eoms.commons.system.user.model.TawSystemUser">
					<option value='<bean:write name="user" property="userid" scope="page"/>'>
						<bean:write name="user" property="username" scope="page"/>
					</option>
				</logic:iterate>
			</select>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.beginTime" />
		</td>
		<td class="content">
			<input type="text" name="beginTime" size="20" value="${faultCircuitForm.beginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.endTime" />
		</td>
		<td class="content">
			<input type="text" name="endTime" size="20" value="${faultCircuitForm.endTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.agentId" />
		</td>
		<td class="content">
			<eoms:comboBox name="agentId" id="agentId" defaultValue="${faultCircuitForm.agentId}" initDicId="1040404" alt="allowBlank:true,vtext:''"/> 
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.fromTo" />
		</td>
		<td class="content">
			<html:text property="fromTo" styleId="fromTo"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.fromTo}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.typeIdList" />
		</td>
		<td class="content" colspan="3">
			<input type="button" name="query" value=<fmt:message key="faultCircuit.selected" /> onclick="fun_showMyBox(this);" class="clsbtn2">
			<input type="text" name="faultTypeName" value="${faultCircuitForm.faultTypeName}" size="70" disabled="true" Class="clstext"/>
			<input type="hidden" name="typeIdList" name="typeIdList" value="${faultCircuitForm.typeIdList}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.filialeId" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultfilialeId" defaultId="${faultCircuitForm.filialeId}" beanId="selectXML" selectId="filialeId"/>
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.propertyRight" />
		</td>
		<td class="content">
			<c:choose>
			<c:when	test="${faultCircuitForm.propertyRight=='1'}">
				<input type="radio" name="propertyRight" value="1" checked="checked"><fmt:message key="faultCircuit.yes" />
				<input type="radio" name="propertyRight" value="2"><fmt:message key="faultCircuit.no" />>
			</c:when>
			<c:otherwise>
				<input type="radio" name="propertyRight" value="1" ><fmt:message key="faultCircuit.yes" />
				<input type="radio" name="propertyRight" value="2" checked="checked"><fmt:message key="faultCircuit.no" />
			</c:otherwise>
			</c:choose>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.isAppEffect" />
		</td>
		<td class="content" >
		<c:choose>
			<c:when	test="${faultCircuitForm.isAppEffect=='1'}">
				<input type="radio" id="isAppEffectId1" name="isAppEffect" value="1" checked="checked" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.yes" />
				<input type="radio" id="isAppEffectId2" name="isAppEffect" value="0" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.no" />
			</c:when>
			<c:otherwise>
				<input type="radio" id="isAppEffectId1" name="isAppEffect" value="1" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.yes" />
				<input type="radio" id="isAppEffectId2" name="isAppEffect" value="0" checked="checked" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.no" />
			</c:otherwise>
		</c:choose>
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.serialNos" />
		</td>
		<td class="content">
			<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.serialNos}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.resumeTime" />
		</td>
		<td class="content" >
			<input type="text" name="resumeTime" size="20" value="${faultCircuitForm.resumeTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.resumeMethod" />
		</td>
		<td class="content" >
			<html:text property="resumeMethod" styleId="resumeMethod"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.resumeMethod}" />
		</td>
	</tr>
	<c:choose>
				<c:when test="${faultCircuitForm.isAppEffect=='1'}">
					<tr id="ifEffectDiv" style="display: block">
				</c:when>
				<c:otherwise>
					<tr id="ifEffectDiv" style="display: none">
				</c:otherwise>
			</c:choose>
				<td class="label">
					<fmt:message key="faultCircuit.appEffect" />
				</td>
				<td class="content" colspan="3">
					<html:text property="appEffect" styleId="appEffect"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.appEffect}" />
				</td>
			</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.dealUser" />
		</td>
		<td class="content" >
			<html:text property="dealUser" styleId="dealUser"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.dealUser}" />
		</td>
		<td class="label">
			<fmt:message key="faultCircuit.linkPhone" />
		</td>
		<td class="content" >
			<html:text property="linkPhone" styleId="linkPhone"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.linkPhone}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.faultDot" />
		</td>
		<td class="content" colspan="3">
			<html:text property="faultDot" styleId="faultDot"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.faultDot}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.faultCause" />
		</td>
		<td class="content" colspan="3">
			<html:text property="faultCause" styleId="faultCause"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.faultCause}" />
		</td>
	</tr>
	<!-- tr>
		<td class="label">
			<fmt:message key="faultCircuit.faultComment" />
		</td>
		<td class="content" colspan="3">
			<html:text property="faultComment" styleId="faultComment"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.faultComment}" />
		</td>
	</tr-->
</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateFaultForm();" />
		</td>
	</tr>
</table>

<p id="innerHtml" style="display:none">
	<table border="0" width="98%" cellspacing="1" class="table_show" cellPadding="0">
		<tr class="tr_show">
			<td class="clsfth">
				<logic:iterate id="tawSystemDictType" indexId="index" name="FAULTTYPELIST" type="com.boco.eoms.commons.system.dict.model.TawSystemDictType">
					<input type='checkbox' name='my_check' value='<bean:write name="tawSystemDictType" property="dictId" scope="page"/>,<bean:write name="tawSystemDictType" property="dictName" scope="page"/>'>
					<bean:write name="tawSystemDictType" property="dictName" scope="page"/></input>
					<c:choose><c:when test="${(index+1)%3==0}"></p></c:when></c:choose>
				</logic:iterate>
			</td>
		</tr>
		<tr align="center" valign="middle">
			<td width="100%" height="40" bgcolor="#EEECED" align="center" colspan="8">
				<input type="button" property="ok" Class="btn" value=<fmt:message key="button.confim"/> onclick="fun_setValue('faultTypeName', 'typeIdList');" >
			</td>
		</tr>
	</table>
</p>
<html:hidden property="id" value="${faultCircuitForm.id}" />
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>