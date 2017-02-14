<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/tawSystemRoleteases.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-tawsystemroletease">

<content tag="heading">
	<fmt:message key="tawSystemRoletease.list.heading" />
</content>

	<display:table name="tawSystemRoleteaseList" cellspacing="0" cellpadding="0"
		id="tawSystemRoleteaseList" pagesize="${pageSize}" class="table tawSystemRoleteaseList"
		export="false"
		requestURI="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="workflowId" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.workflowId" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="workflowName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.workflowName" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="roleId" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.roleId" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="roleName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.roleName" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="subRole_id" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.subRole_id" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="subRoleName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.subRoleName" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="workTemple" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.workTemple" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="timeLimite" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.timeLimite" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="skillRequire" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.skillRequire" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="checkLine" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.checkLine" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deptId" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.deptId" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deptName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.deptName" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="userId" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.userId" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="userName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.userName" href="${app}/tawSystemRoletease/tawSystemRoleteases.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="tawSystemRoletease" />
		<display:setProperty name="paging.banner.items_name" value="tawSystemRoleteases" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>