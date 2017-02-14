
//global checkSegment varibal
var allCheckSegmentJSON;
var checkSegmentGraphics = [];

//设置巡检段图层
function setSegmentGraphics(jsonList) {
	allCheckSegmentJSON = jsonList;
	var p,points,polyline,checkPoints;	
  	var lineSymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new dojo.Color([255, 0, 0, 0.6]), 10);

  	var infoTemplate;
  	 
  	//设置显示巡检点段息的模板
  	infoTemplate = new esri.InfoTemplate();
	for(i=0;i<jsonList.length;i++) {
	  	 	points = new Array();
	  		polyline = new esri.geometry.Polyline(new esri.SpatialReference({wkid:4326}));
	  		checkPoints = jsonList[i].checkPoints;
			for(j=0;j<checkPoints.length;j++) {
				p = new esri.geometry.Point(checkPoints[j].longitude,checkPoints[j].latitude);
				points.push(p);
			}
	  		polyline.addPath(points);
	  		gra = new esri.Graphic(polyline, lineSymbol);
	  		
		  	//巡检段图形内容数组，数组0是类型标识，数组1是当前巡检段的所有信息
		  	var graphicContents = [];
	  		graphicContents[0] = "checkSegment";
	  		graphicContents[1] = jsonList[i];
	  		
	  		infoTemplate = new esri.InfoTemplate();
	  		infoTemplate.setTitle(jsonList[i].segmentName);
			infoTemplate.setContent(getCSTemplateContent(jsonList[i]));
			
			gra.setAttributes(graphicContents);
	  		gra.setInfoTemplate(infoTemplate);
	  		
	  		checkSegmentGraphics[i] = gra;
		}
}

//添加所有巡检段到地图
function addAllCheckSegmentGraphics() {
	var gra;
  	 //添回巡检段图标到地图
  	 for(i=0;i<checkSegmentGraphics.length;i++) {
  		gra = checkSegmentGraphics[i];
  	 	map.graphics.add(gra);
	}
}
//清除地图所有图标
function cleanAllCheckSegmentGraphics() {
	map.infoWindow.hide();
	map.graphics.clear();
}

//单独显示某个巡检段
function addCheckSegment(id) {
	var gra;
	for(i=0;i<allCheckSegmentJSON.length;i++) {
		if(allCheckSegmentJSON[i].id == id) {
			gra = checkSegmentGraphics[i];
	  		//map.graphics.setOpacity(0.6);
	  	 	map.graphics.add(gra);
	  	 }
	}
}

//清除某个巡检段
function removeCheckSegment(id) {
	var gra;
	for(i=0;i<allCheckSegmentJSON.length;i++) {
		if(allCheckSegmentJSON[i].id == id) {
			gra = checkSegmentGraphics[i];
			map.graphics.remove(gra);
			//cleanAllCheckPointGraphics();
	  	 }
	}
}

//inforWindow显示内容
function getCSTemplateContent(data) { 
		 var tpl = "<table id=\"sheet\" class=\"formTable\">"+
			"<tr>"+
				"<td colspan=\"4\"><div class=\"ui-widget-header\" >基本信息</div></td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "巡检段名称*"+
				"</td>"+
				"<td class=\"content\">"+
					data.resourceCode+
				"</td>"+
				"<td class=\"label\">"+
					"包含巡检点数*"+
				"</td>"+
				"<td class=\"content\">"+
					allCheckSegmentJSON.length+
				"(个)</td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "开始标示*"+
				"</td>"+
				"<td class=\"content\">"+
					data.startFlag+
				"</td>"+
				"<td class=\"label\">"+
					"结束标示*"+
				"</td>"+
				"<td class=\"content\">"+
					data.endFlag+
				"</td>"+
			"</tr>"+
		"</table>";
    return tpl; 
} 
