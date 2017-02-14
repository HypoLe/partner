/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.layout.StackContainer"],["require","dijit._Templated"],["require","dijit.layout._LayoutWidget"],["requireLocalization","dijit","common",null,"ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw","ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw"],["require","dojo.cookie"],["require","dijit.layout.StackController"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.layout.StackContainer"]){_4._hasResource["dijit.layout.StackContainer"]=true;_4.provide("dijit.layout.StackContainer");_4.require("dijit._Templated");_4.require("dijit.layout._LayoutWidget");_4.require("dojo.cookie");_4.require("dijit.layout.StackController");_4.declare("dijit.layout.StackContainer",_5.layout._LayoutWidget,{doLayout:true,persist:false,baseClass:"dijitStackContainer",buildRendering:function(){this.inherited(arguments);_4.addClass(this.domNode,"dijitLayoutContainer");_5.setWaiRole(this.containerNode,"tabpanel");},postCreate:function(){this.inherited(arguments);this.connect(this.domNode,"onkeypress",this._onKeyPress);},startup:function(){if(this._started){return;}var _7=this.getChildren();_4.forEach(_7,this._setupChild,this);if(this.persist){this.selectedChildWidget=_5.byId(_4.cookie(this.id+"_selectedChild"));}else{_4.some(_7,function(_8){if(_8.selected){this.selectedChildWidget=_8;}return _8.selected;},this);}var _9=this.selectedChildWidget;if(!_9&&_7[0]){_9=this.selectedChildWidget=_7[0];_9.selected=true;}_4.publish(this.id+"-startup",[{children:_7,selected:_9}]);this.inherited(arguments);},resize:function(){var _a=this.selectedChildWidget;if(_a&&!this._hasBeenShown){this._hasBeenShown=true;this._showChild(_a);}this.inherited(arguments);},_setupChild:function(_b){this.inherited(arguments);_4.replaceClass(_b.domNode,"dijitHidden","dijitVisible");_b.domNode.title="";},addChild:function(_c,_d){this.inherited(arguments);if(this._started){_4.publish(this.id+"-addChild",[_c,_d]);this.layout();if(!this.selectedChildWidget){this.selectChild(_c);}}},removeChild:function(_e){this.inherited(arguments);if(this._started){_4.publish(this.id+"-removeChild",[_e]);}if(this._beingDestroyed){return;}if(this.selectedChildWidget===_e){this.selectedChildWidget=undefined;if(this._started){var _f=this.getChildren();if(_f.length){this.selectChild(_f[0]);}}}if(this._started){this.layout();}},selectChild:function(_10,_11){_10=_5.byId(_10);if(this.selectedChildWidget!=_10){var d=this._transition(_10,this.selectedChildWidget,_11);this._set("selectedChildWidget",_10);_4.publish(this.id+"-selectChild",[_10]);if(this.persist){_4.cookie(this.id+"_selectedChild",this.selectedChildWidget.id);}}return d;},_transition:function(_12,_13,_14){if(_13){this._hideChild(_13);}var d=this._showChild(_12);if(_12.resize){if(this.doLayout){_12.resize(this._containerContentBox||this._contentBox);}else{_12.resize();}}return d;},_adjacent:function(_15){var _16=this.getChildren();var _17=_4.indexOf(_16,this.selectedChildWidget);_17+=_15?1:_16.length-1;return _16[_17%_16.length];},forward:function(){return this.selectChild(this._adjacent(true),true);},back:function(){return this.selectChild(this._adjacent(false),true);},_onKeyPress:function(e){_4.publish(this.id+"-containerKeyPress",[{e:e,page:this}]);},layout:function(){if(this.doLayout&&this.selectedChildWidget&&this.selectedChildWidget.resize){this.selectedChildWidget.resize(this._containerContentBox||this._contentBox);}},_showChild:function(_18){var _19=this.getChildren();_18.isFirstChild=(_18==_19[0]);_18.isLastChild=(_18==_19[_19.length-1]);_18._set("selected",true);_4.replaceClass(_18.domNode,"dijitVisible","dijitHidden");return _18._onShow()||true;},_hideChild:function(_1a){_1a._set("selected",false);_4.replaceClass(_1a.domNode,"dijitHidden","dijitVisible");_1a.onHide();},closeChild:function(_1b){var _1c=_1b.onClose(this,_1b);if(_1c){this.removeChild(_1b);_1b.destroyRecursive();}},destroyDescendants:function(_1d){_4.forEach(this.getChildren(),function(_1e){this.removeChild(_1e);_1e.destroyRecursive(_1d);},this);}});_4.extend(_5._Widget,{selected:false,closable:false,iconClass:"",showTitle:true});}}};});