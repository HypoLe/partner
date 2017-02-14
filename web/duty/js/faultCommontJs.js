
 
 function faultsheet(sheetId){ // 查看重要故障上报工单信息
				if(sheetId.indexOf("YN-")==-1) {
					alert("工单号不正确!");
					return false;
				}
				if(sheetId.length<15) {
					alert("工单号长度不正确!");
					return false;
				}

				if(sheetId.indexOf("YN-051-")!=-1) {
					window.open("../alarmWatch.jsp?userName=zhibanzhang&sheetNo=" + sheetId,"故障处理工单",   "resizable=1,title   =no,menubar=no,toolbar=yes,scrollbars=yes,width=640,height=450,left=70,top=25");
				} else if(sheetId.indexOf("YN-22-")!=-1) {
					window.open("../alarmWatch.jsp?userName=zhibanzhang&sheetNo=" + sheetId,"故障处理工单",   "resizable=1,title   =no,menubar=no,toolbar=yes,scrollbars=yes,width=640,height=450,left=70,top=25");
					showNewHtmlDlg('http://10.168.65.45/wfworksheet/Wfbtsfaultsheet/queryDetail.do?sheetKey=' + sheetId,'','基站故障工单',680,760,true,"基站故障工单");
				} else if(sheetId.indexOf("YN-12-")!=-1) {
					window.open('http://10.168.65.45/wfworksheet/Wftasksheet/queryDetail.do?sheetKey=' + sheetId,"任务工单",   "resizable=1,title   =no,menubar=no,toolbar=yes,scrollbars=yes,width=640,height=450,left=70,top=25");
				} else if(sheetId.indexOf("YN-15-")!=-1) {
					window.open('http://10.168.65.45/wfworksheet/Wfreportsheet/queryDetail.do?sheetKey=' + sheetId,"重要故障上报工单",   "resizable=1,title   =no,menubar=no,toolbar=yes,scrollbars=yes,width=640,height=450,left=70,top=25");
				} else if(sheetId.indexOf("YN-21-")!=-1) {
					window.open('http://10.168.65.45/wfworksheet/Supportsheet/queryDetail.do?sheetKey=' + sheetId,"技术支援工单",   "resizable=1,title   =no,menubar=no,toolbar=yes,scrollbars=yes,width=640,height=450,left=70,top=25");
				} else if(sheetId.indexOf("YN-23-")!=-1) {
					window.open('http://10.168.65.45/wfworksheet/Dlfaultsheet/queryDetail.do?sheetKey=' + sheetId,"动力环境工单",   "resizable=1,title   =no,menubar=no,toolbar=yes,scrollbars=yes,width=640,height=450,left=70,top=25");
				} else {
					alert("工单号不正确，只能查看故障工单!");
				}
				
				return true;
}	

function getDateStr(day) {
	var d,s,t;
	d=new Date(new Date().getTime()+day*1000*60*60*24);
	s=d.getFullYear().toString(10)+"-";
	t=d.getMonth()+1;
	s+=(t>9?"":"0")+t+"-";
	t=d.getDate();
	s+=(t>9?"":"0")+t+" ";
	t=d.getHours();
	s+=(t>9?"":"0")+t+":";
	t=d.getMinutes();
	s+=(t>9?"":"0")+t+":";
	t=d.getSeconds();
	s+=(t>9?"":"0")+t;
	return s;
}

function dateCompare(date1,date2){ // 比较date1是否比date2大,如果是返回true.
	if(date1==null||date1.length<16) {
		return false;
	} 
	if(date2==null||date2.length<16) {
		return true;
	} 
	d1=new Date(date1.replace(/-/,"/"));
	d2=new Date(date2.replace(/-/,"/"));     
			
	//比较时间大小，开始时间一定要小于结束时间  
	if(Date.parse(d1)>Date.parse(d2)) {	
		return true;        
	} else {
		return false;
	}
}

function ifEffects(ifEffectValue) {
	var obj = document.getElementById("ifEffectDiv");
	if(ifEffectValue == 1) { 
		obj.style.display = "block";
	 } else {
		obj.style.display = "none";
	}
}

//// 初始化弹出div
function fun_initMyBox(){
   var msgw = 500; // 提示窗口的宽度
   var msgh = 200; // 提示窗口的高度
   var titleheight = 25 // 提示窗口标题高度
   var bordercolor = "#336699"; // 提示窗口的边框颜色
  
  var sWidth,sHeight;
  sWidth=document.body.offsetWidth;//浏览器工作区域内页面宽度 或使用 screen.width//屏幕的宽度
  sHeight=screen.height;//屏幕高度（垂直分辨率） 
  //背景层（大小与窗口有效区域相同，即当弹出对话框时，背景显示为放射状透明灰色）
  //创建一个div对象（背景层） //动态创建元素，这里创建的是 div
  //定义div属性，即相当于(相当于，但确不是，必须对对象属性进行定义
  //<div id="bgDiv" style="position:absolute; top:0; background-color:#777; filter:progid:DXImagesTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75); opacity:0.6; left:0; width:918px; height:768px; z-index:10000;"></div>
  var bgObj=document.createElement("div");
  bgObj.setAttribute('id','bgDiv');
  bgObj.style.position="absolute";
  bgObj.style.top="0";
  bgObj.style.background="white";
  bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
  bgObj.style.opacity="0.6";
  bgObj.style.left="0";
  bgObj.style.width=sWidth + "px";
  bgObj.style.height=sHeight + "px";
  bgObj.style.zIndex = "10000";
  bgObj.style.display = 'none';
  document.body.appendChild(bgObj);//在body内添加该div对象
   
   //创建一个div对象（提示框层）  
   //定义div属性，即相当于
   msgObj=document.createElement("div");
   msgObj.setAttribute("id","msgDiv");
   msgObj.setAttribute("align","center");
   msgObj.style.background = "white";
   msgObj.style.border="1px solid " + bordercolor;
   msgObj.style.position = "absolute";
   msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
   msgObj.style.width = msgw + "px";
   msgObj.style.height = msgh + "px";
   msgObj.style.textAlign = "center";
   msgObj.style.lineHeight ="25px";
   msgObj.style.zIndex = "10001";
   msgObj.style.display = 'none';
            
   //定义h4的属性，即相当于
   //<h4 id="msgTitle" align="right" style="margin:0; padding:3px; background-color:#336699; filter:progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100); opacity:0.75; border:1px solid #336699; height:18px; font:12px Verdana,Geneva,Arial,Helvetica,sans-serif; color:white; cursor:pointer;" onclick="">关闭</h4>
   var title=document.createElement("h4");//创建一个h4对象（提示框标题栏）
   title.setAttribute("id","msgTitle");
   title.setAttribute("align","right");
   title.style.margin="0";
   title.style.padding="3px";
   title.style.background=bordercolor;
   title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
   title.style.opacity="0.75";
   title.style.border="1px solid " + bordercolor;
   title.style.height="18px";
   title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif";
   title.style.color="white";
   title.style.cursor="pointer";
   title.innerHTML="关闭";
   title.onclick=removeObj;
   
   var button=document.createElement("input");//创建一个input对象（提示框按钮）
   //定义input的属性，即相当于
   //<input type="button" align="center" style="width:100px; align:center; margin-left:250px; margin-bottom:10px;" value="关闭">
   button.setAttribute("type","button");
   button.setAttribute("value","关闭");
   button.style.width="60px";
   button.style.align="center";
   button.style.marginLeft="250px";
   button.style.marginBottom="10px";
   button.style.background=bordercolor;
   button.style.border="1px solid "+ bordercolor;
   button.style.color="white";
   button.onclick=removeObj;
   
   function removeObj(){//点击标题栏触发的事件
     bgObj.style.display = 'none';
     msgObj.style.display = 'none';
     ///document.body.removeChild(bgObj);//删除背景层Div
     ///document.getElementById("msgDiv").removeChild(title);//删除提示框的标题栏
     ///document.body.removeChild(msgObj);//删除提示框层
   }
   
   document.body.appendChild(msgObj);//在body内添加提示框div对象msgObj
   document.getElementById("msgDiv").appendChild(title);//在提示框div中添加标题栏对象title
  
   var txt=document.createElement("p");//创建一个p对象（提示框提示信息）
   //定义p的属性，即相当于
   //<p style="margin:1em 0;" id="msgTxt">测试效果</p>
   txt.style.margin="1em 0";
   txt.setAttribute("id","msgTxt");
   document.getElementById("msgDiv").appendChild(txt);//在提示框div中添加提示信息对象txt
   ///document.getElementById("msgDiv").appendChild(button);//在提示框div中添加按钮对象button
   document.body.appendChild(msgObj);//在body内添加提示框div对象msgObj
}
 
//// 点击弹出设备所属单位多选chkbox层 add by chenqin 2008-10-24
	function fun_showMyBox(obj) {
	    var msgObj = document.getElementById('msgDiv');
	    var bgObj = document.getElementById('bgDiv');
	    var msgTxt = document.getElementById('msgTxt');
	    var ttop  = obj.offsetTop;     //TT控件的定位点高
	    var thei  = obj.clientHeight;  //TT控件本身的高
	    var tleft = obj.offsetLeft;    //TT控件的定位点宽
	    while (obj = obj.offsetParent){ttop+=obj.offsetTop; tleft+=obj.offsetLeft;}
	    msgObj.style.top = ttop + thei + 6;
	    msgObj.style.left = tleft;
	    msgObj.style.display = '';
	    bgObj.style.display = '';
	    if ('' == msgTxt.innerHTML) {
	       msgTxt.innerHTML = document.getElementById('innerHtml').innerHTML;//来源于函数调用时的参数值
	    }
	    event.returnValue = false;
	}

//// 展现选择的值 add by chenqin 2008-10-24
	function fun_setValue(label, value ) {
	    ///// 获取checkBox array
	    var chkBton = document.getElementsByName("my_check");
	    var chkVlue = ''; // checkBox的值
	    var chkInnerTxt = ''; // checkBox的text
	    var spilt_;
	    var length = chkBton.length / 2;
	    for(var i = length;i < (chkBton.length); i++){
	        if (chkBton[i].checked) {            
	            spilt_ = chkBton[i].value.split(',');
	            for(var j = 0 ;j < spilt_.length; j++){
	                //// 设计时checkBox 的 value 为 值,文本
	                //// 所以需要解析
	                chkVlue = chkVlue + '#' + spilt_[0] + '#';
	                chkVlue = chkVlue + ",";	
	                chkInnerTxt = chkInnerTxt + spilt_[1] + ' ';
	                break; 
	            }       
	        }	        
	    }
	    chkVlue = chkVlue.substring(0,chkVlue.length - 1);
	    chkInnerTxt = chkInnerTxt.substring(0,chkInnerTxt.length - 1);
	    $(label).value = chkInnerTxt; //// 展示文本
	    $(value).value = chkVlue; //// 存于数据库的值
	    $('msgDiv').style.display = 'none';
	    $('bgDiv').style.display = 'none';  
	}
	
	function showNewHtmlDlg(_sUrl,_sArgs,_sTitle,_sHeight,_sWidth,_bIsRefresh,_title)
    {
      var sUrl="../HtmlDialogvalue.jsp?myUrl="+filterToUrl(_sUrl)+"&Width="+_sWidth+"&Height="+_sHeight+"&title="+_title;
      var sTop=(window.screen.availHeight-_sHeight)/2;
      var sLeft=(window.screen.availWidth-_sWidth)/2;
      var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:no;help: No; resizable: No; status: No;";

      var a;
      if(_sArgs=='')
      {
        a=new Array();
      }
      else a=_sArgs;

      //记录对话框标题
      a[a.length]=_sTitle;
      //记录关闭对话框需要执行的参数
      a[a.length]="";
      //记录是否刷新
      a[a.length]=_bIsRefresh;
      a[a.length]=false;

      var sValue=window.showModalDialog(sUrl,a,sFeatures);
      var ElBackUrl=document.getElementById("BackUrl");
      if(ElBackUrl)
      {
        var sUrl=ElBackUrl.value;
        if(a[a.length-3].length>1)
          eval(a[a.length-3]);
       if(a[a.length-2]&&a[a.length-1])window.location.href=sUrl;
      }
      //else alert("没有定义自身的链接！")
    }

	function replace(str, replace_what, replace_with)
    {
      var ndx = str.indexOf(replace_what);
      var delta = replace_with.length - replace_what.length;

      while (ndx >= 0)
      {
        str = str.substring(0,ndx) + replace_with + str.substring(ndx + replace_what.length);
        ndx = str.indexOf(replace_what, ndx + delta + 1);
      }
      return str;
    }
    function filterToUrl(str)
    {
      str=replace(str,"%","%25");
      str=replace(str,"#","%23");
      str=replace(str,"?","%3F");
      str=replace(str,"/","%2F");
      str=replace(str,"=","%3D");
      str=replace(str,",","%2C");
      str=replace(str,";","%3B");
      str=replace(str,"&","%26");
      str=replace(str," ","%20");
      str=replace(str,"<","%3C");
      str=replace(str,">","%3E");
      str=replace(str,"'","%27");
      return str;
    }
    
    
var expand = 0; // 判断是否有子过程展开,有子过程展开则不允许排序
		
// 展开子过程
// 算法修改为点击一行后插入一行,然后展示子过程
// 取消原来的为每一行添加一个子行
function showSubList(perid, curObj, tabName) {
    var parNode = document.getElementById(tabName); //定位到table上
    var curRow = curObj.parentElement.parentElement.rowIndex; // 当前行
    var object = document.getElementById('subListView' + perid);
    if (null != object && "undefined" != typeof object) {
        expand = expand - 1;
        parNode.deleteRow(curRow + 1);
        curObj.firstChild.src = "../duty/images/plus.gif";
    } else {
        expand = expand + 1;
        parNode.insertRow(curRow + 1);
        parNode.rows[curRow + 1].bgColor="#FFFFFF";
        parNode.rows[curRow + 1].insertCell(0);
        parNode.rows[curRow + 1].cells[0].colSpan = '9';
        parNode.rows[curRow + 1].cells[0].id = 'subListView' + perid;
        document.getElementById("subListIframe").src = "yndutyevent.do?method=sublist&eventid=" + perid;
		curObj.firstChild.src = "../duty/images/nofollow.gif";		    
    }		       				 			
}	

// 有些请求路径再深一级
function showSubList2(perid, curObj, tabName) {
    var parNode = document.getElementById(tabName); //定位到table上
    var curRow = curObj.parentElement.parentElement.rowIndex; // 当前行
    var object = document.getElementById('subListView' + perid);
    if (null != object && "undefined" != typeof object) {
        expand = expand - 1;
        parNode.deleteRow(curRow + 1);
        curObj.firstChild.src = "../images/plus.gif";
    } else {
        expand = expand + 1;
        parNode.insertRow(curRow + 1);
        parNode.rows[curRow + 1].bgColor="#FFFFFF";
        parNode.rows[curRow + 1].insertCell(0);
        parNode.rows[curRow + 1].cells[0].colSpan = '9';
        parNode.rows[curRow + 1].cells[0].id = 'subListView' + perid;
        document.getElementById("subListIframe").src = "../yndutyevent.do?method=sublist&eventid=" + perid;
		curObj.firstChild.src = "../images/nofollow.gif";		    
    }		       				 			
}	
    
    function GoBack(){
   window.history.back();
}

//// 点击列标题排序
//// add by chenqin 2008-10-09
var sorttype = 1; //desc
function sortByCol(colNum, tabName, objCol){
	if (expand > 0) return;
	var txt = objCol.innerHTML; 
	var sortAsc = false; 
	if (txt.charAt(txt.length - 1)=='↓')
	sortAsc = true; 
	var objTable = document.getElementById(tabName); 
	for (var k = 0; k < objTable.rows(0).cells.length; k++) { 
	txt = objTable.rows(0).cells(k).innerHTML;
	if ((txt.charAt(txt.length - 1)=='↓') || (txt.charAt(txt.length - 1)=='↑')) 
	objTable.rows(0).cells(k).innerHTML = txt.substring(0, txt.length - 1); 
	} 
	objCol.innerHTML += (sortAsc?"↑":"↓"); 
	//排序         
	var parNode = document.getElementById(tabName); //定位到table上      
	for(var i=0; i< parNode.rows.length-1; i++)
	{
	 for(var n=1; n< parNode.rows.length-1-i; n++){  
	  var ifrom = parNode.rows.length-n;
	  var ito = parNode.rows.length-n-1;
	
	  if(sorttype == 1){ // 降序排列
	    if(parNode.rows[ifrom].cells[colNum].innerText > parNode.rows[ito].cells[colNum].innerText){
	        parNode.moveRow(ifrom,ito); //from-to
	    }
	  } else { // 升序排列
	    if(parNode.rows[ifrom].cells[colNum].innerText <  parNode.rows[ito].cells[colNum].innerText){
	        parNode.moveRow(ifrom,ito); //from-to
	    }
	  }
	  }
	} 
	sorttype = 0 - sorttype; //asc
}

// 选择所有
function checkAll(name) {     
     var el = document.getElementsByTagName('input');     
     var len = el.length;     
     for(var i=0; i<len; i++) {         
         if((el[i].type=="checkbox") && (el[i].name==name)) {
            el[i].checked = true;         
            }     
     } 
} 

//// checkBox 取消全选
function clearAll(name) {     
    var el = document.getElementsByTagName('input');     
    var len = el.length;     
    for(var i=0; i<len; i++) {         
       if((el[i].type=="checkbox") && (el[i].name==name)) {             
           el[i].checked = false;         
        }     
    } 
}

//// 获取被勾选的checkBox个数 
function fun_getChkedCount(chkBox) {
///// 获取checkBox array
	var chkBton = document.getElementsByName(chkBox);	    
	var length = chkBton.length / 2;
	var chkedCount = 0
	for (var i = length;i < (chkBton.length); i++) {
	    if (chkBton[i].checked) {
	        chkedCount++;
	    }
    }
    return chkedCount;
}  
