/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.charting.DataChart"],["require","dojox.charting.Chart2D"],["require","dojox.charting.themes.PlotKit.blue"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.charting.DataChart"]){_4._hasResource["dojox.charting.DataChart"]=true;_4.provide("dojox.charting.DataChart");_4.require("dojox.charting.Chart2D");_4.require("dojox.charting.themes.PlotKit.blue");_4.experimental("dojox.charting.DataChart");(function(){var _7={vertical:true,min:0,max:10,majorTickStep:5,minorTickStep:1,natural:false,stroke:"black",majorTick:{stroke:"black",length:8},minorTick:{stroke:"gray",length:2},majorLabels:true};var _8={natural:true,majorLabels:true,includeZero:false,majorTickStep:1,majorTick:{stroke:"black",length:8},fixUpper:"major",stroke:"black",htmlLabels:true,from:1};var _9={markers:true,tension:2,gap:2};_4.declare("dojox.charting.DataChart",[_6.charting.Chart2D],{scroll:true,comparative:false,query:"*",queryOptions:"",fieldName:"value",chartTheme:_6.charting.themes.PlotKit.blue,displayRange:0,stretchToFit:true,minWidth:200,minHeight:100,showing:true,label:"name",constructor:function(_a,_b){this.domNode=_4.byId(_a);_4.mixin(this,_b);this.xaxis=_4.mixin(_4.mixin({},_8),_b.xaxis);if(this.xaxis.labelFunc=="seriesLabels"){this.xaxis.labelFunc=_4.hitch(this,"seriesLabels");}this.yaxis=_4.mixin(_4.mixin({},_7),_b.yaxis);if(this.yaxis.labelFunc=="seriesLabels"){this.yaxis.labelFunc=_4.hitch(this,"seriesLabels");}this._events=[];this.convertLabels(this.yaxis);this.convertLabels(this.xaxis);this.onSetItems={};this.onSetInterval=0;this.dataLength=0;this.seriesData={};this.seriesDataBk={};this.firstRun=true;this.dataOffset=0;this.chartTheme.plotarea.stroke={color:"gray",width:3};this.setTheme(this.chartTheme);if(this.displayRange){this.stretchToFit=false;}if(!this.stretchToFit){this.xaxis.to=this.displayRange;}this.addAxis("x",this.xaxis);this.addAxis("y",this.yaxis);_9.type=_b.type||"Markers";this.addPlot("default",_4.mixin(_9,_b.chartPlot));this.addPlot("grid",_4.mixin(_b.grid||{},{type:"Grid",hMinorLines:true}));if(this.showing){this.render();}if(_b.store){this.setStore(_b.store,_b.query,_b.fieldName,_b.queryOptions);}},destroy:function(){_4.forEach(this._events,_4.disconnect);this.inherited(arguments);},setStore:function(_c,_d,_e,_f){this.firstRun=true;this.store=_c||this.store;this.query=_d||this.query;this.fieldName=_e||this.fieldName;this.label=this.store.getLabelAttributes();this.queryOptions=_f||_f;_4.forEach(this._events,_4.disconnect);this._events=[_4.connect(this.store,"onSet",this,"onSet"),_4.connect(this.store,"onError",this,"onError")];this.fetch();},show:function(){if(!this.showing){_4.style(this.domNode,"display","");this.showing=true;this.render();}},hide:function(){if(this.showing){_4.style(this.domNode,"display","none");this.showing=false;}},onSet:function(_10){var nm=this.getProperty(_10,this.label);if(nm in this.runs||this.comparative){clearTimeout(this.onSetInterval);if(!this.onSetItems[nm]){this.onSetItems[nm]=_10;}this.onSetInterval=setTimeout(_4.hitch(this,function(){clearTimeout(this.onSetInterval);var _11=[];for(var nm in this.onSetItems){_11.push(this.onSetItems[nm]);}this.onData(_11);this.onSetItems={};}),200);}},onError:function(err){console.error("DataChart Error:",err);},onDataReceived:function(_12){},getProperty:function(_13,_14){if(_14==this.label){return this.store.getLabel(_13);}if(_14=="id"){return this.store.getIdentity(_13);}var _15=this.store.getValues(_13,_14);if(_15.length<2){_15=this.store.getValue(_13,_14);}return _15;},onData:function(_16){if(!_16||!_16.length){return;}if(this.items&&this.items.length!=_16.length){_4.forEach(_16,function(m){var id=this.getProperty(m,"id");_4.forEach(this.items,function(m2,i){if(this.getProperty(m2,"id")==id){this.items[i]=m2;}},this);},this);_16=this.items;}if(this.stretchToFit){this.displayRange=_16.length;}this.onDataReceived(_16);this.items=_16;if(this.comparative){var nm="default";this.seriesData[nm]=[];this.seriesDataBk[nm]=[];_4.forEach(_16,function(m,i){var _17=this.getProperty(m,this.fieldName);this.seriesData[nm].push(_17);},this);}else{_4.forEach(_16,function(m,i){var nm=this.store.getLabel(m);if(!this.seriesData[nm]){this.seriesData[nm]=[];this.seriesDataBk[nm]=[];}var _18=this.getProperty(m,this.fieldName);if(_4.isArray(_18)){this.seriesData[nm]=_18;}else{if(!this.scroll){var ar=_4.map(new Array(i+1),function(){return 0;});ar.push(Number(_18));this.seriesData[nm]=ar;}else{if(this.seriesDataBk[nm].length>this.seriesData[nm].length){this.seriesData[nm]=this.seriesDataBk[nm];}this.seriesData[nm].push(Number(_18));}this.seriesDataBk[nm].push(Number(_18));}},this);}var _19;if(this.firstRun){this.firstRun=false;for(nm in this.seriesData){this.addSeries(nm,this.seriesData[nm]);_19=this.seriesData[nm];}}else{for(nm in this.seriesData){_19=this.seriesData[nm];if(this.scroll&&_19.length>this.displayRange){this.dataOffset=_19.length-this.displayRange-1;_19=_19.slice(_19.length-this.displayRange,_19.length);}this.updateSeries(nm,_19);}}this.dataLength=_19.length;if(this.showing){this.render();}},fetch:function(){if(!this.store){return;}this.store.fetch({query:this.query,queryOptions:this.queryOptions,start:this.start,count:this.count,sort:this.sort,onComplete:_4.hitch(this,function(_1a){setTimeout(_4.hitch(this,function(){this.onData(_1a);}),0);}),onError:_4.hitch(this,"onError")});},convertLabels:function(_1b){if(!_1b.labels||_4.isObject(_1b.labels[0])){return null;}_1b.labels=_4.map(_1b.labels,function(ele,i){return {value:i,text:ele};});return null;},seriesLabels:function(val){val--;if(this.series.length<1||(!this.comparative&&val>this.series.length)){return "-";}if(this.comparative){return this.store.getLabel(this.items[val]);}else{for(var i=0;i<this.series.length;i++){if(this.series[i].data[val]>0){return this.series[i].name;}}}return "-";},resizeChart:function(dim){var w=Math.max(dim.w,this.minWidth);var h=Math.max(dim.h,this.minHeight);this.resize(w,h);}});})();}}};});