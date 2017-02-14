<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPlan"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPay"%>
<%@page import="com.boco.eoms.partner.feeInfo.webapp.form.PnrFeeInfoPayForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="com.boco.eoms.partner.feeInfo.mgr.*"%>
<%@page import="java.util.Map"%>
<%@page import="com.google.common.base.Strings"%>
<%@ include file="/common/taglibs.jsp"%>
<jsp:directive.page import="java.util.List" />
<%@ include file="/common/header_eoms_form.jsp"%>


<%
List planList = (List)request.getAttribute("pnrFeeInfoPlanList");
Map planNum = (Map)request.getAttribute("planNum");
//IPnrFeeInfoPayMgr pnrFeeInfoPayMgr = (IPnrFeeInfoPayMgr)request.getAttribute("payMgr");
String planNumStr ="";
String buttomName = "发起付款";
%>
<html>
<head>
<script language="javascript">
function showPlan(trId)
{
	plans = document.getElementById(trId);   
  if(plans.style.display=="")
  {
    plans.style.display="none";
  }
  else
  {
    plans.style.display="";
  }
}
</script>
</head>
	<body>
	<table>
  <tr>
    <td>
           <input type="button"  value="付款待发起列表" onclick="window.location.href='${app}/partner/feeInfo/pnrFeeInfoPays.do?method=feepayUndoList&planState=0'" class="button">
           <input type="button"  value="发起待审核列表" onclick="window.location.href='${app}/partner/feeInfo/pnrFeeInfoPays.do?method=feepayUndoList&planState=1'" class="button">
           <input type="button"  value="材料待提交列表" onclick="window.location.href='${app}/partner/feeInfo/pnrFeeInfoPays.do?method=feepayUndoList&planState=3'" class="button">
           <input type="button"  value="材料待审核列表" onclick="window.location.href='${app}/partner/feeInfo/pnrFeeInfoPays.do?method=feepayUndoList&planState=5'" class="button">
           <input type="button"  value="付款待完成列表" onclick="window.location.href='${app}/partner/feeInfo/pnrFeeInfoPays.do?method=feepayUndoList&planState=6'" class="button">
           <input type="button"  value="付款完成列表" onclick="window.location.href='${app}/partner/feeInfo/pnrFeeInfoPays.do?method=feepayUndoList&planState=7'" class="button">
     </td>
     </tr>
</table>
	
	
		<table>
		<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">
		<table class="formTable" id="tongzhi">
		<caption>
		<div class="header center"  style="text-align:center;font-weight:bold;">
		<c:if test="${planState == ''}">
			付费待处理列表
		</c:if>
		<c:if test="${planState == '0'}">
			付款待发起列表
		</c:if>
		<c:if test="${planState == '1'}">
			付款待负责人审核列表
		</c:if>
		<c:if test="${planState == '2'}">
			付款被驳回列表
		</c:if>
		<c:if test="${planState == '3'}">
			等待提交付款材料列表
		</c:if>
		<c:if test="${planState == '4'}">
			付款材料等待审核列表
		</c:if>
		<c:if test="${planState == '5'}">
			付款材料被驳回列表
		</c:if>
		<c:if test="${planState == '6'}">
			付款等待完成列表
		</c:if>
		<c:if test="${planState == '7'}">
			完成付款列表
		</c:if>
		</div>
	</caption>	
		
		
		
	<%
	PnrFeeInfoPlan pnrFeeInfoPlan = null;
	String feeId = "";
	String planState = "";
	String planStateCH=""; 
	if(planList.size()==0){
		%>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >暂无记录</td>
			</tr>
		<%
		}
	for(int i=0,j=1;i<planList.size();i++,j++){
	pnrFeeInfoPlan = (PnrFeeInfoPlan)planList.get(i);
	
	planState = pnrFeeInfoPlan.getPlanState();
	if("3".equals(planState)){
		planStateCH = "等待提交付款材料";
		buttomName = "录入材料";
	}else if("5".equals(planState)){
		planStateCH = "被驳回的付款材料";
		buttomName = "录入材料";
	}else if("0".equals(planState)){
		planStateCH = "通知付款";
		buttomName = "发起付款";
	}else if("2".equals(planState)){
		planStateCH = "被驳回的通知付款";
		buttomName = "发起付款";
	}else if("1".equals(planState)){
		planStateCH = "待审核";
		buttomName = "审核";
	}else if("4".equals(planState)){
		planStateCH = "材料等待审核中";
		buttomName = "审核";
	}else if("7".equals(planState)){
		planStateCH = "完成付款";
		buttomName = "查看详细";
	}else if("6".equals(planState)){
		planStateCH = "等待付款完成";
		buttomName = "付款完成";
	}
	if(!feeId.equals(pnrFeeInfoPlan.getFeeId())){
		if(i!=0){
	%>
	</tr>
		</table>	
	<%
		}
		j=1;
	%>
	<tr>
	<td class="label" colspan="4">

	<img align=right src="${app}/images/icons/open.gif" alt="隐藏/显示工作内容" onclick="javascript:showPlan('<%=pnrFeeInfoPlan.getFeeId() %>');" />付款信息名称：
	<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=<%=pnrFeeInfoPlan.getFeeId() %>" target="_blank">
	<eoms:id2nameDB id="<%=pnrFeeInfoPlan.getFeeId() %>" beanId="pnrFeeInfoMainDao" />
	</a> 
	<c:if test="${planState=='7'}">
	(<font color="red"><%= planNum.get(pnrFeeInfoPlan.getFeeId())%>个已经完成的任务</font>) 
	</c:if>
	<c:if test="${planState!=7}">
	(<font color="red"><%= planNum.get(pnrFeeInfoPlan.getFeeId())%>个待处理任务</font>) 
	</c:if>
	</td>
	</tr>

	<tr  id="<%=pnrFeeInfoPlan.getFeeId() %>" style="display:none">

	<td colspan="4">
	付款计划:
	<table class="formTable" >
	
	<%
	feeId = pnrFeeInfoPlan.getFeeId();
	}
	%>
	<tr>
			<th colspan="5"><b><%=pnrFeeInfoPlan.getRemark() %>：</b></th>
	</tr>
		<tr>
			<td class="label" style="vertical-align:middle">
				阶段付款名称：
			</td>
			<td class="content">
				<%=pnrFeeInfoPlan.getPlanPayName() %>
				<html:hidden property="feeId" value="<%=pnrFeeInfoPlan.getFeeId() %>" />
			</td>
			<td class="label" style="vertical-align:middle">
				状态：
			</td>
			<td class="content">
				<%=planStateCH %>
				<html:hidden property="feeId" value="<%=pnrFeeInfoPlan.getPlanState() %>" />
			</td>
						<td rowspan="3">
						<c:if test="${planState!='7'}">
			 <input  type="button" class="btn" value="<%=buttomName %>"onclick="window.open('${app}/partner/feeInfo/pnrFeeInfoPays.do?method=planPay&planId=<%=pnrFeeInfoPlan.getId() %>');"	/></th>
			 </c:if>
			 <%if("6".equals(planState)){%>
				 <input  type="button" class="btn" value="阶段回复"onclick="window.open('${app}/partner/feeInfo/pnrFeeInfoPays.do?method=planPayReply&planId=<%=pnrFeeInfoPlan.getId() %>');"	/>
			 <% } %>
			 <c:if test="${planState=='7'}">
			  <input  type="button" class="btn" value="查看详细"onclick="window.open('${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&planState=7&planId=<%=pnrFeeInfoPlan.getId() %>');"	/>
			 </c:if>	
			 </td>
		</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			计划付费时间：
		</td>
		<td class="content">
		<%=pnrFeeInfoPlan.getPlanPayTime() %>
		</td>
		
	
		<td class="label" style="vertical-align:middle">
			计划付款额(万元)：
		</td>
		
			
		<%
			String myType = Strings.nullToEmpty(pnrFeeInfoPlan.getAbilityType());
		if(!myType.equals("circuitProxy")&&!myType.equals("baseStationProxy")){
		%>
		<td class="content">
		<%=pnrFeeInfoPlan.getPlanPayFee() %>
		</td>

			<% }else{%>
				<td class="content">请点击"发起付款"
		</td>
			<%} %>
	</tr>
	<tr>	
		<td class="label" style="vertical-align:middle" >
			阶段付款描述：
		</td>
		<td class="content" colspan="3">
		<%=pnrFeeInfoPlan.getRemark() %>
		</td>
		<%--<td class="label">
			附件：
		</td>
		<td class="content">
			
		<% payList = pnrFeeInfoPayMgr.getPnrFeeInfoPays(pnrFeeInfoPlan.getId()); %>
		<% PnrFeeInfoPay pnrfeeInfoPay = null; %>
		<% if(payList != null && payList.size() >= 1){ %>
		<% 		pnrfeeInfoPay = payList.get(0); %>
		<% request.setAttribute("pnrFeeInfoPayForm", pnrfeeInfoPay); %>
		<%if(pnrfeeInfoPay.getAccessoriesId()!=null&&!"".equals(pnrfeeInfoPay.getAccessoriesId())) {%>
			<eoms:attachment name="pnrFeeInfoPayForm" property="accessoriesId"
			scope="request" idField="accessoriesId" appCode="feeInfo" viewFlag="Y" />
		<% }%> 
		
		
		<%} %>
		
		</td>--%>
	</tr>
	<%
	
	}
	%>
	</td>
	</tr>
		</table>	
</fmt:bundle>
		</table>	
</body>
</html>