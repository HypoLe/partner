/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.MenuBarItem"],["require","dijit.MenuItem"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.MenuBarItem"]){_4._hasResource["dijit.MenuBarItem"]=true;_4.provide("dijit.MenuBarItem");_4.require("dijit.MenuItem");_4.declare("dijit._MenuBarItemMixin",null,{templateString:_4.cache("dijit","templates/MenuBarItem.html","<div class=\"dijitReset dijitInline dijitMenuItem dijitMenuItemLabel\" dojoAttachPoint=\"focusNode\" role=\"menuitem\" tabIndex=\"-1\"\r\n\t\tdojoAttachEvent=\"onmouseenter:_onHover,onmouseleave:_onUnhover,ondijitclick:_onClick\">\r\n\t<span dojoAttachPoint=\"containerNode\"></span>\r\n</div>\r\n"),attributeMap:_4.delegate(_5._Widget.prototype.attributeMap,{label:{node:"containerNode",type:"innerHTML"}})});_4.declare("dijit.MenuBarItem",[_5.MenuItem,_5._MenuBarItemMixin],{});}}};});