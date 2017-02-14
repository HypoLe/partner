<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="app" scope="page" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
    <meta http-equiv="X-UA-Compatible" content="IE=7,IE=9" /> 
    <title>ArcGis</title>
     <style>
      html, body { height: 100%; width: 100%; margin: 0; padding: 0; }
      #map{ margin: 0; padding: 0; }

      /* center the image in the popup */
      .esriViewPopup .gallery { margin: 0 auto !important; }
    </style>
     <link rel="stylesheet" href="${app}/arcGis/arcgis_js_v39_api/library/3.9/3.9compact/js/dojo/dijit/themes/tundra/tundra.css">
     <link rel="stylesheet" href="${app}/arcGis/arcgis_js_v39_api/library/3.9/3.9compact/js/esri/css/esri.css">
 
      <script>
      var dojoConfig = { 
        async: true
      };
      </script>
	  <script type="text/javascript" src="${app}/arcGis/arcgis_js_v39_api/library/3.9/3.9compact/init.js"></script>
      <script type="text/javascript">
		var app='${app}';
		var dataJson = "";
		var singalPoint = "";
		var imgpathPrefix ="http://localhost:8080/partner/";
	  </script>	
	  <script type="text/javascript"><!--
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
	    	parentDivHeightIe=parentDivHeightIe-65;
	    	parentDivWidthIe=parentDivWidthIe-525;
			document.getElementById("map").style.height=parentDivHeightIe+"px";
			document.getElementById("map").style.width=parentDivWidthIe+"px";
	    }
		/**
		*huhao
	    * load the map 
	    **/
		// loadMap();
		}
	
		var map;
      require([
        "dojo/parser", 
        "dojo/ready",
        "dojo/_base/array",
        "esri/Color",
        "dojo/dom-style",
        "dojo/query",

        "esri/map", 
        "esri/request",
        "esri/graphic",
        "esri/geometry/Extent",

        "esri/symbols/SimpleMarkerSymbol",
        "esri/symbols/SimpleFillSymbol",
        "esri/symbols/PictureMarkerSymbol",
        "esri/renderers/ClassBreaksRenderer",

        "esri/layers/GraphicsLayer",
        "esri/SpatialReference",
        "esri/dijit/PopupTemplate",
        "esri/geometry/Point",
        "esri/geometry/webMercatorUtils",

        "extras/ClusterLayer",
		"dojo/on",
		"esri/layers/ArcGISTiledMapServiceLayer",
        "dijit/layout/BorderContainer", 
        "dijit/layout/ContentPane", 
        "dojo/domReady!"
      ], function(
        parser, ready, arrayUtils, Color, domStyle, query,
        Map, esriRequest, Graphic, Extent,
        SimpleMarkerSymbol, SimpleFillSymbol, PictureMarkerSymbol, ClassBreaksRenderer,
        GraphicsLayer, SpatialReference, PopupTemplate, Point, webMercatorUtils,
        ClusterLayer,on,Tiled
      ) {
        ready(function() {
          parser.parse();
             
          var clusterLayer;
          var popupOptions = {
            "markerSymbol": new SimpleMarkerSymbol("circle", 20, null, new Color([0, 0, 0, 0.25])),
            "marginLeft": "20",
            "marginTop": "20"
          };
          map = new Map("map", {
              center: ["${centerlng}","${centerlat}"],
            zoom: 8
          });
          //1、该地址需要改成山东的底图 http://10.249.20.216:6080/arcgis/rest/services/sd/BaseMap/MapServer
         var tiled = new Tiled("http://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer");
          map.addLayer(tiled);
         // map.on("load", function() {
          on(query("#dataJsonStr"),"click", function() {
            map.infoWindow.hide();
            var extents = map.getLayer("clusterExtents");
            if ( extents ) {
              map.removeLayer(extents);
            }
            // hide the popup's ZoomTo link as it doesn't make sense for cluster features
            domStyle.set(query("a.action.zoomTo")[0], "display", "none");

            // get the latest 1000 photos from instagram/laguna beach
         /*  var photos = esriRequest({
              url: "${app}/partner/arcGis/arcGis.do?method=getData",
              handleAs: "json"
            });*/
                     //   photos.then(addClusters, error);
            addClusters();
          });

          function addClusters(resp) {
            var photoInfo = {};
            var wgs = new SpatialReference({
              "wkid": 4326
            });
            photoInfo.data = arrayUtils.map(dataJson, function(p) {
          //  console.log("dataJson:"+p.resName);
              var latlng = new  Point(parseFloat(p.longitude), parseFloat(p.latitude), wgs);
              var webMercator = webMercatorUtils.geographicToWebMercator(latlng);
         //  console.log("arcGisMap2.jsp142:"+imgpathPrefix+p.url);
              var link = imgpathPrefix+p.url;
              var attributes = {
                "Longitude": p.longitude,
                "Latitude": p.latitude, 
                "ResLevelId":p.type,             
                "Link": link
              };
              return {
                "x": webMercator.x,
                "y": webMercator.y,
                "attributes": attributes
              };
            });
         // popupTemplate to work with attributes specific to this dataset
            var popupTemplate = new PopupTemplate({
              "title": "",
              "fieldInfos": [ {
                "fieldName": "Longitude",
                "label": "经度",
                visible: true
              }, {
                "fieldName": "Latitude",
                "label": "纬度",
                visible: true
              }],
              "mediaInfos": [{
                "title": "查看照片",
                "caption": "",
                "type": "image",
                "value": {
                  "sourceURL": "{Link}",
                  "linkURL": "{Link}"
                }
              }]
            });
          //  console.log("photoInfo.data------------"+photoInfo.data);
            if(clusterLayer!=null){
                       map.infoWindow.hide();              
                       map.removeLayer(clusterLayer);         
            }
            // cluster layer that uses OpenLayers style clustering
            clusterLayer = new ClusterLayer({
              "data": photoInfo.data,
              "distance": 100,
              "id": "clusters",
              "labelColor": "#fff",
              "labelOffset": 10,
              "resolution": map.extent.getWidth() / map.width,
              "singleColor": "#888",
              "singleTemplate": popupTemplate
            });
            var defaultSym = new SimpleMarkerSymbol().setSize(4);
            var renderer = new ClassBreaksRenderer(defaultSym, "clusterCount");
			//2、该地址需要改成山东的
            var picBaseUrl = "https://static.arcgis.com/images/Symbols/Shapes/";
            
            var blue = new PictureMarkerSymbol(picBaseUrl + "BluePin1LargeB.png", 32, 32).setOffset(0, 15);
            var green = new PictureMarkerSymbol(picBaseUrl + "GreenPin1LargeB.png", 64, 64).setOffset(0, 15);
            var red = new PictureMarkerSymbol(picBaseUrl + "RedPin1LargeB.png", 72, 72).setOffset(0, 15);
            renderer.addBreak(0, 2, blue);
            renderer.addBreak(2, 200, green);
            renderer.addBreak(200, 1001, red);

            clusterLayer.setRenderer(renderer);
            
            
            map.addLayer(clusterLayer);

 		     //---------单点的点击展现效果
	          on(query("#singalPoint"),"click", function() {
	            	getSingalShow();
	          });
	          
	          function getSingalShow(){
	             clusterLayer.clearSingles(clusterLayer._singles);
	          	 var sin = [];           	          	 
	          	 sin.push(singalPoint);
	          	 clusterLayer._addSingles(sin);
	          };
            // close the info window when the map is clicked
            map.on("click", cleanUp);
            // close the info window when esc is pressed
            map.on("key-down", function(e) {
              if (e.keyCode === 27) {
                cleanUp();
              }
            });
          }

          function cleanUp() {
            map.infoWindow.hide();
            clusterLayer.clearSingles();
          }

          function error(err) {
            console.log("something failed: ", err);
          }

          // show cluster extents...
          // never called directly but useful from the console 
          window.showExtents = function() {
            var extents = map.getLayer("clusterExtents");
            if ( extents ) {
              map.removeLayer(extents);
            }
            extents = new GraphicsLayer({ id: "clusterExtents" });
            var sym = new SimpleFillSymbol().setColor(new Color([205, 193, 197, 0.5]));

            arrayUtils.forEach(clusterLayer._clusters, function(c, idx) {
              var e = c.attributes.extent;
              extents.add(new Graphic(new Extent(e[0], e[1], e[2], e[3], map.spatialReference), sym));
            }, this);
            map.addLayer(extents, 0);
          };
        });
      });
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
				document.getElementById("map").style.height=parentDivHeight-5;
				document.getElementById("map").style.width=parentDivWidth-5;
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
		--></script>
	</head>

	<body>
<input type="hidden" id="dataJsonStr" value="dd"/>
<input type="hidden" id="singalPoint" value="dd"/>
       <div data-dojo-type="dijit/layout/BorderContainer" 
         data-dojo-props="design:'headline',gutters:false" 
         style="width: 100%; height: 100%; margin: 0;">
      <div id="map" 
           data-dojo-type="dijit/layout/ContentPane" 
           data-dojo-props="region:'center'"> 
      </div>
    </div>
       
	</body>
</html>

