<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.assess.util.AssStaticMethod"%>
  <!-- EXT LIBS verson 3.0 -->
  <script type="text/javascript" src="${app}/scripts/ext3/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext3/ext-all.js"></script>
  <script type="text/javascript" src="${app}/scripts/ext3/source/locale/ext-lang-zh_CN.js"></script>
  <script type="text/javascript" src="${app}/scripts/partner/select.js"></script>
  <link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/ext-all.css" />
  <c:if test="${theme ne 'default'}"><link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/xtheme-gray.css" /></c:if>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/ext-adpter.css" />
  
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
  <%@ include file="/common/body.jsp"%>
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})指标详情
		</div>
	</caption>
	<thead>
	<tr>
		<td width="30%" rowspan="2">
			<center>评估指标</center>
		</td>
		<td width="400" rowspan="2">
			<center>评分规则</center>
		</td>		
		<% 
		   Map map = (Map)request.getAttribute("map");
		   List areaList = (List)request.getAttribute("areaList");
		   for(int i=0;i<areaList.size();i++){
		%>
			<td colspan="2">
				<center><eoms:id2nameDB id="<%=String.valueOf(areaList.get(i)) %>" beanId="tawSystemAreaDao" /></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<areaList.size();i++){
		%>
			<td >
				评分原因
			</td>		
			<td >
				评估得分
			</td>
        <%} %>	
	</tr>
	</thead>
	<tbody>
		<tr>
			<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
				<eoms:id2nameDB id="<%=String.valueOf(map.get("kpiId")) %>" beanId="tranAssKpiDao" />
				(${weight}分)
			</td>		
			<td>
				<%=map.get("remark")%>
			</td>	
			<%
				for(int k=0;k<areaList.size();k++){
			%>		
				<td>
					<%=map.get(areaList.get(k)+"_rr")%>
				</td>			
				<td>
					<span style="cursor:hand" Onclick="javascript:showSelected('${treeNodeId}','${kpiId}','${taskId}','${partnerId}','${time }','${requestScope.areaId}','<%=map.get(areaList.get(k)+"_DeviceNum")%>');"/><%=map.get(areaList.get(k))%></span>				
				</td>			
			<%
				}
			%>
		</tr>
	</tbody>
</table>
<script type="text/javascript">
function  showSelected(nodeId,kpiId,taskId,partnerId,time,areaId,parNum)
    {
      selectedConfig('tran',nodeId,kpiId,taskId,partnerId,time,areaId,parNum);
    };
</script> 
<%@ include file="/common/footer_eoms.jsp"%>
