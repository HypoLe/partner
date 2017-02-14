<%@ page language="java" pageEncoding="UTF-8"  import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
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

<script language="javascript">
	function submitForm(){
		 var form = document.forms[0];
		 form.submit();
	}
	function checkMark(answer,mark,id){
		if(parseInt(answer) > parseInt(mark)){
			alert('打分分值不能超过试题本身的分值，请重新打分');
			document.getElementById(id).value = 0;
		}
	}
	function checkzf(obj){
			var str='0123456789';
			var t;
			for(var i=0;i<obj.value.length;i++){
				if(str.indexOf(obj.value.substring(i,i+1))<0){
					obj.value=0;
					return;
				}
			}
		}
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>问答题打分列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>styles/default/theme.css" />
  </head>
  
  <body>
  <form action="<%=basePath %>examonline/findQaMarkPerson.do">
  <input type="hidden" name="markType" value="person">
   <input type="hidden" name="issueId" value="${issueId}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>问答题阅卷</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
  	</table>
  	
  	<div id="divTable" style="position: relative; align: center; top: 5px;width:  100%; height:  85%; z-index: 1; overflow: auto; overflow-x: hidden"> 
	<table cellpadding="1" cellspacing="1" width="95%" border="0" class="table_show">
	 
  	   <% 
   		List list = (List)request.getAttribute("list");
   		
   		for(int j=0;j<list.size();j++){
   			Map m=(HashMap)list.get(j);
   	%>		
   		<tr align="center" class="tr_show">
   			<td width="88%" colspan="2" align="left" bgcolor="#d7e1ed">题目<%=j+1%>：
   			<%=m.get("title")%>(<%=m.get("point")%>分)
	      	</td>
    	</tr>
    	<tr>
	    	<td width="2%"> 标准答案：
	      	</td>
	      	<td width="80%" align="left"> 
		    	<textarea rows="3" style="width: 80%" readonly="readonly"><%=m.get("answer")%></textarea>
		    </td>
    	</tr>
    	<tr>
    		<td width="2%"> 考生答案：
	      	</td>
	    	<td width="80%" align="left"> 
	    	<textarea rows="3" style="width: 80%" readonly="readonly"><%=m.get("answerPerson")%></textarea>
	    	</td>
    	</tr>
    	<tr>
    		<td width="2%">打分：
	      	</td>
	      	<td width="98%" align="left"> 
	    		<input type="text" name="<%=m.get("id")%>" id="<%=m.get("id")%>" value="0" onkeyup="checkzf(this)"
	    		onchange="checkMark(this.value,'<%=m.get("point")%>','<%=m.get("id")%>')"/><font color="red">*</font>
	    	</td>
    	</tr>
   	<%
   	}
    %>
	 </table>
	 </div>
	 <br>
	 <input name="button" type="button" class="add_btn_05"  value="打分" onclick="submitForm()"/>
	 </form>
  </body>
</html>
