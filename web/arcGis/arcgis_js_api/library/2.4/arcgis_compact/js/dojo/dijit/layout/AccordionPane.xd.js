/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.layout.AccordionPane"],["require","dijit.layout.ContentPane"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.layout.AccordionPane"]){_4._hasResource["dijit.layout.AccordionPane"]=true;_4.provide("dijit.layout.AccordionPane");_4.require("dijit.layout.ContentPane");_4.declare("dijit.layout.AccordionPane",_5.layout.ContentPane,{constructor:function(){_4.deprecated("dijit.layout.AccordionPane deprecated, use ContentPane instead","","2.0");},onSelected:function(){}});}}};});