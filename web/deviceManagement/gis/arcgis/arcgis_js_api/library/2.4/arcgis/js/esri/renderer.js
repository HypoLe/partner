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

if(!dojo._hasResource["esri.renderer"]){dojo._hasResource["esri.renderer"]=true;dojo.provide("esri.renderer");dojo.require("esri.graphic");dojo.require("dojo.date");esri.renderer.fromJson=function(_1){var _2=_1.type||"",_3;switch(_2){case "simple":_3=new esri.renderer.SimpleRenderer(_1);break;case "uniqueValue":_3=new esri.renderer.UniqueValueRenderer(_1);break;case "classBreaks":_3=new esri.renderer.ClassBreaksRenderer(_1);break;}return _3;};dojo.declare("esri.renderer.Renderer",null,{constructor:function(){this.getSymbol=dojo.hitch(this,this.getSymbol);},getSymbol:function(_4){},toJson:function(){}});dojo.declare("esri.renderer.SimpleRenderer",esri.renderer.Renderer,{constructor:function(_5){var _6=_5.declaredClass;if(_6&&(_6.indexOf("esri.symbol")!==-1)){this.symbol=_5;}else{var _7=_5,_5=_7.symbol;if(_5){this.symbol=esri.symbol.fromJson(_5);}this.label=_7.label;this.description=_7.description;}},getSymbol:function(_8){return this.symbol;},toJson:function(){return esri._sanitize({type:"simple",label:this.label,description:this.description,symbol:this.symbol&&this.symbol.toJson()});}});dojo.declare("esri.renderer.UniqueValueRenderer",esri.renderer.Renderer,{constructor:function(_9,_a,_b,_c,_d){this.values=[];this._values=[];this.infos=[];var _e=_9.declaredClass;if(_e&&(_e.indexOf("esri.symbol")!==-1)){this.defaultSymbol=_9;this.attributeField=_a;this.attributeField2=_b;this.attributeField3=_c;this.fieldDelimiter=_d;}else{var _f=_9,_9=_f.defaultSymbol;if(_9){this.defaultSymbol=esri.symbol.fromJson(_9);}this.attributeField=_f.field1;this.attributeField2=_f.field2;this.attributeField3=_f.field3;this.fieldDelimiter=_f.fieldDelimiter;this.defaultLabel=_f.defaultLabel;dojo.forEach(_f.uniqueValueInfos,this._addValueInfo,this);}this._multi=(this.attributeField2)?true:false;},addValue:function(_10,_11){var _12=dojo.isObject(_10)?_10:{value:_10,symbol:_11};this._addValueInfo(_12);},removeValue:function(_13){var i=dojo.indexOf(this.values,_13);if(i===-1){return;}this.values.splice(i,1);delete this._values[_13];this.infos.splice(i,1);},getSymbol:function(_14){if(this._multi){var _15=_14.attributes,_16=this.attributeField,_17=this.attributeField2,_18=this.attributeField3;var _19=[];if(_16){_19.push(_15[_16]);}if(_17){_19.push(_15[_17]);}if(_18){_19.push(_15[_18]);}return this._values[_19.join(this.fieldDelimiter||"")]||this.defaultSymbol;}else{return this._values[_14.attributes[this.attributeField]]||this.defaultSymbol;}},_addValueInfo:function(_1a){var _1b=_1a.value;this.values.push(_1b);this.infos.push(_1a);var _1c=_1a.symbol;if(_1c){if(!_1c.declaredClass){_1a.symbol=esri.symbol.fromJson(_1c);}}this._values[_1b]=_1a.symbol;},toJson:function(){var _1d=esri._sanitize;return _1d({type:"uniqueValue",field1:this.attributeField,field2:this.attributeField2,field3:this.attributeField3,fieldDelimiter:this.fieldDelimiter,defaultSymbol:this.defaultSymbol&&this.defaultSymbol.toJson(),defaultLabel:this.defaultLabel,uniqueValueInfos:dojo.map(this.infos||[],function(_1e){_1e=dojo.mixin({},_1e);_1e.symbol=_1e.symbol&&_1e.symbol.toJson();return _1d(_1e);})});}});dojo.declare("esri.renderer.ClassBreaksRenderer",esri.renderer.Renderer,{constructor:function(sym,_1f){this.breaks=[];this._symbols=[];this.infos=[];var _20=sym.declaredClass;if(_20&&(_20.indexOf("esri.symbol")!==-1)){this.defaultSymbol=sym;this.attributeField=_1f;}else{var _21=sym;this.attributeField=_21.field;var min=_21.minValue,_22=_21.classBreakInfos;if(_22&&_22[0]&&esri._isDefined(_22[0].classMaxValue)){dojo.forEach(_22,function(_23){var _24=_23.classMaxValue;_23.minValue=min;_23.maxValue=_24;min=_24;},this);}dojo.forEach(_22,this._addBreakInfo,this);}},addBreak:function(min,max,_25){var _26=dojo.isObject(min)?min:{minValue:min,maxValue:max,symbol:_25};this._addBreakInfo(_26);},removeBreak:function(min,max){var _27,_28=this.breaks,_29=this._symbols;for(var i=0,il=_28.length;i<il;i++){_27=_28[i];if(_27[0]==min&&_27[1]==max){_28.splice(i,1);delete _29[min+"-"+max];this.infos.splice(i,1);break;}}},getSymbol:function(_2a){var val=parseFloat(_2a.attributes[this.attributeField]),rs=this.breaks,_2b=this._symbols,_2c,_2d=this.isMaxInclusive;for(var i=0,il=rs.length;i<il;i++){_2c=rs[i];if(_2c[0]<=val&&(_2d?(val<=_2c[1]):(val<_2c[1]))){return _2b[_2c[0]+"-"+_2c[1]];}}return this.defaultSymbol;},_setMaxInclusiveness:function(_2e){this.isMaxInclusive=_2e;},_addBreakInfo:function(_2f){var min=_2f.minValue,max=_2f.maxValue;this.breaks.push([min,max]);this.infos.push(_2f);var _30=_2f.symbol;if(_30){if(!_30.declaredClass){_2f.symbol=esri.symbol.fromJson(_30);}}this._symbols[min+"-"+max]=_2f.symbol;},toJson:function(){var _31=this.infos||[],_32=esri._sanitize;var _33=_31[0]&&_31[0].minValue;return _32({type:"classBreaks",field:this.attributeField,minValue:(_33===-Infinity)?-Number.MAX_VALUE:_33,classBreakInfos:dojo.map(_31,function(_34){_34=dojo.mixin({},_34);_34.symbol=_34.symbol&&_34.symbol.toJson();_34.classMaxValue=(_34.maxValue===Infinity)?Number.MAX_VALUE:_34.maxValue;delete _34.minValue;delete _34.maxValue;return _32(_34);})});}});dojo.declare("esri.renderer.TemporalRenderer",esri.renderer.Renderer,{constructor:function(_35,_36,_37,_38){this.observationRenderer=_35;this.latestObservationRenderer=_36;this.trackRenderer=_37;this.observationAger=_38;},getSymbol:function(_39){var _3a=_39.getLayer();var _3b=_3a._getKind(_39);var _3c=(_3b===0)?this.observationRenderer:(this.latestObservationRenderer||this.observationRenderer);var _3d=(_3c&&_3c.getSymbol(_39));var _3e=this.observationAger;if(_3a.timeInfo&&_3a._map.timeExtent&&(_3c===this.observationRenderer)&&_3e&&_3d){_3d=_3e.getAgedSymbol(_3d,_39);}return _3d;}});dojo.declare("esri.renderer.SymbolAger",null,{getAgedSymbol:function(_3f,_40){},_setSymbolSize:function(_41,_42){switch(_41.type){case "simplemarkersymbol":_41.setSize(_42);break;case "picturemarkersymbol":_41.setWidth(_42);_41.setHeight(_42);break;case "simplelinesymbol":case "cartographiclinesymbol":_41.setWidth(_42);break;case "simplefillsymbol":case "picturefillsymbol":if(_41.outline){_41.outline.setWidth(_42);}break;}}});dojo.declare("esri.renderer.TimeClassBreaksAger",esri.renderer.SymbolAger,{constructor:function(_43,_44){this.infos=_43;this.timeUnits=_44||"day";_43.sort(function(a,b){if(a.minAge<b.minAge){return -1;}if(a.minAge>b.minAge){return 1;}return 0;});},getAgedSymbol:function(_45,_46){var _47=_46.getLayer(),_48=_46.attributes,_49=esri._isDefined;_45=esri.symbol.fromJson(_45.toJson());var _4a=_47._map.timeExtent;var _4b=_4a.endTime;if(!_4b){return _45;}var _4c=new Date(_48[_47._startTimeField]);var _4d=dojo.date.difference(_4c,_4b,this.timeUnits);dojo.some(this.infos,function(_4e){if(_4d>=_4e.minAge&&_4d<=_4e.maxAge){var _4f=_4e.color,_50=_4e.size;if(_4f){_45.setColor(_4f);}if(_49(_50)){this._setSymbolSize(_45,_50);}return true;}},this);return _45;}});dojo.mixin(esri.renderer.TimeClassBreaksAger,{UNIT_DAYS:"day",UNIT_HOURS:"hour",UNIT_MILLISECONDS:"millisecond",UNIT_MINUTES:"minute",UNIT_MONTHS:"month",UNIT_SECONDS:"second",UNIT_WEEKS:"week",UNIT_YEARS:"year"});dojo.declare("esri.renderer.TimeRampAger",esri.renderer.SymbolAger,{constructor:function(_51,_52){this.colorRange=_51;this.sizeRange=_52;},getAgedSymbol:function(_53,_54){var _55=_54.getLayer(),_56=_54.attributes;_53=esri.symbol.fromJson(_53.toJson());var _57=_55._map.timeExtent;var _58=_57.startTime,_59=_57.endTime;if(!_58||!_59){return _53;}_58=_58.getTime();_59=_59.getTime();var _5a=new Date(_56[_55._startTimeField]);_5a=_5a.getTime();if(_5a<_58){_5a=_58;}var _5b=(_59===_58)?1:(_5a-_58)/(_59-_58);var _5c=this.sizeRange;if(_5c){var _5d=_5c[0],to=_5c[1],_5e=Math.abs(to-_5d)*_5b;this._setSymbolSize(_53,(_5d<to)?(_5d+_5e):(_5d-_5e));}_5c=this.colorRange;if(_5c){var _5f=_5c[0],_60=_5c[1],_61=new dojo.Color(),_62=Math.round;var _63=_5f.r,toR=_60.r,_5e=Math.abs(toR-_63)*_5b;_61.r=_62((_63<toR)?(_63+_5e):(_63-_5e));var _64=_5f.g,toG=_60.g,_5e=Math.abs(toG-_64)*_5b;_61.g=_62((_64<toG)?(_64+_5e):(_64-_5e));var _65=_5f.b,toB=_60.b,_5e=Math.abs(toB-_65)*_5b;_61.b=_62((_65<toB)?(_65+_5e):(_65-_5e));var _66=_5f.a,toA=_60.a,_5e=Math.abs(toA-_66)*_5b;_61.a=(_66<toA)?(_66+_5e):(_66-_5e);_53.setColor(_61);}return _53;}});}