/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/

/*
	This is an optimized version of Dojo, built for deployment and not for
	development. To get sources and documentation, please visit:

		http://dojotoolkit.org
*/

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojo.uacss"],["provide","dijit._base.sniff"],["provide","dojo.fx.Toggler"],["provide","dojo.fx"],["provide","dojox.fx.flip"],["provide","dojox.mobile.compat"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojo.uacss"]){_4._hasResource["dojo.uacss"]=true;_4.provide("dojo.uacss");(function(){var d=_4,_7=d.doc.documentElement,ie=d.isIE,_8=d.isOpera,_9=Math.floor,ff=d.isFF,_a=d.boxModel.replace(/-/,""),_b={dj_ie:ie,dj_ie6:_9(ie)==6,dj_ie7:_9(ie)==7,dj_ie8:_9(ie)==8,dj_ie9:_9(ie)==9,dj_quirks:d.isQuirks,dj_iequirks:ie&&d.isQuirks,dj_opera:_8,dj_khtml:d.isKhtml,dj_webkit:d.isWebKit,dj_safari:d.isSafari,dj_chrome:d.isChrome,dj_gecko:d.isMozilla,dj_ff3:_9(ff)==3};_b["dj_"+_a]=true;var _c="";for(var _d in _b){if(_b[_d]){_c+=_d+" ";}}_7.className=d.trim(_7.className+" "+_c);_4._loaders.unshift(function(){if(!_4._isBodyLtr()){var _e="dj_rtl dijitRtl "+_c.replace(/ /g,"-rtl ");_7.className=d.trim(_7.className+" "+_e);}});})();}if(!_4._hasResource["dijit._base.sniff"]){_4._hasResource["dijit._base.sniff"]=true;_4.provide("dijit._base.sniff");}if(!_4._hasResource["dojo.fx.Toggler"]){_4._hasResource["dojo.fx.Toggler"]=true;_4.provide("dojo.fx.Toggler");_4.declare("dojo.fx.Toggler",null,{node:null,showFunc:_4.fadeIn,hideFunc:_4.fadeOut,showDuration:200,hideDuration:200,constructor:function(_f){var _10=this;_4.mixin(_10,_f);_10.node=_f.node;_10._showArgs=_4.mixin({},_f);_10._showArgs.node=_10.node;_10._showArgs.duration=_10.showDuration;_10.showAnim=_10.showFunc(_10._showArgs);_10._hideArgs=_4.mixin({},_f);_10._hideArgs.node=_10.node;_10._hideArgs.duration=_10.hideDuration;_10.hideAnim=_10.hideFunc(_10._hideArgs);_4.connect(_10.showAnim,"beforeBegin",_4.hitch(_10.hideAnim,"stop",true));_4.connect(_10.hideAnim,"beforeBegin",_4.hitch(_10.showAnim,"stop",true));},show:function(_11){return this.showAnim.play(_11||0);},hide:function(_12){return this.hideAnim.play(_12||0);}});}if(!_4._hasResource["dojo.fx"]){_4._hasResource["dojo.fx"]=true;_4.provide("dojo.fx");(function(){var d=_4,_13={_fire:function(evt,_14){if(this[evt]){this[evt].apply(this,_14||[]);}return this;}};var _15=function(_16){this._index=-1;this._animations=_16||[];this._current=this._onAnimateCtx=this._onEndCtx=null;this.duration=0;d.forEach(this._animations,function(a){this.duration+=a.duration;if(a.delay){this.duration+=a.delay;}},this);};d.extend(_15,{_onAnimate:function(){this._fire("onAnimate",arguments);},_onEnd:function(){d.disconnect(this._onAnimateCtx);d.disconnect(this._onEndCtx);this._onAnimateCtx=this._onEndCtx=null;if(this._index+1==this._animations.length){this._fire("onEnd");}else{this._current=this._animations[++this._index];this._onAnimateCtx=d.connect(this._current,"onAnimate",this,"_onAnimate");this._onEndCtx=d.connect(this._current,"onEnd",this,"_onEnd");this._current.play(0,true);}},play:function(_17,_18){if(!this._current){this._current=this._animations[this._index=0];}if(!_18&&this._current.status()=="playing"){return this;}var _19=d.connect(this._current,"beforeBegin",this,function(){this._fire("beforeBegin");}),_1a=d.connect(this._current,"onBegin",this,function(arg){this._fire("onBegin",arguments);}),_1b=d.connect(this._current,"onPlay",this,function(arg){this._fire("onPlay",arguments);d.disconnect(_19);d.disconnect(_1a);d.disconnect(_1b);});if(this._onAnimateCtx){d.disconnect(this._onAnimateCtx);}this._onAnimateCtx=d.connect(this._current,"onAnimate",this,"_onAnimate");if(this._onEndCtx){d.disconnect(this._onEndCtx);}this._onEndCtx=d.connect(this._current,"onEnd",this,"_onEnd");this._current.play.apply(this._current,arguments);return this;},pause:function(){if(this._current){var e=d.connect(this._current,"onPause",this,function(arg){this._fire("onPause",arguments);d.disconnect(e);});this._current.pause();}return this;},gotoPercent:function(_1c,_1d){this.pause();var _1e=this.duration*_1c;this._current=null;d.some(this._animations,function(a){if(a.duration<=_1e){this._current=a;return true;}_1e-=a.duration;return false;});if(this._current){this._current.gotoPercent(_1e/this._current.duration,_1d);}return this;},stop:function(_1f){if(this._current){if(_1f){for(;this._index+1<this._animations.length;++this._index){this._animations[this._index].stop(true);}this._current=this._animations[this._index];}var e=d.connect(this._current,"onStop",this,function(arg){this._fire("onStop",arguments);d.disconnect(e);});this._current.stop();}return this;},status:function(){return this._current?this._current.status():"stopped";},destroy:function(){if(this._onAnimateCtx){d.disconnect(this._onAnimateCtx);}if(this._onEndCtx){d.disconnect(this._onEndCtx);}}});d.extend(_15,_13);_4.fx.chain=function(_20){return new _15(_20);};var _21=function(_22){this._animations=_22||[];this._connects=[];this._finished=0;this.duration=0;d.forEach(_22,function(a){var _23=a.duration;if(a.delay){_23+=a.delay;}if(this.duration<_23){this.duration=_23;}this._connects.push(d.connect(a,"onEnd",this,"_onEnd"));},this);this._pseudoAnimation=new d.Animation({curve:[0,1],duration:this.duration});var _24=this;d.forEach(["beforeBegin","onBegin","onPlay","onAnimate","onPause","onStop","onEnd"],function(evt){_24._connects.push(d.connect(_24._pseudoAnimation,evt,function(){_24._fire(evt,arguments);}));});};d.extend(_21,{_doAction:function(_25,_26){d.forEach(this._animations,function(a){a[_25].apply(a,_26);});return this;},_onEnd:function(){if(++this._finished>this._animations.length){this._fire("onEnd");}},_call:function(_27,_28){var t=this._pseudoAnimation;t[_27].apply(t,_28);},play:function(_29,_2a){this._finished=0;this._doAction("play",arguments);this._call("play",arguments);return this;},pause:function(){this._doAction("pause",arguments);this._call("pause",arguments);return this;},gotoPercent:function(_2b,_2c){var ms=this.duration*_2b;d.forEach(this._animations,function(a){a.gotoPercent(a.duration<ms?1:(ms/a.duration),_2c);});this._call("gotoPercent",arguments);return this;},stop:function(_2d){this._doAction("stop",arguments);this._call("stop",arguments);return this;},status:function(){return this._pseudoAnimation.status();},destroy:function(){d.forEach(this._connects,_4.disconnect);}});d.extend(_21,_13);_4.fx.combine=function(_2e){return new _21(_2e);};_4.fx.wipeIn=function(_2f){var _30=_2f.node=d.byId(_2f.node),s=_30.style,o;var _31=d.animateProperty(d.mixin({properties:{height:{start:function(){o=s.overflow;s.overflow="hidden";if(s.visibility=="hidden"||s.display=="none"){s.height="1px";s.display="";s.visibility="";return 1;}else{var _32=d.style(_30,"height");return Math.max(_32,1);}},end:function(){return _30.scrollHeight;}}}},_2f));d.connect(_31,"onEnd",function(){s.height="auto";s.overflow=o;});return _31;};_4.fx.wipeOut=function(_33){var _34=_33.node=d.byId(_33.node),s=_34.style,o;var _35=d.animateProperty(d.mixin({properties:{height:{end:1}}},_33));d.connect(_35,"beforeBegin",function(){o=s.overflow;s.overflow="hidden";s.display="";});d.connect(_35,"onEnd",function(){s.overflow=o;s.height="auto";s.display="none";});return _35;};_4.fx.slideTo=function(_36){var _37=_36.node=d.byId(_36.node),top=null,_38=null;var _39=(function(n){return function(){var cs=d.getComputedStyle(n);var pos=cs.position;top=(pos=="absolute"?n.offsetTop:parseInt(cs.top)||0);_38=(pos=="absolute"?n.offsetLeft:parseInt(cs.left)||0);if(pos!="absolute"&&pos!="relative"){var ret=d.position(n,true);top=ret.y;_38=ret.x;n.style.position="absolute";n.style.top=top+"px";n.style.left=_38+"px";}};})(_37);_39();var _3a=d.animateProperty(d.mixin({properties:{top:_36.top||0,left:_36.left||0}},_36));d.connect(_3a,"beforeBegin",_3a,_39);return _3a;};})();}if(!_4._hasResource["dojox.fx.flip"]){_4._hasResource["dojox.fx.flip"]=true;_4.provide("dojox.fx.flip");_4.experimental("dojox.fx.flip");var _3b="border",_3c="Width",_3d="Height",_3e="Top",_3f="Right",_40="Left",_41="Bottom";_6.fx.flip=function(_42){var _43=_4.create("div"),_44=_42.node=_4.byId(_42.node),s=_44.style,_45=null,hs=null,pn=null,_46=_42.lightColor||"#dddddd",_47=_42.darkColor||"#555555",_48=_4.style(_44,"backgroundColor"),_49=_42.endColor||_48,_4a={},_4b=[],_4c=_42.duration?_42.duration/2:250,dir=_42.dir||"left",_4d=0.9,_4e="transparent",_4f=_42.whichAnim,_50=_42.axis||"center",_51=_42.depth;var _52=function(_53){return ((new _4.Color(_53)).toHex()==="#000000")?"#000001":_53;};if(_4.isIE<7){_49=_52(_49);_46=_52(_46);_47=_52(_47);_48=_52(_48);_4e="black";_43.style.filter="chroma(color='#000000')";}var _54=(function(n){return function(){var ret=_4.coords(n,true);_45={top:ret.y,left:ret.x,width:ret.w,height:ret.h};};})(_44);_54();hs={position:"absolute",top:_45["top"]+"px",left:_45["left"]+"px",height:"0",width:"0",zIndex:_42.zIndex||(s.zIndex||0),border:"0 solid "+_4e,fontSize:"0",visibility:"hidden"};var _55=[{},{top:_45["top"],left:_45["left"]}];var _56={left:[_40,_3f,_3e,_41,_3c,_3d,"end"+_3d+"Min",_40,"end"+_3d+"Max"],right:[_3f,_40,_3e,_41,_3c,_3d,"end"+_3d+"Min",_40,"end"+_3d+"Max"],top:[_3e,_41,_40,_3f,_3d,_3c,"end"+_3c+"Min",_3e,"end"+_3c+"Max"],bottom:[_41,_3e,_40,_3f,_3d,_3c,"end"+_3c+"Min",_3e,"end"+_3c+"Max"]};pn=_56[dir];if(typeof _51!="undefined"){_51=Math.max(0,Math.min(1,_51))/2;_4d=0.4+(0.5-_51);}else{_4d=Math.min(0.9,Math.max(0.4,_45[pn[5].toLowerCase()]/_45[pn[4].toLowerCase()]));}var p0=_55[0];for(var i=4;i<6;i++){if(_50=="center"||_50=="cube"){_45["end"+pn[i]+"Min"]=_45[pn[i].toLowerCase()]*_4d;_45["end"+pn[i]+"Max"]=_45[pn[i].toLowerCase()]/_4d;}else{if(_50=="shortside"){_45["end"+pn[i]+"Min"]=_45[pn[i].toLowerCase()];_45["end"+pn[i]+"Max"]=_45[pn[i].toLowerCase()]/_4d;}else{if(_50=="longside"){_45["end"+pn[i]+"Min"]=_45[pn[i].toLowerCase()]*_4d;_45["end"+pn[i]+"Max"]=_45[pn[i].toLowerCase()];}}}}if(_50=="center"){p0[pn[2].toLowerCase()]=_45[pn[2].toLowerCase()]-(_45[pn[8]]-_45[pn[6]])/4;}else{if(_50=="shortside"){p0[pn[2].toLowerCase()]=_45[pn[2].toLowerCase()]-(_45[pn[8]]-_45[pn[6]])/2;}}_4a[pn[5].toLowerCase()]=_45[pn[5].toLowerCase()]+"px";_4a[pn[4].toLowerCase()]="0";_4a[_3b+pn[1]+_3c]=_45[pn[4].toLowerCase()]+"px";_4a[_3b+pn[1]+"Color"]=_48;p0[_3b+pn[1]+_3c]=0;p0[_3b+pn[1]+"Color"]=_47;p0[_3b+pn[2]+_3c]=p0[_3b+pn[3]+_3c]=_50!="cube"?(_45["end"+pn[5]+"Max"]-_45["end"+pn[5]+"Min"])/2:_45[pn[6]]/2;p0[pn[7].toLowerCase()]=_45[pn[7].toLowerCase()]+_45[pn[4].toLowerCase()]/2+(_42.shift||0);p0[pn[5].toLowerCase()]=_45[pn[6]];var p1=_55[1];p1[_3b+pn[0]+"Color"]={start:_46,end:_49};p1[_3b+pn[0]+_3c]=_45[pn[4].toLowerCase()];p1[_3b+pn[2]+_3c]=0;p1[_3b+pn[3]+_3c]=0;p1[pn[5].toLowerCase()]={start:_45[pn[6]],end:_45[pn[5].toLowerCase()]};_4.mixin(hs,_4a);_4.style(_43,hs);_4.body().appendChild(_43);var _57=function(){_4.destroy(_43);s.backgroundColor=_49;s.visibility="visible";};if(_4f=="last"){for(i in p0){p0[i]={start:p0[i]};}p0[_3b+pn[1]+"Color"]={start:_47,end:_49};p1=p0;}if(!_4f||_4f=="first"){_4b.push(_4.animateProperty({node:_43,duration:_4c,properties:p0}));}if(!_4f||_4f=="last"){_4b.push(_4.animateProperty({node:_43,duration:_4c,properties:p1,onEnd:_57}));}_4.connect(_4b[0],"play",function(){_43.style.visibility="visible";s.visibility="hidden";});return _4.fx.chain(_4b);};_6.fx.flipCube=function(_58){var _59=[],mb=_4.marginBox(_58.node),_5a=mb.w/2,_5b=mb.h/2,_5c={top:{pName:"height",args:[{whichAnim:"first",dir:"top",shift:-_5b},{whichAnim:"last",dir:"bottom",shift:_5b}]},right:{pName:"width",args:[{whichAnim:"first",dir:"right",shift:_5a},{whichAnim:"last",dir:"left",shift:-_5a}]},bottom:{pName:"height",args:[{whichAnim:"first",dir:"bottom",shift:_5b},{whichAnim:"last",dir:"top",shift:-_5b}]},left:{pName:"width",args:[{whichAnim:"first",dir:"left",shift:-_5a},{whichAnim:"last",dir:"right",shift:_5a}]}};var d=_5c[_58.dir||"left"],p=d.args;_58.duration=_58.duration?_58.duration*2:500;_58.depth=0.8;_58.axis="cube";for(var i=p.length-1;i>=0;i--){_4.mixin(_58,p[i]);_59.push(_6.fx.flip(_58));}return _4.fx.combine(_59);};_6.fx.flipPage=function(_5d){var n=_5d.node,_5e=_4.coords(n,true),x=_5e.x,y=_5e.y,w=_5e.w,h=_5e.h,_5f=_4.style(n,"backgroundColor"),_60=_5d.lightColor||"#dddddd",_61=_5d.darkColor,_62=_4.create("div"),_63=[],hn=[],dir=_5d.dir||"right",pn={left:["left","right","x","w"],top:["top","bottom","y","h"],right:["left","left","x","w"],bottom:["top","top","y","h"]},_64={right:[1,-1],left:[-1,1],top:[-1,1],bottom:[1,-1]};_4.style(_62,{position:"absolute",width:w+"px",height:h+"px",top:y+"px",left:x+"px",visibility:"hidden"});var hs=[];for(var i=0;i<2;i++){var r=i%2,d=r?pn[dir][1]:dir,wa=r?"last":"first",_65=r?_5f:_60,_66=r?_65:_5d.startColor||n.style.backgroundColor;hn[i]=_4.clone(_62);var _67=function(x){return function(){_4.destroy(hn[x]);};}(i);_4.body().appendChild(hn[i]);hs[i]={backgroundColor:r?_66:_5f};hs[i][pn[dir][0]]=_5e[pn[dir][2]]+_64[dir][0]*i*_5e[pn[dir][3]]+"px";_4.style(hn[i],hs[i]);_63.push(_6.fx.flip({node:hn[i],dir:d,axis:"shortside",depth:_5d.depth,duration:_5d.duration/2,shift:_64[dir][i]*_5e[pn[dir][3]]/2,darkColor:_61,lightColor:_60,whichAnim:wa,endColor:_65}));_4.connect(_63[i],"onEnd",_67);}return _4.fx.chain(_63);};_6.fx.flipGrid=function(_68){var _69=_68.rows||4,_6a=_68.cols||4,_6b=[],_6c=_4.create("div"),n=_68.node,_6d=_4.coords(n,true),x=_6d.x,y=_6d.y,nw=_6d.w,nh=_6d.h,w=_6d.w/_6a,h=_6d.h/_69,_6e=[];_4.style(_6c,{position:"absolute",width:w+"px",height:h+"px",backgroundColor:_4.style(n,"backgroundColor")});for(var i=0;i<_69;i++){var r=i%2,d=r?"right":"left",_6f=r?1:-1;var cn=_4.clone(n);_4.style(cn,{position:"absolute",width:nw+"px",height:nh+"px",top:y+"px",left:x+"px",clip:"rect("+i*h+"px,"+nw+"px,"+nh+"px,0)"});_4.body().appendChild(cn);_6b[i]=[];for(var j=0;j<_6a;j++){var hn=_4.clone(_6c),l=r?j:_6a-(j+1);var _70=function(xn,_71,_72){return function(){if(!(_71%2)){_4.style(xn,{clip:"rect("+_71*h+"px,"+(nw-(_72+1)*w)+"px,"+((_71+1)*h)+"px,0px)"});}else{_4.style(xn,{clip:"rect("+_71*h+"px,"+nw+"px,"+((_71+1)*h)+"px,"+((_72+1)*w)+"px)"});}};}(cn,i,j);_4.body().appendChild(hn);_4.style(hn,{left:x+l*w+"px",top:y+i*h+"px",visibility:"hidden"});var a=_6.fx.flipPage({node:hn,dir:d,duration:_68.duration||900,shift:_6f*w/2,depth:0.2,darkColor:_68.darkColor,lightColor:_68.lightColor,startColor:_68.startColor||_68.node.style.backgroundColor}),_73=function(xn){return function(){_4.destroy(xn);};}(hn);_4.connect(a,"play",this,_70);_4.connect(a,"play",this,_73);_6b[i].push(a);}_6e.push(_4.fx.chain(_6b[i]));}_4.connect(_6e[0],"play",function(){_4.style(n,{visibility:"hidden"});});return _4.fx.combine(_6e);};}if(!_4._hasResource["dojox.mobile.compat"]){_4._hasResource["dojox.mobile.compat"]=true;_4.provide("dojox.mobile.compat");if(!_4.isWebKit){_4.extend(_6.mobile.View,{_doTransition:function(_74,_75,_76,dir){var _77;this.wakeUp(_75);if(!_76||_76=="none"){_75.style.display="";_74.style.display="none";_75.style.left="0px";this.invokeCallback();}else{if(_76=="slide"){var w=_74.offsetWidth;var s1=_4.fx.slideTo({node:_74,duration:400,left:-w*dir,top:_74.offsetTop});var s2=_4.fx.slideTo({node:_75,duration:400,left:0});_75.style.position="absolute";_75.style.left=w*dir+"px";_75.style.display="";_77=_4.fx.combine([s1,s2]);_4.connect(_77,"onEnd",this,function(){_74.style.display="none";_75.style.position="relative";this.invokeCallback();});_77.play();}else{if(_76=="flip"){_77=_6.fx.flip({node:_74,dir:"right",depth:0.5,duration:400});_75.style.position="absolute";_75.style.left="0px";_4.connect(_77,"onEnd",this,function(){_74.style.display="none";_75.style.position="relative";_75.style.display="";this.invokeCallback();});_77.play();}else{if(_76=="fade"){_77=_4.fx.chain([_4.fadeOut({node:_74,duration:600}),_4.fadeIn({node:_75,duration:600})]);_75.style.position="absolute";_75.style.left="0px";_75.style.display="";_4.style(_75,"opacity",0);_4.connect(_77,"onEnd",this,function(){_74.style.display="none";_75.style.position="relative";_4.style(_74,"opacity",1);this.invokeCallback();});_77.play();}}}}},wakeUp:function(_78){if(_4.isIE&&!_78._wokeup){_78._wokeup=true;var _79=_78.style.display;_78.style.display="";var _7a=_78.getElementsByTagName("*");for(var i=0,len=_7a.length;i<len;i++){var val=_7a[i].style.display;_7a[i].style.display="none";_7a[i].style.display="";_7a[i].style.display=val;}_78.style.display=_79;}}});_4.extend(_6.mobile.Switch,{buildRendering:function(){this.domNode=this.srcNodeRef||_4.doc.createElement("DIV");this.domNode.className="mblSwitch";this.domNode.innerHTML="<div class=\"mblSwitchInner\">"+"<div class=\"mblSwitchBg mblSwitchBgLeft\">"+"<div class=\"mblSwitchCorner mblSwitchCorner1T\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner2T\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner3T\"></div>"+"<div class=\"mblSwitchText mblSwitchTextLeft\">"+this.leftLabel+"</div>"+"<div class=\"mblSwitchCorner mblSwitchCorner1B\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner2B\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner3B\"></div>"+"</div>"+"<div class=\"mblSwitchBg mblSwitchBgRight\">"+"<div class=\"mblSwitchCorner mblSwitchCorner1T\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner2T\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner3T\"></div>"+"<div class=\"mblSwitchText mblSwitchTextRight\">"+this.rightLabel+"</div>"+"<div class=\"mblSwitchCorner mblSwitchCorner1B\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner2B\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner3B\"></div>"+"</div>"+"<div class=\"mblSwitchKnobContainer\">"+"<div class=\"mblSwitchCorner mblSwitchCorner1T\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner2T\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner3T\"></div>"+"<div class=\"mblSwitchKnob\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner1B\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner2B\"></div>"+"<div class=\"mblSwitchCorner mblSwitchCorner3B\"></div>"+"</div>"+"</div>";var n=this.inner=this.domNode.firstChild;this.left=n.childNodes[0];this.right=n.childNodes[1];this.knob=n.childNodes[2];_4.addClass(this.domNode,(this.value=="on")?"mblSwitchOn":"mblSwitchOff");this[this.value=="off"?"left":"right"].style.display="none";},_changeState:function(_7b){if(!this.inner.parentNode||!this.inner.parentNode.tagName){_4.addClass(this.domNode,(_7b=="on")?"mblSwitchOn":"mblSwitchOff");return;}var pos;if(this.inner.offsetLeft==0){if(_7b=="on"){return;}pos=-53;}else{if(_7b=="off"){return;}pos=0;}var a=_4.fx.slideTo({node:this.inner,duration:500,left:pos});var _7c=this;_4.connect(a,"onEnd",function(){_7c[_7b=="off"?"left":"right"].style.display="none";});a.play();}});if(_4.isIE||_4.isBB){_4.extend(_6.mobile.RoundRect,{buildRendering:function(){_6.mobile.createRoundRect(this);this.domNode.className="mblRoundRect";}});_6.mobile.RoundRectList._addChild=_6.mobile.RoundRectList.prototype.addChild;_4.extend(_6.mobile.RoundRectList,{buildRendering:function(){_6.mobile.createRoundRect(this,true);this.domNode.className="mblRoundRectList";},postCreate:function(){this.redrawBorders();},addChild:function(_7d){_6.mobile.RoundRectList._addChild.apply(this,arguments);this.redrawBorders();if(_6.mobile.applyPngFilter){_6.mobile.applyPngFilter(_7d.domNode);}},redrawBorders:function(){var _7e=false;for(var i=this.containerNode.childNodes.length-1;i>=0;i--){var c=this.containerNode.childNodes[i];if(c.tagName=="LI"){c.style.borderBottomStyle=_7e?"solid":"none";_7e=true;}}}});_4.extend(_6.mobile.EdgeToEdgeList,{buildRendering:function(){this.domNode=this.containerNode=this.srcNodeRef||_4.doc.createElement("UL");this.domNode.className="mblEdgeToEdgeList";}});if(_6.mobile.IconContainer){_6.mobile.IconContainer._addChild=_6.mobile.IconContainer.prototype.addChild;_4.extend(_6.mobile.IconContainer,{addChild:function(_7f){_6.mobile.IconContainer._addChild.apply(this,arguments);if(_6.mobile.applyPngFilter){_6.mobile.applyPngFilter(_7f.domNode);}}});}_4.mixin(_6.mobile,{createRoundRect:function(_80,_81){var i;_80.domNode=_4.doc.createElement("DIV");_80.domNode.style.padding="0px";_80.domNode.style.backgroundColor="transparent";_80.domNode.style.borderStyle="none";_80.containerNode=_4.doc.createElement(_81?"UL":"DIV");_80.containerNode.className="mblRoundRectContainer";if(_80.srcNodeRef){_80.srcNodeRef.parentNode.replaceChild(_80.domNode,_80.srcNodeRef);for(i=0,len=_80.srcNodeRef.childNodes.length;i<len;i++){_80.containerNode.appendChild(_80.srcNodeRef.removeChild(_80.srcNodeRef.firstChild));}_80.srcNodeRef=null;}_80.domNode.appendChild(_80.containerNode);for(i=0;i<=5;i++){var top=_4.create("DIV");top.className="mblRoundCorner mblRoundCorner"+i+"T";_80.domNode.insertBefore(top,_80.containerNode);var _82=_4.create("DIV");_82.className="mblRoundCorner mblRoundCorner"+i+"B";_80.domNode.appendChild(_82);}}});if(_6.mobile.ScrollableView){_4.extend(_6.mobile.ScrollableView,{postCreate:function(){var _83=_4.create("DIV",{className:"mblDummyForIE",innerHTML:"&nbsp;"},this.containerNode,"first");_4.style(_83,{position:"relative",marginBottom:"-2px",fontSize:"1px"});}});}}if(_4.isIE<=6){_6.mobile.applyPngFilter=function(_84){_84=_84||_4.body();var _85=_84.getElementsByTagName("IMG");var _86=_4.moduleUrl("dojo","resources/blank.gif");for(var i=0,len=_85.length;i<len;i++){var img=_85[i];var w=img.offsetWidth;var h=img.offsetHeight;if(w===0||h===0){if(_4.style(img,"display")!="none"){continue;}img.style.display="";w=img.offsetWidth;h=img.offsetHeight;img.style.display="none";if(w===0||h===0){continue;}}var src=img.src;if(src.indexOf("resources/blank.gif")!=-1){continue;}img.src=_86;img.runtimeStyle.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+src+"')";img.style.width=w+"px";img.style.height=h+"px";}};}_6.mobile.loadCss=function(_87){if(!_4.global._loadedCss){var obj={};_4.forEach(_6.mobile.getCssPaths(),function(_88){obj[_88]=true;});_4.global._loadedCss=obj;}if(!_4.isArray(_87)){_87=[_87];}for(var i=0;i<_87.length;i++){var _89=_87[i];if(!_4.global._loadedCss[_89]){_4.global._loadedCss[_89]=true;if(_4.doc.createStyleSheet){setTimeout(function(_8a){return function(){_4.doc.createStyleSheet(_8a);};}(_89),0);}else{var _8b=_4.doc.createElement("link");_8b.href=_89;_8b.type="text/css";_8b.rel="stylesheet";var _8c=_4.doc.getElementsByTagName("head")[0];_8c.appendChild(_8b);}}}};_6.mobile.getCssPaths=function(){var _8d=[];var i,j;var s=_4.doc.styleSheets;for(i=0;i<s.length;i++){var r=s[i].cssRules||s[i].imports;if(!r){continue;}for(j=0;j<r.length;j++){if(r[j].href){_8d.push(r[j].href);}}}var _8e=_4.doc.getElementsByTagName("link");for(i=0,len=_8e.length;i<len;i++){if(_8e[i].href){_8d.push(_8e[i].href);}}return _8d;};_6.mobile.loadCompatPattern=/\/themes\/(domButtons|buttons|iphone|android).*\.css$/;_6.mobile.loadCompatCssFiles=function(){var _8f=_6.mobile.getCssPaths();for(var i=0;i<_8f.length;i++){var _90=_8f[i];if(_90.match(_6.mobile.loadCompatPattern)&&_90.indexOf("-compat.css")==-1){var _91=_90.substring(0,_90.length-4)+"-compat.css";_6.mobile.loadCss(_91);}}};_6.mobile.hideAddressBar=function(){};_4.addOnLoad(function(){if(_4.config["mblLoadCompatCssFiles"]!==false){_6.mobile.loadCompatCssFiles();}if(_6.mobile.applyPngFilter){_6.mobile.applyPngFilter();}});}}}};});
