
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

<OBJECT   id="WebBrowser"   classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"   height="0"   width="0"   VIEWASTEXT> 
</OBJECT> 
<input   type="button"   value="打印"       onclick= "document.all.WebBrowser.ExecWB(6,1) "   class= "NOPRINT "> 
<input   type="button"   value="直接打印"   onclick= "document.all.WebBrowser.ExecWB(6,6) "   class= "NOPRINT "> 
<input   type="button"   value="页面设置"   onclick= "document.all.WebBrowser.ExecWB(8,1) "   class= "NOPRINT "> 
<input   type="button"   value="打印预览"   onclick= "document.all.WebBrowser.ExecWB(7,1) "   class= "NOPRINT ">
<table class="listTable" width="1300" >
	<caption>
		<div class="header center">
			<eoms:id2nameDB id="${requestScope.areaId}" beanId="tawSystemAreaDao" />-任务(${requestScope.taskName})-周期(${requestScope.timeTypeStr})-时间(${requestScope.timeStr}) 后评估执行表
		</div>
	</caption>
	<thead>
	<tr>
		<td colspan="${requestScope.maxLevel}" width="30%" rowspan="2">
			<center>后评估指标</center>
		</td>
		<% 
		   DecimalFormat df = new DecimalFormat("0.00");
		   List partnerList = (List)request.getAttribute("partnerList");
		   for(int i=0;i<partnerList.size();i++){
			   String parName = AssStaticMethod.orgId2Name(String.valueOf(partnerList.get(i)), "dept");
		%>
			<td colspan="3">
				<center><eoms:id2nameDB id="<%=String.valueOf(partnerList.get(i)) %>" beanId="partnerDeptDao" /></center>
			</td>
        <%} %>
	</tr>
	
	<tr>
		<% 
		   for(int i=0;i<partnerList.size();i++){
		%>
		
			<td >
				扣款/扣分原因
			</td>
			<td >
				扣分情况
			</td>
			<td >
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
				<td rowspan="<%=ekif.getRowspan()%>" colspan="<%=ekif.getColspan()%>"  style="vertical-align:middle;text-align:center;background-color:#cae8ea">
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
							<td style="vertical-align:middle;text-align:center;background-color:#cae8ea">
								<span id='<%=ekif.getNodeId() %>_<%=parIds %>_pay' style='text-align:center;display:none'>0<span>
							</td>
							<td  style="vertical-align:middle;text-align:center;background-color:#cae8ea">
								<%=totalNum %>
							</td>
							<td  style="vertical-align:middle;text-align:center;background-color:#cae8ea">
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
		<td colspan="${requestScope.maxLevel}"  style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			<center>应付金额</center>
		</td>
	<% 
		for(int i=0;i<partnerList.size();i++){
		String parIds = String.valueOf(partnerList.get(i));
	%>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			<center>${price}</center>
		</td>
	<%
	}
	%>
	</tr>
	<tr>
		<td colspan="${requestScope.maxLevel}"  style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			<center>实付金额</center>
		</td>
	<% 
		for(int i=0;i<partnerList.size();i++){
		String parIds = String.valueOf(partnerList.get(i));
	%>
		<td colspan="3" style="vertical-align:middle;text-align:center;background-color:#cae8ea">
			<center><%=df.format(Double.parseDouble(String.valueOf(map.get(parIds))))  %></center>
		</td>
	<%
	}
	%>
	</tr>
	</tbody>	
</table>
<table width="1300" >
	<tr>
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;网络部负责人签字：
		</td>
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;代维单位负责人签字：
		</td>
	</tr>
	<tr>	
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（盖&nbsp;   章）
		</td>
		<td style="font-size:25px;vertical-align:middle;text-align:left" width="50%" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（盖   章）
		</td>	
	</tr>
</table>

<%@ include file="/common/footer_eoms.jsp"%>
