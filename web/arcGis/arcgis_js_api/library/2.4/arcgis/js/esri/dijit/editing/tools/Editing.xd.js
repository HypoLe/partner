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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.tools.Editing"],["require","esri.dijit.editing.tools.DropDownToolBase"],["require","esri.dijit.editing.Util"],["require","esri.dijit.editing.tools.EditingTools"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.tools.Editing"]){_4._hasResource["esri.dijit.editing.tools.Editing"]=true;_4.provide("esri.dijit.editing.tools.Editing");_4.require("esri.dijit.editing.tools.DropDownToolBase");_4.require("esri.dijit.editing.Util");_4.require("esri.dijit.editing.tools.EditingTools");_4.declare("esri.dijit.editing.tools.Editing",[esri.dijit.editing.tools.DropDownToolBase],{_enabled:false,deactivate:function(){if(!this._enabled){return;}this._enabled=false;this.inherited(arguments);this._settings.templatePicker.clearSelection();},onItemClicked:function(_7){this.inherited(arguments);if(this._activeTool===this._tools.AUTOCOMPLETE){this._settings.editor._drawingTool=esri.layers.FeatureTemplate.TOOL_AUTO_COMPLETE_POLYGON;}},_activateTool:function(_8,_9){this.enable(_9);for(var i in this._tools){if(this._tools.hasOwnProperty(i)){this.dropDown.removeChild(this._tools[i]);if(this._tools[i]._enabled===true){this.dropDown.addChild(this._tools[i]);}}}if(this._activeTool._enabled===false){this._activeTool=this._tools[_8.toUpperCase()];}this._activeTool.activate();this._activeTool.setChecked(true);this._updateUI();},_initializeTool:function(_a){this.inherited(arguments);this._initializeTools();},_initializeTools:function(){var _b=this._settings.layers;var _c=this._settings.editor;var _d=false,_e=false,_f=false;var _10=this._toolTypes=[];var _11;_4.forEach(_b,function(_12){_11=_12.geometryType;_d=_d||_11==="esriGeometryPoint";_e=_e||_11==="esriGeometryPolyline";_f=_f||_11==="esriGeometryPolygon";_10=_10.concat(_4.map(this._getTemplatesFromLayer(_12),_4.hitch(this,function(_13){return _c._toDrawTool(_13.drawingTool,_12);})));},this);var _14=this._settings.createOptions;if(_d){this._toolTypes.push("point");}if(_e){this._toolTypes=this._toolTypes.concat(_14.polylineDrawTools);}if(_f){this._toolTypes=this._toolTypes.concat(_14.polygonDrawTools);}this._toolTypes=this._toUnique(this._toolTypes.concat(_10));},_toUnique:function(arr){var _15={};return _4.filter(arr,function(val){return _15[val]?false:(_15[val]=true);});},_getTemplatesFromLayer:function(_16){var _17=_16.templates||[];var _18=_16.types;_4.forEach(_18,function(_19){_17=_17.concat(_19.templates);});return _4.filter(_17,esri._isDefined);},_createTools:function(){_4.forEach(this._toolTypes,this._createTool,this);this.inherited(arguments);},_createTool:function(_1a){var _1b=_4.mixin(esri.dijit.editing.tools.EditingTools[_1a],{settings:this._settings,onClick:_4.hitch(this,"onItemClicked")});this._tools[_1a.toUpperCase()]=new esri.dijit.editing.tools.Edit(_1b);}});}}};});