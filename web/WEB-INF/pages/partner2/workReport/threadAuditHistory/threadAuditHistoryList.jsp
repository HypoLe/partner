<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<jsp:directive.page import="com.boco.partner2.workReport.util.InfopubConstants" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">
	function choose(checkbox){
		eoms.util.checkAll(checkbox,'ids');
	}
	function submitForm(status){
		var flag = false;
		var objs = document.getElementsByName("ids");
		for(var i=0; i<objs.length; i++){
			if(objs[i].type.toLowerCase() == "checkbox"){
				if(objs[i].checked){
      				flag="true";
      				break;
				}
			}
		}
		if(flag == "true"){
			document.getElementById('status').value=status;
			document.forms[0].submit();   
			return true;
		}else{
			alert("请选择");
			return false;
		}
	}

</script>
<content tag="heading">
</content>
<html:form action="/threadAuditHistory.do?method=audit" method="post" styleId="threadAuditHistoryForm">
	<input type="hidden" name="status" id="status" />
	<display:table name="threadAuditHistoryList" cellspacing="0" cellpadding="0" id="threadAuditHistoryList" pagesize="${pageSize }" class="table threadAuditHistoryList" export="false" requestURI="${app}/partner2/workReport/threadAuditHistory.do?method=search"
		sort="external" defaultsort="1" partialList="true" size="resultSize" decorator="com.boco.partner2.workReport.displaytag.support.ThreadAuditHistoryListDisplayTagDecorator">

<display:setProperty name="export.rtf" value="false"></display:setProperty>

		<display:column property="id" title="<input type='checkbox' onclick='javascript:choose(this);'/>" />

		<display:column sortable="true" sortName="threadId" headerClass="sortable" titleKey="infopub.threadAuditHistoryForm.threadId" paramId="id" paramProperty="threadId" href="${app}/partner2/workReport/thread.do?method=detail">
			<eoms:id2nameDB id="${threadAuditHistoryList.threadId}" beanId="threadDao" />
		</display:column>


		<display:column property="status" sortable="true" sortName="status" headerClass="sortable" titleKey="infopub.threadAuditHistoryForm.status" href="${app}/partner2/workReport/threadAuditHistory.do?method=device" paramId="threadId" paramProperty="threadId"/>

		<display:column property="submitTime" sortable="true" sortName="submitTime" headerClass="sortable" titleKey="infopub.threadAuditHistoryForm.submitTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="auditTime" sortable="true" sortName="auditTime" headerClass="sortable" titleKey="infopub.threadAuditHistoryForm.auditTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="orgId" sortable="true" sortName="orgId" headerClass="sortable" titleKey="infopub.threadAuditHistoryForm.orgId" />

		<display:column property="opinion" sortable="true" sortName="opinion" headerClass="sortable" titleKey="infopub.threadAuditHistoryForm.opinion" />

		<display:column property="note" sortable="true" sortName="note" headerClass="sortable" titleKey="infopub.threadAuditHistoryForm.note" />

		<display:setProperty name="paging.banner.item_name" value="threadAuditHistory" />
		<display:setProperty name="paging.banner.items_name" value="threadAuditHistorys" />
	</display:table>


	<logic:notEmpty name="threadAuditHistoryList">
		<li>
			<eoms:label styleClass="desc" key="threadAuditHistoryForm.opinion" />
			<html:errors property="opinion" />
			<html:textarea property="opinion" styleId="opinion" styleClass="textarea medium" />
		</li>
		<input type="button" onclick="javascript:submitForm('<%=InfopubConstants.AUDIT_PASS%>');" value="通过" />

		<input type="button" onclick="javascript:submitForm('<%=InfopubConstants.AUDIT_NO_PASS%>');" value="不通过" />
	</logic:notEmpty>

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
