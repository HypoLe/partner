<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<% String nodeID = (String)request.getParameter("nodeId"); %>
<% System.out.print(nodeID); %>
<a href="${app}/partner/baseinfo/pnrStats.do?method=getNumUsers&nodeId=<% out.print(nodeID); %>">人力信息</a>
<a href="${app}/partner/baseinfo/pnrStats.do?method=getNumTawApparatuss&nodeId=<% out.print(nodeID); %>">仪器仪表</a>
<a href="${app}/partner/baseinfo/pnrStats.do?method=tawPartnerCars&nodeId=<% out.print(nodeID); %>">车辆管理</a>
<a href="${app}/partner/baseinfo/pnrStats.do?method=tawPartnerOils&nodeId=<% out.print(nodeID); %>">油机管理</a>
</br>
   <script language="JavaScript" src="${app}/FusionCharts/FusionCharts.js"></script>
   <div id="chartdiv" align="center">图形报表</div>
   <script type="text/javascript">
      var myChart = new FusionCharts("${app}/FusionCharts/Pie3D.swf", "myChartId", "100%", "300", "0", "0");
      myChart.setDataXML("${strXML}");
      myChart.render("chartdiv");
   </script>

<%@ include file="/common/footer_eoms.jsp"%>