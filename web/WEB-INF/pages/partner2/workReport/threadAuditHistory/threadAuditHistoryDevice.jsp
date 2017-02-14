<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/xtreelibs.jsp"%>
<content tag="heading">
	<eoms:id2nameDB id="${thread.id}" beanId="threadDao" />
</content>


<fmt:bundle basename="com/boco/partner2/workReport/config/applicationResource-workbench-infopub">

	<display:table name="threadAuditHistoryList" cellspacing="0" cellpadding="0" id="threadAuditHistoryList" pagesize="25" class="table threadAuditHistoryList" export="true" requestURI="${app}/partner2/workReport/threadAuditHistory.do?method=search"
		sort="external" partialList="true" size="resultSize" decorator="com.boco.partner2.workReport.displaytag.support.ThreadAuditHistoryListDisplayTagDecorator">



		<display:column property="orgId" sortable="true" headerClass="sortable" titleKey="workReportAuditHistoryForm.orgId" />

		<display:column property="submitTime" sortable="true" headerClass="sortable" titleKey="workReportAuditHistoryForm.submitTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="opinion" sortable="true" headerClass="sortable" titleKey="workReportAuditHistoryForm.opinion" />

		<display:column property="status" sortable="true" headerClass="sortable" titleKey="workReportAuditHistoryForm.status" />

		<display:column property="auditTime" sortable="true" headerClass="sortable" titleKey="workReportAuditHistoryForm.auditTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="isCurrent" sortable="true" headerClass="sortable" titleKey="workReportAuditHistoryForm.isCurrent" />

		<display:column property="note" sortable="true" headerClass="sortable" titleKey="workReportAuditHistoryForm.note" />

		<display:setProperty name="paging.banner.item_name" value="threadAuditHistory" />
		<display:setProperty name="paging.banner.items_name" value="threadAuditHistorys" />
	</display:table>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
