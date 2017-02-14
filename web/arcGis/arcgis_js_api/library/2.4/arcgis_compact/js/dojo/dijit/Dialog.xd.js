/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.Dialog"],["require","dojo.dnd.move"],["require","dojo.dnd.TimedMoveable"],["require","dojo.fx"],["require","dojo.window"],["require","dijit._Widget"],["require","dijit._Templated"],["require","dijit._CssStateMixin"],["require","dijit.form._FormMixin"],["require","dijit._DialogMixin"],["require","dijit.DialogUnderlay"],["require","dijit.layout.ContentPane"],["requireLocalization","dijit","common",null,"ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw","ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw"],["require","dijit.TooltipDialog"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.Dialog"]){_4._hasResource["dijit.Dialog"]=true;_4.provide("dijit.Dialog");_4.require("dojo.dnd.move");_4.require("dojo.dnd.TimedMoveable");_4.require("dojo.fx");_4.require("dojo.window");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.require("dijit._CssStateMixin");_4.require("dijit.form._FormMixin");_4.require("dijit._DialogMixin");_4.require("dijit.DialogUnderlay");_4.require("dijit.layout.ContentPane");_4.require("dijit.TooltipDialog");_4.declare("dijit._DialogBase",[_5._Templated,_5.form._FormMixin,_5._DialogMixin,_5._CssStateMixin],{templateString:_4.cache("dijit","templates/Dialog.html","<div class=\"dijitDialog\" role=\"dialog\" aria-labelledby=\"${id}_title\">\r\n\t<div dojoAttachPoint=\"titleBar\" class=\"dijitDialogTitleBar\">\r\n\t<span dojoAttachPoint=\"titleNode\" class=\"dijitDialogTitle\" id=\"${id}_title\"></span>\r\n\t<span dojoAttachPoint=\"closeButtonNode\" class=\"dijitDialogCloseIcon\" dojoAttachEvent=\"ondijitclick: onCancel\" title=\"${buttonCancel}\" role=\"button\" tabIndex=\"-1\">\r\n\t\t<span dojoAttachPoint=\"closeText\" class=\"closeText\" title=\"${buttonCancel}\">x</span>\r\n\t</span>\r\n\t</div>\r\n\t\t<div dojoAttachPoint=\"containerNode\" class=\"dijitDialogPaneContent\"></div>\r\n</div>\r\n"),baseClass:"dijitDialog",cssStateNodes:{closeButtonNode:"dijitDialogCloseIcon"},attributeMap:_4.delegate(_5._Widget.prototype.attributeMap,{title:[{node:"titleNode",type:"innerHTML"},{node:"titleBar",type:"attribute"}],"aria-describedby":""}),open:false,duration:_5.defaultDuration,refocus:true,autofocus:true,_firstFocusItem:null,_lastFocusItem:null,doLayout:false,draggable:true,"aria-describedby":"",postMixInProperties:function(){var _7=_4.i18n.getLocalization("dijit","common");_4.mixin(this,_7);this.inherited(arguments);},postCreate:function(){_4.style(this.domNode,{display:"none",position:"absolute"});_4.body().appendChild(this.domNode);this.inherited(arguments);this.connect(this,"onExecute","hide");this.connect(this,"onCancel","hide");this._modalconnects=[];},onLoad:function(){this._position();if(this.autofocus&&_5._DialogLevelManager.isTop(this)){this._getFocusItems(this.domNode);_5.focus(this._firstFocusItem);}this.inherited(arguments);},_endDrag:function(e){if(e&&e.node&&e.node===this.domNode){this._relativePosition=_4.position(e.node);}},_setup:function(){var _8=this.domNode;if(this.titleBar&&this.draggable){this._moveable=(_4.isIE==6)?new _4.dnd.TimedMoveable(_8,{handle:this.titleBar}):new _4.dnd.Moveable(_8,{handle:this.titleBar,timeout:0});this._dndListener=_4.subscribe("/dnd/move/stop",this,"_endDrag");}else{_4.addClass(_8,"dijitDialogFixed");}this.underlayAttrs={dialogId:this.id,"class":_4.map(this["class"].split(/\s/),function(s){return s+"_underlay";}).join(" ")};},_size:function(){this._checkIfSingleChild();if(this._singleChild){if(this._singleChildOriginalStyle){this._singleChild.domNode.style.cssText=this._singleChildOriginalStyle;}delete this._singleChildOriginalStyle;}else{_4.style(this.containerNode,{width:"auto",height:"auto"});}var mb=_4._getMarginSize(this.domNode);var _9=_4.window.getBox();if(mb.w>=_9.w||mb.h>=_9.h){var w=Math.min(mb.w,Math.floor(_9.w*0.75)),h=Math.min(mb.h,Math.floor(_9.h*0.75));if(this._singleChild&&this._singleChild.resize){this._singleChildOriginalStyle=this._singleChild.domNode.style.cssText;this._singleChild.resize({w:w,h:h});}else{_4.style(this.containerNode,{width:w+"px",height:h+"px",overflow:"auto",position:"relative"});}}else{if(this._singleChild&&this._singleChild.resize){this._singleChild.resize();}}},_position:function(){if(!_4.hasClass(_4.body(),"dojoMove")){var _a=this.domNode,_b=_4.window.getBox(),p=this._relativePosition,bb=p?null:_4._getBorderBox(_a),l=Math.floor(_b.l+(p?p.x:(_b.w-bb.w)/2)),t=Math.floor(_b.t+(p?p.y:(_b.h-bb.h)/2));_4.style(_a,{left:l+"px",top:t+"px"});}},_onKey:function(_c){if(_c.charOrCode){var dk=_4.keys;var _d=_c.target;if(_c.charOrCode===dk.TAB){this._getFocusItems(this.domNode);}var _e=(this._firstFocusItem==this._lastFocusItem);if(_d==this._firstFocusItem&&_c.shiftKey&&_c.charOrCode===dk.TAB){if(!_e){_5.focus(this._lastFocusItem);}_4.stopEvent(_c);}else{if(_d==this._lastFocusItem&&_c.charOrCode===dk.TAB&&!_c.shiftKey){if(!_e){_5.focus(this._firstFocusItem);}_4.stopEvent(_c);}else{while(_d){if(_d==this.domNode||_4.hasClass(_d,"dijitPopup")){if(_c.charOrCode==dk.ESCAPE){this.onCancel();}else{return;}}_d=_d.parentNode;}if(_c.charOrCode!==dk.TAB){_4.stopEvent(_c);}else{if(!_4.isOpera){try{this._firstFocusItem.focus();}catch(e){}}}}}}},show:function(){if(this.open){return;}if(!this._started){this.startup();}if(!this._alreadyInitialized){this._setup();this._alreadyInitialized=true;}if(this._fadeOutDeferred){this._fadeOutDeferred.cancel();}this._modalconnects.push(_4.connect(window,"onscroll",this,"layout"));this._modalconnects.push(_4.connect(window,"onresize",this,function(){var _f=_4.window.getBox();if(!this._oldViewport||_f.h!=this._oldViewport.h||_f.w!=this._oldViewport.w){this.layout();this._oldViewport=_f;}}));this._modalconnects.push(_4.connect(this.domNode,"onkeypress",this,"_onKey"));_4.style(this.domNode,{opacity:0,display:""});this._set("open",true);this._onShow();this._size();this._position();var _10;this._fadeInDeferred=new _4.Deferred(_4.hitch(this,function(){_10.stop();delete this._fadeInDeferred;}));_10=_4.fadeIn({node:this.domNode,duration:this.duration,beforeBegin:_4.hitch(this,function(){_5._DialogLevelManager.show(this,this.underlayAttrs);}),onEnd:_4.hitch(this,function(){if(this.autofocus&&_5._DialogLevelManager.isTop(this)){this._getFocusItems(this.domNode);_5.focus(this._firstFocusItem);}this._fadeInDeferred.callback(true);delete this._fadeInDeferred;})}).play();return this._fadeInDeferred;},hide:function(){if(!this._alreadyInitialized){return;}if(this._fadeInDeferred){this._fadeInDeferred.cancel();}var _11;this._fadeOutDeferred=new _4.Deferred(_4.hitch(this,function(){_11.stop();delete this._fadeOutDeferred;}));_11=_4.fadeOut({node:this.domNode,duration:this.duration,onEnd:_4.hitch(this,function(){this.domNode.style.display="none";_5._DialogLevelManager.hide(this);this.onHide();this._fadeOutDeferred.callback(true);delete this._fadeOutDeferred;})}).play();if(this._scrollConnected){this._scrollConnected=false;}_4.forEach(this._modalconnects,_4.disconnect);this._modalconnects=[];if(this._relativePosition){delete this._relativePosition;}this._set("open",false);return this._fadeOutDeferred;},layout:function(){if(this.domNode.style.display!="none"){if(_5._underlay){_5._underlay.layout();}this._position();}},destroy:function(){if(this._fadeInDeferred){this._fadeInDeferred.cancel();}if(this._fadeOutDeferred){this._fadeOutDeferred.cancel();}if(this._moveable){this._moveable.destroy();}if(this._dndListener){_4.unsubscribe(this._dndListener);}_4.forEach(this._modalconnects,_4.disconnect);_5._DialogLevelManager.hide(this);this.inherited(arguments);}});_4.declare("dijit.Dialog",[_5.layout.ContentPane,_5._DialogBase],{});_5._DialogLevelManager={show:function(_12,_13){var ds=_5._dialogStack;ds[ds.length-1].focus=_5.getFocus(_12);var _14=_5._underlay;if(!_14||_14._destroyed){_14=_5._underlay=new _5.DialogUnderlay(_13);}else{_14.set(_12.underlayAttrs);}var _15=ds[ds.length-1].dialog?ds[ds.length-1].zIndex+2:950;if(ds.length==1){_14.show();}_4.style(_5._underlay.domNode,"zIndex",_15-1);_4.style(_12.domNode,"zIndex",_15);ds.push({dialog:_12,underlayAttrs:_13,zIndex:_15});},hide:function(_16){var ds=_5._dialogStack;if(ds[ds.length-1].dialog==_16){ds.pop();var pd=ds[ds.length-1];if(ds.length==1){if(!_5._underlay._destroyed){_5._underlay.hide();}}else{_4.style(_5._underlay.domNode,"zIndex",pd.zIndex-1);_5._underlay.set(pd.underlayAttrs);}if(_16.refocus){var _17=pd.focus;if(!_17||(pd.dialog&&!_4.isDescendant(_17.node,pd.dialog.domNode))){pd.dialog._getFocusItems(pd.dialog.domNode);_17=pd.dialog._firstFocusItem;}try{_5.focus(_17);}catch(e){}}}else{var idx=_4.indexOf(_4.map(ds,function(_18){return _18.dialog;}),_16);if(idx!=-1){ds.splice(idx,1);}}},isTop:function(_19){var ds=_5._dialogStack;return ds[ds.length-1].dialog==_19;}};_5._dialogStack=[{dialog:null,focus:null,underlayAttrs:null}];}}};});