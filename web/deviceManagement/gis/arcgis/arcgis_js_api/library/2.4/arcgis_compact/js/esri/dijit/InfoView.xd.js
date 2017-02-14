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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.InfoView"],["require","esri.dijit._TouchBase"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.InfoView"]){_4._hasResource["esri.dijit.InfoView"]=true;_4.provide("esri.dijit.InfoView");_4.require("esri.dijit._TouchBase");_4.declare("esri.dijit.InfoView",null,{_items:[],_top:null,_sections:[],_isDecelerate:false,constructor:function(_7,_8){this.container=_4.byId(_8);this._touchBase=esri.dijit._TouchBase(this.container,null);this._slideDiv=_4.create("div",null,this.container,"first");this.events=[];this._items=_7.items;if(_7.sections){this._sections=_7.sections;}this.container.setAttribute((document.all?"className":"class"),"esriMobileInfoView");if(this._sections.length===0){_4.create("div",{},this._slideDiv);}else{for(var i=0;i<this._sections.length;i++){var _9=_4.create("div",{"class":"esriMobileInfoViewSection"},this._slideDiv);_4.create("div",{innerHTML:this._sections[i].title},_9);}}for(var i=0;i<this._items.length;i++){var _a,_b;var _c=0;if(this._items[i].section){_c=this._items[i].section;}switch(this._items[i].type){case "div":_b=_4.create("div",{"class":"esriMobileInfoViewItem",style:this._items[i].style},this._slideDiv.childNodes[_c]);_a=_4.create("div",{innerHTML:this._items[i].text},_b);break;}if(this._items[i].className){_4.addClass(_a,this._items[i].className);}_a._index=i;_a._item=this._items[i];this._items[i]._node=_a;}this.startTouchY=0;},startup:function(){this.onCreate(this._items);this._animateTo(0);},destroy:function(){_4.forEach(this.events,_4.disconnect);this._touchBase=null;_4.query("img",this.container).forEach(function(_d){_d._index=null;_d._item=null;_4.destroy(_d);_d=null;});this._items=null;_4.destroy(this._slideDiv);_4.destroy(this.container);this.container=this._slideDiv=null;},getItems:function(){return this._items;},setPreventDefault:function(_e){this._touchBase.setPreventDefault(_e);},enableTouchScroll:function(){this._touchBase.setPreventDefault(true);this.events.push(_4.connect(this._touchBase,"onTouchStart",this,this._onTouchStartHandler));this.events.push(_4.connect(this._touchBase,"onTouchMove",this,this._onTouchMoveHandler));this.events.push(_4.connect(this._touchBase,"onTouchEnd",this,this._onTouchEndHandler));this._slideDiv.style.webkitTransform="translate3d(0,"+this._top+"px, 0)";},disableTouchScroll:function(){_4.disconnect(this.events.pop());_4.disconnect(this.events.pop());_4.disconnect(this.events.pop());this._touchBase.setPreventDefault(false);this._slideDiv.style.webkitTransform="translate3d(0, 0px, 0)";},animateTo:function(){this._slideDiv.style.WebkitTransitionDuration="0s";this._animateTo(0);},onSelect:function(_f){},onUnSelect:function(_10){},onCreate:function(_11){},onClick:function(e){},onSwipeLeft:function(){},onSwipeRight:function(){},_onTouchStartHandler:function(e){this._slideDiv.style.WebkitTransitionDuration="0s";this._moveDirection=null;this._startTime=new Date();this.startTouchY=e.touches[0].clientY;this.contentStartOffsetY=this.contentOffsetY;},_onTouchMoveHandler:function(e){if(!this._moveDirection){if(Math.abs(e.curY)>Math.abs(e.curX)){this._moveDirection="vertical";}else{this._moveDirection="horizontal";}}if(this._moveDirection==="horizontal"){return;}else{if(this._moveDirection==="vertical"){var _12=e.touches[0].clientY;var _13=_12-this.startTouchY;var _14=_13+this.contentStartOffsetY;this._animateTo(_14);}}},_onTouchEndHandler:function(e){this._endTime=new Date();this._deltaMovement=e.curY;if(this._moveDirection==="vertical"){if(this._shouldStartMomentum()){this._doMomentum();}else{this._snapToBounds();}}else{if(this._moveDirection==="horizontal"){if(e.swipeDirection==="left"){this.onSwipeLeft();}else{if(e.swipeDirection==="right"){this.onSwipeRight();}}}}},_shouldStartMomentum:function(){this._diff=this._endTime-this._startTime;this._velocity=this._deltaMovement/this._diff;if(Math.abs(this._velocity)>0.2&&this._diff<200){return true;}else{return false;}},_pullToStop:function(_15){if(Math.abs(_15)>80){_15=_15>0?80:(-contentBox.h+parentBox.h-10)-80;}console.log(_15);this._slideDiv.style.webkitTransition="-webkit-transform 200ms cubic-bezier(0, 0, 1, 1)";var _16=_4.connect(this._slideDiv,"webkitTransitionEnd",this,function(){if(_15>0){this._animateTo(0);}else{this._animateTo(-contentBox.h+parentBox.h-10);}_4.disconnect(_16);});this._animateTo(_15);},_doMomentum:function(){var _17=_4.contentBox(this.container);var _18=this._velocity<0?0.001:-0.001;var _19=-(this._velocity*this._velocity)/(2*_18);var _1a=-this._velocity/_18;var p0={x:0,y:0};var p1={x:0,y:0.3};var p2={x:0.6,y:1};var p3={x:1,y:1};var cx=3*(p1.x-p0.x);var bx=3*(p2.x-p1.x)-cx;var ax=p3.x-p0.x-cx-bx;var cy=3*(p1.y-p0.y);var by=3*(p2.y-p1.y)-cy;var ay=p3.y-p0.y-cy-by;var _1b=0,_1c=0;if(_17.h>this._slideDiv.scrollHeight){this.contentOffsetY=0;_1c=300;}else{if(this.contentOffsetY+_19>0){for(var i=0,il=Math.floor(_1a/20);i<il;i++){_1b=(ax*(i*20)^3)+(bx*(i*20)^2)+cx*(i*20)+p0.x;_1b=this._velocity<0?-_1b:_1b;if(this.contentOffsetY+_1b>0){_1c=i*20;break;}}if(_1c===0){_1c=300;}this.contentOffsetY=0;}else{if(Math.abs(this.contentOffsetY+_19)+_17.h>this._slideDiv.scrollHeight){this.contentOffsetY=_17.h-this._slideDiv.scrollHeight;for(var i=0,il=Math.floor(_1a/20);i<il;i++){_1b=(ax*(i*20)^3)+(bx*(i*20)^2)+cx*(i*20)+p0.x;_1b=this._velocity<0?-_1b:_1b;if(Math.abs(this.contentOffsetY+_1b)>this._slideDiv.scrollHeight){_1c=i*20;break;}}}else{_1c=_1a;this.contentOffsetY=this.contentOffsetY+_19;}}}this._slideDiv.style.webkitTransition="-webkit-transform "+_1c+"ms cubic-bezier(0, 0.3, 0.6, 1)";this._animateTo(this.contentOffsetY);},_snapToBounds:function(){var _1d=_4.contentBox(this.container);if(_1d.h>this._slideDiv.scrollHeight){this.contentOffsetY=0;}else{if(this.contentOffsetY>0){this.contentOffsetY=0;}else{if(Math.abs(this.contentOffsetY)+_1d.h>this._slideDiv.scrollHeight){this.contentOffsetY=_1d.h-this._slideDiv.scrollHeight;}}}this._slideDiv.style.WebkitTransitionDuration="0.5s";this._animateTo(this.contentOffsetY);},_animateTo:function(_1e){this.contentOffsetY=_1e;this._slideDiv.style.webkitTransform="translate3d(0, "+_1e+"px, 0)";},_stopMomentum:function(){if(this._isDecelerating()){var _1f=document.defaultView.getComputedStyle(this._slideDiv,null);var _20=new WebKitCSSMatrix(_1f.webkitTransform);this._slideDiv.style.webkitTransition="";this.animateTo(_20.m42);}},_isDecelerating:function(){if(this.isDecelerate){return true;}else{return false;}},_toggleNode:function(_21,_22){if(_22.toggleState==="ON"){_22.toggleState="OFF";if(_22.src){_21.src=_22.src;}this.onUnSelect(_22);}else{_22.toggleState="ON";if(_22.srcAlt){_21.src=_22.srcAlt;}this.onSelect(_22);}}});}}};});