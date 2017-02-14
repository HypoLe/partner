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

if(!dojo._hasResource["esri.tasks.imageserviceidentify"]){dojo._hasResource["esri.tasks.imageserviceidentify"]=true;dojo.provide("esri.tasks.imageserviceidentify");dojo.require("esri.tasks._task");dojo.declare("esri.tasks.ImageServiceIdentifyTask",esri.tasks._Task,{constructor:function(_1){this._url.path+="/identify";this._handler=dojo.hitch(this,this._handler);},__msigns:[{n:"execute",c:3,a:[{i:0,p:["geometry"]}],e:2}],_handler:function(_2,io,_3,_4,_5){try{var _6=new esri.tasks.ImageServiceIdentifyResult(_2);this._successHandler([_6],"onComplete",_3,_5);}catch(err){this._errorHandler(err,_4,_5);}},execute:function(_7,_8,_9,_a){var _b=_a.assembly,_c=this._encode(dojo.mixin({},this._url.query,{f:"json"},_7.toJson(_b&&_b[0]))),_d=this._handler,_e=this._errorHandler;return esri.request({url:this._url.path,content:_c,callbackParamName:"callback",load:function(r,i){_d(r,i,_8,_9,_a.dfd);},error:function(r){_e(r,_9,_a.dfd);}});},onComplete:function(){}});esri._createWrappers("esri.tasks.ImageServiceIdentifyTask");dojo.declare("esri.tasks.ImageServiceIdentifyParameters",null,{geometry:null,mosaicRule:null,pixelSizeX:null,pixelSizeY:null,toJson:function(_f){var g=_f&&_f["geometry"]||this.geometry,_10={geometry:g,mosaicRule:this.mosaicRule?dojo.toJson(this.mosaicRule.toJson()):null};if(g){_10.geometryType=esri.geometry.getJsonType(g);}if(esri._isDefined(this.pixelSizeX)&&esri._isDefined(this.pixelSizeY)){_10.pixelSize=dojo.toJson({x:parseFloat(this.pixelSizeX),y:parseFloat(this.pixelSizeY)});}return _10;}});dojo.declare("esri.tasks.ImageServiceIdentifyResult",null,{constructor:function(_11){if(_11.catalogItems){this.catalogItems=new esri.tasks.FeatureSet(_11.catalogItems);}if(_11.location){this.location=esri.geometry.fromJson(_11.location);}this.catalogItemVisibilities=_11.catalogItemVisibilities;this.name=_11.name;this.objectId=_11.objectId;this.value=_11.value;this.properties=_11.properties;}});}