<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpExecuteCountVO"%>
<%@ page import ="com.boco.eoms.base.util.StaticMethod"%>
<%
  Hashtable monthPlanVOHash = (Hashtable)request.getAttribute("count");
  Enumeration hashKeys = null;
  TawwpExecuteCountVO tawwpExecuteCountVO = null;
  TawwpMonthPlanVO tawwpMonthPlanVO = null;
  String monthPlanId = null;
%>
  <table class="listTable">
    <tr>
      <td>
    您今天未执行的作业计划  &nbsp;<a href='viewdailyexecute.do'>执行所有作业计划</a>
      </td>
    </tr>
  </table>

  <table class="listTable">
    <%
      hashKeys = monthPlanVOHash.keys();
      while (hashKeys.hasMoreElements()){
        monthPlanId = (String)(hashKeys.nextElement());
        tawwpExecuteCountVO = (TawwpExecuteCountVO)monthPlanVOHash.get(monthPlanId);
        tawwpMonthPlanVO = tawwpExecuteCountVO.getTawwpMonthPlanVO();
      
      if(tawwpExecuteCountVO.getDutyExecute()!=1){%>
 
        <tr>
          <td>
          <a href="<%=request.getContextPath() %>/workplan/tawwpexecute/notimplementedexecuteadds.do?netName=<%=tawwpMonthPlanVO.getNetName()%>&monthplanid=<%=tawwpMonthPlanVO.getId()%>&flag=daily">
           
          <%=tawwpMonthPlanVO.getName()%>（<%=tawwpMonthPlanVO.getNetName()%>有<%=tawwpExecuteCountVO.getDayExecute()%>个作业项目未执行）
          
          </td>
        </tr>
    <%
    }else{
    %>
        <tr>
          <td>
         <a href="<%=request.getContextPath() %>/workplan/tawwpexecute/notimplementedexecuteadds.do?netName=<%=tawwpMonthPlanVO.getNetName()%>&monthplanid=<%=tawwpMonthPlanVO.getId()%>&flag=duty">
           
          <%=tawwpMonthPlanVO.getName()%>（<%=tawwpMonthPlanVO.getNetName()%><%=tawwpExecuteCountVO.getDayExecute()%>个作业项目未执行）
          
          </td>
        </tr>
   <%}
      }
    %>
  </table>