<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.lang.Object" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

</br>
   <script language="JavaScript" src="${app}/FusionCharts/FusionCharts.js"></script>
   <div id="chartdiv" align="center">图形报表</div>
   <script type="text/javascript">
      var myChart = new FusionCharts("${app}/FusionCharts/Column3D.swf", "myChartId", "100%", "300", "0", "0");
      myChart.setDataXML("${strXML}");
      myChart.render("chartdiv");
   </script>

<%@ include file="/common/footer_eoms.jsp"%>