<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8"%>
<html>

	<body>
		<form action="billMaterial.do?method=select" method="post">
			<table class="formTable">
				<tr>
					<td class="label">
						相关仓库
						<select name="storeSid">
							<option value="1">
								仓库一
							</option>
							<option value="2">
								仓库二
							</option>
							<option value="3">
								仓库三
							</option>
						</select>
					</td>
					<td class="label">
						开单日期
						<input type="text" name="start">~<input type="text" name="end">
					</td>
				</tr>
				
				<tr>

					<td class="label">
						单号
						<input type="text" name="storeNum">
					</td>


					<td class="label">
						单据类型
						<select name="storeBillType">
							<option value="出库">
								出库
							</option>
							<option value="入库">
								入库
							</option>
							<option value="调拨">
								调拨
							</option>
						</select>
					</td>

				</tr>
				<tr>
					<td class="label">
						经办人
						<input type="text" name="storeMakeBillPeople">
					</td>

					<td class="label">
						对方单位
						<input type="text" name="storeCompany">
					</td>
				</tr>
				<tr>
					<td class="label">
						原始单号
						<input type="text" name="storeOriginalNum">
					</td>

					<td class="label">
						条形码
						<input type="text" name="">
					</td>
				</tr>

			

			</table>



			<display:table name="list" cellspacing="0" cellpadding="0" id="list"
				sort="list" class="table">
				<display:column sortable="true" headerClass="sortable" title="操作">
					<html:link
						href="${app}/materials/billMaterial.do?method=delete&&billId=${billId}"
						paramId="id" paramProperty="id" paramName="list">
						修改
					</html:link>
					<html:link
						href="${app}/materials/billMaterial.do?method=delete&&billId=${billId}"
						paramId="id" paramProperty="id" paramName="list">
						详情
					</html:link>
					<html:link
						href="${app}/materials/billMaterial.do?method=delete&&billId=${billId}"
						paramId="id" paramProperty="id" paramName="list">
						删除
					</html:link>
				</display:column>
				<display:column property="storeNum" sortable="true"
					headerClass="sortable" title="单号" />
				<display:column property="storeOriginalNum" sortable="true"
					headerClass="sortable" title="原始单号" />

				<display:column property="storeRequisitioner" sortable="true"
					headerClass="sortable" title="领料人" />
				<display:column property="storeExamineStatus" sortable="true"
					headerClass="sortable" title="审核状态" />
				<display:column property="storeExaminePeople" sortable="true"
					headerClass="sortable" title="审核人" />
				<display:column property="storeMakeBillDate" sortable="true"
					headerClass="sortable" title="制单日期" />
				<display:column property="storeBillingDate" sortable="true"
					headerClass="sortable" title="开单日期" />
				<display:column property="storeMakeBillPeople" sortable="true"
					headerClass="sortable" title="制单人" />
				<display:column property="storeSname" sortable="true"
					headerClass="sortable" title="仓库名称" />
				<display:column property="storeCompany" sortable="true"
					headerClass="sortable" title="往来单位" />
				<display:column property="storeDepartment" sortable="true"
					headerClass="sortable" title="部门" />
				<display:column property="storeBillType" sortable="true"
					headerClass="sortable" title="单据类型" />

				
			</display:table>

			<input type="submit" value="保存">

		</form>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>