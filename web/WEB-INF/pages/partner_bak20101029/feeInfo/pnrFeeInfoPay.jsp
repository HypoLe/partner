<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.partner.feeInfo.webapp.form.PnrFeeInfoPayForm"%>
<%@ page import="java.util.List"%>
<%@ page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoAudit"%>
<%@ page import="java.text.SimpleDateFormat"%>
<style type="text/css">
#toOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('pay-info', '付费信息 ');
        	tabs.addTab('audit-info', '付费审核信息 ');
    		tabs.activate(0);
	});
	
function changeButton(auditResult) {
	if(auditResult==1){
	eoms.$("userTree").hide();
	eoms.$("button").value="驳回";
	}else{
	eoms.$("userTree").show();
	eoms.$("button").value="提交审核";
	}
}
</script>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";
String buttonName = "发起付费";
PnrFeeInfoPayForm pnrFeeInfoPayForm = (PnrFeeInfoPayForm)request.getAttribute("pnrFeeInfoPayForm");
String payState = pnrFeeInfoPayForm.getPayState();
if("1".equals(payState)){
	buttonName = "提交文件";
}else if("2".equals(payState)||"3".equals(payState)){
	buttonName = "提交审核";
}else if("4".equals(payState)){
	buttonName = "完成审核";
}
%>
<div id="info-page">
  <div id="pay-info">
<html:form action="pnrFeeInfoPays.do?method=save" styleId="pnrFeeInfoPayForm" method="post">

<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">

<table class="formTable">
	<caption>
		<div class="header center">费用付款</div>
	</caption>
		<tr>
		<td class="label" style="vertical-align:middle">
			付款信息名称
		</td>
		<td class="content" colspan="3">
			${pnrFeeInfoPayForm.feeName}
			<html:hidden property="feeName" value="${pnrFeeInfoPayForm.feeName}" />
		</td>
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
			${pnrFeeInfoPayForm.planPayTime}
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
			<html:hidden property="remark" value="${pnrFeeInfoPayForm.planRemark}" />
		</td>
		
	</tr>
	<%
	if(payState == null ||"5".equals(payState)){
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
	<tr>
		<td class="label" style="vertical-align:middle">
			付款说明
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" >${pnrFeeInfoPayForm.remark}</textarea>			
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
	<tr>
		<td class="label" style="vertical-align:middle">
			付款说明
		</td>
		<td class="content"  colspan="3">
			${pnrFeeInfoPayForm.remark}
			<html:hidden property="remark" value="${pnrFeeInfoPayForm.remark}" />
		</td>
	</tr>
	<%
	}
	if("1".equals(payState)){	
	%>
	<tr>
		<td class="label" style="vertical-align:middle">
			补充相关文件
		</td>
		<td class="content"  colspan="3">
			<eoms:attachment name="pnrFeeInfoPayForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo" /> 
		</td>
	</tr>
	<%
	}else if(payState!=null){
	%>
		<tr>
		<td class="label" style="vertical-align:middle">
			相关文件
		</td>
		<td class="content"  colspan="3">
			<eoms:attachment name="pnrFeeInfoPayForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo" viewFlag="Y"/>
        	<html:hidden property="accessoriesId" value="${pnrFeeInfoPayForm.accessoriesId}" />
        </td>
	</tr>
	<%
	}
	if("2".equals(payState)||"3".equals(payState)){
	%>
	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
	       <INPUT type="radio" name="result" value="2" checked="true" onclick="changeButton(this.value);">通过 
	       <INPUT type="radio" name="result" value="1" onclick="changeButton(this.value);">不通过
		</td>
	</tr>
	<tr id='userTree'>	
		<%
		String toOrgPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept'}]";
		String toAuditOrgPanels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept'}]";
		%>	

	<td class="label">
	审核人
	</td>
<td class="" colspan="3">
		<eoms:chooser id="toAuditOrg" category="[{id:'toAuditOrg',text:'审核',allowBlank:false,limit:1,vtext:'请选择审核人'}]" panels="<%=toAuditOrgPanels%>"/>
</td>
</tr>
	<%
	}
	if("4".equals(payState)){
	%>
	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
	       <INPUT type="radio" name="result" value="2" checked="true" >通过 
	       <INPUT type="radio" name="result" value="1" >不通过
		</td>
	</tr>
	<%
	}
	if("3".equals(payState)||"4".equals(payState)){
	%>
<tr >	
	<td class="label">
	审核意见
	</td>
<td class="content" colspan="3">
		<textarea name="auditRemark" cols="50" id="auditRemark" class="textarea max" ></textarea>	
</td>
</tr>
	<%
	}
	%>
	</table>
<table>
	<tr>
		<td>
			<input id="button" type="submit" value="<%=buttonName %>"  class="button" />
		</td>
	</tr>
</table>	
	<html:hidden property="id" value="${pnrFeeInfoPayForm.id}" />
	<html:hidden property="feePlanId" value="${pnrFeeInfoPayForm.feePlanId}" />
	<html:hidden property="feeId" value="${pnrFeeInfoPayForm.feeId}" />
	<html:hidden property="createTime" value="${pnrFeeInfoPayForm.createTime}" />
	<html:hidden property="payTime" value="${pnrFeeInfoPayForm.payTime}" />
	<html:hidden property="payState" value="${pnrFeeInfoPayForm.payState}" />
	<html:hidden property="auditId" value="${auditId}" />
	
</fmt:bundle>
</html:form>
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