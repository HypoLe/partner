<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form  action="/grids.do?method=save" styleId="gridForm" method="post" > 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="grid.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="grid.gridName" />
		</td>
		<td class="content">
			${gridForm.gridName}		
		</td>
		
		<td class="label">
			<fmt:message key="grid.region" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.region}" beanId="tawSystemAreaDao" />
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.city}" beanId="tawSystemAreaDao" />
		</td>
		<td class="label">
			<fmt:message key="grid.provider" />
		</td>
		<td class="content">
			${gridForm.provider}
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="grid.addUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.addUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label">
			<fmt:message key="grid.addTime" />
		</td>
		<td class="content">
			${gridForm.addTime}
		</td>
	</tr>
<c:if test="${not empty gridForm.updateUser}">
	<tr>
		<td class="label">
			<fmt:message key="grid.updateUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.updateUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label">
			<fmt:message key="grid.updateTime" />
		</td>
		<td class="content">
			${gridForm.updateTime}
		</td>
	</tr>
</c:if>
</table>
</fmt:bundle>
	<table>
		<tr>
			<td>
				<c:if test="${param.flag!='search'}">
					<input type="button" class="btn"
						value="<fmt:message key="button.edit"/>"
						onclick="javascript:if(confirm('确定修改吗?')){
						var url=eoms.appPath+'/partner/serviceArea/grids.do?method=edit&id=${gridForm.id}';
						location.href=url}" />
					<input type="button" class="btn"
						value="<fmt:message key="button.delete"/>"
						onclick="javascript:if(confirm('确定删除吗?')){
						var url=eoms.appPath+'/partner/serviceArea/grids.do?method=remove&id=${gridForm.id}';
						location.href=url}" />
				
				</c:if>
			</td>
		</tr>
	</table>
	<html:hidden property="id" value="${gridForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>