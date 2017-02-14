<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<form id="frm">

<!-- 测试chooser分类的隐藏和显示 -->
<!-- 
测试数据：
故障工单 flowId:51, roleId:191,192
软件变更 flowId:43 , roleId:255
 -->
<eoms:chooser id="test" type="role" roleId="191" flowId="51" config="{returnJSON:true,showLeader:true}"
	category="[{id:'dealPerformer',childType:'user,subrole',text:'派发',allowBlank:false,vtext:'请选择派发人'}]" 
/>

<input type="submit" value="提交"/>
</form>
<script type="text/javascript">
    var v = new eoms.form.Validation({form:"frm"});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
