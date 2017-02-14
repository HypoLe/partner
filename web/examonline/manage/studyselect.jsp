 
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.*,java.util.*,java.io.*"%>

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
类型选择
</title>
<script language="javascript" src="<%=request.getContextPath()%>/css/checkform.js"></script>
</head>
<script language="javascript">
  function select(){
    var form = document.forms[0];
    var valueString = "";
    valueString  = form.specialtySel[form.specialtySel.selectedIndex].text;                                  
    valueString = valueString + ">" + form.manufacturerSel[form.manufacturerSel.selectedIndex].text;
    valueString = valueString + ">" + form.difficulty[form.difficulty.selectedIndex].text;
    valueString = valueString + ">" + form.contype[form.contype.selectedIndex].text;
    form.Value.value = valueString;
  }

  function doselect(){
    var form = document.forms[0];
    var flag = false;
    if ( form.Value.value != null && form.Value.value != "" )
    {
      for(var i = 0 ; i < form.studySel.options.length ; i++)
      {
        if (form.studySel.options[i].text == form.Value.value)
          flag = true;
      }
     
      if( !flag ){
        var text = new Option(form.Value.value);
        var SelString = form.specialtySel.value
                  + ">" + form.manufacturerSel.value
                  + ">" + form.difficulty.value
                  + ">" + form.contype.value;
        text.value = SelString;
        //alert(SelString);
        form.studySel.options[form.studySel.options.length]=text;
      }
      else
        alert("所选项重复");
    }
  }

  function deleteSel(){
    var form = document.forms[0];
    form.studySel.options[form.studySel.selectedIndex] = null;
  }

  function confirm(type){
    var form = document.forms[0];
    if (form.studySel.options.length == 0){
      alert("选择类型为空");
      return false;
    }
    //返回类型typeSel为 8>2>3>1;4>3>2>3 的形式
    for(var i = 0; i < form.studySel.options.length; i++){
      if (i == 0){
        form.typeSel.value = form.studySel[i].value;
        form.typeSelName.value = form.studySel[i].text;
      }
      else{
        form.typeSel.value = form.typeSel.value + ";" + form.studySel[i].value;
        form.typeSelName.value = form.typeSelName.value + ";" + form.studySel[i].text;
      }
    }
    //alert(form.typeSelName.value);

    if (type == 0)  //保存为默认
      form.action = "saveDefault.do";
    else if (type == 1) //出题
      form.action = "study.do";
      form.submit();
    //return false;
  }
  
  //模拟考试
  function mockExam(){
  	 var form = document.forms[0];
  	 form.action = "mockExam.do";
  	 form.submit();
  }

</script>
 
<body >
 <input name="button" type="button" class="add_btn_06"  value=" 进入模拟考试" onclick="return mockExam()" />
 <br/>
<form action="${pageContext.request.contextPath}/examonline/study.do">
<input type="hidden" name="typeSel"/> 
<input type="hidden" name="typeSelName"/> 

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>随机出题</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<table width="100%"    cellspacing="1" cellpadding="1" align=center border="0">
   <tr  class="a_table_2">
    <td width="100%" align="center" >
      <table width="80%"    cellspacing="1" cellpadding="1"  align=center border="0">
<tr  class="a_table_1">
   
  <td width="40%" rowspan="3" align="center" valign="top">
  		
	    <p><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 选择专业&nbsp;&nbsp;&nbsp;
	    
	    <select name="specialtySel" id="specialtySel"  size="5" style="width:150" onChange="select();">
	         ${specialtySelList}
	    </select>
   	 	</p><p><br/>
     <img src="<%=basePath%>examonline/manage/images/icon_4.gif" width="15" height="14" /> 选择厂家&nbsp;&nbsp;&nbsp;
     <select name="manufacturerSel" size="5" id="manufacturerSel"  style="width:150" onclick="select();"> 
		${manufacturerSelList}
	 </select>
     </p><p><br/>
      <img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 难易程度&nbsp;&nbsp;&nbsp;
     <select name="difficulty"   style="width:150" size="2"  onclick="select();">
		<option value="1">初级</option>
		<option value="2">中级</option>
	 </select>
     </p><p><br/>
      <img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 题目类型&nbsp;&nbsp;&nbsp;
     <select name="contype"   style="width:150" size="3"  onclick="select();">
		<option value="1">单选题</option>
		<option value="2">多选题</option>
		<option value="3">判断题</option>
	 </select>  </p>
	</td>
  <td width="69%">
    <p><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 所选类别:</p>
    <html:text property="Value" readonly="true" style="width:400" value=""/>  </td>
</tr>
<tr class="a_table_1" >
  <td >
     <p><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 已选类别:</p>
     <select name="studySel"  size="16"  style="width:400"  ondblclick="deleteSel()">
</select>  </td>
</tr>
<tr class="label" >
   <td  >
      <input name="button" type="button" class="add_btn_02"  value=" 选择" onclick="doselect()" />
      <input name="button" type="button" class="add_btn_02"  value=" 删除" onclick="deleteSel()" />
</tr>
<tr class="label"  align="right">
  <td colspan="3">
    <div align="right">
      <!--  
      <html:submit value="保存为默认" onclick="return confirm(0)"/>-->
      <input name="button" type="button" class="add_btn_06"  value=" 随机出题开始" onclick="return confirm(1)" />
      
      <input name="button" type="button" class="add_btn_06"  value=" 练习成绩查询" onclick="window.open('<%=basePath%>examonline/queryDoSelf.do')" />
    </div>
  </td></tr>
</table>
    </td>
  </tr>
</table>



</form>
</body>
</html>
