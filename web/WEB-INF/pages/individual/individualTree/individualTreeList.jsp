<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/individualTrees.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-individualtree">

<content tag="heading">
	<fmt:message key="individualTree.list.heading" />
</content>

	<display:table name="individualTreeList" cellspacing="0" cellpadding="0"
		id="individualTreeList" pagesize="${pageSize}" class="table individualTreeList"
		export="false"
		requestURI="${app}/individualTree/individualTrees.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="groupId" sortable="true"
			headerClass="sortable" titleKey="individualTree.groupId" href="${app}/individualTree/individualTrees.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="dept_user_id" sortable="true"
			headerClass="sortable" titleKey="individualTree.dept_user_id" href="${app}/individualTree/individualTrees.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="type" sortable="true"
			headerClass="sortable" titleKey="individualTree.type" href="${app}/individualTree/individualTrees.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="flag1" sortable="true"
			headerClass="sortable" titleKey="individualTree.flag1" href="${app}/individualTree/individualTrees.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isleaf" sortable="true"
			headerClass="sortable" titleKey="individualTree.flag2" href="${app}/individualTree/individualTrees.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="individualTree" />
		<display:setProperty name="paging.banner.items_name" value="individualTrees" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>