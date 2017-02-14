<%@ include file="/common/taglibs.jsp"%>

<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'individualSpecialForm'});
});

</script>

<html:form action="/individualSpecials.do?method=save" styleId="individualSpecialForm" method="post"> 

<fmt:bundle basename="config/applicationResource-individual">

<table class="formTable">
	<caption>
		<div class="header center"><bean:message key='individualSpecial.form.heading'/></div>
	</caption>
<!-- 
	<tr>
		<td class="label">
			<fmt:message key="individualSpecial.userId" />
		</td>
		<td class="content">
			<html:text property="userId" styleId="userId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualSpecialForm.userId}" />
		</td>
	</tr>
		<tr>
		<td class="label">
			<fmt:message key="individualSpecial.createDate" />
		</td>
		<td class="content">
			<html:text property="createDate" styleId="createDate"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualSpecialForm.createDate}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="individualSpecial.isDeleted" />
		</td>
		<td class="content">
			<html:text property="isDeleted" styleId="isDeleted"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualSpecialForm.isDeleted}" />
		</td>
	</tr>
	
 -->
	<tr>
		<td class="label" align="right">
		<bean:message key='individualSpecial.specialName'/>
		</td>
		<td class="content">
			<html:text property="specialName" styleId="specialName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${individualSpecialForm.specialName}" />
		</td>
	</tr>


	<tr>
		<td class="label" align="right">
			${eoms:a2u('描&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp述')}
		</td>
		<td class="content" >
			<html:textarea property="remark" styleId="remark"
						styleClass="text medium"
						 value="${individualSpecialForm.remark}" />
		</td>
	</tr>

</table>
</fmt:bundle>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty individualSpecialForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/individual/individualSpecials.do?method=remove&id=${individualSpecialForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${individualSpecialForm.id}" />
<html:hidden property="userId" value="${individualSpecialForm.userId}" />
<html:hidden property="createDate" value="${individualSpecialForm.createDate}" />
<html:hidden property="isDeleted" value="${individualSpecialForm.isDeleted}" />
<html:hidden property="isleaf" value="${individualSpecialForm.isleaf}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>