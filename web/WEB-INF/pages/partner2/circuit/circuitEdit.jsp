<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'theForm'});
	v.custom = function(){
		var myValue= $('cableLength').value;
		var x=parseFloat(myValue);
		if(!x){
			alert("请为辖区内光缆长度输入整数或者小数");
			$('cableLength').value="";
			return false;
		}
		var extraHint="确认修改该条记录吗？";
		var myStatus=$('status').value;
		if(myStatus=="WAIT_FOR_ARGREE"){
			extraHint+="此步骤修改该记录会将记录标识为:已修改";
		}
		if(confirm(extraHint)){
			var myBeginTime=$('faultBeginTime');
			if(myBeginTime){
				$('yearFlag').value=$('faultBeginTime').value.substring(0,4);
				$('monthFlag').value=$('faultBeginTime').value.substring(5,7);
				$('dayFlag').value=$('faultBeginTime').value.substring(8,10);
				return true;
			}
		}else{
			return false;
		}	
	};	
});
</script>

<content tag="heading">
<c:out value="线路故障数据编辑页面" />
</content>
<br />
<br />

<c:set var="returnBtn">
	<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回" />
</c:set>

<html:form action="circuit.do?method=edit" method="post"
	styleId="theForm">
	<fieldset>
	<table id="sheet" class="formTable">
		<!-- Set hidden area here. -->
		<input type="hidden" name="id" id="id" value="${circuit.id}" />
		<input type="hidden" name="deleted" id="deleted"
			value="${circuit.deleted}" />
		<input type="hidden" name="insertTime" id="insertTime"
			value="${circuit.insertTime}" />
		<input type="hidden" name="mainCheckIdea" id="mainCheckIdea"
			value="${circuit.mainCheckIdea}" />
		<input type="hidden" name="mainCutOption" id="mainCutOption"
			value="${circuit.mainCutOption}" />
		<input type="hidden" name="mainCityOption" id="mainCityOption"
			value="${circuit.mainCityOption}" />
		<input type="hidden" name="mainMasterOption" id="mainMasterOption"
			value="${circuit.mainMasterOption}" />
		<input type="hidden" name="currentTaskOwner" id="currentTaskOwner"
			value="${circuit.currentTaskOwner}" />
		<input type="hidden" name="ifModified" id="ifModified"
			value="${circuit.ifModified}" />
		<input type="hidden" name="faultTimes" id="faultTimes"
			value="${circuit.faultTimes}" />
		<input type="hidden" name="sendUser" id="sendUser"
			value="${circuit.sendUser}" />
		<!-- End setting hidden area here. -->
		<input type="hidden" name="status" id="status"
			value="${circuit.status}" />
		<input type="hidden" name="statusCnName" id="statusCnName"
			value="${circuit.statusCnName}">
		<input type="hidden" name="yearFlag" id="yearFlag" />
		<input type="hidden" name="monthFlag" id="monthFlag" />
		<input type="hidden" name="dayFlag" id="dayFlag" />
		<input type="hidden" name="city" id="city" value="${circuit.city}" />
		<input type="hidden" name="country" id="country"
			value="${circuit.country}" />
		<tr>
			<td class="label">分公司</td>
			<td><input type="text" class="text readonly="
				readonly" value="<eoms:id2nameDB id="${circuit.city}" beanId="tawSystemAreaDao" />" /></td>
			<td class="label">归属县公司</td>
			<td><input type="text" class="text" readonly="readonly"
				value="<eoms:id2nameDB id="${circuit.country}" beanId="tawSystemAreaDao" />" /></td>
		</tr>
		<tr>
			<td class="label">故障段落*</td>
			<td><input type="text" class="text" name="faultPart"
				id="faultPart" alt="allowBlank:false,vtext:'请填写故障段落'"
				value="${circuit.faultPart}" /></td>
		</tr>
		<tr>
			<td class="label">故障开始时间*</td>
			<td class="content"><input class="text"
				onclick="popUpCalendar(this, this,null,null,null,true,-1,null)"
				type="text" name="faultBeginTime" readonly="readonly"
				id="faultBeginTime"
				alt="vtype:'lessThen',link:'faultEndTime',vtext:'开始时间不能晚于结束时间',allowBlank:false"
				value="${circuit.faultBeginTime}" /></td>
			<td class="label">故障结束时间*</td>
			<td class="content"><input class="text"
				onclick="popUpCalendar(this, this,null,null,null,true,-1,null)"
				type="text" name="faultEndTime" readonly="readonly"
				id="faultEndTime"
				alt="vtype:'moreThen',link:'faultBeginTime',vtext:'结束时间不能早于开始时间',allowBlank:false"
				value="${circuit.faultEndTime}" /></td>
		</tr>
		<tr>
			<td class="label">光缆类别*</td>
			<td><eoms:comboBox name="cableSort" id="cableSort"
				initDicId="1012501" alt="allowBlank:false"
				defaultValue="${circuit.cableSort}" /></td>
			<td class="label">代维公司*</td>
			<td><eoms:comboBox name="monitorCompany" id="monitorCompany"
				initDicId="1012504" alt="allowBlank:false"
				defaultValue="${circuit.monitorCompany}" /></td>
		</tr>
		<tr>
			<td class="label">光缆产权*</td>
			<td><eoms:comboBox name="cableOwner" id="cableOwner"
				initDicId="1012502" alt="allowBlank:false"
				defaultValue="${circuit.cableOwner}" /></td>
			<td class="label">是否影响业务*</td>
			<td><select size='1' name='ifEffect' id='ifEffect'
				class='select'>
				<option value='是' selected="selected">是</option>
				<option value='否'>否</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">辖区内光缆长度*(限整数或小数)</td>
			<td><input type="text" class="text" name="cableLength"
				id="cableLength" alt="allowBlank:false"
				value="${circuit.cableLength}" /></td>
		</tr>
		<tr>
			<td class="label">故障原因*</td>
			<td colspan="3"><textarea class="textarea max"
				name="faultReason" id="faultReason"
				alt="width:500,allowBlank:false,maxLength:500">${circuit.faultReason }</textarea></td>
		</tr>
		<tr>
			<td class="label">处理结果*</td>
			<td colspan="3"><textarea class="textarea max"
				name="faultResult" id="faultResult"
				alt="width:500,allowBlank:false,maxLength:500">${circuit.faultResult }</textarea></td>
		</tr>
		<tr>
			<td class="label">备注*</td>
			<td colspan="3"><textarea class="textarea max"
				name="faultRemark" id="faultRemark"
				alt="width:500,allowBlank:false,maxLength:500">${circuit.faultRemark }</textarea></td>
		</tr>
	</table>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="修改记录"></html:submit>
</html:form>

<script type="text/javascript">
	function returnBack(){
		window.history.back(-1);
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
