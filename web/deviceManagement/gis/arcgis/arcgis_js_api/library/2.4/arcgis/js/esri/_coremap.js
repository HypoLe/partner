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

if(!dojo._hasResource["esri._coremap"]){dojo._hasResource["esri._coremap"]=true;dojo.provide("esri._coremap");dojo.require("dijit._base.manager");dojo.require("esri.geometry");dojo.require("esri.utils");dojo.require("esri.fx");dojo.require("esri.layers.graphics");dojo.require("esri.dijit.InfoWindow");dojo.declare("esri._CoreMap",null,(function(){var _1=esri.geometry.toMapPoint,_2=esri.geometry.toScreenPoint,dc=dojo.connect,_3=dojo.disconnect,dh=dojo.hitch,ds=dojo.style,_4=dojo.indexOf,_5=dojo.mixin,_6=esri.geometry.Point,_7=esri.geometry.Extent,_8=esri.layers.GraphicsLayer,_9=esri.geometry.Rect,_a=0,_b=esri.config.defaults.map;var _c=1000000,_d=0.75,_e=0.25,_f=3,_10=20,_11=40;function _12(_13,_14){var _15=_13.lods;_15.sort(function(l1,l2){if(l1.scale>l2.scale){return -1;}else{if(l1.scale<l2.scale){return 1;}}return 0;});var _16=[];_15=dojo.filter(_15,function(l){if(_4(_16,l.scale)===-1){_16.push(l.scale);return true;}});var pl=(_14.lods=[]),l;dojo.forEach(_15,function(lod,_17){l=(pl[_17]=new esri.layers.LOD(lod));l.level=_17;});_14.tileInfo=new esri.layers.TileInfo(_5(_13,{lods:pl}));};return {constructor:function(_18,_19){_5(this,{_internalLayerIds:[],_layers:[],_layerDivs:[],_layerSize:0,_clickHandles:[],_connects:[]});_5(this,{_zoomAnimDiv:null,_zoomAnim:null,_layersDiv:null,_firstLayerId:null,_delta:null,_gc:null,_cursor:null,_ratioW:1,_ratioH:1,_params:null});_5(this,{cursor:null,layerIds:[],graphicsLayerIds:[],graphics:null,loaded:false});_5(this,{__panning:false,__zooming:false,__container:null,root:null,__LOD:null,__tileInfo:null,__visibleRect:null,__visibleDelta:null});var _1a=(this.container=dojo.byId(_18));var id=(this.id=dojo.attr(_1a,"id")||dijit.getUniqueId(this.declaredClass));dojo.addClass(_1a,"map");var box=dojo.contentBox(_1a),dac=dojo.addClass,dcr=dojo.create;this.position=new _6(0,0);this._reposition();var _1b=(this.width=(box.w||_b.width));var _1c=(this.height=box.h||_b.height);if(box.w===0){ds(_1a,"width",_1b+"px");}if(box.h===0){ds(_1a,"height",_1c+"px");}var _1d=(this.root=dcr("div",{id:_18+"_root",style:{width:_1b+"px",height:_1c+"px"}}));dac(_1d,"container");var _1e=(this.__container=dcr("div",{id:_18+"_container"},_1d));ds(_1e,"position","absolute");dac(_1e,"container");_1a.appendChild(_1d);var _1f=(this._params=_5({slider:true,nav:false,extent:null,layer:null,scales:null,showInfoWindowOnClick:true,displayGraphicsOnPan:true,lods:null,tileInfo:null,wrapAround180:false,fitExtent:false},_19||{}));this.wrapAround180=_1f.wrapAround180;if(_1f.lods){_12({rows:512,cols:512,dpi:96,format:"JPEG",compressionQuality:75,origin:{x:-180,y:90},spatialReference:{wkid:4326},lods:_1f.lods},_1f);this.__tileInfo=_1f.tileInfo;}var ext=(this.extent=_1f.extent);this.spatialReference=(ext&&ext.spatialReference)?ext.spatialReference:null;this.__visibleRect=new _9(0,0,_1b,_1c);this.__visibleDelta=new _9(0,0,_1b,_1c);var _20=(this._layersDiv=dcr("div",{id:id+"_layers"}));dac(_20,"layersDiv");_1e.appendChild(_20);this._zoomAnimDiv=dcr("div",{style:{position:"absolute"}});if(_1f.infoWindow){this.infoWindow=_1f.infoWindow;}else{var iw=(this.infoWindow=new esri.dijit.InfoWindow({map:this,title:"",id:id+"_infowindow"},dcr("div",null,_1d)));iw.startup();iw._ootb=true;ds(iw.domNode,"zIndex",_11);}this._zoomStartHandler=dh(this,this._zoomStartHandler);this._zoomingHandler=dh(this,this._zoomingHandler);this._zoomEndHandler=dh(this,this._zoomEndHandler);this._panningHandler=dh(this,this._panningHandler);this._panEndHandler=dh(this,this._panEndHandler);this._fixedPan=dh(this,this._fixedPan);dojo.addOnWindowUnload(this,this.destroy);},_cleanUp:function(){var iw=this.infoWindow;if(iw){if(iw._ootb){iw.destroy();}else{iw.unsetMap(this);}delete this.infoWindow;}var _21=this._connects,i;for(i=_21.length-1;i>=0;i--){_3(_21[i]);delete _21[i];}_3(this._tsTimeExtentChange_connect);this.setInfoWindowOnClick(false);dojo.destroy(this.root);this.root=null;},_addLayer:function(_22,_23,_24){var id=(_22.id=_22.id||(_22 instanceof _8?_b.graphicsLayerNamePrefix:_b.layerNamePrefix)+(_a++));this._layers[id]=_22;var i;if(_23===this.layerIds||_23===this.graphicsLayerIds){i=this._layerSize;this._layerSize++;}_24=(_24===undefined||_24<0||_24>_23.length)?_23.length:_24;if(i===0){this._firstLayerId=id;}_23.splice(_24,0,id);var _25=dh(this,this._addLayerHandler),_26=this,_27=this._connects,_28=function(){if(_22.loaded){_25(_22);}else{_26[id+"_addtoken_load"]=dc(_22,"onLoad",_26,"_addLayerHandler");_26[id+"_addtoken_err"]=dc(_22,"onError",_26,function(_29){_25(_22,_29,_23);});}};if(this.loaded||i===0||(_22.loaded&&_4(this.graphicsLayerIds,id)===-1)){_28();}else{_27.push(dc(this,"onLoad",_28));}return _22;},_addLayerHandler:function(_2a,_2b,_2c){var id=this.id,_2d=_2a.id,_2e=_4(_2a instanceof _8?this.graphicsLayerIds:this.layerIds,_2d),_2f=_2e,_30=false,_31=this._params;_3(this[_2d+"_addtoken_load"]);_3(this[_2d+"_addtoken_err"]);if(_2b){delete this._layers[_2d];if(_2e!==-1){_2c.splice(_2e,1);this.onLayerAddResult(_2a,_2b);}return;}if(_2e===-1){_2e=_4(this._internalLayerIds,_2d);_2f=_10+_2e;_30=true;}if(_2a instanceof _8){var _32=_2a._setMap(this,this._gc._surface);_32.id=id+"_"+_2d;this._layerDivs[_2d]=_32;this._reorderLayers(this.graphicsLayerIds);if(_31.showInfoWindowOnClick){this._clickHandles.push(dc(_2a,"onClick",this,"_gClickHandler"));}}else{var _33=_2a._setMap(this,this._layersDiv,_2f,this.__LOD);_33.id=id+"_"+_2d;this._layerDivs[_2d]=_33;this._reorderLayers(this.layerIds);if(!_30&&_2a.declaredClass.indexOf("VETiledLayer")!==-1){this._onBingLayerAdd(_2a);}}if(_2d===this._firstLayerId){this.spatialReference=this.spatialReference||_2a.spatialReference;var _34=this.spatialReference;this.wrapAround180=(this.wrapAround180&&_34&&_34._isWrappable())?true:false;if(_2a.tileInfo){if(!this.__tileInfo){_12(_5({},_2a.tileInfo),_31);this.__tileInfo=_31.tileInfo;}else{var _35=this.__tileInfo.lods;this.__tileInfo=_5({},_2a.tileInfo);this.__tileInfo.lods=_35;}}if(this.wrapAround180){var _36=this.__tileInfo,_37=_34._getInfo();if(!_36||Math.abs(_37.origin[0]-_36.origin.x)>_37.dx){this.wrapAround180=false;}if(this.wrapAround180&&_36){esri.TileUtils._addFrameInfo(_36,_37);}}_31.units=_2a.units;this._gc=new esri.layers._GraphicsContainer();var gc=this._gc._setMap(this,this._layersDiv);gc.id=id+"_gc";this.graphics=new _8({id:id+"_graphics",displayOnPan:_31.displayGraphicsOnPan});this._addLayer(this.graphics,this._internalLayerIds,_10);}if(_2a===this.graphics){if(this.extent){var x=this._fixExtent(this.extent,_31.fitExtent);this.extent=x.extent;this.__LOD=x.lod;}var fli=this._firstLayerId;this._firstLayerId=null;this.__setExtent(this.extent?this.extent:new _7(this._layers[fli].initialExtent),null,null,_31.fitExtent);this.loaded=true;this.infoWindow.setMap(this);this.onLoad(this);}if(!_30){this.onLayerAdd(_2a);this.onLayerAddResult(_2a);}_3(this[_2d+"_addLayerHandler_connect"]);},_reorderLayers:function(_38){var _39=this.onLayerReorder,djp=dojo.place,_3a=this._layerDivs,_3b=this._layers,_3c=this._gc?this._gc._surface.getEventSource():null;if(_38===this.graphicsLayerIds){dojo.forEach(_38,function(id,i){var _3d=_3a[id];if(_3d){djp(_3d.getEventSource(),_3c,i);_39(_3b[id],i);}});}else{var g=this.graphics,gId=g?g.id:null,_3e=this._layersDiv,_3f;dojo.forEach(_38,function(id,i){_3f=_3a[id];if(id!==gId&&_3f){djp(_3f,_3e,i);_39(_3b[id],i);}});if(_3c){_3c=esri.vml?_3c.parentNode:_3c;djp(_3c,_3c.parentNode,_38.length);}}this.onLayersReordered([].concat(_38));},_zoomStartHandler:function(){this.__zoomStart(this._zoomAnimDiv.startingExtent,this._zoomAnimDiv.anchor);},_zoomingHandler:function(_40){var rl=parseFloat(_40.left),rt=parseFloat(_40.top),_41=new _7(rl,rt-parseFloat(_40.height),rl+parseFloat(_40.width),rt,this.spatialReference),_42=this.extent.getWidth()/_41.getWidth();this.__zoom(_41,_42,this._zoomAnimDiv.anchor);},_zoomEndHandler:function(){var _43=this._zoomAnimDiv,_44=_43.extent,_45=this.extent.getWidth()/_44.getWidth();var _46=_43.anchor,_47=_43.newLod,_48=_43.levelChange;_43.extent=_43.anchor=_43.levelChange=_43.startingExtent=_43.newLod=this._delta=this._zoomAnim=null;this.__zoomEnd(_44,_45,_46,_47,_48);},_panningHandler:function(_49){var d=new _6(parseFloat(_49.left),parseFloat(_49.top)),dm=this.toMap(d);this.onPan(this.extent.offset(dm.x,dm.y),d);},_panEndHandler:function(){this.__panning=false;var _4a=Math.round,_4b=this._delta.offset(-_4a(this.width/2),-_4a(this.height/2)),dx=_4b.x,dy=_4b.y,_4c=this.__visibleRect,_4d=this.__visibleDelta;_4c.x+=-dx;_4c.y+=-dy;_4d.x+=-dx;_4d.y+=-dy;ds(this._zoomAnimDiv,{left:"0px",top:"0px"});var _4e=this.extent,rw=this._ratioW,rh=this._ratioH;_4e=(this.extent=new _7(_4e.xmin+(dx/rw),_4e.ymin-(dy/rh),_4e.xmax+(dx/rw),_4e.ymax-(dy/rh),this.spatialReference));_4b.setX(-_4b.x);_4b.setY(-_4b.y);this._delta=null;this.onPanEnd(_4e,_4b);this.onExtentChange(_4e,_4b,false,this.__LOD);},_fixExtent:function(_4f,fit){var _50=this._reshapeExtent(_4f),_51=1+_e;while(fit===true&&(_50.extent.getWidth()<_4f.getWidth()||_50.extent.getHeight()<_4f.getHeight())&&_50.lod.level>0&&_51<=_f){_50=this._reshapeExtent(_4f.expand(_51));_51+=_e;}return _50;},_getFrameWidth:function(){var _52=-1,_53=this.spatialReference._getInfo();if(this.__LOD){var _54=this.__LOD._frameInfo;if(_54){_52=_54[3];}}else{if(_53){_52=Math.round((2*_53.valid[1])/(this.extent.getWidth()/this.width));}}return _52;},_reshapeExtent:function(_55){var w=_55.getWidth(),h=_55.getHeight(),r=w/h,_56=this.width/this.height,dw=0,dh=0;if(this.width>this.height){if(w>h){if(_56>r){dw=(h*_56)-w;}else{dh=(w/_56)-h;}}else{if(w<h){dw=(h*_56)-w;}else{dw=(h*_56)-w;}}}else{if(this.width<this.height){if(w>h){dh=(w/_56)-h;}else{if(w<h){if(_56>r){dw=(h*_56)-w;}else{dh=(w/_56)-h;}}else{dh=(w/_56)-h;}}}else{if(w<h){dw=h-w;}else{if(w>h){dh=(w/_56)-h;}}}}if(dw){_55.xmin-=dw/2;_55.xmax+=dw/2;}if(dh){_55.ymin-=dh/2;_55.ymax+=dh/2;}return this._getAdjustedExtent(_55);},_getAdjustedExtent:function(_57){if(this.__tileInfo){return esri.TileUtils.getCandidateTileInfo(this,this.__tileInfo,_57);}else{return {extent:_57};}},_panTo:function(_58){var ewd=this.extent.getWidth(),eht=this.extent.getHeight(),_59=_58.x-(ewd/2),_5a=_59+ewd,_5b=_58.y-(eht/2),_5c=_5b+eht;this.__setExtent(new _7(_59,_5b,_5a,_5c));},_fixedPan:function(dx,dy){this._panTo(this.toMap(new _6((this.width/2)+dx,(this.height/2)+dy)));},_gClickHandler:function(evt){var _5d=evt.graphic,iw=this.infoWindow;if(_5d._getEffInfoTemplate()&&iw){dojo.stopEvent(evt);var _5e=_5d.geometry,_5f=(_5e&&_5e.type==="point")?_5e:evt.mapPoint;iw.setTitle(_5d.getTitle());iw.setContent(_5d.getContent());iw.show(_5f);}},_onBingLayerAdd:function(_60){this["__"+_60.id+"_vis_connect"]=dojo.connect(_60,"onVisibilityChange",this,"_toggleBingLogo");this._toggleBingLogo(_60.visible);},_onBingLayerRemove:function(_61){dojo.disconnect(this["__"+_61.id+"_vis_connect"]);delete this["__"+_61.id+"_vis_connect"];var _62=this.layerIds;var _63=dojo.some(_62,function(_64){var _65=this._layers[_64];return _65&&_65.visible&&_65.declaredClass.indexOf("VETiledLayer")!==-1;},this);this._toggleBingLogo(_63);},_toggleBingLogo:function(_66){if(_66&&!this._bingLogo){var _67={left:(this._mapParams&&this._mapParams.nav?"25px":"")};if(dojo.isIE===6){_67.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true', sizingMethod='crop', src='"+dojo.moduleUrl("esri","../../images/map/logo-med.png")+"')";}var _68=this._bingLogo=dojo.create("div",{style:_67},this.root);dojo.addClass(_68,"bingLogo-lg");}else{if(!_66&&this._bingLogo){dojo.destroy(this._bingLogo);delete this._bingLogo;}}},__panStart:function(x,y){this.__panning=true;this.onPanStart(this.extent,new _6(x,y));},__pan:function(dx,dy){var _69=this.extent,rw=this._ratioW,rh=this._ratioH;this.onPan(new _7(_69.xmin-(dx/rw),_69.ymin+(dy/rh),_69.xmax-(dx/rw),_69.ymax+(dy/rh),this.spatialReference),new _6(dx,dy));},__panEnd:function(dx,dy){var _6a=this.__visibleRect,_6b=this.__visibleDelta;_6a.x+=dx;_6a.y+=dy;_6b.x+=dx;_6b.y+=dy;var d=new _6(dx,dy),_6c=this.extent,rw=this._ratioW,rh=this._ratioH;_6c=(this.extent=new _7(_6c.xmin-(dx/rw),_6c.ymin+(dy/rh),_6c.xmax-(dx/rw),_6c.ymax+(dy/rh),this.spatialReference));this.__panning=false;this.onPanEnd(_6c,d);this.onExtentChange(_6c,d,false,this.__LOD);},__zoomStart:function(_6d,_6e){this.__zooming=true;this.onZoomStart(_6d,1,_6e,this.__LOD?this.__LOD.level:null);},__zoom:function(_6f,_70,_71){this.onZoom(_6f,_70,_71);},__zoomEnd:function(_72,_73,_74,lod,_75){ds(this._layersDiv,{left:"0px",top:"0px"});this._delta=new _6(0,0);this.__visibleRect.x=(this.__visibleRect.y=0);_72=(this.extent=new _7(_72));this.__LOD=lod;this._ratioW=this.width/_72.getWidth();this._ratioH=this.height/_72.getHeight();var _76=this._delta;this._delta=null;this.__zooming=false;this.onZoomEnd(_72,_73,_74,lod?lod.level:null);this.onExtentChange(_72,_76,_75,lod);},__setExtent:function(_77,_78,_79,fit){try{if(this._zoomAnim){return;}if(this._firstLayerId){this.extent=_77;return;}var _7a=true,ext=this.extent,_7b=this._fixExtent(_77,fit||false);_77=_7b.extent;var _7c=_77.getWidth(),_7d=_77.getHeight(),_7e=Math.round;if(ext){var tw=_7e(ext.getWidth()*_c),w=_7e(_7c*_c),th=_7e(ext.getHeight()*_c),h=_7e(_7d*_c);_7a=(tw!==w)||(th!==h);}var _7f,_80,end,_81,_82,_83;if(_b.zoomDuration&&_7a&&ext){_81=new _7(ext);_80={left:ext.xmin,top:ext.ymax,width:ext.getWidth(),height:ext.getHeight()};end={left:_77.xmin,top:_77.ymax,width:_7c,height:_7d};_82=_80.width/end.width;_83=_80.height/end.height;var mtl=new _6(_77.xmin,_77.ymax),mbl=new _6(_77.xmin,_77.ymin),etl=new _6(ext.xmin,ext.ymax),ebl=new _6(ext.xmin,ext.ymin);_7f=esri.geometry.getLineIntersection(etl,mtl,ebl,mbl);if(!_7f){_7a=false;}}this._ratioW=this.width/_7c;this._ratioH=this.height/_7d;var _84=this._zoomAnimDiv;if(_7a){ds(this._layersDiv,{left:"0px",top:"0px"});_78=new _6(0,0);this.__visibleRect.x=(this.__visibleRect.y=0);if(_80&&end){this._delta=_78;_84.id="_zAD";_84.startingExtent=_81;_84.extent=_77;_84.levelChange=_7a;_84.newLod=_7b.lod;this._zoomAnim=esri.fx.resize({node:_84,start:_80,end:end,duration:_b.zoomDuration,rate:_b.zoomRate,beforeBegin:this._zoomStartHandler,onAnimate:this._zoomingHandler,onEnd:this._zoomEndHandler});if(_79){_84.anchor=_79;}else{_84.anchor=_2(ext,this.width,this.height,_7f);}this._zoomAnim.play();}else{this.extent=_77;this.onExtentChange(this.extent,_78,_7a,(this.__LOD=_7b.lod));}}else{if(!this.__panning){if(this.loaded===false){this.extent=_77;this.onExtentChange(this.extent,_78,_7a,(this.__LOD=_7b.lod));}else{this.__panning=true;_80=new _9(0,0,this.width,this.height,this.spatialReference).getCenter();_80.x=_7e(_80.x);_80.y=_7e(_80.y);this.onPanStart(this.extent,new _6(0,0));var _85=(this._delta=this.toScreen(_77.getCenter()));esri.fx.slideTo({node:_84,left:_80.x-_85.x,top:_80.y-_85.y,duration:_b.panDuration,rate:_b.panRate,onAnimate:this._panningHandler,onEnd:this._panEndHandler}).play();}}}}catch(e){console.log(e.stack);console.error(e);}},__getExtentForLevel:function(_86,_87,_88){var ti=this.__tileInfo;_88=_88||this.extent;_87=_87||_88.getCenter();if(ti){var _89=ti.lods;if(_86<0||_86>=_89.length){return {};}var lod=_89[_86],_8a=this.width*lod.resolution/2,_8b=this.height*lod.resolution/2;return {extent:new _7(_87.x-_8a,_87.y-_8b,_87.x+_8a,_87.y+_8b,_87.spatialReference),lod:lod};}else{return {extent:_88.expand(_86).centerAt(_87)};}},__scaleExtent:function(_8c,_8d,_8e){var _8f=_8e||_8c.getCenter();var _90=_8c.expand(_8d),_91=_8c.xmin-((_90.getWidth()-_8c.getWidth())*(_8f.x-_8c.xmin)/_8c.getWidth()),_92=_8c.ymax-((_90.getHeight()-_8c.getHeight())*(_8f.y-_8c.ymax)/_8c.getHeight());return new _7(_91,_92-_90.getHeight(),_91+_90.getWidth(),_92,_8c.spatialReference);},_jobs:0,_incr:function(){if((++this._jobs)===1){this.updating=true;this.onUpdateStart();}},_decr:function(){var _93=--this._jobs;if(!_93){this.updating=false;this.onUpdateEnd();}else{if(_93<0){this._jobs=0;}}},onUpdateStart:function(){},onUpdateEnd:function(){},onLoad:function(){this._setClipRect();},onUnload:function(){},onExtentChange:function(a,b,_94){if(_94){this._setClipRect();}},onTimeExtentChange:function(){},onLayerAdd:function(){},onLayerAddResult:function(){},onLayersAddResult:function(){},onLayerRemove:function(){},onLayersRemoved:function(){},onLayerReorder:function(){},onLayersReordered:function(){},onPanStart:function(){},onPan:function(){},onPanEnd:function(){},onZoomStart:function(){},onZoom:function(){},onZoomEnd:function(){},onResize:function(){this._setClipRect();},onReposition:function(){},destroy:function(){if(!this._destroyed){this.removeAllLayers();this._cleanUp();if(this._gc){this._gc._cleanUp();}this._destroyed=true;this.onUnload(this);}},setCursor:function(_95){ds(this.__container,"cursor",(this.cursor=_95));},setMapCursor:function(c){this.setCursor((this._cursor=c));},resetMapCursor:function(){this.setCursor(this._cursor);},setInfoWindow:function(_96){var iw=this.infoWindow;if(iw){iw.unsetMap(this);}this.infoWindow=_96;if(this.loaded&&_96){_96.setMap(this);}},setInfoWindowOnClick:function(_97){var _98=this._params;if(_97){if(!_98.showInfoWindowOnClick){var _99=[this.graphics].concat(dojo.map(this.graphicsLayerIds,this.getLayer,this));dojo.map(_99,function(_9a){if(_9a&&_9a.loaded){this._clickHandles.push(dc(_9a,"onClick",this,"_gClickHandler"));}},this);}}else{dojo.forEach(this._clickHandles,_3);this._clickHandles=[];}_98.showInfoWindowOnClick=_97;},getInfoWindowAnchor:function(pt){var w2=this.width/2,h2=this.height/2,_9b;if(pt.y<h2){_9b="LOWER";}else{_9b="UPPER";}if(pt.x<w2){return esri.dijit.InfoWindow["ANCHOR_"+_9b+"RIGHT"];}else{return esri.dijit.InfoWindow["ANCHOR_"+_9b+"LEFT"];}},toScreen:function(pt,_9c){return _2(this.extent,this.width,this.height,pt,_9c);},toMap:function(pt){return _1(this.extent,this.width,this.height,pt);},addLayer:function(_9d,_9e){return this._addLayer(_9d,_9d instanceof _8?this.graphicsLayerIds:this.layerIds,_9e);},addLayers:function(_9f){var _a0=[],_a1=_9f.length,_a2,i,len=_9f.length;var _a3=function(_a4,_a5){if(dojo.indexOf(_9f,_a4)!==-1){_a1--;_a0.push({"layer":_a4,"success":!_a5,"error":_a5});if(!_a1){dojo.disconnect(_a2);this.onLayersAddResult(_a0);}}};_a2=dojo.connect(this,"onLayerAddResult",_a3);for(i=0;i<len;i++){this.addLayer(_9f[i]);}return this;},removeLayer:function(_a6){var id=_a6.id,ids=_a6 instanceof _8?this.graphicsLayerIds:this.layerIds,i=_4(ids,id);if(i>=0){ids.splice(i,1);if(_a6 instanceof _8){_3(this["_gl_"+_a6.id+"_click_connect"]);_a6._unsetMap(this,this._gc._surface);}else{_a6._unsetMap(this,this._layersDiv);if(_a6.declaredClass.indexOf("VETiledLayer")!==-1){this._onBingLayerRemove(_a6);}}delete this._layers[id];delete this._layerDivs[id];this._reorderLayers(ids);this.onLayerRemove(_a6);}},removeAllLayers:function(){var ids=this.layerIds,i;for(i=ids.length-1;i>=0;i--){this.removeLayer(this._layers[ids[i]]);}ids=this.graphicsLayerIds;for(i=ids.length-1;i>=0;i--){this.removeLayer(this._layers[ids[i]]);}this.onLayersRemoved();},reorderLayer:function(_a7,_a8){if(dojo.isString(_a7)){dojo.deprecated(this.declaredClass+": "+esri.bundle.map.deprecateReorderLayerString,null,"v2.0");_a7=this.getLayer(_a7);}var id=_a7.id,ids=_a7 instanceof _8?this.graphicsLayerIds:this.layerIds;if(_a8<0){_a8=0;}else{if(_a8>=ids.length){_a8=ids.length-1;}}var i=_4(ids,id);if(i===-1||i===_a8){return;}ids.splice(i,1);ids.splice(_a8,0,id);this._reorderLayers(ids);},getLayer:function(id){return this._layers[id];},setExtent:function(_a9,fit){this.__setExtent(new esri.geometry.Extent(_a9.toJson()),null,null,fit);},centerAt:function(_aa){this._panTo(_aa);},centerAndZoom:function(_ab,_ac){var ext=this.__getExtentForLevel(_ac,_ab).extent;if(ext){this.__setExtent(ext);}else{this.centerAt(_ab);}},getNumLevels:function(){return this.__tileInfo?this.__tileInfo.lods.length:0;},getLevel:function(){return this.__LOD?this.__LOD.level:-1;},setLevel:function(_ad){var ext=this.__getExtentForLevel(_ad).extent;if(ext){this.setExtent(ext);}},setTimeExtent:function(_ae){this.timeExtent=_ae;var arg=_ae?new esri.TimeExtent(_ae.startTime,_ae.endTime):null;this.onTimeExtentChange(arg);},setTimeSlider:function(_af){if(this.timeSlider){_3(this._tsTimeExtentChange_connect);this._tsTimeExtentChange_connect=null;this.timeSlider=null;}if(_af){this.timeSlider=_af;this.setTimeExtent(_af.getCurrentTimeExtent());this._tsTimeExtentChange_connect=dc(_af,"onTimeExtentChange",this,"setTimeExtent");}},resize:function(){var w=this.width,h=this.height,r=esri.geometry._extentToRect(this.extent);var _b0=dojo.contentBox(this.container);ds(this.root,{width:(this.width=_b0.w)+"px",height:(this.height=_b0.h)+"px"});var wd=this.width,ht=this.height;this.__visibleRect.update(this.__visibleRect.x,this.__visibleRect.y,wd,ht);this.__visibleDelta.update(this.__visibleDelta.x,this.__visibleDelta.y,wd,ht);var ne=(this.extent=esri.geometry._rectToExtent(new _9(r.x,r.y,r.width*(wd/w),r.height*(ht/h),this.spatialReference)));this.onResize(ne,wd,ht);this.__setExtent(ne);},reposition:function(){this._reposition();this.onReposition(this.position.x,this.position.y);},_reposition:function(){var pos=dojo.coords(this.container,true),_b1=dojo._getPadBorderExtents(this.container);this.position.update(pos.x+_b1.l,pos.y+_b1.t);},_setClipRect:function(){delete this._clip;var _b2=dojo.isIE?"rect(auto,auto,auto,auto)":null;if(this.wrapAround180){var _b3=this.width,_b4=this.height,_b5=this._getFrameWidth(),_b6=_b3-_b5;if(_b6>0){var _b7=_b6/2;_b2="rect(0px,"+(_b7+_b5)+"px,"+_b4+"px,"+_b7+"px)";var _b8=this.extent.getWidth(),_b9=_b8*(_b5/_b3);this._clip=[(_b8-_b9)/2,_b9];}}ds(this.__container,"clip",_b2);},_getAvailExtent:function(){var _ba=this.extent,_bb=this._clip;if(_bb){if(!_ba._clip){var _bc=new esri.geometry._extentToRect(_ba);_bc.width=_bb[1];_bc.x=_bc.x+_bb[0];_ba._clip=_bc.getExtent();}return _ba._clip;}return _ba;},panUp:function(){this._fixedPan(0,this.height*-_d);},panUpperRight:function(){this._fixedPan(this.width*_d,this.height*-_d);},panRight:function(){this._fixedPan(this.width*_d,0);},panLowerRight:function(){this._fixedPan(this.width*_d,this.height*_d);},panDown:function(){this._fixedPan(0,this.height*_d);},panLowerLeft:function(){this._fixedPan(this.width*-_d,this.height*_d);},panLeft:function(){this._fixedPan(this.width*-_d,0);},panUpperLeft:function(){this._fixedPan(this.width*-_d,this.height*-_d);},enableSnapping:function(_bd){if(!_bd){_bd={};}if(_bd.declaredClass==="esri.SnappingManager"){this.snappingManager=_bd;}else{this.snappingManager=new esri.SnappingManager(dojo.mixin({map:this},_bd));}return this.snappingManager;},disableSnapping:function(){if(this.snappingManager){this.snappingManager.destroy();}this.snappingManager=null;}};}()));}