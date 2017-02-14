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
		window.location.href="<%=basePath %>examonline/exportScore.do?cmd=company&&issueId="+issueId;
	} 
</script>
</head>

<%

TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm)
    request.getSession().getAttribute("sessionform");
   String userId = saveSessionBeanForm.getUserid();
   String userNames = saveSessionBeanForm.getUsername();
   String studyexami="";//(String)request.getAttribute("studyexami");
   int o=0;
%>
<%
List QOlist=(List)request.getAttribute("QOlist");
%>
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
      用户名
    </td>
    <td  class="label" width="16%">
      总分
    </td>
    <td class="label" width="16%">
      正确数
    </td>
    <td class="label" width="16%">
      总题数
    </td>
    <td class="label" width="16%">
      正确率
    </td>
    <td class="label" width="16%">
      显示明细
    </td>
  </tr>



<%if (studyexami.equals("")){%>

  <logic:iterate id="studyQO" name="QOlist" type="com.boco.eoms.examonline.qo.studyQO">
     <tr  align="center">
      <td width="20%">
        <bean:write name="studyQO" property="userName"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="mark"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="right"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="total"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="rate"/>%
      </td>
      <td width="16%">
        <%if (!"study".equalsIgnoreCase(studyQO.getIssueId())
             || userId.equalsIgnoreCase(studyQO.getUserId())){
            java.util.HashMap map = new java.util.HashMap();
            map.put( "userId" , studyQO.getUserId() );
            map.put( "issueId" , studyQO.getIssueId() );
            pageContext.setAttribute( "map" , map );
        %>
          <html:link href="detail.do" name="map">
            <img src="<%=request.getContextPath()%>/images/bottom/an_xs.gif" border="0">
          </html:link>
        <%}%>
      </td>
    </tr>
  </logic:iterate>

  <%}
       else if(studyexami.equals("1")&&userId.equals("admin")){%>
 
  <logic:iterate id="studyQO" name="QOlist" type="com.boco.eoms.examonline.qo.studyQO">
  <bean:define id="userName" name="studyQO" property="userName" type="java.lang.String"/>

    <tr align="center">
      <td width="20%">
        <bean:write name="studyQO" property="userName"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="mark"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="right"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="total"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="rate"/>%
      </td>
      <td width="16%">
        <%if ( !"study".equalsIgnoreCase( studyQO.getIssueId() )
             || userId.equalsIgnoreCase( studyQO.getUserId() ) ){
            java.util.HashMap map = new java.util.HashMap();
            map.put( "userId" , studyQO.getUserId() );
            map.put( "issueId" , studyQO.getIssueId() );
            pageContext.setAttribute( "map" , map );
        %>
          <html:link href="detail.do" name="map">
            <img src="<%=request.getContextPath()%>/images/bottom/an_xs.gif" border="0">
          </html:link>
        <%}%>
      </td>
    </tr>
  </logic:iterate>

       <%}else if(studyexami.equals("1")&&!userId.equals("admin")){%>
         
  <logic:iterate id="studyQO" name="QOlist" type="com.boco.eoms.examonline.qo.studyQO">
  <bean:define id="userName" name="studyQO" property="userName" type="java.lang.String"/>
 <%
 if(userNames.equals(userName)){%>
    <tr align="center">
      <td width="20%">
        <bean:write name="studyQO" property="userName"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="mark"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="right"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="total"/>
      </td>
      <td width="16%">
        <bean:write name="studyQO" property="rate"/>%
      </td>
      <td width="16%">
        <%if ( !"study".equalsIgnoreCase( studyQO.getIssueId() )
             || userId.equalsIgnoreCase( studyQO.getUserId() ) ){
            java.util.HashMap map = new java.util.HashMap();
            map.put( "userId" , studyQO.getUserId() );
            map.put( "issueId" , studyQO.getIssueId() );
            pageContext.setAttribute( "map" , map );
        %>
          <html:link href="detail.do" name="map">
            <img src="<%=request.getContextPath()%>/images/bottom/an_xs.gif" border="0">
          </html:link>
        <%}%>
      </td>
    </tr>
    <%o++;
    }%>
  </logic:iterate>
 <% if(o==0){%>
 <tr>没有你的考试结果</tr>
  <%
  }
  o=0;%>
  </table>
       <%}%>
       
</div>
<tr><td></td><td></td><td></td><td></td><td></td><td>&emsp;&emsp;&emsp;&emsp;<input id="exportScore" type="button" value="导出成绩" onclick="exportScore('${issueId }')"></td></tr>

</body>
</html:html>
