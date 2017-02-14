<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.boco.eoms.duty.mgr.*"%>
<%@ page import="com.boco.eoms.base.util.*"%>
<%@ page import="com.boco.eoms.commons.system.session.form.*"%>
<%@ page import="com.boco.eoms.commons.system.user.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.boco.eoms.commons.system.user.model.*"%>
<jsp:directive.page import="com.boco.eoms.base.webapp.listener.UserCounterListener"/>
<html>
<head>
<title><fmt:message key="webapp.name" /></title>
<%@ include file="/common/meta.jsp"%> 
<script type="text/javascript" src="${app}/scripts/local/zh_CN.js"></script>
<script type="text/javascript" src="${app}/scripts/base/eoms.js"></script>
<script type="text/javascript">eoms.appPath = "${app}";</script>
<link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/index.css" />
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="/partner/deviceManagement/jquery-ui-1.8.14.custom/development-bundle/ui/jquery.effects.bounce.js"></script>
<script type="text/javascript" src="/partner/deviceManagement/jquery-ui-1.8.14.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js"></script>
<%
	    
		//PapersMgr papersMgr = (PapersMgr) ApplicationContextHolder.getInstance().getBean("papersMgr");
		//TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		//String userId = sessionForm.getUserid();
		//Map map = (Map) papersMgr.getPerson();
		//List list = (List) map.get("result");
		//TawSystemUser tawSystemUser = new TawSystemUser();
		boolean a = false;
		//for(int i = 0;i < list.size();i++){
		//	tawSystemUser = (TawSystemUser)list.get(i);
		//	String person = tawSystemUser.getUserid();
		//	if(person!=null&&person.equals(userId)){
		//		a = true;
		//		break;
		//	}
		//}
 %>
 
 
<script type="text/javascript">
	var jq=jQuery.noConflict();
	if(<%=a%>==true){
		window.open("${app}/duty/papers/papers.do?method=searchTixing");
	}

//以下代码用来捕获窗口关闭事件（IE6有效,IE7关闭标签页无效,FireFox无效）
if(eoms.isIE){ //firefox中没找到区分刷新和关闭的代码，不做判断
 // window.attachEvent("onunload",f_bofore,false);  // IE使用这个   
}
function openUser(){
  window.open('${app}/sysuser/tawSystemUsers.do?method=editUserInfo','','width=600,height=600,scrollbars=yes');
 }
	
function f_bofore(event)
{
  var e = window.event || event;

  //ie6中工作正常
  var ie6_closetest = (window.screenLeft >= 10000); 
  //ie7会把关闭标签页认为是刷新。并且鼠标放在关闭图标上按f5，也会判断为关闭。
  var ie7_closetest = (e.clientX > document.body.clientWidth && e.clientY < 0 || e.altKey);

  var url='<c:url value="/sysuser/tawSystemUsers.do?method=saveUserLogOut"/>';

  if(eoms.isIE6 && ie6_closetest){
    window.location.href=url;
  }
  else if(eoms.isIE7 && ie7_closetest){
    window.location.href=url;
  }
  else{
  }
} 

//打开在线用户列表窗口
function openOnlineUserWindow() {
  var url = '${app}/workbench/onlineuser/tawWorkbenchOnlineUsers.do';
  window.open(url, '', 'channelMode, menubar=no, toolbar=no, location=no, directories=no, status=yes, resizable=yes, scrollbars=yes, width=600, height=400, fullscreen=no');
}
var count = null;
//ajax得到在线用户数
function getOnlineUserCounter(){
   var url = "${app}/sysuser/tawSystemUsers.do?method=showOnlineUserCounter";
   count = getResponseText(url);
   var pic=document.getElementById('onlinecounter'); 
   pic.innerHTML = count;
   window.setTimeout(getOnlineUserCounter,300000); 
}
//密码过期提示
function passwdTimeOut(){
	if(""!=('<%=(String)request.getAttribute("remind")%>')){
		var aa = confirm('<%=(String)request.getAttribute("remind")%>') ;
		if(aa==true){
			window.open("${app}/sysuser/tawSystemUsers.do?method=editUserInfo") ;
		}
	}
}
//提示更新内容
function updateRemind(){
	alert("现场综合维护管理系统在9月3日晚上8点升级，升级需要将原有手机客户端软件必须卸载，重新下载安装客户端软件（下载地址为：http://61.156.3.123:8080/partner/apps.html），如果不进行重新下载安装可能会导致手机系统不能使用等，如有问题请咨询：053186052264。");
}
window.onload=function(){ 
   getOnlineUserCounter(); 
   passwdTimeOut();
   updateRemind();
} 
/*
window.onbeforeunload = function()
{   
    if(eoms.isIE){
	     var n = window.event.screenX - window.screenLeft;  
	     var b = n>document.documentElement.scrollWidth-20;  
	     if( b && window.event.clientY < 0 || window.event.altKey )  //不能判断ie7里的选项卡
	     {  
	        //alert("是关闭而非刷新"); 
	        //window.event.returnValue   =   "";     //这里可以放置你想做的操作代码   
	        window.location.href = '${app}/sysuser/tawSystemUsers.do?method=saveUserLogOut';
	        
	     } 
     } 
     else{
        window.location.href = '${app}/sysuser/tawSystemUsers.do?method=saveUserLogOut';
     }    
} */
if(eoms.isIE){
    window.onunload = function(){
          window.location.href = '${app}/sysuser/tawSystemUsers.do?method=saveUserLogOut';
    }
}
if(!eoms.isIE){
    window.onbeforeunload = function(){
          window.location.href = '${app}/sysuser/tawSystemUsers.do?method=saveUserLogOut';
    }
}
</script>
</head>

<body scroll="no" >
<!-- loading -->
<div id="loading-mask">&#160;</div>
<div id="loading">
<div class="loading-indicator">数据装载中,请稍候...</div>
</div>

<%@ include file="/common/extlibs.jsp"%>
  <!-- HEADER -->
  <div id="header">  
    <div id="top">
      <div id="logo">
          <div id="functions">
    	  <a href="javascript:openUser()">你好,${sessionScope.sessionform.username}</a> | 
          <a href="javascript:openOnlineUserWindow();" onmouseover="javascript:getOnlineUserCounter();">在线用户:<font id="onlinecounter"><%=UserCounterListener.getOnlineUserCounter()%></font>人</a>
		  <a href="javascript:if(confirm('确认退出系统么？')){location.href='${app}/sysuser/tawSystemUsers.do?method=saveUserLogOut';}">退出系统</a>
	    </div>
      </div>  
	</div>
	
	<div id="menubar">
	<%@ include file="/common/menu.jsp"%>
	</div>
  </div> 
  <%--
  <iframe id="portal-north" name="portal-north" frameborder="no" width="0" height="0"
	src="${scheme}://${serverName}:${serverPort}/fbrole.proper.loginSystem.do?EOSOperator/userID=<bean:write name='sessionform' property='userid'/>&EOSOperator/password=<bean:write name='sessionform' property='password'/>" ></iframe>
	
  <iframe id="bbs-north" name="flow-north" frameborder="no" width="0" height="0"
	src="http://10.32.2.16:9080/eoms35/j_security_check?j_username=<bean:write name='sessionform' property='id'/>&j_password=<bean:write name='sessionform' property='password'/>" ></iframe>
   --%>
  <!-- iframe -->
  <div id="msgWin"></div>
  <iframe id="mainFrame" name="mainFrame" frameborder="0" width="0" height="0" src="<c:url value="${welcomePage}"/>"></iframe>
	  <iframe name="remindMsg" src="${app}/partner/property/remind.do?method=remindMsgList" width="0"  height="0" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="auto">
	  </iframe>
  <div id="msgDiv"></div>
</iframe>
  <script type="text/javascript" src="${app}/scripts/layout/index.js"></script>  
</body>
</html>