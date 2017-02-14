<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<html:form action="/instrumentConfigs.do?method=save" styleId="instrumentConfigForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="instrumentConfig.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="instrumentConfig.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
								<!-- 地市 -->			
			<eoms:id2nameDB id="${instrumentConfigForm.region}" beanId="tawSystemAreaDao" />
		</td>
		<td class="label">
			<fmt:message key="instrumentConfig.city" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
								<!-- 县区 -->			
			<eoms:id2nameDB id="${instrumentConfigForm.city}" beanId="tawSystemAreaDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="instrumentConfig.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
									<!-- 所属公司 -->			
			${instrumentConfigForm.provider}
		</td>
		<td class="label">
			<fmt:message key="instrumentConfig.serviceProfessional" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${instrumentConfigForm.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />
			
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="instrumentConfig.use" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${instrumentConfigForm.use}"  beanId="ItawSystemDictTypeDao" />
			
		</td>
		<td class="label">
			<fmt:message key="instrumentConfig.type" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${instrumentConfigForm.type}"  beanId="ItawSystemDictTypeDao" />
				
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="instrumentConfig.disposeNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			${instrumentConfigForm.disposeNo}
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
								var url=eoms.appPath+'/partner/baseinfo/instrumentConfigs.do?method=edit&id=${instrumentConfigForm.id}';
								location.href=url"
					 />
					<c:if test="${not empty instrumentConfigForm.id}">
						<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
							onclick="javascript:if(confirm('你确定删除?')){
								var url=eoms.appPath+'/partner/baseinfo/instrumentConfigs.do?method=remove&id=${instrumentConfigForm.id}';
								location.href=url}"	/>
					</c:if>
			
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${instrumentConfigForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>