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

if(!dojo._hasResource["esri.dijit.editing.tools.ToolBase"]){dojo._hasResource["esri.dijit.editing.tools.ToolBase"]=true;dojo.provide("esri.dijit.editing.tools.ToolBase");dojo.require("esri.toolbars.draw");dojo.declare("esri.dijit.editing.tools.ToolBase",null,{_enabled:true,showLabel:false,constructor:function(_1,_2){_1=_1||{};dojo.mixin(this,_1);this.label=this._label?esri.bundle.widgets.editor.tools[this._label]:"";this._settings=_1.settings;this._toolbar=_1.settings.drawToolbar;this._editToolbar=_1.settings.editToolbar;this._initializeTool();},onFinished:function(){},onDrawEnd:function(){},onApplyEdits:function(){},postCreate:function(){this.deactivate();this.inherited(arguments);},destroy:function(){},activate:function(_3){if(this._toolbar){this._toolbar.deactivate();}if(this._editToolbar){this._editToolbar.deactivate();}if(!this._enabled){return;}this._checked=true;this._layer=_3;if(this._toolbar&&this._drawType){this._toolbar.activate(this._drawType);}},deactivate:function(){if(this._toolbar){this._toolbar.deactivate();}if(this._editToolbar){this._editToolbar.deactivate();}this.setChecked(false);this._updateUI();},setEnabled:function(_4){this._enabled=_4;this._updateUI();},setChecked:function(_5){this._checked=_5;},enable:function(_6){this._updateUI();},isEnabled:function(){return _enabled;},getToolName:function(){return this._toolName;},_initializeTool:function(){},_updateUI:function(){this.disabled=!this._enabled;this.attr("iconClass",this._enabled?this._enabledIcon:this._disabledIcon);}});}