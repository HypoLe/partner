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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.layers.agstiled"],["require","esri.layers.tiled"],["require","esri.layers.agscommon"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.layers.agstiled"]){_4._hasResource["esri.layers.agstiled"]=true;_4.provide("esri.layers.agstiled");_4.require("esri.layers.tiled");_4.require("esri.layers.agscommon");_4.declare("esri.layers.ArcGISTiledMapServiceLayer",[esri.layers.TiledMapServiceLayer,esri.layers.ArcGISMapServiceLayer],{constructor:function(_7,_8){if(_8){if(_8.roundrobin){_4.deprecated(this.declaredClass+" : "+esri.bundle.layers.agstiled.deprecateRoundrobin);_8.tileServers=_8.roundrobin;}this._setTileServers(_8.tileServers);this._loadCallback=_8.loadCallback;}this._params=_4.mixin({},this._url.query);this.tsi=0;this._initLayer=_4.hitch(this,this._initLayer);var _9=_8&&_8.resourceInfo;if(_9){this._initLayer(_9);}else{this._load=_4.hitch(this,this._load);this._load();}},_TILE_FORMATS:{PNG:"png",PNG8:"png",PNG24:"png",PNG32:"png",JPG:"jpg",JPEG:"jpg",GIF:"gif"},_setTileServers:function(_a){this.tileServers=_a;if(_a&&_a.length>0){for(var i=0,il=_a.length;i<il;i++){_a[i]=esri.urlToObject(_a[i]).path;}}},_initLayer:function(_b,io){this.inherited(arguments);this.resourceInfo=_4.toJson(_b);this.tileInfo=new esri.layers.TileInfo(_b.tileInfo);this.isPNG32=this.tileInfo.format==="PNG24"||this.tileInfo.format==="PNG32";if(_b.timeInfo){this.timeInfo=new esri.layers.TimeInfo(_b.timeInfo);}if(!this.tileServers){this._setTileServers(_b.tileServers);}this.loaded=true;this.onLoad(this);var _c=this._loadCallback;if(_c){delete this._loadCallback;_c(this);}},getTileUrl:function(_d,_e,_f){var ts=this.tileServers,_10=(ts?ts[this.tsi++%ts.length]:this._url.path)+"/tile/"+_d+"/"+_e+"/"+_f;if(this._url.query){_10+=("?"+_4.objectToQuery(this._url.query));}return esri._getProxiedUrl(_10);}});}}};});