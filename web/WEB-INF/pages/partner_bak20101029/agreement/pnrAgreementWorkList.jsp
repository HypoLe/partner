<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/pnrAgreementWorks.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<content tag="heading">
	<fmt:message key="pnrAgreementWork.list.heading" />
</content>

	<display:table name="pnrAgreementWorkList" cellspacing="0" cellpadding="0"
		id="pnrAgreementWorkList" pagesize="${pageSize}" class="table pnrAgreementWorkList"
		export="false"
		requestURI="${app}/pnrAgreementWork/pnrAgreementWorks.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="agreeId" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementWork.agreeId" href="${app}/pnrAgreementWork/pnrAgreementWorks.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementWork.startTime" href="${app}/pnrAgreementWork/pnrAgreementWorks.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementWork.endTime" href="${app}/pnrAgreementWork/pnrAgreementWorks.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="workContent" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementWork.workContent" href="${app}/pnrAgreementWork/pnrAgreementWorks.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="pnrAgreementWork" />
		<display:setProperty name="paging.banner.items_name" value="pnrAgreementWorks" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>