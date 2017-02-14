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
		<td class="label">代维公司</td>
		<td class="content" colspan=1>${oilEngine.monitorCompany}</td>
		<td class="label">地市</td>
		<td class="content">${oilEngine.city}</td>	
	</tr>	
	<tr>
	  <td class="label">县区</td>
		<td class="content">${oilEngine.country}
	  </td>
		<td class="label">油机费用填写时间</td>
		<td class="content">
		  ${oilEngine.recordTime}
		</td>	

	</tr>
	<tr>
		<td class="label">油机开始使用时间</td>
		<td class="content">${oilEngine.beginTime}
		</td>	
		<td class="label">油机使用结束时间</td>
		<td class="content">${oilEngine.endTime}
		</td>
	</tr>	
	<tr>
		<td class="label">发电基站</td>
		<td class="content" >${oilEngine.station}
		</td>
		<td class="label">驻点名称</td>
		<td class="content">${oilEngine.residentSiteName}
		</td>			
	
	
</table>
   <table>
   <input type="button" class="btn" value="返回" onclick="javascript:window.history.back();"/>
  
   </table>
<%@ include file="/common/footer_eoms.jsp"%>