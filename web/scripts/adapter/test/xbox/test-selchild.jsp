<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<!-- 测试 双击父节点选中所有下级子节点 -->
<style type="text/css">
.panle{
  margin:10px;
  border:1px solid #eee;
  padding:10px;
}
</style>

<form id="theform">	
	<div class="panle">
	  <input type="button" name="showd" id="showd" class="text" value="双击父节点选中所有下级子节点"/>
	  <input type="text" name="saved" id="saved" class="text"/>
	  <eoms:xbox id="cc" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText="area" valueField="saved" handler="showd"
		textField="showd" mode="selectChildren"
	  ></eoms:xbox>
	</div>
</form>
<%@ include file="/common/footer_eoms.jsp"%>
