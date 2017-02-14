<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
  <script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
  <script type="text/javascript">
  var config = {
    /**************
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"${app}/message/smsMobilesTemplates.do?method=getNodes4Team",
    treeRootId:'-1',
	treeRootText:"${eoms:a2u('所有用户组')}",
	pageFrameId:'formPanel-page',
	onClick:{url:"<html:rewrite page='/smsMobilesTemplates.do?method=list&id=${smsMobilesTemplatesForm.id}'/>",text:"${eoms:a2u('用户组详细信息')}"},
	ctxMenu:[
		{id:'NewForum', text:'${eoms:a2u('添加用户组')}',rootCanShow:true,cls:'new-mi',url:'<html:rewrite page='/smsMobilesTemplates.do?method=add&id='/>'},		
		{id:'EditForum', text:'${eoms:a2u('修改用户组')}',cls:'edit-mi',url:'<html:rewrite page='/smsMobilesTemplates.do?method=xeditInit&id='/>'},
		{id:'DelForum', isDelete:true, text:'${eoms:a2u('删除')}',cls:'remove-mi',url:'<html:rewrite page='/smsMobilesTemplates.do?method=xdelete&id='/>'}
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
	<!-- <img src="${app}/styles/default/images/header-user.gif">  -->
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	
</div>
<div id="treePanel" class="x-layout-inactive-content">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="formPanel-page" name="formPanel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
