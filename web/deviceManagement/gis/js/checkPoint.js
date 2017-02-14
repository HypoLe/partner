//global checkPoint varibal
var allCheckPointJSON;
var checkPointGraphics = [];

function setAllCheckPointGraphics(jsonObj) {
	allCheckPointJSON = jsonObj;
	 var picSymbol = new esri.symbol.PictureMarkerSymbol(paramConfig.icon1, 24, 24);
  	 var cpPoint,gra,infoTemplate;
  	 
  	 //设置显示巡检点信息的模板
  	 infoTemplate = new esri.InfoTemplate();
  	 
  	 //添回巡检点图标到地图
  	 for(i=0;i<jsonObj.length;i++) {
  		cpPoint = new esri.geometry.Point(jsonObj[i].longitude, jsonObj[i].latitude, new esri.SpatialReference({ wkid: 4326 }));
  		gra = new esri.Graphic(cpPoint, picSymbol);
  		
  		//巡检点图形内容数组，数组0是类型标识，数组1是当前巡检点的所有信息
		var graphicContents = [];
  		graphicContents[0] = "checkPoint";
  		graphicContents[1] = jsonObj[i];
  		
  		infoTemplate = new esri.InfoTemplate();
  		infoTemplate.setTitle(jsonObj[i].resourceName);
		infoTemplate.setContent(getCPTemplateContent(jsonObj[i]));
		
		gra.setAttributes(graphicContents);
  		gra.setInfoTemplate(infoTemplate);
  		
  		checkPointGraphics[i] = gra;
  	 }
}

//添加所有巡检点到地图
function addAllCheckPointGraphics() {
  	 //添回巡检点图标到地图
  	 for(i=0;i<checkPointGraphics.length;i++) {
  	 	map.graphics.add(checkPointGraphics[i]);
	}
}
	      //清除地图所有图标
function cleanAllCheckPointGraphics() {
	map.infoWindow.hide();
	//添回巡检点图标到地图
  	 for(i=0;i<checkPointGraphics.length;i++) {
  	 	map.graphics.remove(checkPointGraphics[i]);
	}
}

//inforWindow显示内容
function getCPTemplateContent(data) { 
		 var tpl = "<table id=\"sheet\" class=\"formTable\">"+
			"<tr>"+
				"<td colspan=\"4\"><div class=\"ui-widget-header\" >基本信息</div></td>"+
			"</tr>"+
			"<tr>"+
				"<td class=\"label\">"+
				 "资源点编码*"+
				"</td>"+
				"<td class=\"content\">"+
					data.resourceCode+
				"</td>"+
				"<td class=\"label\">"+
					"资源点名称*"+
				"</td>"+
				"<td class=\"content\">"+
					data.resourceName+
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
