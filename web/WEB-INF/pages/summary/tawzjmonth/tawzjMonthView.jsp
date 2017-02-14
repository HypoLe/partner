<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="/tawzjMonths.do?method=save" styleId="tawzjMonthForm" method="post"> 

<fmt:bundle basename="config/ApplicationResources-summary">

<table class="formTable">
	<caption>
		<div class="header center"><html:text property="team" styleId="team" styleClass="text medium"
						 value="${tawzjMonthForm.team}"  readonly="true"/><fmt:message key="tawzjMonth.team"/>
						<%=(String)request.getAttribute("month")%><fmt:message key="tawzjMonth.result"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.work" />
		</td>
		<td class="content">
			<html:textarea property="work" styleId="work" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.work}" readonly="true" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.specialWork" />
		</td>
		<td class="content">
			<html:textarea property="specialWork" styleId="specialWork" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.specialWork}" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.legacy" />
		</td>
		<td class="content">
			<html:textarea property="legacy" styleId="legacy" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.legacy}" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.accessoriesId" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawzjMonthForm.id }">
					<eoms:attachment name="tawzjMonthForm" property="accessoriesId" 
           				scope="request" idField="accessoriesId" appCode="9" viewFlag="Y"/>  
				</c:when>
			</c:choose>
		</td>
	</tr>

</table>
<table class="formTable">
	<caption>
		<div class="header center"><html:text property="team" styleId="team" styleClass="text medium"
						 value="${tawzjMonthForm.team}" readonly="true"/><fmt:message key="tawzjMonth.team"/>
						<%=(String)request.getAttribute("nextMonth")%><fmt:message key="tawzjMonth.plan"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.work" />
		</td>
		<td class="content">
			<html:textarea property="monthWork" styleId="monthWork" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.monthWork}" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.specialWork" />
		</td>
		<td class="content">
			<html:textarea property="planSpecialWork" styleId="planSpecialWork" style="width:13.5cm;" 
						styleClass="text medium" rows="3" value="${tawzjMonthForm.planSpecialWork}" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.accessoriesId" />
		</td>
		<td class="content">
			<c:choose>
				<c:when test="${ not empty tawzjMonthForm.id }">
					<eoms:attachment name="tawzjMonthForm" property="planAccessoriesId" 
           				scope="request" idField="planAccessoriesId" appCode="9" viewFlag="Y"/>  
				</c:when>
			</c:choose>
		</td>
	</tr>
	<tr>
	<td class="label">
			<fmt:message key="tawzjMonth.auditer" />
		</td>
		<td class="content">
			<html:text property="auditer" value="${tawzjMonthForm.auditerName}"styleClass="text medium" readonly="true" /> 
		</td>
	</tr>
</table>
</fmt:bundle>
<html:hidden property="id" value="${tawzjMonthForm.id}" />
<html:hidden property="status" value="0" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>