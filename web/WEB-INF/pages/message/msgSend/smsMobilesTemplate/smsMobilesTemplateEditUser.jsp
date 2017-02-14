<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'smsUserForm'});
});
</script>

<html:form action="/saveSmsMobilesTemplate.do?method=saveUser" styleId="smsUserForm" method="post"> 

<fmt:bundle
	basename="com/boco/eoms/message/config/ApplicationResources-msg">

<table class="formTable">
	

	<tr>
		<td class="label">
			<fmt:message key="smsUserMgr.userName" />
		</td>
		<td class="content">
			<html:text property="userName" styleId="userName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${smsUserMgrForm.name}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="smsUserMgr.mobile" />
		</td>
		<td class="content">
			<html:text property="mobile" styleId="mobile"
						styleClass="text medium" alt="allowBlank:false,vtype:'number',vtext:''" value="${smsUserMgrForm.mobile}" />
		</td>
	</tr>


	

	<tr>
	     <td class="label">
	         <fmt:message key="deptList.heading"/>
	     </td>
	     <td class="newtable">
	         <html:text property="dept" styleId="dept"
	                    styleClass="text medium" 
	                    alt="allowBlank:false,vtext:''" value="${smsUserMgrForm.dept}" />
	     </td>
	</tr>
	
	<tr>
	     <td class="label">
	         <fmt:message key="smsUserMgr.position"/>
	     </td>
	     <td class="newtable">
	         <html:text property="position" styleId="postion"
	                    styleClass="text medium"
	                    alt="allowBlank:true,vtext:''" value="${smsUserMgrForm.position}"/>
	     </td>
	</tr>
	


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			
		</td>
	</tr>
</table>
<html:hidden property="id" value="${smsUserMgrForm.id}" />
<html:hidden property="teamId" value="${smsUserMgrForm.teamId}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>