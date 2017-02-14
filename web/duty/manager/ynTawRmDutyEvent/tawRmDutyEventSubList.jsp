<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	init();
});

function init() {
	window.parent.document.getElementById("subListView<%=request.getParameter("eventid")%>").innerHTML = document.body.innerHTML;		
}
</script>



<table cellpadding="0" class="table TawRmDutyEventSubList" cellspacing="0" id="TawRmDutyEventSubList">
	<thead><tr>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.recordman" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.happentime" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.content" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.worksheetid" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.recordtime" /></th>
	</tr></thead>
<logic:iterate id="TawRmDutyEventSubList" name="TawRmDutyEventSubList" type="com.boco.eoms.duty.model.TawRmDutyEventSub">
	<tr class="tr_show">
		<td><eoms:id2nameDB id="${TawRmDutyEventSubList.recordman}" beanId="tawSystemUserDao" /></td>
		<td><bean:write name="TawRmDutyEventSubList" property="happentime" /></td>
		<td><bean:write name="TawRmDutyEventSubList" property="content" /></td>
		<td>
			<a href="#" onclick='return faultsheet("<bean:write name="TawRmDutyEventSubList" property="worksheetid"/>")'>
    		<bean:write name="TawRmDutyEventSubList" property="worksheetid"/>
       	</a>
		</td>
		<td><bean:write name="TawRmDutyEventSubList" property="recordtime" /></td>
	</tr>
</logic:iterate>
</table>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>