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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.tasks.find"],["require","esri.tasks._task"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.tasks.find"]){_4._hasResource["esri.tasks.find"]=true;_4.provide("esri.tasks.find");_4.require("esri.tasks._task");_4.declare("esri.tasks.FindTask",esri.tasks._Task,{constructor:function(_7){this._url.path+="/find";this._handler=_4.hitch(this,this._handler);},_handler:function(_8,io,_9,_a,_b){try{var _c=[],_d=esri.tasks.FindResult;_4.forEach(_8.results,function(_e,i){_c[i]=new _d(_e);});this._successHandler([_c],"onComplete",_9,_b);}catch(err){this._errorHandler(err,_a,_b);}},execute:function(_f,_10,_11){var _12=this._encode(_4.mixin({},this._url.query,{f:"json"},_f.toJson())),_13=this._handler,_14=this._errorHandler;var dfd=new _4.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path,content:_12,callbackParamName:"callback",load:function(r,i){_13(r,i,_10,_11,dfd);},error:function(r){_14(r,_11,dfd);}});return dfd;},onComplete:function(){}});_4.declare("esri.tasks.FindParameters",null,{searchText:null,contains:true,searchFields:null,outSpatialReference:null,layerIds:null,returnGeometry:false,layerDefinitions:null,toJson:function(){var _15={searchText:this.searchText,contains:this.contains,returnGeometry:this.returnGeometry,maxAllowableOffset:this.maxAllowableOffset},_16=this.layerIds,_17=this.searchFields,_18=this.outSpatialReference;if(_16){_15.layers=_16.join(",");}if(_17){_15.searchFields=_17.join(",");}if(_18){_15.sr=_18.wkid||_4.toJson(_18.toJson());}_15.layerDefs=esri._serializeLayerDefinitions(this.layerDefinitions);return _15;}});_4.declare("esri.tasks.FindResult",null,{constructor:function(_19){_4.mixin(this,_19);this.feature=new esri.Graphic(_19.geometry?esri.geometry.fromJson(_19.geometry):null,null,_19.attributes);delete this.geometry;delete this.attributes;}});}}};});