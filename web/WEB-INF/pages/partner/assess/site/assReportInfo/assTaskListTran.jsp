<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.model.AssFlow"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.mgr.impl.LineAssFlowMgrImpl"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.assess.util.AssStaticMethod"%>
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})单项汇总表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="30%" rowspan="2" style="background-color:#EDF5FD;">
			<center>评估指标</center>
		</td>
		<td width="400" rowspan="2" style="background-color:#EDF5FD;">
			<center>评分规则</center>
		</td>		
		<% 
		   List partnerList = (List)request.getAttribute("partnerList");
		   for(int i=0;i<partnerList.size();i++){
			   String parName = AssStaticMethod.orgId2Name(String.valueOf(partnerList.get(i)), "dept");
		%>
			<td style="background-color:#EDF5FD;">
				<center><eoms:id2nameDB id="<%=String.valueOf(partnerList.get(i)) %>" beanId="partnerDeptDao" /></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<partnerList.size();i++){
		%>
			<td style="background-color:#EDF5FD;">
				评估得分
			</td>
        <%} %>	
	</tr>
	</thead>
	<tbody>
	<% 
	    Map map = (Map)request.getAttribute("map");
     	List tableList = (List)request.getAttribute("tableList");			 
	    for(int i=0;i<tableList.size();i++){
	%>
		<tr>
			<% 
				List rowList = (List)tableList.get(i);
			    for(int k=0;k<rowList.size();k++){
			    	AssKpiInstanceForm ekif = (AssKpiInstanceForm) rowList.get(k);
			%>	
				<%if("total".equals(ekif.getKpiType())){ %>
					<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="siteAssKpiDao" />
					</td>
				<%}else {%>		
					<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>" style="vertical-align:middle;text-align:left">
						<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="siteAssKpiDao" />
						<%if(ekif.getWeight()!=0){ %>
						(<%=ekif.getWeight()%>分)
						<%}%>						
					</td>
				<%} %>
				<%if (AssConstants.NODE_LEAF.equals(ekif.getLeaf())) { 
					if("total".equals(ekif.getKpiType())){
				%>
						<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						</td>
					<%
					}else{
					 %>
					<td>
						<%=ekif.getAlgorithm()%>
					</td>
					<% 
					}				
					   for(int j=0;j<partnerList.size();j++){
						   String parIds = String.valueOf(partnerList.get(j));
						   String parName = AssStaticMethod.orgId2Name(parIds, "dept");
						   String realScore = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_totalNum"),"0.0");
						   String remark = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rm"));
						   String totalNum = StaticMethod.nullObject2String(map.get(parIds+"_total"),"0");
						   if("total".equals(ekif.getKpiType())){
					%>	
							<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<%=totalNum %>
							</td>
					<%
							}else if("text".equals(ekif.getKpiType())){
					 %>		
							<td colspan="3">
								<%=remark%>
							</td>
					<%
							}else{
							if("money".equals(ekif.getKpiType())){
							 %>		
							<td style="vertical-align:middle;text-align:center;">
								/
							</td>
							<%
							}else{
							 %>
							 <td>
								<a href="${app}/partner/assess/siteAssReportInfo.do?method=nodeQuarter&taskId=${taskId}&kpiId=<%=ekif.getKpiId()%>&year=${year}&quarter=${quarter}&month=${month}&timeType=${timeType}&partnerId=<%=parIds%>&kpiInstanceId=<%=ekif.getId() %>" target='_blank'>
								<%=realScore%></a>
							 </td>
							 <%
							 }
							 %>
						<%
						}
					}%>
				
				<%} %>
			<% 
			    }
			%>	
		</tr>
			<% 
			    }
			%>		
	</tbody>
</table>
<%@ include file="/common/footer_eoms.jsp"%>
