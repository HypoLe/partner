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

if(!dojo._hasResource["esri.tasks.identify"]){dojo._hasResource["esri.tasks.identify"]=true;dojo.provide("esri.tasks.identify");dojo.require("esri.tasks._task");dojo.declare("esri.tasks.IdentifyTask",esri.tasks._Task,{constructor:function(_1){this._url.path+="/identify";this._handler=dojo.hitch(this,this._handler);},__msigns:[{n:"execute",c:3,a:[{i:0,p:["geometry"]}],e:2}],_handler:function(_2,io,_3,_4,_5){try{var _6=[],_7=esri.tasks.IdentifyResult;dojo.forEach(_2.results,function(_8,i){_6[i]=new _7(_8);});this._successHandler([_6],"onComplete",_3,_5);}catch(err){this._errorHandler(err,_4,_5);}},execute:function(_9,_a,_b,_c){var _d=_c.assembly,_e=this._encode(dojo.mixin({},this._url.query,{f:"json"},_9.toJson(_d&&_d[0]))),_f=this._handler,_10=this._errorHandler;return esri.request({url:this._url.path,content:_e,callbackParamName:"callback",load:function(r,i){_f(r,i,_a,_b,_c.dfd);},error:function(r){_10(r,_b,_c.dfd);}});},onComplete:function(){}});esri._createWrappers("esri.tasks.IdentifyTask");dojo.declare("esri.tasks.IdentifyParameters",null,{constructor:function(){this.layerOption=esri.tasks.IdentifyParameters.LAYER_OPTION_TOP;},geometry:null,spatialReference:null,layerIds:null,tolerance:null,returnGeometry:false,mapExtent:null,width:esri.config.defaults.map.width,height:esri.config.defaults.map.height,dpi:96,layerDefinitions:null,timeExtent:null,layerTimeOptions:null,toJson:function(_11){var g=_11&&_11["geometry"]||this.geometry,ext=this.mapExtent,sr=this.spatialReference,_12=this.layerIds,_13={geometry:g,tolerance:this.tolerance,returnGeometry:this.returnGeometry,mapExtent:ext,imageDisplay:this.width+","+this.height+","+this.dpi,maxAllowableOffset:this.maxAllowableOffset};if(g){_13.geometryType=esri.geometry.getJsonType(g);}if(sr!==null){_13.sr=sr.wkid||dojo.toJson(sr.toJson());}else{if(g){_13.sr=g.spatialReference.wkid||dojo.toJson(g.spatialReference.toJson());}else{if(ext){_13.sr=ext.spatialReference.wkid||dojo.toJson(ext.spatialReference.toJson());}}}_13.layers=this.layerOption;if(_12){_13.layers+=":"+_12.join(",");}_13.layerDefs=esri._serializeLayerDefinitions(this.layerDefinitions);var _14=this.timeExtent;_13.time=_14?_14.toJson().join(","):null;_13.layerTimeOptions=esri._serializeTimeOptions(this.layerTimeOptions);return _13;}});dojo.mixin(esri.tasks.IdentifyParameters,{LAYER_OPTION_TOP:"top",LAYER_OPTION_VISIBLE:"visible",LAYER_OPTION_ALL:"all"});dojo.declare("esri.tasks.IdentifyResult",null,{constructor:function(_15){dojo.mixin(this,_15);this.feature=new esri.Graphic(_15.geometry?esri.geometry.fromJson(_15.geometry):null,null,_15.attributes);delete this.geometry;delete this.attributes;}});}