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

if(!dojo._hasResource["esri.tasks._task"]){dojo._hasResource["esri.tasks._task"]=true;dojo.provide("esri.tasks._task");dojo.require("esri.graphic");dojo.require("esri.utils");dojo.declare("esri.tasks._Task",null,{constructor:function(_1){if(_1&&dojo.isString(_1)){this._url=esri.urlToObject(this.url=_1);}this.normalization=true;this._errorHandler=dojo.hitch(this,this._errorHandler);},_encode:function(_2,_3,_4){var _5,_6,_7={};for(var i in _2){if(i==="declaredClass"){continue;}_5=_2[i];_6=typeof (_5);if(_5!==null&&_5!==undefined&&_6!=="function"){if(dojo.isArray(_5)){_7[i]=[];for(var p=0,pl=_5.length;p<pl;p++){_7[i][p]=this._encode(_5[p]);}}else{if(_6==="object"){if(_5.toJson){var _8=_5.toJson(_4&&_4[i]);if(_5 instanceof esri.tasks.FeatureSet){if(_8.spatialReference){_8.sr=_8.spatialReference;delete _8.spatialReference;}}_7[i]=_3?_8:dojo.toJson(_8);}}else{_7[i]=_5;}}}}return _7;},_successHandler:function(_9,_a,_b,_c){if(_a){this[_a].apply(this,_9);}if(_b){_b.apply(null,_9);}if(_c){esri._resDfd(_c,_9);}},_errorHandler:function(_d,_e,_f){this.onError(_d);if(_e){_e(_d);}if(_f){_f.errback(_d);}},setNormalization:function(_10){this.normalization=_10;},onError:function(){}});dojo.declare("esri.tasks.FeatureSet",null,{constructor:function(_11){if(_11){dojo.mixin(this,_11);var _12=this.features,sr=_11.spatialReference,_13=esri.Graphic,_14=esri.geometry.getGeometryType(_11.geometryType);sr=(this.spatialReference=new esri.SpatialReference(sr));this.geometryType=_11.geometryType;if(_11.fields){this.fields=_11.fields;}dojo.forEach(_12,function(_15,i){var _16=_15.geometry&&_15.geometry.spatialReference;_12[i]=new _13(_14?new _14(_15.geometry):null,_15.symbol&&esri.symbol.fromJson(_15.symbol),_15.attributes);if(_12[i].geometry&&!_16){_12[i].geometry.setSpatialReference(sr);}});}else{this.features=[];}},displayFieldName:null,geometryType:null,spatialReference:null,fields:[],features:[],fieldAliases:null,toJson:function(_17){var _18={};if(this.displayFieldName){_18.displayFieldName=this.displayFieldName;}if(this.fields){_18.fields=this.fields;}if(this.spatialReference){_18.spatialReference=this.spatialReference.toJson();}else{if(this.features[0].geometry){_18.spatialReference=this.features[0].geometry.spatialReference.toJson();}}if(this.features[0]){if(this.features[0].geometry){_18.geometryType=esri.geometry.getJsonType(this.features[0].geometry);}_18.features=esri._encodeGraphics(this.features,_17);}return _18;}});esri.tasks._SpatialRelationship={SPATIAL_REL_INTERSECTS:"esriSpatialRelIntersects",SPATIAL_REL_CONTAINS:"esriSpatialRelContains",SPATIAL_REL_CROSSES:"esriSpatialRelCrosses",SPATIAL_REL_ENVELOPEINTERSECTS:"esriSpatialRelEnvelopeIntersects",SPATIAL_REL_INDEXINTERSECTS:"esriSpatialRelIndexIntersects",SPATIAL_REL_OVERLAPS:"esriSpatialRelOverlaps",SPATIAL_REL_TOUCHES:"esriSpatialRelTouches",SPATIAL_REL_WITHIN:"esriSpatialRelWithin",SPATIAL_REL_RELATION:"esriSpatialRelRelation"};}