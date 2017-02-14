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

if(!dojo._hasResource["esri.fx"]){dojo._hasResource["esri.fx"]=true;dojo.provide("esri.fx");dojo.require("dojo.fx");esri.fx.animateRange=function(_1){var _2=_1.range;return new dojo._Animation(dojo.mixin({curve:new dojo._Line(_2.start,_2.end)},_1));};esri.fx.resize=function(_3){var _4=(_3.node=dojo.byId(_3.node)),_5=_3.start,_6=_3.end;if(!_5){var mb=dojo._getMarginBox(_4),pb=dojo._getPadBorderExtents(_4);_5=(_3.start={left:mb.l+pb.l,top:mb.t+pb.t,width:mb.w-pb.w,height:mb.h-pb.h});}if(!_6){var _7=_3.anchor?_3.anchor:{x:_5.left,y:_5.top},_8=_3.size;_6=_3.end={left:(_5.left-((_8.width-_5.width)*(_7.x-_5.left)/_5.width)),top:(_5.top-((_8.height-_5.height)*(_7.y-_5.top)/_5.height)),width:_8.width,height:_8.height};}return dojo.animateProperty(dojo.mixin({properties:{left:{start:_5.left,end:_6.left},top:{start:_5.top,end:_6.top},width:{start:_5.width,end:_6.width},height:{start:_5.height,end:_6.height}}},_3));};esri.fx.slideTo=function(_9){var _a=(_9.node=dojo.byId(_9.node)),_b=dojo.getComputedStyle,_c=null,_d=null,_e=(function(){var _f=_a;return function(){var pos=_f.style.position=="absolute"?"absolute":"relative";_c=(pos=="absolute"?_a.offsetTop:parseInt(_b(_a).top)||0);_d=(pos=="absolute"?_a.offsetLeft:parseInt(_b(_a).left)||0);if(pos!="absolute"&&pos!="relative"){var ret=dojo.coords(_f,true);_c=ret.y;_d=ret.x;_f.style.position="absolute";_f.style.top=_c+"px";_f.style.left=_d+"px";}};})();_e();var _10=dojo.animateProperty(dojo.mixin({properties:{top:{start:_c,end:_9.top||0},left:{start:_d,end:_9.left||0}}},_9));dojo.connect(_10,"beforeBegin",_10,_e);return _10;};esri.fx.flash=function(_11){_11=dojo.mixin({end:"#f00",duration:500,count:1},_11);_11.duration/=_11.count*2;var _12=dojo.byId(_11.node),_13=_11.start;if(!_13){_13=dojo.getComputedStyle(_12).backgroundColor;}var end=_11.end,_14=_11.duration,_15=[],_16={node:_12,duration:_14};for(var i=0,il=_11.count;i<il;i++){_15.push(dojo.animateProperty(dojo.mixin({properties:{backgroundColor:{start:_13,end:end}}},_16)));_15.push(dojo.animateProperty(dojo.mixin({properties:{backgroundColor:{start:end,end:_13}}},_16)));}return dojo.fx.chain(_15);};}