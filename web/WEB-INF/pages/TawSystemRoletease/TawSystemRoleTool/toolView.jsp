<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.lang.*,com.boco.eoms.role_tease.webapp.form.*,com.boco.eoms.roleWorkflow.model.*" %>
<script type="text/javascript">
<!--
function roletoolGetRoleList(workflowid){
	document.forms[0].action='${app}/roletease/tawSystemRoleTool.do?method=roletoolGetRoleList&workflowid='+workflowid;
	document.forms[0].submit();
}
//-->
</script>
<fmt:bundle basename="config/applicationResource-tawsystemroletease">
<html:form action="/tawSystemRoleteases.do?method=selfUserView"
	styleId="tawSystemRoleteaseForm" method="post">

	
</html:form>
<table class="formTable">
		<tr>
			<h2>
				<font size="5">角色视图工具</font>
			</h2>
		</tr>
</table>


	<%
		int i = 1;
	%>
	
	<select name="workflowid" id="workflowid" onchange="roletoolGetRoleList(this.options[this.options.selectedIndex].value); ">
		<option value="">请选择</option>
	<%
		List list = (List)request.getSession().getAttribute("workflows");
		for(int j = 0;j<list.size();j++){	
		TawSystemWorkflow tawSystemWorkflow = (TawSystemWorkflow)list.get(j);	
	 %>
	 	
   		<option value="<%=tawSystemWorkflow.getFlowId() %>"><%=tawSystemWorkflow.getRemark() %></option>
   	<%
   		}
   	 %>
  	</select>
	
	
	<display:table name="tawSystemRoleToolList" cellspacing="0"
		cellpadding="0" id="tawSystemRoleToolList" pagesize="${pageSize}"
		offset="${offset}"
		class="table tawSystemRoleToolList" export="false"
		requestURI="${app}/roletease/tawSystemRoleTool.do?method=roletool"
		sort="list" partialList="true" size="resultSize">

		<display:column titleKey="tawSystemRoleTool.number">
			<%=i%>
			<%
				i++;
			%>
		</display:column>

		<display:column property="roleName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.roleName"
			paramId="id" paramProperty="id" />
			
		<display:column property="roleId" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.roleId"
			paramId="id" paramProperty="id" />

		<display:column property="workflowName" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoletease.workflowName"
			paramId="id" paramProperty="id" />

		<display:column property="workflowId" sortable="true"
			headerClass="sortable" titleKey="tawSystemRoleTool.workflowId"
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
		<display:column titleKey="tawSystemRoleTool.edit"><nobr><a href="tawSystemRoleTool.do?method=roletooledit&roleId=${tawSystemRoleToolList.roleId}">修改</a></nobr></display:column>
		
		<display:setProperty name="paging.banner.item_name"
			value="tawSystemRoletease" />
		<display:setProperty name="paging.banner.items_name"
			value="tawSystemRoleteases" />
	</display:table>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>