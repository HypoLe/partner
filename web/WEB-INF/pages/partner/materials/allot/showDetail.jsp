<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8"%>
<html>

	<body>

		<div>
			<p>
			<center>
				<span style="font-size: 20px">调拨单详情</span>
			</center>
			</p>
			<p>
				<span style="color: red">单号：</span>${storeAllot.storeNum}
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span style="color: red">制单人：</span>${storeAllot.storeMakeBillPeople}
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span style="color: red">制单日期：</span>${storeAllot.storeMakeBillDate}
			</p>
		</div>



		<table class="formTable">
			<tr>
		<td class="label">
			调拨日期
		</td>
		<td class="content">
			<input type="text" readonly="true" value="${storeAllot.storeBillingDate}" onClick="popUpCalendar(this, this,null,null,null,true,-1);"/>
		</td>
		<td class="label">
			往来单位
		</td>
		<td class="content">								
			<input readonly="true" class="text" type="text" value="${storeAllot.storeCompany}"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			仓库
		</td>
		<td class="content">
			<select  disabled="true" >
			<option >${storeAllot.storeSname}</option>
			</select>
		</td>

		<td class="label">
			原始单号
		</td>
		<td class="content">								
			<input class="text" readonly="readonly" value="${storeAllot.storeOriginalNum}"/>		
		</td>
	</tr>

	<tr>
		<td class="label">
			经办人
		</td>
		<td class="content">
			<input readonly="true" class="text" type="text" value="${storeAllot.storeRequisitioner}" />
		</td>

		<td class="label">
			制单人			
		</td>
		<td class="content">
			<input readonly="true" class="text" type="text" value="${storeAllot.storeMakeBillPeople}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
		    <input readonly="true" type="text" value="${storeAllot.storeRemark}" class="text max" />
		</td>
	</tr>
		</table>
		<br />



		<display:table name="list" cellspacing="0" cellpadding="0" id="list"
			sort="list" class="table" export="true"
			requestURI="${app}/materials/storeAllot.do?method=view">
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
				<display:column property="totalAmount" sortable="true"
				headerClass="sortable" title="总金额" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
			<display:setProperty name="export.rtf" value="false" />
		</display:table>
		<table class="formTable">
	<tr>
	
		<input type="button" value="返回"
				onClick="window.location.href='${app}/materials/storeAllot.do?method=view'" />

	</tr>
		<%@ include file="/common/footer_eoms.jsp"%>
	</body>
</html>