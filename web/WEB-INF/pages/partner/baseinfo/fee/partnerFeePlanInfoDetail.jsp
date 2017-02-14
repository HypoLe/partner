<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'partnerFeeInfoForm'});
});

function onchangeText(){
	var planId = document.getElementById('planId').value;
	alert(planId);
	var url = '{app}/fee/partnerFeeInfos.do?method=addPlanFee&planId='+planId;
	location.href=url;
}
</script>

<eoms:xbox id="tree"
	dataUrl="${app}/fee/partnerFeePlans.do?method=getNodesRadioTree"
	rootId="1" rootText='付款计划' valueField="planId" handler="name"
	textField="name" checktype="forums" single="true"></eoms:xbox>

<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="payUnit" handler="payUnitName"
		textField="payUnitName"
		checktype="dept" single="true"		
	  ></eoms:xbox>

<eoms:xbox id="tree" dataUrl="${app}/fee/partnerFeePlans.do?method=getPartnerTree" 
	  	rootId="1" 
	  	rootText='单位' 
	  	valueField="collectUnit" handler="collectUnitName"
		textField="collectUnitName"
		checktype="forums" single="true"		
	  ></eoms:xbox>


<html:form action="/partnerFeeInfos.do?method=savePlanFee"
	styleId="partnerFeeInfoForm" method="post">

	<fmt:bundle
		basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">
		<font color='red'>*</font>号为必填内容<br>
		<br>
		<table class="formTable">

			<tr>
				<td class="label">
					对应付款计划&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="name" styleId="name" styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"
						value="${partnerFeeInfoForm.name}" />
					<input type="hidden" id="planId" name="planId"
						value="${partnerFeeInfoForm.planId }" />
				</td>

				<td class="label">
					收款单位&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="collectUnitName" name="collectUnitName"
						class="text" alt="allowBlank:false,vtext:''"
						value='<eoms:id2nameDB id="${partnerFeeInfoForm.collectUnit}" 	beanId="partnerDeptDao" />'
						readonly="true" />
					<input type="hidden" id="collectUnit" name="collectUnit"
						value="${partnerFeeInfoForm.collectUnit}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					费用录入人
				</td>
				<td class="content">
					<input type="text" id="createUserName" name="createUserName"
						readonly="readonly" class="text"
						value='<eoms:id2nameDB id="${partnerFeeInfoForm.createUser}" beanId="tawSystemUserDao" />'>
					<html:hidden property="createUser" 	value="${partnerFeeInfoForm.createUser}" />
				</td>

				<td class="label">
					录入人所在部门
				</td>
				<td class="content">
					<input type="text" id="createDeptName" name="createDeptName"
						readonly="readonly" class="text"
						value='<eoms:id2nameDB id="${partnerFeeInfoForm.createDept}" beanId="tawSystemDeptDao" />'>
					<html:hidden property="createDept" 	value="${partnerFeeInfoForm.createDept}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					录入时间
				</td>
				<td class="content">
					<html:text property="createDate" styleId="createDate"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" readonly="true"
						value="${partnerFeeInfoForm.createDate}" />
				</td>

				<td class="label">
					<fmt:message key="partnerFeeInfo.payDate" />
					&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payDate" styleId="payDate" styleClass="text"
						readonly="true" onclick="popUpCalendar(this, this);"
						alt="allowBlank:false,vtext:''"
						value="${partnerFeeInfoForm.payDate}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeeInfo.payUnit" />
					&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="payUnitName" name="payUnitName" class="text"
						alt="allowBlank:false,vtext:''"
						value='<eoms:id2nameDB id="${partnerFeeInfoForm.payUnit}"  
				beanId="partnerDeptDao" />'
						readonly="true" />
					<input type="hidden" id="payUnit" name="payUnit"
						value="${partnerFeeInfoForm.payUnit}" />
				</td>

				<td class="label">
					<fmt:message key="partnerFeeInfo.payUser" />
					&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payUser" styleId="payUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32"
						value="${partnerFeeInfoForm.payUser}" />
				</td>

			</tr>


			<tr>
				<td class="label">
					<fmt:message key="partnerFeeInfo.feeType" />
					&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="feeType" styleId="feeType"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32"
						value="${partnerFeeInfoForm.feeType}" />
				</td>

				<td class="label">
					<fmt:message key="partnerFeeInfo.payWay" />
					&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payWay" styleId="payWay"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32"
						value="${partnerFeeInfoForm.payWay}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeeInfo.payNo" />
					&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payNo" styleId="payNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:32"
						value="${partnerFeeInfoForm.payNo}" />
				</td>

				<td class="label">
					费用金额(元)
					&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payFee" styleId="payFee"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:20"
						value="${partnerFeeInfoForm.payFee}" />
				</td>
			</tr>

			<c:if test="${partnerFeeInfoForm.remark!=null}">
				<tr>
					<td class="label">
						<fmt:message key="partnerFeeInfo.remark" />
					</td>
					<td class="content" colspan="3">
						<textarea name="remark" id="remark" cols="50" class="textarea max"
							alt="allowBlank:true,vtext:'',maxLength:500">${partnerFeeInfoForm.remark}</textarea>
					</td>
				</tr>
			</c:if>

			<c:if test="${partnerFeeInfoForm.fileName!=null}">
				<tr>
					<td class="label">
						<fmt:message key="partnerFeeInfo.fileName" />
					</td>
					<td class="content" colspan="3">
						<eoms:attachment name="partnerFeeInfoForm" property="fileName"
							scope="request" idField="fileName" appCode="baseinfo" />
					</td>
				</tr>
			</c:if>

		</table>
	</fmt:bundle>

	<table>
		<tr>
			<!-- 只有自己有权限去操作自己创建的费用信息 -->
			<c:if
				test="${partnerFeeInfoForm.createUser == sessionScope.sessionform.userid}">
				<td>
					<input type="submit" class="btn"
						value="<fmt:message key="button.save"/>" />
					<c:if test="${not empty partnerFeeInfoForm.id}">
						<input type="button" class="btn"
							value="<fmt:message key="button.delete"/>"
							onclick="javascript:if(confirm('您确认删除吗?')){
						var url=eoms.appPath+'/fee/partnerFeeInfos.do?method=remove&id=${partnerFeeInfoForm.id}';
						location.href=url}" />
					</c:if>
				</td>
			</c:if>
		</tr>
	</table>
	<html:hidden property="id" value="${partnerFeeInfoForm.id}" />
	<html:hidden property="isPlan" value="${partnerFeeInfoForm.isPlan}" />
	<input type="hidden" id="plan" name="plan" value="${partnerFeeInfoForm.planId }" /> 
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
