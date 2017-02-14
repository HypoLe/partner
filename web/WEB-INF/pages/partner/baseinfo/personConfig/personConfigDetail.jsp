<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="/personConfigs.do?method=save" styleId="personConfigForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="personConfig.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="personConfig.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
					<!-- 地市 -->			
			<eoms:id2nameDB id="${personConfigForm.region}" beanId="tawSystemAreaDao" />
		</td>
		<td class="label">
			<fmt:message key="personConfig.city" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
					<!-- 县区 -->			
			<eoms:id2nameDB id="${personConfigForm.city}" beanId="tawSystemAreaDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="personConfig.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
					<!-- 所属公司 -->			
			${personConfigForm.provider}
		</td>
		<td class="label">
			<fmt:message key="personConfig.serviceProfessional" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${personConfigForm.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="personConfig.disposeNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${personConfigForm.disposeNo}
		</td>
		<td class="label">
			<fmt:message key="personConfig.responsibility" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${personConfigForm.responsibility}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="personConfig.siteNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan='3'>
			${personConfigForm.siteNo}
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
							var url=eoms.appPath+'/partner/baseinfo/personConfigs.do?method=edit&id=${personConfigForm.id}';
							location.href=url"
				 />
				<c:if test="${not empty personConfigForm.id}">
					<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
						onclick="javascript:if(confirm('你确定删除?')){
							var url=eoms.appPath+'/partner/baseinfo/personConfigs.do?method=remove&id=${personConfigForm.id}';
							location.href=url}"	/>
				</c:if>
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${personConfigForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>