/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.gfx.Moveable"],["require","dojox.gfx.Mover"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.gfx.Moveable"]){_4._hasResource["dojox.gfx.Moveable"]=true;_4.provide("dojox.gfx.Moveable");_4.require("dojox.gfx.Mover");_4.declare("dojox.gfx.Moveable",null,{constructor:function(_7,_8){this.shape=_7;this.delay=(_8&&_8.delay>0)?_8.delay:0;this.mover=(_8&&_8.mover)?_8.mover:_6.gfx.Mover;this.events=[this.shape.connect("onmousedown",this,"onMouseDown"),this.shape.connect("ontouchstart",this,"onMouseDown")];},destroy:function(){_4.forEach(this.events,this.shape.disconnect,this.shape);this.events=this.shape=null;},onMouseDown:function(e){var _9=e.touches?e.touches[0]:e;if(this.delay){this.events.push(this.shape.connect("onmousemove",this,"onMouseMove"));this.events.push(this.shape.connect("ontouchmove",this,"onMouseMove"));this.events.push(this.shape.connect("onmouseup",this,"onMouseUp"));this.events.push(this.shape.connect("ontouchend",this,"onMouseUp"));this._lastX=_9.clientX;this._lastY=_9.clientY;}else{new this.mover(this.shape,_9,this);}_4.stopEvent(e);},onMouseMove:function(e){var _a=e.touches?e.touches[0]:e;if(Math.abs(_a.clientX-this._lastX)>this.delay||Math.abs(_a.clientY-this._lastY)>this.delay){this.onMouseUp(e);new this.mover(this.shape,_a,this);}_4.stopEvent(e);},onMouseUp:function(e){this.shape.disconnect(this.events.shift());this.shape.disconnect(this.events.shift());},onMoveStart:function(_b){_4.publish("/gfx/move/start",[_b]);_4.addClass(_4.body(),"dojoMove");},onMoveStop:function(_c){_4.publish("/gfx/move/stop",[_c]);_4.removeClass(_4.body(),"dojoMove");},onFirstMove:function(_d){},onMove:function(_e,_f){this.onMoving(_e,_f);this.shape.applyLeftTransform(_f);this.onMoved(_e,_f);},onMoving:function(_10,_11){},onMoved:function(_12,_13){}});}}};});