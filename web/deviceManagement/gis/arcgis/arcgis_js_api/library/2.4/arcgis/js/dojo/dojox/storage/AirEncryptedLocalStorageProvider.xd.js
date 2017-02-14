/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.storage.AirEncryptedLocalStorageProvider"],["require","dojox.storage.manager"],["require","dojox.storage.Provider"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.storage.AirEncryptedLocalStorageProvider"]){_4._hasResource["dojox.storage.AirEncryptedLocalStorageProvider"]=true;_4.provide("dojox.storage.AirEncryptedLocalStorageProvider");_4.require("dojox.storage.manager");_4.require("dojox.storage.Provider");if(_4.isAIR){(function(){if(!_7){var _7={};}_7.ByteArray=window.runtime.flash.utils.ByteArray;_7.EncryptedLocalStore=window.runtime.flash.data.EncryptedLocalStore,_4.declare("dojox.storage.AirEncryptedLocalStorageProvider",[_6.storage.Provider],{initialize:function(){_6.storage.manager.loaded();},isAvailable:function(){return true;},_getItem:function(_8){var _9=_7.EncryptedLocalStore.getItem("__dojo_"+_8);return _9?_9.readUTFBytes(_9.length):"";},_setItem:function(_a,_b){var _c=new _7.ByteArray();_c.writeUTFBytes(_b);_7.EncryptedLocalStore.setItem("__dojo_"+_a,_c);},_removeItem:function(_d){_7.EncryptedLocalStore.removeItem("__dojo_"+_d);},put:function(_e,_f,_10,_11){if(this.isValidKey(_e)==false){throw new Error("Invalid key given: "+_e);}_11=_11||this.DEFAULT_NAMESPACE;if(this.isValidKey(_11)==false){throw new Error("Invalid namespace given: "+_11);}try{var _12=this._getItem("namespaces")||"|";if(_12.indexOf("|"+_11+"|")==-1){this._setItem("namespaces",_12+_11+"|");}var _13=this._getItem(_11+"_keys")||"|";if(_13.indexOf("|"+_e+"|")==-1){this._setItem(_11+"_keys",_13+_e+"|");}this._setItem("_"+_11+"_"+_e,_f);}catch(e){console.debug("dojox.storage.AirEncryptedLocalStorageProvider.put:",e);_10(this.FAILED,_e,e.toString(),_11);return;}if(_10){_10(this.SUCCESS,_e,null,_11);}},get:function(key,_14){if(this.isValidKey(key)==false){throw new Error("Invalid key given: "+key);}_14=_14||this.DEFAULT_NAMESPACE;return this._getItem("_"+_14+"_"+key);},getNamespaces:function(){var _15=[this.DEFAULT_NAMESPACE];var _16=(this._getItem("namespaces")||"|").split("|");for(var i=0;i<_16.length;i++){if(_16[i].length&&_16[i]!=this.DEFAULT_NAMESPACE){_15.push(_16[i]);}}return _15;},getKeys:function(_17){_17=_17||this.DEFAULT_NAMESPACE;if(this.isValidKey(_17)==false){throw new Error("Invalid namespace given: "+_17);}var _18=[];var _19=(this._getItem(_17+"_keys")||"|").split("|");for(var i=0;i<_19.length;i++){if(_19[i].length){_18.push(_19[i]);}}return _18;},clear:function(_1a){if(this.isValidKey(_1a)==false){throw new Error("Invalid namespace given: "+_1a);}var _1b=this._getItem("namespaces")||"|";if(_1b.indexOf("|"+_1a+"|")!=-1){this._setItem("namespaces",_1b.replace("|"+_1a+"|","|"));}var _1c=(this._getItem(_1a+"_keys")||"|").split("|");for(var i=0;i<_1c.length;i++){if(_1c[i].length){this._removeItem(_1a+"_"+_1c[i]);}}this._removeItem(_1a+"_keys");},remove:function(key,_1d){_1d=_1d||this.DEFAULT_NAMESPACE;var _1e=this._getItem(_1d+"_keys")||"|";if(_1e.indexOf("|"+key+"|")!=-1){this._setItem(_1d+"_keys",_1e.replace("|"+key+"|","|"));}this._removeItem("_"+_1d+"_"+key);},putMultiple:function(_1f,_20,_21,_22){if(this.isValidKeyArray(_1f)===false||!_20 instanceof Array||_1f.length!=_20.length){throw new Error("Invalid arguments: keys = ["+_1f+"], values = ["+_20+"]");}if(_22==null||typeof _22=="undefined"){_22=this.DEFAULT_NAMESPACE;}if(this.isValidKey(_22)==false){throw new Error("Invalid namespace given: "+_22);}this._statusHandler=_21;try{for(var i=0;i<_1f.length;i++){this.put(_1f[i],_20[i],null,_22);}}catch(e){console.debug("dojox.storage.AirEncryptedLocalStorageProvider.putMultiple:",e);if(_21){_21(this.FAILED,_1f,e.toString(),_22);}return;}if(_21){_21(this.SUCCESS,_1f,null);}},getMultiple:function(_23,_24){if(this.isValidKeyArray(_23)===false){throw new Error("Invalid key array given: "+_23);}if(_24==null||typeof _24=="undefined"){_24=this.DEFAULT_NAMESPACE;}if(this.isValidKey(_24)==false){throw new Error("Invalid namespace given: "+_24);}var _25=[];for(var i=0;i<_23.length;i++){_25[i]=this.get(_23[i],_24);}return _25;},removeMultiple:function(_26,_27){_27=_27||this.DEFAULT_NAMESPACE;for(var i=0;i<_26.length;i++){this.remove(_26[i],_27);}},isPermanent:function(){return true;},getMaximumSize:function(){return this.SIZE_NO_LIMIT;},hasSettingsUI:function(){return false;},showSettingsUI:function(){throw new Error(this.declaredClass+" does not support a storage settings user-interface");},hideSettingsUI:function(){throw new Error(this.declaredClass+" does not support a storage settings user-interface");}});_6.storage.manager.register("dojox.storage.AirEncryptedLocalStorageProvider",new _6.storage.AirEncryptedLocalStorageProvider());_6.storage.manager.initialize();})();}}}};});