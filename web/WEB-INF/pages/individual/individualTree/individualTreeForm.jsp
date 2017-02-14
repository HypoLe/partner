<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

Ext.onReady(function(){
		var reportUserViewer = new Ext.JsonView("reportView",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>没有选择</div>'
			}
		);
		var r='${dept_user_id}';
		reportUserViewer.jsonData = eoms.JSONDecode(r);
		reportUserViewer.refresh();
		var	treeAction='${app}/xtree.do?method=dept';
		reportUserTree = new xbox({
			btnId:'dept_user_name',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'人员选择树',treeChkMode:'',treeChkType:'dept',
			saveChkFldId:'dept_user_id',viewer:reportUserViewer,returnJSON:true
		});
	});
	
	
	Ext.onReady(function(){
var forumsViewer = new Ext.JsonView("forumsView",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>没有选择项目</div>'
			}
		);
		var f='[]';
		forumsViewer.jsonData = eoms.JSONDecode(f);
		forumsViewer.refresh();
		var	treeAction3='${app}/individual/individualTrees.do?method=getNodes4Forum';
		forumsTree = new xbox({
			btnId:'dept_user_name2',dlgId:'hello-dlg',
			treeDataUrl:treeAction3,treeRootId:'-1',treeRootText:'人员查看树',treeChkMode:'',treeChkType:'dept,user',
			saveChkFldId:'dept_user_id',viewer:forumsViewer
		});
	});
</script>

<html:form action="/individualTrees.do?method=save" styleId="individualTreeForm" method="post"> 


<table class="formTable">
	<caption>
		<div class="header center">
			<bean:message key='individualTree.detail.heading'/>
		</div>
	</caption>

	<tr>
		<td class="label">
			<bean:message key='individualTree.dept_user_id'/>
		</td>
		
		<td>
			<div id="reportView" class="viewer-box"></div>
			<input type="button" id="dept_user_name" name="dept_user_name" value="选择人员"
					class="button" />
					<html:hidden property="dept_user_id" styleId="dept_user_id" styleClass="text medium" />
		</td>
		<!-- 
		<td class="content">
			<html:text property="dept_user_id" styleId="dept_user_id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualTreeForm.dept_user_id}" />
		</td> -->
	</tr>
 <!-- 
	<tr>
	<td class="label">
			查看
		</td>
	<td>
	<div id="forumsView" class="viewer-box"></div>
			<input type="button" id="dept_user_name2" name="dept_user_name2" value="选择专题"
					class="button" />
			<html:hidden property="dept_user_id" styleId="dept_user_id" styleClass="text medium" />
	</td>
	</tr> 
</table>

 -->
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			
		</td>
	</tr>
</table>
<html:hidden property="id" value="${individualTreeForm.id}" />
<html:hidden property="groupId" value="${individualTreeForm.groupId}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>