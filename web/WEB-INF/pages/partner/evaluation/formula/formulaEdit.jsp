<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
#toOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<html:form action="/formula.do?method=add" styleId="formulaForm"  method="post">
	<input type="hidden" id="ownRuleId" name="ownRuleId"
		value="${ownRuleId}" />
<table class="formTable">
	<tr>
	<td colspan="4">
	费用计算公式<div id='buttonDiv'>
	<font color='red'>费用总额请用大写字母A表示;考核得分请用变量B表示;公式中不能输入中文以及中文符号
	<br/>
	例如:A*(1-(95-B)/100),表示：费用总额*(1-(95-考核得分)/100
	<br/>
	如需增加其他变量，请联系项目经理
	</font>
	<br/>
	&nbsp;&nbsp; 
	</div>
	<br/>
	<div id="formulaTemplate">
		<table id="formulatable" class="formTable">
		<logic:present name="formulaList" scope="request">
			<logic:iterate id="formula" indexId="i" name="formulaList" type="com.boco.eoms.evaluation.model.Formula">
				<c:if test="${i==0}">
					<tr id="rule">
				</c:if>
				<c:if test="${i>0}">
					<tr id="rule${i}">
				</c:if>
					<td width="15%" style="vertical-align:middle;;">
						<html:text property="minScore" name="formula" styleId="minScore" onblur="javascript:_onBlurCheck(this);" style="width:40%;" 
									styleClass = "text medium"  />(最小为0)
					</td>
					<td width="20%" style="vertical-align:middle;">
						<html:select property="minEqual"  name="formula"  styleId="minEqual" onblur="javascript:_onBlurCheck(this);">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
		        		得分
		        		<html:select property="maxEqual"  name="formula" styleId="maxEqual" >
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
					</td>
					<td width="15%" style="vertical-align:middle;">
						<html:text property="maxScore" styleId="maxScore"  name="formula" style="width:40%;"  
									styleClass = "text medium" />(最大为100)
					</td>
					
					<td width="45%" style="vertical-align:middle;">
						付费公式为：<html:text property="formulaExpression" styleId="formulaExpression"  name="formula"  style="width:80%;"
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
						<html:text property="minScore" styleId="minScore" onblur="javascript:_onBlurCheck(this);" style="width:40%;" 
									styleClass = "text medium" value="0" />(最小为0)
					</td>
					<td width="20%" style="vertical-align:middle;">
						<html:select property="minEqual" styleId="minEqual" value="1" onblur="javascript:_onBlurCheck(this);">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
		        		得分<html:select property="maxEqual"styleId="maxEqual" value="1">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
					</td>
					<td width="15%" style="vertical-align:middle;">
						<html:text property="maxScore" styleId="maxScore" value="100" style="width:40%;"  
									styleClass = "text medium" />(最大为100)
					</td>
					
					<td width="45%" style="vertical-align:middle;">
						付费公式为：<html:text property="formulaExpression" styleId="formulaExpression"  style="width:80%;"
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

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="提交" />
		</td>
	</tr>
</table>
</html:form>
<script type="text/javascript">
var v = new eoms.form.Validation({form:'formulaForm'});


v.custom = function(){
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
  	try{
  		if(value!=""){
  			return isNaN(parseFloat(value));
  		}  		
  	}catch(e){}
  	return false;
  }
  
  function isFormul(str,under, over){
 	 try{
			var A=10000;
			var B=fRandomBy(under,over);
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
</script>
<%@ include file="/common/footer_eoms.jsp"%>