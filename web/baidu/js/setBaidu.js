var map=null;
var completed=null;
var jq=jQuery.noConflict();

/********
**huhao
**baidu map
**2012-09
*********/


//datas 为给每一个省份填充了颜色，方便观看。
var datas= [ "广西-#C8C1E3", "广东-#FBC5DC", "湖南-#DBEDC7", "贵州-#E7CCAF",
		"云南-#DBEDC7", "福建-#FEFCBF", "江西-#E7CCAF", "浙江-#C8C1E3",
		"安徽-#FBC5DC", "湖北-#C8C1E3", "河南-#DBECC8", "江苏-#DBECC8",
		"四川-#FCFBBB", "海南省-#FCFBBB", "山东-#FCFBBB", "辽宁-#FCFBBB",
		"新疆-#FCFBBB", "西藏-#E7CCAF", "陕西-#E7CCAF", "河北-#E7CCAF",
		"黑龙江-#E7CCAF", "宁夏-#FBC5DC", "内蒙古自治区-#DBEDC7", "青海-#DBEDC7",
		"甘肃-#C8C1E3", "山西-#FBC5DC", "吉林省-#C8C1E3", "北京-#FBC5DC",
		"天津-#C8C1E3", "三河市-#E7CCAF", "上海-#FCFBBB", "重庆市-#FBC5DC",
		"香港-#C8C1E3" ];
		//目前只为四川省勾勒边框
//var datas1 = ["四川-#FCFBBB"];


var datas1 = ["陕西-#E7CCAF"];



/********
**huhao
**Map Initialization 
*********/
function loadMap(){
	try{
	var map = new BMap.Map("container");  //构建地图容器 structure of  the map container
		//var point = new BMap.Point(104.114129, 37.550339); // 创建点坐标  buiding the co-ordinates of map
	//108.953098,34.2778 陕西
	//102.89916,30.367481四川
		var point = new BMap.Point(108.953098,34.2778 ); 
		map.centerAndZoom(point, 7); // 初始化地图,设置中心点坐标和地图级别。
		window.map = map;//将map变量存储在全局
		//var stdMapCtrl = new BMap.NavigationControl({type: BMAP_NAVIGATION_CONTROL_SMALL})  //创建一个特定样式的地图平移缩放控件。  BMAP_NAVIGATION_CONTROL_SMALL 仅包含平移和缩放按钮。
		//map.addControl(stdMapCtrl);  //将控件添加到地图，一个控件实例只能向地图中添加一次
		map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
		map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
		//map.addControl(new BMap.MapTypeControl()); //添加地图类型控件
		map.addControl(new BMap.NavigationControl()); //在地图上添加了鱼骨控件
		//map.addEventListener("tilesloaded",function(){alert("地图加载完毕");});
		map.enableScrollWheelZoom(); // 启用滚轮放大缩小。
		map.enableKeyboard(); // 启用键盘操作
		map.enableContinuousZoom();  //启用连续缩放效果
		map.clearOverlays();  //清楚地图上所有覆盖物
		for(var i=0;i<datas1.length;i++){  
		    getBoundary(datas1[i]);  
		} 
	}
	catch(err) { 
	alert("无法与百度地图服务器连接，请检查网络！");
	}
};

/********
**huhao
**apply colours to a drawing the map 
*********/
function getBoundary(data){ 
	//对地图进行边框渲染
    var bdary = new BMap.Boundary();  //创建行政区域搜索的对象实例。 
    bdary.get(data.split("-")[0], function(rs){  //get(name:String, callback:function)  返回行政区域的边界。 name: 查询省、直辖市、地级市、或县的名称。 callback:执行查询后，数据返回到客户端的回调函数，数据以回调函数的参数形式返回。返回结果是一个数组，数据格式如下： arr[0] = "x1, y1; x2, y2; x3, y3; ..." arr[1] = "x1, y1; x2, y2; x3, y3; ..." arr[2] = "x1, y1; x2, y2; ..." 
        var bounds;  
        var maxNum = -1;
        var maxPly;          
        for(var i = 0; i < rs.boundaries.length; i++){ //boundaries 为 Class:基础类Boundary 返回结果是一个boundaries数组 
            var ply = new BMap.Polygon(rs.boundaries[i], {//创建多边形覆盖物 
            	strokeWeight: 2, //边线的宽度，以像素为单位 。   类型:	Number 
            	strokeOpacity:0.5,//边线透明度，取值范围0 - 1 。  类型:	Number 
            	fillColor:"",
            	//fillColor:data.split("-")[1],//填充颜色。当参数为空时，折线覆盖物将没有填充效果。  类型:	String
            	strokeColor: "#000000"  //边线颜色。   类型:	String
            	});  
            map.addOverlay(ply);  //将覆盖物添加到地图中，一个覆盖物实例只能向地图中添加一次。    
           // var arrPts = ply.getPoints(); //返回多边型的点数组。(自 1.2 废弃)  
           // var arrPts= ply.getPath();//返回多边型的点数组（自1.2新增） 
           // if(arrPts.length > maxNum){  
             //   maxNum = arrPts.length;  
            //    maxPly = ply;  
            //} 
        }  
      //  if(maxPly){  
         //   map.setViewport(maxPly.getPath());  //根据提供的地理区域或坐标设置地图视野，调整后的视野会保证包含提供的地理区域或坐标。      
          //    }        
    });  
}
function drawMaps(){
	try{
			map.clearOverlays();    // 首先清理已有标记   
			if(completed!=null){
				for(var j=0;j<datas1.length;j++){  
				    getBoundary(datas1[j]);  
				}
			}
			var point = new BMap.Point(108.953098,34.2778 ); 
			map.centerAndZoom(point, 7); // 初始化地图,设置中心点坐标和地图级别。
			completed="completed";
	}
	catch(err) { 
	alert("无法与百度地图服务器连接，请检查网络！");
	}
}
/********
**huhao
**marker results be selected  
*********/
function addMarker(data_a,pathUrl,type) {  
   //block();
	try{
    map.clearOverlays();    // 首先清理已有标记   
    if(type=="siteList"){  //勾勒边框
    if(completed!=null){
		for(var j=0;j<datas1.length;j++){  
		    getBoundary(datas1[j]);  
		}
    }
    completed="completed";
    }
    // 遍历查询结果数据(data_a)   
    for (var i = 0; i < data_a.length; i++) { 
    	var errorInformation=data_a[i].error;
    	if(type=="selected"&&errorInformation!=""&&errorInformation!=null){
    		alert(errorInformation);
    	}
    	else{
        // 获取坐标(经度、纬度),在地图map上显示   
        var px = data_a[i].resLongitude;  
        var py = data_a[i].resLatitude;  
  
        var point = new BMap.Point(px, py);  
        var marker = new BMap.Marker(point);  
        map.addOverlay(marker); 
        if(type=="selected"){//单一站点设置跳动的动画
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
        map.centerAndZoom(point,15);
        }
        else{
        	var point = new BMap.Point(108.953098,34.2778 ); 
    		map.centerAndZoom(point, 7); // 初始化地图,设置中心点坐标和地图级别。
        }
        
        //marker.enableDragging(true);   
  
        // 生成标记信息(table)   
        var content = "<table class='formTable'>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 基站名称：</td><td>" + data_a[i].resName + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 专业：</td><td>" + data_a[i].specialty + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 城市：</td><td>" + data_a[i].city + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 区县：</td><td>" + data_a[i].country + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 街道：</td><td>" + data_a[i].street + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 业主单位名称：</td><td>" + data_a[i].companyName + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 联系人：</td><td>" + data_a[i].contactName + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 联系电话：</td><td>" + data_a[i].phone + "</td></tr>";  
        content += "</table>";  
        if(type=="selected"){//单个站点点击立即显示content数据列表
        marker.openInfoWindow(new BMap.InfoWindow(content));
        }
        // 捕获标记点击事件，并且显示信息   
        // 函数闭包，总是执行   
        (function () {  
            var infoWindow = new BMap.InfoWindow(content);  
            marker.addEventListener("click", function () {  
                this.openInfoWindow(infoWindow);
                //点击后跳转到相应图层
               // var point = new BMap.Point(px, py); 
               // map.centerAndZoom(point,15);
            });  
        })()  
    	}
    } 
	}catch(err) { 
    	alert("无法与百度地图服务器连接，请检查网络！");
    	}
//	unblock();
}  
/********
**huhao
**marker results of the page  
*********/
function ifOnload(data_a,pathUrl,type){
		addMarker(data_a,pathUrl,type);
	}
/********
**huhao
**changeMap  
*********/
function changeMap(){
	addblock();
	map.clearOverlays();    // 首先清理已有标记   
	var point = new BMap.Point(108.953098,34.2778 ); 
	map.centerAndZoom(point, 7); // 初始化地图,设置中心点坐标和地图级别
	if(completed!=null){
		for(var j=0;j<datas1.length;j++){  
		    getBoundary(datas1[j]);  
		}
    }
    completed="completed";
    removeblock();
}

/********
**huhao
**marker the line results of the page  
*********/
var myP1 =null;
var myP2 =null;
var myIcon=null;
function lineMarker (json,app){
//	block();
	map.clearOverlays();    // 首先清理已有标记   
		var px = null;
		    px=json[0].resLongitude;  
	    var py = null;
	   		py =json[0].resLatitude;
	    var px1 = null;
	    	px1 = json[0].endResLongitude;  
	    var py1 = null;
	    	py1 =json[0].endResLatitude;
		var type= json[0].type;  
	    	//px=104.072522;
			//py=30.608357;
			//px1=104.128864;
			//py1=30.599405;
	    var errorInformation="地图坐标错误";
    	if(py1==null||px1==null||px==null||py==null){
    		alert(errorInformation);
    	}
    	else if(type=="line"){
    		var polyline = new BMap.Polyline([
		          new BMap.Point(px,py),    //起点
				  new BMap.Point(px1,py1)  //终点
                ], {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
		    		var x=(parseFloat(px)+parseFloat(px1))/2;
		    		var y=(parseFloat(py)+parseFloat(py1))/2;
		    		map.centerAndZoom(new BMap.Point(x,y), 15);
                    map.addOverlay(polyline);
                   // var marker = new BMap.Marker(new BMap.Point(x,y));  
                   // map.addOverlay(marker);
                    // 生成标记信息(table)   
                    var content = "<table class='formTable'>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 路线名称：</td><td>" + json[0].resName + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 专业：</td><td>" + json[0].specialty + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 城市：</td><td>" + json[0].city + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 区县：</td><td>" + json[0].country + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 街道：</td><td>" + json[0].street + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 业主单位名称：</td><td>" + json[0].companyName + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 联系人：</td><td>" + json[0].contactName + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 联系电话：</td><td>" + json[0].phone + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 任务执行小组：</td><td>" + json[0].executeObject+ "</td></tr>"; 
                    content += "</table>";
                    var opts = {
                  		  //width : 250,     // 信息窗口宽度
                  		 // height: 100,     // 信息窗口高度
                  		 // title : "Hello"  // 信息窗口标题
                  		}
                  		var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                  		map.openInfoWindow(infoWindow, new BMap.Point(x,y));      // 打开信息窗口
//                  		var infoWindow1 = new BMap.InfoWindow("起点", opts);  // 创建信息窗口对象
//                  		map.openInfoWindow(infoWindow1, new BMap.Point(px,py));      // 打开信息窗口
//                  		var infoWindow2 = new BMap.InfoWindow("终点", opts);  // 创建信息窗口对象
//                  		map.openInfoWindow(infoWindow2, new BMap.Point(px1,py1));      // 打开信息窗口
//                  		var marker = new BMap.Marker(new BMap.Point(px,py));  
//                        map.addOverlay(marker);
//                    marker.openInfoWindow(new BMap.InfoWindow("起点"));
//                    var marker2 = new BMap.Marker(new BMap.Point(px1,py1));  
//                    map.addOverlay(marker);
//                marker2.openInfoWindow(new BMap.InfoWindow("终点"));

                   // marker.openInfoWindow(new BMap.InfoWindow(content));
                 // 捕获标记点击事件，并且显示信息   
                    // 函数闭包，总是执行   
                    (function () {  
                        var infoWindow = new BMap.InfoWindow(content);  
                        polyline.addEventListener("click", function () { 
                        	map.openInfoWindow(infoWindow, new BMap.Point(x,y));      // 打开信息窗口
                            //this.openInfoWindow(infoWindow);
                            //点击后跳转到相应图层
                           // var point = new BMap.Point(px, py); 
                           // map.centerAndZoom(point,15);
                        });  
                    })()

    	}
    	else if(type=="driving"){
		myP1 = new BMap.Point(px,py);    //起点
		myP2 = new BMap.Point(px1,py1);    //终点
		var x=(px+px1)/2;
		var y=(py+py1)/2;
		map.centerAndZoom(new BMap.Point(x,y), 15); 
		myIcon = new BMap.Icon("http://dev.baidu.com/wiki/static/map/API/examples/images/Mario.png", new BMap.Size(32, 70), {    //mario图片
		//myIcon = new BMap.Icon(app+"/baidu/image/u=4252636483,2348574916&fm=52&gp=0.jpg", new BMap.Size(32, 70), { 
		 
		//offset: new BMap.Size(0, -5),    //相当于CSS精灵
		    imageOffset: new BMap.Size(0, 0)    //图片的偏移量。为了是图片底部中心对准坐标点。
		  });
		var driving2 = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});    //路线实例
		driving2.search(myP1, myP2);    //显示一条可通行线路（车辆级别）
		setTimeout(function(){
			run(json);},1000); 
			
    	}
//    unblock();
}

/********
**huhao
**mario run	  
*********/
var run = function (data_a){
	try{
    var driving = new BMap.DrivingRoute(map);    //路线实例
    driving.search(myP1, myP2);
    driving.setSearchCompleteCallback(function(){
        var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过路线实例，获得一系列点的数组
        var paths = pts.length;    //获得有几个点

        var carMk = new BMap.Marker(pts[0],{icon:myIcon});//添加一个图形	
        var group=data_a[0].executeObject;
        if(group=null||group==""){
        	group="无法代维小组获取数据";
        }
        var label = new BMap.Label(group,{offset:new BMap.Size(20,-10)});//添加图形旁注释
        carMk.setLabel(label);
        map.addOverlay(carMk);
        // 生成标记信息(table)   
        var content = "<table class='formTable'>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 路线名称：</td><td>" + data_a[0].resName + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 专业：</td><td>" + data_a[0].specialty + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 城市：</td><td>" + data_a[0].city + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 区县：</td><td>" + data_a[0].country + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 街道：</td><td>" + data_a[0].street + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 业主单位名称：</td><td>" + data_a[0].companyName + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 联系人：</td><td>" + data_a[0].contactName + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 联系电话：</td><td>" + data_a[0].phone + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 任务执行小组：</td><td>" + group+ "</td></tr>"; 
        content += "</table>";  
        // 捕获标记点击事件，并且显示信息   
        // 函数闭包，总是执行   
        (function () {  
            var infoWindow = new BMap.InfoWindow(content);  
            carMk.addEventListener("click", function () {  
                this.openInfoWindow(infoWindow);
                //点击后跳转到相应图层
               // var point = new BMap.Point(px, py); 
               // map.centerAndZoom(point,15);
            });  
        })()
        i=0;
        function resetMkPoint(i){
            carMk.setPosition(pts[i]);
            if(i < paths){
                setTimeout(function(){
                    i++;
                    resetMkPoint(i);
                },100);
            }
        }
        setTimeout(function(){
            resetMkPoint(5);
        },100)

    });
	}catch(err) { 
    	alert("无法获取路线！");
    	}
}


/********
**huhao
**trach maker  
*********/
function trackMarker (json,app){
	map.clearOverlays();    // 首先清理已有标记   
	  var lineTrack=[];
	  var centerX=0;
	  var centerY=0;
	  var trackjson=json[0];
	  var sitejson=json[1];
    	if(trackjson[0].error!=null){
    		alert(trackjson[0].error);
    	}else{
    		for(var i=0;i<trackjson.length;i++){
    			var px =trackjson[i].longitude;  
    		    var py =trackjson[i].latitude;
    		    centerX=centerX+parseFloat(trackjson[i].longitude);
    		    centerY=centerY+parseFloat(trackjson[i].latitude);
    		    lineTrack.push(new BMap.Point(px,py));
    		}
//    		var px=104.072522;
//			var py=30.608357;
//			var px1=104.128864;
//			var py1=30.599405;
//			var px2=104.228864;
//			var py2=30.699405;
//			 lineTrack.push(new BMap.Point(px,py));
//			 lineTrack.push(new BMap.Point(px1,py1));
//			 lineTrack.push(new BMap.Point(px2,py2));
    		var polyline = new BMap.Polyline(

                      	lineTrack
//                  	[
//                  new BMap.Point(px,py),    //起点
//   				  new BMap.Point(px1,py1)  //终点
//					]
                , {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
    		         centerX=centerX/trackjson.length;
    		         centerY=centerY/trackjson.length;
		    		map.centerAndZoom(new BMap.Point(centerX,centerY), 15);
                    map.addOverlay(polyline);
                   // var marker = new BMap.Marker(new BMap.Point(x,y));  
                   // map.addOverlay(marker);
                    var sum=trackjson.length+1;
                    for(var j=0;j<trackjson.length;j++){
                    // 生成标记信息(table)   
                    var content = "<table class='formTable'>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 到站时间：</td><td>" + trackjson[j].signTime + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 状态：</td><td>" + trackjson[j].status + "</td></tr>";  
                    content += "</table>";
                    
                    var px=trackjson[j].longitude;
                    var py=trackjson[j].latitude;
//                    var opts = {
//                  		  //width : 250,     // 信息窗口宽度
//                  		 // height: 100,     // 信息窗口高度
//                  		 // title : "Hello"  // 信息窗口标题
//                  		}
//                  		var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
//                  		map.openInfoWindow(infoWindow, new BMap.Point(json[0].longitude,json[0].latitude));      // 打开信息窗口
//                  		map.openInfoWindow(infoWindow, new BMap.Point(json[1].longitude,json[1].latitude));      // 打开信息窗口
                  		var carMk = new BMap.Marker(new BMap.Point(px,py));//添加一个图形	
                  		sum--;
                        var label = new BMap.Label(sum,{offset:new BMap.Size(20,-10)});//添加图形旁注释
                        carMk.setLabel(label);
                        map.addOverlay(carMk);
                        //carMk.openInfoWindow(new BMap.InfoWindow(content));
                 // 捕获标记点击事件，并且显示信息   
                    // 函数闭包，总是执行   
                    (function () {  
                        var infoWindow = new BMap.InfoWindow(content);  
                        carMk.addEventListener("click", function () { 
                        	this.openInfoWindow(infoWindow, new BMap.Point(px,py));      // 打开信息窗口
                            //this.openInfoWindow(infoWindow);
                            //点击后跳转到相应图层
                           // var point = new BMap.Point(px, py); 
                           // map.centerAndZoom(point,15);
                        });  
                    })()
                    }

    	}
    	
    	
    	//渲染资源数据
    	if(sitejson[0].error!=null){
    		alert(sitejson[0].error);
    	}else{
    		if("传输线路"!=sitejson[0].specialty){
    	// 获取坐标(经度、纬度),在地图map上显示   
        var px = sitejson[0].resLongitude;  
        var py = sitejson[0].resLatitude;  
  
        var point = new BMap.Point(px, py);  
        var marker = new BMap.Marker(point);  
        map.addOverlay(marker); 
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
        map.centerAndZoom(point,15);
        //marker.enableDragging(true);   
  
        // 生成标记信息(table)   
        var content = "<table class='formTable'>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 基站名称：</td><td>" + sitejson[0].resName + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 专业：</td><td>" + sitejson[0].specialty + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 城市：</td><td>" + sitejson[0].city + "</td></tr>";  
        content = content + "<tr><td style='background-color:#CDE0FC'> 区县：</td><td>" + sitejson[0].country + "</td></tr>";  
//        content = content + "<tr><td style='background-color:#CDE0FC'> 街道：</td><td>" + sitejson[0].street + "</td></tr>";  
//        content = content + "<tr><td style='background-color:#CDE0FC'> 业主单位名称：</td><td>" + sitejson[0].companyName + "</td></tr>";  
//        content = content + "<tr><td style='background-color:#CDE0FC'> 联系人：</td><td>" + sitejson[0].contactName + "</td></tr>";  
//        content = content + "<tr><td style='background-color:#CDE0FC'> 联系电话：</td><td>" + sitejson[0].phone + "</td></tr>";
        content = content + "<tr><td style='background-color:#CDE0FC'> 巡检小组：</td><td>" + sitejson[0].deptname + "</td></tr>";
        content = content + "<tr><td style='background-color:#CDE0FC'> 开始巡检时间：</td><td>" + sitejson[0].planStartTime + "</td></tr>";
        content = content + "<tr><td style='background-color:#CDE0FC'> 结束巡检时间：</td><td>" + sitejson[0].planEndTime + "</td></tr>";
        content = content + "<tr><td style='background-color:#CDE0FC'> 巡检完成情况：</td><td>" + sitejson[0].inspectState + "</td></tr>";
        content += "</table>";  
        marker.openInfoWindow(new BMap.InfoWindow(content));
        // 捕获标记点击事件，并且显示信息   
        // 函数闭包，总是执行   
        (function () {  
            var infoWindow = new BMap.InfoWindow(content);  
            marker.addEventListener("click", function () {  
                this.openInfoWindow(infoWindow);
                //点击后跳转到相应图层
               // var point = new BMap.Point(px, py); 
               // map.centerAndZoom(point,15);
            });  
        })()
    	}
    	else{
    		var px = null;
		    px=sitejson[0].resLongitude;  
		    var py = null;
		   		py =sitejson[0].resLatitude;
		    var px1 = null;
		    	px1 = sitejson[0].endResLongitude;  
		    var py1 = null;
		    	py1 =sitejson[0].endResLatitude;
			var type= sitejson[0].type; 
    		var polyline = new BMap.Polyline([
		          new BMap.Point(px,py),    //起点
				  new BMap.Point(px1,py1)  //终点
                ], {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
		    		var x=(parseFloat(px)+parseFloat(px1))/2;
		    		var y=(parseFloat(py)+parseFloat(py1))/2;
		    		map.centerAndZoom(new BMap.Point(x,y), 15);
                    map.addOverlay(polyline);
                   // var marker = new BMap.Marker(new BMap.Point(x,y));  
                   // map.addOverlay(marker);
                    // 生成标记信息(table)   
                    var group=json[0].deptname;
                    if(group=null||group==""){
                    	group="无法代维小组获取数据";
                    }
                    var content = "<table class='formTable'>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 线路名称：</td><td>" + sitejson[0].resName + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 专业：</td><td>" + sitejson[0].specialty + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 城市：</td><td>" + sitejson[0].city + "</td></tr>";  
                    content = content + "<tr><td style='background-color:#CDE0FC'> 区县：</td><td>" + sitejson[0].country + "</td></tr>";  
//                    content = content + "<tr><td style='background-color:#CDE0FC'> 街道：</td><td>" + sitejson[0].street + "</td></tr>";  
//                    content = content + "<tr><td style='background-color:#CDE0FC'> 业主单位名称：</td><td>" + sitejson[0].companyName + "</td></tr>";  
//                    content = content + "<tr><td style='background-color:#CDE0FC'> 联系人：</td><td>" + sitejson[0].contactName + "</td></tr>";  
//                    content = content + "<tr><td style='background-color:#CDE0FC'> 联系电话：</td><td>" + sitejson[0].phone + "</td></tr>";
                    content = content + "<tr><td style='background-color:#CDE0FC'> 巡检小组：</td><td>" + sitejson[0].deptname + "</td></tr>";
                    content = content + "<tr><td style='background-color:#CDE0FC'> 开始巡检时间：</td><td>" + sitejson[0].planStartTime + "</td></tr>";
                    content = content + "<tr><td style='background-color:#CDE0FC'> 结束巡检时间：</td><td>" + sitejson[0].planEndTime + "</td></tr>";
                    content = content + "<tr><td style='background-color:#CDE0FC'> 巡检完成情况：</td><td>" + sitejson[0].inspectState + "</td></tr>";
                    content += "</table>"; 
                    var opts = {
                  		  //width : 250,     // 信息窗口宽度
                  		 // height: 100,     // 信息窗口高度
                  		 // title : "Hello"  // 信息窗口标题
                  		}
                  		var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                  		map.openInfoWindow(infoWindow, new BMap.Point(x,y));      // 打开信息窗口
//    		                                  		var infoWindow1 = new BMap.InfoWindow("起点", opts);  // 创建信息窗口对象
//    		                                  		map.openInfoWindow(infoWindow1, new BMap.Point(px,py));      // 打开信息窗口
//    		                                  		var infoWindow2 = new BMap.InfoWindow("终点", opts);  // 创建信息窗口对象
//    		                                  		map.openInfoWindow(infoWindow2, new BMap.Point(px1,py1));      // 打开信息窗口
//    		                                  		var marker = new BMap.Marker(new BMap.Point(px,py));  
//    		                                        map.addOverlay(marker);
//    		                                    marker.openInfoWindow(new BMap.InfoWindow("起点"));
//    		                                    var marker2 = new BMap.Marker(new BMap.Point(px1,py1));  
//    		                                    map.addOverlay(marker);
//    		                                marker2.openInfoWindow(new BMap.InfoWindow("终点"));

                   // marker.openInfoWindow(new BMap.InfoWindow(content));
                 // 捕获标记点击事件，并且显示信息   
                    // 函数闭包，总是执行   
                    (function () {  
                        var infoWindow = new BMap.InfoWindow(content);  
                        polyline.addEventListener("click", function () { 
                        	map.openInfoWindow(infoWindow, new BMap.Point(x,y));      // 打开信息窗口
                            //this.openInfoWindow(infoWindow);
                            //点击后跳转到相应图层
                           // var point = new BMap.Point(px, py); 
                           // map.centerAndZoom(point,15);
                        });  
                    })()
    	}
}
    	
}    	
/********
**huhao
**block the window 
*********/
function addblock(){
		jq.blockUI({
			css: { 
	        border: 'none', 
	        padding: '15px', 
	        backgroundColor: '#000', 
	        '-webkit-border-radius': '10px', 
	        '-moz-border-radius': '10px', 
	        opacity: .5, 
	        color: '#fff',
	        display:'absolute'
	    } }); 
}


/********
**huhao
**remove the block 
*********/
function removeblock(){
	jq.unblockUI(500);
	jq.growlUI('','加载成功！',500);
}


///********
//**huhao
//**network test
//*********/
//function getbandwidth()
//{
//　　var filesize = 3.65　　//measured in KB
//　　var l = 2　　　　
//　　var endtime = new Date()
//　　var speed = Math.round(filesize*1000)/(endtime - starttime)
//　　　if (speed>10)
//　　　　window.location='za/default.ASP?ads=enabled'
//　　　 else
//　　　　window.location='za/default.asp'
//}

///********
//**huhao
//**network test
//*********/
//function getbandwidth(){
//	ping({
//	url : 'http://map.baidu.com', 
//	beforePing : function(){$('#msg').html('')},
//	afterPing : function(ping){$('#msg').html(ping)}, 
//	interval : 1
//	});
//
//}
