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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.tools.DropDownToolBase"],["require","dijit.form.ComboButton"],["require","esri.dijit.editing.tools.ToolBase"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.tools.DropDownToolBase"]){_4._hasResource["esri.dijit.editing.tools.DropDownToolBase"]=true;_4.provide("esri.dijit.editing.tools.DropDownToolBase");_4.require("dijit.form.ComboButton");_4.require("esri.dijit.editing.tools.ToolBase");_4.declare("esri.dijit.editing.tools.DropDownToolBase",[_5.form.ComboButton,esri.dijit.editing.tools.ToolBase],{_enabled:false,_checked:false,postCreate:function(){this._tools=[];this._createTools();this.inherited(arguments);if(this._setShowLabelAttr){this._setShowLabelAttr(false);}},destroy:function(){var _7=this._tools;for(var _8 in _7){if(_7.hasOwnProperty(_8)&&esri._isDefined(_7[_8])){_7[_8].destroy();}}this.inherited(arguments);},_createTools:function(){var _9=new _5.Menu();this.dropDown=_9;for(var i in this._tools){if(this._tools.hasOwnProperty(i)){_9.addChild(this._tools[i]);}}this._activeTool=_9.getChildren()[0];this._updateUI();},activate:function(_a){this.inherited(arguments);if(!this._activeTool){this._activateDefaultTool();}else{this._activeTool.activate();}},deactivate:function(){this.inherited(arguments);if(this._activeTool){this._activeTool.deactivate();}},enable:function(_b){for(var _c in this._tools){if(this._tools.hasOwnProperty(_c)){this._tools[_c].enable(_b);}}this.setEnabled(true);this.inherited(arguments);},setChecked:function(_d){this._checked=_d;this._updateUI();},_onDrawEnd:function(_e){},onLayerChange:function(_f,_10,_11){this._activeTool=null;this._activeType=_10;this._activeTemplate=_11;this._activeLayer=_f;},onItemClicked:function(evt){if(this._activeTool){this._activeTool.deactivate();}this._activeTool=_5.byId(evt.currentTarget.id);if(this._checked===false){this._onClick();}else{this._updateUI();if(this._activeTool){this._activeTool.activate();this._activeTool.setChecked(true);}}},_onClick:function(evt){if(this._enabled===false){return;}this._checked=!this._checked;this.inherited(arguments);},_updateUI:function(){this.attr("disabled",!this._enabled);_4.style(this.focusNode,{outline:"none"});_4.style(this.titleNode,{padding:"0px",border:"none"});if(this._checked){_4.style(this.titleNode,{backgroundColor:"#D4DFF2",border:"1px solid #316AC5"});}else{_4.style(this.titleNode,{backgroundColor:"",border:""});}if(this._activeTool){this.attr("iconClass",this._checked?this._activeTool._enabledIcon:this._activeTool._disabledIcon);this.attr("label",this._activeTool.label);}}});}}};});