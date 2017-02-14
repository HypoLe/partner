<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<!-- 测试js方式创建xbox -->
<form id="theform">	
	<div class="panle">
	  <div id="user-list" class="viewer-list"></div>
	  
	  <input type="text" id="deptName" class="text"/><br />
	  <input type="text" id="dept" class="text" style="width:500px"/><br />
	  <input type="button" id="btn" class="text" value="树图"/>

	  <%--  
	  <eoms:xbox id="cc" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText="area" valueField="saved" handler="showd"
		textField="showd" mode="selectChildren"
	  ></eoms:xbox>
	  --%>
	</div>
</form>

<script type="text/javascript">
	Ext.onReady(function(){
		var	url = '${app}/xtree.do?method=dept';	
		var config = {
			btnId:'btn',
			treeDataUrl : url,treeRootId:'-1',treeRootText:'部门',treeChkType:'dept',
			showChkFldId:'deptName',
			saveChkFldId:'dept',
			panelsConfig:[{text:'个性化部门',dataUrl:'/individual/individualTrees.do?method=getNodes4ForumSingle'}],
			data:[{text:'默认部门',name:'默认部门',id:'1',nodeType:'dept'}]
		};
		
		//旧的onBeforeCheck方法，不推荐使用，
		//以下代码为使用onBeforeCheck判断是否为叶子节点，不是叶子节点不能选择
		//下面有使用beforeselect事件来完成这一工作的代码
		//xbox.prototype.onBeforeCheck = function(node, checked){
		//	if(checked && !node.attributes.leaf) return false;
		//	return true;
		//}
		
		userTree = new xbox(config);
		
		userTree.on('beforeselect',function(node){
			return !!node.attributes.leaf;
		});
	});
</script>
<%@ include file="/common/footer_eoms.jsp"%>
