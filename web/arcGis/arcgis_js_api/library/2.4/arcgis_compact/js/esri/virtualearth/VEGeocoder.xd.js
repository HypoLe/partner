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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.virtualearth.VEGeocoder"],["require","esri.tasks._task"],["require","esri.geometry"],["require","esri.utils"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.virtualearth.VEGeocoder"]){_4._hasResource["esri.virtualearth.VEGeocoder"]=true;_4.provide("esri.virtualearth.VEGeocoder");_4.require("esri.tasks._task");_4.require("esri.geometry");_4.require("esri.utils");_4.declare("esri.virtualearth.VEGeocoder",esri.tasks._Task,{constructor:function(_7){try{_7=_4.mixin({bingMapsKey:null},_7||{});this.url="http://serverapi.arcgisonline.com/veadaptor/production/services/geocode/geocode";this._url=esri.urlToObject(this.url);this._queue=[];this.bingMapsKey=_7.bingMapsKey;this.culture=_7.culture||"en-US";this._errorHandler=_4.hitch(this,this._errorHandler);this._addressToLocationsHandler=_4.hitch(this,this._addressToLocationsHandler);if(!this.bingMapsKey){throw new Error(esri.bundle.virtualearth.vegeocode.bingMapsKeyNotSpecified);}}catch(e){this.onError(e);throw e;}},addressToLocations:function(_8,_9,_a){if(!this.bingMapsKey){console.debug(esri.bundle.virtualearth.vegeocode.requestQueued);this._queue.push(arguments);return;}var _b=_4.mixin({},this._url.query,{query:_8,token:this.bingMapsKey,culture:this.culture}),_c=this._addressToLocationsHandler,_d=this._errorHandler;var _e=new _4.Deferred(esri._dfdCanceller);_e._pendingDfd=esri.request({url:this._url.path,content:_b,callbackParamName:"callback",load:function(r,i){_c(r,i,_9,_a,_e);},error:function(r){_d(r,_a,_e);}});return _e;},_addressToLocationsHandler:function(_f,io,_10,_11,dfd){try{_4.forEach(_f,function(_12,i){_f[i]=new esri.virtualearth.VEGeocodeResult(_12);});this._successHandler([_f],"onAddressToLocationsComplete",_10,dfd);}catch(err){this._errorHandler(err,_11,dfd);}},onAddressToLocationsComplete:function(){},setBingMapsKey:function(_13){this.bingMapsKey=_13;},setCulture:function(_14){this.culture=_14;}});_4.declare("esri.virtualearth.VEAddress",null,{constructor:function(_15){_4.mixin(this,{addressLine:null,adminDistrict:null,countryRegion:null,district:null,formattedAddress:null,locality:null,postalCode:null,postalTown:null},_15);}});_4.declare("esri.virtualearth.VEGeocodeResult",null,{constructor:function(_16){_4.mixin(this,{address:null,bestView:null,calculationMethod:null,confidence:null,displayName:null,entityType:null,location:null,matchCodes:null},_16);if(this.address){this.address=new esri.virtualearth.VEAddress(this.address);}if(this.bestView){this.bestView=new esri.geometry.Extent(this.bestView);}if(this.locationArray){this.calculationMethod=this.locationArray[0].calculationMethod;this.location=new esri.geometry.Point(this.locationArray[0]);}}});}}};});