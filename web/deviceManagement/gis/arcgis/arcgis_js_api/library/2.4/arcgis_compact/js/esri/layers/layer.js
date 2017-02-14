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

if(!dojo._hasResource["esri.layers.layer"]){dojo._hasResource["esri.layers.layer"]=true;dojo.provide("esri.layers.layer");dojo.require("esri.utils");dojo.declare("esri.layers.Layer",null,{constructor:function(_1,_2){if(_1&&dojo.isString(_1)){this._url=esri.urlToObject(this.url=_1);}else{this.url=(this._url=null);_2=_2||_1;if(_2&&_2.layerDefinition){_2=null;}}this._map=this._div=null;this.normalization=true;if(_2){if(_2.id){this.id=_2.id;}if(_2.visible===false){this.visible=false;}if(_2.opacity!==undefined){this.opacity=_2.opacity;}}this._errorHandler=dojo.hitch(this,this._errorHandler);},id:null,visible:true,loaded:false,_errorHandler:function(_3){this.onError(_3);},_setMap:function(_4,_5,_6,_7){},_unsetMap:function(_8,_9){},_cleanUp:function(){this._map=this._div=null;},_fireUpdateStart:function(){if(this.updating){return;}this.updating=true;this.onUpdateStart();if(this._map){this._map._incr();}},_fireUpdateEnd:function(_a){this.updating=false;this.onUpdateEnd(_a);if(this._map){this._map._decr();}},_getToken:function(){var _b=this._url;return (_b&&_b.query&&_b.query.token);},refresh:function(){},show:function(){this.setVisibility(true);},hide:function(){this.setVisibility(false);},getResourceInfo:function(){var _c=this.resourceInfo;return dojo.isString(_c)?dojo.fromJson(_c):dojo.clone(_c);},setNormalization:function(_d){this.normalization=_d;},setVisibility:function(v){if(this.visible!==v){this.visible=v;this.onVisibilityChange(this.visible);}},onLoad:function(){},onVisibilityChange:function(){},onUpdate:function(){},onUpdateStart:function(){},onUpdateEnd:function(){},onError:function(){}});}