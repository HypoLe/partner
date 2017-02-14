/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.ToolbarSeparator"],["require","dijit._Widget"],["require","dijit._Templated"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.ToolbarSeparator"]){_4._hasResource["dijit.ToolbarSeparator"]=true;_4.provide("dijit.ToolbarSeparator");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.declare("dijit.ToolbarSeparator",[_5._Widget,_5._Templated],{templateString:"<div class=\"dijitToolbarSeparator dijitInline\" role=\"presentation\"></div>",buildRendering:function(){this.inherited(arguments);_4.setSelectable(this.domNode,false);},isFocusable:function(){return false;}});}}};});