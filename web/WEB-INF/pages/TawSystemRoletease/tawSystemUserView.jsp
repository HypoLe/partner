<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
Ext.onReady(function(){
		var reportUserViewer = new Ext.JsonView("reportView",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>没有选择项目</div>'
			}
		);
		var r='${orgs}';
		reportUserViewer.jsonData = eoms.JSONDecode(r);
		reportUserViewer.refresh();
		var	treeAction='${app}/xtree.do?method=userFromDept';
		reportUserTree = new xbox({
			btnId:'reportUser',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'人员选择树',treeChkMode:'single',treeChkType:'user',
			saveChkFldId:'userId',viewer:reportUserViewer,returnJSON:true
		});
	});

</script>
<fmt:bundle basename="config/applicationResource-tawsystemroletease">
<html:form action="/tawSystemRoleteases.do?method=selfUserView"
	styleId="tawSystemRoleteaseForm" method="post">

	<table class="formTable">
		<tr>
			<h2>
				人员视图
			</h2>
		</tr>
		<tr>
			<td>
				<div id="reportView" class="viewer-box"></div>
				<input type="button" id="reportUser" name="reportUser" value="选择人员"
					class="button" />
				<html:hidden property="userId" styleId="userId"
					styleClass="text medium" />
				<input type="submit" class="btn" value="查看" />
			</td>
		</tr>
	</table>
</html:form>


	<%
		int i = 1;
	%>
	<display:table name="tawSystemRoleteaseList" cellspacing="0"
		cellpadding="0" id="tawSystemRoleteaseList" pagesize="${count}"
		class="table tawSystemRoleteaseList" export="false"
		requestURI="${app}/roletease/tawSystemRoleteases.do?method=selfUserView"
		sort="list" partialList="true" size="${count}">

		<display:column titleKey="tawSystemRoletease.workflowId">
			<%=i%>
			<%
				i++;
			%>
		</display:column>

		<display:column property="workflowName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.workflowName"
			paramId="id" paramProperty="id" />

		<display:column property="roleName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.roleName"
			paramId="id" paramProperty="id" />

		<display:column property="subRoleName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.subRoleName"
			paramId="id" paramProperty="id" />

		<display:column property="workTemple" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.workTemple"
			paramId="id" paramProperty="id" />

		<display:column property="timeLimite" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.timeLimite"
			paramId="id" paramProperty="id" />

		<display:column property="skillRequire" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.skillRequire"
			paramId="id" paramProperty="id" />

		<display:column property="checkLine" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.checkLine"
			paramId="id" paramProperty="id" />

		<display:setProperty name="paging.banner.item_name"
			value="tawSystemRoletease" />
		<display:setProperty name="paging.banner.items_name"
			value="tawSystemRoleteases" />
	</display:table>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>