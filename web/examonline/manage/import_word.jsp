<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

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
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_14,.add_btn_15{height:21px;border:0;font-size:12px;font-family:"宋体";line-height:21px;padding-left:3px;}
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

<html>
<head>
<title>
试题导入
</title>
<META http-equiv="Content-Type" content="text/html; charset=utf-8 ">
<link rel="stylesheet" type="text/css" href="css.css" />
<script language="JavaScript" >
  function gohead(){
    history.go(-1);
  }
  
/**
*  试题导入
*/	
function addattach(flag){
  		 var specialtySel1=document.getElementById("specialtySel").value;
   	 	 var difficultyValue = document.getElementById("difficultyValue").value;
   		 var difficulty = document.getElementById("difficulty");
		difficultyValue=null;
		if(specialtySel1=="1180107"){
        var difficulty3 = document.getElementById("difficulty3");
	    document.getElementById("difficultyValue").value=difficulty3.selectedIndex+1;
	    }else{
	    document.getElementById("difficultyValue").value=difficulty.selectedIndex+1;
	    }
 	
		var specialtySel=document.getElementById("specialtySel").value;
		var wangyou=document.getElementById("wangyou").value;
		var youhua=document.getElementById("youhua").value;
		if(specialtySel=="1180107"&&(wangyou==""||youhua=="")){
			alert("请选择无线网优和无线优化基本原理");
			return false;
		}
		if(specialtySel==""){
			alert("请选择专业");
			return false;
		}
  		if (document.all.ImportWordForm.attachName.value==""){
  			alert('请点击浏览按钮添加附件');
  			return false;
  		}else{
  			chg(document.getElementById("contype"));
       	 document.all.ImportWordForm.submit();
       	 return true;
  	}
}

/**
*  s_value 每题的分数
*/	
function chg(o){
	if(o.value==1)
	document.getElementById("s_value").value=2; //单选2分
	if(o.value==2)
	document.getElementById("s_value").value=4; //多选4分
	if(o.value==3)
	document.getElementById("s_value").value=1; //判断1分
	if(o.value==4 || o.value==5)
	//多选题的分数是根据导入试题模板决定的
	document.getElementById("s_value").value=0; //问答题分数未知，用0分代替
}		
		///判断，如果选择无线网优，则难易程度为高中低，如果选择非无线网优，则难易程度为中低
		function select(){
			var difficulty = document.getElementById("difficulty");
			difficulty.style.display="";
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
</head>
<body bgcolor="#ffffff">
<FORM name="ImportWordForm" METHOD="POST" ACTION="<%=basePath%>examonline/wordupload.do" ENCTYPE="multipart/form-data">
<input type="hidden" name="s_value" id="s_value" >
<input type="hidden" name="issueType" value="2">
<center>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>试题导入</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<br>
<table width="100%" cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
<tr>
  <td width="10%" class="a_table_2">
     专&nbsp;&nbsp;&nbsp;&nbsp;业  </td>
  		<td width="56%">
          <!--  <select name="specialtySel" id="specialtySel"  style="width:120">
         ${specialtySelList}
         </select>
     	</td>-->
     	<eoms:comboBox  name="specialtySel" id="specialtySel" sub="wangyou" defaultValue="1122301" initDicId="11223" alt="allowBlank:false" onchange="select();"/>
        &nbsp;无线网优:      <eoms:comboBox name="wangyou" defaultValue="" id="wangyou" sub="youhua" initDicId="" alt="allowBlank:false" /> 
        &nbsp;无线优化基本原理:<eoms:comboBox name="youhua" defaultValue="" id="youhua" initDicId="" alt="allowBlank:false" onchange="select();"/>
     <td width="15%" class="a_table_2">
        厂&nbsp;&nbsp;&nbsp;&nbsp;家     </td>
	     <td width="23%">
	         <select name="manufacturerSel" id="manufacturerSel"  style="width:120">
	        ${manufacturerSelList}
	         </select>
	     </td>
     </tr>
     <tr>
     
     <td class="a_table_2">
       易&nbsp;难&nbsp;度
     </td>
     <td>
      <!--  <select name="difficulty"  style="width:120" >
		  <option  value="1">初级</option>
		  <option  value="2">中级</option>
	  </select>-->
	  <select style="width: 60px;display: none" name="difficulty3"  class="sel_input">
          <option value="1">初级</option>
          <option value="2">中级</option>
          <option value="3">高级</option>
        </select>
		  <select style="width: 60px;display: " name="difficulty"  class="sel_input">
          <option value="1">初级</option>
          <option value="2">中级</option>
        </select>
        <input type="hidden" name="difficultyValue" id="difficultyValue"/>
  
  	 </td>
  	 
   	   <td class="a_table_2">
           题目类型
       </td>
       <td>
		 <select style="width:120" name="contype" onchange="chg(this)">
			  <option  value="1">单选题</option>
			  <option  value="2">多选题</option>
			  <option  value="3">判断题</option>
			  <option  value="4">简答题</option>
			  <option  value="5">论述题</option>
		  </select>
  		</td>
 	 </tr>
   <tr >
     
   <td class="a_table_2" >
       附&nbsp;&nbsp;&nbsp;&nbsp;件</td>
     <td>
    <input type="file"  name="attachName" >
    <input name="button" type="button" class="add_btn_04"  value=" 试题导入" onclick="return addattach(1);" />
   </td>
   <td colspan="2" class="a_table_2">
   <a href="<%=basePath%>examonline/worddownload.do">试题模板下载</a>
     
  </td>
  </tr>
  
  <tr>
   <td colspan="4" >
  	<strong>
		<font color="red">${msg }</font>
	</strong>
  	</td>
    </tr>
 
  </table>
<table border="0" width="70%" cellspacing="1" >
 <tr align="center">
    <td   width="25%" align="center">
      <B></B>
    </td>
    <td >
      
    </td>
 </tr>
</table>
</center>
</form>
<center>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>历史导入记录</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>
</center>
<iframe id="history" width="100%" height="600" name="history" src="<%=basePath%>examonline/report.do">

</iframe>
  
</body>
</html>
