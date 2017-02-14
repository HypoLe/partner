<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<script language="JavaScript"
	src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<div><!-- 图表呈现域 -->
<table align="center">
	<tr id="chartArea">
		<%
			//String doc = (String) request.getAttribute("doc");
			//System.out.print(doc);
		%>
		<td><!-- javascript 嵌入 -->
		<div id="chartdiv" align="center"></div>
		<script type="text/javascript">
	    	        // function(swf, id, w, h, debugMode, registerWithJS, c, scaleMode, lang, detectFlashVersion, autoInstallRedirect)
	                var myChart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/${presentStyle}", "myChartId",document.body.clientWidth*0.8, document.body.clientHeight*0.8);
					myChart.setDataXML("${doc}");
	                myChart.render("chartdiv");	       
               </script></td>
	</tr>
</table>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
