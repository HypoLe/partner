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

if(!dojo._hasResource["esri.dijit.editing.tools.Selection"]){dojo._hasResource["esri.dijit.editing.tools.Selection"]=true;dojo.provide("esri.dijit.editing.tools.Selection");dojo.require("esri.dijit.editing.tools.DropDownToolBase");dojo.require("esri.dijit.editing.Util");dojo.require("esri.dijit.editing.tools.SelectionTools");dojo.declare("esri.dijit.editing.tools.Selection",[esri.dijit.editing.tools.DropDownToolBase],{_enabled:true,activate:function(){this.inherited(arguments);this._sConnect=dojo.connect(this._toolbar,"onDrawEnd",this,"_onDrawEnd");},deactivate:function(){this.inherited(arguments);dojo.disconnect(this._sConnect);delete this._sConnect;},_initializeTool:function(){this._createSymbols();this._initializeLayers();this._toolTypes=["select","selectadd","selectremove"];},_onDrawEnd:function(_1){this.inherited(arguments);this._settings.editor._hideAttributeInspector();var _2=this._settings.layers;this._selectMethod=this._activeTool._selectMethod;this._settings.editor._selectionHelper.selectFeaturesByGeometry(_2,_1,this._selectMethod,dojo.hitch(this,"onFinished"));},_createSymbols:function(){this._pointSelectionSymbol=new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE,10,new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([0,0,0]),1),new dojo.Color([255,0,0,0.5]));this._polylineSelectionSymbol=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([0,200,255]),2);this._polygonSelectionSymbol=new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([0,0,0]),1),new dojo.Color([0,200,255,0.5]));},_initializeLayers:function(){var _3=this._settings.layers;dojo.forEach(_3,this._setSelectionSymbol,this);},_setSelectionSymbol:function(_4){var _5=null;switch(_4.geometryType){case "esriGeometryPoint":_5=this._pointSelectionSymbol;break;case "esriGeometryPolyline":_5=this._polylineSelectionSymbol;break;case "esriGeometryPolygon":_5=this._polygonSelectionSymbol;break;}_4.setSelectionSymbol(_4._selectionSymbol||_5);},_createTools:function(){dojo.forEach(this._toolTypes,this._createTool,this);this.inherited(arguments);},_createTool:function(_6){var _7=dojo.mixin(esri.dijit.editing.tools.SelectionTools[_6],{settings:this._settings,onClick:dojo.hitch(this,"onItemClicked")});this._tools[_6.toUpperCase()]=new esri.dijit.editing.tools.Edit(_7);}});}