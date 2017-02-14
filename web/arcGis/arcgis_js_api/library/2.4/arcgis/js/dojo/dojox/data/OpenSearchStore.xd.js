/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.data.OpenSearchStore"],["require","dojo.data.util.simpleFetch"],["require","dojox.xml.DomParser"],["require","dojox.xml.parser"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.data.OpenSearchStore"]){_4._hasResource["dojox.data.OpenSearchStore"]=true;_4.provide("dojox.data.OpenSearchStore");_4.require("dojo.data.util.simpleFetch");_4.require("dojox.xml.DomParser");_4.require("dojox.xml.parser");_4.experimental("dojox.data.OpenSearchStore");_4.declare("dojox.data.OpenSearchStore",null,{constructor:function(_7){if(_7){this.label=_7.label;this.url=_7.url;this.itemPath=_7.itemPath;if("urlPreventCache" in _7){this.urlPreventCache=_7.urlPreventCache?true:false;}}var _8=_4.xhrGet({url:this.url,handleAs:"xml",sync:true,preventCache:this.urlPreventCache});_8.addCallback(this,"_processOsdd");_8.addErrback(function(){throw new Error("Unable to load OpenSearch Description document from ".args.url);});},url:"",itemPath:"",_storeRef:"_S",urlElement:null,iframeElement:null,urlPreventCache:true,ATOM_CONTENT_TYPE:3,ATOM_CONTENT_TYPE_STRING:"atom",RSS_CONTENT_TYPE:2,RSS_CONTENT_TYPE_STRING:"rss",XML_CONTENT_TYPE:1,XML_CONTENT_TYPE_STRING:"xml",_assertIsItem:function(_9){if(!this.isItem(_9)){throw new Error("dojox.data.OpenSearchStore: a function was passed an item argument that was not an item");}},_assertIsAttribute:function(_a){if(typeof _a!=="string"){throw new Error("dojox.data.OpenSearchStore: a function was passed an attribute argument that was not an attribute name string");}},getFeatures:function(){return {"dojo.data.api.Read":true};},getValue:function(_b,_c,_d){var _e=this.getValues(_b,_c);if(_e){return _e[0];}return _d;},getAttributes:function(_f){return ["content"];},hasAttribute:function(_10,_11){if(this.getValue(_10,_11)){return true;}return false;},isItemLoaded:function(_12){return this.isItem(_12);},loadItem:function(_13){},getLabel:function(_14){return undefined;},getLabelAttributes:function(_15){return null;},containsValue:function(_16,_17,_18){var _19=this.getValues(_16,_17);for(var i=0;i<_19.length;i++){if(_19[i]===_18){return true;}}return false;},getValues:function(_1a,_1b){this._assertIsItem(_1a);this._assertIsAttribute(_1b);var _1c=this.processItem(_1a,_1b);if(_1c){return [_1c];}return undefined;},isItem:function(_1d){if(_1d&&_1d[this._storeRef]===this){return true;}return false;},close:function(_1e){},process:function(_1f){return this["_processOSD"+this.contentType](_1f);},processItem:function(_20,_21){return this["_processItem"+this.contentType](_20.node,_21);},_createSearchUrl:function(_22){var _23=this.urlElement.attributes.getNamedItem("template").nodeValue;var _24=this.urlElement.attributes;var _25=_23.indexOf("{searchTerms}");_23=_23.substring(0,_25)+_22.query.searchTerms+_23.substring(_25+13);_4.forEach([{"name":"count","test":_22.count,"def":"10"},{"name":"startIndex","test":_22.start,"def":this.urlElement.attributes.getNamedItem("indexOffset")?this.urlElement.attributes.getNamedItem("indexOffset").nodeValue:0},{"name":"startPage","test":_22.startPage,"def":this.urlElement.attributes.getNamedItem("pageOffset")?this.urlElement.attributes.getNamedItem("pageOffset").nodeValue:0},{"name":"language","test":_22.language,"def":"*"},{"name":"inputEncoding","test":_22.inputEncoding,"def":"UTF-8"},{"name":"outputEncoding","test":_22.outputEncoding,"def":"UTF-8"}],function(_26){_23=_23.replace("{"+_26.name+"}",_26.test||_26.def);_23=_23.replace("{"+_26.name+"?}",_26.test||_26.def);});return _23;},_fetchItems:function(_27,_28,_29){if(!_27.query){_27.query={};}var _2a=this;var url=this._createSearchUrl(_27);var _2b={url:url,preventCache:this.urlPreventCache};var xhr=_4.xhrGet(_2b);xhr.addErrback(function(_2c){_29(_2c,_27);});xhr.addCallback(function(_2d){var _2e=[];if(_2d){_2e=_2a.process(_2d);for(var i=0;i<_2e.length;i++){_2e[i]={node:_2e[i]};_2e[i][_2a._storeRef]=_2a;}}_28(_2e,_27);});},_processOSDxml:function(_2f){var div=_4.doc.createElement("div");div.innerHTML=_2f;return _4.query(this.itemPath,div);},_processItemxml:function(_30,_31){if(_31==="content"){return _30.innerHTML;}return undefined;},_processOSDatom:function(_32){return this._processOSDfeed(_32,"entry");},_processItematom:function(_33,_34){return this._processItemfeed(_33,_34,"content");},_processOSDrss:function(_35){return this._processOSDfeed(_35,"item");},_processItemrss:function(_36,_37){return this._processItemfeed(_36,_37,"description");},_processOSDfeed:function(_38,_39){_38=_6.xml.parser.parse(_38);var _3a=[];var _3b=_38.getElementsByTagName(_39);for(var i=0;i<_3b.length;i++){_3a.push(_3b.item(i));}return _3a;},_processItemfeed:function(_3c,_3d,_3e){if(_3d==="content"){var _3f=_3c.getElementsByTagName(_3e).item(0);return this._getNodeXml(_3f,true);}return undefined;},_getNodeXml:function(_40,_41){var i;switch(_40.nodeType){case 1:var xml=[];if(!_41){xml.push("<"+_40.tagName);var _42;for(i=0;i<_40.attributes.length;i++){_42=_40.attributes.item(i);xml.push(" "+_42.nodeName+"=\""+_42.nodeValue+"\"");}xml.push(">");}for(i=0;i<_40.childNodes.length;i++){xml.push(this._getNodeXml(_40.childNodes.item(i)));}if(!_41){xml.push("</"+_40.tagName+">\n");}return xml.join("");case 3:case 4:return _40.nodeValue;}return undefined;},_processOsdd:function(doc){var _43=doc.getElementsByTagName("Url");var _44=[];var _45;var i;for(i=0;i<_43.length;i++){_45=_43[i].attributes.getNamedItem("type").nodeValue;switch(_45){case "application/rss+xml":_44[i]=this.RSS_CONTENT_TYPE;break;case "application/atom+xml":_44[i]=this.ATOM_CONTENT_TYPE;break;default:_44[i]=this.XML_CONTENT_TYPE;break;}}var _46=0;var _47=_44[0];for(i=1;i<_43.length;i++){if(_44[i]>_47){_46=i;_47=_44[i];}}var _48=_43[_46].nodeName.toLowerCase();if(_48=="url"){var _49=_43[_46].attributes;this.urlElement=_43[_46];switch(_44[_46]){case this.ATOM_CONTENT_TYPE:this.contentType=this.ATOM_CONTENT_TYPE_STRING;break;case this.RSS_CONTENT_TYPE:this.contentType=this.RSS_CONTENT_TYPE_STRING;break;case this.XML_CONTENT_TYPE:this.contentType=this.XML_CONTENT_TYPE_STRING;break;}}}});_4.extend(_6.data.OpenSearchStore,_4.data.util.simpleFetch);}}};});