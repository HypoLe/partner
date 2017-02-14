<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>



<script type="text/javascript">
  	  
 Ext.onReady(function() {
 
	var isdel=document.getElementById("isDel").value;
	if(isdel=="y"){//当删除时
	   parent.AppFrameTree.delReloadNode();
	 //   alert(isdel);
	} 
	else{
	  if(isdel=="b"){//在树的根节点上添加模板。 
	     parent.AppFrameTree.refreshTree();
	    //   alert(isdel);
	  }
	  else{
	  
	    parent.AppFrameTree.delReloadNode();
	  }
	} 
});
 
</script>
<input type="hidden" id="isDel" name="isDel" value="${isDel}" />	
<div class="successPage">
	<h1 class="header">操作成功！</h1>
	<div class="content">
	您的操作已成功执行。<br/>
	</div>
</div>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>