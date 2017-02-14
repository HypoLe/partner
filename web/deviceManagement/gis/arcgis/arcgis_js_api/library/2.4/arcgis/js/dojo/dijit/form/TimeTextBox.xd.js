/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.form.TimeTextBox"],["require","dijit._TimePicker"],["require","dijit.form._DateTimeTextBox"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.form.TimeTextBox"]){_4._hasResource["dijit.form.TimeTextBox"]=true;_4.provide("dijit.form.TimeTextBox");_4.require("dijit._TimePicker");_4.require("dijit.form._DateTimeTextBox");_4.declare("dijit.form.TimeTextBox",_5.form._DateTimeTextBox,{baseClass:"dijitTextBox dijitComboBox dijitTimeTextBox",popupClass:"dijit._TimePicker",_selector:"time",value:new Date(""),_onKey:function(_7){this.inherited(arguments);switch(_7.keyCode){case _4.keys.ENTER:case _4.keys.TAB:case _4.keys.ESCAPE:case _4.keys.DOWN_ARROW:case _4.keys.UP_ARROW:break;default:setTimeout(_4.hitch(this,function(){var _8=this.get("displayedValue");this.filterString=(_8&&!this.parse(_8,this.constraints))?_8.toLowerCase():"";if(this._opened){this.closeDropDown();}this.openDropDown();}),0);}}});}}};});