<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
  <script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
  <script type="text/javascript">
  var config = {
    /**************
 	* Tree Configs
 	**************/  
	treeGetNodeUrl:"${app}/workbench/contact/tawWorkbenchContacts.do?method=getNodes",
    treeRootId:'-1',
	treeRootText:"所有分组",
	pageFrame:'formPanel-page',
	pageFrameId:'formPanel-page',
	onClick:{url:"${app}/workbench/contact/tawWorkbenchContactMain.do?method=doSearch",text:""},
	ctxMenu:[
		{id:'Child', text:'新建分组',cls:'new-mi',url:'<c:url value="/workbench/contact/tawWorkbenchContactGroups.do?method=saveToPage&nodeid=" />',rootCanShow:true},
		{id:'Edit', text:'修改分组',cls:'edit-mi',url:'<c:url value="/workbench/contact/tawWorkbenchContactGroups.do?method=xget&nodeid=" />'},
		{id:'Childs', text:'新增人员',cls:'new-mi',url:'<c:url value="/workbench/contact/tawWorkbenchContacts.do?method=saveToPage&nodeid=" />',rootCanShow:true},
		{id:'Edits', text:'修改人员',cls:'edit-mi',url:'<c:url value="/workbench/contact/tawWorkbenchContacts.do?method=xget&nodeid=" />'},
		{id:'Lists', text:'人员详细',cls:'list-mi',url:'<c:url value="/workbench/contact/tawWorkbenchContacts.do?method=xlist&nodeid=" />'},
		{id:'Search', text:'查询',cls:'list-mi',url:'<c:url value="/workbench/contact/tawWorkbenchContactMain.do?method=doSearch&nodeid=" />'},
		{id:'Delete', isDelete:true, text:'删除',cls:'remove-mi',url:'<c:url value="/workbench/contact/tawWorkbenchContacts.do?method=xdelete&nodeid=" />'}
	],//end of ctxMenu
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
  Ext.onReady(AppFrameTree.init, AppFrameTree);
  </script>
  <style type="text/css">  
  	body{background-image:none;}
  </style> 
<div id="headerPanel" class="x-layout-inactive-content">
	<h1>通讯录</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
	    <dt>功能说明</dt>
	    <dd>个人通讯录是记录本人联系人的信息，如手机号码，邮箱等。</dd>
	    <dd>提供了个性化的分组功能，将不同的联系人按自己的要求放在各自的分组内，这样将很方便查找联系人。</dd>
	    <dd>还提供了按照分组的批量导入和导出功能，这样可以同时添加很多的联系人</dd>
	    <dt>新建分组</dt>
		<dd>在【所有分组】上点击右键。建立分组。在所建立的分组上点击右键可以【修改】和【删除】分组。</dd>
		<dt>新增人员</dt>
		<dd>在所选择的分组上点击右键 ，选择【新增人员】，也可以直接在所有分组上点击右键选择【新增人员】，在新增人员的页面内有一些是必填项，请认真填写，之后点击【保存】。在新增的人员上面点击右键可以【修改人员】，【人员详细】，和【删除人员】 其中在人员详情内点击邮箱地址可以直接给这个发送</dd>
		<dt>导入</dt>
		<dd>点击【导入】，选择需要导入的分组名称，点击【导入】。注意的是导入的时候要按照一定的模板。</dd>
		<dt>导出</dt>
		<dd>点击【导出】，选择需要导入的分组名称，点击【导出】。直接导出excel的形式</dd>
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