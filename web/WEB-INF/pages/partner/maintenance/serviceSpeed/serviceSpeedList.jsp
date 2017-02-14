<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script language=javascript>
<!-- 
function newAdd(){

    window.location.href=eoms.appPath+'/partner/maintenance/serviceSpeeds.do?method=add';

}	
//-->
</script>
<c:set var="buttons">
 <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
	
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<content tag="heading">
	<fmt:message key="serviceSpeed.list.heading" />
</content>

	<display:table name="serviceSpeedList" cellspacing="0" cellpadding="0"
		id="serviceSpeedList" pagesize="${pageSize}" class="table serviceSpeedList"
		export="false"
		requestURI="${app}/partner/maintenance/serviceSpeeds.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="reportTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="serviceSpeed.reportTime"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.reportUser"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${serviceSpeedList.reportUser}" beanId="tawSystemUserDao" />
	</display:column>
	<display:column property="month" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.month"  paramId="id" paramProperty="id"/>

	<display:column property="region" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.region"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="serviceSpeed.region">
		<eoms:id2nameDB id="${serviceSpeedList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<display:column property="grid" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.grid"  paramId="id" paramProperty="id"/>

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.provider"  paramId="id" paramProperty="id"/>

	<display:column property="webfailure" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.webfailure"  paramId="id" paramProperty="id"/>

	<display:column property="customerComplaints" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.customerComplaints"  paramId="id" paramProperty="id"/>

	<display:column property="toService" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.toService"  paramId="id" paramProperty="id"/>

	<display:column property="fromReport" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.fromReport"  paramId="id" paramProperty="id"/>

	<display:column property="fromPrecision" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.fromPrecision"  paramId="id" paramProperty="id"/>

	<display:column property="datumUpdate" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.datumUpdate"  paramId="id" paramProperty="id"/>

	<display:column property="commSecurity" sortable="true"
			headerClass="sortable" titleKey="serviceSpeed.commSecurity"  paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="serviceSpeed" />
		<display:setProperty name="paging.banner.items_name" value="serviceSpeeds" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>