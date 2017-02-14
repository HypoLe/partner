<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpExecuteContentVO"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>

<%
  List list = (List)request.getAttribute("list");
  String allQuality = StaticMethod.nullObject2String(request.getAttribute("allQuality"));
  TawwpExecuteContentVO tawwpExecuteContentVO = null;
  
%>

<!--  body begin  -->

<form name="monthplanform" >

  <table width="700" class="listTable" id="list-table">
    <caption>质检列表</caption>
    <thead>
    <tr>
      <td width="10%">
        作业计划名称
      </td>
      <td width="10%">
        所属网元
      </td>
      <td width="10%">
        执行项名称
      </td>
      <td width="10%">
        执行人
      </td>
      <td width="10%">
        质检人
      </td>
      <td width="10%">
        质检状态
      </td>
      <td width="10%">
        查看
      </td>
    </tr>
    </thead>
    <tbody>
    <%
      for(int i=0; i<list.size(); i++){
        tawwpExecuteContentVO = (TawwpExecuteContentVO)list.get(i);
        
    %>
    <tr>
      <td width="10%">
        <%=tawwpExecuteContentVO.getMonthPlanNname()%>
      </td>
      <td width="10%">
        <%=tawwpExecuteContentVO.getNetNname()%>
      </td>
      <td width="10%">
        <%=tawwpExecuteContentVO.getName()%>
      </td>
      <td width="10%">
      <%=tawwpExecuteContentVO.getUserName()%>
      </td>
      <td width="10%">
      <%=tawwpExecuteContentVO.getQualityCheckUserName()%>
      </td>
      <td width="10%">
      <%=tawwpExecuteContentVO.getQualityCheckFlagName()%>
      </td>
      <td width="10%">
        <%if(tawwpExecuteContentVO.getQualityCheckFlag().equals("0")){%>
        <a href="../tawwpexecute/qualityview.do?id=<%=tawwpExecuteContentVO.getId()%>&allQuality=<%=allQuality%>">
         <img src="../images/bottom/an_pz.gif" width="19" height="18" align="absmiddle">
        </a>
        <%}else{%>
        <a href="../tawwpexecute/queryqualityview.do?id=<%=tawwpExecuteContentVO.getId()%>">
         <img src="../images/bottom/an_pz.gif" width="19" height="18" align="absmiddle">
        </a>
        <%}%>
      </td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
  <INPUT type="hidden" id="ids" name="ids" value="">
  <INPUT type="hidden" id="allQuality" name="allQuality" value="<%=allQuality%>">
</form>

<!--  body end  -->

<%@ include file="/common/footer_eoms.jsp"%>

