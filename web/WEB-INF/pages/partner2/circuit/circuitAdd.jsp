<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
		v = new eoms.form.Validation({form:'circuitForm'});}
		);

function checkFloat(obj){
	var x=parseFloat(obj.value);
	if(!x){
		alert("请输入整数或者小数");
		$('cableLength').value="";
	}
}

function addRecord(){
	if(confirm("确认新增吗？")){
		var myBeginTime=$('faultBeginTime');
		if(myBeginTime){
			$('yearFlag').value=$('faultBeginTime').value.substring(0,4);
			$('dayFlag').value=$('faultBeginTime').value.substring(8,10);
			document.forms[0].submit();
		}
	}else{
		return false;
	}
}
</script>

<content tag="heading">
<c:out value="线路故障新增页面" />
</content>
<br />
<br />

<c:if test="${duplicate == 'yes' }">
	该投诉类型已经配置，不能进行新增操作，只能修改
</c:if>

<html:form action="circuit.do?method=add" method="post"
	styleId="circuitForm">
	<fieldset>
	<table id="sheet" class="formTable">
		<input type="hidden" name="status" id="status" value="GO_TO_MONITOR" />
		<input type="hidden" name="statusCnName" id="statusCnName"
			value="分公司提交阶段" />
		<input type="hidden" name="yearFlag" id="yearFlag" />
		<input type="hidden" name="dayFlag" id="dayFlag" />
		<tr>
			<td class="label">分公司*</td>
			<td><eoms:comboBox name="city" id="city" sub="country"
				initDicId="1012503" alt="allowBlank:false" /></td>
			<td class="label">归属县公司*</td>
			<td><eoms:comboBox name="country" id="country"
				alt="allowBlank:false" /></td>
		</tr>
		<tr>
			<td class="label">故障段落*</td>
			<td><input type="text" class="text" name="faultPart"
				id="faultPart" alt="allowBlank:false,maxLength:40" /></td>
		</tr>
		<tr>
			<td class="label">故障开始时间*</td>
			<td class="content"><input class="text"
				onclick="popUpCalendar(this, this,null,null,null,true,-1,null)"
				type="text" name="faultBeginTime" readonly="readonly"
				id="faultBeginTime"
				alt="vtype:'lessThen',link:'faultEndTime',vtext:'开始时间不能晚于结束时间',allowBlank:false" /></td>
			<td class="label">故障结束时间*</td>
			<td class="content"><input class="text"
				onclick="popUpCalendar(this, this,null,null,null,true,-1,null)"
				type="text" name="faultEndTime" readonly="readonly"
				id="faultEndTime"
				alt="vtype:'moreThen',link:'faultBeginTime',vtext:'结束时间不能早于开始时间',allowBlank:false" /></td>
		</tr>
		<tr>
			<td class="label">光缆类别*</td>
			<td><eoms:comboBox name="cableSort" id="cableSort"
				initDicId="1012501" alt="allowBlank:false" /></td>
			<td class="label">代维公司*</td>
			<td><eoms:comboBox name="monitorCompany" id="monitorCompany"
				initDicId="1012504" alt="allowBlank:false" /></td>
		</tr>
		<tr>
			<td class="label">光缆产权*</td>
			<td><eoms:comboBox name="cableOwner" id="cableOwner"
				initDicId="1012502" alt="allowBlank:false" /></td>
			<td class="label">是否影响业务*</td>
			<td><select size='1' name='ifEffect' id='ifEffect'
				class='select'>
				<option value=''>请选择</option>
				<option value='是'>是</option>
				<option value='否'>否</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">故障原因*</td>
			<td colspan="3"><textarea class="textarea max"
				name="faultReason" id="faultReason"
				alt="width:500,allowBlank:false,maxLength:500"></textarea></td>
		</tr>
		<tr>
			<td class="label">处理结果*</td>
			<td colspan="3"><textarea class="textarea max"
				name="faultResult" id="faultResult"
				alt="width:500,allowBlank:false,maxLength:500"></textarea></td>
		</tr>
		<tr>
			<td class="label">备注*</td>
			<td colspan="3"><textarea class="textarea max"
				name="faultRemark" id="faultRemark"
				alt="width:500,allowBlank:false,maxLength:500"></textarea></td>
		</tr>
	</table>
	<br />
	<input type="button" onclick="addRecord(this);" value="新增记录" /></fieldset>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
