<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<jsp:directive.page import="com.boco.eoms.partner.baseinfo.webapp.form.OilStatByCtiyForm" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%
Map partnerNumMap = (Map)request.getAttribute("partnerNumMap");
Map subPartnerNumMap = (Map)request.getAttribute("subPartnerNumMap");
List statFormList = (List)request.getAttribute("statFormList");
int powerNum = StaticMethod.nullObject2int(request.getAttribute("powerNum"));

%>
<center> 
<div style="width:85%">
<table class="formTable">

	<caption>
		<div class="header center">${year}年${month}月合作伙伴各功率油机数量统计</div>
	</caption>
			<tr>
				<td class="label">地市</td>
				<td class="label">合作伙伴名称</td>
				<td class="label">功率</td>
				<td class="label">实配数</td>
				<td class="label">实配总数</td>
				<td class="label">应配数</td>
			</tr>
		
			<%
			String areaId = "";
			String partnerId = "";
			OilStatByCtiyForm statForm = null;
			int areaRowNum = 0;
			int partnerRowNum = 0;
			for(int i=0;i<statFormList.size();i++,areaId = statForm.getAreaId(),partnerId = statForm.getPartnerId()){
				statForm = (OilStatByCtiyForm)statFormList.get(i);
				areaRowNum = StaticMethod.nullObject2int(partnerNumMap.get(statForm.getAreaId()+"_num"));
				partnerRowNum = areaRowNum*powerNum;
			%>
			<tr>
			<%
			if(!areaId.equals(statForm.getAreaId())){
				
			%>
				<td rowspan="<%=partnerRowNum%>">
				<eoms:id2nameDB id="<%=statForm.getAreaId() %>" beanId="tawSystemAreaDao" />  
				   
				</td>
			<%	
			}
			if(!areaId.equals(statForm.getAreaId())||!partnerId.equals(statForm.getPartnerId())){
			%>
				<td rowspan="<%=powerNum%>">
				   <%=statForm.getPartnerName() %>
				</td>
			<%
			}
			%>
			<td >
				   <%=statForm.getPowName() %>
			</td>
			
			<td >
			<%
			if("无".equals(statForm.getOilNum())){
			%>
			<%=statForm.getOilNum() %>
			<%
			}else{
			%>
			<a href="${app}/partner/baseinfo/pnrStats.do?method=getOilList&partnerId=<%=statForm.getPartnerId() %>&power=<%=statForm.getPower() %>&endTime=${endTime}&areaId=<%=statForm.getAreaId() %>">
				  <%=statForm.getOilNum() %>
		    </a>
		    <%
			}
		    %>
			</td>
			<%
			if(!areaId.equals(statForm.getAreaId())||!partnerId.equals(statForm.getPartnerId())){
			%>
				<td rowspan="<%=powerNum%>">
				   <%=statForm.getOilSumNum() %>
				</td>
				
				<td rowspan="<%=powerNum%>">
				   <%=statForm.getOilConfigNum() %>
				</td>
			<%
			}
			%>
			</tr>
			<%
			}
			%>
</table>
</div>
</center>

<%@ include file="/common/footer_eoms.jsp"%>