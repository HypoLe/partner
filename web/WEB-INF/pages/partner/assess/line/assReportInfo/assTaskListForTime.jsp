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
			任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr})-合作伙伴(<eoms:id2nameDB id="${partner}" beanId="partnerDeptDao" />)后评估执行表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="30%" rowspan="2" style="background-color:#EDF5FD;">
			<center>后评估指标</center>
		</td>
		<% 
		   DecimalFormat df = new DecimalFormat("0.00");
		   List timeList = (List)request.getAttribute("timeList");
		   for(int i=0;i<timeList.size();i++){
		%>
			<td colspan="3" style="background-color:#EDF5FD;">
				<center><%=timeList.get(i) %></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<timeList.size();i++){
		%>
		
			<td style="background-color:#EDF5FD;">
				扣款/扣分原因
			</td>
			<td style="background-color:#EDF5FD;">
				扣分情况
			</td>
			<td style="background-color:#EDF5FD;">
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
							<td  style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
								<%=df.format(Double.parseDouble(String.valueOf(total))) %>
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
							<%=df.format(Double.parseDouble(String.valueOf(money)))%>
						</td>
						<%
						}else{
						 %>
						 <td>
							<%=realScore%>
						</td>
						<td  style="vertical-align:middle;text-align:center;">
							<%=df.format(Double.parseDouble(String.valueOf(money)))%>
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
		for(int i=0;i<timeList.size();i++){
		String parIds = String.valueOf(timeList.get(i));
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
		for(int i=0;i<timeList.size();i++){
		String parIds = String.valueOf(timeList.get(i));
	%>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#EDF5FD">
			<center><%=df.format(Double.parseDouble(String.valueOf(map.get(parIds)))) %></center>
		</td>
	<%
	}
	%>
	</tr>
	</tbody>	
</table>
<table>
	<tr>
		<td>	
			<input type="submit" class="btn" value="返回" onclick="${requestScope.queryType}Back()" />
		</td>
	</tr>
</table>

  </div>
  <div id="step-info" class="tabContent">
  	<%
	AssFlow assFlow = null;
	LineAssFlowMgrImpl flowMgr = new LineAssFlowMgrImpl(); 
	%>
	<logic:iterate id="steps" name="allStepList">
    <table class="formTable">
	<caption>
		<div class="header center">${requestScope.taskName}-${requestScope.timeStr}-评估操作步骤信息</div>
	</caption>
	<tr>
		<td class="label">
			操作人
		</td>
		<!-- 
		<td class="label">
			联系方式
		</td>
		-->
		<td class="label">
			操作时间
		</td>
		<td class="label">
			操作步骤
		</td>
		<td class="label">
			操作状态
		</td>
		<td class="label">
			操作意见
		</td>
	</tr>

	<logic:iterate id="step" name="steps">
	<bean:define id="state" name="step" property="state" />
	<%
	assFlow = flowMgr.getAssFlowByXml(String.valueOf(state));
	%>
	<tr>
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
	</tr>
	</logic:iterate>
	</table>
	</logic:iterate>
    </div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">

function  runBack()
    {
      document.forms[0].action="lineAssReportInfo.do?method=taskViewSearch&searchType=1";
    };
function  queryBack()
    {
      v.passing="true";
      document.forms[0].action="lineAssReportInfo.do?method=taskViewSearch&searchType=0";
    };
    
</script>
</c:if>