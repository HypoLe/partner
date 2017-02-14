	
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
   
    
    <title>制定考试页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <script type="text/javascript" src="<%=basePath%>examonline/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="<%=basePath%>scripts/base/prototype-1.5.1.js"></script>
  <script language="javascript" type="text/javascript">
	Ext.onReady(function(){
		regener();
	});
	
	function regener(){
	 var form = document.forms[1];
	 form.submit();
	}
		
	function updept(o){
		var url="<%=basePath%>examonline/ajaxgetdept.do";
		var parssf="deptid="+o.value;
		new Ajax.Updater("deptId",url,{method:'post',parameters:parssf});
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
	
	//点击制定按钮执行
	function commitform(){ 
		var form = document.forms[0];
		//增加js校验 liuchang 2011-01-17
		if(form.starttime.value == ""){
			alert('请选择开始时间');
			return;
		}
		if(form.times.value == ""){
			alert('请填写考试时长');
			return;
		}	
		if(form.times.value < 0.5){
			alert('考试时长不能小于半小时');
			return;
		}	
		if(form.company_exam.options.length==0){
			alert('请首先点击增加按钮增加考试信息');
			return;
		}
  		for(var i = 0; i < form.company_exam.options.length; i++){
  			if (i == 0)
        		form.companys.value = form.company_exam[i].value;
      		else
        		form.companys.value = form.companys.value + ";" + form.company_exam[i].value;
    	}
		form.submit();
	}
		  
	var num=0;
	
	//点击增加按钮执行
	function doUserSelect(){
      var form = document.forms[0];
      var flag = false;
      //增加js校验 liuchang 2011-01-17
	  if(form.companyId.options[form.companyId.selectedIndex].value == '-1'){
	  	alert('请选择单位');
	  	return;
	  }
	  if(!$F('testerCount')){
		alert('请填写应试人数');
	  	return;
	  }
      var userSel = form.companyId.options[form.companyId.selectedIndex].text+"--"+$F('testerCount')+"人";
      var userSels = form.companyId.options[form.companyId.selectedIndex].text;
      for(var i = 0 ; i < form.company_exam.options.length ; i++){
        if (form.company_exam.options[i].text.indexOf(userSels) >= 0 )
          flag = true;
      }
      if( !flag ){
      	num=num+1;
        var text = new Option(userSel);
        text.value = form.companyId.value+":"+$F('testerCount');
       // alert(text.value);
        form.company_exam.options[form.company_exam.options.length]=text;
      }
      else
        alert("所选项重复");
  }
  function deleteUserSel(){
    var form = document.forms[0];
    form.company_exam.options[form.company_exam.selectedIndex] = null;
     num=num-1;
  }
  
  /**
  *  获取考试时间时长
  *  liuchang 
  *  2011-01-17
  */
  function gettime(){
  //2011-01-11 15:29:19
  var form = document.forms[0];
  var starttimeValue = form.starttime.value;
  var endtimeValue = form.endtime.value;
	  var objDate1=new Date(starttimeValue.substring(0,4),starttimeValue.substring(5,7),starttimeValue.substring(8,10),
	  starttimeValue.substring(11,13),starttimeValue.substring(14,16),starttimeValue.substring(17,19));
	 var objDate2=new Date(endtimeValue.substring(0,4),endtimeValue.substring(5,7),endtimeValue.substring(8,10),
	  endtimeValue.substring(11,13),endtimeValue.substring(14,16),endtimeValue.substring(17,19));
	var msSpan=objDate2.getTime()-objDate1.getTime();
	var c = (msSpan/(60*60*1000)).toFixed(2);
	
	if(c<0){
		 form.endtime.value = '';
		 form.times.value='';
		 alert('结束日期必须大于开始日期');
	}else if(c < 0.5){
		 form.endtime.value = '';
		 form.times.value='';
		 alert('考试时间必须大于半小时');
	}else{
		form.times.value=c + '小时';
	}
  }
  
  //只能输入正整数
  function clearNoNum(obj){
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d.]/g,"");
		//必须保证第一个为数字而不是.
		obj.value = obj.value.replace(/^\./g,"");
		//保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{2,}/g,".");
		//保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	}
  
  //计算考试结束时间
  function getEndTime(){
    var form = document.forms[0];
  	var starttimeValue = form.starttime.value;
  	var hours = form.times.value;
  	if(starttimeValue=='' || hours==''){
  		document.getElementById('endtimeShow').innerHTML = '';
  		return;
  	}
  	//js的月份是0-11 而不是1-12，因此这里的月份要减1
  	var objDate1=new Date(starttimeValue.substring(0,4),starttimeValue.substring(5,7)-1,starttimeValue.substring(8,10),
	  starttimeValue.substring(11,13),starttimeValue.substring(14,16),starttimeValue.substring(17,19));
	
	var iInterval = (60*hours) * 60 * 1000;
    //var dEnd = new Date(objDate1.valueOf() + iInterval);
     var dEnd = new Date(objDate1.getTime() + iInterval);
     
    var endTime = dEnd.getFullYear() + "-";
    endTime += (dEnd.getMonth()+1) + "-";
    endTime += dEnd.getDate() + ' ';
    endTime += (dEnd.getHours()>=10 ? dEnd.getHours() : ("0"+dEnd.getHours())) + ':';
    endTime += (dEnd.getMinutes()>=10 ? dEnd.getMinutes() : ("0"+dEnd.getMinutes()))+ ':';
	endTime += (dEnd.getSeconds()>=10 ? dEnd.getSeconds() : ("0"+dEnd.getSeconds()));
	document.getElementById('endtimeShow').innerHTML = endTime;
	form.endtime.value = endTime;
  }
  
  function getTester(){
  	var partnerDeptId = document.getElementById('partnerDeptId').value;
  	if(partnerDeptId != '-1'){
  		Ext.get(document.body).mask('获取应试人数中......');
  		Ext.lib.Ajax.request("get", '<%=basePath%>examonline/examPartner.do?method=getPartnerTesterCount&partnerDeptId='+partnerDeptId, {
			success: function(response,options){		
				Ext.get(document.body).unmask();
				if(response.responseText){ //如果不为null
					var count = response.responseText;
					var testerCount = document.getElementById('testerCount');
					testerCount.value = count;
				}
			},
			failure: function(){
				Ext.get(document.body).unmask();
				Ext.Msg.show({
					title: '错误提示',
					msg: '获取应试人数失败',
					buttons: Ext.Msg.OK,
					icon: Ext.Msg.ERROR
				});
			}
		});
  	}else{
  		var testerCount = document.getElementById('testerCount');
		testerCount.value = '';
  	}
  	
  }
</script>

 </head>
  <%
  int i=0;
   %>
  <body>
  <form action="<%=basePath%>examonline/generateExamPartner.do">
  <input type="hidden" name="reGenerate" id="reGenerate"/>
  <input type="hidden" name="companys" id="companys"/>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>制定考试</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
  </table>
  
 <table width="100%"    cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
    <tr>
    <td><font color="red"><strong> * </strong></font>开始时间:<input class="Wdate text" type="text"  name="starttime" id="starttime" 
onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" onpicked="getEndTime()"/>
</td>
	<td><font color="red"><strong> * </strong></font>考试时长(小时):
	<input type="text" name="times" width="2px" onkeyup="clearNoNum(this)" onblur="getEndTime()"/>
   </td>
      
  <td><font color="red"><strong> * </strong></font>
  	<select name="companyId" id="partnerDeptId" size="1" onChange="getTester();">
  		<option value="-1">请选择代维公司</option>　
		<c:forEach items="${prnDeptList}" var="prnDept" > 　
			<option value="${prnDept.id}">${prnDept.name}</option>
		</c:forEach> 
  	</select>
  </td>
      <td><font color="red"><strong> * </strong></font>
      应试人数:<input type="text" id="testerCount" name="testerCount" width="2px" onKeyUp="checkzf(this)" readonly="readonly"/>
      <input name="button" type="button" class="add_btn_02"  value=" 增加" onclick="doUserSelect()" />
	</td>
      <td nowrap="nowrap"></td>
      <td>
      	<input name="button" type="button" class="add_btn_02"  value=" 制定" onclick="commitform()" />
      </td>
    </tr>

 	<tr>
   	 <td colspan="7"><font color="red"><strong> * </strong></font>结束时间：
   	 <span style="color: red;" id="endtimeShow"></span>
   		<input type="hidden"  name="endtime" />
   	 </td> 
   </tr> 
   <tr height="2">
   <td colspan="7">
 <select id="company_exam" name="company_exam" size="9" style="width:100%" ondblclick="deleteUserSel()"> 
 
 
 </select>   
 
   </td>
   </tr>
    
  </table>
    </form>
    
    <form action="<%=basePath%>examonline/performGernateExam.do" target="selectDeptSub">
	    <input type="hidden" name="reGenerate" id="reGenerate1" style="width:100%"/>
	    <input name="button" type="button" class="add_btn_06"  value=" 重新生成试卷" onclick="regener()" />
    </form>
 	<iframe name="selectDeptSub"  id="selectDeptSub" height="500" width="100%" ></iframe>
  
  </body>
</html>
