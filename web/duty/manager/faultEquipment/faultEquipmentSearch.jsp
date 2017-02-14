<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<html:form action="/faultEquipments.do?method=list" styleId="faultEquipmentForm" method="post"> 

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
						alt="allowBlank:true,vtext:''" value="${faultEquipmentForm.title}" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.greffier" />
		</td>
		<td class="content">
			<select name="greffier" style="width: 4.0cm">
				<option value='${faultEquipmentForm.greffier}'><eoms:id2nameDB id="${faultEquipmentForm.greffier}" beanId="tawSystemUserDao" /></option>
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
			<fmt:message key="faultEquipment.filialeId" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultfilialeId" defaultId="${faultEquipmentForm.filialeId}" beanId="selectXML" selectId="filialeId"/>
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.equipmentCorpId" />
		</td>
		<td class="content">
			<eoms:comboBox name="equipmentCorpId" id="equipmentCorpId" defaultValue="${faultEquipmentForm.equipmentCorpId}" initDicId="1040401" alt="allowBlank:false,vtext:''"/> 
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.serialNos" />
		</td>
		<td class="content">
			<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultEquipmentForm.serialNos}" />
		</td>
		<td class="label">
			<fmt:message key="faultEquipment.station" />
		</td>
		<td class="content">
			<html:text property="station" styleId="station"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultEquipmentForm.station}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.appEffect" />
		</td>
		<td class="content" colspan="3">
			<html:text property="appEffect" styleId="appEffect"
						styleClass="text medium" style="3.6 cm"
						alt="allowBlank:true,vtext:''" value="${faultEquipmentForm.appEffect}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultEquipment.beginTime" />
		</td>
		<td class="content" colspan="3">
			<input type="text" name="fromBeginTime" size="20" value="${faultEquipmentForm.beginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		&nbsp;&nbsp;--<fmt:message key="faultCommont.to" />--&nbsp;&nbsp;
			<input type="text" name="toBeginTime" size="20" value="${faultEquipmentForm.beginTime}"
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