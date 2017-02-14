<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.summary.model.TawDutyWeek"%>
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/tawDutyWeeks.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="config/ApplicationResources-summary">

<content tag="heading">
	<fmt:message key="taskplanList.heading" />
</content>

	<display:table name="tawDutyWeekList" cellspacing="0" cellpadding="0"
		id="tawDutyWeekList" pagesize="${pageSize}" class="table tawDutyWeekList"
		export="false"
		requestURI="${app}/tawDutyWeek/tawDutyWeeks.do?method=search"
		sort="list" partialList="true" size="resultSize">
		
        <display:column property="title" 
		headerClass="sortable" titleKey="tawDutyWeek.title"/>
		
		<display:column property="userName" 
			headerClass="sortable" titleKey="tawDutyWeek.userId" />
		
		<display:column property="insertTime" 
			headerClass="sortable" titleKey="tawDutyWeek.insertTime" />
			
		<display:column property="weekFlag" 
			headerClass="sortable" titleKey="tawDutyWeek.weekFlag" />
			
		<display:column 
			headerClass="sortable" titleKey="tawDutyWeek.accessories" >
			<eoms:download ids="${ tawDutyWeekList.accessoriesId }"></eoms:download>
		</display:column>
		<display:column 
			headerClass="sortable" titleKey="tawDutyWeek.gejieAccessories" >
			<eoms:download ids="${ tawDutyWeekList.gejieaccessoriesId }"></eoms:download>
		</display:column>
		<display:column 
			headerClass="sortable" titleKey="tawDutyWeek.yiliuAccessories" >
			<eoms:download ids="${ tawDutyWeekList.yiliuaccessoriesId }"></eoms:download>
		</display:column>
		<display:column 
			headerClass="sortable" titleKey="tawDutyWeek.olpAccessories" >
			<eoms:download ids="${ tawDutyWeekList.olpaccessoriesId }"></eoms:download>
		</display:column>

		<display:setProperty name="paging.banner.item_name" value="tawDutyWeek" />
		<display:setProperty name="paging.banner.items_name" value="tawDutyWeeks" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>