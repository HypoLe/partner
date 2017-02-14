<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>



<script type="text/javascript">

var myJ = jQuery.noConflict();
myJ(function() {
v = new eoms.form.Validation({form:'checkRuleForm'});
v.custom = function(){
/*
try{
	var btomRate = document.getElementById("btomRate");
	
	
	if(notNumber(btomRate.value.trim()) ){
			alert("指标上限必须是数字");
			btomRate.focus();
			return false;
		}
		
	
	}catch(e){
	
		return false;
	}
try{
	var topmRate = document.getElementById("topRate");
	
	
	if(notNumber(topmRate.value.trim()) ){
			alert("指标下限必须是数字");
			topmRate.focus();
			return false;
		}
		
	
	}catch(e){
	
		return false;
	}
try{
	var btomScore = document.getElementById("btomScore");
	
	
	if(notNumber(btomScore.value.trim()) ){
			alert("基础分值必须是数字");
			btomScore.focus();
			return false;
		}
		
	
	}catch(e){
	
		return false;
	}
try{
	var topScore = document.getElementById("topScore");
	
	
	if(notNumber(topScore.value.trim()) ){
			alert("挑战分值必须是数字");
			topScore.focus();
			return false;
		}
		
	
	}catch(e){
	
		return false;
	}
	*/
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



<form action="${app}/partner/evaluation/checkRule.do?method=add" method="post"
	id="checkRuleForm" name="checkRuleForm">
	<input type="hidden" id="id" name="id" value="${rule.id}" />


	<input type="hidden" id="ownIndcId" name="ownIndcId"
		value="${ownIndcId}" />




	<table id="sheet" class="formTable">
	<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					编辑规则
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>规则名称
			</td>
			<td>
				<input type="text" class="text" name="ruleNm" id="ruleNm"
					alt="allowBlank:false,vtext:'规则名称 不能超出1000个汉字！',maxLength:2000"
					value="${rule.ruleNm }" />
			</td>
			
			
			<!--  
			<td class="label">
				<font color='red'> * </font>指标下限
			</td>
			<td>
				<input type="text" class="text" name="btomRate" id="btomRate"
					alt="allowBlank:false,vtext:' 必须是数字！',maxLength:15"
					value="${rule.btomRate}" />
			</td>
		</tr>
		<tr>

			
			<td class="label">
				<font color='red'> * </font>指标上限
			</td>
			<td>
				<input type="text" class="text" name="topRate" id="topRate"
					alt="allowBlank:false,vtext:' 必须是数字！',maxLength:15"
					value="${rule.topRate}" />
			</td>
			<td class="label">
				<font color='red'> * </font>基础分值
			</td>
			<td>
				<input type="text" class="text" name="btomScore" id="btomScore"
					alt="allowBlank:false,vtext:' 必须是数字！',maxLength:15"
					value="${rule.btomScore}" />
			</td>
		</tr>
		<tr>

			
			<td class="label">
				<font color='red'> * </font>挑战分值
			</td>
			<td>
				<input type="text" class="text" name="topScore" id="topScore"
					alt="allowBlank:false,vtext:' 必须是数字',maxLength:15"
					value="${rule.topScore}" />
			</td>
		
		-->
			<td class="label">
				备注
			</td>

			<td class="content">
				<textarea name="note" id="note" class="text medium"
					alt="allowBlank:true,vtext:'备注 不能超出1000个汉字！',maxLength:2000"
					>${rule.note}</textarea>
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
<%@ include file="/common/footer_eoms.jsp"%>