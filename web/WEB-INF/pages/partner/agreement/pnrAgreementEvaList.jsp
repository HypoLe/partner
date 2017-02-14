<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/pnrAgreementEvas.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<content tag="heading">
	<fmt:message key="pnrAgreementEva.list.heading" />
</content>

	<display:table name="pnrAgreementEvaList" cellspacing="0" cellpadding="0"
		id="pnrAgreementEvaList" pagesize="${pageSize}" class="table pnrAgreementEvaList"
		export="false"
		requestURI="${app}/pnrAgreementEva/pnrAgreementEvas.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="agreeId" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementEva.agreeId" href="${app}/pnrAgreementEva/pnrAgreementEvas.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="evaContent" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementEva.evaContent" href="${app}/pnrAgreementEva/pnrAgreementEvas.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="pnrAgreementEva" />
		<display:setProperty name="paging.banner.items_name" value="pnrAgreementEvas" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>