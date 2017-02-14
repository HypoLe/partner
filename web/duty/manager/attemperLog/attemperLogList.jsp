<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/attemperLogs.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<content tag="heading">
	<fmt:message key="attemperLog.list.heading" />
</content>

	<display:table name="attemperLogList" cellspacing="0" cellpadding="0"
		id="attemperLogList" pagesize="${pageSize}" class="table attemperLogList"
		export="false"
		requestURI="${app}/attemperLog/attemperLogs.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="operTime" sortable="true"
			headerClass="sortable" titleKey="attemperLog.operTime" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="userId" sortable="true"
			headerClass="sortable" titleKey="attemperLog.userId" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="userName" sortable="true"
			headerClass="sortable" titleKey="attemperLog.userName" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deptId" sortable="true"
			headerClass="sortable" titleKey="attemperLog.deptId" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deptName" sortable="true"
			headerClass="sortable" titleKey="attemperLog.deptName" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="type" sortable="true"
			headerClass="sortable" titleKey="attemperLog.type" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="operName" sortable="true"
			headerClass="sortable" titleKey="attemperLog.operName" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="attemperId" sortable="true"
			headerClass="sortable" titleKey="attemperLog.attemperId" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="subAttemperId" sortable="true"
			headerClass="sortable" titleKey="attemperLog.subAttemperId" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="title" sortable="true"
			headerClass="sortable" titleKey="attemperLog.title" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="intendBeginTime" sortable="true"
			headerClass="sortable" titleKey="attemperLog.intendBeginTime" href="${app}/attemperLog/attemperLogs.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="attemperLog" />
		<display:setProperty name="paging.banner.items_name" value="attemperLogs" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>