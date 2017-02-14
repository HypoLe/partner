/*
 * ! setArcGis.js @author huhao @company Boco
 */var map = null;
var siteGraphics = [];
var toDrawLine = [];
var toDrawLineFlag = [];

var cityData = ["江西","南昌", "上饶", "鹰潭", "抚州", "九江", "景德镇", "宜春", "新余", "萍乡", "吉安","赣州"];
var cityForUrl = ["jiangxi","nanchang", "shangrao", "yingtan", "fuzhou", "jiujiang",
		"jingdezhen", "yichun", "xinyu", "pingxiang", "jian","ganzhou"];
var cityUrl = {
		jiangxi  : ["115.676082","27.757258"],
		nanchang : ["115.893528", "28.689578"],
		shangrao : ["117.955464", "28.457623"],
		yingtan : ["117.03545", "28.24131"],
		fuzhou : ["116.360919", "27.954545"],
		jiujiang : ["115.999848", "29.71964"],
		jingdezhen : ["117.186523", "29.303563"],
		yichun : ["114.400039", "27.81113"],
		xinyu : ["114.947117", "27.822322"],
		pingxiang : ["113.859917", "27.639544"],
		jian : ["114.992039", "27.113848"],
		ganzhou : ["114.935909", "25.845296"]
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
	} else if (data == "2G基站&3G基站") {
		if (typeFlag == true) {
			icon = iconConfig._2g3g1;
		} else if (!typeFlag == true) {
			icon = iconConfig._2g3g2;
		} else {
			icon = iconConfig._2g3g;
		}
	} else if (data == "WLAN") {
		if (typeFlag == true) {
			icon = iconConfig.wlan1;
		} else if (typeFlag == false) {
			icon = iconConfig.wlan2;
		} else {
			icon = iconConfig.wlan;
		}
	} else if (data == "直放站室分") {
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
	}
	return icon;
}
function cityMarker(point, setCarInformation, setOilInformation,
		setCityInformation, setCarType, setOilType) {
	try {
		var picSymbol = new esri.symbol.PictureMarkerSymbol(iconConfig.biaoshi,
				40, 40);
		var cpPoint = new esri.geometry.Point(point[0], point[1],
				new esri.SpatialReference({
							wkid : 4326
						}));
		//cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
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

function loadMap() {
	try {
		var initExtent = new esri.geometry.Extent({
					"xmin" : 115.00387712621024,
					"ymin" : 26.890283759271323,
					"xmax" : 117.08105140585984,
					"ymax" : 28.623292799037632,
					"spatialReference" : {
						"wkid" : 4326
					}
				});
		map = new esri.Map("map", {
					extent :initExtent
					//extent : new esri.geometry.geographicToWebMercator(initExtent)
				});
		var tiledLayer = new esri.layers.ArcGISTiledMapServiceLayer("http://10.181.163.199:8399/arcgis/rest/services/sfp/jxmap_L10_base_v2/MapServer");
//		var tiledLayer = new esri.layers.ArcGISTiledMapServiceLayer("http://10.181.163.199:8399/arcgis/rest/services/JiangXi/MapServer");
		
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
function mapCenterAndZoom(flag, flag1) {
	try {
		if (map.loaded && map != null) {
			if (flag) {
				removeGraphics(flag);
			}
			var location = null;
			if (flag1) {
				location = new esri.geometry.Point(eval(cityUrl.fuzhou[0]),
						eval(cityUrl.fuzhou[1]), new esri.SpatialReference({
									wkid : 4326
								}));
				//location = new esri.geometry.geographicToWebMercator(location);
				map.centerAndZoom(location, 0);
			} else {
				location = new esri.geometry.Point(eval(cityUrl.jiangxi[0]),
						eval(cityUrl.jiangxi[1]), new esri.SpatialReference({
									wkid : 4326
								}));
				//location = new esri.geometry.geographicToWebMercator(location);
				map.centerAndZoom(location, 0);
			}
		}
	} catch (err) {
		alert("地图加载失败，请重试！");
	}
}
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
			var icon = iconAdapter(data_a[0].resType, typeFlag);
			var picSymbol = new esri.symbol.PictureMarkerSymbol(icon, 25, 25);
			var infoTemplate = new esri.InfoTemplate();
			var px = data_a[0].resLongitude;
			var py = data_a[0].resLatitude;
			var cpPoint = new esri.geometry.Point(px, py,
					new esri.SpatialReference({
								wkid : 4326
							}));
			//cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
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
						map.setLevel(8);
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
			//	p1 = new esri.geometry.geographicToWebMercator(p1);
			//	p2 = new esri.geometry.geographicToWebMercator(p2);
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
				//webPoint = new esri.geometry.geographicToWebMercator(webPoint);
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
						//cpPoint = esri.geometry
						//		.geographicToWebMercator(cpPoint);
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
			//	cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
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
				//cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
				var gra = new esri.Graphic(cpPoint, picSymbol);
				var infoTemplate = new esri.InfoTemplate();
				infoTemplate.setTitle(data_a[0].carNumber);
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
				//cpPoint = new esri.geometry.geographicToWebMercator(cpPoint);
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
				//cpPoint1 = new esri.geometry.geographicToWebMercator(cpPoint1);
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


function addTransLine(json,pathUrl,flag,centerFlag){
	alert("传输线路功能暂未开放，敬请期待！");
}
function addTransLineLocus(json,pathUrl,flag,centerFlag){
}


function showDialog(url, pathUrl) {
	window.open(pathUrl + url);
}


