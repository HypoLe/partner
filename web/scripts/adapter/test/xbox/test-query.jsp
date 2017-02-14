<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<!-- 测试 添加搜索功能 -->
<style type="text/css">
.panle{
  margin:10px;
  border:1px solid #eee;
  padding:10px;
}
</style>
<script src="${app}/scripts/layout/panel-query-debug.js"/></script>
<script type="text/javascript">
//覆盖默认模板
//eoms.panel.Query.prototype.getViewTpl = function(){
//	return '<div class="viewlistitem-user" unselectable="on">{username}</div>';
//};
</script>
<form id="theform">
	
	<div class="panle">
	  <input type="button" name="showd" id="showd" class="text" value="树图搜索"/>
	  <input type="text" name="saved" id="saved" class="text"/>

	  <script type="text/javascript">
		Ext.onReady(function(){
		});
	  </script>
	
	  <eoms:xbox id="q" dataUrl="${app}/xtree.do?method=userFromDept" rootId="-1" rootText="人员树" valueField="saved" handler="showd"
		textField="showd" 
		panels = "[{type:'query',pos:'first']"
	  />
	</div>
	

</form>
<%@ include file="/common/footer_eoms.jsp"%>
