/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.layout.ScrollingTabController"],["require","dijit.layout.TabController"],["require","dijit.Menu"],["require","dijit.form.Button"],["require","dijit._HasDropDown"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.layout.ScrollingTabController"]){_4._hasResource["dijit.layout.ScrollingTabController"]=true;_4.provide("dijit.layout.ScrollingTabController");_4.require("dijit.layout.TabController");_4.require("dijit.Menu");_4.require("dijit.form.Button");_4.require("dijit._HasDropDown");_4.declare("dijit.layout.ScrollingTabController",_5.layout.TabController,{templateString:_4.cache("dijit.layout","templates/ScrollingTabController.html","<div class=\"dijitTabListContainer-${tabPosition}\" style=\"visibility:hidden\">\r\n\t<div dojoType=\"dijit.layout._ScrollingTabControllerMenuButton\"\r\n\t\t\tclass=\"tabStripButton-${tabPosition}\"\r\n\t\t\tid=\"${id}_menuBtn\" containerId=\"${containerId}\" iconClass=\"dijitTabStripMenuIcon\"\r\n\t\t\tdropDownPosition=\"below-alt, above-alt\"\r\n\t\t\tdojoAttachPoint=\"_menuBtn\" showLabel=\"false\">&#9660;</div>\r\n\t<div dojoType=\"dijit.layout._ScrollingTabControllerButton\"\r\n\t\t\tclass=\"tabStripButton-${tabPosition}\"\r\n\t\t\tid=\"${id}_leftBtn\" iconClass=\"dijitTabStripSlideLeftIcon\"\r\n\t\t\tdojoAttachPoint=\"_leftBtn\" dojoAttachEvent=\"onClick: doSlideLeft\" showLabel=\"false\">&#9664;</div>\r\n\t<div dojoType=\"dijit.layout._ScrollingTabControllerButton\"\r\n\t\t\tclass=\"tabStripButton-${tabPosition}\"\r\n\t\t\tid=\"${id}_rightBtn\" iconClass=\"dijitTabStripSlideRightIcon\"\r\n\t\t\tdojoAttachPoint=\"_rightBtn\" dojoAttachEvent=\"onClick: doSlideRight\" showLabel=\"false\">&#9654;</div>\r\n\t<div class='dijitTabListWrapper' dojoAttachPoint='tablistWrapper'>\r\n\t\t<div role='tablist' dojoAttachEvent='onkeypress:onkeypress'\r\n\t\t\t\tdojoAttachPoint='containerNode' class='nowrapTabStrip'></div>\r\n\t</div>\r\n</div>\r\n"),useMenu:true,useSlider:true,tabStripClass:"",widgetsInTemplate:true,_minScroll:5,attributeMap:_4.delegate(_5._Widget.prototype.attributeMap,{"class":"containerNode"}),buildRendering:function(){this.inherited(arguments);var n=this.domNode;this.scrollNode=this.tablistWrapper;this._initButtons();if(!this.tabStripClass){this.tabStripClass="dijitTabContainer"+this.tabPosition.charAt(0).toUpperCase()+this.tabPosition.substr(1).replace(/-.*/,"")+"None";_4.addClass(n,"tabStrip-disabled");}_4.addClass(this.tablistWrapper,this.tabStripClass);},onStartup:function(){this.inherited(arguments);_4.style(this.domNode,"visibility","visible");this._postStartup=true;},onAddChild:function(_7,_8){this.inherited(arguments);_4.forEach(["label","iconClass"],function(_9){this.pane2watches[_7.id].push(this.pane2button[_7.id].watch(_9,_4.hitch(this,function(_a,_b,_c){if(this._postStartup&&this._dim){this.resize(this._dim);}})));},this);_4.style(this.containerNode,"width",(_4.style(this.containerNode,"width")+200)+"px");},onRemoveChild:function(_d,_e){var _f=this.pane2button[_d.id];if(this._selectedTab===_f.domNode){this._selectedTab=null;}this.inherited(arguments);},_initButtons:function(){this._btnWidth=0;this._buttons=_4.query("> .tabStripButton",this.domNode).filter(function(btn){if((this.useMenu&&btn==this._menuBtn.domNode)||(this.useSlider&&(btn==this._rightBtn.domNode||btn==this._leftBtn.domNode))){this._btnWidth+=_4._getMarginSize(btn).w;return true;}else{_4.style(btn,"display","none");return false;}},this);},_getTabsWidth:function(){var _10=this.getChildren();if(_10.length){var _11=_10[this.isLeftToRight()?0:_10.length-1].domNode,_12=_10[this.isLeftToRight()?_10.length-1:0].domNode;return _12.offsetLeft+_4.style(_12,"width")-_11.offsetLeft;}else{return 0;}},_enableBtn:function(_13){var _14=this._getTabsWidth();_13=_13||_4.style(this.scrollNode,"width");return _14>0&&_13<_14;},resize:function(dim){if(this.domNode.offsetWidth==0){return;}this._dim=dim;this.scrollNode.style.height="auto";this._contentBox=_5.layout.marginBox2contentBox(this.domNode,{h:0,w:dim.w});this._contentBox.h=this.scrollNode.offsetHeight;_4.contentBox(this.domNode,this._contentBox);var _15=this._enableBtn(this._contentBox.w);this._buttons.style("display",_15?"":"none");this._leftBtn.layoutAlign="left";this._rightBtn.layoutAlign="right";this._menuBtn.layoutAlign=this.isLeftToRight()?"right":"left";_5.layout.layoutChildren(this.domNode,this._contentBox,[this._menuBtn,this._leftBtn,this._rightBtn,{domNode:this.scrollNode,layoutAlign:"client"}]);if(this._selectedTab){if(this._anim&&this._anim.status()=="playing"){this._anim.stop();}var w=this.scrollNode,sl=this._convertToScrollLeft(this._getScrollForSelectedTab());w.scrollLeft=sl;}this._setButtonClass(this._getScroll());this._postResize=true;return {h:this._contentBox.h,w:dim.w};},_getScroll:function(){var sl=(this.isLeftToRight()||_4.isIE<8||(_4.isIE&&_4.isQuirks)||_4.isWebKit)?this.scrollNode.scrollLeft:_4.style(this.containerNode,"width")-_4.style(this.scrollNode,"width")+(_4.isIE==8?-1:1)*this.scrollNode.scrollLeft;return sl;},_convertToScrollLeft:function(val){if(this.isLeftToRight()||_4.isIE<8||(_4.isIE&&_4.isQuirks)||_4.isWebKit){return val;}else{var _16=_4.style(this.containerNode,"width")-_4.style(this.scrollNode,"width");return (_4.isIE==8?-1:1)*(val-_16);}},onSelectChild:function(_17){var tab=this.pane2button[_17.id];if(!tab||!_17){return;}var _18=tab.domNode;if(this._postResize&&_18!=this._selectedTab){this._selectedTab=_18;var sl=this._getScroll();if(sl>_18.offsetLeft||sl+_4.style(this.scrollNode,"width")<_18.offsetLeft+_4.style(_18,"width")){this.createSmoothScroll().play();}}this.inherited(arguments);},_getScrollBounds:function(){var _19=this.getChildren(),_1a=_4.style(this.scrollNode,"width"),_1b=_4.style(this.containerNode,"width"),_1c=_1b-_1a,_1d=this._getTabsWidth();if(_19.length&&_1d>_1a){return {min:this.isLeftToRight()?0:_19[_19.length-1].domNode.offsetLeft,max:this.isLeftToRight()?(_19[_19.length-1].domNode.offsetLeft+_4.style(_19[_19.length-1].domNode,"width"))-_1a:_1c};}else{var _1e=this.isLeftToRight()?0:_1c;return {min:_1e,max:_1e};}},_getScrollForSelectedTab:function(){var w=this.scrollNode,n=this._selectedTab,_1f=_4.style(this.scrollNode,"width"),_20=this._getScrollBounds();var pos=(n.offsetLeft+_4.style(n,"width")/2)-_1f/2;pos=Math.min(Math.max(pos,_20.min),_20.max);return pos;},createSmoothScroll:function(x){if(arguments.length>0){var _21=this._getScrollBounds();x=Math.min(Math.max(x,_21.min),_21.max);}else{x=this._getScrollForSelectedTab();}if(this._anim&&this._anim.status()=="playing"){this._anim.stop();}var _22=this,w=this.scrollNode,_23=new _4._Animation({beforeBegin:function(){if(this.curve){delete this.curve;}var _24=w.scrollLeft,_25=_22._convertToScrollLeft(x);_23.curve=new _4._Line(_24,_25);},onAnimate:function(val){w.scrollLeft=val;}});this._anim=_23;this._setButtonClass(x);return _23;},_getBtnNode:function(e){var n=e.target;while(n&&!_4.hasClass(n,"tabStripButton")){n=n.parentNode;}return n;},doSlideRight:function(e){this.doSlide(1,this._getBtnNode(e));},doSlideLeft:function(e){this.doSlide(-1,this._getBtnNode(e));},doSlide:function(_26,_27){if(_27&&_4.hasClass(_27,"dijitTabDisabled")){return;}var _28=_4.style(this.scrollNode,"width");var d=(_28*0.75)*_26;var to=this._getScroll()+d;this._setButtonClass(to);this.createSmoothScroll(to).play();},_setButtonClass:function(_29){var _2a=this._getScrollBounds();this._leftBtn.set("disabled",_29<=_2a.min);this._rightBtn.set("disabled",_29>=_2a.max);}});_4.declare("dijit.layout._ScrollingTabControllerButtonMixin",null,{baseClass:"dijitTab tabStripButton",templateString:_4.cache("dijit.layout","templates/_ScrollingTabControllerButton.html","<div dojoAttachEvent=\"onclick:_onButtonClick\">\r\n\t<div role=\"presentation\" class=\"dijitTabInnerDiv\" dojoattachpoint=\"innerDiv,focusNode\">\r\n\t\t<div role=\"presentation\" class=\"dijitTabContent dijitButtonContents\" dojoattachpoint=\"tabContent\">\r\n\t\t\t<img role=\"presentation\" alt=\"\" src=\"${_blankGif}\" class=\"dijitTabStripIcon\" dojoAttachPoint=\"iconNode\"/>\r\n\t\t\t<span dojoAttachPoint=\"containerNode,titleNode\" class=\"dijitButtonText\"></span>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n"),tabIndex:"",isFocusable:function(){return false;}});_4.declare("dijit.layout._ScrollingTabControllerButton",[_5.form.Button,_5.layout._ScrollingTabControllerButtonMixin]);_4.declare("dijit.layout._ScrollingTabControllerMenuButton",[_5.form.Button,_5._HasDropDown,_5.layout._ScrollingTabControllerButtonMixin],{containerId:"",tabIndex:"-1",isLoaded:function(){return false;},loadDropDown:function(_2b){this.dropDown=new _5.Menu({id:this.containerId+"_menu",dir:this.dir,lang:this.lang});var _2c=_5.byId(this.containerId);_4.forEach(_2c.getChildren(),function(_2d){var _2e=new _5.MenuItem({id:_2d.id+"_stcMi",label:_2d.title,iconClass:_2d.iconClass,dir:_2d.dir,lang:_2d.lang,onClick:function(){_2c.selectChild(_2d);}});this.dropDown.addChild(_2e);},this);_2b();},closeDropDown:function(_2f){this.inherited(arguments);if(this.dropDown){this.dropDown.destroyRecursive();delete this.dropDown;}}});}}};});