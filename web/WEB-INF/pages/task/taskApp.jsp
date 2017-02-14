<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.eoms.base.util.*,com.boco.eoms.task.mgr.*,java.util.*,com.boco.eoms.commons.system.session.form.*,com.boco.eoms.task.model.*"  %>
<fmt:bundle basename="com/boco/eoms/task/config/applicationResource-task">

<script type="text/javascript">
	Ext.onReady(function(){
			
		
		var tabs1 = new Ext.TabPanel('tabs');
		
	    var taskPendTab = tabs1.addTab('taskApp_person', "按人统计");
	    taskPendTab.setUrl('${app}/task/taskApp.do?method=searchApp', null, true);
	    
	    var taskHistoryTab = tabs1.addTab('taskApp_task', "按大任务统计");
	    var updater = taskHistoryTab.getUpdateManager();
	    updater.setDefaultUrl('${app}/task/taskApp.do?method=searchApp_task');
	    taskHistoryTab.on('activate', updater.refresh, updater, true);
	    
	    tabs1.activate('taskApp_person');	
	});
	
	function setSrc(){
		var principal = document.getElementById('principal').value;
		var startTime = document.getElementById('startTime').value;
		var endTime = document.getElementById('endTime').value;
		document.getElementById('personId').src="${app}/task/taskApp.do?method=searchApp_person&principal="+principal+"&startTime="+startTime+"&endTime="+endTime;
	}
</script>

<div id="tabs">
	<!-- 任务统计（某时间某人参与的任务与完成率） start-->
	<div id="taskApp_person">
		<html:form action="/taskApp.do?method=searchApp_person" styleId="taskForm" method="post" >
		 <table class="formTable">
		  <tr>
				
				<td >
				<select id="principal" name="principal">
					<option value="">请选择</option>
					<%
						IEomsTask eomstask = (IEomsTask) ApplicationContextHolder.getInstance().getBean("IEomsTaskManager");
						TawSystemSessionForm sessionform = (TawSystemSessionForm)request.getSession().getAttribute("sessionform");
						Eoms_Task_User eomsTaskUser = null;
						List list = eomstask.getEomsTaskUser(sessionform.getUserid());
						for (int i = 0; i < list.size(); i++) {
							eomsTaskUser = (Eoms_Task_User) list.get(i);
					 %>
						
					    <option value="<%=eomsTaskUser.getUserid() %>">
					    <%=eomsTaskUser.getUsername() %>
					
					<%} %>
					 </select>
				</td>
			<td class="label">
					<fmt:message key="taskApp.startTime" />
				</td>
				<td >
					<input type="text" id="startTime" name="startTime" styleId="startTime"styleClass="text medium"
								onclick="popUpCalendar(this, this,null,null,null,false,-1);" readonly="true"/>
				</td>
			<td class="label">
					<fmt:message key="taskApp.endTime"/>
				</td>
				<td >
					<input type="text" id="endTime" name="endTime" styleId="endTime"styleClass="text medium"
								onclick="popUpCalendar(this, this,null,null,null,false,-1);"  readonly="true"/>
				</td>		
		  </tr>
		 </table>
		
			<table>
			<tr>
				<td>
					<input type="button" class="btn" value="<fmt:message key="taskApp.button" />" onclick="setSrc();"/>
				</td>
			</tr>
			</table>
		 </html:form>
		<iframe name="person" id="personId" frameborder="0" width="100%" height="100%" 
		   			src=""
		   			onload="document.all['person'].style.height=person.document.body.scrollHeight"> </iframe>
	</div>
	<!-- 任务统计（某时间某人参与的任务与完成率） end-->
	
	<!-- 任务统计（某大任务下的所有及时率完成率） start-->
		<div id="taskApp_task">
			
			<iframe name="task" id="taskId" frameborder="0" width="100%" height="100%" 
		   			src=""
		   			> </iframe>
		</div>
	<!-- 任务统计（某大任务下的所有及时率完成率） end-->
</div>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>