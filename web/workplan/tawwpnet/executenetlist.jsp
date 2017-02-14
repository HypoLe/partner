<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.boco.eoms.workplan.model.TawwpNet"%>

<script language="javascript">

Ext.onReady(function(){
	colorRows('list-table');
})

  function onNetAdd()
  {
    location.href="netadd.do";
  }

  function onRemove(netid){
    if (confirm("<bean:message key="netlist.title.ifDelete" />")==true){
      location.href="netremove.do?netid=" + netid;
    }
  }

</script>



<%
TawwpNet tawwpNetVO = null;
List list = (List)request.getAttribute("netList");
System.out.println("list::"+list.size());
%>

<table width="100%" class="listTable" id="list-table">
  <caption>网元</caption>
  <thead>

  <tr>
    <td width="120">
      <bean:message key="netlist.title.formName" />
    </td>
    <td width="120">
      执行情况
    </td>


 
  </tr>
  </thead>
  <tbody>
  <%
  if(list.size() > 0){
    for(int i=0; i<list.size(); i++){
      tawwpNetVO = (TawwpNet)list.get(i);
  %>
  <tr>
    <td width="120">
   
      <%=tawwpNetVO.getName()%>
    </td>
  
   
    <td width="60">
    
    </td>

  </tr>
  <%
    }
  }
  else{
  %>
  <tr>
    <td width="700" colspan="8">
      <bean:message key="netlist.title.nolist" />
    </td>
  </tr>
  <%
  }
  %>
  </tbody>
</table>

<%@ include file="/common/footer_eoms.jsp"%>
