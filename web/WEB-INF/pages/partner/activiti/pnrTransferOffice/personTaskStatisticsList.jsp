<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	<script type="text/javascript">
		function rollback(){
			window.location="pnrTransferOffice.do?method=showStatisticsPage";
		}
	</script>
<logic:notPresent name="recordTotal">
<bean:define id="url" value="pnrTransferOffice.do" />
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="table businessdesignMain"
		export="false" requestURI="pnrTransferOffice.do"
		sort="list" size="total" partialList="true">
		
		<display:column sortable="true"
			headerClass="sortable" title="地市">
			<c:if test="${taskList.cityName=='全省'}">
			${taskList.cityName}
			</c:if>
			<c:if test="${taskList.cityName!='全省'}">
			
			<a target="taskTicketList" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=personWorkOrderStatisticsByCity&cityname=${taskList.cityName}&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			${taskList.cityName}
			</a>
			</c:if>
		</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="工单总数">
			<a target="taskTicketList" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=personWorkOrderStatisticsDrill&flag=1&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			${taskList.sumNum}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="超时工单总数">
			<a target="taskTicketList" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=personWorkOrderStatisticsDrill&flag=2&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			${taskList.overtimeNum}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="未归档工单总数">
			<a target="taskTicketList" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=personWorkOrderStatisticsDrill&flag=3&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			${taskList.unfiledNumber}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="归档工单数">
			<a target="taskTicketList" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=personWorkOrderStatisticsDrill&flag=4&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=${taskType}">
			${taskList.archiveNumber}
			</a>
			</display:column>
		<display:column property="overtimeRate" sortable="true"
			headerClass="sortable" title="工单超时率"/>
			
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>

	</display:table>
	<!--<input type="button" value="返回" onclick="rollback()"/>  -->
	
</logic:notPresent>

<%@ include file="/common/footer_eoms.jsp"%>