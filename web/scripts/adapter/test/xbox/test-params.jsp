<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<!-- 测试 自定义传送后台的参数

标签方式给默认面板设置树图加载默认参数：
  jsCfg="params:{'属性1名称':'属性1值','属性2名称':'属性2值'...}"
  
标签方式给默认面板设置树图加载时往后台传送当前节点属性：
  jsCfg="attrParams:'属性1名称'"
  jsCfg="attrParams:['属性1名称','属性2名称'...]" 

标签方式非默认面板：直接设置属性

JS方式设置时，直接在设置选项对象中或其panelsConfig属性中添加params或attrParams设置即可
-->
<style type="text/css">
.panle{
  margin:10px;
  border:1px solid #eee;
  padding:10px;
}
</style>

<form id="theform">	
	<div class="panle">
	  <input type="button" name="showd" id="showd" class="btn" value="标签方式"/>
	  <input type="text" name="saved" id="saved" class="text"/>
	  <eoms:xbox id="cc" dataUrl="/xtree.do?method=area" rootId="-1" rootText="area" valueField="saved" handler="showd"
		textField="showd" jsCfg="params:{a:1,b:2},attrParams:['id','iconCls']"
		panels="[{dataUrl:'/xtree.do?method=userFromDept',text:'test',attrParams:'leaf'}]"
	  ></eoms:xbox>
	</div>
			
	<div class="panle">
	  <input type="button" name="showdjs" id="showdjs" class="btn" value="JS方式"/>
	  <input type="text" name="savedjs" id="savedjs" class="text"/>
	</div>
</form>

<script type="text/javascript">
Ext.onReady(function(){
	var url = "/xtree.do?method=userFromDept";
	var cfg = {
		btnId:'showdjs',
		treeDataUrl : url,treeRootId:'-1',treeRootText:'user',
		treeChkType:'user',showChkFldId:'showdjs',saveChkFldId:'frsave',attrParams:'leaf',
		panelsConfig:[{dataUrl:"/xtree.do?method=userFromDept",text:"测试",attrParams:['id','iconCls','ff']}]
	}
	tree = new xbox(cfg);
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
