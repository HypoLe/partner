<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<html:form action="/faultCommonts.do?method=list" styleId="faultCommontForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="faultCommont.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.title" />
		</td>
		<td class="content">
			<html:text property="title" styleId="title"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${faultCommontForm.title}" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.greffier" />
		</td>
		<td class="content">
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
			<fmt:message key="faultCommont.typeId" />
		</td>
		<td class="content">
			<eoms:comboBox name="typeId" id="typeId" defaultValue="${faultCommontForm.typeId}" initDicId="1040402" alt="allowBlank:false,vtext:''"/> 
		</td>
		<td class="label">
			<fmt:message key="faultCommont.estate" />
		</td>
		<td class="content" colspan="3">
			<input type="radio" id="estate1" name="estate" value="-1" checked="checked">
			<fmt:message key="faultCommont.all" />
			<input type="radio" id="estate1" name="estate" value="1" >
			<fmt:message key="faultCommont.yes" />
			<input type="radio" id="estate2" name="estate" value="0">
			<fmt:message key="faultCommont.no" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultPhenomenon" />
		</td>
		<td class="content" >
			<html:text property="faultPhenomenon" styleId="faultPhenomenon"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.faultPhenomenon}" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.serialNos" />
		</td>
		<td class="content" >
			<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.serialNos}" />&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.appEffect" />
		</td>
		<td class="content" >
			<html:text property="appEffect" styleId="appEffect"
						styleClass="text medium" style="width: 90%"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.appEffect}" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.faultCause" />
		</td>
		<td class="content" >
			<html:text property="faultCause" styleId="faultCause"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.faultCause}" />&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.beginTime" />
		</td>
		<td class="content" colspan="3">
			<input type="text" name="fromBeginTime" size="20" value="${faultCommontForm.beginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		&nbsp;&nbsp;--<fmt:message key="faultCommont.to" />--&nbsp;&nbsp;
			<input type="text" name="toBeginTime" size="20" value="${faultCommontForm.beginTime}"
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