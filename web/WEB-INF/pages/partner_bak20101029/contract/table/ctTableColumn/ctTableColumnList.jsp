<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/ctTableColumns.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">


<content tag="heading">
	<fmt:message key="ctTableColumn.list.heading" />
</content>

	<display:table name="ctTableColumnList" cellspacing="0" cellpadding="0"
		id="ctTableColumnList" pagesize="${pageSize}" class="table ctTableColumnList"
		export="false"
		requestURI="${app}/partner/contract/ctTableColumns.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="tableId" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.tableId" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="id" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.id" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createUser" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.createUser" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createDept" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.createDept" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createTime" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.createTime" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="colName" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.colName" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="colChname" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.colChname" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="colType" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.colType" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="colDictType" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.colDictType" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="colDictId" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.colDictId" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="colDefault" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.colDefault" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="colSize" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.colSize" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isNullable" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.isNullable" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isOpen" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.isOpen" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isVisibl" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.isVisibl" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isUnique" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.isUnique" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isIndex" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.isIndex" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isDeleted" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.isDeleted" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="orderBy" sortable="true"
			headerClass="sortable" titleKey="ctTableColumn.orderBy" href="${app}/partner/contract/ctTableColumns.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="ctTableColumn" />
		<display:setProperty name="paging.banner.items_name" value="ctTableColumns" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>