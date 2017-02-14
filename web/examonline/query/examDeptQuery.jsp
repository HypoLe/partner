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
<script language="javascript" src="<%=request.getContextPath()%>/scripts/base/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/examonline/style.css" />
<script type="text/javascript">
	function exportScore(issueId){
		window.location.href="<%=basePath %>examonline/exportScore.do?cmd=dept&&issueId="+issueId;
	} 

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
     单位
    </td>
    <td  class="label" width="16%">
      要求参考人数
    </td>
    <td class="label" width="16%">
      实际参考人数
    </td>
    <td class="label" width="16%">
      缺考人数
    </td>
    <td class="label" width="16%">
      平均成绩
    </td>
  </tr>
  <logic:iterate id="deptQueryVO" name="list" type="com.boco.eoms.examonline.qo.ExamDeptQueryVO">
     <tr  align="center">
      <td width="20%">
      	<a href="<%=basePath%>examonline/examQueryDO.do?issueId=<bean:write name="deptQueryVO" property="issueId"/>">
        	<bean:write name="deptQueryVO" property="companyName"/>
        </a>
       
      </td>
      <td width="16%">
        <bean:write name="deptQueryVO" property="testerCountAll"/>
      </td>
      <td width="16%">
        <bean:write name="deptQueryVO" property="testerCount"/>
      </td>
      <td width="16%">
        <bean:write name="deptQueryVO" property="notesterCount"/>
      </td>
      <td width="16%">
        <bean:write name="deptQueryVO" property="averagePoint"/>
      </td>
    </tr>
  </logic:iterate>
  <tr><td></td><td></td><td></td><td></td><td>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input type="button" value="导出成绩" onclick="exportScore('${issueId }')"></td></tr>
  </table>
</div>

</body>
</html:html>
