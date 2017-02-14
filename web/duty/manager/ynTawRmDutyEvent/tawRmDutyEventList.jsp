<%@ include file="/common/taglibs.jsp"%>
 
<%@ include file="/common/header_eoms_form.jsp"%>
<title> 
</title>
<%--
<logic:notEmpty name="threadForm" property="forumsId">
	<bean:message key="threadForm.forumsId" /> >
<eoms:id2nameDB id="${threadForm.forumsId }" beanId="forumsDao" />
</logic:notEmpty>

--%>
<html:form action="/yndutyevent.do" method="post" styleId="tawRmdutyEventForm">
 
	<display:table name="TawRmDutyEventList" cellspacing="0" cellpadding="0" id="TawRmDutyEventList" pagesize="${pageSize}" class="table TawRmDutyEventList"  requestURI="${app}/duty/yndutyevent.do?method=search" sort="list"
		partialList="true" size="resultSize" decorator="com.boco.eoms.duty.displaytag.support.DutyEventDisplaytagDecorator" >
	 	<display:column property="inputuser" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputuser" paramId="id" paramProperty="id" href="${app}/duty/yndutyevent.do?method=getEvent" />
		 
		<display:column property="inputdate" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputdate" />
		<display:column property="flag" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.flag" />

		<display:column property="eventtitle" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.eventtitle"  />
		<display:column property="beginTime" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.beginTime"  />
  
		<display:column property="complateFlag" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.complateFlag"  />
   		<display:column  sortable="true" headerClass="sortable"
        url="/duty/yndutyevent.do?method=getEvent" paramId="id" paramProperty="id" titleKey="tawRmDutyEvent.read"><bean:message key="tawRmDutyEvent.look"/></display:column>
  

		<display:setProperty name="paging.banner.item_name" value="thread" /> 
		<display:setProperty name="paging.banner.items_name" value="threads" />
	</display:table>
 

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
