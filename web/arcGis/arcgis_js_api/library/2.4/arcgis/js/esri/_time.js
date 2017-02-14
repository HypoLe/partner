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

if(!dojo._hasResource["esri._time"]){dojo._hasResource["esri._time"]=true;dojo.provide("esri._time");dojo.declare("esri.TimeExtent",null,{constructor:function(_1){if(arguments.length>1){this._create(arguments[0],arguments[1]);}else{if(_1){if(dojo.isArray(_1)){var _2=_1[0],_3=_1[1];this.startTime=(_2===null||_2==="null")?null:new Date(_2);this.endTime=(_3===null||_3==="null")?null:new Date(_3);}else{if(_1 instanceof Date){this._create(_1,null);}}}}},offset:function(_4,_5){var _6=new esri.TimeExtent();var _7=this.startTime,_8=this.endTime;if(_7){_6.startTime=this._getOffsettedDate(_7,_4,_5);}if(_8){_6.endTime=this._getOffsettedDate(_8,_4,_5);}return _6;},intersection:function(_9){return this._intersection(this,_9);},toJson:function(){var _a=[];var _b=this.startTime;_a.push(_b?_b.getTime():"null");var _c=this.endTime;_a.push(_c?_c.getTime():"null");return _a;},_create:function(_d,_e){this.startTime=_d?new Date(_d):null;this.endTime=_e?new Date(_e):null;},_refData:{"esriTimeUnitsMilliseconds":{getter:"getUTCMilliseconds",setter:"setUTCMilliseconds",multiplier:1},"esriTimeUnitsSeconds":{getter:"getUTCSeconds",setter:"setUTCSeconds",multiplier:1},"esriTimeUnitsMinutes":{getter:"getUTCMinutes",setter:"setUTCMinutes",multiplier:1},"esriTimeUnitsHours":{getter:"getUTCHours",setter:"setUTCHours",multiplier:1},"esriTimeUnitsDays":{getter:"getUTCDate",setter:"setUTCDate",multiplier:1},"esriTimeUnitsWeeks":{getter:"getUTCDate",setter:"setUTCDate",multiplier:7},"esriTimeUnitsMonths":{getter:"getUTCMonth",setter:"setUTCMonth",multiplier:1},"esriTimeUnitsYears":{getter:"getUTCFullYear",setter:"setUTCFullYear",multiplier:1},"esriTimeUnitsDecades":{getter:"getUTCFullYear",setter:"setUTCFullYear",multiplier:10},"esriTimeUnitsCenturies":{getter:"getUTCFullYear",setter:"setUTCFullYear",multiplier:100}},_intersection:function(_f,_10){if(_f&&_10){var _11=_f.startTime,_12=_f.endTime;var _13=_10.startTime,_14=_10.endTime;_11=_11?_11.getTime():-Infinity;_13=_13?_13.getTime():-Infinity;_12=_12?_12.getTime():Infinity;_14=_14?_14.getTime():Infinity;var _15,end;if(_13>=_11&&_13<=_12){_15=_13;}else{if(_11>=_13&&_11<=_14){_15=_11;}}if(_12>=_13&&_12<=_14){end=_12;}else{if(_14>=_11&&_14<=_12){end=_14;}}if(!isNaN(_15)&&!isNaN(end)){var _16=new esri.TimeExtent();_16.startTime=(_15===-Infinity)?null:new Date(_15);_16.endTime=(end===Infinity)?null:new Date(end);return _16;}else{return null;}}else{return null;}},_getOffsettedDate:function(_17,_18,_19){var _1a=this._refData;var _1b=new Date(_17.getTime());if(_18&&_19){var _1a=_1a[_19];_1b[_1a.setter](_1b[_1a.getter]()+(_18*_1a.multiplier));}return _1b;}});dojo.declare("esri.TimeReference",null,{constructor:function(_1c){if(_1c){dojo.mixin(this,_1c);}}});}