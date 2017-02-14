//global varibale
var map; 
var editToolbar; 
var ctxMenuForGraphics, ctxMenuForMap,ctxMenuForCheckSegmentGraphics; 
var selected, currentLocation; 



var ContextMenu = function() {
	var mapX,mapY;
		
	return {
		  config : "",
		  MENU_TYPE : "",
	      createToolbarAndContextMenu : function(map) { 
	        // Create and setup editing tools 
	        editToolbar = new esri.toolbars.Edit(map); 
	 
	        dojo.connect(map,"onClick", function(evt){ 
	          editToolbar.deactivate(); 
	        }); 
	        
	        switch(ContextMenu.MENU_TYPE) {
	        	case ContextMenu.MENU_TYPE_0:
			        ContextMenu.createMapMenu(); 
			        ContextMenu.createGraphicsMenu();
			        break;
			    case ContextMenu.MENU_TYPE_1:
			        ContextMenu.createCheckSegmentGraphicsMenu();
			        break;
			    case ContextMenu.MENU_TYPE_2:
			        ContextMenu.createCheckSegmentGraphicsMenu();
			        break;
	        }
	        
	        
	        //dojo.connect(map.graphics,"onGraphicAdd",ContextMenu.mapOnGraphicAdd);
	         
	        //resize the map when the browser resizes 
	        dojo.connect(dijit.byId('map'), 'resize', map,map.resize); 
	      },
	 
	      createMapMenu : function() { 
	        // Creates right-click context menu for map 
	        ctxMenuForMap = new dijit.Menu({ 
	          onOpen: function(box) { 
	            // Lets calculate the map coordinates where user right clicked. 
	            // We'll use this to create the graphic when the user clicks 
	            // on the menu item to "Add Point" 
	            currentLocation = ContextMenu.getMapPointFromMenuPosition(box);           
	            editToolbar.deactivate(); 
	          } 
	        }); 
	 
	        ctxMenuForMap.addChild(new dijit.MenuItem({  
	          label: "添加巡检点", 
	          onClick: function(evt) { 
		  		var picSymbol = new esri.symbol.PictureMarkerSymbol(paramConfig.icon1, 24, 24);
	          	var cpPoint = new esri.geometry.Point(currentLocation.x, currentLocation.y, new esri.SpatialReference({ wkid: 4326 }));
		  		var gra = new esri.Graphic(cpPoint, picSymbol);
		  		
		  		var result = ContextMenu.mapOnGraphicAdd(gra);
		  		
		  		if(result == true) {
	            	map.graphics.add(gra); 
	            }
	          } 
	        })); 
	   
	 
	        ctxMenuForMap.startup(); 
	        ctxMenuForMap.bindDomNode(map.container); 
	      }, 
	 
	      createGraphicsMenu : function() { 
	        // Creates right-click context menu for GRAPHICS 
	 
	        ctxMenuForGraphics = new dijit.Menu({}); 
	        ctxMenuForGraphics.addChild(new dijit.MenuItem({  
	          label: "Edit", 
	          onClick: function() { 
	            if(selected.geometry.type !== "point"){ 
	              editToolbar.activate(esri.toolbars.Edit.EDIT_VERTICES, selected); 
	            } 
	            else{ 
	              alert("Not implemented"); 
	            } 
	          }  
	        })); 
	 
	 
	        ctxMenuForGraphics.addChild(new dijit.MenuItem({  
	          label: "Move", 
	          onClick: function() { 
	            editToolbar.activate(esri.toolbars.Edit.MOVE, selected); 
	          }  
	        })); 
	 
	        ctxMenuForGraphics.addChild(new dijit.MenuItem({  
	          label: "Rotate/Scale", 
	          onClick: function() { 
	          if(selected.geometry.type !== "point"){ 
	              editToolbar.activate(esri.toolbars.Edit.ROTATE | esri.toolbars.Edit.SCALE, selected); 
	            } 
	            else{ 
	              alert("Not implemented"); 
	            } 
	          } 
	        })); 
	 
	        ctxMenuForGraphics.addChild(new dijit.MenuItem({  
	          label: "Style", 
	          onClick: function() { 
	            alert("Not implemented"); 
	          } 
	        })); 
	       
	        ctxMenuForGraphics.addChild(new dijit.MenuSeparator()); 
	        ctxMenuForGraphics.addChild(new dijit.MenuItem({  
	          label: "Delete", 
	          onClick: function() { 
	            map.graphics.remove(selected); 
	          } 
	        })); 
	 
	        ctxMenuForGraphics.startup(); 
	 
	        dojo.connect(map.graphics, "onMouseOver", function(evt) { 
	          // We'll use this "selected" graphic to enable editing tools 
	          // on this graphic when the user click on one of the tools 
	          // listed in the menu. 
	          selected = evt.graphic; 
	 
	          // Let's bind to the graphic underneath the mouse cursor            
	          ctxMenuForGraphics.bindDomNode(evt.graphic.getDojoShape().getNode()); 
	        }); 
	 
	 
	        dojo.connect(map.graphics, "onMouseOut", function(evt) { 
	          ctxMenuForGraphics.unBindDomNode(evt.graphic.getDojoShape().getNode()); 
	        }); 
	      }, 
	 	  //创建巡检段右键菜单
	 	  createCheckSegmentGraphicsMenu : function() {
	 	  	// Creates right-click context menu for GRAPHICS 
	 
	        ctxMenuForCheckSegmentGraphics = new dijit.Menu({}); 
	        ctxMenuForCheckSegmentGraphics.addChild(new dijit.MenuItem({  
	          label: "显示巡检点", 
	          onClick: function() { 
	            if(selected.geometry.type !== "point"){
	            	setAllCheckPointGraphics(selected.attributes[1].checkPoints);
	            	addAllCheckPointGraphics();
	            } 
	            else{ 
	              alert("当前操作无效！"); 
	            } 
	          }  
	        })); 
	 
	 
	        ctxMenuForCheckSegmentGraphics.addChild(new dijit.MenuItem({  
	          label: "清除巡检点", 
	          onClick: function() { 
	          	if(selected.geometry.type !== "point"){
	            	cleanAllCheckPointGraphics();
	            } 
	            else{ 
	              alert("当前操作无效！"); 
	            } 
	          }  
	        })); 
	        
	        ctxMenuForCheckSegmentGraphics.startup(); 
	 
	        dojo.connect(map.graphics, "onMouseOver", function(evt) { 
	          // We'll use this "selected" graphic to enable editing tools 
	          // on this graphic when the user click on one of the tools 
	          // listed in the menu. 
	          selected = evt.graphic; 
	 		  
	 		  var info = selected.attributes || "empty";
	          // Let's bind to the graphic underneath the mouse cursor  
	          
	          if(info != "empty" && info[0] == "checkSegment") {          
	      	    ctxMenuForCheckSegmentGraphics.bindDomNode(evt.graphic.getDojoShape().getNode()); 
	          }
	        }); 
	 
	 
	        dojo.connect(map.graphics, "onMouseOut", function(evt) { 
	          ctxMenuForCheckSegmentGraphics.unBindDomNode(evt.graphic.getDojoShape().getNode()); 
	        });
	        
	 	  },
	      /***************** 
	       * Helper Methods 
	       *****************/       
	 
	      getMapPointFromMenuPosition : function(box) { 
	        var x = box.x, y = box.y; 
	 
	        switch(box.corner) { 
	          case "TR": 
	            x += box.w; 
	            break; 
	          case "BL": 
	            y += box.h; 
	            break; 
	          case "BR": 
	            x += box.w; 
	            y += box.h; 
	            break; 
	        } 
	 
	        var screenPoint = new esri.geometry.Point(x - map.position.x, y - map.position.y); 
	        return map.toMap(screenPoint); 
	      },
      
     	 mapOnGraphicAdd : function(graphic) {
      		var geometry = graphic.geometry;
      		
      		var cpParams = {method:"addFromMap"};
      		cpParams.longitude = geometry.x;
      		cpParams.latitude = geometry.y;
      		
      		Ext.get("map").mask("正在添加巡检点，请稍等......");
      		var result = CheckPoint.addCheckPoint(cpParams);
      		
      		return result;
      	},
      	
      	changeMenuType : function(type) {
      		ContextMenu.MENU_TYPE = type;
      	},
      	
      	MENU_TYPE_0 : {identifier:"MENU_TYPE_0",descripteion:"巡检点右键菜单"},
      	MENU_TYPE_1 : {identifier:"MENU_TYPE_1",descripteion:"巡检段右键菜单"},
      	MENU_TYPE_2 : {identifier:"MENU_TYPE_2",descripteion:"设备隐患右键菜单"}
      }
}();
