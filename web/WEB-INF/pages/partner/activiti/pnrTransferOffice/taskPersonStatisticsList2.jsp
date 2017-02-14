<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
	function rollback(){
		window.location = "pnrTransferOffice.do?method=workOrderStatisticsByCity";
	}
</script>
<logic:notPresent name="recordTotal">
<bean:define id="url" value="pnrTransferOffice.do" />
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="table businessdesignMain"
		export="false" requestURI="pnrTransferOffice.do"
		sort="list" size="total" partialList="true">
		
		<display:column sortable="true"
			headerClass="sortable" title="处理人">
			<c:choose>
  				<c:when test="${taskList.cityName == '无' }">无</c:when>
  				<c:otherwise>
  				<eoms:id2nameDB id='${taskList.cityName}' beanId='tawSystemUserDao'/>
  				</c:otherwise>
  			</c:choose>
			
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="工单总数">
			<a target="nextPage" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=newWorkOrderStatisticsDrillbyperson&flag=1&person=${taskList.cityName}&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=transferOffice">
			${taskList.sumNum}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="超时工单总数">
			<a target="nextPage" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=newWorkOrderStatisticsDrillbyperson&flag=2&person=${taskList.cityName}&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=transferOffice">
			${taskList.overtimeNum}
			</a>
			</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="未归档工单总数">
			<a target="nextPage" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=newWorkOrderStatisticsDrillbyperson&flag=3&person=${taskList.cityName}&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=transferOffice">
			${taskList.unfiledNumber}
			</a>
			</display:column>s
		<display:column  sortable="true"
			headerClass="sortable" title="归档工单数">
			<a target="nextPage" href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=newWorkOrderStatisticsDrillbyperson&flag=4&person=${taskList.cityName}&city=${taskList.city}&subType=${subType}&bTime=${bTime}&eTime=${eTime}&taskType=transferOffice">
			${taskList.archiveNumber}
			</a>
			</display:column>
		<display:column property="overtimeRate" sortable="true"
			headerClass="sortable" title="工单超时率"/>
	</display:table>
	
</logic:notPresent>
<!-- <input type="button" value="返回" onclick="rollback()"/> -->
<%@ include file="/common/footer_eoms.jsp"%>