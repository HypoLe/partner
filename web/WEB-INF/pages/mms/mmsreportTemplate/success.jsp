<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<div class="successPage">
	<h1 class="header">操作成功！</h1>
	<div class="content">
	您的操作已成功执行。<br/>
	</div>
</div>

<html:form action="mmsreportTemplates.do?method=add" method="post" styleId="mmsreportTemplateForm">
<table>
	<tr>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<%
		//<td>
			 //<html:submit styleClass="button" onclick="bCancel=true">
			//返回 
			//</html:submit>
		//</td>
		%>
	</tr>
</table>
</html:form>

<%@ include file="/common/footer_eoms_innerpage.jsp"%>