<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'problemForm'});
});
</script>

<html:form action="/problems.do?method=save" styleId="problemForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="problem.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="problem.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${problemForm.city}" beanId="tawSystemAreaDao" />
		</td>
		<td class="label">
			<fmt:message key="problem.county" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${problemForm.county}" beanId="tawSystemAreaDao" />
		</td>		
		
	</tr>

	<tr>

		<td class="label">
			<fmt:message key="problem.partner" />
		</td>
		<td class="content">
			${problemForm.partner}
		</td>

		<td class="label">
			<fmt:message key="problem.reportDept" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-maintenance" dictId="reportDept" itemId="${problemForm.reportDept}" beanId="id2nameXML" />
		</td>
	</tr>

	<tr>
		<!-- 上报人姓名 取当前用户 -->
		<td class="label">
			<fmt:message key="problem.reportPerson" />
		</td>
		<td class="content" colspan=3>

			<eoms:id2nameDB id="${problemForm.reportPerson}" beanId="tawSystemUserDao" />

		</td>
	</tr>

	<tr>
			<td class="label">
			<fmt:message key="problem.problem" />
		</td>
		<td class="content" colspan=3>
			${problemForm.problem}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="problem.handlingInfo" />
		</td>
		<td class="content" colspan=3>
			${problemForm.handlingInfo}
		</td>
	
	</tr> 

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<c:if test="${param.flag!='search'}">
				<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
				onclick="javascript:
							var url=eoms.appPath+'/partner/maintenance/problems.do?method=edit&id=${problemForm.id}';
							location.href=url"
				/>
				<c:if test="${not empty problemForm.id}">
					<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
						onclick="javascript:if(confirm('是否删除？')){
							var url=eoms.appPath+'/partner/maintenance/problems.do?method=removeIsDel&id=${problemForm.id}';
							location.href=url}"	/>
				</c:if>
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${problemForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>