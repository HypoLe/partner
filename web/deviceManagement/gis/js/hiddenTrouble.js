//global checkPoint varibal
var allHiddenTroubleJSON = [];
var hiddenTroubleGraphics = [];

function setAllHiddenTroubleGraphics(jsonArray) {
	 allHiddenTroubleJSON = [];
	 hiddenTroubleGraphics = [];
	 allHiddenTroubleJSON = jsonArray;
	 var picSymbol_processed = new esri.symbol.PictureMarkerSymbol(paramConfig.icon2, 24, 24);
  	 var picSymbol_not_process = new esri.symbol.PictureMarkerSymbol(paramConfig.icon3, 24, 24);
  	 var htPoint,gra,infoTemplate;
  	 
  	 //设置显示隐患点信息的模板
  	 infoTemplate = new esri.InfoTemplate();
  	 
  	 //添回隐患点图标到地图
  	 for(i=0;i<jsonArray.length;i++) {
  		htPoint = new esri.geometry.Point(jsonArray[i].longitude, jsonArray[i].latitude, new esri.SpatialReference({ wkid: 4326 }));
  		
  		//隐患点图形内容数组，数组0是类型标识，数组1是当前隐患点的所有信息
		var graphicContents = [];
  		graphicContents[0] = "hiddenTrouble";
  		graphicContents[1] = jsonArray[i];
  		
  		infoTemplate = new esri.InfoTemplate();
  		infoTemplate.setTitle("隐患上报时间：" + jsonArray[i].reportTime);
  		if(jsonArray[i].processStatus == "未处理") {
	  		gra = new esri.Graphic(htPoint, picSymbol_not_process);
			infoTemplate.setContent(getHTTemplateContent_not_process(jsonArray[i]));
  		} else {
	  		gra = new esri.Graphic(htPoint, picSymbol_processed);
			infoTemplate.setContent(getHTTemplateContent_processed(jsonArray[i]));
  		}

		gra.setAttributes(graphicContents);
  		gra.setInfoTemplate(infoTemplate);
  		
  		hiddenTroubleGraphics[i] = gra;
  	}
}

//添加所有隐患点到地图
function addAllHiddenTroubleGraphics() {
	cleanAllHiddenTroubleGraphics();
  	 //添回隐患点图标到地图
	for(i=0;i<hiddenTroubleGraphics.length;i++) {
  	 	map.graphics.add(hiddenTroubleGraphics[i]);
	}
	//var location = MapUtils.averageCoordinate(hiddenTroubleGraphics);
	//map.centerAndZoom(location,8);      	 
}

//清除地图所有图标
function cleanAllHiddenTroubleGraphics() {
	map.infoWindow.hide();
	map.graphics.clear();
}


//单独显示某个隐患点
function locationHiddenTrouble(id) {
	var gra;
	map.infoWindow.hide();
	cleanAllHiddenTroubleGraphics
	for(var i=0;i<allHiddenTroubleJSON.length;i++) {
		if(allHiddenTroubleJSON[i].id == id) {
			gra = hiddenTroubleGraphics[i];
	  	 	map.graphics.add(gra);
			map.centerAndZoom(gra.geometry,11);
	  	 } else {
	  	 	removeHiddenTrouble(allHiddenTroubleJSON[i].id);
	  	 }
	}
}

//清除某个隐患点
function removeHiddenTrouble(id) {
	var gra;
	for(var i=0;i<allHiddenTroubleJSON.length;i++) {
		if(allHiddenTroubleJSON[i].id == id) {
			gra = hiddenTroubleGraphics[i];
			map.graphics.remove(gra);
	  	 }
	}
}
//inforWindow显示内容未处理的隐患
function getHTTemplateContent_not_process(data) { 
		 var tpl = "<table id=\"sheet\" class=\"formTable\">"+
			"<tr>"+
				"<td colspan=\"4\"><div class=\"ui-widget-header\" >基本信息</div></td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "地区*"+
				"</td>"+
				"<td class=\"content\">"+
					data.areaId+
				"</td>"+
				"<td class=\"label\">"+
					"隐患类型*"+
				"</td>"+
				"<td class=\"content\">"+
					data.hiddenTroubleType+
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
			"<tr>"+
				"<td class=\"label\">"+
				 "上报巡检员*"+
				"</td>"+
				"<td class=\"content\">"+
					data.checkUser+
				"</td>"+
				"<td class=\"label\">"+
					"联系电话*"+
				"</td>"+
				"<td class=\"content\">"+
					data.phone+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "详情查看*"+
				"</td>"+
				"<td class=\"content\" colspan=\"3\">"+
					"<a "+
					"href=\""+paramConfig.hiddenTroubleUrl+"?method=goToDetail&id="+data.id+"\" " +
					"target=\"_blank\"> 查看</a>"+
				"</td>"+
			"</tr>"+
		"</table>";
    return tpl; 
} 
//inforWindow显示内容已处理的隐患
function getHTTemplateContent_processed(data) { 
		 var tpl = "<table id=\"sheet\" class=\"formTable\">"+
			"<tr>"+
				"<td colspan=\"4\"><div class=\"ui-widget-header\" >基本信息</div></td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "地区*"+
				"</td>"+
				"<td class=\"content\">"+
					data.areaId+
				"</td>"+
				"<td class=\"label\">"+
					"隐患类型*"+
				"</td>"+
				"<td class=\"content\">"+
					data.hiddenTroubleType+
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
			"<tr>"+
				"<td class=\"label\">"+
				 "上报巡检员*"+
				"</td>"+
				"<td class=\"content\">"+
					data.checkUser+
				"</td>"+
				"<td class=\"label\">"+
					"联系电话*"+
				"</td>"+
				"<td class=\"content\">"+
					data.phone+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td colspan=\"4\"><div class=\"ui-widget-header\" >处理信息</div></td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "处理时间*"+
				"</td>"+
				"<td class=\"content\">"+
					data.handlTime+
				"</td>"+
				"<td class=\"label\">"+
					"处理人*"+
				"</td>"+
				"<td class=\"content\">"+
					data.processUser+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "处理信息*"+
				"</td>"+
				"<td class=\"content\" colspan=\"3\">"+
					data.handleMsg+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "详情查看*"+
				"</td>"+
				"<td class=\"content\" colspan=\"3\">"+
					"<a "+
					"href=\""+paramConfig.hiddenTroubleUrl+"?method=goToDetail&id="+data.id+"\" " +
					"target=\"_blank\"> 查看</a>"+
				"</td>"+
			"</tr>"+
		"</table>";
    return tpl; 
} 



var HiddenTrouble = function() {
	
	return {
		addResult:false,
		addHiddenTrouble : function(params) {
			var result;
			Ext.Ajax.request({
					url:paramConfig.hiddenTroubleUrl,
					async:false,
					sync:true,
					params:params,
					success:function(res,opt) {
						if(Ext.util.JSON.decode(res.responseText).success == "true") {
							Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {
								Ext.get("map").unmask();
								HiddenTrouble.addResult = true;
								
								parent.Ext.get("left-panel-body").mask('正在刷新数据,请稍等......');
								parent.frames['left-panel-page'].location.reload();
								
								
								/*
								parent.Ext.get("gisPanel").mask('正在刷新地图,请稍等......');
								window.location.reload();
								parent.Ext.get("gisPanel").unmask();
								*/
							});
						} else {
							HiddenTrouble.addResult = false;
							Ext.Msg.alert("提示：","添加失败，请重新尝试!");
						}
					},
					failure:function(res,opt) {
						result = false;
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			parent.Ext.get("left-panel-body").unmask();
				
			return HiddenTrouble.addResult;
		}
	}
}();
