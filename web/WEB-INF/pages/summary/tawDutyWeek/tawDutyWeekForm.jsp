<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawDutyWeekForm'});
});
</script>

<html:form action="/tawDutyWeeks.do?method=save" styleId="tawDutyWeekForm" method="post"> 

<fmt:bundle basename="config/ApplicationResources-summary">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="taskplanList.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="tawDutyWeek.title" />
		</td>
		<td class="content">
			<html:text property="title" styleId="title"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawDutyWeekForm.title}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawDutyWeek.accessories" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawDutyWeekForm.id }">
					<eoms:attachment name="tawDutyWeekForm" property="accessoriesId" 
           				scope="request" idField="accessoriesId" appCode="tawDutyWeek" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="accessoriesId" appCode="tawDutyWeek"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawDutyWeek.gejieAccessories" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawDutyWeekForm.id }">
					<eoms:attachment name="tawDutyWeekForm" property="gejieaccessoriesId" 
           				scope="request" idField="gejieaccessoriesId" appCode="tawDutyWeek" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="gejieaccessoriesId" appCode="tawDutyWeek"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawDutyWeek.yiliuAccessories" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawDutyWeekForm.id }">
					<eoms:attachment name="tawDutyWeekForm" property="yiliuaccessoriesId" 
           				scope="request" idField="yiliuaccessoriesId" appCode="tawDutyWeek" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="yiliuaccessoriesId" appCode="tawDutyWeek"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawDutyWeek.olpAccessories" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawDutyWeekForm.id }">
					<eoms:attachment name="tawDutyWeekForm" property="olpaccessoriesId" 
           				scope="request" idField="olpaccessoriesId" appCode="tawDutyWeek" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="olpaccessoriesId" appCode="tawDutyWeek"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty tawDutyWeekForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确认删除吗?')){
						var url='${app}/summary/tawDutyWeeks.do?method=remove&id=${tawDutyWeekForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${tawDutyWeekForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>