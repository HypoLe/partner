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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.tasks.na"],["require","esri.tasks._task"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.tasks.na"]){_4._hasResource["esri.tasks.na"]=true;_4.provide("esri.tasks.na");_4.require("esri.tasks._task");esri.tasks._NALengthUnit={esriFeet:"esriNAUFeet",esriKilometers:"esriNAUKilometers",esriMeters:"esriNAUMeters",esriMiles:"esriNAUMiles",esriNauticalMiles:"esriNAUNauticalMiles",esriYards:"esriNAUYards"};esri.tasks.NAOutputLine={NONE:"esriNAOutputLineNone",STRAIGHT:"esriNAOutputLineStraight",TRUE_SHAPE:"esriNAOutputLineTrueShape",TRUE_SHAPE_WITH_MEASURE:"esriNAOutputLineTrueShapeWithMeasure"};esri.tasks.NAUTurn={ALLOW_BACKTRACK:"esriNFSBAllowBacktrack",AT_DEAD_ENDS_ONLY:"esriNFSBAtDeadEndsOnly",NO_BACKTRACK:"esriNFSBNoBacktrack",AT_DEAD_ENDS_AND_INTERSECTIONS:"esriNFSBAtDeadEndsAndIntersections"};esri.tasks.NAOutputPolygon={NONE:"esriNAOutputPolygonNone",SIMPLIFIED:"esriNAOutputPolygonSimplified",DETAILED:"esriNAOutputPolygonDetailed"};esri.tasks.NATravelDirection={FROM_FACILITY:"esriNATravelDirectionFromFacility",TO_FACILITY:"esriNATravelDirectionToFacility"};_4.declare("esri.tasks.NAMessage",null,{constructor:function(_7){_4.mixin(this,_7);}});_4.mixin(esri.tasks.NAMessage,{TYPE_INFORMATIVE:0,TYPE_PROCESS_DEFINITION:1,TYPE_PROCESS_START:2,TYPE_PROCESS_STOP:3,TYPE_WARNING:50,TYPE_ERROR:100,TYPE_EMPTY:101,TYPE_ABORT:200});_4.declare("esri.tasks.DataLayer",null,{name:null,where:null,geometry:null,spatialRelationship:null,toJson:function(){var _8={type:"layer",layerName:this.name,where:this.where,spatialRel:this.spatialRelationship};var g=this.geometry;if(g){_8.geometryType=esri.geometry.getJsonType(g);_8.geometry=g.toJson();}return esri.filter(_8,function(_9){if(_9!==null){return true;}});}});_4.mixin(esri.tasks.DataLayer,esri.tasks._SpatialRelationship);_4.declare("esri.tasks.DirectionsFeatureSet",esri.tasks.FeatureSet,{constructor:function(_a,_b){this.routeId=_a.routeId;this.routeName=_a.routeName;_4.mixin(this,_a.summary);this.extent=new esri.geometry.Extent(this.envelope);var _c=this._fromCompressedGeometry,_d=this.features,sr=this.extent.spatialReference,_e=[];_4.forEach(_b,function(cg,i){_d[i].setGeometry(_e[i]=_c(cg,sr));});this.mergedGeometry=this._mergePolylinesToSinglePath(_e,sr);this.geometryType="esriGeometryPolyline";delete this.envelope;},_fromCompressedGeometry:function(_f,sr){var _10=0,_11=0,_12=[],x,y,_13=_f.replace(/(\+)|(\-)/g," $&").split(" "),_14=parseInt(_13[1],32);for(var j=2,jl=_13.length;j<jl;j+=2){_10=(x=(parseInt(_13[j],32)+_10));_11=(y=(parseInt(_13[j+1],32)+_11));_12.push([x/_14,y/_14]);}var po=new esri.geometry.Polyline({paths:[_12]});po.setSpatialReference(sr);return po;},_mergePolylinesToSinglePath:function(_15,sr){var _16=[];_4.forEach(_15,function(_17){_4.forEach(_17.paths,function(_18){_16=_16.concat(_18);});});var _19=[],_1a=[0,0];_4.forEach(_16,function(_1b){if(_1b[0]!==_1a[0]||_1b[1]!==_1a[1]){_19.push(_1b);_1a=_1b;}});return new esri.geometry.Polyline({paths:[_19]}).setSpatialReference(sr);}});}}};});