/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.widget.gauge.AnalogArcIndicator"],["require","dojox.widget.AnalogGauge"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.widget.gauge.AnalogArcIndicator"]){_4._hasResource["dojox.widget.gauge.AnalogArcIndicator"]=true;_4.provide("dojox.widget.gauge.AnalogArcIndicator");_4.require("dojox.widget.AnalogGauge");_4.experimental("dojox.widget.gauge.AnalogArcIndicator");_4.declare("dojox.widget.gauge.AnalogArcIndicator",[_6.widget.gauge.AnalogLineIndicator],{_createArc:function(_7){if(this.shapes[0]){var a=this._gauge._getRadians(this._gauge._getAngle(_7));var _8=Math.cos(a);var _9=Math.sin(a);var sa=this._gauge._getRadians(this._gauge.startAngle);var _a=Math.cos(sa);var _b=Math.sin(sa);var _c=this.offset+this.width;var p=["M"];p.push(this._gauge.cx+this.offset*_b);p.push(this._gauge.cy-this.offset*_a);p.push("A",this.offset,this.offset,0,((a-sa)>Math.PI)?1:0,1);p.push(this._gauge.cx+this.offset*_9);p.push(this._gauge.cy-this.offset*_8);p.push("L");p.push(this._gauge.cx+_c*_9);p.push(this._gauge.cy-_c*_8);p.push("A",_c,_c,0,((a-sa)>Math.PI)?1:0,0);p.push(this._gauge.cx+_c*_b);p.push(this._gauge.cy-_c*_a);this.shapes[0].setShape(p.join(" "));this.currentValue=_7;}},draw:function(_d){var v=this.value;if(v<this._gauge.min){v=this._gauge.min;}if(v>this._gauge.max){v=this._gauge.max;}if(this.shapes){if(_d){this._createArc(v);}else{var _e=new _4.Animation({curve:[this.currentValue,v],duration:this.duration,easing:this.easing});_4.connect(_e,"onAnimate",_4.hitch(this,this._createArc));_e.play();}}else{var _f={color:this.color,width:1};if(this.color.type){_f.color=this.color.colors[0].color;}this.shapes=[this._gauge.surface.createPath().setStroke(_f).setFill(this.color)];this._createArc(v);if(this.hover){this.shapes[0].getEventSource().setAttribute("hover",this.hover);}if(this.onDragMove&&!this.noChange){this._gauge.connect(this.shapes[0].getEventSource(),"onmousedown",this._gauge.handleMouseDown);this.shapes[0].getEventSource().style.cursor="pointer";}}}});}}};});