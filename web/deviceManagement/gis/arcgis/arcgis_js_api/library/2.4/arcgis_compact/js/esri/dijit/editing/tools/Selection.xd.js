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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.tools.Selection"],["require","esri.dijit.editing.tools.DropDownToolBase"],["require","esri.dijit.editing.Util"],["require","esri.dijit.editing.tools.SelectionTools"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.tools.Selection"]){_4._hasResource["esri.dijit.editing.tools.Selection"]=true;_4.provide("esri.dijit.editing.tools.Selection");_4.require("esri.dijit.editing.tools.DropDownToolBase");_4.require("esri.dijit.editing.Util");_4.require("esri.dijit.editing.tools.SelectionTools");_4.declare("esri.dijit.editing.tools.Selection",[esri.dijit.editing.tools.DropDownToolBase],{_enabled:true,activate:function(){this.inherited(arguments);this._sConnect=_4.connect(this._toolbar,"onDrawEnd",this,"_onDrawEnd");},deactivate:function(){this.inherited(arguments);_4.disconnect(this._sConnect);delete this._sConnect;},_initializeTool:function(){this._createSymbols();this._initializeLayers();this._toolTypes=["select","selectadd","selectremove"];},_onDrawEnd:function(_7){this.inherited(arguments);this._settings.editor._hideAttributeInspector();var _8=this._settings.layers;this._selectMethod=this._activeTool._selectMethod;this._settings.editor._selectionHelper.selectFeaturesByGeometry(_8,_7,this._selectMethod,_4.hitch(this,"onFinished"));},_createSymbols:function(){this._pointSelectionSymbol=new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE,10,new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new _4.Color([0,0,0]),1),new _4.Color([255,0,0,0.5]));this._polylineSelectionSymbol=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new _4.Color([0,200,255]),2);this._polygonSelectionSymbol=new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new _4.Color([0,0,0]),1),new _4.Color([0,200,255,0.5]));},_initializeLayers:function(){var _9=this._settings.layers;_4.forEach(_9,this._setSelectionSymbol,this);},_setSelectionSymbol:function(_a){var _b=null;switch(_a.geometryType){case "esriGeometryPoint":_b=this._pointSelectionSymbol;break;case "esriGeometryPolyline":_b=this._polylineSelectionSymbol;break;case "esriGeometryPolygon":_b=this._polygonSelectionSymbol;break;}_a.setSelectionSymbol(_a._selectionSymbol||_b);},_createTools:function(){_4.forEach(this._toolTypes,this._createTool,this);this.inherited(arguments);},_createTool:function(_c){var _d=_4.mixin(esri.dijit.editing.tools.SelectionTools[_c],{settings:this._settings,onClick:_4.hitch(this,"onItemClicked")});this._tools[_c.toUpperCase()]=new esri.dijit.editing.tools.Edit(_d);}});}}};});