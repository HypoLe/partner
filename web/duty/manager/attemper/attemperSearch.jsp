<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperForm'});
});
</script>

<html:form action="/attempers.do?method=sublist" styleId="attemperForm" method="post"> 
<html:hidden name="attemperForm" property="strutsAction" />

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemper.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="attemper.recordDept" />
		</td>
		<td class="content">
			<eoms:comboBox name="recordDept" id="recordDept" defaultValue="${attemperForm.recordDept}" initDicId="1040201" alt="allowBlank:true,vtext:''"/> 
		</td>
		<td class="label">
			<fmt:message key="attemper.cruser" />
		</td>
		<td class="content">
			<html:text property="cruser" styleId="cruser"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperForm.cruser}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.netNames" />
		</td>
		<td class="content">
			<html:text property="netNames" styleId="netNames"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperForm.netNames}" />
		</td>
		<td class="label">
			<fmt:message key="attemper.speciality" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="attemperSpeciality" defaultId="${attemperForm.speciality}" beanId="selectXML" selectId="speciality"/>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.recordUser" />
		</td>
		<td class="content">
			<html:text property="recordUser" styleId="recordUser"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperForm.recordUser}" />
			(<fmt:message key="attemper.validate.date12" />)
		</td>
		<td class="label">
			<fmt:message key="attemper.status" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="attemperStatus" defaultId="${attemperForm.status}" beanId="selectXML" selectId="status"/>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.netDeptList" />
		</td>
		<td class="content">
			<eoms:comboBox name="netDeptList" id="netDeptList" defaultValue="${attemperForm.netDeptList}" initDicId="1040201"/> 
		</td>
		<td class="label">
			<fmt:message key="attemper.planSheetId" />
		</td>
		<td class="content">
			<html:text property="planSheetId" styleId="planSheetId"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperForm.planSheetId}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.title" />
		</td>
		<td class="content" colspan="3">
			<html:text property="title" styleId="title" style="width:13.5cm;"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperForm.title}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.beginTime" />
		</td>
		<td class="content" colspan="3">
			<input type="text" name="fromBeginTime" size="20" value="${attemperForm.fromBeginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		&nbsp;&nbsp;--<fmt:message key="faultCommont.to" />--&nbsp;&nbsp;
			<input type="text" name="toBeginTime" size="20" value="${attemperForm.toBeginTime}"
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