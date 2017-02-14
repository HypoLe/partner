/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojo.dnd.Source"],["require","dojo.dnd.Selector"],["require","dojo.dnd.Manager"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojo.dnd.Source"]){_4._hasResource["dojo.dnd.Source"]=true;_4.provide("dojo.dnd.Source");_4.require("dojo.dnd.Selector");_4.require("dojo.dnd.Manager");_4.declare("dojo.dnd.Source",_4.dnd.Selector,{isSource:true,horizontal:false,copyOnly:false,selfCopy:false,selfAccept:true,skipForm:false,withHandles:false,autoSync:false,delay:0,accept:["text"],generateText:true,constructor:function(_7,_8){_4.mixin(this,_4.mixin({},_8));var _9=this.accept;if(_9.length){this.accept={};for(var i=0;i<_9.length;++i){this.accept[_9[i]]=1;}}this.isDragging=false;this.mouseDown=false;this.targetAnchor=null;this.targetBox=null;this.before=true;this._lastX=0;this._lastY=0;this.sourceState="";if(this.isSource){_4.addClass(this.node,"dojoDndSource");}this.targetState="";if(this.accept){_4.addClass(this.node,"dojoDndTarget");}if(this.horizontal){_4.addClass(this.node,"dojoDndHorizontal");}this.topics=[_4.subscribe("/dnd/source/over",this,"onDndSourceOver"),_4.subscribe("/dnd/start",this,"onDndStart"),_4.subscribe("/dnd/drop",this,"onDndDrop"),_4.subscribe("/dnd/cancel",this,"onDndCancel")];},checkAcceptance:function(_a,_b){if(this==_a){return !this.copyOnly||this.selfAccept;}for(var i=0;i<_b.length;++i){var _c=_a.getItem(_b[i].id).type;var _d=false;for(var j=0;j<_c.length;++j){if(_c[j] in this.accept){_d=true;break;}}if(!_d){return false;}}return true;},copyState:function(_e,_f){if(_e){return true;}if(arguments.length<2){_f=this==_4.dnd.manager().target;}if(_f){if(this.copyOnly){return this.selfCopy;}}else{return this.copyOnly;}return false;},destroy:function(){_4.dnd.Source.superclass.destroy.call(this);_4.forEach(this.topics,_4.unsubscribe);this.targetAnchor=null;},markupFactory:function(_10,_11){_10._skipStartup=true;return new _4.dnd.Source(_11,_10);},onMouseMove:function(e){if(this.isDragging&&this.targetState=="Disabled"){return;}_4.dnd.Source.superclass.onMouseMove.call(this,e);var m=_4.dnd.manager();if(!this.isDragging){if(this.mouseDown&&this.isSource&&(Math.abs(e.pageX-this._lastX)>this.delay||Math.abs(e.pageY-this._lastY)>this.delay)){var _12=this.getSelectedNodes();if(_12.length){m.startDrag(this,_12,this.copyState(_4.isCopyKey(e),true));}}}if(this.isDragging){var _13=false;if(this.current){if(!this.targetBox||this.targetAnchor!=this.current){this.targetBox=_4.position(this.current,true);}if(this.horizontal){_13=(e.pageX-this.targetBox.x)<(this.targetBox.w/2);}else{_13=(e.pageY-this.targetBox.y)<(this.targetBox.h/2);}}if(this.current!=this.targetAnchor||_13!=this.before){this._markTargetAnchor(_13);m.canDrop(!this.current||m.source!=this||!(this.current.id in this.selection));}}},onMouseDown:function(e){if(!this.mouseDown&&this._legalMouseDown(e)&&(!this.skipForm||!_4.dnd.isFormElement(e))){this.mouseDown=true;this._lastX=e.pageX;this._lastY=e.pageY;_4.dnd.Source.superclass.onMouseDown.call(this,e);}},onMouseUp:function(e){if(this.mouseDown){this.mouseDown=false;_4.dnd.Source.superclass.onMouseUp.call(this,e);}},onDndSourceOver:function(_14){if(this!=_14){this.mouseDown=false;if(this.targetAnchor){this._unmarkTargetAnchor();}}else{if(this.isDragging){var m=_4.dnd.manager();m.canDrop(this.targetState!="Disabled"&&(!this.current||m.source!=this||!(this.current.id in this.selection)));}}},onDndStart:function(_15,_16,_17){if(this.autoSync){this.sync();}if(this.isSource){this._changeState("Source",this==_15?(_17?"Copied":"Moved"):"");}var _18=this.accept&&this.checkAcceptance(_15,_16);this._changeState("Target",_18?"":"Disabled");if(this==_15){_4.dnd.manager().overSource(this);}this.isDragging=true;},onDndDrop:function(_19,_1a,_1b,_1c){if(this==_1c){this.onDrop(_19,_1a,_1b);}this.onDndCancel();},onDndCancel:function(){if(this.targetAnchor){this._unmarkTargetAnchor();this.targetAnchor=null;}this.before=true;this.isDragging=false;this.mouseDown=false;this._changeState("Source","");this._changeState("Target","");},onDrop:function(_1d,_1e,_1f){if(this!=_1d){this.onDropExternal(_1d,_1e,_1f);}else{this.onDropInternal(_1e,_1f);}},onDropExternal:function(_20,_21,_22){var _23=this._normalizedCreator;if(this.creator){this._normalizedCreator=function(_24,_25){return _23.call(this,_20.getItem(_24.id).data,_25);};}else{if(_22){this._normalizedCreator=function(_26,_27){var t=_20.getItem(_26.id);var n=_26.cloneNode(true);n.id=_4.dnd.getUniqueId();return {node:n,data:t.data,type:t.type};};}else{this._normalizedCreator=function(_28,_29){var t=_20.getItem(_28.id);_20.delItem(_28.id);return {node:_28,data:t.data,type:t.type};};}}this.selectNone();if(!_22&&!this.creator){_20.selectNone();}this.insertNodes(true,_21,this.before,this.current);if(!_22&&this.creator){_20.deleteSelectedNodes();}this._normalizedCreator=_23;},onDropInternal:function(_2a,_2b){var _2c=this._normalizedCreator;if(this.current&&this.current.id in this.selection){return;}if(_2b){if(this.creator){this._normalizedCreator=function(_2d,_2e){return _2c.call(this,this.getItem(_2d.id).data,_2e);};}else{this._normalizedCreator=function(_2f,_30){var t=this.getItem(_2f.id);var n=_2f.cloneNode(true);n.id=_4.dnd.getUniqueId();return {node:n,data:t.data,type:t.type};};}}else{if(!this.current){return;}this._normalizedCreator=function(_31,_32){var t=this.getItem(_31.id);return {node:_31,data:t.data,type:t.type};};}this._removeSelection();this.insertNodes(true,_2a,this.before,this.current);this._normalizedCreator=_2c;},onDraggingOver:function(){},onDraggingOut:function(){},onOverEvent:function(){_4.dnd.Source.superclass.onOverEvent.call(this);_4.dnd.manager().overSource(this);if(this.isDragging&&this.targetState!="Disabled"){this.onDraggingOver();}},onOutEvent:function(){_4.dnd.Source.superclass.onOutEvent.call(this);_4.dnd.manager().outSource(this);if(this.isDragging&&this.targetState!="Disabled"){this.onDraggingOut();}},_markTargetAnchor:function(_33){if(this.current==this.targetAnchor&&this.before==_33){return;}if(this.targetAnchor){this._removeItemClass(this.targetAnchor,this.before?"Before":"After");}this.targetAnchor=this.current;this.targetBox=null;this.before=_33;if(this.targetAnchor){this._addItemClass(this.targetAnchor,this.before?"Before":"After");}},_unmarkTargetAnchor:function(){if(!this.targetAnchor){return;}this._removeItemClass(this.targetAnchor,this.before?"Before":"After");this.targetAnchor=null;this.targetBox=null;this.before=true;},_markDndStatus:function(_34){this._changeState("Source",_34?"Copied":"Moved");},_legalMouseDown:function(e){if(!_4.mouseButtons.isLeft(e)){return false;}if(!this.withHandles){return true;}for(var _35=e.target;_35&&_35!==this.node;_35=_35.parentNode){if(_4.hasClass(_35,"dojoDndHandle")){return true;}if(_4.hasClass(_35,"dojoDndItem")||_4.hasClass(_35,"dojoDndIgnore")){break;}}return false;}});_4.declare("dojo.dnd.Target",_4.dnd.Source,{constructor:function(_36,_37){this.isSource=false;_4.removeClass(this.node,"dojoDndSource");},markupFactory:function(_38,_39){_38._skipStartup=true;return new _4.dnd.Target(_39,_38);}});_4.declare("dojo.dnd.AutoSource",_4.dnd.Source,{constructor:function(_3a,_3b){this.autoSync=true;},markupFactory:function(_3c,_3d){_3c._skipStartup=true;return new _4.dnd.AutoSource(_3d,_3c);}});}}};});