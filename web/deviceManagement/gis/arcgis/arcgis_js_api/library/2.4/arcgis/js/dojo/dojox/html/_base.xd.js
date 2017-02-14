/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.html._base"],["require","dojo.html"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.html._base"]){_4._hasResource["dojox.html._base"]=true;_4.provide("dojox.html._base");_4.require("dojo.html");(function(){if(_4.isIE){var _7=/(AlphaImageLoader\([^)]*?src=(['"]))(?![a-z]+:|\/)([^\r\n;}]+?)(\2[^)]*\)\s*[;}]?)/g;}var _8=/(?:(?:@import\s*(['"])(?![a-z]+:|\/)([^\r\n;{]+?)\1)|url\(\s*(['"]?)(?![a-z]+:|\/)([^\r\n;]+?)\3\s*\))([a-z, \s]*[;}]?)/g;var _9=_6.html._adjustCssPaths=function(_a,_b){if(!_b||!_a){return;}if(_7){_b=_b.replace(_7,function(_c,_d,_e,_f,_10){return _d+(new _4._Url(_a,"./"+_f).toString())+_10;});}return _b.replace(_8,function(_11,_12,_13,_14,_15,_16){if(_13){return "@import \""+(new _4._Url(_a,"./"+_13).toString())+"\""+_16;}else{return "url("+(new _4._Url(_a,"./"+_15).toString())+")"+_16;}});};var _17=/(<[a-z][a-z0-9]*\s[^>]*)(?:(href|src)=(['"]?)([^>]*?)\3|style=(['"]?)([^>]*?)\5)([^>]*>)/gi;var _18=_6.html._adjustHtmlPaths=function(_19,_1a){var url=_19||"./";return _1a.replace(_17,function(tag,_1b,_1c,_1d,_1e,_1f,_20,end){return _1b+(_1c?(_1c+"="+_1d+(new _4._Url(url,_1e).toString())+_1d):("style="+_1f+_9(url,_20)+_1f))+end;});};var _21=_6.html._snarfStyles=function(_22,_23,_24){_24.attributes=[];return _23.replace(/(?:<style([^>]*)>([\s\S]*?)<\/style>|<link\s+(?=[^>]*rel=['"]?stylesheet)([^>]*?href=(['"])([^>]*?)\4[^>\/]*)\/?>)/gi,function(_25,_26,_27,_28,_29,_2a){var i,_2b=(_26||_28||"").replace(/^\s*([\s\S]*?)\s*$/i,"$1");if(_27){i=_24.push(_22?_9(_22,_27):_27);}else{i=_24.push("@import \""+_2a+"\";");_2b=_2b.replace(/\s*(?:rel|href)=(['"])?[^\s]*\1\s*/gi,"");}if(_2b){_2b=_2b.split(/\s+/);var _2c={},tmp;for(var j=0,e=_2b.length;j<e;j++){tmp=_2b[j].split("=");_2c[tmp[0]]=tmp[1].replace(/^\s*['"]?([\s\S]*?)['"]?\s*$/,"$1");}_24.attributes[i-1]=_2c;}return "";});};var _2d=_6.html._snarfScripts=function(_2e,_2f){_2f.code="";_2e=_2e.replace(/<[!][-][-](.|\s)*?[-][-]>/g,function(_30){return _30.replace(/<(\/?)script\b/ig,"&lt;$1Script");});function _31(src){if(_2f.downloadRemote){src=src.replace(/&([a-z0-9#]+);/g,function(m,_32){switch(_32){case "amp":return "&";case "gt":return ">";case "lt":return "<";default:return _32.charAt(0)=="#"?String.fromCharCode(_32.substring(1)):"&"+_32+";";}});_4.xhrGet({url:src,sync:true,load:function(_33){_2f.code+=_33+";";},error:_2f.errBack});}};return _2e.replace(/<script\s*(?![^>]*type=['"]?(?:dojo\/|text\/html\b))(?:[^>]*?(?:src=(['"]?)([^>]*?)\1[^>]*)?)*>([\s\S]*?)<\/script>/gi,function(_34,_35,src,_36){if(src){_31(src);}else{_2f.code+=_36;}return "";});};var _37=_6.html.evalInGlobal=function(_38,_39){_39=_39||_4.doc.body;var n=_39.ownerDocument.createElement("script");n.type="text/javascript";_39.appendChild(n);n.text=_38;};_4.declare("dojox.html._ContentSetter",[_4.html._ContentSetter],{adjustPaths:false,referencePath:".",renderStyles:false,executeScripts:false,scriptHasHooks:false,scriptHookReplacement:null,_renderStyles:function(_3a){this._styleNodes=[];var st,att,_3b,doc=this.node.ownerDocument;var _3c=doc.getElementsByTagName("head")[0];for(var i=0,e=_3a.length;i<e;i++){_3b=_3a[i];att=_3a.attributes[i];st=doc.createElement("style");st.setAttribute("type","text/css");for(var x in att){st.setAttribute(x,att[x]);}this._styleNodes.push(st);_3c.appendChild(st);if(st.styleSheet){st.styleSheet.cssText=_3b;}else{st.appendChild(doc.createTextNode(_3b));}}},empty:function(){this.inherited("empty",arguments);this._styles=[];},onBegin:function(){this.inherited("onBegin",arguments);var _3d=this.content,_3e=this.node;var _3f=this._styles;if(_4.isString(_3d)){if(this.adjustPaths&&this.referencePath){_3d=_18(this.referencePath,_3d);}if(this.renderStyles||this.cleanContent){_3d=_21(this.referencePath,_3d,_3f);}if(this.executeScripts){var _40=this;var _41={downloadRemote:true,errBack:function(e){_40._onError.call(_40,"Exec","Error downloading remote script in \""+_40.id+"\"",e);}};_3d=_2d(_3d,_41);this._code=_41.code;}}this.content=_3d;},onEnd:function(){var _42=this._code,_43=this._styles;if(this._styleNodes&&this._styleNodes.length){while(this._styleNodes.length){_4.destroy(this._styleNodes.pop());}}if(this.renderStyles&&_43&&_43.length){this._renderStyles(_43);}if(this.executeScripts&&_42){if(this.cleanContent){_42=_42.replace(/(<!--|(?:\/\/)?-->|<!\[CDATA\[|\]\]>)/g,"");}if(this.scriptHasHooks){_42=_42.replace(/_container_(?!\s*=[^=])/g,this.scriptHookReplacement);}try{_37(_42,this.node);}catch(e){this._onError("Exec","Error eval script in "+this.id+", "+e.message,e);}}this.inherited("onEnd",arguments);},tearDown:function(){this.inherited(arguments);delete this._styles;if(this._styleNodes&&this._styleNodes.length){while(this._styleNodes.length){_4.destroy(this._styleNodes.pop());}}delete this._styleNodes;_4.mixin(this,_4.getObject(this.declaredClass).prototype);}});_6.html.set=function(_44,_45,_46){if(!_46){return _4.html._setNodeContent(_44,_45,true);}else{var op=new _6.html._ContentSetter(_4.mixin(_46,{content:_45,node:_44}));return op.set();}};})();}}};});