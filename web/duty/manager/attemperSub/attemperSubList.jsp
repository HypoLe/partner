<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/attemperSubs.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<content tag="heading">
	<fmt:message key="attemperSub.list.heading" />
</content>

	<display:table name="attemperSubList" cellspacing="0" cellpadding="0"
		id="attemperSubList" pagesize="${pageSize}" class="table attemperSubList"
		export="false"
		requestURI="${app}/attemperSub/attemperSubs.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="attemperId" sortable="true"
			headerClass="sortable" titleKey="attemperSub.attemperId" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deptId" sortable="true"
			headerClass="sortable" titleKey="attemperSub.deptId" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="cruser" sortable="true"
			headerClass="sortable" titleKey="attemperSub.cruser" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="crtime" sortable="true"
			headerClass="sortable" titleKey="attemperSub.crtime" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="intendBeginTime" sortable="true"
			headerClass="sortable" titleKey="attemperSub.intendBeginTime" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="intendEndTime" sortable="true"
			headerClass="sortable" titleKey="attemperSub.intendEndTime" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="persistTimes" sortable="true"
			headerClass="sortable" titleKey="attemperSub.persistTimes" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isAppEffect" sortable="true"
			headerClass="sortable" titleKey="attemperSub.isAppEffect" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="effectOperation" sortable="true"
			headerClass="sortable" titleKey="attemperSub.effectOperation" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="title" sortable="true"
			headerClass="sortable" titleKey="attemperSub.title" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="content" sortable="true"
			headerClass="sortable" titleKey="attemperSub.content" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="attemperSub.remark" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="resultType" sortable="true"
			headerClass="sortable" titleKey="attemperSub.resultType" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="result" sortable="true"
			headerClass="sortable" titleKey="attemperSub.result" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="status" sortable="true"
			headerClass="sortable" titleKey="attemperSub.status" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="holdTime" sortable="true"
			headerClass="sortable" titleKey="attemperSub.holdTime" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="finishUser" sortable="true"
			headerClass="sortable" titleKey="attemperSub.finishUser" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="serial" sortable="true"
			headerClass="sortable" titleKey="attemperSub.serial" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="deleted" sortable="true"
			headerClass="sortable" titleKey="attemperSub.deleted" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="ifContrastReport" sortable="true"
			headerClass="sortable" titleKey="attemperSub.ifContrastReport" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="days" sortable="true"
			headerClass="sortable" titleKey="attemperSub.days" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="netDept" sortable="true"
			headerClass="sortable" titleKey="attemperSub.netDept" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="subSpeciality" sortable="true"
			headerClass="sortable" titleKey="attemperSub.subSpeciality" href="${app}/attemperSub/attemperSubs.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="attemperSub" />
		<display:setProperty name="paging.banner.items_name" value="attemperSubs" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>