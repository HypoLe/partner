<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>


<form action="${app }/partner2/baseStation/baseStationMain.do?method=add&listType=list" id="baseStationMainForm" method="post">

<input type="hidden" name="taskOwner" value=""/>
<input type="hidden" name="taskOwnerType" value="user"/>



<%@ include file="/nop3/common/nop3_div_ccc.jsp"%>
<input type="hidden" id="permitAccess" value="no" />
<!-- <div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">年度和月度信息</div>
<div> -->

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" id="opBasicInfo">年度和月度信息</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">

<table class="formTable" >
	<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1'
				name='yearFlag' id='yearFlag'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011'>2011年</option>
				<option value='2012' selected="selected">2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1' name="monthFlag"
				id="monthFlag" class='select' onchange="getAvailableData(this);">
				<option value="default">请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>
		<tr id="loadIndicator1" style="display:none">
			<td class="label"></td>
			<td colspan="3">
			<div id="loadIndicator2">正在验证是否已经配置</div>
			</td>
		</tr>
		</table>
</div>


<!-- <div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">代维基站维护费用 -->


<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">2G宏基站数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				基站总数(个)*
			</td>
			<td>
				<input type="text" name="station1Sum" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站代维数*
			</td>
			<td>
				<input type="text" name="station1Monitor" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
		<tr>
			<td class="label">
				基站增减数*
			</td>
			<td>
				<input type="text" name="station1Minus" class="text"  alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站差异系数*
			</td>
			<td>
				<input type="text" name="station1Differ" class="text"  alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
	</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">边际网基站数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				基站总数(个)*
			</td>
			<td>
				<input type="text" name="station2Sum" class="text"  alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站代维数*
			</td>
			<td>
				<input type="text" name="station2Monitor" class="text"  alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
		<tr>
			<td class="label">
				基站增减数*
			</td>
			<td>
				<input type="text" name="station2Minus" class="text"  alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站差异系数*
			</td>
			<td>
				<input type="text" name="station2Differ" class="text"  alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
	</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">独立TD宏基站数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				基站总数(个)*
			</td>
			<td>
				<input type="text" name="station3Sum" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站代维数*
			</td>
			<td>
				<input type="text" name="station3Monitor"  class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
		<tr>
			<td class="label">
				基站增减数*
			</td>
			<td>
				<input type="text" name="station3Minus" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站差异系数*
			</td>
			<td>
				<input type="text" name="station3Differ" class="text"  alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
	</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">共址TD宏基站数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				基站总数(个)*
			</td>
			<td>
				<input type="text" name="station4Sum" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站代维数*
			</td>
			<td>
				<input type="text" name="station4Monitor" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
		<tr>
			<td class="label">
				基站增减数*
			</td>
			<td>
				<input type="text" name="station4Minus" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站差异系数*
			</td>
			<td>
				<input type="text" name="station4Differ" class="text"  alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
	</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">直放站数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				基站总数(个)*
			</td>
			<td>
				<input type="text" name="station5Sum" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站代维数*
			</td>
			<td>
				<input type="text" name="station5Monitor" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
		<tr>
			<td class="label">
				基站增减数*
			</td>
			<td>
				<input type="text" name="station5Minus" class="text" alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
			<td class="label">
				基站差异系数*
			</td>
			<td>
				<input type="text" name="station5Differ" class="text"  alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
			</td>
		</tr>
	</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">代维基站维护信息</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
	<tr>
		<td class="label">
			维护费用（元/个）*
		</td>
		<td>
			<input type="text" name="stationMaintenancePrice" class="text"
				alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
		</td>
		<td class="label">
			基站地区差异系数*
		</td>
		<td>
			<input type="text" name="stationAreaDiffer" class="text"  alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
		</td>
	</tr>
</table>
</div>
<div style="margin-top: 30px"> 
<input type="submit" id="operationSubmitButton"  class="btn" value="新增记录" />
<input type="button" id="goToImportExcel"  class="btn" value="导入记录" />
</div>

</form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {

	myJ('select#city').bind('change',function(event){
		myJ('select#monthFlag').val('default');
	});
	myJ('select#country').bind('change',function(event){
		myJ('select#monthFlag').val('default');
	});
	myJ('select#monitorCompany').bind('change',function(event){
		myJ('select#monthFlag').val('default');
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
	v = new eoms.form.Validation({form:'baseStationMainForm'});
	// Write your validation here.
	v.custom = function(){
		if(myJ('input#permitAccess').val()=='no'){
			alert('该类型的配置已经存在，无法新增');
			return false;
		}
		return true;
	};
});

function getAvailableData(obj){
		 var v1=eoms.form;
		 var city=$('city').value;    
		 var country=$('country').value;    
		 var monthFlag=$('monthFlag').value; 
		 var myIndicator1= $('loadIndicator1');
		  var monitorCompany =  $('monitorCompany').value;
		 var myIndicator2= $('loadIndicator2');
		 if(country==""){
		 	alert("请选择县");
		 	myJ('select#monthFlag').val('default');
		 	return;
		 }
		  if(monitorCompany==""){
		 	alert("请选择代维公司");
		 	myJ('select#monthFlag').val('default');
		 	return;
		 }
		 if($('monthFlag').value=="default"){
		 	return;
		 }
		 if(city&&country&&monthFlag)
		 	myIndicator2.innnerHTML="正在查询数据是否存在，请稍候";
		 	Ext.Ajax.request({
				method:"post",
				params:{city:city,country:country,monitorCompany:monitorCompany,monthFlag:monthFlag},
				url: "baseStationMain.do?method=getBaseStationData",
			success: function(x){
				v1.enableArea(myIndicator1);
	        	var circuitLength= eoms.JSONDecode(x.responseText);
				if(circuitLength.isExist=="no"){
					myIndicator2.innerHTML="该类型的配置不存在"+"<img src='${app}/images/icons/sheet-icons/tick.png'>";
					$('permitAccess').value="yes";
				}else{
					myIndicator2.innerHTML="该类型的配置已经存在"+"<img src='${app}/images/icons/sheet-icons/exclamation.png'>";
					$('permitAccess').value="no";
				}},
	        failure: function() {
	        	
	        }
	 });
}

</script>
<%@ include file="/common/footer_eoms.jsp"%>