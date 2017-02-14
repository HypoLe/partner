<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
var myJ = $.noConflict();

</script>
<table class="formTable">
	<tr>
		<td class="label">详细信息</td>
		<td class="label">代维公司录入信息</td>
		<td class="label">动环告警录入信息</td>	
	</tr>	
	<tr>
		<td class="label">告警标题</td>
		<td class="content">${oilEngine.title}</td>
		<td class="content">
		<a href="/eomsMain/outlay/NetOilEngineManagement.do?method=detail&id=${netOilEngine.id}">
		${netOilEngine.title}</a></td>	
	</tr>	
	<tr>
		<td class="label">油机开始使用时间</td>
		<td class="content">${oilEngine.startTime}</td>
		<td class="content">${netOilEngine.starttime}</td>	
	</tr>
	<tr>
		<td class="label">油机使用结束时间</td>
		<td class="content">${oilEngine.endTime}</td>
		<td class="content">${netOilEngine.endtime}</td>	
	</tr>	
	<tr>
		<td class="label">发电基站名称</td>
		<td class="content">${oilEngine.powerStations}</td>
		<td class="content">${netOilEngine.power_stations}</td>	
	</tr>
	<tr>
		<td class="label">油机名称</td>
		<td class="content">${oilEngine.oilEngine}</td>
		<td class="content">${netOilEngine.oil_engine}</td>	
	</tr>	
		


</table>
<%@ include file="/common/footer_eoms.jsp"%>