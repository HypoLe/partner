<%@ page language="java" import="com.boco.eoms.base.util.ApplicationContextHolder,com.boco.eoms.extra.supplierkpi.webapp.util.SupplierkpiAttributes" %> 
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import ="java.util.ArrayList"%>
<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
  <script type="text/javascript">
  var config = {
    /**************
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"${app}/supplierkpi/tawSupplierkpiTemplates.do?method=getNodes",
    treeRootId:'<%=((SupplierkpiAttributes) ApplicationContextHolder.getInstance().getBean("supplierkpiAttributes")).getTreeRootId()%>',
	treeRootText:"${eoms:a2u('报表订制管理')}",
	pageFrameId:'formPanel-page',
	ctxMenu:[
		{id:'edtnode', text:'${eoms:a2u('订制报表')}',cls:'new-mi',url:'<c:url value="/supplierkpi/TawSuppKpiInstReportOrder.do?dictId=" />'},
		{id:'delnode', text:'${eoms:a2u('查看已订制报表')}',cls:'new-mi',url:'<c:url value="/supplierkpi/TawSuppKpiInstReportOrder.do?method=list&dictId=" />'}
	],	
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
  Ext.onReady(AppFrameTree.init, AppFrameTree);
  </script>
  <style type="text/css">
  	body{background-image:none;}
  </style>
<div id="headerPanel" class="x-layout-inactive-content">
	<h1>${eoms:a2u('评估报表模型定制')}</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<dt>${eoms:a2u('定制报表')}</dt>
		<dd>${eoms:a2u('step1.展开树图，在需要定制报表的专业节点上点击右键，选择"定制报表"')}</dd>
		<dd>${eoms:a2u('step2.填写选择各项数据，并选择需要统计的指标，点击"统计定制"按钮完成定制')}</dd>
		<dt>${eoms:a2u('查看已定制报表')}</dt>
		<dd>${eoms:a2u('step1.展开树图，右键点击要查看的专业节点，选择"查看已定制报表"，右侧页面列出已定制报表的列表')}</dd>
		<dd>${eoms:a2u('step2.点击列表中的报表名称以查看该报表的详细信息')}</dd>
	</dl>
</div>
<div id="treePanel" class="x-layout-inactive-content">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="formPanel-page" name="formPanel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
