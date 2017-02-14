<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.webapp.form.OilStatByStateForm" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%
List statFormList = (List)request.getAttribute("statFormList");
int kindSize = StaticMethod.nullObject2int(request.getAttribute("kindSize"));
int stateSize = StaticMethod.nullObject2int(request.getAttribute("stateSize"));

%>
<center> 
<div style="width:85%">
<table class="formTable">

	<caption>
		<div class="header center">${year}年${month}月全省各合作伙伴各状态油机数量统计</div>
	</caption>
			<tr>
				<td class="label">合作伙伴名称</td>
				<td class="label">油机性质</td>
				<td class="label">运行状态</td>
				<td class="label">数量</td>
			</tr>
		
			<%
			String partnerId = "";
			String kind = "";
			OilStatByStateForm statForm = null;
			
			for(int i=0;i<statFormList.size();i++,kind = statForm.getKind(),partnerId = statForm.getPartnerId()){
				statForm = (OilStatByStateForm)statFormList.get(i);
			%>
			<tr>
			<%
			if(!partnerId.equals(statForm.getPartnerId())){
				
			%>
				<td rowspan="<%=kindSize%>">
				<%=statForm.getPartnerName() %>
				   
				</td>
			<%	
			}
			if(!kind.equals(statForm.getKind())||!partnerId.equals(statForm.getPartnerId())){
			%>
				<td rowspan="<%=stateSize%>">
				   <eoms:id2nameDB id="<%=statForm.getKind() %>" beanId="IItawSystemDictTypeDao" />
				</td>
			<%
			}
			%>
			<td >
				   <eoms:id2nameDB id="<%=statForm.getState() %>" beanId="IItawSystemDictTypeDao" />
			</td>
			
			<td >
			<%=statForm.getOilNum() %>
			</td>
			
			</tr>
			<%
			}
			%>
</table>
</div>
</center>

<%@ include file="/common/footer_eoms.jsp"%>