<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.tranEva.util.TranEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
<script type="text/javascript">
  eoms.loadCSS(eoms.appPath+"/styles/tranEva/style.css");
  var config = {
    /**************
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"${app}/partner/tranEva/tranEvaTrees.do?method=getKpiNodes",
    treeRootId:'<%=TranEvaConstants.TREE_ROOT_ID%>',
	treeRootText:'考核指标',
	pageFrameId:'formPanel-page',
	ctxMenu:[
		{id:'newnode', text:'新建指标分类',cls:'new-mi',url:'${app}/partner/tranEva/tranEvaTrees.do?method=newNode&nodeId='},
		{id:'NewKpi', text:'新建指标',cls:'new-mi',url:'${app}/partner/tranEva/tranEvaKpis.do?method=newKpi&nodeId='},
		{id:'EditKpi', text:'修改指标',cls:'edit-mi',url:'${app}/partner/tranEva/tranEvaKpis.do?method=editKpi&nodeId='},
		{id:'EditKpiType', text:'修改指标分类',cls:'edit-mi',url:'${app}/partner/tranEva/tranEvaTrees.do?method=editNode&nodeId='},
		{id:'DelKpi', isDelete:true, text:'删除指标',cls:'remove-mi',url:'${app}/partner/tranEva/tranEvaKpis.do?method=delKpiFromTree&nodeId='},
		{id:'DelKpiType', isDelete:true, text:'删除指标分类',cls:'remove-mi',url:'${app}/partner/tranEva/tranEvaTrees.do?method=removeNode&nodeId='}
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
	<h1>考核指标管理</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<dt></dt>
		<dd></dd>
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