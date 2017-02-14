
var jq=jQuery.noConflict();

Ext.onReady(function(){
	var bodyLayout = new Ext.BorderLayout(document.body, {
                    west: {
                        split:true,
                        initialSize: 420,
                        titlebar: true,
                        collapsible: true,
                        minSize: 100,
                        maxSize: 450,
		           		 margins:{left:0,right:0,bottom:5,top:0}
                    },
                    center: {
                        autoScroll: false,tabPosition:'top',
                        titlebar: false, alwaysShowTabs: true,
		           		 margins:{left:0,right:2,bottom:5,top:0}
                    },
                    north: {
		            initialSize: 40, titlebar: false
		        }
                });
    bodyLayout.beginUpdate();
    	
		var leftLayout = new Ext.BorderLayout('left-Panel', {
		                    north: {
		                        split:false,
		                        initialSize: 30,
		                        minSize: 20,
		                        maxSize: 80,
		                        autoScroll:false,
		                        collapsible:false,
		                        titlebar: false
		                    },
		                    center: {
		                        autoScroll:true
		                    }
		                });
		                
		leftLayout.beginUpdate();
			 var tb = new Ext.Toolbar("left-panel-toolbar");
			 /*tb.add(
			 	{
			 		text: "巡检点列表",
			 		icon: config.icon1,
			 		cls: 'x-btn-text-icon',
			 		enableToggle: true,
			 		toggleHandler: showCheckPointList,
			 		pressed: true
			 	}
			 );
			 tb.addSeparator();
			 tb.add(
			 	{
			 		text: "巡检段列表",
			 		icon: config.icon1,
			 		cls: 'x-btn-text-icon',
			 		enableToggle: true,
			 		toggleHandler: showCheckSegmentList,
			 		pressed: true
			 	}
			 );
			 tb.addSeparator();*/
			 tb.add(
			 	{
			 		text: "网格地图",
			 		icon: config.icon1,
			 		cls: 'x-btn-text-icon',
			 		enableToggle: true,
			 		toggleHandler: showGridList,
			 		pressed: true
			 	}
			 );
			 tb.addSeparator();
			 tb.add(
			 	{
			 		text: "基站地图",
			 		icon: config.icon1,
			 		cls: 'x-btn-text-icon',
			 		enableToggle: true,
			 		toggleHandler: showSiteList,
			 		pressed: true
			 	}
			 );
			 
			 tb.addSeparator();
			 tb.add(
			 	{
			 		text: "隐患地图",
			 		icon: config.icon1,
			 		cls: 'x-btn-text-icon',
			 		enableToggle: true,
			 		toggleHandler: showHiddenTroubleList,
			 		pressed: true
			 	}
			 );
			 
			 tb.addSeparator();
			 tb.add(
			 	{
			 		text: "历史地图",
			 		icon: config.icon1,
			 		cls: 'x-btn-text-icon',
			 		enableToggle: true,
			 		toggleHandler: showHistoryList,
			 		pressed: true
			 	}
			 );
			 tb.addSeparator();
			 tb.add(
			 	{
			 		text: "漏检地图",
			 		icon: config.icon1,
			 		cls: 'x-btn-text-icon',
			 		enableToggle: true,
			 		toggleHandler: showLeakList,
			 		pressed: true
			 	}
			 );
			 
			 
			 leftLayout.add('north', new Ext.ContentPanel('left-panel-up', {toolbar: tb}));
			 leftLayout.add('center', new Ext.ContentPanel('left-panel-body'));
		leftLayout.endUpdate(); 
		
		
		bodyLayout.add('north', new Ext.ContentPanel('headerPanel'));
		bodyLayout.add('west',new Ext.NestedLayoutPanel(leftLayout,{title:'资源导航'}));	
		
		bodyLayout.add('center', new Ext.ContentPanel('gisPanel', {
            	title:'地图信息',fitToFrame:true, autoScroll:true
            }));
	    bodyLayout.add('center', new Ext.ContentPanel('helpPanel', {
	      	title:'帮助',fitToFrame:true, autoScroll:true
	       }));
	    
	    var bodyCenterRegion = bodyLayout.getRegion('center');
	    var gisPanel = bodyCenterRegion.getPanel('gisPanel');
	    bodyCenterRegion.showPanel(gisPanel);
	                
	 bodyLayout.endUpdate();              
});

function showCheckSegmentList(item,pressed) {
	var leftBodyPageFrame = Ext.get(config.leftBodyPage).dom;
	var gisPageFrame = Ext.get(config.gisPage).dom;
		Ext.get("left-panel-body").mask('正在载入页面,请稍等......');
		Ext.get("gisPanel").mask('正在载入地图,请稍等......');
		leftBodyPageFrame.src = config.actionUrl+"?method="+"showCheckSegment";
		gisPageFrame.src = config.actionUrl+"?method="+"goToGisPage&type=checkSegment";
}

function showCheckPointList(item,pressed) {
	var leftBodyPageFrame = Ext.get(config.leftBodyPage).dom;
	var gisPageFrame = Ext.get(config.gisPage).dom;
		Ext.get("left-panel-body").mask('正在载入页面,请稍等......');
		Ext.get("gisPanel").mask('正在载入地图,请稍等......');
		leftBodyPageFrame.src = config.actionUrl+"?method="+"showCheckPoint";
		gisPageFrame.src = config.actionUrl+"?method="+"goToGisPage&type=checkPoint";
}

function showHiddenTroubleList(item,pressed) {
	var leftBodyPageFrame = Ext.get(config.leftBodyPage).dom;
	var gisPageFrame = Ext.get(config.gisPage).dom;
		Ext.get("left-panel-body").mask('正在载入页面,请稍等......');
		Ext.get("gisPanel").mask('正在载入地图,请稍等......');
		leftBodyPageFrame.src = config.actionHiddenTroubleUrl+"?method="+"showHiddenTroubleList";
		gisPageFrame.src = config.actionHiddenTroubleUrl+"?method="+"goToHiddenTroubleGisPage";
}

function showGridList(item,pressed) {
	var leftBodyPageFrame = Ext.get(config.leftBodyPage).dom;
	var gisPageFrame = Ext.get(config.gisPage).dom;
		Ext.get("left-panel-body").mask('正在载入页面,请稍等......');
		Ext.get("gisPanel").mask('正在载入地图,请稍等......');
		leftBodyPageFrame.src = config.actionGridUrl+"?method="+"showGridList";
		gisPageFrame.src = config.actionGridUrl+"?method="+"goToGridGisPage";
}
function showSiteList(item,pressed) {
	var leftBodyPageFrame = Ext.get(config.leftBodyPage).dom;
	var gisPageFrame = Ext.get(config.gisPage).dom;
		Ext.get("left-panel-body").mask('正在载入页面,请稍等......');
		Ext.get("gisPanel").mask('正在载入地图,请稍等......');
		leftBodyPageFrame.src = config.actionSiteUrl+"?method="+"showSiteList";
		gisPageFrame.src = config.actionSiteUrl+"?method="+"goToSiteGisPage";
}
function showHistoryList(item,pressed) {
	var leftBodyPageFrame = Ext.get(config.leftBodyPage).dom;
	var gisPageFrame = Ext.get(config.gisPage).dom;
		Ext.get("left-panel-body").mask('正在载入页面,请稍等......');
		Ext.get("gisPanel").mask('正在载入地图,请稍等......');
		leftBodyPageFrame.src = config.actionHistoryUrl+"?method="+"showHistoryList";
		gisPageFrame.src = config.actionHistoryUrl+"?method="+"goToHistoryGisPage";
}
function showLeakList(item,pressed) {
	var leftBodyPageFrame = Ext.get(config.leftBodyPage).dom;
	var gisPageFrame = Ext.get(config.gisPage).dom;
		Ext.get("left-panel-body").mask('正在载入页面,请稍等......');
		//Ext.get("gisPanel").mask('正在载入地图,请稍等......');
		leftBodyPageFrame.src = config.actionLeakUrl+"?method="+"showLeakList";
		gisPageFrame.src = config.actionLeakUrl+"?method="+"goToLeakGisPage";
}