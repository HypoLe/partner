<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<form action="${app }/partner2/supervisionConfig.do?method=add&listType=list" id="myForm" method="post">

<input type="hidden" name="taskOwner" value=""/>
<input type="hidden" name="taskOwnerType" value="user"/>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >督办配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
	<tr>
			<td class="label">督办设置名称</td>
			<td ><input type="text" name="configName" class="text" alt="allowBlank:false" /></td>
	</tr>
	<tr>
			<td class="label">督办项业务类型</td>
			<td ><select  name='myType' id='myType' class='select'>
				<option value='sheet' selected="selected">故障工单</option>
				<option value='work'>作业计划</option>
				<option value='danger'>隐患</option>
				<option value='monitor'>盯防</option>
			</select></td>
	</tr>
</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >督办时间项配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">督办即将超时时长(最小计算单位：分钟)</td>
			<td >
				 <input type="text" class="text" name="onTimeLimitDay" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>天   
				 <input type="text" class="text" name="onTimeLimitHour" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>小时   
				 <input type="text" class="text" name="onTimeLimitMinute" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>分钟  
			</td>
			<td class="label">督办超时时长(最小计算单位：分钟)</td>
			<td >
				 <input type="text" class="text" name="overTimeLimitDay" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>天   
				 <input type="text" class="text" name="overTimeLimitHour" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入正整数',allowBlank:false,vtext:'限整数'"/>小时   
				 <input type="text" class="text" name="overTimeLimitMinute" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>分钟  
			</td>
		</tr>
		<tr>
			<td class="label">
				短信提醒次数
			</td>
			<td>
				<input type="text" class="text" style="width: 40" alt="allowBlank:false" value="1" readonly="readonly" />次
			</td>
			<td class="label">
				督办项即将超时周期内生成次数
			</td>
			<td>
				<input type="text" class="text" style="width: 40" alt="allowBlank:false" value="1" readonly="readonly" />次
			</td>
		</tr>
	</table>
</div>

<div id="sheetDiv" class="supervisionClass">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >故障工单督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				故障工单号(模糊匹配)
			</td>
			<td>
				<input type="text" name="myText1" class="text" alt="allowBlank:false" />
			</td>
			<td class="label">故障处理响应级别</td>
			<td>
				<eoms:comboBox name="myText2" id="myText2" initDicId="1010304" defaultValue="101030401" alt="allowBlank:false"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障类型(网络分类一级)
			</td>
			<td>
				<eoms:comboBox name="myText3" id="myText3" sub="myText4" initDicId="1010104" alt="allowBlank:false"/>
			</td>
			<td class="label">
				故障类型(网络分类二级)
			</td>
			<td>
				  <eoms:comboBox name="myText4" id="myText4" sub="myText5" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				故障类型(网络分类三级)
			</td>
			<td>
				<eoms:comboBox name="myText5" id="myText5" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">督办短信提醒人</td>
			<td >故障工单处理者</td>
		</tr>
	</table>
</div>
<!-- End sheetDetailDiv div -->
</div>

<div id="workDiv" class="supervisionClass" style="display: none">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >作业计划督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">作业计划Test</td>
			<td ><input type="text" name="myText1" class="text" alt="allowBlank:false" /></td>
		</tr>	
		<tr>
			<td class="label" colspan="2">working...</td>
		</tr>
	</table>
</div>
<!-- End workDiv div -->
</div>

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

	//初始化表单，禁用不需要的验证。
	var tmpType = myJ('select#myType').val()+'Div';
	myJ('div.supervisionClass').each(
			function(){
				var tmpId = myJ(this).attr('id');
				if(tmpId==tmpType){
					myEomsForm.enableArea(tmpId);
				}else{
					myEomsForm.disableArea(tmpId);
				}
			}
	);
	
	myJ('select#myType').bind('change',function(event){
		var theChoice = myJ(this).val()+'Div';
		var privateId = "";
		myJ('div.supervisionClass').each(
			function(){
				privateId = myJ(this).attr('id');
				if(privateId==theChoice){
					myJ('div#'+privateId+'.supervisionClass').show();
					myEomsForm.enableArea(privateId);
				}else{
					myJ('div#'+privateId+'.supervisionClass').hide();
					myEomsForm.disableArea(privateId);
				}
			}
		);
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
</script>
<%@ include file="/common/footer_eoms.jsp"%>