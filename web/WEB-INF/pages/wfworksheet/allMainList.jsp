<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%String token = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("Token")); %>
<logic:present name="ifDoneList" scope="request"> 
<logic:present name="taskCountList" scope="request"> 
	<logic:iterate id="obj" name="taskCountList" type="java.util.HashMap">
	 	<bean:write name="obj" property="sheetTypeName" scope="page"/>:
	 	<%
	 		String url = com.boco.eoms.base.util.StaticMethod.nullObject2String(obj.get("sheetTypeUrl"));
	 		String sheetType = com.boco.eoms.base.util.StaticMethod.nullObject2String(obj.get("sheetType"));
	 		
	 		if(sheetType.equals("commonfault")){//
	 			url = url+"?method=showListsenddone"+token;
	 		}else{//
	 			url = url+"?method=showListsenddone"+token;
	 		}
	 	 %>
	 	 <a href='<%=url%>' target='_blank'><bean:write name="obj" property="count" scope="page"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
	</logic:iterate>
</logic:present>
</logic:present>

<logic:notPresent name="ifDoneList" scope="request">

<logic:present name="taskCountList" scope="request"> 
	<logic:iterate id="obj" name="taskCountList" type="java.util.HashMap">
	 	<bean:write name="obj" property="sheetTypeName" scope="page"/>:
	 	<%
	 		String url = com.boco.eoms.base.util.StaticMethod.nullObject2String(obj.get("sheetTypeUrl"));
	 		String sheetType = com.boco.eoms.base.util.StaticMethod.nullObject2String(obj.get("sheetType"));
	 		
	 		if(sheetType.equals("commonfault")){//如果是故障工单，采用可以批量处理的url
	 			url = url+"?method=showOwnStarterList"+token;
	 		}else{//其他工单，可以根据实际情况来拼url是多少，sheetType参考视图里定义的
	 			url = url+"?method=showOwnStarterList"+token;
	 		}
	 	 %>
	 	 <a href='<%=url%>' target='_blank'><bean:write name="obj" property="count" scope="page"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
	</logic:iterate>
</logic:present>
</logic:notPresent>


<bean:define id="url" value="eomsallsheetlist.do"/>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="true" requestURI="eomsallsheetlist.do"
		sort="external" size="total" partialList="true" 
		decorator="com.boco.eoms.sheet.base.webapp.action.AllMainListDisplaytagDecoratorHelper">

		 <display:column property="sheetId" sortable="true"
		sortName="sheetId" headerClass="sortable" title="工单流水号" />

	<display:column property="title" sortable="true" sortName="title"
		headerClass="sortable" title="工单主题" />
	<display:column property="sendTime" sortable="true"
		sortName="sendTime" headerClass="sortable" title="派单时间"
		format="{0,date,yyyy-MM-dd HH:mm:ss}" />
	<display:column property="sheetAcceptLimit" sortable="true"
		sortName="sheetAcceptLimit" headerClass="sortable" title="受理时限"
		format="{0,date,yyyy-MM-dd HH:mm:ss}" />

	<display:column property="sheetCompleteLimit" sortable="true"
		sortName="sheetCompleteLimit" headerClass="sortable" title="完成时限"
		format="{0,date,yyyy-MM-dd HH:mm:ss}" />

	<display:column sortable="true" headerClass="sortable" title="工单状态"
		sortName="status">
		<eoms:dict key="dict-sheet-common" dictId="sheetStatus"
			itemId="${taskList.status}" beanId="id2descriptionXML" />
	</display:column>
		<display:column property="sheetTypeName" 
			 title="所属工单" sortName="sheetType"  sortable="true" />
		
	
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>

<%@ include file="/common/footer_eoms.jsp"%>