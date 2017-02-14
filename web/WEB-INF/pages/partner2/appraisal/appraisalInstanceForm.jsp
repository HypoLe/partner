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

<form action="${app}/partner2/appraisal.do?method=goToAppraisalNextStep" method="post" id="appForm">
<table class="listTable" id="list-table">
	<caption>
		<c:if test="${not empty auditUser}">
			<div style="text-align:left">
				由<font color="red">${auditUser}</font>驳回
			</div>
		</c:if><intput type="hidden" name="totalScoreHidden" id="totalScoreHidden" value=""/>
		<div style="text-align:right" id='totalScore'>
			总分：${appraisalTotalScore}
		</div>
	</caption>
	<thead>
	<tr>
		<td>
			考核指标
		</td>
		<td>
			模板分值
		</td>
		<td>
			算法描述
		</td>
		<td>
			评分方式
		</td>
		<td>
			评分
		</td>
		<td>
			实际得分
		</td>
		<td>
			增扣分原因
		</td>
		<td>
			备注
		</td>
	</tr>
	</thead>
	<% int trIndex = 0; %>
	<c:forEach var="a2a" items="${proxyScaleWithAppraisalList}">
		<tr>
			<td id="appraisalName<%=trIndex%>" >${a2a.appraisalObjectName}</td>
			<td>${a2a.appraiSalScore}</td>
			<td>
				${a2a.appraiSalScore}/100*评分
			</td>
				
			<td>
				<c:if test="${a2a.autoCalculate=='yes'}">
				<!-- 
				<a id="autoScoreHref<%=trIndex%>" class="autoScoreHref" href="#" >获取自动评分值</a>
				<div id="loadIndicator<%=trIndex%>" class="loading-indicator" style="display:none">正在获取自动评分</div>
				 -->
				系统自动评分<a href="#"> 查看明细</a>
				</c:if>
				<c:if test="${a2a.autoCalculate=='no'}">
				非系统自动评分
				</c:if>
			</td>	
				
			<td>
				<c:if test="${a2a.autoCalculate=='yes'}">
				<input type="text" class="text number" name="appraiSalScore" id="score_${a2a.id}" value="${a2a.appraisalRealScore}" readonly/>
				<script>needToSetAutoScoreArray[arrayIndex++] = '${a2a.id}' +"&"+"<%=trIndex%>";</script>
				</c:if>
				<c:if test="${a2a.autoCalculate=='no'}">
				<input type="text" class="text number" name="appraiSalScore" id="score_${a2a.id}" value="${a2a.appraisalRealScore}"/>
				</c:if>
			</td>
			
			<td id="realScore_${a2a.id}">
				${a2a.appraisalScoreByWeight}
			</td>
				<input type="hidden" name="appraisalScoreByWeight" id="scoreByWeight_${a2a.id}" value="${a2a.appraisalScoreByWeight}" />
			<td>
				<input type="text" class="text" name="deductionStandard" value="${a2a.deductionStandard}" />
			</td>
			<td>
				<input type="text" class="text" name="remark" value="${a2a.remark}"/>
			</td>
		
		</tr>
		<% trIndex++; %>
	</c:forEach>
<input type="hidden" id="appraisalRealScore" value="${appraisalTotalScore}" name="appraisalRealScore" />
<input type="hidden" id="proxyScaleId" value="${proxyScaleId}" name="proxyScaleId" />
</table>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff">
<table class="formTable" width="75%">
	<tr>
		<td>
		是否审核：<select id="isAduit">
					<option value="hasAudit" selected="selected">是</option>
					<option value="noAudit">否</option>
				</select>
		</td>
	</tr>
	<tr>
		<td id="auditTd">
		请选择联通公司审核人*：<input type="text" id="iUserName" name="iUserName" class="text" readonly="true" value="" alt="allowBlank:false"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		请选择代维公司审核人：<input type="text" id="iUser2Name" name="iUser2Name" class="text" readonly="true" value=""/>
		</td>
		<td id="auditTd2">
		请选择代维公司确认人员*：<input type="text" name="monitorCompany_i" id="monitorCompany_i"  class="text" alt="allowBlank:false" readonly="readonly"/>

		</td>
	</tr>		<input type="hidden" id="iUser" name="iUser" value=""/>
				<input type="hidden" id="iUser2" name="iUser2" value=""/>
				<input type="hidden" id="flag" name="flag" value="needAudit"/>
	<tr>
		<td>	
			<input type="button" class="btn" value="提交" onclick="validateScore();" />
		</td>
	</tr>
</table>
</div>

</form>

<eoms:xbox id="iUserName" dataUrl="${app}/xtree.do?method=userFromDept"
	rootId="1" rootText='联通公司人员' valueField="iUser"
	handler="iUserName" textField="iUserName" checktype="user" single="true">
</eoms:xbox>
<eoms:xbox id="monitorCompanyTree" dataUrl="${app}/xtree.do?method=userFromDept"  
	rootId="${monitorCompanyTreeId}" rootText="${monitorCompanyName}"  valueField="iUser2" handler="iUser2Name" 
	textField="iUser2Name" checktype="user" single="true">
</eoms:xbox>

<eoms:xbox id="monitorCompanyTree" dataUrl="${app}/xtree.do?method=userFromDept"  
	rootId="${monitorCompanyTreeId}" rootText="${monitorCompanyName}"  valueField="iUser2" handler="monitorCompany_i" 
	textField="monitorCompany_i" checktype="user" single="true">
</eoms:xbox>

	
<script type="text/javascript">
 var myJ=$.noConflict();
 
 function validateScore(){
 	var canSubmit='';
 	myJ("input[id^='score']").each(function(){
 		if(myJ(this).val()==''){
 			myJ(this).focus();
 			alert('分数不能为空！');
 			canSubmit='false';
 			return false;
 		}
 		if(myJ('#isAduit').val()=='hasAudit'){
 			if(myJ('#iUserName').val()==''){
 				alert('请选择联通公司审核人！');
 				canSubmit='false';
 			}
 		}

 		if(canSubmit=='false'){
 			return false;
 		}else{
 			myJ('#appForm').submit();
 		}
 	});
 }
 
 myJ(function(){
 
 	
 	//绑定自动评分按钮
 	myJ('a.autoScoreHref').bind('click',function(event){
 		var tmpId = myJ(this).attr('id');
 		var index = tmpId.substring(13,14);
 		myJ('div#loadIndicator'+index).show();
 		
 		var appraisalName = myJ('td#appraisalName'+index).text();
 		
 		myJ.ajax({
		  type:"POST",
		  url: "${app }/partner2/indicator.do?method=getAppraisalAutoScore",
		  data: {appraisalName:appraisalName},
		  success: function(jsonMsg){
         				var autoScore =myJ.parseJSON(jsonMsg).autoScore;
						myJ('div#loadIndicator'+index).hide();
						if(autoScore=="" || !autoScore){
							myJ('#'+tmpId).text("点击再次获取(当前值为空)");
						}else{
							myJ('#'+tmpId).text("点击再次获取(当前值为:【"+autoScore+"】分)");
						}
      	 }
		});
	});
 
 
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
	 		var weight= myJ(this).parents('tr').find('td:nth-child(2)').text();
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
	 		var weight= myJ(this).parents('tr').find('td:nth-child(2)').text();
	 		var realScore=parseFloat(weight/100*score).toFixed(2);
	 		myJ(this).parent().next().html(realScore);
	 		//alert(myJ(this).parents('tr').find('input:hidden').get(0).name);
	 		myJ(this).parents('tr').find('input:hidden').val(realScore);
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
 	
 	myJ('#isAduit').change(function(){
 		if(myJ(this).val()=='noAudit'){	
 			myJ('#auditTd').hide();
 			myJ('#auditTd2').show();
 			myJ('#iUser').val('');
 			myJ('#iUserName').val('');
 			document.forms[0].flag.value= "noNeedAudit";
 		}else{
 			myJ('#auditTd').show();
 			myJ('#auditTd2').hide();
 			document.forms[0].flag.value= "needAudit";
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
 myJ('#auditTd2').hide();
 setAutoScoreOnload();
var v = new eoms.form.Validation({form:"appForm"});
myJ('#iUserName').bind('change',function(event){
	v.validate(document.forms[0].iUserName);
});
myJ('#monitorCompany_i').bind('change',function(event){
	v.validate(document.forms[0].monitorCompany_i);
});
myJ('#isAduit').bind('change',function(event){
	document.forms[0].monitorCompany_i.value="";
	document.forms[0].iUserName.value= "";
	document.forms[0].iUser2Name.value= "";
});

</script>
<%@ include file="/common/footer_eoms.jsp"%>