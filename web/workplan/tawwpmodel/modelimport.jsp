<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpStaticVariable"%>

<script language="javascript">

function SubmitCheck(){

  frmReg = document.tawwpmodeladd;

  if(frmReg.systype.value == '0'){
    alert("<bean:message key="modelimport.title.warnSelectSys" />");
    return false;
  }

  if(frmReg.nettype.value == '0'){
    alert("<bean:message key="modelimport.title.warnSelectNet" />");
    return false;
  }

  //if( !JustifyNull1(frmReg.theFile))
  if(frmReg.theFile=='')
  {
    alert( "<bean:message key="modelimport.title.warnInputName" />" );
    frmReg.theFile.focus();
    return false;
  }

  return true;
}




<!--
var onecount;
onecount=0;

subcat = new Array();
<%
Hashtable hashtable = TawwpStaticVariable.SYS_NET_INF;
Enumeration sysEnumeration = null;
List netList = null;

String[] sysType = null;
String[] netType = null;
String netID = "";
String netName = "";

int count = 0;

sysEnumeration = hashtable.keys();
while(sysEnumeration.hasMoreElements()){
  sysType = (String[])sysEnumeration.nextElement();
  netList = (List)hashtable.get(sysType);
  for(int i=0; i<netList.size(); i++){
    netType = (String[])netList.get(i);
%>
subcat[<%=count++%>] = new Array("<%=netType[0]%>","<%=sysType[1]%>","<%=netType[1]%>");
<%
  }
}
%>

onecount=<%=count%>;

function changelocation(locationid){
  document.tawwpmodeladd.nettype.length = 0;
  var locationid=locationid;
  var i;

  document.tawwpmodeladd.nettype.options[0] = new Option('<bean:message key="modelimport.title.selNetType" />','0');

  for (i=0;i < onecount; i++){
    if (subcat[i][1] == locationid){
      document.tawwpmodeladd.nettype.options[document.tawwpmodeladd.nettype.length] = new Option(subcat[i][0], subcat[i][2]);
    }
  }
}

//-->

</script>

<form name="tawwpmodeladd" method="post" enctype="multipart/form-data" action="modelimport.do?reaction=/tawwpmodel/modellists.do" onsubmit='return SubmitCheck()'>

  <table class="formTable">
    <caption><bean:message key="modelimport.title.formTitle" /></caption>
    <tr>
      <td class="label">
        <bean:message key="modelimport.title.formSysType" />
      </td>
      <td width="400">
        <select size=1 name="systype" class="select" onChange="changelocation(document.tawwpmodeladd.systype.options[document.tawwpmodeladd.systype.selectedIndex].value)">
          <option value="0" selected><bean:message key="modelimport.title.selSysType" /></option>
          <%
          sysEnumeration = hashtable.keys();
          while(sysEnumeration.hasMoreElements()){
            sysType = (String[])sysEnumeration.nextElement();
          %>
          <option value="<%=sysType[1]%>"><%=sysType[0]%></option>
          <%
          }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td class="label">
        <bean:message key="modelimport.title.formNetType" />
      </td>
      <td width="400">
        <select size="1" name="nettype" class="select">
          <option value="0" selected><bean:message key="modelimport.title.selNetType" /></option>
        </select>
      </td>
    </tr>
     <tr>
      <td class="label">
        <bean:message key="modeladd.title.formType" />
      </td>
      <td>
       <eoms:dict key="dict-workplan" dictId="typeIndex" isQuery="false" defaultId="" selectId="typeIndex" beanId="selectXML"/>
				 
      </td>
    </tr>
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




