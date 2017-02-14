<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.model.AssFlow"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.mgr.impl.LineAssFlowMgrImpl"%>
<%@page import="java.text.DecimalFormat"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
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
			<eoms:id2nameDB id="${requestScope.areaId}" beanId="tawSystemAreaDao" />-任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 后评估执行表
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
		   List partnerList = (List)request.getAttribute("partnerList");
		   for(int i=0;i<partnerList.size();i++){
			   String parName = AssStaticMethod.orgId2Name(String.valueOf(partnerList.get(i)), "dept");
		%>
			<td colspan="4" style="background-color:#EDF5FD;">
				<center><eoms:id2nameDB id="<%=String.valueOf(partnerList.get(i)) %>" beanId="partnerDeptDao" /></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<partnerList.size();i++){
		%>
			<td style="background-color:#EDF5FD;">
				评分原因
			</td>
			<td style="background-color:#EDF5FD;">
				评估得分
			</td>
			<td style="background-color:#EDF5FD;">
				偏差扣分
			</td>	
			<td  style="background-color:#EDF5FD;text-align:center;">
				采集查询
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
				for(int j=0;j<partnerList.size();j++){
					String parNumName = partnerList.get(j)+"_par";
					String value = StaticMethod.nullObject2String(map.get(parNumName),"0");
			%>
				<td colspan="4"  style="text-align:center;">
					<span id="<%=parNumName %>"><%= value%></span>
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
			    	String kpiId = StaticMethod.nullObject2String(map.get(ekif.getId()));
			    	
			%>	
				<%if("total".equals(ekif.getKpiType())){ %>
					<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
						<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="tranAssKpiDao" />
					</td>
				<%}else {%>		
					<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>" style="vertical-align:middle;text-align:left">
						<eoms:id2nameDB id="<%=ekif.getKpiId()%>" beanId="tranAssKpiDao" />
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
					   for(int j=0;j<partnerList.size();j++){
						   String parIds = String.valueOf(partnerList.get(j));
						   String parName = AssStaticMethod.orgId2Name(parIds, "dept");
						   String realScore = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_sc"),"0.0");
						   String reduceReason = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rs"));
						   String remark = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rm"));
						   String money = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_money"),"0.0");
						   String total = StaticMethod.nullObject2String(map.get(ekif.getParentNodeId()+"_"+parIds+"_total"),"0.0");
						   String totalNum = StaticMethod.nullObject2String(map.get(ekif.getParentNodeId()+"_"+parIds+"_totalNum"),"0");
						   double totalWeight = Double.valueOf("100")-Double.valueOf(totalNum);
						   double weight = Double.valueOf(ekif.getWeight())-Double.valueOf(realScore);
						   %>
						   <%
						   if("total".equals(ekif.getKpiType())){
					%>	
							<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<span id='<%=ekif.getNodeId() %>_<%=parIds %>_pay' style='text-align:center;display:none'>0<span>
							</td>
							<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<%=df.format(Double.valueOf(totalNum)) %>
							</td>
							<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<%=df.format(totalWeight) %>
							</td>
							<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
							</td>														
					<%
							}else if("text".equals(ekif.getKpiType())){
					 %>		
							<td colspan="3">
							
								<%=remark%>
							</td>
							<td  style="text-align:center;">
								<%
									if(!"".equals(kpiId)){
								%>		
									<a target='_blank' href="${app}/partner/assess/assAutoCollections.do?method=searchCollectionResult&partner=<%=parIds %>&time=${requestScope.time}&cycle=${requestScope.timeType}&kpiId=<%=kpiId %>&area=${requestScope.areaId}">采集结果</a>
								<% 
									} else {
								%>
									非采集
								<%
									}
								%>
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
						<td  colspan="2" style="vertical-align:middle;text-align:center;">
							/
						</td>
						<%
						}else{
						 %>
						<td  style="text-align:center;">
						 	<span style="cursor:hand" Onclick="javascript:showSelected('<%=ekif.getNodeId() %>','<%=ekif.getKpiId() %>','${requestScope.taskId}','<%=parIds %>','${requestScope.time}','${requestScope.areaId}');"/><%=df.format(Double.valueOf(realScore)) %></span>
						</td>
						<td  style="text-align:center;">
							<%=df.format(weight) %>
						</td>
						 <%
						 }
						 %>
						 <td  style="text-align:center;">
						<%
							if(!"".equals(kpiId)){
						%>		
							<a target='_blank' href="${app}/partner/assess/assAutoCollections.do?method=searchCollectionResult&partner=<%=parIds %>&time=${requestScope.time}&cycle=${requestScope.timeType}&kpiId=<%=kpiId %>&area=${requestScope.areaId}">采集结果</a>
						<% 
							} else {
						%>
							非采集
						<%
							}
						%>
					</td>
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
</c:if>
<script type="text/javascript">
function  showSelected(nodeId,kpiId,taskId,partnerId,time,areaId)
    {
      var parNum = document.getElementById(partnerId+"_par").innerHTML;
      selectedConfig('tran',nodeId,kpiId,taskId,partnerId,time,areaId,parNum);
    };
</script>    
<%@ include file="/common/footer_eoms.jsp"%>
