<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <title>tawWorkbenchSearch.jsp</title>

    <script type="text/javascript">
    function xmlHttp_obj()//根据不同浏览器返回xmlhttprequest对象
    {
     var xmlHttp;
try
    {
   // Firefox, Opera 8.0+, Safari
    xmlHttp=new XMLHttpRequest();
    }
catch (e)
    {

// Internet Explorer
   try
      {
      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
      }
   catch (e)
      {

      try
         {
         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
         }
      catch (e)
         {
         alert("您的浏览器不支持AJAX！");
         return false;
         }
      }
    }
return xmlHttp
    }
    function set_visible(on)//该方法只是让table隐藏或显示
    {
    var tab=document.getElementById("tab_down");
    if(on){tab.style.visibility="visible"}
    else{tab.style.visibility="hidden"}
    }
    function table_bind(tab,a)//将xmlhttprequest获取的数据绑定到table
    {
    while(tab.rows.length>0){tab.deleteRow(0)}//清空table
    if(a!=null && a[1].length>0)
    {
    set_visible(true);
    var g;
    for(var i=0;i<a[1].length;i++)
    {
    if(g=a[1][i])
    {var tr_new=tab.insertRow(g[3]);
    tr_new.onmouseover=function(){set_tr_on_only(this);}//IE不支持参数调用，故使用匿名函数
    tr_new.onmouseout=function(){tr_setoff(this);}//其实也很正常，带参数的话每次触发都需要动态去获取
    tr_new.onmousedown=function(){go(this.cells[0].innerHTML)}
    var td_1=tr_new.insertCell(0);
    td_1.className="gac_c";
    td_1.innerHTML= g[0];
    var td_2=tr_new.insertCell(1);
    td_2.className="gac_c";
    td_2.innerHTML=g[1];
    var td_2=tr_new.insertCell(2);
    td_2.className="gac_d";
    td_2.innerHTML=g[2];
    var td_2=tr_new.insertCell(3);
    td_2.className="gac_d";
    td_2.innerHTML=g[3];
    }
    }
    //新增，关闭tr
    var tr_close=tab.insertRow(a[1].length);
    tr_close.className="gac_e";
    var td=tr_close.insertCell(0);
    td.colSpan="2";
    td.innerHTML="<span onclick='set_visible(false)'>关闭</span>";
    }
    else{set_visible(false);}//若无数据则隐藏table
    }
    function go(v)//新窗口访问Google地址
    {//%20代表空格，空格转+，还有中文URL编码暂不操作。
    //window.open("http://www.google.cn/search?hl=zh-CN&newwindow=1&q="+v+"&btnG=Google+%E6%90%9C%E7%B4%A2&meta=&aq=f&oq=");
    }
    function for_keydown(t,e)//捕捉键盘按下事件的具体方法
    {
    var tab=document.getElementById("tab_down");
    var touch=e.keyCode
    if(touch==38 || touch==40)//38 上,40 下
    {var i=-1;
    var s_tr=get_tr_on(tab);
    if(s_tr!=null){i=s_tr.rowIndex}
    if(touch==40){if(i==tab.rows.length-2){i=0;}else{i++}}//由于有关闭tr，所以判断时要再减去1
    else{if(i<=0){i=tab.rows.length-2}else{i--;}}
    set_tr_on(i,tab);//根据新的rowIndex设定table里tr的高亮
    var str=tab.rows[i].cells[0].innerHTML;//获取该tr的内容
    document.getElementById("hid_q").value=str;
    t.value=str;
    }
    else if(touch==13){go(t.value)}//如果是按下Enter则直接跳转
    }
    function for_keyup(t)//输入框文字改变触发事件的方法
    {
    var hid_q=document.getElementById("hid_q");
    if(hid_q.value!=t.value)//只有更改过了才使用ajax
    {
    var a=xmlHttp_obj();//获取一个xmlhttprequest对象
        a.onreadystatechange=function()
      {
      if(a.readyState==4)//如果接收完成
        {
         table_bind(document.getElementById("tab_down"),eval(a.responseText));//执行绑定table，这里eval表示将字符串对象转化为数组对象
        }
      }
      //重点说明，由于xmlhttrequest不支持跨域访问，所以只好再弄个search.aspx访问google.cn，否则无法测试
    a.open("GET","${app}/workbench/contact/tawWorkbenchContactMain.do?method=doSearch&q="+ t.value,true);//encodeURIComponent表示进行中文编码
    a.send(null);
    hid_q.value=t.value
    }
    }
    function set_tr_on_only(tr)//根据传入指定tr，高亮显示之
    {
    set_tr_on(tr.rowIndex,tr.parentNode.parentNode)
    }
    function set_tr_on(index,tab)//根据传入rowIndex，高亮显示该tr，并还原其他tr的高亮状态
    {
    for(var i=0;i<tab.rows.length;i++)
    {
    if(index==i){tr_seton(tab.rows[i])}
    else{tr_setoff(tab.rows[i])}
    }
    }
    function get_tr_on(tab)//获取table里高亮显示的tr对象
    {
    var tr;
    for(var i=0;i<tab.rows.length;i++)
    {
    if(tr_ison(tab.rows[i])){tr=tab.rows[i];}
    }
    return tr;
    }
    function tr_ison(tr)//用该方法判断tr行是否被高亮选中
    {return tr.style.backgroundColor!=""}
    function tr_seton(tr)//设置tr行的高亮选中
    {tr.style.backgroundColor="#3366CC";}
    function tr_setoff(tr)//设置tr行取消高亮选中
    {tr.style.backgroundColor=""}
    
    function gotoSearch()
    {
    	var name = document.getElementById("txtSearch").value;
    	var iframe = document.getElementById("displayPages");   
          iframe.src ="${app}/workbench/contact/tawWorkbenchContactMain.do?method=getDisplay&name=" + name;   
    	
    }
    </script>
    <style type="text/css">
    .gac_m 
    {
background:white none repeat scroll 0 0;
border:1px solid black;
cursor:default;
font-size:13px;
line-height:17px;
margin:0;
position:absolute;
z-index:99;
}
.gac_d {
color:green;
font-size:10px;
overflow:hidden;
padding:0 3px;
text-align:right;
white-space:nowrap;
}
.gac_c {
overflow:hidden;
padding-left:3px;
text-align:left;
white-space:nowrap;}
.gac_e td {
font-size:10px;
line-height:15px;
padding:0 3px 2px;
text-align:right;
}
.gac_e span {
color:#0000CC;
cursor:pointer;
text-decoration:underline;
}
        #q
        {
            width: 257px;
        }
    </style>

  </head>
  
  <body>
    <input id="txtSearch" name="txtSearch" type="text" value="" onmouseup="for_keyup(this)" onkeyup="for_keyup(this)" onkeydown="for_keydown(this,event)" maxlength="2048" size="55" id="q" onblur="set_visible(false)"/>
<input id="search" type="button" class="btn" value="查找" onclick="gotoSearch()" /><br />
<table cellspacing="0" cellpadding="0" class="gac_m" style="width: 398px;visibility: hidden" id="tab_down">
<tbody>
</tbody>
</table>
<input type="hidden" value="" id="hid_q"  /><br/>
<% String nodeId = request.getParameter("id"); %>
<iframe id="displayPages" name="displayPages" align=left frameborder="0" src="${app}/workbench/contact/tawWorkbenchContactMain.do?method=getDisplay&nodeId=<% out.print(nodeId); %>" width="100%" height="400"></iframe>
  </body>
</html:html>
