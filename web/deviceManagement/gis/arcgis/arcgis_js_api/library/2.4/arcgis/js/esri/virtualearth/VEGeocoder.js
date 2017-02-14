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

if(!dojo._hasResource["esri.virtualearth.VEGeocoder"]){dojo._hasResource["esri.virtualearth.VEGeocoder"]=true;dojo.provide("esri.virtualearth.VEGeocoder");dojo.require("esri.tasks._task");dojo.require("esri.geometry");dojo.require("esri.utils");dojo.declare("esri.virtualearth.VEGeocoder",esri.tasks._Task,{constructor:function(_1){try{_1=dojo.mixin({bingMapsKey:null},_1||{});this.url="http://serverapi.arcgisonline.com/veadaptor/production/services/geocode/geocode";this._url=esri.urlToObject(this.url);this._queue=[];this.bingMapsKey=_1.bingMapsKey;this.culture=_1.culture||"en-US";this._errorHandler=dojo.hitch(this,this._errorHandler);this._addressToLocationsHandler=dojo.hitch(this,this._addressToLocationsHandler);if(!this.bingMapsKey){throw new Error(esri.bundle.virtualearth.vegeocode.bingMapsKeyNotSpecified);}}catch(e){this.onError(e);throw e;}},addressToLocations:function(_2,_3,_4){if(!this.bingMapsKey){console.debug(esri.bundle.virtualearth.vegeocode.requestQueued);this._queue.push(arguments);return;}var _5=dojo.mixin({},this._url.query,{query:_2,token:this.bingMapsKey,culture:this.culture}),_6=this._addressToLocationsHandler,_7=this._errorHandler;var _8=new dojo.Deferred(esri._dfdCanceller);_8._pendingDfd=esri.request({url:this._url.path,content:_5,callbackParamName:"callback",load:function(r,i){_6(r,i,_3,_4,_8);},error:function(r){_7(r,_4,_8);}});return _8;},_addressToLocationsHandler:function(_9,io,_a,_b,_c){try{dojo.forEach(_9,function(_d,i){_9[i]=new esri.virtualearth.VEGeocodeResult(_d);});this._successHandler([_9],"onAddressToLocationsComplete",_a,_c);}catch(err){this._errorHandler(err,_b,_c);}},onAddressToLocationsComplete:function(){},setBingMapsKey:function(_e){this.bingMapsKey=_e;},setCulture:function(_f){this.culture=_f;}});dojo.declare("esri.virtualearth.VEAddress",null,{constructor:function(_10){dojo.mixin(this,{addressLine:null,adminDistrict:null,countryRegion:null,district:null,formattedAddress:null,locality:null,postalCode:null,postalTown:null},_10);}});dojo.declare("esri.virtualearth.VEGeocodeResult",null,{constructor:function(_11){dojo.mixin(this,{address:null,bestView:null,calculationMethod:null,confidence:null,displayName:null,entityType:null,location:null,matchCodes:null},_11);if(this.address){this.address=new esri.virtualearth.VEAddress(this.address);}if(this.bestView){this.bestView=new esri.geometry.Extent(this.bestView);}if(this.locationArray){this.calculationMethod=this.locationArray[0].calculationMethod;this.location=new esri.geometry.Point(this.locationArray[0]);}}});}