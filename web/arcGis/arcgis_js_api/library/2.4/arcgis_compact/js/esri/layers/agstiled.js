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

if(!dojo._hasResource["esri.layers.agstiled"]){dojo._hasResource["esri.layers.agstiled"]=true;dojo.provide("esri.layers.agstiled");dojo.require("esri.layers.tiled");dojo.require("esri.layers.agscommon");dojo.declare("esri.layers.ArcGISTiledMapServiceLayer",[esri.layers.TiledMapServiceLayer,esri.layers.ArcGISMapServiceLayer],{constructor:function(_1,_2){if(_2){if(_2.roundrobin){dojo.deprecated(this.declaredClass+" : "+esri.bundle.layers.agstiled.deprecateRoundrobin);_2.tileServers=_2.roundrobin;}this._setTileServers(_2.tileServers);this._loadCallback=_2.loadCallback;}this._params=dojo.mixin({},this._url.query);this.tsi=0;this._initLayer=dojo.hitch(this,this._initLayer);var _3=_2&&_2.resourceInfo;if(_3){this._initLayer(_3);}else{this._load=dojo.hitch(this,this._load);this._load();}},_TILE_FORMATS:{PNG:"png",PNG8:"png",PNG24:"png",PNG32:"png",JPG:"jpg",JPEG:"jpg",GIF:"gif"},_setTileServers:function(_4){this.tileServers=_4;if(_4&&_4.length>0){for(var i=0,il=_4.length;i<il;i++){_4[i]=esri.urlToObject(_4[i]).path;}}},_initLayer:function(_5,io){this.inherited(arguments);this.resourceInfo=dojo.toJson(_5);this.tileInfo=new esri.layers.TileInfo(_5.tileInfo);this.isPNG32=this.tileInfo.format==="PNG24"||this.tileInfo.format==="PNG32";if(_5.timeInfo){this.timeInfo=new esri.layers.TimeInfo(_5.timeInfo);}if(!this.tileServers){this._setTileServers(_5.tileServers);}this.loaded=true;this.onLoad(this);var _6=this._loadCallback;if(_6){delete this._loadCallback;_6(this);}},getTileUrl:function(_7,_8,_9){var ts=this.tileServers,_a=(ts?ts[this.tsi++%ts.length]:this._url.path)+"/tile/"+_7+"/"+_8+"/"+_9;if(this._url.query){_a+=("?"+dojo.objectToQuery(this._url.query));}return esri._getProxiedUrl(_a);}});}