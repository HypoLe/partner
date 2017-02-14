<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/individualSpecials.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/applicationResource-individual">

<content tag="heading">
	<fmt:message key="individualSpecial.list.heading" />
</content>

	<display:table name="individualSpecialList" cellspacing="0" cellpadding="0"
		id="individualSpecialList" pagesize="${pageSize}" class="table individualSpecialList"
		export="false"
		requestURI="${app}/individualSpecial/individualSpecials.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="userId" sortable="true"
			headerClass="sortable" titleKey="individualSpecial.userId" href="${app}/individualSpecial/individualSpecials.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="specialName" sortable="true"
			headerClass="sortable" titleKey="individualSpecial.specialName" href="${app}/individualSpecial/individualSpecials.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createDate" sortable="true"
			headerClass="sortable" titleKey="individualSpecial.createDate" href="${app}/individualSpecial/individualSpecials.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isDeleted" sortable="true"
			headerClass="sortable" titleKey="individualSpecial.isDeleted" href="${app}/individualSpecial/individualSpecials.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="individualSpecial.flag1" href="${app}/individualSpecial/individualSpecials.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="isleaf" sortable="true"
			headerClass="sortable" titleKey="individualSpecial.flag2" href="${app}/individualSpecial/individualSpecials.do?method=edit" paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="individualSpecial" />
		<display:setProperty name="paging.banner.items_name" value="individualSpecials" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>