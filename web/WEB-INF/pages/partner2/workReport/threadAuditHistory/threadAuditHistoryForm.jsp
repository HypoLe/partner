<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<jsp:directive.page import="com.boco.partner2.workReport.util.InfopubConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/xtreelibs.jsp"%>
<content tag="heading">
<eoms:id2nameDB id="${thread.id}" beanId="threadDao" />
</content>

<script type="text/javascript">

	var auditTree;
	
	function FCKeditor_OnComplete(editorInstance) {
		window.status = editorInstance.Description;
	}
	
	Ext.onReady(function(){
		v = new eoms.form.Validation({form:'workReportAuditHistoryForm'});
		
		deptViewer = new Ext.JsonView("view",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>未选择</div>'
			}
		);
		var s='${jsonOrgs}';
		deptViewer.jsonData = eoms.JSONDecode(s);
		deptViewer.refresh();
		var	treeAction='${app}/xtree.do?method=getDeptSubRoleUserTree';
		deptTree = new xbox({
			btnId:'clkOrg',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'审核',treeChkMode:'single',treeChkType:'user',
			showChkFldId:'showOrg',saveChkFldId:'orgId',viewer:deptViewer,returnJSON:true
		});
	});
</script>

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

<logic:notEqual value="<%=InfopubConstants.AUDIT_PASS%>" name="thread" property="status">
	<html:form action="/threadAuditHistory.do" method="post" styleId="workReportAuditHistoryForm">
		<ul>
			<html:hidden property="threadId" />
			<input type="hidden" id="orgId" name="orgId" />

			<li>
				<eoms:label styleClass="desc" key="workReportAuditHistoryForm.note" />
				<html:errors property="note" />
				<html:textarea property="note" styleId="note" styleClass="textarea medium" />
			</li>

			<li>
				<eoms:label styleClass="desc" key="workReportForumsForm.canread" />
				<html:errors property="threadTypeId" />
				<input type="text" readonly id="showOrg" name="showOrg" />
				<input type="button" id="clkOrg" name="clkOrg" value="审核" />
				<div id="view"></div>
			</li>

			<li class="buttonBar bottom">
				<html:submit styleClass="button" property="method.save" value="确认提交">
				</html:submit>
			</li>
		</ul>
	</html:form>
</logic:notEqual>

<%@ include file="/common/footer_eoms.jsp"%>
