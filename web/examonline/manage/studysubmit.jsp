<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.*,java.util.*,java.io.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>
提交结果
</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/examonline/style.css" />
</head>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=request.getContextPath()%>/examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>提交结果</td>
          <td width="27" align="right"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<bean:define id="right" name="ExamWarehouse" property="value" type="java.lang.Integer" />
<%
String showString = "";
if ( right.intValue() < 1 )
  showString = "<font color='red'>回答错误！</font>";
else if ( right.intValue() == 1 )
  showString = "<font color='red'>部分正确，再接再厉！</font>";
else
  showString = "<font color='red'>恭喜，答对了！</font>";

String typeSel = StaticMethod.null2String(request.getParameter("typeSel"));
%>

<body bgcolor="#ffffff">
<form name="StudyForm" method="post" action="<%=basePath%>examonline/study.do">
<html:hidden property="typeSel" value="<%=typeSel%>" />
<br>
<br>
<br>
<br>
<table cellpadding="0" cellspacing="0" width="100%" border="0" class="table_2">
  <tr align="center">
   <td>
     <B><%=showString%></B>
   </td>
  </tr>
</table>
<br>
<table width="100%"    cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
  <tr class="tr_show" height="35">
    <td width="10%" align="center"><img src="<%=request.getContextPath()%>/examonline/manage/images/icon_3.gif" width="15" height="14" /> 标题
    </td>
    <td width="90%" > * 
      <bean:write name="ExamWarehouse" property="title" />
    </td>
  </tr>
  <tr class="tr_show" height="35">
    <td width="10%" align="center" ><img src="<%=request.getContextPath()%>/examonline/manage/images/icon_3.gif" width="15" height="14" /> 答案
    </td>
    <td width="90%"> * 
      <bean:write name="ExamWarehouse" property="comment" />
    </td>
  </tr>
  <tr class="tr_show" align="center">
    <td colspan="2">
      <a href="#" onClick="goNext()"><img src="<%=request.getContextPath()%>/examonline/manage/images/button_22.png" /></a>
      <a href="#" onClick="goFirst()"><img src="<%=request.getContextPath()%>/examonline/manage/images/button_19.png" /></a>
    </td>
  </tr>
</table>
</form>
<script language="javascript">
function goNext()
{
  document.StudyForm.submit();
}
function goFirst()
{
  window.location="StudySelect.do";
}
</script>
</body>
</html>
