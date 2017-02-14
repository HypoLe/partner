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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri._time"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri._time"]){_4._hasResource["esri._time"]=true;_4.provide("esri._time");_4.declare("esri.TimeExtent",null,{constructor:function(_7){if(arguments.length>1){this._create(arguments[0],arguments[1]);}else{if(_7){if(_4.isArray(_7)){var _8=_7[0],_9=_7[1];this.startTime=(_8===null||_8==="null")?null:new Date(_8);this.endTime=(_9===null||_9==="null")?null:new Date(_9);}else{if(_7 instanceof Date){this._create(_7,null);}}}}},offset:function(_a,_b){var _c=new esri.TimeExtent();var _d=this.startTime,_e=this.endTime;if(_d){_c.startTime=this._getOffsettedDate(_d,_a,_b);}if(_e){_c.endTime=this._getOffsettedDate(_e,_a,_b);}return _c;},intersection:function(_f){return this._intersection(this,_f);},toJson:function(){var _10=[];var _11=this.startTime;_10.push(_11?_11.getTime():"null");var end=this.endTime;_10.push(end?end.getTime():"null");return _10;},_create:function(_12,end){this.startTime=_12?new Date(_12):null;this.endTime=end?new Date(end):null;},_refData:{"esriTimeUnitsMilliseconds":{getter:"getUTCMilliseconds",setter:"setUTCMilliseconds",multiplier:1},"esriTimeUnitsSeconds":{getter:"getUTCSeconds",setter:"setUTCSeconds",multiplier:1},"esriTimeUnitsMinutes":{getter:"getUTCMinutes",setter:"setUTCMinutes",multiplier:1},"esriTimeUnitsHours":{getter:"getUTCHours",setter:"setUTCHours",multiplier:1},"esriTimeUnitsDays":{getter:"getUTCDate",setter:"setUTCDate",multiplier:1},"esriTimeUnitsWeeks":{getter:"getUTCDate",setter:"setUTCDate",multiplier:7},"esriTimeUnitsMonths":{getter:"getUTCMonth",setter:"setUTCMonth",multiplier:1},"esriTimeUnitsYears":{getter:"getUTCFullYear",setter:"setUTCFullYear",multiplier:1},"esriTimeUnitsDecades":{getter:"getUTCFullYear",setter:"setUTCFullYear",multiplier:10},"esriTimeUnitsCenturies":{getter:"getUTCFullYear",setter:"setUTCFullYear",multiplier:100}},_intersection:function(_13,_14){if(_13&&_14){var _15=_13.startTime,_16=_13.endTime;var _17=_14.startTime,_18=_14.endTime;_15=_15?_15.getTime():-Infinity;_17=_17?_17.getTime():-Infinity;_16=_16?_16.getTime():Infinity;_18=_18?_18.getTime():Infinity;var _19,end;if(_17>=_15&&_17<=_16){_19=_17;}else{if(_15>=_17&&_15<=_18){_19=_15;}}if(_16>=_17&&_16<=_18){end=_16;}else{if(_18>=_15&&_18<=_16){end=_18;}}if(!isNaN(_19)&&!isNaN(end)){var _1a=new esri.TimeExtent();_1a.startTime=(_19===-Infinity)?null:new Date(_19);_1a.endTime=(end===Infinity)?null:new Date(end);return _1a;}else{return null;}}else{return null;}},_getOffsettedDate:function(_1b,_1c,_1d){var _1e=this._refData;var _1f=new Date(_1b.getTime());if(_1c&&_1d){var _1e=_1e[_1d];_1f[_1e.setter](_1f[_1e.getter]()+(_1c*_1e.multiplier));}return _1f;}});_4.declare("esri.TimeReference",null,{constructor:function(_20){if(_20){_4.mixin(this,_20);}}});}}};});