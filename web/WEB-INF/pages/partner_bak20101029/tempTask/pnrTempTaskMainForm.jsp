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
						alt="allowBlank:false,vtext:''" value="${pnrTempTaskMainForm.tempTaskName}" />
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
						alt="allowBlank:false,vtext:''" value="${pnrTempTaskMainForm.startTime}" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" />
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrTempTaskMain.endTime" />
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium" style="width:96%;" 
						 value="${pnrTempTaskMainForm.endTime}" onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'startTime',vtext:'结束时间应晚于开始时间'" readonly="true" />
		</td>
	</tr>

	<tr>
	<td colspan="4">
	<span style="font-weight:bold;" >工作任务</span><img align=right src="${app}/images/icons/add.gif" alt="添加工作任务" onclick="javascript:addWork();" />
	<div id="workTemplate">
	<table class="formTable">
		<tr>	
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrTempTaskWork.workContent" />
			</td>
			<td class="content">
				<textarea name="workContent" id="workContent" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:false,vtext:''" ></textarea>			
				<html:hidden property="workId" value="" />
			</td>
			<td class="label" style="vertical-align:middle">
				工作完成标准
			</td>
			<td class="content">
				<textarea name="workStandard" id="workStandard" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:false,vtext:''" ></textarea>			
			</td>
		</tr>	
		
		<tr>	
			<td class="label" style="vertical-align:middle">
				工作任务类型
			</td>
			<td class="content">
					<eoms:dict key="dict-partner-agreement" dictId="workType" defaultId=""
						 selectId="workType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务类型'" />			
			</td>
			<td class="label" style="vertical-align:middle">
				工作任务执行周期
			</td>
			<td class="content">
					<eoms:dict key="dict-partner-agreement" dictId="workCycle" defaultId=""
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务执行周期'" />			
			</td>										
		</tr>	
		<!-- 
		<tr>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrTempTaskWork.startTime" />
			</td>
			<td class="content">
				<html:text property="workStartTime" styleId="workStartTime"
							styleClass="text medium" style="width:97%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="" />
			</td>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrTempTaskWork.endTime" />
			</td>
			<td class="content">
				<html:text property="workEndTime" styleId="workEndTime"
							styleClass="text medium" style="width:98%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="" />
			</td>
		</tr>
		 -->	
		<tr>		
			<td class="label" style="vertical-align:middle">
				考核指标名称
			</td>
			<td class="content" >
				<html:text property="workEvaName" styleClass="text medium"  style="width:98%;" value="" />
			</td>			
			<td class="label" style="vertical-align:middle">
				指标详细算法
			</td>
			<td class="content">
				<textarea name="workEvaContent" id="workEvaContent" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:true,vtext:''" ></textarea>			
			</td>		
		</tr>	
		<tr>
			<td class="label" style="vertical-align:middle">
				考核开始时间
			</td>
			<td class="content">
				<html:text property="workEvaStartTime" styleId="workEvaStartTime"
							styleClass="text medium" style="width:97%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="" />
			</td>
			<td class="label" style="vertical-align:middle">
				考核结束时间
			</td>
			<td class="content">
				<html:text property="workEvaEndTime" styleId="workEvaEndTime"
							styleClass="text medium" style="width:98%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="" />
			</td>			
		</tr>
		<tr>	
			<td class="label" style="vertical-align:middle">
				所占分数
			</td>
			<td class="content">
				<html:text property="workEvaWeight"  alt="allowBlank:false,vtext:''" styleId="workEvaWeight0" styleClass="text medium"  style="width:18%;" value=""  onblur="weightPay('workEvaWeight0')"/>&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'><span id="number0"></span></font>
			</td>
			<td class="label" style="vertical-align:middle">
				考核周期
			</td>
			<td class="content">
					<eoms:dict key="dict-eva" dictId="cycle" defaultId=""
						 selectId="workEvaCycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />			
			</td>					
		</tr>
			
	
<!-- 
		<tr>	
		<td class="label" style="vertical-align:middle"> 
			工作考核标准
		</td>
		<td class="content" colspan="3">
			<textarea name="evaStandard" id="evaStandard" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" ></textarea>			
		</td>
		</tr>
 -->		
		<tr>
			<th colspan="4"><img align=right src="${app}/images/icons/add.gif" alt="添加工作任务" onclick="javascript:addWork();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
		</tr>
		</table>
		
	</div>
	<div id="workDiv">
	<table class="formTable">
	<c:forEach var="work" items="${workList}" varStatus="stauts">
	<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
	</tr>
		<tr>	
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrTempTaskWork.workContent" />
			</td>
			<td class="content">
				<textarea name="workContent" id="workContent" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:false,vtext:''" >${work.workContent}</textarea>			
				<html:hidden property="workId" value="${work.id}" />
			</td>
			<td class="label" style="vertical-align:middle">
				工作完成标准
			</td>
			<td class="content">
				<textarea name="workStandard" id="workStandard" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:false,vtext:''" >${work.workStandard}</textarea>			
			</td>
		</tr>
		<tr>	
			<td class="label" style="vertical-align:middle">
				工作任务类型
			</td>
			<td class="content">
					<eoms:dict key="dict-partner-agreement" dictId="workType" defaultId="${work.workType}"
						 selectId="workType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务类型'" />			
			</td>
			<td class="label" style="vertical-align:middle">
				工作任务执行周期
			</td>
			<td class="content">
					<eoms:dict key="dict-partner-agreement" dictId="workCycle" defaultId="${work.cycle}"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务执行周期'" />			
			</td>										
		</tr>
		<!-- 		
		<tr>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrTempTaskWork.startTime" />
			</td>
			<td class="content">
				<html:text property="workStartTime" styleId="workStartTime"
							styleClass="text medium" style="width:97%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="${work.startTimeStr}" />
			</td>
			<td class="label" style="vertical-align:middle">
				<fmt:message key="pnrTempTaskWork.endTime" />
			</td>
			<td class="content">
				<html:text property="workEndTime" styleId="workEndTime"
							styleClass="text medium" style="width:98%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="${work.endTimeStr}" />
			</td>
		</tr>
		 -->
		<tr>		
				<td class="label" style="vertical-align:middle">
					考核指标名称
				</td>
				<td class="content">
					<html:text property="workEvaName" styleClass="text medium"  style="width:98%;" value="${work.workEvaName}" />
				</td>			
				<td class="label" style="vertical-align:middle">
					指标详细算法
				</td>
				<td class="content">
					<textarea name="workEvaContent" id="workEvaContent" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:true,vtext:''" >${work.workEvaContent}</textarea>			
				</td>		
		</tr>	
		<tr>
			<td class="label" style="vertical-align:middle">
				考核指标开始时间
			</td>
			<td class="content">
				<html:text property="workEvaStartTime" styleId="workEvaStartTime"
							styleClass="text medium" style="width:97%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="${work.workEvaStartTime}" />
			</td>
			<td class="label" style="vertical-align:middle">
				考核指标结束时间
			</td>
			<td class="content">
				<html:text property="workEvaEndTime" styleId="workEvaEndTime"
							styleClass="text medium" style="width:98%;" 
							 onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="${work.workEvaEndTime}" />
			</td>			
		</tr>		
		<tr>
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					<html:text property="workEvaWeight" alt="allowBlank:false,vtext:''"  styleId="workEvaWeight${stauts.count}"  styleClass="text medium"  style="width:18%;" value="${work.workEvaWeight}" onblur="weightPay('workEvaWeight${stauts.count}')"/>&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'><span id="number${stauts.count}"></span></font>
				</td>	
				<td class="label" style="vertical-align:middle">
					考核指标周期
				</td>
				<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" defaultId="${work.workEvaCycle}"
							 selectId="workEvaCycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核指标周期'" />			
				</td>						
		</tr>

		<tr>
			<th colspan="4"><img align=right src="${app}/images/icons/add.gif" alt="添加工作任务" onclick="javascript:addWork();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
		</tr>
	</c:forEach>
	</table>
	</div>
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
	var workStr = document.getElementById("workTemplate").innerHTML;
	document.getElementById("workTemplate").removeNode(true);
	
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
	if(weightPay()){
	}else{
		return false;
	}
	if(v.check()){
       $("pnrTempTaskMainForm").submit();
	}	
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>