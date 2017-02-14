//global checkPoint varibal
var allHistoryJSON = [];
var historyGraphics = [];

function setAllHistoryGraphics(jsonObj) {
	 allHistoryJSON = [];
	 historyGraphics = [];
	 allHistoryJSON = jsonObj;
	 var picSymbol = new esri.symbol.PictureMarkerSymbol(paramConfig.icon1, 24, 24);
  	 var cpPoint,gra,infoTemplate;
  	 
  	 //设置显示网格信息的模板
  	 infoTemplate = new esri.InfoTemplate();
  	 
  	 //添回网格图标到地图
  	 for(i=0;i<jsonObj.length;i++) {
  	 	jsonObj[i].longitude = jsonObj[i].locationLongitude;
  	 	jsonObj[i].latitude = jsonObj[i].locationLatitude;
  		cpPoint = new esri.geometry.Point(jsonObj[i].longitude, jsonObj[i].latitude, new esri.SpatialReference({ wkid: 4326 }));
  		webPoint = esri.geometry.geographicToWebMercator(cpPoint);
  		webPoint.setSpatialReference(new esri.SpatialReference({wkid:4326}));
  		gra = new esri.Graphic(webPoint, picSymbol);
  		
  		//网格图形内容数组，数组0是类型标识，数组1是当前网格的所有信息
		var graphicContents = [];
  		graphicContents[0] = "history";
  		graphicContents[1] = jsonObj[i];
  		
  		infoTemplate = new esri.InfoTemplate();
  		infoTemplate.setTitle(jsonObj[i].siteName);
		infoTemplate.setContent(getHistoryTemplateContent(jsonObj[i]));
		
		gra.setAttributes(graphicContents);
  		gra.setInfoTemplate(infoTemplate);
  		
  		historyGraphics[i] = gra;
  	 }
}

//添加所有网格到地图
function addAllHistoryGraphics() {
	cleanAllHistoryGraphics();
  	 //添回网格图标到地图
  	 for(i=0;i<historyGraphics.length;i++) {
  	 	map.graphics.add(historyGraphics[i]);
	}
	var location = MapUtils.averageCoordinate(historyGraphics);
	map.centerAndZoom(location,8);
}
//清除地图所有图标
function cleanAllHistoryGraphics() {
	map.infoWindow.hide();
	map.graphics.clear();
}

//单独显示某个站点
function locationHistory(id) {
	var gra;
	map.infoWindow.hide();
	for(var i=0;i<allHistoryJSON.length;i++) {
		if(allHistoryJSON[i].id == id) {
			gra = historyGraphics[i];
	  	 	map.graphics.add(gra);
			map.centerAndZoom(gra.geometry,11);
	  	 } else {
	  	 	removeHistory(allHistoryJSON[i].id);
	  	 }
	}
}

//清除某个站点
function removeHistory(id) {
	var gra;
	for(var i=0;i<allHistoryJSON.length;i++) {
		if(allHistoryJSON[i].id == id) {
			gra = historyGraphics[i];
			map.graphics.remove(gra);
	  	 }
	}
}

//inforWindow显示内容
function getHistoryTemplateContent(data) { 
		 var tpl = "<table id=\"sheet\" class=\"formTable\">"+
			"<tr>"+
				"<td colspan=\"4\"><div class=\"ui-widget-header\" >基本信息</div></td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "站点名称"+
				"</td>"+
				"<td class=\"content\">"+
					data.siteName+
				"</td>"+
				"<td class=\"label\">"+
					"巡检人"+
				"</td>"+
				"<td class=\"content\">"+
					data.inspectUserName+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "设备数量"+
				"</td>"+
				"<td class=\"content\">"+
					data.deviceNum+
				"</td>"+
				"<td class=\"label\">"+
					"巡检项数量"+
				"</td>"+
				"<td class=\"content\">"+
					data.itemNum+
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
