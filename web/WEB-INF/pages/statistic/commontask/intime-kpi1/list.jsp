<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page import="com.boco.eoms.commons.statistic.base.util.StatAttributeUrlLocator"%>

<script type="text/javascript">
Ext.onReady(function(){
	colorRows('list-table');
})
function openSheet(url){
	if(parent.frames['north']){
		parent.frames['north'].location.href = url;
	}
	else{
		location.href = url;
	}
}
</script>
<!-- bean:define id="url" value="stat.do" / -->
<fmt:bundle basename="config/statistic/commontask-config/ApplicationResources-statistic-commontask">
	<display:table name="estList" cellspacing="0" cellpadding="0"
		id="process" pagesize="${pageSize}" class="table estList"
		export="false" requestURI="stat.do"
		sort="list" size="total" partialList="true"
		decorator="com.boco.eoms.commons.statistic.commontask.util.CommontaskStatListDisplayHelper">

	<display:column headerClass="sortable" titleKey="list.no"
	 sortable="true"><%=pageContext.getAttribute("process_rowNum")%></display:column>

	<display:column property="sheetid" sortable="true"
		headerClass="sortable" titleKey="list.sheetid" href="" paramId="sheetid" paramProperty="sheetid" />

	<display:column property="title" sortable="true"
		headerClass="sortable" titleKey="list.title" />

	<display:column property="senddept" sortable="true"
		headerClass="sortable" titleKey="list.senddept" />

	<display:column property="operatedept" sortable="true"
		headerClass="sortable" titleKey="list.operatedept" />		
		
	<display:column property="endtime" sortable="true"
		headerClass="sortable" titleKey="statfile.endtime"
		format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		
	<%--<display:column property="overtimeDept" sortable="true"
		headerClass="sortable" titleKey="list.overtimeDept" />

      
	--%></display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
