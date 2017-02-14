<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<html:form action="/faultCommonts.do?method=edit" styleId="faultCommontForm" method="post"> 
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'faultCommontForm'});
});

/**
 * 生成故障记录表
 */
function addFault(typeName) {
	var url= '${app}/duty/' + typeName + 's.do?method=add&faultCommontId=${faultCommontForm.id}';
	window.navigate(url);
}
</script>

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="faultCommont.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.title" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="title" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.greffier" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${faultCommontForm.greffier}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.beginTime" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="beginTime" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.typeId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${faultCommontForm.typeId}" beanId="tawSystemDictTypeDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.declarer" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="declarer" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.isFirstFind" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultIsFirstFind" itemId="${faultCommontForm.isFirstFind}" beanId="id2nameXML" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="faultCommont.isAppEffect" />
		</td>
		<td class="content">
		<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${faultCommontForm.isAppEffect}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.upSerialNo" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="upSerialNo" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultAddress" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="faultAddress" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.endTime" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="endTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.resumeTimeSlot" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="resumeTimeSlot" />&nbsp;
			<fmt:message key="faultCommont.tims.unit" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.timeSlot" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="timeSlot" />&nbsp;
			<fmt:message key="faultCommont.tims.unit" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.reportTime" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="reportTime" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.linkPhone" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="linkPhone" />
		</td>
	</tr>
	<c:choose>
		<c:when	test="${faultCommontForm.isAppEffect=='1'}"><tr id="ifEffectDiv" style="display:block"></c:when>
		<c:otherwise><tr id="ifEffectDiv" style="display:none"></c:otherwise>
	</c:choose>
		<td class="label">
			<fmt:message key="faultCommont.resumeTime" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="resumeTime" />
		</td>
		<td class="label">
			<fmt:message key="faultCommont.appEffect" />
		</td>
		<td class="content">
			<bean:write name="faultCommontForm" property="appEffect" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultPhenomenon" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCommontForm" property="faultPhenomenon" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultCause" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCommontForm" property="faultCause" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.dealDetail" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCommontForm" property="dealDetail" />
		</td>
	</tr>
	<tr>
		<td class="label">
			${eoms:a2u('通用任务工单')}
		</td>
		<td class="content" colspan="3">
			<a href="#" onclick='return faultsheet("<bean:write name="faultCommontForm" property="serialNos" />")'>
				<bean:write name="faultCommontForm" property="serialNos" />
			</a>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.serialno" />
		</td>
		<td class="content" >
			<a href="#" onclick='return faultsheet("<bean:write name="faultCommontForm" property="serialno" />")'>
				<bean:write name="faultCommontForm" property="serialno" />
			</a>
		</td>
		<td class="label">
			<fmt:message key="faultCommont.informOfficeTime" />
		</td>
		<td class="content" >
			<bean:write name="faultCommontForm" property="informOfficeTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.faultComment" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="faultCommontForm" property="faultComment" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="faultCommont.estate" />
		</td>
		<td class="content" colspan="3">
			<eoms:dict key="dict-duty" dictId="faultEstate" itemId="${faultCommontForm.estate}" beanId="id2nameXML" />
		</td>
	</tr>
</table>

<c:choose>
	<c:when	test="${faultCommontForm.roomId==sessionform.roomId}">
		<table>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<c:if test="${not empty faultCommontForm.id&&faultCommontForm.estate!=1}">
						<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
							onclick="javascript:{
								var url='${app}/duty/faultCommonts.do?method=edit&id=${faultCommontForm.id}';
								location.href=url};"	/>
						<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
							onclick="javascript:if(confirm('<fmt:message key="faultCommont.is.delete"/>')){
								var url='${app}/duty/faultCommonts.do?method=remove&id=${faultCommontForm.id}';
								location.href=url};"	/>
					</c:if>
					<input type="button" property="ok" Class="btn" value="${eoms:a2u('生成设备故障')}" onclick="addFault('faultEquipment');" >
					<input type="button" property="ok" Class="btn" value="${eoms:a2u('生成线路故障')}" onclick="addFault('faultCircuit');" >
				</td>
			</tr>
		</table>
	</c:when>
</c:choose>
<html:hidden property="id" value="${faultCommontForm.id}" />
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>