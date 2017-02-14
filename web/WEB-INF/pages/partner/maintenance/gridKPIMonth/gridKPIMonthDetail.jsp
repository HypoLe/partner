<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridKPIMonthForm'});
});
</script>

<html:form action="/gridKPIMonths.do?method=save" styleId="gridKPIMonthForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="gridKPIMonth.form.heading"/></div>
	</caption>
<!-- 年 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.year}年									
		</td>
<!-- 月 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.month" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.month}月					
		</td>
		
	</tr>
<!-- 区域地市 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridKPIMonthForm.region}" beanId="tawSystemAreaDao" />
		</td>
		
<!-- 维护公司 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.companyName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.companyName}
		</td>
	</tr>
<!-- 网格名称 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.gridName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.gridName}
		</td>
<!-- 网月度基站告警比例 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.bsWarning" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.bsWarning}
		</td>
	</tr>
<!-- 基站告警数量削减 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.bswReduce" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.bswReduce}
		</td>
<!-- 最坏小区比 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.mbplot" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.mbplot}
		</td>
	</tr>
<!-- 无线接入性 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.wirelessIn" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.wirelessIn}
		</td>
<!-- 掉话率 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.dropCallRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.dropCallRate}
		</td>
	</tr>	
<!-- 基站退服率 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.bsOutRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.bsOutRate}
		</td>
<!-- 接入网重大故障率 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.mostfailRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.mostfailRate}
		</td>
	</tr>
<!-- 季度板件无故障返修率 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.nofaiRamRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.nofaiRamRate}
		</td>
<!-- 网络故障（基站）工单处理及时率 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.workOrderATR" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIMonthForm.workOrderATR}
		</td>
		
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<c:if test="${param.flag!='search'}">
				<input type="button" class="btn"
					value="<fmt:message key="button.edit"/>"
					onclick="javascript:if(confirm('确定修改吗?')){
					var url=eoms.appPath+'/partner/maintenance/gridKPIMonths.do?method=edit&id=${gridKPIMonthForm.id}';
					location.href=url}" />
				<input type="button" class="btn"
					value="<fmt:message key="button.delete"/>"
					onclick="javascript:if(confirm('确定删除吗?')){
					var url=eoms.appPath+'/partner/maintenance/gridKPIMonths.do?method=remove&id=${gridKPIMonthForm.id}';
					location.href=url}" />
			
			</c:if>

		</td>
	</tr>
</table>
<html:hidden property="id" value="${gridKPIMonthForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>