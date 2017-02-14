<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperLogForm'});
});
</script>

<html:form action="/attemperLogs.do?method=save" styleId="attemperLogForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemperLog.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.operTime" />
		</td>
		<td class="content">
			<html:text property="operTime" styleId="operTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.operTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.userId" />
		</td>
		<td class="content">
			<html:text property="userId" styleId="userId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.userId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.userName" />
		</td>
		<td class="content">
			<html:text property="userName" styleId="userName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.userName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.deptId" />
		</td>
		<td class="content">
			<html:text property="deptId" styleId="deptId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.deptId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.deptName" />
		</td>
		<td class="content">
			<html:text property="deptName" styleId="deptName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.deptName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.type" />
		</td>
		<td class="content">
			<html:text property="type" styleId="type"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.type}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.operName" />
		</td>
		<td class="content">
			<html:text property="operName" styleId="operName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.operName}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.attemperId" />
		</td>
		<td class="content">
			<html:text property="attemperId" styleId="attemperId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.attemperId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.subAttemperId" />
		</td>
		<td class="content">
			<html:text property="subAttemperId" styleId="subAttemperId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.subAttemperId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.title" />
		</td>
		<td class="content">
			<html:text property="title" styleId="title"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.title}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperLog.intendBeginTime" />
		</td>
		<td class="content">
			<html:text property="intendBeginTime" styleId="intendBeginTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperLogForm.intendBeginTime}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty attemperLogForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/attemperLog/attemperLogs.do?method=remove&id=${attemperLogForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${attemperLogForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>