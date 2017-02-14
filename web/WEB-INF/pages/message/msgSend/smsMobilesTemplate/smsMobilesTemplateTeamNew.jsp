<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="/smsMobilesTemplates.do?method=xsaveTeam" styleId="SmsMobilesTemplateForm" method="post"> 

<fmt:bundle
	basename="com/boco/eoms/message/config/ApplicationResources-msg">

<table class="formTable">
	<caption>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="smsUserMgr.teamName" />
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''"  />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="smsUserMgr.teamName.etc" />
		</td>
		<td class="content">
			<html:text property="remark" styleId="remark"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''"  />
		</td>
	</tr>

	


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty mappingForm.id}">
				<!--  <input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/message/SmsMobilesTemplate.do?method=remove&id=${mappingForm.id}';
						location.href=url}"	/>-->
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${SmsMobilesTemplateForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>