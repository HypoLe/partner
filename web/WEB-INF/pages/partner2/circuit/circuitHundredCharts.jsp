<%@ page language="java" pageEncoding="UTF-8"%>

<!-- 图表呈现域 -->
<script language="JavaScript"
	src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<table align="center">

	<%
		//对xml字符串进行处理
		String doc = (String) request.getAttribute("doc");
		System.out.println(doc);

		if (doc != null) {
			doc = doc.replace("\"", "\'");
			int i = doc.indexOf("<graph");
			doc = doc.substring(i);
		}
		System.out.println(doc);
	%>
	<tr>
		<td><!-- javascript 嵌入 -->
		<div id="chartdiv" align="center"></div>
		<script type="text/javascript">
	    	        // function(swf, id, w, h, debugMode, registerWithJS, c, scaleMode, lang, detectFlashVersion, autoInstallRedirect)
	                var myChart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/${presentStyle}", "myChartId",document.body.clientWidth*0.8, document.body.clientHeight*0.8);
					myChart.setDataXML("<%=doc%>");
	                myChart.render("chartdiv");	       
               </script></td>
	</tr>
</table>
