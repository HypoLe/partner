<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultCommontForm'});
	// var idValue = Ext.get("id").dom.value;
});


function ifEffects(ifEffectValue) {
	var obj = document.getElementById("ifEffectDiv");
	if(ifEffectValue == 1) { 
		obj.style.display = "block";
	 } else {
		obj.style.display = "none";
	}
}

function validateFaultForm() {
	var frm = document.forms["faultCommontForm"];
	var date1 = frm.endTime.value;
	var date2 = frm.beginTime.value;
	
	// 标题
	if (document.getElementById("title").value == "") {
 		// Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date10" />');
 		alert('<fmt:message key="faultCommont.validate.date10" />');
		document.getElementById("title").focus();
 		return false;
 	}
 	
 	// 故障类别
	if (document.getElementById("typeId").value == "") {
 		//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date8" />');
 		alert('<fmt:message key="faultCommont.validate.date8" />');
		document.getElementById("typeId").focus();
 		return false;
 	}
 	
 	// 上报人/部门
 	if (document.getElementById("declarer").value == "") {
 		//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date9" />');
 		alert('<fmt:message key="faultCommont.validate.date9" />');
		document.getElementById("declarer").focus();
 		return false;
 	}
	
	// -----------------故障发生时间不能是将来时------------BEGIN
	if(dateCompare(date2,getDateStr(0))) {
		//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date2" />');
		alert('<fmt:message key="faultCommont.validate.date2" />');
		document.getElementById("beginTime").focus();
		return false;
	} 
	
	if(document.getElementById("serialNos").value!=""&&document.getElementById("serialNos").value==document.getElementById("serialno").value) {
		//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date3" />');
		alert('<fmt:message key="faultCommont.validate.date3" />');
		document.getElementById("serialno").focus();
		return false;
	}

	if((document.getElementById("estate1").checked)) { // 如果选择为是,则为归档状态
		if(document.getElementById("serialno").value=="") { // 如果选择为是,则故障工单号不能为空
			//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date4" />');
			alert('<fmt:message key="faultCommont.validate.date4" />');
			document.getElementById("serialno").focus();
			return false;
		}

		if(document.getElementById("serialNos").value=="") { // 如果选择为是,则重要故障上报工单号不能为空
			//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date5" />');
			alert('<fmt:message key="faultCommont.validate.date5" />');
			document.getElementById("serialNos").focus();
			return false;
		}
	
		if(document.getElementById("endTime").value=="") { // 如果选择为是,则故障结束时间不能为空
			//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date6" />');
			alert('<fmt:message key="faultCommont.validate.date6" />');
			document.getElementById("endTime").focus();
			return false;
		}
	}
		
	if(document.getElementById("isAppEffectId1").checked&&document.getElementById("resumeTime").value=="") { // 选择是否影响业务
		//Ext.MessageBox.alert('<fmt:message key="faultCommont.detail.title" />','<fmt:message key="faultCommont.validate.date7" />');
		alert('<fmt:message key="faultCommont.validate.date7" />');
		document.getElementById("resumeTime").focus();
		return false;
	} 

 	frm.submit();
 }
 
 // 生成任务工单
function addTaskSheet() {
	var title = document.getElementById("title").value;
	var mainFaultStarttime = document.getElementById("beginTime").value;
	var mainReportDesc = document.getElementById("faultPhenomenon").value;
	var mainDealInfo = document.getElementById("dealDetail").value;
	var mainReportScop = document.getElementById("appEffect").value; // 影响业务情况

	window.open('<%=request.getContextPath()%>/sheet/commontask/commontask.do?method=showNewSheetPage&title='+title + "&mainFaultStarttime="+mainFaultStarttime + "&mainReportDesc="+mainReportDesc+ "&mainDealInfo="+mainDealInfo+ "&mainReportScop=" + mainReportScop);
 	return true;
 }	
</script>

<html:form action="/faultCommonts.do?method=save" styleId="faultCommontForm" method="post"> 
<input type="hidden" id="recordPerId" name="recordPerId" value="${faultCommontForm.recordPerId}" />	
<input type="hidden" id="inputTime" name="inputTime" value="${faultCommontForm.inputTime}" />
<input type="hidden" id="inputUser" name="inputUser" value="${faultCommontForm.inputUser}" />
<input type="hidden" id="roomId" name="roomId" value="${faultCommontForm.roomId}" />
<input type="hidden" id="sequenceNo" name="sequenceNo" value="${faultCommontForm.sequenceNo}" />
<input type="hidden" id="id" name="id" value="${faultCommontForm.id}" />

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="faultCommont.form.heading"/></div>
	</caption>
	<tr>
		<td class="label" width="15%">
			<fmt:message key="faultCommont.title" />
		</td>
		<td class="content" width="35%">
			<html:text property="title" styleId="title"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCommontForm.title}" />
		</td>
		<td class="label" width="15%">
			<fmt:message key="faultCommont.greffier" />
		</td>
		<td class="content" width="35%">
			<select name="greffier" style="width: 4.0cm">
				<option value='${faultCommontForm.greffier}'><eoms:id2nameDB id="${faultCommontForm.greffier}" beanId="tawSystemUserDao" /></option>
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
			<fmt:message key="faultCommont.beginTime" />
		</td>
		<td class="content">
			<input type="text" name="beginTime" size="20" value="${faultCommontForm.beginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		<td class="label">
			<fmt:message key="faultCommont.typeId" />
		</td>
		<td class="content">
			<eoms:comboBox name="typeId" id="typeId" defaultValue="${faultCommontForm.typeId}" initDicId="1040402" alt="allowBlank:false,vtext:''"/> 
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.declarer" />
		</td>
		<td class="content">
			<html:text property="declarer" styleId="declarer"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCommontForm.declarer}" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.isFirstFind" />
		</td>
		<td class="content">
			<c:choose>
			<c:when	test="${faultCommontForm.isFirstFind=='1'}">
				<input type="radio" name="isFirstFind" value="1" checked="checked"><fmt:message key="faultCommont.yes" />
				<input type="radio" name="isFirstFind" value="0"><fmt:message key="faultCommont.no" />
			</c:when>
			<c:otherwise>
				<input type="radio" name="isFirstFind" value="1" ><fmt:message key="faultCommont.yes" />
				<input type="radio" name="isFirstFind" value="0" checked="checked"><fmt:message key="faultCommont.no" />
			</c:otherwise>
			</c:choose>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCommont.isAppEffect" />
		</td>
		<td class="content">
		<c:choose>
			<c:when	test="${faultCommontForm.isAppEffect=='1'}">
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
			<fmt:message key="faultCommont.upSerialNo" />
		</td>
		<td class="content">
			<html:text property="upSerialNo" styleId="upSerialNo"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.upSerialNo}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultAddress" />
		</td>
		<td class="content">
					<eoms:dict key="dict-duty" dictId="faultfilialeId"
						defaultId="${faultCommontForm.faultAddressId}" beanId="selectXML"
						selectId="faultAddressId" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.endTime" />
		</td>
		<td class="content">
			<input type="text" name="endTime" size="20" value="${faultCommontForm.endTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.reportTime" />
		</td>
		<td class="content">
			<input type="text" name="reportTime" size="20" value="${faultCommontForm.reportTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		<td class="label">
			<fmt:message key="faultCommont.linkPhone" />
		</td>
		<td class="content">
			<html:text property="linkPhone" styleId="linkPhone"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.linkPhone}" />
		</td>
	</tr>
	<c:choose>
		<c:when	test="${faultCommontForm.isAppEffect=='1'}"><tr id="ifEffectDiv" style="display:block"></c:when>
		<c:otherwise><tr id="ifEffectDiv" style="display:none"></c:otherwise>
	</c:choose>
		<td class="label">
			<fmt:message key="faultCommont.resumeTime" />
		</td>
		<td class="content">
			<input type="text"name="resumeTime" size="20" value="${faultCommontForm.resumeTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		<td class="label">
			<fmt:message key="faultCommont.appEffect" />
		</td>
		<td class="content">
			<html:text property="appEffect" styleId="appEffect"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.appEffect}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultPhenomenon" />
		</td>
		<td class="content" colspan="3">
			<html:text property="faultPhenomenon" styleId="faultPhenomenon"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.faultPhenomenon}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultCause" />
		</td>
		<td class="content" colspan="3">
			<html:text property="faultCause" styleId="faultCause"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.faultCause}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.dealDetail" />
		</td>
		<td class="content" colspan="3">
			<html:text property="dealDetail" styleId="dealDetail"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.dealDetail}" />
		</td>
	</tr>
	<tr>
		<td class="label">${eoms:a2u('通用任务工单')}</td>
		<td class="content" colspan="3">
			<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.serialNos}" />&nbsp;&nbsp;
						<input type="button" value=<fmt:message key="faultCommont.addFaultButton" /> onclick="addTaskSheet();"
							class="clsbtn2">
			&nbsp;<a href="gzbgb.doc">${eoms:a2u('云南分公司重要通信故障报告表 ')}</a>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.serialno" />
		</td>
		<td class="content" >
			<html:text property="serialno" styleId="serialno"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.serialno}" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.informOfficeTime" />
		</td>
		<td class="content" >
			<input type="text" name="informOfficeTime" size="20" value="${faultCommontForm.informOfficeTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultComment" />
		</td>
		<td class="content" colspan="3">
			<html:text property="faultComment" styleId="faultComment"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.faultComment}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.estate" />
		</td>
		<td class="content" colspan="3">
			<c:choose>
			<c:when	test="${faultCommontForm.estate=='1'}">
				<input type="radio" id="estate1" name="estate" value="1" checked="checked">
				<fmt:message key="faultCommont.yes" />
				<input type="radio" id="estate2" name="estate" value="0">
				<fmt:message key="faultCommont.no" />
			</c:when>
			<c:otherwise>
				<input type="radio" id="estate1" name="estate" value="1">
				<fmt:message key="faultCommont.yes" />
				<input type="radio" id="estate2" name="estate" value="0" checked="checked">
				<fmt:message key="faultCommont.no" />
			</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateFaultForm();" />
		</td>
	</tr>
</table>
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>