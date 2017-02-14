<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'serviceSpeedForm'});
});
</script>

<html:form action="/serviceSpeeds.do?method=save" styleId="serviceSpeedForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="serviceSpeed.form.heading"/></div>
	</caption>



	<tr>
<!-- 地市地域 -->		
		<td class="label">
			<fmt:message key="serviceSpeed.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${serviceSpeedForm.region}" beanId="tawSystemAreaDao" />
		</td>
		
		
		<td class="label"> 
			县区&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${serviceSpeedForm.city}" beanId="tawSystemAreaDao" />
		</td>			
		
		
	</tr>

	<tr>
<!-- 合作伙伴 -->		
		<td class="label">
			<fmt:message key="serviceSpeed.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.provider}
		</td>	
	
<!-- 网格 -->		
		<td class="label">
			<fmt:message key="serviceSpeed.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.grid}
		</td>		
	</tr>
	<tr>
	
	<!-- 年 -->
		<td class="label">
			<fmt:message key="serviceSpeed.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.year}年									
		</td>
	<!-- 月份 -->
		<td class="label">
			<fmt:message key="serviceSpeed.month" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.month}月				
		</td>
				
	</tr>
<!-- 网络故障响应度 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.webfailure" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.webfailure}
		</td>
<!-- 客户投诉处理响应度 -->
		<td class="label">
			<fmt:message key="serviceSpeed.customerComplaints" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.customerComplaints}
		</td>
	</tr>
<!-- 表报上报及时率 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.fromReport" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.fromReport}
		</td>
<!-- 表报上报准确率 -->
		<td class="label">
			<fmt:message key="serviceSpeed.fromPrecision" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.fromPrecision}
		</td>
	</tr>
<!-- 资料更新准确率 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.datumUpdate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.datumUpdate}		
		</td>
<!-- 应急通信保障响应度 -->
		<td class="label">
			<fmt:message key="serviceSpeed.commSecurity" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.commSecurity}		
		</td>
	</tr>
<!-- 对基层业务、服务的响应度 -->
	<tr>
		<td class="label">
			<fmt:message key="serviceSpeed.toService" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${serviceSpeedForm.toService}
		</td>
<!-- 上报时间 -->
		<td class="label">
			<fmt:message key="serviceSpeed.reportTime" />
		</td>
		<td class="content">
			${serviceSpeedForm.reportTime}
		</td>
	</tr>	
	<tr>
		<!-- 上报人 -->
		<td class="label">
			<fmt:message key="serviceSpeed.reportUser" />
		</td>
		<td class="content" colspan="3">
			<eoms:id2nameDB id="${serviceSpeedForm.reportUser}" beanId="tawSystemUserDao" />
		</td>
	
	</tr>	

</table>

</fmt:bundle>

	<table>
		<tr>
			<td>
				<c:if test="${param.flag!='search'}">
					<input type="button" class="btn"
						value="<fmt:message key="button.edit"/>"
						onclick="javascript:if(confirm('确定修改吗?')){
						var url=eoms.appPath+'/partner/maintenance/serviceSpeeds.do?method=edit&id=${serviceSpeedForm.id}';
						location.href=url}" />
					<input type="button" class="btn"
						value="<fmt:message key="button.delete"/>"
						onclick="javascript:if(confirm('确定删除吗?')){
						var url=eoms.appPath+'/partner/maintenance/serviceSpeeds.do?method=remove&id=${serviceSpeedForm.id}';
						location.href=url}" />
				
				</c:if>
			</td>
		</tr>
	</table>
<html:hidden property="id" value="${serviceSpeedForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>