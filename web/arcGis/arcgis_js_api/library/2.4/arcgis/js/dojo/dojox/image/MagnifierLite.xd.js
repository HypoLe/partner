/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.image.MagnifierLite"],["require","dijit._Widget"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.image.MagnifierLite"]){_4._hasResource["dojox.image.MagnifierLite"]=true;_4.provide("dojox.image.MagnifierLite");_4.experimental("dojox.image.MagnifierLite");_4.require("dijit._Widget");_4.declare("dojox.image.MagnifierLite",_5._Widget,{glassSize:125,scale:6,postCreate:function(){this.inherited(arguments);this._adjustScale();this._createGlass();this.connect(this.domNode,"onmouseenter","_showGlass");this.connect(this.glassNode,"onmousemove","_placeGlass");this.connect(this.img,"onmouseout","_hideGlass");this.connect(window,"onresize","_adjustScale");},_createGlass:function(){var _7=this.glassNode=_4.create("div",{style:{height:this.glassSize+"px",width:this.glassSize+"px"},className:"glassNode"},_4.body());this.surfaceNode=_7.appendChild(_4.create("div"));this.img=_4.place(_4.clone(this.domNode),_7);_4.style(this.img,{position:"relative",top:0,left:0,width:this._zoomSize.w+"px",height:this._zoomSize.h+"px"});},_adjustScale:function(){this.offset=_4.coords(this.domNode,true);this._imageSize={w:this.offset.w,h:this.offset.h};this._zoomSize={w:this._imageSize.w*this.scale,h:this._imageSize.h*this.scale};},_showGlass:function(e){this._placeGlass(e);_4.style(this.glassNode,{visibility:"visible",display:""});},_hideGlass:function(e){_4.style(this.glassNode,{visibility:"hidden",display:"none"});},_placeGlass:function(e){this._setImage(e);var _8=Math.floor(this.glassSize/2);_4.style(this.glassNode,{top:Math.floor(e.pageY-_8)+"px",left:Math.floor(e.pageX-_8)+"px"});},_setImage:function(e){var _9=(e.pageX-this.offset.l)/this.offset.w,_a=(e.pageY-this.offset.t)/this.offset.h,x=(this._zoomSize.w*_9*-1)+(this.glassSize*_9),y=(this._zoomSize.h*_a*-1)+(this.glassSize*_a);_4.style(this.img,{top:y+"px",left:x+"px"});},destroy:function(_b){_4.destroy(this.glassNode);this.inherited(arguments);}});}}};});