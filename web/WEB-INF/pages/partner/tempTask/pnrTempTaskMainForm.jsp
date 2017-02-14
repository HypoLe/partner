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
<script type="text/javascript">
 var workNum = ${fn:length(workList)};
</script>
<html:form action="/pnrTempTaskMains.do?method=save" styleId="pnrTempTaskMainForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/tempTask/config/applicationResources-partner-tempTask">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrTempTaskMain.form.heading"/></div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.tempTaskName" />
		</td>
		<td class="content" colspan="3">
			<html:text property="tempTaskName" styleId="tempTaskName"
						styleClass="text"  style="width:98%;" 
						alt="allowBlank:false,vtext:'',maxLength:100" value="${pnrTempTaskMainForm.tempTaskName}" />
		</td>	
		<!-- 
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.tempTaskNO" />
		</td>
		<td class="content" >
			<html:text property="tempTaskNO" styleId="tempTaskNO"
						styleClass="text"  style="width:96%;" 
						alt="allowBlank:false,vtext:''" value="${pnrTempTaskMainForm.tempTaskNO}" />
		</td>
		 -->
	</tr>
	<html:hidden property="tempTaskNO" value="${pnrTempTaskMainForm.tempTaskNO}" />
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.startTime" />
		</td>
		<td class="content">
			<html:text property="startTime" styleId="startTime"
						styleClass="text medium" style="width:97%;" 
						alt="allowBlank:false,vtext:''" value="${pnrTempTaskMainForm.startTimeStr}" onclick="popUpCalendar(this, this,null,null,null,false,-1);" readonly="true" />
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.endTime" />
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium" style="width:96%;" 
						 value="${pnrTempTaskMainForm.endTimeStr}" onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtype:'moreThen',link:'startTime',vtext:'结束时间应晚于开始时间'" readonly="true" />
		</td>
	</tr>

	<tr>
	<td colspan="4">
	<span style="font-weight:bold;" >工作任务:</span>
	<div id="workDiv">
		<c:forEach var="work" items="${workList}"  varStatus="stauts">
			<div id="${work.id}">
			<table class="formTable">
						<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
			</tr>
				<tr>		
					<td class="label" style="vertical-align:middle" >
						工作内容
					</td>
					<td class="content">
						${work.workContent}
						<html:hidden property="workContent" value="${work.workContent}" />
					</td>
					<td class="label" style="vertical-align:middle" >
						执行人
					</td>
					<td class="content">
							${work.toOrgUserName}
						<html:hidden property="toOrgUserName" value="${work.toOrgUserName}" />
						<html:hidden property="toOrgUser" value="${work.toOrgUser}" />
					</td>
					</tr>
					<tr>
					<td class="label" style="vertical-align:middle" >
						工作完成标准
					</td>
					<td class="content" colspan="3">
							${work.workStandard}
						<html:hidden property="workStandard" value="${work.workStandard}" />
					</td>				
				</tr>
				<tr>		
					<td class="label" style="vertical-align:middle">
						工作任务类型
					</td>
					<td class="content" >
							<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />
						<html:hidden property="workType" value="${work.workType}" />				
					</td>
					<td class="label" style="vertical-align:middle">
						工作任务执行周期
					</td>
					<td class="content">
						<eoms:dict key="dict-partner-agreement" dictId="workCycle" itemId="${work.cycle}" beanId="id2nameXML" />
						<html:hidden property="workCycle" value="${work.cycle}" />	
					</td>	
				</tr>	
				<tr>		
						<td class="label" style="vertical-align:middle">
							考核指标名称
						</td>
						<td class="content">
							${work.workEvaName}
						<html:hidden property="workEvaName" value="${work.workEvaName}" />
						</td>		
						<td class="label" style="vertical-align:middle">
							算法分类
						</td>
						<td class="content">
							<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${work.algorithmType}" beanId="id2nameXML" />
						<html:hidden property="algorithmType" value="${work.algorithmType}" />
						</td>	
				</tr>
				<tr>
						<td class="label" style="vertical-align:middle">
							指标详细算法
						</td>
						<td class="content" colspan="3">
							${work.workEvaContentByType}
						<html:hidden property="workEvaContent" value="${work.workEvaContent}" />
						</td>						
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						考核开始时间
					</td>
					<td class="content">
						${work.workEvaStartTimeStr}
						<html:hidden property="workEvaStartTime" value="${work.workEvaStartTimeStr}" />
					</td>
					<td class="label" style="vertical-align:middle">
						考核结束时间
					</td>
					<td class="content">
						${work.workEvaEndTimeStr}
						<html:hidden property="workEvaEndTime" value="${work.workEvaEndTimeStr}" />			
					</td>			
				</tr>
				<tr>		
					<td class="label" style="vertical-align:middle">
						所占分数
					</td>
					<td class="content">
							${work.workEvaWeight}
						<html:hidden property="workEvaWeight" value="${work.workEvaWeight}" />
					</td>	
					<td class="label" style="vertical-align:middle">
						考核周期
					</td>
					<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" itemId="${work.workEvaCycle}" beanId="id2nameXML" />
						<html:hidden property="workEvaCycle" value="${work.workEvaCycle}" />
						<img align=right src="${app}/images/icons/delwork.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
						<img align=right src="${app}/images/icons/editwork.gif" alt="编辑工作任务" onclick="javascript:show_windowsWork('${app}/partner/tempTask/pnrTempTaskMains.do?method=createWork','','${work.id}');" />
					</td>	
				</tr>
				<html:hidden property="workId" value="${work.id}" />
				</table>
				<hr />
			</div>
		</c:forEach>	
	</div>
	<img align=right src="${app}/images/icons/addwork.gif" alt="添加工作任务" onclick="javascript:show_windowsWork('${app}/partner/tempTask/pnrTempTaskMains.do?method=createWork','toolbar=no,scrollBars=yes,width=800,height=450','');" />
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.accessories" />
		</td>
		<td class="content" colspan="3">
			<c:choose>
				<c:when test="${ not empty pnrTempTaskMainForm.id }">
					<eoms:attachment name="pnrTempTaskMainForm" property="accessoriesId" 
           				scope="request" idField="accessoriesId" appCode="tempTask" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="accessoriesId" appCode="tempTask"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
<tr id='userTree'>	
		<%
		String toOrgPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept', rootId:'2'}]";
		String toAuditOrgPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept', rootId:'1'}]";
		String toEvaOrPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept', rootId:'1'}]";
		%>	
<td class="label">
	执行人
</td>
<td class="">
		<eoms:chooser id="toOrg" category="[{id:'toOrg',text:'执行',allowBlank:false,limit:1,vtext:'请选择执行人'}]" panels="<%=toOrgPanels%>"/>
</td>
<td class="label">
	审核人
</td>
<td class="" >
		<eoms:chooser id="toAuditOrg" category="[{id:'toAuditOrg',text:'审核',allowBlank:false,limit:1,vtext:'请选择审核人'}]" panels="<%=toAuditOrgPanels%>"/>
</td>
</tr>
<tr>
<td class="label">
	考核人
</td>
<td class="" colspan="3">
		<eoms:chooser id="toEvaOrg" category="[{id:'toEvaOrg',text:'考核',allowBlank:false,limit:1,vtext:'请选择考核人'}]" panels="<%=toEvaOrPanels%>"/>
</td>
</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="提交" onclick="sub();"/>			
			<!-- 
			<c:if test="${not empty pnrTempTaskMainForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/pnrTempTaskMain/pnrTempTaskMains.do?method=remove&id=${pnrTempTaskMainForm.id}';
						location.href=url}"	/>
			</c:if>
			 -->
		</td>
	</tr>
</table>
<html:hidden property="id" value="${pnrTempTaskMainForm.id}" />
<html:hidden property="createTime" value="${pnrTempTaskMainForm.createTime}" />
<html:hidden property="createUser" value="${pnrTempTaskMainForm.createUser}" />
<html:hidden property="createDept" value="${pnrTempTaskMainForm.createDept}" />
<html:hidden property="state" value="${pnrTempTaskMainForm.state}" />
</html:form>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrTempTaskMainForm'});
});
	
	var listSize = '${workListSize}';
	var count=0;
	
	if(listSize==0){
		count = 0;
	} else {
		count = listSize;
	}	
	
function addWork() {
	if(count==0){
		count = 1;
	}else{
		count = account('+',1,count);
	}
    var workStrAdd = workStr.replace('workEvaWeight0','workEvaWeight'+count);
    workStrAdd = workStrAdd.replace('workEvaWeight0','workEvaWeight'+count);
    workStrAdd = workStrAdd.replace("number0","number"+count);
	Ext.DomHelper.append('workDiv', 
		{tag:'div', 			
		html:workStrAdd
        }
    );
}

function removeNodes(obj) {   
   	obj.removeNode(true);  
	}
	
function weightPay(workEvaWeightIdThis){
	var workEvaWeights = document.getElementsByName("workEvaWeight");
	var planWeightCount = 0 ;
	for(var i = 1 ;workEvaWeights.length>=i ; i++){
		var workEvaWeightId = workEvaWeights[i-1].id;
		workEvaWeightValue = document.getElementById(workEvaWeightId).value;
		if(workEvaWeightValue!=0&&workEvaWeightValue!=''){
			if(i==1){
				planWeightCount = workEvaWeightValue;
			} else {
				planWeightCount = account('+',planWeightCount,workEvaWeightValue);
			}
		}
	}	
	if(planWeightCount>100){
		if(workEvaWeightIdThis!=''){
			var workEvaWeightValueThis = document.getElementById(workEvaWeightIdThis).value;
			var outValue = account('-',planWeightCount,100);
			outValue = account('-',workEvaWeightValueThis,outValue);
			span();
			document.getElementById(workEvaWeightIdThis).value = '';
			document.getElementById("number"+workEvaWeightIdThis.substring(13)).innerHTML = '累计分数大于100分，请重新填写。';
		}
		return false;
	}else{
		if(planWeightCount<100&&workEvaWeightIdThis==null){
			alert("累计分数不足100分，请补充");
			return false;
		}
		span();
	}
	return true;
}
/** 
* 清除所有span的值
*/ 
function span(){
	var planWeights = document.getElementsByName("workEvaWeight");
	for(var i = 1 ;planWeights.length>=i ; i++){
		var planWeightId = planWeights[i-1].id;
		document.getElementById("number"+planWeightId.substring(13)).innerHTML = '';
	}	
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
function sub(){
	
	if(v.check()){
       $("pnrTempTaskMainForm").submit();
	}	
}

function show_windowsWork(myurl,props,tableId)
{  
  var newWindow;
  var temp_props = props;
  var workEvaWeights = document.getElementsByName("workEvaWeight");
  var toOrgId = '';
  var editWeight ='';//要编辑的记录权重
  var allWeight = 100;
  for(var i = 0 ;i<workEvaWeights.length ; i++){
  	if(allWeight==0||workEvaWeights[i].value==0){
  		allWeight = allWeight - parseFloat(workEvaWeights[i].value);
  	} else {
 	 	allWeight = account('-',allWeight,workEvaWeights[i].value);
  	}
  }
  if(tableId!=''){
  	editWeight = document.getElementById(tableId).getElementsByTagName("input")[11].value;
  	
  	if(allWeight==0||editWeight.value==0){
  		allWeight = allWeight + parseFloat(editWeight);
  	} else {
 	 	allWeight = account('+',allWeight,editWeight);
  	}  	
  	toOrgId = document.getElementById(tableId).getElementsByTagName("input")[2].value;
  }
  myurl = myurl+'&allWeight='+allWeight+'&tableId='+tableId+'&toOrgId='+toOrgId;
  newWindow = window.open(myurl,'',temp_props);
 
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>