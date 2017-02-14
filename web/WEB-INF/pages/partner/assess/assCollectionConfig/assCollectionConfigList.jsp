<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/assCollectionConfigs.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-asscollectionconfig">

<content tag="heading">
	<fmt:message key="assCollectionConfig.list.heading" />
</content>

	<display:table name="assCollectionConfigList" cellspacing="0" cellpadding="0"
		id="assCollectionConfigList" pagesize="${pageSize}" class="table assCollectionConfigList"
		export="false"
		requestURI="${app}/assCollectionConfig/assCollectionConfigs.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="name" sortable="true"
			headerClass="sortable" titleKey="assCollectionConfig.name" href="${app}/assCollectionConfig/assCollectionConfigs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="sql" sortable="true"
			headerClass="sortable" titleKey="assCollectionConfig.sql" href="${app}/assCollectionConfig/assCollectionConfigs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="treeNodeId" sortable="true"
			headerClass="sortable" titleKey="assCollectionConfig.treeNodeId" href="${app}/assCollectionConfig/assCollectionConfigs.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="assCollectionConfig" />
		<display:setProperty name="paging.banner.items_name" value="assCollectionConfigs" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>