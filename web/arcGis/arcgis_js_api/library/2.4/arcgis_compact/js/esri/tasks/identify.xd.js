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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.tasks.identify"],["require","esri.tasks._task"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.tasks.identify"]){_4._hasResource["esri.tasks.identify"]=true;_4.provide("esri.tasks.identify");_4.require("esri.tasks._task");_4.declare("esri.tasks.IdentifyTask",esri.tasks._Task,{constructor:function(_7){this._url.path+="/identify";this._handler=_4.hitch(this,this._handler);},__msigns:[{n:"execute",c:3,a:[{i:0,p:["geometry"]}],e:2}],_handler:function(_8,io,_9,_a,_b){try{var _c=[],_d=esri.tasks.IdentifyResult;_4.forEach(_8.results,function(_e,i){_c[i]=new _d(_e);});this._successHandler([_c],"onComplete",_9,_b);}catch(err){this._errorHandler(err,_a,_b);}},execute:function(_f,_10,_11,_12){var _13=_12.assembly,_14=this._encode(_4.mixin({},this._url.query,{f:"json"},_f.toJson(_13&&_13[0]))),_15=this._handler,_16=this._errorHandler;return esri.request({url:this._url.path,content:_14,callbackParamName:"callback",load:function(r,i){_15(r,i,_10,_11,_12.dfd);},error:function(r){_16(r,_11,_12.dfd);}});},onComplete:function(){}});esri._createWrappers("esri.tasks.IdentifyTask");_4.declare("esri.tasks.IdentifyParameters",null,{constructor:function(){this.layerOption=esri.tasks.IdentifyParameters.LAYER_OPTION_TOP;},geometry:null,spatialReference:null,layerIds:null,tolerance:null,returnGeometry:false,mapExtent:null,width:esri.config.defaults.map.width,height:esri.config.defaults.map.height,dpi:96,layerDefinitions:null,timeExtent:null,layerTimeOptions:null,toJson:function(_17){var g=_17&&_17["geometry"]||this.geometry,ext=this.mapExtent,sr=this.spatialReference,_18=this.layerIds,_19={geometry:g,tolerance:this.tolerance,returnGeometry:this.returnGeometry,mapExtent:ext,imageDisplay:this.width+","+this.height+","+this.dpi,maxAllowableOffset:this.maxAllowableOffset};if(g){_19.geometryType=esri.geometry.getJsonType(g);}if(sr!==null){_19.sr=sr.wkid||_4.toJson(sr.toJson());}else{if(g){_19.sr=g.spatialReference.wkid||_4.toJson(g.spatialReference.toJson());}else{if(ext){_19.sr=ext.spatialReference.wkid||_4.toJson(ext.spatialReference.toJson());}}}_19.layers=this.layerOption;if(_18){_19.layers+=":"+_18.join(",");}_19.layerDefs=esri._serializeLayerDefinitions(this.layerDefinitions);var _1a=this.timeExtent;_19.time=_1a?_1a.toJson().join(","):null;_19.layerTimeOptions=esri._serializeTimeOptions(this.layerTimeOptions);return _19;}});_4.mixin(esri.tasks.IdentifyParameters,{LAYER_OPTION_TOP:"top",LAYER_OPTION_VISIBLE:"visible",LAYER_OPTION_ALL:"all"});_4.declare("esri.tasks.IdentifyResult",null,{constructor:function(_1b){_4.mixin(this,_1b);this.feature=new esri.Graphic(_1b.geometry?esri.geometry.fromJson(_1b.geometry):null,null,_1b.attributes);delete this.geometry;delete this.attributes;}});}}};});