<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page
	import="java.util.List,java.util.HashMap,com.boco.eoms.commons.system.role.model.TawSystemRole,java.util.ArrayList,com.boco.eoms.commons.system.user.model.TawSystemUser;"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
Ext.onReady(function(){
		var reportDeptViewer = new Ext.JsonView("deptView",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>没有选择项目</div>'
			}
		);
		var s='${orgs}';
		reportDeptViewer.jsonData = eoms.JSONDecode(s);
		reportDeptViewer.refresh();
		var	treeAction2='${app}/xtree.do?method=dept';
		reportDeptTree = new xbox({
			btnId:'reportDept',dlgId:'hello-dlg',
			treeDataUrl:treeAction2,treeRootId:'-1',treeRootText:'部门选择树',treeChkMode:'single',treeChkType:'dept',
			saveChkFldId:'deptId',viewer:reportDeptViewer,returnJSON:true
		});
	});
</script>
<html:form action="/tawSystemRoleteases.do?method=selfDeptView"
	styleId="tawSystemRoleteaseForm" method="post">

	<table class="formTable">
		<tr>
			<h2>
				部门视图
			</h2>
		</tr>
		<tr>
			<td>
				<input type="hidden" id="deptId" name="deptId" />
				<div id="deptView" class="viewer-box"></div>
				<input type="button" id="reportDept" name="reportDept" value="选择部门"
					class="button" />
				<input type="submit" class="btn" value="查看" />
			</td>
		</tr>
	</table>
</html:form>
<table class="formTable">

	<tr>
		<td rowspan="2" align="center" class="label" valign="middle">
			人员名称
		</td>
		<%
		    List noRoleList = new ArrayList();
			List workFlowList = (List) request.getAttribute("workflowMaplist");
			for (int i = 0; i < workFlowList.size(); i++) {
				HashMap map = (HashMap) workFlowList.get(i);
				String workflowName = (String) map.get("workflow");
				Integer count = (Integer) map.get("count");
				if(count.intValue()==0){
				noRoleList.add(workflowName);
				}else{
		%>
		<td colspan="<%=count%>" align="center" valign="middle" class="label">
			<%=workflowName%>
		</td>
		<%
			}
			}
			for(int i=0;i<noRoleList.size();i++){
			String noRoleworkflowName = (String) noRoleList.get(i);
		%>
		<td colspan="1" align="center" valign="middle" class="label">
			<%=noRoleworkflowName%>
		</td>
		<%} %>
	</tr>
	<tr>
		<%
			List roleList = (List) request.getAttribute("roleList");
			List allRoleList = new ArrayList();
			for (int j = 0; j < roleList.size(); j++) {
				List list = (List) roleList.get(j);
				for (int k = 0; k < list.size(); k++) {
					TawSystemRole role = new TawSystemRole();
					role = (TawSystemRole) list.get(k);
					allRoleList.add(role);
					long roleId = role.getRoleId();
					String roleName = role.getRoleName();
		%>
		<td class="label" valign="middle" align="center">
			<%=roleName%>
		</td>
		<%
			}}
		 %>
	</tr>
	<%
		List userList = (List) request.getAttribute("userList");
		String userName = "";
		String userid = "";
		for (int i = 0; i < userList.size(); i++) {
			TawSystemUser tawSystemUser = (TawSystemUser) userList.get(i);
			userName = tawSystemUser.getUsername();
			userid = tawSystemUser.getUserid();
	%>
	<tr>
		<td style="width: 1px">
			<%=userName%>
		</td>
		<%
			for (int j = 0; j < allRoleList.size(); j++) {
					String id = userid
							+ ((TawSystemRole) allRoleList.get(j)).getRoleId();
		%>
		<td id="<%=id%>">

		</td>
		<%
			}
		%>
	</tr>
	<%
		}
	%>
</table>
<script language="JavaScript" type="text/JavaScript">

<% List userRoleList =(List) request.getAttribute("userRoleList"); 
   for(int i=0;i<userRoleList.size();i++){
   String  userRole = (String)userRoleList.get(i);
%>
  //alert("<%=userRole%>");
  // document.getElementById("<%=userRole%>").style.backgroundImage="${app}/eoms/images/icons/yes.gif";
  document.getElementById("<%=userRole%>").className = "msgbox";
<%}%>
//-->
</script>
<%@ include file="/common/footer_eoms.jsp"%>