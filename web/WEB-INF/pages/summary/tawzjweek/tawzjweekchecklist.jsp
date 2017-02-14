<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.summary.model.TawzjWeekSub,com.boco.eoms.summary.model.TawzjWeek,com.boco.eoms.base.util.StaticMethod,com.boco.eoms.commons.system.user.model.TawSystemUser"%>

<html:form  action="/tawzjweek?method=save" method="post" styleId="tawzjWeekForm">
	<table border="0" width="600" class="formTable">
		<caption>
			周总结待审批
		</caption>
		<tr>
			<td class="label">
				名称
			</td>
			<td class="label">
				审核状态
			</td>
		</tr>
		<%List weekCheckList = (List) request.getAttribute("checkList");
		if(weekCheckList!=null){
		for(int z=0;z<weekCheckList.size();z++){
		TawzjWeek check = (TawzjWeek)weekCheckList.get(z);
		%>
		<tr>
			<td>
				<a href="${app}/summary/tawzjweek.do?method=audit&id=<%=check.getId()%>"><%=StaticMethod.nullObject2String(check.getName())%></a>
			</td>
			<td>
				<%=StaticMethod.nullObject2String(check.getState())%>
			</td>
			 
		</tr>
		
		<%}
		}
		%>
	</table>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
