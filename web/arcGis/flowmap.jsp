<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="app" scope="page" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
 <script type="text/javascript">
		var app='${app}';
		var list='${list}';
 </script>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!--引用百度地图API-->
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=49qpF8r45NsQiH4LV4Kbvq9t"></script>
    <script type="text/javascript" src="${app}/arcGis/js/setBaiduGis.js"></script>
    <script type="text/javascript" src="${app}/arcGis/js/iconConfig.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
  </head>

	
  <body onload="ff();" onResize="toResize();" >
    <!--百度地图容器-->
    <div style="width:100%;height:100%;border:#ccc solid 1px;font-size:12px" id="map"></div>
  </body>
  <script type="text/javascript">
 function ff(){
		    /**
		    *chenruoke
		    * 设置地图自适应
		    **/
		    if(-[1,]){ 
			var parentDivHeight=parent.document.getElementById("center").style.height;
			document.getElementById("map").style.height=parentDivHeight;
		    }
		    else{
		    	var parentDivHeightIe=parent.document.getElementById("center").offsetHeight;
		    	var parentDivWidthIe=parent.document.getElementById("center").offsetWidth;
		    	parentDivHeightIe=parentDivHeightIe-90;
		    	parentDivWidthIe=parentDivWidthIe-550;
				document.getElementById("map").style.height=parentDivHeightIe+"px";
				document.getElementById("map").style.width=parentDivWidthIe+"px";
		    }
			/**
			* chenruoke
		    * 加载地图开始
		    **/
			initMap();
			var returnJson=parent.returnJson;
			var json = eval(returnJson);
			for(var i=0;i<json.length;i++){
				var photo=json[i];
				alert(photo.photoSubType);
				if(photo.photoSubType=='netResInspect'){
					var icons = "${app}/baidu/image/red.png"; //这个是你要显示坐标的图片的相对路径
					var icon = new BMap.Icon(icons, new BMap.Size(15, 15)); //显示图标大小
				}else{
					var icons = "${app}/baidu/image/blue.png"; //这个是你要显示坐标的图片的相对路径
					var icon = new BMap.Icon(icons, new BMap.Size(15, 15)); //显示图标大小
				}
				var new_point = new BMap.Point(photo.longitude,photo.latitude);
				var marker = new BMap.Marker(new_point);  // 创建标注
				marker.setIcon(icon);//设置标签的图标为自定义图标
				map.addOverlay(marker);              // 将标注添加到地图中
				map.panTo(new_point); 
			}
			
		}
		
		/**
		* chenruoke
	    * 设置地图自适应
	    **/
		function toResize(){
	    if(map!=null){
				if(map.loaded){
				    if(-[1,]){ 
					var parentDivHeight=parent.document.getElementById("center").style.height;
					var parentDivWidth=parent.document.getElementById("center").style.width;
					document.getElementById("map").style.height=parentDivHeight;
					document.getElementById("map").style.width=parentDivWidth;
				    }
				    else{
				    	//var parentDivHeightIe=parent.document.getElementById("center").offsetHeight;
				    	var parentDivWidthIe=parent.document.getElementById("center").offsetWidth;
				    	//var parentScrollWidthIe=parent.document.getElementById("center").scrollWidth;
				    	//var parentEastWidthIe=parent.document.getElementById("east").offsetWidth;
						document.getElementById("map").style.width=parentDivWidthIe+"px";
				    }
				}
			}
	    }
	    
  
  </script>
  
  
</html>