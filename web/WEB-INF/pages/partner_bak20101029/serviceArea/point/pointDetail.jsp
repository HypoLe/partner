<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="/points.do?method=save" styleId="pointForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="point.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="point.pointName" />
		</td>
		<td class="content">
			${pointForm.pointName}
		</td>

		<td class="label">
			<fmt:message key="point.region" />
		</td>
		<td class="content">
		<eoms:id2nameDB id="${pointForm.region}" beanId="tawSystemAreaDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="point.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pointForm.city}" beanId="tawSystemAreaDao" />
		</td>

		
		<td class="label">
			<fmt:message key="point.provider" />
		</td>
		<td class="content">
			${pointForm.provider}
		</td>
		
	</tr>


	<tr>

		<td class="label">
			<fmt:message key="point.userNameNew" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pointForm.userNameNew}" beanId="tawSystemUserDao" />
		</td>


		<td class="label">
			<fmt:message key="point.timeNew" />
		</td>
		<td class="content"> 
			${pointForm.timeNew}
		</td>



	</tr>

<c:if test="${not empty pointForm.userNameModify}">
	<tr>
		<td class="label">
			<fmt:message key="point.userNameModify" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pointForm.userNameModify}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="point.timeModify" />
		</td>
		<td class="content">
			${pointForm.timeModify}
		</td>
	</tr>
</c:if>

	<tr>
		<td class="label">
			<fmt:message key="point.grid" />
		</td>
		<td class="content"  colspan=3>
			${pointForm.grid}
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
							var url=eoms.appPath+'/partner/serviceArea/points.do?method=edit&id=${pointForm.id}';
							location.href=url"
				/>
				<c:if test="${not empty pointForm.id}">
					<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
						onclick="javascript:if(confirm('是否删除?')){
							var url=eoms.appPath+'/partner/serviceArea/points.do?method=removeIsDel&id=${pointForm.id}';
							location.href=url}"	/>
				</c:if>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pointForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>