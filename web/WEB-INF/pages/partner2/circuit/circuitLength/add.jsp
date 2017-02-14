<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

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

	v = new eoms.form.Validation({form:'circuitLengthForm'});
	v.custom = function(){
		
		if(myJ('input#permitAccess').val()=='no'){
			alert('该类型的配置已经存在，无法新增');
			return false;
		}
	
		if($('monthFlag').value=="default"){
			alert("请选择月度");
			return false;
		}
		var x1 = parseFloat($('cableLength1').value);
		var x2 = parseFloat($('cableLength2').value);
		var x3 = parseFloat($('cableLength3').value);
		var x4 = parseFloat($('cableLength4').value);
		var x5 = parseFloat($('cableLength5').value);
		var exetraHint="";
		if(x1<0||x2<0||x3<0||x4<0||x5<0){
			alert("光缆长度输入整数或者小数");
			return false;
		}
		if($('permitAccess').value == "no"){
			alert("该县的数据已经存在，新增会覆盖当前记录数据!");
			return false;
		}else{
			exetraHint += "光缆长度计算值为:"+ (x1+x2+x3+x4+x5);
		}
		
		$('specialMonth').value=$('monthFlag').value;
		if(confirm(exetraHint+"确认提交吗？")){
				//$('yearFlag').value= ${yearFlag};
				return true;
		}else{
			return false;
		}	
	};	
});


function getAvailableData(obj){
		 var v1=eoms.form;
		 var city=$('city').value;    
		 var country=$('country').value;    
		 var monthFlag=$('monthFlag').value;
		 var monitorCompany =  $('monitorCompany').value;
		 var myIndicator1= $('loadIndicator1');
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
				url: "circuitLength.do?method=getCircuitLengthData",
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

function resetArea(){
	var v1=eoms.form;
	$('permitAccess').value="no";
	var myIndicator1= $('loadIndicator1');
	$('monthFlag').value="default";
	v1.disableArea(myIndicator1,true);
}

</script>

<c:if test="${duplicate == 'yes' }">
	该投诉类型已经配置，不能进行新增操作，只能修改
</c:if>


<form action="${app }/partner2/circuit/circuitLength.do?method=add" id="circuitLengthForm" method="post">

<%@ include file="/nop3/common/nop3_div_ccc.jsp"%>
<input type="hidden" id="permitAccess" value="no" />
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">年度和月度信息</div>
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
				<option value='2011' >2011年</option>
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

<!-- <div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">架空光缆数据</div>
<div> -->
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">架空光缆数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
		<tr>
			<td class="label">皮长公里*</td>
			<td><input type="text" class="text" name="cableLength1"
				id="cableLength1" alt="allowBlank:false,maxLength:40" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength1x"
				id="cableLength1x" alt="allowBlank:false,maxLength:40" /></td>
		</tr>
		<tr>
			<td class="label">差异系数*</td>
			<td><input type="text" class="text" name="cableLength1Differ"
				id="cableLength1Differ" alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" /></td>
			<td colspan="2" class='label'></td>
		</tr>
	</table>
</div>
<!-- <div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">管道（直埋）光缆数据</div>
<div> -->
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">管道（直埋）光缆数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >	
		<tr style="margin-top: 5px">
			<td class="label">皮长公里*</td>
			<td><input type="text" class="text" name="cableLength2"
				id="cableLength2" alt="allowBlank:false,maxLength:40" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength2x"
				id="cableLength2x" alt="allowBlank:false,maxLength:40" /></td>
		</tr>
		<tr>
			<td class="label">差异系数*</td>
			<td><input type="text" class="text" name="cableLength2Differ"
				id="cableLength2Differ" alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" /></td>
			<td colspan="2" class='label'></td>
		</tr>
	</table>
</div>
<!-- <div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">同路由架空光缆数据</div>
<div> -->
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">同路由架空光缆数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
	<tr>
			<td class="label">皮长公里*</td>
			<td><input type="text" class="text" name="cableLength3"
				id="cableLength3" alt="allowBlank:false,maxLength:40" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength3x"
				id="cableLength3x" alt="allowBlank:false,maxLength:40" /></td>
		</tr>
		<tr>
			<td class="label">差异系数*</td>
			<td><input type="text" class="text" name="cableLength3Differ"
				id="cableLength3Differ" alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" /></td>
			<td colspan="2" class='label'></td>
		</tr>
	</table>
</div>	
<!-- <div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">同路由管道（直埋）光缆数据</div>
<div> -->
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">同路由管道（直埋）光缆数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
		<tr>
			<td class="label">皮长公里*</td>
			<td><input type="text" class="text" name="cableLength4"
				id="cableLength4" alt="allowBlank:false,maxLength:40" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength4x"
				id="cableLength4x" alt="allowBlank:false,maxLength:40" /></td>
		</tr>
		<tr>
			<td class="label">差异系数*</td>
			<td><input type="text" class="text" name="cableLength4Differ"
				id="cableLength4Differ" alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" /></td>
			<td colspan="2" class='label'></td>
		</tr>
	</table>
</div>	
<!-- <div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">空闲管道数据</div>
<div> -->
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">空闲管道数据</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
		<tr>
			<td class="label">管程公里*</td>
			<td><input type="text" class="text" name="cableLength5"
				id="cableLength5" alt="allowBlank:false,maxLength:40" /></td>
			<td class="label">增减*</td>
			<td><input type="text" class="text" name="cableLength5x"
				id="cableLength5x" alt="allowBlank:false,maxLength:40" /></td>
		</tr>
		<tr>
			<td class="label">差异系数*</td>
			<td><input type="text" class="text" name="cableLength5Differ"
				id="cableLength5Differ" alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" /></td>
			<td colspan="2" class='label'></td>
		</tr>
	</table>
</div>	
<!-- <div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">其它数据</div>
<div> -->
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;">代维线路维护信息</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
		<tr>
			<td class="label" >每公里光缆维护费用(元)*</td>
			<td><input type="text" name="cableMaintenancePrice" class="text" id="cableMaintenancePrice"
						alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" /></td>
			<td class="label">线路地区差异系数*</td>
			<td><input type="text" class="text" name="cableAreaDiffer"
				id="cableAreaDiffer" alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" /></td>
		</tr>
	</table>
</div>		
<input type="submit"  class="btn" value="新增记录" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>