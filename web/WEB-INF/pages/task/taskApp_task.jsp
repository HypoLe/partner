<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.task.util.TaskConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/taskApp.do?method=searchApp'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<html:form action="/taskApp.do?method=searchApp_taskOne" styleId="taskForm" method="post" >
<fmt:bundle basename="com/boco/eoms/task/config/applicationResource-task">
	<display:table name="taskList" cellspacing="0" cellpadding="0" id="taskList" pagesize="${pageSize}" class="table taskList" export="false"
			requestURI="${app}/task/taskApp.do?method=searchApp_taskOne" sort="list" partialList="true" size="resultSize"
			decorator="com.boco.eoms.task.util.TaskDecorator">
			
			<logic:notEmpty name="taskList">		 
				<display:column property="taskId" titleKey="taskApp.choose" />
				<display:setProperty name="css.table" value="0,"/>
				</logic:notEmpty>
			
			<display:column property="name" sortable="true" headerClass="sortable" titleKey="taskApp.name"  paramId="taskId" paramProperty="taskId"/>
			
			<display:column property="content" sortable="true" headerClass="sortable" titleKey="taskApp.content"  paramId="taskId" paramProperty="taskId"/>
			
			<display:column property="progress" sortable="true" headerClass="sortable" titleKey="taskApp.progress"  paramId="taskId" paramProperty="taskId"/>
			
			<display:column property="endTime" sortable="true" headerClass="sortable" titleKey="taskApp.endTime" paramId="taskId" paramProperty="taskId"/>
					
	</display:table>
	
	<table><tr><td>
			<input type="button"  value="<fmt:message key="taskApp.button" />" 
			onclick="javascript:
						var objs=document.getElementsByName('ids');
						var a=0;
						var id='';
					    for(var i=0; i<objs.length; i++) {
					    	if(objs[i].type.toLowerCase() == 'checkbox' )
					    	if(objs[i].checked){
					    		a=a+1;
					    		id=objs[i].value;
					      }	
					    }
					    if(a==1){
								document.getElementById('taskOneId').src='${app}/task/taskApp.do?method=searchApp_taskOne&taskId='+id ;
					    		return true;
					    }else{
					    	 alert('请选择一个任务进行统计！！！');
					    }"/>
	</td></tr></table>
	
</fmt:bundle>

	<div id="taskApp">
			<iframe name="taskOne" id="taskOneId" frameborder="0" width="100%" height="100%" 
		   			src=""
		   			onload="document.all['taskOne'].style.height=taskOne.document.body.scrollHeight"> </iframe>
		</div>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>