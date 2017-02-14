/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.tree.ForestStoreModel"],["require","dijit.tree.TreeStoreModel"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.tree.ForestStoreModel"]){_4._hasResource["dijit.tree.ForestStoreModel"]=true;_4.provide("dijit.tree.ForestStoreModel");_4.require("dijit.tree.TreeStoreModel");_4.declare("dijit.tree.ForestStoreModel",_5.tree.TreeStoreModel,{rootId:"$root$",rootLabel:"ROOT",query:null,constructor:function(_7){this.root={store:this,root:true,id:_7.rootId,label:_7.rootLabel,children:_7.rootChildren};},mayHaveChildren:function(_8){return _8===this.root||this.inherited(arguments);},getChildren:function(_9,_a,_b){if(_9===this.root){if(this.root.children){_a(this.root.children);}else{this.store.fetch({query:this.query,onComplete:_4.hitch(this,function(_c){this.root.children=_c;_a(_c);}),onError:_b});}}else{this.inherited(arguments);}},isItem:function(_d){return (_d===this.root)?true:this.inherited(arguments);},fetchItemByIdentity:function(_e){if(_e.identity==this.root.id){var _f=_e.scope?_e.scope:_4.global;if(_e.onItem){_e.onItem.call(_f,this.root);}}else{this.inherited(arguments);}},getIdentity:function(_10){return (_10===this.root)?this.root.id:this.inherited(arguments);},getLabel:function(_11){return (_11===this.root)?this.root.label:this.inherited(arguments);},newItem:function(_12,_13,_14){if(_13===this.root){this.onNewRootItem(_12);return this.store.newItem(_12);}else{return this.inherited(arguments);}},onNewRootItem:function(_15){},pasteItem:function(_16,_17,_18,_19,_1a){if(_17===this.root){if(!_19){this.onLeaveRoot(_16);}}_5.tree.TreeStoreModel.prototype.pasteItem.call(this,_16,_17===this.root?null:_17,_18===this.root?null:_18,_19,_1a);if(_18===this.root){this.onAddToRoot(_16);}},onAddToRoot:function(_1b){console.log(this,": item ",_1b," added to root");},onLeaveRoot:function(_1c){console.log(this,": item ",_1c," removed from root");},_requeryTop:function(){var _1d=this.root.children||[];this.store.fetch({query:this.query,onComplete:_4.hitch(this,function(_1e){this.root.children=_1e;if(_1d.length!=_1e.length||_4.some(_1d,function(_1f,idx){return _1e[idx]!=_1f;})){this.onChildrenChange(this.root,_1e);}})});},onNewItem:function(_20,_21){this._requeryTop();this.inherited(arguments);},onDeleteItem:function(_22){if(_4.indexOf(this.root.children,_22)!=-1){this._requeryTop();}this.inherited(arguments);},onSetItem:function(_23,_24,_25,_26){this._requeryTop();this.inherited(arguments);}});}}};});