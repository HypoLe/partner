<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/eventReads.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-eventread">

<content tag="heading">
	<fmt:message key="eventRead.list.heading" />
</content>

	<display:table name="eventReadList" cellspacing="0" cellpadding="0"
		id="eventReadList" pagesize="${pageSize}" class="table eventReadList"
		export="false"
		requestURI="${app}/eventRead/eventReads.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="eventid" sortable="true"
			headerClass="sortable" titleKey="eventRead.eventid" href="${app}/eventRead/eventReads.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="userid" sortable="true"
			headerClass="sortable" titleKey="eventRead.userid" href="${app}/eventRead/eventReads.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="readtime" sortable="true"
			headerClass="sortable" titleKey="eventRead.readtime" href="${app}/eventRead/eventReads.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deptid" sortable="true"
			headerClass="sortable" titleKey="eventRead.deptid" href="${app}/eventRead/eventReads.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="readaffirm" sortable="true"
			headerClass="sortable" titleKey="eventRead.readaffirm" href="${app}/eventRead/eventReads.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="eventRead" />
		<display:setProperty name="paging.banner.items_name" value="eventReads" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>