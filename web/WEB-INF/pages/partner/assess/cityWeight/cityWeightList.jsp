<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/cityWeights.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-cityweight">

<content tag="heading">
	<fmt:message key="cityWeight.list.heading" />
</content>

	<display:table name="cityWeightList" cellspacing="0" cellpadding="0"
		id="cityWeightList" pagesize="${pageSize}" class="table cityWeightList"
		export="false"
		requestURI="${app}/cityWeight/cityWeights.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="weight" sortable="true"
			headerClass="sortable" titleKey="cityWeight.weight" href="${app}/cityWeight/cityWeights.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="templateId" sortable="true"
			headerClass="sortable" titleKey="cityWeight.templateId" href="${app}/cityWeight/cityWeights.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="city" sortable="true"
			headerClass="sortable" titleKey="cityWeight.city" href="${app}/cityWeight/cityWeights.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="creator" sortable="true"
			headerClass="sortable" titleKey="cityWeight.creator" href="${app}/cityWeight/cityWeights.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createTime" sortable="true"
			headerClass="sortable" titleKey="cityWeight.createTime" href="${app}/cityWeight/cityWeights.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deleted" sortable="true"
			headerClass="sortable" titleKey="cityWeight.deleted" href="${app}/cityWeight/cityWeights.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="cityWeight" />
		<display:setProperty name="paging.banner.items_name" value="cityWeights" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>