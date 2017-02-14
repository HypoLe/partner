<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/individualGroups.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-individualgroup">

<content tag="heading">
	<fmt:message key="individualGroup.list.heading" />
</content>

	<display:table name="individualGroupList" cellspacing="0" cellpadding="0"
		id="individualGroupList" pagesize="${pageSize}" class="table individualGroupList"
		export="false"
		requestURI="${app}/individualGroup/individualGroups.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="specialId" sortable="true"
			headerClass="sortable" titleKey="individualGroup.specialId" href="${app}/individualGroup/individualGroups.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="groupName" sortable="true"
			headerClass="sortable" titleKey="individualGroup.groupName" href="${app}/individualGroup/individualGroups.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createDate" sortable="true"
			headerClass="sortable" titleKey="individualGroup.createDate" href="${app}/individualGroup/individualGroups.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isDeleted" sortable="true"
			headerClass="sortable" titleKey="individualGroup.isDeleted" href="${app}/individualGroup/individualGroups.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="individualGroup.flag1" href="${app}/individualGroup/individualGroups.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isleaf" sortable="true"
			headerClass="sortable" titleKey="individualGroup.flag2" href="${app}/individualGroup/individualGroups.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="individualGroup" />
		<display:setProperty name="paging.banner.items_name" value="individualGroups" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>