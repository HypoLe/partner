/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.layout.TabContainer"],["require","dijit.layout._TabContainerBase"],["require","dijit.layout.TabController"],["require","dijit.layout.ScrollingTabController"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.layout.TabContainer"]){_4._hasResource["dijit.layout.TabContainer"]=true;_4.provide("dijit.layout.TabContainer");_4.require("dijit.layout._TabContainerBase");_4.require("dijit.layout.TabController");_4.require("dijit.layout.ScrollingTabController");_4.declare("dijit.layout.TabContainer",_5.layout._TabContainerBase,{useMenu:true,useSlider:true,controllerWidget:"",_makeController:function(_7){var _8=this.baseClass+"-tabs"+(this.doLayout?"":" dijitTabNoLayout"),_9=_4.getObject(this.controllerWidget);return new _9({id:this.id+"_tablist",dir:this.dir,lang:this.lang,tabPosition:this.tabPosition,doLayout:this.doLayout,containerId:this.id,"class":_8,nested:this.nested,useMenu:this.useMenu,useSlider:this.useSlider,tabStripClass:this.tabStrip?this.baseClass+(this.tabStrip?"":"No")+"Strip":null},_7);},postMixInProperties:function(){this.inherited(arguments);if(!this.controllerWidget){this.controllerWidget=(this.tabPosition=="top"||this.tabPosition=="bottom")&&!this.nested?"dijit.layout.ScrollingTabController":"dijit.layout.TabController";}}});}}};});