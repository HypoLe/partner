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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.tools.ToggleToolBase"],["require","esri.dijit.editing.tools.ToolBase"],["require","dijit.form.ToggleButton"],["require","esri.toolbars.draw"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.tools.ToggleToolBase"]){_4._hasResource["esri.dijit.editing.tools.ToggleToolBase"]=true;_4.provide("esri.dijit.editing.tools.ToggleToolBase");_4.require("esri.dijit.editing.tools.ToolBase");_4.require("dijit.form.ToggleButton");_4.require("esri.toolbars.draw");_4.declare("esri.dijit.editing.tools.ToggleToolBase",[_5.form.ToggleButton,esri.dijit.editing.tools.ToolBase],{postCreate:function(){this.inherited(arguments);if(this._setShowLabelAttr){this._setShowLabelAttr(false);}},destroy:function(){_5.form.ToggleButton.prototype.destroy.apply(this,arguments);esri.dijit.editing.tools.ToolBase.prototype.destroy.apply(this,arguments);},setChecked:function(_7){_5.form.ToggleButton.prototype.setChecked.apply(this,arguments);}});}}};});