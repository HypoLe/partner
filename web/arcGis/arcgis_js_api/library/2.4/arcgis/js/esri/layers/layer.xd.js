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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.layers.layer"],["require","esri.utils"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.layers.layer"]){_4._hasResource["esri.layers.layer"]=true;_4.provide("esri.layers.layer");_4.require("esri.utils");_4.declare("esri.layers.Layer",null,{constructor:function(_7,_8){if(_7&&_4.isString(_7)){this._url=esri.urlToObject(this.url=_7);}else{this.url=(this._url=null);_8=_8||_7;if(_8&&_8.layerDefinition){_8=null;}}this._map=this._div=null;this.normalization=true;if(_8){if(_8.id){this.id=_8.id;}if(_8.visible===false){this.visible=false;}if(_8.opacity!==undefined){this.opacity=_8.opacity;}}this._errorHandler=_4.hitch(this,this._errorHandler);},id:null,visible:true,loaded:false,_errorHandler:function(_9){this.onError(_9);},_setMap:function(_a,_b,_c,_d){},_unsetMap:function(_e,_f){},_cleanUp:function(){this._map=this._div=null;},_fireUpdateStart:function(){if(this.updating){return;}this.updating=true;this.onUpdateStart();if(this._map){this._map._incr();}},_fireUpdateEnd:function(_10){this.updating=false;this.onUpdateEnd(_10);if(this._map){this._map._decr();}},_getToken:function(){var url=this._url;return (url&&url.query&&url.query.token);},refresh:function(){},show:function(){this.setVisibility(true);},hide:function(){this.setVisibility(false);},getResourceInfo:function(){var _11=this.resourceInfo;return _4.isString(_11)?_4.fromJson(_11):_4.clone(_11);},setNormalization:function(_12){this.normalization=_12;},setVisibility:function(v){if(this.visible!==v){this.visible=v;this.onVisibilityChange(this.visible);}},onLoad:function(){},onVisibilityChange:function(){},onUpdate:function(){},onUpdateStart:function(){},onUpdateEnd:function(){},onError:function(){}});}}};});