<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript" src="${app}/evaluation/ext1.1.1/AppFrameTree.js"></script>
<script type="text/javascript">
  var config = {                   
    /**************R
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"${app}/partner/evaluation/theTree.do?method=gettreeNodeData",
    treeRootId:"0000",
	treeRootText:'考核模板',
	pageFrameId:'formPanel-page',  
	onClick:{url:"${app}/partner/evaluation/checkRule.do?method=showDetail&nodeId=",text:""},
	ctxMenu:[
		{id:'newnode', text:'新增考核模板分类',cls:'new-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=addTemplate&nodeId='},
		{id:'newTemplateType', text:'添加考核项目',cls:'new-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=addTemplate&nodeId='},
		{id:'newTemplateMajor', text:'添加考核专业',cls:'new-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=addTemplate&nodeId='},
		{id:'editTemplateMajor', text:'修改考核专业',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=updateTemplate&nodeId='},
		{id:'editTemplateType', text:'修改考核模板',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=updateTemplate&nodeId='},
		{id:'editProject', text:'修改考核项目',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=updateTemplate&nodeId='},
		{id:'delTemplateMajor', isDelete:true,text:'删除考核专业',cls:'remove-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=doDeleteTemplate&nodeId='},
		{id:'delTemplateTp', isDelete:true,text:'删除考核模板',cls:'remove-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=doDeleteTemplate&nodeId='},
		{id:'delTemplateType', isDelete:true,text:'删除考核项目',cls:'remove-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=doDeleteTemplate&nodeId='},
		{id:'newProgramme', text:'添加考核大纲',cls:'new-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=addProgramme&nodeId='},
		{id:'newSonProgramme', text:'添加考核子大纲',cls:'new-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=addProgramme&nodeId='},
		{id:'editProgramme', text:'修改考核大纲',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=updateProgramme&nodeId='},
		{id:'delProgramme',isDelete:true, text:'删除考核大纲',cls:'remove-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=doDeleteProgramme&nodeId='},
		{id:'newQuota', text:'添加考核指标',cls:'new-mi',url:eoms.appPath+'/partner/evaluation/quota.do?method=edit&nodeId='},
 		{id:'editQuota',  text:'修改考核指标',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/quota.do?method=goToEdit&nodeId='},
		{id:'detailQuota',  text:'查看考核指标',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/quota.do?method=showDetail&nodeId='},
		{id:'delQuota', isDelete:true, text:'删除考核指标',cls:'remove-mi',url:eoms.appPath+'/partner/evaluation/quota.do?method=delete&nodeId='},
		{id:'newCheckRule', text:'添加考核规则',cls:'new-mi',url:eoms.appPath+'/partner/evaluation/checkRule.do?method=edit&nodeId='},
		{id:'editCheckRule',  text:'修改考核规则',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/checkRule.do?method=goToEdit&nodeId='},
		{id:'delCheckRule', isDelete:true, text:'删除考核规则',cls:'remove-mi',url:eoms.appPath+'/partner/evaluation/checkRule.do?method=delete&nodeId='},
		{id:'editTemplate', text:'预览/激活考核模板',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=templatePreview&nodeId='},
		{id:'edTemplate', text:'查看/失效考核模板',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=templatePreview&nodeId='},
        {id:'viewTemplate', text:'查看考核模板',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=templateOnlyView&nodeId='},
        {id:'viewTemplateXM', text:'查看考核项目',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=templateOnlyView&nodeId='},
        {id:'viewTemplateZY', text:'查看考核专业',cls:'edit-mi',url:eoms.appPath+'/partner/evaluation/evaluation.do?method=templateOnlyView&nodeId='}
	],//end of ctxMenu
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
  Ext.onReady(AppFrameTree.init, AppFrameTree);
</script>
<div id="headerPanel" class="x-layout-inactive-content">
	<h1>考核模板管理</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<dt>指标模板管理</dt>
		<dd>提供应用于合同的服务质量评估模型的增、删、查、改功能。</dd>
		<dd>评估模型是对代维公司进行评估的依据，主要对KPI进行评估，以树形结构展示，增加用户的可操作性。</dd>
		<dd>内容包括评估模板分类、评估模板、评估指标、任务激活等模块。</dd>
		<dd>按照树节点的层次关系从上到下分为评估模板分类、评估模板、评估指标，其中指标支持无限级。</dd>
		<dd>只有激活的评估模板才能进行评估执行。</dd>
		<dt>评估执行</dt>
		<dd>基站评估数据手工填报。根据生成的评估任务用户进行手工填写，完成评估对象的评分、增扣分原因、备注填写。包括评估执行和评估结果查询模块。</dd>
		<dt>评估报表</dt>
		<dd>对评估执行的评估结果进行不同形式的统计报表展示，包括“不同月份、同一厂商”、“同一月份、不同厂商”2张报表的统计。</dd>	
	</dl>
</div>
<div id="treePanel" class="x-layout-inactive-content">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="formPanel-page" name="formPanel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
