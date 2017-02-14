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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {defineResource:function(_4,_5,_6){(function(){var _7=window[(typeof (djConfig)!="undefined"&&djConfig.scopeMap&&djConfig.scopeMap[0][1])||"dojo"];var _8=window[(typeof (djConfig)!="undefined"&&djConfig.scopeMap&&djConfig.scopeMap[1][1])||"dijit"];var _9=window[(typeof (djConfig)!="undefined"&&djConfig.scopeMap&&djConfig.scopeMap[2][1])||"dojox"];_7.registerModulePath("esri",(location.protocol === 'file:' ? 'http:' : location.protocol) + '//' + "[HOSTNAME_AND_PATH_TO_JSAPI]js/esri");_7.mixin(typeof esri=="undefined"?window.esri={}:esri,{_dojoScopeName:_7._scopeName,_dijitScopeName:_8._scopeName,_dojoxScopeName:_9._scopeName,version:2.4,config:{defaults:{screenDPI:96,geometryService:null,kmlService:null,map:{width:400,height:400,layerNamePrefix:"layer",graphicsLayerNamePrefix:"graphicsLayer",slider:{left:"30px",top:"30px",width:null,height:"200px"},sliderLabel:{tick:5,labels:null,style:"width:2em; font-family:Verdana; font-size:75%;"},sliderChangeImmediate:true,zoomSymbol:{color:[0,0,0,64],outline:{color:[255,0,0,255],width:1.25,style:"esriSLSSolid"},style:"esriSFSSolid"},zoomDuration:250,zoomRate:25,panDuration:250,panRate:25,logoLink:"http://www.esri.com"},io:{errorHandler:function(_a,io){_7.publish("esri.Error",[_a]);},proxyUrl:null,alwaysUseProxy:false,postLength:2000,timeout:60000}}}});var _b=navigator.userAgent,_c;esri.isiPhone=esri.isAndroid=0;_c=_b.match(/(iPhone|iPad|CPU)\s+OS\s+(\d+\_\d+)/i);if(_c){esri.isiPhone=parseFloat(_c[2].replace("_","."));}_c=_b.match(/Android\s+(\d+\.\d+)/i);if(_c){esri.isAndroid=parseFloat(_c[1]);}if(_b.indexOf("BlackBerry")>=0){if(_b.indexOf("WebKit")>=0){esri.isBlackBerry=1;}}esri.isTouchEnabled=(esri.isiPhone||esri.isAndroid||esri.isBlackBerry)?true:false;esri._getDOMAccessor=function(_d){var _e="";if(_7.isFF){_e="Moz";}else{if(_7.isWebKit){_e="Webkit";}else{if(_7.isIE){_e="ms";}else{if(_7.isOpera){_e="O";}}}}return _e+_d.charAt(0).toUpperCase()+_d.substr(1);};esriConfig=esri.config;(function(){var h=document.getElementsByTagName("head")[0],_f=[_7.moduleUrl("esri","../../css/jsapi.css"),_7.moduleUrl("esri","dijit/css/InfoWindow.css")],_10={rel:"stylesheet",type:"text/css",media:"all"};_7.forEach(_f,function(css){_10.href=css.toString();_7.create("link",_10,h);});})();}());}};});
