/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.ColorPalette"],["require","dijit._Widget"],["require","dijit._Templated"],["require","dojo.colors"],["require","dojo.i18n"],["require","dojo.string"],["require","dijit._PaletteMixin"],["requireLocalization","dojo","colors",null,"ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw","ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.ColorPalette"]){_4._hasResource["dijit.ColorPalette"]=true;_4.provide("dijit.ColorPalette");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.require("dojo.colors");_4.require("dojo.i18n");_4.require("dojo.string");_4.require("dijit._PaletteMixin");_4.declare("dijit.ColorPalette",[_5._Widget,_5._Templated,_5._PaletteMixin],{palette:"7x10",_palettes:{"7x10":[["white","seashell","cornsilk","lemonchiffon","lightyellow","palegreen","paleturquoise","lightcyan","lavender","plum"],["lightgray","pink","bisque","moccasin","khaki","lightgreen","lightseagreen","lightskyblue","cornflowerblue","violet"],["silver","lightcoral","sandybrown","orange","palegoldenrod","chartreuse","mediumturquoise","skyblue","mediumslateblue","orchid"],["gray","red","orangered","darkorange","yellow","limegreen","darkseagreen","royalblue","slateblue","mediumorchid"],["dimgray","crimson","chocolate","coral","gold","forestgreen","seagreen","blue","blueviolet","darkorchid"],["darkslategray","firebrick","saddlebrown","sienna","olive","green","darkcyan","mediumblue","darkslateblue","darkmagenta"],["black","darkred","maroon","brown","darkolivegreen","darkgreen","midnightblue","navy","indigo","purple"]],"3x4":[["white","lime","green","blue"],["silver","yellow","fuchsia","navy"],["gray","red","purple","black"]]},templateString:_4.cache("dijit","templates/ColorPalette.html","<div class=\"dijitInline dijitColorPalette\">\r\n\t<table class=\"dijitPaletteTable\" cellSpacing=\"0\" cellPadding=\"0\">\r\n\t\t<tbody dojoAttachPoint=\"gridNode\"></tbody>\r\n\t</table>\r\n</div>\r\n"),baseClass:"dijitColorPalette",buildRendering:function(){this.inherited(arguments);this._preparePalette(this._palettes[this.palette],_4.i18n.getLocalization("dojo","colors",this.lang),_4.declare(_5._Color,{hc:_4.hasClass(_4.body(),"dijit_a11y"),palette:this.palette}));}});_4.declare("dijit._Color",_4.Color,{template:"<span class='dijitInline dijitPaletteImg'>"+"<img src='${blankGif}' alt='${alt}' class='dijitColorPaletteSwatch' style='background-color: ${color}'/>"+"</span>",hcTemplate:"<span class='dijitInline dijitPaletteImg' style='position: relative; overflow: hidden; height: 12px; width: 14px;'>"+"<img src='${image}' alt='${alt}' style='position: absolute; left: ${left}px; top: ${top}px; ${size}'/>"+"</span>",_imagePaths:{"7x10":_4.moduleUrl("dijit.themes","a11y/colors7x10.png"),"3x4":_4.moduleUrl("dijit.themes","a11y/colors3x4.png")},constructor:function(_7,_8,_9){this._alias=_7;this._row=_8;this._col=_9;this.setColor(_4.Color.named[_7]);},getValue:function(){return this.toHex();},fillCell:function(_a,_b){var _c=_4.string.substitute(this.hc?this.hcTemplate:this.template,{color:this.toHex(),blankGif:_b,alt:this._alias,image:this._imagePaths[this.palette].toString(),left:this._col*-20-5,top:this._row*-20-5,size:this.palette=="7x10"?"height: 145px; width: 206px":"height: 64px; width: 86px"});_4.place(_c,_a);}});}}};});