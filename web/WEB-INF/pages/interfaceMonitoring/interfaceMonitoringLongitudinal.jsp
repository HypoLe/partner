<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style>
#tabs {
	width:90%;
}
#tabs .x-tabs-item-body {
	display:none;
	padding:10px;
}
</style>
<script type="text/javascript">
Ext.onReady(function(){
	var tabs = new Ext.TabPanel('tabs');
    var formTab = tabs.addTab('form', "<bean:message key='interfaceMonitoring.Horizontal'/>");
    var infoTab = tabs.addTab('info', "<bean:message key='interfaceMonitoring.Lzontal'/>");
    formTab.on('activate',function(){
    	//location.href = "/interfaceMonitoringLog.do?method=newInterfaceMonitoring&id=ssssss"
    });
    infoTab.on('activate',function(){
    	//location.href = "/interfaceMonitoringLog.do?method=newInterfaceMonitoring&id=ss"
    });
    tabs.activate('info');	
});
</script>

<content tag="heading">
<bean:message key="interfaceMonitoring.text"/></content>



<!-- <c:out value="${buttons}" escapeXml="false"/> -->
<div id="tabs">

<div id="form" class="tab-content">
<html:form action="/interfaceMonitoringLog.do?method=horizontalInterfaceMonitoring">
<table border="0" width="100%" cellspacing="1">
     
	<tr class="tr_show">
		<td class="clsfth"><bean:message key='interfaceMonitoring.Provider'/></td>
		<td>
	      <eoms:comboBox name="provider" id="a0"  styleClass="border" initDicId="1025"/>
	   </td>
	   	<td class="clsfth"><bean:message key='interfaceMonitoring.CallingSide'/></td>
		
		<td>
		 <eoms:comboBox name="callingSide" id="a0"  styleClass="border" initDicId="1025"/>
		
		 </td>
    </tr>
  <tr class="tr_show">
		<td class="clsfth"><bean:message key='interfaceMonitoring.InterfaceType'/></td>
		<td>
		 <eoms:comboBox name="interFaceType" id="a1"  styleClass="border" initDicId="1027"/>
		
	   </td>
	      <td class="clsfth"><bean:message key='interfaceMonitoring.Services'/></td>
	<td>
    <html:text property="interFaceMethod"/>
  </td>
 </tr>
     <tr class="tr_show">
  <td class="clsfth"><bean:message key='interfaceMonitoring.STARTTIME'/></td>
		<td>
		
		<input type="text" name="startTime" size="30" readonly="true" 
					class="text" onclick="popUpCalendar(this, this);">
		
	   </td>
		<td class="clsfth">
		
		<bean:message key='interfaceMonitoring.ENDTIME'/></td>
		<td >
			
		<input type="text" name="endTime" size="30" readonly="true" 
					class="text" onclick="popUpCalendar(this, this);">
		
		 
	   </td>
	
 </tr>
   <tr class="tr_show">

	      <td class="clsfth"><bean:message key='interfaceMonitoring.Success'/></td>
	<td>
   <eoms:comboBox name="success" id="a6"  styleClass="border" initDicId="10307"/>
  </td>
 </tr>



</table>
<table border="0" width="100%" cellspacing="0">
  <tr>
    <td width="100%" height="32" align="right">
                    <html:submit property="strutsButton" styleClass="clsbtn2">
                    <bean:message key='interfaceMonitoring.Enquiries'/>
                    </html:submit>
                    &nbsp;&nbsp;</td>
  </tr>
  </table>
</html:form>
<bean:message key='interfaceMonitoring.HorizontalList'/>
<fmt:bundle basename="com/boco/eoms/commons/interfaceMonitoring/config/ApplicationResources-commons-InterfaceMonitoring">
<display:table name="InterfaceMonitoringList" cellspacing="0" cellpadding="0"
    id="InterfaceMonitoringList" pagesize="${pageSize}" class="table InterfaceMonitoringList"
    export="true" requestURI="/interfaceMonitoringLog.do?method=newInterfaceMonitoring" sort="list">

    <display:column property="provider" sortable="true" headerClass="sortable"
      
         titleKey="interfaceMonitoring.Provider"/>
     
      <display:column property="callingSide" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.CallingSide"/>
     
      <display:column property="interFaceType" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.InterFaceType"/>
         
          <display:column property="interFaceMethod" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.InterfaceType"/>
          <display:column property="successName" sortable="true" headerClass="sortable"       
         titleKey="interfaceMonitoring.Success"/>
          <display:column property="callingTime" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.CallungTime"/>
         <display:column property="view" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.Log" paramId="id" paramProperty="id" href="${app}/interfaceMonitoring/interfaceMonitoringLog.do?method=detail"/>
         
         
         
     <display:setProperty name="paging.banner.item_name" value="forums" />
		<display:setProperty name="paging.banner.items_name" value="forumss" />
    
</display:table>

</fmt:bundle>
</div>
</div>
  <div id="info">
  <table border="0" width="95%" cellspacing="1">
  <html:form action="/interfaceMonitoringLog.do?method=longitudinalMonitoring">
<table border="0" width="100%" cellspacing="1">
     
	<tr class="tr_show">
		<td class="clsfth"><bean:message key='interfaceMonitoring.Provider'/></td>
		<td>
	      <eoms:comboBox name="providerName" id="a0"  styleClass="border" initDicId="1026"/>
	   </td>
	   	<td class="clsfth"><bean:message key='interfaceMonitoring.CallingSide'/></td>
		
		<td>
		 <eoms:comboBox name="callingSideName" id="a0"  styleClass="border" initDicId="1026"/>
		
		 </td>
    </tr>
  <tr class="tr_show">
		<td class="clsfth"><bean:message key='interfaceMonitoring.InterfaceType'/></td>
		<td>
		 <eoms:comboBox name="interFaceType" id="a1"  styleClass="border" initDicId="1027"/>
		
	   </td>
	      <td class="clsfth"><bean:message key='interfaceMonitoring.Services'/></td>
	<td>
    <html:text property="interFaceMethod"/>
  </td>
 </tr>
     <tr class="tr_show">
  <td class="clsfth"><bean:message key='interfaceMonitoring.STARTTIME'/></td>
		<td>
		
		<input type="text" name="startTime" size="30" readonly="true" 
					class="text" onclick="popUpCalendar(this, this);">
		
	   </td>
		<td class="clsfth">
		
		<bean:message key='interfaceMonitoring.ENDTIME'/></td>
		<td >
			
		<input type="text" name="endTime" size="30" readonly="true" 
					class="text" onclick="popUpCalendar(this, this);">
		
		 
	   </td>
	
 </tr>
   <tr class="tr_show">

	      <td class="clsfth"><bean:message key='interfaceMonitoring.Success'/></td>
	<td>
   <eoms:comboBox name="success" id="a6"  styleClass="border" initDicId="10307"/>
  </td>
 </tr>



</table>
<table border="0" width="100%" cellspacing="0">
  <tr>
    <td width="100%" height="32" align="right">
                    <html:submit property="strutsButton" styleClass="clsbtn2">
                    <bean:message key='interfaceMonitoring.Enquiries'/>
                    </html:submit>
                    &nbsp;&nbsp;</td>
  </tr>
  </table>
</html:form>
<bean:message key='interfaceMonitoring.LongitudinalList'/>
<fmt:bundle basename="com/boco/eoms/commons/interfaceMonitoring/config/ApplicationResources-commons-InterfaceMonitoring">
<display:table name="LongitudinalList" cellspacing="0" cellpadding="0"
    id="LongitudinalList" pagesize="${pageSize}" class="table InterfaceMonitoringList"
    export="true" requestURI="/interfaceMonitoringLog.do?method=newInterfaceMonitoring" sort="list">

     <display:column property="provider" sortable="true" headerClass="sortable"
      
         titleKey="interfaceMonitoring.Provider"/>
     
      <display:column property="callingSide" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.CallingSide"/>
     
      <display:column property="interFaceType" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.InterFaceType"/>
         
          <display:column property="interFaceMethod" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.InterfaceType"/>
          <display:column property="successName" sortable="true" headerClass="sortable"       
         titleKey="interfaceMonitoring.Success"/>
          <display:column property="callingTime" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.CallungTime"/>
         <display:column property="view" sortable="true" headerClass="sortable"
       
         titleKey="interfaceMonitoring.Log" paramId="id" paramProperty="id" href="${app}/interfaceMonitoring/interfaceMonitoringLog.do?method=detail"/>
         
         
         
     <display:setProperty name="paging.banner.item_name" value="forums" />
		<display:setProperty name="paging.banner.items_name" value="forumss" />
    
</display:table>

</fmt:bundle>
  </div>
</div>
<c:out value="${buttons}" escapeXml="false"/>

<%@ include file="/common/footer_eoms.jsp"%>