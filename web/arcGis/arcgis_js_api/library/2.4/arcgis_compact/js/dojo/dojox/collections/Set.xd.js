/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.collections.Set"],["require","dojox.collections.ArrayList"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.collections.Set"]){_4._hasResource["dojox.collections.Set"]=true;_4.provide("dojox.collections.Set");_4.require("dojox.collections.ArrayList");(function(){var _7=_6.collections;_7.Set=new (function(){function _8(_9){if(_9.constructor==Array){return new _6.collections.ArrayList(_9);}return _9;};this.union=function(_a,_b){_a=_8(_a);_b=_8(_b);var _c=new _6.collections.ArrayList(_a.toArray());var e=_b.getIterator();while(!e.atEnd()){var _d=e.get();if(!_c.contains(_d)){_c.add(_d);}}return _c;};this.intersection=function(_e,_f){_e=_8(_e);_f=_8(_f);var _10=new _6.collections.ArrayList();var e=_f.getIterator();while(!e.atEnd()){var _11=e.get();if(_e.contains(_11)){_10.add(_11);}}return _10;};this.difference=function(_12,_13){_12=_8(_12);_13=_8(_13);var _14=new _6.collections.ArrayList();var e=_12.getIterator();while(!e.atEnd()){var _15=e.get();if(!_13.contains(_15)){_14.add(_15);}}return _14;};this.isSubSet=function(_16,_17){_16=_8(_16);_17=_8(_17);var e=_16.getIterator();while(!e.atEnd()){if(!_17.contains(e.get())){return false;}}return true;};this.isSuperSet=function(_18,_19){_18=_8(_18);_19=_8(_19);var e=_19.getIterator();while(!e.atEnd()){if(!_18.contains(e.get())){return false;}}return true;};})();})();}}};});