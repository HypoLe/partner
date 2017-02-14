<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'checkForm'});
});
</script>

<html:form action="/checks.do?method=save" styleId="checkForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="check.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="check.person" />
		</td>
		<td class="content" >
			<eoms:id2nameDB id="${checkForm.person}" beanId="tawSystemUserDao" />
		</td>
		
		<td class="label">
			<fmt:message key="check.speciality" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${checkForm.speciality}"  beanId="ItawSystemDictTypeDao" />
		</td>
		

	</tr>


	<tr>
	
		<td class="label">
			<fmt:message key="check.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${checkForm.city}" beanId="tawSystemAreaDao" />
		</td>
	
		<td class="label">
			<fmt:message key="check.county" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${checkForm.county}" beanId="tawSystemAreaDao" />
		</td>

	</tr>
	

	<tr>

		<td class="label">
			<fmt:message key="check.checkTime" />
		</td>
		<td class="content">
		
	          ${checkForm.checkTime}
		
		
		</td>
		
		<td class="label">
			<fmt:message key="check.writeTime" />
		</td>
		<td class="content">
			${checkForm.writeTime}

		</td>
		
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="check.sample" />
		</td>
		<td class="content" colspan=3>
			${checkForm.sample}
		</td>

	</tr>
	
	

	<tr>
		<td class="label">
			<fmt:message key="check.condition" />
		</td>
		<td class="content" colspan=3>
			${checkForm.condition}
		</td>
	
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="check.remark" />
		</td>
		<td class="content" colspan=3 >
			${checkForm.remark}

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
							var url=eoms.appPath+'/partner/maintenance/checks.do?method=edit&id=${checkForm.id}';
							location.href=url"
				/>
				<c:if test="${not empty checkForm.id}">
					<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
						onclick="javascript:if(confirm('确定删除？')){
							var url=eoms.appPath+'/partner/maintenance/checks.do?method=removeIsDel&id=${checkForm.id}';
							location.href=url}"	/>
				</c:if>
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${checkForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>