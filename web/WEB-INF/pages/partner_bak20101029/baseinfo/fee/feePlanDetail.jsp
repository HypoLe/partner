<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.parter.baseinfo.fee.webapp.form.PartnerFeePlanForm"%>
<%@ page import="com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeAudit"%>
<%@ page import="com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
PartnerFeePlanForm partnerFeePlanForm = (PartnerFeePlanForm)request.getAttribute("partnerFeePlanForm");
int factPayFee = StaticMethod.nullObject2int(partnerFeePlanForm.getFactPayFee());
int planPayFee = StaticMethod.nullObject2int(partnerFeePlanForm.getPlanPayFee());
String flag = StaticMethod.nullObject2String(request.getAttribute("flag"));

String auditTime = "";
%>

<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">
		
</content>
  <div id="info-page">
  <div id="plan-info" class="tabContent">
	<table class="formTable">
			<caption>
				<div class="header center">
					付款计划
				</div>
			</caption>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.name" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content" colspan="3">
				${partnerFeePlanForm.name}
				</td>
			</tr>

			<tr>
				<td class="label">
					计划创建人
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerFeePlanForm.createUser}" beanId="tawSystemUserDao" />
					
				</td>

				<td class="label">
					所在部门
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerFeePlanForm.createDept}" beanId="tawSystemDeptDao" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.payUnit" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlanForm.payUnitName}
				</td>
				<td class="label">
					收款单位&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlanForm.collectUnitName}
				</td>
			</tr>
				<tr>
					<td class="label">
						<fmt:message key="partnerFeePlan.payStage" />
					</td>
					<td class="content">
						${partnerFeePlanForm.payStage}
					</td>
					<td class="label">
						<fmt:message key="partnerFeePlan.compactNo" />
					</td>
					<td class="content">
					<a href="${app}/partner/contract/ctContentss.do?method=detailByContentId&contentId=${partnerFeePlanForm.contentId }" target="_blank">
						${partnerFeePlanForm.compactNo}
					</a>
						
					</td>
				</tr>
			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.planPayDate" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlanForm.planPayDate}
				</td>

				<td class="label">
					计划付款金额(元)&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlanForm.planPayFee}
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.remark" />
				</td>
				<td class="content" colspan="3">
					${partnerFeePlanForm.remark}
				</td>
			</tr>
						<c:if
				test="${partnerFeePlanForm.factPayDate !=null }">
			<tr>
			<td class="label">
					<fmt:message key="partnerFeePlan.factPayDate" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlanForm.factPayDate}
				</td>

				<td class="label">
					已金额(元)&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlanForm.factPayFee}
				</td>
			</tr>
			</c:if>
	</table>
  </div>
  <div id="audit-info" class="tabContent">
  <%
  	List auditList = (List)request.getAttribute("auditList");
  	PartnerFeeAudit partnerFeeAudit = null;
  for(int i=0;i<auditList.size();i++){
	  partnerFeeAudit = (PartnerFeeAudit)auditList.get(i);
  %>
  <table class="formTable">
  			<caption>
				<div class="header center">
					付款计划审核信息
				</div>
			</caption>
			<tr>
			<td class="label">
					审核者
				</td>
				<td class="content">
				<%
				if("user".equals(partnerFeeAudit.getToOrgType())){
				%>
					<eoms:id2nameDB id="<%=partnerFeeAudit.getToOrgId() %>" beanId="tawSystemUserDao" />	
				<%
				}else if("dept".equals(partnerFeeAudit.getToOrgType())){
				%>
					<eoms:id2nameDB id="<%=partnerFeeAudit.getToOrgId() %>" beanId="tawSystemDeptDao" />
				<%
				}
				%>
				</td>

				<td class="label">
					审核时间
				</td>
				<td class="content">
				<%
				if(partnerFeeAudit.getOperateTime()!=null){
					auditTime = dateFormat.format(partnerFeeAudit.getOperateTime());
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
				if("1".equals(partnerFeeAudit.getAuditResult())){
				%>
					驳回
				<%
				}else if("2".equals(partnerFeeAudit.getAuditResult())){
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
				if(partnerFeeAudit.getRemark() != null){
			%>
			<tr>
				<td class="label">
					审核说明
				</td>
				<td class="content" colspan="3"s>
					<%=partnerFeeAudit.getRemark() %>
				</td>
			</tr>
			<%
				}
			%>
	</table>
	<br>
  <%
  }
  %>
  </div>
  <div id="pay-info" class="tabContent">
  
  	<table class="formTable">
  		 <caption>
				<div class="header center">
					付款信息
				</div>
			</caption>
		 <tr>
			<td width="20%" class="label">付款时间</td>
			<td width="20%" class="label">付款单位</td>
			<td width="20%" class="label">付款人</td>
			<td width="20%" class="label">收款单位</td>
			<td width="20%" class="label">费用金额(元)</td>
		</tr>	
	<%
  	List payList = (List)request.getAttribute("payList");
	PartnerFeeInfo partnerFeeInfo = null;
	String payTime = "";
	int factPay = 0;
  	for(int i=0;i<payList.size();i++){
  		partnerFeeInfo = (PartnerFeeInfo)payList.get(i);
  		payTime = dateFormat.format(partnerFeeInfo.getPayDate());
  	%>
 		 	<tr>
			<td  width="20%"><%=payTime %></td>
			<td  width="20%"><eoms:id2nameDB id="<%=partnerFeeInfo.getPayUnit() %>" beanId="tawSystemDeptDao" /></td>
			<td  width="20%"><%=partnerFeeInfo.getPayUser() %></td>
			<td  width="20%"><eoms:id2nameDB id="<%=partnerFeeInfo.getCollectUnit() %>" beanId="partnerDeptDao" /></td>
			<td  width="20%"><%=partnerFeeInfo.getPayFee() %></td>
			</tr>
  	<%
  		}
  	%>
  	<tr>
	<td  width="20%" colspan="5"><font color='red'>计划付款(元)：<%=planPayFee %>  实际付款总额(元)：<%=factPayFee %> <span id = "surplus"></span></font></td>
	</tr>
  	</table>
			<c:if
				test="${partnerFeePlanForm.createUser != sessionScope.sessionform.userid && partnerFeePlanForm.payState ==2}">
				<br>
					<font color='red'>只有计划制定者才可以录入的费用信息</font>
				
			</c:if>
			<!-- 只有计划制定者才可以录入的费用信息 -->
			<c:if
				test="${partnerFeePlanForm.createUser == sessionScope.sessionform.userid && partnerFeePlanForm.payState ==2}">
				
					<input type="button" class="btn" value="付费" class="button" onClick="pay();"/>
				
			</c:if>
<div id="payForm" style="display:none">
<html:form action="/partnerFeeInfos.do?method=savePlanFee" styleId="partnerFeeInfoForm" method="post"> 

<font color='red'>*</font>号为必填内容<br>	<br>
<table class="formTable">

	<tr>
		<td class="label">
			对应付款计划&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<html:text property="name" styleId="name"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" 
						 value="${partnerFeePlanForm.name}"  readonly="true"/>
			<input type="hidden" id="planId"  name="planId" value="${partnerFeePlanForm.id }" />
		</td>
		
		<td class="label">
			收款单位&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<input type="text"   id="collectUnitName" name="collectUnitName" class="text"  
				 alt="allowBlank:false,vtext:''"    value='<eoms:id2nameDB id="${partnerFeePlanForm.collectUnit}"  
				beanId="partnerDeptDao" />'   readonly="true"/>
			<input type="hidden" id="collectUnit" name="collectUnit" value="${partnerFeePlanForm.collectUnit}"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.payUnit" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input type="text"   id="payUnitName" name="payUnitName" class="text"  alt="allowBlank:false,vtext:''" 
				value='<eoms:id2nameDB id="${partnerFeePlanForm.payUnit}"  
				beanId="tawSystemDeptDao" />'   readonly="true"/>
			<input type="hidden" id="payUnit" name="payUnit" value="${partnerFeePlanForm.payUnit}"/>
		</td>
		
		<td class="label">
			<fmt:message key="partnerFeeInfo.payUser" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payUser" styleId="payUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" 
						 value="" />
		</td>
		
	</tr>


	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.feeType" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="feeType" styleId="feeType"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" 
						 value="" />
		</td>
	
		<td class="label">
			<fmt:message key="partnerFeeInfo.payWay" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payWay" styleId="payWay"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" 
						 value="" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.payNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payNo" styleId="payNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:32" 
						 value="" />
		</td>
		
		<td class="label">
			费用金额(元)&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payFee" styleId="payFee"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:20" 
						 value="" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.payDate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="payDate" styleId="payDate"
						styleClass="text" readonly="true"
			           	onclick="popUpCalendar(this, this);"
						alt="allowBlank:false,vtext:''" 
						 value="" />
		</td>
		 
		<td class="label">
			<fmt:message key="partnerFeeInfo.remark" />
		</td>
		<td class="content" >
			<textarea name="remark" id="remark" 
			          cols="50" class="textarea max" 
			          alt="allowBlank:true,vtext:'',maxLength:500" ></textarea>	
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="partnerFeeInfo.fileName" />
		</td>
		<td id='file' class="content" colspan="3">
			<eoms:attachment idList="" idField="fileName" appCode="baseinfo"  name="partnerFeeInfoForm" property="fileName"/> 
		
		</td>
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="确定" />

		</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerFeeInfoForm.id}" />

</html:form>
</div>
  </div>
  </div>

</fmt:bundle>
<script type="text/javascript">
	var readTree;
	Ext.onReady(function(){
			v = new eoms.form.Validation({form:'partnerFeeInfoForm'});
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('plan-info', '付款计划信息 ');
        	tabs.addTab('audit-info', '计划审核信息 ');
        	tabs.addTab('pay-info', '付款信息 ');
    		tabs.activate(0);
	});
</script>
<script type="text/javascript">
function pay(){
	 var factPay =<%=factPayFee %>;
	 var planPay =<%=planPayFee %>;
	 if(factPay<planPay){
	 	 surplusPay = planPay-factPay;
		 document.getElementById("payForm").style.display=""; 	 
		 document.getElementById("UIFrame1-fileName").style.height="80%";
		 document.getElementById("surplus").innerHTML = " 还需付款(元)："+surplusPay;
	 }else{
		 alert("已付金额已超过计划金额"); 
	 }
    }
	document.getElementById("UIFrame1-fileName").style.height="80%";
</script>
<%@ include file="/common/footer_eoms.jsp"%>
