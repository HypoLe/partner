<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.assiEva.util.AssiEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<fmt:bundle basename="com/boco/eoms/assiEva/config/ApplicationResources-assiEva">
<content tag="heading">
<bean:message key="assiEva.auditHistoryList.heading" />
</content>
	<display:table name="auditInfoList" cellspacing="0" cellpadding="0" id="auditInfoList" pagesize="${pageSize }" class="table auditInfoList" export="false"
		requestURI="${app}/partner/assiEva/assiEvaAudit.do?method=auditInfoList&templateId=${assiEvaAuditForm.templateId}" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.assiEva.util.AuditInfoListDisplaytagDecorator">

		<display:column property="auditUser" sortable="false" headerClass="sortable" titleKey="assiEva.auditInfo.user" />

		<display:column property="status" sortable="false" headerClass="sortable" titleKey="assiEva.auditInfo.status" />

		<display:column property="auditTime" sortable="false" headerClass="sortable" titleKey="assiEva.auditInfo.auditTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="auditInfo" sortable="false" headerClass="sortable" titleKey="assiEva.auditInfo.auditInfo" />

	</display:table>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
