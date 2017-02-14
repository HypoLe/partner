<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/gis/js/indexBorderLayout.js"></script>
<script type="text/javascript">
var config = {
    /**************
 	* Tree Configs
 	**************/
 	icon1:'${app}/deviceManagement/gis/image/page/list-items.gif',
	leftBodyPage:'leftBodyPage',
	gisPage:'gisPage',
	actionUrl:"${app}/partner/gis/gisReq.do",
	actionGridUrl:"${app}/partner/gis/gisGridReq.do",
	actionSiteUrl:"${app}/partner/gis/gisSiteReq.do",
	actionHiddenTroubleUrl:"${app}/partner/gis/gisHiddenTroubleReq.do",
	actionHistoryUrl:"${app}/partner/gis/gisHistoryReq.do",
	actionLeakUrl:"${app}/partner/gis/gisLeakReq.do", 
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
 // Ext.onReady(AppFrameTree.init, AppFrameTree);
</script>



<div id="headerPanel" class="x-layout-inactive-content">
	<h1>黑龙江GIS功能管理</h1>
</div>



<div id="left-Panel" class="x-layout-inactive-content">
	
	<div id="left-panel-up" >
		<div id="left-panel-toolbar"></div>
	</div>
	<div id="left-panel-body">
		<iframe id="leftBodyPage" name="left-panel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
	</div>
</div>

<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<%@include file="/WEB-INF/pages/partner/gis/help.jsp" %>
</div>

<div id="gisPanel" class="x-layout-inactive-content">
	<iframe id="gisPage" name="gis-frame" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

<%@ include file="/common/footer_eoms.jsp"%>