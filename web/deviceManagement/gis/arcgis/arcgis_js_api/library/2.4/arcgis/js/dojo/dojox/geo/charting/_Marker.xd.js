/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.geo.charting._Marker"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.geo.charting._Marker"]){_4._hasResource["dojox.geo.charting._Marker"]=true;_4.provide("dojox.geo.charting._Marker");_4.declare("dojox.geo.charting._Marker",null,{constructor:function(_7,_8){var _9=_8.mapObj;this.features=_9.features;this.markerData=_7;},show:function(_a){this.markerText=this.features[_a].markerText||this.markerData[_a]||_a;this.currentFeature=this.features[_a];_6.geo.charting.showTooltip(this.markerText,this.currentFeature.shape,"before");},hide:function(){_6.geo.charting.hideTooltip(this.currentFeature.shape);},_getGroupBoundingBox:function(_b){var _c=_b.children;var _d=_c[0];var _e=_d.getBoundingBox();this._arround=_4.clone(_e);_4.forEach(_c,function(_f){var _10=_f.getBoundingBox();this._arround.x=Math.min(this._arround.x,_10.x);this._arround.y=Math.min(this._arround.y,_10.y);},this);},_toWindowCoords:function(_11,_12,_13){var _14=(_11.x-this.topLeft[0])*this.scale;var _15=(_11.y-this.topLeft[1])*this.scale;if(_4.isFF==3.5){_11.x=_12.x;_11.y=_12.y;}else{if(_4.isChrome){_11.x=_13.x+_14;_11.y=_13.y+_15;}else{_11.x=_12.x+_14;_11.y=_12.y+_15;}}_11.width=(this.currentFeature._bbox[2])*this.scale;_11.height=(this.currentFeature._bbox[3])*this.scale;_11.x+=_11.width/6;_11.y+=_11.height/4;}});}}};});