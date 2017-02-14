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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.toolbars.Drawing"],["require","dijit._CssStateMixin"],["require","esri.dijit.editing.toolbars.ToolbarBase"],["require","esri.dijit.editing.tools.Editing"],["require","esri.dijit.editing.tools.Selection"],["require","esri.dijit.editing.tools.AdvancedTools"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.toolbars.Drawing"]){_4._hasResource["esri.dijit.editing.toolbars.Drawing"]=true;_4.provide("esri.dijit.editing.toolbars.Drawing");_4.require("dijit._CssStateMixin");_4.require("esri.dijit.editing.toolbars.ToolbarBase");_4.require("esri.dijit.editing.tools.Editing");_4.require("esri.dijit.editing.tools.Selection");_4.require("esri.dijit.editing.tools.AdvancedTools");(function(){var _7=[_4.moduleUrl("esri.dijit.editing","css/drawingToolbar.css")];var _8=document.getElementsByTagName("head").item(0),_9;for(i=0,il=_7.length;i<il;i++){_9=document.createElement("link");_9.type="text/css";_9.rel="stylesheet";_9.href=_7[i];_8.appendChild(_9);}})();_4.declare("esri.dijit.editing.toolbars.Drawing",[esri.dijit.editing.toolbars.ToolbarBase,_5._CssStateMixin],{onShowAttributeInspector:function(){},_activateTool:function(_a,_b){this._settings.editor._activeTool=_a;if(_a!=="EDITING"){this._settings.templatePicker.clearSelection();}if(_a!=="ATTRIBUTES"){this._settings.editor._hideAttributeInspector();}if(_a==="CLEAR"){return;}this.inherited(arguments);},_initializeToolbar:function(){var _c=this._settings.layers;_4.forEach(_c,function(_d){this._tbConnects.push(_4.connect(_d,"onSelectionComplete",this,"_updateUI"));},this);},activateEditing:function(_e,_f){this._tools.EDITING._activateTool(_e,_f.geometryType);this._activeTool=this._tools.EDITING;this._activeTool.setChecked(true);},_updateUI:function(){if(this._settings.undoManager){this._tools.UNDO.set("disabled",this._settings.undoManager.canUndo===false);this._tools.REDO.set("disabled",this._settings.undoManager.canRedo===false);}this._selectedFeatures=esri.dijit.editing.Util.LayerHelper.getSelection(this._settings.layers);var _10=this._selectedFeatures.length;if(this._tools.DELETE){this._tools.DELETE.set("disabled",_10<=0);}if(this._tools.CLEAR){this._tools.CLEAR.set("disabled",_10<=0);}if(this._tools.ATTRIBUTES){this._tools.ATTRIBUTES.set("disabled",_10<=0);}if(this._tools.UNION){this._tools.UNION.set("disabled",_10<2);}},_toolFinished:function(_11){if(_11==="ATTRIBUTES"&&(this._selectedFeatures&&this._selectedFeatures.length)){this.onShowAttributeInspector(this._selectedFeatures[0]);}if(_11==="SELECT"||_11==="CUT"||_11==="RESHAPING"||_11==="EDITING"){this._activeTool.deactivate();this._activeTool.setChecked(false);this._activeTool=null;}if(_11==="DELETE"){this.onDelete();}this._updateUI();},_createTools:function(){this._tools.SELECT=new esri.dijit.editing.tools.Selection({settings:this._settings,onClick:_4.hitch(this,"_activateTool","SELECT",true),onFinished:_4.hitch(this,"_toolFinished","SELECT")});this.addChild(this._tools.SELECT);this._tools.CLEAR=new esri.dijit.editing.tools.ButtonToolBase(_4.mixin(esri.dijit.editing.tools.SelectionTools.selectClear,{settings:this._settings,onClick:_4.hitch(this._settings.editor,"_clearSelection",false)}));this.addChild(this._tools.CLEAR);this._createSeparator();this._tools.ATTRIBUTES=new esri.dijit.editing.tools.ButtonToolBase(_4.mixin(esri.dijit.editing.tools.EditingTools.attributes,{settings:this._settings,onClick:_4.hitch(this,"_toolFinished","ATTRIBUTES")}));this.addChild(this._tools.ATTRIBUTES);this._createSeparator();this._tools.EDITING=new esri.dijit.editing.tools.Editing({settings:this._settings,onClick:_4.hitch(this,"_activateTool","EDITING",true),onApplyEdits:_4.hitch(this,"onApplyEdits"),onFinished:_4.hitch(this,"_toolFinished","EDITING")});this.addChild(this._tools.EDITING);this._tools.DELETE=new esri.dijit.editing.tools.ButtonToolBase(_4.mixin(esri.dijit.editing.tools.EditingTools.del,{settings:this._settings,onClick:_4.hitch(this,"_toolFinished","DELETE")}));this.addChild(this._tools.DELETE);if(this._settings.toolbarOptions){if(this._settings.toolbarOptions.cutVisible||this._settings.toolbarOptions.mergeVisible||this._settings.toolbarOptions.reshapeVisible){this._createSeparator();}if(this._settings.toolbarOptions.cutVisible){this._tools.CUT=new esri.dijit.editing.tools.Cut({settings:this._settings,onFinished:_4.hitch(this,"_toolFinished","CUT"),onClick:_4.hitch(this,"_activateTool","CUT",true),onApplyEdits:_4.hitch(this,"onApplyEdits")});this.addChild(this._tools.CUT);}if(this._settings.toolbarOptions.mergeVisible){this._tools.UNION=new esri.dijit.editing.tools.Union({settings:this._settings,onFinished:_4.hitch(this,"_toolFinished","UNION"),onApplyEdits:_4.hitch(this,"onApplyEdits")});this.addChild(this._tools.UNION);}if(this._settings.toolbarOptions.reshapeVisible){this._tools.RESHAPING=new esri.dijit.editing.tools.Reshape({settings:this._settings,onClick:_4.hitch(this,"_activateTool","RESHAPING",true),onFinished:_4.hitch(this,"_toolFinished","RESHAPING"),onApplyEdits:_4.hitch(this,"onApplyEdits")});this.addChild(this._tools.RESHAPING);}}if(this._settings.enableUndoRedo){this._createSeparator();this._tools.UNDO=new esri.dijit.editing.tools.ButtonToolBase(_4.mixin(esri.dijit.editing.tools.EditingTools.undo,{settings:this._settings,onClick:_4.hitch(this,function(){this._tools.UNDO.set("disabled",true);this._tools.REDO.set("disabled",true);this._settings.editor._undo();})}));this.addChild(this._tools.UNDO);this._tools.REDO=new esri.dijit.editing.tools.ButtonToolBase(_4.mixin(esri.dijit.editing.tools.EditingTools.redo,{settings:this._settings,onClick:_4.hitch(this,function(){this._tools.UNDO.set("disabled",true);this._tools.REDO.set("disabled",true);this._settings.editor._redo();})}));this.addChild(this._tools.REDO);}}});}}};});