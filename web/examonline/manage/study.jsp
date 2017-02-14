<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.*,java.util.*,java.io.*"%>
<html>
<head>
  
<title>
在线学习
</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/examonline/style.css" />
<script language="JavaScript" >

function checkItem()
{
	var form = document.studyForm;
   for (var i=0;i<form.elements.length;i++) {
     var e = form.elements[i];
     if (e.type=='checkbox'){
	  if (e.checked){
            if( form.options.value == "" || form.options.value == null )
              form.options.value = e.name;
            else
              form.options.value = form.options.value + ";" + e.name;
	  }
     }
   }
   if ( form.options.value == "" || form.options.value == null ){
     alert("选择答案不能为空");
     return false;
   }
   form.action = '${pageContext.request.contextPath}/examonline/StudySubmit.do';
   form.submit();
}
function view(image){
        var win;
        win=window.open("/EOMS_J2EE/studyonline/manage/view.jsp?fileName="+image,"图片显示","height=350,width=350,left=0,top=350,resizable=yes,scrollbars=yes,status=no");
}
</script>

<!--定义正确数和总体数，求正确率时使用-->
<bean:define id="right" name="Usercontent" property="right" type="java.lang.Integer" />
<bean:define id="total" name="Usercontent" property="total" type="java.lang.Integer" />

<%
  String typeSel = StaticMethod.null2String(request.getParameter("typeSel"));
  String showTop = "TOP10排名 ";
  int topId = 1;
%>

<logic:iterate id="topTen" name="TopTen" type="com.boco.eoms.examonline.model.ExamTopTen">
  <% showTop += topId++ + "." + topTen.getUserId() + ":" + topTen.getSum() + "分 "; %>
</logic:iterate>

</head>
<body>
<form name="studyForm" method="post">
<input type="hidden" name="options"> 

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=request.getContextPath()%>/examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>自测练习</td>
          <td width="27" align="right"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<table width="100%" cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
  <tr>
      <td colspan=5 align="center" >
        <marquee scrollamount=3><%=showTop%></marquee>
      </td>
  </tr>
  <tr>
    <td align="left" >&nbsp;&nbsp;&nbsp;&nbsp;
    	<img src="<%=request.getContextPath()%>/examonline/manage/images/icon_3.gif" width="15" height="14" /> 您的积分：<bean:write name="Usercontent" property="mark"/>
    </td>
    <td align="left" >
      正确数：<bean:write name="Usercontent" property="right"/>
    </td>
    <td align="left" >
      总题数：<bean:write name="Usercontent" property="total"/>
    </td>
    <td align="left" >
      正确率：
      <%=( 0 == total.intValue() ? 0 : Math.round((float)right.intValue() / total.intValue() * 10000) / 100.0)%>%
    </td>
  </tr>
</table>
<br>
<br>

<logic:iterate id="SubjectObj" name="ExamWarehouse" type="com.boco.eoms.examonline.model.ExamWarehouse">
<%
  String[] options = SubjectObj.getOptions().split(";");
  //System.out.println(options[0]);
%>

<table width="100%" cellspacing="1" cellpadding="1" class="table_2" align=center border="0">
<tr align="center">
   <td width="10%"><img src="<%=request.getContextPath()%>/examonline/manage/images/icon_3.gif" width="15" height="14" /> <%
   if(SubjectObj.getContype()==1)
    out.print("单选题");
     if(SubjectObj.getContype()==2)
    out.print("多选题");
     if(SubjectObj.getContype()==3)
    out.print("判断题");
   
   %></td>
   <td width="90%" >
     <bean:write name="SubjectObj" property="title" scope="page"/>              <!--标题-->
     <logic:notEmpty name="SubjectObj" property="image">                   <!--图片-->
          <img src="<%=request.getContextPath()%>/images/bottom/an_xs.gif" border="0" onClick="view('<%=SubjectObj.getImage()%>')">
      </logic:notEmpty>
   </td>
 </tr>
 </table>

<table width="100%" cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
<input type="hidden" name="typeSel" value="<%=typeSel%>"/> 
<%
  for(int i = 0; i < options.length; i++ ){
    String comment = options[i].toString();
    String opt = comment.substring(0,1);
%>
  <tr align="center" class="tr_show">
    <td width="10%">
      <input name="<%=SubjectObj.getSubId()%><%=opt%>" type="checkbox">
    </td>
    <td width="90%" align="left">
      <%=comment%>
    </td>
  </tr>
<%
  }
%>
  <tr align="center" class="tr_show">
    <td colspan="2">
    	<a href="#" onClick="checkItem()"><img src="<%=request.getContextPath()%>/examonline/manage/images/button_1(1).png" /></a>
    </td>
  </tr>
</table>
</logic:iterate>
</form>
</body>
</html>
