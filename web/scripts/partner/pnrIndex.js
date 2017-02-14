function subLongString(str,maxLength)
{
	if(str==undefined||str==null) return "";
	var temp = str.replace(/[\W]/g,'##');
	if(temp.length>maxLength){
		var result;
		var chineseArray = temp.substring(0,maxLength).match(/#/g);
		if(chineseArray==null) result = str.substring(0,maxLength-1)+"..";
		else result = str.substring(0,maxLength-1-chineseArray.length/2)+"..";
		return result;
	}
	return str;
}
function resizeWidth()
{
	var bodyWidth = document.body.clientWidth;
	var containerWidth;
	if(bodyWidth<1000)
		containerWidth = 1000;
	else 
		containerWidth = bodyWidth;
	document.getElementById("container").style.width = containerWidth;
	document.getElementById("middlePart").style.width =  containerWidth - 360;
	//document.getElementById("topBorder0").style.width =  (containerWidth - 360)*0.9 - 10; 
//	document.getElementById("topBorder1").style.width =  (containerWidth - 360)*0.9 - 10; 
//	document.getElementById("topBorder2").style.width = (containerWidth - 360)*0.9 - 10;
	//document.getElementById("topBorder3").style.width = (containerWidth - 360)*0.9 - 10;
	//document.getElementById("topBorder4").style.width = (containerWidth - 360)*0.9 - 10;
	//document.getElementById("bottomBorder0").style.width = (containerWidth - 360)*0.9 - 10;  
//	document.getElementById("bottomBorder1").style.width = (containerWidth - 360)*0.9 - 10; 
//	document.getElementById("bottomBorder2").style.width = (containerWidth - 360)*0.9 - 10; 
	//document.getElementById("bottomBorder3").style.width = (containerWidth - 360)*0.9 - 10; 
	//document.getElementById("bottomBorder4").style.width = (containerWidth - 360)*0.9 - 10; 
}
function switchTab(url, str, num)
{
	for(var i=1;i<=5;i++)
	{
		document.getElementById(str+"Order"+i).style.display="none";
		document.getElementById(str+"Tab"+i).className="orderTabDefault";
	}
	showOrder(url, str, num);
	document.getElementById(str+"order"+num).style.display="";
	document.getElementById(str+"tab"+num).className="orderTabOver";
}
function out(obj)
{
	if(obj.className!="orderTabOver")obj.className="orderTabDefault";
}

var currentInfopub = 0;
var maxStep = 0;
var flag = true;
function scanInfopub()
{
	if(flag){
		var infopubs = document.getElementById("infopubContainer").getElementsByTagName("span");
		var length = infopubs.length;
		var clientWidth = document.getElementById("infopubList").clientWidth;
		var appendCount= clientWidth-(length-1)*70;
		if(appendCount>0)
		{
			var appendCount = parseInt(appendCount/70+1);
			var container = document.getElementById("infopubContainer");
			try{
				for(var i=0;i<appendCount;i++)
				{
					var copyObj = container.childNodes[i%length].cloneNode(true); 
					container.appendChild(copyObj);
				}
			}catch(e){return;}
			length = infopubs.length;
			container.style.width=70*length+70;
		}
		maxStep += 1;
		for(var i=0;i<length;i++)
		{
			try{
				infopubs[i].style.left = parseInt(infopubs[i].style.left)-1;
			}catch(e){
				infopubs[i].style.left = -1;
			}
			if(maxStep>=70){
				infopubs[currentInfopub].style.left=(length-1-currentInfopub)*70;
				if(currentInfopub>=length-1)
					currentInfopub = 0;
				else
					currentInfopub++;
				maxStep = 0;
			}
		}
	}
	window.setTimeout(scanInfopub,30);
}
function infopubOver()
{
	flag = false;
}
function infopubOut()
{
	flag = true;
}



//初始化页面内容

function on_off(e){
	e=e.parentNode.parentNode;
	var open="dir_item_open";
	e.className="dir_item_"+(e.className==open?"close":"open");
} 

function en(s){
  return encodeURI(encodeURI(s));
}

function sb(){
    var m = document.getElementById('actionName').value;
    var w = document.getElementById('actionValue').value;
    window.location.href = 'http://10.218.7.20:8888/search/' + m + '?q='+en(w);
}
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}

function showUser(oldurl)
{
	var url = oldurl + "/kmmanager/kmIndexs.do?method=getUserInfo";
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		document.getElementById("knowledgeScore").innerHTML="知识积分："+json[0].knowledgeScore;
		     		if(json[0].expertScore!=-1)
		     			document.getElementById("expertScore").innerHTML="专家积分："+json[0].expertScore;
		     		//document.getElementById("expertPhoto").src=oldurl+"/images/head/photo/zoom/"+json[0].expertPhoto;
				}	
	     }
	     httpRequest.send(null);
	}
}
function showInfopubs(oldurl)
{
	var url = oldurl + "/partner/baseinfo/index.do?method=listInfopubsForIndex";
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		var container = document.getElementById("infopubContainer");
		     		var expertName;
		     		try{
		     			if(json.length<=0){
		     				document.getElementById("infopubList").style.display = "none";
		     				return;
		     			}
			     		for(var i=0;i<json.length;i++){
			     				var detail = (i+1)+"、"+json[i].title+"。（创建时间："+json[i].createTime+"）";
			     				var newSpan = document.createElement("span");
			     				newSpan.className = "infopub";
			     				expertName = subLongString(json[i].userName,8); 
			     				newSpan.innerHTML = '<a href="'+oldurl+'/workbench/infopub/thread.do?method=detail&id='
			     									+json[i].id+'">'+detail+'</a>';
			     				alert(newSpan.innerHTML);
				     			container.appendChild(newSpan);
			     		}
			     		scanInfopub();
			     	}catch(e){}
				}	
	     }
	     httpRequest.send(null);
	}
}
function showOrder(oldurl, str, num)
{
	if(document.getElementById(str+"Order"+num).innerHTML!="") return;
	
	var method = "knowledgeTableAmountOrder";
	if(str=="readcount") method="knowledgeReadCountOrder";
	else if(str=="score") method="userKnowledgeScoreOrder"; 
	else if(str=="expertScore") method="expertScoreOrder"; 
	else if(str=="contribute") method="userKnowledgeContributeOrder";

	var timeType = ['','','YEAR','QUARTER','MONTH','WEEK'];
	var links = ['','search','searchLibYear','searchLibQuarter','searchLibMonth','searchLibWeek'];
	
	var url = oldurl + "/kmmanager/kmIndexs.do?method="+method+"&time="+timeType[num]+"&orderNumber=5";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		try{
			     		if(str==""){
			     			if(json.length<=0)
				     			document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem">本时间暂无排行信息</div>';
				     		else
			     			for(var i=0;i<json.length;i++)
								document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem"><div class="orderContentDetailItemNum">'
				     			+(i>9?"":"0")+(i+1)+'</div><div title="'+json[i].name+'" style="cursor:pointer"><a href="'+oldurl+'/kmmanager/kmContentss.do?method='+links[num]+'&node='
				     			+json[i].themeId+'&nodeLeaf=false">'+subLongString(json[i].name,14)+'</a></div><div class="orderContentDetailItemScore">'
				     			+json[i].count+'</div></div>';
				     	}
				     	else  if(str=="readcount"){
				     		if(json.length<=0)
				     			document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem">本时间暂无排行信息</div>';
				     		else
				     		for(var i=0;i<json.length;i++)
				     			document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem"><div class="orderContentDetailItemNum">'
				     			+(i>8?"":"0")+(i+1)+'</div><div title="'+json[i].name+'" style="cursor:pointer"><a href="'+oldurl+'/kmmanager/kmContentss.do?method=detail&ID='
				     			+json[i].id+'&TABLE_ID='+json[i].tableId+'&THEME_ID='+json[i].themeId+'">'+subLongString(json[i].name,14)+'</a></div><div class="orderContentDetailItemScore">'
				     			+json[i].count+'</div></div>';
				     				
				     	}
				     	else if(str=="score"){
				     		if(json.length<=0){
				     			document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem">本时间暂无排行信息</div>';
				     		} 
				     		else {     		
					     		for(var i=0;i<json.length;i++){
					     			document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem"><div class="orderContentDetailItemNum">'
					     			+(i>9?"":"0")+(i+1)+'</div><div title="姓名：'+json[i].user+' 部门：'+json[i].dept+'" style="cursor:pointer"><a href="'+oldurl+'/kmmanager/scoreOrderStatistic.do?method=userScoreMsg&orderNumber='+json[i].score+'&userName='
					     			+json[i].userid+'">'+subLongString(json[i].user,14)+'</a></div><div class="orderContentDetailItemScore">'
					     			+json[i].score+'</div></div>';
					     		}
				     		}
				     	}
				     	else if(str=="expertScore"){
				     		if(json.length<=0){
				     			document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem">本时间暂无排行信息</div>';
				     		} 
				     		else {     		
					     		for(var i=0;i<json.length;i++){
					     			document.getElementById(str+"Order"+num).innerHTML += '<div class="orderContentDetailItem"><div class="orderContentDetailItemNum">'
					     			+(i>9?"":"0")+(i+1)+'</div><div title="姓名：'+json[i].user+' 部门：'+json[i].dept+'" style="cursor:pointer"><a href="'+oldurl+'/kmmanager/scoreOrderStatistic.do?method=userScoreMsg&orderNumber='+json[i].score+'&userName='
					     			+json[i].userid+'">'+subLongString(json[i].user,14)+'</a></div><div class="orderContentDetailItemScore">'
					     			+json[i].score+'</div></div>';
					     		}
				     		}
				     	}
			     	}catch(e){}
				}	
	     }
	     httpRequest.send(null);
	}
}

function showContent(oldurl, type)//type为1则是知识，为2则是文档
{
	var method = "listKmContents";
	if(type==2) method = "listKmFiles";
	
	var url = oldurl + "/kmmanager/kmIndexs.do?method="+method+"&count=5&nodeId=107";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		try{
			     		if(type==1){
				     		for(var i=0;i<json.length;i++)
				     			document.getElementById("dataDetailContent").innerHTML += 
				     					'<div class="dataDetailContentItem"><span class="span1"><span style="width:500px;">'+subLongString(json[i].tableName,100)
				     					+'</span></span><span class="span2"><a href="'+oldurl+'/kmmanager/kmContentss.do?method=detail&ID='
				     					+json[i].id+'&TABLE_ID='+json[i].tableId+'&THEME_ID='+json[i].themeId+'" style="width:500px;" title="'+json[i].contentTitle+'">'
				     					+subLongString(json[i].contentTitle,100)+'</a></span><span class="span3">'+json[i].createTime
				     					+'</span><span class="span4">'+json[i].userName+'</span></div>';
				     		for(var i=json.length;i<5;i++)
			     				document.getElementById("dataDetailContent").innerHTML += '<div class="dataDetailContentItem"></div>';
				     	}	
				     	else{
				     		for(var i=0;i<json.length;i++)
				     			document.getElementById("dataDetailFile").innerHTML += 
				     					'<div class="dataDetailContentItem"><span class="span1">'+json[i].nodeName
				     					+'</span><span class="span2"><a href="files.do?method=detail&id='+json[i].id+'" style="width:500px;" title="'+json[i].fileName+'">'
				     					+subLongString(json[i].fileName,100)+'</a></span><span class="span3">'+json[i].uploadTime
				     					+'</span><span class="span4">'+json[i].userName+'</span></div>';
				     		for(var i=json.length;i<5;i++)
			     				document.getElementById("dataDetailFile").innerHTML += '<div class="dataDetailContentItem"></div>';
				     	}
			     	}catch(e){}
				}	
	     }
	     httpRequest.send(null);
	}
}

function showBestContent(oldurl)
{
	var url = oldurl + "/kmmanager/kmIndexs.do?method=listKmBestContents&count=5";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		try{
			     		for(var i=0;i<json.length;i++)
			     			document.getElementById("dataDetailBestContent").innerHTML += 
			     					'<div class="dataDetailContentItem"><span class="span1">'+json[i].tableName
			     					+'</span><span class="span2"><a href="'+oldurl+'/kmmanager/kmContentss.do?method=detail&ID='
			     					+json[i].id+'&TABLE_ID='+json[i].tableId+'&THEME_ID='+json[i].themeId+'" style="width:500px;" title="'+json[i].contentTitle+'">'
			     					+subLongString(json[i].contentTitle,100)+'</a></span><span class="span3">'+json[i].createTime
			     					+'</span><span class="span4">'+json[i].userName+'</span></div>';
			     		for(var i=json.length;i<5;i++)
			     			document.getElementById("dataDetailBestContent").innerHTML += '<div class="dataDetailContentItem"></div>';
			     	}catch(e){}
				}	
	     }
	     httpRequest.send(null);
	}
}

function showLeaderReadFile(oldurl)
{
	var url = oldurl + "/kmmanager/kmIndexs.do?method=listKmLeaderReadFiles&count=5&nodeId=107";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		try{
			     		for(var i=0;i<json.length;i++)
			     			document.getElementById("dataDetailLeaderReadFile").innerHTML += 
			     					'<div class="dataDetailContentItem"><span class="span1">'+json[i].nodeName
			     					+'</span><span class="span2"><a href="files.do?method=detail&id='+json[i].id+'" style="width:500px;" title="'+json[i].fileName+'">'
			     					+subLongString(json[i].fileName,100)+'</a></span><span class="span3">'+json[i].uploadTime
			     					+'</span><span class="span4">'+json[i].userName+'</span></div>';
			     		for(var i=json.length;i<5;i++)
		     				document.getElementById("dataDetailLeaderReadFile").innerHTML += '<div class="dataDetailContentItem"></div>';
			     	}catch(e){}
				}	
	     }
	     httpRequest.send(null);
	}
}

function showNavigationTree(oldurl, type)//type为1则是知识管理，为2则是文件管理，为3则是课程管理
{
	var method = "listKmContentTree";
	if(type==2) method = "listKmFileTree";
	else if(type==3) method = "listKmLessonTree";
	
	var url = oldurl + "/kmmanager/kmIndexs.do?method="+method+"&parentNodeId=1";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		try{
			     		if(type==1)
				     		for(var i=0;i<json.length;i++){
				     			var newDiv = document.createElement("div");
				     			newDiv.appendChild(new NavigationTree(json[i].id,json[i].text,0,type,oldurl).coreDom);
				     			document.getElementById("contextTree").appendChild(newDiv);
				     		}
				     	else if(type==2)
				     		for(var i=0;i<json.length;i++){
				     			var newDiv = document.createElement("div");
				     			newDiv.appendChild(new NavigationTree(json[i].id,json[i].text,0,type,oldurl).coreDom);
				     			document.getElementById("fileTree").appendChild(newDiv);
				     		}
				     	else 
				     		for(var i=0;i<json.length;i++)
				     			document.getElementById("lessonTree").innerHTML += 
				     					'<div class="firstLevel" title="'+json[i].text+'"><a href="'+oldurl
				     					+'/kmmanager/kmLesson.do?method=detail&id='+json[i].id+'">'+subLongString(json[i].text,22)+'</a></div>';
			     	}catch(e){}
				}	
	     }     
	     httpRequest.send(null);
	 }
}

function NavigationTree(id,text,level,type,oldurl)
{
	this.coreDom = document.createElement("DIV");
	this.coreDom.appendChild(new NavigationTreeHandler(id,type,level,oldurl).coreDom);
	if(type==1){
		var a = document.createElement("A");
		a.href = oldurl+'/kmmanager/kmContentss.do?method=search&node='+id+'&nodeLeaf=false';
		a.innerHTML = subLongString(text,22);
		a.style.position = "absolute";
		this.coreDom.appendChild(a);
	}
	else if(type==2){
		var a = document.createElement("A");
		a.href = oldurl+'/kmmanager/files.do?nodeId='+id;
		a.innerHTML = subLongString(text,22);
		a.style.position = "absolute";
		this.coreDom.appendChild(a);
	}
	this.coreDom.id = id;
	this.coreDom.text = text;
	this.coreDom.level = level;
	this.coreDom.className = "firstLevel";
	this.coreDom.style.paddingLeft = level*15;
	this.coreDom.title = text;
	this.coreDom.isOpen = false;
	this.coreDom.isRequest = false;
}
function NavigationTreeHandler(id,type,level,oldurl)
{
	this.coreDom = document.createElement("DIV");
	this.coreDom.className = "treeDefaultHandlerDefault";
	this.coreDom.onclick = function(){
								if(!this.parentNode.isRequest){
									nextLevelNavigationTree(type,id,oldurl,level,this.parentNode.parentNode);
									this.parentNode.isRequest = true;
									this.className = "treeDefaultHandlerHit";	
								}
								if(this.parentNode.nextSibling!=null)
									if(this.parentNode.isOpen){
										this.parentNode.nextSibling.style.display = "none";
										this.className = "treeDefaultHandlerDefault";
									}
									else{
										this.parentNode.nextSibling.style.display = "";
										this.className = "treeDefaultHandlerHit";
									}
								this.parentNode.isOpen = !this.parentNode.isOpen;	
							}
}
function nextLevelNavigationTree(type,parentNodeId,oldurl,level,obj)
{
	var method = "listKmContentTree";
	if(type==2) method = "listKmFileTree";
	
	var url = oldurl + "/kmmanager/kmIndexs.do?method="+method+"&count=10&parentNodeId="+parentNodeId;
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		var divContainer = document.createElement("div");
		     		try{
		     			if(type==1)
				     		for(var i=0;i<json.length;i++){
				     			var newDiv = document.createElement("div");
				     			newDiv.appendChild(new NavigationTree(json[i].id,json[i].text,level+1,type,oldurl).coreDom);
				     			divContainer.appendChild(newDiv);
				     		}
				     	else if(type==2)
				     		for(var i=0;i<json.length;i++){
				     			var newDiv = document.createElement("div");
				     			newDiv.appendChild(new NavigationTree(json[i].id,json[i].text,level+1,type,oldurl).coreDom);
				     			document.getElementById("fileTree").appendChild(newDiv);
				     			divContainer.appendChild(newDiv);
				     		}
				     	obj.appendChild(divContainer);
			     	}catch(e){}
				}	
	     }     
	     httpRequest.send(null);
	 }
}
