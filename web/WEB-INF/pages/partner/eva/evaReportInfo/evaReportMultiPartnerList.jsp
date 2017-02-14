<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<fmt:bundle basename="com/boco/eoms/partner/eva/config/ApplicationResources-partner-eva">
	<display:table name="reportInfoList" cellspacing="0" cellpadding="0" id="reportInfoList" pagesize="${pageSize}" class="table auditInfoList" export="false"
		requestURI="${app}/partner/eva/evaReportInfos.do?method=partnerList" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.eva.util.AuditInfoListDisplaytagDecorator">
		<display:column sortable="true" headerClass="sortable" titleKey="eva.reportInfo.taskName">
		 	<a href="${app}/partner/eva/evaReportInfos.do?method=reportDetailList&taskId=${reportInfoList.taskId}&partnerId=${reportInfoList.partnerId}&time=${reportInfoList.time}&startTime=${startTime}&endTime=${endTime}&areaId=${areaId}&partner=${partner}">${reportInfoList.taskName}</a>
		</display:column>
		<display:column property="time"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.time" />
		<display:column property="timeType"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.timeType" />
		<display:column property="createTime"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.createTime" />
		<display:column property="totalScore"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.totalScore" />
		<display:column sortable="true" headerClass="sortable" titleKey="eva.reportInfo.area">
			<eoms:id2nameDB id="${reportInfoList.area}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column property="partnerName"  sortable="false" headerClass="sortable" titleKey="eva.reportInfo.partnerName" />
	eva.reportInfo.partnerName
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
			window.location = "${app}/partner/eva/evaReportInfos.do?method=partnerInfoView";
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>

