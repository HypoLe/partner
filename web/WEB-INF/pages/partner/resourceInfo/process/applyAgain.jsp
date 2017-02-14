<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var myjs=jQuery.noConflict();

Ext.onReady(function(){
	//  v1= new eoms.form.Validation({form:'downloadForm'});
	//  v2= new eoms.form.Validation({form:'importForm'});
});
function linkChange(e) {
	var dictValue = e.value;
	var text = e.options[e.selectedIndex].label;
	if(dictValue==""){
	myjs("#changeType1").val(dictValue);
	myjs("#changeTypeText").val(" ");
	}
	myjs("#changeType1").val(dictValue);
	myjs("#changeTypeText").val(text);
}
function linkChange1(e){
	var dictValue = e.value;
	var text = e.options[e.selectedIndex].label;
	if(dictValue==""){
	myjs("#referenceModel1").val(dictValue);
	myjs("#referenceModelText").val(" ");
	}
	myjs("#referenceModel1").val(dictValue);
	myjs("#referenceModelText").val(text);
}
</script>
<div class="header center">
	<b>驳回变更重申
</div>
<br>
<div>
<form action="process.do?method=download" method="post" id="downloadForm" name="downloadForm">
<table  class="formTable" >
		<tr>
			<td colspan="4">
							<div class="ui-widget-header">
							<b>变更模板下载
							</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				变更类型&nbsp;*
			</td>
			<td class="content" >
				<eoms:comboBox  name="changeType" id="changeType" 	initDicId="1230501" styleClass="text medium" 
				defaultValue="${ppMain.changeType}" 	onchange="linkChange(this)" 	alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
			</td>
			<td class="label">
				变更模块&nbsp;*
			</td>
			<td class="content" >
				<eoms:comboBox  name="referenceModel" id="referenceModel" 	initDicId="${dictId}" styleClass="text medium" 
				defaultValue="${ppMain.referenceModel}"	 onchange="linkChange1(this)" alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
			</td>
		</tr>
		<tr>
			<td class="label">
				模板下载
			</td>
			<td colspan="3">
				<input type="submit" class="btn" value="下载导入文件模板" />
			</td>
		</tr>
</table>
</form>
</div>
<br>
<div>
<form action="process.do?method=xlsValidate" method="post" id="importForm" name="importForm">
<table  class="formTable">
		<tr>
			<td colspan="4">	<div class="ui-widget-header">
					<b>变更申请数据上传</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				变更类型&nbsp;*
			</td>
			<td class="content" >
				<eoms:comboBox  name="changeType" id="changeType1" 	initDicId="1230501"  defaultValue="${ppMain.changeType}"
				styleClass="text medium"  	alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
				
			</td>
			<td class="label">
				变更模块&nbsp;*
			</td>
			<td class="content" >
				<eoms:comboBox  name="referenceModel" id="referenceModel1" 	initDicId="${dictId}"  
				defaultValue="${ppMain.referenceModel}" 	styleClass="text medium"  	
				alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
				
			</td>
		</tr>
		<tr>
			<td class="label">
				选择审批人&nbsp;*
			</td>
			<td class="content" colspan="3">
				<c:set var="boxData">[{id:'${ppLink.dataSender}',name:'<eoms:id2nameDB id="${ppLink.dataSender}" beanId="tawSystemUserDao"/>'}]</c:set>
				<input type="text" name="reviewer" id="reviewer" 	class="text" readonly="readonly" alt="allowBlank:false" value="" />
				<input type="button" name="userButton" id="userButton"	value="请选择审核人" class="btn"  alt="allowBlank:false" />
				<input type="hidden" name="dataReceiver" id="dataReceiver" value="" />
					<eoms:xbox id="tree1"	dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"
					rootText='用户树' valueField="dataReceiver" handler="userButton" 	textField="reviewer" checktype="user" single="true"
					data="${boxData}"></eoms:xbox>
			</td>
		</tr>
		<tr rowspan="3">
			<td class="label">
				申请附件&nbsp;*
			</td>
			<td class="content" colspan="3">
				<eoms:attachment scope="request" idField="changeAttachment"  appCode="netresource" idList="${ppMain.changeAttachment}" alt="allowBlank:true" />
			</td>
		</tr>
		<input type="hidden" name="idMain" value="${ppMain.id }">
		<tr>
			<td colspan="4">
			<input type="submit"  value="提交申请">
			</td>
		</tr>
</table>
</form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>