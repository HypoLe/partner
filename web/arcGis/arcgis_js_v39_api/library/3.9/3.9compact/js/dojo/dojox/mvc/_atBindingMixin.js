//>>built
define("dojox/mvc/_atBindingMixin","dojo/_base/array dojo/_base/lang dojo/_base/declare dojo/has dojo/Stateful ./resolve ./sync".split(" "),function(s,p,g,n,v,t,w){function x(a){var b;try{b=require("dijit/registry")}catch(d){return}for(a=a.domNode&&a.domNode.parentNode;a;){if(a=b.getEnclosingWidget(a)){var c=a._relTargetProp||"target";if((p.isFunction(a.get)?a.get(c):a[c])||c in a.constructor.prototype)return a}a=a&&a.domNode.parentNode}}function q(a,b,d,c,h){function u(){k.Two&&k.Two.unwatch();delete k.Two;
var f=e&&(p.isFunction(e.get)?e.get(g):e[g]),l=t(a,f),f=t(d,f);n("mvc-bindings-log-api")&&(!l||/^rel:/.test(a)&&!e)&&r(a,b);n("mvc-bindings-log-api")&&(!f||/^rel:/.test(d)&&!e)&&r(d,c);if(l&&f&&(!/^rel:/.test(a)&&!/^rel:/.test(d)||e))(!l.set||!l.watch)&&"*"==b?n("mvc-bindings-log-api")&&r(a,b):null==b?(p.isFunction(f.set)?f.set(c,l):f[c]=l,n("mvc-bindings-log-api")&&console.log("dojox/mvc/_atBindingMixin set "+l+" to: "+[f._setIdAttr||!f.declaredClass?f:f.declaredClass,c].join(":"))):k.Two=w(l,b,
f,c,h)}var k={},e=x(d),g=e&&e._relTargetProp||"target";u();if(e&&/^rel:/.test(a)||/^rel:/.test(d)&&p.isFunction(e.set)&&p.isFunction(e.watch))k.rel=e.watch(g,function(a,b,c){b!==c&&(n("mvc-bindings-log-api")&&console.log("Change in relative data binding target: "+e),u())});var m={};m.unwatch=m.remove=function(){for(var a in k)k[a]&&k[a].unwatch(),delete k[a]};return m}if(n("mvc-bindings-log-api"))var r=function(a,b){console.warn(b+" could not be resolved"+("string"==typeof a?" with "+a:"")+".")};
var m={dataBindAttr:"data-mvc-bindings",_dbpostscript:function(a,b){var d=this._refs=(a||{}).refs||{},c;for(c in a)if("dojox.mvc.at"==(a[c]||{}).atsignature){var h=a[c];delete a[c];d[c]=h}var h=new v,g=this;h.toString=function(){return"[Mixin value of widget "+g.declaredClass+", "+(g.id||"NO ID")+"]"};h.canConvertToLoggable=!0;this._startAtWatchHandles(h);for(c in d)void 0!==h[c]&&((a=a||{})[c]=h[c]);this._stopAtWatchHandles()},_startAtWatchHandles:function(a){this.canConvertToLoggable=!0;var b=this._refs;
if(b){var d=this._atWatchHandles=this._atWatchHandles||{};this._excludes=null;for(var c in b)b[c]&&"*"!=c&&(d[c]=q(b[c].target,b[c].targetProp,a||this,c,{bindDirection:b[c].bindDirection,converter:b[c].converter,equals:b[c].equalsCallback}));if("dojox.mvc.at"==(b["*"]||{}).atsignature)d["*"]=q(b["*"].target,b["*"].targetProp,a||this,"*",{bindDirection:b["*"].bindDirection,converter:b["*"].converter,equals:b["*"].equalsCallback})}},_stopAtWatchHandles:function(){for(var a in this._atWatchHandles)this._atWatchHandles[a].unwatch(),
delete this._atWatchHandles[a]},_setAtWatchHandle:function(a,b){if("ref"==a)throw Error(this+": 1.7 ref syntax used in conjuction with 1.8 dojox/mvc/at syntax, which is not supported.");var d=this._atWatchHandles=this._atWatchHandles||{};d[a]&&(d[a].unwatch(),delete d[a]);this._excludes=this[a]=null;this._started?d[a]=q(b.target,b.targetProp,this,a,{bindDirection:b.bindDirection,converter:b.converter,equals:b.equalsCallback}):this._refs[a]=b},_setBind:function(a){a=eval("({"+a+"})");for(var b in a){var d=
a[b];"dojox.mvc.at"!=(d||{}).atsignature?console.warn(b+" in "+dataBindAttr+" is not a data binding handle."):this._setAtWatchHandle(b,d)}},_getExcludesAttr:function(){if(this._excludes)return this._excludes;var a=[],b;for(b in this._atWatchHandles)"*"!=b&&a.push(b);return a},_getPropertiesAttr:function(){if(this.constructor._attribs)return this.constructor._attribs;var a=["onClick"].concat(this.constructor._setterAttrs);s.forEach(["id","excludes","properties","ref","binding"],function(b){b=s.indexOf(a,
b);0<=b&&a.splice(b,1)});return this.constructor._attribs=a}};m[m.dataBindAttr]="";g=g("dojox/mvc/_atBindingMixin",null,m);g.mixin=m;return g});