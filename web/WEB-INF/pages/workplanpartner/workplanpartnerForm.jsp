<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'workplanpartnerForm'});
});
</script>

<html:form action="/workplanpartners.do?method=save" styleId="workplanpartnerForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/workplanpartner/config/applicationResource-workplanpartner">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="workplanpartner.form.heading"/></div>
	</caption>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty workplanpartnerForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/workplanpartner/workplanpartners.do?method=remove&nodeId=${workplanpartnerForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${workplanpartnerForm.id}" />
<html:hidden property="nodeId" value="${workplanpartnerForm.nodeId}" />
<html:hidden property="parentNodeId" value="${workplanpartnerForm.parentNodeId}" />
<html:hidden property="leaf" value="${workplanpartnerForm.leaf}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>