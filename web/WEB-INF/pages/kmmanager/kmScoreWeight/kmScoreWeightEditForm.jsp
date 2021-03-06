<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmScoreWeightForm'});
});
</script>

<html:form action="/kmScoreWeights.do?method=editSave" styleId="kmScoreWeightForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<table class="formTable">
	<c:choose>
		<c:when test="${kmScoreWeightForm.nodeId<10101}">
			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.powerName" />
				</td>
				<td class="content">
					<html:text property="powerName" styleId="powerName"
								styleClass="text medium"
								alt="allowBlank:false,vtext:'',maxLength:32" value="${kmScoreWeightForm.powerName}" />
				</td>
			</tr>
		
			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.powerWeight" />
				</td>
				<td class="content">
					<html:text property="powerWeight" styleId="powerWeight"
								styleClass="text medium"
								alt="allowBlank:false,vtext:'',vtype:'number',maxLength:2" value="${kmScoreWeightForm.powerWeight}" />
				</td>
			</tr>
		
			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.area" />
				</td>
				<td class="content">
					<html:text property="area" styleId="area"
								styleClass="text medium"
								alt="allowBlank:false,vtext:'',maxLength:1000" value="${kmScoreWeightForm.area}" />
				</td>
			</tr>
		

		</c:when>
		<c:otherwise>
			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.powerName" />
				</td>
				<td class="content">
					${kmScoreWeightForm.powerName}
				    <input type="hidden" name="powerName" value="${kmScoreWeightForm.powerName}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.powerWeight" />
				</td>
				<td class="content">
					${kmScoreWeightForm.powerWeight}
					<input type="hidden" name="powerWeight" value="${kmScoreWeightForm.powerWeight}" />
				</td>
			</tr>
		
			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.actionName" />
				</td>
				<td class="content">
					<html:text property="actionName" styleId="actionName"
								styleClass="text medium"
								alt="allowBlank:false,vtext:'',maxLength:32" value="${kmScoreWeightForm.actionName}" />
				</td>
			</tr>
		
			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.actionWeight" />
				</td>
				<td class="content">
					<html:text property="actionWeight" styleId="actionWeight"
								styleClass="text medium"
								alt="allowBlank:false,vtext:'',vtype:'number',maxLength:2" value="${kmScoreWeightForm.actionWeight}" />
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
			<tr>
				<td class="label">
					<fmt:message key="kmScoreWeight.remark" />
				</td>
				<td class="content">
					<html:textarea property="remark" styleId="remark" cols="37" rows="8"
						alt="maxLength:1000,vtext:'',maxLength:1000" value="${kmScoreWeightForm.remark}" />
				</td>
			</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty kmScoreWeightForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/kmmanager/kmScoreWeights.do?method=remove&nodeId=${kmScoreWeightForm.nodeId}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="isDeleted" value="${kmScoreWeightForm.isDeleted}"/>
<html:hidden property="id" value="${kmScoreWeightForm.id}" />
<html:hidden property="nodeId" value="${kmScoreWeightForm.nodeId}" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>