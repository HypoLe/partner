<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/ctContentsDicts.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<content tag="heading">
	<fmt:message key="ctContentsDict.list.heading" />
</content>

	<display:table name="ctContentsDictList" cellspacing="0" cellpadding="0"
		id="ctContentsDictList" pagesize="${pageSize}" class="table ctContentsDictList"
		export="false"
		requestURI="${app}/ctContentsDict/ctContentsDicts.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="contentId" sortable="true"
			headerClass="sortable" titleKey="ctContentsDict.contentId" href="${app}/ctContentsDict/ctContentsDicts.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="dictId" sortable="true"
			headerClass="sortable" titleKey="ctContentsDict.dictId" href="${app}/ctContentsDict/ctContentsDicts.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="dictType" sortable="true"
			headerClass="sortable" titleKey="ctContentsDict.dictType" href="${app}/ctContentsDict/ctContentsDicts.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="ctContentsDict" />
		<display:setProperty name="paging.banner.items_name" value="ctContentsDicts" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>