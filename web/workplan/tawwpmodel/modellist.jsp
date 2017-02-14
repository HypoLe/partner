<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpModelPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpStaticVariable,com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>
<script type="text/javascript" src="${app}/workplan/script/tableSort.js"></script>
<% String Mtypeid = (String)request.getSession().getAttribute("Mtype");
	System.out.println(Mtypeid);
%>
<script language="javascript">
  Ext.onReady(function(){
	colorRows('list-table');
  })
  
  var trueIcon = new Image();
  trueIcon.src = "../images/true.gif";
  var falseIcon = new Image();
  falseIcon.src = "../images/false.gif";

  function load(divID){
    var targetTR;
    targetTR = eval("document.all." + divID);

    if (targetTR.style.display!="block"){
      targetTR.style.display="block";
      event.srcElement.src = trueIcon.src;
    }
    else{
      targetTR.style.display="none";
      event.srcElement.src = falseIcon.src;
    }
  }

  function onAdd()
  {
    location.href="modeladd.do";
  }
 
 function onModelImport()
  {
    location.href="modelimportgx.do?Mtypeid=<%=Mtypeid%>";
  }
  function onImport()
  {
    location.href="modelimportadd.do";
  }
  function onRemove(modelplanid){
      if (confirm("<bean:message key="modellist.title.warnRemove" />")==true){
        document.modelplanform.modelplanid.value=modelplanid;
        document.modelplanform.action="modelremove.do";
        document.modelplanform.submit();
      }
  }

</script>

<%
Hashtable hashtable = null;

if(request.getAttribute("modelhash")!=null){
	 hashtable =(Hashtable)request.getAttribute("modelhash"); //获取作业计划模版集合结构
}
Hashtable sysInfHashtable = TawwpStaticVariable.SYS_INF;
Hashtable netInfHashtable = TawwpStaticVariable.NET_INF;

Hashtable netHashtable = null;
List list = null;

TawwpModelPlanVO tawwpModelPlanVO = null;

String sysTypeId = null;
String netTypeId = null;
TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

%>

<form name="modelplanform">
<input type="hidden" name="modelplanid">

<table class="listTable" id="list-table">
  <caption><bean:message key="modellist.title.formTitle" /></caption>
  
  <thead>
  <tr>
	<td onclick="sort('list-table',0,'char')"><span style="cursor: hand"><bean:message key="modellist.title.formName" /></span></td>
    <td onclick="sort('list-table',1,'char')"><span style="cursor: hand"><bean:message key="modellist.title.formCrtime" /></span></td>
    <td onclick="sort('list-table',2,'char')"><span style="cursor: hand"><bean:message key="modellist.title.formCreater" /></span></td>
    <td><bean:message key="modellist.title.formEdit" /></td>
    <td><bean:message key="modellist.title.formRemove" /></td>
    <td><bean:message key="modellist.title.formDetail" /></td>
  </tr>
  </thead>
  <tbody>
  <%
  if(hashtable!=null&&hashtable.size() != 0){
  
	  Enumeration sysTypeEnumeration = hashtable.keys();
	  Enumeration netTypeEnumeration = null;
	  
	  while(sysTypeEnumeration.hasMoreElements()){
	  	sysTypeId = (String)sysTypeEnumeration.nextElement();
	    netHashtable = (Hashtable)hashtable.get(sysTypeId);
	    netTypeEnumeration = netHashtable.keys();
	    
	    while(netTypeEnumeration.hasMoreElements()){
	    	netTypeId = (String)netTypeEnumeration.nextElement();
	    	
	        if(netTypeId.equals(request.getSession().getAttribute("Mtype"))){//add by weiketing 080523                    
	        	list = (List)netHashtable.get(netTypeId);
	            for(int i=0; i<list.size(); i++){
	            	tawwpModelPlanVO = (TawwpModelPlanVO)list.get(i);
	  %>
					  <tr>
					    <td><%=tawwpModelPlanVO.getName()%></td>
					    <td><%=tawwpModelPlanVO.getCrtime()%></td>
					    <td><%=tawwpModelPlanVO.getCrusername()%></td>
					    <td>
					    <%if(tawwpModelPlanVO.getCruser().equals(tawSystemSessionForm.getUserid())){%>
					      <a href="modeledit.do?modelplanid=<%=tawwpModelPlanVO.getId()%>">
					        <img src="${app }/images/icons/edit.gif">
					      </a>
					      <%}%>
					    </td>
					    <td>
					     <%if(tawwpModelPlanVO.getCruser().equals(tawSystemSessionForm.getUserid())){%>
					      <a href="javascript:onRemove('<%=tawwpModelPlanVO.getId()%>')">
					        <img src="${app }/images/icons/icon.gif" width="20" height="25">
					      </a>
					         <%}%>
					    </td>
					    <td>
					      <a href="itemlist.do?modelplanid=<%=tawwpModelPlanVO.getId()%>">
					        <img src="${app }/images/icons/icon1.gif">
					      </a>
					    </td>
					  </tr>
			  <%
			    }//for end
			  }//if end by weiketing 080523
		}//while(net) end
      }//while(sys) end
  }// if end
  else{
  %>
					  <tr>
					    <td colspan="6">
					      <bean:message key="modellist.title.nolist" />
					    </td>
					  </tr>
  <%
  }
  %>

  </tbody>
</table>
<br>
	   <input type="button" value="<bean:message key="modellist.title.btnImport" />" class="button" onClick="onImport();">
	   <input type="button" value="<bean:message key="modellist.title.btnAdd" />" class="button" onClick="onAdd();">
	   <%--<input type="button" value="模板导入" class="button" onClick="onModelImport();">
    
--%></form>

<%@ include file="/common/footer_eoms.jsp"%>
 

