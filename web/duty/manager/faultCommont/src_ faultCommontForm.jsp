<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultCommontForm'});
});
</script>
<script language="javascript" src="faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<html:form action="/faultCommonts.do?method=save" styleId="faultCommontForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

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
				<option value=''></option>
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
			<eoms:comboBox name="typeId" id="typeId" initDicId="1040402" /> 
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
			<input type="radio" name="isFirstFind" value="1" checked="checked">
				<fmt:message key="faultCommont.yes" />
			<input type="radio" name="isFirstFind" value="0">
				<fmt:message key="faultCommont.no" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCommont.isAppEffect" />
		</td>
		<td class="content">
		    <input type="radio" name="isAppEffect" value="1">
				<fmt:message key="faultCommont.yes" />
			<input type="radio" name="isAppEffect" value="0" checked="checked">
				<fmt:message key="faultCommont.no" />
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
			<html:text property="faultAddress" styleId="faultAddress"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.faultAddress}" />
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
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.resumeTime" />
		</td>
		<td class="content">
			<input type="text" name="resumeTime" size="20" value="${faultCommontForm.resumeTime}"
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
		<td class="label">
			<fmt:message key="faultCommont.serialNos" />
		</td>
		<td class="content" colspan="3">
			<html:text property="serialNos" styleId="serialNos"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.serialNos}" />&nbsp;&nbsp;
						<input type="button" value=<fmt:message key="faultCommont.addFaultButton" /> onclick="addFaultSheet();"
							class="clsbtn2">
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
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${faultCommontForm.faultComment}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.estate" />
		</td>
		<td class="content" colspan="3">
			<input type="radio" name="estate" value="1" checked="checked">
				<fmt:message key="faultCommont.yes" />
			<input type="radio" name="estate" value="0">
				<fmt:message key="faultCommont.no" />
		</td>
	</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty faultCommontForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/faultCommont/faultCommonts.do?method=remove&id=${faultCommontForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${faultCommontForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>