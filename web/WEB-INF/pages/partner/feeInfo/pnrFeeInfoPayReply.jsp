<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.partner.feeInfo.webapp.form.PnrFeeInfoPayForm"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoAudit"%>
<%@ page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPlan"%>

<%@ page import="java.text.SimpleDateFormat"%>
<style type="text/css">
#toOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";
String buttonName = "确定";
PnrFeeInfoPayForm pnrFeeInfoPayForm = (PnrFeeInfoPayForm)request.getAttribute("pnrFeeInfoPayForm");
PnrFeeInfoPlan pnrFeeInfoPlan = (PnrFeeInfoPlan)request.getAttribute("pnrFeeInfoPlan");
String payState = pnrFeeInfoPlan.getPlanState();
String operateUserId = (String)request.getAttribute("operateUserId");
%>
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
			<% if(operateUserId.equals(pnrFeeInfoPayForm.getPayUser())){ %>
        	tabs.addTab('pay-info', '付费信息 ');
        	<% } %>
        	tabs.addTab('audit-info', '阶段付费信息 ');
    		tabs.activate(0);
	});
	
function changePayState(newPayState) {
	// document.getElementById("payState").value = newPayState;
}

function validateForm() {
	// 阶段回复
	document.getElementById("payState").value = 8;		
}
</script>

<div id="info-page">
<div id="pay-info"class="tabContent">

<html:form action="pnrFeeInfoPays.do?method=save" styleId="pnrFeeInfoPayForm" method="post" onsubmit="return validateForm();">
 
<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">

<% if(operateUserId.equals(pnrFeeInfoPayForm.getPayUser())){ %>

<table class="formTable">
	<caption>
		<div class="header center">费用付款</div>
	</caption>
		<tr>
		<%--<td class="label" style="vertical-align:middle">
			付款信息名称
		</td>
		<td class="content" colspan="3">
			${pnrFeeInfoPayForm.feeName}
			<html:hidden property="feeName" value="${pnrFeeInfoPayForm.feeName}" />
		</td>
		--%>
		
		</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			付款信息编号
		</td>
		<td class="content">
			${pnrFeeInfoPayForm.feeNO}
			<html:hidden property="feeNO" value="${pnrFeeInfoPayForm.feeNO}" />
		</td>
		<td class="label" style="vertical-align:middle">
			合同编号
		</td>
		<td class="content">
		<a href="${app}/partner/contract/ctContentss.do?method=detailAgr&id=${pnrFeeInfoPayForm.contractId}" target="_blank">
			${pnrFeeInfoPayForm.contractNO}
			<html:hidden property="contractNO" value="${pnrFeeInfoPayForm.contractNO}" />
			</a>
		</td>
		
	</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			合同金额(万元)
		</td>
		<td class="content">
			${pnrFeeInfoPayForm.contractAmount}
			<html:hidden property="contractAmount" value="${pnrFeeInfoPayForm.contractAmount}" />
		</td>
		<td class="label" style="vertical-align:middle">
			计划付款金额(万元)
		</td>
		<td class="content">
			${pnrFeeInfoPayForm.planPayFee}
			<html:hidden property="planPayFee" value="${pnrFeeInfoPayForm.planPayFee}" />
		</td>
		
	</tr>	
	<tr>
		<td class="label" style="vertical-align:middle">
			计划付款时间
		</td>
		<td class="content">
			${pnrFeeInfoPayForm.planPayTimeStr}
			<html:hidden property="planPayTime" value="${pnrFeeInfoPayForm.planPayTime}" />
		</td>
		<td class="label" style="vertical-align:middle">
			付款方式
		</td>
		<td class="content">
			${pnrFeeInfoPayForm.payWay}
			<html:hidden property="payWay" value="${pnrFeeInfoPayForm.payWay}" />
		</td>
		
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			阶段付款描述
		</td>
		<td class="content"colspan="3">
			${pnrFeeInfoPayForm.planRemark}
			 
		</td>
		
	</tr>
	<c:if test="${agreementList!=null}">
		<tr>
		<td class="label" style="vertical-align:middle">
			综合评分
		</td>
		<td class="label"colspan="3">
			<table>
			<tr>
				<td class="label">协议名称</td><td class="label">综合得分</td><td class="label">满意度</td>
			</tr>
			<c:forEach var='agreementList' items='${agreementList}'>
			<tr>
				<td>${agreementList.agreementName}</td><td>${agreementList.lastScore}</td><td><eoms:dict key="dict-partner-agreement" dictId="satisfy" itemId="${agreementList.satisfy}" beanId="id2nameXML" /></td>
			</tr>
			</c:forEach>
			</table> 
		</td>
		
	</tr>
	</c:if>
	
	
	
	
	
	
	
	<%
	if("0".equals(payState) || "2".equals(payState)){
	%>
	<tr>
		<td class="label" style="vertical-align:middle">
			付款执行人
		</td>
		<td class="content">
			${pnrFeeInfoPayForm.payUser}
			<html:hidden property="payUser" value="${pnrFeeInfoPayForm.payUser}" />
			<html:hidden property="collectUnitUser" value="${pnrFeeInfoPayForm.collectUnitUser}" />
		</td>
		<td class="label" style="vertical-align:middle">
			实际付款额
		</td>
		<td class="content">
			<html:text property="factPayFee" styleId="factPayFee" styleClass = "text medium"  value="${pnrFeeInfoPayForm.factPayFee}" />
		</td>
	</tr>

	<%
	}else{
	%>
	<tr>
		<td class="label" style="vertical-align:middle">
			付款执行人
		</td>
		<td class="content">
			${pnrFeeInfoPayForm.payUser}
			<html:hidden property="payUser" value="${pnrFeeInfoPayForm.payUser}" />
			<html:hidden property="collectUnitUser" value="${pnrFeeInfoPayForm.collectUnitUser}" />
		</td>
		<td class="label" style="vertical-align:middle">
			实际付款额
		</td>
		<td class="content">
		${pnrFeeInfoPayForm.factPayFee}
		<html:hidden property="factPayFee" value="${pnrFeeInfoPayForm.factPayFee}" />
			</td>
	</tr>
 
	<%
	}
	if("0".equals(payState) || "2".equals(payState) || "6".equals(payState)){	
	%>
		<tr>
		<td class="label" style="vertical-align:middle">
			阶段付款描述
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" ></textarea>			
		</td>
	</tr>
	 
	<%
	}
	%>
	</table>
<table>
	<tr>
		<td>
			<% if(!"7".equals(payState)) { %>
			<input id="button" type="submit" value="<%=buttonName %>"  class="button"  />
			<% } %>
		</td>
	</tr>
</table>	
	<html:hidden property="id" value="" />
	<html:hidden property="feePlanId" value="${pnrFeeInfoPayForm.feePlanId}" />
	<html:hidden property="feeId" value="${pnrFeeInfoPayForm.feeId}" />
	<html:hidden property="createTime" value="${pnrFeeInfoPayForm.createTime}" />
	<html:hidden property="payTime" value="${pnrFeeInfoPayForm.payTime}" />
	<html:hidden property="payState" styleId="payState" value="${pnrFeeInfoPayForm.payState}" />
	<html:hidden property="auditId" value="${auditId}" />
<% } %>	
</fmt:bundle>
</html:form>
  </div>
  
<div id="audit-info" class="tabContent">
 </br>
<div  style="text-align:center;font-weight:bold;">
			阶段回复信息
		</div>

 <table class="formTable">
 <c:forEach  var='replyrecord' items='${replylist}'>
 	<tr>
 	<td class="label">付款回复人</td>
 	<td class="content">${replyrecord.payUser}</td>
 	<td class="label">阶段回复时间：</td>
	<td class="content">${replyrecord.createTime}</td>
 	</tr>
 	<tr>
 		<td class="label">阶段回复内容：</td>
		<td colspan="3" class="content">${replyrecord.remark}</td>
	</tr>
	<tr>
		<td colspan="4">　</td>
	</tr>
 </c:forEach>
 </table>
 </br>
 </div>
  
  <div id=" " class="tabContent">

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
				<td class="content" colspan="3"s>
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
<%@ include file="/common/footer_eoms.jsp"%>