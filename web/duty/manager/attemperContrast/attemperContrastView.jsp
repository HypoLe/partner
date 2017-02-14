<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperContrastForm'});
});

function GoBack(){
   window.history.back();
}

</script>

<html:form action="/attemperContrasts.do?method=save" styleId="attemperContrastForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemperContrast.form.heading"/></div>
	</caption>
	
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.friendCompany" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperContrastForm.friendCompany}" beanId="tawSystemDictTypeDao" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.beginTime" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="beginTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.subCompany" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultfilialeId" itemId="${attemperContrastForm.subCompany}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.endTime" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="endTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.cableClass" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="cableClass" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.persistTimes" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="persistTimes" />
			(<fmt:message key="attemperContrast.hour.name"/>)
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.ifNormalSwitch" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="contrastIfSwitch" itemId="${attemperContrastForm.ifNormalSwitch}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.subRing" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="subRing" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.mainCable" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="mainCable" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.protectCable" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="protectCable" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.netNameA" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="netNameA" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.netNameB" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="netNameB" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.isAppEffect" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${attemperContrastForm.isAppEffect}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.finishResult" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="finishResult" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.beforeIntoA" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="beforeIntoA" />&nbsp;(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.beforeOuterA" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="beforeOuterA" />&nbsp;(db)
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.beforeIntoB" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="beforeIntoB" />&nbsp;(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.beforeOuterB" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="beforeOuterB" />&nbsp;(db)
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.afterIntoA" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="afterIntoA" />&nbsp;(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.afterOuterA" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="afterOuterA" />&nbsp;(db)
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.afterIntoB" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="afterIntoB" />&nbsp;(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.afterOuterB" />
		</td>
		<td class="content">
			<bean:write name="attemperContrastForm" property="afterOuterB" />&nbsp;(db)
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.adjustReason" />
		</td>
		<td class="content" colspan="3">
			<c:choose><c:when test="${attemperContrastForm.adjustReason !=null}">	
				<bean:define id="adjustReason" name="attemperContrastForm" property="adjustReason" />
					<%= com.boco.eoms.base.util.StaticMethod.toHtml((String)adjustReason) %>
			</c:when></c:choose>&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.remark" />
		</td>
		<td class="content" colspan="3">
			<c:choose><c:when test="${attemperContrastForm.remark !=null}">	
				<bean:define id="remark" name="attemperContrastForm" property="remark" />
					<%= com.boco.eoms.base.util.StaticMethod.toHtml((String)remark) %>
			</c:when></c:choose>&nbsp;
		</td>
	</tr>
</table>

<table>
	<tr>
		<td>
			<c:choose><c:when test="${attemperContrastForm.deptId==sessionform.deptid}">	
			<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
			onclick="javascript:{var url='${app}/duty/attemperContrasts.do?method=edit&id=${attemperContrastForm.id}';
					location.href=url};"	/>&nbsp;&nbsp;
			<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('<fmt:message key="faultCommont.is.delete"/>')){
					var url='${app}/duty/attemperContrasts.do?method=remove&id=${attemperContrastForm.id}';
					location.href=url};"	/>&nbsp;&nbsp;
			</c:when></c:choose>
			<input type="button" class="btn" value="<fmt:message key="button.goback"/>" onclick="GoBack();" />
		</td>
	</tr>
</table>

</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>