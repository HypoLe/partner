<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>


<form  id="myForm" method="post">

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
				<option value='2011' selected="selected">2011年</option>
				<option value='2012'>2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1' name="monthFlag"
				id="monthFlag" class='select' >
				<option value='1' selected="selected">1</option>
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
		<tr>
			<td class="label">代维类型</td>
			<td class="content"><select id='monitorType' size='1'  class='select' >
				<option value='baseStationProxy' selected="selected" >基站代维</option>
				<option value='circuitProxy'>线路代维</option>
			</select></td>
		</tr>
		</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" id="opBasicInfo">结算分公司和代维公司</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
		<tr>
			<td class="label">甲方结算部门</td>
			<td class="content">
				<input type="text" id="iCityName" name="iCityName" class="text" alt="allowBlank:false" readonly="true" />
				<input type="hidden" id="iCity" name="iCity" />
			</td>
			<td class="label">代维公司</td>
			<td class="content">
				<input type="text" id="monitorCompanyName" name="monitorCompanyName" class="text" alt="allowBlank:false" readonly="true" />
				<input type="hidden" id="monitorCompany" name="monitorCompany" />
			</td>
		</tr>
</table>

</div>

<div style="margin-top: 30px"> 
<input type="submit" id="operationSubmitButton"  class="btn" value="生成代维结算表" />
</div>

</form>

<eoms:xbox id="iCityBox" dataUrl="${app}/xtree.do?method=dept&perspective=partyA"
	rootId="1" rootText='甲方结算部门' valueField="iCity"
	handler="iCityName" textField="iCityName" checktype="dept" single="true">
</eoms:xbox>
<eoms:xbox id="monitorCompanyBox" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
	rootId="-1" rootText='代维公司' valueField="monitorCompany"
	handler="monitorCompanyName" textField="monitorCompanyName" single="true">
</eoms:xbox>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {

	myJ('input[type=button]').bind('click',function(event){
		var buttonId= event.target.id;
		if(buttonId == 'operationSubmitButton'){
			
		} else{
			//Do nothing.
		}
	});
	
	// Add validation function supplied by Ext.
	v = new eoms.form.Validation({form:'myForm'});
	// Write your validation here.
	v.custom = function(){
		var monitorType = myJ('select#monitorType').val();
		//console.log(myJ('select#monitorType').val());
		if(!monitorType||monitorType==""){
			alert('请选择代维类型');
			return false;
		}
		if(myJ('select#monitorCompany').val()==""){
			alert('请选择代维公司');
			return false;
		}
		
		if(monitorType=="baseStationProxy"){
			myJ('form#myForm').attr('action',"${app}/partner2/baseStation/baseStationMain.do?method=balanceBaseStationResult");
		}else{
			myJ('form#myForm').attr('action',"${app}/partner2/baseStation/baseStationMain.do?method=balanceCircuitResult");
		}
		return true;
	};
});


</script>
<%@ include file="/common/footer_eoms.jsp"%>