
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import ="com.boco.eoms.common.tree.WKTree"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
String regionId = StaticMethod.nullObject2String(request.getAttribute("REGIONID"),"1");
String path = request.getContextPath();
String url = "";
String dept1 = "";
String wsClass = "-1";
String sdomIds = StaticMethod.nullObject2String(request.getAttribute("SDOMIDS"));
%>
<html>
<head>
<script type="text/javascript" src="<%=path%>/css/finallytree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/examonline/style.css" />
<script language="javascript">
<!-- 部门树
Ext.onReady(function(){
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
	userViewer = new Ext.JsonView("dept-list",	
			'<div id="user-{id}" class="viewlistitem-user">{name}</div>',
			{ 
				multiSelect: false,		
				emptyText : '<div></div>'								
			}
		);
		userViewer.refresh();
		userTree = new xbox({
			btnId:'deptTreeBtn',dlgId:'dlg-user',	
			treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'部门',treeChkMode:'single',treeChkType:'dept',
			viewer:userViewer,saveChkFldId:'deptid' 
		});
	});
	//人员树
	Ext.onReady(function(){
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
	userViewer = new Ext.JsonView("user-list",	
			'<div id="user-{id}" class="viewlistitem-user">{name}</div>',
			{ 
				multiSelect: false,		
				emptyText : '<div></div>'								
			}
		);
		userViewer.refresh();
		userTree = new xbox({
			btnId:'userTreeBtn',dlgId:'dlg-user',	
			treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'部门',treeChkMode:'single',treeChkType:'user',
			viewer:userViewer,saveChkFldId:'userId' 
		});
	});
	
	function sl(v)
	{
	if(v==1)
	{
	document.getElementById("suser").style.display="block";
	document.getElementById("sdept").style.display="none";
	document.getElementById("deptid").value="";
    document.getElementById("dept-list").innerHTML="";
	}
	else if(v==2)
	{
	document.getElementById("suser").style.display="none";
	document.getElementById("sdept").style.display="block";
	document.getElementById("userId").value="";
	document.getElementById("user-list").innerHTML="";
	}
	}
	
	function chk()
	{
		if(document.getElementById("userId").value!=""||document.getElementById("deptid").value!=""){
			//document.forms[0].submit();
			document.forms[0].submit();
			//return true;
		}else{
			alert("请选择待查询的部门或人员!");
			return false;
		}
	}
//-->
</script>
</head>

<title>查询人员</title>

<body background="<%=request.getContextPath()%>/images/img/bg001.gif" onLoad="initIt();">

<form method="post" action="<%=request.getContextPath()%>/examonline/queryDo.do" >
<!--html:hidden property="strutsAction" value="3"/-->

<style type="text/css">
	body{font-size: 9pt;color: #000000;LINE-HEIGHT: 18px}
	#tree {position: absolute;
	visibility: hidden;
	left: 72%; top: 10%;
	z-index:2;
	background-color:#ECF2FE;
	padding:12px;
	border-top:1px solid #FeFeFe;
	border-left:1px solid #FeFeFe;
	border-right:3px solid #8E9295;
	border-bottom:3px solid #8E9295;
	}
</style>


<input type="hidden" name="path" id="path" value="<%=path%>">
<input type="hidden" name="sdomids" id="sdomids" value="<%=sdomIds%>">
<div align="center">
  <center>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=request.getContextPath()%>/examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>练习分数查询</td>
          <td width="27" align="right"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<table class="table_2" width="100%">
 <tr>
    <td >
    <select onchange="sl(this.value)">
      <option > 请选择查询类型</option>
     <option value="1">人员</option>
     <option value="2">部门</option>
     </select>
    </td>
    </tr>
  <tr>
   <td>
	  <div id="suser" style="display: none">
	    <div id="user-list" class="viewer-list"></div>
	    <input style="border:none;width:83px; height:25px;BACKGROUND-IMAGE:url(<%=request.getContextPath()%>/examonline/manage/images/button_6(1).png);BACKGROUND-REPEAT: no-repeat" width="16" height="16" type="button" value="" id="userTreeBtn" class="btn"/>
	    <INPUT TYPE="hidden" name="userId" id="userId" value="">
	    
	    </div>
	  <div id="sdept" style="display: none">
	        <div id="dept-list" class="viewer-list"></div>
	        <input style="border:none;width:83px; height:25px;BACKGROUND-IMAGE:url(<%=request.getContextPath()%>/examonline/manage/images/button_5(1).png);BACKGROUND-REPEAT: no-repeat" width="16" height="16" type="button" value="" id="deptTreeBtn" class="btn"/>
	        <INPUT TYPE="hidden" name="deptid" id="deptid" value="">
	    </div>
    </td>
  </tr>
</table>
  </center>
  <table border="0" width="95%" align="left" cellspacing="0">
  <tr>
    <td width="100%" height="32" >
    	<a href="#" onClick="return chk()"><img src="<%=request.getContextPath()%>/examonline/manage/images/button_10(1).png" /></a>
    </td>
  </tr>
</table>
</div>
</form>

</body>

</html>
