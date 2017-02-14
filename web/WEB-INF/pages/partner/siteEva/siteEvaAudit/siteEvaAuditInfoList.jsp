<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.siteEva.util.SiteEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<fmt:bundle basename="com/boco/eoms/siteEva/config/ApplicationResources-siteEva">
<content tag="heading">
<bean:message key="siteEva.auditHistoryList.heading" />
</content>
	<display:table name="auditInfoList" cellspacing="0" cellpadding="0" id="auditInfoList" pagesize="${pageSize }" class="table auditInfoList" export="false"
		requestURI="${app}/partner/siteEva/siteEvaAudit.do?method=auditInfoList&templateId=${siteEvaAuditForm.templateId}" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.siteEva.util.AuditInfoListDisplaytagDecorator">

		<display:column property="auditUser" sortable="false" headerClass="sortable" titleKey="siteEva.auditInfo.user" />

		<display:column property="status" sortable="false" headerClass="sortable" titleKey="siteEva.auditInfo.status" />

		<display:column property="auditTime" sortable="false" headerClass="sortable" titleKey="siteEva.auditInfo.auditTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="auditInfo" sortable="false" headerClass="sortable" titleKey="siteEva.auditInfo.auditInfo" />

	</display:table>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
