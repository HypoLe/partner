<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/common/header_eoms_form.jsp"%>
<title>标题</title>
<script type="text/javascript">
<!--
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'threadForm'});
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
			treeDataUrl:treeAction3,treeRootId:'-1',treeRootText:'所有专题信息',treeChkMode:'single',treeChkType:'forums',
			saveChkFldId:'forumsId',viewer:forumsViewer
		});
	});
</script>

<html:form action="/thread.do" method="post"
	styleId="workReportForm">
	<table class="formTable" width="75%">
	<tr>
		<td class="label" align="right">
			标题：
			<html:errors property="title" />
		</td>
		<td>
			<html:text property="title" styleId="title" styleClass="text medium" />
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			开始时间：
			<html:errors property="startDate" />
		</td>
		<td>
			<input id="startDate" name="startDate" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间'"/>
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			结束时间：
			<html:errors property="endDate" />
		</td>
		<td>
			<input id="endDate" name="endDate" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间'"/>
		</td>
	</tr>
	
	<tr>		
		<td class="label" align="right">
		内容：
			<html:errors property="content" />
		</td>
		<td>
			<html:text property="content" styleId="content" styleClass="text medium" />
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			<eoms:label styleClass="desc" key="threadForm.status" />
			<html:errors property="status" />
		</td>
		<td>
			<eoms:dict key="dict-workbench-infopub" dictId="auditStatus" selectId="status" beanId="selectXML" />
		</td>
	</tr>
	<tr>	
		<td class="label" align="right">
			<eoms:label styleClass="desc" key="threadForm.threadTypeId" />
			<html:errors property="threadTypeId" />
		</td>
		<td>
			<eoms:dict key="dict-workbench-infopub" dictId="threadType"
				selectId="threadTypeId" beanId="selectXML" 
				defaultId="${threadForm.threadTypeId }" />
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<html:submit styleClass="button" property="method.search"
				onclick="bCancel=false">
				<fmt:message key="button.query" />
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
