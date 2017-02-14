<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPlan"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="java.util.Map"%>
<%@ include file="/common/taglibs.jsp"%>
<jsp:directive.page import="java.util.List" />
<%@ include file="/common/header_eoms_form.jsp"%>
<%
List planList = (List)request.getAttribute("pnrFeeInfoPlanList");
Map planNum = (Map)request.getAttribute("planNum");
String planNumStr ="";
String buttomName = "发起";
%>


<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">


<table class="formTable">

	<%
	PnrFeeInfoPlan pnrFeeInfoPlan = null;
	String feeId = "";
	if(planList.size()==0){
		%>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >暂无记录</td>
			</tr>
		<%
		}
	for(int i=0,j=1;i<planList.size();i++,j++){
	pnrFeeInfoPlan = (PnrFeeInfoPlan)planList.get(i);
	buttomName = "发起";
	if("1".equals(pnrFeeInfoPlan.getPlanState())){
		buttomName = "上传文件";
	}else if("2".equals(pnrFeeInfoPlan.getPlanState())){
		buttomName = "送审";
	}
	planNumStr = StaticMethod.nullObject2String(planNum.get(pnrFeeInfoPlan.getFeeId()));
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
	<img align=left src="${app}/images/icons/list.gif" alt="隐藏/显示工作内容" onclick="javascript:showPlan('<%=pnrFeeInfoPlan.getFeeId() %>');" />付款信息名称：
	<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=<%=pnrFeeInfoPlan.getFeeId() %>" target="_blank">
	<eoms:id2nameDB id="<%=pnrFeeInfoPlan.getFeeId() %>" beanId="pnrFeeInfoMainDao" />
	</a>
	(<font color="red"><%=planNumStr %>个付款任务需要处理</font>) 
	</td>
	</tr>
	<tr  id="<%=pnrFeeInfoPlan.getFeeId() %>" style="display:none ">
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
			计划付费时间：
		</td>
		<td class="content">
		<%=pnrFeeInfoPlan.getPlanPayTimeStr() %>
		</td>
		<td class="label" style="vertical-align:middle">
			计划付款额：
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
		</tr>	
	<%
	
	}
	%>
	</td>
	</tr>
		</table>	
</table>

</fmt:bundle>
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
<%@ include file="/common/footer_eoms.jsp"%>