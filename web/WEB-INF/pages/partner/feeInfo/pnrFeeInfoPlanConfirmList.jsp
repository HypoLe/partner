<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPlan"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPay"%>
<%@page import="com.boco.eoms.partner.feeInfo.webapp.form.PnrFeeInfoPayForm"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="com.boco.eoms.partner.feeInfo.mgr.*"%>
<%@page import="java.util.Map"%>
<%@ include file="/common/taglibs.jsp"%>
<jsp:directive.page import="java.util.List" />
<%@ include file="/common/header_eoms_form.jsp"%>
<%
List planList = (List)request.getAttribute("pnrFeeInfoPlanConfirmList");
Map planNum = (Map)request.getAttribute("planNum");
//IPnrFeeInfoPayMgr pnrFeeInfoPayMgr = (IPnrFeeInfoPayMgr)request.getAttribute("payMgr");
String planNumStr ="";
String buttomName = "审核";
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
		<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">


		<table class="formTable" id="tongzhi">
		<caption>
		<div class="header center"  style="text-align:center;font-weight:bold;">待审核付款申请列表</div>
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
	if("1".equals(planState)){
		planStateCH = "待审核";
	}
	planNumStr = StaticMethod.nullObject2String(planNum.get(pnrFeeInfoPlan.getFeeId()));
	List<PnrFeeInfoPay> payList = null;
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
	(<font color="red"><%=planNumStr %>个待审核付款申请需要处理</font>) 
	</td>
	</tr>
	<tr  id="<%=pnrFeeInfoPlan.getFeeId() %>" style="display:">
	<td colspan="4">
	付款计划:
	<table class="formTable" >
	
	<%
	feeId = pnrFeeInfoPlan.getFeeId();
	}
	%>
	<tr>
			<th colspan="5"><b>第<%=j %>项：</b></th>
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
		</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			计划付费时间：
		</td>
		<td class="content">
		<%=pnrFeeInfoPlan.getPlanPayTimeStr() %>
		</td>
		<td class="label" style="vertical-align:middle">
			计划付款额(万元)：
		</td>
		<td class="content">
		<%=pnrFeeInfoPlan.getPlanPayFee() %>
		</td>
			<th rowspan="2">
			 <input  type="button" class="btn" value="<%=buttomName %>"onclick="window.open('${app}/partner/feeInfo/pnrFeeInfoPays.do?method=planPay&planId=<%=pnrFeeInfoPlan.getId() %>');"	/></th>	
	</tr>
	<tr>	
		<td class="label" style="vertical-align:middle">
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