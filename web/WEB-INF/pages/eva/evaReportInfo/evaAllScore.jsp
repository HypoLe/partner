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
<style type="text/css">
.formTableNew {
	BORDER-COLLAPSE: collapse; FONT-SIZE: 12px
}
.formTableNew TD {
	BORDER-BOTTOM: #c9defa 1px solid; BORDER-LEFT: #c9defa 1px solid; PADDING-BOTTOM: 6px; BACKGROUND-COLOR: #ffffff; PADDING-LEFT: 6px; PADDING-RIGHT: 6px; BORDER-TOP: #c9defa 1px solid; BORDER-RIGHT: #c9defa 1px solid; PADDING-TOP: 6px
}
.formTableNew TR.header TD {
	BACKGROUND: #cae8ea; COLOR: #006699
}
TD.labelNew {
	BACKGROUND-COLOR: #edf5fd; VERTICAL-ALIGN: top
}
TD.content {
	VERTICAL-ALIGN: top
}

TD.checkColumn {
	WIDTH: 15px
}
.basicdlg {
	POSITION: absolute; VISIBILITY: hidden; TOP: 0px
}
</style>
<c:if test="${view==null||view==''}">
<div>
<table class="formTableNew" id="form">
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
		<td class="labelNew">
			指标名称
		</td>
		<%
		String timeStrName = "";
		EvaTree kpiTree = new EvaTree();
		String kpiStr = "";
		for(int i=1;i<=allCycleList.size();i++){
			timeStrName = "第"+String.valueOf(i)+"次";
		%>
		<td class="labelNew">
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
</div>	
	<c:if test="${template.activated=='EvaConstants.TEMPLATE_CLOSED'}">
		<table id="submit-btn" align="left">
			<tr>
				<td>
					<input type="button" class="btn" value="执行" onclick="excuteEva();"/>
				</td>
			</tr>
		</table>
	</c:if>
<script type="text/javascript">
setTableWidth();
function setTableWidth() {
		var cellCount = <%= allCycleList.size() %>;
		if(cellCount > 6){
		    //每屏按6列显示
			var cellSize = screen.width / 6;
			form.width = cellSize * 90 / 100 * cellCount;
		}else{
		    //占屏幕宽度的95%
			form.width = screen.width * 95 / 100;
		}
}
</script>
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