<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>
<form id="frm">

<!-- 测试双击后选择所有子节点 -->
<eoms:chooser id="dbl" type="role" roleId="192" flowId="51"
	category="[
	 {id:'send',text:'派发',allowBlank:false,vtext:'sss',limit:3}
	,{id:'copy',text:'抄送',childType:'user,subrole,dept',limit:2}
	,{id:'audit',text:'审核',childType:'user',limit:-1}
	]"
/>

</form>
<%@ include file="/common/footer_eoms.jsp"%>
