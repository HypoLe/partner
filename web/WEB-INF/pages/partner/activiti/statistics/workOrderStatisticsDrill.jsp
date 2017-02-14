<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<logic:notPresent name="recordTotal">
<bean:define id="url" value="pnrStatistics.do" />
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="table businessdesignMain"
		export="false" requestURI="pnrStatistics.do"
		sort="list" size="total" partialList="true">
		
		
		<display:column sortable="true"
			headerClass="sortable" title="工单号">
			<c:if test="${taskList.flag == 1}">
			<a href="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
				${taskList.processInstanceId} 
			</a>
			</c:if>
			<c:if test="${taskList.flag == 2}">
			<a href="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
				${taskList.processInstanceId}
			</a>
			</c:if>
		</display:column>	
		
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="主题" maxLength="5">
		</display:column>	
		
		<display:column sortable="true"
			headerClass="sortable" title="处理人">
			<eoms:id2nameDB id='${taskList.initiator}' beanId='tawSystemUserDao'/>
		</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="所属部门">
			<eoms:id2nameDB id='${taskList.deptId}' beanId='tawSystemDeptDao'/>
		</display:column>	
			<display:column property="createDate" sortable="true"
				headerClass="sortable" title="派单时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column sortable="true"
			headerClass="sortable" title="地市">
			<eoms:id2nameDB id='${taskList.city}' beanId='tawSystemAreaDao'/>
		</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="工单类型">
			<c:if test="${taskList.flag == 1}">
			故障工单
			</c:if>
			<c:if test="${taskList.flag == 2}">
			任务工单
			</c:if>
		</display:column>	
			
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>

	</display:table>
</logic:notPresent>

<%@ include file="/common/footer_eoms.jsp"%>