<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.lineEva.util.LineEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<fmt:bundle basename="com/boco/eoms/lineEva/config/ApplicationResources-lineEva">
<content tag="heading">
<bean:message key="lineEva.auditHistoryList.heading" />
</content>
	<display:table name="auditInfoList" cellspacing="0" cellpadding="0" id="auditInfoList" pagesize="${pageSize }" class="table auditInfoList" export="false"
		requestURI="${app}/partner/lineEva/lineEvaAudit.do?method=auditInfoList&templateId=${lineEvaAuditForm.templateId}" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.lineEva.util.AuditInfoListDisplaytagDecorator">

		<display:column property="auditUser" sortable="false" headerClass="sortable" titleKey="lineEva.auditInfo.user" />

		<display:column property="status" sortable="false" headerClass="sortable" titleKey="lineEva.auditInfo.status" />

		<display:column property="auditTime" sortable="false" headerClass="sortable" titleKey="lineEva.auditInfo.auditTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="auditInfo" sortable="false" headerClass="sortable" titleKey="lineEva.auditInfo.auditInfo" />

	</display:table>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
