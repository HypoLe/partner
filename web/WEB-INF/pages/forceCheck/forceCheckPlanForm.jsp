<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.forceCheck.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
<script type="text/javascript">
Ext.onReady(function() {
 v = new eoms.form.Validation({form:'pnrForceCheckPlanForm'});
});
function addTask(){
	var url=eoms.appPath+'/forceCheck/forceCheckPlan.do?method=addTask';
	location.href=url}
function addTaskForm(){
    var date1 = document.getElementsByName("startDate")[0].value;
    var date2 = document.getElementsByName("endDate")[0].value;
    var form = document.getElementById("pnrforceCheckTaskForm");
    if('${pnrForceCheckPlanForm.id}'){
    v1 = new eoms.form.Validation({form:'forceCheckTaskForm'});
	if(!v1.check()) {
		return;
	}
    form["planId"].value = '${pnrForceCheckPlanForm.id}';
    form["taskName"].value = document.getElementById("taskName").value;
    form["startTime"].value = document.getElementById("startTime").value;
    form["endTime"].value = document.getElementById("endTime").value;
    form["pollingMan"].value = document.getElementById("pollingMan").value;
    form["siteName"].value = document.getElementById("siteName").value;
    if(form["startTime"].value < date1){
        alert("任务开始时间不能早于计划开始时间");
        return false;
    }
    if(form["startTime"].value > date2){
        alert("任务开始时间不能晚于任务结束时间");
        return false;
    }    
    if(form["endTime"].value < form["startTime"].value){
        alert("任务结束时间不能早于任务开始时间");
        return false;
    }
    if(form["endTime"].value > date2){
        alert("任务结束时间不能晚于任务结束时间");
        return false;
    }
	form.submit(); 
	}else{
	alert("当前计划尚未保存，请先保存");
	}  
}	
function dateCompare(date1,date2){ // 比较date1是否比date2大,如果是返回true.
	if(date1==null||date1.length<16) {
		return false;
	} 
	if(date2==null||date2.length<16) {
		return true;
	} 
	d1=new Date(date1.replace(/-/,"/"));
	d2=new Date(date2.replace(/-/,"/"));     
			
	//比较时间大小，开始时间一定要小于结束时间  
	if(Date.parse(d1)>Date.parse(d2)) {	
		return true;        
	} else {
		return false;
	}
}
function submitForm(){
    var taskIsSubmit = '${taskIsSubmit}';
    if(taskIsSubmit){
      if(taskIsSubmit=="yes"){
         var planId = '${pnrForceCheckPlanForm.id}';
	     var url=eoms.appPath+'/forceCheck/forceCheckPlan.do?method=submit&planId='+planId;
	     location.href=url}else{
	     alert("您还有尚未完成提交的任务，请先提交");
	     	return false;
	     }
	
	}else{
	alert("该计划还没有盯防任务，请先指定盯防任务。");
	return false;
	}
}
</script>
<eoms:xbox id="pollingManTree" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText="巡检负责人" 
	  	valueField="pollingMan" handler="pollingManName"
		textField="pollingManName"
		checktype="user" single="true"
		data='${pollingManData}'></eoms:xbox>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<fmt:bundle basename="com/boco/eoms/forceCheck/config/applicationResources-forceCheck"> 
<html:form action="/forceCheckPlan.do?method=save" styleId="pnrForceCheckPlanForm" method="post">
	<html:hidden name="pnrForceCheckPlanForm" property="id" />
   <table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="forceCheckPlan.form.title"/></div>
	</caption>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.planName" />&nbsp;*	
		</td>
		<td class="content" colspan="3">
		<html:text property="planName" styleId="planName"
						styleClass="text max" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckPlanForm.planName}" />
		</td>
	  </tr>	 
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckRoute" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="forcecheckRoute" styleId="forcecheckRoute"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckPlanForm.forcecheckRoute}" />
		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckRouteStage" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="forcecheckRouteStage" styleId="forcecheckRouteStage"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckPlanForm.forcecheckRouteStage}" />
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckPlace" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="forcecheckPlace" styleId="forcecheckPlace"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckPlanForm.forcecheckPlace}" />
		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckGpsFacility" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="forcecheckGpsFacility" styleId="forcecheckGpsFacility"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckPlanForm.forcecheckGpsFacility}" />
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.startDate" />&nbsp;*
		</td>
		<td class="content">
		<input type="text" name="startDate" size="20" value="${pnrForceCheckPlanForm.startDate}" onclick="popUpCalendar(this, this,null,null,null,true,0);" 
		       alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间！'"
					 id="startDate"readonly="true"  class="text"/>

		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.endDate" />&nbsp;*
		</td>
		<td class="content">
		<input type="text" name="endDate" size="20" value="${pnrForceCheckPlanForm.endDate}" onclick="popUpCalendar(this, this,null,null,null,true,0);" 
					alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间！'"
					 id="endDate"  readonly="true"  class="text"/>
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.planTime" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="planTime" styleId="planTime"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckPlanForm.planTime}" />
		</td>
	   </tr>
	   <tr> 	
        <td class="label">
			<fmt:message key="forceCheckPlan.planContent" />&nbsp;*
		</td>
		<td class="content" colspan="3">
		<html:textarea property="planContent" styleId="planContent"
						styleClass="text max" onblur="testCharSize(this,255);" cols="40" rows="4"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckPlanForm.planContent}" />
		</td>
	  </tr> 
      </table>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty pnrForceCheckPlanForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.remove"/>" 
					onclick="javascript:if(confirm('确认删除?')){
						var url=eoms.appPath+'/forceCheck/forceCheckPlan.do?method=remove&id=${pnrForceCheckPlanForm.id}';
						location.href=url}"	/>		
			</c:if>							
		</td>
		<td>
		    <input type="button" class="btn" value="<fmt:message key="button.submit"/>" onclick="submitForm();"/>
		</td>
	</tr>
</table>
</html:form>
<html:form action="/forceCheckPlan.do?method=addTask" styleId="forceCheckTaskForm" method="post" >
<table class="listTable">
<caption>
    <div class="header center"><fmt:message key="forceCheckTask.form.title" /></div>
</caption>
      <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.taskName" />&nbsp;*	
		</td>
		<td class="content" colspan="3">
		<html:text property="taskName" styleId="taskName"
						styleClass="text max" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckTaskForm.taskName}" />
		</td>
	  </tr>	
      <tr>
          <td class="label">
            <fmt:message key="forceCheckTask.startTime" />
          </td>
          <td class="content">
            <input type="text" name="startTime" size="20" VALUE='${pnrForceCheckTaskForm.startTime}' onclick="popUpCalendar(this, this,null,null,null,true,-1);" 
                   id="startTime" readonly="true"  class="text">
          </td>
          <td class="label">
            <fmt:message key="forceCheckTask.endTime" />
          </td>
          <td class="content">
            <input type="text" name="endTime" size="20" VALUE='${pnrForceCheckTaskForm.endTime}' onclick="popUpCalendar(this, this,null,null,null,true,-1);" 
                   id="endTime" readonly="true"  class="text">
          </td>
      </tr>
      	  <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.siteName" />&nbsp;*
		</td>
		<td class="content">
		<html:text property="siteName" styleId="siteName"
						styleClass="text medium" onblur="testCharSize(this,32);"
						alt="allowBlank:false,vtext:''" value="${pnrForceCheckTaskForm.siteName}" />
		</td>
        <td class="label">
			<fmt:message key="forceCheckTask.pollingMan" />&nbsp;*
		</td>
		<td class="content">
			<input type="hidden" id="pollingMan" name="pollingMan" value="${pnrForceCheckTaskForm.pollingMan}" />
			<input class="input text" type="text" name="pollingManName" id="pollingManName" 
						alt="allowBlank:false,vtext:'请选择巡检负责人'" value="<eoms:id2nameDB id="${pnrForceCheckTaskForm.pollingMan}" beanId="tawSystemUserDao" />" readonly="true" />
		</td>
	  </tr>
</table>
</html:form>
<table>
	<tr>
		<td>
		    <input type="button" class="btn" value="<fmt:message key="forceCheckPlan.addtask"/>" onclick="addTaskForm();"/>
		</td>
	</tr>
</table>      
<display:table  name="pnrForceCheckTaskList" cellspacing="0" cellpadding="0"
      id="pnrForceCheckTaskList"  pagesize="${pageSize}" class="table pnrForceCheckTaskList" 
      export="false" 
      requestURI="${app}/forceCheck/forceCheckTask.do?method=search"
      sort="list" partialList="true" size="resultSize">
      <display:column property="taskName" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.taskName" />
	  <display:column property="siteName" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.siteName" />
	  <display:column property="pollingMan" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.pollingMan" />
	  <display:column property="startTime" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.startTime"  format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
	  <display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.endTime"  format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
      <display:column sortable="true" headerClass="sortable" title="任务状态">
			<c:if test="${pnrForceCheckTaskList.status eq '0'}">
				新建状态
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '1'}">
				任务执行中
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '2'}">
				任务归档
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '-1'}">
				计划取消或终止
			</c:if>
		</display:column>
	   <display:column sortable="false" headerClass="sortable" title="编辑"
			 media="html">
			 <c:if test="${pnrForceCheckTaskList.status=='0'}">
			<a id="${pnrForceCheckTaskList.id }"
				href="${app }/forceCheck/forceCheckTask.do?method=audit&id=${pnrForceCheckTaskList.id}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>
				</c:if>
				 <c:if test="${pnrForceCheckTaskList.status!='0'}">
				无
				 </c:if>
		</display:column>		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${pnrForceCheckTaskList.id }"
				href="${app }/forceCheck/forceCheckTask.do?method=detail&id=${pnrForceCheckTaskList.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>		
		<display:column sortable="false" headerClass="sortable"
			title="删除" media="html">
			 <c:if test="${pnrForceCheckTaskList.status=='0'}">
			<a id="${pnrForceCheckTaskList.id }"
				href="${app }/forceCheck/forceCheckTask.do?method=remove&id=${pnrForceCheckTaskList.id}"
				>
				<img src="${app }/images/icons/icon.gif">
				</a>
				</c:if>
				 <c:if test="${pnrForceCheckTaskList.status!='0'}">
				无
				 </c:if>
		</display:column>				
 
</display:table>


</fmt:bundle>
<div style="display: none">
	<html:form action="/forceCheckPlan.do?method=addTask" styleId="pnrforceCheckTaskForm" method="post" >
		<input type="hidden" name="id" value="">
		<input type="hidden" name="planId" value="">
		<input type="hidden" name="taskName" value="">
		<input type="hidden" name="startTime" value="">
		<input type="hidden" name="endTime" value="">
		<input type="hidden" name="pollingMan" value="">
		<input type="hidden" name="siteName" value="">
	</html:form>	
</div>
<%@ include file="/common/footer_eoms.jsp"%>