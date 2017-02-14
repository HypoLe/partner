<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/assCollectionTypes.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-asscollectiontype">

<content tag="heading">
	<fmt:message key="assCollectionType.list.heading" />
</content>

	<display:table name="assCollectionTypeList" cellspacing="0" cellpadding="0"
		id="assCollectionTypeList" pagesize="${pageSize}" class="table assCollectionTypeList"
		export="false"
		requestURI="${app}/assCollectionType/assCollectionTypes.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="name" sortable="true"
			headerClass="sortable" titleKey="assCollectionType.name" href="${app}/assCollectionType/assCollectionTypes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="serviceAddr" sortable="true"
			headerClass="sortable" titleKey="assCollectionType.serviceAddr" href="${app}/assCollectionType/assCollectionTypes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="treeNodeId" sortable="true"
			headerClass="sortable" titleKey="assCollectionType.treeNodeId" href="${app}/assCollectionType/assCollectionTypes.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="assCollectionType" />
		<display:setProperty name="paging.banner.items_name" value="assCollectionTypes" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>