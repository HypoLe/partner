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

if(!dojo._hasResource["esri.dijit.editing.tools.MenuItemBase"]){dojo._hasResource["esri.dijit.editing.tools.MenuItemBase"]=true;dojo.provide("esri.dijit.editing.tools.MenuItemBase");dojo.require("esri.dijit.editing.tools.ToolBase");dojo.require("dijit.MenuItem");dojo.require("esri.toolbars.draw");dojo.declare("esri.dijit.editing.tools.MenuItemBase",[dijit.MenuItem,esri.dijit.editing.tools.ToolBase],{destroy:function(){dijit.MenuItem.prototype.destroy.apply(this,arguments);esri.dijit.editing.tools.ToolBase.prototype.destroy.apply(this,arguments);}});}