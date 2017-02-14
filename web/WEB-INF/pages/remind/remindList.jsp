<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<div class="list-title">
	<B><bean:message key="remind.heading"/></B>
</div>
<table class="listTable" id="list-table">
	<tr height="30" class="header">
		<td nowrap="nowrap" width="20%">
			<bean:message key="remind.remindTitle"/>
		</td>
		<td nowrap="nowrap" width="20%">
			<bean:message key="remind.remindContent"/>
		</td>
		<td nowrap="nowrap" width="20%">
			<bean:message key="remind.perform"/>
		</td>
	</tr>
<logic:iterate id="remindIt" name="remindIt">
	<tr height="30">
		<td nowrap="nowrap" width="20%">
			<bean:write name="remindIt" property="remindTitle"/>
		</td>
		<td nowrap="nowrap" width="20%">
			<bean:write name="remindIt" property="remindContent"/>
		</td>
		<td nowrap="nowrap" width="20%">
			<a href='${app}<bean:write name="remindIt" property="url"/>'><bean:message key="remind.perform"/></a>
		</td>
	</tr>
</logic:iterate>
</table>
<%@ include file="/common/footer_eoms.jsp"%>