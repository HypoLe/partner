/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.data.css"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.data.css"]){_4._hasResource["dojox.data.css"]=true;_4.provide("dojox.data.css");_6.data.css.rules={};_6.data.css.rules.forEach=function(fn,_7,_8){if(_8){var _9=function(_a){_4.forEach(_a[_a.cssRules?"cssRules":"rules"],function(_b){if(!_b.type||_b.type!==3){var _c="";if(_a&&_a.href){_c=_a.href;}fn.call(_7?_7:this,_b,_a,_c);}});};_4.forEach(_8,_9);}};_6.data.css.findStyleSheets=function(_d){var _e=[];var _f=function(_10){var s=_6.data.css.findStyleSheet(_10);if(s){_4.forEach(s,function(_11){if(_4.indexOf(_e,_11)===-1){_e.push(_11);}});}};_4.forEach(_d,_f);return _e;};_6.data.css.findStyleSheet=function(_12){var _13=[];if(_12.charAt(0)==="."){_12=_12.substring(1);}var _14=function(_15){if(_15.href&&_15.href.match(_12)){_13.push(_15);return true;}if(_15.imports){return _4.some(_15.imports,function(_16){return _14(_16);});}return _4.some(_15[_15.cssRules?"cssRules":"rules"],function(_17){if(_17.type&&_17.type===3&&_14(_17.styleSheet)){return true;}return false;});};_4.some(document.styleSheets,_14);return _13;};_6.data.css.determineContext=function(_18){var ret=[];if(_18&&_18.length>0){_18=_6.data.css.findStyleSheets(_18);}else{_18=document.styleSheets;}var _19=function(_1a){ret.push(_1a);if(_1a.imports){_4.forEach(_1a.imports,function(_1b){_19(_1b);});}_4.forEach(_1a[_1a.cssRules?"cssRules":"rules"],function(_1c){if(_1c.type&&_1c.type===3){_19(_1c.styleSheet);}});};_4.forEach(_18,_19);return ret;};}}};});