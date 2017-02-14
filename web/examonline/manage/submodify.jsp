<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basePath",basePath);
%>

<style type="text/css">
	.title {width:150px; line-height:25px; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;}
	*{margin:0;padding:0; font-family:"微软雅黑";}
	.table_1{margin:4px;}
	.tltle_bg{background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fff;}
	.inter{background:url(<%=basePath%>examonline/manage/images/table_bg.gif) repeat-x; color:#9d9d9f;line-height:22px; font-size:12px;padding-

left:15px;}
	.table_2{background:#d7e1ed; border-top:1px solid #a2b3cf; border-bottom:1px solid #a2b3cf; line-height:27px;padding-left:15px;font-

size:12px;color:#546c8e;}
	.txt_input{background:#f0f1f6; border:1px solid #c4ceda;height:19px;width:60%;}
	.a_table_1,.a_table_2{ border-top:1px solid #fff; border-bottom:1px solid #d2e2ef; line-height:27px;color:#546c8e;padding-left:5px;font-size:12px;}
	.a_table_2{background:#e9eef2; }
	.a_table_1{background:#f1f1f1;}
	.sel_input{height:18px;border:1px solid #c5cdda; width:60%; line-height:18px;font-size:12px;}
	.table_3{height:51px; border-top:1px solid #fff; border-bottom:1px solid #fff; background:#f5f5f5;}
	.table_4{background:url(<%=basePath%>examonline/manage/images/table_bg2.gif) repeat-x;height:20px;}
	.datalist{border-bottom:1px solid #aebdd4;}
	.datalist th{background:url(<%=basePath%>examonline/manage/images/table_bg3.gif) repeat-x;color:#546c8e;border-left:1px solid #fff; border-right:1px 

solid #a1a7bd; font-size:12px; font-weight:normal;}
	.datalist td{border-top:1px solid #aebdd4; padding-left:5px; font-size:12px; line-height:22px;}
	.datalist tr {background-color:expression((this.sectionRowIndex%2==0)?"#fff":"#f0f1f6")}
	.datalist tr:hover, .datalist tr.altrow{background-color:#d7e1ed;/* 动态变色 */}
	.page_style{background:url(<%=basePath%>examonline/manage/images/table_bg4.gif) repeat-x;height:27px; font-size:12px; padding-left:20px;}
	
	/*-- button 风格 begin ---*/
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_14,.add_btn_15{height:21px;border:0;font-size:12px;font-family:"宋

体";line-height:21px;padding-left:3px;}
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06{color:#dbffff;}
		.add_btn_14,.add_btn_15{color:#eee;}
		.add_btn_02{background:url(<%=basePath%>examonline/manage/img/pic_w2.png) no-repeat;width:52px;}
		.add_btn_03{background:url(<%=basePath%>examonline/manage/img/pic_w3.png) no-repeat;width:65px;}
		.add_btn_04{background:url(<%=basePath%>examonline/manage/img/pic_w4.png) no-repeat;width:77px;}
		.add_btn_05{background:url(<%=basePath%>examonline/manage/img/pic_w5.png) no-repeat;width:90px;}
		.add_btn_06{background:url(<%=basePath%>examonline/manage/img/pic_w6.png) no-repeat;width:101px;}
		.add_btn_14{background:url(<%=basePath%>examonline/manage/img/pic_1w4.png) no-repeat;width:78px;}
		.add_btn_15{background:url(<%=basePath%>examonline/manage/img/pic_1w5.png) no-repeat;width:87px;}
	/*-- button 风格 end ---*/
</style>

<script type="text/javascript">
<!--
function ret()
{
	window.location.href="<%=basePath%>examonline/reportDetail.do?importId=<%=session.getAttribute("importId")%>";
}
function sub()
{
	document.forms[0].submit();
}
//-->
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>
题目信息修改
</title>
<link rel="stylesheet" type="text/css" href="css.css" />
<script language="javascript" src="<%=request.getContextPath()%>/css/checkform.js"></script>

<script language="JavaScript" >
</script>
<!--  eoms:DictType typeName="Specialty"/ -->
<!--eoms:DictType typeName="Manufacturer"/ -->
<!--eoms:DictType typeName="EquipId"/ -->

</head>
<body>
<html:form method="post" action="modifySubmit.do" enctype="multipart/form-data" >
<input type="hidden" name="subId" value="${ExamWarehouse.subId}">
<input type="hidden" name="issueType" value="${ExamWarehouse.issueType}">
<input type="hidden" name="importId" value="${ExamWarehouse.importId}">
<input type="hidden" name="contype" value="${ExamWarehouse.contype}">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>题目修改</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<br/>
<table cellpadding="0" cellspacing="0" width="95%" align="center" border="0" class="datalist">
  <tr >
    <td class="a_table_2" width="10%" align="center">
      标题
    </td>
    <td width="90%">
       
       <textarea rows="4" style="width:900" name="title">${ExamWarehouse.title}</textarea>
       
    </td>
  </tr>
  <c:if test="${(param.contype != 4 && param.contype != 5 
  			&& contype != 4 && contype != 5)}" var="condition"> <!-- 如果是非问答题 -->
	<tr>
	    <td class="a_table_2" width="10%" align="center">
	      选项
	    </td>
	    <td width="90%">
	          <textarea rows="4" style="width:900" name="options">${ExamWarehouse.options}</textarea>
	    </td>
  	</tr>
  <tr>
    <td class="a_table_2" width="10%" align="center">
      答案
    </td>
    <td width="90%">
       <input type="text" name="result" value="${ExamWarehouse.result}">
    </td>
  </tr>
  </c:if>
  <c:if test="${!condition}"> <!-- 如果是问答题 -->
	<tr>
	    <td class="a_table_2" width="10%" align="center">
	      标准答案
	    </td>
	    <td width="90%">
	          <textarea rows="4" style="width:900" name="options">${ExamWarehouse.options}</textarea>
	    </td>
  	</tr>
  </c:if>
  
 
  <tr>
    <td class="a_table_2" width="10%" align="center">
      难度
    </td>
    <td width="90%">
    <select name="difficulty">
    <option value="1" <c:if test="${ExamWarehouse.difficulty eq 1}">selected</c:if>>初级</option>
     <option value="2"<c:if test="${ExamWarehouse.difficulty eq 2}">selected</c:if>>中级</option>
    </select>
      
    </td>
  </tr>
  <tr>
    <td class="a_table_2" width="10%" align="center">
      专业
    </td>
    <td width="90%">
    <select name="specialty">
      ${specialtySelList}
    </select>
       
    </td>
  </tr>
  <tr>
    <td class="a_table_2" width="10%" align="center">
      厂家
    </td>
    <td width="90%">
    <select name="manufacturer">
    ${manufacturerSelList }
    </select>
    </td>
  </tr>
  
  <tr>
    <td class="a_table_2" width="10%" align="center">
      图片
    </td>
    <td width="90%">
    	<input type="file" name="uploadFile">
    </td>
  </tr>
  
  <tr>
    <td class="a_table_2" width="10%" align="center">
      备注
    </td>
    <td width="90%"><br>
    <textarea name="comment" cols="4" style="width:900">${ExamWarehouse.comment}</textarea>
    <br></td>
  </tr>
</table>

<br>
<center>
  <input name="button" type="button" class="add_btn_02"  value=" 提交" onclick="sub()" />
  <input name="button" type="button" class="add_btn_02"  value=" 返回" onclick="ret()" />
</center>

</html:form>
</body>
</html>
