/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.drawing.plugins.tools.Iconize"],["require","dojox.drawing.plugins._Plugin"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.drawing.plugins.tools.Iconize"]){_4._hasResource["dojox.drawing.plugins.tools.Iconize"]=true;_4.provide("dojox.drawing.plugins.tools.Iconize");_4.require("dojox.drawing.plugins._Plugin");_6.drawing.plugins.tools.Iconize=_6.drawing.util.oo.declare(_6.drawing.plugins._Plugin,function(_7){},{onClick:function(){var _8;for(var nm in this.stencils.stencils){console.log(" stanceil item:",this.stencils.stencils[nm].id,this.stencils.stencils[nm]);if(this.stencils.stencils[nm].shortType=="path"){_8=this.stencils.stencils[nm];break;}}if(_8){console.log("click Iconize plugin",_8.points);this.makeIcon(_8.points);}},makeIcon:function(p){var _9=function(n){return Number(n.toFixed(1));};var x=10000;var y=10000;p.forEach(function(pt){if(pt.x!==undefined&&!isNaN(pt.x)){x=Math.min(x,pt.x);y=Math.min(y,pt.y);}});var _a=0;var _b=0;p.forEach(function(pt){if(pt.x!==undefined&&!isNaN(pt.x)){pt.x=_9(pt.x-x);pt.y=_9(pt.y-y);_a=Math.max(_a,pt.x);_b=Math.max(_b,pt.y);}});console.log("xmax:",_a,"ymax:",_b);var s=60;var m=20;p.forEach(function(pt){pt.x=_9(pt.x/_a)*s+m;pt.y=_9(pt.y/_b)*s+m;});var _c="[\n";_4.forEach(p,function(pt,i){_c+="{\t";if(pt.t){_c+="t:'"+pt.t+"'";}if(pt.x!==undefined&&!isNaN(pt.x)){if(pt.t){_c+=", ";}_c+="x:"+pt.x+",\t\ty:"+pt.y;}_c+="\t}";if(i!=p.length-1){_c+=",";}_c+="\n";});_c+="]";console.log(_c);var n=_4.byId("data");if(n){n.value=_c;}}});_6.drawing.plugins.tools.Iconize.setup={name:"dojox.drawing.plugins.tools.Iconize",tooltip:"Iconize Tool",iconClass:"iconPan"};_6.drawing.register(_6.drawing.plugins.tools.Iconize.setup,"plugin");}}};});