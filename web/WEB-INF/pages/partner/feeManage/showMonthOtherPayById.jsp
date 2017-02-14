<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>


<table class="table">
	<tr>
		<td  class="label">
			费用类型
		</td>
		<td>

					<c:if test="${monthOtherPay.costType eq '1'}">奖励</c:if>
					<c:if test="${monthOtherPay.costType eq '2'}">补贴</c:if>
					<c:if test="${monthOtherPay.costType eq '3'}">报销</c:if>
					<c:if test="${monthOtherPay.costType eq '4'}">其他</c:if>
		</td>
	
		<td class="label">
				应付款（元）：
			</td>
		<td>
		${monthOtherPay.shdMnyAmt}
		</td></tr>
		<tr>
				<td class="label">
				实付款（元）：
			</td>
		<td>
		 ${monthOtherPay.realMnyAmt} 
		</td>
				<td class="label">
				 款项依据：
			</td>
			<td class="content">
				<pre>${monthOtherPay.costVoucher}</pre>
			</td>
		
		</tr>
</table>
<%@ include file="/common/footer_eoms.jsp"%>