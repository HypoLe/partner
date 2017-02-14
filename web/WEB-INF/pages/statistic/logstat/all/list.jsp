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

<fmt:bundle basename="config/statistic/logstat-config/ApplicationResources-statistic-logstat">
	<display:table name="estList" cellspacing="0" cellpadding="0"
		id="process" pagesize="${pageSize}" class="table estList"
		export="false" requestURI="stat.do"
		sort="list" size="total" partialList="true">

	<display:column headerClass="sortable" titleKey="list.no"
	 sortable="true"><%=pageContext.getAttribute("process_rowNum")%></display:column>

	<display:column property="opername" sortable="true"
		headerClass="sortable" titleKey="list.opername" />

	<display:column property="modelname" sortable="true"
		headerClass="sortable" titleKey="list.modelname" />

	<display:column property="username" sortable="true"
		headerClass="sortable" titleKey="list.username" />

	<display:column property="beginnotetime" sortable="true"
		headerClass="sortable" titleKey="list.beginnotetime" />

	
		
</display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
