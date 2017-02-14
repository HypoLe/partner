<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<html:form action="forums.do" method="post" styleId="workReportForumsForm"> 
	<table id="sheet" class="formTable">
		<html:hidden property="id"/>
		<html:hidden property="parentId"/>
	<html:hidden property="isDel"/>
	<html:hidden property="isLeaf"/>
	<html:hidden property="createTime"/>
	<html:hidden property="createrId"/>
	<input type="hidden" name="projectManager" value="${workReportForumsForm.projectManager}"/>
	<input type="hidden" name="forumsId" value="${workReportForumsForm.id}"/>
	
		
<c:if test="${depth != '0'}">
<!-- 部门树使用 -->
<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
						父专题:<eoms:id2nameDB id="${workReportForumsForm.parentId}" beanId="forumsDao2" />
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				专题名称*
			</td>
			<td class="content">
				<input class="text" type="text" name="title"
					id="title" alt="allowBlank:false" value="${workReportForumsForm.title }"/>
			</td>
			<td class="label">
				项目经理*
			</td>
			<td class="content">
				<input class="text" type="text" readonly="readonly" name="showProjectManager" id="showProjectManager" 
				alt="allowBlank:true,vtext:'请选择项目经理'"
				value="<eoms:id2nameDB id='${workReportForumsForm.projectManager}' beanId='tawSystemUserDao'/>" />
				<input type="hidden" name="projectManager" id="projectManager"
					value="${workReportForumsForm.projectManager}" /> 
			</td>
		</tr>
		<tr>
			<td class="label">
				项目描述
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="description" id="description" alt="allowBlank:true" >${workReportForumsForm.description}</textarea>
			</td>
		</tr>				
</c:if>

<!-- 当节点深度为0即根节点时，只能在该节点下新增项目，同时加入了新增项目经理的功能 -->
<c:if test="${depth == '0'}">
<!-- 部门树使用 -->
<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
						新增项目（根节点下请新增一个新的项目）
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				项目名称*
			</td>
			<td class="content">
				<input class="text" type="text" name="title"
					id="title" alt="allowBlank:false" value="${workReportForumsForm.title }"/>
			</td>
			<td class="label">
				项目经理*
			</td>
			<td class="content">
				<input class="text" type="text" readonly="readonly" name="showProjectManager" id="showProjectManager" 
				alt="allowBlank:false,vtext:'请选择项目经理'"
				value="<eoms:id2nameDB id='${workReportForumsForm.projectManager}' beanId='tawSystemUserDao'/>" />
				<input type="hidden" name="projectManager" id="projectManager"
					value="${workReportForumsForm.projectManager}" /> 
			</td>
		</tr>
		<tr>
			<td class="label">
				项目描述
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="description" id="description" alt="allowBlank:true" >${workReportForumsForm.description }</textarea>
			</td>
		</tr>			
</c:if>
</table>
	<fieldset>
		<html:submit styleClass="button" property="method.save" value="保存"></html:submit>
		<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
		<input type="button" class="btn" value="新增模板" onclick="onSave();" />
	</fieldset>
</html:form>


<script type="text/javascript">

var myJ = $.noConflict();

myJ(function(){
	v = new eoms.form.Validation({form:'workReportForumsForm'});
	myJ("#showProjectManager").bind("click",function(event) {
			var showChkFldId = "showProjectManager";
			var saveChkFldId = "projectManager";
			showTree(showChkFldId,saveChkFldId);
	});
});
function onSave(){
	myJ("#workReportForumsForm").attr("action","template.do?method=addTemplate").submit();
}
function showTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			//Example data:"[{id:'ceshi1',name:'<eoms:id2nameDB id="ceshi1" beanId="tawSystemUserDao"/>',nodeType:'user'}]"
			var data = "[]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/xtree.do?method=userFromDept';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'使用部门',
			      treeChkMode:'single',
			      treeChkType:'user',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>