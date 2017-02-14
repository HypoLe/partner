/**
//add by chenruoke
使用百度地图API重构山东GIS需求
**/

//创建和初始化地图函数：
var map;
//常用地市中心经纬度
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
	jinan : ["117.01166000", "36.66147500"],
	zibo : ["118.0672050000","36.8151050000"],
	changchun : ["125.357321","43.893221"]
};
    function initMap(){
      createMap();//创建地图
      setMapEvent();//设置地图事件
      addMapControl();//向地图添加控件
//      addMapOverlay();//向地图添加覆盖物
	  
    }
    function createMap(){
      map = new BMap.Map("map"); 
      //初始加载地图中心位置 及缩放级别 越大越精细
      map.centerAndZoom(new BMap.Point(cityUrl.zibo[0],cityUrl.zibo[1]),9);
    }
    function setMapEvent(){
      map.enableScrollWheelZoom();
      map.enableKeyboard();
      map.enableDragging();
      map.enableDoubleClickZoom()
    }
    function addClickHandler(target,window){
      target.addEventListener("click",function(){
        target.openInfoWindow(window);
      });
    }
      //一个样例点 学习用注释
//    function addMapOverlay(){
//      var markers = [
//        {content:"我的备注",title:"我的标记",imageOffset: {width:-92,height:-21},position:{lat:43.893221,lng:125.357321}}
//      ];
//      for(var index = 0; index < markers.length; index++ ){
//        var point = new BMap.Point(markers[index].position.lng,markers[index].position.lat);
//        var marker = new BMap.Marker(point,{icon:new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png",new BMap.Size(20,25),{
//          imageOffset: new BMap.Size(markers[index].imageOffset.width,markers[index].imageOffset.height)
//        })});
//        var label = new BMap.Label(markers[index].title,{offset: new BMap.Size(25,5)});
//        var opts = {
//          width: 200,
//          title: markers[index].title,
//          enableMessage: false
//        };
//        var infoWindow = new BMap.InfoWindow(markers[index].content,opts);
//        marker.setLabel(label);
//        addClickHandler(marker,infoWindow);
//        map.addOverlay(marker);
//      };
//    }
    
    //向地图添加控件
   function addMapControl(){
      var scaleControl = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT});
      scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
      map.addControl(scaleControl);
      var navControl = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:1});
      map.addControl(navControl);
      var overviewControl = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_TOP_RIGHT,isOpen:false});
      map.addControl(overviewControl);
      //构造全景控件
      var stCtrl = new BMap.PanoramaControl(); 
	  stCtrl.setOffset(new BMap.Size(20, 40));
	  map.addControl(stCtrl);//添加全景控件
	  //构造2D图与卫星图切换按钮
	  var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
	  map.addControl(mapType1);          //2D图，卫星图

    }
    

 //---------------------------------------------------------地图初始化end---------------------------------------------   

//根据url打开新页面    
function showDialog(url, pathUrl) {
	window.open(pathUrl + url);
}  
//移除之前屏幕上的点
function removeGraphics(flag) {
	try {
		if (flag) {
			if (map.loaded && map != null) {
				//map.infoWindow.hide();
				//map.graphics.clear();
				map.clearOverlays(); 
			}
		}
	} catch (err) {
		alert("地图加载失败，请重试！");
	}
}
    
    
//资源加载并打点    
function addMarker(data_a, pathUrl, type, flag, otherInformation, detailFlag,centerFlag) {
//flag：移除之前地图上的打点  
//informationFlag ： 设置标头 使此点居中 显示信息框
//centerFlag ：点击居中放大或缩小 （true or  false）
 	try {
 		if(map.loaded && map != null){
 			removeGraphics(flag);
 		    var typeFlag = null;
			if (otherInformation != null && otherInformation.length != 0) {
				if (otherInformation.length == 11) {
					typeFlag = otherInformation[10];
				} else if (otherInformation.length == 10) {
					typeFlag = otherInformation[9];
				}
			}
 			//alert(typeFlag);
 			var px = data_a[0].resLongitude;
			var py = data_a[0].resLatitude;
		    var poi = new BMap.Point(px,py);

			//拼接信息框内容 纯html写法
			var i = 0;
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
		    
		    //创建检索信息窗口对象
		    var searchInfoWindow = null;
			searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
					title  : data_a[0].resName,      //标题
					width  : 270,                   //宽度
					height : i*36,              //高度
					//panel  : "panel",         //检索结果面板
				    enableAutoPan : true,       //自动平移 当信息框显示时超出显示范围自动平移到合适位置
					searchTypes   :[
						//BMAPLIB_TAB_SEARCH,   //周边检索
						//BMAPLIB_TAB_TO_HERE,  //到这里去
						//BMAPLIB_TAB_FROM_HERE //从这里出发
					]
				});
			var icon = iconAdapter(data_a[0].resType, typeFlag);    //创建自定义图标   
			var myIcon = new BMap.Icon(icon, new BMap.Size(50,50)); //生成图标于地图上 
		    var marker = new BMap.Marker(poi,{icon:myIcon}); 		//创建marker对象
		   // marker.enableDragging();								 //marker可拖拽
		    marker.addEventListener("click", function(e){
			    searchInfoWindow.open(marker);
		    })
		    map.addOverlay(marker); //在地图中添加marker
		    if(centerFlag){
		    	map.centerAndZoom(poi, 16);  //以此点为中心缩放
				map.enableScrollWheelZoom(); 
				searchInfoWindow.open(marker);
		    }
	    }
    } catch (err) {
		alert("地图渲染资源"+data_a[0].resName+"失败，请重试！");
	}
	
}






//根据类型提供不同的显示图标
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
			icon = iconConfig.station_red;
		} else if (typeFlag == false) {
			icon = iconConfig.station_red;
		} else {
			icon = iconConfig.station_red;
		}
	}else if (data == "基站-A类") {
		if (typeFlag == true) {
			icon = iconConfig.station_blue;
		} else if (typeFlag == false) {
			icon = iconConfig.station_blue;
		} else {
			icon = iconConfig.station_blue;
		}
	}else if (data == "基站-B类") {
		if (typeFlag == true) {
			icon = iconConfig.station_green;
		} else if (typeFlag == false) {
			icon = iconConfig.station_green;
		} else {
			icon = iconConfig.station_green;
		}
	}else if (data == "基站-C类") {
		if (typeFlag == true) {
			icon = iconConfig.station_yellow;
		} else if (typeFlag == false) {
			icon = iconConfig.station_yellow;
		} else {
			icon = iconConfig.station_yellow;
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
    
    
    