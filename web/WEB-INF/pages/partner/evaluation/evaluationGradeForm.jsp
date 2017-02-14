<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<style type="text/css">
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<script type="text/javascript">
var needToSetAutoScoreArray = new Array();var arrayIndex=0;
</script>

<form action="${app}/partner/evaluation/evaluationEntity.do?method=saveEvaluationScore" method="post" id="appForm">
<table class="listTable" id="list-table">
	<caption>
		<c:if test="${not empty auditUser}">
			<div style="text-align:left">
				由<font color="red">${auditUser}</font>驳回
			</div>
		</c:if><intput type="hidden" name="totalScoreHidden" id="totalScoreHidden" value=""/>
		<div style="text-align:right" id='totalScore'>
			总分：${totalScore}
		</div>
	</caption>


	
<input type="hidden" id="id" value="${id}" name="id" />
</table>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff">
<table class="formTable" width="75%">
	<tr>
		<td id="auditTd2">
		请选择代维公司确认人员*：<input type="text" name="dyConfirmPersonnelName" id="dyConfirmPersonnelName"  class="text" alt="allowBlank:false" readonly="readonly"/>
		</td>
				<input type="hidden" id="dyConfirmPersonnel" name="dyConfirmPersonnel" value=""/>
	<c:if test="${not empty tmpltyp}"> 
		<td id="auditTd3">
		请选择省公司确认人员*：<input type="text" name="provConfirmPersonnelName" id="provConfirmPersonnelName"  class="text" alt="allowBlank:false" readonly="readonly"/>
		</td>
				<input type="hidden" id="provConfirmPersonnel" name="provConfirmPersonnel" value=""/>
				<input type="hidden" id=tmpltyp name="tmpltyp" value="${tmpltyp}"/>
	</c:if>
	</tr>
	<tr>
		<td>	
			<input type="button" class="btn" value="提交" onclick="validateScore();" />
		</td>
		
	</tr>
</table>
</div>

</form>


<eoms:xbox id="evaluationTargetTree" dataUrl="${app}/xtree.do?method=userFromDept"  
	rootId="${evaluationTargetTreeId}" rootText="${evaluationTargetName}" valueField="dyConfirmPersonnel" handler="dyConfirmPersonnelName" 
	textField="dyConfirmPersonnelName" checktype="user" single="true">
</eoms:xbox>
<c:if test="${not empty tmpltyp}"> 
<eoms:xbox id="provConfirmPersonnelTree" dataUrl="${app}/xtree.do?method=userFromDept"  
	rootId="1" rootText="联通公司" valueField="provConfirmPersonnel" handler="provConfirmPersonnelName"
	textField="provConfirmPersonnelName" checktype="user" single="true">
</eoms:xbox>
</c:if>

	
<script type="text/javascript">
 var myJ=$.noConflict();
 
 function validateScore(){
 	var canSubmit='';
 	var iUser=myJ('#dyConfirmPersonnel').val();
 	
 	myJ("input[id^='score']").each(function(){
 	    
 		if(myJ(this).val()==''){
 			myJ(this).focus();
 			alert('分数不能为空！');
 			canSubmit='false';
 			return false;
 		}
 		if(iUser==''){
 		myJ(this).focus();
 			alert('请选择代维公共确认人员！');
 			canSubmit='false';
 			return false;
 		}
 		});
   
 		if(canSubmit=='false'){
 			return false;
 		}else{
 			myJ('#appForm').submit();
 		}
 	
 }
 
 myJ(function(){
 
 	
 
 
	  //所有input的text元素只能输入数字
 	myJ("input[id^='score']").keyup(function(){
 		if(myJ(this).val().charAt(0)=='0'){
			myJ(this).val('');
		}else{
			myJ(this).val(myJ(this).val().replace(/[^0-9.]+/,''));
 			if(parseInt(myJ(this).val())>100){
 				myJ(this).val('100');
 			}
	 		var score=myJ(this).val();
	 		//取得这个考核项所占的分数
	 		var weight= myJ(this).parents('tr').find('td:nth-child(4)').text();
	 		//根据评分算出实际得分
	 		var realScore=parseFloat(weight/100*score).toFixed(2);
	 		myJ(this).parent().next().html(realScore);
		}
 	});
 	//评分焦点失去后执行，用以防止鼠标复制进的分数不会执行keyup事件
 	myJ("input[id^='score']").blur(function(){
 		if(myJ(this).val().charAt(0)=='0'){
			myJ(this).val('');
		}else{
			myJ(this).val(myJ(this).val().replace(/[^0-9.]+/,''));
	 		if(parseInt(myJ(this).val())>100){
	 			myJ(this).val('100');
	 		}
	 		var score=myJ(this).val();
	 		var weight= myJ(this).parents('tr').find('td:nth-child(4)').text();
	 		var realScore=parseFloat(weight/100*score).toFixed(2);
	 		myJ(this).parent().next().html(realScore);
	 	//	alert(myJ(this).parents('tr').find('#appraisalScoreByWeight'));
	        myJ(this).parents('tr').find('#appraisalScoreByWeight').val(realScore);
	 		//用于保存总分
	 		var totalScore=0;
	 		myJ("td[id^='realScore']").each(function(){
	 			totalScore+=parseFloat(myJ.trim(myJ(this).text().toString()));
	 		});
	 		if(parseFloat(totalScore)>100){
	 			myJ('div#totalScore').html("总分：100");
	 			myJ('input#appraisalRealScore').val('100');
	 		}else{
	 			myJ('div#totalScore').html("总分："+parseFloat(totalScore).toFixed(2));
	 			myJ('input#appraisalRealScore').val(parseFloat(totalScore).toFixed(2));
	 		}
		}
 	});
 	
 });
 function setAutoScoreOnload(){
 		for(var i=0;i<needToSetAutoScoreArray.length;i++){
			var autoScoreInputId = needToSetAutoScoreArray[i].split("&")[0];
			var tempInputObj = document.getElementById("score_"+autoScoreInputId);
	 		var index = needToSetAutoScoreArray[i].split("&")[1];
	 		var appraisalName = myJ('td#appraisalName'+index).text();
	 		myJ.ajax({
			  type:"POST",
			  async:false,
			  url: "${app }/partner2/indicator.do?method=getAppraisalAutoScore",
			  data: {appraisalName:appraisalName},
			  success: function(jsonMsg){
	         				var autoScore =myJ.parseJSON(jsonMsg).autoScore;
	         				autoScore = 99; //just test;
							if(autoScore=="" || !autoScore){
								myJ("input#score_"+autoScoreInputId).val("获取的值为空！");
							}else{
								myJ("input#score_"+autoScoreInputId).val(autoScore);
								var weight = myJ("#realScore_"+autoScoreInputId).parents('tr').find('td:nth-child(2)').text();
								var realScore=parseFloat(weight/100*autoScore).toFixed(2);
	 							myJ("#realScore_"+autoScoreInputId).html(realScore);
	 							//myJ("#scoreByWeight_"+autoScoreInputId).val(realScore); 
	 													
							}
	      	 }
			});
 		}
 }
 setAutoScoreOnload();
var v = new eoms.form.Validation({form:"appForm"});
myJ('#dyConfirmPersonnelName').bind('change',function(event){
	v.validate(document.forms[0].dyConfirmPersonnelName);
});

</script>
<%@ include file="/common/footer_eoms.jsp"%>