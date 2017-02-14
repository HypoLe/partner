<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.task.util.TaskConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
  	body{background-image:none;}
</style>

<fmt:bundle basename="com/boco/eoms/task/config/applicationResource-task">
<content tag="heading">
	统计结果：
</content>
<html:form action="/taskApp.do?method=searchApp" styleId="taskForm" method="post" >
	<display:table name="taskList" cellspacing="0" cellpadding="0" id="taskList" pagesize="${pageSize}" class="table taskList" export="false"
			requestURI="${app}/task/taskApp.do?method=searchApp" sort="list" partialList="true" size="resultSize"
			decorator="com.boco.eoms.task.util.TaskDecorator">
			
			<display:column property="name" sortable="true" headerClass="sortable" titleKey="taskApp.name"  paramId="id" paramProperty="id"/>
			
			<display:column property="content" sortable="true" headerClass="sortable" titleKey="taskApp.content"  paramId="id" paramProperty="id"/>
			
			<display:column property="progress" sortable="true" headerClass="sortable" titleKey="taskApp.progress"  paramId="id" paramProperty="id"/>

			<display:column property="inTimeProgress" sortable="true" headerClass="sortable" titleKey="taskApp.inTimeProgress"  paramId="id" paramProperty="id"/>
			
			<display:column property="endTime" sortable="true" headerClass="sortable" titleKey="taskApp.endTime" paramId="id" paramProperty="id"/>
					
	</display:table>
	
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>