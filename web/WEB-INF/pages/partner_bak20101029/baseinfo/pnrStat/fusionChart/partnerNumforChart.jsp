<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.lang.Object" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod" />
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script language="JavaScript" src="${app}/FusionCharts/FusionCharts.js"></script>
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >选择地市:
					<select name="area" id="area" onchange="changeRegion();">								
						<option value="">
							--请选择地市--
						</option>
						<option value="all">
							全部
						</option>							
						<logic:iterate id="regionBuffer" name="regionBuffer">
							<option value="${regionBuffer.areaid}">
								${regionBuffer.areaname}
							</option>
						</logic:iterate></td>
					</select>	
			</tr>
		</table>
<div id="chartdiv" align="center">图形报表</div>
<script type="text/javascript">
	var myChart = new FusionCharts("${app}/FusionCharts/Pie3D.swf", "myChartId", "100%", "300", "0", "0");
	myChart.setDataXML("${strXML}");
	myChart.render("chartdiv");
	//地市、县区联动合作伙伴		
	function changeRegion(){
			var areaValue = document.getElementById("area").value; 
			var url=eoms.appPath+'/partner/baseinfo/pnrStats.do?method=getPartnerNumforChart&area='+areaValue;
			location.href=url;
	} 		
	var city = '${city}';	     
	if(city!=''){
		document.getElementById("area").value = city;
	}
</script>

<%@ include file="/common/footer_eoms.jsp"%>