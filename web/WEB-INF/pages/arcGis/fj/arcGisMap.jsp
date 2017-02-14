<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="app" scope="page" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=9" /> 
    <title>ArcGis</title>
    <script type="text/javascript">
		var app='${app}';
		</script>
		<script type="text/javascript" src="${app}/arcGis/js/iconConfig.js"></script>
		 <script type="text/javascript">dojoConfig = { parseOnLoad:true }</script>
		<%@ include file="../arcgisConfig.jsp"%>
		
		<script type="text/javascript" language="Javascript"> 
		dojo.require("esri.map"); 
		</script>
		
		<script type="text/javascript" src="${app}/arcGis/js/fj/setArcGis.js"></script>
		
		<script type="text/javascript">
		
		function ff(){
	    /**
	    *huhao
	    * set the map div's height
	    **/
	    if(-[1,]){ 
		var parentDivHeight=parent.document.getElementById("center").style.height;
		document.getElementById("map").style.height=parentDivHeight;
	    }
	    else{
	    	var parentDivHeightIe=parent.document.getElementById("center").offsetHeight;
	    	var parentDivWidthIe=parent.document.getElementById("center").offsetWidth;
	    	var parentEastWidthIe=parent.document.getElementById("east").offsetWidth;
	    	var parentEastHeightIe=parent.document.getElementById("east").offsetHeight;
	    	parentDivHeightIe=parentDivHeightIe-60;
	    	parentDivWidthIe=parentDivWidthIe-parentEastWidthIe-30;
			document.getElementById("map").style.height=parentDivHeightIe+"px";
			document.getElementById("map").style.width=parentDivWidthIe+"px";
	    }
		/**
		*huhao
	    * load the map 
	    **/
		loadMap();
		}
		/**
		*huhao
	    * add a listener when window change 
	    **/
		function toResize(){
			 /**
			    *huhao
			    * set the map div's height
			    **/
	    if(map!=null){
			if(map.loaded){
			    if(-[1,]){ 
				var parentDivHeight=parent.document.getElementById("center").style.height;
				var parentDivWidth=parent.document.getElementById("center").style.width;
				document.getElementById("map").style.height=parentDivHeight;
				document.getElementById("map").style.width=parentDivWidth;
			    }
			    else{
			    	//var parentDivHeightIe=parent.document.getElementById("center").offsetHeight;
			    	var parentDivWidthIe=parent.document.getElementById("center").offsetWidth;
			    	//var parentScrollWidthIe=parent.document.getElementById("center").scrollWidth;
			    	//var parentEastWidthIe=parent.document.getElementById("east").offsetWidth;
					document.getElementById("map").style.width=parentDivWidthIe+"px";
			    }
				map.resize();
				}
			}
	    }
		</script>
	</head>

	<body onload="ff();" onResize="toResize();" >
						  <div id="map" class="claro" style="width:100%; height:100%;"></div> 

	</body>
	

	
</html>

