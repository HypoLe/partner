<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'vehicleForm'});
});

</script>

<html:form action="/vehicles.do?method=save" styleId="vehicleForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="vehicle.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="vehicle.region" />
		</td>
		<td class="content">
			<!-- 地市 -->			
			<eoms:id2nameDB id="${vehicleForm.region}" beanId="tawSystemAreaDao" />
		</td>

		<td class="label">
			<fmt:message key="vehicle.city" />
		</td>
		<td class="content">
			<!-- 县区 -->			
			<eoms:id2nameDB id="${vehicleForm.city}" beanId="tawSystemAreaDao" />			
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="vehicle.provider" />
		</td>
		<td class="content">
			${vehicleForm.provider}
		</td>

		<td class="label">
			<fmt:message key="vehicle.serviceProfession" />
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${vehicleForm.serviceProfession}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="vehicle.use" />
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${vehicleForm.use}"  beanId="ItawSystemDictTypeDao" />			
		</td>

		<td class="label">
			<fmt:message key="vehicle.disposeNo" />
		</td>
		<td class="content">
			${vehicleForm.disposeNo}
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
						var url=eoms.appPath+'/partner/baseinfo/vehicles.do?method=edit&id=${vehicleForm.id}';
						location.href=url"
			 />
			
			<c:if test="${not empty vehicleForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除？')){
						var url=eoms.appPath+'/partner/baseinfo/vehicles.do?method=removeIsDel&id=${vehicleForm.id}';
						location.href=url}"	/>
			</c:if>
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${vehicleForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>