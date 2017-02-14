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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.editing.Util"],["require","dojo.DeferredList"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.editing.Util"]){_4._hasResource["esri.dijit.editing.Util"]=true;_4.provide("esri.dijit.editing.Util");_4.require("dojo.DeferredList");esri.dijit.editing.Util.LayerHelper={findFeatures:function(_7,_8,_9){var _a=_8.objectIdField;var _b=_8.graphics;var _c=_4.filter(_b,function(_d){return _4.some(_7,function(id){return _d.attributes[_a]===id.objectId;});});if(_9){_9(_c);}else{return _c;}},getSelection:function(_e){var _f=[];_4.forEach(_e,function(_10){var _11=_10.getSelectedFeatures();_4.forEach(_11,function(_12){_f.push(_12);});});return _f;}};}}};});