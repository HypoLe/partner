<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_innerpage.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
  try{
	parent.AppFrameTree.refreshNodeTree();
  }catch(e){}
 function jump(count){
  	window.setInterval(function(){
	  	count--;
	  	if(count>0){
	  	document.getElementById("num").innerHTML=count;
	  	}else{
	  		location.href="<%=request.getContextPath()%>/partner/resourceInfo/process.do?method=goToApplyOperatePage";
	  	}
  },1000);
}
  Ext.onReady(function(){
  	jump(5);
  });
</script>
<div class="successPage">
	<h1 class="header">${eoms:a2u('操作成功！')}</h1>
	<div class="content">
	${eoms:a2u('您的操作已成功执行,5秒后自动跳转到列表页面。当前还剩余')}
	<span id="num" style="color: red">5</span>s</br>
	
	</div>
</div>
<%@ include file="/common/footer_eoms_innerpage.jsp"%>