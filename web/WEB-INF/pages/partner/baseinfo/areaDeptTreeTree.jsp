<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.util.AreaDeptTreeConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
<script type="text/javascript">
  var config = {
	/**************
	 * Tree Configs
	 **************/
	treeGetNodeUrl:"${app}/partner/baseinfo/areaDeptTrees.do?method=getNodes",
	treeRootId:'<%=AreaDeptTreeConstants.TREE_ROOT_ID%>',
	treeRootText:'<fmt:message key="areaDeptTree.tree.rootText"/>',
	pageFrameId:'formPanel-page',
	onClick:{url:"${app}/partner/baseinfo/pnrBaseClicks.do?method=search&nodeId=",text:""},
	ctxMenu:[
//		{id:'newnode', text:'<fmt:message key="areaDeptTree.tree.add"/>',cls:'new-mi',url:eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=add&nodeId='},
		{id:'NewFactory', text:'<fmt:message key="areaDeptTree.tree.NewFactory"/>',cls:'new-mi',url:eoms.appPath+'/partner/baseinfo/partnerDepts.do?method=add&nodeId='},
		{id:'ListFactory', text:'查询合作伙伴',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/partnerDepts.do?method=search&nodeId='},
		{id:'EditFactory', text:'编辑合作伙伴',cls:'edit-mi',url:eoms.appPath+'/partner/baseinfo/aptitudes.do?method=newExpert&nodeId='},
		{id:'DelFactory', text:'删除合作伙伴',cls:'remove-mi',url:eoms.appPath+'/partner/baseinfo/partnerDepts.do?method=remove&nodeId='},
		{id:'NewUser', text:'新增人力信息',cls:'new-mi',url:eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=add&nodeId='},
		{id:'SearchUser', text:'查询人力信息',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=search&nodeId='},
		{id:'SearchInFactory', text:'查询',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=search&in=factory&nodeId='},
		{id:'Statistics', text:'统计',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/pnrStats.do?method=getNumUsers&nodeId='},
		{id:'NewInstrument', text:'新增仪器仪表',cls:'new-mi',url:eoms.appPath+'/partner/baseinfo/tawApparatuss.do?method=add&nodeId='},
		{id:'SearchInstrument', text:'查询仪器仪表',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/tawApparatuss.do?method=search&nodeId='},
		{id:'NewCar', text:'新增车辆管理',cls:'new-mi',url:eoms.appPath+'/partner/baseinfo/tawPartnerCars.do?method=add&nodeId='},
		{id:'SearchCar', text:'查询车辆管理',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/tawPartnerCars.do?method=search&nodeId='},
		{id:'NewOil', text:'新增油机',cls:'new-mi',url:eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=add&nodeId='},
		{id:'SearchOil', text:'查询油机',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/tawPartnerOils.do?method=search&nodeId='},
//		{id:'edtnode', text:'<fmt:message key="areaDeptTree.tree.edit"/>',cls:'edit-mi',url:eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=edit&nodeId='},
//		{id:'delnode', isDelete:true, text:'<fmt:message key="areaDeptTree.tree.delete"/>',cls:'remove-mi',url:eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=remove&nodeId='},
		{id:'SearchInProvince', text:'查询',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=search&in=province&nodeId='},
		{id:'SearchInArea', text:'查询',cls:'list-mi',url:eoms.appPath+'/partner/baseinfo/partnerUsers.do?method=search&in=area&nodeId='}
		
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
	<h1><fmt:message key="areaDeptTree.tree.header" /></h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<dt>地域部门树</dt>
		<dd>地域部门树节点层次结构是：第一级根节点是地域部门树；<br>
		    第二级是省级节点，代表省公司，这一级节点只能有一个；<br>
		    第三级节点是地市节点，代表每个省的地市；<br>
		    第四级节点是合作伙伴（厂商）节点，是地市的合作伙伴；<br>
		    第五级节点是单表菜单节点，有人力信息、仪器仪表、车辆管理、油机管理，当建好合作伙伴节点后自动生成。<br>
	    </dd>
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