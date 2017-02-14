<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">

<content tag="heading">
	<caption>
		<div class="header center"  style="text-align:center;font-weight:bold;">
		<c:if test="${state == ''}">
			付款计划列表
		</c:if>
		<c:if test="${state == '2'}">
			有效付款计划列表
		</c:if>
		<c:if test="${state == '1'}">
			驳回付款计划列表
		</c:if>
		<c:if test="${state == '0'}">
			待审付款计划列表
		</c:if>
		</div>
	</caption>	
</content>

	<display:table name="pnrFeeInfoMainList" cellspacing="0" cellpadding="0"
		id="pnrFeeInfoMainList" pagesize="${pageSize}" class="table pnrFeeInfoMainList"
		export="false"
		requestURI="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=search&state=${state}"
		sort="list" partialList="true" size="resultSize">

	<display:column property="feeNO" sortable="true"
			headerClass="sortable" titleKey="pnrFeeInfoMain.feeInfoNO"  paramId="id" paramProperty="id"/>

	<display:column property="feeName" sortable="true"
			headerClass="sortable" titleKey="pnrFeeInfoMain.feeInfoName"  paramId="id" paramProperty="id"/>

	<display:column property="contractNO" sortable="true"
			headerClass="sortable" title="合同编号" paramId="id" paramProperty="id"/>

	<display:column property="contractAmount" sortable="true"
			headerClass="sortable" title="合同金额(万元)" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrFeeInfoMain.state">
		<c:if test="${pnrFeeInfoMainList.state == 0}">待审核</c:if>
		<c:if test="${pnrFeeInfoMainList.state == 1}">驳回</c:if>
		<c:if test="${pnrFeeInfoMainList.state == 2}">有效</c:if>
	</display:column>
	<c:if test="${state == ''||state == '2'}">
	<display:column title="查看" headerClass="imageColumn">
		<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${pnrFeeInfoMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${state == '1'}">
	<display:column title="修改" headerClass="imageColumn">
		<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=edit&id=${pnrFeeInfoMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${state == '0'}">
	<display:column title="审核" headerClass="imageColumn">
		<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=audit&id=${pnrFeeInfoMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
		<display:setProperty name="paging.banner.item_name" value="pnrFeeInfoMain" />
		<display:setProperty name="paging.banner.items_name" value="pnrFeeInfoMains" />
	</display:table>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >付款计划编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">付款计划名称</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label" >合同编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">合同金额(万元)</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">状态</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">查看</td>
			</tr>
			<tr>
				<td  style="text-align:center;"  colspan="6" >暂无记录</td>
			</tr>
		</table>
	</c:if>		
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>