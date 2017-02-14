<%@ page language="java" pageEncoding="UTF-8"  %>
<%@ include file="/intelpages/commons/taglibs.jsp"%>

<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style type="text/css">
	.title {width:150px; line-height:25px; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;}
	*{margin:0;padding:0; font-family:"微软雅黑";}
	.table_1{margin:4px;}
	.tltle_bg{background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fff;}
	.inter{background:url(<%=basePath%>examonline/manage/images/table_bg.gif) repeat-x; color:#9d9d9f;line-height:22px; font-size:12px;padding-left:15px;}
	.table_2{background:#d7e1ed; border-top:1px solid #a2b3cf; border-bottom:1px solid #a2b3cf; line-height:27px;padding-left:15px;font-size:12px;color:#546c8e;}
	.txt_input{background:#f0f1f6; border:1px solid #c4ceda;height:19px;width:60%;}
	.a_table_1,.a_table_2{ border-top:1px solid #fff; border-bottom:1px solid #d2e2ef; line-height:27px;color:#546c8e;padding-left:5px;font-size:12px;}
	.a_table_2{background:#e9eef2; }
	.a_table_1{background:#f1f1f1;}
	.sel_input{height:18px;border:1px solid #c5cdda; width:60%; line-height:18px;font-size:12px;}
	.table_3{height:51px; border-top:1px solid #fff; border-bottom:1px solid #fff; background:#f5f5f5;}
	.table_4{background:url(<%=basePath%>examonline/manage/images/table_bg2.gif) repeat-x;height:20px;}
	.datalist{border-bottom:1px solid #aebdd4;}
	.datalist th{background:url(<%=basePath%>examonline/manage/images/table_bg3.gif) repeat-x;color:#546c8e;border-left:1px solid #fff; border-right:1px solid #a1a7bd; font-size:12px; font-weight:normal;}
	.datalist td{border-top:1px solid #aebdd4; padding-left:5px; font-size:12px; line-height:22px;}
	.datalist tr {background-color:expression((this.sectionRowIndex%2==0)?"#fff":"#f0f1f6")}
	.datalist tr:hover, .datalist tr.altrow{background-color:#d7e1ed;/* 动态变色 */}
	.page_style{background:url(<%=basePath%>examonline/manage/images/table_bg4.gif) repeat-x;height:27px; font-size:12px; padding-left:20px;}
</style>

<html>
   <head>
   <title>${form.pagetitle}</title>
	<!--link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css" --> 
 </head>
	<div id="tabs">
	<body>
		<!---正文开始------------------------------------------------------------------------------------------>
		<center>
		
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
	        <tr>
	          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
	          <td>题库统计</td>
	          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
	        </tr>
</table>

<div id="sheet-list">

<table width="100%" cellspacing="1" cellpadding="1"  class="datalist" border="0"> 
	<tr >
		  <td colspan="12" width="70%"><b></b></td>		      
    </tr>
<!-- 用户可编辑区 开始 -->
    <tr class="a_table_2">
    <!-- 此处写标题 -->
    <td width="15%" nowrap="nowrap" valign="bottom">
    <p align="left">
  难度
     </p>
     </td>
     <td width="15%" nowrap="nowrap" valign="bottom">
    <p align="left">
 华为
     </p>
     </td>
     <td width="15%" nowrap="nowrap" valign="bottom">
    <p align="left">
   中兴
     </p>
     </td>
     <td width="15%" nowrap="nowrap" valign="bottom">
    <p align="left">
 阿尔卡特
     </p>
     </td>
  <td width="15%" nowrap="nowrap" valign="bottom">
    <p align="left">
大唐
     </p>
     </td>
     <td width="15%" nowrap="nowrap" valign="bottom">
    <p align="left">
不分厂家
     </p>
     </td>
  </tr>
   <!-- 以下的 showlist为固定参数,表示从数据库取出的数据列表,row表示showlist列表中的每一条数据的别名,这个名字自己自己取 -->
   <c:forEach items="${list}" var="map">
	  <tr class="a_table_1">
	  <!-- 此处写从数据库取出的数据列表 -->
		  <td nowrap="nowrap" >&nbsp;${map.difficultyName}&nbsp;</td>
		  <td nowrap="nowrap" >&nbsp;${map.k1180201}&nbsp;</td>
		  <td nowrap="nowrap" >&nbsp;${map.k1180202}&nbsp;</td>
		  <td nowrap="nowrap" >&nbsp;${map.k1180203}&nbsp;</td>
		  <td nowrap="nowrap" >&nbsp;${map.k1180204}&nbsp;</td>
		  <td nowrap="nowrap" >&nbsp;${map.k1180205}&nbsp;</td>
	   </tr>
    </c:forEach>
	<!-- 用户可编辑区 结束 -->
</table>





