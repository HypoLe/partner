<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pg"  uri="http://jsptags.com/tags/navigation/pager" %>


<html>	
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
	
	/*-- button 风格 begin ---*/
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_14,.add_btn_15,.add_btn_frist,.add_btn_prev,.add_btn_next,.add_btn_last{height:21px;border:0;font-size:12px;font-family:"宋体";line-height:21px;padding-left:3px;}
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_frist,.add_btn_prev,.add_btn_next,.add_btn_last{color:#dbffff;}
		.add_btn_14,.add_btn_15{color:#eee;}
		.add_btn_02{background:url(<%=basePath%>examonline/manage/img/pic_w2.png) no-repeat;width:52px;}
		.add_btn_03{background:url(<%=basePath%>examonline/manage/img/pic_w3.png) no-repeat;width:65px;}
		.add_btn_04{background:url(<%=basePath%>examonline/manage/img/pic_w4.png) no-repeat;width:77px;}
		.add_btn_05{background:url(<%=basePath%>examonline/manage/img/pic_w5.png) no-repeat;width:90px;}
		.add_btn_06{background:url(<%=basePath%>examonline/manage/img/pic_w6.png) no-repeat;width:101px;}
		.add_btn_14{background:url(<%=basePath%>examonline/manage/img/pic_1w4.png) no-repeat;width:78px;}
		.add_btn_15{background:url(<%=basePath%>examonline/manage/img/pic_1w5.png) no-repeat;width:87px;}
	/*-- 翻页按钮 --*/
		.add_btn_frist{background:url(<%=basePath%>examonline/manage/images/first.gif) no-repeat;width:15px;}
		.add_btn_prev{background:url(<%=basePath%>examonline/manage/images/previous.gif) no-repeat;width:12px;}
		.add_btn_next{background:url(<%=basePath%>examonline/manage/images/next.gif) no-repeat;width:12px;}
		.add_btn_last{background:url(<%=basePath%>examonline/manage/images/last.gif) no-repeat;width:15px;}
	/*-- 数字按钮 --*/	
		.num_btn_01{border:0;background:no-repeat;text-decoration:underline;}
	/*-- button 风格 end ---*/
</style>

   <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	</head>

	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
	        <tr>
	          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
	          <td>试题查询</td>
	          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
	        </tr>
	</table>

	<div id="tabs">

	<body>
 
	<!-- 用户可编辑区 开始 注:可编辑区已经被包含于本平台通用表单内,无须在此另建表单-->
	<form id="form1" name="form1" method="post" action="examCountQuery.do?method=goExamDetailListPage">
	
		<img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 专业:
		 <eoms:comboBox  name="specialtySel" id="specialtySel" sub="wangyou" defaultValue="" initDicId="11801" alt="allowBlank:false" onchange="select();"/>
        —<eoms:comboBox name="wangyou" defaultValue="" id="wangyou" sub="youhua" initDicId="" alt="allowBlank:false" /> 
        —<eoms:comboBox name="youhua" defaultValue="" id="youhua" initDicId="" alt="allowBlank:false" onchange="select();"/>
           
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="<%=basePath%>examonline/manage/images/icon_4.gif" width="15" height="14" /> 厂家:
		<select name="manufacturer" onclick="select();" >
			<option value="">不限厂家</option>
		 	<option value='1180201'>华为</option>
		   	<option value='1180202'>中兴</option>
		    <option value='1180203'>阿尔卡特</option>
		    <option value='1180204'>大唐</option>
		    <option value='1180205'>不分厂家</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 易难度:
		<select style="width: 80px;display: none" name="difficulty3"  class="sel_input">
			<option value="">不限难度</option>
         	<option value="1">初级</option>
          	<option value="2">中级</option>
          	<option value="3">高级</option>
        </select>
		<select style="width: 80px;display: " name="difficulty"  class="sel_input">
		  	<option value="">不限难度</option>
          	<option value="1">初级</option>
          	<option value="2">中级</option>
        </select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 题型:       
		<select style="width:80px" name="contype" onchange="chg(this)">
			<option value="">不限题型</option>
			<option value="1">单选题</option>
			<option value="2">多选题</option>
			<option value="3">判断题</option>
			<option value="4">简答题</option>
			<option value="5">论述题</option>
		  </select>
		<br/>
		<input name="button" type="submit" class="add_btn_02"  value=" 查询"/>
	</form>
<!---正文开始------------------------------------------------------------------------------------------>
<center>
		
<table width="100%"  cellspacing="1" cellpadding="1" class="datalist" align="center" border="0"> 

<!-- 用户可编辑区 开始 -->
	<tr class="a_table_2">
		<th style="text-align:center;">题干</th>
		<th style="text-align:center;">选项</th>
		
	</tr>
	<c:forEach items="${showlist}" var="obj">
		<tr class="a_table_1" height="35">
		
			
			<td >${obj.title}</td>
			<td >${obj.options}</td>
			
		
		</tr>
	</c:forEach>
	 <!-- 用户可编辑区 结束 -->
</table>

</center>
<c:if test="${showlist!=null}">
	<!-- 分页 -->
	<table width="100%" class="table_2" height="20" border="0" cellpadding="0" cellspacing="0">
       <TBODY>
					<TR>
						<TD height=28 align=right vAlign=center noWrap>
							&nbsp;&nbsp;
							<!-- 可以在这里插入分页导航条 -->
							<pg:pager url="examCountQuery.do" items="${resultSize }"
								export="currentPageNumber=pageNumber" maxPageItems="5">
								
								<pg:param name="method" value="goExamDetailListPage"/>
								<pg:param name="pageHql" value="${pageHql}"/>
								
								<pg:first>
									<input name="button" type="button" class="add_btn_frist"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:first>
								<pg:prev>
									<input name="button" type="button" class="add_btn_prev"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:prev>
								<pg:pages>
									<c:choose>
										<c:when test="${currentPageNumber eq pageNumber }">
											<font color="red">${pageNumber }</font>
										</c:when>
										<c:otherwise>
											<input name="button" type="button" class="num_btn_01" value="${pageNumber}" onclick="window.location.href='${pageUrl}'" />
										</c:otherwise>
									</c:choose>
								</pg:pages>
								<pg:next>
									<input name="button" type="button" class="add_btn_next"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:next>
								<pg:last>
									<input name="button" type="button" class="add_btn_last"  value="" onclick="window.location.href='${pageUrl}'" />
								</pg:last>
							</pg:pager>
						</TD>
					</TR>
				</TBODY>
      </table>
</c:if>
</body>

<script language="JavaScript" >
	//判断，如果选择无线网优，则难易程度为高中低，如果选择非无线网优，则难易程度为中低
	function select(){
		var difficulty = document.getElementById("difficulty");
		var specialtySel=document.getElementById("specialtySel").value;
		if(specialtySel=="1180107"){
			var difficulty = document.getElementById("difficulty");
   			var difficulty3 = document.getElementById("difficulty3");
			difficulty3.style.display="";
			difficulty3.disabled=false;
			difficulty.style.display="none";
			difficulty.disabled=true;
		}else{
		     var difficulty = document.getElementById("difficulty");
   			 var difficulty3 = document.getElementById("difficulty3");
		    difficulty.style.display="";
			difficulty.disabled=false;
			difficulty3.style.display="none";
			difficulty3.disabled=true;
		}
	}
</script>
	
	
	