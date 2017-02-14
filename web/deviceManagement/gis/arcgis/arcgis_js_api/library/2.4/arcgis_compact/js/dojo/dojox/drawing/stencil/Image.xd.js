/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.drawing.stencil.Image"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.drawing.stencil.Image"]){_4._hasResource["dojox.drawing.stencil.Image"]=true;_4.provide("dojox.drawing.stencil.Image");_6.drawing.stencil.Image=_6.drawing.util.oo.declare(_6.drawing.stencil._Base,function(_7){},{type:"dojox.drawing.stencil.Image",anchorType:"group",baseRender:true,dataToPoints:function(o){o=o||this.data;this.points=[{x:o.x,y:o.y},{x:o.x+o.width,y:o.y},{x:o.x+o.width,y:o.y+o.height},{x:o.x,y:o.y+o.height}];return this.points;},pointsToData:function(p){p=p||this.points;var s=p[0];var e=p[2];this.data={x:s.x,y:s.y,width:e.x-s.x,height:e.y-s.y,src:this.src||this.data.src};return this.data;},_createHilite:function(){this.remove(this.hit);this.hit=this.container.createRect(this.data).setStroke(this.style.current).setFill(this.style.current.fill);this._setNodeAtts(this.hit);},_create:function(_8,d,_9){this.remove(this[_8]);var s=this.container.getParent();this[_8]=s.createImage(d);this.container.add(this[_8]);this._setNodeAtts(this[_8]);},render:function(_a){if(this.data.width=="auto"||isNaN(this.data.width)){this.getImageSize(true);console.warn("Image size not provided. Acquiring...");return;}this.onBeforeRender(this);this.renderHit&&this._createHilite();this._create("shape",this.data,this.style.current);},getImageSize:function(_b){if(this._gettingSize){return;}this._gettingSize=true;var _c=_4.create("img",{src:this.data.src},_4.body());var _d=_4.connect(_c,"error",this,function(){_4.disconnect(c);_4.disconnect(_d);console.error("Error loading image:",this.data.src);console.warn("Error image:",this.data);});var c=_4.connect(_c,"load",this,function(){var _e=_4.marginBox(_c);this.setData({x:this.data.x,y:this.data.y,src:this.data.src,width:_e.w,height:_e.h});_4.disconnect(c);_4.destroy(_c);_b&&this.render(true);});}});_6.drawing.register({name:"dojox.drawing.stencil.Image"},"stencil");}}};});