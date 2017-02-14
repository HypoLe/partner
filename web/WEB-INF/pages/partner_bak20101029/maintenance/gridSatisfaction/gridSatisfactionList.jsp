<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
  function newAdd(){

    window.location.href=eoms.appPath+'/partner/maintenance/gridSatisfactions.do?method=add';

}
</script>
<c:set var="buttons">

	   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
		
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<content tag="heading">
	<fmt:message key="gridSatisfaction.list.heading" />
</content>

	<display:table name="gridSatisfactionList" cellspacing="0" cellpadding="0"
		id="gridSatisfactionList" pagesize="${pageSize}" class="table gridSatisfactionList"
		export="false"
		requestURI="${app}/partner/maintenance/gridSatisfactions.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="reportTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="gridSatisfaction.reportTime"  paramId="id" paramProperty="id"/>

	<display:column sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.reportUser"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${gridSatisfactionList.reportUser}" beanId="tawSystemUserDao" />
	</display:column>
	<display:column property="month" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.month"  paramId="id" paramProperty="id"/>

			
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="gridSatisfaction.region">
		<eoms:id2nameDB id="${gridSatisfaction.region}" beanId="tawSystemAreaDao" />
	</display:column>			
			

	<display:column property="grid" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.grid"  paramId="id" paramProperty="id"/>

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.provider"  paramId="id" paramProperty="id"/>

	<display:column property="synRating" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.synRating"  paramId="id" paramProperty="id"/>

	<display:column property="tieMaintenance" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.tieMaintenance"  paramId="id" paramProperty="id"/>

	<display:column property="faultDispose" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.faultDispose"  paramId="id" paramProperty="id"/>

	<display:column property="phonicsQuality" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.phonicsQuality"  paramId="id" paramProperty="id"/>

	<display:column property="businessLobby" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.businessLobby"  paramId="id" paramProperty="id"/>

	<display:column property="customerComplaints" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.customerComplaints"  paramId="id" paramProperty="id"/>

	<display:column property="valueCustomer" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.valueCustomer"  paramId="id" paramProperty="id"/>

	<display:column property="corporateCustomer" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.corporateCustomer"  paramId="id" paramProperty="id"/>

	<display:column property="companyAct" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.companyAct"  paramId="id" paramProperty="id"/>

	<display:column property="personnelStatus" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.personnelStatus"  paramId="id" paramProperty="id"/>

	<display:column property="instrumentStatus" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.instrumentStatus"  paramId="id" paramProperty="id"/>

	<display:column property="managementAbility" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.managementAbility"  paramId="id" paramProperty="id"/>


		<display:setProperty name="paging.banner.item_name" value="gridSatisfaction" />
		<display:setProperty name="paging.banner.items_name" value="gridSatisfactions" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>