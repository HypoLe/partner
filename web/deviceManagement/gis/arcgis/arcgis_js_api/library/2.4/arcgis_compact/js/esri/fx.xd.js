/*
 COPYRIGHT 2009 ESRI

 TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
 Unpublished material - all rights reserved under the
 Copyright Laws of the United States and applicable international
 laws, treaties, and conventions.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts and Legal Services Department
 380 New York Street
 Redlands, California, 92373
 USA

 email: contracts@esri.com
 */

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.fx"],["require","dojo.fx"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.fx"]){_4._hasResource["esri.fx"]=true;_4.provide("esri.fx");_4.require("dojo.fx");esri.fx.animateRange=function(_7){var _8=_7.range;return new _4._Animation(_4.mixin({curve:new _4._Line(_8.start,_8.end)},_7));};esri.fx.resize=function(_9){var _a=(_9.node=_4.byId(_9.node)),_b=_9.start,_c=_9.end;if(!_b){var mb=_4._getMarginBox(_a),pb=_4._getPadBorderExtents(_a);_b=(_9.start={left:mb.l+pb.l,top:mb.t+pb.t,width:mb.w-pb.w,height:mb.h-pb.h});}if(!_c){var _d=_9.anchor?_9.anchor:{x:_b.left,y:_b.top},_e=_9.size;_c=_9.end={left:(_b.left-((_e.width-_b.width)*(_d.x-_b.left)/_b.width)),top:(_b.top-((_e.height-_b.height)*(_d.y-_b.top)/_b.height)),width:_e.width,height:_e.height};}return _4.animateProperty(_4.mixin({properties:{left:{start:_b.left,end:_c.left},top:{start:_b.top,end:_c.top},width:{start:_b.width,end:_c.width},height:{start:_b.height,end:_c.height}}},_9));};esri.fx.slideTo=function(_f){var _10=(_f.node=_4.byId(_f.node)),_11=_4.getComputedStyle,top=null,_12=null,_13=(function(){var _14=_10;return function(){var pos=_14.style.position=="absolute"?"absolute":"relative";top=(pos=="absolute"?_10.offsetTop:parseInt(_11(_10).top)||0);_12=(pos=="absolute"?_10.offsetLeft:parseInt(_11(_10).left)||0);if(pos!="absolute"&&pos!="relative"){var ret=_4.coords(_14,true);top=ret.y;_12=ret.x;_14.style.position="absolute";_14.style.top=top+"px";_14.style.left=_12+"px";}};})();_13();var _15=_4.animateProperty(_4.mixin({properties:{top:{start:top,end:_f.top||0},left:{start:_12,end:_f.left||0}}},_f));_4.connect(_15,"beforeBegin",_15,_13);return _15;};esri.fx.flash=function(_16){_16=_4.mixin({end:"#f00",duration:500,count:1},_16);_16.duration/=_16.count*2;var _17=_4.byId(_16.node),_18=_16.start;if(!_18){_18=_4.getComputedStyle(_17).backgroundColor;}var end=_16.end,_19=_16.duration,_1a=[],_1b={node:_17,duration:_19};for(var i=0,il=_16.count;i<il;i++){_1a.push(_4.animateProperty(_4.mixin({properties:{backgroundColor:{start:_18,end:end}}},_1b)));_1a.push(_4.animateProperty(_4.mixin({properties:{backgroundColor:{start:end,end:_18}}},_1b)));}return _4.fx.chain(_1a);};}}};});