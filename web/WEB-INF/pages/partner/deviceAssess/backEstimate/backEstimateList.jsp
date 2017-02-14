<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/backEstimates.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="backEstimate.list.heading" />
</content>

	<display:table name="backEstimateList" cellspacing="0" cellpadding="0"
		id="backEstimateList" pagesize="${pageSize}" class="table backEstimateList"
		export="false"
		requestURI="${app}/partner/deviceAssess/backEstimates.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="facilityFauTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.facilityFauTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="badPlankTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.badPlankTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="bigFaultTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.bigFaultTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="facilityQueTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.facilityQueTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="faultAvgTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.faultAvgTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="operationRenewTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.operationRenewTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="plankRepairTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.plankRepairTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="referServeTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.referServeTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="artServeTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.artServeTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="projectServeTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.projectServeTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="trainServeTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.trainServeTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="specialServeTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.specialServeTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="softwareSucTar" sortable="true"
			headerClass="sortable" titleKey="backEstimate.softwareSucTar" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="facilityFauPoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.facilityFauPoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="badPlankPoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.badPlankPoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="bigFaultPoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.bigFaultPoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="facilityQuePoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.facilityQuePoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="faultAvgPoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.faultAvgPoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="operationRenewPoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.operationRenewPoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="plankRepairPoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.plankRepairPoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="referServePoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.referServePoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="artServePoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.artServePoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="projectServePoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.projectServePoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="trainServePoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.trainServePoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="specialServePoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.specialServePoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="softwareSucPoi" sortable="true"
			headerClass="sortable" titleKey="backEstimate.softwareSucPoi" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="year" sortable="true"
			headerClass="sortable" titleKey="backEstimate.year" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="quarter" sortable="true"
			headerClass="sortable" titleKey="backEstimate.quarter" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="factory" sortable="true"
			headerClass="sortable" titleKey="backEstimate.factory" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="speciality" sortable="true"
			headerClass="sortable" titleKey="backEstimate.speciality" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="equipmentType" sortable="true"
			headerClass="sortable" titleKey="backEstimate.equipmentType" href="${app}/partner/deviceAssess/backEstimate/backEstimates.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="backEstimate" />
		<display:setProperty name="paging.banner.items_name" value="backEstimates" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>