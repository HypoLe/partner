<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    	Ext.onReady(function(){	
			
})

    function sendBox() {		
		document.getElementById("checkAndSearchFrom").submit();		
	}
	
	
	
</script>



<form id="checkAndSearchFrom"
	action="OilEngineManagement.do?method=oilEngineStat" method="post">

<fieldset><legend>请输入查询条件</legend>
<table class="formTable">
<tr>
	
		<td class="label">基站名称</td>
		<td><input id="powerStations" type="text" name="powerStations"  class="text" /></td>
		<td class="label">油机</td>
		<td><input id="oilEngine" type="text" name="oilEngine"  class="text" /></td>
			 </tr>
		<tr>
		<td class="label">
			油机开始使用时间范围从：
		</td>	
		<td class="content">
				<input type="text" class="text"
					name="startDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'lessThen',link:'endDate2search',vtext:'开始时间不能晚于结束时间',allowBlank:false"
					id="startDate2search" value="${startDate2search}"/>	
		</td>
		<td class="label">
			到：
		</td>	
		<td class="content">
				<input type="text" class="text"
					name="endDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'moreThen',link:'startDate2search',vtext:'结束时间不能早于开始时间',allowBlank:false"
					id="endDate2search" value="${endDate2search}"/>			
		</td>
		</tr>
</table>
</fieldset>

<input type="button" name="查询" value="查询" onclick="sendBox()" /> <!-- This hidden area is for batch deleting. -->

<form id="mainForm" action="/OilEngineManagement.do?method=gotoContrast" method="post"> 
<logic:notEmpty name="oilEngineContrastList">
<c:set var="myCheckboxAllBtn" scope="page">
	<input type="checkbox" id="myCheckbox" />
</c:set>
<display:table name="oilEngineContrastList" class="table" id="oilEngine" export="true" sort="list"
		partialList="true" size="${size}" requestURI="OilEngineManagement.do">  
<display:column title="代维公司" headerClass="sortable" sortable="true">
${oilEngine.company}
</display:column>
<display:column property="oil_engine" title="油机" headerClass="sortable" sortable="true"/>
<display:column property="startTime" title="油机开始使用时间" headerClass="sortable" sortable="true"/>
<display:column property="endTime" title="油机使用结束时间" headerClass="sortable" sortable="true"/>
<display:column property="operate_time" title="油机费用填写时间" headerClass="sortable" sortable="true"/>
<display:column property="station_name" title="维护站名称" headerClass="sortable" sortable="true"/>
<display:column property="power_stations" title="发电基站" headerClass="sortable" sortable="true"/>
<display:column sortable="true" headerClass="sortable" title="信息对比"
	paramProperty="id" url="/outlay/OilEngineManagement.do?method=oilEngineContrast"
	paramId="id" media="html">
	<img src="${app}/images/icons/search.gif">
</display:column>
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
</display:table>
</logic:notEmpty>
</form>
<%@ include file="/common/footer_eoms.jsp"%>