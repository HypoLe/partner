<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<form action="${app}/partner/feeManage/feeRule.do?method=addRuleAndFormula" method="post"
name = "formulaForm"  id= "formulaForm">
    <table id="sheet" class="formTable">
		<tr>
		    <td class="label">
			<input type="hidden" name="feeRule_Id" id="feeRule_Id" value="${feeRule.id }"/>
				<font color='red'> * </font>规则名称
			</td>
			<td>
				<input type="text" class="text" name="name" id="name"  value="${feeRule.name }"
					alt="allowBlank:false,vtext:'规则名称 不能超出500个汉字！',maxLength:500"
				/>
			</td>
		
			<td class="label">
				备注
			</td>
			<td class="content">
				<textarea name="remark" id="remark" class="text medium"
					alt="allowBlank:true,vtext:'备注不能超出500个汉字！',maxLength:500"
				 >${feeRule.remark }</textarea>
			</td>
		</tr>
		<tr>
		  <td colspan="4">
		    <span style="color:red;">
		      <font style="font-weight:bold;">计算公式说明:</font><br/>
		       计算公式由字母和数字的四则运算表达式组成，字母表示变量。<br/>
		       考核费用的计算公式，最多含有两个变量，A:当月全额代维费用;B:月度考核得分.<br/>
		      例如：A*(1-(95-B)*1.2/100)
		    </span>
		  </td>
		</tr>		
	</table>
	
	<input type="button" id="button1" value="生成标准公式"   onclick="creatBaseFormula();"/>
	
	<table id="formulaTable" name="formulaTable" class="formTable">	
	        <tr>
	           <td colspan="5">
			     <div class="ui-widget-header"> 编辑规则</div>
		       </td>
	        </tr>
	        
	       <logic:notEmpty name="feeFormulaList" scope="request">
			<logic:iterate id="formula" indexId="i" name="feeFormulaList" type="com.boco.eoms.partner.feeManage.model.FeeFormula">
				<c:if test="${i==0}">
					<tr id="rule">
				</c:if>
				<c:if test="${i>0}">
					<tr id="rule${i}">
				</c:if>
					<td width="20%" style="vertical-align:middle;;">
						<html:text property="minScore"  styleId="minScore" onblur="javascript:_onBlurCheck(this);" style="width:20%;" 
												styleClass = "text medium" value="${formula.minscore}"/>(最小为0)
					</td>
					<td width="30%" style="vertical-align:middle;">
						<html:select property="minEqual"   styleId="minEqual" value="${formula.minequalyn}" onblur="javascript:_onBlurCheck(this);">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
		        		打分
		        		<html:select property="maxEqual"  styleId="maxEqual" value="${formula.maxequalyn}">
		        		<html:options collection="equalList" property="value" labelProperty="label"/>
		        		</html:select>
					</td>
					<td width="10%" style="vertical-align:middle;">
						<html:text property="maxScore" styleId="maxScore" value="${formula.maxscore}" style="width:20%;"  
									styleClass = "text medium" />(最大为100)
					</td>					
					<td width="25%" style="vertical-align:middle;">
						公式为：<html:text property="formulaExpression" styleId="formulaExpression" value="${formula.expression}"  style="width:80%;"
									styleClass="text medium"  
									 alt="allowBlank:false,vtype:''" />
					</td>
					<td width="5%" style="vertical-align:middle;">
					<c:choose> 
						<c:when test="${(i+1)==fn:length(feeFormulaList)}"> 
							<img src="${app}/images/icons/add.gif" onclick="addRow(this)" style="cursor:hand;display:block">
						</c:when>
						<c:otherwise>
							<img src="${app}/images/icons/add.gif" onclick="addRow(this)" style="cursor:hand;display:none">
						</c:otherwise>
					</c:choose>
					<c:choose> 
						<c:when test="${fn:length(feeFormulaList)!=1 and (i+1)==fn:length(feeFormulaList)}"> 
							<img src="${app}/images/icons/delete.gif" onclick="delRow(this)" style="cursor:hand;display:block">
						</c:when>
						<c:otherwise>
							<img src="${app}/images/icons/delete.gif" onclick="delRow(this)" style="cursor:hand;display:none">
						</c:otherwise>
					</c:choose>						
				 </td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>    
		
	    <logic:empty name="feeFormulaList" scope="request">
		        <tr id="rule">
						<td width="20%" style="vertical-align:middle;">
							<html:text property="minScore" styleId="minScore" onblur="javascript:_onBlurCheck(this);" style="width:20%;" 
										styleClass = "text medium" value="0" />(最小为0)
						</td>
						<td width="30%" style="vertical-align:middle;">
							<html:select property="minEqual" styleId="minEqual" value="1" onblur="javascript:_onBlurCheck(this);">
			        		  <html:options collection="equalList" property="value" labelProperty="label"/>
			        		</html:select>
			        		打分
			        		<html:select property="maxEqual" styleId="maxEqual" value="1">
			        		  <html:options collection="equalList" property="value" labelProperty="label"/>
			        		</html:select>
						</td>
						<td width="10%" style="vertical-align:middle;">
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
	     </logic:empty>
	</table>
	
	<input type="submit" class="btn" value="提交" />
</form>

<script type="text/javascript">
var baseType=0;//0表没使用基础类型    1表示使用指标上限大于指标下限的基础类型   2表示使用指标上限小于指标下限的基础类型 
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
<logic:present name="feeFormulaList" scope="request">
var rowidm = ${fn:length(feeFormulaList)-1};
</logic:present>
<logic:notPresent  name="feeFormulaList" scope="request">
var rowidm = 0;
</logic:notPresent>

//var baseType=0;//0表没使用基础类型    1表示使用指标上限大于指标下限的基础类型   2表示使用指标上限小于指标下限的基础类型 
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
  
  //校验公式
  function isFormul(str,under, over){//isFormul(formul,minnum,maxnum) . A:当月全额代维费用;B:月度考核得分
 	 try{
			var B=fRandomBy(under,over); //B:月度考核得分 
			var A=10000;//A:当月全额代维费用. 此处设置一个默认值10000做校验	
			var flag=0;
			var isNumFlag=false;
			for(var i=0;i<str.length;i++){//保证后台计算公式（公式不能出现计算符号开头，和连续两个计算符号）
			  if(str.charAt(i)=="+"||str.charAt(i)=="-"||str.charAt(i)=="*"||str.charAt(i)=="/"){			
			    flag=flag-1;
			    isNumFlag=false;
			  }
			  if(str.charAt(i)=="A"||str.charAt(i)=="B"){
			    flag=flag+1;
			    isNumFlag=false;
			  }
			  if(str.charAt(i)=="0"||str.charAt(i)=="1"||str.charAt(i)=="2"||str.charAt(i)=="3"||str.charAt(i)=="4"||str.charAt(i)=="5"||str.charAt(i)=="6"||str.charAt(i)=="7"||str.charAt(i)=="8"||str.charAt(i)=="9"){	
			    if(isNumFlag==false){flag=flag+1}
			    isNumFlag=true;
			  }
			  if(flag<0){return false;}
			}
			eval(str);//给变量B、A赋予了值，然后执行含这些变量(此处为B、A)的公式，不发生异常就true.
			return true;		
		}catch(e){
			return false;
		}
	}
function fRandomBy(under, over){//得到under到over的随机整数
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
	rowidm=0
	
	//先去除不必要的
	var formulaTable=document.getElementById("formulaTable");
	var varrows=formulaTable.rows;
	var j=0,len=varrows.length;
	for(var i=0;i<len;i++){
	   if(varrows[i-j].id!='rule'){//varrows[i]将会报错,js的对象的特定，varrows是一个指向页面某个element的指针，并没有保存副本，内容一直只有一份！ 删除Element的部分内容会影响到其自己； 与C的指针指向同一块区域类似
	     formulaTable.deleteRow(i-j);//iRowIndex Optional. Integer that specifies the zero-based position in the rows collection of the row to remove  
	     j=j+1;// iRowIndex要删除的行的位置，当删除了一个后，删除后后面的位置自动减少1
	   }
	}
	
	var tr1=document.getElementById("rule");
	tr1.getElementsByTagName("input")[0].value="95";
	tr1.getElementsByTagName("input")[1].value="100";
	tr1.getElementsByTagName("input")[2].value="A";
		
	addRow(tr1.getElementsByTagName("img")[0]);
	var tr2=document.getElementById("rule1");	
	tr2.getElementsByTagName("input")[0].value="80";
	tr2.getElementsByTagName("input")[1].value="95";
	tr2.getElementsByTagName("input")[2].value="A*(1-(95-B)/100)"; 
	tr2.getElementsByTagName("select")[0].value="1";
	
	addRow(tr2.getElementsByTagName("img")[0]);
    var tr3=document.getElementById("rule2");	
	tr3.getElementsByTagName("input")[0].value="70";
	tr3.getElementsByTagName("input")[1].value="80";
	tr3.getElementsByTagName("input")[2].value="A*(1-(95-B)*1.2/100)";
	tr3.getElementsByTagName("select")[0].value="1";
	
	addRow(tr3.getElementsByTagName("img")[0]);
    var tr4=document.getElementById("rule3");	
	tr4.getElementsByTagName("input")[0].value="60";
	tr4.getElementsByTagName("input")[1].value="70";
	tr4.getElementsByTagName("input")[2].value="A*(1-(95-B)*1.3/100)";
	tr4.getElementsByTagName("select")[0].value="1";
	
	addRow(tr4.getElementsByTagName("img")[0]);
    var tr5=document.getElementById("rule4");	
	tr5.getElementsByTagName("input")[0].value="0";
	tr5.getElementsByTagName("input")[1].value="60";
	tr5.getElementsByTagName("input")[2].value="A*0.5";
	tr5.getElementsByTagName("select")[0].value="1";
	
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