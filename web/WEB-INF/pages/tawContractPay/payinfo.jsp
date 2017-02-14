<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.lang.*,java.util.List,com.boco.eoms.parter.baseinfo.contract.model.TawContractPay,java.math.BigDecimal;
	"%>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawContractPayForm'});
});
</script>
<%
	List list = (List) request.getAttribute("payinfoList");
	BigDecimal b = new BigDecimal("0");
	for (int i = 0; i < list.size(); i++) {
		BigDecimal b1 = new BigDecimal(((TawContractPay) list.get(i)).getMoney());
		b = b.add(b1);		
	}
	String t =b.toString();
	
%>
<html:form action="/tawContractPays.do?method=payadd"
	styleId="tawContractPayForm" method="post">

	<fmt:bundle
		basename="com/boco/eoms/parter/baseinfo/config/applicationResource-tawcontractpay">



		<display:table name="payinfoList" cellspacing="0" cellpadding="0"
			id="payinfoList" pagesize="${pageSize}" class="table tawContractList"
			export="false"
			requestURI="${app}/contract/tawContractPays.do?method=payinfo"
			sort="list" partialList="true" size="resultSize" varTotals="totalMap">

			<display:column property="payer" titleKey="tawContractPay.payer" />

			<display:column property="money" sortable="true"
				headerClass="sortable" titleKey="tawContractPay.money" />

			<display:column property="paytime" sortable="true"
				headerClass="sortable" titleKey="tawContractPay.paytime" />

			<display:column property="remark" sortable="true"
				headerClass="sortable" titleKey="tawContractPay.remark" />

			<display:footer>
				<tr>
					<td colspan="1">
						<B>总计</B>
					</td>
					<td colspan="1">
						<B><%=t%></B>
					</td>
					<td>

					</td>
					<td>

					</td>
				</tr>
			</display:footer>

		</display:table>
	</fmt:bundle>

	<html:hidden property="contractid"
		value="${tawContractPayForm.contractid}" />
	<html:hidden property="id" value="${tawContractPayForm.id}" />
	<html:hidden property="contractname"
		value="${tawContractPayForm.contractname}" />
	<table>
		<tr>
			<td>
				<input type="submit" class="btn" value="付款" />

			</td>
		</tr>
	</table>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>