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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.layers.osm"],["require","esri.layers.tiled"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.layers.osm"]){_4._hasResource["esri.layers.osm"]=true;_4.provide("esri.layers.osm");_4.require("esri.layers.tiled");_4.declare("esri.layers.OpenStreetMapLayer",esri.layers.TiledMapServiceLayer,{constructor:function(_7){this.spatialReference=new esri.SpatialReference({wkid:102100});this.tileInfo=new esri.layers.TileInfo({rows:256,cols:256,dpi:96,format:"PNG8",compressionQuality:0,origin:{x:-20037508.342787,y:20037508.342787},spatialReference:{wkid:102100},lods:[{level:0,scale:591657527.591555,resolution:156543.033928},{level:1,scale:295828763.795777,resolution:78271.5169639999},{level:2,scale:147914381.897889,resolution:39135.7584820001},{level:3,scale:73957190.948944,resolution:19567.8792409999},{level:4,scale:36978595.474472,resolution:9783.93962049996},{level:5,scale:18489297.737236,resolution:4891.96981024998},{level:6,scale:9244648.868618,resolution:2445.98490512499},{level:7,scale:4622324.434309,resolution:1222.99245256249},{level:8,scale:2311162.217155,resolution:611.49622628138},{level:9,scale:1155581.108577,resolution:305.748113140558},{level:10,scale:577790.554289,resolution:152.874056570411},{level:11,scale:288895.277144,resolution:76.4370282850732},{level:12,scale:144447.638572,resolution:38.2185141425366},{level:13,scale:72223.819286,resolution:19.1092570712683},{level:14,scale:36111.909643,resolution:9.55462853563415},{level:15,scale:18055.954822,resolution:4.77731426794937},{level:16,scale:9027.977411,resolution:2.38865713397468},{level:17,scale:4513.988705,resolution:1.19432856685505},{level:18,scale:2256.994353,resolution:0.597164283559817}]});this.fullExtent=new esri.geometry.Extent({xmin:-20037508.34,ymin:-20037508.34,xmax:20037508.34,ymax:20037508.34,spatialReference:{wkid:102100}});this.initialExtent=new esri.geometry.Extent({xmin:-20037508.34,ymin:-20037508.34,xmax:20037508.34,ymax:20037508.34,spatialReference:{wkid:102100}});this.tileServers=(_7&&_7.tileServers)||["http://a.tile.openstreetmap.org/","http://b.tile.openstreetmap.org/","http://c.tile.openstreetmap.org/"];this.serversLength=this.tileServers.length;this.serverIndex=0;this._displayLevels=_7?_7.displayLevels:null;this.loaded=true;this.onLoad(this);var _8=_7&&_7.loadCallback;if(_8){_8(this);}},getTileUrl:function(_9,_a,_b){return esri._getProxiedUrl(this.tileServers[this.serverIndex++%this.serversLength]+"/"+_9+"/"+_b+"/"+_a+".png");}});}}};});