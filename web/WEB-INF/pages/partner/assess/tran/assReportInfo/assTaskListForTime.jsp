<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.model.AssFlow"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.mgr.impl.TranAssFlowMgrImpl"%>
<%@page import="java.text.DecimalFormat"%>
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
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.assess.util.AssStaticMethod"%>
<c:if test="${execute=='execute'}">
	<table class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#EDF5FD;border: 1px solid #98C0F4;" >任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 未执行</td>
			</tr>
	</table>
</c:if>
<c:if test="${execute!='execute'}">
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr})-合作伙伴(<eoms:id2nameDB id="${partner}" beanId="partnerDeptDao" />)后评估执行表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="30%" rowspan="2" style="background-color:#EDF5FD;">
			<center>评估指标</center>
		</td>
		<td width="400" rowspan="2" style="background-color:#EDF5FD;">
			<center>评分规则</center>
		</td>		
		<% 
		   DecimalFormat df = new DecimalFormat("0.00");
		   List timeList = (List)request.getAttribute("timeList");
		   for(int i=0;i<timeList.size();i++){
		%>
			<td colspan="2" style="background-color:#EDF5FD;">
				<center><%=timeList.get(i) %></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<timeList.size();i++){
		%>
			<td style="background-color:#EDF5FD;">
				评分原因
			</td>
			<td style="background-color:#EDF5FD;">
				评估得分
			</td>
        <%} %>	
	</tr>
	</thead>
	<tbody>
	<tr>
			<td>
				网络基础信息
			</td>
			<td>
				网组设备量
			</td>
			<c:if test="${maxLevel==3}">
				<td>
					用于汇总后考量些关键指标评估(物理网元数)
				</td>	
				<td>
					作为参考指标量，用于故障率等指标项的基数
				</td>
			</c:if>
			<c:if test="${maxLevel==2}">
				<td>
					用于汇总后考量些关键指标评估(物理网元数)--
					作为参考指标量，用于故障率等指标项的基数
				</td>
			</c:if>			
			<%
				Map map = (Map)request.getAttribute("map");
				for(int i=0;i<timeList.size();i++){
					String DeviceNum = timeList.get(i)+"_DeviceNum";
					String value = StaticMethod.nullObject2String(map.get(DeviceNum),"0");
			%>
				<td colspan="2" >
					<span id="<%=DeviceNum %>"><%= value%></span>
				</td>
			<%
				}
			%>
	</tr>		
	
	<% 
	    
     	List tableList = (List)request.getAttribute("tableList");			 
	    for(int i=0;i<tableList.size();i++){
	%>
		<tr>
			<% 
				List rowList = (List)tableList.get(i);
			    for(int k=0;k<rowList.size();k++){
			    	AssKpiInstanceForm ekif = (AssKpiInstanceForm) rowList.get(k);
			%>	
				<%if("total".equals(ekif.getKpiType())||"text".equals(ekif.getKpiType())){ %>
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="lineAssKpiDao" />
				</td>
				<%}else {%>		
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>" style="vertical-align:middle;text-align:left">
					<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="lineAssKpiDao" />
					<%if(ekif.getWeight()!=0){ %>
					(<%=ekif.getWeight()%>分)
					<%}%>
				</td>
				<%} %>
				<%if (AssConstants.NODE_LEAF.equals(ekif.getLeaf())) { 
					if("total".equals(ekif.getKpiType())){
				%>
					<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
					</td>
					<%
					}else{
					 %>
					<td>
						<%=ekif.getRemark()%>
					</td>
					<% 
					}
					   for(int j=0;j<timeList.size();j++){
						   String time = String.valueOf(timeList.get(j));
						   String parName = AssStaticMethod.orgId2Name(time, "dept");
						   String realScore = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+time+"_sc"),"0.0");
						   String reduceReason = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+time+"_rs"));
						   String remark = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+time+"_rm"));
						   String money = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+time+"_money"),"0.0");
						   String total = StaticMethod.nullObject2String(map.get(ekif.getParentNodeId()+"_"+time+"_total"),"0.0");
						   String totalNum = StaticMethod.nullObject2String(map.get(ekif.getParentNodeId()+"_"+time+"_totalNum"),"0");
						   %>
						   <%
						   if("total".equals(ekif.getKpiType())){
					%>	
							<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<span id='<%=ekif.getNodeId() %>_<%=time %>_pay' style='text-align:center;display:none'>0<span>
							</td>
							<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<%=df.format(Double.parseDouble(String.valueOf(totalNum))) %>
							</td>
					<%
							}else if("text".equals(ekif.getKpiType())){
					 %>		
							<td colspan="3">
								<%=remark%>
							</td>
					<%
							}else{
					%>		
						<td>
							<%=reduceReason%>
						</td>
						<%
							if("money".equals(ekif.getKpiType())){
						 %>		
						<td style="vertical-align:middle;text-align:center;">
							/
						</td>
						<%
						}else{
						 %>
						 <td>
							<span style="cursor:hand" Onclick="javascript:showSelected('<%=ekif.getNodeId() %>','<%=ekif.getKpiId() %>','${requestScope.taskId}','${partner}','<%=time %>','${requestScope.areaId}');"/><%=realScore%></span>						 
						</td>
						 <%
						 }
						 %>
					<%
					}
					}%>
				
				<%} %>
			<% 
			    }
			%>	
		</tr>
			<% 
			    }
			%>		
	</tbody>
</table>
<script type="text/javascript">
function  showSelected(nodeId,kpiId,taskId,partnerId,time,areaId)
    {
      var parNum = document.getElementById(time+"_DeviceNum").innerHTML;
      selectedConfig('tran',nodeId,kpiId,taskId,partnerId,time,areaId,parNum);
    };
</script>  
<%@ include file="/common/footer_eoms.jsp"%>
</c:if>