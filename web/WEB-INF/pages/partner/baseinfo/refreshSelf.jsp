<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_innerpage.jsp"%>
<script type="text/javascript">
	try{
			  //刷新父框架中当前选中的节点
    parent.AppFrameTree.reloadNode();
	  //刷新自己
	location.href='${app}/partner/baseinfo/${actionDo}.do?method=search&nodeId=${treeNodeId}';
	}catch(e){}
</script>
<div class="successPage">
	<h1 class="header">操作成功！</h1>
	<div class="content">
	您的操作已成功执行。<br/>
	</div>
</div>
<%@ include file="/common/header_eoms_innerpage.jsp"%>