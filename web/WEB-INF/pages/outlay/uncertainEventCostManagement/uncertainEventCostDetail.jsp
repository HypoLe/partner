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
		<td class="content" colspan=3><input id="partnerName" name ="partnerName" type="text" value="<eoms:id2nameDB id='${uncertainEventCost.partnerName}' beanId='partnerDeptDao'/>" readonly="true"/></td>
	</tr>	
	<tr>
		<td class="label">车辆信息</td>
		<td class="content"><input id="consumingDetail" name ="consumingDetail" type="text" value="${uncertainEventCost.consumingDetail}" readonly="true"/></td>
	</tr>
	<tr>
	<td class="label">费用</td>
		<td class="content"><input id="totalFee" name ="totalFee" type="text" value="${uncertainEventCost.totalFee}" readonly="true"/></td>
	</tr>
	<tr>
		<td class="label">备注</td>
		<td class="content" colspan=3><textarea rows="4" cols="90" id="notes" name ="notes" readonly="true">${uncertainEventCost.notes}</textarea></td>
	</tr>
</table>
<%@ include file="/common/footer_eoms.jsp"%>