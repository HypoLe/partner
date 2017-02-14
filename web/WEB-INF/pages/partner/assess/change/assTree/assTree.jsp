<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
<script type="text/javascript">
  eoms.loadCSS(eoms.appPath+"/styles/assess/style.css");
  var config = {
    /**************
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"${app}/partner/assess/changeAssTrees.do?method=getTemplateNodes&areaId=${areaId}&partnerNodeType=${partnerNodeType}",
    treeRootId:'<%=PnrEvaConstants.TREE_ROOT_ID%>',
	treeRootText:'评估模板',
	pageFrameId:'formPanel-page',
	onClick:{url:"${app}/partner/assess/changeAssTrees.do?method=detailNode&nodeId=",text:""},
	ctxMenu:[
		{id:'newnode', text:'新增评估模板分类',cls:'new-mi',url:eoms.appPath+'/partner/assess/changeAssTrees.do?method=newTemplateType&areaId=${areaId}&nodeId='},
		{id:'newTemplateType', text:'新增评估模板分类',cls:'new-mi',url:eoms.appPath+'/partner/assess/changeAssTrees.do?method=newTemplateType&areaId=${areaId}&nodeId='},
		{id:'delTemplateType', text:'删除评估模板分类',cls:'remove-mi',url:eoms.appPath+'/partner/assess/changeAssTrees.do?method=removeTemplateType&areaId=${areaId}&nodeId='},
		{id:'editTemplateType', text:'修改评估模板分类',cls:'edit-mi',url:eoms.appPath+'/partner/assess/changeAssTrees.do?method=editTemplateType&areaId=${areaId}&nodeId='},
		{id:'newTemplate', text:'新增评估模板',cls:'new-mi',url:eoms.appPath+'/partner/assess/changeAssTemplates.do?method=newTemplate&areaId=${areaId}&nodeId='},
		{id:'newKpi', text:'新增评估指标',cls:'new-mi',url:eoms.appPath+'/partner/assess/changeAssKpis.do?method=listNextLevelKpi&areaId=${areaId}&parentNodeId='},
		{id:'editTemplate', text:'修改/激活评估模板',cls:'edit-mi',url:eoms.appPath+'/partner/assess/changeAssTemplates.do?method=editTemplate&areaId=${areaId}&nodeId='},
		{id:'editKpi', text:'修改评估指标',cls:'edit-mi',url:eoms.appPath+'/partner/assess/changeAssKpis.do?method=editKpi&areaId=${areaId}&nodeId='},
		{id:'delTemplate', isDelete:true, text:'删除评估模板',cls:'remove-mi',url:eoms.appPath+'/partner/assess/changeAssTemplates.do?method=removeTemplate&areaId=${areaId}&nodeId='},
		{id:'delKpi', isDelete:true, text:'删除评估指标',cls:'remove-mi',url:eoms.appPath+'/partner/assess/changeAssKpis.do?method=delKpiFromTree&areaId=${areaId}&nodeId='},
		{id:'editWeight',  text:'权重配置',cls:'edit-mi',url:eoms.appPath+'/partner/assess/assWeight.do?method=editWeight&areaId=${areaId}&nodeId='},
<%--		{id:'editFactory',  text:'厂商配置',cls:'edit-mi',url:eoms.appPath+'/partner/assess/changeEditFactory.do?method=editFactory&areaId=${areaId}&partnerNodeType=${partnerNodeType}&nodeId='},--%>
		{id:'editCityWeight',  text:'地市权重配置',cls:'edit-mi',url:eoms.appPath+'/partner/assess/changeCityWeights.do?method=edit&areaId=${areaId}&partnerNodeType=${partnerNodeType}&nodeId='}
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
	<h1>评估模板管理</h1>
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
