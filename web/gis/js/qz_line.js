var richmap = null;		//地图对象
var tdMarke = -3;
var wlanMarke = -4;
var winX = 0;
var winY = 0;//当前区域指标汇总
//var colorArr = ["#E20C0C","#E2C60D","#5AEEF0","#5955DE","#34B443","#FADDBD","#DBDDDC"];
var colorArr = ["#E20C0C","#E2C60D","#5AEEF0","#34B443","#34B443","#34B443","#34B443"];
function loadMap(city){
	if(city!=null){nowFocusCity=city;}
	if(countInfoDiv == null){
		countInfoDiv = document.createElement("div");
		countInfoDiv.className = "countinfo";
		countInfoDiv.innerHTML = "当前区域2G指标汇总..";
		//countInfoDiv.style.display = "none";
		document.body.appendChild(countInfoDiv);
	}
	//if(tdcountInfoDiv == null){
	//	tdcountInfoDiv = document.createElement("div");
	//	tdcountInfoDiv.className = "tdcountinfo";
	//	tdcountInfoDiv.innerHTML = "当前区域TD指标汇总..";
	//	document.body.appendChild(tdcountInfoDiv);
	//}
	setSize();
	var outstr = "<b>图例：</b>　";
	outstr += "<span style='color:"+colorArr[0]+"'>■</span>　一级告警　　";
	outstr += "<span style='color:"+colorArr[1]+"'>■</span>　二级告警　　";
	outstr += "<span style='color:"+colorArr[2]+"'>■</span>　三级告警　　";
	//outstr += "<span style='color:"+colorArr[3]+"'>■</span>　正　常<br>";
	outstr += "<span style='color:"+colorArr[4]+"'>■</span>　优　良　　";
	//outstr += "<span style='color:"+colorArr[5]+"'>■</span>　无告警<br>";
	//outstr += "<span style='color:"+colorArr[6]+"'>■</span>　无数据<br>";
	$("colordefined").innerHTML = outstr;
	richmap = new RWing(document.getElementById("wingMap"));
	richmap.showMarkBtn(true);		//设置显示标点按钮
	richmap.showEagle(true);		//设置显示鹰眼
	richmap.showEagleMax(false);	//设置显示鹰眼的时候不最大化
	richmap.setMarkLayer(3);		//设置要到至少到第8层才能进行标点(缺省第8层)
	richmap.setCityList(true);		//设置显示城市选择列表
	richmap.setRectSelect(false);	//设置显示框选按钮
	//richmap.showNoteBtn(true);		//设置显示便签按钮
	//richmap.showPolygonBtn(true);		//设置显示画图按钮
	//richmap.showOvalBtn(true);		//设置显示画圆按钮
	richmap.showScrollBar(true);	//
	richmap.setUserContainer(2);
	richmap.addEventListener("onsuccess","loadMapSuccess");	//挂接地图成功初始化事件
	richmap.addEventListener("onrectselect","rectSelectReturn");	//挂接框选返回函数▲
	//richmap.addEventListener("onzoom","showLine");
	richmap.addEventListener("onmark","markReturn");
	richmap.addEventListener("onChangeCity","resetSource");
	richmap.init();
};
var nowFocusCity = "FJFZ";
function resetSource(){
	var city = richmap.getCity();
	city = city.replace(/-.*?$/g,"");
	if(nowFocusCity != city){
		nowFocusCity = city;
		loadSite();
	}
}
function setCity(city) {
	nowFocusCity=city;
}
function lockRegion(z,x,y) {
	richmap.zoomTo(z,x,y);
}
function setSize(){
	winX = document.body.clientWidth;
	winY = document.body.clientHeight;
	$("wingMap").style.width = winX - 180;
	$("wingMap").style.height = winY - 2 - 20;
	$("lineInfo").style.left = winX - 180 + 5;
	$("lineInfo").style.top = 240;
	$("lineInfo").style.height = winY - 250;
	//$("colordefined").style.left = winX - 180 + 10;
	//$("colordefined").style.top = 240;
	//$("colordefined").style.height = winY - 250;
	$("pageInfo").style.left = winX - 180 + 10;
	$("pageInfo").style.top = 400 + (winY - 400)*0.5 - 90;
	countInfoDiv.style.left = winX - 180 + 5;
	countInfoDiv.style.top = 1;
	//tdcountInfoDiv.style.left = winX - 180 + 5;
	//tdcountInfoDiv.style.top = 190;
}
window.onresize = function(){
	setSize();
	richmap.resizeMap(winX - 180,winY - 2 - 20);
}
function markReturn(x,y){
	$("sss").innerText += x + " " + y + " ";
}
//框选结束后的动作
function rectSelectReturn1(l,b,r,t){
	
}
function showStation(){
	//4582235 933151
	var tempObj = new wingBasePoint("yjc",4582235,933151);
	var tempPic = new wingPicObj();
	tempPic.sImageSrc = "yjc.gif";
	tempPic.nImageWidth = 40;	//图片长
	tempPic.nImageHeight = 40;	//图片高
	tempPic.sAlt = "应急车";
	tempPic.nScreenX = -20;			//图片热点相对坐标x
	tempPic.nScreenY = -20;			//图片热点相对坐标y
	tempObj.addObj(tempPic);
	//var tempText = new wingTextObj();
//	tempText.sText = begin;
//	tempText.nScreenX = 12;
//	tempText.nScreenY = -6;
//	tempText.sSize = "12px";
//	tempText.sColor = "#333333";
//	tempText.sBorder = "solid 1px #333333";
//	tempText.sPadding = "2px";
//	tempText.sBgColor = "#ffffff";
//	tempText.addObj(tempObj1);
	richmap.showElement(tempObj);
}
var linePathArr = [];
linePathArr["FJFZ_1"] = "4585637 931852 4585636 931722 4585778 931719 4585917 931717 4585947 931834 4585993 932026 4586012 932094 4586022 932138 4586024 932157 4586034 932243 4586043 932292 4586049 932338 4586054 932378 4586061 932411 4586066 932475 4586081 932604 4586102 932773 4586121 932940 4586136 933055 4586155 933204 4586176 933396 4586189 933490 4586202 933607 4586221 933743 4586239 933862 4586252 933948 4586264 934007 4586275 934100 4586300 934176 4586321 934240 4586378 934223 4586437 934209 4586506 934180 4586561 934164 4586623 934158 4586719 934146 4586795 934135 4586903 934124 4586985 934104 4587069 934087 4587156 934066 4587201 934057 4587280 934050 4587368 934045 4587454 934046 4587515 934050 4587557 934064 4587631 934084 4587657 934097 4587766 934117 4587854 934131 4587936 934144 4588003 934153 4588121 934161 4588250 934179 4588403 934195 4588575 934213 4588633 934219 4588697 934234 4588807 934264 4588898 934287 4589007 934302 4589136 934315 4589230 934318 4589381 934367 4589375 934368 4589771 934503 4589917 934589 4590471 934819 4590844 934975 4591002 935035 4591087 935068 4591604 935464 4592058 935647 4592416 936055 4592614 936238 4592632 936324 4592667 936366 4592667 936409 4592757 936509 4592935 936572 4593078 936717 4593264 937038 4594086 937562 4594347 937620 4594700 937712 4595114 937740 4595379 937753 4595713 937722 4596184 937740 4596748 937873 4597340 938234 4598448 939149 4598844 939583 4599102 940019 4599258 940164 4599544 940290 4599714 940460 4599885 940758 4600095 940856 4600534 941102 4600421 941297";
linePathArr["FJFZ_2"] = "4585092 934648 4585066 934639 4585005 934648 4584922 934632 4584807 934632 4584647 934642 4584606 934648 4584495 934620 4584427 934587 4584289 934465 4584230 934415 4584116 934258 4583445 933884 4583392 933825 4583317 933786 4583125 933741 4582559 933474 4582467 933392 4582329 933213 4582193 933058 4581813 932753 4581031 932097 4580995 932012 4580977 931759 4580992 931973 4581024 932087 4581038 932106 4582220 933089 4582288 933167";
linePathArr["FJFZ_3"] = "4587622 934082 4587559 934063 4587515 934049 4587449 934046 4587301 934049 4587192 934060 4587104 934078 4586987 934103 4586921 934125 4586900 934127 4586905 934203 4586913 934312 4586918 934417 4586923 934501 4586929 934593 4586928 934633 4586934 934655 4586947 934746 4586960 934866 4586972 934980 4586984 935048 4586993 935117 4587007 935194 4587021 935270 4587034 935321 4587047 935354 4587044 935411 4587047 935443 4587053 935500 4587061 935577 4587070 935652 4587073 935706 4587076 935762 4587081 935800 4586978 935803 4586844 935811 4586755 935815 4586636 935815 4586608 935811 4586551 935798 4586444 935765 4586339 935736 4586259 935712 4586228 935701 4586160 935682 4586090 935663 4586002 935617 4585910 935567 4585842 935529 4585794 935496 4585746 935466 4585709 935445 4585657 935416 4585590 935384 4585538 935364 4585505 935349 4585483 935344 4585426 935344 4585372 935346 4585340 935346 4585300 935340 4585263 935325 4585233 935310 4585176 935277 4585112 935344 4585038 935407 4584975 935440 4584908 935480 4584827 935521 4584741 935562 4584683 935593 4584761 935552 4584698 935583 4584631 935631 4584570 935708 4584512 935785 4584447 935869 4584402 935894 4584324 935930 4584265 935946 4584236 935958 4584234 935951 4584185 935884 4584105 935767 4584070 935718 4583951 935681 4583827 935647 4583673 935617 4583544 935595 4583446 935578 4583398 935560 4583264 935455 4583161 935377 4583075 935319 4582970 935242 4582925 935203 4582863 935149 4582841 935129 4582806 935088 4582757 935037 4582702 934969 4582639 934889 4582579 934811 4582547 934765 4582505 934708 4582436 934607 4582366 934522 4582293 934444 4582202 934352 4582110 934260 4582044 934193 4581965 934106 4581985 934064 4581977 934015 4581995 933977 4582115 934027 4582306 934111 4582411 934158 4582448 934175";
linePathArr["FJQZ"] = "4513386 1045933 4513050 1045888 4512704 1045832 4512243 1045737 4512113 1045702 4511992 1045622 4511862 1045537 4511792 1045577 4511797 1045627 4511802 1045797 4511807 1046033 4511847 1046093 4511967 1046349 4512012 1046439 4512017 1046535 4512027 1046730 4512017 1046835 4512007 1047091 4512002 1047232 4511987 1047382 4512012 1047487 4512032 1047522 4512032 1047578 4512057 1047638 4512133 1047843 4512178 1047964 4512228 1048134 4512238 1048174 4512253 1048189 4512258 1048250 4512383 1048616 4512439 1048741 4512514 1048927 4512599 1049092 4512669 1049238 4512775 1049408 4512845 1049508 4513010 1049619 4513176 1049714 4513311 1049799 4513432 1049834 4513487 1049834 4513497 1049890 4513482 1049940 4513477 1049980 4513492 1050040 4513547 1050135 4513572 1050221 4513622 1050346 4513652 1050426 4513677 1050516 4513702 1050562 4513707 1050617 4513727 1050637 4513778 1050712 4513823 1050787 4513893 1050787 4513898 1050827 4513908 1050898 4514053 1051143 4514545 1051449 4514936 1051540 4515072 1051585 4515372 1051700 4515563 1051775 4515728 1051840 4515844 1051845 4515984 1051815 4516115 1051765 4516245 1051730 4516370 1051640 4516395 1051519 4516531 1051359 4516666 1051143 4516897 1050832 4517042 1050617 4516661 1050436 4516451 1050316 4516355 1050281 4516411 1050135 4516641 1049930 4516742 1049724 4516777 1049508 4516807 1049393 4517108 1049458 4517419 1049523 4517639 1049569 4517915 1049624 4518201 1049684 4518361 1049659 4518562 1049579 4518863 1049298 4519078 1049057 4519329 1048801 4519610 1048510 4519896 1048285 4520162 1048044 4520618 1047633 4520804 1047392 4520924 1047267 4521084 1047021 4521355 1046620 4521571 1046168";
linePathArr["FJXM_1"] = "4473026 1092273 4472863 1092554 4472717 1092842 4473317 1093095 4472908 1093647 4472517 1093973 4472178 1094173 4471915 1094351 4471612 1094649 4471105 1094970 4471008 1095041 4470554 1095407 4470353 1095479 4470048 1095717 4469757 1096013 4469619 1096086 4469270 1096179 4469077 1096236 4468927 1096394 4468776 1096800 4468651 1096999 4468488 1097124 4468348 1097129 4467944 1096996 4467599 1096985 4467093 1096795 4466802 1096609 4466476 1096589 4466260 1096509 4465859 1096444 4465634 1096334 4464977 1096128 4464821 1096193 4464530 1096183 4464004 1096143 4463668 1095927 4463337 1095576 4462950 1094869";
linePathArr["FJXM_2"] = "4464840 1092174 4464318 1091327 4465401 1091028 4465253 1090707 4465589 1090617";
linePathArr["FJLY_1"] = "4356845 1031899 4356355 1031937 4356425 1033793 4356499 1035854 4356318 1037008 4356459 1041261 4356389 1042545 4357321 1042615 4357541 1042000 4357646 1041293 4357719 1041014 4357582 1040311 4357647 1040161 4357597 1040001 4357547 1039574 4357572 1038822 4357708 1038591 4358069 1036771 4358008 1035923 4357587 1032353 4357562 1031535 4357953 1031179 4357958 1030860 4358021 1030260 4358364 1030353 4358577 1030183 4358800 1030072 4358862 1029767 4358978 1029661 4359163 1029202 4358943 1029052 4358679 1029489 4358544 1029572 4358408 1029635 4358265 1029641 4358066 1029452 4358056 1029403 4358092 1029293 4358076 1029217 4357982 1028955 4357770 1029008 4357808 1029311 4357830 1029395 4357852 1029457 4357877 1029539 4357935 1029606 4357942 1029873 4358012 1030050 4357843 1030086 4357744 1030134 4357697 1030131 4357610 1029981 4357526 1030023 4357389 1030025 4357297 1029994 4357136 1029996 4356833 1030033 4356838 1030336 4356842 1031659 4356916 1035865 4356487 1035850 4356501 1035801 4356424 1033727 4356132 1033735";
linePathArr["FJLY_2"] = "4356858 1031665 4356352 1031710 4356497 1035849 4358017 1035919 4357711 1033562";
linePathArr["FJLY_3"] = "4357525 1031520 4356813 1031641 4356853 1030327 4356322 1030347 4356352 1031681 4356843 1031671 4356924 1035874";
linePathArr["FJLY_4"] = "4289808 955897 4289663 955781 4289613 955731 4288966 955360 4289136 955320 4289613 955149 4289788 955109 4289994 955099 4290205 955149 4290340 955250 4290485 955400 4290651 955470 4290706 955636 4290736 955676 4291012 955781 4291122 955872 4290771 956273 4290786 956368 4290897 956428 4291017 956554 4291107 956579 4291870 957366 4291980 957602 4291970 957913 4291900 958058 4291719 958108 4291433 958128 4291173 957998 4291082 957807 4290711 957797 4290235 957837 4289864 957812 4289593 957652 4289382 957496 4289292 957180 4289272 956122";
linePathArr["FJLY_5"] = "4289078 957871 4289293 957698 4289263 956096";
//显示线路
var nowFocusLine = "FJFZ_1";
function showLine(lines){
	if(lines != null){nowFocusLine = lines;}
	richmap.clearAllElement();
	//return;
	
	var linePath = linePathArr[nowFocusLine];
	var tempLine = new wingBaseLine();
	var paths = linePath.split(" ");
	var l = r = paths[0]*1;
	var b = t = paths[1]*1;
	for(var i=0;i<paths.length;i++){
		var x = paths[i]*1;
		tempLine.aScreenX.push(x);
		l = l>x?x:l;
		r = r<x?x:r;
		i++;
		var y = paths[i]*1;
		tempLine.aScreenY.push(paths[i]);
		t = t>y?y:t;
		b = b<y?y:b;
	}
	tempLine.sId = "busLine" + richmap.getLayer();
	tempLine.sColor = "red";
	tempLine.nWeight = 1;
	tempLine.run = true;
	tempLine.runSpeed = (30-richmap.getLayer()*2)*20;	//跑动速度
	tempLine.runLength = 1;		//每次跑动的像素距
	richmap.addWingElement(tempLine);
	richmap.setCity(nowFocusLine.replace(/_.*?$/,""));
	//alert(l+","+b+","+r+","+t);
	richmap.zoomToMBR(l,b,r,t);
}

var nowX = 4585637;
var nowY = 931852;
var nowZ = 13;

function setNowX(x) {
	nowX = x;
}
function setNowY(y) {
	nowY = y;
}
function setNowZ(z){
	nowZ = z;
}

function loadMapSuccess(){
	richmap.showCity(nowFocusCity);		//显示城市地图到指定层级
	lockRegion(nowZ,nowX,nowY);
	load("2g","0,-1");
}
//得到一个xmlhttp对象
function getRequest(){
	var req=null;
	try{
		req = new ActiveXObject("Msxml2.XMLHTTP.4.0");
	}catch(e){
		try{
			req = new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			try{
				req = new ActiveXObject("Microsoft.XMLHTTP"); 
			}catch(e){
				req = null;
			};
		};
	};
	if (req==null && typeof XMLHttpRequest!='undefined'){
		req = new XMLHttpRequest();
	};
	return req;
}; 
function $(id){return document.getElementById(id);}
/*
*	读取从亿阳获得的基站数据并展现
*	laoding 20080425
* 下面是双方接口定义
*参数设定：
	callback=
	city = FJ FJXM FJFZ
	xiaoqu = 1 要小区信息 0 不要小区信息
*返回数据格式(不要小区)：
	callback([['基站ID','基站名',x,y],
	[...],
	...])
*返回数据格式(要小区)：
	callback([['基站ID','基站名',x,y,['小区id',方位角,波瓣宽度]],
	[...],
	...])
*10.53.160.91:9201
*/
var startLoadDate = null;
var loadBaseStationScript = null;
var returnStationData = [];
//var basePath = "http://10.210.17.23:8080/";
var basePath = "http://10.209.0.15:8080/";
var basePaths = ["http://10.209.0.15:8080/","http://10.53.160.91:9202/"];
var nowPath = 0;
//var basePath = "http://10.46.0.1/f1/";
var nettype = "1";

function loadSite(act,sceneId){
	if(act != null){nettype=act;}
	var scene = "";
	if(sceneId != null || sceneId!='' || sceneId!='all'){scene="&sceneId="+sceneId;}
	clearAllStation();
	clearAllAngle();
	returnStationData = null;
	addShowInfo("开始读取数据...");
	//$("lineInfo").innerHTML="开始读取数据...";
	startLoadDate = new Date();
	if(loadBaseStationScript == null){
		loadBaseStationScript = document.createElement("script");
		loadBaseStationScript.setAttribute("type","text/javascript");
		loadBaseStationScript.setAttribute("language","javascript");
		document.body.appendChild(loadBaseStationScript);
	}
	//http://10.210.17.23:8080/taifeng/index.jsp?callback=abc&city=FJFZ
	//alert(basePath + "taifeng/f1_area/siteposition.jsp?callback=resoluteCallBack&nettype="+nettype+"&city="+nowFocusCity+"&isincludecells=true&req=" + Math.random());
	loadBaseStationScript.src = "../siteposition.json?action=getSiteList&callback=resoluteCallBack&nettype="+nettype+scene+"&city="+nowFocusCity+"&isincludecells=true&req=" + Math.random();
	//alert(loadBaseStationScript.src)
//	loadBaseStationScript.src = "http://111/?city=FJFZ&xiaoqu=1&callback=resoluteCallBack&req=" + Math.random();
	//alert(basePath + "taifeng/f1_area/siteposition.jsp?callback=resoluteCallBack&nettype="+nettype+"&city="+nowFocusCity+"&isincludecells=true&req=" + Math.random())
	startGetBasePath("loadSite('"+act+"','"+sceneId+"')");
}
var hasReturn = false;
var reDoFun = "";
var correctSite = false;
function startGetBasePath(redo){
	if(correctSite)return;
	hasReturn = false;
	reDoFun = redo;
	setTimeout(checkBaseReturn,10000);
}
function checkBaseReturn(){
	if(hasReturn == false){
		nowPath ++;
		if(nowPath == basePaths.length)nowPath = 0;
		//window.status = "nowPath:" + nowPath;
		basePath = basePaths[nowPath];
		eval(reDoFun);
	}else{
		correctSite = true;
	}
}
function getBaseReturn(){
	hasReturn = true;
}
function resoluteCallBack(returnArr){
	getBaseReturn()
	addShowInfo("<br>服务端组装数据并下载完毕！");
	addShowInfo("<br>总基站数据：<font color=red>" + returnArr.length + "</font>个");
	addShowInfo("<br>耗时：<font color=red>" + (new Date().getTime()-startLoadDate.getTime()) + "</font>ms");
	//$("lineInfo").innerHTML += "<br>服务端组装数据并下载完毕！";
	//$("lineInfo").innerHTML += "<br>总基站数据：<font color=red>" + returnArr.length + "</font>个";
	//$("lineInfo").innerHTML += "<br>耗时：<font color=red>" + (new Date().getTime()-startLoadDate.getTime()) + "</font>ms";
	for(var i=0;i<returnArr.length;i++){
		returnArr[i][2] = richmap.getScreenXByLongitude(returnArr[i][2]);
		returnArr[i][3] = richmap.getScreenYByLatitude(returnArr[i][3]);
	}
	returnStationData = returnArr;
	//startSoluteDate = new Date();
	richmap.addEventListener("onzoom","showAllCallBack");
	richmap.addEventListener("onchange","showStationAngels");
	if(richmap.getLayer() == 5){
		showAllCallBack();
	}else{
		showAllCallBack();
		//richmap.zoomTo(8,returnArr[0][2],returnArr[0][3]);
	}
	
}
var loadCountInfoScript = null;
var nowAngelArr = [];		//['小区id',方位角,波瓣宽度,x,y,color]
var areaIds = "";
var colorInterval = null;
function showStationAngels(){
	if(colorInterval != null){
		clearInterval(colorInterval);
		colorInterval = null;
	}
	var layer = richmap.getLayer();
	var MBR = richmap.getMBR();
	if(layer >= 13){
		areaIds = "";
		nowAngelArr = [];
		//先把ID记下来
		for(var i=0;i<returnStationData.length;i++){
			x = returnStationData[i][2];
			y = returnStationData[i][3];
			if(x > MBR[0] && x < MBR[2] && y>MBR[3] && y<MBR[1]){
				if(returnStationData[i][5].length > 0){
					for(var j=0;j<returnStationData[i][5].length;j++){
						//window.status = "2..." + i + "," + j + ".." + returnStationData[i][4][j][0];
						if(returnStationData[i][5][j].length > 0){
							returnStationData[i][5][j].push(x);
							returnStationData[i][5][j].push(y);
							returnStationData[i][5][j].push(5);
							returnStationData[i][5][j].push(returnStationData[i][4]);
							nowAngelArr.push(returnStationData[i][5][j]);
							areaIds += returnStationData[i][5][j][0] + ",";
						}
					}
				}
			}
		}
		areaIds = areaIds.replace(/\,$/,"");
		showNowAngel();
		colorInterval = setInterval(getAngelColor,300000);
		//下面显示统计信息
		if(areaIds != ""){
			var ajform = document.countInfoForm;
			ajform.action = basePath + "taifeng/f1_area/areaBtsPInfoList.jsp?callback=countInfo";
			ajform.cellids.value = areaIds;
			AJForm.submitForm(ajform);
			//startGetBasePath("loadSite('"+act+"')");
		}else{
			clearCountInfo();
		}
	}else{
		clearAllAngle();
		clearCountInfo();
	}
}
function getAngelColor(){
	window.status = "getAngelColor...";
	var ajform1 = document.angelColorForm;
	ajform1.action = basePath + "taifeng/f1_area/btsPColorInfoList.jsp?callback=showAngelColor&req=" + Math.random();
	ajform1.cellids.value = areaIds;
	//alert(ajform1.action + "&cellids=" + areaIds);
	AJForm.submitForm(ajform1);
	//startGetBasePath("loadSite('"+act+"')");
}
function showAngelColor(returnData){
	window.status = "return getangelcolor";
	for(var i=0;i<nowAngelArr.length;i++){
		if(returnData[nowAngelArr[i][0]] != null){
			nowAngelArr[i][5] = parseInt(returnData[nowAngelArr[i][0]]);
		}
	}
	window.status = "getAngelColor end";
	showNowAngel();
}
var layerAngelWidth = [20,10,5,3];	//14,13,12,11层
function showNowAngel(){
	clearAllAngle();
	var layer = richmap.getLayer();
	var MBR = richmap.getMBR();
	var container = richmap.getUserContainer(0);
	var sectorVml = "";
	//用勾股定理换算斜边值
	var webXY = layerAngelWidth[14-layer];
	var w = 1000;
	var h = 1000;
	webXY = (webXY * 2) + "," + (webXY * 2);
	for(var i=0;i<nowAngelArr.length;i++){
		//var webLeft = richmap.getCoordFromScreenX(MBR[0],nowAngelArr[i][3]);
		//var webTop = richmap.getCoordFromScreenY(MBR[3],nowAngelArr[i][4]);						//filter:progid:DXImageTransform.Microsoft.Alpha(opacity=60);
		var webLeft = richmap.getPixXFromScreenX(nowAngelArr[i][3]);
		var webTop = richmap.getPixYFromScreenY(nowAngelArr[i][4]);
		sectorVml += "<v:shape CoordOrig=\"0,0\" id=\"angel_"+nowAngelArr[i][0]+"\" CoordSize=\""+w+","+h+"\" "
					+ "style=\"height:"+w+"px;width:"+h+"px;position:absolute;left:"+webLeft+"px;top:"+webTop+"px;\""
					+ " fillcolor = \"\" strokecolor=\"#eeeeee\"  strokeweight = \"1\" path = \"m0,0ae0,0,"+webXY+","+nowAngelArr[i][1]*65536+","+nowAngelArr[i][2]*65536+"xe\""
					+ " onmousemove=\"showAreaInfo('"+nowAngelArr[i][0]+"','"+nowAngelArr[i][6]+"')\" onmouseout=\"hideShowArea();\">"
					+" <v:fill type=\"solid\" color=\""+colorArr[nowAngelArr[i][5]-1]+"\" color2=\"white\"/>"
					+" </v:shape>";
	}
	//var myxy = richmap.getBaseMovePosition();
	//myxy[0] = - myxy[0];
	//myxy[1] = - myxy[1];
	//container.style.left = myxy[0] + "px";
	//container.style.top = myxy[1] + "px";
	container.innerHTML = sectorVml;
	sectorVml = null;
}

var countInfoDiv = null;
//var tdcountInfoDiv = null;
function clearCountInfo(){
	if(countInfoDiv != null){
		//countInfoDiv.style.display = "none";
		countInfoDiv.innerHTML = "";
	}
}
function showCountInfo(str){
	countInfoDiv.style.display = "";
	countInfoDiv.innerHTML = str;
	return true;
}
//function showTdCountInfo(str){
//	tdcountInfoDiv.style.display = "";
//	tdcountInfoDiv.innerHTML = str;
//	return true;
//}
var loadBaseStationInfoScript = null;
var canShowInfo = true;
function showAreaInfo(id,netype){
	canShowInfo = true;
	showInfoX = document.body.scrollLeft + event.clientX;
	showInfoY = document.body.scrollTop + event.clientY;
	if(loadBaseStationInfoScript == null){
		loadBaseStationInfoScript = document.createElement("script");
		loadBaseStationInfoScript.setAttribute("type","text/javascript");
		loadBaseStationInfoScript.setAttribute("language","javascript");
		document.body.appendChild(loadBaseStationInfoScript);
	}
	if(netype==wlanMarke) {
		loadBaseStationInfoScript.src ="../map/wlanInfo.jsp?callback=showAreaBack&cellid=" + id;		
	} else {
		loadBaseStationInfoScript.src = basePath + "taifeng/f1_area/areaBtsPInfo.jsp?callback=showAreaBack&cellid=" + id;
	}
	
}
var showInfoDiv = null;
var showInfoX = 0;
var showInfoY = 0;

function showAreaBack(returnData){//alert(returnData);
	if(!canShowInfo)return;
	if(showInfoDiv == null){
		showInfoDiv = document.createElement("div");
		showInfoDiv.className = "infodiv";
		showInfoDiv.style.display = "none";
		document.body.appendChild(showInfoDiv);
	}
	var outStr = "";
	for(k in returnData){
		outStr += "<nobr>" + k + "：<font color=green>" + returnData[k] + "</font></nobr><br>";
	}
	showInfoDiv.style.display = "";
	showInfoDiv.style.left = showInfoX;
	showInfoDiv.style.top = showInfoY;
	showInfoDiv.innerHTML = outStr;	
}

function hideShowArea(){
	if(showInfoDiv != null){showInfoDiv.style.display = "none";}
	canShowInfo = false;
};
function showAllCallBack(){
	clearAllStation();
	addShowInfo("<br>开始显示基站..");
	//$("lineInfo").innerHTML += "";
	startSoluteDate = new Date();
	var container = richmap.getUserContainer(1);
	var tx = richmap.getMoveWindowCoordX();
	var ty = richmap.getMoveWindowCoordY();
	var tempObj = textObj = null;
	var MBR = richmap.getMBR();
	var l = t = 0;
	for(var i=0;i<returnStationData.length;i++){
		tempObj = document.createElement("div");
		if(returnStationData[i][4]==tdMarke) {
			tempObj.className = "tdpoint" + richmap.getLayer();
		} else if (returnStationData[i][4]==wlanMarke){
			tempObj.className = "wlanpoint" + richmap.getLayer();
		} else {
			tempObj.className = "mypoint" + richmap.getLayer();
		}
		
		//tempObj.innerHTML = "▲";
		l = richmap.getCoordFromScreenX(tx,returnStationData[i][2])-4;
		t = richmap.getCoordFromScreenY(ty,returnStationData[i][3])-4;
		tempObj.style.left = l;
		tempObj.style.top = t;
		if(richmap.getLayer() > 12){
			//if(returnStationData[i][2] > MBR[0] && returnStationData[i][2] < MBR[2] && returnStationData[i][3]>MBR[3] && returnStationData[i][3]<MBR[1]){
				textObj = document.createElement("div");
				textObj.className = "stationText";
				textObj.style.left = l+10;
				textObj.style.top = t;
				textObj.innerHTML = "<nobr>" + returnStationData[i][1] + "</nobr>";
				container.appendChild(textObj);
			//}
		}
		container.appendChild(tempObj);
	};
	tempObj = null;
	textObj = null;
	addShowInfo("<br>耗时：<font color=red>" + (new Date().getTime()-startSoluteDate.getTime()) + "</font>ms");
	//$("lineInfo").innerHTML += "<br>耗时：<font color=red>" + (new Date().getTime()-startSoluteDate.getTime()) + "</font>ms";
}
function addShowInfo(str){
	var obj = $("lineInfo");
	obj.innerHTML += str;
	obj.scrollTop = obj.scrollHeight;
}
function searchBack(data,statusCode,statusMessage){
	//window.status = statusCode;
	//alert(statusCode);
	if(statusCode != AJForm.STATUS['SUCCESS']){
		return false;
	}
	//alert(data);
	eval(data);
	getAngelColor();
}
function colorBack(data,statusCode,statusMessage){
	//window.status = "colorback.." + statusCode;
	if(statusCode != AJForm.STATUS['SUCCESS']){
		return false;
	}
	//alert(data);
	eval(data);
}
function countInfo(returnData){
	var info2g = returnData['2G'];
	var infotd = returnData['TD'];
	var outStr = "<font color='black'><b>当前区域2G指标汇总：</b></font><br>";
	for(k in info2g){
		outStr += "<nobr><font color=black>" + k + "：</font><font color=green>" + info2g[k] + "</font></nobr><br>";
	}
	showCountInfo(outStr);
	//outStr = "<font color='black'><b>当前区域TD指标汇总：</b></font><br>";
	//for(k in infotd){
	//	outStr += "<nobr><font color=black>" + k + "：</font><font color=green>" + infotd[k] + "</font></nobr><br>";
	//}
	//showTdCountInfo(outStr);
}
function clearAllStation(){
	var container = richmap.getUserContainer(1);
	container.innerHTML = "";
	//CollectGarbage();
	return true;
}
function clearAllAngle(){
	var container = richmap.getUserContainer(0);
	//alert("container length:" + container.childNodes.length + ";hasChild:" + container.hasChildNodes());
	//while(container.hasChildNodes()){
	//	container.removeChild(container.firstChild);
	//}
	//showAngelHashTable = [];
	//container = null;
	container.innerHTML = "";
	//CollectGarbage();
	//container.innerHTML = "";
	//setTimeout(function(){void(0);},0);
	//window.status = "clearAngle..";
	return true;
}