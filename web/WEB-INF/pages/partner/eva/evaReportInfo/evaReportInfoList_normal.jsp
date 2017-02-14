<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants" />
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%
String partnerShow = StaticMethod.nullObject2String(request.getAttribute("partnerShow"));
%>
<fmt:bundle basename="com/boco/eoms/partner/eva/config/ApplicationResources-partner-eva">
	<display:table name="reportInfoList" cellspacing="0" cellpadding="0" id="reportInfoList" pagesize="${pageSize}" class="table auditInfoList" export="false"
		requestURI="${app}/partner/eva/evaReportInfos.do?method=taskReportInfoList" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.eva.util.AuditInfoListDisplaytagDecorator">
		<display:column sortable="true" headerClass="sortable" titleKey="eva.reportInfo.taskName">
		 	<a href="${app}/partner/eva/evaReportInfos.do?method=reportDetailListNormal&taskId=${reportInfoList.taskId}&partnerId=${reportInfoList.partnerId}&time=${reportInfoList.time}&startTime=${startTime}&endTime=${endTime}&areaId=${areaId}">${reportInfoList.taskName}</a>
		</display:column>
		<display:column property="time"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.time" />
		<display:column property="timeType"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.timeType" />
		<display:column property="createTime"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.createTime" />
<%
if(partnerShow.equals("ture")){
%>
		<display:column sortable="true" headerClass="sortable" title="合作伙伴">
			<eoms:id2nameDB id="${reportInfoList.partnerId}" beanId="areaDeptTreeDao" />
		</display:column>
<%
}
%>
		<display:column property="totalScore"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.totalScore" />
		<display:column property="area"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.area" />
	</display:table>
	</fmt:bundle>

<script type="text/javascript" />
	function back() {
			window.location = "${app}/partner/eva/evaReportInfos.do?method=taskView";
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>

