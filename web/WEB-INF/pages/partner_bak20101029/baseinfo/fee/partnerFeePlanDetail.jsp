<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
//Ext.onReady(function() {
	//v = new eoms.form.Validation({form:'partnerFeePlanForm'});
//});
	function getContractUrl(){
	 var collectUnit =  document.getElementById("collectUnit").value;
	 var payUnit =  document.getElementById("payUnit").value;
	 window.open ('${app}/fee/partnerFeePlans.do?method=queryContractDo&partyBId='+collectUnit+'&partyAId='+payUnit,'newwindow','height=600,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }
</script>

<eoms:xbox id="tree" dataUrl="${app}/fee/partnerFeePlans.do?method=getPartnerTree" 
	  	rootId="1" 
	  	rootText='单位' 
	  	valueField="collectUnit" handler="collectUnitName"
		textField="collectUnitName"
		checktype="forums" single="true"		
	  ></eoms:xbox>

<html:form action="/partnerFeePlans.do?method=save"
	styleId="partnerFeePlanForm" method="post">
	<fmt:bundle
		basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">
		<font color='red'>*</font>号为必填内容
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
					<html:text property="name" styleId="name" styleClass="text max"
						alt="allowBlank:false,vtext:'',maxLength:50"
						value="${partnerFeePlanForm.name}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					计划创建人
				</td>
				<td class="content">
					<input type="text" id="createUserName" name="createUserName"
						readonly="readonly" class="text"
						value='<eoms:id2nameDB id="${partnerFeePlanForm.createUser}" beanId="tawSystemUserDao" />'>
					<html:hidden property="createUser" value="${partnerFeePlanForm.createUser}" />
				</td>

				<td class="label">
					所在部门
				</td>
				<td class="content">
					<input type="text" id="createDeptName" name="createDeptName"
						readonly="readonly" class="text"
						value='<eoms:id2nameDB id="${partnerFeePlanForm.createDept}" beanId="tawSystemDeptDao" />'>
					<html:hidden property="createDept" value="${partnerFeePlanForm.createDept}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.payUnit" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="payUnitName" name="payUnitName" class="text"
						value='${partnerFeePlanForm.payUnitName}'
						alt="allowBlank:false'" />
					<input type="hidden" id="payUnit" name="payUnit"
						value="${partnerFeePlanForm.payUnit}" />
				</td>
				<td class="label">
					收款单位&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" id="collectUnitName" name="collectUnitName" class="text"
						value='${partnerFeePlanForm.collectUnitName}'
						alt="allowBlank:false'" readonly="true" />
					<input type="hidden" id="collectUnit" name="collectUnit"
						value="${partnerFeePlanForm.collectUnit}" />
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
					<textarea name="remark" id="remark" cols="50" class="textarea max"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:500">${partnerFeePlanForm.remark}</textarea>
				</td>
			</tr>
			<c:if test="${partnerFeePlanForm.payStage!=null}">
				<tr>
					<td class="label">
						<fmt:message key="partnerFeePlan.payStage" />
					</td>
					<td class="content" colspan="3">
						<html:text property="payStage" styleId="payStage"
							styleClass="text max"
							alt="allowBlank:false,vtext:'',maxLength:50"
							value="${partnerFeePlanForm.payStage}" />
					</td>
				</tr>
			</c:if>

			<c:if test="${partnerFeePlanForm.compactNo != null}">
				<tr>
					<td class="label">
						<fmt:message key="partnerFeePlan.compactNo" />
					</td>
					<td class="content" colspan="3">
						<html:text property="compactNo" styleId="compactNo"
							styleClass="text max"
							alt="allowBlank:false,vtext:'',vtype:'number',maxLength:20"
							value="${partnerFeePlanForm.compactNo}" />
						<html:hidden property="contentId" value="${partnerFeePlanForm.contentId}"/>
			<input type="button" name="contractUrl" id="contractUrl" value="选择合同" class="btn" onclick="getContractUrl();"/>
					</td>
				</tr>
			</c:if>

			<c:if test="${partnerFeePlanForm.payStatus ==1}">
				<tr>
					<td class="label">
						<fmt:message key="partnerFeePlan.factPayDate" />
					</td>
					<td class="content">
						<html:text property="factPayDate" styleId="factPayDate"
							styleClass="text" readonly="true" alt="allowBlank:false,vtext:''"
							value="${partnerFeePlanForm.factPayDate}" />
					</td>

					<td class="label">
						实际付款费用金额(元)
					</td>
					<td class="content">
						<html:text property="factPayFee" styleId="factPayFee"
							styleClass="text medium" readonly="true"
							alt="allowBlank:false,vtext:'',vtype:'number',maxLength:20"
							value="${partnerFeePlanForm.factPayFee}" />
					</td>
				</tr>

				<tr>
					<td class="label">
						<fmt:message key="partnerFeePlan.payWay" />
					</td>
					<td class="content">
						<html:text property="payWay" styleId="payWay"
							styleClass="text medium" readonly="true"
							alt="allowBlank:false,vtext:'',maxLength:32"
							value="${partnerFeePlanForm.payWay}" />
					</td>

					<td class="label">
						<fmt:message key="partnerFeePlan.payUser" />
					</td>
					<td class="content">
						<html:text property="payUser" styleId="payUser"
							styleClass="text medium" readonly="true"
							alt="allowBlank:false,vtext:'',maxLength:32"
							value="${partnerFeePlanForm.payUser}" />
					</td>
				</tr>
			</c:if>
		</table>
	</fmt:bundle>
	<table>
		<tr>
			<!-- 只有自己有权限去操作自己创建的费用计划信息 -->
			<c:if
				test="${partnerFeePlanForm.createUser == sessionScope.sessionform.userid}">
				<td>
					<!-- 对于已经付款的付款的计划 不允许再进行修改操作 。只有没有付款的付款计划才能进行修改操作-->
					<c:if test="${partnerFeePlanForm.payStatus==0}">
						<input type="submit" class="btn" value="保存" />

						<c:if test="${not empty partnerFeePlanForm.id}">
							<input type="button" class="btn"
								value="<fmt:message key="button.delete"/>"
								onclick="javascript:if(confirm('您确认删除吗?')){
						var url=eoms.appPath+'/fee/partnerFeePlans.do?method=remove&id=${partnerFeePlanForm.id}';
						location.href=url}" />
						</c:if>
					</c:if>
				</td>
			</c:if>
		</tr>
	</table>
	<html:hidden property="id" value="${partnerFeePlanForm.id}" />
	<input type="hidden" id="createDate" name="createDate" value="${partnerFeePlanForm.createDate}" />
	<html:hidden property="payStatus"
		value="${partnerFeePlanForm.payStatus}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
