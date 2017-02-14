<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctContentsDictForm'});
});
</script>

<html:form action="/ctContentsDicts.do?method=save" styleId="ctContentsDictForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContentsDict.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsDict.contentId" />
		</td>
		<td class="content">
			<html:text property="contentId" styleId="contentId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsDictForm.contentId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsDict.dictId" />
		</td>
		<td class="content">
			<html:text property="dictId" styleId="dictId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsDictForm.dictId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsDict.dictType" />
		</td>
		<td class="content">
			<html:text property="dictType" styleId="dictType"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsDictForm.dictType}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty ctContentsDictForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/ctContentsDict/ctContentsDicts.do?method=remove&id=${ctContentsDictForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${ctContentsDictForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>