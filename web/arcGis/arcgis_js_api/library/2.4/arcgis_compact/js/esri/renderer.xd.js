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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.renderer"],["require","esri.graphic"],["require","dojo.date"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.renderer"]){_4._hasResource["esri.renderer"]=true;_4.provide("esri.renderer");_4.require("esri.graphic");_4.require("dojo.date");esri.renderer.fromJson=function(_7){var _8=_7.type||"",_9;switch(_8){case "simple":_9=new esri.renderer.SimpleRenderer(_7);break;case "uniqueValue":_9=new esri.renderer.UniqueValueRenderer(_7);break;case "classBreaks":_9=new esri.renderer.ClassBreaksRenderer(_7);break;}return _9;};_4.declare("esri.renderer.Renderer",null,{constructor:function(){this.getSymbol=_4.hitch(this,this.getSymbol);},getSymbol:function(_a){},toJson:function(){}});_4.declare("esri.renderer.SimpleRenderer",esri.renderer.Renderer,{constructor:function(_b){var _c=_b.declaredClass;if(_c&&(_c.indexOf("esri.symbol")!==-1)){this.symbol=_b;}else{var _d=_b,_b=_d.symbol;if(_b){this.symbol=esri.symbol.fromJson(_b);}this.label=_d.label;this.description=_d.description;}},getSymbol:function(_e){return this.symbol;},toJson:function(){return esri._sanitize({type:"simple",label:this.label,description:this.description,symbol:this.symbol&&this.symbol.toJson()});}});_4.declare("esri.renderer.UniqueValueRenderer",esri.renderer.Renderer,{constructor:function(_f,_10,_11,_12,_13){this.values=[];this._values=[];this.infos=[];var _14=_f.declaredClass;if(_14&&(_14.indexOf("esri.symbol")!==-1)){this.defaultSymbol=_f;this.attributeField=_10;this.attributeField2=_11;this.attributeField3=_12;this.fieldDelimiter=_13;}else{var _15=_f,_f=_15.defaultSymbol;if(_f){this.defaultSymbol=esri.symbol.fromJson(_f);}this.attributeField=_15.field1;this.attributeField2=_15.field2;this.attributeField3=_15.field3;this.fieldDelimiter=_15.fieldDelimiter;this.defaultLabel=_15.defaultLabel;_4.forEach(_15.uniqueValueInfos,this._addValueInfo,this);}this._multi=(this.attributeField2)?true:false;},addValue:function(_16,_17){var _18=_4.isObject(_16)?_16:{value:_16,symbol:_17};this._addValueInfo(_18);},removeValue:function(_19){var i=_4.indexOf(this.values,_19);if(i===-1){return;}this.values.splice(i,1);delete this._values[_19];this.infos.splice(i,1);},getSymbol:function(_1a){if(this._multi){var _1b=_1a.attributes,_1c=this.attributeField,_1d=this.attributeField2,_1e=this.attributeField3;var _1f=[];if(_1c){_1f.push(_1b[_1c]);}if(_1d){_1f.push(_1b[_1d]);}if(_1e){_1f.push(_1b[_1e]);}return this._values[_1f.join(this.fieldDelimiter||"")]||this.defaultSymbol;}else{return this._values[_1a.attributes[this.attributeField]]||this.defaultSymbol;}},_addValueInfo:function(_20){var _21=_20.value;this.values.push(_21);this.infos.push(_20);var _22=_20.symbol;if(_22){if(!_22.declaredClass){_20.symbol=esri.symbol.fromJson(_22);}}this._values[_21]=_20.symbol;},toJson:function(){var _23=esri._sanitize;return _23({type:"uniqueValue",field1:this.attributeField,field2:this.attributeField2,field3:this.attributeField3,fieldDelimiter:this.fieldDelimiter,defaultSymbol:this.defaultSymbol&&this.defaultSymbol.toJson(),defaultLabel:this.defaultLabel,uniqueValueInfos:_4.map(this.infos||[],function(_24){_24=_4.mixin({},_24);_24.symbol=_24.symbol&&_24.symbol.toJson();return _23(_24);})});}});_4.declare("esri.renderer.ClassBreaksRenderer",esri.renderer.Renderer,{constructor:function(sym,_25){this.breaks=[];this._symbols=[];this.infos=[];var _26=sym.declaredClass;if(_26&&(_26.indexOf("esri.symbol")!==-1)){this.defaultSymbol=sym;this.attributeField=_25;}else{var _27=sym;this.attributeField=_27.field;var min=_27.minValue,_28=_27.classBreakInfos;if(_28&&_28[0]&&esri._isDefined(_28[0].classMaxValue)){_4.forEach(_28,function(_29){var _2a=_29.classMaxValue;_29.minValue=min;_29.maxValue=_2a;min=_2a;},this);}_4.forEach(_28,this._addBreakInfo,this);}},addBreak:function(min,max,_2b){var _2c=_4.isObject(min)?min:{minValue:min,maxValue:max,symbol:_2b};this._addBreakInfo(_2c);},removeBreak:function(min,max){var _2d,_2e=this.breaks,_2f=this._symbols;for(var i=0,il=_2e.length;i<il;i++){_2d=_2e[i];if(_2d[0]==min&&_2d[1]==max){_2e.splice(i,1);delete _2f[min+"-"+max];this.infos.splice(i,1);break;}}},getSymbol:function(_30){var val=parseFloat(_30.attributes[this.attributeField]),rs=this.breaks,_31=this._symbols,_32,_33=this.isMaxInclusive;for(var i=0,il=rs.length;i<il;i++){_32=rs[i];if(_32[0]<=val&&(_33?(val<=_32[1]):(val<_32[1]))){return _31[_32[0]+"-"+_32[1]];}}return this.defaultSymbol;},_setMaxInclusiveness:function(_34){this.isMaxInclusive=_34;},_addBreakInfo:function(_35){var min=_35.minValue,max=_35.maxValue;this.breaks.push([min,max]);this.infos.push(_35);var _36=_35.symbol;if(_36){if(!_36.declaredClass){_35.symbol=esri.symbol.fromJson(_36);}}this._symbols[min+"-"+max]=_35.symbol;},toJson:function(){var _37=this.infos||[],_38=esri._sanitize;var _39=_37[0]&&_37[0].minValue;return _38({type:"classBreaks",field:this.attributeField,minValue:(_39===-Infinity)?-Number.MAX_VALUE:_39,classBreakInfos:_4.map(_37,function(_3a){_3a=_4.mixin({},_3a);_3a.symbol=_3a.symbol&&_3a.symbol.toJson();_3a.classMaxValue=(_3a.maxValue===Infinity)?Number.MAX_VALUE:_3a.maxValue;delete _3a.minValue;delete _3a.maxValue;return _38(_3a);})});}});_4.declare("esri.renderer.TemporalRenderer",esri.renderer.Renderer,{constructor:function(_3b,_3c,_3d,_3e){this.observationRenderer=_3b;this.latestObservationRenderer=_3c;this.trackRenderer=_3d;this.observationAger=_3e;},getSymbol:function(_3f){var _40=_3f.getLayer();var _41=_40._getKind(_3f);var _42=(_41===0)?this.observationRenderer:(this.latestObservationRenderer||this.observationRenderer);var _43=(_42&&_42.getSymbol(_3f));var _44=this.observationAger;if(_40.timeInfo&&_40._map.timeExtent&&(_42===this.observationRenderer)&&_44&&_43){_43=_44.getAgedSymbol(_43,_3f);}return _43;}});_4.declare("esri.renderer.SymbolAger",null,{getAgedSymbol:function(_45,_46){},_setSymbolSize:function(_47,_48){switch(_47.type){case "simplemarkersymbol":_47.setSize(_48);break;case "picturemarkersymbol":_47.setWidth(_48);_47.setHeight(_48);break;case "simplelinesymbol":case "cartographiclinesymbol":_47.setWidth(_48);break;case "simplefillsymbol":case "picturefillsymbol":if(_47.outline){_47.outline.setWidth(_48);}break;}}});_4.declare("esri.renderer.TimeClassBreaksAger",esri.renderer.SymbolAger,{constructor:function(_49,_4a){this.infos=_49;this.timeUnits=_4a||"day";_49.sort(function(a,b){if(a.minAge<b.minAge){return -1;}if(a.minAge>b.minAge){return 1;}return 0;});},getAgedSymbol:function(_4b,_4c){var _4d=_4c.getLayer(),_4e=_4c.attributes,_4f=esri._isDefined;_4b=esri.symbol.fromJson(_4b.toJson());var _50=_4d._map.timeExtent;var _51=_50.endTime;if(!_51){return _4b;}var _52=new Date(_4e[_4d._startTimeField]);var _53=_4.date.difference(_52,_51,this.timeUnits);_4.some(this.infos,function(_54){if(_53>=_54.minAge&&_53<=_54.maxAge){var _55=_54.color,_56=_54.size;if(_55){_4b.setColor(_55);}if(_4f(_56)){this._setSymbolSize(_4b,_56);}return true;}},this);return _4b;}});_4.mixin(esri.renderer.TimeClassBreaksAger,{UNIT_DAYS:"day",UNIT_HOURS:"hour",UNIT_MILLISECONDS:"millisecond",UNIT_MINUTES:"minute",UNIT_MONTHS:"month",UNIT_SECONDS:"second",UNIT_WEEKS:"week",UNIT_YEARS:"year"});_4.declare("esri.renderer.TimeRampAger",esri.renderer.SymbolAger,{constructor:function(_57,_58){this.colorRange=_57;this.sizeRange=_58;},getAgedSymbol:function(_59,_5a){var _5b=_5a.getLayer(),_5c=_5a.attributes;_59=esri.symbol.fromJson(_59.toJson());var _5d=_5b._map.timeExtent;var _5e=_5d.startTime,_5f=_5d.endTime;if(!_5e||!_5f){return _59;}_5e=_5e.getTime();_5f=_5f.getTime();var _60=new Date(_5c[_5b._startTimeField]);_60=_60.getTime();if(_60<_5e){_60=_5e;}var _61=(_5f===_5e)?1:(_60-_5e)/(_5f-_5e);var _62=this.sizeRange;if(_62){var _63=_62[0],to=_62[1],_64=Math.abs(to-_63)*_61;this._setSymbolSize(_59,(_63<to)?(_63+_64):(_63-_64));}_62=this.colorRange;if(_62){var _65=_62[0],_66=_62[1],_67=new _4.Color(),_68=Math.round;var _69=_65.r,toR=_66.r,_64=Math.abs(toR-_69)*_61;_67.r=_68((_69<toR)?(_69+_64):(_69-_64));var _6a=_65.g,toG=_66.g,_64=Math.abs(toG-_6a)*_61;_67.g=_68((_6a<toG)?(_6a+_64):(_6a-_64));var _6b=_65.b,toB=_66.b,_64=Math.abs(toB-_6b)*_61;_67.b=_68((_6b<toB)?(_6b+_64):(_6b-_64));var _6c=_65.a,toA=_66.a,_64=Math.abs(toA-_6c)*_61;_67.a=(_6c<toA)?(_6c+_64):(_6c-_64);_59.setColor(_67);}return _59;}});}}};});