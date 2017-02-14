<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>

<form id="frm">

<!-- 测试在选择前进行的定制处理 -->
<!-- 
测试数据：
故障工单 flowId:51, roleId:191,192
软件变更 flowId:43 , roleId:255
 -->
<eoms:chooser id="test" type="role" roleId="192" flowId="51" config="{subroleId:'some'}"
	category="[
	 {id:'send',text:'派发',allowBlank:false,vtext:'sss',limit:3}
	,{id:'copy',text:'抄送',childType:'user,subrole,dept',limit:2}
	]"
/>

<br/>

<script type="text/javascript">
Ext.onReady(function(){
	// 选择前，将选择的节点数据上添加parentId属性，以备选择时使用parentId代替原节点id
	chooser_test.beforeChoose = function(){
		var data = arguments[0];
		if(data.nodeType=='user'){
  			if(data.parentNode && data.parentNode.attributes.nodeType=='subrole'){
    			data.parentId = data.parentNode.attributes.id;
  			}
  			else if(this.subroleId){
    			data.parentId = this.subroleId;
			}
		}
		eoms.log(data);
		return this.constructor.prototype.beforeChoose.apply(this,arguments);
	};
});
</script>

<br/>
<br/><br/>
</form>
<%@ include file="/common/footer_eoms.jsp"%>
