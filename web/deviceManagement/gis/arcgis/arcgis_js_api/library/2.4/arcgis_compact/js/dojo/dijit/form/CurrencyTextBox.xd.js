/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.form.CurrencyTextBox"],["require","dojo.currency"],["require","dijit.form.NumberTextBox"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.form.CurrencyTextBox"]){_4._hasResource["dijit.form.CurrencyTextBox"]=true;_4.provide("dijit.form.CurrencyTextBox");_4.require("dojo.currency");_4.require("dijit.form.NumberTextBox");_4.declare("dijit.form.CurrencyTextBox",_5.form.NumberTextBox,{currency:"",baseClass:"dijitTextBox dijitCurrencyTextBox",regExpGen:function(_7){return "("+(this._focused?this.inherited(arguments,[_4.mixin({},_7,this.editOptions)])+"|":"")+_4.currency.regexp(_7)+")";},_formatter:_4.currency.format,_parser:_4.currency.parse,parse:function(_8,_9){var v=this.inherited(arguments);if(isNaN(v)&&/\d+/.test(_8)){v=_4.hitch(_4.mixin({},this,{_parser:_5.form.NumberTextBox.prototype._parser}),"inherited")(arguments);}return v;},_setConstraintsAttr:function(_a){if(!_a.currency&&this.currency){_a.currency=this.currency;}this.inherited(arguments,[_4.currency._mixInDefaults(_4.mixin(_a,{exponent:false}))]);}});}}};});