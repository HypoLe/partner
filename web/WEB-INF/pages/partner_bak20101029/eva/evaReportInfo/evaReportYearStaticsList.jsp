<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="com.boco.eoms.partner.eva.webapp.form.PnrEvaReportYearStaticFrom"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.List"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<table class="listTable" id="list-table">
	<caption>
		<div class="header center">考核年报表管理视图—${title}</div>
	</caption>
<thead>
	<tr>
		<td rowSpan="2">
			服务公司
		</td>
		<td rowSpan="2">
			维护区域
		</td>
		<c:if test="${startYear == endYear}">
		<td colspan="12" align="center">
			${startYear}年
		</td>
		<td rowSpan="2" colspan="2" align="center">
			平均分
		</td>
		<tr>
		<td>1月</td>
		<td>2月</td>
		<td>3月</td>
		<td>4月</td>
		<td>5月</td>
		<td>6月</td>
		<td>7月</td>
		<td>8月</td>
		<td>9月</td>
		<td>10月</td>
		<td>11月</td>
		<td>12月</td>
		</tr>
		</c:if>
		<c:if test="${startYear != endYear}">
		<td colspan="6" align="center">
			${startYear}年
		</td>
		<td colspan="6" align="center">
			${endYear}年
		</td>
		<td rowSpan="2" colspan="2" align="center">
			平均分
		</td>
		<tr>
		<td>7月</td>
		<td>8月</td>
		<td>9月</td>
		<td>10月</td>
		<td>11月</td>
		<td>12月</td>
		<td>1月</td>
		<td>2月</td>
		<td>3月</td>
		<td>4月</td>
		<td>5月</td>
		<td>6月</td>
		</tr>
		</c:if>

		
	</tr>
	</thead>
	<tbody>
	<tr>
	<%
	Map rowMap = (Map)request.getAttribute("rowMap");
	List formList = (List)request.getAttribute("formList");
	String partner = "";
	int rowNum;
	float partnerScore;
	for(int i=0;i<formList.size();i++){
		PnrEvaReportYearStaticFrom staticFrom = (PnrEvaReportYearStaticFrom)formList.get(i);
	%>
	<tr>
	<%
	if(!partner.equals(staticFrom.getPartnerId())){
		rowNum = StaticMethod.nullObject2int(rowMap.get(staticFrom.getPartnerId()+"_num"));
	%>
		<td  rowSpan="<%=rowNum %>">
			<eoms:id2nameDB id="<%=staticFrom.getPartnerId() %>" beanId="areaDeptTreeDao" />
		</td>
	<%
	}
	%>
	
		<td>
		<%
		if("-1".equals(staticFrom.getArea())){
		%>
	累计均分
		<%
		}else{
		%>
		<eoms:id2nameDB id="<%=staticFrom.getArea()%>" beanId="tawSystemAreaDao" /> 
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthA()!=0){
		%>
			<%=staticFrom.getMonthA()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthB()!=0){
		%>
			<%=staticFrom.getMonthB()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthC()!=0){
		%>
			<%=staticFrom.getMonthC()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthD()!=0){
		%>
			<%=staticFrom.getMonthD()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthE()!=0){
		%>
			<%=staticFrom.getMonthE()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthF()!=0){
		%>
			<%=staticFrom.getMonthF()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthG()!=0){
		%>
			<%=staticFrom.getMonthG()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthH()!=0){
		%>
			<%=staticFrom.getMonthH()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthI()!=0){
		%>
			<%=staticFrom.getMonthI()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthJ()!=0){
		%>
			<%=staticFrom.getMonthJ()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthK()!=0){
		%>
			<%=staticFrom.getMonthK()%>
		<%
		}
		%>
		</td>
		<td>
		<%
		if(staticFrom.getMonthL()!=0){
		%>
			<%=staticFrom.getMonthL()%>
		<%
		}
		%>
		</td>
		<td>
		<%=staticFrom.getTotalScore()%>
		</td>
	<%
	if(!partner.equals(staticFrom.getPartnerId())){
		partner = staticFrom.getPartnerId();
		rowNum = StaticMethod.nullObject2int(rowMap.get(partner+"_num"));
		
		partnerScore = Float.parseFloat(StaticMethod.nullObject2String(rowMap.get(partner+"_score")));
	%>
		<td  rowSpan="<%=rowNum %>">
			<%=partnerScore %>
		</td>
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