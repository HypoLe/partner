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

if(!dojo._hasResource["esri.PopupBase"]){dojo._hasResource["esri.PopupBase"]=true;dojo.provide("esri.PopupBase");dojo.declare("esri.PopupBase",null,{onSetFeatures:function(){},onClearFeatures:function(){},onSelectionChange:function(){},onDfdComplete:function(){},initialize:function(){this.count=0;this.selectedIndex=-1;},cleanup:function(){this.features=this.deferreds=null;},setFeatures:function(_1){if(!_1||!_1.length){return;}this.clearFeatures();var _2,_3;if(_1[0] instanceof dojo.Deferred){_3=_1;}else{_2=_1;}if(_2){this._updateFeatures(null,_2);}else{this.deferreds=_3;_3=_3.slice(0);dojo.forEach(_3,function(_4){_4.addBoth(dojo.hitch(this,this._updateFeatures,_4));},this);}},clearFeatures:function(){this.features=this.deferreds=null;this.count=0;var _5=this.selectedIndex;this.selectedIndex=-1;if(_5>-1){this.onSelectionChange();}this.onClearFeatures();},getSelectedFeature:function(){var _6=this.features;if(_6){return _6[this.selectedIndex];}},select:function(_7){if(_7<0||_7>=this.count){return;}this.selectedIndex=_7;this.onSelectionChange();},enableHighlight:function(_8){this._highlighted=_8.graphics.add(new esri.Graphic(new esri.geometry.Point(0,0,_8.spatialReference)));this._highlighted.hide();var _9=esri.symbol;if(!this.markerSymbol){var _a=(this.markerSymbol=new _9.SimpleMarkerSymbol());_a.setStyle(_9.SimpleMarkerSymbol.STYLE_TARGET);_a._setDim(16,16,7);_a.setOutline(new _9.CartographicLineSymbol(_9.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([0,255,255]),2,_9.CartographicLineSymbol.CAP_ROUND,_9.CartographicLineSymbol.JOIN_ROUND));_a.setColor(new dojo.Color([0,0,0,0]));}if(!this.lineSymbol){this.lineSymbol=new _9.SimpleLineSymbol(_9.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([0,255,255]),2);}if(!this.fillSymbol){this.fillSymbol=new _9.SimpleFillSymbol(_9.SimpleFillSymbol.STYLE_NULL,new _9.SimpleLineSymbol(_9.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([0,255,255]),2),new dojo.Color([0,0,0,0]));}},disableHighlight:function(_b){var _c=this._highlighted;if(_c){_c.hide();_b.graphics.remove(_c);delete this._highlighted;}this.markerSymbol=this.lineSymbol=this.fillSymbol=null;},showHighlight:function(){if(this._highlighted&&this.features&&this.features[this.selectedIndex]){this._highlighted.show();}},hideHighlight:function(){if(this._highlighted){this._highlighted.hide();}},updateHighlight:function(_d,_e){var _f=_e.geometry,_10=this._highlighted;if(!_f||!_10){return;}_10.hide();if(!_10.getLayer()&&_d){_d.graphics.add(_10);}_10.setGeometry(esri.geometry.fromJson(_f.toJson()));var _11;switch(_f.type){case "point":case "multipoint":_11=this.markerSymbol;_11.setOffset(0,0);_11.setAngle(0);var lyr=_e.getLayer();if(lyr){var _12=lyr._getSymbol(_e),_13,_14,_15=0,_16=0,_17=0;if(_12){switch(_12.type){case "simplemarkersymbol":_13=_14=(_12.size||0);break;case "picturemarkersymbol":_13=(_12.width||0);_14=(_12.height||0);break;}_15=_12.xoffset||0;_16=_12.yoffset||0;_17=_12.angle||0;}if(_13&&_14){_11._setDim(_13+1,_14+1,7);}_11.setOffset(_15,_16);_11.setAngle(_17);}break;case "polyline":_11=this.lineSymbol;break;case "polygon":_11=this.fillSymbol;break;}_10.setSymbol(_11);},_unbind:function(dfd){var _18=dojo.indexOf(this.deferreds,dfd);if(_18===-1){return;}this.deferreds.splice(_18,1);if(!this.deferreds.length){this.deferreds=null;return 2;}return 1;},_updateFeatures:function(dfd,_19){if(dfd){if(this.deferreds){var res=this._unbind(dfd);if(!res){return;}if(_19&&_19 instanceof Error){this.onDfdComplete(_19);if(res===2){this.onSetFeatures();}return;}if(_19&&_19.length){if(!this.features){this.features=_19;this.count=_19.length;this.selectedIndex=0;this.onDfdComplete();if(res===2){this.onSetFeatures();}this.select(0);}else{var _1a=dojo.filter(_19,function(_1b){return dojo.indexOf(this.features,_1b)===-1;},this);this.features=this.features.concat(_1a);this.count=this.features.length;this.onDfdComplete();if(res===2){this.onSetFeatures();}}}else{this.onDfdComplete();if(res===2){this.onSetFeatures();}}}}else{this.features=_19;this.count=_19.length;this.selectedIndex=0;this.onSetFeatures();this.select(0);}}});dojo.declare("esri.PopupInfoTemplate",[esri.InfoTemplate],{"-chains-":{constructor:"manual"},initialize:function(_1c){if(!_1c){return;}this.info=_1c;this.title=this.getTitle;this.content=this.getContent;var _1d=(this._fieldLabels={}),_1e=(this._fieldsMap={});if(_1c.fieldInfos){dojo.forEach(_1c.fieldInfos,function(_1f){_1d[_1f.fieldName]=_1f.label;_1e[_1f.fieldName]=_1f;});}},toJson:function(){return dojo.fromJson(dojo.toJson(this.info));},getTitle:function(){},getContent:function(){},getComponents:function(_20){var _21=this.info,_22=_20.getLayer(),_23=dojo.clone(_20.attributes)||{},_24=dojo.clone(_23),_25=_21.fieldInfos,_26="",_27="",_28,_29,_2a,_2b=_22&&_22._getDateOpts&&_22._getDateOpts().properties,_2c={dateFormat:{properties:_2b,formatter:"DateFormat"+this._dateFormats["shortDateShortTime"]}};if(_25){dojo.forEach(_25,function(_2d){var _2e=_2d.fieldName,val=_24[_2e];_24[_2e]=this._formatValue(val,_2e,_2c);if(_2b&&_2d.format&&_2d.format.dateFormat){var pos=dojo.indexOf(_2b,_2e);if(pos>-1){_2b.splice(pos,1);}}},this);}if(_22){var _2f=_22.types,_30=_22.typeIdField,_31=_30&&_23[_30];for(_29 in _23){_2a=_23[_29];if(esri._isDefined(_2a)){var _32=this._getDomainName(_22,_2f,_31,_29,_2a);if(esri._isDefined(_32)){_24[_29]=_32;}else{if(_29===_30){var _33=this._getTypeName(_22,_2a);if(esri._isDefined(_33)){_24[_29]=_33;}}}}}}if(_21.title){_26=dojo.trim(esri.substitute(_24,this._fixTokens(_21.title),_2c)||"");}if(_21.description){_27=dojo.trim(esri.substitute(_24,this._fixTokens(_21.description),_2c)||"");}if(_25){_28=[];dojo.forEach(_25,function(_34){_29=_34.fieldName;if(_29&&_34.visible){_28.push([_34.label||_29,esri.substitute(_24,"${"+_29+"}",_2c)||""]);}});}var _35,_36;if(_21.mediaInfos){_35=[];dojo.forEach(_21.mediaInfos,function(_37){_36=0;_2a=_37.value;switch(_37.type){case "image":var url=_2a.sourceURL;url=url&&dojo.trim(esri.substitute(_23,this._fixTokens(url)));_36=!!url;break;case "piechart":case "linechart":case "columnchart":case "barchart":_36=dojo.some(_2a.fields,function(_38){return esri._isDefined(_23[_38]);});break;default:return;}if(_36){_37=dojo.clone(_37);_2a=_37.value;_37.title=_37.title?dojo.trim(esri.substitute(_24,this._fixTokens(_37.title),_2c)||""):"";_37.caption=_37.caption?dojo.trim(esri.substitute(_24,this._fixTokens(_37.caption),_2c)||""):"";if(_37.type==="image"){_2a.sourceURL=esri.substitute(_23,this._fixTokens(_2a.sourceURL));if(_2a.linkURL){_2a.linkURL=dojo.trim(esri.substitute(_23,this._fixTokens(_2a.linkURL))||"");}}else{var _39=_23[_2a.normalizeField]||0;_2a.fields=dojo.map(_2a.fields,function(_3a){var _3b=_23[_3a];_3b=(_3b===undefined)?null:_3b;if(_3b&&_39){_3b=_3b/_39;}return {y:_3b,tooltip:(this._fieldLabels[_3a]||_3a)+":<br/>"+this._formatValue(_3b,_3a,_2c)};},this);}_35.push(_37);}},this);}return {title:_26,description:_27,fields:(_28&&_28.length)?_28:null,mediaInfos:(_35&&_35.length)?_35:null,formatted:_24};},getAttachments:function(_3c){var _3d=_3c.getLayer(),_3e=_3c.attributes;if(this.info.showAttachments&&_3d&&_3d.hasAttachments&&_3d.objectIdField){var oid=_3e&&_3e[_3d.objectIdField];if(oid){return _3d.queryAttachmentInfos(oid);}}},_dateFormats:{"shortDate":"(datePattern: 'M/d/y', selector: 'date')","longMonthDayYear":"(datePattern: 'MMMM d, y', selector: 'date')","dayShortMonthYear":"(datePattern: 'd MMM y', selector: 'date')","longDate":"(datePattern: 'EEEE, MMMM d, y', selector: 'date')","shortDateShortTime":"(datePattern: 'M/d/y', timePattern: 'h:mm a', selector: 'date and time')","shortDateShortTime24":"(datePattern: 'M/d/y', timePattern: 'H:mm', selector: 'date and time')","longMonthYear":"(datePattern: 'MMMM y', selector: 'date')","shortMonthYear":"(datePattern: 'MMM y', selector: 'date')","year":"(datePattern: 'y', selector: 'date')"},_fixTokens:function(_3f){return _3f.replace(/(\{[^\{\r\n]+\})/g,"$$$1");},_formatValue:function(val,_40,_41){var _42=this._fieldsMap[_40],fmt=_42&&_42.format;if(!esri._isDefined(val)||!_42||!esri._isDefined(fmt)){return val;}var _43="",_44=[],_45=fmt.hasOwnProperty("places")||fmt.hasOwnProperty("digitSeparator"),_46=fmt.hasOwnProperty("digitSeparator")?fmt.digitSeparator:true;if(_45){_43="NumberFormat";_44.push("places: "+(esri._isDefined(fmt.places)?Number(fmt.places):"Infinity"));if(_44.length){_43+=("("+_44.join(",")+")");}}else{if(fmt.dateFormat){_43="DateFormat"+(this._dateFormats[fmt.dateFormat]||this._dateFormats["shortDateShortTime"]);}else{return val;}}var _47=esri.substitute({"myKey":val},"${myKey:"+_43+"}",_41)||"";if(_45&&!_46){var _48=dojo.i18n.getLocalization("dojo.cldr","number");if(_48.group){_47=_47.replace(new RegExp("\\"+_48.group,"g"),"");}}return _47;},_getDomainName:function(_49,_4a,_4b,_4c,_4d){var _4e,_4f;if(_4a&&esri._isDefined(_4b)){dojo.some(_4a,function(_50){if(_50.id==_4b){_4e=_50.domains&&_50.domains[_4c];if(_4e&&_4e.type==="inherited"){_4e=this._getLayerDomain(_49,_4c);_4f=true;}return true;}return false;},this);}if(!_4f&&!_4e){_4e=this._getLayerDomain(_49,_4c);}if(_4e&&_4e.codedValues){var _51;dojo.some(_4e.codedValues,function(_52){if(_52.code==_4d){_51=_52.name;return true;}return false;});return _51;}},_getLayerDomain:function(_53,_54){var _55=_53.fields;if(_55){var _56;dojo.some(_55,function(_57){if(_57.name===_54){_56=_57.domain;return true;}return false;});return _56;}},_getTypeName:function(_58,id){var _59=_58.types;if(_59){var _5a;dojo.some(_59,function(_5b){if(_5b.id==id){_5a=_5b.name;return true;}return false;});return _5a;}}});}