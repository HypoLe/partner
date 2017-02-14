<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<fmt:bundle basename="com/boco/eoms/partner/eva/config/ApplicationResources-partner-eva">
	<display:table name="reportMultiMonthList" cellspacing="0" cellpadding="0" id="reportMultiMonthList" pagesize="${pageSize}" class="table reportInfoList" export="false"
		requestURI="${app}/partner/eva/evaReportInfos.do?method=queryInitMultiList" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.eva.util.AuditInfoListDisplaytagDecorator">
		<display:column sortable="true" headerClass="sortable" titleKey="eva.reportInfo.taskName">
		 	<a href="${app}/partner/eva/evaReportInfos.do?method=reportDetailList&taskId=${reportMultiMonthList.taskId}&partnerId=${reportMultiMonthList.partnerId}&time=${reportMultiMonthList.time}&startTime=${startTime}&endTime=${endTime}&areaId=${areaId}&partnerId=${partnerId}">${reportMultiMonthList.taskName}</a>
		</display:column>
		<display:column property="time"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.time" />
		<display:column property="timeType"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.timeType" />
		<display:column property="createTime"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.createTime" />
		<display:column property="totalScore"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.totalScore" />
		<display:column property="area"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.area" />
		<display:column property="partnerName"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.partnerName" />
	</display:table>
	</fmt:bundle>
<table id="submit-btn" align="left">
	<tr>
		<td>
			<input type="submit" class="btn" value="返回" onClick="return back();"/>
		</td>
	</tr>
</table>
<script type="text/javascript" />
	function back() {
			window.location = "${app}/partner/eva/evaReportInfos.do?method=queryInitMultiMonth";
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>

