<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.assess.util.AssConstants"/>
<%@page import="com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="java.text.DecimalFormat"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
  <script type="text/javascript" src="${app}/scripts/partner/saveExcel.js"></script>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.partner.assess.util.AssStaticMethod"%>
<c:if test="${execute=='execute'}">
	<table class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#EDF5FD;border: 1px solid #98C0F4;" >任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 未全部执行</td>
			</tr>
	</table>
</c:if>
<c:if test="${execute!='execute'}">
<script type="text/javascript">
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'assKpiInstanceForm'});
})
	var totalStr = "";
	var totalNodesStr = "";
	var totalAreaStr = "";
</script>

<html:form action="/lineAssReportInfo.do?method=saveTaskDetail" styleId="assKpiInstanceForm" method="post">
<table id ="asstable" class="listTable" width="1300" >
	<caption>
		<div class="header center">
			任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 后评估执行列表
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
								<%=df.format(Double.parseDouble(total)) %>
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
								<%=df.format(Double.parseDouble(money))%>
							</td>
							<%
							}else{
							 %>
							 <td>
								<%=realScore%>
							</td>
							<td  style="vertical-align:middle;text-align:center;">
								<%=df.format(Double.parseDouble(money))%>
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
			<center><%=map.get(parIds) %></center>
		
	<%
	}
	%>
	</tr>
	</tbody>	
</table>
<table>
	<tr>
	<%-- 
		<td>	
			<input type="submit" class="btn" value="返回" onclick="${requestScope.queryType}Back()" />
		</td>
		--%>
		<td>	
			<input type="button" class="btn" value="导出" onclick="saveAsExcel('asstable')" />
		</td>
		<td><a href="${app}/partner/assess/lineAssReportInfo.do?method=listQuarterPrint&taskId=${taskId}&areaId=${areaId}&year=${year}&quarter=${quarter}&timeType=${timeType}" target='_blank'>
			       打印报表</a></td>		
	</tr>
</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function  runBack()
    {
      document.forms[0].action="lineAssReportInfo.do?method=taskViewForQuarter&searchType=1";
    };
function  queryBack()
    {
      document.forms[0].action="lineAssReportInfo.do?method=taskViewForQuarter&searchType=0";
    };

</script>
</c:if>