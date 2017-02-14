<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<title><bean:message key="threadDetail.title" /></title>
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
				<!--emptyText : '<div>没有选择项目</div>'-->
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
				<!--emptyText : '<div>没有选择项目</div>'-->
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
</script>

<html:form action="/thread.do" method="post" styleId="threadForm">
<center>
<div  style="width:100%">
	<table class="formTable">
	<caption>
		<div class="header center">信息发布查询</div>
	</caption>
	<tr>
		<td class="label" align="left">
			<eoms:label styleClass="desc" key="threadForm.title" />
			<html:errors property="title" />
		</td>
		<td>
			<html:text property="title" styleId="title" styleClass="text medium" style="width:100%"/>
		</td>
		<td class="label" align="left">
			<eoms:label styleClass="desc" key="threadForm.content" />
			<html:errors property="content" />
		</td>
		<td>
			<html:text property="content" styleId="content" styleClass="text medium" style="width:100%"/>
		</td>
	</tr>
	<tr>
	<td class="label" align="left">
			<eoms:label styleClass="desc" key="threadForm.startDate" />
			<html:errors property="startDate" />
		</td>
	<td>
			<input id="startDate" name="startDate" type="text" style="width: 100%"class="text" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,-1,-1);" alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间'"/>
		</td>
		<td class="label" align="left">
			<eoms:label styleClass="desc" key="threadForm.endDate" />
			<html:errors property="endDate" />
		</td>
		<td>
			<input id="endDate" name="endDate" type="text" style="width: 100%" class="text" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,-1,-1);" alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间'"/>
		</td>
	</tr>
	
	
	<tr>
		<td class="label" align="left">
			<eoms:label styleClass="desc" key="threadForm.createrId" />
			<html:errors property="createrId" />
		</td>
		<td>
			<div id="reportView" class="viewer-box"  style="width: 80%" ></div>
			<input type="button" id="reportUser" name="reportUser" value="选择人员"
					class="button"  />
			<html:hidden property="createrId" styleId="createrId" styleClass="text medium" />
		</td>
		<td class="label" align="left"> 
			信息来源 
		</td>
		<td>
			<input type="hidden" id="deptId" name="deptId" />
			<div id="deptView" class="viewer-box"  style="width: 80%" ></div> 
			<input type="button" id="reportDept" name="reportDept" value="选择部门"
					class="button" />
		</td>
	</tr>
	
	
	
	<tr>	
		<td class="label" align="left">
			<eoms:label styleClass="desc" key="threadForm.status" />
			<html:errors property="status" />
		</td>
		<td colspan="3">
			<eoms:dict key="dict-workbench-infopub" dictId="auditStatus" selectId="status" beanId="selectXML" />
		</td>
		
		<logic:notEmpty name="forums">
	<html:select property="toForumsId">
		<html:options collection="forums" property="id" labelProperty="title"/>
	</html:select>
	</logic:notEmpty>
	</tr>
	
	</table>
</center>
<html:submit styleClass="button" property="method.search"
				onclick="bCancel=false" >
				<fmt:message key="button.query" />
			</html:submit>
</html:form>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
