<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
<script type="text/javascript">
  eoms.loadCSS(eoms.appPath+"/styles/eva/style.css");
  var config = {
    /**************
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"${app}/eva/evaTrees.do?method=getTemplateNodes",
    treeRootId:'<%=EvaConstants.TREE_ROOT_ID%>',
	treeRootText:'考核内容',
	pageFrameId:'formPanel-page',
	onClick:{url:"${app}/eva/evaTemplates.do?method=editTemplate&nodeId=",text:""},
	ctxMenu:[
		{id:'newnode', text:'新增模板分类',cls:'new-mi',url:'${app}/eva/evaTemplateTypes.do?method=newTemplateType&nodeId='},
//		{id:'editTemplateType', text:'修改模板分类',cls:'edit-mi',url:'${app}/eva/evaTemplateTypes.do?method=editTemplateType&nodeId='},
//		{id:'newTemplate', text:'新增考核模板',cls:'new-mi',url:'${app}/eva/evaTemplates.do?method=newTemplate&nodeId='},
		{id:'newKpi', text:'新增考核指标',cls:'new-mi',url:'${app}/eva/evaKpis.do?method=listNextLevelKpi&parentNodeId='},
		{id:'editTemplate', text:'修改/激活考核内容',cls:'edit-mi',url:'${app}/eva/evaTemplates.do?method=editTemplate&nodeId='},
		{id:'detailTemplate', text:'查看考核内容',cls:'edit-mi',url:'${app}/eva/evaTemplates.do?method=editTemplate&nodeId='},
		{id:'executeStatus', text:'考核执行情况',cls:'edit-mi',url:'${app}/eva/evaReportInfos.do?method=getAllScroe&nodeId='},
		{id:'editKpi', text:'修改考核指标',cls:'edit-mi',url:'${app}/eva/evaKpis.do?method=editKpi&nodeId='},
		{id:'delTemplate', isDelete:true, text:'删除考核内容',cls:'remove-mi',url:'${app}/eva/evaTemplates.do?method=removeTemplate&nodeId='},
		{id:'delKpi', isDelete:true, text:'删除考核指标',cls:'remove-mi',url:'${app}/eva/evaKpis.do?method=delKpiFromTree&nodeId='},
		{id:'editTemplateMess', text:'修改考核内容',cls:'edit-mi',url:'${app}/eva/evaTemplateTemps.do?method=editTemplateTemp&nodeId='},
		{id:'execute', text:'执行',cls:'edit-mi',url:'${app}/eva/evaReportInfos.do?method=getTaskForTemplate&nodeId='}
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
	<h1>考核内容管理</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<dt>指标内容管理</dt>
		<dd>提供应用于合同的服务质量考核模型的增、删、查、改功能。</dd>
		<dd>考核模型是对合作伙伴公司进行考核的依据，主要对KPI进行考核，以树形结构展示，增加用户的可操作性。</dd>
		<dd>内容包括考核内容分类、考核内容、考核指标等模块。</dd>
		<dd>按照树节点的层次关系从上到下分为考核内容分类、考核内容、考核指标，其中指标支持无限级。</dd>
		<dd>只有未执行过的考核内容才能进行修改。</dd>
		<dt>考核执行</dt>
		<dd>根据生成的考核任务用户进行手工填写，完成考核对象的评分、增扣分原因、备注填写。包括考核执行和考核结果查询模块。</dd>
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