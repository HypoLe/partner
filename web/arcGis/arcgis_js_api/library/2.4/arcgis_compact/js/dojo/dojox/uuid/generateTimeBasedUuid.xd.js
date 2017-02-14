/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.uuid.generateTimeBasedUuid"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.uuid.generateTimeBasedUuid"]){_4._hasResource["dojox.uuid.generateTimeBasedUuid"]=true;_4.provide("dojox.uuid.generateTimeBasedUuid");_6.uuid.generateTimeBasedUuid=function(_7){var _8=_6.uuid.generateTimeBasedUuid._generator.generateUuidString(_7);return _8;};_6.uuid.generateTimeBasedUuid.isValidNode=function(_9){var _a=16;var _b=parseInt(_9,_a);var _c=_4.isString(_9)&&_9.length==12&&isFinite(_b);return _c;};_6.uuid.generateTimeBasedUuid.setNode=function(_d){_6.uuid.assert((_d===null)||this.isValidNode(_d));this._uniformNode=_d;};_6.uuid.generateTimeBasedUuid.getNode=function(){return this._uniformNode;};_6.uuid.generateTimeBasedUuid._generator=new function(){this.GREGORIAN_CHANGE_OFFSET_IN_HOURS=3394248;var _e=null;var _f=null;var _10=null;var _11=0;var _12=null;var _13=null;var _14=16;function _15(_16){_16[2]+=_16[3]>>>16;_16[3]&=65535;_16[1]+=_16[2]>>>16;_16[2]&=65535;_16[0]+=_16[1]>>>16;_16[1]&=65535;_6.uuid.assert((_16[0]>>>16)===0);};function _17(x){var _18=new Array(0,0,0,0);_18[3]=x%65536;x-=_18[3];x/=65536;_18[2]=x%65536;x-=_18[2];x/=65536;_18[1]=x%65536;x-=_18[1];x/=65536;_18[0]=x;return _18;};function _19(_1a,_1b){_6.uuid.assert(_4.isArray(_1a));_6.uuid.assert(_4.isArray(_1b));_6.uuid.assert(_1a.length==4);_6.uuid.assert(_1b.length==4);var _1c=new Array(0,0,0,0);_1c[3]=_1a[3]+_1b[3];_1c[2]=_1a[2]+_1b[2];_1c[1]=_1a[1]+_1b[1];_1c[0]=_1a[0]+_1b[0];_15(_1c);return _1c;};function _1d(_1e,_1f){_6.uuid.assert(_4.isArray(_1e));_6.uuid.assert(_4.isArray(_1f));_6.uuid.assert(_1e.length==4);_6.uuid.assert(_1f.length==4);var _20=false;if(_1e[0]*_1f[0]!==0){_20=true;}if(_1e[0]*_1f[1]!==0){_20=true;}if(_1e[0]*_1f[2]!==0){_20=true;}if(_1e[1]*_1f[0]!==0){_20=true;}if(_1e[1]*_1f[1]!==0){_20=true;}if(_1e[2]*_1f[0]!==0){_20=true;}_6.uuid.assert(!_20);var _21=new Array(0,0,0,0);_21[0]+=_1e[0]*_1f[3];_15(_21);_21[0]+=_1e[1]*_1f[2];_15(_21);_21[0]+=_1e[2]*_1f[1];_15(_21);_21[0]+=_1e[3]*_1f[0];_15(_21);_21[1]+=_1e[1]*_1f[3];_15(_21);_21[1]+=_1e[2]*_1f[2];_15(_21);_21[1]+=_1e[3]*_1f[1];_15(_21);_21[2]+=_1e[2]*_1f[3];_15(_21);_21[2]+=_1e[3]*_1f[2];_15(_21);_21[3]+=_1e[3]*_1f[3];_15(_21);return _21;};function _22(_23,_24){while(_23.length<_24){_23="0"+_23;}return _23;};function _25(){var _26=Math.floor((Math.random()%1)*Math.pow(2,32));var _27=_26.toString(_14);while(_27.length<8){_27="0"+_27;}return _27;};this.generateUuidString=function(_28){if(_28){_6.uuid.assert(_6.uuid.generateTimeBasedUuid.isValidNode(_28));}else{if(_6.uuid.generateTimeBasedUuid._uniformNode){_28=_6.uuid.generateTimeBasedUuid._uniformNode;}else{if(!_e){var _29=32768;var _2a=Math.floor((Math.random()%1)*Math.pow(2,15));var _2b=(_29|_2a).toString(_14);_e=_2b+_25();}_28=_e;}}if(!_f){var _2c=32768;var _2d=Math.floor((Math.random()%1)*Math.pow(2,14));_f=(_2c|_2d).toString(_14);}var now=new Date();var _2e=now.valueOf();var _2f=_17(_2e);if(!_12){var _30=_17(60*60);var _31=_17(_6.uuid.generateTimeBasedUuid._generator.GREGORIAN_CHANGE_OFFSET_IN_HOURS);var _32=_1d(_31,_30);var _33=_17(1000);_12=_1d(_32,_33);_13=_17(10000);}var _34=_2f;var _35=_19(_12,_34);var _36=_1d(_35,_13);if(now.valueOf()==_10){_36[3]+=_11;_15(_36);_11+=1;if(_11==10000){while(now.valueOf()==_10){now=new Date();}}}else{_10=now.valueOf();_11=1;}var _37=_36[2].toString(_14);var _38=_36[3].toString(_14);var _39=_22(_37,4)+_22(_38,4);var _3a=_36[1].toString(_14);_3a=_22(_3a,4);var _3b=_36[0].toString(_14);_3b=_22(_3b,3);var _3c="-";var _3d="1";var _3e=_39+_3c+_3a+_3c+_3d+_3b+_3c+_f+_3c+_28;_3e=_3e.toLowerCase();return _3e;};}();}}};});