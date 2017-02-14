<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<title> 

</title>
<html:form action="/tawRmEmergency.do" method="post" styleId="tawRmEmergencyForm">
	<display:table name="tawRmEmergencyList" cellspacing="0" cellpadding="0" id="tawRmEmergencyList" pagesize="${pageSize}" class="table tawRmEmergencyList"  requestURI="${app}/duty/tawRmEmergency.do?method=editList" sort="list"
		partialList="true" size="resultSize"> 
	 	<display:column property="deptName" sortable="true" headerClass="sortable" titleKey="tawRmEmergency.deptid" paramId="id" paramProperty="id" href="${app}/duty/tawRmEmergency.do?method=getForEdit" />
		<display:column property="userName" sortable="true" headerClass="sortable" titleKey="tawRmEmergency.cruser"  />
		<display:column property="specialty" sortable="true" headerClass="sortable" titleKey="tawRmEmergency.specialty" />
		<display:column property="immobility" sortable="true" headerClass="sortable" titleKey="tawRmEmergency.immobility" />
		<display:column property="other" sortable="true" headerClass="sortable" titleKey="tawRmEmergency.other"  />
   		<display:column  sortable="true" headerClass="sortable"
        url="/duty/tawRmEmergency.do?method=getForEdit" paramId="id" paramProperty="id" titleKey="tawRmEmergency.edit"><bean:message key="tawRmEmergency.edit"/></display:column>
        <display:column  sortable="true" headerClass="sortable"
        url="/duty/tawRmEmergency.do?method=deleted" paramId="id" paramProperty="id" titleKey="label.delete"><bean:message key="label.delete"/></display:column>
		<display:setProperty name="paging.banner.item_name" value="thread" /> 
		<display:setProperty name="paging.banner.items_name" value="threads" />
</display:table>

</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
