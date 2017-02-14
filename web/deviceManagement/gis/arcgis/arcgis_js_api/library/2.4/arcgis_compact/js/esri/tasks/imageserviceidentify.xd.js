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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.tasks.imageserviceidentify"],["require","esri.tasks._task"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.tasks.imageserviceidentify"]){_4._hasResource["esri.tasks.imageserviceidentify"]=true;_4.provide("esri.tasks.imageserviceidentify");_4.require("esri.tasks._task");_4.declare("esri.tasks.ImageServiceIdentifyTask",esri.tasks._Task,{constructor:function(_7){this._url.path+="/identify";this._handler=_4.hitch(this,this._handler);},__msigns:[{n:"execute",c:3,a:[{i:0,p:["geometry"]}],e:2}],_handler:function(_8,io,_9,_a,_b){try{var _c=new esri.tasks.ImageServiceIdentifyResult(_8);this._successHandler([_c],"onComplete",_9,_b);}catch(err){this._errorHandler(err,_a,_b);}},execute:function(_d,_e,_f,_10){var _11=_10.assembly,_12=this._encode(_4.mixin({},this._url.query,{f:"json"},_d.toJson(_11&&_11[0]))),_13=this._handler,_14=this._errorHandler;return esri.request({url:this._url.path,content:_12,callbackParamName:"callback",load:function(r,i){_13(r,i,_e,_f,_10.dfd);},error:function(r){_14(r,_f,_10.dfd);}});},onComplete:function(){}});esri._createWrappers("esri.tasks.ImageServiceIdentifyTask");_4.declare("esri.tasks.ImageServiceIdentifyParameters",null,{geometry:null,mosaicRule:null,pixelSizeX:null,pixelSizeY:null,toJson:function(_15){var g=_15&&_15["geometry"]||this.geometry,_16={geometry:g,mosaicRule:this.mosaicRule?_4.toJson(this.mosaicRule.toJson()):null};if(g){_16.geometryType=esri.geometry.getJsonType(g);}if(esri._isDefined(this.pixelSizeX)&&esri._isDefined(this.pixelSizeY)){_16.pixelSize=_4.toJson({x:parseFloat(this.pixelSizeX),y:parseFloat(this.pixelSizeY)});}return _16;}});_4.declare("esri.tasks.ImageServiceIdentifyResult",null,{constructor:function(_17){if(_17.catalogItems){this.catalogItems=new esri.tasks.FeatureSet(_17.catalogItems);}if(_17.location){this.location=esri.geometry.fromJson(_17.location);}this.catalogItemVisibilities=_17.catalogItemVisibilities;this.name=_17.name;this.objectId=_17.objectId;this.value=_17.value;this.properties=_17.properties;}});}}};});