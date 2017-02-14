<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/feeDetails.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-feedetail">

<content tag="heading">
	<fmt:message key="feeDetail.list.heading" />
</content>

	<display:table name="feeDetailList" cellspacing="0" cellpadding="0"
		id="feeDetailList" pagesize="${pageSize}" class="table feeDetailList"
		export="false"
		requestURI="${app}/feeDetail/feeDetails.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="number" sortable="true"
			headerClass="sortable" titleKey="feeDetail.number" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="price" sortable="true"
			headerClass="sortable" titleKey="feeDetail.price" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="total" sortable="true"
			headerClass="sortable" titleKey="feeDetail.total" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="resultId" sortable="true"
			headerClass="sortable" titleKey="feeDetail.resultId" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="nodeId" sortable="true"
			headerClass="sortable" titleKey="feeDetail.nodeId" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="areaId" sortable="true"
			headerClass="sortable" titleKey="feeDetail.areaId" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="year" sortable="true"
			headerClass="sortable" titleKey="feeDetail.year" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="type" sortable="true"
			headerClass="sortable" titleKey="feeDetail.type" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deleted" sortable="true"
			headerClass="sortable" titleKey="feeDetail.deleted" href="${app}/feeDetail/feeDetails.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="feeDetail" />
		<display:setProperty name="paging.banner.items_name" value="feeDetails" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>