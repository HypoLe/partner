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

if(!dojo._hasResource["esri.dijit.NavigationBar"]){dojo._hasResource["esri.dijit.NavigationBar"]=true;dojo.provide("esri.dijit.NavigationBar");dojo.require("esri.dijit._TouchBase");dojo.declare("esri.dijit.NavigationBar",null,{_items:[],constructor:function(_1,_2){this.container=dojo.byId(_2);this._touchBase=esri.dijit._TouchBase(this.container,null);this._slideDiv=dojo.create("div",{},this.container,"first");this.events=[dojo.connect(this._touchBase,"onclick",this,this._onClickHandler)];this._items=_1.items;this.container.setAttribute((document.all?"className":"class"),"esriMobileNavigationBar");var _3=dojo.create("div",{},this._slideDiv);for(var i=0;i<this._items.length;i++){var _4,_5;switch(this._items[i].type){case "img":_5=dojo.create("div",{"class":"esriMobileNavigationItem"},_3);_4=dojo.create("img",{src:this._items[i].src,style:{width:"100%",height:"100%"}},_5);break;case "span":_5=dojo.create("div",{"class":"esriMobileNavigationItem"},_3);_4=dojo.create("span",{innerHTML:this._items[i].text},_5);break;case "div":_5=dojo.create("div",{"class":"esriMobileNavigationInfoPanel"},_3);_4=dojo.create("div",{innerHTML:this._items[i].text},_5);break;}dojo.addClass(_5,this._items[i].position);if(this._items[i].className){dojo.addClass(_4,this._items[i].className);}_4._index=i;_4._item=this._items[i];this._items[i]._node=_4;}},startup:function(){this.onCreate(this._items);},destroy:function(){dojo.forEach(this.events,dojo.disconnect);this._touchBase=null;dojo.query("img",this.container).forEach(function(_6){_6._index=null;_6._item=null;dojo.destroy(_6);_6=null;});this._items=null;dojo.destroy(this._slideDiv);dojo.destroy(this.container);this.container=this._slideDiv=null;},getItems:function(){return this._items;},select:function(_7){this._markSelected(_7._node,_7);},onSelect:function(_8){},onUnSelect:function(_9){},onCreate:function(_a){},_onClickHandler:function(e){if(e.target.tagName.toLowerCase()==="img"){var _b=e.target;var _c=_b._index;var _d=_b._item;dojo.query("img",this.container).forEach(function(_e){if(_e!==_b&&_e._item.toggleGroup===_d.toggleGroup){this._markUnSelected(_e,_e._item);}},this);this._toggleNode(_b,_d);}},_toggleNode:function(_f,_10){if(_10.toggleState==="ON"){_10.toggleState="OFF";if(_10.src){_f.src=_10.src;}this.onUnSelect(_10);}else{_10.toggleState="ON";if(_10.srcAlt){_f.src=_10.srcAlt;}this.onSelect(_10);}},_markSelected:function(_11,_12){_12.toggleState="ON";if(_12.srcAlt){_11.src=_12.srcAlt;}this.onSelect(_12);},_markUnSelected:function(_13,_14){if(_14.toggleState==="ON"){_14.toggleState="OFF";if(_14.src){_13.src=_14.src;}this.onUnSelect(_14);}}});}