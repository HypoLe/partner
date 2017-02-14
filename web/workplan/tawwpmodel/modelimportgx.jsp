<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpStaticVariable"%>
<% String Mtypeid = (String)request.getAttribute("Mtypeid");%>


<form name="tawwpmodeladd" method="post" enctype="multipart/form-data" action="modelimportgxsave.do?reaction=/tawwpmodel/modellists.do?Mtype=<%=Mtypeid%>" >

  <table class="formTable">
    <caption><bean:message key="modelimport.title.formTitle" /></caption>
    　　　
    <tr>
      <td class="label">
        <bean:message key="modelimport.title.formModel" />
      </td>
      <td width="400">
        <input type="file" name="theFile" class="file">
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="<bean:message key="modelimport.title.btnImport" />" name="B1"  class="submit">
  <input type="button" value="<bean:message key="modelimport.title.btnBack" />" onclick="javascript:window.history.back();" class="button">
  
</form>




