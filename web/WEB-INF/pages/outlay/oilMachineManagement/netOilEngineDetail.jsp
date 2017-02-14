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
		<td class="label">地市</td>
		<td class="content" colspan=1>${netOilEngine.city}</td>
		<td class="label">区县</td>
		<td class="content">${netOilEngine.country}</td>	
	</tr>		
	<tr>
		<td class="label">发电基站</td>
		<td class="content">${netOilEngine.station}
		</td>
		<td class="label">驻点名称</td>
		<td class="content">${netOilEngine.residentSiteName}
		</td>		
	</tr>
		<tr>
		<td class="label">告警发生时间</td>
		<td class="content">${netOilEngine.beginTime}
		</td>	
		<td class="label">告警清除时间</td>
		<td class="content">${netOilEngine.endTime}
		</td>
	</tr>
	<!-- 
	<tr>
		<td class="label">油耗</td>
		<td class="content"><input id="oilConsumingCount" name ="oilConsumingCount" type="text" value="${oilConsuming.oilConsumingCount}" readonly="true"/></td>
	</tr>
	<tr>
		<td class="label">单价</td>
		<td class="content"><input id="perPrice" name ="perPrice" type="text" value="${oilConsuming.perPrice}" readonly="true"/></td>
	</tr>
	<tr>
		<td class="label">费用</td>
		<td class="content"><input id="totalFee" name ="totalFee" type="text" value="${oilConsuming.totalFee}" readonly="true"/></td>
	</tr>
	 -->
</table>
<%@ include file="/common/footer_eoms.jsp"%>