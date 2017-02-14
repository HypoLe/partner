<jsp:directive.page import="com.boco.eoms.partner.assess.AssFee.util.FeeTreeConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<fmt:bundle basename="com/boco/eoms/partner/assess/AssFee/config/ApplicationResources-feetree">

<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
<script type="text/javascript">
  var config = {
	/**************
	 * Tree Configs
	 **************/
	treeGetNodeUrl:"${app}/partner/assess/feeTrees.do?method=getNodes",
	treeRootId:'<%=FeeTreeConstants.TREE_ROOT_ID%>',
	treeRootText:'光缆设备',
	pageFrameId:'formPanel-page',
	onClick:{url:"${app}/partner/assess/feeTrees.do?method=edit",text:""},
	ctxMenu:[
		{id:'newnode', text:'新增',cls:'new-mi',url:'${app}/partner/assess/feeTrees.do?method=add&nodeId='},
		{id:'edtnode', text:'修改',cls:'edit-mi',url:'${app}/partner/assess/feeTrees.do?method=edit&nodeId='},
		{id:'delnode', isDelete:true, text:'删除',cls:'remove-mi',url:'${app}/partner/assess/feeTrees.do?method=remove&nodeId='}
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
	<h1>线路后评估树</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		帮助
	</dl>
</div>
<div id="treePanel" class="x-layout-inactive-content">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="formPanel-page" name="formPanel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>