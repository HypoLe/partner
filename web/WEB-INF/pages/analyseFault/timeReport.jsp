<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<script language="JavaScript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<html:form action="analyseFault.do?method=timeReport${reportID}" method="post">
<div id="tableContainer" style="border:1px solid #98c0f4;padding:5px;width:98%;">
	<table id="sheet" class="formTable">
	<tr>
		<td class="label">统计年份</td>
			<td class="content" colspan=4>
			<select name="year">
 			<logic-el:present name="sltYearList">
				<c:forEach items="${sltYearList}" var="option">
				<option value="${option}">${option}</option>
				</c:forEach>
			</logic-el:present>
			</select>

			</td>

		</tr>
		
		<tr>
			<td class="label">开始月份</td>
			<td class="content">
				<select name="startMonth">
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="03">03</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option>
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				</select>
			</td>
			<td class="label">结束月份</td>
			<td class="content">
				<select name="endMonth">
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="03">03</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option>
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12" selected>12</option>
				</select>
			</td>
		</tr>
		<tr>
		<td class=""><input type="submit" value="统计"/></td>
		</tr>
	</table><input type="hidden" value="sss"/>
</div>
<div id="tableStatisticsContainer" style="border:1px solid #98c0f4;padding:5px;width:98%;">
	<table id="sheet" class="formTable">
	<% List list = (List)request.getAttribute("reportList");%>
	<%if(list!=null){
	for(int i=0;i<list.size();i++){%>
	<tr>
	<%for(int j=0;j<((List)list.get(i)).size();j++){%>
	<%if(i==0){%>
		<td class="label" style="background-color: #98c0f4"><%=((List)list.get(i)).get(j)%></td>
	<%}else{%>
		<td class="content">
		<%=((List)list.get(i)).get(j)%>
		</td>
	<%}%>
	<%}%>
	</tr>
	<%}}%>

	</table>
</div>
<div style="border:1px solid #98c0f4;width:98%;padding:5px;">
	<div id="chartContainer" style="margin-left: auto;margin-right: auto;padding-left:200px">FusionCharts will load here!</div> 
	<logic-el:present name="textList">
	<div style="border:1px solid #98c0f4;width:98%;padding:5px;">
	<c:forEach  items="${textList}" var="text">
		 	<li>${text}</li><br><br>
	</c:forEach>
	</div>
	</logic-el:present> 
</div>
<script type="text/javascript">
      var myChart = new FusionCharts( "<%=request.getContextPath()%>/FusionCharts/MSColumn3D.swf", 
      "myChartId", "480", "370", "0", "1" );
      myChart.setDataXML("${doc}");
      myChart.render("chartContainer");
</script>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>