<%@ page contentType="text/html; charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html>
<head>
<title><bean:message key="label.remove"/>&nbsp;<bean:write name="tawInfInfoForm" property="infSortName" scope="request"/></title>
<html:base/>

</head>

<html:form action="/TawInfAppu/deldone" method="post">
<body>
<br>
  <table border="0" width="100%" cellspacing="0" align="center">
	<tr>
      <td width="100%" align="center" class="table_title">
        <b>
          <bean:message key="label.remove"/>&nbsp;<bean:write name="tawInfInfoForm" property="infSortName" scope="request"/>
        </b>
      </td>
    </tr>
  </table>
  <html:hidden name="tawInfInfoForm" property="id"/>
  <html:hidden name="tawInfInfoForm" property="deptId"/>
  <table border="0" width="100%" cellspacing="1" cellpadding="1" class="table_show" align="center">
    <tr class="tr_show">
      <td width="30%" height="25" class="clsfth">&nbsp;
        &nbsp<bean:message key="TawInfAppu.InfInfoId"/>
	  </td>
	  <td width="70%" height="25">
		&nbsp<bean:write name="tawInfInfoForm" property="infInfoId" scope="request"/>
	  </td>
	</tr>
    <tr class="tr_show">
      <td width="30%" height="25" class="clsfth">&nbsp;
        &nbsp<bean:message key="TawInfAppu.InfInfoName"/>
	  </td>
	  <td width="70%" height="25">
		&nbsp<bean:write name="tawInfInfoForm" property="infInfoName" scope="request"/>
	  </td>
	</tr>
	<tr class="tr_show">
      <td width="30%" height="25" class="clsfth">&nbsp;
        &nbsp<bean:message key="TawInfAppu.DeptName"/>
	  </td>
	  <td width="70%" height="25">
		&nbsp<bean:write name="tawInfInfoForm" property="deptName" scope="request"/>
	  </td>
	</tr>
	<tr class="tr_show">
      <td width="30%" height="25" class="clsfth">&nbsp;
        &nbsp<bean:message key="TawInfAppu.InfUpName"/>
	  </td>
	  <td width="70%" height="25">
		&nbsp<bean:write name="tawInfInfoForm" property="infUpName" scope="request"/>
	  </td>
	</tr>
	<tr class="tr_show">
      <td width="30%" height="25" class="clsfth">&nbsp;
        &nbsp<bean:message key="TawInfAppu.InfUpTime"/>
	  </td>
	  <td width="70%" height="25">
		&nbsp<bean:write name="tawInfInfoForm" property="infUpTime" scope="request"/>
	  </td>
	</tr>
	<tr class="tr_show">
      <td width="30%" height="25" class="clsfth">&nbsp;
        &nbsp<bean:message key="TawInfAppu.InfUpfileName"/>
	  </td>
	  		<td height="50" width="480" colspan=3>
<%
String att=(String)request.getAttribute("attValue");
String shortName=(String)request.getAttribute("attText");
if(att != null)
{
  String attArr[]=att.split(",",0);
  String shortArr[]=shortName.split(",",0);
  if (!shortName.equals(""))
  {
       for (int i=0;i<attArr.length;i++)
       {
        out.println("<a href='../../upload/" + attArr[i] + "'>" + shortArr[i] + "</a><br>");
%>
<!--     <a href="<%=request.getContextPath()%>/worksheet/download.jsp?name=<%=java.net.URLEncoder.encode(attArr[i])%>"><%=shortArr[i]%></a> -->
<%
       }
  }
  else
  {
    out.print("&nbsp;");
  }
}
else
{
out.print("&nbsp;");
}
%>
	</tr>
  </table>
  <table border="0" width="100%" cellspacing="1" align="center">
     <tr>
	    <td width="100%" height="32" align="right">
          <input type="button" value="<bean:message key="label.remove"/>" onclick="toSubmit()" class="clsbtn2"/>
          <input type="button" value="<bean:message key="label.cancel"/>" onclick="history.back()" class="clsbtn2"/>
		</td>
    </tr>
  </table>
</body>
</html:form>
</html:html>

<script language="javascript">
  function toSubmit()
  {
    window.document.tawInfInfoForm.submit();
  }
</script>


