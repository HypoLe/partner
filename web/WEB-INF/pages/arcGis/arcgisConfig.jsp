<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%
	String path = request.getContextPath();
	String arcgisURL = request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
	var arcgisURL = '<%=arcgisURL%>';
</script>	
<style type="text/css">
	@import "http://<%=arcgisURL%>/deviceManagement/gis/arcgis/arcgis_js_api/library/2.4/arcgis/js/dojo/dijit/themes/claro/claro.css";
	.zoominIcon { background-image:url('${app}/deviceManagement/gis/image/map/nav_zoomin.png'); width:16px; height:16px; } 
	.zoomoutIcon { background-image:url('${app}/deviceManagement/gis/image/map/nav_zoomout.png'); width:16px; height:16px; } 
	.zoomfullextIcon { background-image:url('${app}/deviceManagement/gis/image/map/nav_fullextent.png'); width:16px; height:16px; } 
	.zoomprevIcon { background-image:url('${app}/deviceManagement/gis/image/map/nav_previous.png'); width:16px; height:16px; } 
	.zoomnextIcon { background-image:url('${app}/deviceManagement/gis/image/map/nav_next.png'); width:16px; height:16px; } 
	.panIcon { background-image:url('${app}/deviceManagement/gis/image/map/nav_pan.png'); width:16px; height:16px; } 
	.deactivateIcon { background-image:url('${app}/deviceManagement/gis/image/map/nav_decline.png'); width:16px; height:16px; } 
</style>
<link rel="stylesheet" type="text/css" href="http://<%=arcgisURL%>/deviceManagement/gis/arcgis/arcgis_js_api/library/2.4/arcgis/js/dojo/dijit/themes/tundra/tundra.css">
<script type="text/javascript" src="http://<%=arcgisURL%>/deviceManagement/gis/arcgis/arcgis_js_api/library/2.4/arcgis/js/dojo/dojo/dojo.xd.js"></script>
<script type="text/javascript" src="http://<%=arcgisURL%>/deviceManagement/gis/arcgis/arcgis_js_api/library/2.4/arcgis/js/esri/esri.js"></script>
<script type="text/javascript" src="http://<%=arcgisURL%>/deviceManagement/gis/arcgis/arcgis_js_api/library/2.4/arcgis/js/esri/jsapi.js"></script>
