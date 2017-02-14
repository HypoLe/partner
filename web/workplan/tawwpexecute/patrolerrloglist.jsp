<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>

 

<table width="500" class="listTable" id="list" >
  <caption>
      智能巡检错误日志
 </caption>

<%
List list = (List)request.getAttribute("detail");
%>
  <thead>
  <tr class="td_label">
    <td width="100">
      部门
    </td>
    <td width="400">
      文件名
    </td>
  </tr>
  </thead>
  <tbody>
  <%
  if(list.size() > 0){
  %>
  <tr class="tr_show">
    <td width="100">
      <%=list.get(0)%>
    </td>
    <td width="400">
      <a href='../tawwpexecute/downfileznxj.jsp?filepath=../tawwpfile/attmentfile/<%=list.get(1)%>&filename=<%=list.get(1)%>'><%=list.get(1)%></a>
      <a href='../tawwpexecute/downfileznxj.jsp?filepath=../tawwpfile/attmentfile/<%=list.get(1)%>&filename=<%=list.get(1)%>'><%=list.get(1)%></a>
    </td>
  </tr>
  <%
  }
  else{
  %>
  <tr class="td_label">
    <td width="500" colspan="3">
      没有任何日志信息
      <a href='../tawwpexecute/downfileznxj.jsp?filepath=../tawwpfile/attmentfile/<%=list.get(1)%>&filename=<%=list.get(1)%>'><%=list.get(1)%></a>
    </td>
  </tr>
  <%
  }
  %> </tbody>
  </table>
<%@ include file="/common/footer_eoms.jsp"%>


