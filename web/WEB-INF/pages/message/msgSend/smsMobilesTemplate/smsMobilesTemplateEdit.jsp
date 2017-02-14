<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="/saveSmsMobilesTemplate.do?method=xsave&deleted=2" method="post" styleId="smsUserMgrForm"> 
	<table class="formTable">
		<caption><bean:message key='smsTitle.newContent'/></caption>
		<tr>
			<td class="label">
				<bean:message key='smsMobilesTemplate.name'/>
			</td>
			<td class="content max">
				<html:text property="name" size="120"></html:text>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key='smsMobilesTemplate.mobiles'/>
			</td>
			<td class="content max">
				<html:textarea property="mobiles" styleId="mobiles" cols="90" rows="5"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key='smsMobilesTemplate.users'/>
			</td>
			<td class="label"><div id="user-list" class="viewer-list"></div>
    			<input type="button" value="<bean:message key='smsService.modifyuserlist'/>" id="userTreeBtn" class="btn"/>    		
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key='smsMobilesTemplate.remark'/>
			</td>
			<td class="content max">
				<html:textarea property="remark" cols="90" rows="5"></html:textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<html:submit property="save"><bean:message key="button.save"/></html:submit>	
				<html:button property="return"><bean:message key='smsButton.return'/></html:button>
			</td>
		</tr>		
	</table>
	<html:hidden property="id"/>
	<html:hidden property="deleted"/>
	<input type="hidden" id="users" name="users"/> 
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>