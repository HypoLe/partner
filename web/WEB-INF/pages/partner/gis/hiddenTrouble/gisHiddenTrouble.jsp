<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
	
<html>
<head>
  <title><fmt:message key="webapp.name" /></title>
  <%@ include file="/common/meta.jsp"%>
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/local/zh_CN.js"></script>  
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/base/eoms.js"></script>
  <script type="text/javascript">eoms.appPath = "${app}";</script>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/theme.css" />
  
  
  <!-- EXT LIBS verson 1.1 -->
  <script type="text/javascript" src="${app}/scripts/ext/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext/ext-all.js"></script>
  <script type="text/javascript" src="${app}/scripts/adapter/ext-ext.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext/source/locale/ext-lang-zh_CN.js"></script>
  <link rel="stylesheet" type="text/css" href="${app}/scripts/ext/resources/css/ext-all.css" />
  <c:if test="${theme ne 'default'}"><link rel="stylesheet" type="text/css" href="${app}/scripts/ext/resources/css/xtheme-gray.css" /></c:if>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/ext-adpter.css" />
  <!-- EXT LIBS END -->
  
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
  
    <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
    
    <!-- JQUERY LIBS  -->
    <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
	<!-- JQUERY LIBS END -->
	
	
    <style type="text/css">
      td.label {
		    background-color: #EDF5FD;
		    vertical-align: top;
		    width: 23%;
		}
		.hide {display:block}
    </style>

    <script type="text/javascript">
			var paramConfig = {
			 	icon1:'${app}/deviceManagement/gis/image/map/cp_1.gif',
			 	icon2:'${app}/deviceManagement/gis/image/map/hiddenTouble_1.gif',
			 	icon3:'${app}/deviceManagement/gis/image/map/hiddenTrouble_2.gif',
				icon4:"${app}/deviceManagement/gis/image/map/flag.png",
				actionUrl:"${app}/partner/gis/gisReq.do",
				hiddenTroubleUrl:"${app}/hiddenTrouble/hiddenTrouble.do",
				htJSONObj:""
			}; // end of config
    </script>

    <script type="text/javascript">dojoConfig = { parseOnLoad:true }</script>
    
    <%@ include file="../config/arcgisConfig.jsp"%>
       
	<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
		
    
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/common.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/hiddenTrouble.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/contextMenu.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/measurement.js"></script>
	
	
    <script type="text/javascript">
		var jq=jQuery.noConflict();
		Ext.onReady(function(){
			function initMyMap() {
				//设置设备隐患右键菜单
				ContextMenu.MENU_TYPE = ContextMenu.MENU_TYPE_2;
				MapConfig.init();
				//调整mapdiv的大小 
				fireMapResize(null);
			}
			dojo.addOnLoad(initMyMap);
			parent.Ext.get("gisPanel").unmask();
			
			
			var leftLayout = new Ext.BorderLayout('mapContainer', {
		                    east: {
		                        split:true,
		                        initialSize: 100,
		                        titlebar: true,
		                        collapsible: true,
		                        collapsed:true,
		                        minSize: 0,
		                        maxSize: 220,
				           		margins:{left:0,right:5,bottom:5,top:5}
		                    },
		                    center: {
		                    	titlebar:false,
		                        autoScroll:false,
		                        tabPosition:'top',
				           		margins:{left:5,right:0,bottom:5,top:5}
		                    }
		                });
			leftLayout.getRegion('east').addListener("resized",mapResize);
			leftLayout.getRegion('east').addListener("collapsed",fireMapResize);
			leftLayout.getRegion('east').addListener("expanded",fireMapResize);
			
			function fireMapResize(region) {
				leftLayout.getRegion('east').fireEvent("resized",leftLayout.getRegion('east'),-1);
			}
			
			function mapResize(region,newSize) {
				var cR = leftLayout.getRegion('center');
				var height = Ext.get("mapCenter").getHeight()-Ext.get("navToolbar").getHeight();
				var width = cR.getEl().getWidth();
				Ext.get("map").setSize(width,height);
				map.reposition();
				map.resize();
			}
			
			Ext.EventManager.onWindowResize(function() {
				leftLayout.getRegion('east').fireEvent("resized",leftLayout.getRegion('east'),-1);
			});
			
			leftLayout.beginUpdate();
				 leftLayout.add('east', new Ext.ContentPanel('mapLeft',{title:"工具箱"}));
				 leftLayout.add('center', new Ext.ContentPanel('mapCenter',{
	            	fitToFrame:true, autoScroll:false
	            }));
			leftLayout.endUpdate(); 
			
			waitDataFrameReady();
		});
		
		function waitDataFrameReady() { 
	        var ready = parent.frames["left-panel-page"].document.readyState;
	        if(ready === "complete" && typeof (parent.frames["left-panel-page"].htJSONObj) != "undefined") {
	        	setAllHiddenTroubleGraphics(parent.frames["left-panel-page"].htJSONObj);
	        } else {
	        	setTimeout("waitDataFrameReady();",1000);
	        } 
		} 
    </script>
    
  <body class="claro">
    <div id="mapContainer" style="position: relative; margin-top: -10px;">
   	<div id="mapCenter" class="x-layout-inactive-content">
	    <div id="navToolbar" data-dojo-type="dijit.Toolbar" style="width:100%;height:100%">
	      <div data-dojo-type="dijit.form.Button" id="zoomin"  data-dojo-props="iconClass:'zoominIcon', onClick:function(){navToolbar.activate(esri.toolbars.Navigation.ZOOM_IN);}">区域放大</div>
	      <div data-dojo-type="dijit.form.Button" id="zoomout" data-dojo-props="iconClass:'zoomoutIcon', onClick:function(){navToolbar.activate(esri.toolbars.Navigation.ZOOM_OUT);}">区域缩小</div>
	      <div data-dojo-type="dijit.form.Button" id="zoomfullext" data-dojo-props="iconClass:'zoomfullextIcon', onClick:function(){navToolbar.zoomToFullExtent();}">全视图</div>
	      <div data-dojo-type="dijit.form.Button" id="zoomprev" data-dojo-props="iconClass:'zoomprevIcon', onClick:function(){navToolbar.zoomToPrevExtent();}">前一个区域</div>
	      <div data-dojo-type="dijit.form.Button" id="zoomnext" data-dojo-props="iconClass:'zoomnextIcon', onClick:function(){navToolbar.zoomToNextExtent();}">下一个区域</div>
	      <div data-dojo-type="dijit.form.Button" id="pan" data-dojo-props="iconClass:'panIcon', onClick:function(){navToolbar.activate(esri.toolbars.Navigation.PAN);}">拖动</div>
	      <div data-dojo-type="dijit.form.Button" id="deactivate" data-dojo-props="iconClass:'deactivateIcon' ,onClick:function(){navToolbar.deactivate();}">停用</div>
	    </div>
	    <div id="map" style="width:100%;height:100%; border:1px solid #000; border-bottom:none;" ></div>
    </div>
    <div id="mapLeft" class="x-layout-inactive-content">
		<div id="measurementDiv"></div> 
	</div>
   </div>

<%@ include file="/common/footer_eoms.jsp"%>