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

if(!dojo._hasResource["esri.utils"]){dojo._hasResource["esri.utils"]=true;dojo.provide("esri.utils");dojo.require("dojo.io.script");dojo.require("esri.graphic");dojo.requireLocalization("esri","jsapi",null,"ROOT,ar,de,es,fr,it,ja,ru,zh");dojo.addOnLoad(function(){esri.bundle=dojo.i18n.getLocalization("esri","jsapi");});esri.show=function(_1){_1.style.display="block";};esri.hide=function(_2){_2.style.display="none";};esri.toggle=function(_3){_3.style.display=_3.style.display==="none"?"block":"none";};esri.valueOf=function(_4,_5){for(var i in _4){if(_4[i]==_5){return i;}}return null;};esri.substitute=(function(){var _6="${*}",_7=["NumberFormat","DateString","DateFormat"];function _8(_9){return esri._isDefined(_9)?_9:"";};function _a(_b,_c,_d){var _e=_d.match(/([^\(]+)(\([^\)]+\))?/i);var _f=dojo.trim(_e[1]);var _10=dojo.fromJson((_e[2]?dojo.trim(_e[2]):"()").replace(/^\(/,"({").replace(/\)$/,"})"));var _11=_c[_b];if(dojo.indexOf(_7,_f)===-1){var ref=dojo.getObject(_f);if(dojo.isFunction(ref)){_11=ref(_11,_b,_c);}}else{if(typeof _11==="number"||(typeof _11==="string"&&_11&&!isNaN(Number(_11)))){_11=Number(_11);switch(_f){case "NumberFormat":if(dojo.getObject("dojo.number.format")){return dojo.number.format(_11,_10);}break;case "DateString":var _12=new Date(_11);if(_10.local||_10.systemLocale){if(_10.systemLocale){return _12.toLocaleDateString()+(_10.hideTime?"":(" "+_12.toLocaleTimeString()));}else{return _12.toDateString()+(_10.hideTime?"":(" "+_12.toTimeString()));}}else{_12=_12.toUTCString();if(_10.hideTime){_12=_12.replace(/\s+\d\d\:\d\d\:\d\d\s+(utc|gmt)/i,"");}return _12;}break;case "DateFormat":if(dojo.getObject("dojo.date.locale.format")){return dojo.date.locale.format(new Date(_11),_10);}break;}}}return _8(_11);};return function(_13,_14,_15){var _16,_17,_18;if(esri._isDefined(_15)){if(dojo.isObject(_15)){_16=_15.first;_17=_15.dateFormat;_18=_15.numberFormat;}else{_16=_15;}}if(!_14||_14===_6){var s=[],val;for(var i in _13){val=_13[i];if(_17&&dojo.indexOf(_17.properties||"",i)!==-1){val=_a(i,_13,_17.formatter||"DateString");}else{if(_18&&dojo.indexOf(_18.properties||"",i)!==-1){val=_a(i,_13,_18.formatter||"NumberFormat");}}s.push(i+" = "+_8(val)+"<br/>");if(_16){break;}}return s.join("");}else{return dojo.replace(_14,dojo.hitch({obj:_13},function(_19,key){var _1a=key.split(":");if(_1a.length>1){key=_1a[0];_1a.shift();return _a(key,this.obj,_1a.join(":"));}else{if(_17&&dojo.indexOf(_17.properties||"",key)!==-1){return _a(key,this.obj,_17.formatter||"DateString");}if(_18&&dojo.indexOf(_18.properties||"",key)!==-1){return _a(key,this.obj,_18.formatter||"NumberFormat");}}return _8(this.obj[key]);}),/\$\{([^\}]+)\}/g);}};}());esri.documentBox=dojo.isIE?{w:document.documentElement.clientWidth,h:document.documentElement.clientHeight}:{w:window.innerWidth,h:window.innerHeight};esri.urlToObject=function(url){var iq=url.indexOf("?");if(iq===-1){return {path:url,query:null};}else{return {path:url.substring(0,iq),query:dojo.queryToObject(url.substring(iq+1))};}};esri._getProxyUrl=function(){var _1b=esri.config.defaults.io.proxyUrl;if(!_1b){console.log(esri.bundle.io.proxyNotSet);throw new Error(esri.bundle.io.proxyNotSet);}return esri.urlToObject(_1b);};esri._getProxiedUrl=function(url){if(esri.config.defaults.io.alwaysUseProxy){var _1c=esri._getProxyUrl(),_1d=esri.urlToObject(url);url=_1c.path+"?"+_1d.path;var _1e=dojo.objectToQuery(dojo.mixin(_1c.query||{},_1d.query));if(_1e){url+=("?"+_1e);}}return url;};esri._hasSameOrigin=function(_1f,_20){_1f=new dojo._Url(_1f);_20=new dojo._Url(_20);return (_1f.scheme===_20.scheme&&_1f.host===_20.host&&_1f.port===_20.port);};esri.request=function(_21,_22){var _23=false,_24=false;if(esri._isDefined(_22)){if(dojo.isObject(_22)){_23=!!_22.useProxy;_24=!!_22.usePost;}else{_23=!!_22;}}var _25=_21.content,_26=_21.url,_27=_21.load,_28=_21.error,_29=esri.config.defaults.io;_21.load=function(_2a,io){_21.load=_27;if(_2a&&_2a.error){_21.error(_2a.error,io);}else{if(_27){_27(_2a,io);}}};_21.error=function(_2b,io){if(io&&io.xhr){io.xhr.abort();}if(!(_2b instanceof Error)){_2b=dojo.mixin(new Error(),_2b);}_21.error=_28;_29.errorHandler(_2b,io);if(_28){_28(_2b,io);}};var len=0;if(_25&&_26){len=dojo.objectToQuery(_25).length+_26.length+1;}_21.timeout=_21.timeout||_29.timeout;_21.handleAs=_21.handleAs||"json";try{var _2c=esri._reqPreCallback,_2d=(_24||len>_29.postLength)?true:false,_2e=(_21.handleAs.indexOf("json")!==-1&&_21.callbackParamName)?true:false,_2f=(_29.alwaysUseProxy||_23||((!_2e||_2d)&&!esri._hasSameOrigin(_21.url,window.location.href)))?true:false;if(_2f){var _30=esri._getProxyUrl();if(!_2d&&(_30.path.length+1+len)>_29.postLength){_2d=true;}_21=dojo.mixin({},_21);_21.url=_30.path+"?"+_26;if(_2d){_21.content=dojo.mixin(_30.query||{},_25);}else{var _31=dojo.objectToQuery(dojo.mixin(_30.query||{},_25));if(_31){_21.url+=("?"+_31);}_21.content=null;}}if(_2e&&!_2d){if(!esri._isDefined(_21.isAsync)&&dojo.isFF<4){_21.isAsync=true;}return dojo.io.script.get(_2c?_2c(_21):_21);}else{_21=_2c?_2c(_21):_21;if(_2d){return dojo.rawXhrPost(_21);}else{return dojo.xhrGet(_21);}}}catch(e){_21.error(e);}};esri.setRequestPreCallback=function(_32){esri._reqPreCallback=_32;};esri._getParts=function(arr,obj,cb){return [dojo.isString(arr)?arr.split(""):arr,obj||dojo.global,dojo.isString(cb)?new Function("item","index","array",cb):cb];};esri.filter=function(arr,_33,_34){var _35=esri._getParts(arr,_34,_33),_36={};arr=_35[0];for(var i in arr){if(_35[2].call(_35[i],arr[i],i,arr)){_36[i]=arr[i];}}return _36;};esri.TileUtils=(function(){function _37(map,ti,_38){var wd=map.width,ht=map.height,ew=_38.xmax-_38.xmin,eh=_38.ymax-_38.ymin,ed=-1,_39=ti.lods,abs=Math.abs,lod,cl,ced;for(var i=0,il=_39.length;i<il;i++){cl=_39[i];ced=ew>eh?abs(eh-(ht*cl.resolution)):abs(ew-(wd*cl.resolution));if(ed<0||ced<=ed){lod=cl;ed=ced;}else{break;}}return lod;};function _3a(map,_3b,lod){var res=lod.resolution,cx=(_3b.xmin+_3b.xmax)/2,cy=(_3b.ymin+_3b.ymax)/2,_3c=(map.width/2)*res,_3d=(map.height/2)*res;return new esri.geometry.Extent(cx-(_3c),cy-(_3d),cx+(_3c),cy+(_3d),_3b.spatialReference);};function _3e(map,ti,_3f,lod){var res=lod.resolution,tw=ti.width,th=ti.height,to=ti.origin,mv=map.__visibleDelta,_40=Math.floor,tmw=tw*res,tmh=th*res,tr=_40((to.y-_3f.y)/tmh),tc=_40((_3f.x-to.x)/tmw),_41=to.x+(tc*tmw),_42=to.y-(tr*tmh),oX=_40(Math.abs((_3f.x-_41)*tw/tmw))+mv.x,oY=_40(Math.abs((_3f.y-_42)*th/tmh))+mv.y;return {point:_3f,coords:{row:tr,col:tc},offsets:{x:oX,y:oY}};};return {_addFrameInfo:function(_43,_44){var _45,_46,_47=2*_44.origin[1],_48=_44.origin[0],_49=_43.origin.x,_4a=_43.width,_4b;dojo.forEach(_43.lods,function(lod){_45=Math.round(_47/lod.resolution);_46=Math.ceil(_45/_4a);_4b=Math.floor((_48-_49)/(_4a*lod.resolution));if(!lod._frameInfo){lod._frameInfo=[_46,_4b,_4b+_46-1,_45];}});},getContainingTileCoords:function(ti,_4c,lod){var to=ti.origin,res=lod.resolution,tmw=ti.width*res,tmh=ti.height*res,tc=Math.floor((_4c.x-to.x)/tmw),tr=Math.floor((to.y-_4c.y)/tmh);return {row:tr,col:tc};},getCandidateTileInfo:function(map,ti,_4d){var lod=_37(map,ti,_4d),adj=_3a(map,_4d,lod),ct=_3e(map,ti,new esri.geometry.Point(adj.xmin,adj.ymax,_4d.spatialReference),lod);return {tile:ct,lod:lod,extent:adj};},getTileExtent:function(ti,_4e,row,col){var to=ti.origin,lod=ti.lods[_4e],res=lod.resolution,tw=ti.width,th=ti.height;return new esri.geometry.Extent(((col*res)*tw)+to.x,to.y-((row+1)*res)*th,(((col+1)*res)*tw)+to.x,to.y-((row*res)*th),ti.spatialReference);}};}());esri.graphicsExtent=function(_4f){var g=_4f[0].geometry,_50=g.getExtent(),ext;if(_50===null){_50=new esri.geometry.Extent(g.x,g.y,g.x,g.y,g.spatialReference);}for(var i=1,il=_4f.length;i<il;i++){ext=(g=_4f[i].geometry).getExtent();if(ext===null){ext=new esri.geometry.Extent(g.x,g.y,g.x,g.y,g.spatialReference);}_50=_50.union(ext);}if(_50.getWidth()<=0&&_50.getHeight()<=0){return null;}return _50;};esri.getGeometries=function(_51){return dojo.map(_51,function(_52){return _52.geometry;});};esri._encodeGraphics=function(_53,_54){var _55=[],_56,enc,_57;dojo.forEach(_53,function(g,i){_56=g.toJson();enc={};if(_56.geometry){_57=_54&&_54[i];enc.geometry=_57&&_57.toJson()||_56.geometry;}if(_56.attributes){enc.attributes=_56.attributes;}_55[i]=enc;});return _55;};esri._serializeLayerDefinitions=function(_58){var _59=[],_5a=false,re=/[:;]/;if(_58){dojo.forEach(_58,function(_5b,i){if(_5b){_59.push([i,_5b]);if(!_5a&&re.test(_5b)){_5a=true;}}});if(_59.length>0){var _5c;if(_5a){_5c={};dojo.forEach(_59,function(_5d){_5c[_5d[0]]=_5d[1];});_5c=dojo.toJson(_5c);}else{_5c=[];dojo.forEach(_59,function(_5e){_5c.push(_5e[0]+":"+_5e[1]);});_5c=_5c.join(";");}return _5c;}}return null;};esri._serializeTimeOptions=function(_5f,ids){if(!_5f){return;}var _60=[];dojo.forEach(_5f,function(_61,i){if(_61){var _62=_61.toJson();if(ids&&dojo.indexOf(ids,i)!==-1){_62.useTime=false;}_60.push("\""+i+"\":"+dojo.toJson(_62));}});if(_60.length){return "{"+_60.join(",")+"}";}};esri._isDefined=function(_63){return (_63!==undefined)&&(_63!==null);};esri._sanitize=function(obj){for(var _64 in obj){if(obj.hasOwnProperty(_64)){if(obj[_64]===undefined){delete obj[_64];}}}return obj;};esri._dfdCanceller=function(dfd){dfd.canceled=true;var _65=dfd._pendingDfd;if(dfd.fired===-1&&_65&&_65.fired===-1){_65.cancel();}dfd._pendingDfd=null;};esri._fixDfd=function(dfd){var _66=dfd.then;dfd.then=function(_67,b,c){if(_67){var _68=_67;_67=function(_69){if(_69&&_69._argsArray){return _68.apply(null,_69);}return _68(_69);};}return _66.call(this,_67,b,c);};return dfd;};esri._resDfd=function(dfd,_6a,_6b){var _6c=_6a.length;if(_6c===1){if(_6b){dfd.errback(_6a[0]);}else{dfd.callback(_6a[0]);}}else{if(_6c>1){_6a._argsArray=true;dfd.callback(_6a);}else{dfd.callback();}}};esri._createWrappers=function(_6d){var _6e=dojo.getObject(_6d+".prototype");dojo.forEach(_6e.__msigns,function(sig){var _6f=_6e[sig.n];_6e[sig.n]=function(){var _70=this,_71=[],_72=new dojo.Deferred(esri._dfdCanceller);if(sig.f){esri._fixDfd(_72);}for(var i=0;i<sig.c;i++){_71[i]=arguments[i];}var _73={dfd:_72};_71.push(_73);var _74,_75=[],_76;if(_70.normalization&&!_70._isTable){_74=esri._disassemble(_71,sig.a);dojo.forEach(_74,function(_77){_75=_75.concat(_77.value);});if(_75.length){var sr=_75[0].spatialReference;if(sr&&sr._isWrappable()){_76=esri.geometry.normalizeCentralMeridian(_75,esri.config.defaults.geometryService);}}}if(_76){_72._pendingDfd=_76;_76.addCallbacks(function(_78){if(_72.canceled){return;}_73.assembly=esri._reassemble(_78,_74);_72._pendingDfd=_6f.apply(_70,_71);},function(err){var _79=_70.declaredClass;if(_79&&_79.indexOf("FeatureLayer")!==-1){_70._resolve([err],null,_71[sig.e],_72,true);}else{_70._errorHandler(err,_71[sig.e],_72);}});}else{_72._pendingDfd=_6f.apply(_70,_71);}return _72;};});};esri._disassemble=function(_7a,_7b){var _7c=[];dojo.forEach(_7b,function(_7d){var _7e=_7d.i,arg=_7a[_7e],_7f=_7d.p;if(!dojo.isObject(arg)||!arg){return;}if(_7f){if(_7f[0]==="*"){for(var _80 in arg){if(arg.hasOwnProperty(_80)){esri._addToBucket(arg[_80],_7c,_7e,_80);}}}else{dojo.forEach(_7f,function(_81){esri._addToBucket(dojo.getObject(_81,false,arg),_7c,_7e,_81);});}}else{esri._addToBucket(arg,_7c,_7e);}});return _7c;};esri._addToBucket=function(_82,_83,_84,_85){var _86=false,_87;if(dojo.isObject(_82)&&_82){if(dojo.isArray(_82)){if(_82.length){_87=_82[0]&&_82[0].declaredClass;if(_87&&_87.indexOf("Graphic")!==-1){_82=dojo.map(_82,function(_88){return _88.geometry;});_82=dojo.filter(_82,esri._isDefined);_86=_82.length?true:false;}else{if(_87&&_87.indexOf("esri.geometry.")!==-1){_86=true;}}}}else{_87=_82.declaredClass;if(_87&&_87.indexOf("FeatureSet")!==-1){_82=dojo.map(_82.features||[],function(_89){return _89.geometry;});_82=dojo.filter(_82,esri._isDefined);_86=_82.length?true:false;}else{if(_87&&_87.indexOf("esri.geometry.")!==-1){_86=true;}}}}if(_86){_83.push({index:_84,property:_85,value:_82});}};esri._reassemble=function(_8a,_8b){var idx=0,_8c={};dojo.forEach(_8b,function(_8d){var _8e=_8d.index,_8f=_8d.property,_90=_8d.value,len=_90.length||1;var _91=_8a.slice(idx,idx+len);if(!dojo.isArray(_90)){_91=_91[0];}idx+=len;delete _8d.value;if(_8f){_8c[_8e]=_8c[_8e]||{};_8c[_8e][_8f]=_91;}else{_8c[_8e]=_91;}});return _8c;};esri.setScrollable=function(_92){var _93=0,_94=0,_95=0,_96=0,_97=0,_98=0;return [dojo.connect(_92,"ontouchstart",function(evt){_93=evt.touches[0].screenX;_94=evt.touches[0].screenY;_95=_92.scrollWidth;_96=_92.scrollHeight;_97=_92.clientWidth;_98=_92.clientHeight;}),dojo.connect(_92,"ontouchmove",function(evt){evt.preventDefault();var _99=_92.firstChild,_9a=_99._currentX||0,_9b=_99._currentY||0;_9a+=(evt.touches[0].screenX-_93);if(_9a>0){_9a=0;}else{if(_9a<0&&(Math.abs(_9a)+_97)>_95){_9a=-1*(_95-_97);}}_99._currentX=_9a;_9b+=(evt.touches[0].screenY-_94);if(_9b>0){_9b=0;}else{if(_9b<0&&(Math.abs(_9b)+_98)>_96){_9b=-1*(_96-_98);}}_99._currentY=_9b;dojo.style(_99,{"-webkit-transition-property":"-webkit-transform","-webkit-transform":"translate("+_9a+"px, "+_9b+"px)"});_93=evt.touches[0].screenX;_94=evt.touches[0].screenY;})];};}