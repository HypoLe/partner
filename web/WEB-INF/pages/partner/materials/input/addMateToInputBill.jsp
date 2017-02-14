<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8"%>

<%
	response.setHeader("Cache-Control", "Public");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<html>

	<body>
		<form action="material.do?method=selectByItem" method="post"
			name="select">
			<table class="formTable">
				<caption>
					<div class="header center">
						添加货品
					</div>
				</caption>

				<tr>
					<td class="label">
						材料编码
					</td>
					<td class="content">
						<input class="text" type="text" name="encode" />
					</td>
					<td class="label">
						材料类别
					</td>
					<td>
						<input class="text" type="text" name="categoryNum" />
					</td>
				</tr>

				<tr>
					<td class="label">
						材料名称
					</td>
					<td class="content">
						<input class="text" type="text" name="name" />
					</td>

					<td class="label">
						材料条码
					</td>
					<td class="content">
						<input class="text" type="text" name="" />
					</td>
				</tr>
			</table>

			<br>
			<input type="hidden" name="flag" value="${flag}">

			<table>
				<tr>
					<td>
						<input type="submit" class="btn" value="查找" />
						&nbsp;&nbsp;

					</td>
				</tr>
			</table>
			<br />
			<div style="border-top: 1px solid #000; width: 100%; height: 1px;">
			</div>
			<br />

		</form>
		<form action="billMaterial.do?method=addBillMaterial" method="post"
			name="save">

			<display:table name="list" cellspacing="0" cellpadding="0" id="list"
				sort="list" class="table">

				<display:column property="id" sortable="true" headerClass="sortable"
					title="ID" />
				<display:column property="encode" sortable="true"
					headerClass="sortable" title="材料编码" />
				<display:column property="name" sortable="true"
					headerClass="sortable" title="材料名称" />

				<display:column property="unit" sortable="true"
					headerClass="sortable" title="材料单位" />
				<display:column property="inputPrice" sortable="true"
					headerClass="sortable" title="入库价" />

				<display:column title="选择">
					<input id='ch2' type='checkbox' name='checkbox' value="${list.id }" />
				</display:column>


			</display:table>
			<input type="hidden" name="storeBillingDate"
				value="${storeBillingDate}">
			<input type="hidden" name="storeCompany" value="${storeCompany}">
			<input type="hidden" name="storeSid" value="${storeSid}">
			<input type="hidden" name="storeDepartment"
				value="${storeDepartment}">
			<input type="hidden" name="storeRequisitioner"
				value="${storeRequisitioner}">
			<input type="hidden" name="storeOriginalNum"
				value="${storeOriginalNum}">
			<input type="hidden" name="remark" value="${remark}">
			<input type="hidden" name="storeNum" value="${storeNum}">
			<input type="hidden" name="username" value="${username}">
			<input type="hidden" name="storeName" value="${storeName}">
			<input type="hidden" name="storeInputId" value="${storeInputId}">
			<input type="hidden" name="storeMakeBillDate" value="${storeMakeBillDate}">
			<input type="hidden" name="storeMakeBillPeople" value="${storeMakeBillPeople}">
			<input type="hidden" name="outStoreSid" id="outStoreSid"
				value="${outStoreSid}">
			<input type="hidden" name="inputStoreSid" id="inputStoreSid"
				value="${inputStoreSid}">
			<input type="hidden" value="${flag}" name="flag">
			<input type="submit" value="保存">
			<input type="button" class="bnt_an" value="返回"
				onclick="history.back();" />
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>