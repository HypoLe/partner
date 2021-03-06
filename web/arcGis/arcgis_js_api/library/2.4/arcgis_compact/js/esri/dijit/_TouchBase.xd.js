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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit._TouchBase"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit._TouchBase"]){_4._hasResource["esri.dijit._TouchBase"]=true;_4.provide("esri.dijit._TouchBase");_4.declare("esri.dijit._TouchBase",null,{_preventDefault:true,_swipeThreshold:20,constructor:function(_7,_8){this.domNode=_4.byId(_7);this.events=[_4.connect(this.domNode,"touchstart",this,this._touchStartHandler),_4.connect(this.domNode,"touchmove",this,this._touchMoveHandler),_4.connect(this.domNode,"touchend",this,this._touchEndHandler),_4.connect(this.domNode,"onclick",this,this._clickHandler)];},setPreventDefault:function(_9){this._preventDefault=_9;},disableOnClick:function(){_4.disconnect(this.events.pop());},_clickHandler:function(e){if(!this._moved){this.onclick(e);}else{e.preventDefault();}},_touchStartHandler:function(e){this._moved=false;this.client_x=e.targetTouches[0].clientX;this.client_y=e.targetTouches[0].clientY;this.down_x=e.targetTouches[0].pageX;this.down_y=e.targetTouches[0].pageY;e.downX=this.down_x;e.downY=this.down_y;this.onTouchStart(e);},_touchMoveHandler:function(e){if(this._preventDefault){e.preventDefault();}this._moved=true;this.up_x=e.targetTouches[0].pageX;this.cur_x=e.targetTouches[0].pageX-this.down_x;this.cur_y=e.targetTouches[0].pageY-this.down_y;e.curX=this.cur_x;e.curY=this.cur_y;this.onTouchMove(e);},_touchEndHandler:function(e){if(!this._moved){e.clientX=this.client_x;e.clientY=this.client_y;}else{e.curX=this.cur_x;e.curY=this.cur_y;if(this.down_x-this.up_x>this._swipeThreshold){e.swipeDirection="left";}else{if(this.up_x-this.down_x>this._swipeThreshold){e.swipeDirection="right";}}}this.onTouchEnd(e);},onTouchStart:function(_a){},onTouchMove:function(_b){},onTouchEnd:function(_c){},onclick:function(_d){}});}}};});