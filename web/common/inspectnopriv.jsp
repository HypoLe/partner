<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_innerpage.jsp"%>
<script type="text/javascript">
	//刷新父框架中的整颗树
	parent.AppFrameTree.refreshTree();
	//刷新父框架中当前选中的节点
	//parent.AppFrameTree.reloadNode();
</script>

<div class="successPage">
	<h1 class="header">您无权使用此功能！</h1>
	<div class="content">
	<font color='red'>可能的原因：<br/>1如果您是联通公司用户，请联系管理员为您的部门增加所属区域属性<br/>
	2.如果您是联通公司用户，但是你所在部门的地市没有代维公司<br/></font>
	</div>

</div>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>