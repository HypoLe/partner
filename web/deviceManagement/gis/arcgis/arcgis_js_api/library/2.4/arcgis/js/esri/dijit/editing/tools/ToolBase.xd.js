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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.tools.ToolBase"],["require","esri.toolbars.draw"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.tools.ToolBase"]){_4._hasResource["esri.dijit.editing.tools.ToolBase"]=true;_4.provide("esri.dijit.editing.tools.ToolBase");_4.require("esri.toolbars.draw");_4.declare("esri.dijit.editing.tools.ToolBase",null,{_enabled:true,showLabel:false,constructor:function(_7,_8){_7=_7||{};_4.mixin(this,_7);this.label=this._label?esri.bundle.widgets.editor.tools[this._label]:"";this._settings=_7.settings;this._toolbar=_7.settings.drawToolbar;this._editToolbar=_7.settings.editToolbar;this._initializeTool();},onFinished:function(){},onDrawEnd:function(){},onApplyEdits:function(){},postCreate:function(){this.deactivate();this.inherited(arguments);},destroy:function(){},activate:function(_9){if(this._toolbar){this._toolbar.deactivate();}if(this._editToolbar){this._editToolbar.deactivate();}if(!this._enabled){return;}this._checked=true;this._layer=_9;if(this._toolbar&&this._drawType){this._toolbar.activate(this._drawType);}},deactivate:function(){if(this._toolbar){this._toolbar.deactivate();}if(this._editToolbar){this._editToolbar.deactivate();}this.setChecked(false);this._updateUI();},setEnabled:function(_a){this._enabled=_a;this._updateUI();},setChecked:function(_b){this._checked=_b;},enable:function(_c){this._updateUI();},isEnabled:function(){return _enabled;},getToolName:function(){return this._toolName;},_initializeTool:function(){},_updateUI:function(){this.disabled=!this._enabled;this.attr("iconClass",this._enabled?this._enabledIcon:this._disabledIcon);}});}}};});