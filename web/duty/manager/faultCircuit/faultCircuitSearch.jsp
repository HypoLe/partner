<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<html:form action="/faultCircuits.do?method=list" styleId="faultCircuitForm" method="post"> 
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
			<eoms:comboBox name="typeId" id="typeId" defaultValue="${faultCircuitForm.typeId}" initDicId="1040403" /> 
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
			<input type="radio" name="propertyRight" value="-1" checked="checked"><fmt:message key="faultCommont.all" />
			<input type="radio" name="propertyRight" value="1"><fmt:message key="faultCircuit.yes" />
			<input type="radio" name="propertyRight" value="2"><fmt:message key="faultCircuit.no" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.isAppEffect" />
		</td>
		<td class="content" >
			<input type="radio" name="isAppEffect" value="-1" checked="checked"><fmt:message key="faultCommont.all" />
			<input type="radio" name="isAppEffect" value="1" ><fmt:message key="faultCommont.yes" />
			<input type="radio" name="isAppEffect" value="0"><fmt:message key="faultCommont.no" />
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
			<fmt:message key="faultCircuit.appEffect" />
		</td>
		<td class="content" colspan="3">
			<html:text property="appEffect" styleId="appEffect"
						styleClass="text medium" style="3.6 cm"
						alt="allowBlank:true,vtext:''" value="${faultCircuitForm.appEffect}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCircuit.beginTime" />
		</td>
		<td class="content" colspan="3">
			<input type="text" name="fromBeginTime" size="20" value="${faultCircuitForm.beginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		&nbsp;&nbsp;--<fmt:message key="faultCommont.to" />--&nbsp;&nbsp;
			<input type="text" name="toBeginTime" size="20" value="${faultCircuitForm.beginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		
	</tr>
</table>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />
		</td>
	</tr>
</table>
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>