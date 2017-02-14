<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/ctTableThemes.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">>

<content tag="heading">
	<fmt:message key="ctTableTheme.list.heading" />
</content>

	<display:table name="ctTableThemeList" cellspacing="0" cellpadding="0"
		id="ctTableThemeList" pagesize="${pageSize}" class="table ctTableThemeList"
		export="false"
		requestURI="${app}/ctTableTheme/ctTableThemes.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="id" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.id" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createUser" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.createUser" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createDept" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.createDept" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createTime" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.createTime" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="themeName" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.themeName" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="parentId" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.parentId" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isLeaf" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.isLeaf" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isOpen" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.isOpen" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isDeleted" sortable="true"
			headerClass="sortable" titleKey="ctTableTheme.isDeleted" href="${app}/ctTableTheme/ctTableThemes.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="ctTableTheme" />
		<display:setProperty name="paging.banner.items_name" value="ctTableThemes" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>