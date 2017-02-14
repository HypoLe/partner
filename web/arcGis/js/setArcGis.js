/*
 * ! setArcGis.js @author huhao @company Boco
 */var map = null;
var siteGraphics = [];
var toDrawLine = [];
var toDrawLineFlag = [];
var configUrl = {
	shanxi : {
		"xmin" : 101.592850940096,
		"ymin" : 31.4956355823356,
		"xmax" : 115.133769008904,
		"ymax" : 39.7967254386645,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	baoji : {
		"xmin" : 106.97268633778467,
		"ymin" : 34.1716570787191,
		"xmax" : 107.44857853895073,
		"ymax" : 34.5642681446811,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	shangluo : {
		"xmin" : 109.87576677363593,
		"ymin" : 33.82805101217218,
		"xmax" : 109.99759517713444,
		"ymax" : 33.928559445058454,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	ankang : {
		"xmin" : 108.96443082894194,
		"ymin" : 32.644818817263946,
		"xmax" : 109.08625923244045,
		"ymax" : 32.74532725015022,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	hanzhong : {
		"xmin" : 106.96524576321943,
		"ymin" : 33.03588085411114,
		"xmax" : 107.08707416671794,
		"ymax" : 33.136389286997414,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	tongchuan : {
		"xmin" : 108.69807158448823,
		"ymin" : 34.732208123011596,
		"xmax" : 109.1739637856543,
		"ymax" : 35.124819188973596,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	yanan : {
		"xmin" : 109.24058869381753,
		"ymin" : 36.40734867111611,
		"xmax" : 109.71648089498359,
		"ymax" : 36.79995973707811,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	yulin : {
		"xmin" : 109.52344712088559,
		"ymin" : 38.083084084472084,
		"xmax" : 109.99933932205165,
		"ymax" : 38.475695150434085,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	weinan : {
		"xmin" : 109.35809599612949,
		"ymin" : 34.39711100902153,
		"xmax" : 109.59604209671252,
		"ymax" : 34.59341654200253,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	xianyang : {
		"xmin" : 108.58375751884617,
		"ymin" : 34.24067096573221,
		"xmax" : 108.8217036194292,
		"ymax" : 34.43697649871321,
		"spatialReference" : {
			"wkid" : 4326
		}
	},
	xian : {
		"xmin" : 108.69182549934789,
		"ymin" : 34.084994729425745,
		"xmax" : 109.16771770051395,
		"ymax" : 34.477605795387746,
		"spatialReference" : {
			"wkid" : 4326
		}
	}
};
var cityData = ["宝鸡", "商洛", "安康", "汉中", "铜川", "延安", "榆林", "渭南", "咸阳", "西安"];
var cityForUrl = ["baoji", "shangluo", "ankang", "hanzhong", "tongchuan",
		"yanan", "yulin", "weinan", "xianyang", "xian"];
var cityUrl = {
	shanxi : ["109.503789", "35.860026"],
	baoji : ["107.174299", "34.363696"],
	shangluo : ["109.934208", "33.873907"],
	ankang : ["109.038045", "32.70437 "],
	hanzhong : ["107.045478", "33.081569"],
	tongchuan : ["108.968067", "34.908368"],
	yanan : ["109.50051 ", "36.60332 "],
	yulin : ["109.745926", "38.279439"],
	weinan : ["109.483933", "34.502358"],
	xianyang : ["108.707509", "34.345373"],
	xian : ["108.953098", "34.2778  "],
	jinan : ["117.01166000", "36.66147500"]
};
function cityAdapter(car, oil, flag) {
	try {
		if (flag) {
			removeGraphics(flag);
		}
		if (0 != car.length && null != car) {
			var city = [];
			var carCount = [];
			var oilCount = [];
			var arr = [];
			var oilType = null;
			var carType = car[0];
			if (car.length == oil.length) {
				oilType = oil[0];
			} else {
				oilType = "";
			}
			var size = (car.length - 1) / 2;
			for (var i = 1; i < size + 1; i++) {
				city.push(car[i]);
			}
			for (var j = size + 1; j < car.length; j++) {
				carCount.push(car[j]);
				if (car.length == oil.length) {
					oilCount.push(oil[j]);
				}
			}
			if (0 != city.length) {
				for (var ii = 0; ii < city.length; ii++) {
					for (var jj = 0; jj < cityData.length; jj++) {
						if (cityData[jj] == city[ii]) {
							arr.push(cityForUrl[jj]);
							break;
						}
					}
				}
				alert(arr.length);
				alert(carCount.length);
			} else {
				alert("无法在地图上渲染地市信息，请重试！");
			}
			if (0 != arr.length && 0 != carCount.length) {
				for (var a = 0; a < arr.length; a++) {
					var setCarInformation = null;
					var setCarType = null;
					var setOilInformation = null;
					var setOilType = null;
					var point = eval("cityUrl." + arr[a]);
					setCarInformation = carCount[a];
					setCarType = carType;
					if (car.length == oil.length) {
						setOilInformation = oilCount[a];
						setOilType = oilType;
					}
					var setCityInformation = city[a];
					if (0 != point.length && null != setCityInformation) {
						cityMarker(point, setCarInformation, setOilInformation,
								setCityInformation, setCarType, setOilType);
					}
				}
			} else {
				alert("无法在地图上渲染地市统计信息，请重试！");
			}
		} else {
			alert("gis统计数据无法在地图上渲染，请重试！");
		}
	} catch (err) {
		alert("渲染gis统计信息失败，请重试！");
	}
}
function iconAdapter(data, type) {
	var typeFlag;
	if (type == "已完成") {
		typeFlag = true;
	} else if (type == "未完成") {
		typeFlag = false;
	} else {
		typeFlag = null;
	}
	var icon = iconConfig.error;
	if (data == "2G基站") {
		if (typeFlag == true) {
			icon = iconConfig._2g1;
		} else if (typeFlag == false) {
			icon = iconConfig._2g2;
		} else {
			icon = iconConfig._2g;
		}
	} else if (data == "3G基站") {
		if (typeFlag == true) {
			icon = iconConfig._3g1;
		} else if (typeFlag == false) {
			icon = iconConfig._3g2;
		} else {
			icon = iconConfig._3g;
		}
	}else if (data == "基站-VIP基站") {
		if (typeFlag == true) {
			icon = iconConfig._3g7;
		} else if (typeFlag == false) {
			icon = iconConfig._3g7;
		} else {
			icon = iconConfig._3g7;
		}
	}else if (data == "基站-A类") {
		if (typeFlag == true) {
			icon = iconConfig._3g5;
		} else if (typeFlag == false) {
			icon = iconConfig._3g5;
		} else {
			icon = iconConfig._3g5;
		}
	}else if (data == "基站-B类") {
		if (typeFlag == true) {
			icon = iconConfig._3g6;
		} else if (typeFlag == false) {
			icon = iconConfig._3g6;
		} else {
			icon = iconConfig._3g;
		}
	}else if (data == "基站-C类") {
		if (typeFlag == true) {
			icon = iconConfig._3g8;
		} else if (typeFlag == false) {
			icon = iconConfig._3g8;
		} else {
			icon = iconConfig._3g8;
		}
	} else if (data == "2G基站&3G基站") {
		if (typeFlag == true) {
			icon = iconConfig._2g3g1;
		} else if (!typeFlag == true) {
			icon = iconConfig._2g3g2;
		} else {
			icon = iconConfig._2g3g;
		}
	}else if (data == "接入网-A类") {
		if (typeFlag == true) {
			icon = iconConfig._2g3g7;
		} else if (!typeFlag == true) {
			icon = iconConfig._2g3g7;
		} else {
			icon = iconConfig._2g3g7;
		}
	}else if (data == "接入网-B类") {
		if (typeFlag == true) {
			icon = iconConfig._2g3g5;
		} else if (!typeFlag == true) {
			icon = iconConfig._2g3g5;
		} else {
			icon = iconConfig._2g3g5;
		}
	}else if (data == "接入网-C类") {
		if (typeFlag == true) {
			icon = iconConfig._2g3g6;
		} else if (!typeFlag == true) {
			icon = iconConfig._2g3g6;
		} else {
			icon = iconConfig._2g3g6;
		}
	} else if (data.substr(0,4) == "WLAN") {
		if (typeFlag == true) {
			icon = iconConfig.wlan1;
		} else if (typeFlag == false) {
			icon = iconConfig.wlan2;
		} else {
			icon = iconConfig.wlan;
		}
	} else if (data.substr(0,5)== "直放站室分") {
		if (typeFlag == true) {
			icon = iconConfig.zhifangzhan1;
		} else if (typeFlag == false) {
			icon = iconConfig.zhifangzhan2;
		} else {
			icon = iconConfig.zhifangzhan;
		}
	} else if (data == "角钢塔及馈线") {
		if (typeFlag == true) {
			icon = iconConfig.tieta1;
		} else if (typeFlag == false) {
			icon = iconConfig.tieta2;
		} else {
			icon = iconConfig.tieta;
		}
	} else if (data == "铁塔及天馈-月标准") {
		if (typeFlag == true) {
			icon = iconConfig.tieta7;
		} else if (typeFlag == false) {
			icon = iconConfig.tieta7;
		} else {
			icon = iconConfig.tieta7;
		}
	} else if (data == "铁塔及天馈-季度") {
		if (typeFlag == true) {
			icon = iconConfig.tieta5;
		} else if (typeFlag == false) {
			icon = iconConfig.tieta5;
		} else {
			icon = iconConfig.tieta5;
		}
	} else if (data == "单管塔（景管塔）及馈线") {
		if (typeFlag == true) {
			icon = iconConfig.tianxian1;
		} else if (typeFlag == false) {
			icon = iconConfig.tianxian2;
		} else {
			icon = iconConfig.tianxian;
		}
	} else if (data == "三管塔及馈线") {
		if (typeFlag == true) {
			icon = iconConfig.tieta1;
		} else if (typeFlag == false) {
			icon = iconConfig.tieta2;
		} else {
			icon = iconConfig.tieta;
		}
	} else if (data == "拉线塔及馈线") {
		if (typeFlag == true) {
			icon = iconConfig.tianxian1;
		} else if (typeFlag == false) {
			icon = iconConfig.tianxian2;
		} else {
			icon = iconConfig.tianxian;
		}
	} else if (data == "传输专线") {
		if (typeFlag == true) {
			icon = iconConfig.chuanshuzhuanxian1;
		} else if (typeFlag == false) {
			icon = iconConfig.chuanshuzhuanxian2;
		} else {
			icon = iconConfig.chuanshuzhuanxian;
		}
	} else if (data == "驻地网小区（ONU）") {
		if (typeFlag == true) {
			icon = iconConfig.shujuzhuanxian1;
		} else if (typeFlag == false) {
			icon = iconConfig.shujuzhuanxian2;
		} else {
			icon = iconConfig.shujuzhuanxian;
		}
	} else if (data == "集团客户机房") {
		if (typeFlag == true) {
			icon = iconConfig.gprs1;
		} else if (typeFlag == false) {
			icon = iconConfig.gprs2;
		} else {
			icon = iconConfig.gprs;
		}
	} else if(data == "重点客户机房-标准"){
		if (typeFlag == true) {
			icon = iconConfig.gprs1;
		} else if (typeFlag == false) {
			icon = iconConfig.gprs2;
		} else {
			icon = iconConfig.gprs;
		}
	} 
	return icon;
}
function cityMarker(point, setCarInformation, setOilInformation,
		setCityInformation, setCarType, setOilType) {
		alert("aaaaa"+point+" "+setCarInformation+" "+setOilInformation+" "+setCityInformation+" "+setCarType+" "+setOilType );
		
	try {
		var picSymbol = new esri.symbol.PictureMarkerSymbol(iconConfig.biaoshi,
				40, 40);
		var cpPoint = new esri.geometry.Point(point[0], point[1],
				new esri.SpatialReference({
							wkid : 4326
						}));
		cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
		var gra = new esri.Graphic(cpPoint, picSymbol);
		var infoTemplate = new esri.InfoTemplate();
		infoTemplate.setTitle(setCityInformation);
		var tempContent = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
		tempContent = tempContent
				+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> "
				+ setCarType
				+ "</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;'> "
				+ setCarInformation + "</td></tr>";
		if ("" != setOilInformation && null != setOilInformation) {
			tempContent = tempContent
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> "
					+ setOilType
					+ "</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;'> "
					+ setOilInformation + "</td></tr>";
		}
		tempContent = tempContent + "</table>";
		infoTemplate.setContent(tempContent);
		gra.setInfoTemplate(infoTemplate);
		map.graphics.add(gra);
		map.infoWindow.resize(245, 100);
	} catch (err) {
	}
}
function layerAdapter(px, py) {
	var data = [];
	var shanxi = ["101.592850940096", " 31.4956355823356", " 115.133769008904",
			" 39.7967254386645"];
	var baoji = ["106.97268633778467", " 34.1716570787191",
			" 107.44857853895073", " 34.5642681446811"];
	var shangluo = ["109.87576677363593", " 33.82805101217218",
			" 109.99759517713444", " 33.928559445058454"];
	var ankang = ["108.96443082894194", " 32.644818817263946",
			" 109.08625923244045", " 32.74532725015022"];
	var hanzhong = ["106.96524576321943", " 33.03588085411114",
			" 107.08707416671794", " 33.136389286997414"];
	var tongchuan = ["108.69807158448823", " 34.732208123011596",
			" 109.1739637856543", " 35.124819188973596"];
	var yanan = ["109.24058869381753", " 36.40734867111611",
			" 109.71648089498359", " 36.79995973707811"];
	var yulin = ["109.52344712088559", " 38.083084084472084",
			" 109.99933932205165", " 38.475695150434085"];
	var weinan = ["109.35809599612949", " 34.39711100902153",
			" 109.59604209671252", " 34.59341654200253"];
	var xianyang = ["108.58375751884617", " 34.24067096573221",
			" 108.8217036194292", " 34.43697649871321"];
	var xian = ["108.69182549934789", " 34.084994729425745",
			" 109.16771770051395", " 34.477605795387746"];
	if (shanxi[0] * 1 - px * 1 < 0 && shanxi[2] * 1 - px * 1 > 0
			&& shanxi[1] * 1 - py * 1 < 0 && shanxi[3] * 1 - py * 1 > 0) {
		data.push("shanxi");
		if (baoji[0] * 1 - px * 1 < 0 && baoji[2] * 1 - px * 1 > 0
				&& baoji[1] * 1 - py * 1 < 0 && baoji[3] * 1 - py * 1 > 0) {
			data.push("baoji");
		} else if (shangluo[0] * 1 - px * 1 < 0 && shangluo[2] * 1 - px * 1 > 0
				&& shangluo[1] * 1 - py * 1 < 0 && shangluo[3] * 1 - py * 1 > 0) {
			data.push("shangluo");
		} else if (ankang[0] * 1 - px * 1 < 0 && ankang[2] * 1 - px * 1 > 0
				&& ankang[1] * 1 - py * 1 < 0 && ankang[3] * 1 - py * 1 > 0) {
			data.push("ankang");
		} else if (hanzhong[0] * 1 - px * 1 < 0 && hanzhong[2] * 1 - px * 1 > 0
				&& hanzhong[1] * 1 - py * 1 < 0 && hanzhong[3] * 1 - py * 1 > 0) {
			data.push("hanzhong");
		} else if (tongchuan[0] * 1 - px * 1 < 0
				&& tongchuan[2] * 1 - px * 1 > 0
				&& tongchuan[1] * 1 - py * 1 < 0
				&& tongchuan[3] * 1 - py * 1 > 0) {
			data.push("tongchuan");
		} else if (yanan[0] * 1 - px * 1 < 0 && yanan[2] * 1 - px * 1 > 0
				&& yanan[1] * 1 - py * 1 < 0 && yanan[3] * 1 - py * 1 > 0) {
			data.push("yanan");
		} else if (yulin[0] * 1 - px * 1 < 0 && yulin[2] * 1 - px * 1 > 0
				&& yulin[1] * 1 - py * 1 < 0 && yulin[3] * 1 - py * 1 > 0) {
			data.push("yulin");
		} else if (weinan[0] * 1 - px * 1 < 0 && weinan[2] * 1 - px * 1 > 0
				&& weinan[1] * 1 - py * 1 < 0 && weinan[3] * 1 - py * 1 > 0) {
			data.push("weinan");
		} else if (xianyang[0] * 1 - px * 1 < 0 && xianyang[2] * 1 - px * 1 > 0
				&& xianyang[1] * 1 - py * 1 < 0 && xianyang[3] * 1 - py * 1 > 0) {
			data.push("xianyang");
		} else if (xian[0] * 1 - px * 1 < 0 && xian[2] * 1 - px * 1 > 0
				&& xian[1] * 1 - py * 1 < 0 && xian[3] * 1 - py * 1 > 0) {
			data.push("xian");
		}
	}
	if (data.length == 1) {
		var name = data[o];
	} else if (data.length == 2) {
		var name = data[1];
	}
	return eval("configUrl." + data[1]);
}
function loadMap() {
	try {
		var initExtent = new esri.geometry.Extent({
//					"xmin" : 113.292850940196,
//					"ymin" : 33.7556355823356,
//					"xmax" : 119.573769008904,
//					"ymax" : 39.9567254386645,
//                  初始位置
					"xmin" : 116.5006600000,
					"ymin" : 36.6614750000,
					"xmax" : 117.5016600000,
					"ymax" : 36.6614750000,
					"spatialReference" : {
						"wkid" : 4326
					}
				});
		map = new esri.Map("map", {
					extent : esri.geometry.geographicToWebMercator(initExtent)
				});
//备用地址
//		var tiledLayer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/arcgis/rest/services/World_Physical_Map/MapServer");		
//英文的。。。		var tiledLayer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
		var tiledLayer = new esri.layers.ArcGISTiledMapServiceLayer("http://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer");
		map.addLayer(tiledLayer);
		if (map.loaded) {
			mapOnLoad(map);
		} else {
			dojo.connect(map, "onLoad", mapOnLoad);
		}
	} catch (err) {
		alert("地图加载失败，请重试！");
	}
};
function mapOnLoad(map) {
	try {
		if (map != null) {
			var resizeTimer;
			dojo.connect(dijit.byId('map'), 'resize', function() {
						clearTimeout(resizeTimer);
						resizeTimer = setTimeout(function() {
									map.resize();
									map.reposition();
								}, 500);
					});
			dojo.connect(map.graphics, "onMouseOver", graphicsOnMouseOver);
		}
	} catch (err) {
		alert("地图加载失败，请重试！");
	}
}
function graphicsOnMouseOver(event) {
	event.target.style.cursor = "pointer";
}
function mapOnZoomEnd(extent, zoomFactor, anchor, level) {
}

//加载地图后会在此重新定位一次
function mapCenterAndZoom(flag, flag1) {
	try {
		if (map.loaded && map != null) {
			if (flag) {
				removeGraphics(flag);
			}
			var location = null;
			if (flag1) {
				location = new esri.geometry.Point(eval(cityUrl.jinan[0]),
						eval(cityUrl.jinan[1]), new esri.SpatialReference({
									wkid : 4326
								}));
				location = new esri.geometry.geographicToWebMercator(location);
				map.centerAndZoom(location, 8);
			} else {
				location = new esri.geometry.Point(eval(cityUrl.jinan[0]),
						eval(cityUrl.jinan[1]), new esri.SpatialReference({
									wkid : 4326
								}));
				location = new esri.geometry.geographicToWebMercator(location);
				map.centerAndZoom(location, 8);
			}
		}
	} catch (err) {
		alert("地图加载失败，请重试！");
	}
}

//资源信息初始化是打点方法
function addMarker(data_a, pathUrl, type, flag, otherInformation, detailFlag,centerFlag) {
	try {
		if (map.loaded && map != null) {
			removeGraphics(flag);
			var typeFlag = null;
			if (otherInformation != null && otherInformation.length != 0) {
				if (otherInformation.length == 11) {
					typeFlag = otherInformation[10];
				} else if (otherInformation.length == 10) {
					typeFlag = otherInformation[9];
				}
			}
			//此处获取打点呈现图片
			var icon = iconAdapter(data_a[0].resType, typeFlag);
			var picSymbol = new esri.symbol.PictureMarkerSymbol(icon, 25, 25);
			var infoTemplate = new esri.InfoTemplate();
			var px = data_a[0].resLongitude;
			var py = data_a[0].resLatitude;
			var cpPoint = new esri.geometry.Point(px, py,
					new esri.SpatialReference({		wkid : 4326	}));
			cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
			var gra = new esri.Graphic(cpPoint, picSymbol);
			var infoTemplate = new esri.InfoTemplate();
			infoTemplate.setTitle(data_a[0].resName);
			var getResourceInformation = getResourceMarkerTemplateContent(
					data_a, otherInformation, pathUrl, detailFlag);
			infoTemplate.setContent(getResourceInformation[0]);
			gra.setInfoTemplate(infoTemplate);
			if(centerFlag){
			map.centerAt(cpPoint);
			}
			var mapHeight = document.getElementById("map").style.height;
			mapHeight = mapHeight.split("px")[0] / 2 - 50;
			var informationHeight = getResourceInformation[1] * 33;
			var endInformationHeight = getResourceInformation[1] * 33;
			if (mapHeight < informationHeight) {
				endInformationHeight = mapHeight
			}
			setTimeout(function() {
				if(centerFlag){
						map.setLevel(15);
				}
						map.graphics.add(gra);
						if (type == "selected") {
							map.infoWindow.setTitle(data_a[0].resName);
							map.infoWindow
									.setContent(getResourceInformation[0]);
							map.infoWindow.show(cpPoint);
						}
						map.infoWindow.resize(245, endInformationHeight);
					}, 500);
		}
	} catch (err) {
		alert("地图渲染资源失败，请重试！");
	}
}

///资源信息--拼出详情
function getResourceMarkerTemplateContent(data_a, otherInformation, pathUrl,
		detailFlag) {
	try {
		if (map.loaded && map != null) {
			var i = 0;
			var allReturn = [];
			var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
			if (data_a[0].resName != null && data_a[0].resName != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 资源名称：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].resName + "</td></tr>";
				i++;
			}
			if (data_a[0].specialty != null && data_a[0].specialty != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 专业：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].specialty + "</td></tr>";
				i++;
			}
			if (data_a[0].resType != null && data_a[0].resType != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 类型：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].resType + "</td></tr>";
				i++;
			}
			if (data_a[0].city != null && data_a[0].city != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 城市：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].city + "</td></tr>";
				i++;
			}
			if (data_a[0].country != null && data_a[0].country != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 区县：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].country + "</td></tr>";
				i++;
			}
			if (data_a[0].street != null && data_a[0].street != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 街道：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].street + "</td></tr>";
				i++;
			}
			if (data_a[0].companyName != null && data_a[0].companyName != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 业主单位名称：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].companyName + "</td></tr>";
				i++;
			}
			if (data_a[0].contactName != null && data_a[0].contactName != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系人：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].contactName + "</td></tr>";
				i++;
			}
			if (data_a[0].phone != null && data_a[0].phone != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系电话：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].phone + "</td></tr>";
				i++;
			}
			if (data_a[0].executeObject != null
					&& data_a[0].executeObject != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 任务执行小组：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].executeObject + "</td></tr>";
				i++;
			}
			if (otherInformation != null && otherInformation.length != 0) {
				if (otherInformation[7] != null && otherInformation[7] != "") {
					content = content
							+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检开始时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
							+ otherInformation[7] + "</td></tr>";
					i++;
				}
				if (otherInformation[8] != null && otherInformation[8] != "") {
					content = content
							+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检结束时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
							+ otherInformation[8] + "</td></tr>";
					i++;
				}
				if (otherInformation.length == 11) {
					if (otherInformation[9] != null
							&& otherInformation[9] != "") {
						content = content
								+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 完成时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
								+ otherInformation[9] + "</td></tr>";
						i++;
					}
					if (otherInformation[10] != null
							&& otherInformation[10] != "") {
						content = content
								+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 完成情况：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
								+ otherInformation[10] + "</td></tr>";
						i++;
					}
				} else if (otherInformation.length == 10) {
					if (otherInformation[9] != null
							&& otherInformation[9] != "") {
						content = content
								+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 完成情况：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
								+ otherInformation[9] + "</td></tr>";
						i++;
					}
				}
			}
			if (data_a[0].signTime != null && data_a[0].signTime != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检签到时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].signTime + "</td></tr>";
				i++;
			}
			if (data_a[0].inspectTime != null && data_a[0].inspectTime != "") {
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检完成时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].inspectTime + "</td></tr>";
				i++;
			}
			content += "</table>";
			if (detailFlag) {
				content += "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
						+ data_a[0].resUrl
						+ "','"
						+ pathUrl
						+ "')\""
						+ ">查看详情</a></div>";
			}
			allReturn.push(content);
			allReturn.push(i);
		}
	} catch (err) {
		alert("获取资源数据失败，请重试！");
	}
	return allReturn;
}
function addLine(data_a, pathUrl, type, flag, other) {
	try {
		if (map.loaded && map != null) {
			removeGraphics(flag);
			siteGraphics = [];
			var lineSymbol = new esri.symbol.SimpleLineSymbol(
					esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([
							255, 0, 0]), 3);
			var infoTemplate = new esri.InfoTemplate();
			var px = data_a[0].resLongitude;
			var py = data_a[0].resLatitude;
			var px1 = data_a[0].endResLongitude;
			var py1 = data_a[0].endResLatitude;
			if (px != "" && py != "" && px1 != "" && py1 != "") {
				var polyline = new esri.geometry.Polyline(new esri.SpatialReference(
						{
							wkid : 4326
						}));
				var p1 = new esri.geometry.Point(px, py,
						new esri.SpatialReference({
									wkid : 4326
								}));
				var p2 = new esri.geometry.Point(px1, py1,
						new esri.SpatialReference({
									wkid : 4326
								}))
				p1 = new esri.geometry.geographicToWebMercator(p1);
				p2 = new esri.geometry.geographicToWebMercator(p2);
				polyline.addPath([p1, p2]);
				var gra = new esri.Graphic(polyline, lineSymbol);
				infoTemplate = new esri.InfoTemplate();
				infoTemplate.setTitle(data_a[0].resName);
				var getResourceInformation = getResourceLineTemplateContent(
						data_a, pathUrl);
				infoTemplate.setContent(getResourceInformation[0]);
				gra.setInfoTemplate(infoTemplate);
				map.graphics.add(gra);
				var x = (parseFloat(px) + parseFloat(px1)) / 2;
				var y = (parseFloat(py) + parseFloat(py1)) / 2;
				var webPoint = new esri.geometry.Point(new esri.geometry.Point(
						x, y, new esri.SpatialReference({
									wkid : 4326
								})));
				webPoint = new esri.geometry.geographicToWebMercator(webPoint);
				if (type == "selected") {
					map.infoWindow.setTitle(data_a[0].resName);
					map.infoWindow.setContent(getResourceInformation[0]);
					var mapHeight = document.getElementById("map").style.height;
					mapHeight = mapHeight.split("px")[0] / 2 - 50;
					var informationHeight = getResourceInformation[1] * 33;
					var endInformationHeight = getResourceInformation[1] * 33;
					if (mapHeight < informationHeight) {
						endInformationHeight = mapHeight
					}
					map.infoWindow.resize(245, endInformationHeight);
					map.infoWindow.show(webPoint);
				}
				map.centerAndZoom(webPoint, 8);
			} else {
				alert("该资源坐标坐标信息不正确！");
			}
		}
	} catch (err) {
		alert("地图渲染线路失败，请重试！");
	}
}
function getResourceLineTemplateContent(json, pathUrl) {
	try {
		var i = 0;
		var allReturn = [];
		var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
		if (json[0].resName != null && json[0].resName != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 路线名称：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].resName + "</td></tr>";
			i++;
		}
		if (json[0].specialty != null && json[0].specialty != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 专业：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].specialty + "</td></tr>";
			i++;
		}
		if (json[0].resType != null && json[0].resType != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 类型：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].resType + "</td></tr>";
			i++;
		}
		if (json[0].city != null && json[0].city != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 城市：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].city + "</td></tr>";
			i++;
		}
		if (json[0].country != null && json[0].country != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 区县：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].country + "</td></tr>";
			i++;
		}
		if (json[0].companyName != null && json[0].companyName != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 业主单位名称：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].companyName + "</td></tr>";
			i++;
		}
		if (json[0].contactName != null && json[0].contactName != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系人：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].contactName + "</td></tr>";
			i++;
		}
		if (json[0].phone != null && json[0].phone != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系电话：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].phone + "</td></tr>";
			i++;
		}
		if (json[0].executeObject != null && json[0].executeObject != "") {
			content = content
					+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 任务执行小组：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
					+ json[0].executeObject + "</td></tr>";
			i++;
		}
		content += "</table>";
		content += "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
				+ json[0].resUrl + "','" + pathUrl + "')\"" + ">查看详情</a></div>";
		allReturn.push(content);
		allReturn.push(i);
	} catch (err) {
		alert("获取线路信息失败，请重试！");
	}
	return allReturn;
}
function removeGraphics(flag) {
	try {
		if (flag) {
			if (map.loaded && map != null) {
				map.infoWindow.hide();
				map.graphics.clear();
			}
		}
	} catch (err) {
		alert("地图加载失败，请重试！");
	}
}
function allResources(data_a, pathUrl, type, flag, otherInformation) {
	try {
		if (map.loaded && map != null) {
			toDrawLine = [];
			toDrawLineFlag = [];
			var allData = [];
			removeGraphics(flag);
			if (data_a != null && data_a.length != 0) {
				for (var i = 0; i < data_a.length; i++) {
					var specialty = data_a[i].specialty;
					if ("传输线路" != specialty) {
						var typeFlag = data_a[i].inspectState;
						var icon = iconAdapter(data_a[i].resType, typeFlag);
						var picSymbol = new esri.symbol.PictureMarkerSymbol(
								icon, 25, 25);
						var cpPoint, webPoint, gra, infoTemplate;
						infoTemplate = new esri.InfoTemplate();
						var px = data_a[i].resLongitude;
						var py = data_a[i].resLatitude;
						cpPoint = new esri.geometry.Point(px, py,
								new esri.SpatialReference({
											wkid : 4326
										}));
						cpPoint = esri.geometry
								.geographicToWebMercator(cpPoint);
						toDrawLine.push(cpPoint);
						if (typeFlag == "已完成") {
							toDrawLineFlag.push(cpPoint);
						}
						gra = new esri.Graphic(cpPoint, picSymbol);
						infoTemplate.setTitle(data_a[i].resName);
						var ii = 0;
						var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
						if (data_a[i].resName != null
								&& data_a[i].resName != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 资源名称：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].resName + "</td></tr>";
							ii++;
						}
						if (data_a[i].specialty != null
								&& data_a[i].specialty != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 专业：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].specialty + "</td></tr>";
							ii++;
						}
						if (data_a[i].resType != null
								&& data_a[i].resType != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 类别：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].resType + "</td></tr>";
							ii++;
						}
						if (data_a[i].city != null && data_a[i].city != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 城市：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].city + "</td></tr>";
							ii++;
						}
						if (data_a[i].country != null
								&& data_a[i].country != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 区县：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].country + "</td></tr>";
							ii++;
						}
						if (data_a[i].street != null && data_a[i].street != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 街道：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].street + "</td></tr>";
							ii++;
						}
						if (data_a[i].companyName != null
								&& data_a[i].companyName != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 业主单位名称：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].companyName + "</td></tr>";
							ii++;
						}
						if (data_a[i].contactName != null
								&& data_a[i].contactName != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系人：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].contactName + "</td></tr>";
							ii++;
						}
						if (data_a[0].phone != null && data_a[0].phone != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系电话：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].phone + "</td></tr>";
							ii++;
						}
						if (data_a[i].executeObject != null
								&& data_a[i].executeObject != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 任务执行小组：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].executeObject + "</td></tr>";
							ii++;
						}
						if (data_a[i].planStartTime != null
								&& data_a[i].planStartTime != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检开始时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].planStartTime + "</td></tr>";
							ii++;
						}
						if (data_a[i].planEndTime != null
								&& data_a[i].planEndTime != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检结束时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].planEndTime + "</td></tr>";
							ii++;
						}
						if (data_a[i].inspectState != null
								&& data_a[i].inspectState != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 完成情况：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].inspectState + "</td></tr>";
							ii++;
						}
						if (data_a[i].signTime != null
								&& data_a[i].signTime != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检签到时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].signTime + "</td></tr>";
							ii++
						}
						if (data_a[i].inspectTime != null
								&& data_a[i].inspectTime != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 巡检完成时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].inspectTime + "</td></tr>";
							ii++
						}
						content += "</table>";
						content += "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
								+ data_a[i].resUrl
								+ "','"
								+ pathUrl
								+ "')\""
								+ ">查看详情</a></div>";
						infoTemplate.setContent(content);
						gra.setInfoTemplate(infoTemplate);
						var mapHeight = document.getElementById("map").style.height;
						mapHeight = mapHeight.split("px")[0] / 2 - 50;
						var informationHeight = ii * 33;
						var endInformationHeight = ii * 33;
						if (mapHeight < informationHeight) {
							endInformationHeight = mapHeight
						}
						map.infoWindow.resize(245, endInformationHeight);
						map.graphics.add(gra);
						var textSym = new esri.symbol.TextSymbol();
						textSym.setText(i + 1);
						textSym.setFont(new esri.symbol.Font("12pt"));
						textSym.setColor(new dojo.Color([128, 0, 0]));
						textSym.setOffset(20, 0);
						var graText = new esri.Graphic(cpPoint, textSym);
						map.graphics.add(graText);
						allData.push(gra);
					} else {
					}
				}
			}
			map.centerAt(averageCoordinate(allData));
			setTimeout(function() {
						if (window.confirm("是否展示资源轨迹？")) {
							drawResLine();
						}
					}, 3000);
		}
	} catch (err) {
		alert("地图渲染轨迹失败，请重试！");
	}
}

function drawResLine() {
	try {
		if (map.loaded && map != null) {
			var gras = [];
			for (var i = toDrawLineFlag.length - 1; i > 0; i--) {
				var lineSymbol = new esri.symbol.SimpleLineSymbol(
						esri.symbol.SimpleLineSymbol.STYLE_SOLID,
						new dojo.Color([255, 0, 0]), 3);
				var polyline = new esri.geometry.Polyline(new esri.SpatialReference(
						{
							wkid : 4326
						}));
				polyline.addPath([toDrawLineFlag[i], toDrawLineFlag[i - 1]]);
				var gra = new esri.Graphic(polyline, lineSymbol);
				gras.push(gra);
			}
			var runTime = setInterval(function() {
						gras.length > 0 ? function() {
							var lineGras = gras.pop();
							map.graphics.add(lineGras);
						}() : clearInterval(runTime);
					}, 1000);
		}
	} catch (err) {
		alert("模拟巡检轨迹失败，请重试！");
	}
}
function averageCoordinate(graphics) {
	graphics = graphics || [];
	var longitudes = 0, latitudes = 0, avgLongitude = 0, avgLatitude, avgCoordinate;
	if (graphics.length == 0) {
		var location = new esri.geometry.Point(104.06, 30.67);
		avgCoordinate = {
			longitude : location.x,
			latitude : location.y
		};
	} else {
		var length = 0;
		for (var i = 0; i < graphics.length; i++) {
			if ((graphics[i].geometry.x != "" || graphics[i].geometry.x != 0)
					&& (graphics[i].geometry.y != "" || graphics[i].geometry.y != 0)) {
				longitudes += graphics[i].geometry.x;
				latitudes += graphics[i].geometry.y;
				length++;
			}
		}
		avgLongitude = longitudes / length;
		avgLatitude = latitudes / length;
		avgCoordinate = {
			longitude : avgLongitude,
			latitude : avgLatitude
		};
	}
	var location = new esri.geometry.Point(avgCoordinate.longitude,
			avgCoordinate.latitude, new esri.SpatialReference({
						wkid : 4326
					}));
	return location;
}

function addCarMarker(data_a, pathUrl, flag, informationFlag,centerFlag) {
	try {
		if (map.loaded && map != null) {
			removeGraphics(flag);
			var picSymbol = new esri.symbol.PictureMarkerSymbol(iconConfig.car,
					25, 25);
			var infoTemplate = new esri.InfoTemplate();
			var px = data_a[0].longitude;
			var py = data_a[0].latitude;
			if (px == null || py == null) {
				alert("无法获取车辆坐标信息，请重试！");
			} else {
				var cpPoint = new esri.geometry.Point(px, py,
						new esri.SpatialReference({
									wkid : 4326
								}));
				cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
				var gra = new esri.Graphic(cpPoint, picSymbol);
				var infoTemplate = new esri.InfoTemplate();
				infoTemplate.setTitle(data_a[0].carNumber);
				var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 车牌号：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].carNumber + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 车辆类型：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].carType + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 所属公司：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].company + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 所属区县：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].area + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 负责人：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].driver + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系方式：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].driverContact + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 当前状态：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].carStatus + "</td></tr>";
				content = content + "</table>";
				content = content
						+ "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
						+ data_a[0].carUrl + "','" + pathUrl + "')\""
						+ ">查看详情</a></div>";
				infoTemplate.setContent(content);
				gra.setInfoTemplate(infoTemplate);
				var mapHeight = document.getElementById("map").style.height;
				mapHeight = mapHeight.split("px")[0] / 2 - 50;
				var informationHeight = 264;
				var endInformationHeight = 264;
				if (mapHeight < informationHeight) {
					endInformationHeight = mapHeight
				}
				if(centerFlag){
				map.centerAt(cpPoint);
				}
				setTimeout(function() {
					if(centerFlag){
							map.setLevel(8);
					}
							map.graphics.add(gra);
							if (informationFlag) {
								map.infoWindow.setTitle(data_a[0].carNumber);
								map.infoWindow.setContent(content);
								map.infoWindow.show(cpPoint);
							}
							map.infoWindow.resize(245, endInformationHeight);
						}, 500);
			}
		}
	} catch (err) {
		alert("地图渲染车辆信息失败，请重试！");
	}
}


function addUserMarker(data_a, pathUrl, flag, informationFlag,centerFlag) {
	try {
		if (map.loaded && map != null) {
			removeGraphics(flag);
			var picSymbol = new esri.symbol.PictureMarkerSymbol(
					iconConfig.people, 25, 25);
			var infoTemplate = new esri.InfoTemplate();
			var px = data_a[0].longitude;
			var py = data_a[0].latitude;
			if (px == null || py == null) {
				alert("无法获取人员坐标信息！");
			} else {
				var cpPoint = new esri.geometry.Point(px, py,
						new esri.SpatialReference({
									wkid : 4326
								}));
				cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
				var gra = new esri.Graphic(cpPoint, picSymbol);
				var infoTemplate = new esri.InfoTemplate();
				infoTemplate.setTitle(data_a[0].userName);
				var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 人员姓名：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].userName + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 人员编号：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].userNo + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 所属公司：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].dept + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 所属区县：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].area + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 联系电话：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+data_a[0].mobilePhone+ "</td></tr>";
				content = content + "</table>";
				content = content
						+ "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
						+ data_a[0].userUrl + "','" + pathUrl + "')\""
						+ ">查看详情</a></div>";
				infoTemplate.setContent(content);
				gra.setInfoTemplate(infoTemplate);
				var mapHeight = document.getElementById("map").style.height;
				mapHeight = mapHeight.split("px")[0] / 2 - 50;
				var informationHeight = 220;
				var endInformationHeight = 220;
				if (mapHeight < informationHeight) {
					endInformationHeight = mapHeight
				}
				if(centerFlag){
				map.centerAt(cpPoint);
				}
				setTimeout(function() {
					if(centerFlag){
							map.setLevel(8);
					}
							map.graphics.add(gra);
							if (informationFlag) {
								map.infoWindow.setTitle(data_a[0].userName);
								map.infoWindow.setContent(content);
								map.infoWindow.show(cpPoint);
							}
							map.infoWindow.resize(245, endInformationHeight);
						}, 500);
			}
		}
	} catch (err) {
		alert("地图渲染人员信息失败，请重试！");
	}
}



function addGisOrder(data_a, pathUrl, flag, informationFlag,centerFlag) {
//flag：移除之前地图上的打点  
//informationFlag ： 设置标头 使此点居中 显示信息框
//centerFlag ：点击放大或缩小
	try {
		if (map.loaded && map != null) {
			removeGraphics(flag);
            //此处增加图片颜色判断 需要 action的json 传递事前 事中 事后的判断条件
            if(data_a[0].type == 'Temp'){
            	var picSymbol = new esri.symbol.PictureMarkerSymbol(iconConfig.red_pointer, 25, 25);
            }
            if(data_a[0].type == '事中照片'){
           		var picSymbol = new esri.symbol.PictureMarkerSymbol(iconConfig.yellow_pointer, 25, 25);
            }
            if(data_a[0].type == '事后照片'){
           		var picSymbol = new esri.symbol.PictureMarkerSymbol(iconConfig.blue_pointer, 25, 25);
            }
			
			
			//var infoTemplate = new esri.InfoTemplate();
			var px = data_a[0].longitude;
			var py = data_a[0].latitude;
			if (px == null || py == null) {
				alert("无法获取GIS工单坐标信息，请重试！");
			} else {
				var cpPoint = new esri.geometry.Point(px, py,
						new esri.SpatialReference({
									wkid : 4326
								}));
				cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
				var gra = new esri.Graphic(cpPoint, picSymbol);
				var infoTemplate = new esri.InfoTemplate();
				infoTemplate.setTitle("GIS工单");
				var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 经度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].longitude + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 纬度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].latitude + "</td></tr>";
				content = content
						+ "<tr> <img id='img' src='/partner/arcGis/tempImage/"+ data_a[0].filename +"'  onclick=\"lookimage('"+pathUrl+"','"+data_a[0].filename+"')\" style='cursor:pointer'  width='120px' heigth='120px'/> "+
						 "</tr>";
				content = content + "</table>";
//				content = content
//						+ "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
//						+ data_a[0].imageURL + "','" + pathUrl + "')\""
//						+ ">查看照片</a></div>";

				infoTemplate.setContent(content);
				gra.setInfoTemplate(infoTemplate);
				var mapHeight = document.getElementById("map").style.height;
				mapHeight = mapHeight.split("px")[0] / 2 - 50;
				var informationHeight = 264;
				var endInformationHeight = 264;
				if (mapHeight < informationHeight) {
					endInformationHeight = mapHeight
				}
				if(centerFlag){
					map.centerAt(cpPoint);
				}
				setTimeout(function() {
					if(centerFlag){
							map.setLevel(15);  //值越大 越精细
					}
							map.graphics.add(gra);
							if (informationFlag) {
								map.infoWindow.setTitle("GIS工单");
								map.infoWindow.setContent(content);
								map.infoWindow.show(cpPoint);
							}
							map.infoWindow.resize(245, endInformationHeight);   //设置信息框的大小
						}, 500);
			}
		}
	} catch (err) {
		alert("地图渲染GIS工单信息失败，请重试！");
	}
}




function addErrorMarker(data_a, pathUrl, flag) {
	try {
		if (map.loaded && map != null) {
			if (data_a.length != 0 && data_a != null) {
				removeGraphics(flag);
				var icon = iconAdapter(data_a[2], null);
				var picSymbol = new esri.symbol.PictureMarkerSymbol(icon, 25,
						25);
				var infoTemplate = new esri.InfoTemplate();
				var px = data_a[7];
				var py = data_a[8];
				var cpPoint = new esri.geometry.Point(px, py,
						new esri.SpatialReference({
									wkid : 4326
								}));
				cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
				var gra = new esri.Graphic(cpPoint, picSymbol);
				var infoTemplate = new esri.InfoTemplate();
				infoTemplate.setTitle(data_a[0]);
				var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 资源名称：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 专业：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[1] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 资源类型：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[2] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 资源级别：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[3] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 地市：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[4] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 区县：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[5] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 代维小组：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[6] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 资源经纬度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[7] + "," + data_a[8] + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 实际打点经纬度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[9] + "," + data_a[10] + "</td></tr>";
				content = content + "</table>";
				infoTemplate.setContent(content);
				gra.setInfoTemplate(infoTemplate);
				map.centerAt(cpPoint);
				var mapHeight = document.getElementById("map").style.height;
				mapHeight = mapHeight.split("px")[0] / 2 - 50;
				var informationHeight = 320;
				var endInformationHeight = 320;
				if (mapHeight < informationHeight) {
					endInformationHeight = mapHeight
				}
				setTimeout(function() {
							map.setLevel(8);
							map.graphics.add(gra);
							map.infoWindow.setTitle(data_a[0]);
							map.infoWindow.setContent(content);
							map.infoWindow.show(cpPoint);
							map.infoWindow.resize(245, endInformationHeight);
						}, 500);
				var picSymbol1 = new esri.symbol.PictureMarkerSymbol(
						iconConfig.biaoshi, 25, 25);
				var px = null;
				var py = null;
				var cpPoint1;
				var flag;
				if (data_a[9] != null && data_a[10] != null) {
					px = data_a[9];
					py = data_a[10];
					flag = true;
				} else {
					px = data_a[7];
					py = data_a[8];
					flag = false;
				}
				cpPoint1 = new esri.geometry.Point(px, py,
						new esri.SpatialReference({
									wkid : 4326
								}));
				cpPoint1 = new esri.geometry.geographicToWebMercator(cpPoint1);
				if (flag) {
					var gra1 = new esri.Graphic(cpPoint1, picSymbol1);
					map.graphics.add(gra1);
				}
				var textSym = new esri.symbol.TextSymbol();
				if (flag) {
					textSym.setText("实际打点处");
				} else {
					textSym.setText("强制到站");
				}
				textSym.setFont(new esri.symbol.Font("12pt"));
				textSym.setColor(new dojo.Color([128, 0, 0]));
				textSym.setOffset(20, 0);
				var graText = new esri.Graphic(cpPoint1, textSym);
				map.graphics.add(graText);
			} else {
				alert("该资源数据错误，请重试！");
			}
		}
	} catch (err) {
		alert("地图渲染资源失败，请重试！");
	}
}



/*
 * 轨迹点查看详情  add by chenruoke
 */
function addLocusMarker(data_a, pathUrl, type, flag, otherInformation, detailFlag,centerFlag) {
		try {
		if (map.loaded && map != null) {
			removeGraphics(flag);
			var picSymbol = new esri.symbol.PictureMarkerSymbol(
					iconConfig.point, 25, 25);
			var infoTemplate = new esri.InfoTemplate();
			var px = data_a[0].longitude;
			var py = data_a[0].latitude;
			if (px == null || py == null) { //人员没有坐标
				alert("无法获取轨迹坐标信息！");
			} else { //人员有坐标时
				var cpPoint = new esri.geometry.Point(px, py,
						new esri.SpatialReference({
									wkid : 4326
								}));
				cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
				var gra = new esri.Graphic(cpPoint, picSymbol);
				var infoTemplate = new esri.InfoTemplate();
				infoTemplate.setTitle("轨迹当前信息");
							var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 轨迹点时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].sendTime + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 经度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+ data_a[0].longitude + "</td></tr>";
				content = content
						+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 纬度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
						+data_a[0].latitude+ "</td></tr>";
				content = content + "</table>";
				infoTemplate.setContent(content);
				gra.setInfoTemplate(infoTemplate);
				var mapHeight = document.getElementById("map").style.height;
				mapHeight = mapHeight.split("px")[0] / 2 - 50;
				var informationHeight = 220;
				var endInformationHeight = 220;
				if (mapHeight < informationHeight) {
					endInformationHeight = mapHeight
				}
				if(centerFlag){
				map.centerAt(cpPoint);
				}
				setTimeout(function() {
					if(centerFlag){
							map.setLevel(14);
					}
							map.graphics.add(gra);
							if (informationFlag) {
								map.infoWindow.setTitle("人员当前信息");
								map.infoWindow.setContent(content);
								map.infoWindow.show(cpPoint);
							}
							map.infoWindow.resize(245, endInformationHeight);
						}, 500);
			}
		}
	} catch (err) {
		alert("地图渲染人员信息失败，请重试！");
	}
}


//人员轨迹初始页面 add by chenruoke
function userlocus(data_a, pathUrl, type, flag, otherInformation) {
	try {
		if (map.loaded && map != null) {
			toDrawLine = [];
			toDrawLineFlag = [];
			var allData = [];
			removeGraphics(flag);
			if (data_a != null && data_a.length != 0) {
				for (var i = 0; i < data_a.length; i++) {
						var picSymbol = new esri.symbol.PictureMarkerSymbol(
							iconConfig.point, 25, 25);
						var cpPoint, webPoint, gra, infoTemplate;
						infoTemplate = new esri.InfoTemplate();
						var px = data_a[i].longitude;
						var py = data_a[i].latitude;
						cpPoint = new esri.geometry.Point(px, py,
								new esri.SpatialReference({
											wkid : 4326
										}));
						cpPoint = esri.geometry.geographicToWebMercator(cpPoint);
						toDrawLine.push(cpPoint);
						toDrawLineFlag.push(cpPoint);
						
						gra = new esri.Graphic(cpPoint, picSymbol);
						infoTemplate.setTitle("人员轨迹点-"+(1+i));
						var ii = 0;
						var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
						if (data_a[i].sendTime != null
								&& data_a[i].sendTime != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 轨迹点时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].sendTime + "</td></tr>";
							ii++;
						}
						if (data_a[i].longitude != null
								&& data_a[i].longitude != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 经度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].longitude + "</td></tr>";
							ii++;
						}
						if (data_a[i].latitude != null
								&& data_a[i].latitude != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 纬度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].latitude + "</td></tr>";
							ii++;
						}
				
						content += "</table>";
						//content += "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
						//		+ data_a[i].resUrl
						//		+ "','"
						//		+ pathUrl
						//		+ "')\""
						//		+ ">查看详情</a></div>";
						infoTemplate.setContent(content);
						gra.setInfoTemplate(infoTemplate);
						var mapHeight = document.getElementById("map").style.height;
						mapHeight = mapHeight.split("px")[0] / 2 - 50;
						var informationHeight = ii * 45;
						var endInformationHeight = ii * 45;
						if (mapHeight < informationHeight) {
							endInformationHeight = mapHeight
						}
						map.infoWindow.resize(245, endInformationHeight);
						map.graphics.add(gra);
						var textSym = new esri.symbol.TextSymbol();
						//textSym.setText(i + 1);
						textSym.setFont(new esri.symbol.Font("10pt"));
						textSym.setColor(new dojo.Color([128, 0, 0]));
						textSym.setOffset(20, 0);
						var graText = new esri.Graphic(cpPoint, textSym);
						map.graphics.add(graText);
						allData.push(gra);

				}
			}
			map.centerAt(averageCoordinate(allData));
			setTimeout(function() {
						if (window.confirm("是否展示资源轨迹？")) {
							drawResLine();
						}
					}, 100);
		}
	} catch (err) {
		alert("地图渲染轨迹失败，请重试！");
	}
}


//车辆轨迹初始页面 add by chenruoke
function carlocus(data_a, pathUrl, type, flag, otherInformation) {
	try {
		if (map.loaded && map != null) {
			toDrawLine = [];
			toDrawLineFlag = [];
			var allData = [];
			removeGraphics(flag);
			if (data_a != null && data_a.length != 0) {
				for (var i = 0; i < data_a.length; i++) {
						var picSymbol = new esri.symbol.PictureMarkerSymbol(
							iconConfig.point, 25, 25);
						var cpPoint, webPoint, gra, infoTemplate;
						infoTemplate = new esri.InfoTemplate();
						var px = data_a[i].longitude;
						var py = data_a[i].latitude;
						cpPoint = new esri.geometry.Point(px, py,
								new esri.SpatialReference({
											wkid : 4326
										}));
						cpPoint = esri.geometry.geographicToWebMercator(cpPoint);
						toDrawLine.push(cpPoint);
						toDrawLineFlag.push(cpPoint);
						
						gra = new esri.Graphic(cpPoint, picSymbol);
						infoTemplate.setTitle("人员轨迹点-"+(1+i));
						var ii = 0;
						var content = "<table style='width: 100%;border-collapse: collapse;font-size: 12px;'>";
						if (data_a[i].sendTime != null
								&& data_a[i].sendTime != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 轨迹点时间：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].sendTime + "</td></tr>";
							ii++;
						}
						if (data_a[i].longitude != null
								&& data_a[i].longitude != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 经度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].longitude + "</td></tr>";
							ii++;
						}
						if (data_a[i].latitude != null
								&& data_a[i].latitude != "") {
							content = content
									+ "<tr><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#CDE0FC'> 纬度：</td><td style='border: 1px solid #C9DEFA;padding: 6px 6px;background-color:#FFFFFF;'>"
									+ data_a[i].latitude + "</td></tr>";
							ii++;
						}
				
						content += "</table>";
						//content += "</br><div><a style='cursor:pointer' onclick= \"showDialog('"
						//		+ data_a[i].resUrl
						//		+ "','"
						//		+ pathUrl
						//		+ "')\""
						//		+ ">查看详情</a></div>";
						infoTemplate.setContent(content);
						gra.setInfoTemplate(infoTemplate);
						var mapHeight = document.getElementById("map").style.height;
						mapHeight = mapHeight.split("px")[0] / 2 - 50;
						var informationHeight = ii * 45;
						var endInformationHeight = ii * 45;
						if (mapHeight < informationHeight) {
							endInformationHeight = mapHeight
						}
						map.infoWindow.resize(245, endInformationHeight);
						map.graphics.add(gra);
						var textSym = new esri.symbol.TextSymbol();
						//textSym.setText(i + 1);
						textSym.setFont(new esri.symbol.Font("10pt"));
						textSym.setColor(new dojo.Color([128, 0, 0]));
						textSym.setOffset(20, 0);
						var graText = new esri.Graphic(cpPoint, textSym);
						map.graphics.add(graText);
						allData.push(gra);

				}
			}
			map.centerAt(averageCoordinate(allData));
			setTimeout(function() {
						if (window.confirm("是否展示资源轨迹？")) {
							drawResLine();
						}
					}, 100);
		}
	} catch (err) {
		alert("地图渲染轨迹失败，请重试！");
	}
}


function addTransLine(json,pathUrl,flag,centerFlag){
	alert("传输线路功能暂未开发，敬请期待！");
}
function addTransLineLocus(json,pathUrl,flag,centerFlag){
}

function lookimage(pathUrl,imagename){  
  var objForm = document.applyForm;    
  var url= pathUrl+"/partner/arcGis/arcGis.do?method=showGisImage&imagename="+imagename;   //调用上面的java方法  
  var openStyle="dialogHeight:500px; dialogWidth:500px; status:no; help:no; scroll:auto";  
  var result = window.showModalDialog(url,window.document,openStyle);    
  return true;  
 }  


function showDialog(url, pathUrl) {
	window.open(pathUrl + url);
}