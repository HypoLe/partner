/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.data.AndOrWriteStore"],["require","dojox.data.AndOrReadStore"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.data.AndOrWriteStore"]){_4._hasResource["dojox.data.AndOrWriteStore"]=true;_4.provide("dojox.data.AndOrWriteStore");_4.require("dojox.data.AndOrReadStore");_4.declare("dojox.data.AndOrWriteStore",_6.data.AndOrReadStore,{constructor:function(_7){this._features["dojo.data.api.Write"]=true;this._features["dojo.data.api.Notification"]=true;this._pending={_newItems:{},_modifiedItems:{},_deletedItems:{}};if(!this._datatypeMap["Date"].serialize){this._datatypeMap["Date"].serialize=function(_8){return _4.date.stamp.toISOString(_8,{zulu:true});};}if(_7&&(_7.referenceIntegrity===false)){this.referenceIntegrity=false;}this._saveInProgress=false;},referenceIntegrity:true,_assert:function(_9){if(!_9){throw new Error("assertion failed in ItemFileWriteStore");}},_getIdentifierAttribute:function(){var _a=this.getFeatures()["dojo.data.api.Identity"];return _a;},newItem:function(_b,_c){this._assert(!this._saveInProgress);if(!this._loadFinished){this._forceLoad();}if(typeof _b!="object"&&typeof _b!="undefined"){throw new Error("newItem() was passed something other than an object");}var _d=null;var _e=this._getIdentifierAttribute();if(_e===Number){_d=this._arrayOfAllItems.length;}else{_d=_b[_e];if(typeof _d==="undefined"){throw new Error("newItem() was not passed an identity for the new item");}if(_4.isArray(_d)){throw new Error("newItem() was not passed an single-valued identity");}}if(this._itemsByIdentity){this._assert(typeof this._itemsByIdentity[_d]==="undefined");}this._assert(typeof this._pending._newItems[_d]==="undefined");this._assert(typeof this._pending._deletedItems[_d]==="undefined");var _f={};_f[this._storeRefPropName]=this;_f[this._itemNumPropName]=this._arrayOfAllItems.length;if(this._itemsByIdentity){this._itemsByIdentity[_d]=_f;_f[_e]=[_d];}this._arrayOfAllItems.push(_f);var _10=null;if(_c&&_c.parent&&_c.attribute){_10={item:_c.parent,attribute:_c.attribute,oldValue:undefined};var _11=this.getValues(_c.parent,_c.attribute);if(_11&&_11.length>0){var _12=_11.slice(0,_11.length);if(_11.length===1){_10.oldValue=_11[0];}else{_10.oldValue=_11.slice(0,_11.length);}_12.push(_f);this._setValueOrValues(_c.parent,_c.attribute,_12,false);_10.newValue=this.getValues(_c.parent,_c.attribute);}else{this._setValueOrValues(_c.parent,_c.attribute,_f,false);_10.newValue=_f;}}else{_f[this._rootItemPropName]=true;this._arrayOfTopLevelItems.push(_f);}this._pending._newItems[_d]=_f;for(var key in _b){if(key===this._storeRefPropName||key===this._itemNumPropName){throw new Error("encountered bug in ItemFileWriteStore.newItem");}var _13=_b[key];if(!_4.isArray(_13)){_13=[_13];}_f[key]=_13;if(this.referenceIntegrity){for(var i=0;i<_13.length;i++){var val=_13[i];if(this.isItem(val)){this._addReferenceToMap(val,_f,key);}}}}this.onNew(_f,_10);return _f;},_removeArrayElement:function(_14,_15){var _16=_4.indexOf(_14,_15);if(_16!=-1){_14.splice(_16,1);return true;}return false;},deleteItem:function(_17){this._assert(!this._saveInProgress);this._assertIsItem(_17);var _18=_17[this._itemNumPropName];var _19=this.getIdentity(_17);if(this.referenceIntegrity){var _1a=this.getAttributes(_17);if(_17[this._reverseRefMap]){_17["backup_"+this._reverseRefMap]=_4.clone(_17[this._reverseRefMap]);}_4.forEach(_1a,function(_1b){_4.forEach(this.getValues(_17,_1b),function(_1c){if(this.isItem(_1c)){if(!_17["backupRefs_"+this._reverseRefMap]){_17["backupRefs_"+this._reverseRefMap]=[];}_17["backupRefs_"+this._reverseRefMap].push({id:this.getIdentity(_1c),attr:_1b});this._removeReferenceFromMap(_1c,_17,_1b);}},this);},this);var _1d=_17[this._reverseRefMap];if(_1d){for(var _1e in _1d){var _1f=null;if(this._itemsByIdentity){_1f=this._itemsByIdentity[_1e];}else{_1f=this._arrayOfAllItems[_1e];}if(_1f){for(var _20 in _1d[_1e]){var _21=this.getValues(_1f,_20)||[];var _22=_4.filter(_21,function(_23){return !(this.isItem(_23)&&this.getIdentity(_23)==_19);},this);this._removeReferenceFromMap(_17,_1f,_20);if(_22.length<_21.length){this._setValueOrValues(_1f,_20,_22);}}}}}}this._arrayOfAllItems[_18]=null;_17[this._storeRefPropName]=null;if(this._itemsByIdentity){delete this._itemsByIdentity[_19];}this._pending._deletedItems[_19]=_17;if(_17[this._rootItemPropName]){this._removeArrayElement(this._arrayOfTopLevelItems,_17);}this.onDelete(_17);return true;},setValue:function(_24,_25,_26){return this._setValueOrValues(_24,_25,_26,true);},setValues:function(_27,_28,_29){return this._setValueOrValues(_27,_28,_29,true);},unsetAttribute:function(_2a,_2b){return this._setValueOrValues(_2a,_2b,[],true);},_setValueOrValues:function(_2c,_2d,_2e,_2f){this._assert(!this._saveInProgress);this._assertIsItem(_2c);this._assert(_4.isString(_2d));this._assert(typeof _2e!=="undefined");var _30=this._getIdentifierAttribute();if(_2d==_30){throw new Error("ItemFileWriteStore does not have support for changing the value of an item's identifier.");}var _31=this._getValueOrValues(_2c,_2d);var _32=this.getIdentity(_2c);if(!this._pending._modifiedItems[_32]){var _33={};for(var key in _2c){if((key===this._storeRefPropName)||(key===this._itemNumPropName)||(key===this._rootItemPropName)){_33[key]=_2c[key];}else{if(key===this._reverseRefMap){_33[key]=_4.clone(_2c[key]);}else{_33[key]=_2c[key].slice(0,_2c[key].length);}}}this._pending._modifiedItems[_32]=_33;}var _34=false;if(_4.isArray(_2e)&&_2e.length===0){_34=delete _2c[_2d];_2e=undefined;if(this.referenceIntegrity&&_31){var _35=_31;if(!_4.isArray(_35)){_35=[_35];}for(var i=0;i<_35.length;i++){var _36=_35[i];if(this.isItem(_36)){this._removeReferenceFromMap(_36,_2c,_2d);}}}}else{var _37;if(_4.isArray(_2e)){var _38=_2e;_37=_2e.slice(0,_2e.length);}else{_37=[_2e];}if(this.referenceIntegrity){if(_31){var _35=_31;if(!_4.isArray(_35)){_35=[_35];}var map={};_4.forEach(_35,function(_39){if(this.isItem(_39)){var id=this.getIdentity(_39);map[id.toString()]=true;}},this);_4.forEach(_37,function(_3a){if(this.isItem(_3a)){var id=this.getIdentity(_3a);if(map[id.toString()]){delete map[id.toString()];}else{this._addReferenceToMap(_3a,_2c,_2d);}}},this);for(var rId in map){var _3b;if(this._itemsByIdentity){_3b=this._itemsByIdentity[rId];}else{_3b=this._arrayOfAllItems[rId];}this._removeReferenceFromMap(_3b,_2c,_2d);}}else{for(var i=0;i<_37.length;i++){var _36=_37[i];if(this.isItem(_36)){this._addReferenceToMap(_36,_2c,_2d);}}}}_2c[_2d]=_37;_34=true;}if(_2f){this.onSet(_2c,_2d,_31,_2e);}return _34;},_addReferenceToMap:function(_3c,_3d,_3e){var _3f=this.getIdentity(_3d);var _40=_3c[this._reverseRefMap];if(!_40){_40=_3c[this._reverseRefMap]={};}var _41=_40[_3f];if(!_41){_41=_40[_3f]={};}_41[_3e]=true;},_removeReferenceFromMap:function(_42,_43,_44){var _45=this.getIdentity(_43);var _46=_42[this._reverseRefMap];var _47;if(_46){for(_47 in _46){if(_47==_45){delete _46[_47][_44];if(this._isEmpty(_46[_47])){delete _46[_47];}}}if(this._isEmpty(_46)){delete _42[this._reverseRefMap];}}},_dumpReferenceMap:function(){var i;for(i=0;i<this._arrayOfAllItems.length;i++){var _48=this._arrayOfAllItems[i];if(_48&&_48[this._reverseRefMap]){console.log("Item: ["+this.getIdentity(_48)+"] is referenced by: "+_4.toJson(_48[this._reverseRefMap]));}}},_getValueOrValues:function(_49,_4a){var _4b=undefined;if(this.hasAttribute(_49,_4a)){var _4c=this.getValues(_49,_4a);if(_4c.length==1){_4b=_4c[0];}else{_4b=_4c;}}return _4b;},_flatten:function(_4d){if(this.isItem(_4d)){var _4e=_4d;var _4f=this.getIdentity(_4e);var _50={_reference:_4f};return _50;}else{if(typeof _4d==="object"){for(var _51 in this._datatypeMap){var _52=this._datatypeMap[_51];if(_4.isObject(_52)&&!_4.isFunction(_52)){if(_4d instanceof _52.type){if(!_52.serialize){throw new Error("ItemFileWriteStore:  No serializer defined for type mapping: ["+_51+"]");}return {_type:_51,_value:_52.serialize(_4d)};}}else{if(_4d instanceof _52){return {_type:_51,_value:_4d.toString()};}}}}return _4d;}},_getNewFileContentString:function(){var _53={};var _54=this._getIdentifierAttribute();if(_54!==Number){_53.identifier=_54;}if(this._labelAttr){_53.label=this._labelAttr;}_53.items=[];for(var i=0;i<this._arrayOfAllItems.length;++i){var _55=this._arrayOfAllItems[i];if(_55!==null){var _56={};for(var key in _55){if(key!==this._storeRefPropName&&key!==this._itemNumPropName&&key!==this._reverseRefMap&&key!==this._rootItemPropName){var _57=key;var _58=this.getValues(_55,_57);if(_58.length==1){_56[_57]=this._flatten(_58[0]);}else{var _59=[];for(var j=0;j<_58.length;++j){_59.push(this._flatten(_58[j]));_56[_57]=_59;}}}}_53.items.push(_56);}}var _5a=true;return _4.toJson(_53,_5a);},_isEmpty:function(_5b){var _5c=true;if(_4.isObject(_5b)){var i;for(i in _5b){_5c=false;break;}}else{if(_4.isArray(_5b)){if(_5b.length>0){_5c=false;}}}return _5c;},save:function(_5d){this._assert(!this._saveInProgress);this._saveInProgress=true;var _5e=this;var _5f=function(){_5e._pending={_newItems:{},_modifiedItems:{},_deletedItems:{}};_5e._saveInProgress=false;if(_5d&&_5d.onComplete){var _60=_5d.scope||_4.global;_5d.onComplete.call(_60);}};var _61=function(){_5e._saveInProgress=false;if(_5d&&_5d.onError){var _62=_5d.scope||_4.global;_5d.onError.call(_62);}};if(this._saveEverything){var _63=this._getNewFileContentString();this._saveEverything(_5f,_61,_63);}if(this._saveCustom){this._saveCustom(_5f,_61);}if(!this._saveEverything&&!this._saveCustom){_5f();}},revert:function(){this._assert(!this._saveInProgress);var _64;for(_64 in this._pending._modifiedItems){var _65=this._pending._modifiedItems[_64];var _66=null;if(this._itemsByIdentity){_66=this._itemsByIdentity[_64];}else{_66=this._arrayOfAllItems[_64];}_65[this._storeRefPropName]=this;for(key in _66){delete _66[key];}_4.mixin(_66,_65);}var _67;for(_64 in this._pending._deletedItems){_67=this._pending._deletedItems[_64];_67[this._storeRefPropName]=this;var _68=_67[this._itemNumPropName];if(_67["backup_"+this._reverseRefMap]){_67[this._reverseRefMap]=_67["backup_"+this._reverseRefMap];delete _67["backup_"+this._reverseRefMap];}this._arrayOfAllItems[_68]=_67;if(this._itemsByIdentity){this._itemsByIdentity[_64]=_67;}if(_67[this._rootItemPropName]){this._arrayOfTopLevelItems.push(_67);}}for(_64 in this._pending._deletedItems){_67=this._pending._deletedItems[_64];if(_67["backupRefs_"+this._reverseRefMap]){_4.forEach(_67["backupRefs_"+this._reverseRefMap],function(_69){var _6a;if(this._itemsByIdentity){_6a=this._itemsByIdentity[_69.id];}else{_6a=this._arrayOfAllItems[_69.id];}this._addReferenceToMap(_6a,_67,_69.attr);},this);delete _67["backupRefs_"+this._reverseRefMap];}}for(_64 in this._pending._newItems){var _6b=this._pending._newItems[_64];_6b[this._storeRefPropName]=null;this._arrayOfAllItems[_6b[this._itemNumPropName]]=null;if(_6b[this._rootItemPropName]){this._removeArrayElement(this._arrayOfTopLevelItems,_6b);}if(this._itemsByIdentity){delete this._itemsByIdentity[_64];}}this._pending={_newItems:{},_modifiedItems:{},_deletedItems:{}};return true;},isDirty:function(_6c){if(_6c){var _6d=this.getIdentity(_6c);return new Boolean(this._pending._newItems[_6d]||this._pending._modifiedItems[_6d]||this._pending._deletedItems[_6d]).valueOf();}else{if(!this._isEmpty(this._pending._newItems)||!this._isEmpty(this._pending._modifiedItems)||!this._isEmpty(this._pending._deletedItems)){return true;}return false;}},onSet:function(_6e,_6f,_70,_71){},onNew:function(_72,_73){},onDelete:function(_74){},close:function(_75){if(this.clearOnClose){if(!this.isDirty()){this.inherited(arguments);}else{throw new Error("dojox.data.AndOrWriteStore: There are unsaved changes present in the store.  Please save or revert the changes before invoking close.");}}}});}}};});