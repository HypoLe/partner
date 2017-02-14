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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.editOperation"],["require","esri.undoManager"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.editOperation"]){_4._hasResource["esri.dijit.editing.editOperation"]=true;_4.provide("esri.dijit.editing.editOperation");_4.require("esri.undoManager");_4.declare("esri.dijit.editing.Add",esri.OperationBase,{type:"edit",label:"Add Features",constructor:function(_7){_7=_7||{};if(!_7.featureLayer){console.error("In constructor of 'esri.dijit.editing.Add', featureLayer is not provided");return;}this._featureLayer=_7.featureLayer;if(!_7.addedGraphics){console.error("In constructor of 'esri.dijit.editing.Add', no graphics provided");return;}this._addedGraphics=_7.addedGraphics;},performUndo:function(){this._featureLayer.applyEdits(null,null,this._addedGraphics);},performRedo:function(){this._featureLayer.applyEdits(this._addedGraphics,null,null);}});_4.declare("esri.dijit.editing.Delete",esri.OperationBase,{type:"edit",label:"Delete Features",constructor:function(_8){_8=_8||{};this._add=new esri.dijit.editing.Add({featureLayer:_8.featureLayer,addedGraphics:_8.deletedGraphics});},performUndo:function(){this._add.performRedo();},performRedo:function(){this._add.performUndo();}});_4.declare("esri.dijit.editing.Update",esri.OperationBase,{type:"edit",label:"Update Features",constructor:function(_9){_9=_9||{};if(!_9.featureLayer){console.error("In constructor of 'esri.dijit.editing.Update', featureLayer not provided");return;}this._featureLayer=_9.featureLayer;if(!_9.preUpdatedGraphics){console.error("In constructor of 'esri.dijit.editing.Update', preUpdatedGraphics not provided");return;}this._preUpdatedGraphicsGeometries=[];this._preUpdatedGraphicsAttributes=[];for(var i=0;i<_9.preUpdatedGraphics.length;i++){this._preUpdatedGraphicsGeometries.push(_9.preUpdatedGraphics[i].geometry.toJson());this._preUpdatedGraphicsAttributes.push(_9.preUpdatedGraphics[i].attributes);}if(!_9.postUpdatedGraphics){console.error("In constructor of 'esri.dijit.editing.Update', postUpdatedGraphics not provided");return;}this._postUpdatedGraphics=_9.postUpdatedGraphics;this._postUpdatedGraphicsGeometries=[];this._postUpdatedGraphicsAttributes=[];for(i=0;i<_9.postUpdatedGraphics.length;i++){this._postUpdatedGraphicsGeometries.push(_9.postUpdatedGraphics[i].geometry.toJson());this._postUpdatedGraphicsAttributes.push(_4.clone(_9.postUpdatedGraphics[i].attributes));}},performUndo:function(){for(var i=0;i<this._postUpdatedGraphics.length;i++){this._postUpdatedGraphics[i].setGeometry(esri.geometry.fromJson(this._preUpdatedGraphicsGeometries[i]));this._postUpdatedGraphics[i].setAttributes(this._preUpdatedGraphicsAttributes[i]);}this._featureLayer.applyEdits(null,this._postUpdatedGraphics,null);},performRedo:function(){for(var i=0;i<this._postUpdatedGraphics.length;i++){this._postUpdatedGraphics[i].setGeometry(esri.geometry.fromJson(this._postUpdatedGraphicsGeometries[i]));this._postUpdatedGraphics[i].setAttributes(this._postUpdatedGraphicsAttributes[i]);}this._featureLayer.applyEdits(null,this._postUpdatedGraphics,null);}});_4.declare("esri.dijit.editing.Cut",esri.OperationBase,{type:"edit",label:"Cut Features",constructor:function(_a){_a=_a||{};if(!_a.featureLayer){console.error("In constructor of 'esri.dijit.editing.Cut', featureLayer not provided");return;}this._featureLayer=_a.featureLayer;if(!_a.addedGraphics){console.error("In constructor of 'esri.dijit.editing.Cut', addedGraphics for cut not provided");return;}this._addedGraphics=_a.addedGraphics;if(!_a.preUpdatedGraphics){console.error("In constructor of 'esri.dijit.editing.Cut', preUpdatedGraphics not provided");return;}this._preUpdatedGraphicsGeometries=[];this._preUpdatedGraphicsAttributes=[];for(var i=0;i<_a.preUpdatedGraphics.length;i++){this._preUpdatedGraphicsGeometries.push(_a.preUpdatedGraphics[i].geometry.toJson());this._preUpdatedGraphicsAttributes.push(_a.preUpdatedGraphics[i].attributes);}if(!_a.postUpdatedGraphics){console.error("In constructor of 'esri.dijit.editing.Cut', postUpdatedGraphics not provided");return;}this._postUpdatedGraphics=_a.postUpdatedGraphics;this._postUpdatedGraphicsGeometries=[];this._postUpdatedGraphicsAttributes=[];for(i=0;i<_a.postUpdatedGraphics.length;i++){this._postUpdatedGraphicsGeometries.push(_a.postUpdatedGraphics[i].geometry.toJson());this._postUpdatedGraphicsAttributes.push(_4.clone(_a.postUpdatedGraphics[i].attributes));}},performUndo:function(){for(var i=0;i<this._postUpdatedGraphics.length;i++){this._postUpdatedGraphics[i].setGeometry(esri.geometry.fromJson(this._preUpdatedGraphicsGeometries[i]));this._postUpdatedGraphics[i].setAttributes(this._preUpdatedGraphicsAttributes[i]);}this._featureLayer.applyEdits(null,this._postUpdatedGraphics,this._addedGraphics);},performRedo:function(){for(var i=0;i<this._postUpdatedGraphics.length;i++){this._postUpdatedGraphics[i].setGeometry(esri.geometry.fromJson(this._postUpdatedGraphicsGeometries[i]));this._postUpdatedGraphics[i].setAttributes(this._postUpdatedGraphicsAttributes[i]);}this._featureLayer.applyEdits(this._addedGraphics,this._postUpdatedGraphics,null);}});_4.declare("esri.dijit.editing.Union",esri.OperationBase,{type:"edit",label:"Union Features",constructor:function(_b){_b=_b||{};this._cut=new esri.dijit.editing.Cut({featureLayer:_b.featureLayer,addedGraphics:_b.deletedGraphics,preUpdatedGraphics:_b.preUpdatedGraphics,postUpdatedGraphics:_b.postUpdatedGraphics});},performUndo:function(){this._cut.performRedo();},performRedo:function(){this._cut.performUndo();}});}}};});