<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/ctTableDicts.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<content tag="heading">
	<fmt:message key="ctTableDict.list.heading" />
</content>

	<display:table name="ctTableDictList" cellspacing="0" cellpadding="0"
		id="ctTableDictList" pagesize="${pageSize}" class="table ctTableDictList"
		export="false"
		requestURI="${app}/partner/contract/ctTableDicts.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="id" sortable="true"
			headerClass="sortable" titleKey="ctTableDict.id" href="${app}/partner/contract/ctTableDicts.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="dictName" sortable="true"
			headerClass="sortable" titleKey="ctTableDict.dictName" href="${app}/partner/contract/ctTableDicts.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="parentId" sortable="true"
			headerClass="sortable" titleKey="ctTableDict.parentId" href="${app}/partner/contract/ctTableDicts.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isDeleted" sortable="true"
			headerClass="sortable" titleKey="ctTableDict.isDeleted" href="${app}/partner/contract/ctTableDicts.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="ctTableDict" />
		<display:setProperty name="paging.banner.items_name" value="ctTableDicts" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>