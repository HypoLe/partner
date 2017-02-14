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
<fmt:bundle basename="config/statistic/circuitdispatch-config/ApplicationResources-statistic-circuitdispatch">
	<display:table name="estList" cellspacing="0" cellpadding="0"
		id="process" pagesize="${pageSize}" class="table estList"
		export="false" requestURI="stat.do"
		sort="list" size="total" partialList="true"
		decorator="com.boco.eoms.commons.statistic.circuitdispatch.util.CircuitdispatchListDisplayHelper">

	<display:column headerClass="sortable" titleKey="list.no"
	 sortable="true"><%=pageContext.getAttribute("process_rowNum")%></display:column>

	<display:column property="title" sortable="true"
		headerClass="sortable" titleKey="list.title" />
		
	<%--<display:column property="sheetid" sortable="true"
		headerClass="sortable" titleKey="list.sheetid" href="<%=StatAttributeUrlLocator.getStatAttributeUrl().getAttributeUrl().getProperty("1")%>" paramId="sheetid" paramProperty="sheetid" />

	--%><display:column property="linkExecuteEndDate" sortable="true"
		headerClass="sortable" titleKey="list.linkExecuteEndDate" />

		<display:column property="endtime" sortable="true"
		headerClass="sortable" titleKey="list.endtime"
		format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<%--<display:column property="operateuserid" sortable="true"
		headerClass="sortable" titleKey="list.operateuserid" />

	--%></display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
