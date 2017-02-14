/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.storage.CookieStorageProvider"],["require","dojox.storage.Provider"],["require","dojox.storage.manager"],["require","dojo.cookie"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.storage.CookieStorageProvider"]){_4._hasResource["dojox.storage.CookieStorageProvider"]=true;_4.provide("dojox.storage.CookieStorageProvider");_4.require("dojox.storage.Provider");_4.require("dojox.storage.manager");_4.require("dojo.cookie");_4.declare("dojox.storage.CookieStorageProvider",[_6.storage.Provider],{store:null,cookieName:"dojoxStorageCookie",storageLife:730,initialize:function(){this.store=_4.fromJson(_4.cookie(this.cookieName))||{};this.initialized=true;_6.storage.manager.loaded();},isAvailable:function(){return _4.cookie.isSupported();},put:function(_7,_8,_9,_a){this._assertIsValidKey(_7);_a=_a||this.DEFAULT_NAMESPACE;this._assertIsValidNamespace(_a);fullKey=this.getFullKey(_7,_a);this.store[fullKey]=_4.toJson(_8);this._save();var _b=_4.toJson(this.store)===_4.cookie(this.cookieName);if(!_b){this.remove(_7,_a);}if(_9){_9(_b?this.SUCCESS:this.FAILED,_7,null,_a);}},get:function(_c,_d){this._assertIsValidKey(_c);_d=_d||this.DEFAULT_NAMESPACE;this._assertIsValidNamespace(_d);_c=this.getFullKey(_c,_d);return this.store[_c]?_4.fromJson(this.store[_c]):null;},getKeys:function(_e){_e=_e||this.DEFAULT_NAMESPACE;this._assertIsValidNamespace(_e);_e="__"+_e+"_";var _f=[];for(var _10 in this.store){if(this._beginsWith(_10,_e)){_10=_10.substring(_e.length);_f.push(_10);}}return _f;},clear:function(_11){_11=_11||this.DEFAULT_NAMESPACE;this._assertIsValidNamespace(_11);_11="__"+_11+"_";for(var _12 in this.store){if(this._beginsWith(_12,_11)){delete (this.store[_12]);}}this._save();},remove:function(key,_13){_13=_13||this.DEFAULT_NAMESPACE;this._assertIsValidNamespace(_13);this._assertIsValidKey(key);key=this.getFullKey(key,_13);delete this.store[key];this._save();},getNamespaces:function(){var _14=[this.DEFAULT_NAMESPACE];var _15={};_15[this.DEFAULT_NAMESPACE]=true;var _16=/^__([^_]*)_/;for(var _17 in this.store){if(_16.test(_17)==true){var _18=_17.match(_16)[1];if(typeof _15[_18]=="undefined"){_15[_18]=true;_14.push(_18);}}}return _14;},isPermanent:function(){return true;},getMaximumSize:function(){return 4;},hasSettingsUI:function(){return false;},isValidKey:function(_19){if(_19===null||_19===undefined){return false;}return /^[0-9A-Za-z_-]*$/.test(_19);},isValidNamespace:function(_1a){if(_1a===null||_1a===undefined){return false;}return /^[0-9A-Za-z-]*$/.test(_1a);},getFullKey:function(key,_1b){return "__"+_1b+"_"+key;},_save:function(){_4.cookie(this.cookieName,_4.toJson(this.store),{expires:this.storageLife});},_beginsWith:function(_1c,_1d){if(_1d.length>_1c.length){return false;}return _1c.substring(0,_1d.length)===_1d;},_assertIsValidNamespace:function(_1e){if(this.isValidNamespace(_1e)===false){throw new Error("Invalid namespace given: "+_1e);}},_assertIsValidKey:function(key){if(this.isValidKey(key)===false){throw new Error("Invalid key given: "+key);}}});_6.storage.manager.register("dojox.storage.CookieStorageProvider",new _6.storage.CookieStorageProvider());}}};});