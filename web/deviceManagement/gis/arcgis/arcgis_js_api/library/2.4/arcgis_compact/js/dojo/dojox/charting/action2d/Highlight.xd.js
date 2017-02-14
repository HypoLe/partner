/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.charting.action2d.Highlight"],["require","dojox.charting.action2d.Base"],["require","dojox.color"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.charting.action2d.Highlight"]){_4._hasResource["dojox.charting.action2d.Highlight"]=true;_4.provide("dojox.charting.action2d.Highlight");_4.require("dojox.charting.action2d.Base");_4.require("dojox.color");(function(){var _7=100,_8=75,_9=50,c=_6.color,cc=function(_a){return function(){return _a;};},hl=function(_b){var a=new c.Color(_b),x=a.toHsl();if(x.s==0){x.l=x.l<50?100:0;}else{x.s=_7;if(x.l<_9){x.l=_8;}else{if(x.l>_8){x.l=_9;}else{x.l=x.l-_9>_8-x.l?_9:_8;}}}return c.fromHsl(x);};_4.declare("dojox.charting.action2d.Highlight",_6.charting.action2d.Base,{defaultParams:{duration:400,easing:_4.fx.easing.backOut},optionalParams:{highlight:"red"},constructor:function(_c,_d,_e){var a=_e&&_e.highlight;this.colorFun=a?(_4.isFunction(a)?a:cc(a)):hl;this.connect();},process:function(o){if(!o.shape||!(o.type in this.overOutEvents)){return;}var _f=o.run.name,_10=o.index,_11,_12,_13;if(_f in this.anim){_11=this.anim[_f][_10];}else{this.anim[_f]={};}if(_11){_11.action.stop(true);}else{var _14=o.shape.getFill();if(!_14||!(_14 instanceof _4.Color)){return;}this.anim[_f][_10]=_11={start:_14,end:this.colorFun(_14)};}var _15=_11.start,end=_11.end;if(o.type=="onmouseout"){var t=_15;_15=end;end=t;}_11.action=_6.gfx.fx.animateFill({shape:o.shape,duration:this.duration,easing:this.easing,color:{start:_15,end:end}});if(o.type=="onmouseout"){_4.connect(_11.action,"onEnd",this,function(){if(this.anim[_f]){delete this.anim[_f][_10];}});}_11.action.play();}});})();}}};});