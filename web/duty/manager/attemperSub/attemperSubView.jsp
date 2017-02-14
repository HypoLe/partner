<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperSubForm'});
});
</script>

<html:form action="/attemperSubs.do?method=save" styleId="attemperSubForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center"><bean:write name="ATTEMPER" property="title" scope="request"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.deptId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperSubForm.deptId}" beanId="tawSystemDeptDao" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.cruser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperSubForm.cruser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.intendBeginTime" />
		</td>
		<td class="content">
			<bean:write name="attemperSubForm" property="intendBeginTime" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.intendEndTime" />
		</td>
		<td class="content">
			<bean:write name="attemperSubForm" property="intendEndTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.isAppEffect" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${attemperSubForm.isAppEffect}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.persistTimes" />
		</td>
		<td class="content">
			<bean:write name="attemperSubForm" property="persistTimes" />
		</td>
	</tr>
	<c:choose>
		<c:when	test="${attemperSubForm.isAppEffect=='1'}">
		<tr>
			<td class="label" height="78"><fmt:message key="attemperSub.effectOperation" /></td>
			<td class="content" colspan="3">
				<c:choose><c:when test="${attemperSubForm.effectOperation !=null}">	
					<bean:define id="effectOperation" name="attemperSubForm" property="effectOperation" />
						<%= com.boco.eoms.base.util.StaticMethod.toHtml((String)effectOperation) %>
				</c:when></c:choose>&nbsp;
			</td>
		</tr>
	</c:when></c:choose>
	<tr>
		<td class="label" height="78"><fmt:message key="attemperSub.content" /></td>
		<td class="content" colspan="3">
			<c:choose><c:when test="${attemperSubForm.content !=null}">	
				<bean:define id="content" name="attemperSubForm" property="content" />
				<%= com.boco.eoms.base.util.StaticMethod.toHtml((String)content) %>
			</c:when></c:choose>&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.status" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="attemperStatus" itemId="${attemperSubForm.status}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.holdTime" />
		</td>
		<td class="content">
			<bean:write name="attemperSubForm" property="holdTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.resultType" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="attemperResult" itemId="${attemperSubForm.resultType}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.finishUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperSubForm.finishUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
		<td class="label" height="78"><fmt:message key="attemperSub.remark" /></td>
		<td class="content" colspan="3">
			<c:choose><c:when test="${attemperSubForm.remark !=null}">	
				<bean:define id="remark" name="attemperSubForm" property="remark" />
				<%= com.boco.eoms.base.util.StaticMethod.toHtml((String)remark) %>
			</c:when></c:choose>&nbsp;
		</td>
	</tr>
</table>

<c:choose>
	<c:when	test="${attemperSubForm.deptId==sessionform.deptid&&attemperSubForm.status==1&&attemperSubForm.strutsAction==4}">
	<table>
		<tr>
			<td>
				<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
						onclick="javascript:{
							var url='${app}/duty/attemperSubs.do?method=edit&id=${attemperSubForm.id}';
							location.href=url};"	/>
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('<fmt:message key="faultCommont.is.delete"/>')){
						var url='${app}/duty/attemperSubs.do?method=remove&id=${attemperSubForm.id}';
						location.href=url}"	/>
				<input type="button" class="btn" value="<fmt:message key="button.finish"/>" 
					onclick="javascript:
						var url='${app}/duty/attemperSubs.do?method=finish&id=${attemperSubForm.id}';
						location.href=url"	/>
				<input type="button" class="btn" value="<fmt:message key="button.goback"/>" onclick="GoBack();" />
			</td>
		</tr>
	</table>
	</c:when>
</c:choose>
<html:hidden property="id" value="${attemperSubForm.id}" />
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>