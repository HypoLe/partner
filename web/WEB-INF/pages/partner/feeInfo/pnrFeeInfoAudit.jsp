<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<html:form action="pnrFeeInfoMains.do?method=auditDo" styleId="pnrFeeInfoAuditForm" method="post">

<table class="formTable">
	<caption>
		<div class="header center">付费计划信息</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同名称
		</td>
		<td class="content" colspan="3">
			${pnrFeeInfoMainForm.contractName}
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			合同编号
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.contractNO}
		</td><%--

		<td class="label" style="vertical-align:middle">
			合同编号：
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.contractNO}
		</td>
	--%></tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			甲方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnit}" beanId="tawSystemDeptDao" />		
			${pnrFeeInfoMainForm.payUnit}
		</td>

		<td class="label" style="vertical-align:middle">
			甲方负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnitUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			乙方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.collectUnit}" beanId="tawSystemDeptDao" />	
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
				阶段付款名称：
			</td>
			<td class="content" colspan="3">
				${plan.planPayName}	
				<html:hidden property="feeId" value="${plan.feeId}" />
			</td>
		</tr>	
		<tr>
			<td class="label" style="vertical-align:middle">
				计划付费时间：
			</td>
			<td class="content">
				${plan.planPayTimeStr}
			</td>
			<td class="label" style="vertical-align:middle">
				计划付款额：
			</td>
			<td class="content">
				${plan.planPayFee}
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
		<tr>
			<th colspan="4"><img align=right src="${app}/images/icons/add.gif" alt="添加工作任务" onclick="javascript:addPlan();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方内容" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
		</tr>
	</c:forEach>
	</table>
	</td>
	</tr>
		<%--<tr>
		<td class="label" style="vertical-align:middle">
			付款描述:
		</td>
		<td class="content"  colspan="3">
			${pnrFeeInfoMainForm.description}
						
			
		</td>

		</tr>

	--%><tr>
		<td class="label" style="vertical-align:middle">
			合同正本：
		</td>
		<td class="content" colspan="3">
           	<eoms:attachment name="pnrFeeInfoMainForm" property="accessoriesId" scope="request" idField="accessoriesId" appCode="feeInfo" viewFlag="Y"/>
		</td>
	</tr>

	
	<tr>
		<td class="label">
			审核结果			
		</td>
		<td class="content" colspan="3">
			       <INPUT type="radio" name="auditResult" value="2" checked="true">通过 
	      		   <INPUT type="radio" name="auditResult" value="1">不通过		
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
</html:form>


<%@ include file="/common/footer_eoms.jsp"%>