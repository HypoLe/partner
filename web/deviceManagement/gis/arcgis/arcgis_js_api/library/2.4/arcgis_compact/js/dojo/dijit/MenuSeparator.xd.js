/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.MenuSeparator"],["require","dijit._Widget"],["require","dijit._Templated"],["require","dijit._Contained"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.MenuSeparator"]){_4._hasResource["dijit.MenuSeparator"]=true;_4.provide("dijit.MenuSeparator");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.require("dijit._Contained");_4.declare("dijit.MenuSeparator",[_5._Widget,_5._Templated,_5._Contained],{templateString:_4.cache("dijit","templates/MenuSeparator.html","<tr class=\"dijitMenuSeparator\">\r\n\t<td class=\"dijitMenuSeparatorIconCell\">\r\n\t\t<div class=\"dijitMenuSeparatorTop\"></div>\r\n\t\t<div class=\"dijitMenuSeparatorBottom\"></div>\r\n\t</td>\r\n\t<td colspan=\"3\" class=\"dijitMenuSeparatorLabelCell\">\r\n\t\t<div class=\"dijitMenuSeparatorTop dijitMenuSeparatorLabel\"></div>\r\n\t\t<div class=\"dijitMenuSeparatorBottom\"></div>\r\n\t</td>\r\n</tr>\r\n"),buildRendering:function(){this.inherited(arguments);_4.setSelectable(this.domNode,false);},isFocusable:function(){return false;}});}}};});