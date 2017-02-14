<%@ page language="java" import="java.util.*,com.boco.eoms.gz.util.TimeUtil,com.boco.eoms.common.util.StaticMethod" pageEncoding="UTF-8"%>
<%@ page import="com.boco.eoms.examonline.model.ExamResult"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="pg"  uri="http://jsptags.com/tags/navigation/pager" %>

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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
   var checkflag = "false";
	function chooseAll(){	
	    var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        }
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    }
		    checkflag="false";
	    }
	}
	
	function isChecked(flag){
		var notice = '';
		var pass = '';
		if(flag == 'yes'){
			notice='确认考试合格通过，自动生成系统用户？';
			pass = 'true';
		}else{
			notice='确认考试合格不通过？';
			pass = 'false';
		}
		if(confirm(notice)){
			var objs = document.getElementsByName("checkbox11");
		    document.forms[0].action = "${app}/examonline/examPartner.do?method=mark&pass="+pass;
		    var flag = false;
		    for(var i=0; i<objs.length; i++){
		       if(objs[i].checked==true){
		           flag=true;
		           break;
		       }
		    }
		    if(flag==true){
		       document.forms[0].submit();
		       return;
		    }
		    else if(flag==false){
		        alert("请选择！");
		        return;
		    }
		}
	}
  </script>

    <base href="<%=basePath%>">
    
    <title>My JSP 'myexamList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  <form id="form" method="post">
  	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>实时联考</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
  	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="datalist">
	  <tr>
	  	  <th width="3%">&nbsp;<input type='checkbox' onclick='javascript:chooseAll();'></th>
		  <th width="3%">序号</th>
		  <th width="10%">考试人</th>
		  <th width="10%">身份证号</th>
		  <th width="5%">考试分数</th>
		  <th width="15%">考试是否合格</th>
	  </tr>
  
   <% 
   		List list = (List)request.getAttribute("list");
   		
   		for(int j=0;j<list.size();j++){
   			ExamResult exam=(ExamResult)list.get(j);
   	%>		
	   	 <tr height="35">
	   	 	  <td>
	   	 	  <%if(exam.getAssignType()==0){
		     	%><input type="checkbox" name="checkbox11" value="<%=exam.getId()%>" ></td><%
		     	}else{
		     		%>
		     		--
		     		<%
		     	} %>
	   	 	  
		      <td><%=j+1%></td>
		      <td><%=exam.getTesterName()%></td>
		      <td><%=exam.getTester()%></td>
		      <td><%=exam.getScore()%></td>
		      <td>
		     	<%if(exam.getAssignType()==0){
		     	%>
		     	待指定
		     	<% 	
		     	}else if(exam.getAssignType()==1){
		     		%>
			     	合格
			     	<% 	
		     	}else if(exam.getAssignType()==2){
		     		%>
			     	不合格
			     	<%
		     	} %>
		      </td>
    		</tr>
   	<%
   	}
    %>
  
	  <table width="100%" class="table_2" height="20" border="0" cellpadding="0" cellspacing="0">
       <TBODY>
					<TR>
						<TD height="28" align=right vAlign=center noWrap>
							&nbsp;&nbsp;
							<!-- 可以在这里插入分页导航条 -->
							<pg:pager url="${app}/examonline/examResult.do" items="${resultSize }"
								export="currentPageNumber=pageNumber" maxPageItems="10">
								<pg:param name="method" value="findExamResultList"/>
								<pg:param name="issueId" value="${issueId }"/>
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
      <input type="button" class="btn" value="合格" onclick="isChecked('yes')"/>
      <input type="button" class="btn" value="不合格" onclick="isChecked('no')"/>
      </form>
  </body>
</html>
