<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>

</head>
<body><div align="center"><h2>出库单反向审核</h2></div>
	<body>
		
		<form action="storeOut.do?method=deAudit" method="post">
			<table class="formTable">
				<tr>
				<input type="hidden" name="id" value="${storeOut.id}" readOnly="true">
					<td class="label">
						出库日期
						<input type="text" name="storeBillingDate" value="${storeOut.storeBillingDate}" readOnly="true">
					</td>

					<td class="label">
						供应商
						<input type="text" name="storeCompany" value="${storeOut.storeCompany}" readOnly="true">
					</td>
				</tr>
				<tr>
					<td class="label" >
						仓库
						<input type="text" naem="storeSname" value="${storeOut.storeSname}" readOnly="true">
						
					</td>

					<td class="label">
						经办人
						<input type="text" name="storeRequisitioner" value="${storeOut.storeRequisitioner}" readOnly="true">
					</td>
				</tr>
				<tr>

					<td class="label">
						制单人
						<input type="text" name="storeMakeBillPeople" value="${storeOut.storeMakeBillPeople}" readOnly="true">
					</td>
					<td class="label">
						备注
						<input type="text" name="remark" value="${storeOut.storeRemark}" readOnly="true">
					</td>
				</tr>

				
			</table>
<br />

		
		<br/>
		<br/>
		
		<display:table name="list" cellspacing="0" cellpadding="0" id="list"
			sort="list" class="table" pagesize="10">
			
			<display:column property="encode" sortable="true"
				headerClass="sortable" title="材料编码" />
			<display:column property="materialName" sortable="true"
				headerClass="sortable" title="材料名称" />

			<display:column property="specification" sortable="true"
				headerClass="sortable" title="材料规格" />
			<display:column property="unit" sortable="true"
				headerClass="sortable" title="单位" />
			<display:column property="materialAmount" sortable="true"
				headerClass="sortable" title="数量" />
			<display:column property="materialPrice" sortable="true"
				headerClass="sortable" title="单价" />
			<display:column
				headerClass="sortable" title="总额" >
				 
				</display:column>
			<display:column property="remark" sortable="true"
				headerClass="sortable" title="备注" />
			

		</display:table>
		<br/>
	审核人<input type="text" name="storeExaminePeople">
			<input type="submit" value="确定审核">

		</form>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>