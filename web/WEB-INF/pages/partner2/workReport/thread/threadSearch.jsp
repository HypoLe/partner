<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<title>标题</title>
<script type="text/javascript">
<!--
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'workReportForm'});
});
//-->
</script>

<script type="text/javascript">
	function FCKeditor_OnComplete(editorInstance) {
		window.status = editorInstance.Description;
	}	
	Ext.onReady(function(){
		var reportUserViewer = new Ext.JsonView("reportView",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>没有选择项目</div>'
			}
		);
		var r='[]';
		reportUserViewer.jsonData = eoms.JSONDecode(r);
		reportUserViewer.refresh();
		var	treeAction='${app}/xtree.do?method=userFromDept';
		reportUserTree = new xbox({
			btnId:'reportUser',dlgId:'hello-dlg',
			treeDataUrl:treeAction,treeRootId:'-1',treeRootText:'人员选择树',treeChkMode:'single',treeChkType:'user',
			saveChkFldId:'createrId',viewer:reportUserViewer
		});
	});
	Ext.onReady(function(){
		var reportDeptViewer = new Ext.JsonView("deptView",
			'<div class="viewlistitem-{nodeType}">{name}</div>',
			{ 
				emptyText : '<div>没有选择项目</div>'
			}
		);
		var s='[]';
		reportDeptViewer.jsonData = eoms.JSONDecode(s);
		reportDeptViewer.refresh();
		var	treeAction2='${app}/xtree.do?method=dept';
		reportDeptTree = new xbox({
			btnId:'reportDept',dlgId:'hello-dlg',
			treeDataUrl:treeAction2,treeRootId:'-1',treeRootText:'部门选择树',treeChkMode:'single',treeChkType:'dept',
			saveChkFldId:'deptId',viewer:reportDeptViewer
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
		var	treeAction3='${app}/partner2/workReport/forums.do?method=getNodes4query';
		forumsTree = new xbox({
			btnId:'forumsName',dlgId:'hello-dlg',
			treeDataUrl:treeAction3,treeRootId:'-1',treeRootText:'选择',treeChkMode:'single',treeChkType:'forums',
			saveChkFldId:'forumsId',viewer:forumsViewer
		});
	});
</script>

<html:form action="/thread.do?mehtod=search" method="post"
	styleId="workReportForm">
	<table class="formTable" width="75%">
	<tr>
		<td class="label" align="right">
			报告标题：
		</td>
		<td>
			<html:text property="title" styleId="title" styleClass="text medium" />
		</td>
	</tr>
	<tr>		
		<td class="label" align="right">
			报告内容：
		</td>
		<td>
			<html:text property="content" styleId="content" styleClass="text medium" />
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			起始时间：
		</td>
		<td>
			<input id="startDate" name="startDate" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间'"/>
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			结束时间：
		</td>
		<td>
			<input id="endDate" name="endDate" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间'"/>
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			报告专题：
		</td>
		<td>
			<div id="forumsView" class="viewer-box"></div>
			<input type="button" id="forumsName" name="forumsName" value="选择专题"
					class="button" />
			<html:hidden property="forumsId" styleId="forumsId" styleClass="text medium" />
		</td>
	</tr>
	<tr>
		<td class="label" align="right">
			上报人：
		</td>
		<td>
			<div id="reportView" class="viewer-box"></div>
			<input type="button" id="reportUser" name="reportUser" value="选择人员"
					class="button" />
			<html:hidden property="createrId" styleId="createrId" styleClass="text medium" />
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			来源：
		</td>
		<td>
			<input type="hidden" id="deptId" name="deptId" />
			<div id="deptView" class="viewer-box"></div>
			<input type="button" id="reportDept" name="reportDept" value="选择部门"
					class="button" />
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<html:submit styleClass="button" property="method.search">
				查询
			</html:submit>
		</td>
	</tr>
	</table>
<logic:notEmpty name="forums">
	<html:select property="toForumsId">
		<html:options collection="forums" property="id" labelProperty="title"/>
	</html:select>
</logic:notEmpty>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
