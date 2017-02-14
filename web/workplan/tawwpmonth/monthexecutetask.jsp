<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthExecuteVO"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import ="com.boco.eoms.commons.util.xml.XmlManage,com.boco.eoms.workplan.util.Inspection"%>

 
<%  
  List monthExecuteVOList = (ArrayList)request.getAttribute("monthexecutevolist");
  TawwpMonthExecuteVO tawwpMonthExecuteVO = null;
  String monthPlanId = (String)request.getAttribute("monthplanid");
  //added by lijia 2005-11-28
  String month = (String)request.getAttribute("month");
  String year = (String)request.getAttribute("year");
  String currTime = (String)request.getAttribute("currtime");
  String switchCheck = StaticMethod.null2String(XmlManage.getFile("/com/boco/eoms/workplan/config/WorkplanService.xml").getProperty("switchQualityCheck")); 
%>
<script language="JavaScript">
function GoBack()
{
  var monthPlanId = "<%=monthPlanId%>";
  location.href="../tawwpmonth/monthview.do?monthplanid=" + monthPlanId;
}
</script>
<form name="tawwpmonthexecuteform" method="post" action="../tawwpmonth/monthexecutetasksave.do">
<!--   body begin   -->
<INPUT TYPE="hidden" name="switch" id="switch" value="<%=switchCheck%>">

<br>

<table class="listTable" id="listTable">
  <caption><bean:message key="monthexecuteedit.title.formTitle" /></caption>
  <thead>
  <tr>
    
    <td width="100" nowrap>
      作业项目
    </td>
    
    <td width="100" nowrap>
	  业务类型
	</td>
	<td width="100" nowrap>
	  执行单位级别
	</td>
	<td width="100" nowrap>
	  适用说明
	</td>
	<td width="10" nowrap>
	  原任务ID
	</td>
    <td width="100" nowrap>
	  备选任务
	</td>
  </tr>
  </thead>
  <tbody>
  <%
    List list = (List)request.getAttribute("commonList");
    for(int i=1; i<(monthExecuteVOList.size()+1); i++){
      tawwpMonthExecuteVO = (TawwpMonthExecuteVO)(monthExecuteVOList.get(i-1));
  %>

  <tr>
     
    <td rowspan="1" nowrap>
      <%=tawwpMonthExecuteVO.getName()%>
    </td>
    <td rowspan="1" nowrap>
      <%=tawwpMonthExecuteVO.getBotype() %>
    </td>
    <td rowspan="1" nowrap>
      <%=tawwpMonthExecuteVO.getExecutedeptlevel() %>
    </td>
    <td rowspan="1" nowrap>
      <%=tawwpMonthExecuteVO.getAppdesc() %>
    </td>
    <td rowspan="1" nowrap>
       <%=tawwpMonthExecuteVO.getCommand() %>
    </td>
    <td rowspan="1" nowrap>
       <SELECT name="taskid<%=i%>">
        <OPTION value="0"> --请选择--</OPTION>
    	<%for(int k=0;k<list.size();k++){
    	  Inspection inspectionTask =(Inspection)list.get(k);
    	  
    	  if(StaticMethod.null2String(tawwpMonthExecuteVO.getCommand()).equals(inspectionTask.getTaskID())){
    	%>
    	<OPTION value="<%=inspectionTask.getTaskID()+"#"+inspectionTask.getTaskName()%>" selected >【<%=inspectionTask.getTaskID()%>】<%=inspectionTask.getTaskName()%></OPTION>
    	<%}else{%>
 		<OPTION value="<%=inspectionTask.getTaskID()+"#"+inspectionTask.getTaskName()%>">【<%=inspectionTask.getTaskID()%>】<%=inspectionTask.getTaskName()%></OPTION>
 		<%}}%>
     </SELECT>
     
    </td>
  </tr>
 
  <input type ="hidden" name="size" value="<%=monthExecuteVOList.size()+1%>" >      
  <INPUT TYPE="hidden" name="monthExecute<%=i%>" id="monthExecute<%=i%>" value="<%=tawwpMonthExecuteVO.getId()%>" label="<%=tawwpMonthExecuteVO.getName()%>">
 
  <%
    }
  %>
  </tbody>
  <tr>
    <td width="20%" colspan="6">
      <!--added by lijia 2005-11-28-->
      <INPUT type="hidden" value="<%=month%>" name="month">
      <INPUT type="hidden" value="<%=year%>" name="year">
      <INPUT type="hidden" value="<%=currTime%>" name="currtime">
      <!-- end -->
      <INPUT type="hidden" value="<%=monthExecuteVOList.size()%>" name="count">
      <INPUT type="hidden" value="<%=monthPlanId%>" name="monthplanid">
	  <INPUT type="submit" value="<bean:message key="monthexecuteedit.title.btnSubmit" />" name="submit" Class="submit">
	  <input type="button" value="<bean:message key="monthexecuteedit.title.btnBack" />" name="B1" class="button" onclick="javascript:GoBack();">
    </td>
  </tr>
</table>
 
</form>
<%@ include file="/common/footer_eoms.jsp"%>



