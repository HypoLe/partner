/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.gfx.decompose"],["require","dojox.gfx.matrix"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.gfx.decompose"]){_4._hasResource["dojox.gfx.decompose"]=true;_4.provide("dojox.gfx.decompose");_4.require("dojox.gfx.matrix");(function(){var m=_6.gfx.matrix;function eq(a,b){return Math.abs(a-b)<=0.000001*(Math.abs(a)+Math.abs(b));};function _7(r1,m1,r2,m2){if(!isFinite(r1)){return r2;}else{if(!isFinite(r2)){return r1;}}m1=Math.abs(m1),m2=Math.abs(m2);return (m1*r1+m2*r2)/(m1+m2);};function _8(_9){var M=new m.Matrix2D(_9);return _4.mixin(M,{dx:0,dy:0,xy:M.yx,yx:M.xy});};function _a(_b){return (_b.xx*_b.yy<0||_b.xy*_b.yx>0)?-1:1;};function _c(_d){var M=m.normalize(_d),b=-M.xx-M.yy,c=M.xx*M.yy-M.xy*M.yx,d=Math.sqrt(b*b-4*c),l1=-(b+(b<0?-d:d))/2,l2=c/l1,_e=M.xy/(l1-M.xx),_f=1,vx2=M.xy/(l2-M.xx),vy2=1;if(eq(l1,l2)){_e=1,_f=0,vx2=0,vy2=1;}if(!isFinite(_e)){_e=1,_f=(l1-M.xx)/M.xy;if(!isFinite(_f)){_e=(l1-M.yy)/M.yx,_f=1;if(!isFinite(_e)){_e=1,_f=M.yx/(l1-M.yy);}}}if(!isFinite(vx2)){vx2=1,vy2=(l2-M.xx)/M.xy;if(!isFinite(vy2)){vx2=(l2-M.yy)/M.yx,vy2=1;if(!isFinite(vx2)){vx2=1,vy2=M.yx/(l2-M.yy);}}}var d1=Math.sqrt(_e*_e+_f*_f),d2=Math.sqrt(vx2*vx2+vy2*vy2);if(!isFinite(_e/=d1)){_e=0;}if(!isFinite(_f/=d1)){_f=0;}if(!isFinite(vx2/=d2)){vx2=0;}if(!isFinite(vy2/=d2)){vy2=0;}return {value1:l1,value2:l2,vector1:{x:_e,y:_f},vector2:{x:vx2,y:vy2}};};function _10(M,_11){var _12=_a(M),a=_11.angle1=(Math.atan2(M.yx,M.yy)+Math.atan2(-_12*M.xy,_12*M.xx))/2,cos=Math.cos(a),sin=Math.sin(a);_11.sx=_7(M.xx/cos,cos,-M.xy/sin,sin);_11.sy=_7(M.yy/cos,cos,M.yx/sin,sin);return _11;};function _13(M,_14){var _15=_a(M),a=_14.angle2=(Math.atan2(_15*M.yx,_15*M.xx)+Math.atan2(-M.xy,M.yy))/2,cos=Math.cos(a),sin=Math.sin(a);_14.sx=_7(M.xx/cos,cos,M.yx/sin,sin);_14.sy=_7(M.yy/cos,cos,-M.xy/sin,sin);return _14;};_6.gfx.decompose=function(_16){var M=m.normalize(_16),_17={dx:M.dx,dy:M.dy,sx:1,sy:1,angle1:0,angle2:0};if(eq(M.xy,0)&&eq(M.yx,0)){return _4.mixin(_17,{sx:M.xx,sy:M.yy});}if(eq(M.xx*M.yx,-M.xy*M.yy)){return _10(M,_17);}if(eq(M.xx*M.xy,-M.yx*M.yy)){return _13(M,_17);}var MT=_8(M),u=_c([M,MT]),v=_c([MT,M]),U=new m.Matrix2D({xx:u.vector1.x,xy:u.vector2.x,yx:u.vector1.y,yy:u.vector2.y}),VT=new m.Matrix2D({xx:v.vector1.x,xy:v.vector1.y,yx:v.vector2.x,yy:v.vector2.y}),S=new m.Matrix2D([m.invert(U),M,m.invert(VT)]);_10(VT,_17);S.xx*=_17.sx;S.yy*=_17.sy;_13(U,_17);S.xx*=_17.sx;S.yy*=_17.sy;return _4.mixin(_17,{sx:S.xx,sy:S.yy});};})();}}};});