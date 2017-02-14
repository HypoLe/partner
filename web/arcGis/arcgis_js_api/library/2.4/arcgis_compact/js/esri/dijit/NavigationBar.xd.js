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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.dijit.NavigationBar"],["require","esri.dijit._TouchBase"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.dijit.NavigationBar"]){_4._hasResource["esri.dijit.NavigationBar"]=true;_4.provide("esri.dijit.NavigationBar");_4.require("esri.dijit._TouchBase");_4.declare("esri.dijit.NavigationBar",null,{_items:[],constructor:function(_7,_8){this.container=_4.byId(_8);this._touchBase=esri.dijit._TouchBase(this.container,null);this._slideDiv=_4.create("div",{},this.container,"first");this.events=[_4.connect(this._touchBase,"onclick",this,this._onClickHandler)];this._items=_7.items;this.container.setAttribute((document.all?"className":"class"),"esriMobileNavigationBar");var _9=_4.create("div",{},this._slideDiv);for(var i=0;i<this._items.length;i++){var _a,_b;switch(this._items[i].type){case "img":_b=_4.create("div",{"class":"esriMobileNavigationItem"},_9);_a=_4.create("img",{src:this._items[i].src,style:{width:"100%",height:"100%"}},_b);break;case "span":_b=_4.create("div",{"class":"esriMobileNavigationItem"},_9);_a=_4.create("span",{innerHTML:this._items[i].text},_b);break;case "div":_b=_4.create("div",{"class":"esriMobileNavigationInfoPanel"},_9);_a=_4.create("div",{innerHTML:this._items[i].text},_b);break;}_4.addClass(_b,this._items[i].position);if(this._items[i].className){_4.addClass(_a,this._items[i].className);}_a._index=i;_a._item=this._items[i];this._items[i]._node=_a;}},startup:function(){this.onCreate(this._items);},destroy:function(){_4.forEach(this.events,_4.disconnect);this._touchBase=null;_4.query("img",this.container).forEach(function(_c){_c._index=null;_c._item=null;_4.destroy(_c);_c=null;});this._items=null;_4.destroy(this._slideDiv);_4.destroy(this.container);this.container=this._slideDiv=null;},getItems:function(){return this._items;},select:function(_d){this._markSelected(_d._node,_d);},onSelect:function(_e){},onUnSelect:function(_f){},onCreate:function(_10){},_onClickHandler:function(e){if(e.target.tagName.toLowerCase()==="img"){var img=e.target;var _11=img._index;var _12=img._item;_4.query("img",this.container).forEach(function(_13){if(_13!==img&&_13._item.toggleGroup===_12.toggleGroup){this._markUnSelected(_13,_13._item);}},this);this._toggleNode(img,_12);}},_toggleNode:function(_14,_15){if(_15.toggleState==="ON"){_15.toggleState="OFF";if(_15.src){_14.src=_15.src;}this.onUnSelect(_15);}else{_15.toggleState="ON";if(_15.srcAlt){_14.src=_15.srcAlt;}this.onSelect(_15);}},_markSelected:function(_16,_17){_17.toggleState="ON";if(_17.srcAlt){_16.src=_17.srcAlt;}this.onSelect(_17);},_markUnSelected:function(_18,_19){if(_19.toggleState==="ON"){_19.toggleState="OFF";if(_19.src){_18.src=_19.src;}this.onUnSelect(_19);}}});}}};});