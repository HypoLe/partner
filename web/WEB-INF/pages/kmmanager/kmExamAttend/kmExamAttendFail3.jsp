<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_innerpage.jsp"%>
<script type="text/javascript">
	try{
	  //刷新父框架中的整颗树
	  parent.AppFrameTree.refreshTree();
	  //刷新父框架中当前选中的节点
	  //parent.AppFrameTree.reloadNode();
	}catch(e){}
</script>
<div class="failurePage">
	<h1 class="header">操作失败</h1>
	<div class="content">
		已有同一账号正在参加考试，请稍后再登陆考试!<br/>
	</div>
</div>
<%@ include file="/common/header_eoms_innerpage.jsp"%>