<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
#test-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerFeePlanForm'});
});
	function getContractUrl(){
	 var collectUnit =  document.getElementById("collectUnit").value;
	 var payUnit =  document.getElementById("payUnit").value;
	 window.open ('${app}/fee/partnerFeePlans.do?method=queryContractDo&partyBId='+collectUnit+'&partyAId='+payUnit,'newwindow','height=600,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }
</script>

<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="payUnit" handler="payUnitName"
		textField="payUnitName"
		checktype="dept" single="true"		
	  ></eoms:xbox>
	  
<eoms:xbox id="tree" dataUrl="${app}/partner/contract/ctContentss.do?method=getPartnerTree" 
	  	rootId="1" 
	  	rootText='单位' 
	  	valueField="collectUnit" handler="collectUnitName"
		textField="collectUnitName"
		checktype="forums" single="true"		
	  ></eoms:xbox>


<html:form action="/partnerFeePlans.do?method=save" styleId="partnerFeePlanForm" method="post"> 
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center">付款计划</div>
	</caption>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.name" />&nbsp;<font color='red'>*</font>
					
				</td>
				<td class="content" colspan="3">
					<html:text property="name" styleId="name" styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  
						value="${partnerFeePlanForm.name}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.payUnit" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="payUnitName" name="payUnitName" class="text"
						value='${partnerFeePlanForm.payUnitName}'
						alt="allowBlank:false,vtext:''"/>
					<input type="hidden" id="payUnit" name="payUnit"
						value="${partnerFeePlanForm.payUnit}" />
				</td>
				<td class="label">
					收款单位&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="collectUnitName" name="collectUnitName" class="text"  readonly="true"
						value='${partnerFeePlanForm.collectUnitName}'
						alt="allowBlank:false,vtext:''"/>
					<input type="hidden" id="collectUnit" name="collectUnit"
						value="${partnerFeePlanForm.collectUnit}" />
				</td>
			</tr>
		<tr>
		<td class="label">
			<fmt:message key="partnerFeePlan.payStage" />
		</td>
		<td class="content">
			<html:text property="payStage" styleId="payStage"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',maxLength:50" 
						 value="${partnerFeePlanForm.payStage}" />
		</td>
	
		<td class="label">
			<fmt:message key="partnerFeePlan.compactNo" />
		</td>
		<td class="content">
			<html:text property="compactNo" styleId="compactNo"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',vtype:'number',maxLength:20" 
						 value="${partnerFeePlanForm.compactNo}" />
			
			<html:hidden property="contentId" value="${partnerFeePlanForm.contentId}"/>
			<input type="button" name="contractUrl" id="contractUrl" value="选择合同" class="btn" onclick="getContractUrl();"/>
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="partnerFeePlan.planPayDate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="planPayDate" styleId="planPayDate"
						styleClass="text" readonly="true"
			           onclick="popUpCalendar(this, this);"
						alt="allowBlank:false,vtext:''" value="${partnerFeePlanForm.planPayDate}" />
		</td>
	
		<td class="label">
			计划付款金额(元)&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="planPayFee" styleId="planPayFee"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:20" 
						 value="${partnerFeePlanForm.planPayFee}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="partnerFeePlan.remark" />
		</td>
		<td class="content" colspan="3">
			<textarea name="remark" id="remark" 
			          cols="50" class="textarea max" 
			          alt="allowBlank:true,vtext:'',maxLength:500">${partnerFeePlanForm.remark}</textarea>	
		</td>
	</tr>

<tr id='userTree'>		
<td class="label">
	审核人
</td>
<td class="" colspan="3">
		<%
		String panels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept'}]";
		%>
		<eoms:chooser id="test" category="[{id:'toOrg',text:'审核',allowBlank:false,limit:1,vtext:'请选择派发人'}]" panels="<%=panels%>"/>
</td>
</tr>
</table>	
</fmt:bundle>

<table>
	<tr>
			<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty partnerFeePlanForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url=eoms.appPath+'/fee/partnerFeePlans.do?method=remove&id=${partnerFeePlanForm.id}';
						location.href=url}"	/>
			</c:if>
			</td>
	</tr>
</table>
<html:hidden property="id" value="${partnerFeePlanForm.id}" />
<html:hidden property="payStatus" value="${partnerFeePlanForm.payStatus}"/>
<html:hidden property="createUser" value="${partnerFeePlanForm.createUser}"/>
<html:hidden property="createDept" value="${partnerFeePlanForm.createDept}" />
<html:hidden property="createDate" value="${partnerFeePlanForm.createDate}"/>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
