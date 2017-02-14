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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.tasks._task"],["require","esri.graphic"],["require","esri.utils"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.tasks._task"]){_4._hasResource["esri.tasks._task"]=true;_4.provide("esri.tasks._task");_4.require("esri.graphic");_4.require("esri.utils");_4.declare("esri.tasks._Task",null,{constructor:function(_7){if(_7&&_4.isString(_7)){this._url=esri.urlToObject(this.url=_7);}this.normalization=true;this._errorHandler=_4.hitch(this,this._errorHandler);},_encode:function(_8,_9,_a){var _b,_c,_d={};for(var i in _8){if(i==="declaredClass"){continue;}_b=_8[i];_c=typeof (_b);if(_b!==null&&_b!==undefined&&_c!=="function"){if(_4.isArray(_b)){_d[i]=[];for(var p=0,pl=_b.length;p<pl;p++){_d[i][p]=this._encode(_b[p]);}}else{if(_c==="object"){if(_b.toJson){var _e=_b.toJson(_a&&_a[i]);if(_b instanceof esri.tasks.FeatureSet){if(_e.spatialReference){_e.sr=_e.spatialReference;delete _e.spatialReference;}}_d[i]=_9?_e:_4.toJson(_e);}}else{_d[i]=_b;}}}}return _d;},_successHandler:function(_f,_10,_11,dfd){if(_10){this[_10].apply(this,_f);}if(_11){_11.apply(null,_f);}if(dfd){esri._resDfd(dfd,_f);}},_errorHandler:function(err,_12,dfd){this.onError(err);if(_12){_12(err);}if(dfd){dfd.errback(err);}},setNormalization:function(_13){this.normalization=_13;},onError:function(){}});_4.declare("esri.tasks.FeatureSet",null,{constructor:function(_14){if(_14){_4.mixin(this,_14);var _15=this.features,sr=_14.spatialReference,_16=esri.Graphic,_17=esri.geometry.getGeometryType(_14.geometryType);sr=(this.spatialReference=new esri.SpatialReference(sr));this.geometryType=_14.geometryType;if(_14.fields){this.fields=_14.fields;}_4.forEach(_15,function(_18,i){var _19=_18.geometry&&_18.geometry.spatialReference;_15[i]=new _16(_17?new _17(_18.geometry):null,_18.symbol&&esri.symbol.fromJson(_18.symbol),_18.attributes);if(_15[i].geometry&&!_19){_15[i].geometry.setSpatialReference(sr);}});}else{this.features=[];}},displayFieldName:null,geometryType:null,spatialReference:null,fields:[],features:[],fieldAliases:null,toJson:function(_1a){var _1b={};if(this.displayFieldName){_1b.displayFieldName=this.displayFieldName;}if(this.fields){_1b.fields=this.fields;}if(this.spatialReference){_1b.spatialReference=this.spatialReference.toJson();}else{if(this.features[0].geometry){_1b.spatialReference=this.features[0].geometry.spatialReference.toJson();}}if(this.features[0]){if(this.features[0].geometry){_1b.geometryType=esri.geometry.getJsonType(this.features[0].geometry);}_1b.features=esri._encodeGraphics(this.features,_1a);}return _1b;}});esri.tasks._SpatialRelationship={SPATIAL_REL_INTERSECTS:"esriSpatialRelIntersects",SPATIAL_REL_CONTAINS:"esriSpatialRelContains",SPATIAL_REL_CROSSES:"esriSpatialRelCrosses",SPATIAL_REL_ENVELOPEINTERSECTS:"esriSpatialRelEnvelopeIntersects",SPATIAL_REL_INDEXINTERSECTS:"esriSpatialRelIndexIntersects",SPATIAL_REL_OVERLAPS:"esriSpatialRelOverlaps",SPATIAL_REL_TOUCHES:"esriSpatialRelTouches",SPATIAL_REL_WITHIN:"esriSpatialRelWithin",SPATIAL_REL_RELATION:"esriSpatialRelRelation"};}}};});