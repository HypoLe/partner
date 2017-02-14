<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<logic:notPresent name="recordTotal">
<bean:define id="url" value="pnrTransferInterface.do?method=workOrderQuery" />
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="table businessdesignMain"
		export="true" requestURI="pnrTransferInterface.do?method=workOrderQuery"
		sort="list" size="total" partialList="true">
		
		<display:column  sortable="true"
			headerClass="sortable" title="工单号">
				<c:choose>
				<c:when test="${taskList.statusName  ne '已删除' && taskType eq '1012201'}">
				<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}"  target="_blank">
		        ${taskList.processInstanceId}
		        </a>
				</c:when>
				<c:when test="${taskList.statusName  ne '已删除' && taskType eq '1011101'}">
				<a href="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
				${taskList.processInstanceId}
				</a>
				</c:when>
				<c:when test="${taskList.statusName  ne '已删除' && taskType eq '1012301'}">
				<a href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
				${taskList.processInstanceId}
				</a>
				</c:when>
				<c:otherwise>
				  ${taskList.processInstanceId}
				</c:otherwise>
				</c:choose>
			</display:column>
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="主题" maxLength="15"/>
		<display:column sortable="true"
			headerClass="sortable" title="处理人">
			  <eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/> 
		</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="所属部门">
			  <eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/> 
		</display:column>	
		<display:column property="sendTime" sortable="true" 
			headerClass="sortable" title="时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="statusName" sortable="true"
			headerClass="sortable" title="当前状态"/>	
			
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>

	</display:table>
</logic:notPresent>

<%@ include file="/common/footer_eoms.jsp"%>