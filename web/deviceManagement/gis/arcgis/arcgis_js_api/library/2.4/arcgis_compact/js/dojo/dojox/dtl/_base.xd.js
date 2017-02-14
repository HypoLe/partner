/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.dtl._base"],["require","dojox.string.Builder"],["require","dojox.string.tokenize"],["require","dojox.string.Builder"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.dtl._base"]){_4._hasResource["dojox.dtl._base"]=true;_4.provide("dojox.dtl._base");_4.require("dojox.string.Builder");_4.require("dojox.string.tokenize");_4.experimental("dojox.dtl");(function(){var dd=_6.dtl;dd.TOKEN_BLOCK=-1;dd.TOKEN_VAR=-2;dd.TOKEN_COMMENT=-3;dd.TOKEN_TEXT=3;dd._Context=_4.extend(function(_7){if(_7){_4._mixin(this,_7);if(_7.get){this._getter=_7.get;delete this.get;}}},{push:function(){var _8=this;var _9=_4.delegate(this);_9.pop=function(){return _8;};return _9;},pop:function(){throw new Error("pop() called on empty Context");},get:function(_a,_b){var n=this._normalize;if(this._getter){var _c=this._getter(_a);if(typeof _c!="undefined"){return n(_c);}}if(typeof this[_a]!="undefined"){return n(this[_a]);}return _b;},_normalize:function(_d){if(_d instanceof Date){_d.year=_d.getFullYear();_d.month=_d.getMonth()+1;_d.day=_d.getDate();_d.date=_d.year+"-"+("0"+_d.month).slice(-2)+"-"+("0"+_d.day).slice(-2);_d.hour=_d.getHours();_d.minute=_d.getMinutes();_d.second=_d.getSeconds();_d.microsecond=_d.getMilliseconds();}return _d;},update:function(_e){var _f=this.push();if(_e){_4._mixin(this,_e);}return _f;}});var _10=/("(?:[^"\\]*(?:\\.[^"\\]*)*)"|'(?:[^'\\]*(?:\\.[^'\\]*)*)'|[^\s]+)/g;var _11=/\s+/g;var _12=function(_13,_14){_13=_13||_11;if(!(_13 instanceof RegExp)){_13=new RegExp(_13,"g");}if(!_13.global){throw new Error("You must use a globally flagged RegExp with split "+_13);}_13.exec("");var _15,_16=[],_17=0,i=0;while(_15=_13.exec(this)){_16.push(this.slice(_17,_13.lastIndex-_15[0].length));_17=_13.lastIndex;if(_14&&(++i>_14-1)){break;}}_16.push(this.slice(_17));return _16;};dd.Token=function(_18,_19){this.token_type=_18;this.contents=new String(_4.trim(_19));this.contents.split=_12;this.split=function(){return String.prototype.split.apply(this.contents,arguments);};};dd.Token.prototype.split_contents=function(_1a){var bit,_1b=[],i=0;_1a=_1a||999;while(i++<_1a&&(bit=_10.exec(this.contents))){bit=bit[0];if(bit.charAt(0)=="\""&&bit.slice(-1)=="\""){_1b.push("\""+bit.slice(1,-1).replace("\\\"","\"").replace("\\\\","\\")+"\"");}else{if(bit.charAt(0)=="'"&&bit.slice(-1)=="'"){_1b.push("'"+bit.slice(1,-1).replace("\\'","'").replace("\\\\","\\")+"'");}else{_1b.push(bit);}}}return _1b;};var ddt=dd.text={_get:function(_1c,_1d,_1e){var _1f=dd.register.get(_1c,_1d.toLowerCase(),_1e);if(!_1f){if(!_1e){throw new Error("No tag found for "+_1d);}return null;}var fn=_1f[1];var _20=_1f[2];var _21;if(fn.indexOf(":")!=-1){_21=fn.split(":");fn=_21.pop();}_4["require"](_20);var _22=_4.getObject(_20);return _22[fn||_1d]||_22[_1d+"_"]||_22[fn+"_"];},getTag:function(_23,_24){return ddt._get("tag",_23,_24);},getFilter:function(_25,_26){return ddt._get("filter",_25,_26);},getTemplate:function(_27){return new dd.Template(ddt.getTemplateString(_27));},getTemplateString:function(_28){return _4._getText(_28.toString())||"";},_resolveLazy:function(_29,_2a,_2b){if(_2a){if(_2b){return _4.fromJson(_4._getText(_29))||{};}else{return dd.text.getTemplateString(_29);}}else{return _4.xhrGet({handleAs:(_2b)?"json":"text",url:_29});}},_resolveTemplateArg:function(arg,_2c){if(ddt._isTemplate(arg)){if(!_2c){var d=new _4.Deferred();d.callback(arg);return d;}return arg;}return ddt._resolveLazy(arg,_2c);},_isTemplate:function(arg){return (typeof arg=="undefined")||(typeof arg=="string"&&(arg.match(/^\s*[<{]/)||arg.indexOf(" ")!=-1));},_resolveContextArg:function(arg,_2d){if(arg.constructor==Object){if(!_2d){var d=new _4.Deferred;d.callback(arg);return d;}return arg;}return ddt._resolveLazy(arg,_2d,true);},_re:/(?:\{\{\s*(.+?)\s*\}\}|\{%\s*(load\s*)?(.+?)\s*%\})/g,tokenize:function(str){return _6.string.tokenize(str,ddt._re,ddt._parseDelims);},_parseDelims:function(_2e,_2f,tag){if(_2e){return [dd.TOKEN_VAR,_2e];}else{if(_2f){var _30=_4.trim(tag).split(/\s+/g);for(var i=0,_31;_31=_30[i];i++){_4["require"](_31);}}else{return [dd.TOKEN_BLOCK,tag];}}}};dd.Template=_4.extend(function(_32,_33){var str=_33?_32:ddt._resolveTemplateArg(_32,true)||"";var _34=ddt.tokenize(str);var _35=new dd._Parser(_34);this.nodelist=_35.parse();},{update:function(_36,_37){return ddt._resolveContextArg(_37).addCallback(this,function(_38){var _39=this.render(new dd._Context(_38));if(_36.forEach){_36.forEach(function(_3a){_3a.innerHTML=_39;});}else{_4.byId(_36).innerHTML=_39;}return this;});},render:function(_3b,_3c){_3c=_3c||this.getBuffer();_3b=_3b||new dd._Context({});return this.nodelist.render(_3b,_3c)+"";},getBuffer:function(){_4.require("dojox.string.Builder");return new _6.string.Builder();}});var _3d=/\{\{\s*(.+?)\s*\}\}/g;dd.quickFilter=function(str){if(!str){return new dd._NodeList();}if(str.indexOf("{%")==-1){return new dd._QuickNodeList(_6.string.tokenize(str,_3d,function(_3e){return new dd._Filter(_3e);}));}};dd._QuickNodeList=_4.extend(function(_3f){this.contents=_3f;},{render:function(_40,_41){for(var i=0,l=this.contents.length;i<l;i++){if(this.contents[i].resolve){_41=_41.concat(this.contents[i].resolve(_40));}else{_41=_41.concat(this.contents[i]);}}return _41;},dummyRender:function(_42){return this.render(_42,dd.Template.prototype.getBuffer()).toString();},clone:function(_43){return this;}});dd._Filter=_4.extend(function(_44){if(!_44){throw new Error("Filter must be called with variable name");}this.contents=_44;var _45=this._cache[_44];if(_45){this.key=_45[0];this.filters=_45[1];}else{this.filters=[];_6.string.tokenize(_44,this._re,this._tokenize,this);this._cache[_44]=[this.key,this.filters];}},{_cache:{},_re:/(?:^_\("([^\\"]*(?:\\.[^\\"])*)"\)|^"([^\\"]*(?:\\.[^\\"]*)*)"|^([a-zA-Z0-9_.]+)|\|(\w+)(?::(?:_\("([^\\"]*(?:\\.[^\\"])*)"\)|"([^\\"]*(?:\\.[^\\"]*)*)"|([a-zA-Z0-9_.]+)|'([^\\']*(?:\\.[^\\']*)*)'))?|^'([^\\']*(?:\\.[^\\']*)*)')/g,_values:{0:"\"",1:"\"",2:"",8:"\""},_args:{4:"\"",5:"\"",6:"",7:"'"},_tokenize:function(){var pos,arg;for(var i=0,has=[];i<arguments.length;i++){has[i]=(typeof arguments[i]!="undefined"&&typeof arguments[i]=="string"&&arguments[i]);}if(!this.key){for(pos in this._values){if(has[pos]){this.key=this._values[pos]+arguments[pos]+this._values[pos];break;}}}else{for(pos in this._args){if(has[pos]){var _46=arguments[pos];if(this._args[pos]=="'"){_46=_46.replace(/\\'/g,"'");}else{if(this._args[pos]=="\""){_46=_46.replace(/\\"/g,"\"");}}arg=[!this._args[pos],_46];break;}}var fn=ddt.getFilter(arguments[3]);if(!_4.isFunction(fn)){throw new Error(arguments[3]+" is not registered as a filter");}this.filters.push([fn,arg]);}},getExpression:function(){return this.contents;},resolve:function(_47){if(typeof this.key=="undefined"){return "";}var str=this.resolvePath(this.key,_47);for(var i=0,_48;_48=this.filters[i];i++){if(_48[1]){if(_48[1][0]){str=_48[0](str,this.resolvePath(_48[1][1],_47));}else{str=_48[0](str,_48[1][1]);}}else{str=_48[0](str);}}return str;},resolvePath:function(_49,_4a){var _4b,_4c;var _4d=_49.charAt(0);var _4e=_49.slice(-1);if(!isNaN(parseInt(_4d))){_4b=(_49.indexOf(".")==-1)?parseInt(_49):parseFloat(_49);}else{if(_4d=="\""&&_4d==_4e){_4b=_49.slice(1,-1);}else{if(_49=="true"){return true;}if(_49=="false"){return false;}if(_49=="null"||_49=="None"){return null;}_4c=_49.split(".");_4b=_4a.get(_4c[0]);if(_4.isFunction(_4b)){var _4f=_4a.getThis&&_4a.getThis();if(_4b.alters_data){_4b="";}else{if(_4f){_4b=_4b.call(_4f);}else{_4b="";}}}for(var i=1;i<_4c.length;i++){var _50=_4c[i];if(_4b){var _51=_4b;if(_4.isObject(_4b)&&_50=="items"&&typeof _4b[_50]=="undefined"){var _52=[];for(var key in _4b){_52.push([key,_4b[key]]);}_4b=_52;continue;}if(_4b.get&&_4.isFunction(_4b.get)&&_4b.get.safe){_4b=_4b.get(_50);}else{if(typeof _4b[_50]=="undefined"){_4b=_4b[_50];break;}else{_4b=_4b[_50];}}if(_4.isFunction(_4b)){if(_4b.alters_data){_4b="";}else{_4b=_4b.call(_51);}}else{if(_4b instanceof Date){_4b=dd._Context.prototype._normalize(_4b);}}}else{return "";}}}}return _4b;}});dd._TextNode=dd._Node=_4.extend(function(obj){this.contents=obj;},{set:function(_53){this.contents=_53;return this;},render:function(_54,_55){return _55.concat(this.contents);},isEmpty:function(){return !_4.trim(this.contents);},clone:function(){return this;}});dd._NodeList=_4.extend(function(_56){this.contents=_56||[];this.last="";},{push:function(_57){this.contents.push(_57);return this;},concat:function(_58){this.contents=this.contents.concat(_58);return this;},render:function(_59,_5a){for(var i=0;i<this.contents.length;i++){_5a=this.contents[i].render(_59,_5a);if(!_5a){throw new Error("Template must return buffer");}}return _5a;},dummyRender:function(_5b){return this.render(_5b,dd.Template.prototype.getBuffer()).toString();},unrender:function(){return arguments[1];},clone:function(){return this;},rtrim:function(){while(1){i=this.contents.length-1;if(this.contents[i] instanceof dd._TextNode&&this.contents[i].isEmpty()){this.contents.pop();}else{break;}}return this;}});dd._VarNode=_4.extend(function(str){this.contents=new dd._Filter(str);},{render:function(_5c,_5d){var str=this.contents.resolve(_5c);if(!str.safe){str=dd._base.escape(""+str);}return _5d.concat(str);}});dd._noOpNode=new function(){this.render=this.unrender=function(){return arguments[1];};this.clone=function(){return this;};};dd._Parser=_4.extend(function(_5e){this.contents=_5e;},{i:0,parse:function(_5f){var _60={},_61;_5f=_5f||[];for(var i=0;i<_5f.length;i++){_60[_5f[i]]=true;}var _62=new dd._NodeList();while(this.i<this.contents.length){_61=this.contents[this.i++];if(typeof _61=="string"){_62.push(new dd._TextNode(_61));}else{var _63=_61[0];var _64=_61[1];if(_63==dd.TOKEN_VAR){_62.push(new dd._VarNode(_64));}else{if(_63==dd.TOKEN_BLOCK){if(_60[_64]){--this.i;return _62;}var cmd=_64.split(/\s+/g);if(cmd.length){cmd=cmd[0];var fn=ddt.getTag(cmd);if(fn){_62.push(fn(this,new dd.Token(_63,_64)));}}}}}}if(_5f.length){throw new Error("Could not find closing tag(s): "+_5f.toString());}this.contents.length=0;return _62;},next_token:function(){var _65=this.contents[this.i++];return new dd.Token(_65[0],_65[1]);},delete_first_token:function(){this.i++;},skip_past:function(_66){while(this.i<this.contents.length){var _67=this.contents[this.i++];if(_67[0]==dd.TOKEN_BLOCK&&_67[1]==_66){return;}}throw new Error("Unclosed tag found when looking for "+_66);},create_variable_node:function(_68){return new dd._VarNode(_68);},create_text_node:function(_69){return new dd._TextNode(_69||"");},getTemplate:function(_6a){return new dd.Template(_6a);}});dd.register={_registry:{attributes:[],tags:[],filters:[]},get:function(_6b,_6c){var _6d=dd.register._registry[_6b+"s"];for(var i=0,_6e;_6e=_6d[i];i++){if(typeof _6e[0]=="string"){if(_6e[0]==_6c){return _6e;}}else{if(_6c.match(_6e[0])){return _6e;}}}},getAttributeTags:function(){var _6f=[];var _70=dd.register._registry.attributes;for(var i=0,_71;_71=_70[i];i++){if(_71.length==3){_6f.push(_71);}else{var fn=_4.getObject(_71[1]);if(fn&&_4.isFunction(fn)){_71.push(fn);_6f.push(_71);}}}return _6f;},_any:function(_72,_73,_74){for(var _75 in _74){for(var i=0,fn;fn=_74[_75][i];i++){var key=fn;if(_4.isArray(fn)){key=fn[0];fn=fn[1];}if(typeof key=="string"){if(key.substr(0,5)=="attr:"){var _76=fn;if(_76.substr(0,5)=="attr:"){_76=_76.slice(5);}dd.register._registry.attributes.push([_76.toLowerCase(),_73+"."+_75+"."+_76]);}key=key.toLowerCase();}dd.register._registry[_72].push([key,fn,_73+"."+_75]);}}},tags:function(_77,_78){dd.register._any("tags",_77,_78);},filters:function(_79,_7a){dd.register._any("filters",_79,_7a);}};var _7b=/&/g;var _7c=/</g;var _7d=/>/g;var _7e=/'/g;var _7f=/"/g;dd._base.escape=function(_80){return dd.mark_safe(_80.replace(_7b,"&amp;").replace(_7c,"&lt;").replace(_7d,"&gt;").replace(_7f,"&quot;").replace(_7e,"&#39;"));};dd._base.safe=function(_81){if(typeof _81=="string"){_81=new String(_81);}if(typeof _81=="object"){_81.safe=true;}return _81;};dd.mark_safe=dd._base.safe;dd.register.tags("dojox.dtl.tag",{"date":["now"],"logic":["if","for","ifequal","ifnotequal"],"loader":["extends","block","include","load","ssi"],"misc":["comment","debug","filter","firstof","spaceless","templatetag","widthratio","with"],"loop":["cycle","ifchanged","regroup"]});dd.register.filters("dojox.dtl.filter",{"dates":["date","time","timesince","timeuntil"],"htmlstrings":["linebreaks","linebreaksbr","removetags","striptags"],"integers":["add","get_digit"],"lists":["dictsort","dictsortreversed","first","join","length","length_is","random","slice","unordered_list"],"logic":["default","default_if_none","divisibleby","yesno"],"misc":["filesizeformat","pluralize","phone2numeric","pprint"],"strings":["addslashes","capfirst","center","cut","fix_ampersands","floatformat","iriencode","linenumbers","ljust","lower","make_list","rjust","slugify","stringformat","title","truncatewords","truncatewords_html","upper","urlencode","urlize","urlizetrunc","wordcount","wordwrap"]});dd.register.filters("dojox.dtl",{"_base":["escape","safe"]});})();}}};});