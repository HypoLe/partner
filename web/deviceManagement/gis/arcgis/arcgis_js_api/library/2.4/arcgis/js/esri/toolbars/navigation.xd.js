/*
 COPYRIGHT 2009 ESRI

 TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
 Unpublished material - all rights reserved under the
 Copyright Laws of the United States and applicable international
 laws, treaties, and conventions.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts and Legal Services Department
 380 New York Street
 Redlands, California, 92373
 USA

 email: contracts@esri.com
 */

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.toolbars.navigation"],["require","esri.toolbars._toolbar"],["require","esri.geometry"],["require","esri.symbol"],["require","esri.utils"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.toolbars.navigation"]){_4._hasResource["esri.toolbars.navigation"]=true;_4.provide("esri.toolbars.navigation");_4.require("esri.toolbars._toolbar");_4.require("esri.geometry");_4.require("esri.symbol");_4.require("esri.utils");_4.declare("esri.toolbars.Navigation",esri.toolbars._Toolbar,{constructor:function(_7){this.zoomSymbol=new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new _4.Color([255,0,0]),2),new _4.Color([0,0,0,0.25]));_4.connect(_7,"onUnload",this,"_cleanUp");this._normalizeRect=_4.hitch(this,this._normalizeRect);this._onMouseDownHandler=_4.hitch(this,this._onMouseDownHandler);this._onMouseUpHandler=_4.hitch(this,this._onMouseUpHandler);this._onMouseDragHandler=_4.hitch(this,this._onMouseDragHandler);this._onExtentChangeHandler_connect=_4.connect(_7,"onExtentChange",this,"_extentChangeHandler");this._extents=[];if(_7.extent){this._extents.push(_7.extent.toJson());}},_navType:null,_start:null,_graphic:null,_prevExtent:false,_nextExtent:false,_extentCursor:-1,_cleanUp:function(_8){this._extents=null;_4.disconnect(this._onExtentChangeHandler_connect);},activate:function(_9){var _a=this.map;if(!this._graphic){this._deactivateMapTools(true,false,false,true);this._graphic=new esri.Graphic(null,this.zoomSymbol);}switch(_9){case esri.toolbars.Navigation.ZOOM_IN:case esri.toolbars.Navigation.ZOOM_OUT:this._deactivate();this._onMouseDownHandler_connect=_4.connect(_a,"onMouseDown",this,"_onMouseDownHandler");this._onMouseDragHandler_connect=_4.connect(_a,"onMouseDrag",this,"_onMouseDragHandler");this._onMouseUpHandler_connect=_4.connect(_a,"onMouseUp",this,"_onMouseUpHandler");this._navType=_9;break;case esri.toolbars.Navigation.PAN:this._deactivate();_a.enablePan();this._navType=_9;break;}},_extentChangeHandler:function(_b){if(!this._prevExtent&&!this._nextExtent){this._extents=this._extents.splice(0,this._extentCursor+1);this._extents.push(_4.toJson(_b.toJson()));this._extentCursor=this._extents.length-1;}this._prevExtent=this._nextExtent=false;this.onExtentHistoryChange();},_normalizeCursor:function(){if(this._extentCursor<0){this._extentCursor=0;}else{if(this._extentCursor>this._extents.length){this._extentCursor=this._extents.length;}}},_deactivate:function(){var _c=this._navType;if(_c===esri.toolbars.Navigation.PAN){this.map.disablePan();}else{if(_c===esri.toolbars.Navigation.ZOOM_IN||_c===esri.toolbars.Navigation.ZOOM_OUT){_4.disconnect(this._onMouseDownHandler_connect);_4.disconnect(this._onMouseDragHandler_connect);_4.disconnect(this._onMouseUpHandler_connect);}}},_normalizeRect:function(_d,_e,_f){var sx=_d.x,sy=_d.y,ex=_e.x,ey=_e.y,_10=Math.abs(sx-ex),_11=Math.abs(sy-ey);return {x:Math.min(sx,ex),y:Math.max(sy,ey),width:_10,height:_11,spatialReference:_f};},_onMouseDownHandler:function(evt){this._start=evt.mapPoint;},_onMouseDragHandler:function(evt){var _12=this._graphic,_13=this.map.graphics;_13.remove(_12,true);_12.setGeometry(new esri.geometry.Rect(this._normalizeRect(this._start,evt.mapPoint,this.map.spatialReference)));_13.add(_12,true);},_onMouseUpHandler:function(evt){var map=this.map,_14=this._normalizeRect(this._start,evt.mapPoint,map.spatialReference);map.graphics.remove(this._graphic,true);if(_14.width===0&&_14.height===0){return;}if(this._navType===esri.toolbars.Navigation.ZOOM_IN){map.setExtent(esri.geometry._rectToExtent(new esri.geometry.Rect(_14)));}else{var tl=map.toScreen(_14),tr=map.toScreen({x:_14.x+_14.width,y:_14.y,spatialReference:map.spatialReference}),_15=map.extent.getWidth(),_16=(_15*map.width)/Math.abs(tr.x-tl.x),_17=(_16-_15)/2,ext=map.extent;map.setExtent(new esri.geometry.Extent(ext.xmin-_17,ext.ymin-_17,ext.xmax+_17,ext.ymax+_17,ext.spatialReference));}},deactivate:function(){this._deactivate();if(this._graphic){this.map.graphics.remove(this._graphic,true);}this._navType=this._start=this._graphic=null;this._activateMapTools(true,false,false,true);},setZoomSymbol:function(_18){this.zoomSymbol=_18;},isFirstExtent:function(){return this._extentCursor===0;},isLastExtent:function(){return this._extentCursor===(this._extents.length-1);},zoomToFullExtent:function(){var map=this.map;map.setExtent(map.getLayer(map.layerIds[0]).initialExtent);},zoomToPrevExtent:function(){if(this.isFirstExtent()){return;}this._extentCursor--;this._normalizeCursor();this._prevExtent=true;this.map.setExtent(new esri.geometry.Extent(_4.fromJson(this._extents[this._extentCursor])));},zoomToNextExtent:function(){if(this.isLastExtent()){return;}this._extentCursor++;this._normalizeCursor();this._nextExtent=true;this.map.setExtent(new esri.geometry.Extent(_4.fromJson(this._extents[this._extentCursor])));},onExtentHistoryChange:function(){}});_4.mixin(esri.toolbars.Navigation,{ZOOM_IN:"zoomin",ZOOM_OUT:"zoomout",PAN:"pan"});}}};});