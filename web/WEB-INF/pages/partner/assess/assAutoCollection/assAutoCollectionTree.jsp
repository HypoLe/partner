<jsp:directive.page import="com.boco.eoms.partner.assess.AssAutoCollection.util.AssAutoCollectionConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
<script type="text/javascript">
  var config = {
	/**************
	 * Tree Configs
	 **************/
	treeGetNodeUrl:"${app}/partner/assess/assAutoCollections.do?method=getNodes",
	treeRootId:'<%=AssAutoCollectionConstants.TREE_ROOT_ID%>',
	treeRootText:'自动采集指标',
	pageFrameId:'formPanel-page',
	ctxMenu:[
//		{id:'newnode', text:'新增采集指标分类',cls:'new-mi',url:eoms.appPath+'/partner/assess/assAutoCollections.do?method=autoCollection&nodeId='},
		{id:'newnode', text:'新增采集指标分类',cls:'new-mi',url:eoms.appPath+'/partner/assess/assCollectionTypes.do?method=add&nodeId='},
		{id:'newNode', text:'新增采集指标分类',cls:'new-mi',url:eoms.appPath+'/partner/assess/assCollectionTypes.do?method=add&nodeId='},
		{id:'newCollection', text:'新增采集指标',cls:'new-mi',url:'${app}/partner/assess/assCollectionConfigs.do?method=add&nodeId='},
		{id:'editNode', text:'修改采集指标分类',cls:'edit-mi',url:eoms.appPath+'/partner/assess/assCollectionTypes.do?method=edit&nodeId='},
		{id:'delNode', isDelete:true,  text:'删除采集指标分类',cls:'remove-mi',url:eoms.appPath+'/partner/assess/assCollectionTypes.do?method=remove&nodeId='},
		{id:'editCollection', text:'修改采集指标',cls:'edit-mi',url:'${app}/partner/assess/assCollectionConfigs.do?method=edit&nodeId='},
		{id:'delCollection', isDelete:true, text:'删除采集指标',cls:'remove-mi',url:'${app}/partner/assess/assCollectionConfigs.do?method=remove&nodeId='}
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
	<h1>自动采集指标管理</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<dt>自动采集指标管理</dt>
		<dd>提供采集指标配置的增、删、改功能。</dd>
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