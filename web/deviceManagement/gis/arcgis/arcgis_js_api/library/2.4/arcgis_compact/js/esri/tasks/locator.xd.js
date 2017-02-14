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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.tasks.locator"],["require","esri.tasks._task"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.tasks.locator"]){_4._hasResource["esri.tasks.locator"]=true;_4.provide("esri.tasks.locator");_4.require("esri.tasks._task");_4.declare("esri.tasks.Locator",esri.tasks._Task,{constructor:function(_7){this._geocodeHandler=_4.hitch(this,this._geocodeHandler);this._reverseGeocodeHandler=_4.hitch(this,this._reverseGeocodeHandler);},outSpatialReference:null,setOutSpatialReference:function(sr){this.outSpatialReference=sr;},_geocodeHandler:function(_8,io,_9,_a,_b){try{var _c=_8.candidates,_d,_e=[],i,il=_c.length,sr=_8.spatialReference;for(i=0;i<il;i++){_d=_c[i];_e[i]=new esri.tasks.AddressCandidate(_d);var _f=_e[i].location;if(sr&&_f&&!_f.spatialReference){_f.setSpatialReference(new esri.SpatialReference(sr));}}this._successHandler([_e],"onAddressToLocationsComplete",_9,_b);}catch(err){this._errorHandler(err,_a,_b);}},addressToLocations:function(_10,_11,_12,_13){var _14=this.outSpatialReference;var _15=this._encode(_4.mixin({},this._url.query,_10,{f:"json",outSR:_14&&_4.toJson(_14.toJson()),outFields:(_11&&_11.join(","))||null})),_16=this._geocodeHandler,_17=this._errorHandler;var dfd=new _4.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path+"/findAddressCandidates",content:_15,callbackParamName:"callback",load:function(r,i){_16(r,i,_12,_13,dfd);},error:function(r){_17(r,_13,dfd);}});return dfd;},_reverseGeocodeHandler:function(_18,io,_19,_1a,dfd){try{var _1b=new esri.tasks.AddressCandidate({address:_18.address,location:_18.location,score:100});this._successHandler([_1b],"onLocationToAddressComplete",_19,dfd);}catch(err){this._errorHandler(err,_1a,dfd);}},locationToAddress:function(_1c,_1d,_1e,_1f){if(_1c&&this.normalization){_1c=_1c.normalize();}var _20=this.outSpatialReference;var _21=this._encode(_4.mixin({},this._url.query,{outSR:_20&&_4.toJson(_20.toJson()),location:_1c&&_4.toJson(_1c.toJson()),distance:_1d,f:"json"})),_22=this._reverseGeocodeHandler,_23=this._errorHandler;var dfd=new _4.Deferred(esri._dfdCanceller);dfd._pendingDfd=esri.request({url:this._url.path+"/reverseGeocode",content:_21,callbackParamName:"callback",load:function(r,i){_22(r,i,_1e,_1f,dfd);},error:function(r){_23(r,_1f,dfd);}});return dfd;},onAddressToLocationsComplete:function(){},onLocationToAddressComplete:function(){}});_4.declare("esri.tasks.AddressCandidate",null,{constructor:function(_24){_4.mixin(this,_24);this.location=new esri.geometry.Point(this.location);}});}}};});