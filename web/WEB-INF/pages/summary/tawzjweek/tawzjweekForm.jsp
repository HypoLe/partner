<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.summary.model.TawzjWeekSub,com.boco.eoms.summary.model.TawzjWeek,com.boco.eoms.base.util.StaticMethod,com.boco.eoms.commons.system.user.model.TawSystemUser"%>
<script language="javascript">
 function sendsave(){
 	window.tawzjWeekForm.action="${app}/summary/tawzjweek.do?method=save&state=0"
	window.tawzjWeekForm.submit();
  }
  
 function sendedit(){
   if(window.tawzjWeekForm.auditer.value=="-1"){
    alert("请选择审核人");
    } else{
    if(confirm("确定送审？"))
    {
 	window.tawzjWeekForm.action="${app}/summary/tawzjweek.do?method=send&state=1"
	window.tawzjWeekForm.submit();
	}
	}
  }
</script>


<html:form  action="/tawzjweek?method=save" method="post" styleId="tawzjWeekForm">
	<table border="0" width="600" class="formTable">
		<caption>
			<%=(String)request.getAttribute("userid")%><%=(String)request.getAttribute("year")%>年第<%=(String)request.getAttribute("yearWeek")%>周报填写模板
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
		<%List list = (List) request.getAttribute("dataList");
		 TawzjWeek week = (TawzjWeek) request.getAttribute("tawzjWeek");
		%>
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
				<textarea cols="15" name="work0" rows="2"><%=StaticMethod.nullObject2String(sub1.getWork())%></textarea>
			</td>
			<TD rowspan='7'>
				<textarea cols="15" name="description" rows="21"><%=StaticMethod.nullObject2String(week.getDescription())%></textarea>
			</TD>
			<TD rowspan='7'>
				<textarea cols="15" name="schedule" rows="21"><%=StaticMethod.nullObject2String(week.getSchedule())%></textarea>
			</TD>
			<td rowspan='7'>
				<textarea cols="15" name="remark" rows="21"><%=StaticMethod.nullObject2String(week.getRemark())%></textarea>
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
				<textarea cols="15" name="work<%=i%>" rows="2"><%=StaticMethod.nullObject2String(sub.getWork())%></textarea>
				
			</td>
		</tr>
		<%}%>

		<tr>
			<td class="label">
				存在的问题与改进意见
			</td>
			<td colspan="6">
				<textarea cols="100" name="question" rows="3"><%=StaticMethod.nullObject2String(week.getQuestion())%></textarea>
			</td>
		</tr>
	</table>
	<br>
	<table border="0" width="100%" cellspacing="1" cellpadding="1"
		align=center>
		<tr>
		<input type="button" value="保存" name="B1" class="submit" onclick="sendsave()">
		<input type="button" value="提交" name="B1" class="submit" onclick="sendedit()">
		<select size="1" name="auditer" class="select">
		<option value="-1" selected>请选择审核人</option>
		<% List userlist =(List) request.getAttribute("checkUserList");
		if (userlist != null) {
		for(int k = 0 ; k<userlist.size();k++){
		TawSystemUser user = (TawSystemUser)userlist.get(k);
		%>
        <option value="<%=user.getUserid() %>" ><%=user.getDeptname() %>&nbsp;<%=user.getUsername()%></option>
        <% 
	 	}
	 	}
		%>
	 </select>
	</tr>
	</table>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>

