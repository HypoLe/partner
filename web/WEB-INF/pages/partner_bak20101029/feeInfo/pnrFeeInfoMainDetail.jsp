<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoAudit"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";
%>
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('plan-info', '付费计划信息 ');
        	tabs.addTab('audit-info', '计划审核信息 ');
        	tabs.addTab('finish-info', '已完成付费计划信息 ');
    		tabs.activate(0);
	});
</script>
<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">
<div id="info-page">
  <div id="plan-info" class="tabContent">

<table class="formTable">
	<caption>
		<div class="header center">付费计划信息</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			付费计划名称
		</td>
		<td class="content" colspan="3">
			${pnrFeeInfoMainForm.feeName}
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			付费计划编号
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.feeNO}
		</td>

		<td class="label" style="vertical-align:middle">
			合同编号：
		</td>
		<td class="content">
			<a href="${app}/partner/contract/ctContentss.do?method=detail&ID=${pnrFeeInfoMainForm.contractId}&TABLE_ID=${tableId}&THEME_ID=${themeId}" target="_blank">
				${pnrFeeInfoMainForm.contractNO}</a>			
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			付款方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnit}" beanId="tawSystemDeptDao" />		
		</td>

		<td class="label" style="vertical-align:middle">
			付款方负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnitUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			收款方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.collectUnit}" beanId="tawSystemDeptDao" />	
		</td>

		<td class="label" style="vertical-align:middle">
			收款方负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.collectUnitUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			付款方式:
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.payWay}
		</td>
		<td class="label" style="vertical-align:middle">
			合同金额(万元):
		</td>
		<td class="content"  colspan="3">
			${pnrFeeInfoMainForm.contractAmount}
		</td>
		</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			收款银行：
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.bank}
		</td>
		<td class="label" style="vertical-align:middle">
			银行账号：
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.bankAccount}
		</td>
	</tr>
<tr>
<td colspan="4">
<table class="formTable">

	<c:forEach var="plan" items="${planList}" varStatus="stauts">
	<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			计划付费时间：
		</td>
		<td class="content">
			${plan.planPayTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			计划付款额(万元)：
		</td>
		<td class="content">
			${plan.planPayFee}(所占比例：${plan.planWeight}%)
		</td>
		</tr>
		<c:if test="${plan.factPayTime !=null}">
		<td class="label" style="vertical-align:middle">
			实际付费时间：
		</td>
		<td class="content">
			${plan.factPayTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			实际付款额：
		</td>
		<td class="content">
			${plan.factPayFee}
		</td>
		</tr>
		</c:if>
		<tr>	
		<td class="label" style="vertical-align:middle">
			阶段付款描述：
		</td>
		<td class="content" colspan="3">
			${plan.remark}
		</td>
		</tr>
	</c:forEach>
	</table>
	</td>
	</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			付款描述:
		</td>
		<td class="content"  colspan="3">
			${pnrFeeInfoMainForm.description}
						
			
		</td>

		</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			附件：
		</td>
		<td class="content" colspan="3">
			<eoms:attachment name="pnrFeeInfoMainForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo" viewFlag="Y"/>
		</td>
	</tr>
</table>

  </div>
  <div id="audit-info" class="tabContent">

  <table class="formTable">
  			<caption>
				<div class="header center">
					计划审核信息
				</div>
			</caption>
	<%
  		List auditList = (List)request.getAttribute("auditList");
  		PnrFeeInfoAudit pnrFeeInfoAudit = null;
  		for(int i=0;i<auditList.size();i++){
	  		pnrFeeInfoAudit = (PnrFeeInfoAudit)auditList.get(i);
  %>
			<tr>
			<th colspan="4">&nbsp;</th>
			</tr>
			<tr>
			<td class="label">
					审核者
				</td>
				<td class="content">
				<%
				if("user".equals(pnrFeeInfoAudit.getToOrgType())){
				%>
					<eoms:id2nameDB id="<%=pnrFeeInfoAudit.getToOrgId() %>" beanId="tawSystemUserDao" />	
				<%
				}else if("dept".equals(pnrFeeInfoAudit.getToOrgType())){
				%>
					<eoms:id2nameDB id="<%=pnrFeeInfoAudit.getToOrgId() %>" beanId="tawSystemDeptDao" />
				<%
				}
				%>
				</td>

				<td class="label">
					审核时间
				</td>
				<td class="content">
				<%
				if(pnrFeeInfoAudit.getOperateTime()!=null){
					auditTime = dateFormat.format(pnrFeeInfoAudit.getOperateTime());
				}else{
					auditTime = "";
				}
				%>
					<%=auditTime %>
					
				</td>
			</tr>
			<tr>
			<td class="label">
					审核结果
				</td>
				<td class="content" colspan="3">
				<%
				if("1".equals(pnrFeeInfoAudit.getAuditResult())){
				%>
					驳回
				<%
				}else if("2".equals(pnrFeeInfoAudit.getAuditResult())){
				%>
				    通过
				<%
				}else{
				%>
					待审核
				<%
				}
				%>
				</td>
			</tr>
			<%
				if(pnrFeeInfoAudit.getRemark() != null){
			%>
			<tr>
				<td class="label">
					审核说明
				</td>
				<td class="content" colspan="3">
					<%=pnrFeeInfoAudit.getRemark() %>
				</td>
			</tr>
			<%
				}
  }
			%>
	</table>
	<br>
  </div>
</div>


<div id="finish-info" class="tabContent">
	<div id="workDiv">
	<c:if test="${planFishListsize==0}">
		暂无完成付费任务
	</c:if>
	<table class="formTable">
	<c:forEach var="plan" items="${planFishList}" varStatus="stauts">
	<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			计划付费时间：
		</td>
		<td class="content">
			${plan.planPayTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			计划付款额(万元)：
		</td>
		<td class="content">
			${plan.planPayFee}(所占比例：${plan.planWeight}%)
		</td>
		</tr>
		<td class="label" style="vertical-align:middle">
			实际付费时间：
		</td>
		<td class="content">
			${plan.factPayTimeStr}
		</td>
		<td class="label" style="vertical-align:middle">
			实际付款额(万元)：
		</td>
		<td class="content">
			${plan.factPayFee}
		</td>
		</tr>
		<tr>	
		<td class="label" style="vertical-align:middle">
			阶段付款描述：
		</td>
		<td class="content" colspan="3">
			${plan.remark}
		</td>
		</tr>
	</c:forEach>

		</table>
	</div>
</div>	
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>