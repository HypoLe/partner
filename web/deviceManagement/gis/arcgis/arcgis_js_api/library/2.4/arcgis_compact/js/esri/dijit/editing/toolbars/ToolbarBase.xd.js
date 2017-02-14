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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.toolbars.ToolbarBase"],["require","dijit.Toolbar"],["require","dijit.ToolbarSeparator"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.toolbars.ToolbarBase"]){_4._hasResource["esri.dijit.editing.toolbars.ToolbarBase"]=true;_4.provide("esri.dijit.editing.toolbars.ToolbarBase");_4.require("dijit.Toolbar");_4.require("dijit.ToolbarSeparator");_4.declare("esri.dijit.editing.toolbars.ToolbarBase",[_5.Toolbar],{_enabled:true,graphicsAdded:function(){},drawEnd:function(){},onApplyEdits:function(){},onDelete:function(){},constructor:function(_7,_8){if(!_7||!_7.settings){return;}this._tools=[];this._tbConnects=[];this._initialize(_7.settings);},postCreate:function(){this._createTools();this.deactivate();},destroy:function(){var _9=this._tools;for(var _a in _9){if(_9.hasOwnProperty(_a)&&esri._isDefined(this._tools[_a])){this._tools[_a].destroy();}}_4.forEach(this._tbConnects,"dojo.disconnect(item)");this.inherited(arguments);},activate:function(_b){this._enabled=true;},deactivate:function(){this._enabled=false;this._layer=null;this._geometryType=null;var _c=this._tools;for(var _d in _c){if(_c.hasOwnProperty(_d)){this._tools[_d].deactivate();this._tools[_d].setChecked(false);}}},isEnabled:function(){return _enabled;},setActiveSymbol:function(_e){this._activeSymbol=_e;},_getSymbol:function(){},_createTools:function(){},_initialize:function(_f){this._settings=_f;this._toolbar=_f.drawToolbar;this._editToolbar=_f.editToolbar;this._initializeToolbar();},_activateTool:function(_10,_11){if(this._activeTool){this._activeTool.deactivate();}if(_11===true&&this._activeTool==this._tools[_10]){this._activeTool.setChecked(false);this._activeTool=null;}else{this._activeTool=this._tools[_10];this._activeTool.setChecked(true);this._activeTool.activate(null);}},_createSeparator:function(){this.addChild(new _5.ToolbarSeparator());}});}}};});