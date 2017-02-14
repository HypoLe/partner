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

if(!dojo._hasResource["esri.tasks.locator"]){dojo._hasResource["esri.tasks.locator"]=true;dojo.provide("esri.tasks.locator");dojo.require("esri.tasks._task");dojo.declare("esri.tasks.Locator",esri.tasks._Task,{constructor:function(_1){this._geocodeHandler=dojo.hitch(this,this._geocodeHandler);this._reverseGeocodeHandler=dojo.hitch(this,this._reverseGeocodeHandler);},outSpatialReference:null,setOutSpatialReference:function(sr){this.outSpatialReference=sr;},_geocodeHandler:function(_2,io,_3,_4,_5){try{var _6=_2.candidates,_7,_8=[],i,il=_6.length,sr=_2.spatialReference;for(i=0;i<il;i++){_7=_6[i];_8[i]=new esri.tasks.AddressCandidate(_7);var _9=_8[i].location;if(sr&&_9&&!_9.spatialReference){_9.setSpatialReference(new esri.SpatialReference(sr));}}this._successHandler([_8],"onAddressToLocationsComplete",_3,_5);}catch(err){this._errorHandler(err,_4,_5);}},addressToLocations:function(_a,_b,_c,_d){var _e=this.outSpatialReference;var _f=this._encode(dojo.mixin({},this._url.query,_a,{f:"json",outSR:_e&&dojo.toJson(_e.toJson()),outFields:(_b&&_b.join(","))||null})),_10=this._geocodeHandler,_11=this._errorHandler;var dfd=new dojo.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path+"/findAddressCandidates",content:_f,callbackParamName:"callback",load:function(r,i){_10(r,i,_c,_d,dfd);},error:function(r){_11(r,_d,dfd);}});return dfd;},_reverseGeocodeHandler:function(_12,io,_13,_14,dfd){try{var _15=new esri.tasks.AddressCandidate({address:_12.address,location:_12.location,score:100});this._successHandler([_15],"onLocationToAddressComplete",_13,dfd);}catch(err){this._errorHandler(err,_14,dfd);}},locationToAddress:function(_16,_17,_18,_19){if(_16&&this.normalization){_16=_16.normalize();}var _1a=this.outSpatialReference;var _1b=this._encode(dojo.mixin({},this._url.query,{outSR:_1a&&dojo.toJson(_1a.toJson()),location:_16&&dojo.toJson(_16.toJson()),distance:_17,f:"json"})),_1c=this._reverseGeocodeHandler,_1d=this._errorHandler;var dfd=new dojo.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path+"/reverseGeocode",content:_1b,callbackParamName:"callback",load:function(r,i){_1c(r,i,_18,_19,dfd);},error:function(r){_1d(r,_19,dfd);}});return dfd;},onAddressToLocationsComplete:function(){},onLocationToAddressComplete:function(){}});dojo.declare("esri.tasks.AddressCandidate",null,{constructor:function(_1e){dojo.mixin(this,_1e);this.location=new esri.geometry.Point(this.location);}});}