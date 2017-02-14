<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<form action="${app }/partner2/indicator.do?method=add&listType=list" id="myForm" method="post">

<input type="hidden" name="taskOwner" value=""/>
<input type="hidden" name="taskOwnerType" value="user"/>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >自动评分项配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
	<tr>
			<td class="label">考核指标名称</td>
			<td ><input type="text" name="appraisalName" class="text" alt="allowBlank:false" /></td>
	</tr>
	<tr>
			<td class="label">自动评分规则</td>
			<td ><select  name='myType' id='myType' class='select'>
				<option value='1' selected="selected">实时调用脚本，实时计算</option>
				<option value='2'>非实时调用脚本，实时计算</option>
				<option value='3'>不调用脚本，实时计算</option>
				<option value='4'>不自动评分</option>
			</select></td>
	</tr>
</table>
</div>

<c:if test="${addType == '1' }">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >"实时调用脚本，实时计算"详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">脚本录入风格</td>
			<td ><select  name='execCategory' id='execCategory' class='select'>
				<option value='execFile' selected="selected">执行脚本文件</option>
				<!--
					该功能目前看似没有必要了，一般性的perl脚本已经够用了 
					<option value='execCmds'>执行脚本命令</option>
				 -->
			</select></td>
		</tr>
		<tr>
			<td class="label">线程默认Sleep最小间隔*</td>
			<td><input type="text" value="1"  class="text" alt="allowBlank:false" readonly="readonly" />秒</td>
		</tr>
		<tr>
			<td class="label">数据默认有效期*</td>
			<td><input type="text" value="1"  class="text" alt="allowBlank:false" readonly="readonly" />分钟</td>
		</tr>
		<tr>
			<td class="label">线程(Process)超时时限*</td>
			<td><input type="text" name="execTimeLimit" class="text" alt="allowBlank:false" onblur="checkProcessTimeLimit(this)" />秒(10秒到60秒之间的整数)</td>
		</tr>
		<tr  >
			<td class="label">脚本附件</td>
				<td class="content" colspan="3">
					<eoms:attachment name="scriptFile" idField="scriptFile" scope="request" appCode="partner2" alt="allowBlank:false" />
				</td>
		</tr>
	</table>
	
</div>
</c:if>

<c:if test="${addType == '2' }">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >"非实时调用脚本，实时计算"详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">脚本录入风格</td>
			<td ><select  name='execCategory' id='execCategory' class='select'>
				<option value='execFile' selected="selected">执行脚本文件</option>
				<!--
					该功能目前看似没有必要了，一般性的perl脚本已经够用了 
					<option value='execCmds'>执行脚本命令</option>
				 -->
			</select></td>
		</tr>
		<tr>
			<td class="label">线程默认Sleep最小间隔*</td>
			<td><input type="text" value="1"  class="text" alt="allowBlank:false" readonly="readonly" />秒(未开放配置)</td>
		</tr>
		<tr>
			<td class="label">数据默认有效期*</td>
			<td>
				 <input type="text" class="text" name="overTimeLimitDay" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>天   
				 <input type="text" class="text" name="overTimeLimitHour" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入正整数',allowBlank:false,vtext:'限整数'"/>小时   
				 <input type="text" class="text" name="overTimeLimitMinute" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>分钟  
			</td>
		</tr>
		<tr>
			<td class="label">线程(Process)超时时限*</td>
			<td><input type="text" name="execTimeLimit" class="text" alt="allowBlank:false" onblur="checkProcessTimeLimit(this)" />秒(10秒到60秒之间的整数)</td>
		</tr>
		<tr >
			<td class="label">脚本附件</td>
				<td class="content" colspan="3">
					<eoms:attachment name="scriptFile" idField="scriptFile" scope="request" appCode="partner2" alt="allowBlank:false" />
				</td>
		</tr>
	</table>
</div>
</c:if>

<c:if test="${addType == '3' }">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >"不调用脚本，实时计算"详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label" colspan="4">不需要更多配置信息</td>
		</tr>
	</table>
</div>
</c:if>

<c:if test="${addType == '4' }">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >"不自动评分"详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label" colspan="4">不需要更多配置信息</td>
		</tr>
	</table>
</div>
</c:if>

<div style="margin-top: 30px"> 
<input type="submit" id="operationSubmitButton"  class="btn" value="新增记录" />
<!-- 
	<input type="button" id="goToImportExcel"  class="btn" value="导入记录" />
 -->
</div>

</form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	
	var myEomsForm = eoms.form;
	
	//初始化自动评分类型，默认情况为1
	var myInitType = '${addType}';
	if(!myInitType || myInitType == ""){
		myJ('select#myType').val('1');
	}else{
		myJ('select#myType').val(myInitType);
	}

	myJ('select#myType').bind('change',function(event){
		location.href= '${URI}?method=goToAdd&addType='+ myJ(this).val();
	});
	
	//As this function's special, so read the comments please. By Steve. 2011-10-09
	myJ('input[type=button]').bind('click',function(event){
		var buttonId= event.target.id;
		if(buttonId == 'addNewRecord'){
		
		}else if(buttonId == 'goToImportExcel'){
			location.href = '${app}/partner2/baseStation/baseStationMain.do?method=goToImportExcel&listType=goToImportExcel';
			//禁用该按钮防止多次提交
		}else{
			//Do nothing.
		}
	});
	
	// Add validation function supplied by Ext.
	v = new eoms.form.Validation({form:'myForm'});
	// Write your validation here.
	v.custom = function(){
		return true;
	};
});



function checkProcessTimeLimit(inputDom){
	var tmp =  parseInt(myJ(inputDom).val());
	if(tmp>10 && tmp<60){
		//do nothing.
	}else{
		alert('需要10到60秒之间的整数');
		myJ(inputDom).val('');	
	}
}




</script>
<%@ include file="/common/footer_eoms.jsp"%>