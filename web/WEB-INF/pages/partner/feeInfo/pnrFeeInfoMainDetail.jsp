<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPlan"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPay"%>
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
//        	tabs.addTab('audit-info', '计划审核信息 ');
        	tabs.addTab('finish-info', '已完成付费计划信息 ');
    		tabs.activate(0);
	});
</script>
<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">
<div id="info-page">
  <div id="plan-info" class="tabContent">

<table class="formTable">
	<caption>
		<div class="header center">合同费用信息</div>
	</caption>
	<tr>
		<%--<td class="label" style="vertical-align:middle">
			付费计划名称
		</td>
		<td class="content" colspan="3">
			${pnrFeeInfoMainForm.feeName}
		</td>
		--%>
		<td class="label" style="vertical-align:middle" >
			合同编号：
		</td>
		<td class="content" >
<!-- 		<a href="${app}/partner/contract/ctContentss.do?method=detail&ID=${pnrFeeInfoMainForm.contractId}&TABLE_ID=${tableId}&THEME_ID=${themeId}" target="_blank">
				${pnrFeeInfoMainForm.contractNO}</a>	 -->	
					${pnrFeeInfoMainForm.contractNO}	
		</td>
		<td class="label" style="vertical-align:middle">
			合同名称:
		</td>
		<td class="content" colspan="3">
			${pnrFeeInfoMainForm.contractName}
		</td>
	</tr>
	<%--
	<tr>
		<td class="label" style="vertical-align:middle">
			付费计划编号
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.feeNO}
		</td>

		<td class="label" style="vertical-align:middle" >
			合同编号：
		</td>
		<td class="content" >
			<a href="${app}/partner/contract/ctContentss.do?method=detail&ID=${pnrFeeInfoMainForm.contractId}&TABLE_ID=${tableId}&THEME_ID=${themeId}" target="_blank">
				${pnrFeeInfoMainForm.contractNO}</a>			
		</td>--%>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			甲方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnit}" beanId="tawSystemAreaDao" />		
		</td>
<td class="label" style="vertical-align:middle">
			乙方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.collectUnit}" beanId="tawSystemAreaDao" />	
		</td>
		
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			甲方负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnitUser}" beanId="tawSystemUserDao" />
		</td>

		<td class="label" style="vertical-align:middle">
			乙方负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.collectUnitUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			付款负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payMoneyUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label" style="vertical-align:middle">
			付款方式:
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.payWay}
		</td>
	</tr>
	<tr>
		
		<td class="label" style="vertical-align:middle">
			合同金额(万元):
		</td>
		<td class="content"  >
			${pnrFeeInfoMainForm.contractAmount}
		</td>
		
	</tr>
	
	<tr>
		<td class="label" style="vertical-align: middle">
			合同开始时间：
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.startTime}
		</td>
		<td class="label" style="vertical-align: middle">
			合同结束时间：
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.endTime}
		</td>
	</tr>
	
	<c:if test="${not empty baseStationMains}">
		<tr>
			<td class="label" style="vertical-align: middle">关联基站代维规模信息：</td>
			<td class="content" colspan="3"></td>
		</tr>
		<c:forEach var="baseStation" items="${baseStationMains}">
			<tr>
				<td class="label" style="vertical-align:middle">
					代维公司：
				</td>
				<td class="content">
					${baseStation.monitorCompany}
				</td>
				<td class="label" style="vertical-align:middle">
					详情：
				</td>
				<td class="content">
					<a href="${app}/partner2/baseStation/baseStationMain.do?method=detail&id=${baseStation.id}" target="_blank" >查看</a>
				</td>
			</tr>
		</c:forEach>
	</c:if>
	
	<c:if test="${not empty circuitLengths}">
		<tr>
			<td class="label" style="vertical-align: middle">关联线路代维规模信息：</td>
			<td class="content" colspan="3"></td>
		</tr>
		<c:forEach var="circuitLength" items="${circuitLengths}">
			<tr>
				<td class="label" style="vertical-align:middle">
					代维公司：
				</td>
				<td class="content">
					${circuitLength.monitorCompany}
				</td>
				<td class="label" style="vertical-align:middle">
					详情：
				</td>
				<td class="content">
					<a href="${app}/partner2/circuit/circuitLength.do?method=goToSingleDetail&id=${circuitLength.id}" target="_blank" >查看</a>
				</td>
			</tr>
		</c:forEach>
	</c:if>
	
	
	<tr>
			<td class="label" style="vertical-align:middle">
				合同正本：
			</td>
			<td class="content">
			<% int index = 0; %>
			<% List payList = (List)request.getAttribute("payList"); %>
			<% 
			if(payList!=null&&payList.size() > index){ %>
			<% PnrFeeInfoPay feeInfoPay = (PnrFeeInfoPay)payList.get(index);
			   index++;
			   request.setAttribute("pnrFeeInfoPayForm", feeInfoPay);
			   }
			 %>
				<eoms:attachment name="pnrFeeInfoMainForm" property="contractAccessoriesId"
				scope="request" idField="contractAccessoriesId" appCode="feeInfo" viewFlag="Y" />
			</td>
		<td class="label" style="vertical-align:middle">
			相关附件：
		</td>
		<td class="content">
			<eoms:attachment name="pnrFeeInfoMainForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo" viewFlag="Y"/>
		</td>
	</tr>
</table>

  </div>
  <!-- 
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
  		for(int i=0;auditList!=null&&i<auditList.size();i++){
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
 -->

<div id="finish-info" class="tabContent">
	<div id="workDiv">
	<c:if test="${planFishListsize==0}">
		暂无完成付费任务
	</c:if>
	<table class="formTable">
	<% int planIndex = 0; %>
	<% List pl = (List)request.getAttribute("planFishList"); %>
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
	
	<c:if test="${plan.noticeFiles!=null}">
	<tr>
		<td class="label" style="vertical-align:middle">
			付款通知书:
		</td>
		<td class="content">
		<eoms:attachment name="plan" property="noticeFiles" scope="page" idField="${plan.noticeFiles}" appCode="feeInfo" viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			付款申请:
		</td>
		<td class="content">
		<eoms:attachment name="plan" property="requisitionFiles" scope="page" idField="${plan.requisitionFiles}" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同文本关键页:
		</td>
		<td class="content">
		<eoms:attachment name="plan" property="contractFiles" scope="page" idField="${plan.contractFiles}" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			相应金额发票:
		</td>
		<td class="content">
		<eoms:attachment name="plan" property="invoiceFiles" scope="page" idField="${plan.invoiceFiles}" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
	</c:if>
	</c:forEach>

		</table>
	</div>
</div>	
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>