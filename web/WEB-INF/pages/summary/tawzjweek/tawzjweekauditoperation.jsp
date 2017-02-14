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
 	if(v=='2'){
 	
 	if(window.tawzjWeekForm.auditer.value=="-1"){
    alert("请选择审核人");
    } else{
    if(confirm("确定送审？"))
    {
 		window.tawzjWeekForm.action="${app}/summary/tawzjweek.do?method=auditSave&state="+v;
	window.tawzjWeekForm.submit();
	}
	}
 	}else{
 	
 	window.tawzjWeekForm.action="${app}/summary/tawzjweek.do?method=auditSave&state="+v;
	window.tawzjWeekForm.submit();
	}
  }
</script>
 <%
 	String state = (String)request.getAttribute("state");
 	String stateStr = (String)request.getAttribute("stateStr");
 	String id = (String)request.getAttribute("id");
 %>
<html:form  action="/tawzjweek?method=save" method="post" styleId="tawzjWeekForm">
	<table border="0" width="600" class="formTable">
		<caption>
			周报模板审核<%=state%>
		</caption>
		<input type="hidden" name="id" value='<%=id%>'>
		<tr>
			<td class="label" width="15%">
				<%=stateStr%>
			</td>
			<td width="85%">
				 <textarea cols="85" name="operation" rows="4"><%=id%></textarea>
			</td>
		</tr>
			 
		
	</table>
	<br>
	<!-- 如果是新提交审核或者是被上级领导驳回的。要重新审核-->
	 <%if("3".equals(state)){%>
		<input type="button" value="驳回" name="B1" class="submit" onclick="sendaudit(3)">
	<%}%>
	 <%if("2".equals(state) || "6".equals(state)){%><!-- 审核通过提交下一级审核人-->
		
		<select size="1" name="auditer" class="select">
		<option value="-1" selected>请选择下一级审核人</option>
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
	 <input type="button" value="提交审核" name="B1" class="submit" onclick="sendaudit(2)">
	<%}else %>
	 <%if("4".equals(state)){%>
		<input type="button" value="归档" name="B1" class="submit" onclick="sendaudit(4)">
	<%}else%>
	<%if("5".equals(state)){%>
		<input type="button" value="驳回到派发人" name="B1" class="submit" onclick="sendaudit(5)">
		<input type="button" value="驳回到审核人" name="B1" class="submit" onclick="sendaudit(6)">
	<%}%>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>

