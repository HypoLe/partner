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

if(!dojo._hasResource["esri.InfoWindowBase"]){dojo._hasResource["esri.InfoWindowBase"]=true;dojo.provide("esri.InfoWindowBase");dojo.require("dijit._base.manager");dojo.declare("esri.InfoWindowBase",null,{constructor:function(){var _1=dojo.hitch;this.__set_title=_1(this,this.__set_title);this.__err_title=_1(this,this.__err_title);this.__set_content=_1(this,this.__set_content);this.__err_content=_1(this,this.__err_content);},setMap:function(_2){this.map=_2;},unsetMap:function(_3){delete this.map;},setTitle:function(){},setContent:function(){},show:function(){},hide:function(){},resize:function(){},onShow:function(){},onHide:function(){},place:function(_4,_5){if(esri._isDefined(_4)){if(dojo.isObject(_4)){dojo.place(_4,_5,"only");}else{_5.innerHTML=_4;}}else{_5.innerHTML="";}},startupDijits:function(_6){this._processDijits(_6);},destroyDijits:function(_7){this._processDijits(_7,true);},_processDijits:function(_8,_9){if(_8&&_8.children.length===1){var _a=_8.children[0];if(_a){var _b=dijit.byNode(_a);var _c=_b?[_b]:dijit.findWidgets(_a);dojo.forEach(_c,function(_d){if(_9){if(_d._started&&!_d._destroyed){try{if(_d.destroyRecursive){_d.destroyRecursive();}else{if(_d.destroy){_d.destroy();}}}catch(ex){console.debug("An error occurred when destroying a widget embedded within InfoWindow: "+ex.message);}}}else{if(!_d._started){try{_d.startup();}catch(ex2){console.debug("An error occurred when starting a widget embedded within InfoWindow: "+ex2.message);}}}});}}},__registerMapListeners:function(){this.__unregisterMapListeners();var _e=this.map;this.__handles=[dojo.connect(_e,"onPan",this,this.__onMapPan),dojo.connect(_e,"onZoomStart",this,this.__onMapZmStart),dojo.connect(_e,"onExtentChange",this,this.__onMapExtChg)];},__unregisterMapListeners:function(){var _f=this.__handles;if(_f){dojo.forEach(_f,dojo.disconnect,dojo);this.__handles=null;}},__onMapPan:function(_10,_11){this.move(_11,true);},__onMapZmStart:function(){this.__mcoords=this.mapCoords||this.map.toMap(new esri.geometry.Point(this.coords));this.hide(null,true);},__onMapExtChg:function(_12,_13,_14){var map=this.map,_15=this.mapCoords;if(_15){this.show(_15,null,true);}else{var _16;if(_14){_16=map.toScreen(this.__mcoords);}else{_16=this.coords.offset(_13.x,_13.y);}this.show(_16,null,true);}},__setValue:function(_17,_18){this[_17].innerHTML="";var dfd="_dfd"+_17,_19=this[dfd];if(_19&&_19.fired===-1){_19.cancel();this[dfd]=null;}if(esri._isDefined(_18)){if(_18 instanceof dojo.Deferred){this[dfd]=_18;_18.addCallbacks(this["__set"+_17],this["__err"+_17]);}else{this.__render(_17,_18);}}},__set_title:function(_1a){this._dfd_title=null;this.__render("_title",_1a);},__err_title:function(_1b){this._dfd_title=null;},__set_content:function(_1c){this._dfd_content=null;this.__render("_content",_1c);},__err_content:function(_1d){this._dfd_content=null;},__render:function(_1e,_1f){var _20=this[_1e];this.place(_1f,_20);if(this.isShowing){this.startupDijits(_20);if(_1e==="_title"&&this._adjustContentArea){this._adjustContentArea();}}}});}