<%@ page language="java" pageEncoding="UTF-8"  import="java.util.*,com.boco.eoms.examonline.model.ExamWarehouse"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>制定考试子页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script type="text/javascript" src="${app}/scripts/base/prototype.js"></script>
    <script type="text/javascript">
       
        //通过ajax更新session里面的试题分数
		function changePoint(newValue,id){
			Ext.get(document.body).mask('分数修改中......');
			
			var totalScore = parseInt(document.getElementById('totalScore').innerHTML);
       		var oldid = 'o_' + id;
       		var oldValue = document.getElementById(oldid).value;
       		document.getElementById(oldid).value = newValue;
       		var newTotalScore = totalScore + parseInt(newValue) - parseInt(oldValue);
       		document.getElementById('totalScore').innerHTML = newTotalScore;
       		document.getElementById('totalScoreNew').value = newTotalScore;
			
			Ext.lib.Ajax.request("get", '<%=basePath%>examonline/changeExamScore.do?id='+id+'&newValue='+newValue+'&newTotalScore='+newTotalScore, {
				success: function(response,options){		
					Ext.get(document.body).unmask();
				},
				failure: function(){
					Ext.get(document.body).unmask();
					Ext.Msg.show({
						title: '错误提示',
						msg: '修改分数失败',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.ERROR
					});
				}
			});
		}
       
       function delExam(subId,contype,typeSelId){
       		var form=document.forms[0];
       		form.op.value = 'remove';
       		form.subId.value = subId;
       		form.contype.value = contype;
       		form.submit();
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
		
		function opeWin(contype){
			//var typesel = document.getElementById("typeselselected");
			//alert(typesel.selectedIndex);
			var typesel = document.getElementById("typeselselected").selectedIndex;
			var url = '<%=basePath%>examonline/operateExamConfigAdd.do?contype='+ contype +'&typesel=' + typesel;
			window.open(url);
		}
		function num2str(){
			var diff=document.getElementById("textdifficulty").value;
			if(diff=1){
				document.getElementById("difficulty").innerHTML="初级"
			}
			if(diff=2){
				document.getElementById("difficulty").innerHTML="中级"
			}
			if(diff=3){
				document.getElementById("difficulty").innerHTML="高级"
			}
		}
		function showExamcount(){
			var selectIndex=document.getElementById("typeselselected").selectedIndex;
			var jsonArray = ${sessionScope.examTypeSelForm};
			var list = eval(jsonArray); 
			var storeRadioCount=list[selectIndex].storeRadioCount;			
			var storeMultipleCount=list[selectIndex].storeMultipleCount;			
			var storeJudgeCount=list[selectIndex].storeJudgeCount;			
			var storeQaCount=list[selectIndex].storeQaCount;			
			var storeEssayCount=list[selectIndex].storeEssayCount;	
			document.getElementById("radioCountId").innerHTML=storeRadioCount;	
			document.getElementById("multipleCountId").innerHTML=storeMultipleCount;	
			document.getElementById("judgeCountId").innerHTML=storeJudgeCount;	
			document.getElementById("qaCountId").innerHTML=storeQaCount;	
			document.getElementById("essayCountId").innerHTML=storeEssayCount;	
		}		
    </script>
  </head>
  <body>
  	<input type="hidden" id="textdifficulty" value="${list.difficulty}"/>
  	 <form action="<%=basePath%>examonline/operateExamConfig.do" name="del">
  	 	<input type="hidden" name="op" value="" />
  	 	<input type="hidden" name="subId" value="" />
  	 	<input type="hidden" name="contype" value="" />
  	 	<input type="hidden" name="typeSelId" value="" />
  	 	<input type="hidden" name="totalScoreNew" value="" id="totalScoreNew"/>
            <div align="center"><font color="red" size="4" >试卷标题：${sessionScope.issuetitle}&emsp;&emsp;</font>总分:${sessionScope.totalScore}</div>
  	<%-- <div align="center"><font color="red" size="4" >${sessionScope.title}</font></div>--%>
      <table width="100%" border="0" cellpadding="0" cellspacing="0"   >
        <tr>
          <c:forEach items="${sessionScope.typeSelList}"  var="typeSel" >
          <tr>
          		<td ><font color="#000000">专业：<eoms:id2nameDB id="${typeSel.specialty}" beanId="ItawSystemDictTypeDao"/></font>  &nbsp;&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">厂商：<eoms:id2nameDB id="${typeSel.manufacturer}" beanId="ItawSystemDictTypeDao"/></font>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">难易度：
			  	<c:if test="${typeSel.difficulty==1}">初级</c:if>
 				 <c:if test="${typeSel.difficulty==2}">中级</c:if>
  				 <c:if test="${typeSel.difficulty==3}">高级</c:if>
			  	
			  	</font> &nbsp;&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">单选题：${typeSel.radioCount}</font>&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">多选题：${typeSel.multipleCount}</font>&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">判断题：${typeSel.judgeCount}</font>&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">简答题：${typeSel.qaCount}</font>&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">论述题：${typeSel.essayCount}</font>&nbsp;&nbsp;&nbsp;</td>
			  	<td ><font color="#000000">总分：${typeSel.radioCount*typeSel.radioScore+typeSel.multipleCount*typeSel.multipleScore+typeSel.judgeCount*typeSel.judgeScore+typeSel.qaCount*typeSel.qaScore+typeSel.essayCount*typeSel.essayScore}
			  	</font>&nbsp;&nbsp;&nbsp;</td>
          </tr>
          </c:forEach>
        </tr>
  	</table>
  
    <%
  		int i=0;
   %>
   <table width="100%" cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
  <tr>
    <td nowrap bgcolor="#d7e1ed">
      <div align="center">序号</div>
    </td>
    <td align="center" nowrap bgcolor="#d7e1ed">
      标题    
    </td>
    <td align="center" nowrap bgcolor="#d7e1ed">
     选项    
    </td>
    <td align="center" nowrap bgcolor="#d7e1ed">
     分数   
    </td>
    <td align="center" nowrap bgcolor="#d7e1ed">
     操作   
    </td>
  </tr>
  <%int j=1; %>
     <c:forEach items="${sessionScope.examWarehouseList}" var="exam">
    <tr>
    <td><%=j++%></td>
    <td>${exam.title }</td>
    <td>${exam.options }</td>
    <td>${exam.value }</td>
    <td width="5%" align="left">
    <input name="button" type="button" class="add_btn_02"  value=" 删除" onclick="delExam('${exam.subId}','${exam.contype}')" />
    </td>
    </tr>
    </c:forEach>
    </td>
 </tr>
  </table>
  </form>
  选择类别：<select id="typeselselected" name="typeselselected" onchange="showExamcount()">
  <c:forEach items="${sessionScope.typeSelList}" var="typeSel">
  <option >专业：<eoms:id2nameDB id="${typeSel.specialty}" beanId="ItawSystemDictTypeDao"/>;厂商：<eoms:id2nameDB id="${typeSel.manufacturer}" beanId="ItawSystemDictTypeDao"/>;
  难易度:
  <c:if test="${typeSel.difficulty==1}">初级</c:if>
  <c:if test="${typeSel.difficulty==2}">中级</c:if>
  <c:if test="${typeSel.difficulty==3}">高级</c:if>
  </option>
  </c:forEach>
  </select>
  <br/>
  <table width="100%">
  <tr >
		  <td width="19%"><input name="button" type="button" id="storeRadioCount" class="add_btn_06"  value=" 单选题添加" 
		  	onclick="opeWin(1)">共：<span id="radioCountId">${sessionScope.storeCount[0].storeRadioCount}</span>&emsp;道</td>
		  <td width="19%"><input name="button" type="button" id="storeMultipleCount" class="add_btn_06"  value=" 多选题添加" 
		  	onclick="opeWin(2)">共：<span id="multipleCountId">${sessionScope.storeCount[0].storeMultipleCount}</span>&emsp;道</td>
		  <td width="19%"><input name="button" type="button" id="storeJudgeCount" class="add_btn_06"  value=" 判断题添加" 
		  	onclick="opeWin(3)">共：<span id="judgeCountId">${sessionScope.storeCount[0].storeJudgeCount}</span>&emsp;道</td>
		  <td width="19%"><input name="button" type="button" id="storeQaCount" class="add_btn_06"  value=" 简答题添加" 
		  	onclick="opeWin(4)">共：<span id="qaCountId">${sessionScope.storeCount[0].storeQaCount}</span>&emsp;道</td>
		  <td width="19%"><input name="button" type="button" id="storeEssayCount" class="add_btn_06"  value=" 论述题添加" 
		  	onclick="opeWin(5)">共：<span id="essayCountId">${sessionScope.storeCount[0].storeEssayCount}</span>&emsp;道</td>
		  	<td width="20%"></td>
  	</tr>
  	</table>
  <br/><br/>
  </body>
</html>
