<%@ page contentType="text/html; charset=gb2312"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jstl-core.tld" prefix="c" %>
<html:form method="post" action="/TawRmAssignwork/save">
<html:hidden property="strutsAction"/>

  <table bgcolor="#666666" cellpadding="1" cellspacing="0" border="0" width="500">
<tr>
<td>
<table cellpadding="0" cellspacing="0" border="0" width="500">
<tr>
<td bgcolor="#fecc51">&nbsp;</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table cellpadding="0" cellspacing="0" border="0" width="500">
  <tr>
<td bgcolor="#d6e0ed">

    <c:choose>
      <c:when test="${requestScope['tawRmAssignworkForm'].strutsAction == 1}">
        <bean:message key="label.add"/>
      </c:when>
      <c:otherwise>
        <bean:message key="label.edit"/>
      </c:otherwise>
    </c:choose>
    &nbsp;TawRmAssignwork

  </td>
</tr>
<tr bgcolor="#FFFFFF">
<td width="5%"></td><td width="19%"></td><td width="76%"></td>
</tr>
  <tr>
<td>
<table bgcolor="#f2f2f2" width="500" cellspacing="0" border="0">
    <tr bgcolor="#FFFFFF">
<td width="5%"></td><td width="19%"></td><td width="76%"></td>
</tr>
<tr bgcolor="#FFFFFF">
<td width="5%"></td><td width="19%"></td><td width="76%"></td>
</tr>
    <c:choose>
      <c:when test="${requestScope['tawRmAssignworkForm'].strutsAction == 1}">
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.id"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="id" size="8"/></font></td>
</tr>
    </c:when>
    <c:otherwise>
    <html:hidden property="id"/>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.id"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><bean:write name="tawRmAssignworkForm" property="id"/></font></td>
</tr>
    </c:otherwise></c:choose>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.regionId"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="regionId" size="8"/></font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.roomId"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="roomId" size="8"/></font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.dutydate"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="dutydate" size="30"/>(yyyy-mm-dd hh:mm:ss.fffffffff)</font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.workid"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="workid" size="8"/></font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.flag"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="flag" size="8"/></font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.dutymaster"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="dutymaster" size="255"/></font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.starttimeDefined"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="starttimeDefined" size="30"/>(yyyy-mm-dd hh:mm:ss.fffffffff)</font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.endtimeDefined"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="endtimeDefined" size="30"/>(yyyy-mm-dd hh:mm:ss.fffffffff)</font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.smsflag"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="smsflag" size="8"/></font></td>
</tr>
    <tr>
<td width="5%"></td><td width="19%"><font color="#022077" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b><bean:message key="TawRmAssignwork.notes"/></b></font></td><td width="76%"><font size="2" face="Tahoma"><html:text property="notes" size="255"/></font></td>
</tr>
    <tr bgcolor="#FFFFFF">
<td width="5%"></td><td width="19%"></td><td width="76%">

              <logic:messagesPresent>
                <bean:message key="errors.header"/>
                <ul>
                  <html:messages id="error">
                    <li>
                      <bean:write name="error"/>
                    </li>
                  </html:messages>
                </ul>
                <hr/>
              </logic:messagesPresent>

    </td>
</tr>
  </table>
</td>
</tr>
  <tr>
<td>
<table bgcolor="#f2f2f2" height="30" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr align="right" valign="middle">
<td>

      <html:submit>
        <bean:message key="label.save"/>
      </html:submit>
      <html:cancel>
        <bean:message key="label.cancel"/>
      </html:cancel>

  </td>
</tr>
</table>
</td>
</tr>
  </table>
</td>
</tr>
</table>

</html:form>


