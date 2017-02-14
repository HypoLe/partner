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
	.sel_input2{height:18px;border:1px solid #c5cdda; width:10%; line-height:18px;font-size:12px;}
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
<script type="text/javascript">
	 Ext.onReady(function(){
	 	var tabConfig = {
			items : [{
				id : 'proGrid',
				text : '省级试卷汇总'
			}, {
				text : '网络部试卷汇总',
				url : '<%=basePath%>examonline/examConfigList.do?examType=net',
				isIframe : true
			}, {
				text : '地市级试卷汇总',
				url : '<%=basePath%>examonline/examConfigList.do?examType=city',
				isIframe : true
			}]
		};
		var tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
	 });
</script>

<script language="javascript">
var num=0;
var selall="";
function doUserSelect(){
		var title=document.getElementById("title").value;
		var issuetitle=document.getElementById("issuetitle").value;
		issuetitle=title;
		var form = document.forms[0];
   		 var boolean = doselect();
		var specialtySel=document.getElementById("specialtySel");
		var wangyou=document.getElementById("wangyou").value;
		var youhua=document.getElementById("youhua").value;

	if(specialtySel.value=="1122307"&&(wangyou==""||youhua=="")){
			alert("请选择无线网优和无线优化基本原理");
			return false;
	}
    
    //校验是否设置分数
    if(form.radioScore.value ==''){
		alert('由于在后面的操作中可以增加新的单选题，请在此设置单选题分数');
    	
		return false;
	}
	if(form.multipleScore.value==''){
		alert('由于在后面的操作中可以增加新的多选题，请在此设置多选题分数');
		return false;
	}
	if(form.judgeScore.value==''){
		alert('由于在后面的操作中可以增加新的判断题，请在此设置判断题分数');
		return false;
	}
	if(form.qaScore.value==''){
		alert('由于在后面的操作中可以增加新的简答题，请在此设置简答题分数');
		return false;
	}
	if(form.essayScore.value==''){
		alert('由于在后面的操作中可以增加新的论述题，请在此设置论述题分数');
		return false;
	}
    //校验试题数目
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
	if(form.qa.value!=''){
		if(parseInt(document.getElementById("qaQuestionCount").innerHTML) < form.qa.value){
			alert('简答题题库试题数不足' +form.qa.value+ '道');
			return false;
		}
	}
	if(form.essay.value!=''){
		if(parseInt(document.getElementById("essayCount").innerHTML) < form.essay.value){
			alert('论述题题库试题数不足' +form.essay.value+ '道');
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
    else if(form.title.value==""){
      alert("试卷标题不能为空");
      return false;
    }
    else if(specialtySel.value==""){
	  alert("请选择专业");
      return false;
    }
    
    var specialtySel = document.getElementById("specialtySel");
    var specialtySelValue = document.getElementById("specialtySelValue");
   // specialtySelValue.value = specialtySel.options[specialtySel.selectedIndex].text;
    var radio=document.getElementById("radio").value;
    var radio1;
    document.getElementById("radio").value==""?radio1="0":radio1=radio;
    
    var multiple=document.getElementById("multiple").value;
    var multiple1;
    document.getElementById("multiple").value==""?multiple1="0":multiple1=multiple;
    
    var judge=document.getElementById("judge").value;
    var judge1;
    document.getElementById("judge").value==""?judge1="0":judge1=judge;
    
    var qa=document.getElementById("qa").value;
    var qa1;
    document.getElementById("qa").value==""?qa1="0":qa1=qa;
    
    var essay=document.getElementById("essay").value;
    var essay1;
    document.getElementById("essay").value==""?essay1="0":essay1=essay;
    var radioScore=document.getElementById("radioScore").value;
    var multipleScore=document.getElementById("multipleScore").value;
    var judgeScore=document.getElementById("judgeScore").value;
    var qaScore=document.getElementById("qaScore").value;
    var essayScore=document.getElementById("essayScore").value;
    var manufacturerSel = document.getElementById("manufacturerSel");
    var manufacturerSelValue = document.getElementById("manufacturerSelValue");
    manufacturerSelValue.value = manufacturerSel.options[manufacturerSel.selectedIndex].text;
 	var specialtySel=document.getElementById("specialtySel");
	var difficulty = document.getElementById("difficulty");
   	var difficulty3 = document.getElementById("difficulty3");
  	var difficultyValue = document.getElementById("difficultyValue");
	specialtySel.value=="1122307" ? difficultyValue.value = difficulty3.options[difficulty3.selectedIndex].text:difficultyValue.value = difficulty.options[difficulty.selectedIndex].text;
	specialtySel.value=="1122307" ? difficultyV= document.getElementById("difficulty3").value:difficultyV = document.getElementById("difficulty").value;
	specialtySel.value=="1122307" ? specialtySelValue=youhua:specialtySelValue = specialtySel.value;
	
	//sel1为要在页面上显示的值	
	var sel1=manufacturerSelValue.value+"-"+difficultyValue.value+"-"+specialtySel.options[specialtySel.selectedIndex].text+";单选题："+radio1+",多选题："+multiple1+",判断题："+judge1+",简答题:"+qa1+",论述题:"+essay1;
	var sel2=new Array();
	
	
	
	  var flag = false;
      for(var i = 0 ; i < form.company_exam.options.length ; i++){
        if (form.company_exam.options[i].text.indexOf(sel1) >= 0 )
          flag = true;
      }
      if( !flag ){
      	num=num+1;
        var text = new Option(sel1);
        text.value = sel1;
        form.company_exam.options[form.company_exam.options.length]=text;
      }
      else{
        alert("所选项重复");
        return false;
        }
       //在session中存入数据库题中的总题数 
		var storeRadioCount = parseInt(document.getElementById("specialtyCount").innerHTML);
		var storeMultipleCount = parseInt(document.getElementById("manufacturerCount").innerHTML);
		var storeJudgeCount = parseInt(document.getElementById("difficutyCount").innerHTML)
		var storeQaCount = parseInt(document.getElementById("qaQuestionCount").innerHTML)
		var storeEssayCount = parseInt(document.getElementById("essayCount").innerHTML)
   Ext.get(document.body).mask('保存信息中......');

		
		Ext.Ajax.request({
			method: 'post',
			params:{
				specialty : specialtySelValue,
				manufacturer:manufacturerSel.value,
				difficulty:difficultyV,
				radioCount:radio1,
				multipleCount:multiple1,
				judgeCount:judge1,
				qaCount:qa1,
				essayCount:essay1,
				radioScore:radioScore,
				multipleScore:multipleScore,
				judgeScore:judgeScore,
				qaScore:qaScore,
				essayScore:essayScore,
				storeRadioCount:storeRadioCount,
				storeMultipleCount:storeMultipleCount,
				storeJudgeCount:storeJudgeCount,
				storeQaCount:storeQaCount,
				storeEssayCount:storeEssayCount
			},
			url: '<%=basePath%>examonline/addTypesel.do?issuetitle='+issuetitle,
			success: function(response,options){		
				Ext.get(document.body).unmask();
				if(response.responseText){ //如果不为null

				}
			},
			failure: function(){
				Ext.get(document.body).unmask();
				Ext.Msg.show({
					title: '错误提示',
					msg: '保存信息失败',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
        
}
  function deleteUserSel(issuetitle){
    var form = document.forms[0];
    var sel=form.company_exam.selectedIndex;
    
	Ext.get(document.body).mask('保存信息中......');
		Ext.Ajax.request({
			method: 'post',
			url: '<%=basePath%>examonline/delTypesel.do?sel='+sel,
			success: function(response,options){		
				Ext.get(document.body).unmask();
				if(response.responseText){ //如果不为null

				}
			},
			failure: function(){
				Ext.get(document.body).unmask();
				Ext.Msg.show({
					title: '错误提示',
					msg: '信息更新失败',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
    form.company_exam.options[form.company_exam.selectedIndex] = null;
     num=num-1;
  }

	//通过ajax获取试题数量
	function select(){
	var difficultyValue;
			var difficulty = document.getElementById("difficulty");
			difficulty.style.display="";
			var specialtySel=document.getElementById("specialtySel").value;
			if(specialtySel=="1122307"){
				 var difficulty = document.getElementById("difficulty");
   				 var difficulty3 = document.getElementById("difficulty3");
				difficulty.style.display="none";
				difficulty3.style.display="";
				difficultyValue=difficulty3.options[difficulty3.selectedIndex].value;
				
				
				
			}else{
		    	 var difficulty = document.getElementById("difficulty");
   				 var difficulty3 = document.getElementById("difficulty3");
		    	 difficulty.style.display="";
				 difficulty3.style.display="none";
				 difficultyValue=difficulty.options[difficulty.selectedIndex].value;
			}
		var form=document.forms[0];
		var specialtySel=document.getElementById("specialtySel").value;
		if(specialtySel=="1122307"&&document.getElementById("wangyou").value!=""&&document.getElementById("youhua").value!=""){
				specialtySel =document.getElementById("youhua").value;
				
		}
		else if(specialtySel!="1122307"){
		specialtySel = form.specialtySel.value;
		
		}
		var manufacturerSel = form.manufacturerSel.value;
	    //var difficulty = form.difficulty.value;
	    
	   
	    if(specialtySel==""){
	    	return;
	    }
	    
		Ext.get(document.body).mask('获取题库试题数中......');
		Ext.lib.Ajax.request("get", '<%=basePath%>examonline/findExamCount.do?specialtySel='+specialtySel+'&manufacturerSel='+manufacturerSel+'&difficulty='+difficultyValue, {
			success: function(response,options){		
				Ext.get(document.body).unmask();
			
				if(response.responseText){ //如果不为null
					var array = response.responseText.split(',');
					document.getElementById('specialtyCount').innerHTML =array[0];
					document.getElementById('manufacturerCount').innerHTML =array[1];
					document.getElementById('difficutyCount').innerHTML =array[2];
					document.getElementById('qaQuestionCount').innerHTML =array[3];
					document.getElementById('essayCount').innerHTML =array[4];
//判断数据库中是否有题，如果没有题，则分值为０
     　　 　 var radioScore=document.getElementById("radioScore");
 	  　	　　var multipleScore=document.getElementById("multipleScore");
      　　　 var judgeScore=document.getElementById("judgeScore");
      　　　 var qaScore=document.getElementById("qaScore");
     　　 　 var essayScore=document.getElementById("essayScore");
     		array[0]=="0"?radioScore.value="0":radioScore.value="";
     		array[1]=="0"?multipleScore.value="0":multipleScore.value="";
     		array[2]=="0"?judgeScore.value="0":judgeScore.value="";
     		array[3]=="0"?qaScore.value="0":qaScore.value="";
     		array[4]=="0"?essayScore.value="0":essayScore.value="";

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
	
	function select2(){
		select();
	}

Ext.onReady(function(){
	select();
});

//分数初始化
var radioScore=0;   //论述题每题分数
var judgeScore=0;   //判断题每题分数
var multipleScore=0;//多选题每题分数
var qaScore=0;      //简答题每题分数
var essayScore=0;   //论述题每题分数

var radioScoreAll=0;   //论述题总分数
var judgeScoreAll=0;   //判断题总分数
var multipleScoreAll=0;//多选题总分数
var qaScoreAll=0;      //简答题总分数
var essayScoreAll=0;   //论述题总分数
//计算分数
function checkfs(){
	var form=document.forms[0];
	if(form.radio.value!='' && form.radioScore.value!=''){
		radioScore = parseInt(form.radioScore.value);
		radioScoreAll = radioScore*form.radio.value;
		document.getElementById('radiod').innerHTML='小计：'+radioScoreAll+'分';
	}
	else{
		radioScoreAll = 0;
		document.getElementById('radiod').innerHTML='小计：';
	}
	if(form.multiple.value!='' && form.multipleScore.value!=''){
		multipleScore = parseInt(form.multipleScore.value);
		multipleScoreAll = multipleScore*form.multiple.value;
		document.getElementById('multipled').innerHTML='小计：'+multipleScoreAll+'分';
	}
	else{
		multipleScoreAll = 0;
		document.getElementById('multipled').innerHTML='小计：';
	}
	if(form.judge.value!='' && form.judgeScore.value!=''){
		judgeScore = parseInt(form.judgeScore.value);
		judgeScoreAll = judgeScore*form.judge.value;
		document.getElementById('judged').innerHTML='小计：'+judgeScoreAll+'分';
	}
	else{
		judgeScoreAll = 0;
		document.getElementById('judged').innerHTML='小计：';
	}
	if(form.qa.value!='' && form.qaScore.value!=''){
		qaScore = parseInt(form.qaScore.value);
		qaScoreAll = qaScore*form.qa.value;
		document.getElementById('qaed').innerHTML='小计：'+qaScoreAll+'分';
	}
	else{
	    qaScoreAll = 0;
		document.getElementById('qaed').innerHTML='小计：';
	}
	if(form.essay.value!='' && form.essayScore.value!=''){
		essayScore = parseInt(form.essayScore.value);
		essayScoreAll = essayScore*form.essay.value;
		document.getElementById('essayed').innerHTML='小计：'+essayScoreAll+'分';
	}
	else{
	    essayScoreAll = 0;
		document.getElementById('essayed').innerHTML='小计：';
	}
	
	
	var zf= radioScoreAll +judgeScoreAll+multipleScoreAll+qaScoreAll+ essayScoreAll;
	document.getElementById('countd').innerHTML='合计：'+zf+'分';
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
	checkfs();
}

//校验正整数
function validatezs(obj){
	obj.value=obj.value.replace(/\D/g,'');
}

</SCRIPT>

</head>
<body>

<SCRIPT language=javascript>

	
	
  function doselect(){
    var form = document.forms[0];
    
    if(document.getElementById("youhua").value!=""){
    var SelString2 = form.youhua.value
              + ">" + form.manufacturerSel.value
              + ">" + form.difficulty3.value  
              + ">" + form.radio.value
              + ":" + form.multiple.value 
              + ":" + form.judge.value
              + ":" + form.qa.value
              + ":" + form.essay.value;
              form.typeSel.value = SelString2;
              return true; 
    }else{  
    var SelString = form.specialtySel.value
              + ">" + form.manufacturerSel.value
              + ">" + form.difficulty.value  
              + ">" + form.radio.value
              + ":" + form.multiple.value 
              + ":" + form.judge.value
              + ":" + form.qa.value
              + ":" + form.essay.value;
    		form.typeSel.value = SelString;
  			return true; 
   } 
  }
  
  //生成试卷
  function confirm(){
    var form = document.forms[0];
   // var boolean = doselect();
		var specialtySel=document.getElementById("specialtySel").value;
		var wangyou=document.getElementById("wangyou").value;
		var youhua=document.getElementById("youhua").value;
		if(specialtySel=="1122307"&&(wangyou==""||youhua=="")){
			alert("请选择无线网优和无线优化基本原理");
			return false;
		}
    
    //校验是否设置分数
    if(form.radioScore.value ==''){
		alert('由于在后面的操作中可以增加新的单选题，请在此设置单选题分数');
    	
		return false;
	}
	if(form.multipleScore.value==''){
		alert('由于在后面的操作中可以增加新的多选题，请在此设置多选题分数');
		return false;
	}
	if(form.judgeScore.value==''){
		alert('由于在后面的操作中可以增加新的判断题，请在此设置判断题分数');
		return false;
	}
	if(form.qaScore.value==''){
		alert('由于在后面的操作中可以增加新的简答题，请在此设置简答题分数');
		return false;
	}
	if(form.essayScore.value==''){
		alert('由于在后面的操作中可以增加新的论述题，请在此设置论述题分数');
		return false;
	}
    //校验试题数目
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
	if(form.qa.value!=''){
		if(parseInt(document.getElementById("qaQuestionCount").innerHTML) < form.qa.value){
			alert('简答题题库试题数不足' +form.qa.value+ '道');
			return false;
		}
	}
	if(form.essay.value!=''){
		if(parseInt(document.getElementById("essayCount").innerHTML) < form.essay.value){
			alert('论述题题库试题数不足' +form.essay.value+ '道');
			return false;
		}
	}
    
    //if(!boolean){
    //	return false;
    //}
    if (form.typeSel.value == ""){
      alert("选择类型为空");
      return false;
    }else if(form.title.value==""){
      alert("试卷标题不能为空");
      return false;
    }
    
    var specialtySel = document.getElementById("specialtySel");
    var specialtySelValue = document.getElementById("specialtySelValue");
    specialtySelValue.value = specialtySel.options[specialtySel.selectedIndex].text;
    
    var manufacturerSel = document.getElementById("manufacturerSel");
    var manufacturerSelValue = document.getElementById("manufacturerSelValue");
    manufacturerSelValue.value = manufacturerSel.options[manufacturerSel.selectedIndex].text;
		var specialtySel=document.getElementById("specialtySel").value;
		var difficulty = document.getElementById("difficulty");
   		var difficulty3 = document.getElementById("difficulty3");
  		var difficultyValue = document.getElementById("difficultyValue");
		specialtySel=="1122307" ? difficultyValue.value = difficulty3.options[difficulty3.selectedIndex].text:difficultyValue.value = difficulty.options[difficulty.selectedIndex].text;
    form.submit();
  }
//////点击无线优化基本原理获取试题数////////////////
function getExamNum(){
	var youhua=document.getElementById('youhua').value;
			Ext.lib.Ajax.request("get", '<%=basePath%>examonline/findExamCount.do?specialtySel='+specialtySel+'&manufacturerSel='+manufacturerSel+'&difficulty='+difficulty, {
			success: function(response,options){		
				Ext.get(document.body).unmask();
			
				if(response.responseText){ //如果不为null
					var array = response.responseText.split(',');
					document.getElementById('specialtyCount').innerHTML =array[0];
					document.getElementById('manufacturerCount').innerHTML =array[1];
					document.getElementById('difficutyCount').innerHTML =array[2];
					document.getElementById('qaQuestionCount').innerHTML =array[3];
					document.getElementById('essayCount').innerHTML =array[4];
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

</SCRIPT>


<form action="<%=basePath %>examonline/sendSelectDeptPage.do" method="post">
<input type="hidden" name="typeSel"id="typeSel" />
<input type="hidden" name="typeSelName"id="typeSelName" />
<input type="hidden" name="issuetitle"id="issuetitle" />

<input type="hidden" name="storeRadioCount"id="storeRadioCount" />
<input type="hidden" name="storeMultipleCount"id="storeMultipleCount" />
<input type="hidden" name="storeJudgeCount"id="storeJudgeCount" />
<input type="hidden" name="storeQaCount"id="storeQaCount" />
<input type="hidden" name="storeEssayCount"id="storeEssayCount" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
	<div class="table_1">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg">
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>试卷生成</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
      </table>
	  <h4 class="inter"><img src="<%=basePath%>examonline/manage/images/icon_1.gif" width="11" height="11" /> 填写完毕后请点击生成试卷</h4>
	  <div class="table_2"><img src="<%=basePath%>examonline/manage/images/icon_2.gif" />
	  <label for="name"> 试卷标题：</label>
	  <input type="text" name="title" id="title" class="txt_input"></div>
	   <table width="100%" border="0">  
	 <tr>
  		<td class="a_table_2"  cellpadding="0" cellspacing="0" width="98%" ><img src="<%=basePath%>examonline/manage/images/icon_4.gif"  /> 选择厂家
            <select style="width: 60px" name="manufacturerSel" id="manufacturerSel"  class="sel_input" onChange="select();">
			${manufacturerSelList}
            </select>
             <input type="hidden" name="manufacturerSelValue" id="manufacturerSelValue"/>&nbsp;
        
        
        &nbsp;选择专业: <eoms:comboBox  name="specialtySel" id="specialtySel" sub="wangyou" defaultValue="1122301" initDicId="11223" alt="allowBlank:false" onchange="select();"/>
            <img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />&nbsp;难易程度&nbsp; 
        &nbsp;<img src="<%=basePath%>examonline/manage/images/icon_3.gif"   />
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
        &nbsp;无线网优:      <eoms:comboBox name="wangyou" defaultValue="" id="wangyou" sub="youhua" initDicId="" alt="allowBlank:false" /> 
        &nbsp;无线优化基本原理:<eoms:comboBox name="youhua" defaultValue="" id="youhua" initDicId="" alt="allowBlank:false" onchange="select2();"/>
        
        <input type="hidden" name="specialtySelValue" id="specialtySelValue"/>
	</td>
	</tr>
</table>
	  
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  
  <tr >
    <td width="40%" class="a_table_1"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 
    单选题<input type="text" name="radio" class="sel_input"  id="radio" 
    	onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()"/>
	每题<input type="text" class="sel_input2" name="radioScore" id="radioScore"
    	onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()">分
    </td>
    <td width="15%" class="a_table_1">题库试题数：<span id="specialtyCount"></span></td>
    <td width="15%" class="a_table_1"><div id="radiod">小计：</div></td>
  </tr>
  
  <tr >
    <td width="40%" class="a_table_2"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 
    多选题<input type="text" name="multiple" class="sel_input"   id="multiple" 
		onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()"/>
	每题<input type="text" class="sel_input2" name="multipleScore" id="multipleScore"
    	onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()">分
    </td>
     <td width="15%" class="a_table_2">题库试题数：<span id="manufacturerCount"></span></td>
    <td width="15%" class="a_table_2"><div id="multipled">小计：</div></td>
  </tr>

  <tr >
    <td width="40%" class="a_table_1"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />
     判断题<input type="text" name="judge" class="sel_input"  id="judge" 
      onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()"/>
     每题<input type="text" class="sel_input2" name="judgeScore" id="judgeScore"
    	onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()">分
    </td>
 	<td width="15%" class="a_table_1">题库试题数：<span id="difficutyCount"></span></td>
    <td width="15%" class="a_table_1"><div id="judged">小计：</div></td>
  </tr>
 
  <tr >
  	<!-- <td width="30%"  class="a_table_2">&nbsp;</td> -->
    <td width="40%" class="a_table_2"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />
     简答题<input type="text" name="qa" class="sel_input"  id="qa" 
     onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()"/>
     每题<input type="text" class="sel_input2" name="qaScore" id="qaScore"
     onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()">分
    </td>
 	<td width="15%" class="a_table_2">题库试题数：<span id="qaQuestionCount"></span></td>
    <td width="15%" class="a_table_2"><div id="qaed">小计：</div></td>
  </tr>
  
  <tr >
  	<!-- <td width="30%"  class="a_table_1">&nbsp;</td> -->
    <td width="40%" class="a_table_1"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" />
     论述题<input type="text" name="essay" class="sel_input"  id="essay" 
     onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()"/>
     每题<input type="text" class="sel_input2" id="essayScore" name="essayScore"
     onKeyUp="validatezs(this)" onafterpaste="validatezs(this)" onblur="checkfs()">分 
    </td>
 	<td width="15%" class="a_table_1">题库试题数：<span id="essayCount"></span></td>
    <td width="15%" class="a_table_1"><div id="essayed">小计：</div></td>
  </tr>
  
   <tr >
  	<td width="30%"  class="a_table_2">&nbsp;</td>
    <!-- <td width="40%"  class="a_table_2">&nbsp;</td> -->
 	<td width="15%"  class="a_table_2">&nbsp;</td>
    <td width="15%"  class="a_table_2"><div id="countd">合计：</div></td>
  </tr>
 
</table>
<table>
<tr><td><input name="button"  type="button" class="add_btn_02"  value=" 增加" onclick="doUserSelect()" /></td></tr>
<tr><td width="3000px"><select id="company_exam" name="company_exam" size="9" style="width:100%" ondblclick="deleteUserSel()"> 

 
 </select></td></tr>
   
</table>

	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_3">
        <tr>
          <td align="center">
          	<img src="<%=basePath%>examonline/manage/images/button_1.gif" width="151" height="45" onClick="confirm()" />
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


 <div id="sheet-detail-page">
	<div id="proGrid" class="tabContent">
		<iframe id="history" width="100%" height="520" frameborder="no" border="0" framespacing="0"
			name="history" src="<%=basePath%>examonline/examConfigList.do?examType=prov">
		</iframe>
	</div>
	<div class="sheet-deal-content" id="sheet-deal-content"></div>
</div>

</body>
</html>