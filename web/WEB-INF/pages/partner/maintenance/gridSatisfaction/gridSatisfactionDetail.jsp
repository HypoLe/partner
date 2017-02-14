<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridSatisfactionForm'});
});
</script>

<html:form action="/gridSatisfactions.do?method=save" styleId="gridSatisfactionForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="gridSatisfaction.form.heading"/></div>
	</caption>

	<tr>
<!-- 地市区域 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridSatisfactionForm.region}" beanId="tawSystemAreaDao" />
		</td>
		
		<td class="label"> 
			县区&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridSatisfactionForm.city}" beanId="tawSystemAreaDao" />

		</td>		
		
		
	</tr>	

	<tr>

<!-- 合作伙伴 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.provider}
		</td>

<!-- 网格 -->		
		<td class="label">
			<fmt:message key="gridSatisfaction.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.grid}		
		</td>		
	</tr>

	<tr>
	
<!-- 年 -->	
		<td class="label">
			<fmt:message key="gridSatisfaction.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.year}年								
		</td>
	
<!-- 月份 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.month" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.month}月					
		</td>
	</tr>
<!-- 综合评价 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.synRating" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.synRating}
		</td>
<!-- 与业主关系维系满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.tieMaintenance" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.tieMaintenance}
		</td>
	</tr>
<!-- 故障处理满意度 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.faultDispose" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.faultDispose}
		</td>
<!-- 语音网络质量满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.phonicsQuality" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.phonicsQuality}
		</td>
	</tr>
<!-- 营业厅满意度 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.businessLobby" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.businessLobby}
		</td>
<!-- 投诉客户满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.customerComplaints" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.customerComplaints}
		</td>
	</tr>
<!-- 价值客户满意度 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.valueCustomer" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.valueCustomer}
		</td>
<!-- 集团客户满意度 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.corporateCustomer" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.corporateCustomer}
		</td>
	</tr>
<!-- 主动接受基层公司管理、调遣和检查 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.companyAct" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.companyAct}
		</td>
<!-- 维护人员技术能力、储备及流失情况 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.personnelStatus" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.personnelStatus}
		</td>
	</tr>
<!-- 仪器、仪表到位率及完好率情况 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.instrumentStatus" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.instrumentStatus}
		</td>
<!-- 管理水平 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.managementAbility" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${gridSatisfactionForm.managementAbility}
		</td>
	</tr>
	<tr>
		<!-- 上报人 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.reportUser" />
		</td>
		<td class="content" colspan="3">
			<eoms:id2nameDB id="${gridSatisfactionForm.reportUser}" beanId="tawSystemUserDao" />
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
					var url=eoms.appPath+'/partner/maintenance/gridSatisfactions.do?method=edit&id=${gridSatisfactionForm.id}';
					location.href=url}" />
				<input type="button" class="btn"
					value="<fmt:message key="button.delete"/>"
					onclick="javascript:if(confirm('确定删除吗?')){
					var url=eoms.appPath+'/partner/maintenance/gridSatisfactions.do?method=remove&id=${gridSatisfactionForm.id}';
					location.href=url}" />
			
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${gridSatisfactionForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>