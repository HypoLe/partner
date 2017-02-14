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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.map"],["require","esri._coremap"],["require","esri.touchcontainer"],["require","dijit.form.HorizontalSlider"],["require","dijit.form.VerticalSlider"],["require","dijit.form.HorizontalRule"],["require","dijit.form.VerticalRule"],["require","dijit.form.HorizontalRuleLabels"],["require","dijit.form.VerticalRuleLabels"],["require","esri.layers.agsdynamic"],["require","esri.layers.agstiled"],["require","esri.layers.agsimageservice"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.map"]){_4._hasResource["esri.map"]=true;_4.provide("esri.map");_4.require("esri._coremap");if(esri.isTouchEnabled){_4.require("esri.touchcontainer");}else{_4.declare("esri._MapContainer",esri._CoreMap,(function(){var dc=_4.connect,_7=_4.disconnect,dh=_4.hitch,_8=_4.mixin,_9=_4.isMozilla,_a=_4.stopEvent,_b=_4.fixEvent,_c=esri.geometry.Point;var _d=navigator.userAgent.indexOf("Macintosh")!==-1?1:3,_e=_4.isChrome<2?360:120,_f=1,_10=2,_11=300,_12=300;return {constructor:function(_13){_8(this,{_dragEnd:false,_clickDuration:_12,_mouseWheelEvent:{},_downCoords:null,_clickTimer:null,_mouseWheelTimer:null,_onKeyDown_connect:null,_onKeyUp_connect:null,_onMouseDragHandler_connect:null});var _14=this.__container,_15=this._connects;_15.push(dc(_14,"onselectstart",function(evt){_a(evt);return false;}),dc(_14,"ondragstart",function(evt){_a(evt);return false;}));if(_9){_4.style(_14,"MozUserSelect","none");}_15.push(dc(_14,"onmouseenter",this,"_onMouseEnterHandler"),dc(_14,"onmouseleave",this,"_onMouseLeaveHandler"),dc(_14,"onmousedown",this,"_onMouseDownHandler"),dc(_14,"onclick",this,"_onClickHandler"),dc(_14,"ondblclick",this,"_onDblClickHandler"),dc(_14,_4.isFF||_9?"DOMMouseScroll":"onmousewheel",this,"_onMouseWheelHandler"));this._onMouseMoveHandler_connect=dc(_14,"onmousemove",this,"_onMouseMoveHandler");this._onMouseUpHandler_connect=dc(_14,"onmouseup",this,"_onMouseUpHandler");this._processEvent=dh(this,this._processEvent);this._fireClickEvent=dh(this,this._fireClickEvent);this._fireMouseWheel=dh(this,this._fireMouseWheel);},_cleanUp:function(){_7(this._onMouseMoveHandler_connect);_7(this._onMouseUpHandler_connect);_7(this._onMouseDragHandler_connect);var _16=this._connects,i;for(i=_16.length;i>=0;i--){_7(_16[i]);delete _16[i];}this.inherited("_cleanUp",arguments);},_processEvent:function(evt){evt=_b(evt,evt.target);if(evt.type==="DOMMouseScroll"&&_4.isFF<3){evt.screenPoint=new _c(window.scrollX+evt.screenX-this.position.x,window.scrollY+evt.screenY-this.position.y);}else{evt.screenPoint=new _c(evt.pageX-this.position.x,evt.pageY-this.position.y);}evt.mapPoint=this.extent?this.toMap(evt.screenPoint):new _c();return evt;},_onMouseEnterHandler:function(evt){_7(this._onKeyDown_connect);_7(this._onKeyUp_connect);this._onKeyDown_connect=dc(document,"onkeydown",this,"_onKeyDownHandler");this._onKeyUp_connect=dc(document,"onkeyup",this,"_onKeyUpHandler");this.onMouseOver(this._processEvent(evt));},_onMouseLeaveHandler:function(evt){_7(this._onKeyDown_connect);_7(this._onKeyUp_connect);this.onMouseOut(this._processEvent(evt));},_onMouseMoveHandler:function(evt){if(this._dragEnd){this._dragEnd=false;return;}this.onMouseMove(this._processEvent(evt));},_onMouseDownHandler:function(evt){_7(this._onMouseMoveHandler_connect);var _17=this.__container;if(_17.setCapture){_17.setCapture(false);}this._onMouseDragHandler_connect=dc(document,"onmousemove",this,"_onMouseDragHandler");evt=this._processEvent(evt);this._downCoords=evt.screenPoint.x+","+evt.screenPoint.y;this.onMouseDown(evt);},_onMouseUpHandler:function(evt){var _18=this.__container;if(_18.releaseCapture){_18.releaseCapture();}evt=this._processEvent(evt);_7(this._onMouseDragHandler_connect);_7(this._onMouseMoveHandler_connect);this._onMouseMoveHandler_connect=dc(_18,"onmousemove",this,"_onMouseMoveHandler");this.onMouseUp(evt);},_onMouseDragHandler:function(evt){_7(this._onMouseDragHandler_connect);this._onMouseDragHandler_connect=dc(document,"onmousemove",this,"_onMouseDraggingHandler");_7(this._onMouseUpHandler_connect);this._onMouseUpHandler_connect=dc(document,"onmouseup",this,"_onDragMouseUpHandler");this._docLeaveConnect=dc(document,"onmouseout",this,"_onDocMouseOut");this.onMouseDragStart(this._processEvent(evt));},_onDocMouseOut:function(evt){var _19=evt.relatedTarget;if(!_19){this._onDragMouseUpHandler(evt);}},_onMouseDraggingHandler:function(evt){this.onMouseDrag(this._processEvent(evt));_4.stopEvent(evt);},_onDragMouseUpHandler:function(evt){var _1a=this.__container;if(_1a.releaseCapture){_1a.releaseCapture();}this._dragEnd=true;evt=this._processEvent(evt);this.onMouseDragEnd(evt);_7(this._docLeaveConnect);_7(this._onMouseDragHandler_connect);_7(this._onMouseUpHandler_connect);this._onMouseMoveHandler_connect=dc(_1a,"onmousemove",this,"_onMouseMoveHandler");this._onMouseUpHandler_connect=dc(_1a,"onmouseup",this,"_onMouseUpHandler");this.onMouseUp(evt);},_onClickHandler:function(evt){evt=this._processEvent(evt);if(this._downCoords!==(evt.screenPoint.x+","+evt.screenPoint.y)){return;}clearTimeout(this._clickTimer);this._clickEvent=_8({},evt);this._clickTimer=setTimeout(this._fireClickEvent,this._clickDuration);},_fireClickEvent:function(){clearTimeout(this._clickTimer);if(_4.isIE<9){var GL=esri.layers.GraphicsLayer;this._clickEvent.graphic=GL._clicked;delete GL["_clicked"];}this.onClick(this._clickEvent);},_onDblClickHandler:function(evt){clearTimeout(this._clickTimer);this.onDblClick(this._processEvent(evt));},_onMouseWheelHandler:function(evt){clearTimeout(this._mouseWheelTimer);evt=this._processEvent(evt);var _1b=_4.isIE||_4.isWebKit?evt.wheelDelta/_e:-evt.detail/_d,_1c=Math.abs(_1b);if(_1c<=_f){_1c=_f;}else{_1c=_10;}evt.value=_1b<0?-_1c:_1c;_8(this._mouseWheelEvent,evt);clearTimeout(this._mouseWheelTimer);this._mouseWheelTimer=setTimeout(this._fireMouseWheel,_11);if(this.__canStopSWEvt()){_4.stopEvent(evt);}},__canStopSWEvt:function(){},_fireMouseWheel:function(){this.onMouseWheel(this._mouseWheelEvent);this._mouseWheelEvent={};this._mouseWheelTimer=null;},_onKeyDownHandler:function(evt){this.onKeyDown(evt);},_onKeyUpHandler:function(evt){this.onKeyUp(evt);},__setClickDuration:function(dur){this._clickDuration=dur;},__resetClickDuration:function(){this._clickDuration=_12;},onMouseOver:function(){},onMouseMove:function(){},onMouseOut:function(){},onMouseDown:function(){},onMouseDragStart:function(){},onMouseDrag:function(){},onMouseDragEnd:function(){},onMouseUp:function(){},onClick:function(){},onDblClick:function(){},onMouseWheel:function(){},onKeyDown:function(){},onKeyUp:function(){}};}()));}_4.require("dijit.form.HorizontalSlider");_4.require("dijit.form.VerticalSlider");_4.require("dijit.form.HorizontalRule");_4.require("dijit.form.VerticalRule");_4.require("dijit.form.HorizontalRuleLabels");_4.require("dijit.form.VerticalRuleLabels");_4.declare("esri.Map",esri._MapContainer,(function(){var _1d=30,_1e=30,_1f=10,_20=1,_21=-1,_22=_4.mouseButtons.LEFT,_23={up:"panUp",right:"panRight",down:"panDown",left:"panLeft"},_24={upperRight:"panUpperRight",lowerRight:"panLowerRight",lowerLeft:"panLowerLeft",upperLeft:"panUpperLeft"};var dc=_4.connect,ddc=_4.disconnect,dcr=_4.create,ds=_4.style,dh=_4.hitch,abs=Math.abs,_25=_4.coords,_26=_4.deprecated,dk=_4.keys,_27=_4.mixin,_28=esri.geometry.Rect,_29=esri.geometry.Point,_2a=esri.geometry.Extent;var _2b=[dk.NUMPAD_PLUS,61,dk.NUMPAD_MINUS,dk.UP_ARROW,dk.NUMPAD_8,dk.RIGHT_ARROW,dk.NUMPAD_6,dk.DOWN_ARROW,dk.NUMPAD_2,dk.LEFT_ARROW,dk.NUMPAD_4,dk.PAGE_UP,dk.NUMPAD_9,dk.PAGE_DOWN,dk.NUMPAD_3,dk.END,dk.NUMPAD_1,dk.HOME,dk.NUMPAD_7];return {constructor:function(_2c,_2d){_27(this,{_dragOrigin:null,_slider:null,_navDiv:null,_zoomRect:null,_mapParams:_27({slider:true,nav:false,logo:true,sliderStyle:"default"},_2d||{}),_sliderChangeAnchor:null,_zoom:0,_keyboardPanDx:0,_keyboardPanDy:0});_27(this,{_onLoadHandler_connect:null,_panHandler_connect:null,_panStartHandler_connect:null,_upPanHandler_connect:null,_dblClickZoomHandler_connect:null,_recenterZoomHandler_connect:null,_recenterHandler_connect:null,_downPanHandler_connect:null,_downZoomHandler_connect:null,_keyNavigatingHandler_connect:null,_keyNavigationEndHandler_connect:null,_scrollZoomHandler_connect:null,_zoomHandler_connect:null,_upZoomHandler_connect:null,_slider_connect:null,_slidermovestop_connect:null});_27(this,{isDoubleClickZoom:false,isShiftDoubleClickZoom:false,isClickRecenter:false,isScrollWheelZoom:false,isPan:false,isRubberBandZoom:false,isKeyboardNavigation:false,isPanArrows:false,isZoomSlider:false});this._zoomRect=new esri.Graphic(null,new esri.symbol.SimpleFillSymbol(esri.config.defaults.map.zoomSymbol));this.setMapCursor("default");this._normalizeRect=dh(this,this._normalizeRect);this._isPanningOrZooming=dh(this,this._isPanningOrZooming);this._canZoom=dh(this,this._canZoom);this._onLoadHandler_connect=dc(this,"onLoad",this,"_onLoadInitNavsHandler");if(this._mapParams.logo){var _2e={right:(this._mapParams.nav?"25px":"")};if(_4.isIE===6){_2e["filter"]="progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true', sizingMethod='crop', src='"+_4.moduleUrl("esri","../../images/map/logo-med.png")+"')";}var _2f=this._ogol=dcr("div",{style:_2e},this.root);if((this.root.clientWidth*this.root.clientHeight)<250000){_4.addClass(_2f,"logo-sm");}else{_4.addClass(_2f,"logo-med");}if(!esri.isTouchEnabled){this._ogol_connect=dc(_2f,"onclick",this,"_openLogoLink");}}if(esri.isTouchEnabled){this._panInitEvent="onTouchStart";this._zoomInitEvent="onGestureStart";}else{this._panInitEvent="onMouseDown";this._zoomInitEvent="onMouseDown";}},_cleanUp:function(){this.disableMapNavigation();var i;for(i=this._connects.length;i>=0;i--){ddc(this._connects[i]);delete this._connects[i];}ddc(this._slider_connect);ddc(this._ogol_connect);var _30=this._slider;if(_30&&_30.destroy){_30.destroy();}var _31=this._navDiv;if(_31){_4.destroy(_31);}this.inherited("_cleanUp",arguments);},_normalizeRect:function(evt){var xy=evt.screenPoint,dx=this._dragOrigin.x,dy=this._dragOrigin.y,_32=new _28((xy.x<dx?xy.x:dx)-this.__visibleRect.x,(xy.y<dy?xy.y:dy)-this.__visibleRect.y,abs(xy.x-dx),abs(xy.y-dy));if(_32.width===0){_32.width=1;}if(_32.height===0){_32.height=1;}return _32;},_downZoomHandler:function(evt){if(evt.button==_22&&evt.shiftKey&&this.isRubberBandZoom){this._dragOrigin=_27({},evt.screenPoint);this.setCursor("crosshair");this._zoomHandler_connect=dc(this,"onMouseDrag",this,"_zoomHandler");this._upZoomHandler_connect=dc(this,"onMouseUp",this,"_upZoomHandler");if(evt.ctrlKey){this._zoom=_21;}else{this._zoom=_20;}}},_zoomHandler:function(evt){var _33=this._normalizeRect(evt).offset(this.__visibleRect.x,this.__visibleRect.y),g=this.graphics,_34=this._zoomRect;if(!_34.geometry){this.setCursor("crosshair");}if(_34.geometry){g.remove(_34,true);}var tl=this.toMap(new _29(_33.x,_33.y)),br=this.toMap(new _29(_33.x+_33.width,_33.y+_33.height));_33=new _28(tl.x,tl.y,br.x-tl.x,tl.y-br.y);_33._originOnly=true;_34.setGeometry(_33);g.add(_34,true);},_upZoomHandler:function(evt){var _35=this._zoomRect;ddc(this._zoomHandler_connect);ddc(this._upZoomHandler_connect);if(this._canZoom(this._zoom)&&_35.getDojoShape()){this.graphics.remove(_35);_35.geometry=null;var _36=this._normalizeRect(evt);_36.x+=this.__visibleRect.x;_36.y+=this.__visibleRect.y;var _37;if(this._zoom===_21){var _38=this.extent.getWidth(),_39=(_38*this.width)/_36.width,_3a=(_39-_38)/2,ext=this.extent;_37=new _2a(ext.xmin-_3a,ext.ymin-_3a,ext.xmax+_3a,ext.ymax+_3a,this.spatialReference);}else{var min=this.toMap({x:_36.x,y:(_36.y+_36.height)}),max=this.toMap({x:(_36.x+_36.width),y:_36.y});_37=new _2a(min.x,min.y,max.x,max.y,this.spatialReference);}this.__setExtent(_37);}if(_35.getDojoShape()){this.graphics.remove(_35,true);}this._zoom=0;this.resetMapCursor();},_downPanHandler:function(evt){if(evt.button==_22&&!evt.shiftKey&&this.isPan){this._dragOrigin=new _29(0,0);_27(this._dragOrigin,evt.screenPoint);this._panHandler_connect=dc(this,"onMouseDrag",this,"_panHandler");this._panStartHandler_connect=dc(this,"onMouseDragStart",this,"_panStartHandler");this._upPanHandler_connect=dc(this,"onMouseUp",this,"_upPanHandler");}},_panStartHandler:function(evt){this.setCursor("move");this.__panStart(evt.screenPoint.x,evt.screenPoint.y);},_panHandler:function(evt){this.__pan(evt.screenPoint.x-this._dragOrigin.x,evt.screenPoint.y-this._dragOrigin.y);},_upPanHandler:function(evt){ddc(this._panHandler_connect);ddc(this._panStartHandler_connect);ddc(this._upPanHandler_connect);if(this.__panning){this.__panEnd(evt.screenPoint.x-this._dragOrigin.x,evt.screenPoint.y-this._dragOrigin.y);this.resetMapCursor();}},_isPanningOrZooming:function(){return this.__panning||this.__zooming;},_recenterHandler:function(evt){if(evt.shiftKey&&!this._isPanningOrZooming()){this.centerAt(evt.mapPoint);}},_recenterZoomHandler:function(evt){if(evt.shiftKey&&!this._isPanningOrZooming()){evt.value=evt.ctrlKey?-1:1;this._scrollZoomHandler(evt);}},_dblClickZoomHandler:function(evt){if(!this._isPanningOrZooming()){evt.value=1;this._scrollZoomHandler(evt);}},_canZoom:function(_3b){if(!this.__tileInfo){return true;}var _3c=this.getLevel(),_3d=this.getNumLevels();if((_3c===0&&_3b<0)||(_3c===_3d-1&&_3b>0)){return false;}return true;},_scrollZoomHandler:function(evt){if(!this._canZoom(evt.value)){return;}var _3e=this.extent,_3f;if(this.__tileInfo){_3f=this.__getExtentForLevel(this.getLevel()+evt.value).extent;}else{_3f=_3e.expand(evt.value>0?0.5*evt.value:2*-evt.value);}var _40=evt.mapPoint,_41=_3e.xmin-((_3f.getWidth()-_3e.getWidth())*(_40.x-_3e.xmin)/_3e.getWidth()),_42=_3e.ymax-((_3f.getHeight()-_3e.getHeight())*(_40.y-_3e.ymax)/_3e.getHeight());this.__setExtent(new _2a(_41,_42-_3f.getHeight(),_41+_3f.getWidth(),_42,this.spatialReference),null,evt.screenPoint);},_keyNavigatingHandler:function(evt){var kc=evt.keyCode;if(_4.indexOf(_2b,kc)!==-1){var ti=this.__tileInfo;if(kc===dk.NUMPAD_PLUS||kc===61){if(ti){this.setLevel(this.getLevel()+1);}else{this.__setExtent(this.extent.expand(0.5));}}else{if(kc===dk.NUMPAD_MINUS){if(ti){this.setLevel(this.getLevel()-1);}else{this.__setExtent(this.extent.expand(2));}}else{if(!this.__panning){this.__panStart(0,0);}switch(kc){case dk.UP_ARROW:case dk.NUMPAD_8:this._keyboardPanDy+=_1f;break;case dk.RIGHT_ARROW:case dk.NUMPAD_6:this._keyboardPanDx-=_1f;break;case dk.DOWN_ARROW:case dk.NUMPAD_2:this._keyboardPanDy-=_1f;break;case dk.LEFT_ARROW:case dk.NUMPAD_4:this._keyboardPanDx+=_1f;break;case dk.PAGE_UP:case dk.NUMPAD_9:this._keyboardPanDx-=_1f;this._keyboardPanDy+=_1f;break;case dk.PAGE_DOWN:case dk.NUMPAD_3:this._keyboardPanDx-=_1f;this._keyboardPanDy-=_1f;break;case dk.END:case dk.NUMPAD_1:this._keyboardPanDx+=_1f;this._keyboardPanDy-=_1f;break;case dk.HOME:case dk.NUMPAD_7:this._keyboardPanDx+=_1f;this._keyboardPanDy+=_1f;break;default:return;}this.__pan(this._keyboardPanDx,this._keyboardPanDy);}}_4.stopEvent(evt);}},_keyNavigationEndHandler:function(evt){if(this.__panning){this.__panEnd(this._keyboardPanDx,this._keyboardPanDy);this._keyboardPanDx=this._keyboardPanDy=0;}},_onLoadInitNavsHandler:function(){this.enableMapNavigation();this._createNav();if(this._mapParams.sliderStyle==="small"||!this._createSlider){this._createSimpleSlider();}else{this._createSlider();}ddc(this._onLoadHandler_connect);},_createNav:function(){if(this._mapParams.nav){var div,v,i,_43=_4.addClass,id=this.id;this._navDiv=dcr("div",{id:id+"_navdiv"},this.root);_43(this._navDiv,"navDiv");var w2=this.width/2,h2=this.height/2,wh;for(i in _23){v=_23[i];div=dcr("div",{id:id+"_pan_"+i},this._navDiv);_43(div,"fixedPan "+v);if(i==="up"||i==="down"){wh=parseInt(_25(div).w,10)/2;ds(div,{left:(w2-wh)+"px",zIndex:_1d});}else{wh=parseInt(_25(div).h,10)/2;ds(div,{top:(h2-wh)+"px",zIndex:_1d});}this._connects.push(dc(div,"onclick",dh(this,this[v])));}this._onMapResizeNavHandler_connect=dc(this,"onResize",this,"_onMapResizeNavHandler");for(i in _24){v=_24[i];div=dcr("div",{id:id+"_pan_"+i,style:{zIndex:_1d}},this._navDiv);_43(div,"fixedPan "+v);this._connects.push(dc(div,"onclick",dh(this,this[v])));}this.isPanArrows=true;}},_onMapResizeNavHandler:function(_44,wd,ht){var id=this.id,w2=wd/2,h2=ht/2,_45=_4.byId,i,div,wh;for(i in _23){div=_45(id+"_pan_"+i);if(i==="up"||i==="down"){wh=parseInt(_25(div).w,10)/2;ds(div,"left",(w2-wh)+"px");}else{wh=parseInt(_25(div).h,10)/2;ds(div,"top",(h2-wh)+"px");}}},_createSimpleSlider:function(){if(this._mapParams.slider){var _46=(this._slider=dcr("div",{id:this.id+"_zoom_slider","class":"esriSimpleSlider",style:"z-index: "+_1e+";"}));_4.addClass(_46,esri.config.defaults.map.slider.width?"esriSimpleSliderHorizontal":"esriSimpleSliderVertical");var _47=dcr("div",{"class":"esriSimpleSliderIncrementButton"},_46);_47.innerHTML="+";var _48=dcr("div",{"class":"esriSimpleSliderDecrementButton"},_46);_48.innerHTML="-";if(_4.isIE<8){_4.addClass(_48,"dj_ie67Fix");}this._connects.push(dc(_47,"onclick",this,this._simpleSliderChangeHandler));this._connects.push(dc(_48,"onclick",this,this._simpleSliderChangeHandler));this.root.appendChild(_46);this.isZoomSlider=true;}},_simpleSliderChangeHandler:function(evt){var _49=(evt.currentTarget.className.indexOf("IncrementButton")!==-1)?true:false;var _4a=this.getLevel();if(_4a!==-1){var _4b=_49?(_4a+1):(_4a-1);this.setLevel(_4b);}else{var _4c=_49?0.5:2;this.__setExtent(this.extent.expand(_4c));}},_createSlider:function(){if(this._mapParams.slider){var div=dcr("div",{id:this.id+"_zoom_slider"},this.root),_4d=esri.config.defaults.map,_4e=_4d.slider.width,_4f=_4e?_5.form.HorizontalSlider:_5.form.VerticalSlider,_50=_4.toJson(_27({position:"absolute"},_4d.slider)),_51=this.getNumLevels(),_52=_5.form,i,il,_53;_50=_50.substring(1,_50.length-1).split("\"").join("").split(",").join(";");if(_51>0){var _54,_55,_56,_57,_58,_59=_4d.sliderLabel;if(_59){var _5a=_4e?_52.HorizontalRule:_52.VerticalRule,_5b=_4e?_52.HorizontalRuleLabels:_52.VerticalRuleLabels,_5c=_4e?"topDecoration":"rightDecoration",_5d=_4e?"height:"+_59.tick+"px":"width:"+_59.tick+"px";_58=_59.labels;if(_58===null){_58=[];for(i=0,il=_51;i<il;i++){_58[i]="";}}_54=dcr("div");div.appendChild(_54);_55=new _5a({container:_5c,count:_51,style:_5d},_54);_56=dcr("div");div.appendChild(_56);_57=new _5b({container:_5c,count:_51,labels:_58,style:_59.style},_56);_54=_56=null;}_53=(this._slider=new _4f({id:div.id,minimum:0,maximum:_51-1,discreteValues:_51,value:this.getLevel(),clickSelect:true,intermediateChanges:true,style:_50+"; z-index:"+_1e+";"},div));_53.startup();if(_59){_55.startup();_57.startup();}this._slider_connect=dc(_53,"onChange",this,"_onSliderChangeHandler");this._connects.push(dc(this,"onExtentChange",this,"_onExtentChangeSliderHandler"));this._connects.push(dc(_53._movable,"onFirstMove",this,"_onSliderMoveStartHandler"));}else{_53=(this._slider=new _4f({id:div.id,minimum:0,maximum:2,discreteValues:3,value:1,clickSelect:true,intermediateChanges:_4d.sliderChangeImmediate,style:_50+" height:100px; z-index:"+_1e+";"},div));var _5e=_53.domNode.firstChild.childNodes;for(i=1;i<=3;i++){ds(_5e[i],"visibility","hidden");}_53.startup();this._slider_connect=dc(_53,"onChange",this,"_onDynSliderChangeHandler");this._connects.push(dc(this,"onExtentChange",this,"_onExtentChangeDynSliderHandler"));}_4.forEach(_53._connects,function(_5f){var _60=_5f[0],_61=_60&&_60[0],_62=_61&&_61.className;if(_62&&(_62.indexOf("dijitSliderIncrementIcon")>=0||_62.indexOf("dijitSliderDecrementIcon")>=0)){_4.forEach(_5f,ddc);}});var _63=_53.incrementButton,_64=_53.decrementButton;_63.style.outline="none";_64.style.outline="none";_53._connects.push([dc(_63,"onmousedown",_53,function(e){this._typematicCallback(1,_63,e);}),dc(_64,"onmousedown",_53,function(e){this._typematicCallback(1,_64,e);})]);_53.sliderHandle.style.outline="none";_53._onKeyPress=function(){};var _65=_53._movable;if(_65){var _66=_65.onMouseDown;_65.onMouseDown=function(e){if(_4.isIE<9&&e.button!==1){return;}_66.apply(this,arguments);};}this.isZoomSlider=true;}},_onSliderMoveStartHandler:function(){ddc(this._slider_connect);this._slider_connect=dc(this._slider,"onChange",this,"_onSliderChangeDragHandler");this._slidermovestop_connect=dc(this._slider._movable,"onMoveStop",this,"_onSliderMoveEndHandler");this._sliderChangeAnchor=this.toScreen(this.extent.getCenter());this.__zoomStart(this.extent,this._sliderChangeAnchor);},_onSliderChangeDragHandler:function(_67){var _68=this.__getExtentForLevel(_67).extent,_69=this.extent.getWidth()/_68.getWidth();this.__zoom(_68,_69,this._sliderChangeAnchor);},_onSliderMoveEndHandler:function(){ddc(this._slider_connect);ddc(this._slidermovestop_connect);var _6a=this.__getExtentForLevel(this._slider.value),_6b=_6a.extent,_6c=this.extent.getWidth()/_6b.getWidth();this.__zoomEnd(_6b,_6c,this._sliderChangeAnchor,_6a.lod,true);this._sliderChangeAnchor=null;},_onSliderChangeHandler:function(_6d){this.setLevel(_6d);},_updateSliderValue:function(_6e,_6f){ddc(this._slider_connect);var _70=this._slider;var _71=_70._onChangeActive;_70._onChangeActive=false;_70.set("value",_6e);_70._onChangeActive=_71;this._slider_connect=dc(_70,"onChange",this,_6f);},_onExtentChangeSliderHandler:function(_72,_73,_74,lod){ddc(this._slidermovestop_connect);this._updateSliderValue(lod.level,"_onSliderChangeHandler");},_onDynSliderChangeHandler:function(_75){if(_75>0){this.__setExtent(this.extent.expand(0.5));}else{this.__setExtent(this.extent.expand(2));}},_onExtentChangeDynSliderHandler:function(){this._updateSliderValue(1,"_onDynSliderChangeHandler");},_openLogoLink:function(evt){window.open(esri.config.defaults.map.logoLink,"_blank");_4.stopEvent(evt);},enableMapNavigation:function(){this.enableDoubleClickZoom();this.enableClickRecenter();this.enablePan();this.enableRubberBandZoom();this.enableKeyboardNavigation();this.enableScrollWheelZoom();},disableMapNavigation:function(){this.disableDoubleClickZoom();this.disableClickRecenter();this.disablePan();this.disableRubberBandZoom();this.disableKeyboardNavigation();this.disableScrollWheelZoom();},enableDoubleClickZoom:function(){if(!this.isDoubleClickZoom){this._dblClickZoomHandler_connect=dc(this,"onDblClick",this,"_dblClickZoomHandler");this.isDoubleClickZoom=true;}},disableDoubleClickZoom:function(){if(this.isDoubleClickZoom){ddc(this._dblClickZoomHandler_connect);this.isDoubleClickZoom=false;}},enableShiftDoubleClickZoom:function(){if(!this.isShiftDoubleClickZoom){_26(this.declaredClass+": "+esri.bundle.map.deprecateShiftDblClickZoom,null,"v2.0");this._recenterZoomHandler_connect=dc(this,"onDblClick",this,"_recenterZoomHandler");this.isShiftDoubleClickZoom=true;}},disableShiftDoubleClickZoom:function(){if(this.isShiftDoubleClickZoom){_26(this.declaredClass+": "+esri.bundle.map.deprecateShiftDblClickZoom,null,"v2.0");ddc(this._recenterZoomHandler_connect);this.isShiftDoubleClickZoom=false;}},enableClickRecenter:function(){if(!this.isClickRecenter){this._recenterHandler_connect=dc(this,"onClick",this,"_recenterHandler");this.isClickRecenter=true;}},disableClickRecenter:function(){if(this.isClickRecenter){ddc(this._recenterHandler_connect);this.isClickRecenter=false;}},enablePan:function(){if(!this.isPan){this._downPanHandler_connect=dc(this,this._panInitEvent,this,"_downPanHandler");this.isPan=true;}},disablePan:function(){if(this.isPan){ddc(this._downPanHandler_connect);this.isPan=false;}},enableRubberBandZoom:function(){if(!this.isRubberBandZoom){this._downZoomHandler_connect=dc(this,this._zoomInitEvent,this,"_downZoomHandler");this.isRubberBandZoom=true;}},disableRubberBandZoom:function(){if(this.isRubberBandZoom){ddc(this._downZoomHandler_connect);this.isRubberBandZoom=false;}},enableKeyboardNavigation:function(){if(!this.isKeyboardNavigation){this._keyNavigatingHandler_connect=dc(this,"onKeyDown",this,"_keyNavigatingHandler");this._keyNavigationEndHandler_connect=dc(this,"onKeyUp",this,"_keyNavigationEndHandler");this.isKeyboardNavigation=true;}},disableKeyboardNavigation:function(){if(this.isKeyboardNavigation){ddc(this._keyNavigatingHandler_connect);ddc(this._keyNavigationEndHandler_connect);this.isKeyboardNavigation=false;}},enableScrollWheelZoom:function(){if(!this.isScrollWheelZoom){this._scrollZoomHandler_connect=dc(this,"onMouseWheel",this,"_scrollZoomHandler");this.isScrollWheelZoom=true;}},__canStopSWEvt:function(){return this.isScrollWheelZoom;},disableScrollWheelZoom:function(){if(this.isScrollWheelZoom){ddc(this._scrollZoomHandler_connect);this.isScrollWheelZoom=false;}},showPanArrows:function(){if(this._navDiv){esri.show(this._navDiv);this.isPanArrows=true;}},hidePanArrows:function(){if(this._navDiv){esri.hide(this._navDiv);this.isPanArrows=false;}},showZoomSlider:function(){if(this._slider){ds(this._slider.domNode||this._slider,"visibility","visible");this.isZoomSlider=true;}},hideZoomSlider:function(){if(this._slider){ds(this._slider.domNode||this._slider,"visibility","hidden");this.isZoomSlider=false;}}};}()));_4.require("esri.layers.agsdynamic");_4.require("esri.layers.agstiled");_4.require("esri.layers.agsimageservice");if(esri.isTouchEnabled){_4.extend(esri.Map,(function(){var dc=_4.connect,ddc=_4.disconnect,_76=esri.geometry.Point,_77=esri.geometry.getLength,_78=esri.TileUtils.getCandidateTileInfo;return {_multiTouchTapZoomHandler:function(evt){if(!this._isPanningOrZooming()){evt.value=-1;this._scrollZoomHandler(evt);}},_downPanHandler:function(evt){this._dragOrigin=new _76(0,0);_4.mixin(this._dragOrigin,evt.screenPoint);this._panHandler_connect=dc(this,"onTouchMove",this,this._panHandler);this._upPanHandler_connect=dc(this,"onTouchEnd",this,this._upPanHandler);},_panHandler:function(evt){if(this.__panning){this.__pan(evt.screenPoint.x-this._dragOrigin.x,evt.screenPoint.y-this._dragOrigin.y);}else{this.setCursor("move");this.__panStart(evt.screenPoint.x,evt.screenPoint.y);}evt.preventDefault();},_upPanHandler:function(evt){ddc(this._panHandler_connect);ddc(this._upPanHandler_connect);if(this.__panning){this.__panEnd(evt.screenPoint.x-this._dragOrigin.x,evt.screenPoint.y-this._dragOrigin.y);this.resetMapCursor();}},_downZoomHandler:function(evt){this._zoomHandler_connect=dc(this,"onGestureChange",this,this._zoomHandler);this._upZoomHandler_connect=dc(this,"onGestureEnd",this,this._upZoomHandler);},_zoomHandler:function(evt){if(evt.screenPoints){this.currLength=_77(evt.screenPoints[0],evt.screenPoints[1]);if(this.__zooming){var _79=this.currLength/this._length;this._zoomStartExtent=this.__scaleExtent(this.extent,_79,this._dragOrigin);this.__zoom(this._zoomStartExtent,_79,this._dragOrigin);}else{this._dragOrigin=new _76((evt.screenPoints[0].x+evt.screenPoints[1].x)/2,(evt.screenPoints[0].y+evt.screenPoints[1].y)/2);this._length=this.currLength;this.__zoomStart(this.extent,this._dragOrigin);}evt.preventDefault();}},_upZoomHandler:function(evt){ddc(this._zoomHandler_connect);ddc(this._upZoomHandler_connect);if(evt.processMultiTouchTap){this._multiTouchTapZoomHandler(evt);evt.preventDefault();}else{if(this.__zooming&&this._zoomAnim===null){var _7a=this.currLength/this._length,_7b=this.extent.getWidth();this._zoomAnimAnchor=this.toMap(this._dragOrigin);this._zoomStartExtent=this.__scaleExtent(this.extent,1/_7a,this._zoomAnimAnchor);if(this.__tileInfo){var ct=_78(this,this.__tileInfo,this._zoomStartExtent),_7c=this.__getExtentForLevel(ct.lod.level,this._zoomAnimAnchor);this._zoomEndExtent=_7c.extent;this._zoomEndLod=_7c.lod;this._zoomAnim=esri.fx.animateRange({range:{start:(_7b/this._zoomStartExtent.getWidth()),end:(_7b/this._zoomEndExtent.getWidth())},duration:200,rate:50,onAnimate:_4.hitch(this,"_adjustZoomHandler"),onEnd:_4.hitch(this,"_adjustZoomEndHandler")}).play();}else{this._zoomEndExtent=this._zoomStartExtent;this._adjustZoomEndHandler();}}}},_adjustZoomHandler:function(_7d){var _7e=this.__scaleExtent(this.extent,_7d,this._zoomAnimAnchor);this.__zoom(_7e,_7d,this._dragOrigin);},_adjustZoomEndHandler:function(){var _7f=this.extent.getWidth()/this._zoomEndExtent.getWidth(),_80=this.__scaleExtent(this.extent,1/_7f,this._zoomAnimAnchor);this.__zoomEnd(_80,_7f,this._dragOrigin,this._zoomEndLod,this.__LOD?(this.__LOD.level!=this._zoomEndLod.level):true);this._zoomStartExtent=this._zoomEndExtent=this._zoomEndLod=this._dragOrigin=this._zoomAnim=this._zoomAnimAnchor=null;}};}()));}}}};});