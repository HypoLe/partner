/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.form.DateTextBox"],["require","dijit.Calendar"],["require","dijit.form._DateTimeTextBox"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.form.DateTextBox"]){_4._hasResource["dijit.form.DateTextBox"]=true;_4.provide("dijit.form.DateTextBox");_4.require("dijit.Calendar");_4.require("dijit.form._DateTimeTextBox");_4.declare("dijit.form.DateTextBox",_5.form._DateTimeTextBox,{baseClass:"dijitTextBox dijitComboBox dijitDateTextBox",popupClass:"dijit.Calendar",_selector:"date",value:new Date("")});}}};});