var richmap = null;
var nowFocusCity = 'FJXM';
var colorArr = ["#E20C0C","#E2C60D","#5AEEF0","#34B443","#34B443","#34B443","#34B443"];
var netypeArr = ["#E20C0C","#E2C60D","#5AEEF0","#34B443","#34B443","#34B443","#34B443"];
function loadMap(){
	richmap = new RWing(document.getElementById("wingMap"));
	//initPoints();
	richmap.showMouseWheel(true);
	richmap.showEagle(false);
	richmap.setUserContainer(3);
	richmap.setMarkLayer(14);
	richmap.setCityList(true);
	richmap.addEventListener("onsuccess","loadMapSuccess");
	richmap.addEventListener("onzoom","showSite");
	//richmap.addEventListener("onchange","showSite");
	//richmap.addEventListener("onChangeCity","showSite");
	richmap.init();
};
function loadMapSuccess(){
	
	richmap.showCity(nowFocusCity);
	showSite();
}

function setMapCity(cityCode)
{
	richmap.showCity(cityCode);	//显示福州地图
}

var winX = 0;
var winY = 0;
function setSize(padx,pady){
	winX = document.body.clientWidth;
	winY = document.body.clientHeight;
	document.getElementById("wingMap").style.width = winX-padx;
	document.getElementById("wingMap").style.height = winY-pady;
}
/********
**直接缩进到某个点坐标中**************************************************
*********/
function zoomIntoBts(regionId,longitude,latitude){
	var screenX = richmap.getScreenXByLongitude(longitude);
	var screenY = richmap.getScreenYByLatitude(latitude);
	//richmap.setCity(cityArr[regionId]);
	richmap.zoomTo(11,screenX,screenY);
}
/********
**显示扇形**************************************************
*********/
function showSector(id,x,y,r,sa,ea,color){
	var sector = new wingSectorObj();	//声明一个扇形对象
	sector.setId(id);			//设置线路唯一ID，ID重复将覆盖前一个圆形
	sector.setBgColor(color);		//设置背景颜色
	sector.setRadius(r);			//设置半径500米
	sector.setAlpha(60);			//设置透明度，100不透明（缺省）
	sector.setStartAngel(sa);		//设置初始和X轴正向的夹角（逆时针）
	sector.setEndAngel(ea);			//设置扇形中间的夹角（逆时针）
	sector.setFnOnMouseOver(function(){alert("扇形点击事件"+sector.getId());});		//只支持在以IE为核心浏览器中的事件绑定，为了避免图形的事件和地图控制事件相遮盖，这里需要按住ctrl健再点击才能触发多边形的事件
	sector.setScreenX(x);		//设置中心点经度
	sector.setScreenY(y);		//设置中心点纬度
	//提交地图显示
	richmap.addElement(sector);			//添加该扇形到地图，但不立即显示
	//richmap.zoomTo(12,4582310,933203);	//聚焦到扇形
	
}
/********
**显示扇形VML**************************************************
*********/
var layerAngelWidth = [20,10,5,3,3];	//14,13,12,11层
function showSectorVML(id,x,y,lev,neType,sa,ea,color,container){
	var layer = richmap.getLayer();
	if(layer > lev){
		var MBR = richmap.getMBR();
		var sectorVml = "";
		var webXY = layerAngelWidth[13-layer];
		var w = 1000;
		var h = 1000;
		webXY = (webXY * 2) + "," + (webXY * 2);
		var webLeft = richmap.getPixXFromScreenX(x);
		var webTop = richmap.getPixYFromScreenY(y);
		sectorVml += "<v:shape CoordOrig=\"0,0\" id=\"angel_"+id+"\" CoordSize=\""+w+","+h+"\" "
					+ "style=\"height:"+w+"px;width:"+h+"px;position:absolute;left:"+webLeft+"px;top:"+webTop+"px;\""
					+ " fillcolor = \"\" strokecolor=\"#eeeeee\"  strokeweight = \"1\" path = \"m0,0ae0,0,"+webXY+","+sa*65536+","+ea*65536+"xe\""
					+ " onmousemove=\"showAngelInfo('"+id+"','"+neType+"')\" onmouseout=\"hideAngelInfo();\">"
					+" <v:fill type=\"solid\" color=\""+colorArr[color-1]+"\" color2=\"white\"/>"
					+" </v:shape>";
		container.innerHTML = container.innerHTML + sectorVml;
		sectorVml = null;
	}
}


/********
**显示圆**************************************************
*********/
function showOval(){
	var oval = new wingOvalObj();	//声明一个圆形对象
	oval.setId("ovalid");			//设置线路唯一ID，ID重复将覆盖前一个圆形
	oval.setBgColor("red");			//设置背景颜色
	oval.setStrokeWeight(2);		//设置边线宽度
	oval.setStrokeColor("blue");	//设置边线颜色
	oval.setRadius(500);			//设置半径500米
	oval.setAlpha(30);				//设置透明度，100不透明（缺省）
	//oval.setFnOnMouseOver(showMsg);		//只支持在以IE为核心浏览器中的事件绑定，为了避免图形的事件和地图控制事件相遮盖，这里需要按住ctrl健再点击才能触发多边形的事件
	oval.setScreenX(4582310);		//设置中心点经度
	oval.setScreenY(933203);		//设置中心点纬度
	//提交地图显示
	richmap.addElement(oval);			//添加该圆对象到地图，但不立即显示
	//richmap.zoomTo(12,4582310,933203);	//聚焦到圆形
}

/********
**显示点**************************************************
*********/
function showPoint(id,x,y,n,type,icon){
	var basePoint = new wingBasePoint();	//声明一个基点
	basePoint.setId("pointid"+x);		//设置唯一ID
	basePoint.setScreenX(x);	//设置经度
	basePoint.setScreenY(y);	//设置纬度
	
	//下面为基点添加图片来显示，当然你也可以添加多个
	var wpo = new wingPicObj();	//声明一个基点附属的图片对象
	wpo.setImageUrl(icon);
	wpo.setWidth(16);		//图片长
	wpo.setHeight(16);		//图片高
	//wpo.setX(-8);			//图片在基点的位置x
	//wpo.setY(-8);			//图片在基点的位置y
	//wpo.setTitle("提示信息");	//图片鼠标提示信息，也可以不设置
	wpo.setCursor("pointer");	//设置手型，也可以不设置
	wpo.setFnOnMouseOver(function(){showSiteInfo(id,type);});	//设置点击事件，当然，这里你也可以把这个函数写在外面，比如wpo.setFnOnClick(myclick);然后在外部写myclick这个函数
	basePoint.addObj(wpo);		//把图片增加到基点上,不然不能显示
	
	//为这个图标增加文字信息，当然你也可以添加多个
	if(richmap.getLayer() > 12){
	var wto = new wingTextObj();
	wto.setText(n);
	wto.setColor("red");				//字体颜色,也可不设置,默认黑色
	wto.setSize("12px");				//字体大小,也可不设置
	wto.setBorder("solid 1px red");		//也可不设置
	//wto.setBgColor("white");			//背景也可不设置,则默认透明
	//wto.setWidth(120);				//还可以设置长度，这里我们不设置，让文字自动撑开
	//wto.setHeight(20);				//还可以设置高度，这里我们不设置，让文字自动撑开
	wto.setPadding("2px");				//设置内联宽度,也可不设置
	wto.setCursor("pointer");			//设置手型,也可不设置
	wto.setX(12);						//文本框在基点的位置x
	wto.setY(27);						//文本框在基点的位置x
	//wto.setFnOnMouseUp(function(){alert("文本鼠标按起事件");});	//设置事件
	basePoint.addObj(wto);		//添加到基点
	}
	//最后提交地图显示
	richmap.addElement(basePoint);	//添加该点到地图，但不立即显示
	//richmap.zoomTo(12,4585283,930528);	//聚焦到该点，并显示所有点，如果有多个点显示，可以等待所有点都添加完再聚焦，可以提高效率哦
	/*
		这里如果是单独点显示且不需要聚焦，你还可以单独使用，richmap.showElement(basePoint);
		这样可以添加到地图并显示，如果是多个点显示，但不聚焦，还可以建议使用richmap.addElement()后
		用：richmap.showAllElement()方法来显示点，这是不聚焦的方法，聚焦多个点还可以用richmap.zoomToMBR()方法
	*/
}

/********
**显示大量的点**************************************************
*********/
//以下面的中心点来模拟2000个点，当然，实际应用中，这些数据很可能是您从数据库或其他地方读取出来的，但这又有什么关系呢？-_-
var baseX = 4585283;	//中心点加密经度
var baseY = 930528;		//中心点加密纬度
var points = [];		//点数组

//下面显示上面模拟到的多个点
function showPoints(id,longitude,latitude,text,color,userContainer){
	/*
		得到之前我们自己定义的用户容器，这里的参数“0”指的是第一个容器，
		比如如果我们设定了richmap.setUserContainer(2);即初始化2个用户容器
		那么取第二个容器就是richmap.getUserContainer(1);返回的是页面元素对象
	*/
	var tempElement = textObj = null;
	tempElement = document.createElement("div");
	tempElement.className = "tdpoint10";	//这个样式表中，设定的position:absolute也很重要
	//tempElement.style.cssText = "position:absolute;left:0px;top:0px;cursor:default;border:solid 0px green;width:3px;height:3px;line-height:0px;background-color:red;overflow:hidden;";
	tempElement.innerHTML = "&nbsp;";
	//getPixXFromScreenX和getPixXFromScreenY 是这里的重点，即在该地图层级得到这个点在用户容器中的像素位置
	var screenX = richmap.getScreenXByLongitude(longitude);
	var screenY = richmap.getScreenYByLatitude(latitude);
	var eleft = richmap.getPixXFromScreenX(screenX);
	var etop = richmap.getPixYFromScreenY(screenY);
	tempElement.style.backgroundColor = color;
	tempElement.style.left = eleft - 2;
	tempElement.style.top = etop - 2;
	/*当然，还可以增加点的事件
	(function(){
		tempElement["onclick"] = function(){alert("点击的是第"+id+"个点");};
	})();*/
	//下面这个很重要，就是把这个点添加到用户容器下，这样就可以显示出来了
	if(richmap.getLayer() > 12){
		textObj = document.createElement("div");
		//textObj.className = "stationText";
		textObj.style.cssText = "position:absolute;color:#003399;padding-top:2px;";
		textObj.style.left = eleft-10;
		textObj.style.top = etop;
		textObj.innerHTML = "<nobr>"+text+"</nobr>";
		userContainer.appendChild(textObj);
	}
	userContainer.appendChild(tempElement);
	tempElement = null;
}

/********
**显示对话框**************************************************
*********/
function showMsg(){
	//var sContent = "这里是对话框内显示的内容，支持HTML代码，比如一个按钮<input type=button value='mybtn'><br><br><br><br><br>我的位置";
	//var nBoxW = 300;	//指定对话框的长度
	//var nBoxH = 400;		//高度自适应
	//var bArrowModule = false;	//显示大箭头，小箭头用true
	//var nZoom = 0;			//聚焦到该对话框
	//var bClosebtn = false;		//显示关闭按钮
	//var bShadowStyle = true;	//阴影离对话框远点
	//richmap.showMsgBox(4582310,933203,sContent,nBoxW,nBoxH,bArrowModule,nZoom,bClosebtn,bShadowStyle);
	
}

/********
**获取空的基站图层**************************************************
*********/
function getEmptySitesContainer(){
	var container = richmap.getUserContainer(1);
	container.innerHTML = "";
	return container;
}

function cleaerSites(){
	var container = richmap.getUserContainer(1);
	container.innerHTML = "";
	return true;
}


/********
**获取空的小区图层**************************************************
*********/
function getEmptyCellsContainer(){
	var container = richmap.getUserContainer(0);
	container.innerHTML = "";
	return container;
}

function cleaerCells(){
	var container = richmap.getUserContainer(0);
	container.innerHTML = "";
	return true;
}

/****
**获取左上角和右下角的经纬度
****/
function getMbr(){
	var aMBR = richmap.getMBR();
	var xy = [];
	xy.push(richmap.getLongitudeByScreenX(aMBR[0]));
	xy.push(richmap.getLatitudeByScreenY(aMBR[3]));
	xy.push(richmap.getLongitudeByScreenX(aMBR[2]));
	xy.push(richmap.getLatitudeByScreenY(aMBR[1]));
	return xy;
}

function resizeMap(){
	richmap.resizeMap(winX,winY);
}