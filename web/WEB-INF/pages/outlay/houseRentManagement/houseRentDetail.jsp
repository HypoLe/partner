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
		<td class="label">合作伙伴</td>
		<td class="content"><input id="partnerName" name ="partnerName" type="text" value="<eoms:id2nameDB id='${houseRent.partnerName}' beanId='partnerDeptDao'/>" readonly="true"/></td>
		<td class="label">基站</td>
		<td class="content"><input id="stationName" name ="stationName" type="text" value="${houseRent.stationName}" readonly="true"/></td>
		
	</tr>	
	<tr>
		<td class="label">地市</td>
		<td class="content"><input id="areaName" name ="areaName" type="text" value="${houseRent.areaName}" readonly="true"/></td>
		<td class="label">区县</td>
		<td class="content"><input id="country" name ="country" type="text" value="${houseRent.country}" readonly="true"/></td
	</tr>
	<tr>
		<td class="label">时间</td>
		<td class="content"><input id="date" name ="date" type="text" value="${houseRent.date}" readonly="true"/></td>
		<td class="label">房租费</td>
		<td class="content"><input id="houseRentFee" name ="houseRentFee" type="text" value="${houseRent.houseRentFee}" readonly="true"/></td>
	</tr>

	<tr>
		<td class="label">备注说明</td>
		<td class="content" colspan=3><textarea rows="4" cols="90" id="notes" name ="notes" readonly="true">${houseRent.notes}</textarea></td>
	</tr>
</table>
<%@ include file="/common/footer_eoms.jsp"%>