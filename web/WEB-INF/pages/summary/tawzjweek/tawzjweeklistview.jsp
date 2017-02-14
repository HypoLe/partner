<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.summary.model.TawzjWeekSub,com.boco.eoms.summary.model.TawzjWeek,com.boco.eoms.base.util.StaticMethod,com.boco.eoms.commons.system.user.model.TawSystemUser,com.boco.eoms.summary.model.TawzjWeekCheck"%>
<script language="javascript">
 function sendsave(){
 	window.tawzjWeekForm.action="${app}/summary/tawzjweek.do?method=save&state=0"
	window.tawzjWeekForm.submit();
  }
  
 function sendaudit(v){
 	window.tawzjWeekForm.action="${app}/summary/tawzjweek.do?method=sendoperation&state="+v;
	window.tawzjWeekForm.submit();
  }
</script>

		<%List list = (List) request.getAttribute("dataList");
		 TawzjWeek week = (TawzjWeek) request.getAttribute("tawzjWeek");
		%>
<html:form  action="/tawzjweek?method=save" method="post" styleId="tawzjWeekForm">
	<table border="0" width="600" class="formTable">
		<caption>
			<%=week.getCruser()%><%=week.getWeekid()%>周报模板
		</caption>
		<tr>
			<td class="label">
				日期
			</td>
			<td class="label">
				星期
			</td>
			<td class="label">
				日常工作描述（工作量）
			</td>
			<td class="label">
				专项工作描述
			</td>
			<td class="label">
				专项工作进度
			</td>
			<td class="label">
				备注
			</td>
		</tr>
	
		<tr>
			<input type="hidden" name="id" value="<%=StaticMethod.nullObject2String(week.getId())%>" >
			<%TawzjWeekSub sub1 = (TawzjWeekSub) list.get(0);
			%>
			<input type="hidden" name="subid0" value="<%=StaticMethod.nullObject2String(sub1.getId())%>" >
			<td>
				<%=StaticMethod.nullObject2String(sub1.getDatetime())%>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(sub1.getWeekStr())%>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(sub1.getWork())%>
			</td>
			<TD rowspan='7'><%=StaticMethod.nullObject2String(week.getDescription())%> </TD>
			<TD rowspan='7'>
				<%=StaticMethod.nullObject2String(week.getSchedule())%>
			</TD>
			<td rowspan='7'>
				<%=StaticMethod.nullObject2String(week.getRemark())%>
			</td>
			 
		</tr>
		<%for (int i = 1; i < list.size(); i++) {
				TawzjWeekSub sub = (TawzjWeekSub) list.get(i);
				%>
		<tr>
			<input type="hidden" name="subid<%=i%>" value="<%=StaticMethod.nullObject2String(sub.getId())%>" >
			<td>
				<%=StaticMethod.nullObject2String(sub.getDatetime())%>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(sub.getWeekStr())%>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(sub.getWork())%>
				
			</td>
		</tr>
		<%}%>

		<tr>
			<td class="label">
				存在的问题与改进意见
			</td>
			<td colspan="6">
				<%=StaticMethod.nullObject2String(week.getQuestion())%>
			</td>
		</tr>
	</table>
	
	<table  border="0" width="600" class="formTable">
		<caption>
			审核状态
		</caption>
		<tr>
			<td class="label">
				送审人
			</td>
			<td class="label">
				审核人
			</td>
			<td class="label">
				审核状态
			</td>
			<td class="label">
				审核意见
			</td>
		</tr>
		<%List weekCheckList = (List) request.getAttribute("weekCheckList");
		if(weekCheckList!=null){
		for(int z=0;z<weekCheckList.size();z++){
		TawzjWeekCheck check = (TawzjWeekCheck)weekCheckList.get(z);
		%>
		<tr>
			<td>
				<%=StaticMethod.nullObject2String(check.getSender())%>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(check.getAuditer())%>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(check.getState())%>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(check.getOpinion())%>
			</td>
		</tr>
		
		<%}
		}
		%>
	</table>
	 
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>

