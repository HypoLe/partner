<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants"/>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.eva.util.DateTimeUtil"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaTree"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaKpiInstance"%>
<%@ page language="java" import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page language="java" import="com.boco.eoms.eva.util.EvaConstants"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<c:if test="${view==null||view==''}">
<table class="formTable" id="form">
	<caption>
		<div class="header center">
			模板名称(${template.templateName})-合作伙伴(<eoms:id2nameDB id="${template.partnerDept}" beanId="tawSystemDeptDao"/>)-周期(<eoms:dict key="dict-eva" dictId="cycle" itemId="${template.cycle}" beanId="id2nameXML" />) 考核执行情况列表
		</div>
	</caption>

  <%
  Map kpiMap = (Map)request.getAttribute("kpiMap");
  List allCycleList = (List)request.getAttribute("allCycleList");
  List kpiNodeList = (List)request.getAttribute("kpiNodeList");
  %>

	<tr>
		<td class="label">
			指标名称
		</td>
		<%
		String timeStrName = "";
		EvaTree kpiTree = new EvaTree();
		String kpiStr = "";
		for(int i=1;i<=allCycleList.size();i++){
			timeStrName = "第"+String.valueOf(i)+"次";
		%>
		<td class="label">
			<%=timeStrName %>
		</td>
		<%
		}
		%>
	</tr>
	
	<%
	for(int j=0;j<kpiNodeList.size();j++){
		kpiTree = (EvaTree)kpiNodeList.get(j);
	%>
	<tr>
	<td>
		<eoms:id2nameDB id="<%=kpiTree.getNodeId() %>" beanId="evaTreeDao" />
	</td>
	<%
	for(int i=1;i<=allCycleList.size();i++){
		kpiStr = StaticMethod.nullObject2String(kpiMap.get(String.valueOf(i)+"_"+kpiTree.getNodeId()));
	%>
	<td>
	<%=kpiStr %>
	</td>
	<%
	}
	%>
	</tr>
	<%
	}
	%>
	<tr>
	<td>
	总分
	</td>
	<%
	for(int i=1;i<=allCycleList.size();i++){
		kpiStr = StaticMethod.nullObject2String(kpiMap.get(String.valueOf(i)));
	%>
	<td>
	<%=kpiStr %>
	</td>
	<%
	}
	%>
	</tr>
	</table>
	<c:if test="${template.activated=='EvaConstants.TEMPLATE_CLOSED'}">
		<table id="submit-btn" align="left">
			<tr>
				<td>
					<input type="button" class="btn" value="执行" onclick="excuteEva();"/>
				</td>
			</tr>
		</table>
	</c:if>
</c:if>
<c:if test="${view=='view'}">
<script language="JavaScript" src="${app}/FusionCharts/FusionCharts.js"></script>
<div id="chartdiv" align="center">图形报表</div>
<script type="text/javascript">
   var myChart = new FusionCharts("${app}/FusionCharts/Column3D.swf", "myChartId", "100%", "300", "0", "0");
   myChart.setDataXML("${strXML}");
   myChart.render("chartdiv");
</script>
</c:if>
<script type="text/javascript">
function excuteEva() {
		var url=eoms.appPath+'/eva/evaReportInfos.do?method=getTaskForTemplate&nodeId=${nodeId }';
		window.location=eoms.appPath+'/eva/evaReportInfos.do?method=getTaskForTemplate&nodeId=${nodeId }';
}
</script>
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${template.agreementId}" target="_blank">
		<eoms:id2nameDB id="${template.agreementId}" beanId="pnrAgreementMainDao" />
		</a>
<%@ include file="/common/footer_eoms.jsp"%>