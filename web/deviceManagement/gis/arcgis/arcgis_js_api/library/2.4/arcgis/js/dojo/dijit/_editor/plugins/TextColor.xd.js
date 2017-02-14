/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit._editor.plugins.TextColor"],["require","dijit._editor._Plugin"],["require","dijit.ColorPalette"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit._editor.plugins.TextColor"]){_4._hasResource["dijit._editor.plugins.TextColor"]=true;_4.provide("dijit._editor.plugins.TextColor");_4.require("dijit._editor._Plugin");_4.require("dijit.ColorPalette");_4.declare("dijit._editor.plugins.TextColor",_5._editor._Plugin,{buttonClass:_5.form.DropDownButton,useDefaultCommand:false,constructor:function(){this.dropDown=new _5.ColorPalette();this.connect(this.dropDown,"onChange",function(_7){this.editor.execCommand(this.command,_7);});},updateState:function(){var _8=this.editor;var _9=this.command;if(!_8||!_8.isLoaded||!_9.length){return;}if(this.button){var _a=this.get("disabled");this.button.set("disabled",_a);if(_a){return;}var _b;try{_b=_8.queryCommandValue(_9)||"";}catch(e){_b="";}}if(_b==""){_b="#000000";}if(_b=="transparent"){_b="#ffffff";}if(typeof _b=="string"){if(_b.indexOf("rgb")>-1){_b=_4.colorFromRgb(_b).toHex();}}else{_b=((_b&255)<<16)|(_b&65280)|((_b&16711680)>>>16);_b=_b.toString(16);_b="#000000".slice(0,7-_b.length)+_b;}if(_b!==this.dropDown.get("value")){this.dropDown.set("value",_b,false);}}});_4.subscribe(_5._scopeName+".Editor.getPlugin",null,function(o){if(o.plugin){return;}switch(o.args.name){case "foreColor":case "hiliteColor":o.plugin=new _5._editor.plugins.TextColor({command:o.args.name});}});}}};});