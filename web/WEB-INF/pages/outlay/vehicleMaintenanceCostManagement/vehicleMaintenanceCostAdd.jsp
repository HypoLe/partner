<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
var myJ = $.noConflict();

function goAdd(){
	myJ("form#mainForm").get(0).action = "VehicleMaintenanceCostManagement.do?method=save";
	myJ("form#mainForm").submit();
}
</script>
<form id="mainForm" name="mainForm" action="/VehicleMaintenanceCostManagement.do?method=" method="post"> 
<input type="hidden" name="updateId" id="updateId" value="${vehicleMaintenanceCost.id}"/> 
<table class="formTable">
	<tr>
		<td class="label">合作伙伴</td>
		<td class="content" colspan=3>
		<input type="text" id="partnerName_CN" name="partnerName_CN" value="<eoms:id2nameDB id='${vehicleMaintenanceCost.partnerName}' beanId='partnerDeptDao'/>"  alt="allowBlank:false"/>
		<input type="hidden" id="partnerName" name ="partnerName" value="${vehicleMaintenanceCost.partnerName}"/></td>
		 <eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
				rootId="" rootText="代维公司树"  valueField="partnerName" handler="partnerName_CN" 
				textField="partnerName_CN" checktype="dept" single="true" />
	</tr>

	<tr>
		<td class="label">车辆信息</td>
		<td class="content"><input id="vehicleDetail" name ="vehicleDetail" type="text" value="${vehicleMaintenanceCost.vehicleDetail}"/></td>
	</tr>
		<tr>
		<td class="label">消耗说明</td>
		<td class="content"><input id="costDesc" name ="costDesc" type="text" value="${vehicleMaintenanceCost.costDesc}"/></td>
	</tr>
		<tr>
		<td class="label">费用</td>
		<td class="content"><input id="totalFee" name ="totalFee" type="text" value="${vehicleMaintenanceCost.totalFee}"/></td>
	</tr>
	<tr>
		<td class="label">消耗说明</td>
		<td class="content" colspan=3><textarea rows="4" cols="90" id="vehicleMaintenanceCostEventDetail" name ="vehicleMaintenanceCostEventDetail">${vehicleMaintenanceCost.vehicleMaintenanceCostEventDetail}</textarea></td>
	</tr>
</table>

		<input type="button" name="add" id="add" value="保存" onclick="goAdd();"/> 
</form>
<script>
	var vlt = new eoms.form.Validation({form:'mainForm'});
	myJ('#partnerName_CN').bind('change',function(event){
		vlt.validate(myJ('#partnerName_CN'));
	});
</script>
<%@ include file="/common/footer_eoms.jsp"%>