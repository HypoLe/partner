/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["require","dojox.analytics._base"],["provide","dojox.analytics.plugins.mouseOver"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.analytics.plugins.mouseOver"]){_4._hasResource["dojox.analytics.plugins.mouseOver"]=true;_4.require("dojox.analytics._base");_4.provide("dojox.analytics.plugins.mouseOver");_6.analytics.plugins.mouseOver=new (function(){this.watchMouse=_4.config["watchMouseOver"]||true;this.mouseSampleDelay=_4.config["sampleDelay"]||2500;this.addData=_4.hitch(_6.analytics,"addData","mouseOver");this.targetProps=_4.config["targetProps"]||["id","className","localName","href","spellcheck","lang","textContent","value"];this.toggleWatchMouse=function(){if(this._watchingMouse){_4.disconnect(this._watchingMouse);delete this._watchingMouse;return;}_4.connect(_4.doc,"onmousemove",this,"sampleMouse");};if(this.watchMouse){_4.connect(_4.doc,"onmouseover",this,"toggleWatchMouse");_4.connect(_4.doc,"onmouseout",this,"toggleWatchMouse");}this.sampleMouse=function(e){if(!this._rateLimited){this.addData("sample",this.trimMouseEvent(e));this._rateLimited=true;setTimeout(_4.hitch(this,function(){if(this._rateLimited){this.trimMouseEvent(this._lastMouseEvent);delete this._lastMouseEvent;delete this._rateLimited;}}),this.mouseSampleDelay);}this._lastMouseEvent=e;return e;};this.trimMouseEvent=function(e){var t={};for(var i in e){switch(i){case "target":var _7=this.targetProps;t[i]={};for(var j=0;j<_7.length;j++){if(_4.isObject(e[i])&&_7[j] in e[i]){if(_7[j]=="text"||_7[j]=="textContent"){if(e[i]["localName"]&&(e[i]["localName"]!="HTML")&&(e[i]["localName"]!="BODY")){t[i][_7[j]]=e[i][_7[j]].substr(0,50);}}else{t[i][_7[j]]=e[i][_7[j]];}}}break;case "x":case "y":if(e[i]){var _8=e[i];t[i]=_8+"";}break;default:break;}}return t;};})();}}};});