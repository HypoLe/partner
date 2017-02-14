<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'eventReadForm'});
});
</script>

<html:form action="/eventReads.do?method=save" styleId="eventReadForm" method="post"> 

<fmt:bundle basename="config/applicationResource-eventread">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="eventRead.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="eventRead.eventid" />
		</td>
		<td class="content">
			<html:text property="eventid" styleId="eventid"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${eventReadForm.eventid}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="eventRead.userid" />
		</td>
		<td class="content">
			<html:text property="userid" styleId="userid"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${eventReadForm.userid}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="eventRead.readtime" />
		</td>
		<td class="content">
			<html:text property="readtime" styleId="readtime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${eventReadForm.readtime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="eventRead.deptid" />
		</td>
		<td class="content">
			<html:text property="deptid" styleId="deptid"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${eventReadForm.deptid}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="eventRead.readaffirm" />
		</td>
		<td class="content">
			<html:text property="readaffirm" styleId="readaffirm"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${eventReadForm.readaffirm}" />
		</td>
	</tr>


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty eventReadForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/eventRead/eventReads.do?method=remove&id=${eventReadForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${eventReadForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>