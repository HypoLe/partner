<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>

<form id="frm">

<!-- 测试多级地域呈现 -->
<!-- 
测试数据：
故障工单 flowId:51, roleId:191,192
	   flowId:53, rolwId:190
软件变更 flowId:43 , roleId:255
 -->
<eoms:chooser id="test" category="[{id:'send',text:'派发',limit:-1,childType:'subrole'}]"
	panels="[
		{text:'默认角色树',dataUrl:'/role/tawSystemRoles.do?method=xGetSubRoleNodesFromArea&flowId=51'},
		{text:'多级地域角色树',dataUrl:'/role/tawSystemRoles.do?method=xTreeSubRoleByArea&flowId=51'}
	]"
/>

<br/>

<script type="text/javascript">
var changeRole = function(){
    chooser_test.west.getPanel(1).tree.getLoader().baseParams.roleId = "192";
    chooser_test.reset();
    chooser_test.west.getPanel(1).tree.root.reload();
};
Ext.onReady(function(){
  chooser_test.west.getPanel(1).tree.getLoader().baseParams.roleId = "191";
})
</script>

</form>
<%@ include file="/common/footer_eoms.jsp"%>
