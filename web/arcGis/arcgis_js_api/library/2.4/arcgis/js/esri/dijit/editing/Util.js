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

if(!dojo._hasResource["esri.dijit.editing.Util"]){dojo._hasResource["esri.dijit.editing.Util"]=true;dojo.provide("esri.dijit.editing.Util");dojo.require("dojo.DeferredList");esri.dijit.editing.Util.LayerHelper={findFeatures:function(_1,_2,_3){var _4=_2.objectIdField;var _5=_2.graphics;var _6=dojo.filter(_5,function(_7){return dojo.some(_1,function(id){return _7.attributes[_4]===id.objectId;});});if(_3){_3(_6);}else{return _6;}},getSelection:function(_8){var _9=[];dojo.forEach(_8,function(_a){var _b=_a.getSelectedFeatures();dojo.forEach(_b,function(_c){_9.push(_c);});});return _9;}};}