<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import ="com.boco.eoms.base.util.StaticMethod"%>
<%
   	  String text = "workplan-stat.xls";
  	  response.setHeader("Content-Disposition: ","attachment; filename="+text);
	  String  yearFlag = StaticMethod.null2String((String)request.getAttribute("yearflag"));
      String  monthFlag = StaticMethod.null2String((String)request.getAttribute("monthflag")); 
      String  type = StaticMethod.null2String((String)request.getAttribute("type")); 
      String str =  (String)request.getAttribute("str");
%>
 <script language='javascript'>
 function onMonth()
 {
 document.statyear.submit();
 }
 function down()
{
  location.href="downexcel.do?type=<%=type%>&yearflag=<%=yearFlag%>&monthflag=<%=monthFlag%>";
}
 </script>
 <table class="formTable">
 
 <tr >
 <td rowspan='2' style="{background-color:#EDF5FD;}">年</td>
 <td rowspan='2' style="{background-color:#EDF5FD;}">月</td><td rowspan='2'style="{background-color:#EDF5FD;}">作业计划</td>
 <td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labDay" />
		</td>
		<td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labWeek" />
		</td>
		<td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labHalfMonth" />
		</td>
		<td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labMonth" />
		</td>
		<td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labQuarterYear" />
		</td>
		<td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labHalfYear" />
		</td>
		<td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labYear" />
		</td>
		<td colspan='5' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labTemp" />
		</td>
		<td colspan='3' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCount" />
		</td>
		<td rowspan='2' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeRate" />
		</td>
		<td rowspan='2' style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishRate" />
		</td>
		<%--<td rowspan='2' style="{background-color:#EDF5FD;}"><bean:message key="statreportall.title.labConstituteCount" /></td>
 <td rowspan='2' style="{background-color:#EDF5FD;}"><bean:message key="statreportall.title.labScore" /></td>
 --%>
	</tr>
	<tr>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			非正常
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labAllCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labInTimeCounts" />
		</td>
		<td style="{background-color:#EDF5FD;}">
			<bean:message key="statreportall.title.labFinishCounts" />
		</td>
 </tr>
	<%=str%>
 </table >
 <br>
 
 
 <%@ include file="/common/footer_eoms.jsp"%>