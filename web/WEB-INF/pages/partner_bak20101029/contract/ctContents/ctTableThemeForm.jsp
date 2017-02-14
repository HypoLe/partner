<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctTableThemeForm'});
});
</script>

<html:form action="/ctTableThemes.do?method=save" styleId="ctTableThemeForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctTableTheme.form.heading"/></div>
	</caption>

	<tr>
		<td  class="label">
			<fmt:message key="ctTableTheme.themeName" />
		</td>
		<td class="content">
			<html:text property="themeName" styleId="themeName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableThemeForm.themeName}" />
		</td>
	</tr>
	<!--  tr>
		<td  class="label">
			<fmt:message key="ctTableTheme.isLeaf" />
		</td>
		<td class="content">		
		  <eoms:comboBox name="leaf" id="leaf" initDicId="10301" form="ctTableThemeForm"/>
		</td>
	</tr>-->
	<tr>
		<td  class="label">
			<fmt:message key="ctTableTheme.isOpen" />
		</td>
		<td class="content">	
			<eoms:comboBox name="isOpen" id="isOpen" initDicId="10301" defaultValue="1030101" form="ctTableThemeForm"/>
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="ctTableTheme.isDeleted" />
		</td>
		<td class="content">
			<eoms:comboBox name="isDeleted" id="isDeleted" initDicId="10301" defaultValue="1030102" form="ctTableThemeForm"/>			
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="ctTableTheme.orderBy" />
		</td>
		<td class="content">
			<html:text property="orderBy" styleId="orderBy"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableThemeForm.orderBy}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty ctTableThemeForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('<fmt:message key="message.delMessage"/>')){
						var url='${app}/partner/contract/ctTableThemes.do?method=remove&nodeId=${ctTableThemeForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${ctTableThemeForm.id}" />
<html:hidden property="nodeId" value="${ctTableThemeForm.nodeId}" />
<html:hidden property="parentNodeId" value="${ctTableThemeForm.parentNodeId}" />
<html:hidden property="leaf" value="${ctTableThemeForm.leaf}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>