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

if(!dojo._hasResource["esri.dijit.editing.tools.ButtonToolBase"]){dojo._hasResource["esri.dijit.editing.tools.ButtonToolBase"]=true;dojo.provide("esri.dijit.editing.tools.ButtonToolBase");dojo.require("esri.dijit.editing.tools.ToolBase");dojo.require("dijit.form.Button");dojo.declare("esri.dijit.editing.tools.ButtonToolBase",[dijit.form.Button,esri.dijit.editing.tools.ToolBase],{postCreate:function(){this.inherited(arguments);if(this._setShowLabelAttr){this._setShowLabelAttr(false);}},destroy:function(){dijit.form.Button.prototype.destroy.apply(this,arguments);esri.dijit.editing.tools.ToolBase.prototype.destroy.apply(this,arguments);}});}