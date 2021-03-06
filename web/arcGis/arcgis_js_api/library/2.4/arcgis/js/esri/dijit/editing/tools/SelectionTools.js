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

if(!dojo._hasResource["esri.dijit.editing.tools.SelectionTools"]){dojo._hasResource["esri.dijit.editing.tools.SelectionTools"]=true;dojo.require("esri.dijit.editing.tools.ButtonToolBase");dojo.provide("esri.dijit.editing.tools.SelectionTools");esri.dijit.editing.tools.SelectionTools={select:{id:"btnNewSelection",_enabledIcon:"toolbarIcon newSelectionIcon",_disabledIcon:"toolbarIcon newSelectionIcon",_drawType:esri.toolbars.Draw.EXTENT,_selectMethod:esri.layers.FeatureLayer.SELECTION_NEW,_label:"NLS_selectionNewLbl"},selectadd:{id:"btnAddToSelection",_enabledIcon:"toolbarIcon addToSelectionIcon",_disabledIcon:"toolbarIcon addToSelectionIcon",_drawType:esri.toolbars.Draw.EXTENT,_selectMethod:esri.layers.FeatureLayer.SELECTION_ADD,_label:"NLS_selectionAddLbl"},selectremove:{id:"btnSubtractFromSelection",_enabledIcon:"toolbarIcon removeFromSelectionIcon",_disabledIcon:"toolbarIcon removeFromSelectionIcon",_drawType:esri.toolbars.Draw.EXTENT,_selectMethod:esri.layers.FeatureLayer.SELECTION_SUBTRACT,_label:"NLS_selectionRemoveLbl"},selectClear:{id:"btnClearSelection",_enabledIcon:"toolbarIcon clearSelectionIcon",_disabledIcon:"toolbarIcon clearSelectionIcon",_enabled:false,_label:"NLS_selectionClearLbl"}};}