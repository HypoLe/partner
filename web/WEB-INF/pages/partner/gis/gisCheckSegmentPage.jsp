<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

     <style type="text/css"> 
      td.label {
		    background-color: #EDF5FD;
		    vertical-align: top;
		    width: 20%;
		}
		.hide {display:block}
    </style>

    <script type="text/javascript">
			var paramConfig = {
			 	icon1:'${app}/deviceManagement/gis/image/map/cp_1.gif',
			 	icon2:'${app}/deviceManagement/gis/image/map/hiddenTouble_1.gif',
			 	icon3:'${app}/deviceManagement/gis/image/map/hiddenTouble_2.gif',
				actionUrl:"${app}/partner/gis/gisReq.do",
				checkPointUrl:"${app}/checkpoint/checkpoint.do",
				checkSegmentUrl:"${app}/checkSegment/checkSegmentAction.do",
				hiddenTroubleUrl:"${app}/hiddenTrouble/hiddenTrouble.do",
				cpJSONObj:"",
				csJSONObj:"",
				htJSONObj:"",
			}; // end of config
    </script>

	<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
		

    <script type="text/javascript">dojoConfig = { parseOnLoad:true }</script>
    
     <%@ include file="config/arcgisConfig.jsp"%>
    
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/common.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/checkPoint.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/checkSegment.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/contextMenu.js"></script>
	<script type="text/javascript" src="${app}/deviceManagement/gis/js/measurement.js"></script>
	
	
    <script type="text/javascript">
		var jq=jQuery.noConflict();
		Ext.onReady(function(){
			function initMyMap() {
				//设置巡检段右键菜单
				ContextMenu.MENU_TYPE = ContextMenu.MENU_TYPE_1;
				MapConfig.init();
			}
			dojo.addOnLoad(initMyMap);
			parent.Ext.get("gisPanel").unmask();
			
			var leftLayout = new Ext.BorderLayout('mapContainer', {
		                    east: {
		                        split:true,
		                        initialSize: 100,
		                        titlebar: true,
		                        collapsible: true,
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
		});
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