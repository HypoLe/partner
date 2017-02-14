<%@ page language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
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
 <link rel="stylesheet" type="text/css" href="<%=basePath%>styles/default/theme.css" />
<title>
在线考试
</title>
<script language="javascript" src="<%=request.getContextPath()%>/css/checkform.js"></script>

<%
  int subnum = 0;
  boolean nchk_single=true,nchk_multi=true,nchk_juge=true,single=false,multi=false,juge=false;
%>
<script language="JavaScript" >

function $(id){return document.getElementById(id);}
function finish(){
	var count = <%=((java.util.List)request.getAttribute("list")).size()%>
	var an;
	var total = 0;
	for(i=1;i<=count;i++){
		an = ''; //用户答案
		var v=$('v'+i).value; // 正确答案
		var o=document.getElementsByName('o'+i); // 选项
		var s=$('s'+i); // 提示信息
		var p=$('p'+i).value; // 分值
		total =parseInt(total,10)+parseInt(p,10);
		var point = parseInt(p,10);
		for(j=0;j<o.length;j++){
			o[j].disabled='true';
			if(o[j].checked){
				//an=o[j].value;
				an = an + o[j].value + ';';
			}
		}
		if(an != ''){
			an = an.substr(0,an.length -1);
		}else{
			s.innerHTML='没有答题！';
			total = total - point;
//s.innerHTML=point+'没有答题！'+total;
			continue;
		}
		var anArr = an.split(';'); //用户回答
		var vArr; //正确答案
		if(v.indexOf(';')==-1 && v.indexOf('；')!=-1){
			vArr = v.split('；');
		}else{
			vArr = v.split(';');
		}
		
		if(anArr.length != vArr.length){
			s.innerHTML = '回答错误！正确答案：' + v;
			total = total - point;
			continue;
		}
		for(n=0;n<anArr.length;n++){
			if(v.indexOf(anArr[n]) == -1){
				s.innerHTML = '回答错误！正确答案：' + v;
				total = total - point;
				break;
			}
		}
	}
	var score = $('score');
	score.innerHTML = '得分：' + total + '分';
}
/**
* 重新考试
*/
function reMockExam(){
	document.getElementById("total").value=0;
	document.getElementById("cmd").value="remock";
	var form = document.forms[0];
	form.submit();
}
function view(image){
    var win;
	win=window.open("/eomsMain/examonline/manage/view.jsp?fileName="+image,"图片显示","height=350,width=350,left=0,top=350,resizable=yes,scrollbars=yes,status=no");
}
</script>

<style type="text/css">
</style>

</head>

<body >

<form action="<%=basePath %>examonline/mockExam.do?cmd=remock">
<input type="hidden" name="typeSel"id="typeSel" value='${typeSel}'/>
<input type="hidden" name="begin" value="true"/>
<input type="hidden" name="cmd" id="cmd"/>
<input type="hidden" name="radioCount" value="${radioCount }"/>
<input type="hidden" name="multipleCount" value="${multipleCount }"/>
<input type="hidden" name="judgeCount" value="${judgeCount }"/>
<input type="hidden" name="total" id="total"/>
  <br/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>模拟考试</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
  </table>



<div id="divTable" style="position: relative; align: center; top: 5px;width:  100%; height:  85%; z-index: 1; overflow: auto; overflow-x: hidden"> 
  <table cellpadding="1" cellspacing="1" width="95%" border="0" class="table_show">
    <logic:iterate id="SubjectObj" name="list" type="com.boco.eoms.examonline.model.ExamWarehouse"> 
  		
		 <tr align="center" class="tr_show"> 
    <%
    if(SubjectObj.getContype()==1&&!single){
    	single=true;
    	multi=false;
    	juge=false;
    }else if(SubjectObj.getContype()==2&&!multi){
    	single=false;
    	multi=true;
    	juge=false;
    }
    else if(SubjectObj.getContype()==3&&!juge){
    	single=false;
    	multi=false;
    	juge=true;
    }
    if(single&&nchk_single)
    {
     %>
      <td  width="100%" align="left" colspan="2"> 
        <!--标记-->
        <h4><b>
       一:单项选择题(只有一个正确答案,每题2分)</b></h4>
        </td>
        <%
         nchk_single=false;
        } 
        else if(multi&&nchk_multi){
        %>
        <td width="100%" align="left"  colspan="2"> 
       <h4><b>
       二:多项选择题(有一个或多个正确答案,每题4分,少选,多选或错选均不得分)</b></h4>
        </td>
        <%
      		nchk_multi=false;
        }else if(juge&&nchk_juge) {
        %>
         <td  width="100%" align="left"   colspan="2"> 
       <h4><b>
       三:判断题(只有一个正确答案,每题1分)</b></h4>
        </td>
        <% nchk_juge = false;
        }%>
      </tr>
    <tr align="center" class="tr_show"> 
      <td width="88%" colspan="2" align="left" bgcolor="#d7e1ed"><img src="<%=request.getContextPath()%>/examonline/manage/images/icon_3.gif" width="15" height="14" /> 题
        <%=++subnum%>
        ： 
        <%=SubjectObj.getTitle()%><!--标题-->
        <logic:notEmpty name="SubjectObj" property="image"> 
        <!--图片-->
        <img src="<%=request.getContextPath()%>/images/bottom/an_xs.gif" border="0" onClick="view('<%=SubjectObj.getImage()%>')"> 
        </logic:notEmpty>
        <font id='s<%=subnum%>' color="red"></font>
        
        <!-- 隐藏域存储正确答案 -->
        <input type='hidden' id='v<%=subnum%>' value='<%=SubjectObj.getResult().trim()%>' />
        <!-- 隐藏域存储分值 -->
        <input type='hidden' id='p<%=subnum%>' value=<%=SubjectObj.getValue()%> />
      </td>
    </tr>
    
    
    <%
	//选项
 	String[] options = SubjectObj.getOptions().split(";");
  	for(int i = 0; i < options.length; i++ ){
      String comment = options[i].trim();
      String le = comment.substring(0,1);
      int contype = SubjectObj.getContype();
      if(contype == 2){ //多选
	%>
    <tr align="center"> 
      <td width="2%"> <input name="o<%=subnum%>" type="checkbox" value="<%=le%>" > 
      </td>
      <td width="98%" align="left"> 
        <%=comment%>
      </td>
    </tr>
    <%
    } else if(contype==1 || contype==3) //单选和判断
    {
    %>
    <tr align="center" class="tr_show"> 
      <td width="2%"> 
      	<input name="o<%=subnum%>" type="radio" value="<%=le%>" > 
      </td>
      <td width="98%" align="left"> 
        <%=comment%>
      </td>
    </tr>
    <% }
      }%>
    </logic:iterate> 
   
  </tr>
  </table>
  
  <input name="button" type="button" class="add_btn_05"  value="提交试卷" onclick="finish()";/> 
  <input name="button" type="button" class="add_btn_05"  value="重新开始" onclick="reMockExam()";/>
  <font id='score' color="red"></font>
  <br/><br/><br/><br/>
</div>
</form>
</body>
</html>
