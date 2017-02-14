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
			headerClass="sortable" title="区县">
			<c:if test="${taskList.citylength != 4}">
            <a href="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatisticsByCountry&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
            </c:if>
            ${taskList.cityName}
		    </a>

		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="工单总数">
			<c:if test="${taskList.citylength != 4}">
			<a href="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatisticsDrillbycity&flag=1&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			</c:if>
			${taskList.sumNum}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="超时工单总数">
			<c:if test="${taskList.citylength != 4}">
			<a href="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatisticsDrillbycity&flag=2&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			</c:if>
			${taskList.overtimeNum}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="未归档工单总数">
			<c:if test="${taskList.citylength != 4}">
			<a href="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatisticsDrillbycity&flag=3&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			</c:if>
			${taskList.unfiledNumber}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="归档工单数">
			<c:if test="${taskList.citylength != 4}">
			<a href="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatisticsDrillbycity&flag=4&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			</c:if>
			${taskList.archiveNumber}
			</a>
			</display:column>
		<display:column property="overtimeRate" sortable="true"
			headerClass="sortable" title="工单超时率"/>
			
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>

	</display:table>
</logic:notPresent>

<%@ include file="/common/footer_eoms.jsp"%>