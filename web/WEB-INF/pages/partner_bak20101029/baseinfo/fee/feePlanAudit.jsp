<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="partnerFeePlans.do?method=auditDo" styleId="partnerFeeAuditForm" method="post">
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">
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
				${partnerFeePlan.name}
				</td>
			</tr>

			<tr>
				<td class="label">
					计划创建人
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerFeePlan.createUser}" beanId="tawSystemUserDao" />
					
				</td>

				<td class="label">
					所在部门
				</td>
				<td class="content">
					<eoms:id2nameDB id="${partnerFeePlan.createDept}" beanId="tawSystemDeptDao" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.payUnit" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlan.payUnitName}
				</td>
				<td class="label">
					收款单位&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlan.collectUnitName}
				</td>
			</tr>


			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.planPayDate" />&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlan.planPayDate}
				</td>

				<td class="label">
					计划付款金额(元)&nbsp;<font color='red'>*</font>
				</td>
				<td class="content">
					${partnerFeePlan.planPayFee}
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerFeePlan.remark" />
				</td>
				<td class="content" colspan="3">
					${partnerFeePlan.remark}
				</td>
			</tr>
			<c:if test="${partnerFeePlan.payStage!=null}">
				<tr>
					<td class="label">
						<fmt:message key="partnerFeePlan.payStage" />
					</td>
					<td class="content" colspan="3">
						${partnerFeePlan.payStage}
					</td>
				</tr>
			</c:if>

			<c:if test="${partnerFeePlan.compactNo != null}">
				<tr>
					<td class="label">
						<fmt:message key="partnerFeePlan.compactNo" />
					</td>
					<td class="content" colspan="3">
					<a href="${app}/partner/contract/ctContentss.do?method=detailByContentId&contentId=${partnerFeePlanForm.contentId }" target="_blank">
						${partnerFeePlanForm.compactNo}
					</a>
					</td>
				</tr>
			</c:if>

	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
			           <select name="auditResult" id="auditResult" alt="allowBlank:false,vtext:'审核结果...'">
			           <option value="">请选择</option>
			           <option id="1" value="1">不通过</option>
			           <option id="2" value="2">通过</option>
			           </select>
		</td>
	</tr>
	
	<tr>
      <td class="label">
        	审核意见
      </td>
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
      		<textarea name="remark" cols="50" id="remark" class="textarea max" ></textarea>										
      </td>
    </tr>
	<html:hidden property="id" value="${id}" />
	</table>
        <input type="submit" value="提交"  class="button" />
</fmt:bundle>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
