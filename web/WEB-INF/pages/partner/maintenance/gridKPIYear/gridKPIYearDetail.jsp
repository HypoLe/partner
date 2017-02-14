<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridKPIYearForm'});
});
</script>

<html:form action="/gridKPIYears.do?method=save" styleId="gridKPIYearForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="gridKPIYear.form.heading"/></div>
	</caption>
<!-- 年 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.year}年									
		</td>
<!-- 区域地市 -->
		<td class="label">
			<fmt:message key="gridKPIYear.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridKPIYearForm.region}" beanId="tawSystemAreaDao" />
		</td>
		
	</tr>
<!-- 维护公司 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.companyName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.companyName}
		</td>
<!-- 网格名称 -->
		<td class="label">
			<fmt:message key="gridKPIYear.gridName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.gridName}
		</td>
	</tr>
<!-- 网月度基站告警比例 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.bsWarning" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.bsWarning}
		</td>
<!-- 基站告警数量削减 -->
		<td class="label">
			<fmt:message key="gridKPIYear.bswReduce" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.bswReduce}
		</td>
	</tr>
<!-- 最坏小区比 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.mbplot" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.mbplot}
		</td>
<!-- 无线接入性 -->
		<td class="label">
			<fmt:message key="gridKPIYear.wirelessIn" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.wirelessIn}
		</td>
	</tr>
<!-- 掉话率 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.dropCallRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.dropCallRate}
		</td>
<!-- 基站退服率 -->
		<td class="label">
			<fmt:message key="gridKPIYear.bsOutRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.bsOutRate}
		</td>
	</tr>	
<!-- 接入网重大故障率 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.mostfailRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.mostfailRate}
		</td>
<!-- 季度板件无故障返修率 -->
		<td class="label">
			<fmt:message key="gridKPIYear.nofaiRamRate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridKPIYearForm.nofaiRamRate}
		</td>
	</tr>
<!-- 网络故障（基站）工单处理及时率 -->
	<tr>
		<td class="label" colspan="1">
			<fmt:message key="gridKPIYear.workOrderATR" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			${gridKPIYearForm.workOrderATR}
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
					var url=eoms.appPath+'/partner/maintenance/gridKPIYears.do?method=edit&id=${gridKPIYearForm.id}';
					location.href=url}" />
				<input type="button" class="btn"
					value="<fmt:message key="button.delete"/>"
					onclick="javascript:if(confirm('确定删除吗?')){
					var url=eoms.appPath+'/partner/maintenance/gridKPIYears.do?method=remove&id=${gridKPIYearForm.id}';
					location.href=url}" />
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${gridKPIYearForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>