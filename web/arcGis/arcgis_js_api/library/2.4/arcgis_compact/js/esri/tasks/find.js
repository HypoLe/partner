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

if(!dojo._hasResource["esri.tasks.find"]){dojo._hasResource["esri.tasks.find"]=true;dojo.provide("esri.tasks.find");dojo.require("esri.tasks._task");dojo.declare("esri.tasks.FindTask",esri.tasks._Task,{constructor:function(_1){this._url.path+="/find";this._handler=dojo.hitch(this,this._handler);},_handler:function(_2,io,_3,_4,_5){try{var _6=[],_7=esri.tasks.FindResult;dojo.forEach(_2.results,function(_8,i){_6[i]=new _7(_8);});this._successHandler([_6],"onComplete",_3,_5);}catch(err){this._errorHandler(err,_4,_5);}},execute:function(_9,_a,_b){var _c=this._encode(dojo.mixin({},this._url.query,{f:"json"},_9.toJson())),_d=this._handler,_e=this._errorHandler;var _f=new dojo.Deferred(esri._dfdCanceller);_f._pendingDfd=esri.request({url:this._url.path,content:_c,callbackParamName:"callback",load:function(r,i){_d(r,i,_a,_b,_f);},error:function(r){_e(r,_b,_f);}});return _f;},onComplete:function(){}});dojo.declare("esri.tasks.FindParameters",null,{searchText:null,contains:true,searchFields:null,outSpatialReference:null,layerIds:null,returnGeometry:false,layerDefinitions:null,toJson:function(){var _10={searchText:this.searchText,contains:this.contains,returnGeometry:this.returnGeometry,maxAllowableOffset:this.maxAllowableOffset},_11=this.layerIds,_12=this.searchFields,_13=this.outSpatialReference;if(_11){_10.layers=_11.join(",");}if(_12){_10.searchFields=_12.join(",");}if(_13){_10.sr=_13.wkid||dojo.toJson(_13.toJson());}_10.layerDefs=esri._serializeLayerDefinitions(this.layerDefinitions);return _10;}});dojo.declare("esri.tasks.FindResult",null,{constructor:function(_14){dojo.mixin(this,_14);this.feature=new esri.Graphic(_14.geometry?esri.geometry.fromJson(_14.geometry):null,null,_14.attributes);delete this.geometry;delete this.attributes;}});}