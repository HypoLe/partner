<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>
<script src="${app}/scripts/layout/panel-query-debug.js"></script>

<form id="frm">

<!-- 测试搜索面板 -->
<!-- 
<input style="display:none" title="当表单只有一个输入框时，回车会自动提交"/> -- 已由keydonw事件解决
 -->
<eoms:chooser id="q" type="role" roleId="191" flowId="51"
	category="[
	 {id:'send',text:'派发',allowBlank:false,vtext:'sss',limit:3}
	,{id:'copy',text:'抄送',childType:'user,subrole,dept',limit:2}
	,{id:'audit',text:'审核',childType:'user',limit:-1}
	]"
	panels="[{text:'查询人员',type:'query',qparam:'username',pos:'first'}]"
/>

<br/>


</form>
<%@ include file="/common/footer_eoms.jsp"%>
