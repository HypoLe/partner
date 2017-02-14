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
		<td class="content" colspan=3><input id="partnerName" name ="partnerName" type="text" value="<eoms:id2nameDB id='${oilConsuming.partnerName}' beanId='partnerDeptDao'/>" readonly="true"/></td>
	</tr>	
	<tr>
		<td class="label">油机信息</td>
		<td class="content"><input id="oilDetial" name ="oilDetial" type="text" value="${oilConsuming.oilDetial}" readonly="true"/></td>
		<td class="label">单价</td>
		<td class="content"><input id="perPrice" name ="perPrice" type="text" value="${oilConsuming.perPrice}" readonly="true"/></td>
	</tr>
	<tr>
		<td class="label">油耗</td>
		<td class="content"><input id="oilConsumingCount" name ="oilConsumingCount" type="text" value="${oilConsuming.oilConsumingCount}" readonly="true"/></td>
		<td class="label">费用</td>
		<td class="content"><input id="totalFee" name ="totalFee" type="text" value="${oilConsuming.totalFee}" readonly="true"/></td>
	</tr>
	<tr>
		<td class="label">油耗事件说明</td>
		<td class="content" colspan=3><textarea rows="4" cols="90" id="oilConsumingEventDetail" name ="oilConsumingEventDetail" readonly="true">${oilConsuming.oilConsumingEventDetail}</textarea></td>
	</tr>
</table>
<%@ include file="/common/footer_eoms.jsp"%>