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

if(!dojo._hasResource["esri.dijit.editing.tools.DropDownToolBase"]){dojo._hasResource["esri.dijit.editing.tools.DropDownToolBase"]=true;dojo.provide("esri.dijit.editing.tools.DropDownToolBase");dojo.require("dijit.form.ComboButton");dojo.require("esri.dijit.editing.tools.ToolBase");dojo.declare("esri.dijit.editing.tools.DropDownToolBase",[dijit.form.ComboButton,esri.dijit.editing.tools.ToolBase],{_enabled:false,_checked:false,postCreate:function(){this._tools=[];this._createTools();this.inherited(arguments);if(this._setShowLabelAttr){this._setShowLabelAttr(false);}},destroy:function(){var _1=this._tools;for(var _2 in _1){if(_1.hasOwnProperty(_2)&&esri._isDefined(_1[_2])){_1[_2].destroy();}}this.inherited(arguments);},_createTools:function(){var _3=new dijit.Menu();this.dropDown=_3;for(var i in this._tools){if(this._tools.hasOwnProperty(i)){_3.addChild(this._tools[i]);}}this._activeTool=_3.getChildren()[0];this._updateUI();},activate:function(_4){this.inherited(arguments);if(!this._activeTool){this._activateDefaultTool();}else{this._activeTool.activate();}},deactivate:function(){this.inherited(arguments);if(this._activeTool){this._activeTool.deactivate();}},enable:function(_5){for(var _6 in this._tools){if(this._tools.hasOwnProperty(_6)){this._tools[_6].enable(_5);}}this.setEnabled(true);this.inherited(arguments);},setChecked:function(_7){this._checked=_7;this._updateUI();},_onDrawEnd:function(_8){},onLayerChange:function(_9,_a,_b){this._activeTool=null;this._activeType=_a;this._activeTemplate=_b;this._activeLayer=_9;},onItemClicked:function(_c){if(this._activeTool){this._activeTool.deactivate();}this._activeTool=dijit.byId(_c.currentTarget.id);if(this._checked===false){this._onClick();}else{this._updateUI();if(this._activeTool){this._activeTool.activate();this._activeTool.setChecked(true);}}},_onClick:function(_d){if(this._enabled===false){return;}this._checked=!this._checked;this.inherited(arguments);},_updateUI:function(){this.attr("disabled",!this._enabled);dojo.style(this.focusNode,{outline:"none"});dojo.style(this.titleNode,{padding:"0px",border:"none"});if(this._checked){dojo.style(this.titleNode,{backgroundColor:"#D4DFF2",border:"1px solid #316AC5"});}else{dojo.style(this.titleNode,{backgroundColor:"",border:""});}if(this._activeTool){this.attr("iconClass",this._checked?this._activeTool._enabledIcon:this._activeTool._disabledIcon);this.attr("label",this._activeTool.label);}}});}