<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*"%>
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})指标详情
		</div>
	</caption>
	<thead>
	<tr>
		<td width="30%" rowspan="2">
			<center>评估指标</center>
		</td>
		<td width="400" rowspan="2">
			<center>评分规则</center>
		</td>		
		<% 
		   Map map = (Map)request.getAttribute("map");
		   List areaList = (List)request.getAttribute("areaList");
		   for(int i=0;i<areaList.size();i++){
		%>
			<td colspan="2">
				<center><eoms:id2nameDB id="<%=String.valueOf(areaList.get(i)) %>" beanId="tawSystemAreaDao" /></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<areaList.size();i++){
		%>
			<td >
				评分原因
			</td>		
			<td >
				评估得分
			</td>
        <%} %>	
	</tr>
	</thead>
	<tbody>
		<tr>
			<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
				<eoms:id2nameDB id="<%=String.valueOf(map.get("kpiId")) %>" beanId="siteAssKpiDao" />
				(<%=map.get("weight")%>分)
			</td>		
			<td>
				<%=map.get("algorithm")%>
			</td>	
			<%
				for(int k=0;k<areaList.size();k++){
			%>		
				<td>
					<%=map.get(areaList.get(k)+"_rr")%>
				</td>			
				<td>
					<%=map.get(areaList.get(k))%>
				</td>			
			<%
				}
			%>
		</tr>
	</tbody>
</table>
<%@ include file="/common/footer_eoms.jsp"%>
