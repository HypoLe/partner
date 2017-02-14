<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
		var myjs=jQuery.noConflict();
		Ext.onReady(function(){
			v1= new eoms.form.Validation({form:'downloadForm',mask:'sdfsdf'});
			v2= new eoms.form.Validation({form:'importForm'});
		});
		function validateXLS(dictId) {
			//表单验证
			if(!v2.check()) {
				return;
			}
			//var dictId=myjs("#dictid").val();
			new Ext.form.BasicForm('importForm').submit({
				method : 'post',
				url : "${app}/partner/process/process.do?method=xlsValidate",
			  	waitTitle : '请稍后...',
				waitMsg : '正在导入数据,请稍后...',
			    success : function(form, action) {
					alert(action.result.infor);
					//reload页面
					location.href="${app}/partner/process/process.do?method=goToApply&dictId="+dictId;
				},
				failure : function(form, action) {
					alert(action.result.infor);
				}
		    });
		 }
		
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
	<b>变更申请
</div>
<br>
<div>
<form action="process.do?method=download" method="post" id="downloadForm" name="downloadForm">
	<fieldset>
		<legend >
			<b>变更模板下载</b>
		</legend>
			<table  class="formTable" >
					<tr>
						<td class="label">
							变更类型&nbsp;*
						</td>
						<td class="content" >
							<eoms:comboBox  name="changeType" id="changeType" 	initDicId="1230501" styleClass="input select" 
							defaultValue="${ppMain.changeType}" 	onchange="linkChange(this)" 	alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
						</td>
						<td class="label">
							变更模块&nbsp;*
						</td>
						<td class="content" >
							<eoms:comboBox  name="referenceModel"  id="referenceModel" 	initDicId="${dictId}" styleClass="select" 
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
			<input type="hidden" id="dictid" value="${dictId}">
		</fieldset>
</form>
</div>
<br>
<div>
<form action="process.do?method=xlsValidate" method="post" id="importForm" name="importForm">
	<fieldset>
		<legend >
			<b>变更申请数据上传</b>
		</legend>
		<table  class="formTable">
				<tr>
					<td class="label">
						变更类型&nbsp;*
					</td>
					<td class="content" >
						<eoms:comboBox  name="changeType" id="changeType1" 	initDicId="1230501"  defaultValue="${ppMain.changeType}"
						styleClass="input select"   	alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
					</td>
					<td class="label">
						变更模块&nbsp;*
					</td>
					<td class="content" >
						<eoms:comboBox  name="referenceModel" id="referenceModel1" 	initDicId="${dictId}" defaultValue="${ppMain.referenceModel}" 	
						styleClass="input select"   	alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
					</td>
				</tr>
				<tr>
					<td class="label">
						选择审批人&nbsp;*
					</td>
					<td class="content" colspan="3">
						<input type="text" name="reviewer" id="reviewer" class="text" readonly="readonly" alt="allowBlank:false" />
						<input type="button" name="userButton" id="userButton"	 value="请选择审核人" class="btn"  alt="allowBlank:false" />
						<input type="hidden" name="dataReceiver" id="dataReceiver" />
						<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"	rootText='用户树' valueField="dataReceiver" 
						handler="userButton" textField="reviewer" checktype="user" single="true">
						</eoms:xbox>
					</td>
				</tr>
				<tr rowspan="3">
					<td class="label">
						申请附件&nbsp;*
					</td>
					<td class="content" colspan="3">
						<span style=" color:red">目前系统只支持上传单个文件,如果有多个文件只有一个文件才能被识别</span>
						<eoms:attachment scope="request" idField="changeAttachment" appCode="netresource" 
						idList="${ppMain.changeAttachment}" alt="allowBlank:false" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<input type="button"   onclick ="validateXLS('${dictId}')"  class="btn" value="提交申请">
					</td>
				</tr>
		</table>
	</fieldset>
</form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>