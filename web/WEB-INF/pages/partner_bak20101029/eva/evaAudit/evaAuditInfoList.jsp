<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<fmt:bundle basename="com/boco/eoms/partner/eva/config/ApplicationResources-partner-eva">
	<display:table name="auditInfoList" cellspacing="0" cellpadding="0" id="auditInfoList" pagesize="${pageSize}" class="table auditInfoList" export="false"
		requestURI="${app}/partner/eva/evaTaskAudit.do?method=unAuditTaskList" sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.eva.util.AuditInfoListDisplaytagDecorator">
		
		<display:column sortable="true" headerClass="sortable" titleKey="eva.auditInfo.templateName">
		 	<a href="${app}/partner/eva/evaAudit.do?method=editAuditInfo&id=${auditInfoList.id}&templateId=${auditInfoList.templateId}">${auditInfoList.templateName}</a>
		</display:column>
		
		<display:column property="createTime"  sortable="false" headerClass="sortable" titleKey="eva.auditInfo.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column sortable="true" headerClass="sortable" titleKey="eva.auditInfo.status">
		 <eoms:dict key="dict-partner-eva" dictId="auditFlag" itemId="${auditInfoList.auditResult}" beanId="id2nameXML" />
		</display:column>

	</display:table>
	</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
