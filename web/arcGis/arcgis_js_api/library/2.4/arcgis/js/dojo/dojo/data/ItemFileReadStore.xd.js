/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojo.data.ItemFileReadStore"],["require","dojo.data.util.filter"],["require","dojo.data.util.simpleFetch"],["require","dojo.date.stamp"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojo.data.ItemFileReadStore"]){_4._hasResource["dojo.data.ItemFileReadStore"]=true;_4.provide("dojo.data.ItemFileReadStore");_4.require("dojo.data.util.filter");_4.require("dojo.data.util.simpleFetch");_4.require("dojo.date.stamp");_4.declare("dojo.data.ItemFileReadStore",null,{constructor:function(_7){this._arrayOfAllItems=[];this._arrayOfTopLevelItems=[];this._loadFinished=false;this._jsonFileUrl=_7.url;this._ccUrl=_7.url;this.url=_7.url;this._jsonData=_7.data;this.data=null;this._datatypeMap=_7.typeMap||{};if(!this._datatypeMap["Date"]){this._datatypeMap["Date"]={type:Date,deserialize:function(_8){return _4.date.stamp.fromISOString(_8);}};}this._features={"dojo.data.api.Read":true,"dojo.data.api.Identity":true};this._itemsByIdentity=null;this._storeRefPropName="_S";this._itemNumPropName="_0";this._rootItemPropName="_RI";this._reverseRefMap="_RRM";this._loadInProgress=false;this._queuedFetches=[];if(_7.urlPreventCache!==undefined){this.urlPreventCache=_7.urlPreventCache?true:false;}if(_7.hierarchical!==undefined){this.hierarchical=_7.hierarchical?true:false;}if(_7.clearOnClose){this.clearOnClose=true;}if("failOk" in _7){this.failOk=_7.failOk?true:false;}},url:"",_ccUrl:"",data:null,typeMap:null,clearOnClose:false,urlPreventCache:false,failOk:false,hierarchical:true,_assertIsItem:function(_9){if(!this.isItem(_9)){throw new Error("dojo.data.ItemFileReadStore: Invalid item argument.");}},_assertIsAttribute:function(_a){if(typeof _a!=="string"){throw new Error("dojo.data.ItemFileReadStore: Invalid attribute argument.");}},getValue:function(_b,_c,_d){var _e=this.getValues(_b,_c);return (_e.length>0)?_e[0]:_d;},getValues:function(_f,_10){this._assertIsItem(_f);this._assertIsAttribute(_10);return (_f[_10]||[]).slice(0);},getAttributes:function(_11){this._assertIsItem(_11);var _12=[];for(var key in _11){if((key!==this._storeRefPropName)&&(key!==this._itemNumPropName)&&(key!==this._rootItemPropName)&&(key!==this._reverseRefMap)){_12.push(key);}}return _12;},hasAttribute:function(_13,_14){this._assertIsItem(_13);this._assertIsAttribute(_14);return (_14 in _13);},containsValue:function(_15,_16,_17){var _18=undefined;if(typeof _17==="string"){_18=_4.data.util.filter.patternToRegExp(_17,false);}return this._containsValue(_15,_16,_17,_18);},_containsValue:function(_19,_1a,_1b,_1c){return _4.some(this.getValues(_19,_1a),function(_1d){if(_1d!==null&&!_4.isObject(_1d)&&_1c){if(_1d.toString().match(_1c)){return true;}}else{if(_1b===_1d){return true;}}});},isItem:function(_1e){if(_1e&&_1e[this._storeRefPropName]===this){if(this._arrayOfAllItems[_1e[this._itemNumPropName]]===_1e){return true;}}return false;},isItemLoaded:function(_1f){return this.isItem(_1f);},loadItem:function(_20){this._assertIsItem(_20.item);},getFeatures:function(){return this._features;},getLabel:function(_21){if(this._labelAttr&&this.isItem(_21)){return this.getValue(_21,this._labelAttr);}return undefined;},getLabelAttributes:function(_22){if(this._labelAttr){return [this._labelAttr];}return null;},_fetchItems:function(_23,_24,_25){var _26=this,_27=function(_28,_29){var _2a=[],i,key;if(_28.query){var _2b,_2c=_28.queryOptions?_28.queryOptions.ignoreCase:false;var _2d={};for(key in _28.query){_2b=_28.query[key];if(typeof _2b==="string"){_2d[key]=_4.data.util.filter.patternToRegExp(_2b,_2c);}else{if(_2b instanceof RegExp){_2d[key]=_2b;}}}for(i=0;i<_29.length;++i){var _2e=true;var _2f=_29[i];if(_2f===null){_2e=false;}else{for(key in _28.query){_2b=_28.query[key];if(!_26._containsValue(_2f,key,_2b,_2d[key])){_2e=false;}}}if(_2e){_2a.push(_2f);}}_24(_2a,_28);}else{for(i=0;i<_29.length;++i){var _30=_29[i];if(_30!==null){_2a.push(_30);}}_24(_2a,_28);}};if(this._loadFinished){_27(_23,this._getItemsArray(_23.queryOptions));}else{if(this._jsonFileUrl!==this._ccUrl){_4.deprecated("dojo.data.ItemFileReadStore: ","To change the url, set the url property of the store,"+" not _jsonFileUrl.  _jsonFileUrl support will be removed in 2.0");this._ccUrl=this._jsonFileUrl;this.url=this._jsonFileUrl;}else{if(this.url!==this._ccUrl){this._jsonFileUrl=this.url;this._ccUrl=this.url;}}if(this.data!=null){this._jsonData=this.data;this.data=null;}if(this._jsonFileUrl){if(this._loadInProgress){this._queuedFetches.push({args:_23,filter:_27});}else{this._loadInProgress=true;var _31={url:_26._jsonFileUrl,handleAs:"json-comment-optional",preventCache:this.urlPreventCache,failOk:this.failOk};var _32=_4.xhrGet(_31);_32.addCallback(function(_33){try{_26._getItemsFromLoadedData(_33);_26._loadFinished=true;_26._loadInProgress=false;_27(_23,_26._getItemsArray(_23.queryOptions));_26._handleQueuedFetches();}catch(e){_26._loadFinished=true;_26._loadInProgress=false;_25(e,_23);}});_32.addErrback(function(_34){_26._loadInProgress=false;_25(_34,_23);});var _35=null;if(_23.abort){_35=_23.abort;}_23.abort=function(){var df=_32;if(df&&df.fired===-1){df.cancel();df=null;}if(_35){_35.call(_23);}};}}else{if(this._jsonData){try{this._loadFinished=true;this._getItemsFromLoadedData(this._jsonData);this._jsonData=null;_27(_23,this._getItemsArray(_23.queryOptions));}catch(e){_25(e,_23);}}else{_25(new Error("dojo.data.ItemFileReadStore: No JSON source data was provided as either URL or a nested Javascript object."),_23);}}}},_handleQueuedFetches:function(){if(this._queuedFetches.length>0){for(var i=0;i<this._queuedFetches.length;i++){var _36=this._queuedFetches[i],_37=_36.args,_38=_36.filter;if(_38){_38(_37,this._getItemsArray(_37.queryOptions));}else{this.fetchItemByIdentity(_37);}}this._queuedFetches=[];}},_getItemsArray:function(_39){if(_39&&_39.deep){return this._arrayOfAllItems;}return this._arrayOfTopLevelItems;},close:function(_3a){if(this.clearOnClose&&this._loadFinished&&!this._loadInProgress){if(((this._jsonFileUrl==""||this._jsonFileUrl==null)&&(this.url==""||this.url==null))&&this.data==null){console.debug("dojo.data.ItemFileReadStore: WARNING!  Data reload "+" information has not been provided."+"  Please set 'url' or 'data' to the appropriate value before"+" the next fetch");}this._arrayOfAllItems=[];this._arrayOfTopLevelItems=[];this._loadFinished=false;this._itemsByIdentity=null;this._loadInProgress=false;this._queuedFetches=[];}},_getItemsFromLoadedData:function(_3b){var _3c=false,_3d=this;function _3e(_3f){var _40=((_3f!==null)&&(typeof _3f==="object")&&(!_4.isArray(_3f)||_3c)&&(!_4.isFunction(_3f))&&(_3f.constructor==Object||_4.isArray(_3f))&&(typeof _3f._reference==="undefined")&&(typeof _3f._type==="undefined")&&(typeof _3f._value==="undefined")&&_3d.hierarchical);return _40;};function _41(_42){_3d._arrayOfAllItems.push(_42);for(var _43 in _42){var _44=_42[_43];if(_44){if(_4.isArray(_44)){var _45=_44;for(var k=0;k<_45.length;++k){var _46=_45[k];if(_3e(_46)){_41(_46);}}}else{if(_3e(_44)){_41(_44);}}}}};this._labelAttr=_3b.label;var i,_47;this._arrayOfAllItems=[];this._arrayOfTopLevelItems=_3b.items;for(i=0;i<this._arrayOfTopLevelItems.length;++i){_47=this._arrayOfTopLevelItems[i];if(_4.isArray(_47)){_3c=true;}_41(_47);_47[this._rootItemPropName]=true;}var _48={},key;for(i=0;i<this._arrayOfAllItems.length;++i){_47=this._arrayOfAllItems[i];for(key in _47){if(key!==this._rootItemPropName){var _49=_47[key];if(_49!==null){if(!_4.isArray(_49)){_47[key]=[_49];}}else{_47[key]=[null];}}_48[key]=key;}}while(_48[this._storeRefPropName]){this._storeRefPropName+="_";}while(_48[this._itemNumPropName]){this._itemNumPropName+="_";}while(_48[this._reverseRefMap]){this._reverseRefMap+="_";}var _4a;var _4b=_3b.identifier;if(_4b){this._itemsByIdentity={};this._features["dojo.data.api.Identity"]=_4b;for(i=0;i<this._arrayOfAllItems.length;++i){_47=this._arrayOfAllItems[i];_4a=_47[_4b];var _4c=_4a[0];if(!Object.hasOwnProperty.call(this._itemsByIdentity,_4c)){this._itemsByIdentity[_4c]=_47;}else{if(this._jsonFileUrl){throw new Error("dojo.data.ItemFileReadStore:  The json data as specified by: ["+this._jsonFileUrl+"] is malformed.  Items within the list have identifier: ["+_4b+"].  Value collided: ["+_4c+"]");}else{if(this._jsonData){throw new Error("dojo.data.ItemFileReadStore:  The json data provided by the creation arguments is malformed.  Items within the list have identifier: ["+_4b+"].  Value collided: ["+_4c+"]");}}}}}else{this._features["dojo.data.api.Identity"]=Number;}for(i=0;i<this._arrayOfAllItems.length;++i){_47=this._arrayOfAllItems[i];_47[this._storeRefPropName]=this;_47[this._itemNumPropName]=i;}for(i=0;i<this._arrayOfAllItems.length;++i){_47=this._arrayOfAllItems[i];for(key in _47){_4a=_47[key];for(var j=0;j<_4a.length;++j){_49=_4a[j];if(_49!==null&&typeof _49=="object"){if(("_type" in _49)&&("_value" in _49)){var _4d=_49._type;var _4e=this._datatypeMap[_4d];if(!_4e){throw new Error("dojo.data.ItemFileReadStore: in the typeMap constructor arg, no object class was specified for the datatype '"+_4d+"'");}else{if(_4.isFunction(_4e)){_4a[j]=new _4e(_49._value);}else{if(_4.isFunction(_4e.deserialize)){_4a[j]=_4e.deserialize(_49._value);}else{throw new Error("dojo.data.ItemFileReadStore: Value provided in typeMap was neither a constructor, nor a an object with a deserialize function");}}}}if(_49._reference){var _4f=_49._reference;if(!_4.isObject(_4f)){_4a[j]=this._getItemByIdentity(_4f);}else{for(var k=0;k<this._arrayOfAllItems.length;++k){var _50=this._arrayOfAllItems[k],_51=true;for(var _52 in _4f){if(_50[_52]!=_4f[_52]){_51=false;}}if(_51){_4a[j]=_50;}}}if(this.referenceIntegrity){var _53=_4a[j];if(this.isItem(_53)){this._addReferenceToMap(_53,_47,key);}}}else{if(this.isItem(_49)){if(this.referenceIntegrity){this._addReferenceToMap(_49,_47,key);}}}}}}}},_addReferenceToMap:function(_54,_55,_56){},getIdentity:function(_57){var _58=this._features["dojo.data.api.Identity"];if(_58===Number){return _57[this._itemNumPropName];}else{var _59=_57[_58];if(_59){return _59[0];}}return null;},fetchItemByIdentity:function(_5a){var _5b,_5c;if(!this._loadFinished){var _5d=this;if(this._jsonFileUrl!==this._ccUrl){_4.deprecated("dojo.data.ItemFileReadStore: ","To change the url, set the url property of the store,"+" not _jsonFileUrl.  _jsonFileUrl support will be removed in 2.0");this._ccUrl=this._jsonFileUrl;this.url=this._jsonFileUrl;}else{if(this.url!==this._ccUrl){this._jsonFileUrl=this.url;this._ccUrl=this.url;}}if(this.data!=null&&this._jsonData==null){this._jsonData=this.data;this.data=null;}if(this._jsonFileUrl){if(this._loadInProgress){this._queuedFetches.push({args:_5a});}else{this._loadInProgress=true;var _5e={url:_5d._jsonFileUrl,handleAs:"json-comment-optional",preventCache:this.urlPreventCache,failOk:this.failOk};var _5f=_4.xhrGet(_5e);_5f.addCallback(function(_60){var _61=_5a.scope?_5a.scope:_4.global;try{_5d._getItemsFromLoadedData(_60);_5d._loadFinished=true;_5d._loadInProgress=false;_5b=_5d._getItemByIdentity(_5a.identity);if(_5a.onItem){_5a.onItem.call(_61,_5b);}_5d._handleQueuedFetches();}catch(error){_5d._loadInProgress=false;if(_5a.onError){_5a.onError.call(_61,error);}}});_5f.addErrback(function(_62){_5d._loadInProgress=false;if(_5a.onError){var _63=_5a.scope?_5a.scope:_4.global;_5a.onError.call(_63,_62);}});}}else{if(this._jsonData){_5d._getItemsFromLoadedData(_5d._jsonData);_5d._jsonData=null;_5d._loadFinished=true;_5b=_5d._getItemByIdentity(_5a.identity);if(_5a.onItem){_5c=_5a.scope?_5a.scope:_4.global;_5a.onItem.call(_5c,_5b);}}}}else{_5b=this._getItemByIdentity(_5a.identity);if(_5a.onItem){_5c=_5a.scope?_5a.scope:_4.global;_5a.onItem.call(_5c,_5b);}}},_getItemByIdentity:function(_64){var _65=null;if(this._itemsByIdentity&&Object.hasOwnProperty.call(this._itemsByIdentity,_64)){_65=this._itemsByIdentity[_64];}else{if(Object.hasOwnProperty.call(this._arrayOfAllItems,_64)){_65=this._arrayOfAllItems[_64];}}if(_65===undefined){_65=null;}return _65;},getIdentityAttributes:function(_66){var _67=this._features["dojo.data.api.Identity"];if(_67===Number){return null;}else{return [_67];}},_forceLoad:function(){var _68=this;if(this._jsonFileUrl!==this._ccUrl){_4.deprecated("dojo.data.ItemFileReadStore: ","To change the url, set the url property of the store,"+" not _jsonFileUrl.  _jsonFileUrl support will be removed in 2.0");this._ccUrl=this._jsonFileUrl;this.url=this._jsonFileUrl;}else{if(this.url!==this._ccUrl){this._jsonFileUrl=this.url;this._ccUrl=this.url;}}if(this.data!=null){this._jsonData=this.data;this.data=null;}if(this._jsonFileUrl){var _69={url:this._jsonFileUrl,handleAs:"json-comment-optional",preventCache:this.urlPreventCache,failOk:this.failOk,sync:true};var _6a=_4.xhrGet(_69);_6a.addCallback(function(_6b){try{if(_68._loadInProgress!==true&&!_68._loadFinished){_68._getItemsFromLoadedData(_6b);_68._loadFinished=true;}else{if(_68._loadInProgress){throw new Error("dojo.data.ItemFileReadStore:  Unable to perform a synchronous load, an async load is in progress.");}}}catch(e){console.log(e);throw e;}});_6a.addErrback(function(_6c){throw _6c;});}else{if(this._jsonData){_68._getItemsFromLoadedData(_68._jsonData);_68._jsonData=null;_68._loadFinished=true;}}}});_4.extend(_4.data.ItemFileReadStore,_4.data.util.simpleFetch);}}};});