<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/tawSystemRoleDescs.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-tawsystemroledesc">

<content tag="heading">
	<fmt:message key="tawSystemRoleDesc.list.heading" />
</content>

	<display:table name="tawSystemRoleDescList" cellspacing="0" cellpadding="0"
		id="tawSystemRoleDescList" pagesize="${pageSize}" class="table tawSystemRoleDescList"
		export="false"
		requestURI="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="roleId" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.roleId" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="roleName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.roleName" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="dictCode" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.dictCode" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="workflowFlag" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.workflowFlag" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="workflowName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.workflowName" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="templateCode" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.templateCode" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="templateName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.templateName" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="timeLimit" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.timeLimit" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="skill" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.skill" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="kpi" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.kpi" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="orderFlag" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.orderFlag" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="notes" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleDesc.notes" href="${app}/tawSystemRoleDesc/tawSystemRoleDescs.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="tawSystemRoleDesc" />
		<display:setProperty name="paging.banner.items_name" value="tawSystemRoleDescs" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>