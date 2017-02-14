/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.charting.widget.Legend"],["require","dijit._Widget"],["require","dijit._Templated"],["require","dojox.lang.functional.array"],["require","dojox.lang.functional.fold"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.charting.widget.Legend"]){_4._hasResource["dojox.charting.widget.Legend"]=true;_4.provide("dojox.charting.widget.Legend");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.require("dojox.lang.functional.array");_4.require("dojox.lang.functional.fold");_4.declare("dojox.charting.widget.Legend",[_5._Widget,_5._Templated],{chartRef:"",horizontal:true,swatchSize:18,templateString:"<table dojoAttachPoint='legendNode' class='dojoxLegendNode' role='group' aria-label='chart legend'><tbody dojoAttachPoint='legendBody'></tbody></table>",legendNode:null,legendBody:null,postCreate:function(){if(!this.chart){if(!this.chartRef){return;}this.chart=_5.byId(this.chartRef);if(!this.chart){var _7=_4.byId(this.chartRef);if(_7){this.chart=_5.byNode(_7);}else{console.log("Could not find chart instance with id: "+this.chartRef);return;}}this.series=this.chart.chart.series;}else{this.series=this.chart.series;}this.refresh();},refresh:function(){var df=_6.lang.functional;if(this._surfaces){_4.forEach(this._surfaces,function(_8){_8.destroy();});}this._surfaces=[];while(this.legendBody.lastChild){_4.destroy(this.legendBody.lastChild);}if(this.horizontal){_4.addClass(this.legendNode,"dojoxLegendHorizontal");this._tr=_4.create("tr",null,this.legendBody);this._inrow=0;}var s=this.series;if(s.length==0){return;}if(s[0].chart.stack[0].declaredClass=="dojox.charting.plot2d.Pie"){var t=s[0].chart.stack[0];if(typeof t.run.data[0]=="number"){var _9=df.map(t.run.data,"Math.max(x, 0)");if(df.every(_9,"<= 0")){return;}var _a=df.map(_9,"/this",df.foldl(_9,"+",0));_4.forEach(_a,function(x,i){this._addLabel(t.dyn[i],t._getLabel(x*100)+"%");},this);}else{_4.forEach(t.run.data,function(x,i){this._addLabel(t.dyn[i],x.legend||x.text||x.y);},this);}}else{_4.forEach(s,function(x){this._addLabel(x.dyn,x.legend||x.name);},this);}},_addLabel:function(_b,_c){var _d=_4.create("td"),_e=_4.create("div",null,_d),_f=_4.create("label",null,_d),div=_4.create("div",{style:{"width":this.swatchSize+"px","height":this.swatchSize+"px","float":"left"}},_e);_4.addClass(_e,"dojoxLegendIcon dijitInline");_4.addClass(_f,"dojoxLegendText");if(this._tr){this._tr.appendChild(_d);if(++this._inrow===this.horizontal){this._tr=_4.create("tr",null,this.legendBody);this._inrow=0;}}else{var tr=_4.create("tr",null,this.legendBody);tr.appendChild(_d);}this._makeIcon(div,_b);_f.innerHTML=String(_c);},_makeIcon:function(div,dyn){var mb={h:this.swatchSize,w:this.swatchSize};var _10=_6.gfx.createSurface(div,mb.w,mb.h);this._surfaces.push(_10);if(dyn.fill){_10.createRect({x:2,y:2,width:mb.w-4,height:mb.h-4}).setFill(dyn.fill).setStroke(dyn.stroke);}else{if(dyn.stroke||dyn.marker){var _11={x1:0,y1:mb.h/2,x2:mb.w,y2:mb.h/2};if(dyn.stroke){_10.createLine(_11).setStroke(dyn.stroke);}if(dyn.marker){var c={x:mb.w/2,y:mb.h/2};if(dyn.stroke){_10.createPath({path:"M"+c.x+" "+c.y+" "+dyn.marker}).setFill(dyn.stroke.color).setStroke(dyn.stroke);}else{_10.createPath({path:"M"+c.x+" "+c.y+" "+dyn.marker}).setFill(dyn.color).setStroke(dyn.color);}}}else{_10.createRect({x:2,y:2,width:mb.w-4,height:mb.h-4}).setStroke("black");_10.createLine({x1:2,y1:2,x2:mb.w-2,y2:mb.h-2}).setStroke("black");_10.createLine({x1:2,y1:mb.h-2,x2:mb.w-2,y2:2}).setStroke("black");}}}});}}};});