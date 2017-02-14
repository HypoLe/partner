<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<style type="text/css">
#toOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<script>
function getContractUrl(){
	 window.open ('${app}/partner/agreement/pnrAgreementMains.do?method=queryContractDo','newwindow','height=600,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
   }
function setCompactNostr(obj){
	document.getElementById("compactNostr").innerHTML=obj;
}
</script>
<eoms:xbox id="payUnit" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="1" 
	  	rootText='单位' 
	  	valueField="payUnit" handler="payUnitName"
		textField="payUnitName"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="collectUnit" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="2" 
	  	rootText='单位' 
	  	valueField="collectUnit" handler="collectUnitName"
		textField="collectUnitName"
		checktype="dept" single="true"		
	  ></eoms:xbox>
	 
	 
<!-- 甲方负责人 -->
<eoms:xbox id="payUnitUser" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="1" 
	  	rootText='甲方负责人' 
	  	valueField="payUnitUser" handler="payUnitUserName"
		textField="payUnitUserName"
		checktype="user" single="true">
</eoms:xbox>

<!-- 乙方负责人 -->
<eoms:xbox id="collectUnitUser" dataUrl="${app}/xtree.do?method=userFromDept&adminFlag=true" 
	  	rootId="2" 
	  	rootText='乙方负责人' 
	  	valueField="collectUnitUser" handler="collectUnitUserName"
		textField="collectUnitUserName"
		checktype="user" single="true"></eoms:xbox>
	 
<!-- 付款负责人 --> 
<eoms:xbox id="payMoneyUser" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="1" 
	  	rootText='付款负责人' 
	  	valueField="payMoneyUser" handler="payMoneyUserName"
		textField="payMoneyUserName"
		checktype="user" single="true"></eoms:xbox>
		
<script type="text/javascript">
 var planNum = ${fn:length(planList)};
 
</script>
<html:form action="/pnrFeeInfoMains.do?method=save" styleId="pnrFeeInfoMainForm" method="post"> 


<table class="formTable">
	<caption>
		<div class="header center">合同费用信息</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同名称&nbsp;<font color='red'>*</font>
		</td>
		<td class="content"  colspan="3">
				<html:text property="contractName" styleId= "contractName" style="width:98%" alt="allowBlank:false,vtext:'',maxLength:100"/> 
		</td>
		<html:hidden property="feeName" value=""/>
		<html:hidden property="contractNO" value=""/>
		<html:hidden property="contractId" value="${pnrFeeInfoMainForm.contractId}"/>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			甲方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payUnitName" styleId="payUnitName"
						styleClass="text"  style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="${partyADeptName}" readonly="true"  onblur="changeRootId(xbox_payUnitUser,document.getElementById('payUnit').value);">
						</html:text>
			<input type="hidden" id="payUnit" name="payUnit" value="${pnrFeeInfoMainForm.payUnit}" />
		</td>
		
		<td class="label" style="vertical-align:middle">
			乙方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="collectUnitName" styleId="collectUnitName"
						styleClass="text"  style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="${partyBDeptName}" readonly="true"  onblur="changeRootId(xbox_collectUnitUser,document.getElementById('collectUnit').value);"/>
			<html:hidden property="collectUnit" styleId="collectUnit" value="${pnrFeeInfoMainForm.collectUnit}" />
		</td>
		

		
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			甲方负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payUnitUserName" styleId="payUnitUserName"
						styleClass="text"  style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="${payUnitUserName}" readonly="true" />
			<html:hidden property="payUnitUser" styleId="payUnitUser" value="${pnrFeeInfoMainForm.payUnitUser}" />
		</td>
		<td class="label" style="vertical-align:middle">
			乙方负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="collectUnitUserName" styleId="collectUnitUserName"
						styleClass="text"  style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="${collectUnitUserName}" readonly="true" />
			<html:hidden property="collectUnitUser" styleId="collectUnitUser" value="${pnrFeeInfoMainForm.collectUnitUser}" />
		</td>
	</tr>

	<tr>
	<td class="label" style="vertical-align:middle">
			付款负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payMoneyUserName" styleId="payMoneyUserName"
						styleClass="text"  style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="${payMoneyUserName}" readonly="true" />
			<html:hidden property="payMoneyUser" styleId="payMoneyUser" value="${pnrFeeInfoMainForm.payMoneyUser}" />
		</td>
		<td class="label" style="vertical-align:middle">
			付款方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payWay" styleId="payWay"
						styleClass="text"  style="width:95%;" 
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrFeeInfoMainForm.payWay}" />
		</td>
		

	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同金额(万元)&nbsp;<font color='red'>*</font>
		</td>
		<td class="content"  >
			<html:text property="contractAmount" styleId="contractAmount"
					   styleClass="text"  style="width:95%;" alt="re:/^[0-9]+\.{0,1}[0-9]{0,2}$/,re_vt:'请输入正整数或小数',allowBlank:false"
					   value="${pnrFeeInfoMainForm.contractAmount}" />
		</td>
		<td class="label" style="vertical-align:middle">
			服务类型&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-feeInfo" dictId="abilityType" defaultId="${pnrFeeInfoMainForm.abilityType}"
							 selectId="abilityType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择服务类型'" onchange='changeAbilityType();'/>
		</td>
		</tr>



	<tr>
	<td colspan="4">
	付款计划<div id='fbuttonDiv'><img align=right src="${app}/images/icons/addpay.gif" alt="添加付费计划子项" onclick="javascript:addPlan();" /></div>
	<div id="planTemplate">
		<table class="formTable">
			<tr>
				<td class="label" style="vertical-align:middle">
					阶段付款名称：
				</td>
				<td class="content" colspan="3">
					<html:text property="planPayName" styleId="planPayName" style="width:95%;" alt="allowBlank:false,vtext:'',maxLength:64"  />			
					<html:hidden property="planId" value="" />
				</td>			
				
			</tr>	
			<tr>
				<td class="label" style="vertical-align:middle">
					计划付款比例：
				</td>
				<td class="content">
					<html:text property="planWeight" styleId="planWeight1"  style="width:15%;"  alt="re:/^([1]{1}[0]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1,2}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" 
								styleClass = "text medium"  value="" name="ceshi"/>% &nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'><span id="payMoney1"></span></font>
				</td>		
				<td class="label" style="vertical-align:middle">
					计划付费时间：
				</td>
				<td class="content">
					<html:text property="planPayTime" styleId="planPayTime"
								styleClass="text medium"  style="width:96%;"
								 onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="" />
				</td>
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					阶段付款描述：
				</td>
				<td class="content" colspan="3">
					<html:textarea property="planRemark" styleId="planRemark" style="width:99%;" alt="allowBlank:false,vtext:'',maxLength:1000" rows="3"/>			
				</td>
			</tr>
			<tr>
				<th colspan="4"><div id='buttonDiv'><img align=right src="${app}/images/icons/addpay.gif" alt="添加付款阶段" onclick="javascript:addPlan();" /><img align=right src="${app}/images/icons/delpay.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></div></th>
			</tr>
		</table>
	</div>
	
	<div id="planDiv">
		<table class="formTable">
			<c:forEach var="plan" items="${planList}" varStatus="stauts">
			<tr>
					<th colspan="4"><b>第${stauts.count} 项：</b></th>
			</tr>
		
				<tr>
					<td class="label" style="vertical-align:middle">
						阶段付款名称：
					</td>
					<td class="content" colspan="3">
						<html:text property="planPayName" styleId="planPayName" style="width:99%;" alt="allowBlank:false,vtext:'',maxLength:64"   value="${plan.planPayName}"/>			
						<html:hidden property="feeId" value="${plan.feeId}" />
					</td>			
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						计划付款比例：
					</td>
					<td class="content">
						<html:text property="planWeight" styleId="planWeight${stauts.count}"  style="width:15%;"  alt="re:/^([1]{1}[0]{1}[0]{1}([.][0]{1,2})?)$|^([(0-9)]{1,2}([.][0-9]{1,2})?)$/,re_vt:'请输入小于等于10的正整数或小数(2位)',allowBlank:false,maxLength:5" 
									styleClass = "text medium"  value="${plan.planWeight}"  onclick="alert(this.name);"/>% &nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'><span id="payMoney${stauts.count}"></span></font>
					</td>		
					<td class="label" style="vertical-align:middle">
						计划付费时间：
					</td>
					<td class="content">
						<html:text property="planPayTime" styleId="planPayTime"
									styleClass="text medium"  style="width:96%;"
									 onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="${plan.planPayTimeStr}" />
					</td>
				</tr>	
				<tr>
					<td class="label" style="vertical-align:middle">
						阶段付款描述：
					</td>
					<td class="content"  colspan="3">
						<html:textarea property="planRemark" styleId="planRemark" style="width:96%;" value="${plan.remark}"  alt="allowBlank:false,vtext:'',maxLength:1000" rows="3"/>			
					</td>				
				</tr>
				<tr name='buttonTr'>
					<th colspan="4"><img align=right src="${app}/images/icons/addpay.gif" alt="添加付款阶段" onclick="javascript:addPlan();" /><img align=right src="${app}/images/icons/delpay.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同正本：
		</td>
		<td class="content" colspan="3">
			<c:choose>
				<c:when test="${ not empty pnrFeeInfoMainForm.id }">
					<eoms:attachment name="pnrFeeInfoMainForm" property="contractAccessoriesId" 
           				scope="request" idField="contractAccessoriesId" appCode="feeInfo"/> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="contractAccessoriesId" appCode="feeInfo"/>
				</c:otherwise>
			</c:choose>
		</td>
		</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			相关附件：
		</td>
		<td class="content" colspan="3">
			<c:choose>
				<c:when test="${ not empty pnrFeeInfoMainForm.id }">
					<eoms:attachment name="pnrFeeInfoMainForm" property="accessoriesId" 
           				scope="request" idField="accessoriesId" appCode="feeInfo" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="accessoriesId" appCode="feeInfo"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>

	<tr>
			<td class="label">
				操作类型
			</td>
			<td class="content" colspan="3">
				<select id="operateType" name="operateType" >
					<option value="draft" >
						保存草稿
					</option>
					<option value="audit" selected="selected">
						提交审核
					</option>
				</select>
			</td>
		</tr>


</table>

<div id="auditDiv">
	<fieldset>
			<legend> 派发给:合同审核人 </legend> 
			<eoms:chooser id="taskOwner" type="user" config="{returnJSON:true,showLeader:true}"
			category="[{id:'taskOwner',text:'派发',childType:'user',allowBlank:false,vtext:'请选择审核对象'}]" />
		</fieldset>
</div>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="提交" onclick="sub();"/>&nbsp;&nbsp;<input type="button" class="btn" value="计算费用" onclick="scorePay();"	/>
		</td>
	</tr>
</table>
<html:hidden property="agreementId" value="${agreementId}" />
<html:hidden property="contractNew" value="${contractNew}" />
<html:hidden property="id" value="${pnrFeeInfoMainForm.id}" />
<html:hidden property="createTime" value="${pnrFeeInfoMainForm.createTime}" />
<html:hidden property="createUser" value="${pnrFeeInfoMainForm.createUser}" />
<html:hidden property="createDept" value="${pnrFeeInfoMainForm.createDept}" />
<html:hidden property="state" value="2" />
<html:hidden property="tableId" value="${tableId}"/>
<html:hidden property="themeId" value="${themeId}"/>
</html:form>
<script type="text/javascript">
var myJ=jQuery.noConflict();


Ext.onReady(function() {

	//Bind jquery function. todoo
		myJ('#operateType').bind('change',function(event){
		if(this.value == 'draft'  ){
			myJ('#auditDiv').hide();
			chooser_taskOwner.disable();
		}else{
			myJ('#auditDiv').show();
			chooser_taskOwner.enable();
		}
	});
	
	

	
	v = new eoms.form.Validation({form:'pnrFeeInfoMainForm'});
});

	var planStr = document.getElementById("planTemplate").innerHTML;
	
	
	//document.getElementById("planTemplate").removeNode(true);
	myJ('#planTemplate').children().remove();
	
	var listSize = '${planListsize}';
	var count=1;
	
	if(listSize==0){
		count = 1;
	} else {
		count = listSize+1;
	}
	
function addPlan() {
    count = account('+',1,count);
    var planStrAdd = planStr.replace("planWeight1","planWeight"+count);
    planStrAdd = planStrAdd.replace("payMoney1","payMoney"+count);
	Ext.DomHelper.append('planDiv', 
		{tag:'div', 			
		html:planStrAdd
        }
    );
}

function scorePay(){
	var planWeights = document.getElementsByName("planWeight");
	var contractAmount = document.getElementById("contractAmount").value;
	var planWeightCount = 0;
	for(var i = 1 ;planWeights.length>=i ; i++){
		var planWeightId = planWeights[i-1].id;
		planWeightValue = document.getElementById(planWeightId).value;
		if(i==1){
			planWeightCount = planWeightValue;
		} else {
			planWeightCount = account('+',planWeightCount,planWeightValue);
		}
		var tempRes = 0;
		var result = 0;
		if(contractAmount!=''){
			tempRes = account('*',contractAmount,planWeightValue);
			result = account('/',tempRes,100);
		}
		document.getElementById("payMoney"+planWeightId.substring(10)).innerHTML = '需付金额：'+result+'(万元)';
	}
	if(planWeightCount==100){
	
	}else{
		alert("付费总值不符合要求（比例总值应为100%）");
		return false;
	}
	return true;
}
/** 
*实现两个数据的简单计算；计算结果进行四舍五入，只保留小数据点后两位； 
*num1，第一个数据。如果是减法与除法；则作为被减数、被除数； 
*num2，第二个数据。如果是减法与除法：则作为减数、除数； 
*str，运算符号，目前只接受:+、-、*、/:分别对应：加法、减法、乘法、除法； 
*返回计算结果；计算结果进行四舍五入，只保留小数据点后两位； 
*/ 
function account(str,num1,num2){ 
	if(num1!=""&&num2!=""&&str!=""){ 
		num1 = parseFloat(num1); 
		num2= parseFloat(num2); 
		var rs=0.0; 
		  if(str=="+"){ 
		   rs=num1+num2; 
		   return (Math.round(rs*100)/100);
		   //保保留小数点后两位数； 
		   //如果要保留三位则改为:Math.round(rs*1000)/1000; 
		   //如果要保留四位则改为:Math.round(rs*10000)/10000;.....以次类推
		  } 
		  if(str=="-"){ 
		   rs=num1-num2; 
		   return (Math.round(rs*100)/100); 
		  } 
		  if(str=="*"){ 
		   rs=num1*num2; 
		   return (Math.round(rs*100)/100); 
		  } 
		  if(str=="/"){ 
		   if(num2!="0"){ 
		    rs=num1/num2; 
		    return (Math.round(rs*100)/100); 
		   }else{ 
		    alert("Error"); 
		   } 
		  } 
	} 
} 

function removeNodes(obj) {
	myJ(obj).parent().parent().parent().remove();
   	//obj.removeNode(true);  
	}
function changeRootId(xbox_obj,rootId) {   
	xbox_obj.defaultTree.root.id = rootId; 
	xbox_obj.reset();
	}
function sub(){
	if(scorePay()){
	}else{
		return false;
	}
	if(v.check()){
       $("pnrFeeInfoMainForm").submit();
	}	
}	
function changeAbilityType() { 
  	document.getElementById("planDiv").innerHTML = '';
  	if(document.getElementById("abilityType").value=='Proxy'){
	for(var i=1;i<14;i++){
		if(i==13){
			Ext.DomHelper.append('planDiv', {tag:'div',html:'考核款'})
        }else{
        	Ext.DomHelper.append('planDiv', {tag:'div',html:'第'+i+'月'})
        	}
		addPlan();
	}
	var buttonDiv = document.all.item("buttonDiv");
	for(var i = 1 ;buttonDiv.length>=i ; i++){
		buttonDiv[i-1].innerHTML = '';
	}
	document.getElementById("fbuttonDiv").innerHTML = ''
	}else{
		document.getElementById("fbuttonDiv").innerHTML = '<img align=right src=\"${app}/images/icons/addpay.gif\" alt=\"添加付费计划子项\" onclick=\"javascript:addPlan();\" /></div>'
	}
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>