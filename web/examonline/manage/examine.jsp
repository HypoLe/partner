<%@ page language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<%@ page import="com.boco.eoms.common.util.*"%>
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
  int Page = StaticMethod.nullObject2int( request.getParameter("pager.size") );
  int offset = StaticMethod.nullObject2int( request.getParameter("pageroffset") );
  int subnum = offset + 1;
  boolean nchk_single=true,nchk_multi=true,nchk_juge=true,nchk_qa=true,nchk_essay=true;
  boolean single=false,multi=false,juge=false,qa=false,essay=false;
%>
<script language="JavaScript" >

function checkItem(form)
{
   for (var i=0;i<form.elements.length;i++) //循环表单所有元素
   {
     var e = form.elements[i];
     if (e.type=='checkbox')//查找checkbox类型及alt属性不是tag的元素（即为答案项）
     {
	  if (e.checked){
            if( form.options.value == "" || form.options.value == null )//如果表单域options为空值，则赋予当前循环到的元素的值
              form.options.value = e.name;
            else
              form.options.value = form.options.value + ";" + e.name;//如果表单域options有值，则追加当前循环到的元素的值
	  }
     }
     else if (e.type=='radio'){//查找checkbox类型及alt属性是tag的元素（即为标记类型元素）
       if(e.checked){
         if( form.options.value == "" || form.options.value == null )
             form.options.value = e.value;
         else
             form.options.value = form.options.value + ";" + e.value;
       }
     }
   }
}

function view(image)
{
        var win;
		win=window.open("/eomsMain/examonline/manage/view.jsp?fileName="+image,"图片显示","height=350,width=350,left=0,top=350,resizable=yes,scrollbars=yes,status=no");
}
function finish(){
  if ( !confirm("交卷后则不能再进行试卷填写。是否确认？"))
    return false;
  var form=document.forms[0];
  form.isFinish.value="true";
  checkItem(form);
  form.submit();
}
function goPage(length,offset){
  var form=document.forms[0];
 // form.action = form.action + "?pageroffset="+offset+"&pagersize="+length;
  //alert(form.action);
  form.pageroffset.value=offset;
  form.pagersize.value=length;
  checkItem(form);
  //alert(form.action);
  form.submit();
}
</script>

<style type="text/css">
</style>

</head>

<%
//获取已经选中的选项
Object selObj =  request.getAttribute("Sel") ;
String hadSel = "";
if(selObj != null){
	hadSel = selObj.toString();
}
%>

<script type="text/javascript">
//检查字数
function checksize(sel){
		
		//var qatextcontent=document.getElementById("qatext").value
		if(sel.value.length>500){
			alert("该题输入的内容不能超过500个字数否则超过的字数将会被自动截取");
		}
}
</script>
<body >

<form   method="post" action="<%=basePath %>examonline/examine.do">
<input type="hidden" name="options" >
<input type="hidden" name="pageroffset" id="pageroffset" >
<input type="hidden" name="pagersize" id="pagersize">
<input type="hidden" name="tags" >
<input type="hidden" name="issueid" value="${issueid}">
<input type="hidden" name="isFinish" >
	
  <br/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>实时联考</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
  </table>

  <table cellpadding="0" cellspacing="0" width="95%" border="0">
  <tr>
    
    <td align="center" class="table_title" width="30%" bgcolor="#d7e1ed">
     <h3><b>${examConfig.title }</b></h3>
    </td>
    <td align="center" class="table_title" width="23%" bgcolor="#d7e1ed">
   
      考试时间: ${fn:substring(examConfig.startTime,11,19)}-- ${fn:substring(examConfig.endTime,11,19)}
      
    </td>
    <td align="center" class="table_title" width="23%" bgcolor="#d7e1ed">
      总分：${examConfig.mark }分
    </td>
    <td align="center"  width="23%" bgcolor="#d7e1ed">
      <input name="button" type="button" class="add_btn_02"  value=" 交卷" onclick="return finish()" />
    </td>
  </tr>
  
  <tr>
    <td colspan="2">
    </td>
  </tr>
</table>


<div id="divTable" style="position: relative; align: center; top: 5px;width:  100%; height:  85%; z-index: 1; overflow: auto; overflow-x: hidden"> 
  <table cellpadding="1" cellspacing="1" width="95%" border="0" class="table_show">
    <logic:iterate id="SubjectObj" name="ExamWarehouse" type="com.boco.eoms.examonline.model.ExamIssue"> 
    <tr align="center" class="tr_show"> 
    <%
    if(SubjectObj.getContype().intValue()==1&&!single){
    	single=true;
    	multi=false;
    	juge=false;
    	qa=false;
    	essay=false;
    }else if(SubjectObj.getContype().intValue()==2&&!multi){
    	single=false;
    	multi=true;
    	juge=false;
    	qa=false;
    	essay=false;
    }
    else if(SubjectObj.getContype().intValue()==3&&!juge){
    	single=false;
    	multi=false;
    	juge=true;
    	qa=false;
    	essay=false;
    }else if(SubjectObj.getContype().intValue()==4&&!qa){
    	single=false;
    	multi=false;
    	juge=false;
    	qa=true;
    	essay=false;
    }else if(SubjectObj.getContype().intValue()==5&&!essay){
    	single=false;
    	multi=false;
    	juge=false;
    	qa=false;
    	essay=true;
    }
    if(single&&nchk_single)
    {
     %>
      <td  width="100%" align="left" colspan="2"> 
        <!--标记-->
        <h4><b>
       一:单项选择题(只有一个正确答案)</b></h4>
        </td>
        <%
         nchk_single=false;
        } 
        else if(multi&&nchk_multi){
        %>
        <td width="100%" align="left"  colspan="2"> 
       <h4><b>
       二:多项选择题(有一个或多个正确答案,少选,多选或错选均不得分)</b></h4>
        </td>
        <%
      		nchk_multi=false;
        }else if(juge&&nchk_juge) {
        %>
         <td  width="100%" align="left"   colspan="2"> 
       <h4><b>
       三:判断题(只有一个正确答案)</b></h4>
        </td>
        <% nchk_juge=false;
        }else if(qa&&nchk_qa) { %>
         <td  width="100%" align="left"   colspan="2"> 
       <h4><b>
       四:简答题</b></h4>
        </td>
        <% nchk_qa=false;
        }else if(essay&&nchk_essay) { %>
         <td  width="100%" align="left"   colspan="2"> 
       <h4><b>
       五:论述题</b></h4>
        </td>
        <% nchk_essay=false;
        } %>
      </tr>
    <tr align="center" class="tr_show"> 
       
      <td width="88%" colspan="2" align="left" bgcolor="#d7e1ed"><img src="<%=request.getContextPath()%>/examonline/manage/images/icon_3.gif" width="15" height="14" /> 题
        <%=subnum++%>
        ： 
        <%=SubjectObj.getTitle()%>   <%=SubjectObj.getValue()%>分
        <!--标题-->
        <logic:notEmpty name="SubjectObj" property="image"> 
        <!--图片-->
        <img src="<%=request.getContextPath()%>/images/bottom/an_xs.gif" border="0" onClick="view('<%=SubjectObj.getImage()%>')"> 
        </logic:notEmpty> </td>
    </tr>
    
    
    <%
    if(SubjectObj.getContype().intValue() != 4 && SubjectObj.getContype().intValue() != 5){
    //选项格式为  A|B.选项1;B|D.选项2
 String[] options = SubjectObj.getOptions().split(";");
  for(int i = 0; i < options.length; i++ )
  {
      String comment =
       options[i].trim().substring(0,1) + options[i].trim().substring(2).trim().substring(1);
    String opt = options[i].trim().substring(2).trim().substring(0,1);//实际选项标识：真实的a,b,c,d项
    String checkName = SubjectObj.getSubId() + opt;//
    int contype = SubjectObj.getContype().intValue();
    if(contype == 2) //如果是多选题
    {
%>
    <tr align="center"> 
      <td width="2%"> <input name="<%=checkName%>" type="checkbox" 
       <%if( hadSel.indexOf( checkName ) != -1 ) out.print("checked");%>> 
      </td>
      <td width="98%" align="left"> 
        <%=comment%>
      </td>
    </tr>
    <%
    } else if(contype==1 || contype==3) //如果是单选或者判断题
    {
    %>
    <tr align="center" class="tr_show"> 
      <td width="2%"> <input name="<%=SubjectObj.getSubId()%>" type="radio" value="<%=checkName%>" 
       <%if( hadSel.indexOf( checkName ) != -1 )
       {
        out.print("checked");
        
        }%>> 
      </td>
      <td width="98%" align="left"> 
        <%=comment%>
      </td>
    </tr>
    <% 
    	}
    }
  }else{ //如果是问答题
  %>

    <tr align="center" class="tr_show"> 
      <td width="2%"> 
      </td>
      <td width="98%" align="left"> 
       <textarea rows="3" id="qatext" onblur="checksize(this)" style="width: 100%" name="qa_<%=SubjectObj.getSubId()%>"><%=request.getAttribute("qa_"+SubjectObj.getSubId()) == null ? "" : request.getAttribute("qa_"+SubjectObj.getSubId()) %></textarea>
      </td>
    </tr>
  <%
  }
%>
    </logic:iterate> 
    <tr align="right">
  
    <td colspan="3"><bean:write name="pagerHeader" scope="request" filter="false"/></td>  <!--分页-->
  </tr>
  </table>
  <br/><br/>
</div>
</form>
</body>
</html>
