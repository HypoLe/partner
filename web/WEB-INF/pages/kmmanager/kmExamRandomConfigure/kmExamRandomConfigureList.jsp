<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/kmExamRandomConfigures.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<content tag="heading">
	<fmt:message key="kmExamRandomConfigure.list.heading" />
</content>

	<display:table name="kmExamRandomConfigureList" cellspacing="0" cellpadding="0"
		id="kmExamRandomConfigureList" pagesize="${pageSize}" class="table kmExamRandomConfigureList"
		export="false"
		requestURI="${app}/kmExamRandomConfigure/kmExamRandomConfigures.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="randomTestId" sortable="true"
			headerClass="sortable" titleKey="kmExamRandomConfigure.randomTestId" href="${app}/kmExamRandomConfigure/kmExamRandomConfigures.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="testType" sortable="true"
			headerClass="sortable" titleKey="kmExamRandomConfigure.testType" href="${app}/kmExamRandomConfigure/kmExamRandomConfigures.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="number" sortable="true"
			headerClass="sortable" titleKey="kmExamRandomConfigure.number" href="${app}/kmExamRandomConfigure/kmExamRandomConfigures.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="kmExamRandomConfigure" />
		<display:setProperty name="paging.banner.items_name" value="kmExamRandomConfigures" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>