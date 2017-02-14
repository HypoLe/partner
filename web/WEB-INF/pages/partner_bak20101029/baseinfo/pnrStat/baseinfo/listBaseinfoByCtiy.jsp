<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.webapp.form.BaseinfoStatForm" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%
Map partnerNumMap = (Map)request.getAttribute("partnerNumMap");
Map subPartnerNumMap = (Map)request.getAttribute("subPartnerNumMap");
List statFormList = (List)request.getAttribute("statFormList");
int professionSize = StaticMethod.nullObject2int(request.getAttribute("professionSize"));

%>
<center> 
<div style="width:85%">
<table class="formTable">

	<caption>
		<div class="header center">${year}年${month}月合作伙伴数量统计</div>
	</caption>
			<tr>
				<td class="label">地市</td>
				<td class="label">合作伙伴数量</td>
				<td class="label">合作伙伴名称</td>
				<td class="label">维护专业</td>
				<td class="label">维护人员数量</td>
			</tr>
		
			<%
			String areaId = "";
			String partnerId = "";
			BaseinfoStatForm statForm = null;
			int areaRowNum = 0;
			int partnerRowNum = 0;
			for(int i=0;i<statFormList.size();i++,areaId = statForm.getAreaId(),partnerId = statForm.getPartnerId()){
				statForm = (BaseinfoStatForm)statFormList.get(i);
				areaRowNum = StaticMethod.nullObject2int(partnerNumMap.get(statForm.getAreaId()+"_num"));
				partnerRowNum = areaRowNum*professionSize;
			%>
			<tr>
			<%
			if(!areaId.equals(statForm.getAreaId())){
				
			%>
				<td rowspan="<%=partnerRowNum%>">
				<eoms:id2nameDB id="<%=statForm.getAreaId() %>" beanId="tawSystemAreaDao" />  
				   
				</td>
				<td rowspan="<%=partnerRowNum%>">
				   <%=areaRowNum %>
				</td>
			<%	
			}
			if(!areaId.equals(statForm.getAreaId())||!partnerId.equals(statForm.getPartnerId())){
			%>
				<td rowspan="<%=professionSize%>">
				   <%=statForm.getPartnerName() %>
				</td>
			<%
			}
			%>
			<td >
				   <%=statForm.getProfessionName() %>
			</td>
			
			<td >
				   <%=statForm.getUserNum() %>
			</td>
			
			</tr>
			<%
			}
			%>
</table>
</div>
</center>

<%@ include file="/common/footer_eoms.jsp"%>