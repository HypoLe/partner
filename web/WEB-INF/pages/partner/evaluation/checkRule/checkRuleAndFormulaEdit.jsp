<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<form action="${app}/partner/evaluation/checkRule.do?method=addFormula" method="post"
name = "formulaForm"  id= "formulaForm">
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
			<td class="label">
				<font color='red'> * </font>指标下限(B)
			</td>
			<td>
				<input type="text" class="text" name="btomRate" id="btomRate"
					alt="allowBlank:false,vtext:' 必须是数字！',maxLength:15"
					value="${rule.btomRate}" />
			</td>
		</tr>
		<tr>

			
			<td class="label">
				<font color='red'> * </font>指标上限(C)
			</td>
			<td>
				<input type="text" class="text" name="topRate" id="topRate"
					alt="allowBlank:false,vtext:' 必须是数字！',maxLength:15"
					value="${rule.topRate}" />
			</td>
			<td class="label">
				<font color='red'> * </font>基础分值(D)
			</td>
			<td>
				<input type="text" class="text" name="btomScore" id="btomScore"
					alt="allowBlank:false,vtext:' 必须是数字！',maxLength:15"
					value="${rule.btomScore}" />
			</td>
		</tr>
		<tr>

			
			<td class="label">
				<font color='red'> * </font>挑战分值(E)
			</td>
			<td>
				<input type="text" class="text" name="topScore" id="topScore"
					alt="allowBlank:false,vtext:' 必须是数字',maxLength:15"
					value="${rule.topScore}" />
			</td>
		
			<td class="label">
				备注
			</td>

			<td class="content">
				<textarea name="note" id="note" class="text medium"  style="width: 95%; height: 80px;" 
					alt="allowBlank:true,vtext:'备注 不能超出1000个汉字！',maxLength:2000"
					>${rule.note}</textarea>
			</td>
		</tr>
<tr>
<td>
    <input type="button" id="button1" value="生成标准公式"   onclick="creatBaseFormula();"/>
    
</td>
</tr>
</table>
<div id="baseFormula">
<table class="formTable">
	<tr>
	<td colspan="4">
	考核计算公式<div id='buttonDiv'>
	<font color='red'>计算公式打分用A表示;指标下限用B表示;指标上限用C标示；基础分值用D标示；挑战分值用E标示；公式中不能输入中文以及中文符号
	<br/>
	例如:D+(E-D)*(A-B)/(C-B)
	<br/>
	如需增加其他变量，请联系项目经理
	</font>
	<br/>
	&nbsp;&nbsp; 
	</div>
	<br/>
	<div id="formulaTemplate">
		<table id="formulatable" class="formTable">
		<tr>
			<td colspan="5">
				<div class="ui-widget-header">
					编辑公式
				</div>
			</td>
		</tr>
		
		<logic:present name="formulaList" scope="request">
			<logic:iterate id="formula" indexId="i" name="formulaList" type="com.boco.eoms.evaluation.model.Formula">
				<c:if test="${i==0}">
					<tr id="rule">
				</c:if>
				<c:if test="${i>0}">
					<tr id="rule${i}">
				</c:if>
					<td width="15%" style="vertical-align:middle;">
						<html:text property="minScore" name="formula" styleId="minScore" onblur="javascript:_onBlurCheck(this);" style="width:40%;" 
									styleClass = "text medium"  />(最小为0)
					</td>
					<td width="36%" style="vertical-align:middle;">
						<html:select property="minEqual"  name="formula"  styleId="minEqual" onblur="javascript:_onBlurCheck(this);">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
		        		打分
		        		<html:select property="maxEqual"  name="formula" styleId="maxEqual" >
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
					</td>
					<td width="19%" style="vertical-align:middle;">
						<html:text property="maxScore" styleId="maxScore"  name="formula" style="width:40%;"  
									styleClass = "text medium" />(最大为100)
					</td>
					
					<td width="25%" style="vertical-align:middle;">
						公式为：<html:text property="formulaExpression" styleId="formulaExpression"  name="formula"  style="width:80%;"
									styleClass="text medium"  
									 alt="allowBlank:false,vtype:''" />
					</td>
					<td width="5%" style="vertical-align:middle;">
					<c:choose> 
						<c:when test="${(i+1)==fn:length(formulaList)}"> 
							<img src="${app}/images/icons/add.gif" onclick="addRow(this)" style="cursor:hand;display:block">
						</c:when>
						<c:otherwise>
							<img src="${app}/images/icons/add.gif" onclick="addRow(this)" style="cursor:hand;display:none">
						</c:otherwise>
					</c:choose>
					<c:choose> 
						<c:when test="${fn:length(formulaList)!=1 and (i+1)==fn:length(formulaList)}"> 
							<img src="${app}/images/icons/delete.gif" onclick="delRow(this)" style="cursor:hand;display:block">
						</c:when>
						<c:otherwise>
							<img src="${app}/images/icons/delete.gif" onclick="delRow(this)" style="cursor:hand;display:none">
						</c:otherwise>
					</c:choose>
						
						
					</td>
				</tr>
			</logic:iterate>
		</logic:present>
		<logic:notPresent  name="formulaList" scope="request">		
			<tr id="rule">
					<td width="15%" style="vertical-align:middle;;">
						<html:text property="minScore" styleId="minScore" onblur="javascript:_onBlurCheck(this);" style="width:20%;" 
									styleClass = "text medium" value="0" />(最小为0)
					</td>
					<td width="36%" style="vertical-align:middle;">
						<html:select property="minEqual" styleId="minEqual" value="1" onblur="javascript:_onBlurCheck(this);">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
		        		打分<html:select property="maxEqual"styleId="maxEqual" value="1">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
					</td>
					<td width="19%" style="vertical-align:middle;">
						<html:text property="maxScore" styleId="maxScore" value="100" style="width:20%;"  
									styleClass = "text medium" />(最大为100)
					</td>
					
					<td width="25%" style="vertical-align:middle;">
						公式为：<html:text property="formulaExpression" styleId="formulaExpression"  style="width:80%;"
									styleClass="text medium"  
									 alt="allowBlank:false,vtype:''" value="" />
					</td>
					<td width="5%" style="vertical-align:middle;">
						<img src="${app}/images/icons/add.gif" onclick="addRow(this)" style="cursor:hand;display:block"><img src="${app}/images/icons/delete.gif" onclick="delRow(this)" style="cursor:hand;display:none">
						
					</td>
				</tr>
			</logic:notPresent>
			
		</table>
	</div>
	</td>
	</tr>
		
	</table>
</div>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="提交" />
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
var v = new eoms.form.Validation({form:'formulaForm'});


v.custom = function(){

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
		if(0>=parseFloat(btomScore.value.trim())||parseFloat(btomScore.value.trim())>100)	
	{alert("基础分值必须数字且小于等于100大于等于0");
			btomScore.focus();
			return false;}
	
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
	if(0>=parseFloat(topScore.value.trim())||parseFloat(topScore.value.trim())>100)	
	{alert("挑战分值必须数字且小于等于100大于等于0");
			topScore.focus();
			return false;}
	
	
	}catch(e){
	
		return false;
	}
try{
	if(parseFloat(topmRate.value.trim())>parseFloat(btomRate.value.trim())&&baseType==2){
	
	alert("生成的标准公式和上下限大小关系不匹配请从新点击生成标准公式");
	return false;
	}
	if(parseFloat(topmRate.value.trim())<parseFloat(btomRate.value.trim())&&baseType==1)
	{alert("生成的标准公式和上下限大小关系不匹配请从新点击生成标准公式");
	return false;
	}
	
	

	
	
	}catch(e){
	
		return false;
	}

try{
	var minScore = document.getElementsByName("minScore");
	var maxScore = document.getElementsByName("maxScore");
	var minEqual = document.getElementsByName("minEqual");
	var maxEqual = document.getElementsByName("maxEqual");
	var formulaExpression = document.getElementsByName("formulaExpression");
	//逐行校验规则内容
	for(i=0;i<minScore.length;i++){
		var min = minScore[i].value.trim();
		var max = maxScore[i].value.trim();
		var minE = minEqual[i].value.trim();
		var maxE = maxEqual[i].value.trim();
		var formul = formulaExpression[i].value.trim();
		//如果最小值不是数字，或者最小值区间值不对，则提示并将焦点设置在错误行
		if(notNumber(min) || (!notNumber(min) && eval(min<0 || min>=100))){
			alert("请输入0到100的整数或者小数,并且小于右区间");
			minScore[i].focus();
			return false;
		}
		//如果最大值不是数字，或者最小值区间值不对，则提示并将焦点设置在错误行
		//不过目前应该不存在这个问题，因为最大值基本在所有情况下，都会被默认为只读，不允许用户修改
		if(notNumber(max) || (!notNumber(max) && eval(max<=0 || max>100))){
			alert("请输入0到100的整数或者小数,并且大于左区间");
			maxScore[i].focus();
			return false;
		}
		//验证符号位置不能为“请选择”
		if(minE==""){
			alert("请选择符号");
			minEqual[i].focus();
			return false;
		}
		//验证符号位置不能为“请选择”
		if(maxE==""){
			alert("请选择符号");
			maxEqual[i].focus();
			return false;
		}
		//通过如上判断后，已经可以确认min和max的值是数字类型，所以可以对之进行parseFloat类型转换
		var minnum = parseFloat(min);
		var maxnum = parseFloat(max);
		if(minnum>=maxnum){
			alert("左区间大于等于右区间，请修改");
			minScore[i].focus();
			return false;
		}
		if(eval(maxnum-minnum<=1)){
			alert("左区间与右区间间隔太小，间隔必须至少大于1，请修改");
			minScore[i].focus();
			return false;
		}
		
		if(!isFormul(formul,minnum,maxnum)){
			alert("公式校验失败,请修改");
			formula[i].focus();
			return false;
		}	
	  }
	}catch(e){
		return false;
	}
	return true;
}

//行号初始值，第一行为不带，第二行默认为1，以后递增
//再删除时行时这个变量也会随之修改
<logic:present name="formulaList" scope="request">
var rowidm = ${fn:length(formulaList)-1};
</logic:present>
<logic:notPresent  name="formulaList" scope="request">
var rowidm = 0;
</logic:notPresent>

var baseType=0;//0表没使用基础类型    1表示使用指标上限大于指标下限的基础类型   2表示使用指标上限小于指标下限的基础类型
//依据当前行添加一个新行
function addRow(obj){
  	var trEl = obj.parentNode.parentNode;
  	if(trEl.getElementsByTagName("INPUT")[0].value==""){//如果当前行最小值为空，则要求用户输入
  		alert("请输入区间下标，值为0到100的整数或者小数");
  		return;
  	}else if(trEl.getElementsByTagName("INPUT")[0].value=="0"){
  		alert("区间范围已经到0，无法再设置");
  		return;  		
  	}
  	if(trEl.getElementsByTagName("SELECT")[0].value==""){//如果当前行最小值是否包含未选择，则要求用户选择
  		alert("请选择是否包含区间下标");
  		return;
  	}else{
  	}
  	if(trEl.getElementsByTagName("INPUT")[1].value==""){//如果当前行最大值为空，则要求用户输入
  		alert("请输入区间上标，值为0到100的整数或者小数");
  		return;
  	}else{
  	}
  	if(trEl.getElementsByTagName("SELECT")[1].value==""){//如果当前行最大值是否包含未选择，则要求用户选择
  		alert("请选择是否包含区间上标");
  		return;
  	}else{
  	}
  	
  	var newTR = trEl.cloneNode(true);
  	newTR.id = "rule" + (++rowidm);
  	//newTR.id = "";
  	clearTable(newTR);
  	//newTR.style.display = "block";
    trEl.parentNode.appendChild(newTR);
   	trEl.getElementsByTagName("IMG")[0].style.display = "none";
   	
   	if(trEl.id!='rule'){
   		trEl.getElementsByTagName("IMG")[1].style.display = "none";
   	}
   //将新增行的最大值默认为当前行的最小值，并设置为只读
   newTR.getElementsByTagName("INPUT")[1].value = trEl.getElementsByTagName("INPUT")[0].value;
   newTR.getElementsByTagName("INPUT")[1].readOnly = true;
   //将新增行的最大值是否包含依据当前行的最小值进行相反设置，防止配置出错，并设置为只读
   newTR.getElementsByTagName("SELECT")[1].value = trEl.getElementsByTagName("SELECT")[0].value==0?1:0;
   setReadOnly(newTR.getElementsByTagName("SELECT")[1]);
   newTR.getElementsByTagName("IMG")[0].style.display = "block";
   newTR.getElementsByTagName("IMG")[1].style.display = "block";
}

//用于在复制表格时
function clearTable(obj){
  //清除输入的值
  var inputs = obj.getElementsByTagName("INPUT");
  for(var i=0;i<inputs.length;i++){
    var t = inputs[i].type;
    if(t == "text" || t == "textarea" || t=="hidden"){
      inputs[i].value = "";
    }
  }
  var inputs = obj.getElementsByTagName("SELECT");
  for(var i=0;i<inputs.length;i++){    
      inputs[i].value = "";
  }
  inputs = null;
  //清除URL参数
  var ifrs = obj.getElementsByTagName("IFRAME");
  for(var i=0;i<ifrs.length;i++){
    var s = ifrs[i].src;
    var index = s.indexOf("idList=");
    if(index!=-1){
    
      var temp = s.substring(index);
      var temp1 = s.substring(0,index);
      var temp2 = temp.substring(temp.indexOf("&"));
      ifrs[i].src = temp1+"idList="+temp2;
    }
  }  
}

function delRow(obj){
  var tr = obj.parentNode.parentNode;
  if(tr.tagName == "TR"){  	
    if(confirm("确定删除此行数据吗？")) { 
      --rowidm;
      tr.parentNode.deleteRow(tr.rowIndex);
      try{
      	var trid=tr.id;
     
  		if(trid!="rule" && trid!="rule1"){
  			var num = trid.substring(4,trid.length);
  			var newid = parseInt(num)-1;
  			var parentTR = document.getElementById("rule"+newid);
  			parentTR.getElementsByTagName("IMG")[0].style.display = "block";
   			parentTR.getElementsByTagName("IMG")[1].style.display = "block";
  		}
  		if(trid=="rule1"){
  			
  			var parentTR = document.getElementById("rule");
  			parentTR.getElementsByTagName("IMG")[0].style.display = "block";
   			
  		}
  		
  		}catch(e){}
    }
  }
}

 //将设置下拉框只读

  function setReadOnly(obj){

	//var obj = document.getElementById(obj_id);

	obj.onmouseover = function(){

 	 obj.setCapture();
	}

	obj.onmouseout = function(){

 	 obj.releaseCapture();

	}

	obj.onfocus = function(){

 	 obj.blur();

	}
	obj.onbeforeactivate = function(){

 	 return false;

	}
  }
  //将指定的下拉框从只读设置为可选
  function setSelectEnable(obj){


	obj.onmouseover = function(){

 	 //obj.setCapture();
	}

	obj.onmouseout = function(){

 	 //obj.releaseCapture();

	}

	obj.onfocus = function(){

 	// obj.blur();

	}
	obj.onbeforeactivate = function(){

 	 return true;

	}
  }
  
  /**
  *失去焦点时，如果修改的是最小值，则需要将它下面一行的最小值跟着修改(如果存在下一行)
  *如果修改的是最小值是否包含，则需要将它下面一行的最小值是否包含跟着修改(如果存在下一行)
  **/
  function _onBlurCheck(obj){
  	try{
  		var curTR = obj.parentNode.parentNode;
      	var trid=curTR.id;
      	var num;
      	num = trid.length<=4?0:trid.substring(4,trid.length);

		var newid = parseInt(num)+1;
		var nextTR = document.getElementById("rule"+newid);	 
 		if(obj.id=="minScore"){
			//将下一行的最大值默认为当前行的最小值，并设置为只读
			if(obj.value=="0"){//如果最小值输入为0，则最小值是否包含默认为包含，并且不可改
				curTR.getElementsByTagName("SELECT")[0].value="1";
				setReadOnly(curTR.getElementsByTagName("SELECT")[0]);
 				curTR.getElementsByTagName("SELECT")[0] = "1";				
			}else{
			    curTR.getElementsByTagName("SELECT")[0].value="0";
				setSelectEnable(curTR.getElementsByTagName("SELECT")[0]);
			}
 			nextTR.getElementsByTagName("INPUT")[1].value = obj.value; 			
		}
		if(obj.id=="minEqual"){
			//将下一行的最大值是否包含依据当前行的最小值进行相反设置，防止配置出错，并设置为只读
			nextTR.getElementsByTagName("SELECT")[1].value = obj.value==0?1:0;
		}
  		
  		}catch(e){}
  	
  }
  function notNumber(value){
  	   	var price=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var value=value; 
   		if(value.match(price) && ""!=value){
   			return false;
      	}else{
           	return true;
      	}
  }
  
  function isFormul(str,under, over){
 	 try{
			var A=fRandomBy(under,over);
			var B=document.getElementById("btomRate").value;
			var C=document.getElementById("topRate").value;
			var D=document.getElementById("btomScore").value;
			var E=document.getElementById("topScore").value;
			var flag=0;
			var isNumFlag=false;
			for(var i=0;i<str.length;i++){//保证后台计算公式（公式不能出现计算符号开头，和连续两个计算符号）
			if(str.charAt(i)=="+"||str.charAt(i)=="-"||str.charAt(i)=="*"||str.charAt(i)=="/"){
			
			flag=flag-1;
			isNumFlag=false;
			}
			if(str.charAt(i)=="A"||str.charAt(i)=="B"||str.charAt(i)=="C"||str.charAt(i)=="D"||str.charAt(i)=="E"){
			flag=flag+1;
			isNumFlag=false;
			}
			if(str.charAt(i)=="0"||str.charAt(i)=="1"||str.charAt(i)=="2"||str.charAt(i)=="3"||str.charAt(i)=="4"||str.charAt(i)=="5"||str.charAt(i)=="6"||str.charAt(i)=="7"||str.charAt(i)=="8"||str.charAt(i)=="9"){
			
			if(isNumFlag==false){flag=flag+1}
			isNumFlag=true;
			}
			if(flag<0){return false;}
			}
			eval(str);
			return true;		
		}catch(e){
			return false;
		}
	}
   function fRandomBy(under, over){
	  var i=0;
	  while(1){
			i=parseInt(Math.random()*(over-under+1) + under);
			if(under==i || over == i){
		    	continue;
			}else{
				return i;
			}
		}
	}
	setReadOnly(document.getElementById("maxEqual"));
	function creatBaseFormula(){
	
	var btomRate = document.getElementById("btomRate");
	
	var topmRate = document.getElementById("topRate");
	
	
	if(notNumber(topmRate.value.trim()) ){
			alert("请先填好指标上限且必须是数字");
			topmRate.focus();
			return false;
		}
	if(notNumber(btomRate.value.trim()) ){
			alert("请先填好指标下限且必须是数字");
			btomRate.focus();
			return false;
		}
	
	if(parseFloat(topmRate.value.trim())>parseFloat(btomRate.value.trim())){
	baseType=1;
	}
	if(parseFloat(topmRate.value.trim())<parseFloat(btomRate.value.trim()))
	{baseType=2;}
	
	
	var  baseFormula = document.getElementById("baseFormula");

	baseFormula.innerHTML="<table class='formTable'><tr><td colspan='4'>考核计算公式<div id='buttonDiv'><font color='red'>计算公式打分用A表示;指标下限用B表示;指标上限用C标示；基础分值用D标示；挑战分值用E标示；公式中不能输入中文以及中文符号<br/>例如:D+(E-D)*(A-B)/(C-B)<br/>如需增加其他变量，请联系项目经理</font><br/>&nbsp;&nbsp; </div><br/><div id='formulaTemplate'><table id='formulatable' class='formTable'><tr><td colspan='4'><div class='ui-widget-header'>编辑公式</div></td></tr><tr id='rule'><td width='15%' style='vertical-align:middle;;'><input type='text' name='minScore' value='50' onblur='javascript:_onBlurCheck(this);' id='minScore' style='width:40%;' class='text medium' />(最小为0)</td><td width='36%' style='vertical-align:middle;'><select name='minEqual' onblur='javascript:_onBlurCheck(this);' id='minEqual'><option value=''>请选择</option><option value='0'>&lt;</option><option value='1' selected='selected'>&lt;=</option></select>打分<select name='maxEqual' id='maxEqual'><option value=''>请选择</option><option value='0'>&lt;</option><option value='1' selected='selected'>&lt;=</option></select></td><td width='19%' style='vertical-align:middle;'><input type='text' name='maxScore' value='100' id='maxScore' style='width:40%;' class='text medium' />(最大为100)</td><td width='25%' style='vertical-align:middle;'>公式为：<input type='text' name='formulaExpression' value='' id='formulaExpression' style='width:80%;' class='text medium' alt='allowBlank:false,vtype:''' /></td><td width='5%' style='vertical-align:middle;'><img src='${app}/images/icons/add.gif' onclick='addRow(this)' style='cursor:hand;display:block'><img src='${app}/images/icons/delete.gif' onclick='delRow(this)' style='cursor:hand;display:none'></td></tr></table></div></div></td></tr></table>";
	

	rowidm=0


	
	
	var tr1=document.getElementById("rule");
	
	if(baseType==1){
	tr1.getElementsByTagName("input")[0].value=topmRate.value.trim();
	tr1.getElementsByTagName("input")[2].value="E";
	}
	if(baseType==2){
	tr1.getElementsByTagName("input")[0].value=btomRate.value.trim();
	tr1.getElementsByTagName("input")[2].value="D-2*(E-D)*(A-B)/(B-C)";
	}
	
	
	addRow(tr1.getElementsByTagName("img")[0]);
	
	
	
	var tr2=document.getElementById("rule1");
	
	if(baseType==1){
	tr2.getElementsByTagName("input")[0].value=btomRate.value.trim();
	}
	if(baseType==2){
	tr2.getElementsByTagName("input")[0].value=topmRate.value.trim();
	}
	tr2.getElementsByTagName("input")[2].value="D+(E-D)*(A-B)/(C-B)";
	
	tr2.getElementsByTagName("select")[0].value="1";
	
	addRow(tr2.getElementsByTagName("img")[0]);
	
		var tr3=document.getElementById("rule2");
	
	tr3.getElementsByTagName("input")[0].value="0";
	
	if(baseType==1){
	tr3.getElementsByTagName("input")[2].value="D-2*(E-D)*(B-A)/(C-B)";
	}
	if(baseType==2){
	tr3.getElementsByTagName("input")[2].value="E";
	}
	tr3.getElementsByTagName("select")[0].value="1";
	setReadOnly(document.getElementById("maxEqual"));
	}
	function setMaxEqualReadOnly(){
	var maxEquals=document.getElementsByName("maxEqual");
	if(maxEquals.length>0){
	for(var i=0;i<maxEquals.length;i++){
	setReadOnly(maxEquals[i]);
	}
	}
	}
	setMaxEqualReadOnly();
</script>
<%@ include file="/common/footer_eoms.jsp"%>