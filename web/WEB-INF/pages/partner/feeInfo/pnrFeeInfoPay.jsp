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
<script type="text/javascript">
	Ext.onReady(function(){
			var tabs = new Ext.TabPanel('info-page');
        	tabs.addTab('pay-info', '付费信息 ');
        
    		tabs.activate(0);
	});
	
function changePayState(newPayState) {
	// document.getElementById("payState").value = newPayState;
}

function validateForm() {
	var payState = document.getElementById("payState").value;
	if (payState == 0 || payState == 2 ) {
		// 转换为等待甲方负责人审核状态
		document.getElementById("payState").value = 1;		
	} else if (payState == 1 ) {
		// 甲方审核人通过或是不通过
		if(document.getElementsByName('result')[0].checked) {
			document.getElementById("payState").value = document.getElementsByName('result')[0].value;
		} else {
			document.getElementById("payState").value = document.getElementsByName('result')[1].value;		
		}
		
	} else if (payState == 3 ) {
		if(document.getElementById("noticeFiles").value==""){
			alert("请上传付款通知书！");
			return false;
		}
		if(document.getElementById("requisitionFiles").value==""){
			alert("请上传付款申请！");
			return false;
		}
		if(document.getElementById("contractFiles").value==""){
			alert("请上传合同文本关键页！");
			return false;
		}
		if(document.getElementById("invoiceFiles").value==""){
			alert("请上传相应金额发票！");
			return false;
		}
		// 转换为等待付款负责人审核状态
		document.getElementById("payState").value = 4;		
	}  else if (payState == 4 ) {
		// 甲方审核人通过或是不通过
		if(document.getElementsByName('result')[0].checked) {
			document.getElementById("payState").value = document.getElementsByName('result')[0].value;
		} else {
			document.getElementById("payState").value = document.getElementsByName('result')[1].value;		
		}
	} else if (payState == 5 ) {
		if(document.getElementById("noticeFiles").value==""){
			alert("请上传付款通知书！");
			return false;
		}
		if(document.getElementById("requisitionFiles").value==""){
			alert("请上传付款申请！");
			return false;
		}
		if(document.getElementById("contractFiles").value==""){
			alert("请上传合同文本关键页！");
			return false;
		}
		if(document.getElementById("invoiceFiles").value==""){
			alert("请上传相应金额发票！");
			return false;
		}
		// 转换为等待付款负责人审核状态
		document.getElementById("payState").value = 4;		
	} else if (payState == 6 ) {
		// 确认完成了付款
		document.getElementById("payState").value = 7;		
	}
	
	
}
</script>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String auditTime = "";
String buttonName = "确定";
PnrFeeInfoPayForm pnrFeeInfoPayForm = (PnrFeeInfoPayForm)request.getAttribute("pnrFeeInfoPayForm");
PnrFeeInfoPlan pnrFeeInfoPlan = (PnrFeeInfoPlan)request.getAttribute("pnrFeeInfoPlan");
 String payState = pnrFeeInfoPlan.getPlanState();
if("0".equals(payState) || "2".equals(payState) ){
	buttonName = "发给负责人确认";
}else if("3".equals(payState)){
	buttonName = "提交审核材料";
}else if("4".equals(payState)){
	buttonName = "确定";
} else if ("5".equals(payState)) {
	buttonName = "再次提交审核材料";
} else if ("6".equals(payState)) {
	buttonName = "确认完成付款";
}
%>
<div id="info-page">
  <div id="pay-info">
<html:form action="pnrFeeInfoPays.do?method=save" styleId="pnrFeeInfoPayForm" method="post" onsubmit="return validateForm();">
 
<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">

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
		
			${pnrFeeInfoPayForm.contractNO}
			<html:hidden property="contractNO" value="${pnrFeeInfoPayForm.contractNO}" />
			 
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
	<c:if test="${agreementList!=null&&evaPlanFlag=='eva'}">
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
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${pnrFeeInfoPayForm.payUser}" />
			
			<html:hidden property="payUser" value="${pnrFeeInfoPayForm.payUser}" />
			<html:hidden property="collectUnitUser" value="${pnrFeeInfoPayForm.collectUnitUser}" />
		</td>
		<td class="label" style="vertical-align:middle">
			实际付款额
		</td>
		<td class="content">
			${finalScore}
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
			<eoms:id2nameDB id="${pnrFeeInfoPayForm.payUser}" beanId="tawSystemUserDao" />
			<html:hidden property="payUser" value="${pnrFeeInfoPayForm.payUser}" />
			<html:hidden property="collectUnitUser" value="${pnrFeeInfoPayForm.collectUnitUser}" />
		</td>
		<td class="label" style="vertical-align:middle">
			实际付款额(万元)
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
			付款申请说明
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" ></textarea>			
		</td>
	</tr>
	 
	<%
	}
	 
	// 等待甲方审核通过允许付款
	if("1".equals(payState)){
	%>
	
			<tr>
		<td class="label" style="vertical-align:middle">
			付款申请说明
		</td>
		<td class="content"  colspan="3">
			${pnrFeeInfoPayForm.remark}			
		</td>
	</tr>
	
		<tr>
		<td class="label" style="vertical-align:middle">
			审核说明
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" > </textarea>			
		</td>
	</tr>
	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
	       <INPUT type="radio" name="result" value="3" checked="true" onclick="changePayState(this.value);">通过 
	       <INPUT type="radio" name="result" value="2" onclick="changePayState(this.value);">不通过
		</td>
	</tr>
	 
	<%
	}
	
	// 甲方负责人审核不通过不允许付款
	if ("2".equals(payState)) {
	%>
			<tr>
		<td class="label" style="vertical-align:middle">
			审核意见
		</td>
		<td class="content"  colspan="3">
				${pnrFeeInfoPayForm.remark}				
		</td>
	</tr>
	<tr>
		<td class="label">
			当前状态		
		</td>
		<td class="content" colspan="3">
	          甲方负责人不通过付款申请
		</td>
	</tr>		
	<%
	}
	
	// 乙方提交审核材料
	if("3".equals(payState)){
	%>

		<tr> 
		<td class="label" style="vertical-align:middle">
			提交材料说明
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" > </textarea>			
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			付款通知书:
			<br><br>
			<a href="${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=00000000000000000000000000000001" target="_blank">
			下载模板
			</a> 
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="noticeFiles" scope="request" idField="noticeFiles" appCode="feeInfo" /> 	
		</td>
		<td class="label" style="vertical-align:middle">
			付款申请:
			<br><br>
			<a href="${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=00000000000000000000000000000002" target="_blank">
			下载模板
			</a> 
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="requisitionFiles" scope="request" idField="requisitionFiles" appCode="feeInfo" /> 	
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同文本关键页:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="contractFiles" scope="request" idField="contractFiles" appCode="feeInfo" /> 	
		</td>
		<td class="label" style="vertical-align:middle">
			相应金额发票:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="invoiceFiles" scope="request" idField="invoiceFiles" appCode="feeInfo" /> 	
		</td>
	</tr>
	<%
	}
	 
	
	// 付款负责人审核材料
	if("4".equals(payState)){
	%>

		<tr>
		<td class="label" style="vertical-align:middle">
			提交的材料说明
		</td>
		<td class="content"  colspan="3">
			 	${pnrFeeInfoPayForm.remark}			
		</td>
	</tr>	
	<tr>
		<td class="label" style="vertical-align:middle">
			付款通知书:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="noticeFiles" scope="request" idField="noticeFiles" appCode="feeInfo" viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			付款申请:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="requisitionFiles" scope="request" idField="requisitionFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同文本关键页:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="contractFiles" scope="request" idField="contractFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			相应金额发票:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="invoiceFiles" scope="request" idField="invoiceFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			审核说明
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" > </textarea>			
		</td>
	</tr>
	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
	       <INPUT type="radio" name="result" value="6" checked="true" onclick="changePayState(this.value);">通过 
	       <INPUT type="radio" name="result" value="5" onclick="changePayState(this.value);">不通过
		</td>
	</tr>	
	<%
	}
		
	// 付款负责人退回乙方审核材料
	if("5".equals(payState)){
	%>

	
		<tr>
		<td class="label" style="vertical-align:middle">
			提交材料说明
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" > 	</textarea>			
		</td>
	</tr>	
	<tr>
		<td class="label" style="vertical-align:middle">
			付款通知书:
			<br><br>
			<a href="${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=00000000000000000000000000000001" target="_blank">
			下载模板
			</a> 
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="noticeFiles" scope="request" idField="noticeFiles" appCode="feeInfo" /> 	
		</td>
		<td class="label" style="vertical-align:middle">
			付款申请:
			<br><br>
			<a href="${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=00000000000000000000000000000002" target="_blank">
			下载模板
			</a> 
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="requisitionFiles" scope="request" idField="requisitionFiles" appCode="feeInfo" /> 	
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同文本关键页:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="contractFiles" scope="request" idField="contractFiles" appCode="feeInfo" /> 	
		</td>
		<td class="label" style="vertical-align:middle">
			相应金额发票:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="invoiceFiles" scope="request" idField="invoiceFiles" appCode="feeInfo" /> 	
		</td>
	</tr>

		<tr>
		<td class="label" style="vertical-align:middle">
			审核说明
		</td>
		<td class="content"  colspan="3">
			${pnrFeeInfoPayForm.remark}				
		</td>
	</tr>
	<tr>
		<td class="label">
			当前状态			
		</td>
		<td class="content" colspan="3">
	           材料审核不通过
		</td>
	</tr>	
	<%
	}
	// 付款负责人审核通过了材料 开始付款
	if("6".equals(payState)){
	%>

		<tr>
		<td class="label" style="vertical-align:middle">
			付款通知书:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="noticeFiles" scope="request" idField="noticeFiles" appCode="feeInfo" viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			付款申请:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="requisitionFiles" scope="request" idField="requisitionFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同文本关键页:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="contractFiles" scope="request" idField="contractFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			相应金额发票:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="invoiceFiles" scope="request" idField="invoiceFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			提交材料说明
		</td>
		<td class="content"  colspan="3">
			<textarea name="remark" id="remark" maxLength="2000" rows="3" style="width:90%;" alt="allowBlank:false,vtext:''" > 	</textarea>			
		</td>
	</tr>	
 	<tr>
		<td class="label" style="vertical-align:middle">
			补充相关文件
		</td>
		<td class="content"  colspan="3">
			<eoms:attachment name="pnrFeeInfoPayForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo"   /> 
		</td>
	</tr>
 
	<tr>
		<td class="label">
			当前状态		
		</td>
		<td class="content" colspan="3">
	           材料审核通过
		</td>
	</tr>	
	<%
	}// 付款全部完成
	if("7".equals(payState)){
	%>

	
		<tr>
		<td class="label" style="vertical-align:middle">
			提交材料说明
		</td>
		<td class="content"  colspan="3">
			${pnrFeeInfoPayForm.remark }
		</td>
	</tr>	
		<tr>
		<td class="label" style="vertical-align:middle">
			付款通知书:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="noticeFiles" scope="request" idField="noticeFiles" appCode="feeInfo" viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			付款申请:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="requisitionFiles" scope="request" idField="requisitionFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同文本关键页:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="contractFiles" scope="request" idField="contractFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
		<td class="label" style="vertical-align:middle">
			相应金额发票:
		</td>
		<td class="content">
		<eoms:attachment name="pnrFeeInfoPayForm" property="invoiceFiles" scope="request" idField="invoiceFiles" appCode="feeInfo"  viewFlag="Y"/> 	
		</td>
	</tr>
 	<tr>
		<td class="label" style="vertical-align:middle">
			补充相关文件
		</td>
		<td class="content"  colspan="3">
			<eoms:attachment name="pnrFeeInfoPayForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo" viewFlag="Y" /> 
		</td>
	</tr>
 
	<tr>
		<td class="label">
			当前状态		
		</td>
		<td class="content" colspan="3">
	           完成当前阶段付款
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
			<input id="button" type="button" value="查看流程图"  class="button"  onclick="window.open('${app}/partner/baseinfo/pnrFlow.do?method=showFlow&id=${pnrFeeInfoPayForm.feePlanId}&flowType=feeinfo','','toolbar=no,scrollBars=no,width=800,height=470')"/>
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
	
</fmt:bundle>
</html:form>
  </div>
  
  
  
<%@ include file="/common/footer_eoms.jsp"%>