<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.assEva.util.AssEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assEvaTemplateForm'});
try{
	// 部门树
	var	deptTreeAction='${app}/xtree.do?method=dept';
	deptViewer = new Ext.JsonView("dept-list",
		'<div id="dept-{id}" class="viewlistitem-{nodeType}">{name};</div>',
		{ 
			multiSelect: true,
			emptyText : '<div>未选择接收单位</div>'
		}
	);
	var deptStr = '${requestScope.orgIds}';
	deptViewer.jsonData = eoms.JSONDecode(deptStr);
	deptViewer.refresh();
	
	// 人员树
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
	userViewer = new Ext.JsonView("user-list",
		'<div id="user-{id}" class="viewlistitem-{nodeType}">{name};</div>',
		{ 
			multiSelect: true,
			emptyText : '<div>未选择接收人</div>'
		}
	);
	var userStr = '${requestScope.orgIds}';
	userViewer.jsonData = eoms.JSONDecode(userStr);
	userViewer.refresh();

} catch(e){}
})
</script>
<html:form action="/assEvaTemplates.do?method=distribute" styleId="assEvaTemplateForm" method="post"> 
<table class="formTable" id="form">
	<caption>
		<div class="header center">查看考核模板</div>
		<div class="help">     
        <span>
      	<dl>
      	  <dt class="warn">该模板已经下发，不能进行此操作！</dt>
      	</dl>
      	</span>
        <div class="clear"></div>
      </div>
	</caption>
	<tr>
		<td class="label">
			模板名称
		</td>
		<td class="content" colspan="3">
			${assEvaTemplateForm.templateName}
		</td>
	</tr>
	<tr>
		<td class="label">
			考核周期
		</td>
		<td class="content">
			<eoms:dict key="dict-assEva" dictId="cycle" itemId="${assEvaTemplateForm.cycle}" beanId="id2nameXML" />
		</td>
		<td class="label">
			接收单位类型
		</td>
		<td class="content">
			<eoms:dict key="dict-assEva" dictId="orgType" itemId="${assEvaTemplateForm.orgType}" beanId="id2nameXML" />
		</td>
	</tr>
	<tr>
		<td class="label">
			起始时间
		</td>
		<td class="content">
			${assEvaTemplateForm.startTime}
		</td>
		<td class="label">
			结束时间
		</td>
		<td class="content">
			${assEvaTemplateForm.endTime}
		</td>
	</tr>
	<tr id="deptTr" style="display:none">
		<td class="label">
			接收单位
		</td>
		<td class="content" colspan="3">
			
		</td>
	</tr>
	<tr id="userTr">
		<td class="label">
			接收人
		</td>
		<td class="content" colspan="3">
			<div id="user-list" class="viewer-box"></div>
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			${assEvaTemplateForm.remark}
		</td>
	</tr>
</table>

</html:form>
<c:if test="${assEvaTemplateForm.id != null}">
<iframe id="fileList" name="fileList" frameborder="0" width="100%" height="100%" 
	src="${app}/assEva/assEvaKpis.do?method=listKpiOfTemplate&templateId=${assEvaTemplateForm.id}">
</iframe>
</c:if>
<script type="text/javascript">
  try{
	//刷新父框架中的整颗树
	parent.AppFrameTree.refreshTree();
  }catch(e){}
</script>
<c:if test="${assEvaTemplateForm.orgType == 'dept'}">
<script type="text/javascript">
document.getElementById('deptTr').style.display='block';
</script>
</c:if>
<c:if test="${assEvaTemplateForm.orgType == 'user'}">
<script type="text/javascript">
document.getElementById('userTr').style.display='block';
</script>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>