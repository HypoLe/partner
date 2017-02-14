/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.charting.themes.gradientGenerator"],["require","dojox.charting.Theme"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.charting.themes.gradientGenerator"]){_4._hasResource["dojox.charting.themes.gradientGenerator"]=true;_4.provide("dojox.charting.themes.gradientGenerator");_4.require("dojox.charting.Theme");(function(){var gg=_6.charting.themes.gradientGenerator;gg.generateFills=function(_7,_8,_9,_a){var _b=_6.charting.Theme;return _4.map(_7,function(c){return _b.generateHslGradient(c,_8,_9,_a);});};gg.updateFills=function(_c,_d,_e,_f){var _10=_6.charting.Theme;_4.forEach(_c,function(t){if(t.fill&&!t.fill.type){t.fill=_10.generateHslGradient(t.fill,_d,_e,_f);}});};gg.generateMiniTheme=function(_11,_12,_13,_14,_15){var _16=_6.charting.Theme;return _4.map(_11,function(c){c=new _6.color.Color(c);return {fill:_16.generateHslGradient(c,_12,_13,_14),stroke:{color:_16.generateHslColor(c,_15)}};});};gg.generateGradientByIntensity=function(_17,_18){_17=new _4.Color(_17);return _4.map(_18,function(_19){var s=_19.i/255;return {offset:_19.o,color:new _4.Color([_17.r*s,_17.g*s,_17.b*s,_17.a])};});};})();}}};});