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

if(!dojo._hasResource["esri.tasks.na"]){dojo._hasResource["esri.tasks.na"]=true;dojo.provide("esri.tasks.na");dojo.require("esri.tasks._task");esri.tasks._NALengthUnit={esriFeet:"esriNAUFeet",esriKilometers:"esriNAUKilometers",esriMeters:"esriNAUMeters",esriMiles:"esriNAUMiles",esriNauticalMiles:"esriNAUNauticalMiles",esriYards:"esriNAUYards"};esri.tasks.NAOutputLine={NONE:"esriNAOutputLineNone",STRAIGHT:"esriNAOutputLineStraight",TRUE_SHAPE:"esriNAOutputLineTrueShape",TRUE_SHAPE_WITH_MEASURE:"esriNAOutputLineTrueShapeWithMeasure"};esri.tasks.NAUTurn={ALLOW_BACKTRACK:"esriNFSBAllowBacktrack",AT_DEAD_ENDS_ONLY:"esriNFSBAtDeadEndsOnly",NO_BACKTRACK:"esriNFSBNoBacktrack",AT_DEAD_ENDS_AND_INTERSECTIONS:"esriNFSBAtDeadEndsAndIntersections"};esri.tasks.NAOutputPolygon={NONE:"esriNAOutputPolygonNone",SIMPLIFIED:"esriNAOutputPolygonSimplified",DETAILED:"esriNAOutputPolygonDetailed"};esri.tasks.NATravelDirection={FROM_FACILITY:"esriNATravelDirectionFromFacility",TO_FACILITY:"esriNATravelDirectionToFacility"};dojo.declare("esri.tasks.NAMessage",null,{constructor:function(_1){dojo.mixin(this,_1);}});dojo.mixin(esri.tasks.NAMessage,{TYPE_INFORMATIVE:0,TYPE_PROCESS_DEFINITION:1,TYPE_PROCESS_START:2,TYPE_PROCESS_STOP:3,TYPE_WARNING:50,TYPE_ERROR:100,TYPE_EMPTY:101,TYPE_ABORT:200});dojo.declare("esri.tasks.DataLayer",null,{name:null,where:null,geometry:null,spatialRelationship:null,toJson:function(){var _2={type:"layer",layerName:this.name,where:this.where,spatialRel:this.spatialRelationship};var g=this.geometry;if(g){_2.geometryType=esri.geometry.getJsonType(g);_2.geometry=g.toJson();}return esri.filter(_2,function(_3){if(_3!==null){return true;}});}});dojo.mixin(esri.tasks.DataLayer,esri.tasks._SpatialRelationship);dojo.declare("esri.tasks.DirectionsFeatureSet",esri.tasks.FeatureSet,{constructor:function(_4,_5){this.routeId=_4.routeId;this.routeName=_4.routeName;dojo.mixin(this,_4.summary);this.extent=new esri.geometry.Extent(this.envelope);var _6=this._fromCompressedGeometry,_7=this.features,sr=this.extent.spatialReference,_8=[];dojo.forEach(_5,function(cg,i){_7[i].setGeometry(_8[i]=_6(cg,sr));});this.mergedGeometry=this._mergePolylinesToSinglePath(_8,sr);this.geometryType="esriGeometryPolyline";delete this.envelope;},_fromCompressedGeometry:function(_9,sr){var _a=0,_b=0,_c=[],x,y,_d=_9.replace(/(\+)|(\-)/g," $&").split(" "),_e=parseInt(_d[1],32);for(var j=2,jl=_d.length;j<jl;j+=2){_a=(x=(parseInt(_d[j],32)+_a));_b=(y=(parseInt(_d[j+1],32)+_b));_c.push([x/_e,y/_e]);}var po=new esri.geometry.Polyline({paths:[_c]});po.setSpatialReference(sr);return po;},_mergePolylinesToSinglePath:function(_f,sr){var _10=[];dojo.forEach(_f,function(_11){dojo.forEach(_11.paths,function(_12){_10=_10.concat(_12);});});var _13=[],_14=[0,0];dojo.forEach(_10,function(_15){if(_15[0]!==_14[0]||_15[1]!==_14[1]){_13.push(_15);_14=_15;}});return new esri.geometry.Polyline({paths:[_13]}).setSpatialReference(sr);}});}