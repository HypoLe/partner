<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExamTestSpecialtyForm'});
});
</script>

<html:form action="/kmExamTestSpecialtys.do?method=save" styleId="kmExamTestSpecialtyForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmExamTestSpecialty.form.heading"/></div>
	</caption>	

	<tr>
		<td  class="label">
			<fmt:message key="kmExamTestSpecialty.specialtyName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="specialtyName" styleId="specialtyName"
						styleClass="text medium" maxlength="20"
						alt="allowBlank:false,vtext:''" value="${kmExamTestSpecialtyForm.specialtyName}" />
		</td>
	</tr>

	<!-- tr>
		<td  class="label">
			<fmt:message key="kmExamTestSpecialty.isDeleted" />
		</td>
		<td class="content">
			<html:text property="isDeleted" styleId="isDeleted"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExamTestSpecialtyForm.isDeleted}" />
		</td>
	</tr> -->
<html:hidden property="isDeleted" value="0" />
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty kmExamTestSpecialtyForm.specialtyID}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定要删除吗？')){
						var url='${app}/kmmanager/kmExamTestSpecialtys.do?method=remove&nodeId=${kmExamTestSpecialtyForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="specialtyID" value="${kmExamTestSpecialtyForm.specialtyID}" />
<html:hidden property="nodeId" value="${kmExamTestSpecialtyForm.nodeId}" />
<html:hidden property="parentNodeId" value="${kmExamTestSpecialtyForm.parentNodeId}" />
<html:hidden property="leaf" value="${kmExamTestSpecialtyForm.leaf}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>