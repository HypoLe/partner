<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.model.AssFlow"%>
<%@ page import="com.boco.eoms.partner.assess.AssFlow.mgr.impl.LineAssFlowMgrImpl"%>
<%@page import="java.text.DecimalFormat"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
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
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('main-info', '评估信息 ');
        	tabs.addTab('step-info', '步骤列表 ');
    		tabs.activate(0);
	});	
</script>
<div id="info-page">
	<div id="main-info" class="tabContent">
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			<eoms:id2nameDB id="${requestScope.areaId}" beanId="tawSystemAreaDao" />-任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 后评估执行表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="30%" rowspan="2" style="background-color:#EDF5FD;">
			<center>后评估指标</center>
		</td>
		<% 
		   DecimalFormat df = new DecimalFormat("0.00");
		   List partnerList = (List)request.getAttribute("partnerList");
		   for(int i=0;i<partnerList.size();i++){
			   String parName = AssStaticMethod.orgId2Name(String.valueOf(partnerList.get(i)), "dept");
		%>
			<td colspan="3" style="background-color:#EDF5FD;">
				<center><eoms:id2nameDB id="<%=String.valueOf(partnerList.get(i)) %>" beanId="partnerDeptDao" /></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<partnerList.size();i++){
		%>
		
			<td  style="background-color:#EDF5FD;">
				扣款/扣分原因
			</td>
			<td  style="background-color:#EDF5FD;">
				扣分情况
			</td>
			<td  style="background-color:#EDF5FD;">
				扣款情况（元）
			</td>
        <%} %>	
	</tr>
	</thead>
	<tbody>
	<% 
	    Map map = (Map)request.getAttribute("map");
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
				<%if (AssConstants.NODE_LEAF.equals(ekif.getLeaf())) { %>
					<% 
					   for(int j=0;j<partnerList.size();j++){
						   String parIds = String.valueOf(partnerList.get(j));
						   String parName = AssStaticMethod.orgId2Name(parIds, "dept");
						   String realScore = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_sc"),"0.0");
						   String reduceReason = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rs"));
						   String remark = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_rm"));
						   String money = StaticMethod.nullObject2String(map.get(ekif.getNodeId()+"_"+parIds+"_money"),"0.0");
						   String total = StaticMethod.nullObject2String(map.get(ekif.getParentNodeId()+"_"+parIds+"_total"),"0.0");
						   String totalNum = StaticMethod.nullObject2String(map.get(ekif.getParentNodeId()+"_"+parIds+"_totalNum"),"0");
						   %>
						   <%
						   if("total".equals(ekif.getKpiType())){
					%>	
							<td style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<span id='<%=ekif.getNodeId() %>_<%=parIds %>_pay' style='text-align:center;display:none'>0<span>
							</td>
							<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<%=totalNum %>
							</td>
							<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<%=total %>
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
						<td  style="vertical-align:middle;text-align:center;">
							<%=money%>
						</td>
						<%
						}else{
						 %>
						 <td>
							<%=realScore%>
						</td>
						<td  style="vertical-align:middle;text-align:center;">
							<%=money%>
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
	<tbody>
	<tr>
		<td colspan="${requestScope.maxLevel}"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center>应付金额</center>
		</td>
	<% 
		for(int i=0;i<partnerList.size();i++){
		String parIds = String.valueOf(partnerList.get(i));
	%>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center>${price}</center>
		</td>
	<%
	}
	%>
	</tr>
	<tr>
		<td colspan="${requestScope.maxLevel}"  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center>实付金额</center>
		</td>
	<% 
		for(int i=0;i<partnerList.size();i++){
		String parIds = String.valueOf(partnerList.get(i));
	%>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center><%=df.format(Double.parseDouble(String.valueOf(map.get(parIds))))  %></center>
		</td>
	<%
	}
	%>
	</tr>
	</tbody>	
</table>
<table>
	<tr>
		<td><a href="${app}/partner/assess/lineAssReportInfo.do?method=assessListPrint&taskId=${taskId}&areaId=${areaId}&year=${year}&month=${month}&quarter=${quarter}&timeType=${timeType}" target='_blank'>
			       打印报表</a></td>
	</tr>
</table>

  </div>
  <div id="step-info" class="tabContent">
  	<%
	AssFlow assFlow = null;
	LineAssFlowMgrImpl flowMgr = new LineAssFlowMgrImpl(); 
	int k=0;
	%>
	<logic:iterate id="steps" name="allStepList">
    <table class="formTable">
	<caption>
		<div class="header center"><eoms:id2nameDB id="<%=String.valueOf(partnerList.get(k)) %>" beanId="partnerDeptDao" />-${requestScope.taskName}-${requestScope.timeStr}-评估操作步骤信息</div>
	</caption>
	<tr>
		<td width="10%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作人
		</td>
		<!-- 
		<td class="label" style="vertical-align:middle;text-align:center;">
			联系方式
		</td>
		-->
		<td width="15%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作时间
		</td>
		<td width="15%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作步骤
		</td>
		<td width="10%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作状态
		</td>
		<td width="25%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			操作意见
		</td>
		<td width="25%" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			相关附件
		</td>
	</tr>

	<logic:iterate id="step" name="steps">
	<bean:define id="state" name="step" property="state" />
	<%
	assFlow = flowMgr.getAssFlowByXml(String.valueOf(state));
	%>
	<tr height="50">
		<td class="label">
			<eoms:id2nameDB id="${step.operateUser }" beanId="tawSystemUserDao" />
		</td>
		<!-- 
		<td class="label">
			${step.operateUserContact }
		</td>
		-->
		<td class="label">
			${step.operateTime }
		</td>
		<td class="label">
			<%=assFlow.getDescription() %>
		</td>
		<td class="label">
		<c:if test="${step.operateFlag=='1' }">
			未处理
		</c:if>
		<c:if test="${step.operateFlag=='2' }">
			完成
		</c:if>
		<c:if test="${step.operateFlag=='3' }">
			驳回
		</c:if>
		<c:if test="${step.operateFlag=='4' }">
			通过
		</c:if>
		</td>
		<td class="label">
			${step.operateOpinion }
		</td>		
		<td  style="vertical-align:middle;text-align:center;">
		 <eoms:attachment name="step" property="accessoriesId"
						scope="page" idField="accessoriesId" appCode="assess"  viewFlag="Y"/>
		</td>
	</tr>
	</logic:iterate>
	</table>
	<%k++; %>
	</logic:iterate>
    </div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>

</c:if>