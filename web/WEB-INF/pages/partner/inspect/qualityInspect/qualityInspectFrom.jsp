<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	Ext.onReady(function(){
	    v = new eoms.form.Validation({form:'taskOrderForm'});
	});

	/**
 	* 验证评分时输入分数是否大于限定的最大分数值
 	*/
 	function validateScore(input,maxScore){
 		var inputValue = input.value; //输入值
 		if(inputValue > maxScore){
 			alert('不能输入大于'+maxScore+'的分数，请重新输入');
 			input.value = '';
 			return;
 		}
 		countTotalScore();
 	}
 	
 	/**
 	* 计算总分
 	*/
 	function countTotalScore(){
 		var scoreInTime = parseFloat(document.getElementById('scoreInTime').value);
		var scoreStandard = parseFloat(document.getElementById('scoreStandard').value);
		var scoreFinish = parseFloat(document.getElementById('scoreFinish').value);
		var scoreStatisfied = parseFloat(document.getElementById('scoreStatisfied').value);
		var holdStatisfied;
		var total;
		if(scoreInTime <=10 && scoreStandard <=80 && scoreFinish <=5 && scoreStatisfied <=5 ){
			total = scoreInTime + scoreStandard + scoreFinish + scoreStatisfied;
			var mark = '';
			if(total >= 95){
				mark = '满意';
				holdStatisfied = '101010801';
			}else if(total >=85 && total <95){
				mark = '基本满意';
				holdStatisfied = '101010802';
			}else if(total >=75 && total <85){
				mark = '合格';
				holdStatisfied = '101010803';
			}else if(total >0 && total <75){
				mark = '一般';
				holdStatisfied = '101010804';
			}else if(total == 0){
				mark = '失效';
				holdStatisfied = '101010805';
			}
			document.getElementById('scoreTotal').value = total;
			document.getElementById('scoreTotalShow').innerHTML = total;
			document.getElementById('satisfaction').value = holdStatisfied;
			document.getElementById('satisfactionShow').innerHTML = mark;
		}else{
			document.getElementById('scoreTotal').value = 0;
			document.getElementById('scoreTotalShow').innerHTML = '';
			document.getElementById('satisfaction').value = '';
			document.getElementById('satisfactionShow').innerHTML = '';
		}
 	}
</script>

<form action="${app}/partner/inspect/inspectPlanResQc.do?method=saveResQualityCheck" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="planResId" value="${planRes.id}">
	<input type="hidden" name="planId" value="${planRes.planId}">
	<table id="taskOrderTable" class="formTable">
		<tr>
			<td class="label">
			 元任务名称 
			</td>
			<td colspan="3" class="content">
				${planRes.resName }
			</td>
		</tr>
		<tr>
		  	<td class="label">评分栏*</td>
		    <td colspan='3'>
		    	巡检及时率(10)<input type="text" name="scoreInTime" id="scoreInTime" size="5" alt="allowBlank:false,maxLength:6,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'"  onblur="validateScore(this,10)"/> |
		    	巡检规范性(80)<input type="text" name="scoreStandard" id="scoreStandard" size="5"  alt="allowBlank:false,maxLength:6,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'"  onblur="validateScore(this,80)"/>  |
		    	巡检问题通报及时性(5)<input type="text" name="scoreFinish" id="scoreFinish" size="5"  alt="allowBlank:false,maxLength:6,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'"  onblur="validateScore(this,5)"/>  |
		    	现场抽查打分(5) <input type="text" name="scoreStatisfied" id="scoreStatisfied" size="5"  alt="allowBlank:false,maxLength:6,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'"  onblur="validateScore(this,5)"/> &nbsp;&nbsp;
		    </td>
	    </tr>
	    <tr>
			<td class="label">总分</td>
			<td colspan='3' class="content">
				<span id="scoreTotalShow"></span>
				<input type="hidden" name="scoreTotal" id="scoreTotal" />
			</td>
		</tr>
		<tr>
			<td class="label">满意度</td>
			<td colspan='3' class="content">
				<span id="satisfactionShow"></span>
				<input type="hidden" name="satisfaction" id="satisfaction" />
			</td>
		</tr>
		<tr>
			<td class="label">
				备注*
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="textRemark"
					id="textRemark"></textarea>
			</td> 
		</tr>
</table>

	<input type="submit" value="确定" class="btn" id="inspectsave" />		
			
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>

</form>

<%@ include file="/common/footer_eoms.jsp"%>
