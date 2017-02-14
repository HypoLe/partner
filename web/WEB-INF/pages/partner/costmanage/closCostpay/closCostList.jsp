<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/closCosts.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/costmanage/config/applicationResource-partner-costmanage">

<content tag="heading">
	<fmt:message key="closCost.list.heading" />
</content>

	<display:table name="closCostList" cellspacing="0" cellpadding="0"
		id="closCostList" pagesize="${pageSize}" class="table closCostList"
		export="false"
		requestURI="${app}/partner/costmanage/closCosts.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="agreementNo" sortable="true"
			headerClass="sortable" titleKey="closCost.agreementNo" href="${app}/partner/costmanage/closCosts.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="workPlanNo" sortable="true"
			headerClass="sortable" titleKey="closCost.workPlanNo" />

	<display:column property="assessmentNo" sortable="true"
			headerClass="sortable" titleKey="closCost.assessmentNo" />

	<display:column property="money" sortable="true"
			headerClass="sortable" titleKey="closCost.money" />

	<display:column property="payPlan" sortable="true"
			headerClass="sortable" titleKey="closCost.payPlan" />

	<display:column property="payMessage" sortable="true"
			headerClass="sortable" titleKey="closCost.payMessage" />

	<display:column property="payfin" sortable="true"
			headerClass="sortable" titleKey="closCost.payfin" />

	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="closCost.remark" />

	<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/costmanage/closCosts.do?method=detail&id=${closCostList.id }'  target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
		<display:setProperty name="paging.banner.item_name" value="closCost" />
		<display:setProperty name="paging.banner.items_name" value="closCosts" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>