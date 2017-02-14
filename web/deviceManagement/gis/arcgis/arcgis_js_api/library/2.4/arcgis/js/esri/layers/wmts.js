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

if(!dojo._hasResource["esri.layers.wmts"]){dojo._hasResource["esri.layers.wmts"]=true;dojo.provide("esri.layers.wmts");dojo.require("esri.layers.tiled");dojo.require("esri.layers.agscommon");dojo.require("esri.WKIDUnitConversion");dojo.require("dojox.xml.parser");dojo.declare("esri.layers.WMTSLayer",[esri.layers.TiledMapServiceLayer],{copyright:null,extent:null,tileUrl:null,layerInfo:null,spatialReference:null,tileInfo:null,constructor:function(_1,_2){this.version="1.0.0";this.tileUr=this._url=_1;this.serviceMode="RESTful";this._parseCapabilities=dojo.hitch(this,this._parseCapabilities);this._getCapabilitiesError=dojo.hitch(this,this._getCapabilitiesError);if(!_2){_2={};}if(_2.serviceMode){if(_2.serviceMode==="KVP"||_2.serviceMode==="RESTful"){this.serviceMode=_2.serviceMode;}else{console.error("WMTS mode could only be 'KVP' or 'RESTful'");return;}}if(_2.layerInfo){this.layerInfo=_2.layerInfo;this._identifier=_2.layerInfo.identifier;this._tileMatrixSetId=_2.layerInfo.tileMatrixSet;this.format="image/"+_2.layerInfo.format;this._style=_2.layerInfo.style;this.title=_2.layerInfo.title;}if(_2.resourceInfo){this.version=_2.resourceInfo.version;if(_2.resourceInfo.getTileUrl){this._url=this.tileUrl=_2.resourceInfo.getTileUrl;}this.copyright=_2.resourceInfo.copyright;this.layerInfos=_2.resourceInfo.layerInfos;this._parseResourceInfo();this.loaded=true;this.onLoad(this);}else{this._getCapabilities();}this._formatDictionary={"image/png":".png","image/png8":".png","image/png24":".png","image/png32":".png","image/jpg":".jpg","image/jpeg":".jpg","image/gif":".gif","image/bmp":".bmp","image/tiff":".tif"};},setActiveLayer:function(_3){this.layerInfo=_3;this._identifier=_3.identifier;this._tileMatrixSetId=_3.tileMatrixSet;if(_3.format){this.format="image/"+_3.format;}this._style=_3.style;this.title=_3.title;this._parseResourceInfo();this.refresh(true);},getTileUrl:function(_4,_5,_6){var _7;if(this.serviceMode==="KVP"){_7=this._url+"SERVICE=WMTS&VERSION="+this.version+"&REQUEST=GetTile"+"&LAYER="+this._identifier+"&STYLE="+this._style+"&FORMAT="+this.format+"&TILEMATRIXSET="+this._tileMatrixSetId+"&TILEMATRIX="+_4+"&TILEROW="+_5+"&TILECOL="+_6;}else{if(this.serviceMode==="RESTful"){var _8;if(this._formatDictionary[this.format]){_8=this._formatDictionary[this.format];}_7=this._url+this._identifier+"/"+this._style+"/"+this._tileMatrixSetId+"/"+_4+"/"+_5+"/"+_6+_8;}}return _7;},_parseResourceInfo:function(){var _9=this.layerInfos;if(this.serviceMode==="KVP"){this._url+=(this._url.substring(this._url.length-1,this._url.length)=="?")?"":"?";}for(var i=0;i<_9.length;i++){if((!this._identifier||_9[i].identifier===this._identifier)&&(!this.title||_9[i].title===this.title)&&(!this._tileMatrixSetId||_9[i].tileMatrixSet===this._tileMatrixSetId)&&(!this.format||"image/"+_9[i].format===this.format)&&(!this._style||_9[i].style===this._style)){dojo.mixin(this,{"description":_9[i].description,tileInfo:_9[i].tileInfo,spatialReference:_9[i].tileInfo.spatialReference,fullExtent:_9[i].fullExtent,initialExtent:_9[i].initialExtent,_identifier:_9[i].identifier,_tileMatrixSetId:_9[i].tileMatrixSet,format:"image/"+_9[i].format,_style:_9[i].style});break;}}},_getCapabilities:function(){var _a;if(this.serviceMode==="KVP"){_a=this._url+"?request=GetCapabilities&service=WMTS&version="+this.version;}else{if(this.serviceMode==="RESTful"){_a=this._url+"/"+this.version+"/WMTSCapabilities.xml";}}esri.request({url:_a,handleAs:"text",load:this._parseCapabilities,error:this._getCapabilitiesError});},_parseCapabilities:function(_b){_b=_b.replace(/ows:/gi,"");var _c=dojox.xml.parser.parse(_b);var _d=dojo.query("OperationsMetadata",_c)[0];var _e=dojo.query("[name='GetTile']",_d)[0];var _f=this.tileUrl;if(this._getAttributeValue("Get","xlink:href",_e)){_f=this._getAttributeValue("Get","xlink:href",_e);}if(_f.indexOf("/1.0.0/")===-1&&this.serviceMode==="RESTful"){_f+="/1.0.0/";}if(this.serviceMode==="KVP"){_f+=(_f.substring(_f.length-1,_f.length)=="?")?"":"?";}this._url=_f;var _10=dojo.query("Contents",_c)[0];var _11,_12,_13,_14,lod,_15=[];if(!this._identifier){this._identifier=this._getTagValues("Capabilities>Contents>Layer>Identifier",_c)[0];}this.copyright=this._getTagValues("Capabilities>ServiceIdentification>AccessConstraints",_c)[0];var _16=this._getTagWithChildTagValue("Layer","Identifier",this._identifier,_10);this.description=this._getTagValues("Abstract",_16)[0];this.title=this._getTagValues("Title",_16)[0];if(!this._style){var _17=dojo.query("[isDefault='true']",_16)[0];if(_17){this._style=this._getTagValues("Identifier",_17)[0];}this._style=this._getTagValues("Identifier",dojo.query("Style",_16)[0])[0];}var _18=this._getTagValues("Format",_16);if(!this.format){this.format=_18[0];}if(dojo.indexOf(_18,this.format)===-1){console.error("The format "+this.format+" is not supported by the service");}var _19=this._getTagValues("TileMatrixSet",_16);if(!this._tileMatrixSetId){if(dojo.indexOf(_19,"GoogleMapsCompatible")!==-1){this._tileMatrixSetId="GoogleMapsCompatible";}else{this._tileMatrixSetId=_19[0];}}var _1a=this._getTagWithChildTagValue("TileMatrixSetLink","TileMatrixSet",this._tileMatrixSetId,_16);var _1b=this._getTagValues("TileMatrix",_1a);var _1c=this._getTagWithChildTagValue("TileMatrixSet","Identifier",this._tileMatrixSetId,_10);var crs=this._getTagValues("SupportedCRS",_1c)[0];_14=crs.split(":").pop();if(_14==900913){_14=3857;}this.spatialReference=new esri.SpatialReference({"wkid":_14});var _1d=dojo.query("TileMatrix",_1c)[0];_11=parseInt(this._getTagValues("TileWidth",_1d)[0],10);_12=parseInt(this._getTagValues("TileHeight",_1d)[0],10);var _1e=this._getTagValues("TopLeftCorner",_1d)[0].split(" ");var top=_1e[0],_1f=_1e[1];if(top.split("E").length>1){var _20=top.split("E");top=_20[0]*Math.pow(10,_20[1]);}if(_1f.split("E").length>1){var _21=_1f.split("E");_1f=_21[0]*Math.pow(10,_21[1]);}_13={"x":parseInt(top,10),"y":parseInt(_1f,10)};if(_14==3857||_14==102113||_14==102100){_13={"x":-20037508.342787,"y":20037508.342787};}else{if(_14==4326){_13={"x":-180,"y":90};}}var _22=this._getTagValues("MatrixWidth",_1d)[0];var _23=this._getTagValues("MatrixHeight",_1d)[0];if(_1b.length===0){var _24=dojo.query("TileMatrix",_1c);for(var j=0;j<_24.length;j++){lod=this._getLodFromTileMatrix(_24[j],_14);_15.push(lod);}}else{for(var i=0;i<_1b.length;i++){var _25=this._getTagWithChildTagValue("TileMatrix","Identifier",_1b[i],_1c);lod=this._getLodFromTileMatrix(_25,_14);_15.push(lod);}}var _26=_13.x;var _27=_13.y;var _28=_22>_23?_22:_23;var _29=_22>_23?_23:_22;var _2a=_26+_28*_12*_15[0].resolution;var _2b=_27-_29*_11*_15[0].resolution;var _2c=new esri.geometry.Extent(_26,_2b,_2a,_27);this.fullExtent=this.initialExtent=_2c;this.tileInfo=new esri.layers.TileInfo({"dpi":90.71428571428571});dojo.mixin(this.tileInfo,{"spatialReference":this.spatialReference},{"format":this.format},{"height":_11},{"width":_12},{"origin":_13},{"lods":_15});this.loaded=true;this.onLoad(this);},_getCapabilitiesError:function(err){console.error("Failed to get capabilities xml");},_getLodFromTileMatrix:function(_2d,_2e){var id=this._getTagValues("Identifier",_2d)[0];var _2f=this._getTagValues("ScaleDenominator",_2d)[0];if(_2f.split("E").length>1){var _30=_2f.split("E");_2f=_30[0]*Math.pow(10,_30[1]);}else{_2f=parseFloat(_2f);}var _31;if(esri._isDefined(esri.WKIDUnitConversion[_2e])){_31=esri.WKIDUnitConversion.values[esri.WKIDUnitConversion[_2e]];}else{_31=111194.6519066546;}var _32=_2f*7/25000/_31;var lod={"level":id,"scale":_2f,"resolution":_32};return lod;},_getTag:function(_33,xml){var _34=dojo.query(_33,xml);if(_34&&_34.length>0){return _34[0];}else{return null;}},_getTagValues:function(_35,xml){var _36=[];var _37=_35.split(">");var tag,_38;tag=dojo.query(_37[0],xml)[0];if(_37.length>1){for(var i=1;i<_37.length-1;i++){tag=dojo.query(_37[i],tag)[0];}_38=dojo.query(_37[_37.length-1],tag);}else{_38=dojo.query(_37[0],xml);}if(_38&&_38.length>0){dojo.forEach(_38,function(_39){if(dojo.isIE){_36.push(_39.childNodes[0].nodeValue);}else{_36.push(_39.textContent);}});}return _36;},_getAttributeValue:function(_3a,_3b,xml,_3c){var _3d=dojo.query(_3a,xml);if(_3d&&_3d.length>0){return _3d[0].getAttribute(_3b);}else{return _3c;}},_getTagWithChildTagValue:function(_3e,_3f,_40,xml){var _41=xml.childNodes;var _42;for(var j=0;j<_41.length;j++){if(_41[j].nodeName===_3e){if(dojo.isIE){if(esri._isDefined(dojo.query(_3f,_41[j])[0])){_42=dojo.query(_3f,_41[j])[0].childNodes[0].nodeValue;}}else{if(esri._isDefined(dojo.query(_3f,_41[j])[0])){_42=dojo.query(_3f,_41[j])[0].textContent;}}if(_42===_40){return _41[j];}}}}});dojo.declare("esri.layers.WMTSLayerInfo",null,{identifier:null,tileMatrixSet:null,format:null,style:null,tileInfo:null,title:null,fullExtent:null,initialExtent:null,description:null,constructor:function(_43){if(_43){this.title=_43.title;this.tileMatrixSet=_43.tileMatrixSet;this.format=_43.format;this.style=_43.style;this.tileInfo=_43.tileInfo;this.fullExtent=_43.fullExtent;this.initialExtent=_43.initialExtent;this.identifier=_43.identifier;this.description=_43.description;}}});}