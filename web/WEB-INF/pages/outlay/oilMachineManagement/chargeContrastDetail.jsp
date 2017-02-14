<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<style type='text/css'>
  .td {background-color:#00ff00} 
</style>
<script type="text/javascript">
var myJ = $.noConflict();

</script>
<table class="formTable" style="width:100%" >
	<tr >
		<td rowspan="2" align="center" valign="middle" style="width:10%;background:#EDF5FD">地市</td>		
		<td rowspan="2" align="center" valign="middle" style="width:10%;background:#EDF5FD">县区</td>
		<td rowspan="2" align="center" valign="middle" style="width:10%;background:#EDF5FD">驻点名称</td>
		<td rowspan="2" align="center" valign="middle" style="width:10%;background:#EDF5FD">基站名称</td>
		<td colspan="3" align="center" style="width:20%;background:#EDF5FD">代维录入油机发电信息</td>
		<td colspan="3" align="center" style="width:20%;background:#EDF5FD">动环告警信息</td>
		<td colspan="3" align="center" style="width:20%;background:#EDF5FD">能耗信息</td>
	</tr>
	<tr>
	   <td style="background:#EDF5FD">开始时间</td>
	   <td style="background:#EDF5FD">结束时间</td>
	   <td style="background:#EDF5FD">&nbsp;时&nbsp;长</td>
	   <td style="background:#EDF5FD">开始时间</td>
	   <td style="background:#EDF5FD">结束时间</td>
	   <td style="background:#EDF5FD">&nbsp;时&nbsp;长</td>
	   <td style="background:#EDF5FD">历史电量</td>
	   <td style="background:#EDF5FD">发电期间电量</td>
	   <td style="background:#EDF5FD">节省电量</td>
	</tr>  
	<tr>
	  <td align="center">${oilEngine.city}</td>
	  <td align="center">${oilEngine.country}</td>
	  <td align="center">${oilEngine.residentSiteName}</td>
	  <td align="center">${oilEngine.station}</td>
	  <td align="center">${oilEngine.beginTime}</td>
	  <td align="center">${oilEngine.endTime}</td>
	  <td align="center">${subOilTime}</td>
	  <td align="center">${netOilEngine.beginTime}</td>
	  <td align="center">${netOilEngine.endTime}</td>
	  <td align="center">${subNetOilTime}</td>
	  <td align="center">-</td>
	  <td align="center">-</td>
	  <td align="center">-</td>
	</tr>	
</table>
   <table>
   <input type="button" class="btn" value="返回" onclick="javascript:window.history.back();"/>
  
   </table>
<%@ include file="/common/footer_eoms.jsp"%>