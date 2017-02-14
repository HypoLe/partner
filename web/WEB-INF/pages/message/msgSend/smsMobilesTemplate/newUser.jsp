<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<html:form action="/smsMobilesTemplates.do?method=saveUser" styleId="smsMobilesTemplateForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/message/config/ApplicationResources-msg">

<script language="javascript">
Ext.onReady(function() {
		v = new eoms.form.Validation({form:'smsMobilesTemplateForm'});
});

function check(){
	 
    	if(document.forms[0].userName.value==""){
    		alert("请填写姓名!");
    		return false; 
    	}
    	if(document.forms[0].mobile.value==""){
    		alert("请填写电话!");
    		return false; 
    	}
    	if(document.forms[0].dept.value==""){
    		alert("请填写部门!");
    		return false; 
    	}
    	if(document.forms[0].position.value==""){
    	    alert("请填写职务!");
    		return false; 
    	}
}
</script>
<table class="formTable">
	<caption>
	
		<div class="header center"><fmt:message key="smsButton.new"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="smsUserMgr.userName" />
		</td>
		<td class="content">
			<html:text property="userName" styleId="userName"
						styleClass="text medium" 
						alt="allowBlank:false,vtext:''" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="smsUserMgr.mobile" />
		</td>
		<td class="content">
			<html:text property="mobile" styleId="mobile"
						styleClass="text medium" alt="allowBlank:false,vtype:'number',vtext:''" />
		</td>
	</tr>
	<tr>
	     <td class="label">
	         <fmt:message key="deptList.heading"/>
	     </td>
	     <td class="newtable">
	         <html:text property="dept" styleId="dept"
	                    styleClass="text medium" alt="allowBlank:false,vtext:''" />
	     </td>
	</tr>
	<tr>
	     <td class="label">
	         <fmt:message key="smsUserMgr.position"/>
	     </td>
	     <td class="newtable">
	         <html:text property="position" styleId="position"
	                    styleClass="text medium" alt="allowBlank:false,vtext:''" />
	     </td>
	</tr>
	<html:hidden property="teamId"  />
</table>
</fmt:bundle>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" onClick="javascript:return check()"/>
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>