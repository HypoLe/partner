<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/feeInfo/config/applicationResources-partner-feeInfo">

<content tag="heading">
	<caption>
		<div class="header center"  style="text-align:center;font-weight:bold;">
			本人相关付费流程列表
		</div>
	</caption>	
</content>

	<display:table name="pnrFeeInfoMainList" cellspacing="0" cellpadding="0"
		id="pnrFeeInfoMainList" pagesize="${pageSize}" class="table pnrFeeInfoMainList"
		export="false"
		requestURI="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=search&state=${state}"
		sort="list" partialList="true" size="resultSize">
<display:column property="contractNO" sortable="true"
			headerClass="sortable" title="合同编号" paramId="id" paramProperty="id"/>
<display:column property="contractName" sortable="true"
			headerClass="sortable" title="合同名称"  paramId="id" paramProperty="id"/>

	

	<display:column property="contractAmount" sortable="true"
			headerClass="sortable" title="合同金额(万元)" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrFeeInfoMain.state">
		<c:if test="${pnrFeeInfoMainList.state == 0}">待审核</c:if>
		<c:if test="${pnrFeeInfoMainList.state == 1}">驳回</c:if>
		<c:if test="${pnrFeeInfoMainList.state == 2}">有效</c:if>
	</display:column>
	<display:column title="流程向导" headerClass="imageColumn">
		<img align=left height="15px"  width="65px"  src="${app}/images/icons/showflow.gif" alt="查看流程图" onclick="window.open('${app}/partner/baseinfo/pnrFlow.do?method=showFeeFlow&id=${pnrFeeInfoMainList.id}&flowType=feeinfo','','toolbar=no,scrollBars=no,width=800,height=490')"/>	
	</display:column>
	</display:table>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable"  border="1"   bordercolor="#98C0F4">
		</br>
			<tr>
				<%--<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >付款计划编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">付款计划名称</td>
				--%><td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label" >合同编号</td>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" class="label">合同名称</td>
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
		<table>
			<tr>
				<td><font color='red'>请选择一个合同，系统可以为您提供该付费流程向导图，您可以在图上进行相应任务的操作。</font></td>
			</tr>
		</table>
<%@ include file="/common/footer_eoms.jsp"%>