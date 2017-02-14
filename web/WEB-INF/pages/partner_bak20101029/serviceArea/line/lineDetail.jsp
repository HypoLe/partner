<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'lineForm'});
});
</script>

<html:form action="/lines.do?method=save" styleId="lineForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="line.form.heading"/></div>
	</caption> 

	<tr>
		<td class="label">
			<fmt:message key="line.lineName" />
		</td>
		<td class="content">
			${lineForm.lineName}
		</td>

		<td class="label">
			<fmt:message key="line.region" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${lineForm.region}" beanId="tawSystemAreaDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="line.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${lineForm.city}" beanId="tawSystemAreaDao" />
		</td>

		<td class="label">
			<fmt:message key="line.provider" />
		</td>
		<td class="content">
			${lineForm.provider}
		</td>

	</tr>

	<tr>

		<td class="label">
			<fmt:message key="line.grid" />
		</td>
		<td class="content">
			${lineForm.grid}
		</td>

		<td class="label">
			<fmt:message key="line.lineLength" />
		</td>
		<td class="content">
			${lineForm.lineLength}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="line.isFullService" />
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-serviceArea" dictId="isFullService" itemId="${lineForm.isFullService}" beanId="id2nameXML" />
		</td>

		<td class="label">
			<fmt:message key="line.grade" />
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${lineForm.grade}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="line.userNameNew" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${lineForm.userNameNew}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="line.timeNew" />
		</td>
		<td class="content">
			${lineForm.timeNew}
		</td>

	</tr>

<c:if test="${not empty lineForm.userNameModify}">

	<tr>
	
		<td class="label">
			<fmt:message key="line.userNameModify" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${lineForm.userNameModify}" beanId="tawSystemUserDao" />
		</td>
	
		<td class="label">
			<fmt:message key="line.timeModify" />
		</td>
		<td class="content">
			${lineForm.timeModify}
		</td>

	</tr>
</c:if>
	

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
		<c:if test="${param.flag!='search'}">

			<input type="button" class="btn" value="<fmt:message key="button.edit"/>"
			onclick="javascript:
						var url=eoms.appPath+'/partner/serviceArea/lines.do?method=edit&id=${lineForm.id}';
						location.href=url"
			
			 />
			<c:if test="${not empty lineForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除？')){
						var url=eoms.appPath+'/partner/serviceArea/lines.do?method=removeIsDel&id=${lineForm.id}';
						location.href=url}"	/>
			</c:if>
	
	
	
		</c:if>
		
		</td>
	</tr>
</table>
<html:hidden property="id" value="${lineForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>