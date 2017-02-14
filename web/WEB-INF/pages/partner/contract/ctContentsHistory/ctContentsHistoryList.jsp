<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/ctContentsHistorys.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<content tag="heading">
	<fmt:message key="ctContentsHistory.list.heading" />
</content>

	<display:table name="ctContentsHistoryList" cellspacing="0" cellpadding="0"
		id="ctContentsHistoryList" pagesize="${pageSize}" class="table ctContentsHistoryList"
		export="false"
		requestURI="${app}/ctContentsHistory/ctContentsHistorys.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="themeId" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.themeId" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="tableId" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.tableId" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createUser" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.createUser" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createDept" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.createDept" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createTime" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.createTime" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="modifyUser" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.modifyUser" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="modifyDept" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.modifyDept" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="modifyTime" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.modifyTime" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="contentTitle" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.contentTitle" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="contentKeys" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.contentKeys" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="contentStatus" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.contentStatus" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="auditFlag" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.auditFlag" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="rolestrFlag" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.rolestrFlag" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="levelFlag" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.levelFlag" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isBest" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.isBest" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isPublic" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.isPublic" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="gradeOne" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.gradeOne" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="gradeTwo" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.gradeTwo" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="gradeThree" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.gradeThree" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="gradeFour" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.gradeFour" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="gradeFive" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.gradeFive" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="readCount" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.readCount" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="useCount" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.useCount" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="contentXml" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.contentXml" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="modifyCount" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.modifyCount" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="version" sortable="true"
			headerClass="sortable" titleKey="ctContentsHistory.version" href="${app}/ctContentsHistory/ctContentsHistorys.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="ctContentsHistory" />
		<display:setProperty name="paging.banner.items_name" value="ctContentsHistorys" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>