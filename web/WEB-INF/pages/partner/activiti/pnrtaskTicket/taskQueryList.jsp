<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<logic:notPresent name="recordTotal">
<bean:define id="url" value="pnrTaskTicket.do" />
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="table businessdesignMain"
		export="true" requestURI="pnrTaskTicket.do"
		sort="list" size="total" partialList="true">
		
		<display:column  sortable="true"
			headerClass="sortable" title="工单流水号">
			<c:choose>
				<c:when test="${taskList.statusName  ne '已删除'}">
			<a href="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}">
			${taskList.processInstanceId}
			</a>
			</c:when>
				<c:otherwise>
				  ${taskList.processInstanceId}
				</c:otherwise>
			</c:choose>
			</display:column>
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="工单主题"/>
		<display:column sortable="true"
			headerClass="sortable" title="工单生成人">
			<eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/>     
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="执行人">
			<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao"/>     
		</display:column>
		<display:column property="statusName" sortable="true"
			headerClass="sortable" title="当前状态列"/>
				
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>

	</display:table>
</logic:notPresent>

<%@ include file="/common/footer_eoms.jsp"%>