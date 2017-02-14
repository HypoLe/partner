<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="com.boco.eoms.partner.eva.model.PnrEvaReportInfo"/>
<jsp:directive.page import="com.boco.eoms.partner.eva.webapp.form.PnrEvaReportYearStaticFrom"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.List"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
String[] temtitles = (String[])request.getAttribute("temtitles");
System.out.println(temtitles);
List scoreList = (List)request.getAttribute("scoreList");
Map rowMap = (Map)request.getAttribute("rowMap");

%>
<table class="listTable" id="list-table">
	<caption>
		<div class="header center">考核月报表管理视图</div>
	</caption>
<thead>
	<tr>
		<td>
			维护区域
		</td>
		<td>
			服务公司
		</td>
		<%
		for(int i=0;i<temtitles.length;i++){
		%>
		<td>
			<%=temtitles[i] %>
		</td>
		<%
		}
		%>
	</tr>
	</thead>
	<tbody>
	<%
	    String area ="";
		for(int i=0;i<scoreList.size();i++){
			String[] score = (String[])scoreList.get(i);
		%>
		<tr>
		<%
		if(!area.equals(score[0])){
			area = score[0];
		%>
		
		<td rowSpan="<%=StaticMethod.nullObject2String(rowMap.get(score[0]+"_row")) %>">
			<eoms:id2nameDB id="<%=score[0] %>" beanId="tawSystemAreaDao" />
			
		</td>
		<%
		}
		%>
		<td>
			<eoms:id2nameDB id="<%=score[1] %>" beanId="areaDeptTreeDao" />
		</td>
		<%
		for(int j=2;j<score.length;j++){
		%>
		<td>
			<%=StaticMethod.nullObject2String(score[j],"未执行") %>
		</td>
		<%
		}
		%>
		</tr>
		<%
		}
		if(scoreList.size()==0){
			scoreList = (List)request.getAttribute("listReports");
			PnrEvaReportInfo reportInfo = null;
			for(int i=0;i<scoreList.size();i++){
				reportInfo = (PnrEvaReportInfo)scoreList.get(i);
		%>
		<tr>
		<%
		if(!area.equals(reportInfo.getArea())){
			area = reportInfo.getArea();
		%>
		<td rowSpan="<%=StaticMethod.nullObject2String(rowMap.get(area+"_row")) %>">
			<eoms:id2nameDB id="<%=reportInfo.getArea() %>" beanId="tawSystemAreaDao" />
		</td>
		<%
		}
		%>
		<td>
			<eoms:id2nameDB id="<%=reportInfo.getPartnerId() %>" beanId="areaDeptTreeDao" />
		</td>
		<td>
			<%=reportInfo.getTotalScore() %>
		</td>
		</tr>
		<%
			}
	
		%>
		
		<%
		}
		%>
	</tbody>
</table>
<%@ include file="/common/footer_eoms.jsp"%>