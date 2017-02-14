<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8"%>
<html>

	<body>
		<form action="storeInventory.do?method=select" method="post">
		<font color="red">${message}</font><div align="center">
				<h2>
					库存查询
				</h2>
			</div>
			<table class="formTable">
				<tr>
					<td class="label">
						仓库
					</td>
					<td>
						<select name="storeSid" id="storeSid">
							<option value="">请选择仓库</option>
							<c:forEach var="store" items="${storeList}" >
							<option value="${store.id}">
								${store.name}
							</option>
							</c:forEach>
						</select>
					</td class="label">

					<td class="label">
						材料名称
					</td>
					<td>
						<input type="text" name="materialName">
					</td>
				</tr>
				<tr>
					<td class="label">
						材料编码
					</td>
					<td>
						<input type="text" name="materialEncode">
					</td>

					<td></td>
					<td></td>
				</tr>
			</table>
			<br />
			<input type="submit" value="查询">
		</form>

		<display:table name="list" cellspacing="0" cellpadding="0" id="list"
			sort="list" class="table" pagesize="${pageSize}" export="true"
			requestURI="${app}/materials/storeInventory.do?method=select"
			 size="total">

			<display:column property="materialName" sortable="true"
				headerClass="sortable" title="材料名称" />
			
			
			<display:column property="materialEncode" sortable="true"
				headerClass="sortable" title="材料编码" />
			<display:column property="onhand" sortable="true"
				headerClass="sortable" title="库存数量" />
			
			<display:column property="materialSpecification" sortable="true"
				headerClass="sortable" title="材料规格" />
				<display:column property="materialUnit" sortable="true"
				headerClass="sortable" title="材料单位" />
			<display:column property="materialCategoryNum" sortable="true"
				headerClass="sortable" title="材料类别号" />
			
			
				<display:column property="materialPrice" sortable="true"
				headerClass="sortable" title="单价" />
				<display:column property="storeSname" sortable="true"
				headerClass="sortable" title="所属仓库" />
				<display:column property="materialCategoryDetail" sortable="true"
				headerClass="sortable" title="生产厂家" />
			<display:column property="totalAmount" sortable="true"
				headerClass="sortable" title="总金额" />
<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
		<display:setProperty name="export.rtf" value="false"/>		

		</display:table>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>