<%@ page language="java" pageEncoding="UTF-8" %>
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

<html>
<head>

<title>
考试试题发布配置页面
</title>
<script type="text/javascript" src="${app}/scripts/base/prototype.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>

<script language="javascript">
function select2(){
	select();
}
	//通过ajax获取试题数量
	function select(){
	        var difficulty = document.getElementById("difficulty");
			difficulty.style.display="";
	        var specialtySel1=document.getElementById("specialtySel").value;
			if(specialtySel1=="1180107"){
				 var difficulty = document.getElementById("difficulty");
   				 var difficulty3 = document.getElementById("difficulty3");
				 difficulty.style.display="none";
				 difficulty3.style.display="";
			}else{
		    	 var difficulty = document.getElementById("difficulty");
   				 var difficulty3 = document.getElementById("difficulty3");
		    	 difficulty.style.display="";
				 difficulty3.style.display="none";
			}
			if(specialtySel1=="1180107"&&document.getElementById("youhua").value==null){
    			alert("请选择无线优化基本原理");
	        	return false;
			}
		Ext.get(document.body).mask('获取题库试题数中......');
		var form=document.forms[0];
		var specialtySel;
		var manufacturerSel = form.manufacturerSel.value;
		 var difficultyValue = document.getElementById("difficultyValue");
         var difficulty = document.getElementById("difficulty");
		//根据专业是否选择无线网优来判断从哪里来取难度值和专业值
	    if(specialtySel1=="1180107"){
		var wangyou=document.getElementById("wangyou");
		var youhua=document.getElementById("youhua").value;
	    specialtySel=youhua;
	    var difficulty3 = document.getElementById("difficulty3");
	    difficultyValue.value=difficulty3.selectedIndex+1;
	    }else{
	    specialtySel=form.specialtySel.value;
	    difficultyValue.value=difficulty.selectedIndex+1;
	    }
		Ext.lib.Ajax.request("get", '<%=basePath%>examonline/findExamCount.do?specialtySel='+specialtySel+'&manufacturerSel='+manufacturerSel+'&difficulty='+difficultyValue.value, {
			success: function(response,options){		
				Ext.get(document.body).unmask();
			
				if(response.responseText){ //如果不为null
					var array = response.responseText.split(',');
					document.getElementById('specialtyCount').innerHTML =array[0];
					document.getElementById('manufacturerCount').innerHTML =array[1];
					document.getElementById('difficutyCount').innerHTML =array[2];
				}
			},
			failure: function(){
				Ext.get(document.body).unmask();
				Ext.Msg.show({
					title: '错误提示',
					msg: '获取题库试题数失败',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
	}

Ext.onReady(function(){
	select();
});

//分数初始化
var danxt=2;duoxt=4;pdt=1;
document.getElementById('radiod').display='inline';
document.getElementById('multipled').display='inline';
document.getElementById('judged').display='inline';
document.getElementById('countd').display='inline';

//计算分数
function checkfs(){
	var form=document.forms[0];

	if(form.radio.value!=''){
		document.getElementById('radiod').innerHTML='小计：'+(danxt*form.radio.value)+'分'
	}
	else{
		document.getElementById('radiod').innerHTML='小计：';
	}
	if(form.multiple.value!=''){
		document.getElementById('multipled').innerHTML='小计：'+(duoxt*form.multiple.value)+'分';
	}
	else{
		document.getElementById('multipled').innerHTML='小计：';
	}
	if(form.judge.value!=''){
		document.getElementById('judged').innerHTML='小计：'+(pdt*form.judge.value)+'分'
	}
	else{
		document.getElementById('judged').innerHTML='小计：';
	}
	var zf=((danxt*form.radio.value)+(duoxt*form.multiple.value)+(pdt*form.judge.value));
	document.getElementById('countd').innerHTML='非主观题合计：'+zf+'分'
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
	checkfs()
}

</SCRIPT>

</head>
<body onLoad="opener.location.reload()">

<SCRIPT language=javascript>
<!--
//以下四行用于日历显示
var outObject;
var old_dd = null;
writeCalender();
bltSetDay(bltTheYear,bltTheMonth);
//-->
  function doselect(){
    var form = document.forms[0];
    var radio=isNaN(parseInt(form.radio.value))?0:parseInt(form.radio.value);
  
    var multiple=isNaN(parseInt(form.multiple.value))?0:parseInt(form.multiple.value);
    var judge=isNaN(parseInt(form.judge.value))?0:parseInt(form.judge.value);
    var contv=radio*2+multiple*4+judge;
    

   // if(contv<100){
    //	alert("已经选择"+contv+"分题目，还差"+(100-contv));
    //	return false;
    //}
    //选足100分不提示 2011-01-19 liuchang
    //else if(contv==100) alert("已经选足100分");
    //else if(contv>100){
	  //  alert("所选择题分数已经超出"+(contv-100)+"分，请减掉超出部分");
	 //   return false;
    //}
    var SelString = form.specialtySel.value
              + ">" + form.manufacturerSel.value
              + ">" + form.difficulty.value  
              + ">" +   form.radio.value
              + ":" +  form.multiple.value 
              + ":" + form.judge.value;
    form.typeSel.value =SelString;    
    return true;         
  }
  
  //生成试卷
  function confirm(){
    var form = document.forms[0];
    var boolean = doselect();
     var specialtySel1=document.getElementById("specialtySel").value;
    var difficultyValue = document.getElementById("difficultyValue");
    var difficulty = document.getElementById("difficulty");
    if(form.radio.value!=''){
		if(parseInt(document.getElementById("specialtyCount").innerHTML) < form.radio.value){
			alert('单选题题库试题数不足'+ form.radio.value + '道');
			return false;
		}
	}
	if(form.multiple.value!=''){
		if(parseInt(document.getElementById("manufacturerCount").innerHTML) < form.multiple.value){
			alert('多选题题库试题数不足' + form.multiple.value + '道');
			return false;
		}
	}
	if(form.judge.value!=''){
		if(parseInt(document.getElementById("difficutyCount").innerHTML) < form.judge.value){
			alert('判断题题库试题数不足' +form.judge.value+ '道');
			return false;
		}
	}
    
    if(!boolean){
    	return false;
    }
    if (form.typeSel.value == ""){
      alert("选择类型为空");
      return false;
    }
   
    if(specialtySel1=="1180107"&&document.getElementById("youhua").value==null){
    		alert("请选择无线优化基本原理");
	        return false;
	}
   
   
    if(specialtySel1=="1180107"){
		var wangyou=document.getElementById("wangyou");
		var youhua=document.getElementById("youhua").value;
		document.getElementById("typeSelValue").value=youhua;
	    specialtySel=youhua;
        var difficulty3 = document.getElementById("difficulty3");
	    difficultyValue.value=difficulty3.selectedIndex;
	    }else{
	    document.getElementById("typeSelValue").value=form.specialtySel.value;
	    difficultyValue.value=difficulty.selectedIndex;
	    }
    var specialtySel = document.getElementById("specialtySel");
    var specialtySelValue = document.getElementById("specialtySelValue");
    specialtySelValue.value = specialtySel.options[specialtySel.selectedIndex].text;
    
    var manufacturerSel = document.getElementById("manufacturerSel");
    var manufacturerSelValue = document.getElementById("manufacturerSelValue");
    manufacturerSelValue.value = manufacturerSel.options[manufacturerSel.selectedIndex].text;
    document.getElementById("manufacturerv").value=document.getElementById("manufacturerSel").selectedIndex.value;
    form.submit();
  }

  
</SCRIPT>

<form action="<%=basePath %>examonline/mockExam.do" method="post">
<input type="hidden" name="typeSel"id="typeSel" />
<input type="hidden" name="typeSelValue"id="typeSelValue" />
<input type="hidden" name="difficultyValue"id="difficultyValue" />
<input type="hidden" name="manufacturerv"id="manufacturerv" />
<input type="hidden" name="begin" value="true"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	<div class="table_1">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg">
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>模拟考试</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
      </table>

	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  
	  <tr>
  		<td class="a_table_2"  cellpadding="0" cellspacing="0" width="98%" ><img src="<%=basePath%>examonline/manage/images/icon_4.gif"  /> 选择厂家
            <select style="width: 60px" name="manufacturerSel" id="manufacturerSel"  class="sel_input" onChange="select();">
			${manufacturerSelList}
            </select>
             <input type="hidden" name="manufacturerSelValue" id="manufacturerSelValue"/>&nbsp;
            <img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />&nbsp;难易程度&nbsp; 
		<select style="width: 60px;display: " name="difficulty3" id="difficulty3" class="sel_input" onChange="select();">
          <option value="1">初级</option>
          <option value="2">中级</option>
          <option value="3">高级</option>
        </select>
		  <select style="width: 60px;display: " name="difficulty" id="difficulty" class="sel_input" onChange="select();">
          <option value="1">初级</option>
          <option value="2">中级</option>
        </select>
        <input type="hidden" name="difficultyValue" id="difficultyValue"/>
        
        
        &nbsp;<img src="<%=basePath%>examonline/manage/images/icon_3.gif"   />
        &nbsp;选择专业:       <eoms:comboBox  name="specialtySel" id="specialtySel" sub="wangyou" defaultValue="1122301" initDicId="11223" alt="allowBlank:false" onchange="select();"/>
        &nbsp;无线网优:      <eoms:comboBox name="wangyou" defaultValue="" id="wangyou" sub="youhua" initDicId="" alt="allowBlank:false" /> 
        &nbsp;无线优化基本原理:<eoms:comboBox name="youhua" defaultValue="" id="youhua" initDicId="" alt="allowBlank:false" onchange="select2();"/>
        
        <input type="hidden" name="specialtySelValue" id="specialtySelValue"/>
	</td>
	</tr>
	  

    </table>
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td width="30%" class="a_table_1"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 
    单选题<input type="text" name="radio" class="sel_input"  id="radio" onKeyUp="checkzf(this)"/>每题2分 
    </td>
    <td width="20%" class="a_table_1">题库试题数：<span id="specialtyCount"></span></td>
    <td width="20%" class="a_table_1"><div id="radiod">小计：</div></td><td width="20%" class="a_table_1"></td>
  </tr>
  
  <tr >
    <td width="30%" class="a_table_2"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 
    多选题<input type="text" name="multiple" class="sel_input"   id="multiple" onKeyUp="checkzf(this)"/>每题4分 
    </td>
     <td width="20%" class="a_table_2">题库试题数：<span id="manufacturerCount"></span></td>
    <td width="20%" class="a_table_2"><div id="multipled">小计：</div></td><td width="30%" class="a_table_2"></td>
  </tr>
  <tr >
    
    <td width="30%" class="a_table_1"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />
     判断题<input type="text" name="judge" class="sel_input"  id="judge" onKeyUp="checkzf(this)"/>每题1分 
    </td>
 	<td width="20%" class="a_table_1">题库试题数：<span id="difficutyCount"></span></td>
    <td width="20%" class="a_table_1"><div id="judged">小计：</div></td><td width="20%" class="a_table_1"></td>
  </tr>
  
  <tr >
  	<td width="20%"  class="a_table_2">&nbsp;</td>
    <td width="20%"  class="a_table_2">&nbsp;</td>
    <td width="20%"  class="a_table_2">&nbsp;</td>
    <td width="20%"  class="a_table_2"><div id="countd">非主观题合计：</div></td>
  </tr>
  
</table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_3">
        <tr>
          <td align="center">
          	<input name="button" type="button" class="add_btn_05"  value="模拟考试" onclick="confirm()";/> 
          </td>
        </tr>
      </table>
	  <div class="table_4"></div>
	</div>
	
	</td>
  </tr>
  
  <tr>
    <td height="6" bgcolor="#cacaca"></td>
  </tr>
</table>

</form>

</div>

