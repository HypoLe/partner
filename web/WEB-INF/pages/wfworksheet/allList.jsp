<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%String token = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("Token")); %>
<logic:present name="taskCountList" scope="request"> 
	<logic:iterate id="obj" name="taskCountList" type="java.util.HashMap">
	 	<bean:write name="obj" property="sheetTypeName" scope="page"/>:
	 	<%
	 		String url = com.boco.eoms.base.util.StaticMethod.nullObject2String(obj.get("sheetTypeUrl"));
	 		String sheetType = com.boco.eoms.base.util.StaticMethod.nullObject2String(obj.get("sheetType"));
	 		
	 		
	 		if(sheetType.equals("commonfault")){//如果是故障工单，采用可以批量处理的url
	 			url = url+"?method=showListsendundo&batch=true"+token;
	 		}else{//其他工单，可以根据实际情况来拼url是多少，sheetType参考视图里定义的
	 			url = url+"?method=showListsendundo"+token;
	 		}
	 	 %>
	 	 <a href='<%=url%>' target='_blank'><bean:write name="obj" property="count" scope="page"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
	</logic:iterate>
</logic:present>


<bean:define id="Token" value="${token}"/>
<bean:define id="url" value="eomsallsheetlist.do"/>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="true" requestURI="eomsallsheetlist.do"
		sort="external" size="total" partialList="true" 
		decorator="com.boco.eoms.sheet.base.webapp.action.ProcessAllListDisplaytagDecoratorHelper">

		<display:caption media="html"> 
   			<span class="map alert">将要超时的工单</span>
			<span class="map serious">已经超时的工单</span>
   		</display:caption>
   		
		<display:column sortable="true" headerClass="sortable" title="" class="icon" media="html"/>

		 <display:column property="sheetId"
			 title="工单流水号" sortName="sheetId" sortable="true" />
		<display:column property="title"
			 title="工单主题" sortName="title" sortable="true" />
		<display:column property="sendTime"
			 title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" sortName="sendTime" sortable="true" />	
		<display:column property="completeTimeLimit" 
			 title="完成时限"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" sortName="completeTimeLimit" sortable="true" />
		<display:column property="taskDisplayName"
			 title="处理环节" sortName="taskName" sortable="true" />
		<display:column property="sheetTypeName" 
			 title="所属工单" sortName="sheetType"  sortable="true" />
		<display:column title="任务状态" >
			<c:if test="${taskList.ifWaitForSubTask=='true'}">
				<eoms:dict key="dict-sheet-common" dictId="taskStatus" itemId="-1" beanId="id2descriptionXML"/>
			</c:if>
			<c:if test="${empty taskList.ifWaitForSubTask||taskList.ifWaitForSubTask=='false'}">
				<eoms:dict key="dict-sheet-common" dictId="taskStatus" itemId="${taskList.taskStatus}" beanId="id2descriptionXML"/>
			</c:if>
		</display:column> 
        <display:column title="任务所有者">
			<eoms:id2nameDB id="${taskList.taskOwner}" beanId="tawSystemUserDao" />
		</display:column>
	
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>

<%@ include file="/common/footer_eoms.jsp"%>