<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'oilEngineConfigureForm'});
});
</script>

<html:form action="/oilEngineConfigures.do?method=save" styleId="oilEngineConfigureForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="oilEngineConfigure.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="oilEngineConfigure.region" />
		</td>
		<td class="content">
			<!-- 地市 -->			
			<eoms:id2nameDB id="${oilEngineConfigureForm.region}" beanId="tawSystemAreaDao" />
			
		</td>
		<td class="label">
			<fmt:message key="oilEngineConfigure.city" />
		</td>
		<td class="content">
			
			<!-- 县区 -->			
			<eoms:id2nameDB id="${oilEngineConfigureForm.city}" beanId="tawSystemAreaDao" />		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="oilEngineConfigure.provider" />
		</td>
		<td class="content">
			${oilEngineConfigureForm.provider}
		</td>
		<td class="label">
			<fmt:message key="oilEngineConfigure.disposeNo" />
		</td>
		<td class="content">
			${oilEngineConfigureForm.disposeNo}
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
							var url=eoms.appPath+'/partner/baseinfo/oilEngineConfigures.do?method=edit&id=${oilEngineConfigureForm.id}';
							location.href=url"
				 />
				
				<c:if test="${not empty oilEngineConfigureForm.id}">
					<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
						onclick="javascript:if(confirm('是否删除？')){
							var url=eoms.appPath+'/partner/baseinfo/oilEngineConfigures.do?method=removeIsDel&id=${oilEngineConfigureForm.id}';
							location.href=url}"	/>
				</c:if>
			
			</c:if>
			
		</td>
	</tr>
</table>
<html:hidden property="id" value="${oilEngineConfigureForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>