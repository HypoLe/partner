dojo.require("esri.map"); 
dojo.require("esri.toolbars.navigation"); 
dojo.require("dijit.form.Button"); 
dojo.require("dijit.Toolbar"); 

dojo.require("esri.layers.agstiled");
dojo.require("esri.toolbars.draw");

dojo.require("esri.toolbars.draw"); 
dojo.require("esri.toolbars.edit"); 
dojo.require("dijit.Menu"); 
dojo.require("dijit.layout.BorderContainer"); 
dojo.require("dijit.layout.ContentPane");

dojo.require("esri.dijit.Measurement"); 
dojo.require("esri.dijit.Scalebar"); 

//glabal variable
var map,navToolbar,tb;

//地图配置
var MapConfig = function() {
	//private
 	
	return {
			config:"",
			init:function() {
				MapConfig.gisInit();
			},
			
			gisInit : function() {
				var initExtent = new esri.geometry.Extent({"xmin":11048185.44251304,"ymin":3318418.4559335746,"xmax":11936077.963073406,"ymax":3876103.01430207,"spatialReference":{"wkid":102100}});
			   	map = new esri.Map("map",{extent:initExtent});
			   	var layer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
			    map.addLayer(layer);
			    
			    if (map.loaded) {
                    MapConfig.mapOnLoad(map);//对图层的操作
                }
                else{
				   	dojo.connect(map, "onLoad", MapConfig.mapOnLoad);
                }
		 	},
		 	
			mapOnLoad : function(map) {
				dojo.connect(map,'onZoomEnd',MapConfig.mapOnZoomEnd);
				//resize the map when the browser resizes
          		dojo.connect(dijit.byId('map'), 'resize', map,map.resize);
				//创建测试画图工具
				//GraphicToolbar.initToolbar(map);
				//创建导航工具
				MapConfig.initNavToolbar(map);
				//创建右键菜单工具
				ContextMenu.createToolbarAndContextMenu(map);
				//创建测量工具
				Measurement.initMeasurementToolbar(map);
				//鼠标移到图形上时更改鼠标手型
				dojo.connect(map.graphics,"onMouseOver",MapConfig.graphicsOnMouseOver);
				dojo.connect(map.graphics,"onClick",GraphicEventHandler.mapGraphicOnClick);
				dojo.connect(map.graphics,"onMouseOut",GraphicEventHandler.mapGraphicOnMouseOut);
				//定位到成都
				//var location = new esri.geometry.Point(126.63,45.75, new esri.SpatialReference({ wkid: 4326 }));
				var location = new esri.geometry.Point(104.06,30.67, new esri.SpatialReference({ wkid: 4326 }));
	        	var sr = new esri.SpatialReference({wkid:4326});
	        	var geom = esri.geometry.geographicToWebMercator(location);
	        	geom.setSpatialReference(sr);
				map.centerAndZoom(geom,8);
			},
			
			mapOnZoomEnd : function(extent, zoomFactor, anchor, level) {
				//alert(level);
			},
			
			graphicsOnMouseOver : function(event) {
				event.target.style.cursor = "pointer";
			},
			
 			initNavToolbar : function(map) {
 				esriConfig.defaults.map.sliderLabel = null; 
     			navToolbar = new esri.toolbars.Navigation(map); 
     			dojo.connect(navToolbar, "onExtentHistoryChange", MapConfig.extentHistoryChangeHandler); 
 			},
 			
 			extentHistoryChangeHandler : function() { 
		     	dijit.byId("zoomprev").disabled = navToolbar.isFirstExtent(); 
		     	dijit.byId("zoomnext").disabled = navToolbar.isLastExtent(); 
   			}
	}
}();

var GraphicEventHandler = function() {
	return {
		mapGraphicOnClick : function(evt) {
			var gra = evt.graphic;
			var info = gra.attributes || "empty";
	       	var p = evt.mapPoint;
	       	
	       	var type = info[0];
	       	switch(type) {
	       		case "checkPoint":
			       	map.infoWindow.setContent(gra.getContent());
			       	map.infoWindow.resize(460,130);	
	       			break;
	       		case "checkSegment":
			       	map.infoWindow.setContent(gra.getContent());
			       	map.infoWindow.resize(460,140);	
	       			break;
	       		case "hiddenTrouble":
	       			if(info[1].processStatus == "未处理") {
				       	map.infoWindow.resize(460,220);	
	       			} else {
				       	map.infoWindow.resize(460,300);	
	       			}
			       	map.infoWindow.setContent(gra.getContent());
	       			break;
	       		case "grid":
	       			map.infoWindow.setContent(gra.getContent());
			       	map.infoWindow.resize(460,140);	
	       			break;
	       		case "site":
	       			map.infoWindow.setContent(gra.getContent());
			       	map.infoWindow.resize(460,140);	
	       			break;
	       	}
	     	if(info != "empty") map.infoWindow.show(p,esri.dijit.InfoWindow.ANCHOR_LOWERRIGHT);
		},
		mapGraphicOnMouseOut : function(evt) {
			
		}
	}
}();

//画图工具，测试用
var GraphicToolbar = function() {
	return {
		  	initToolbar : function(map) {
		        tb = new esri.toolbars.Draw(map);
		        dojo.connect(tb, "onDrawEnd", GraphicToolbar.addGraphic);
		        
		        dojo.connect(map, "onMouseMove", GraphicToolbar.myMouseMoveHandler);
				dojo.connect(map, "onClick",GraphicToolbar.myClickHandler);
	     	},
	
		  	myMouseMoveHandler : function (e) {
				//var mp = esri.geometry.webMercatorToGeographic(e.mapPoint); 
		        //display mouse coordinates 
		        //dojo.byId("showData").innerHTML = dojo.byId("showData").innerHTM + ("<br/>mp.x="+e.mapPoint.x + ", mp.y=" + e.mapPoint.y); 
	      	},
	      
	      	myClickHandler : function (e) {
		     	 map.infoWindow.hide();
		     	 /*var mp = esri.geometry.webMercatorToGeographic(e.mapPoint); 
		     	 var mpp = esri.geometry.geographicToWebMercator(mp);
		         dojo.byId("clickData").innerHTML = "mapPoint:<br/>x= " + e.mapPoint.x + ", <br/>y=" + e.mapPoint.y+"<br/>"+
		         									"webMercatorToGeographic:<br/>x="+mp.x+", <br/>y="+mp.y+"<br/>"+
		         									"geographicToWebMercator:<br/>x="+mpp.x+", <br/>y="+mpp.y;*/
	      	},	     
	
	      	addGraphic : function (geometry) {
		        var symbol = dojo.byId("symbol").value;
		        if (symbol) {
		            symbol = eval(symbol);
		        }
		        else {
		          var type = geometry.type;
		          if (type === "point" || type === "multipoint") {
		            symbol = tb.markerSymbol;
		          }
		          else if (type === "line" || type === "polyline") {
		            symbol = tb.lineSymbol;
		          }
		          else {
		            symbol = tb.fillSymbol;
		          }
		        }
		        map.graphics.add(new esri.Graphic(geometry, symbol));
	      	}
	  	}
}();


//画图工具，测试用
var GraphicToolbar = function() {
	return {
		  	initToolbar : function(map) {
		        tb = new esri.toolbars.Draw(map);
		        dojo.connect(tb, "onDrawEnd", GraphicToolbar.addGraphic);
		        
		        dojo.connect(map, "onMouseMove", GraphicToolbar.myMouseMoveHandler);
				dojo.connect(map, "onClick",GraphicToolbar.myClickHandler);
	     	},
	
		  	myMouseMoveHandler : function (e) {
				//var mp = esri.geometry.webMercatorToGeographic(e.mapPoint); 
		        //display mouse coordinates 
		       // dojo.byId("showData").innerHTML = dojo.byId("showData").innerHTM + ("<br/>mp.x="+e.mapPoint.x + ", mp.y=" + e.mapPoint.y); 
	      	},
	      
	      	myClickHandler : function (e) {
		     	 map.infoWindow.hide();
		       // dojo.byId("clickData").innerHTML = "The map coordinate at this point is " + e.mapPoint.x + ", " + e.mapPoint.y;
	      	},	     
	
	      	addGraphic : function (geometry) {
		        var symbol = dojo.byId("symbol").value;
		        if (symbol) {
		            symbol = eval(symbol);
		        }
		        else {
		          var type = geometry.type;
		          if (type === "point" || type === "multipoint") {
		            symbol = tb.markerSymbol;
		          }
		          else if (type === "line" || type === "polyline") {
		            symbol = tb.lineSymbol;
		          }
		          else {
		            symbol = tb.fillSymbol;
		          }
		        }
		        map.graphics.add(new esri.Graphic(geometry, symbol));
	      	}
	  	}
}();

//通用工具类
var MapUtils = function() {
	
	return {
		//反回所有点的平均点坐标
		averageCoordinate : function(graphics) {
			graphics = graphics || [];
			var longitudes=0,latitudes=0,avgLongitude=0,avgLatitude,avgCoordinate;
			if(graphics.length == 0) {
				//返回成都中心坐标
				var location = new esri.geometry.Point(104.06,30.67);
	        	var geom = esri.geometry.geographicToWebMercator(location);
				avgCoordinate = {longitude:geom.x,latitude:geom.y};
			} else {
				var length = 0;
				for(var i=0;i<graphics.length;i++) {
					if((graphics[i].geometry.x != "" || graphics[i].geometry.x != 0)&& (graphics[i].geometry.y != "" || graphics[i].geometry.y != 0)) {
						longitudes += graphics[i].geometry.x;
						latitudes  += graphics[i].geometry.y;
						length++;
					}
				}
				avgLongitude = longitudes/length;
				avgLatitude  = latitudes/length;
				avgCoordinate = {longitude:avgLongitude,latitude:avgLatitude};
			}
			var location = new esri.geometry.Point(avgCoordinate.longitude,avgCoordinate.latitude, new esri.SpatialReference({ wkid: 4326 }));
			return location;
		}
	}
	
}();