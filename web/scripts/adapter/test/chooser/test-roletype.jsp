<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>

<form id="frm">

<!-- 测试type=role时的设置 -->
<!-- 
设置rolePanel的url地址：
config="{rolePanelUrl:'/role/tawSystemRoles.do?method=xGetSubRoleNodesFromArea2'}
 -->
<eoms:chooser id="test" type="role" roleId="191" flowId="51" config="{rolePanelUrl:'/role/tawSystemRoles.do?method=xGetSubRoleNodesFromArea2'}"
	category="[
	 {id:'send',text:'派发',allowBlank:false,vtext:'sss',limit:3}
	,{id:'copy',text:'抄送',childType:'user,subrole,dept',limit:2}
	,{id:'audit',text:'审核',childType:'user',limit:-1}
	,{id:'ff',text:'批准',childType:'user',limit:-1}
	]"
/>

<br/>

<script type="text/javascript">

</script>
<input type="button" value="role 186" onclick="javascript:chooser_test.setRoleId(186)">
<input type="button" value="role 244" onclick="javascript:chooser_test.setRoleId(244)">
</form>
<%@ include file="/common/footer_eoms.jsp"%>
