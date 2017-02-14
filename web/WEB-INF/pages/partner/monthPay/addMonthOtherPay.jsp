<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>



<script type="text/javascript">

var myJ = jQuery.noConflict();
myJ(function() {
v = new eoms.form.Validation({form:'monthOtherPayForm'});
v.custom = function(){

	return true;




}



  
  });
  /*
    function notNumber(value){


  	   	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var value=value; 
   		if(value.match(price) && ""!=value){
   			return false;
      	}else{
           	return true;
      	}
  }
  */


</script>



<form action="${app}/partner/monthPay/monthOtherPay.do?method=add" method="post"
	id="monthOtherPayForm" name="monthOtherPayForm">
	




	<table id="sheet" class="formTable">
	<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					月度其他费用
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				时间
			</td>
			<td class="content">
				<select id="year" name="year" class="text">

					<option value="2012" >
						2012年
					</option>
					<option value="2013"  >
						2013年
					</option>
					<option value="2014" >
						2014年
					</option>
				</select>
			
				<select id="month" name="month" class="text">

					<option value="1" >
						1月
					</option>
					<option value="2"  >
						2月
					</option>
					<option value="3" >
						3月
					</option>
					<option value="4" >
						4月
					</option>
					<option value="5"  >
						5月
					</option>
					<option value="6" >
						6月
					</option>
					<option value="7" >
						7月
					</option>
					<option value="8"  >
						8月
					</option>
					<option value="9" >
						9月
					</option>
					<option value="10" >
						10月
					</option>
					<option value="11"  >
						11月
					</option>
					<option value="12" >
						12月
					</option>
				</select>
			</td>
			
			
			
			<td class="label">
				巡检组织机构
			</td>

			<td class="content">
				<input type="text" id="partner_name" name="partner_name" class="text" alt="allowBlank:false" readonly="true" value="" />
				<input type="hidden" id="partner_id" name="partner_id" />
			</td>

		</tr>
		<tr>
		<td class="label">
				区域
			</td>
		<td>
		<input type="text" class="text"
					     maxLength="100"
						name="areaName" id="areaName"  alt="allowBlank:false" readonly="true" />
			<input type="hidden" name="areaid" id="areaid"
						 maxLength="32"
						class="text medium" />
		</td>
		<td class="label">
				费用类型
			</td>
		<td>
		<select id="cost_type" name="cost_type" class="text">

					<option value="1" >
						奖励
					</option>
					<option value="2"  >
						补贴
					</option>
					<option value="3" >
						报销
					</option>
					<option value="4" >
						其他
					</option>
				</select></td>
		
		</tr>
		<tr>
		<td class="label">
				应付款（元）：
			</td>
		<td>
		<input type="text" id="payables" name="payables" class="text" alt="allowBlank:false" />
		</td>
		<td class="label">
				式付款（元）：
			</td>
		<td>
		<input type="text" id="actual_payment" name="actual_payment" class="text" alt="allowBlank:false" />
		</td></tr>
		<tr>
		<td>
		付款依据
		</td>
		
			<td colspan="3" class="content">
				<textarea name="cost_voucher" id="cost_voucher" class="text medium"
					alt="allowBlank:false,vtext:'备注 不能超出125个汉字！',maxLength:325"></textarea>
			</td>
		</tr>
		

</table>






		<tr>
			<td class="label">

			</td>
			<td>
				<input type="submit" value="提交" />
			</td>
		</tr>



	

</form>
<eoms:xbox id="partner_id" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
	rootId="" rootText='代维公司' valueField="partner_id"
	handler="partner_name" textField="partner_name" checktype="dept"
	single="true"></eoms:xbox>
	<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
	rootText='所属地市' valueField="areaid" handler="areaName" textField="areaName"
	checktype="area" single="true"></eoms:xbox>
<%@ include file="/common/footer_eoms.jsp"%>