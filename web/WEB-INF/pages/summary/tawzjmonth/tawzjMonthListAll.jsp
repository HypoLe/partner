<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/tawzjMonths.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/ApplicationResources-summary">

<content tag="heading">
	<fmt:message key="tawzjMonth.heading.lisst" />
</content>
<html:form action="/tawzjMonths.do?method=edit" method="post" styleId="tawzjMonthForm" enctype="multipart/form-data" onsubmit="return SubmitCheck()">
	<display:table name="tawzjMonthList" cellspacing="0" cellpadding="0" id="tawzjMonthList" pagesize="${pageSize}" class="table tawzjMonthList"
		export="false" requestURI="${app}/summary/tawzjMonths.do?method=search" sort="list" partialList="true"
		 size="resultSize" >	
	
	<display:column property="work" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.work" href="${app}/summary/tawzjMonths.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="specialWork" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.specialWork" href="${app}/summary/tawzjMonths.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="legacy" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.legacy"  paramId="id" paramProperty="id"/>
			
	<display:column property="team" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.team" paramId="id" paramProperty="id"/>

	<display:column property="monthWork" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.planmonthWork"  paramId="id" paramProperty="id"/>

	<display:column property="planSpecialWork" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.planlist" paramId="id" paramProperty="id"/>

	<display:column property="dateTime" sortable="true"
			headerClass="sortable" titleKey="tawzjMonth.dateTime"  paramId="id" paramProperty="id"/>
			
	<display:column property="statusName" sortable="true" paramName="statusName" 
			headerClass="sortable" titleKey="tawzjMonth.status"  paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="tawzjMonth" />
		<display:setProperty name="paging.banner.items_name" value="tawzjMonths" />
	</display:table>

</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>