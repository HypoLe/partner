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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.layers.KMLLayer"],["require","esri.utils"],["require","esri.layers.layer"],["require","esri.layers.MapImageLayer"],["require","esri.layers.FeatureLayer"],["require","esri.dijit.Popup"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.layers.KMLLayer"]){_4._hasResource["esri.layers.KMLLayer"]=true;_4.provide("esri.layers.KMLLayer");_4.require("esri.utils");_4.require("esri.layers.layer");_4.require("esri.layers.MapImageLayer");_4.require("esri.layers.FeatureLayer");_4.require("esri.dijit.Popup");_4.declare("esri.layers.KMLLayer",[esri.layers.Layer],{serviceUrl:"http://utility.arcgis.com/sharing/kml",constructor:function(_7,_8){if(!_7){console.log("KMLLayer:constructor - please provide url for the KML file");}this._outSR=null;if(_8&&_8.outSR){this._outSR=_8.outSR;this._outSR=(this._outSR)?this._outSR.wkid:null;}this._options=_8;if(esri.config.defaults.kmlService){this.serviceUrl=esri.config.defaults.kmlService;}var _9=(this.linkInfo=_8&&_8.linkInfo);if(_9){this.visible=!!_9.visibility;}if(!_9||(_9&&_9.visibility)){this._parseKml();}},getFeature:function(_a){if(!_a){return;}var _b=_a.type,id=_a.id,_c,i,_d;switch(_b){case "esriGeometryPoint":case "esriGeometryPolyline":case "esriGeometryPolygon":var _e=this["_"+_b];if(_e){_c=_4.getObject("_mode._featureMap."+id,false,_e);}break;case "GroundOverlay":var _f=this._groundLyr;if(_f){var _10=_f.getImages();_d=_10.length;for(i=0;i<_d;i++){if(_10[i].id===id){_c=_10[i];break;}}}break;case "ScreenOverlay":break;case "NetworkLink":_4.some(this._links,function(_11){if(_11.linkInfo.id===id){_c=_11;return true;}else{return false;}});break;case "Folder":var _12=this.folders;_d=_12?_12.length:0;for(i=0;i<_d;i++){if(_12[i].id===id){_c=_12[i];break;}}break;default:console.log("KMLLayer:getFeature - unknown feature type");break;}return _c;},getLayers:function(){var _13=[];if(this._groundLyr){_13.push(this._groundLyr);}if(this._fLayers){_13=_13.concat(this._fLayers);}return _13;},setFolderVisibility:function(_14,_15){if(!_14){return;}_14.visible=_15;if(_15){var _16=this._getParentFolders(_14,[]);if(_16.length>0){_15=_4.every(_16,function(_17){return _17.visible;});}}this._setState(_14,_15);},_parseKml:function(){var _18=this;esri.request({url:this.serviceUrl,content:{url:this.url,model:"simple",folders:"",outSR:this._outSR},callbackParamName:"callback",load:function(_19){_18._initLayer(_19);},error:function(err){_18.onError("Error reading kml document. ("+_4.toJson(err)+")");}});},_initLayer:function(_1a){this.name=_1a.name;this.description=_1a.description;this.snippet=_1a.snippet;this.visibility=_1a.visibility;this.featureInfos=_1a.featureInfos;var i,len;var _1b=(this._links=_1a.networkLinks);len=_1b?_1b.length:0;var _1c=this;for(i=0;i<len;i++){var _1d={linkInfo:_1b[i]};_1d=_4.mixin(_1d,this._options);if(_1d.id){_1d.id=_1d.id+"_"+i;}if(_1b[i].viewRefreshMode==="never"){_1b[i]=new esri.layers.KMLLayer(_1b[i].href,_1d);_4.connect(_1b[i],"onLoad",function(a){_1c._map.addLayer(a);});}}var _1e=_1a.groundOverlays;if(_1e&&_1e.length>0){var _1d=_4.mixin({},this._options);if(_1d.id){_1d.id=_1d.id+"_"+"mapImage";}var _1f=(this._groundLyr=new esri.layers.MapImageLayer(_1d));len=_1e.length;for(i=0;i<len;i++){_1f.addImage(new esri.layers.KMLGroundOverlay(_1e[i]));}}var _20=_4.getObject("featureCollection.layers",false,_1a);if(_20&&_20.length>0){this._fLayers=[];_4.forEach(_20,function(_21,i){var _22=_4.getObject("featureSet.features",false,_21),_23;if(_22&&_22.length>0){_21.layerDefinition.capabilities="Query,Data";var _24={outFields:["*"],infoTemplate:_21.popupInfo?new esri.dijit.PopupTemplate(_21.popupInfo):null,editable:false};_24=_4.mixin(_24,this._options);if(_24.id){_24.id=_24.id+"_"+i;}_23=new esri.layers.FeatureLayer(_21,_24);if(_23.geometryType){this["_"+_23.geometryType]=_23;}this._fLayers.push(_23);}},this);if(this._fLayers.length===0){delete this._fLayers;}}var _25=(this.folders=_1a.folders),_26=[],_27;if(_25){len=_25.length;for(i=0;i<len;i++){_27=(_25[i]=new esri.layers.KMLFolder(_25[i]));if(_27.parentFolderId===-1){_26.push(_27);}}}len=_26.length;for(i=0;i<len;i++){_27=_26[i];this._setState(_27,_27.visible);}this.loaded=true;this.onLoad(this);},_setState:function(_28,_29){var _2a=_28.featureInfos,_2b,_2c,i,len=_2a?_2a.length:0,_2d=_29?"show":"hide";for(i=0;i<len;i++){_2b=_2a[i];_2c=this.getFeature(_2b);if(!_2c){continue;}if(_2b.type==="Folder"){this._setState(_2c,_29&&_2c.visible);}else{_2c[_2d]();}}},_getParentFolders:function(_2e,_2f){var _30=_2e.parentFolderId;if(_30!==-1){var _31=this.getFeature({type:"Folder",id:_30});_2f.push(_31);return this._getParentFolders(_31,_2f);}return _2f;},_setMap:function(map,_32){this._map=map;var div=this._div=_4.create("div",null,_32);_4.style(div,"position","absolute");if(this._groundLyr){var _33=map.spatialReference._isWebMercator();if(_33){var _34=esri.geometry.geographicToWebMercator;_4.forEach(this._groundLyr.getImages(),function(_35){_35.extent=_34(_35.extent);});}map.addLayer(this._groundLyr,map.layerIds.length);}var _36=this._fLayers;if(_36&&_36.length>0){var _33=map.spatialReference._isWebMercator();_4.forEach(_36,function(_37){if(_33){var _38=_37.graphics,i,_39,len=_38?_38.length:0,_34=esri.geometry.geographicToWebMercator;for(i=0;i<len;i++){_39=_38[i].geometry;if(_39){_38[i].setGeometry(_34(_39));}}}map.addLayer(_37);});}this.onVisibilityChange(this.visible);return div;},_unsetMap:function(map,_3a){if(this._groundLyr){map.removeLayer(this._groundLyr);}if(this._fLayers){_4.forEach(this._fLayers,function(_3b){map.removeLayer(_3b);});}var div=this._div;if(div){_3a.removeChild(div);_4.destroy(div);}this._map=this._div=null;},onVisibilityChange:function(_3c){var _3d=_3c?"show":"hide";if(_3c&&this.linkInfo&&!this.loaded){this._parseKml();}else{if(this._groundLyr){this._groundLyr[_3d]();}if(this._fLayers){_4.forEach(this._fLayers,function(_3e){_3e[_3d]();});}}}});_4.declare("esri.layers.KMLGroundOverlay",[esri.layers.MapImage],{constructor:function(_3f){if(esri._isDefined(this.visibility)){this.visible=!!this.visibility;}}});_4.declare("esri.layers.KMLFolder",null,{constructor:function(_40){_4.mixin(this,_40);if(esri._isDefined(this.visibility)){this.visible=!!this.visibility;}}});}}};});