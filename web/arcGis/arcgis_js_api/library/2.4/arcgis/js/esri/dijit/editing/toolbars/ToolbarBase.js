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

if(!dojo._hasResource["esri.dijit.editing.toolbars.ToolbarBase"]){dojo._hasResource["esri.dijit.editing.toolbars.ToolbarBase"]=true;dojo.provide("esri.dijit.editing.toolbars.ToolbarBase");dojo.require("dijit.Toolbar");dojo.require("dijit.ToolbarSeparator");dojo.declare("esri.dijit.editing.toolbars.ToolbarBase",[dijit.Toolbar],{_enabled:true,graphicsAdded:function(){},drawEnd:function(){},onApplyEdits:function(){},onDelete:function(){},constructor:function(_1,_2){if(!_1||!_1.settings){return;}this._tools=[];this._tbConnects=[];this._initialize(_1.settings);},postCreate:function(){this._createTools();this.deactivate();},destroy:function(){var _3=this._tools;for(var _4 in _3){if(_3.hasOwnProperty(_4)&&esri._isDefined(this._tools[_4])){this._tools[_4].destroy();}}dojo.forEach(this._tbConnects,"dojo.disconnect(item)");this.inherited(arguments);},activate:function(_5){this._enabled=true;},deactivate:function(){this._enabled=false;this._layer=null;this._geometryType=null;var _6=this._tools;for(var _7 in _6){if(_6.hasOwnProperty(_7)){this._tools[_7].deactivate();this._tools[_7].setChecked(false);}}},isEnabled:function(){return _enabled;},setActiveSymbol:function(_8){this._activeSymbol=_8;},_getSymbol:function(){},_createTools:function(){},_initialize:function(_9){this._settings=_9;this._toolbar=_9.drawToolbar;this._editToolbar=_9.editToolbar;this._initializeToolbar();},_activateTool:function(_a,_b){if(this._activeTool){this._activeTool.deactivate();}if(_b===true&&this._activeTool==this._tools[_a]){this._activeTool.setChecked(false);this._activeTool=null;}else{this._activeTool=this._tools[_a];this._activeTool.setChecked(true);this._activeTool.activate(null);}},_createSeparator:function(){this.addChild(new dijit.ToolbarSeparator());}});}