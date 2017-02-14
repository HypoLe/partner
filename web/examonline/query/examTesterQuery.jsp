<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.*,com.boco.eoms.commons.system.session.form.TawSystemSessionForm,java.util.*,java.io.*"%>
<html:html>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<head>
<title>
分数情况查询
</title>
<script language="javascript" src="<%=request.getContextPath()%>/css/checkform.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/examonline/style.css" />
<script type="text/javascript">
	function exportScore(issueId){
		window.location.href="<%=basePath %>examonline/exportScore.do?cmd=score&&issueId="+issueId;
	} 
</script>


</script>
</head>

<body bgcolor="#ffffff">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=request.getContextPath()%>/examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>分数情况查询</td>
          <td width="27" align="right"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>
<table cellpadding="1" cellspacing="1"width="100%" align="center" border="0" class="datalist">
  <tr align="center">
    <td class="label" width="20%">
     姓名
    </td>
    <td  class="label" width="16%">
      单位
    </td>
    <td class="label" width="16%">
      考试成绩
    </td>
    <td class="label" width="16%">
      考试时长
    </td>
  </tr>

  <logic:iterate id="deptQueryVO" name="list" type="com.boco.eoms.examonline.qo.ExamTesterQueryVO">
     <tr  align="center">
      <td width="20%">
        <bean:write name="deptQueryVO" property="name"/>
      </td>
      <td width="16%">
        <bean:write name="deptQueryVO" property="companyName"/>
      </td>
      <td width="16%">
        <bean:write name="deptQueryVO" property="point"/>
      </td>
      <td width="16%">
        <bean:write name="deptQueryVO" property="examTime"/>
      </td>
     
    </tr>
    <tr>
  </logic:iterate>
    </td><td></td><td></td><td></td><td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input id="exportScore" type="button" value="导出成绩" onclick="exportScore('${issueId }')"></td></tr>
  </table>
</div>

</body>
</html:html>
