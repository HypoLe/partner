<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.boco.eoms.partner.assess.AssReport.model.AssReportInfo"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			中国联合网络通信集团有限公司${time}
		</div>
	</caption>
	<thead>
	<tr>
		<td>
			序号
		</td>
		<td>
			汇总单项
		</td>
		<%
			List areaList = (List)request.getAttribute("areaList");
			List partnerList = (List)request.getAttribute("partnerList");
			Map map = (Map)request.getAttribute("map");
			for(int i = 0;i<partnerList.size();i++){
		%>
			<td>
				<center><eoms:id2nameDB id="<%=String.valueOf(partnerList.get(i)) %>" beanId="partnerDeptDao" /></center>
			</td>
		<%
			}
		%>
	</tr>
	<%
		for(int i = 0;i<areaList.size();i++){
	%>
		<tr>
			<td>
				<%=i+1 %>
			</td>
			<td>
				<%=map.get(areaList.get(i)) %>
			</td>
			<%
				for(int k = 0;k<partnerList.size();k++){
					AssReportInfo report = (AssReportInfo)map.get(String.valueOf(areaList.get(i))+String.valueOf(partnerList.get(k)));
			%>
				<td>
					<center><%=report.getTotalScore() %></center>
				</td>
			<%		
				}
			%>			
		</tr>
	<%		
		}
	%>
</table>
<%@ include file="/common/footer_eoms.jsp"%>
