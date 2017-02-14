//global checkPoint varibal
var allSiteJSON = [];
var siteGraphics = [];

function setAllSiteGraphics(jsonObj) {
	 allSiteJSON = [];
	 siteGraphics = [];
	 allSiteJSON = jsonObj;
	 var picSymbol = new esri.symbol.PictureMarkerSymbol(paramConfig.icon5, 18, 18);
  	 var cpPoint,webPoint,gra,infoTemplate;
  	 
  	 //设置显示站点信息的模板
  	 infoTemplate = new esri.InfoTemplate();
  	 
  	 //添回站点图标到地图
  	 for(i=0;i<jsonObj.length;i++) {
  		cpPoint = new esri.geometry.Point(jsonObj[i].longitude, jsonObj[i].latitude, new esri.SpatialReference({ wkid: 4326 }));
  		webPoint = esri.geometry.geographicToWebMercator(cpPoint);
  		webPoint.setSpatialReference(new esri.SpatialReference({wkid:4326}));
  		gra = new esri.Graphic(webPoint, picSymbol);
  		
  		//站点图形内容数组，数组0是类型标识，数组1是当前站点的所有信息
		var graphicContents = [];
  		graphicContents[0] = "site";
  		graphicContents[1] = jsonObj[i];
  		
  		infoTemplate = new esri.InfoTemplate();
  		infoTemplate.setTitle(jsonObj[i].siteName);
		infoTemplate.setContent(getSiteTemplateContent(jsonObj[i]));
		
		gra.setAttributes(graphicContents);
  		gra.setInfoTemplate(infoTemplate);
  		
  		siteGraphics[i] = gra;
  	 }
}

//添加所有站点到地图
function addAllSiteGraphics() {
	cleanAllSiteGraphics();
  	 //添回站点图标到地图
  	 for(i=0;i<siteGraphics.length;i++) {
  	 	map.graphics.add(siteGraphics[i]);
	}
	var location = MapUtils.averageCoordinate(siteGraphics);
	map.centerAndZoom(location,8);
	
}
//清除地图所有图标
function cleanAllSiteGraphics() {
	map.infoWindow.hide();
	map.graphics.clear();
	//清除站点
  	 //for(i=0;i<siteGraphics.length;i++) {
  	 	//map.graphics.remove(siteGraphics[i]);
	//}
}

//单独显示某个站点
function locationSite(id) {
	var gra;
	map.infoWindow.hide();
	cleanAllSiteGraphics();
	for(var i=0;i<allSiteJSON.length;i++) {
		if(allSiteJSON[i].id == id) {
			gra = siteGraphics[i];
	  	 	map.graphics.add(gra);
			map.centerAndZoom(gra.geometry,11);
	  	 } else {
	  	 	removeSite(allSiteJSON[i].id);
	  	 }
	}
}

//清除某个站点
function removeSite(id) {
	var gra;
	for(var i=0;i<allSiteJSON.length;i++) {
		if(allSiteJSON[i].id == id) {
			gra = siteGraphics[i];
			map.graphics.remove(gra);
	  	 }
	}
}

//inforWindow显示内容
function getSiteTemplateContent(data) { 
		 var tpl = "<table id=\"sheet\" class=\"formTable\">"+
			"<tr>"+
				"<td colspan=\"4\"><div class=\"ui-widget-header\" >基本信息</div></td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "站点名称*"+
				"</td>"+
				"<td class=\"content\">"+
					data.siteName+
				"</td>"+
				"<td class=\"label\">"+
					"站点编号*"+
				"</td>"+
				"<td class=\"content\">"+
					data.siteNo+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "经度*"+
				"</td>"+
				"<td class=\"content\">"+
					data.longitude+
				"</td>"+
				"<td class=\"label\">"+
					"纬度*"+
				"</td>"+
				"<td class=\"content\">"+
					data.latitude+
				"</td>"+
			"</tr>"+
		"</table>";
    return tpl; 
} 

var CheckPoint = function() {
	
	return {
		addResult:false,
		addCheckPoint : function(params) {
			var result;
			Ext.Ajax.request({
					url:paramConfig.checkPointUrl,
					async:false,
					sync:true,
					params:params,
					success:function(res,opt) {
						if(Ext.util.JSON.decode(res.responseText).success == "true") {
							Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {
								Ext.get("map").unmask();
								CheckPoint.addResult = true;
								
								parent.Ext.get("left-panel-body").mask('正在刷新数据,请稍等......');
								parent.frames['left-panel-page'].location.reload();
								
								
								/*
								parent.Ext.get("gisPanel").mask('正在刷新地图,请稍等......');
								window.location.reload();
								parent.Ext.get("gisPanel").unmask();
								*/
							});
						} else {
							CheckPoint.addResult = false;
							Ext.Msg.alert("提示：","添加失败，请重新尝试!");
						}
					},
					failure:function(res,opt) {
						result = false;
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			parent.Ext.get("left-panel-body").unmask();
				
			return CheckPoint.addResult;
		}
	}
}();
