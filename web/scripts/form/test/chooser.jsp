<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext_debug.jsp"%>
<!-- 
<script type="text/javascript" src="${app}/scripts/adapter/eos-adapter.js"></script>
 -->
<form id="frm">
	  <input type="button" name="usert" id="usert" value="请选择部门" class="btn"/>
	  <input type="text" name="showd2" id="showd2" value="" class="text" alt="allowBlank:false"/>
	  <input type="hidden" name="saved2" id="saved2"/>
	  <%--
	  <eoms:xbox id="userTree" dataUrl="${app}/xtree.do?method=dept" rootId="-1" rootText="部门树" valueField="saved2" handler="usert"
		textField="showd2" viewer="true" 
		data='[{"id":"2","name":"集团公司","nodeType":"dept"},{"id":"1","name":"省公司","nodeType":"dept"}]'
	  ></eoms:xbox>
	  --%>
	  <eoms:xbox id="dictTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId="1" rootText="专业树" valueField="saved2" handler="usert"
		textField="showd2" viewer="true" mode="clearPathById" data="[{'id':'Gongyufeng','name':'Gongyufeng','text':'Gongyufeng','nodeType':'user'}]"
	  ></eoms:xbox>


	<fieldset id="link1">
	 <legend>
			最简单的派发树，默认含有部门/人员树,可选择任意类型		 
	 </legend>
<eoms:chooser id="test0" category="[{id:'send1',text:'派发',childType:'user,dept',limit:3,allowBlank:false},{id:'copy2',text:'抄送'}]"/>
</fieldset>

<!-- 自定义面板的派发树 -->
<eoms:chooser id="test1"
	category="[{id:'send1',text:'派发',childType:'user,subrole',allowBlank:false,limit:-1,vtext:'请选择派发人'},{id:'c',text:'copy',limit:-1}]"
	panels="[
		{text:'当前角色1',dataUrl:'/role/tawSystemRoles.do?method=xGetSubRoleNodesFromAreaTwoLevel&roleId=190&flowId=53', rootId:'1201'},
		{text:'dept',dataUrl:'/xtree.do?method=userFromDept'},
		{text:'个性化部门',dataUrl:'/individual/individualTrees.do?method=getNodes4ForumSingle'}
	]"
/>

<%-- 
<!-- 派发树测试 -->
<eoms:chooser id="test1"
	category="[{id:'send1',text:'派发',limit:-1},{id:'c',text:'copy',limit:-1}]"
	panels="[
		{text:'子角色',dataUrl:'/role/tawSystemRoles.do?method=xGetSubRoleNodesFromArea&roleId=189'},
		{text:'树图节点图标样式',dataUrl:'/test/nodetypes.json'}
	]"
/>
--%>

<!-- 包含已选择数据的派发树 -->
<%--
<c:set var="id">common/role[@type=\'sdf\']/send2</c:set>
<eoms:chooser id="test2"  
	category="[{id:'${id}',text:'派发',allowBlank:false,vtext:'请选择派发人2'}]" 
	data="[
		{id:'liqiuye',nodeType:'user',categoryId:'${id}'}
	]"
/>
--%>

<br/>

<script type="text/javascript">
	Ext.onReady(function(){
		var p = chooser_test1.west.getPanel(1);
		p.on('activate',function(){
			chooser_test1.category[0].hide();
		}); 	
		p.on('deactivate',function(){
			chooser_test1.category[0].show();
		});
		chooser_test0.disable();
		chooser_test1.enable();
		
	});
</script>

<br/><br/>


<br/><br/>
<input type="submit" value="submit" id="btn2" class="submit">
</form>
<%@ include file="/common/footer_eoms.jsp"%>
