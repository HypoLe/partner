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
	myJ("form#mainForm").get(0).action = "ElectricityManagement.do?method=save";
	myJ("form#mainForm").submit();
}
</script>
<form id="mainForm" name="mainForm" action="/ElectricityManagement.do?method=" method="post"> 
<input type="hidden" name="updateId" id="updateId" value="${electricity.id}"/> 
<table class="formTable">
	<tr>
		<td class="label">合作伙伴</td>
		<td>
		<input type="text" id="partnerName_CN" name="partnerName_CN" value="<eoms:id2nameDB id='${electricity.partnerName}' beanId='partnerDeptDao'/>" alt="allowBlank:false"/>
		<input type="hidden" id="partnerName" name ="partnerName" value="${electricity.partnerName}"/></td>
		 <eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
				rootId="" rootText="代维公司树"  valueField="partnerName" handler="partnerName_CN" 
				textField="partnerName_CN" checktype="dept" single="true" />
		</td>
		<td class="label">电费</td>
		<td class="content"><input id="electricCharge" name ="electricCharge" type="text" value="${electricity.electricCharge}"/></td>
		
	</tr>	
	<tr>
		<td class="label">地市</td>
		<td class="content"><input id="areaName" name ="areaName" type="text" value="${electricity.areaName}"/></td>
		<eoms:xbox id="areaNameTree" dataUrl="${app}/xtree.do?method=areaTree"  
		rootId="" rootText="地市"  valueField="" handler="areaName" 
		textField="areaName" checktype="area" single="true" />
		<td class="label">区县</td>
		<td class="content"><input id="country" name ="country" type="text" value="${electricity.country}"/></td
	</tr>
	<tr>
		<td class="label">时间</td>
		<td class="content"><input id="date" name ="date" type="text" value="${electricity.date}" onclick="popUpCalendar(this, this,null,null,null,true,-1);"/></td>
		<td class="label">月度电量</td>
		<td class="content"><input id="electricQuantity" name ="electricQuantity" type="text" value="${electricity.electricQuantity}"/></td>
	</tr>

	<tr>
		<td class="label">备注说明</td>
		<td class="content" colspan=3><textarea rows="4" cols="90" id="notes" name ="notes">${electricity.notes}</textarea></td>
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