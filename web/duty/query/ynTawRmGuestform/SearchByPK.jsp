<%@ include file="../../../common/taglibs.jsp"%>
<%@ include file="../../../common/header_eoms_form.jsp"%>
<%@ page import="java.util.*,java.lang.*, org.apache.struts.util.*"%>
<%
Vector SelectRoom = (Vector)request.getAttribute("SelectRoom");
Vector SelectRoomName = (Vector)request.getAttribute("SelectRoomName");
%>
<head>
<title> </title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

</head>
<body leftmargin="0" topmargin="0" class="clssclbar">
<html:form method="post" action="/TawRmGuestform/search">
<br>
<table cellpadding="0" cellspacing="0" border="0" width="500" align="center">
<tr>
<td align="center" class="table_title"><bean:message key="label.query"/><bean:message key="TawRmGuestform.tablename"/></td>
</tr>
</table>

<table border="0"  cellspacing="1" cellpadding="1" class="formTable" width="500" align=center>
<tr class="tr_show">
<td class="clsfth">
     <bean:message key="TawRmSysteminfo.select_room"/>
</td>
<td>
               <select name="roomId">
               <option value='0'>${eoms:a2u('－－全部机房－－')}</option>
               <%
               for(int i=0;i<SelectRoom.size();i++) {
               %>
               
               <option value='<%=SelectRoom.get(i).toString()%>'><%=SelectRoomName.get(i).toString()%></option>
               <%}%>
               </select>
</td>
</tr>
<tr class="tr_show">
<td class="clsfth">
    <bean:message key="TawRmGuestform.starttime"/>
</td>
<td>
    <eoms:SelectDate name="starttime" formName="tawRmGuestformForm"/>
</td>
</tr>
<tr class="tr_show">
<td class="clsfth">
    <bean:message key="TawRmGuestform.endtime"/>
</td>
<td>
    <eoms:SelectDate name="endtime" formName="tawRmGuestformForm"/>
</td>
</tr>
<tr class="tr_show">
<td class="clsfth">
    <bean:message key="TawRmGuestform.flag"/>
</td>
<td>
        <html:select property="flag" size="1">
        <html:option value="0"><bean:message key="TawRmGuestform.nosubmit"/></html:option>
        <html:option value="1"><bean:message key="TawRmGuestform.submit"/></html:option>
        <html:option value="-1"><bean:message key="TawRmGuestform.nolimit"/></html:option>
      </html:select>
</td>
</tr>
  </table>

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

<table cellpadding="0" cellspacing="0" border="0" width="500" align="center">
  <tr>
<td align="right" height="32">
      <html:submit property="strutsButton" styleClass="clsbtn2">
         ${eoms:a2u('查询记录')}
      </html:submit>
       <html:submit property="strutsexport" styleClass="clsbtn2">
         ${eoms:a2u('导出excel')}
      </html:submit>
  </td>
</tr>
</table>
</html:form>
</body>

