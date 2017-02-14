<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/styles/common/reset-grids-base.css" />
<style type="text/css">
body {
	overflow-x: hidden;
	overflow-y: auto;
}

.bd {
}

.grid {
	margin: 5px 0 0 5px;
}

.col-main {
	float: left;
	width: 100%;
}

.main-wrap {
	margin-right: 215px;
}

.col-sub {
	float: left;
	width: 190px;
	margin-left: -205px;
}

.box .hd h3 {
	background: none no-repeat scroll 2px center;
	padding-left: 20px;
	margin-left: 3px;
}

ol.order {
	padding-left: 40px;
}

ol.onDuty {
	padding-left: 20px;
}

ol.order li {
	list-style-type: decimal;
}

.viewt {
	border: 0;
}

.viewt th {
	border: 0;
	padding: 6px;
	border-bottom: 1px solid #6B95BF;
	font-weight: bold;
	color: #336699;
}

.viewt td {
	background-color: #DFF3FF;
	border: 0;
	padding: 6px;
}
</style>

<script type="text/javascript">
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
    //alert(m);
    //alert(w);
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

function showUser()
{
	var url = "../kmmanager/kmContentsMains.do?method=showUser";
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);		     			
		     			var obj = document.getElementById("showUser");
		     			var newLi = document.createElement("LI");
		     			newLi.innerHTML = "<span style='width:140px;'>知识贡献积分:"+json[0].userSum+"</span>专家积分："+json[0].expertSum;
		     			obj.appendChild(newLi);
		     			document.getElementById("headPhoto").style.backgroundImage = "url(${app}/images/head/photo/zoom/"+json[0].head+")";
				}	
	     }
	     httpRequest.send(null);
	}
}

function showContents(type)
{
	var moreContentsUrl = document.getElementById("moreContents").href;
	document.getElementById("moreContents").href = moreContentsUrl.substring(0,moreContentsUrl.length-1)+type;
	var obj = document.getElementById("contentsId");
	var url = "../kmmanager/kmContentsMains.do?count=7&type="+type;
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		while(obj.rows.length>1)
		     		{
		     			obj.deleteRow(1);
		     		}
		     		for(var i=0;i<json.length;i++)
		     		{
		     			var newRow = obj.insertRow();
		     			var newTd1 = newRow.insertCell();
		     			var newTd2 = newRow.insertCell();
		     			var newTd3 = newRow.insertCell();
		     			var newTd4 = newRow.insertCell();
		     			if(i%2==0)
		     				newRow.className = "odd";
		     			else
		     				newRow.className = "even"; 
					    newTd1.innerHTML = json[i].tableName;
					    var url;
					    if(type==1)
                        {
	                        url='${app}/kmmanager/kmContentss.do?method=detail';
	                        url = url + '&ID=' + json[i].id + '&TABLE_ID=' + json[i].tableId + '&THEME_ID=' + json[i].themeId;
                        }
                        else if(type==2)
                        {
                        	url='${app}/kmmanager/files.do?method=detail&id='+json[i].id;
                        }
                       	else if(type==3)
                       	{}
					    newTd2.innerHTML = "<a href="+url+">"+json[i].contentTitle+"</a>";
					    newTd3.innerHTML = json[i].createTime;
					    newTd4.innerHTML = json[i].createUser;			
		     		}
				}	
	     }
	     httpRequest.send(null);
    }
}

function showFiles(type)
{
	var moreContentsUrl = document.getElementById("moreContents").href;
	document.getElementById("moreContents").href = moreContentsUrl.substring(0,moreContentsUrl.length-1)+type;
	var obj = document.getElementById("contentsFileId");
	var url = "../kmmanager/kmContentsMains.do?count=7&type="+type;
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		while(obj.rows.length>1)
		     		{
		     			obj.deleteRow(1);
		     		}
		     		for(var i=0;i<json.length;i++)
		     		{
		     			var newRow = obj.insertRow();
		     			var newTd1 = newRow.insertCell();
		     			var newTd2 = newRow.insertCell();
		     			var newTd3 = newRow.insertCell();
		     			var newTd4 = newRow.insertCell();
		     			if(i%2==0)
		     				newRow.className = "odd";
		     			else
		     				newRow.className = "even"; 
					    newTd1.innerHTML = json[i].tableName;
					    var url;
					    if(type==1)
                        {
	                        url='${app}/kmmanager/kmContentss.do?method=detail';
	                        url = url + '&ID=' + json[i].id + '&TABLE_ID=' + json[i].tableId + '&THEME_ID=' + json[i].themeId;
                        }
                        else if(type==2)
                        {
                        	url='${app}/kmmanager/files.do?method=detail&id='+json[i].id;
                        }
                       	else if(type==3)
                       	{}
					    newTd2.innerHTML = "<a href="+url+">"+json[i].contentTitle+"</a>";
					    newTd3.innerHTML = json[i].createTime;
					    newTd4.innerHTML = json[i].createUser;			
		     		}
				}	
	     }
	     httpRequest.send(null);
    }
}

function showOrder()
{
	var url = "../kmmanager/kmContentsMains.do?method=mainScoreOrder&orderNumber=5";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		var json1 = json[0];
		     		var json2 = json[1];
		     		var json3 = json[2];
		     		var json4 = json[3];
		     		var ret1 = json1[0].order;
		     		var ret3 = json3[0].order;
		     		for(var i=0;i<json1.length;i++)
		     		{	
		     			var obj = document.getElementById("order1");
		     			var newLi = document.createElement("LI");
						var long = json1[i].order/ret1*55;
		     			newLi.innerHTML = "<span style='width:90px;' title='"+json1[i].table+"' >"+subStringkm(json1[i].table,8)+"</span>" +"<span style='width:60px;'><img src='${app}/images/hljunicom/pic_1.jpg' width='"+long+"' height='10'></span>"+ json1[i].order;
		     			obj.appendChild(newLi);
		     		}
		     		/*
		     		for(var i=0;i<json2.length;i++)
		     		{	
		     			var obj = document.getElementById("order2");
		     			var newLi = document.createElement("LI");
		     			newLi.innerHTML = "<span style='width:140px;'>"+json2[i].user+"</span>" + json2[i].order;
		     			obj.appendChild(newLi);
		     		}
		     		*/
		     		for(var i=0;i<json3.length;i++)
		     		{	
		     			var obj = document.getElementById("order3");
		     			var newLi = document.createElement("LI");
		     			var long = json3[i].order/ret3*55;
		     			newLi.innerHTML = "<span style='width:90px;' title='"+json3[i].user+"'>"+subStringkm(json3[i].user,8)+"</span>" +"<span style='width:60px;'><img src='${app}/images/hljunicom/pic_1.jpg' width='"+long+"' height='10'></span>"+  json3[i].order;
		     			obj.appendChild(newLi);
		     		}
		     		/*
		     		for(var i=0;i<json4.length;i++)
		     		{	
		     			var obj = document.getElementById("order4");
		     			var newLi = document.createElement("LI");
		     			
		     			if(json4[i].title.length>10){
		     				newLi.innerHTML = "<span style='width:140px;' title='" + json4[i].title + "'>" + json4[i].title.substring(0,8) + "..</span>" + json4[i].order;
		     			}
		     			else{
		     				newLi.innerHTML = "<span style='width:140px;'>"+ json4[i].title+"</span>" + json4[i].order;
		     			}
		     			
		     			obj.appendChild(newLi);
		     		}
		     		*/		     		
				}	
	     }
	     httpRequest.send(null);
	}
}
function showPers(time)
{
	var url = "../kmmanager/kmContentsMains.do?method=mainScoreOrderTime&orderNumber=5&time="+time;
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var obj = document.getElementById("order3");
		     		obj.innerHTML="";
		     		var json = eval(httpRequest.responseText);
		     		var ret = json[0].order;
		     		for(var i=0;i<json.length;i++)
		     		{	
		     			var newLi = document.createElement("LI");
						var long = json[i].order/ret*55;
		     			newLi.innerHTML = "<span style='width:90px;' title='"+json[i].user+"' >"+subStringkm(json[i].user,8)+"</span>" +"<span style='width:60px;'><img src='${app}/images/hljunicom/pic_1.jpg' width='"+long+"' height='10'></span>"+ json[i].order;
		     			obj.appendChild(newLi);
		     		}	     		
				}	
	     }
	     httpRequest.send(null);
	}
}
function showKnos(time)
{
	var url = "../kmmanager/kmContentsMains.do?method=mainScoreOrderTimeKno&orderNumber=5&time="+time;
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var obj = document.getElementById("order1");
		     		obj.innerHTML="";
		     		var json = eval(httpRequest.responseText);
		     		var ret = json[0].order;
		     		for(var i=0;i<json.length;i++)
		     		{	
		     			var newLi = document.createElement("LI");
						var long = json[i].order/ret*55;
						newLi.innerHTML = "<span style='width:90px;' title='"+json[i].table+"' >"+subStringkm(json[i].table,8)+"</span>" +"<span style='width:60px;'><img src='${app}/images/hljunicom/pic_1.jpg' width='"+long+"' height='10'></span>"+ json[i].order;
		     			obj.appendChild(newLi);
		     		}	     		
				}	
	     }
	     httpRequest.send(null);
	}
}
function showOnDuty()
{
	var url = "../kmmanager/kmContentsMains.do?method=showOnDuty";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);		     		
		     		for(var i=0;i<json.length;i++)
		     		{	
		     		    var obj = document.getElementById("onDuty");
		     		    var newLi = document.createElement("LI");
		     			newLi.innerHTML = "<span style='width:140px;'>"+ json[i].text + "</span>";
		     			obj.appendChild(newLi);
		     		}		     		
				}	
	     }
	     httpRequest.send(null);
	}
}
function showFileTrees()
{
	var url = "../kmmanager/kmFileTrees.do?method=getNodes&node=1";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		var innerStr = "";
		     		for(var i=0;i<json.length;i++)
		     		{	
		     			//alert(json[i].table);
		     			var obj = document.getElementById("filesTree");
		     			innerStr = innerStr +"<a href='files.do?nodeId="+json[i].id+"' title='"+json[i].text+"'>"+subStringkm(json[i].text,14)+"</a><br>";
		     		}
		     			//alert(innerStr);
		     			obj.innerHTML = innerStr;
				}	
	     }
	     httpRequest.send(null);
	}
}
function showContentsTrees()
{
	var url = "../kmmanager/themes.do?method=getNodesShow&node=1";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		var innerStr = "";
		     		for(var i=0;i<json.length;i++)
		     		{	
		     			//alert(json[i].table);
		     			var obj = document.getElementById("contentsTree");
		     			innerStr = innerStr +"<a href='kmContentss.do?method=search&node="+json[i].id+"&nodeLeaf=false' title='"+json[i].text+"'>"+subStringkm(json[i].text,14)+"</a><br>";
		     		}
		     			//alert(innerStr);
		     			obj.innerHTML = innerStr;
				}	
	     }
	     httpRequest.send(null);
	}
}

function showLessons()
{
	var url = "../kmmanager/kmLesson.do?method=showLesson&pageSize=5";
 	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
		     		var innerStr = "";
		     		for(var i=0;i<json.length;i++)
		     		{	
		     			//alert(json[i].table);
		     			var obj = document.getElementById("lessonsTree");
		     			innerStr = innerStr +"<a href='kmLesson.do?method=detail&id="+json[i].id+"' title='"+json[i].text+"'>"+subStringkm(json[i].text,14)+"</a><br>";
		     		}	    
		     			//alert(innerStr);
		     			obj.innerHTML = innerStr;
				}	
	     }
	     httpRequest.send(null);
	}
}
function subStringkm(str,int)
{
	if(str.length>int){
		str = str.substr(0,int)+"...";
		return str;
	}else{
		return str;
	}
}
function init()
{	
	showContents(1);
	showFiles(2);
	showOrder();
	showFileTrees();
	showUser();
	showContentsTrees();
	showLessons();
	showOnDuty();
}
</script>
</head>

<body onload="init()">
	<div id="page">
		<div id="content" class="clearfix">
			<div id="main">
				<br />

				<div class="grid-c3 skin-blue image-disabled">

					<div class="col-main">
						<div class="main-wrap">
							<!-- 搜索 -->
							<div class="box">
								<span class="rc-tp"><span></span>
								</span>
								<div class="bd" style="padding: 10px;">
									<form>
										搜索:
										<select name="actionName" id="actionName" class="select">
											<option value="search.do">知识案例库</option>
											<option value="sdoc.do">文档案例库</option>
										</select>
										<input type="text" name="actionValue" id="actionValue" size="20" class="text" />
										<input type="button" class="submit" value="搜索" onClick="sb()" />
									</form>
								</div>
								<span class="rc-bt"><span></span>
								</span>
							</div>


							<!-- 最新更新 -->
							<div class="box">
								<span class="rc-tp">
								    <span></span>
								</span>
								<div class="hd">
									<h3 class="msg">
										知识库最新更新
									</h3>
								</div>
								<div class="bd" style="padding: 10px;">									
									  <table id="contentsId" cellpadding="0" class="viewt" cellspacing="0" style="width: 95%">
										<thead>
											<tr>
												<th class="sortable">所属类别</th>
												<th class="sortable">文档主题</th>
												<th class="sortable">创建时间</th>
												<th class="sortable">创建人</th>
											</tr>
										</thead>
									</table> 
								</div>

								<div class="ft">
									<ul class="act">										
										<li>
											<a id="moreContents" href="${app}/kmmanager/kmContentsMains.do?method=searchAll&type=1" class="more">更多</a>
										</li>
									</ul>
								</div>
								
								<span class="rc-bt"><span></span>
								</span>
							</div>

							<div class="box">
								<span class="rc-tp">
								    <span></span>
								</span>
								<div class="hd">
									<h3 class="msg">
										文档库最新更新
									</h3>
								</div>
								<div class="bd" style="padding: 10px;">									
									  <table id="contentsFileId" cellpadding="0" class="viewt" cellspacing="0" style="width: 95%">
										<thead>
											<tr>
												<th class="sortable">所属类别</th>
												<th class="sortable">文档主题</th>
												<th class="sortable">创建时间</th>
												<th class="sortable">创建人</th>
											</tr>
										</thead>
									</table> 
								</div>

								<div class="ft">
									<ul class="act">										
										<li>
											<a id="moreContents" href="${app}/kmmanager/kmContentsMains.do?method=searchAll&type=2" class="more">更多</a>
										</li>
									</ul>
								</div>
								
								<span class="rc-bt"><span></span>
								</span>
							</div>
						</div>
					</div>

					<!-- 左列 -->
					<div class="col-sub">

						<!-- 用户登录信息 -->
						<div class="box">
							<span class="rc-tp"><span></span>
							</span>
							<div class="hd">
								<h3 class="msg">当前用户:${sessionScope.sessionform.username}</h3>
							</div>
							<div class="bd" style="padding: 5px; text-align: center;">
								<div  id="headPhoto" style="width:120px;height:120px;border:2px solid #6699cc;">
								</div>
								<div style="width:120px; text-align:left;">
									<ol class="showUser" id="showUser"></ol> 	
									继续努力!
								</div>
							</div>
							<div class="ft">
								<ul class="act"></ul>
							</div>
							<span class="rc-bt"><span></span></span>
						</div>

						<!-- 导航树 -->
						<div class="box">
							<span class="rc-tp"><span></span>
							</span>
							<div class="hd">
								<h3 class="tool">
									导航树
								</h3>
							</div>
							<div class="bd" id="tools" style="padding: 5px">
								<div class="dir_tree">
									<div class="dir_item_open">
										<div class="dir_folder">
											<a class="dir_turn_sym" onclick="on_off(this)"></a>
											<p class="now">
												<a class="dir_turn" title="">知识管理</a>
											</p>
										</div>
										<div id="contentsTree" class="dir_tree_content">
										</div>
									</div>
									<div class="dir_item_open">
										<div class="dir_folder">
											<a class="dir_turn_sym" onclick="on_off(this)"></a>
											<p class="now">
												<a class="dir_turn" title="">文件管理</a>
											</p>
										</div>
										<div id="filesTree" class="dir_tree_content">
										</div>
									</div>
									<div class="dir_item_open">
										<div class="dir_folder">
											<a class="dir_turn_sym" onclick="on_off(this)"></a>
											<p class="now">
												<a class="dir_turn" title="">课程管理</a>
											</p>
										</div>
										<div id="lessonsTree" class="dir_tree_content">
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<!-- 右列 -->
					<div class="col-extra">

						<!-- 当前值班专家 -->
						<div class="box">
							<span class="rc-tp"><span></span>
							</span>
							<div class="hd">
								<h3 class="msg">
									当前值班专家
								</h3>
							</div>
							<div class="bd" style="padding: 5px">
								<ol class="onDuty" id="onDuty"></ol>
							</div>
						</div>
						
						<!-- 知识排名 -->
						<div class="box">
							<span class="rc-tp"><span></span>
							</span>
							<div class="hd">
								<h3 class="msg">
									知识库知识量排行
								</h3>
							</div>
							<div class="bd" style="padding: 5px">
								<center><a onclick="showKnos('all')">总排行</a> <a onclick="showKnos('year')">本年</a> <a onclick="showKnos('quarter')">本季度</a> <a onclick="showKnos('month')">本月</a> <a onclick="showKnos('week')">本周</a></center>
								<ol class="order" id="order1"></ol> 
							</div>
						</div>

						<!-- 知识排名 
						<div class="box">
							<span class="rc-tp"><span></span>
							</span>
							<div class="hd">
								<h3 class="msg">
									知识热度排行
								</h3>
							</div>
							<div class="bd" style="padding: 5px">
								<ol class="order" id="order4"></ol> 				
							</div>
						</div>
						-->
						<!-- 知识排名
						<div class="box">
							<span class="rc-tp"><span></span>
							</span>
							<div class="hd">
								<h3 class="msg">
									员工知识积分排行
								</h3>
							</div>
							<div class="bd" style="padding: 5px">
								<ol class="order" id="order2"></ol> 
							</div>
						</div>
						 -->
						<!-- 员工知识贡献排行 -->
						<div class="box">
							<span class="rc-tp"><span></span>
							</span>
							<div class="hd">
								<h3 class="msg">
									员工知识贡献排行
								</h3>
							</div>
							
							<div class="bd" style="padding: 5px">
								<center><a onclick="showPers('all')">总排行</a> <a onclick="showPers('year')">本年</a> <a onclick="showPers('quarter')">本季度</a> <a onclick="showPers('month')">本月</a> <a onclick="showPers('week')">本周</a></center>
								<ol class="order" id="order3"></ol>
							</div>
						</div>

					</div>
				</div>
				<%@ include file="/common/footer_eoms.jsp"%>
				