/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.html.metrics"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.html.metrics"]){_4._hasResource["dojox.html.metrics"]=true;_4.provide("dojox.html.metrics");(function(){var _7=_6.html.metrics;_7.getFontMeasurements=function(){var _8={"1em":0,"1ex":0,"100%":0,"12pt":0,"16px":0,"xx-small":0,"x-small":0,"small":0,"medium":0,"large":0,"x-large":0,"xx-large":0};if(_4.isIE){_4.doc.documentElement.style.fontSize="100%";}var _9=_4.doc.createElement("div");var ds=_9.style;ds.position="absolute";ds.left="-100px";ds.top="0";ds.width="30px";ds.height="1000em";ds.borderWidth="0";ds.margin="0";ds.padding="0";ds.outline="0";ds.lineHeight="1";ds.overflow="hidden";_4.body().appendChild(_9);for(var p in _8){ds.fontSize=p;_8[p]=Math.round(_9.offsetHeight*12/16)*16/12/1000;}_4.body().removeChild(_9);_9=null;return _8;};var _a=null;_7.getCachedFontMeasurements=function(_b){if(_b||!_a){_a=_7.getFontMeasurements();}return _a;};var _c=null,_d={};_7.getTextBox=function(_e,_f,_10){var m,s;if(!_c){m=_c=_4.doc.createElement("div");var c=_4.doc.createElement("div");c.appendChild(m);s=c.style;s.overflow="scroll";s.position="absolute";s.left="0px";s.top="-10000px";s.width="1px";s.height="1px";s.visibility="hidden";s.borderWidth="0";s.margin="0";s.padding="0";s.outline="0";_4.body().appendChild(c);}else{m=_c;}m.className="";s=m.style;s.borderWidth="0";s.margin="0";s.padding="0";s.outline="0";if(arguments.length>1&&_f){for(var i in _f){if(i in _d){continue;}s[i]=_f[i];}}if(arguments.length>2&&_10){m.className=_10;}m.innerHTML=_e;var box=_4.position(m);box.w=m.parentNode.scrollWidth;return box;};var _11={w:16,h:16};_7.getScrollbar=function(){return {w:_11.w,h:_11.h};};_7._fontResizeNode=null;_7.initOnFontResize=function(_12){var f=_7._fontResizeNode=_4.doc.createElement("iframe");var fs=f.style;fs.position="absolute";fs.width="5em";fs.height="10em";fs.top="-10000px";if(_4.isIE){f.onreadystatechange=function(){if(f.contentWindow.document.readyState=="complete"){f.onresize=f.contentWindow.parent[_6._scopeName].html.metrics._fontresize;}};}else{f.onload=function(){f.contentWindow.onresize=f.contentWindow.parent[_6._scopeName].html.metrics._fontresize;};}f.setAttribute("src","javascript:'<html><head><script>if(\"loadFirebugConsole\" in window){window.loadFirebugConsole();}</script></head><body></body></html>'");_4.body().appendChild(f);_7.initOnFontResize=function(){};};_7.onFontResize=function(){};_7._fontresize=function(){_7.onFontResize();};_4.addOnUnload(function(){var f=_7._fontResizeNode;if(f){if(_4.isIE&&f.onresize){f.onresize=null;}else{if(f.contentWindow&&f.contentWindow.onresize){f.contentWindow.onresize=null;}}_7._fontResizeNode=null;}});_4.addOnLoad(function(){try{var n=_4.doc.createElement("div");n.style.cssText="top:0;left:0;width:100px;height:100px;overflow:scroll;position:absolute;visibility:hidden;";_4.body().appendChild(n);_11.w=n.offsetWidth-n.clientWidth;_11.h=n.offsetHeight-n.clientHeight;_4.body().removeChild(n);delete n;}catch(e){}if("fontSizeWatch" in _4.config&&!!_4.config.fontSizeWatch){_7.initOnFontResize();}});})();}}};});