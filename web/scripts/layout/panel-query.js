eoms.panel.Query=function(b,a){a=Ext.apply({dataUrl:eoms.appPath+"/sysuser/tawSystemUsers.do?method=xquery&tpl=view-query",qparam:"q",tips:"\u67e5\u8be2\u4eba\u5458\u540d\u79f0...",title:a.text||"\u67e5\u8be2\u4eba\u5458",adjustments:[0,-30],fitToFrame:true,fitContainer:true,autoScroll:true,autoCreate:true,preInput:"",timer:false,cache:[],cacheSize:0,maxCacheSize:100,datatype:"user"},a);if(/^\//.test(a.dataUrl)&&a.dataUrl.indexOf(eoms.appPath)!=0){a.dataUrl=eoms.appPath+a.dataUrl}eoms.panel.Query.superclass.constructor.call(this,b,a);this.addEvents({dblclick:true});this.el.dom.style.overflow="hidden";var c=this.el.createChild({tag:"input",type:"text",maxlength:"30",autocomplete:"off",cls:"text",style:"width:237px;height:26px;margin:5px;padding-top:4px;padding-left:4px;"});c.dom.value=c.dom.title=this.tips;c.on({keyup:this.processKey,keydown:function(e,d){if(e.getKey()==e.ENTER){e.stopEvent()}},focus:function(e,d){if(d.value==d.title){d.value=""}},blur:function(e,d){if(d.value==""){d.value=d.title}},scope:this});this.qEl=c;this.resizeEl=this.el.createChild({cls:"viewer-list",style:"padding-left:5px;overflow-y:auto"});this.view=new Ext.JsonView(this.resizeEl,this.getViewTpl(),{multiSelect:true,emptyText:"<div>\u6ca1\u6709\u627e\u5230\u67e5\u8be2\u7ed3\u679c</div>"});this.updater=this.view.el.getUpdateManager();this.view.on({dblclick:this.onDblClick,load:function(e,f,d){if(f&&f.length){this.addToCache(e.q,f,f.length)}},scope:this})};Ext.extend(eoms.panel.Query,Ext.ContentPanel,{getViewTpl:function(){return'<div class="viewlistitem-'+this.datatype+'" unselectable="on">{0}</div>'},getSelection:function(){var b=this.view.getSelectedNodes();var e=[],f;for(var c=0,a=b.length;c<a;c++){f=this.view.getNodeData(b[c]);e.push(this.getData(f))}return e},clearSelections:function(){this.view.clearSelections()},getData:function(a){return{id:a[1],text:a[0],nodeType:this.datatype,leaf:1}},onDblClick:function(c,a,b,f){c.select(b);var d=c.getNodeData(b);this.fireEvent("dblclick")},processKey:function(b,a){if(b.isNavKeyPress()){b.stopEvent();switch(b.getKey()){case b.ENTER:this.start();break}}else{if(a.value!=this.preInput){this.updater.abort();if(this.timer){clearTimeout(this.timer)}this.timer=this.start.defer(200,this);this.preInput=a.value}}},start:function(){var b=this.qEl.dom.value.trim();if(b==""){this.view.jsonData=[];this.view.refresh();return}b=encodeURI(b);this.view.q=b;var a=this.checkCache(b);if(a){this.view.jsonData=a.data;this.view.refresh();return}this.view.load({url:this.dataUrl,params:this.qparam+"="+b,text:"\u641c\u7d22\u4e2d..."})},checkCache:function(c){for(var b=0,a=this.cache.length;b<a;b++){if(this.cache[b]["q"]==c){this.cache.unshift(this.cache.splice(b,1)[0]);return this.cache[0]}}return false},addToCache:function(d,c,a){while(this.cache.length&&(this.cacheSize+a>this.maxCacheSize)){var b=this.cache.pop();this.cacheSize-=b.size}this.cache.push({q:d,size:a,data:c});this.cacheSize+=a}});eoms.panel.query=eoms.panel.Query;
