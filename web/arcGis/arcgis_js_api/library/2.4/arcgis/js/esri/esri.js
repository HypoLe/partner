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

(function(){var _1=window[(typeof (djConfig)!="undefined"&&djConfig.scopeMap&&djConfig.scopeMap[0][1])||"dojo"];var _2=window[(typeof (djConfig)!="undefined"&&djConfig.scopeMap&&djConfig.scopeMap[1][1])||"dijit"];var _3=window[(typeof (djConfig)!="undefined"&&djConfig.scopeMap&&djConfig.scopeMap[2][1])||"dojox"];_1.registerModulePath("esri",(location.protocol === 'file:' ? 'http:' : location.protocol) + '//' + arcgisURL+"/deviceManagement/gis/arcgis/arcgis_js_api/library/2.4/arcgis/js/esri");_1.mixin(typeof esri=="undefined"?window.esri={}:esri,{_dojoScopeName:_1._scopeName,_dijitScopeName:_2._scopeName,_dojoxScopeName:_3._scopeName,version:2.4,config:{defaults:{screenDPI:96,geometryService:null,kmlService:null,map:{width:400,height:400,layerNamePrefix:"layer",graphicsLayerNamePrefix:"graphicsLayer",slider:{left:"30px",top:"30px",width:null,height:"200px"},sliderLabel:{tick:5,labels:null,style:"width:2em; font-family:Verdana; font-size:75%;"},sliderChangeImmediate:true,zoomSymbol:{color:[0,0,0,64],outline:{color:[255,0,0,255],width:1.25,style:"esriSLSSolid"},style:"esriSFSSolid"},zoomDuration:250,zoomRate:25,panDuration:250,panRate:25,logoLink:"http://www.esri.com"},io:{errorHandler:function(_4,io){_1.publish("esri.Error",[_4]);},proxyUrl:null,alwaysUseProxy:false,postLength:2000,timeout:60000}}}});var _5=navigator.userAgent,_6;esri.isiPhone=esri.isAndroid=0;_6=_5.match(/(iPhone|iPad|CPU)\s+OS\s+(\d+\_\d+)/i);if(_6){esri.isiPhone=parseFloat(_6[2].replace("_","."));}_6=_5.match(/Android\s+(\d+\.\d+)/i);if(_6){esri.isAndroid=parseFloat(_6[1]);}if(_5.indexOf("BlackBerry")>=0){if(_5.indexOf("WebKit")>=0){esri.isBlackBerry=1;}}esri.isTouchEnabled=(esri.isiPhone||esri.isAndroid||esri.isBlackBerry)?true:false;esri._getDOMAccessor=function(_7){var _8="";if(_1.isFF){_8="Moz";}else{if(_1.isWebKit){_8="Webkit";}else{if(_1.isIE){_8="ms";}else{if(_1.isOpera){_8="O";}}}}return _8+_7.charAt(0).toUpperCase()+_7.substr(1);};esriConfig=esri.config;(function(){var h=document.getElementsByTagName("head")[0],_9=[_1.moduleUrl("esri","../../css/jsapi.css"),_1.moduleUrl("esri","dijit/css/InfoWindow.css")],_a={rel:"stylesheet",type:"text/css",media:"all"};_1.forEach(_9,function(_b){_a.href=_b.toString();_1.create("link",_a,h);});})();}());
