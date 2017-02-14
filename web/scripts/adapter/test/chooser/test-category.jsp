<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>

<form id="frm">

<!-- 测试chooser分类的隐藏和显示 -->
<!-- 
测试数据：
故障工单 flowId:51, roleId:191,192
软件变更 flowId:43 , roleId:255
 -->
<eoms:chooser id="test" type="role" roleId="191" flowId="51"
	category="[
	 {id:'send',text:'派发',allowBlank:false,vtext:'sss',limit:3}
	,{id:'copy',text:'抄送',childType:'user,subrole,dept',limit:2}
	,{id:'audit',text:'审核',childType:'user',limit:-1}
	,{id:'ff',text:'批准',childType:'user',limit:-1}
	]"
/>

<br/>

<script type="text/javascript">
	function hideCategory(a){
		Ext.each(a,function(c){
			chooser_test.category[c].hide();
		});
		chooser_test.show();
	}
	function showCategory(a){
		Ext.each(a,function(c){
			chooser_test.category[c].show();
		});
		chooser_test.show();
	}
</script>

<br/>
<input type="button" value="hide：" onclick="javascript:hideCategory(eoms.$('hide').value.split(','))"/>
<input id="hide" value="0,1" size="6" class="text"/>
<br /><br/>
<input type="button" value="show：" onclick="javascript:showCategory(eoms.$('show').value.split(','))"/>
<input id="show" value="2,3" size="6" class="text"/>
<br/><br/>
<input type="button" value="setDataUrl" onclick="javascript:chooser_test.setDataUrl('/xtree.do?method=userFromDept',1)">
<input type="button" value="0limitto2" onclick="javascript:chooser_test.category[0].attr('limit',2);chooser_test.reset();">
</form>
<%@ include file="/common/footer_eoms.jsp"%>
