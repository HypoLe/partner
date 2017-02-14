<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_innerpage.jsp"%>
<script type="text/javascript">
	try{
		  //刷新父框架中当前选中的节点
    parent.parent.AppFrameTree.reloadNode();
      //刷新父窗口
	parent.location='${app}/partner/baseinfo/${actionDo}.do?method=search&nodeId=${treeNodeId}';
	}catch(e){}
</script>
<%@ include file="/common/header_eoms_innerpage.jsp"%>