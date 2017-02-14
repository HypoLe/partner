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

if(!dojo._hasResource["esri.dijit.editing.tools.Editing"]){dojo._hasResource["esri.dijit.editing.tools.Editing"]=true;dojo.provide("esri.dijit.editing.tools.Editing");dojo.require("esri.dijit.editing.tools.DropDownToolBase");dojo.require("esri.dijit.editing.Util");dojo.require("esri.dijit.editing.tools.EditingTools");dojo.declare("esri.dijit.editing.tools.Editing",[esri.dijit.editing.tools.DropDownToolBase],{_enabled:false,deactivate:function(){if(!this._enabled){return;}this._enabled=false;this.inherited(arguments);this._settings.templatePicker.clearSelection();},onItemClicked:function(_1){this.inherited(arguments);if(this._activeTool===this._tools.AUTOCOMPLETE){this._settings.editor._drawingTool=esri.layers.FeatureTemplate.TOOL_AUTO_COMPLETE_POLYGON;}},_activateTool:function(_2,_3){this.enable(_3);for(var i in this._tools){if(this._tools.hasOwnProperty(i)){this.dropDown.removeChild(this._tools[i]);if(this._tools[i]._enabled===true){this.dropDown.addChild(this._tools[i]);}}}if(this._activeTool._enabled===false){this._activeTool=this._tools[_2.toUpperCase()];}this._activeTool.activate();this._activeTool.setChecked(true);this._updateUI();},_initializeTool:function(_4){this.inherited(arguments);this._initializeTools();},_initializeTools:function(){var _5=this._settings.layers;var _6=this._settings.editor;var _7=false,_8=false,_9=false;var _a=this._toolTypes=[];var _b;dojo.forEach(_5,function(_c){_b=_c.geometryType;_7=_7||_b==="esriGeometryPoint";_8=_8||_b==="esriGeometryPolyline";_9=_9||_b==="esriGeometryPolygon";_a=_a.concat(dojo.map(this._getTemplatesFromLayer(_c),dojo.hitch(this,function(_d){return _6._toDrawTool(_d.drawingTool,_c);})));},this);var _e=this._settings.createOptions;if(_7){this._toolTypes.push("point");}if(_8){this._toolTypes=this._toolTypes.concat(_e.polylineDrawTools);}if(_9){this._toolTypes=this._toolTypes.concat(_e.polygonDrawTools);}this._toolTypes=this._toUnique(this._toolTypes.concat(_a));},_toUnique:function(_f){var _10={};return dojo.filter(_f,function(val){return _10[val]?false:(_10[val]=true);});},_getTemplatesFromLayer:function(_11){var _12=_11.templates||[];var _13=_11.types;dojo.forEach(_13,function(_14){_12=_12.concat(_14.templates);});return dojo.filter(_12,esri._isDefined);},_createTools:function(){dojo.forEach(this._toolTypes,this._createTool,this);this.inherited(arguments);},_createTool:function(_15){var _16=dojo.mixin(esri.dijit.editing.tools.EditingTools[_15],{settings:this._settings,onClick:dojo.hitch(this,"onItemClicked")});this._tools[_15.toUpperCase()]=new esri.dijit.editing.tools.Edit(_16);}});}