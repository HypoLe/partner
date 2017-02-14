/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.storage.FlashStorageProvider"],["require","dojox.flash"],["require","dojox.storage.manager"],["require","dojox.storage.Provider"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.storage.FlashStorageProvider"]){_4._hasResource["dojox.storage.FlashStorageProvider"]=true;_4.provide("dojox.storage.FlashStorageProvider");_4.require("dojox.flash");_4.require("dojox.storage.manager");_4.require("dojox.storage.Provider");_4.declare("dojox.storage.FlashStorageProvider",_6.storage.Provider,{initialized:false,_available:null,_statusHandler:null,_flashReady:false,_pageReady:false,initialize:function(){if(_4.config["disableFlashStorage"]==true){return;}_6.flash.addLoadedListener(_4.hitch(this,function(){this._flashReady=true;if(this._flashReady&&this._pageReady){this._loaded();}}));var _7=_4.moduleUrl("dojox","storage/Storage.swf").toString();_6.flash.setSwf(_7,false);_4.connect(_4,"loaded",this,function(){this._pageReady=true;if(this._flashReady&&this._pageReady){this._loaded();}});},setFlushDelay:function(_8){if(_8===null||typeof _8==="undefined"||isNaN(_8)){throw new Error("Invalid argunment: "+_8);}_6.flash.comm.setFlushDelay(String(_8));},getFlushDelay:function(){return Number(_6.flash.comm.getFlushDelay());},flush:function(_9){if(_9==null||typeof _9=="undefined"){_9=_6.storage.DEFAULT_NAMESPACE;}_6.flash.comm.flush(_9);},isAvailable:function(){return (this._available=!_4.config["disableFlashStorage"]);},put:function(_a,_b,_c,_d){if(!this.isValidKey(_a)){throw new Error("Invalid key given: "+_a);}if(!_d){_d=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_d)){throw new Error("Invalid namespace given: "+_d);}this._statusHandler=_c;if(_4.isString(_b)){_b="string:"+_b;}else{_b=_4.toJson(_b);}_6.flash.comm.put(_a,_b,_d);},putMultiple:function(_e,_f,_10,_11){if(!this.isValidKeyArray(_e)||!_f instanceof Array||_e.length!=_f.length){throw new Error("Invalid arguments: keys = ["+_e+"], values = ["+_f+"]");}if(!_11){_11=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_11)){throw new Error("Invalid namespace given: "+_11);}this._statusHandler=_10;var _12=_e.join(",");var _13=[];for(var i=0;i<_f.length;i++){if(_4.isString(_f[i])){_f[i]="string:"+_f[i];}else{_f[i]=_4.toJson(_f[i]);}_13[i]=_f[i].length;}var _14=_f.join("");var _15=_13.join(",");_6.flash.comm.putMultiple(_12,_14,_15,_11);},get:function(key,_16){if(!this.isValidKey(key)){throw new Error("Invalid key given: "+key);}if(!_16){_16=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_16)){throw new Error("Invalid namespace given: "+_16);}var _17=_6.flash.comm.get(key,_16);if(_17==""){return null;}return this._destringify(_17);},getMultiple:function(_18,_19){if(!this.isValidKeyArray(_18)){throw new ("Invalid key array given: "+_18);}if(!_19){_19=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_19)){throw new Error("Invalid namespace given: "+_19);}var _1a=_18.join(",");var _1b=_6.flash.comm.getMultiple(_1a,_19);var _1c=eval("("+_1b+")");for(var i=0;i<_1c.length;i++){_1c[i]=(_1c[i]=="")?null:this._destringify(_1c[i]);}return _1c;},_destringify:function(_1d){if(_4.isString(_1d)&&(/^string:/.test(_1d))){_1d=_1d.substring("string:".length);}else{_1d=_4.fromJson(_1d);}return _1d;},getKeys:function(_1e){if(!_1e){_1e=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_1e)){throw new Error("Invalid namespace given: "+_1e);}var _1f=_6.flash.comm.getKeys(_1e);if(_1f==null||_1f=="null"){_1f="";}_1f=_1f.split(",");_1f.sort();return _1f;},getNamespaces:function(){var _20=_6.flash.comm.getNamespaces();if(_20==null||_20=="null"){_20=_6.storage.DEFAULT_NAMESPACE;}_20=_20.split(",");_20.sort();return _20;},clear:function(_21){if(!_21){_21=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_21)){throw new Error("Invalid namespace given: "+_21);}_6.flash.comm.clear(_21);},remove:function(key,_22){if(!_22){_22=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_22)){throw new Error("Invalid namespace given: "+_22);}_6.flash.comm.remove(key,_22);},removeMultiple:function(_23,_24){if(!this.isValidKeyArray(_23)){_4.raise("Invalid key array given: "+_23);}if(!_24){_24=_6.storage.DEFAULT_NAMESPACE;}if(!this.isValidKey(_24)){throw new Error("Invalid namespace given: "+_24);}var _25=_23.join(",");_6.flash.comm.removeMultiple(_25,_24);},isPermanent:function(){return true;},getMaximumSize:function(){return _6.storage.SIZE_NO_LIMIT;},hasSettingsUI:function(){return true;},showSettingsUI:function(){_6.flash.comm.showSettings();_6.flash.obj.setVisible(true);_6.flash.obj.center();},hideSettingsUI:function(){_6.flash.obj.setVisible(false);if(_4.isFunction(_6.storage.onHideSettingsUI)){_6.storage.onHideSettingsUI.call(null);}},getResourceList:function(){return [];},_loaded:function(){this._allNamespaces=this.getNamespaces();this.initialized=true;_6.storage.manager.loaded();},_onStatus:function(_26,key,_27){var ds=_6.storage;var dfo=_6.flash.obj;if(_26==ds.PENDING){dfo.center();dfo.setVisible(true);}else{dfo.setVisible(false);}if(ds._statusHandler){ds._statusHandler.call(null,_26,key,null,_27);}}});_6.storage.manager.register("dojox.storage.FlashStorageProvider",new _6.storage.FlashStorageProvider());}}};});