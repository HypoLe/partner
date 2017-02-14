<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<script type="text/javascript" src="${app}/scripts/partner/contract/AppFrameTreeCt.js"></script>
<script type="text/javascript">
	AppFrameTreeCt.fixUrl=function(url) {
			if (url.substr(url.length - 1) == "=") {
				return url + this.selNode.id;
			} else if (url.indexOf('?') > 0) {
				return url + "&orgId=" + this.selNode.id+ "&orgType=" + this.selNode.attributes.nodeType;
			} else {
				return url + "?orgId=" + this.selNode.id+ "&orgType=" + this.selNode.attributes.nodeType;
			}
		};
  var config = {
	/**************
	 * Tree Configs
	 **************/
	treeGetNodeUrl:"${app}/partner/contract/ctAcls.do?method=userFromDept",
	treeRootId:'-1',
	treeRootText:'权限管理',
	pageFrameId:'config-page',
	ctxMenu:[
		{id:'content', text:'合同内容权限',cls:'edit-mi',url:'${app}/partner/contract/ctAcls.do?method=edit'},
		{id:'file', text:'文件管理权限',cls:'edit-mi',url:'${app}/partner/contract/ctAcls.do?method=editFileAcl'}
	],//end of ctxMenu 
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
  Ext.onReady(AppFrameTreeCt.init, AppFrameTreeCt);

</script>
<div id="headerPanel" class="x-layout-inactive-content">
	<h1>权限管理</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<b>注：
		在您要配置权限的主体上点击鼠标右键选择合同内容权限(或文件管理权限)进行配置 </b><br>
	<br>
	<b>查看权限:</b><br>
	对目录或对应的文件有查看操作 <br>
	<br>
	<b>增加权限:</b><br>
	对目录或对应的文件可以进行增加操作 <br>
	<br>
	<b>修改权限:</b><br>
	对目录或对应的文件可以进行修改操作 <br>
	<br>
	<b>删除权限:</b><br>
	对目录或对应的文件可以进行删除操作 <br>
	<br>
	<b>授予权限：</b><br>
	对目录或对应的文件给别的用户赋予各种操作权限 <br>
	<br>
	<b>排出权限：</b><br>
	对该用户清除对此目录的所有操作权限（主要用在整个部门配好权限后对某些用户的权限排出上） <br>
	<br>
	<b>操作等级:</b><br>
	操作等级只针对查看 修改 删除等操作进行合同等级或者文件等级的控制 <br>
	
	<br><br><font color="red"><b>管理员所有权限,如果需要相应操作权限请与管理员联系</b></font>
	</dl>
</div>
<div id="treePanel" >
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="config-page" name="config-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>