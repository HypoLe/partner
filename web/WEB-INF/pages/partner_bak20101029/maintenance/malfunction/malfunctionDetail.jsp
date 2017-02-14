<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'malfunctionForm'});
});
</script>

<html:form action="/malfunctions.do?method=save" styleId="malfunctionForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="malfunction.form.heading"/></div>
	</caption>
	
	<tr>
		<td class="label">
			<fmt:message key="malfunction.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${malfunctionForm.city}" beanId="tawSystemAreaDao" />
		</td>

		<td class="label">
			<fmt:message key="malfunction.county" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${malfunctionForm.county}" beanId="tawSystemAreaDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.devicesFactory" />
		</td>
		<td class="content">

			<eoms:id2nameDB id="${malfunctionForm.devicesFactory}"  beanId="ItawSystemDictTypeDao" />
		</td>

		<td class="label">
			<fmt:message key="malfunction.devicesName" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${malfunctionForm.devicesName}"  beanId="ItawSystemDictTypeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.devicesLevel" />
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${malfunctionForm.devicesLevel}"  beanId="ItawSystemDictTypeDao" />			
		</td>

		<td class="label">
			<fmt:message key="malfunction.malfunctionLevel" />
		</td>
		<td class="content">
			
			<eoms:id2nameDB id="${malfunctionForm.malfunctionLevel}"  beanId="ItawSystemDictTypeDao" />			
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.beginTime" />
		</td>
		<td class="content">
	         ${malfunctionForm.beginTime}
		</td>

		<td class="label">
			<fmt:message key="malfunction.endTime" />
		</td>
		<td class="content">
	          ${malfunctionForm.endTime}
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="malfunction.malfunctionLast" />
		</td>
		<td class="content">
			${malfunctionForm.malfunctionLast}
		</td>

		<td class="label">
			<fmt:message key="malfunction.malfunctionState" />
		</td>
		<td class="content">
			${malfunctionForm.malfunctionState}
		</td>
	</tr>


	<tr>
		<td class="label">
			<fmt:message key="malfunction.partner" />
		</td>
		<td class="content">
			${malfunctionForm.partner}
		</td>

		<td class="label">
			<fmt:message key="malfunction.processPeople" />
		</td>
		<td class="content">
			${malfunctionForm.processPeople}
		</td>
	</tr>
	
	<tr>
		<!-- 上报人 取当前用户 -->
		<td class="label">
			<fmt:message key="malfunction.reportPerson" />
		</td>
		<td class="content" colspan=3>

			<eoms:id2nameDB id="${malfunctionForm.reportPerson}" beanId="tawSystemUserDao" />

		</td>
		
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="malfunction.causeAnalysis" />
		</td>
		<td class="content" colspan=3>
			${malfunctionForm.causeAnalysis}
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="malfunction.handlingInfo" />
		</td>
		<td class="content" colspan=3>
			${malfunctionForm.handlingInfo}
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
							var url=eoms.appPath+'/partner/maintenance/malfunctions.do?method=edit&id=${malfunctionForm.id}';
							location.href=url"
				/>
				<c:if test="${not empty malfunctionForm.id}">
					<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
						onclick="javascript:if(confirm('确定删除？')){
							var url=eoms.appPath+'/partner/maintenance/malfunctions.do?method=removeIsDel&id=${malfunctionForm.id}';
							location.href=url}"	/>
				</c:if>
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${malfunctionForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>