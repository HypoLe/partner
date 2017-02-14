/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.editor.plugins.StatusBar"],["require","dijit.Toolbar"],["require","dijit._editor._Plugin"],["require","dojox.layout.ResizeHandle"],["require","dojo.i18n"],["requireLocalization","dojox.editor.plugins","StatusBar",null,"",""]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.editor.plugins.StatusBar"]){_4._hasResource["dojox.editor.plugins.StatusBar"]=true;_4.provide("dojox.editor.plugins.StatusBar");_4.require("dijit.Toolbar");_4.require("dijit._editor._Plugin");_4.require("dojox.layout.ResizeHandle");_4.require("dojo.i18n");_4.experimental("dojox.editor.plugins.StatusBar");_4.declare("dojox.editor.plugins._StatusBar",[_5._Widget,_5._Templated],{templateString:"<div class=\"dojoxEditorStatusBar\">"+"<table><tbody><tr>"+"<td class=\"dojoxEditorStatusBarText\" tabindex=\"-1\" aria-role=\"presentation\" aria-live=\"aggressive\"><span dojoAttachPoint=\"barContent\">&nbsp;</span></td>"+"<td><span dojoAttachPoint=\"handle\"></span></td>"+"</tr></tbody><table>"+"</div>",_getValueAttr:function(){return this.barContent.innerHTML;},_setValueAttr:function(_7){if(_7){_7=_4.trim(_7);if(!_7){_7="&nbsp;";}}else{_7="&nbsp;";}this.barContent.innerHTML=_7;}});_4.declare("dojox.editor.plugins.StatusBar",_5._editor._Plugin,{statusBar:null,resizer:true,setEditor:function(_8){this.editor=_8;this.statusBar=new _6.editor.plugins._StatusBar();if(this.resizer){this.resizeHandle=new _6.layout.ResizeHandle({targetId:this.editor,activeResize:true},this.statusBar.handle);this.resizeHandle.startup();}else{_4.style(this.statusBar.handle.parentNode,"display","none");}var _9=null;if(_8.footer.lastChild){_9="after";}_4.place(this.statusBar.domNode,_8.footer.lastChild||_8.footer,_9);this.statusBar.startup();this.editor.statusBar=this;this._msgListener=_4.subscribe(this.editor.id+"_statusBar",_4.hitch(this,this._setValueAttr));},_getValueAttr:function(){return this.statusBar.get("value");},_setValueAttr:function(_a){this.statusBar.set("value",_a);},set:function(_b,_c){if(_b){var _d="_set"+_b.charAt(0).toUpperCase()+_b.substring(1,_b.length)+"Attr";if(_4.isFunction(this[_d])){this[_d](_c);}else{this[_b]=_c;}}},get:function(_e){if(_e){var _f="_get"+_e.charAt(0).toUpperCase()+_e.substring(1,_e.length)+"Attr";var f=this[_f];if(_4.isFunction(f)){return this[_f]();}else{return this[_e];}}return null;},destroy:function(){if(this.statusBar){this.statusBar.destroy();delete this.statusBar;}if(this.resizeHandle){this.resizeHandle.destroy();delete this.resizeHandle;}if(this._msgListener){_4.unsubscribe(this._msgListener);delete this._msgListener;}delete this.editor.statusBar;}});_4.subscribe(_5._scopeName+".Editor.getPlugin",null,function(o){if(o.plugin){return;}var _10=o.args.name.toLowerCase();if(_10==="statusbar"){var _11=("resizer" in o.args)?o.args.resizer:true;o.plugin=new _6.editor.plugins.StatusBar({resizer:_11});}});}}};});