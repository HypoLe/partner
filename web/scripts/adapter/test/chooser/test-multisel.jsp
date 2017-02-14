<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>

<form id="frm">

<!-- 测试按下ctrl键后多选的功能 -->
<!-- 
测试数据：
故障工单 flowId:51, roleId:191,192
软件变更 flowId:43 , roleId:255
 -->
<eoms:chooser id="test" type="role" roleId="192" flowId="51"
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

<br/>
<br/><br/>
</form>
<%@ include file="/common/footer_eoms.jsp"%>
