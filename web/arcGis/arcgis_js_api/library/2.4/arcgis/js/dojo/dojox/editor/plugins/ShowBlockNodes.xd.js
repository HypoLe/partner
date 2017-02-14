/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.editor.plugins.ShowBlockNodes"],["require","dijit._editor._Plugin"],["require","dijit.form.Button"],["require","dojo.i18n"],["requireLocalization","dojox.editor.plugins","ShowBlockNodes",null,"ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw","ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.editor.plugins.ShowBlockNodes"]){_4._hasResource["dojox.editor.plugins.ShowBlockNodes"]=true;_4.provide("dojox.editor.plugins.ShowBlockNodes");_4.require("dijit._editor._Plugin");_4.require("dijit.form.Button");_4.require("dojo.i18n");_4.declare("dojox.editor.plugins.ShowBlockNodes",_5._editor._Plugin,{useDefaultCommand:false,iconClassPrefix:"dijitAdditionalEditorIcon",_styled:false,_initButton:function(){var _7=_4.i18n.getLocalization("dojox.editor.plugins","ShowBlockNodes");this.button=new _5.form.ToggleButton({label:_7["showBlockNodes"],showLabel:false,iconClass:this.iconClassPrefix+" "+this.iconClassPrefix+"ShowBlockNodes",tabIndex:"-1",onChange:_4.hitch(this,"_showBlocks")});this.editor.addKeyHandler(_4.keys.F9,true,true,_4.hitch(this,this.toggle));},updateState:function(){this.button.set("disabled",this.get("disabled"));},setEditor:function(_8){this.editor=_8;this._initButton();},toggle:function(){this.button.set("checked",!this.button.get("checked"));},_showBlocks:function(_9){var _a=this.editor.document;if(!this._styled){try{this._styled=true;var _b="";var _c=["div","p","ul","ol","table","h1","h2","h3","h4","h5","h6","pre","dir","center","blockquote","form","fieldset","address","object","pre","hr","ins","noscript","li","map","button","dd","dt"];var _d="@media screen {\n"+"\t.editorShowBlocks {TAG} {\n"+"\t\tbackground-image: url({MODURL}/images/blockelems/{TAG}.gif);\n"+"\t\tbackground-repeat: no-repeat;\n"+"\t\tbackground-position: top left;\n"+"\t\tborder-width: 1px;\n"+"\t\tborder-style: dashed;\n"+"\t\tborder-color: #D0D0D0;\n"+"\t\tpadding-top: 15px;\n"+"\t\tpadding-left: 15px;\n"+"\t}\n"+"}\n";_4.forEach(_c,function(_e){_b+=_d.replace(/\{TAG\}/gi,_e);});var _f=_4.moduleUrl(_6._scopeName,"editor/plugins/resources").toString();if(!(_f.match(/^https?:\/\//i))&&!(_f.match(/^file:\/\//i))){var _10;if(_f.charAt(0)==="/"){var _11=_4.doc.location.protocol;var _12=_4.doc.location.host;_10=_11+"//"+_12;}else{_10=this._calcBaseUrl(_4.global.location.href);}if(_10[_10.length-1]!=="/"&&_f.charAt(0)!=="/"){_10+="/";}_f=_10+_f;}_b=_b.replace(/\{MODURL\}/gi,_f);if(!_4.isIE){var _13=_a.createElement("style");_13.appendChild(_a.createTextNode(_b));_a.getElementsByTagName("head")[0].appendChild(_13);}else{var ss=_a.createStyleSheet("");ss.cssText=_b;}}catch(e){console.warn(e);}}if(_9){_4.addClass(this.editor.editNode,"editorShowBlocks");}else{_4.removeClass(this.editor.editNode,"editorShowBlocks");}},_calcBaseUrl:function(_14){var _15=null;if(_14!==null){var _16=_14.indexOf("?");if(_16!=-1){_14=_14.substring(0,_16);}_16=_14.lastIndexOf("/");if(_16>0&&_16<_14.length){_15=_14.substring(0,_16);}else{_15=_14;}}return _15;}});_4.subscribe(_5._scopeName+".Editor.getPlugin",null,function(o){if(o.plugin){return;}var _17=o.args.name.toLowerCase();if(_17==="showblocknodes"){o.plugin=new _6.editor.plugins.ShowBlockNodes();}});}}};});